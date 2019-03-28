package com.sunyard.sunfintech.prod.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.sunyard.sunfintech.account.model.bo.BatchPayDetail;
import com.sunyard.sunfintech.account.model.bo.BatchPayResponse;
import com.sunyard.sunfintech.account.provider.IAccountQueryService;
import com.sunyard.sunfintech.account.provider.IAccountTransferThirdParty;
import com.sunyard.sunfintech.core.Constants;
import com.sunyard.sunfintech.core.TransConsts;
import com.sunyard.sunfintech.core.base.BaseResponse;
import com.sunyard.sunfintech.core.base.BaseServiceSimple;
import com.sunyard.sunfintech.core.config.GlobalConfig;
import com.sunyard.sunfintech.core.dic.*;
import com.sunyard.sunfintech.core.exception.BusinessException;
import com.sunyard.sunfintech.core.exception.BusinessMsg;
import com.sunyard.sunfintech.core.util.CacheUtil;
import com.sunyard.sunfintech.core.util.DateUtils;
import com.sunyard.sunfintech.core.util.SeqUtil;
import com.sunyard.sunfintech.core.util.StringUtils;
import com.sunyard.sunfintech.dao.entity.*;
import com.sunyard.sunfintech.dao.mapper.PayEaccountBindMapper;
import com.sunyard.sunfintech.dao.mapper.PayTransSerialTransferMapper;
import com.sunyard.sunfintech.dao.mapper.ProdRefundReqDataMapper;
import com.sunyard.sunfintech.prod.model.bo.*;
import com.sunyard.sunfintech.prod.provider.IProdSearchService;
import com.sunyard.sunfintech.prod.provider.IProductRefundV2Service;
import com.sunyard.sunfintech.prod.utils.ProductPublicParams;
import com.sunyard.sunfintech.pub.provider.IEaccTransTransreqService;
import com.sunyard.sunfintech.pub.provider.IOrderCheck;
import com.sunyard.sunfintech.pub.provider.ITransReqService;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;

import java.math.BigDecimal;
import java.util.*;

/**
 * Created by terry on 2018/7/19.
 */
@Service(interfaceClass = IProductRefundV2Service.class)
@CacheConfig(cacheNames = "productRefundV2Service")
@org.springframework.stereotype.Service
public class ProductRefundV2Service extends BaseServiceSimple implements IProductRefundV2Service {

    @Autowired
    private ITransReqService transReqService;

    @Autowired
    private IProdSearchService prodSearchService;

    @Autowired
    private IAccountQueryService accountQueryService;

    @Autowired
    private IOrderCheck orderCheck;

    @Autowired
    private IEaccTransTransreqService eaccTransTransreqService;

    @Autowired
    private IAccountTransferThirdParty accountTransferThirdParty;

    @Autowired
    private ProdRefundReqDataMapper prodRefundReqDataMapper;

    @Autowired
    private PayTransSerialTransferMapper payTransSerialTransferMapper;

    @Autowired
    private PayEaccountBindMapper payEaccountBindMapper;

    @Override
    public List<ProdBusinessData> batchRefund(ProdBatchRepayOldRequest prodBatchRepayRequest) throws BusinessException {
        List<ProdBusinessData> prodBusinessDataList = new ArrayList<>();

        //记录业务流水
        TransTransreq transTransReq= transReqService.getTransTransReq(prodBatchRepayRequest.clone(), TransConsts.PRODREPAY_CODE,TransConsts.PRODREPAY_NAME,true);
        transReqService.insert(transTransReq);
        //验证标是否存在
        ProdProductinfo checkProductInfo = prodSearchService.queryProdInfo(prodBatchRepayRequest.getMer_no(),prodBatchRepayRequest.getProd_id());
        try{
            if(checkProductInfo == null){
                logger.info("【标的还款】========标的不存在");
                BaseResponse baseResponse=new BaseResponse();
                baseResponse.setRecode(BusinessMsg.INVALID_PRODUCT_ID);
                baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.INVALID_PRODUCT_ID));
                throw new BusinessException(baseResponse);
            }

            //如果是活期则不限制标的状态
            if(!(ProdType.CURRENT.getCode().equals(checkProductInfo.getProd_type()))){
                if(!ProdState.FOUND.getCode().equals(checkProductInfo.getProd_state())){

                    logger.info("-----------当前标的状态为----------------" + checkProductInfo.getProd_state());

                    transTransReq.setReturn_code(BusinessMsg.ESTABLISH_PRODUCT_FAILED);
                    transTransReq.setReturn_msg(BusinessMsg.getMsg(BusinessMsg.ESTABLISH_PRODUCT_FAILED));
                    transTransReq.setStatus(FlowStatus.FAIL.getCode());
                    transReqService.insert(transTransReq);

                    BaseResponse baseResponse=new BaseResponse();
                    baseResponse.setRecode(BusinessMsg.ESTABLISH_PRODUCT_FAILED);
                    baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.ESTABLISH_PRODUCT_FAILED) + "当前标未成立无法出账");
                    throw new BusinessException(baseResponse);
                }
            }

            logger.info("【标的还款】========开始校验金额");
            //校验金额
            if (!checkCustRepayForBatchRefund(prodBatchRepayRequest)) {
                logger.info("【标的还款】======== 交易金额和明细金额之和不匹配!");
                //不足，更新流水
                transTransReq.setReturn_code(BusinessMsg.FAIL);
                transTransReq.setReturn_msg(BusinessMsg.getMsg(BusinessMsg.FAIL) +
                        "交易金额和明细金额之和不匹配或者还款项与明细实际还款金额不匹配!");
                transTransReq.setStatus(FlowStatus.FAIL.getCode());
                transReqService.insert(transTransReq);

                BaseResponse baseResponse=new BaseResponse();
                baseResponse.setRecode(BusinessMsg.MONEY_ERROR);
                baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.MONEY_ERROR) +
                        "交易金额和明细金额之和不匹配或者还款项与明细实际还款金额不匹配!");
                throw new BusinessException(baseResponse);
            }
            List<BatchCustRepayOld> custRepayList=prodBatchRepayRequest.getCustRepayList();
            //检查标的余额
            List<AccountSubjectInfo> eaccCashAccount=accountQueryService.queryPlatAccountList(
                    checkProductInfo.getPlat_no(),checkProductInfo.getProd_account(),null, Ssubject.PROD.getCode());
            if(eaccCashAccount.size()>0){
                BigDecimal allAmt=BigDecimal.ZERO;
                for(AccountSubjectInfo as:eaccCashAccount){
                    if(Tsubject.CASH.getCode().equals(as.getSubject())){
                        allAmt=allAmt.add(as.getN_balance());
                    }
                }
                String prodAllRefundAmtKey="Prod"+checkProductInfo.getProd_account()+"AllAmt";
                Object amtObj= CacheUtil.getCache().get(prodAllRefundAmtKey);
                logger.info("【标的还款】==========标的账户："+checkProductInfo.getProd_account()+
                        "，已经需累计还款："+amtObj);
                BigDecimal prodFundAllAmt=BigDecimal.ZERO;
                if(amtObj!=null){
                    prodFundAllAmt=(BigDecimal) amtObj;
                }
                for(BatchCustRepayOld custRepay:custRepayList){
                    if(custRepay.getReal_repay_amount()!=null){
                        prodFundAllAmt=prodFundAllAmt.add(custRepay.getReal_repay_amount());
                    }
                    if(custRepay.getReal_repay_val()!=null){
                        prodFundAllAmt=prodFundAllAmt.add(custRepay.getReal_repay_val());
                    }
                    if(custRepay.getRepay_fee()!=null){
                        prodFundAllAmt=prodFundAllAmt.add(custRepay.getRepay_fee());
                    }
                    logger.info("【标的还款】==========剩余需还款："+prodFundAllAmt);
                }
                if(allAmt.compareTo(prodFundAllAmt)<0){
                    throw new BusinessException(BusinessMsg.BALANCE_LACK,BusinessMsg.getMsg(BusinessMsg.BALANCE_LACK)+":标的账户余额不足");
                }
            }else{
                throw new BusinessException(BusinessMsg.INVALID_ACCOUNT,BusinessMsg.getMsg(BusinessMsg.INVALID_ACCOUNT)+":标的账户不存在");
            }
            //添加数据到缓存
            List<String> refundStrList=new ArrayList<>();
            for(BatchCustRepayOld custRepay:custRepayList){
                //==================合成返回数据=================
                ProdBusinessData prodBusinessData=new ProdBusinessData();
                Amtlist amtlist = new Amtlist();
                prodBusinessData.setOrder_no(custRepay.getDetail_no());
                prodBusinessData.setProcess_date(DateUtils.formatDateToStr(new Date()));
                logger.info("【标的还款】========检查订单号重复");
                if(!orderCheck.checkOrder(custRepay.getDetail_no())) {
                    logger.info("【标的还款】========订单号重复，不处理，返回处理中，order_no:" + custRepay.getDetail_no());
                    prodBusinessData.setOrder_status(OrderStatus.PROCESSING.getCode());
                }else{
                    //记录流水
                    TransTransreq custRepayTransTransreq=transReqService.getTransTransReq(prodBatchRepayRequest, TransConsts.PRODREPAY_CODE,TransConsts.PRODREPAY_NAME,true);
                    custRepayTransTransreq.setOrder_no(custRepay.getDetail_no());
                    transReqService.insert(custRepayTransTransreq);
                    prodBusinessData.setQuery_id(custRepayTransTransreq.getTrans_serial());
                    try{
                        //将还款数据插入数据库
                        ProdRefundReqData prodRefundReqData=new ProdRefundReqData();
                        prodRefundReqData.setProd_id(prodBatchRepayRequest.getProd_id());
                        prodRefundReqData.setOrder_no(custRepay.getDetail_no());
                        prodRefundReqData.setPlat_no(prodBatchRepayRequest.getMer_no());
                        prodRefundReqData.setMall_no(prodBatchRepayRequest.getMall_no());
                        prodRefundReqData.setTrans_serial(custRepayTransTransreq.getTrans_serial());
                        prodRefundReqData.setReal_repay_amount(custRepay.getReal_repay_amount());
                        prodRefundReqData.setExperience_amt(custRepay.getExperience_amt());
                        prodRefundReqData.setRates_amt(custRepay.getRates_amt());
                        prodRefundReqData.setRepay_fee(custRepay.getRepay_fee());
                        prodRefundReqData.setReal_repay_val(custRepay.getReal_repay_val());
                        prodRefundReqData.setCust_no(custRepay.getCust_no());
                        prodRefundReqData.setRepay_num(String.valueOf(custRepay.getRepay_num()));
                        prodRefundReqData.setRepay_date(custRepay.getRepay_date());
                        prodRefundReqData.setReal_repay_date(custRepay.getReal_repay_date());
                        prodRefundReqData.setIs_payoff(prodBatchRepayRequest.getIs_payoff());
                        prodRefundReqData.setRepay_flag(prodBatchRepayRequest.getRepay_flag());
                        insertProdRefundReqData(prodRefundReqData);
                    }catch (Exception e){
                        logger.error("【标的还款】数据入库失败|",e);
                        prodBusinessData.setOrder_status(OrderStatus.FAIL.getCode());
                        custRepayTransTransreq.setStatus(FlowStatus.FAIL.getCode());
                        custRepayTransTransreq.setReturn_code(BusinessMsg.RUNTIME_EXCEPTION);
                        custRepayTransTransreq.setReturn_msg(BusinessMsg.getMsg(BusinessMsg.RUNTIME_EXCEPTION));
                        transReqService.insert(custRepayTransTransreq);
                    }
                }
                amtlist.setAmttype(Tsubject.CASH.getCode());
                amtlist.setPlatcust(custRepay.getCust_no());
                amtlist.setAmt(custRepay.getReal_repay_amt());
                prodBusinessData.setAmtlist(amtlist);
                prodBusinessDataList.add(prodBusinessData);
            }

            //更新流水
            transTransReq.setReturn_code(BusinessMsg.SUCCESS);
            transTransReq.setReturn_msg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));
            transTransReq.setStatus(FlowStatus.SUCCESS.getCode());
            transReqService.insert(transTransReq);
        }catch (Exception e){
            BaseResponse baseResponse=new BaseResponse();
            if(e instanceof BusinessException){
                baseResponse=((BusinessException) e).getBaseResponse();
            }else{
                logger.info("【标的还款】=========异常：",e);
                baseResponse.setRecode(BusinessMsg.RUNTIME_EXCEPTION);
                baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.RUNTIME_EXCEPTION));
            }
            //抛出批次订单的异常，包括余额不足之类的
            if(prodBusinessDataList.size()<=0){
                throw new BusinessException(baseResponse);
            }

            //更新流水
            transTransReq.setReturn_code(baseResponse.getRecode());
            transTransReq.setReturn_msg(baseResponse.getRemsg());
            transTransReq.setStatus(FlowStatus.FAIL.getCode());
            transReqService.insert(transTransReq);
        }

        logger.info("【标的还款】========="+prodBusinessDataList);
        return prodBusinessDataList;
    }

    @Override
    public Integer insertProdRefundReqData(ProdRefundReqData prodRefundReqData) throws BusinessException {
        prodRefundReqData.setStatus(ProdRefundStatus.WAITPAY.getCode());
        prodRefundReqData.setEnabled(Constants.ENABLED);
        return prodRefundReqDataMapper.insert(prodRefundReqData);
    }

    @Override
    public List<ProdRefundReqData> queryProdRefundReqData(Integer limit, String... queryStatus) throws BusinessException {
        ProdRefundReqDataExample example=new ProdRefundReqDataExample();
        ProdRefundReqDataExample.Criteria criteria=example.createCriteria();
        criteria.andEnabledEqualTo(Constants.ENABLED);
        if(queryStatus.length==1){
            criteria.andStatusEqualTo(queryStatus[0]);
        }else{
            criteria.andStatusIn(Arrays.asList(queryStatus));
        }
        example.setOrderByClause(" id asc limit "+limit);
        return prodRefundReqDataMapper.selectByExample(example);
    }

    @Override
    public Integer updateProdRefundReqDataStatus(Integer dataId, String nowStatus, String nextStatus) throws BusinessException {
        ProdRefundReqDataExample example=new ProdRefundReqDataExample();
        ProdRefundReqDataExample.Criteria criteria=example.createCriteria();
        criteria.andStatusEqualTo(nowStatus);
        criteria.andIdEqualTo(dataId);
        ProdRefundReqData prodRefundReqData=new ProdRefundReqData();
        prodRefundReqData.setStatus(nextStatus);
        return prodRefundReqDataMapper.updateByExampleSelective(prodRefundReqData,example);
    }

    @Override
    public Integer insertPayTransSerialTransfer(PayTransSerialTransfer payTransSerialTransfer) throws BusinessException {
        payTransSerialTransfer.setStatus(ProdRefundStatus.PAYING.getCode());
        payTransSerialTransfer.setEnabled(Constants.ENABLED);
        return payTransSerialTransferMapper.insert(payTransSerialTransfer);
    }

    @Override
    public List<PayTransSerialTransfer> queryPayTransSerialTransfer(String pay_trans_serial, String trans_serial) throws BusinessException {
        PayTransSerialTransferExample example=new PayTransSerialTransferExample();
        PayTransSerialTransferExample.Criteria criteria=example.createCriteria();
        criteria.andEnabledEqualTo(Constants.ENABLED);
        if(StringUtils.isNotBlank(pay_trans_serial)){
            criteria.andPay_trans_serialEqualTo(pay_trans_serial);
        }
        if(StringUtils.isNotBlank(trans_serial)){
            criteria.andTrans_serialEqualTo(trans_serial);
        }
        return payTransSerialTransferMapper.selectByExample(example);
    }

    @Override
    public Integer insertPayEaccountBind(PayEaccountBind payEaccountBind) throws BusinessException {
        payEaccountBind.setStatus(ProdRefundStatus.PAYING.getCode());
        payEaccountBind.setEnabled(Constants.ENABLED);
        return payEaccountBindMapper.insert(payEaccountBind);
    }

    @Override
    public List<PayEaccountBind> queryPayEaccountBind(String trans_serial, String eaccount, String order_no) throws BusinessException {
        if(StringUtils.isBlank(trans_serial) || StringUtils.isBlank(eaccount)){
            return null;
        }
        PayEaccountBindExample example=new PayEaccountBindExample();
        PayEaccountBindExample.Criteria criteria=example.createCriteria();
        criteria.andEaccountEqualTo(eaccount);
        criteria.andTrans_serialEqualTo(trans_serial);
        if(StringUtils.isNotBlank(order_no)){
            criteria.andOrder_noEqualTo(order_no);
        }
        return payEaccountBindMapper.selectByExample(example);
    }

    @Override
    public void doWaitPayRefund(List<ProdRefundReqData> prodRefundReqDataList) throws BusinessException {
        List<String> dataListBack=new ArrayList<>();
        try{
            PlatCardinfo platCardinfo=null;
            List<BatchPayDetail> batchPayDetailList=new ArrayList<>();
            BigDecimal allAmt=BigDecimal.ZERO;
            String trans_serial= SeqUtil.getSeqNum();

            //合成转账参数
            Map<String,Object> params=new HashMap<>();
            params.put("partner_id","10000001");
            params.put("partner_serial_no",trans_serial);
            params.put("partner_trans_date",DateUtils.getyyyyMMddDate());
            params.put("partner_trans_time",DateUtils.getHHmmssTime());
            params.put("transaction_flag","1");

            Map<String,Object> repayListMap=new HashMap<>();
            for(ProdRefundReqData refundReqData:prodRefundReqDataList){
                    String orderKey=null;
                    BigDecimal trans_amt=BigDecimal.ZERO;
                    Boolean addFlag=false;
                    BatchPayDetail detail=null;
                    try{
                        //更新订单状态
                        Integer updateRows=updateProdRefundReqDataStatus(refundReqData.getId(),ProdRefundStatus.WAITPAY.getCode(),ProdRefundStatus.PAYING.getCode());
                        if(updateRows==0){
                            continue;
                        }
                        //查询存管户
                        logger.info("【标的还款】=========查询存管户"+refundReqData.getPlat_no());
                        if(platCardinfo==null){
                            platCardinfo=accountQueryService.getPlatcardinfo(refundReqData.getPlat_no(),PlatAccType.PLATTRUST.getCode());
                        }
                        //查询流水
                        logger.info("【标的还款】=========查询流水"+refundReqData.getOrder_no());
                        TransTransreq transTransreq=transReqService.queryTransTransReqByOrderno(refundReqData.getOrder_no());
                        //查询用户信息
                        logger.info("【标的还款】=========查询用户信息"+refundReqData.getCust_no());
                        EaccUserinfo eaccUserinfo=accountQueryService.getEaccUserinfo(refundReqData.getMall_no(),refundReqData.getCust_no());

                        detail= (BatchPayDetail) repayListMap.get(eaccUserinfo.getEaccount());
                        trans_amt=getRealTransAmt(refundReqData);
                        if(trans_amt==null){
                            transTransreq.setStatus(FlowStatus.FAIL.getCode());
                            transTransreq.setReturn_code(BusinessMsg.TRANS_FAIL);
                            transTransreq.setReturn_msg(BusinessMsg.getMsg(BusinessMsg.TRANS_FAIL)+"：数据异常，处理失败！");
                            transReqService.insert(transTransreq);
                            continue;
                        }
                        if(detail!=null){
                            detail.setOccur_balance(detail.getOccur_balance().add(trans_amt));
                        }else{
                            detail=new BatchPayDetail();
                            detail.setCard_no(eaccUserinfo.getEaccount());
                            detail.setOccur_balance(trans_amt);
                            detail.setSummary("标的还款");
                        }
                        allAmt=allAmt.add(trans_amt);
                        addFlag=true;

                        repayListMap.put(detail.getCard_no(),detail);
                        params.put("details",JSON.toJSONString(detail));

                        //记录一借多贷流水号和存管流水号的映射关系
                        PayTransSerialTransfer payTransSerialTransfer=new PayTransSerialTransfer();
                        payTransSerialTransfer.setPay_trans_serial(trans_serial);
                        payTransSerialTransfer.setTrans_serial(refundReqData.getTrans_serial());
                        insertPayTransSerialTransfer(payTransSerialTransfer);

                        //记录电子账户和订单的映射关系
                        PayEaccountBind payEaccountBind=new PayEaccountBind();
                        payEaccountBind.setTrans_serial(trans_serial);
                        payEaccountBind.setOrder_no(refundReqData.getOrder_no());
                        payEaccountBind.setEaccount(eaccUserinfo.getEaccount());
                        insertPayEaccountBind(payEaccountBind);

                        //记录电子账户转账流水
                        logger.info("【标的还款】=========查询电子账户转账流水"+transTransreq.getTrans_serial());
                        EaccTransTransreqWithBLOBs eaccTransTransreq=new EaccTransTransreqWithBLOBs();
                        eaccTransTransreq.setParent_trans_serial(transTransreq.getTrans_serial());
                        eaccTransTransreq.setStatus(Integer.valueOf(FlowStatus.INPROCESS.getCode()));
                        eaccTransTransreq.setTrans_amt(trans_amt);
                        eaccTransTransreq.setEaccount(platCardinfo.getCard_no());
                        eaccTransTransreq.setOppo_eaccount(eaccUserinfo.getEaccount());
                        eaccTransTransreq.setName(platCardinfo.getCard_name());
                        eaccTransTransreq.setOppo_name(eaccUserinfo.getName());
                        eaccTransTransreq.setProperty(Integer.valueOf(CusType.COMPANY.getCode()));
                        eaccTransTransreq.setOppo_property(Integer.valueOf(CusType.PERSONAL.getCode()));
                        eaccTransTransreq.setTrans_serial(trans_serial);
                        eaccTransTransreq.setReq_params(JSON.toJSONString(params));
                        logger.info("【标的还款】========添加电子账户转账流水（处理中）"+refundReqData.getOrder_no());
                        eaccTransTransreqService.addFlow(eaccTransTransreq);
//                        //将订单消息添加到redis
//                        orderKey=trans_serial+eaccUserinfo.getEaccount();
//                        CacheUtil.getCache().leftPush(orderKey,refundListItemStr);
//                        CacheUtil.getCache().expire(orderKey,24*3600);
//                        dataListBack.add(refundListItemStr);
                    }catch (Exception e){
                        logger.error("【标的还款】========还款订单加入一借多贷失败：",e);
                        if(orderKey!=null){
                            CacheUtil.getCache().del(orderKey);
                        }
                        if(addFlag){
                            //扣除转账金额
                            detail.setOccur_balance(detail.getOccur_balance().subtract(trans_amt));
                            allAmt=allAmt.subtract(trans_amt);
                        }
                        //将失败的数据丢回处理队列
//                        CacheUtil.getCache().leftPush(key,refundListItemStr);
                    }
            }
            for(String mapKey:repayListMap.keySet()){
                BatchPayDetail detail= (BatchPayDetail) repayListMap.get(mapKey);
                batchPayDetailList.add(detail);
            }
            params.put("total_num",batchPayDetailList.size());
            params.put("total_balance",allAmt.toString());
            params.put("third_no",platCardinfo.getCard_no());
            params.put("details",JSON.toJSONString(batchPayDetailList));
            logger.info("【标的还款】========请求支付");
            try{
                BatchPayResponse batchPayResponse=accountTransferThirdParty.batchPay(params,platCardinfo.getMall_no());
                //data返回失败，或者未返回data数据，或者返回错误信息不为通讯超时，当失败处理
                if((batchPayResponse.getData()==null && !"027019".equals(batchPayResponse.getError_code())) ||
                        (batchPayResponse.getData()!=null && "7".equals(batchPayResponse.getData().get(0).getPay_status()))){
                    logger.info("【标的还款】========请求支付失败"+batchPayResponse.getError_info());
                    throw new BusinessException(BusinessMsg.TRANS_FAIL,"请求支付失败"+batchPayResponse.getError_info());
                }
            }catch (Exception e){
                BaseResponse baseResponse=new BaseResponse();
                if(e instanceof BusinessException && "请求超时".equals(baseResponse.getRemsg())){
                    logger.info("【标的还款】========一借多贷请求超时");
                }else{
                    throw e;
                }
            }
            try{
                //在redis中设置转账状态查询缓存
                Map<String,Object> queryMap=new HashMap<>();
                queryMap.put("trans_serial",trans_serial);
                queryMap.put("mall_no",platCardinfo.getMall_no());
                queryMap.put("plat_no",platCardinfo.getPlat_no());
                queryMap.put("startTime",(new Date()).getTime());
                queryMap.put("lastTime",(new Date()).getTime());
                CacheUtil.getCache().addToSet(ProductPublicParams.fundPayWaitQueryListKey,ProductPublicParams.waitQueryOrder+trans_serial);
                CacheUtil.getCache().set(ProductPublicParams.waitQueryOrder+trans_serial,JSON.toJSONString(queryMap,GlobalConfig.serializerFeature));
            }catch (Exception e){
                logger.info("【标的还款】========redis缓存设置失败！");
            }
            //================================================================
            logger.info("【标的还款】========启动查询支付状态JOB");
//            refundJobAction.startQueryBatchPayNew(trans_serial,platCardinfo.getMall_no(),platCardinfo.getPlat_no());
        }catch (Exception e){
            logger.error("【还款批量代付】============处理失败，回滚数据："+e.getMessage());
            //回滚数据
//            for(String data:dataListBack){
//                CacheUtil.getCache().leftPush(key,data);
//            }
        }
    }

    private boolean checkCustRepayForBatchRefund(ProdBatchRepayOldRequest prodBatchRepayRequest) {
        //实际还款金额
        BigDecimal real_repay_amt=BigDecimal.ZERO;
        for (BatchCustRepayOld custRepay : prodBatchRepayRequest.getCustRepayList()) {
            BigDecimal num = BigDecimal.ZERO;
            if (custRepay.getReal_repay_amount()!=null){
                num = num.add(custRepay.getReal_repay_amount());
            }

            if (custRepay.getRates_amt()!=null){
                num = num.add(custRepay.getRates_amt());
            }

            if (custRepay.getReal_repay_val()!=null){
                num = num.add(custRepay.getReal_repay_val());
            }

            if (custRepay.getExperience_amt()!=null){
                num = num.add(custRepay.getExperience_amt());
            }
            if (0!=custRepay.getReal_repay_amt().compareTo(num)){
                return false;
            }
            real_repay_amt = real_repay_amt.add(custRepay.getReal_repay_amt());
        }

        //校验金额
        if(0!=real_repay_amt.compareTo(prodBatchRepayRequest.getTrans_amt())){
            return false;
        }

        return true;
    }

    private BigDecimal getRealTransAmt(ProdRefundReqData prodRefundReqData)throws BusinessException{
        BigDecimal realTransAmt=BigDecimal.ZERO;
        if(prodRefundReqData.getReal_repay_amount()!=null){
            realTransAmt=realTransAmt.add(prodRefundReqData.getReal_repay_amount());
        }
        if(prodRefundReqData.getReal_repay_val()!=null){
            realTransAmt=realTransAmt.add(prodRefundReqData.getReal_repay_val());
        }
        if(prodRefundReqData.getRepay_fee()!=null){
            realTransAmt=realTransAmt.add(prodRefundReqData.getRepay_fee());
        }
        return realTransAmt;
    }
}
