package com.sunyard.sunfintech.prod.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.sunyard.sunfintech.core.Constants;
import com.sunyard.sunfintech.core.TransConsts;
import com.sunyard.sunfintech.core.annotation.SerialNo;
import com.sunyard.sunfintech.core.annotation.SerialNoDetail;
import com.sunyard.sunfintech.core.base.*;
import com.sunyard.sunfintech.core.base.BaseErrorData;
import com.sunyard.sunfintech.core.base.BaseRequest;
import com.sunyard.sunfintech.core.base.BaseResponse;
import com.sunyard.sunfintech.core.base.BaseServiceSimple;
import com.sunyard.sunfintech.core.dic.*;
import com.sunyard.sunfintech.core.exception.BusinessException;
import com.sunyard.sunfintech.core.exception.BusinessMsg;
import com.sunyard.sunfintech.core.util.*;
import com.sunyard.sunfintech.custdao.mapper.CustProdProdInfoMapper;
import com.sunyard.sunfintech.custdao.mapper.CustProdShareInallMapper;
import com.sunyard.sunfintech.dao.entity.*;
import com.sunyard.sunfintech.dao.mapper.EaccUserinfoMapper;
import com.sunyard.sunfintech.dao.mapper.ProdShareInallMapper;
import com.sunyard.sunfintech.dao.mapper.ProdShareListMapper;
import com.sunyard.sunfintech.dao.mapper.ProdTransferRecordMapper;
import com.sunyard.sunfintech.prod.model.bo.*;
import com.sunyard.sunfintech.prod.provider.*;
import com.sunyard.sunfintech.pub.model.MsgModel;
import com.sunyard.sunfintech.pub.model.NotifyData;
import com.sunyard.sunfintech.pub.provider.*;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

/**
 * Created by terry on 2017/4/28.
 */

@Service(interfaceClass = IProductInvestmentService.class)
@CacheConfig(cacheNames = "productInvestmentService")
@org.springframework.stereotype.Service("productInvestmentService")
public class ProductInvestmentService extends BaseServiceSimple implements IProductInvestmentService {

    @Autowired
    private ProdShareInallMapper prodShareInallMapper;

    @Autowired
    private ProdTransferRecordMapper transferRecordMapper;

    @Autowired
    private ProdShareListMapper prodShareListMapper;

    @Autowired
    private IProdSearchService prodSearchService;

    @Autowired
    private ITransReqService transReqService;

    @Autowired
    private IProdShareOptionService prodShareOptionService;

    @Autowired
    private ISendMsgService sendMsgService;

    @Autowired
    private EaccUserinfoMapper eaccUserinfoMapper;//todo

    @Autowired
    private ISysParameterService sysParameterService;

    @Autowired
    private IProdInvestmentExtService prodInvestmentExtService;

    @Autowired
    private IProdMQOptionService prodMQOptionService;

    @Autowired
    private CustProdShareInallMapper custProdShareInallMapper;
    @Autowired
    private IAuthCheckService authCheckService;
    @Autowired
    private INotifyService notifyService;
    @Autowired
    private CustProdProdInfoMapper custProdProdInfoMapper;

    @Transactional
    public boolean prodTrans(ProdShareInall prodShareInallBuyer, ProdShareInall prodShareInallSaler, BigDecimal changeVol) throws BusinessException {
        //将转让人的份额减少
        try {
            Map<String,Object> params=new HashMap<>();
            params.put("prod_id",prodShareInallSaler.getProd_id());
            params.put("platcust",prodShareInallSaler.getPlatcust());
            params.put("plat_no",prodShareInallSaler.getPlat_no());
            params.put("vol",changeVol);
            params.put("update_by",SeqUtil.RANDOM_KEY);
            params.put("update_time",new Date());
            //更新转让人份额
            int successCount=custProdShareInallMapper.subtractInallVol(params);
            if(successCount<=0){
                BaseResponse baseResponse = new BaseResponse();
                baseResponse.setRecode(BusinessMsg.NVALID_TRANSFER_NO);
                baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.NVALID_TRANSFER_NO) + "：转让人份额不足。");
                throw new BusinessException(baseResponse);
            }
            ProdShareInall oldProdShareInallBuyer = prodShareOptionService.queryProdShareInall(prodShareInallBuyer);
            if (oldProdShareInallBuyer != null) {
                params.put("platcust",prodShareInallBuyer.getPlatcust());
                custProdShareInallMapper.addInallVol(params);
            } else {
                //创建产品份额
                prodShareInallBuyer.setTot_vol(changeVol);
                prodShareInallBuyer.setVol(changeVol);
                prodShareInallBuyer.setFrozen_vol(BigDecimal.ZERO);
                prodShareInallBuyer.setCreate_by(SeqUtil.RANDOM_KEY);
                prodShareInallBuyer.setEnabled(Constants.ENABLED);
                prodShareInallBuyer.setCreate_time(DateUtils.today());
                prodShareInallMapper.insert(prodShareInallBuyer);
            }
        } catch (Exception e) {
            BaseResponse baseResponse = new BaseResponse();
            if (e instanceof BusinessException) {
                baseResponse = ((BusinessException) e).getBaseResponse();
            } else {
                logger.error(e);
                baseResponse.setRecode(BusinessMsg.DATEBASE_EXCEPTION);
                baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.DATEBASE_EXCEPTION));
            }
            throw new BusinessException(baseResponse);
        }
        return true;
    }

    @Override
    public boolean backProdTrans(ProdShareInall prodShareInallBuyer, ProdShareInall prodShareInallSaler, BigDecimal changeVol) throws BusinessException {
        //将转让人的份额增加
        try {
            Map<String,Object> params=new HashMap<>();
            params.put("prod_id",prodShareInallSaler.getProd_id());
            params.put("platcust",prodShareInallSaler.getPlatcust());
            params.put("plat_no",prodShareInallSaler.getPlat_no());
            params.put("vol",changeVol);
            params.put("update_by",SeqUtil.RANDOM_KEY);
            params.put("update_time",new Date());
            //更新转让人份额
            int successCount=custProdShareInallMapper.addInallVol(params);
            if(successCount<=0){
                BaseResponse baseResponse = new BaseResponse();
                baseResponse.setRecode(BusinessMsg.INVALID_ACCOUNT);
                baseResponse.setRecode(BusinessMsg.getMsg(BusinessMsg.INVALID_ACCOUNT) + "：转让人份额不足。");
                throw new BusinessException(baseResponse);
            }
            ProdShareInall oldProdShareInallBuyer = prodShareOptionService.queryProdShareInall(prodShareInallBuyer);
            if (oldProdShareInallBuyer != null) {
                //将购买人的份额减少
                params.put("platcust",prodShareInallBuyer.getPlatcust());
                custProdShareInallMapper.subtractInallVol(params);
            } else {
//                //创建产品份额
//                prodShareInallBuyer.setTot_vol(BigDecimal.ZERO);
//                prodShareInallBuyer.setVol(BigDecimal.ZERO);
//                prodShareInallBuyer.setFrozen_vol(BigDecimal.ZERO);
//                prodShareInallBuyer.setCreate_by("ADMIN");
//                prodShareInallBuyer.setEnabled(Constants.ENABLED);
//                prodShareInallBuyer.setCreate_time(DateUtils.today());
//                prodShareInallMapper.insert(prodShareInallBuyer);
                logger.info("【标的转让】========回滚找不到份额 platcust:"+prodShareInallBuyer.getPlatcust()+",vol:"+changeVol);
                //给管理员发送通知
                String content="（标的转让）债转回滚转让份额时找不到受让人份额，请处理！ platcust:"+
                        prodShareInallBuyer.getPlatcust()+",prod_id:"+prodShareInallBuyer.getVol()+",vol:"+changeVol;
//                sendMsgService.sendErrorToAdmin(content,prodShareInallSaler.getPlat_no());

            }
        } catch (Exception e) {
            BaseResponse baseResponse = new BaseResponse();
            if (e instanceof BusinessException) {
                baseResponse = ((BusinessException) e).getBaseResponse();
            } else {
                logger.error(e);
                baseResponse.setRecode(BusinessMsg.DATEBASE_EXCEPTION);
                baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.DATEBASE_EXCEPTION));
            }
            throw new BusinessException(baseResponse);
        }
        return true;
    }

    @Override
    public boolean assignment(ProdTransferDebtRequestBo prodTransferDebtRequestBo) throws BusinessException {
        //记录流水
        TransTransreq transTransReq = transReqService.getTransTransReq(prodTransferDebtRequestBo.clone(), TransConsts.TRANSFERDEBT_CODE, TransConsts.TRANSFERDEBT_NAME, false);
        transReqService.insert(transTransReq);

        try {
            prodTransferDebtRequestBo.setDetail_no(prodTransferDebtRequestBo.getOrder_no());
            prodInvestmentExtService.transProd(prodTransferDebtRequestBo,transTransReq);
        } catch (Exception e) {
            BaseResponse baseResponse = new BaseResponse();
            if (e instanceof BusinessException) {
                baseResponse = ((BusinessException) e).getBaseResponse();
            } else {
                logger.error("标的转让=============" , e);
                baseResponse.setRecode(BusinessMsg.DATEBASE_EXCEPTION);
                baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.DATEBASE_EXCEPTION));
            }
            //更新流水
            transTransReq.setReturn_code(baseResponse.getRecode());
            transTransReq.setReturn_msg(baseResponse.getRemsg());
            transTransReq.setStatus(FlowStatus.FAIL.getCode());
            transReqService.insert(transTransReq);
            throw new BusinessException(baseResponse);
        }

        BigDecimal rechargeAmt=BigDecimal.ZERO;
        rechargeAmt=rechargeAmt.add(prodTransferDebtRequestBo.getDeal_amount()).add(prodTransferDebtRequestBo.getTransfer_income());
        if(prodTransferDebtRequestBo.getCommissionObject()!=null && prodTransferDebtRequestBo.getCommissionObject().getPayout_amt()!=null){
            rechargeAmt=rechargeAmt.add(prodTransferDebtRequestBo.getCommissionObject().getPayout_amt());
        }

        BigDecimal transAmt=BigDecimal.ZERO;
        transAmt=transAmt.add(prodTransferDebtRequestBo.getDeal_amount());
        if(prodTransferDebtRequestBo.getCommission_extObject()!=null && prodTransferDebtRequestBo.getCommission_extObject().getPayout_amt()!=null){
            transAmt=transAmt.add(prodTransferDebtRequestBo.getCommission_extObject().getPayout_amt());
        }


        try{
            logger.info("【标的转让】========发送通知短信给转让人");
            EaccUserinfo eaccUserinfo=getEaccUserInfo(prodTransferDebtRequestBo.getMall_no(),prodTransferDebtRequestBo.getPlatcust());
            if(eaccUserinfo!=null){
                MsgModel msgModel=new MsgModel();
                String mall_name=sysParameterService.querySysParameter(prodTransferDebtRequestBo.getMall_no(), SysParamterKey.MALL_NAME);
                BigDecimal allAmt=sendMsgService.getAccountAllAmount(prodTransferDebtRequestBo.getPlatcust());
                msgModel.setOrder_no(prodTransferDebtRequestBo.getOrder_no());
                msgModel.setPlat_no(transTransReq.getPlat_no());
                msgModel.setTrans_code(transTransReq.getTrans_code());

                msgModel.setMall_no(prodTransferDebtRequestBo.getMall_no());
                msgModel.setPlatcust(prodTransferDebtRequestBo.getPlatcust());
                String content=sysParameterService.querySysParameter(prodTransferDebtRequestBo.getMall_no(),MsgContent.PROD_TRANS_REFUND_NOTIFY.getMsg());
                if(StringUtils.isNotBlank(content)){
                    msgModel.setMsgContent(String.format(content,
                            mall_name, NumberUtils.formatNumber(rechargeAmt),NumberUtils.formatNumber(allAmt)));
                    //=========ccb参数===========
                    msgModel.setTrans_serial(transTransReq.getTrans_serial());
                    msgModel.setMsg_type(MsgType.INVEST_SUCCESS_NOTIFY.getType());
                    msgModel.setAmount(rechargeAmt);
                    //===========================
                    sendMsgService.addMsgToQueue(msgModel);
                }

          //  ServiceAsyncHelper.publishTopic(amqpTemplate,msgModel);
           }
            logger.info("【标的转让】========发送通知短信给受让人");
           eaccUserinfo=getEaccUserInfo(prodTransferDebtRequestBo.getMall_no(),prodTransferDebtRequestBo.getDeal_platcust());
           if(eaccUserinfo!=null){
               MsgModel  msgModel=new MsgModel();
               String  mall_name=sysParameterService.querySysParameter(prodTransferDebtRequestBo.getMall_no(), SysParamterKey.MALL_NAME);
               BigDecimal  allAmt=sendMsgService.getAccountAllAmount(prodTransferDebtRequestBo.getDeal_platcust());
               msgModel.setOrder_no(prodTransferDebtRequestBo.getOrder_no());
               msgModel.setPlat_no(transTransReq.getPlat_no());
               msgModel.setTrans_code(transTransReq.getTrans_code());
               msgModel.setMobile(eaccUserinfo.getMobile());
               String content=sysParameterService.querySysParameter(prodTransferDebtRequestBo.getMall_no(),MsgContent.INVEST_SUCCESS_NOTIFY.getMsg());
               if(StringUtils.isNotBlank(content)){
                   msgModel.setMsgContent(String.format(content,
                           mall_name,NumberUtils.formatNumber(transAmt),NumberUtils.formatNumber(allAmt)));
                   //=========ccb参数===========
                   msgModel.setTrans_serial(transTransReq.getTrans_serial());
                   msgModel.setMsg_type(MsgType.INVEST_SUCCESS_NOTIFY.getType());
                   msgModel.setAmount(transAmt);
                   //===========================
                   sendMsgService.addMsgToQueue(msgModel);
               }
         // ServiceAsyncHelper.publishTopic(amqpTemplate,msgModel);
           }
        }catch (Exception e){
            logger.info("【短信发送异常】========"+e);
        }

        //更新业务流水trans_transreq
        transTransReq.setReturn_code(BusinessMsg.SUCCESS);
        transTransReq.setReturn_msg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));
        transTransReq.setStatus(FlowStatus.SUCCESS.getCode());
        transReqService.insert(transTransReq);

        return true;
    }

    @Override
    public ProdBatchTransferReturnData batchAssignment(ProdBatchTransferRequestBo prodBatchTransferRequestBo) throws BusinessException {

        ProdBatchTransferReturnData returnData = new ProdBatchTransferReturnData();
        List<ProdBatchTransferSuccessData> successDataList = new ArrayList<ProdBatchTransferSuccessData>();
        List<BaseErrorData> errorDataList = new ArrayList<BaseErrorData>();
        ProdTransferDebtRequestBo prodTransferDebtRequestBo = new ProdTransferDebtRequestBo();

        BeanUtils.copyProperties(prodBatchTransferRequestBo, prodTransferDebtRequestBo);
        List<ProdBatchTransferRequestDataBoTb> dataArray = prodBatchTransferRequestBo.getDataArray();

        TransTransreq transTransReq = transReqService.getTransTransReq(prodBatchTransferRequestBo.clone(), TransConsts.TRANSFERDEBT_CODE, TransConsts.TRANSFERDEBT_NAME, true);
        transReqService.insert(transTransReq);
        logger.info("【标的批量转让】========开始批量转让");
        BigDecimal rechargeAmt=BigDecimal.ZERO;
        for (ProdBatchTransferRequestDataBoTb dataBo : dataArray) {
            BeanUtils.copyProperties(dataBo, prodTransferDebtRequestBo);
            prodTransferDebtRequestBo.setCommissionObject(dataBo.getCommissionObj());
            prodTransferDebtRequestBo.setCommission_extObject(dataBo.getCommission_extObj());
            prodTransferDebtRequestBo.setPublishDate(dataBo.getPublish_date());
            prodTransferDebtRequestBo.setTransDate(dataBo.getTrans_date());
            prodTransferDebtRequestBo.setDetail_no(dataBo.getDetail_no());
            logger.debug(prodTransferDebtRequestBo.toString());
            validate(prodTransferDebtRequestBo);
            rechargeAmt=rechargeAmt.add(dataBo.getDeal_amount()).add(dataBo.getTransfer_income());
            if(dataBo.getCommissionObj()!=null && dataBo.getCommissionObj().getPayout_amt()!=null){
                rechargeAmt=rechargeAmt.subtract(dataBo.getCommissionObj().getPayout_amt());
            }
            BigDecimal transAmt=BigDecimal.ZERO;
            transAmt=transAmt.add(prodTransferDebtRequestBo.getDeal_amount());
            if(prodTransferDebtRequestBo.getCommission_extObject()!=null && prodTransferDebtRequestBo.getCommission_extObject().getPayout_amt()!=null){
                transAmt=transAmt.add(prodTransferDebtRequestBo.getCommission_extObject().getPayout_amt());
            }
            TransTransreq singleTransTransReq = transReqService.getTransTransReq(prodTransferDebtRequestBo.clone(), TransConsts.TRANSFERDEBT_CODE, TransConsts.TRANSFERDEBT_NAME, true);
            singleTransTransReq.setOrder_no(prodTransferDebtRequestBo.getDetail_no());
            transReqService.insert(singleTransTransReq);
            try {
                prodInvestmentExtService.transProd(prodTransferDebtRequestBo,singleTransTransReq);
                ProdBatchTransferSuccessData successData = new ProdBatchTransferSuccessData();
                Amtlist amtlist = new Amtlist();
                amtlist.setPlatcust(dataBo.getDeal_platcust());
                if("0".equals(dataBo.getSubject_priority())){
                    amtlist.setAmttype(Tsubject.CASH.getCode());
                }else if("1".equals(dataBo.getSubject_priority())){
                    amtlist.setAmttype(Tsubject.FLOAT.getCode());
                }
                amtlist.setAmt(transAmt);
                successData.setAmtlist(amtlist);
                successData.setDetail_no(dataBo.getDetail_no());
                successData.setPlatcust(dataBo.getDeal_platcust());
                successDataList.add(successData);
            } catch (BusinessException e) {
                BaseResponse baseResponse = e.getBaseResponse();
                BaseErrorData errorData = new BaseErrorData();
                errorData.setDetail_no(dataBo.getDetail_no());
                errorData.setError_no(baseResponse.getRecode());
                errorData.setError_info(baseResponse.getRemsg());
                errorDataList.add(errorData);
            }
        }

        try{
            logger.info("【标的批量转让】========发送通知短信给转让人");
            EaccUserinfo eaccUserinfo=getEaccUserInfo(prodBatchTransferRequestBo.getMall_no(),prodBatchTransferRequestBo.getPlatcust());
            if(eaccUserinfo!=null)
            {
                //TODO 杨磊
                MsgModel msgModel=new MsgModel();
                String mall_name=sysParameterService.querySysParameter(prodBatchTransferRequestBo.getMall_no(), SysParamterKey.MALL_NAME);
                BigDecimal allAmt=sendMsgService.getAccountAllAmount(prodBatchTransferRequestBo.getPlatcust());
                msgModel.setOrder_no(prodBatchTransferRequestBo.getOrder_no());
                msgModel.setPlat_no(transTransReq.getPlat_no());
                msgModel.setTrans_code(transTransReq.getTrans_code());
                msgModel.setMall_no(eaccUserinfo.getMall_no());
                msgModel.setPlatcust(eaccUserinfo.getMallcust());
                String content=sysParameterService.querySysParameter(prodBatchTransferRequestBo.getMall_no(),MsgContent.PROD_TRANS_REFUND_NOTIFY.getMsg());
                if(StringUtils.isNotBlank(content)){
                    msgModel.setMsgContent(String.format(content,
                            mall_name,NumberUtils.formatNumber(rechargeAmt),NumberUtils.formatNumber(allAmt)));
                    //=========ccb参数===========
                    msgModel.setTrans_serial(transTransReq.getTrans_serial());
                    msgModel.setMsg_type(MsgType.PROD_TRANS_REFUND_NOTIFY.getType());
                    msgModel.setAmount(rechargeAmt);
                    //===========================
                    sendMsgService.addMsgToQueue(msgModel);
                }
            }
            logger.info("【标的批量转让】========发送通知短信给受让人");
            for(ProdBatchTransferSuccessData successData:successDataList){
                eaccUserinfo=getEaccUserInfo(prodBatchTransferRequestBo.getMall_no(),successData.getPlatcust());
                if(eaccUserinfo!=null){
                    MsgModel msgModel=new MsgModel();
                    String mall_name=sysParameterService.querySysParameter(prodBatchTransferRequestBo.getMall_no(), SysParamterKey.MALL_NAME);
                    BigDecimal allAmt=sendMsgService.getAccountAllAmount(successData.getPlatcust());
                    msgModel.setOrder_no(successData.getDetail_no());
                    msgModel.setPlat_no(transTransReq.getPlat_no());
                    msgModel.setTrans_code(transTransReq.getTrans_code());
                    msgModel.setMobile(eaccUserinfo.getMobile());
                    String content=sysParameterService.querySysParameter(prodTransferDebtRequestBo.getMall_no(),MsgContent.INVEST_SUCCESS_NOTIFY.getMsg());
                    if(StringUtils.isNotBlank(content)){
                        msgModel.setMsgContent(String.format(content,
                                mall_name,NumberUtils.formatNumber(successData.getAmtlist().getAmt()),NumberUtils.formatNumber(allAmt)));
                        //=========ccb参数===========
                        msgModel.setTrans_serial(transTransReq.getTrans_serial());
                        msgModel.setMsg_type(MsgType.INVEST_SUCCESS_NOTIFY.getType());
                        msgModel.setAmount(successData.getAmtlist().getAmt());
                        //===========================
                        sendMsgService.addMsgToQueue(msgModel);
                    }
                }
            }
        }catch (Exception e){
            logger.info("【短信发送异常】========"+e);
        }
        transTransReq.setReturn_code(BusinessMsg.SUCCESS);
        transTransReq.setReturn_msg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));
        transTransReq.setStatus(FlowStatus.SUCCESS.getCode());
        transReqService.insert(transTransReq);
        returnData.setErrorData(errorDataList);
        returnData.setSuccessData(successDataList);
        return returnData;
    }

    @Override
    public boolean batchAssignmentNoSync(ProdBatchTransferAsynRequestBo prodBatchTransferRequestBo) throws BusinessException {
        TransTransreq transTransReq = transReqService.getTransTransReq(prodBatchTransferRequestBo.clone(), TransConsts.TRANSFERDEBT_CODE, TransConsts.TRANSFERDEBT_NAME, true);
        transReqService.insert(transTransReq);
        logger.info("【标的批量转让】========开始批量转让");
        ProdBatchTransferReturnData returnData = new ProdBatchTransferReturnData();
        List<ProdBatchTransferSuccessData> successDataList = new ArrayList<ProdBatchTransferSuccessData>();
        List<BaseErrorData> errorDataList = new ArrayList<BaseErrorData>();
        try{
            List<ProdBatchTransferRequestDataBo> dataArray = prodBatchTransferRequestBo.getDataArray();

            BigDecimal rechargeAmt=BigDecimal.ZERO;
            //记录转让人账号，转让笔数
            String key="Assignment"+prodBatchTransferRequestBo.getOrder_no();
            //设置转让人成功转让金额
            CacheUtil.getCache().set(key+"Amt",BigDecimal.ZERO);
            //设置受让笔数
            CacheUtil.getCache().set(key+"Count",dataArray.size());
            Map<String,List<ProdTransferDebtRequestBo>> transMap=new HashMap<>();
            for (ProdBatchTransferRequestDataBo dataBo : dataArray) {
                ProdTransferDebtRequestBo prodTransferDebtRequestBo = new ProdTransferDebtRequestBo();
                BeanUtils.copyProperties(prodBatchTransferRequestBo, prodTransferDebtRequestBo);
                TransTransreq singleTransTransReq = transReqService.getTransTransReq(prodTransferDebtRequestBo.clone(), TransConsts.TRANSFERDEBT_CODE, TransConsts.TRANSFERDEBT_NAME, true);
                singleTransTransReq.setOrder_no(dataBo.getDetail_no());
                transReqService.insert(singleTransTransReq);
                try {
                    BeanUtils.copyProperties(dataBo, prodTransferDebtRequestBo);
                    prodTransferDebtRequestBo.setCommissionObject(dataBo.getCommissionObj());
                    prodTransferDebtRequestBo.setCommission_extObject(dataBo.getCommission_extObj());
                    prodTransferDebtRequestBo.setPublishDate(dataBo.getPublish_date());
                    prodTransferDebtRequestBo.setTransDate(dataBo.getTrans_date());
                    prodTransferDebtRequestBo.setDetail_no(dataBo.getDetail_no());
                    prodTransferDebtRequestBo.setInterface_version(dataBo.getInterface_version());
                    logger.info(prodTransferDebtRequestBo.toString());
                    validate(prodTransferDebtRequestBo);
                    rechargeAmt=rechargeAmt.add(dataBo.getDeal_amount()).add(dataBo.getTransfer_income());
                    if(dataBo.getCommissionObj()!=null && dataBo.getCommissionObj().getPayout_amt()!=null){
                        rechargeAmt=rechargeAmt.subtract(dataBo.getCommissionObj().getPayout_amt());
                    }
//                    BigDecimal transAmt=BigDecimal.ZERO;
//                    transAmt=transAmt.add(prodTransferDebtRequestBo.getDeal_amount());
//                    if(prodTransferDebtRequestBo.getCommission_extObject()!=null && prodTransferDebtRequestBo.getCommission_extObject().getPayout_amt()!=null){
//                        transAmt=transAmt.add(prodTransferDebtRequestBo.getCommission_extObject().getPayout_amt());
//                    }
                    List<ProdTransferDebtRequestBo> readyTransList=transMap.get(prodTransferDebtRequestBo.getProd_id());
                    if(readyTransList==null){
                        readyTransList=new ArrayList<>();
                    }
                    readyTransList.add(prodTransferDebtRequestBo);
                    transMap.put(prodTransferDebtRequestBo.getProd_id(),readyTransList);
                } catch (Exception e) {
                    BaseResponse baseResponse = new BaseResponse();
                    if(e instanceof BusinessException){
                        baseResponse=((BusinessException) e).getBaseResponse();
                        singleTransTransReq.setReturn_code(baseResponse.getRecode());
                        singleTransTransReq.setReturn_msg(baseResponse.getRemsg());
                    }else{
                        baseResponse.setRecode(BusinessMsg.RUNTIME_EXCEPTION);
                        baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.RUNTIME_EXCEPTION));
                        singleTransTransReq.setReturn_code(BusinessMsg.RUNTIME_EXCEPTION);
                        singleTransTransReq.setReturn_msg(BusinessMsg.getMsg(BusinessMsg.RUNTIME_EXCEPTION));
                    }
                    singleTransTransReq.setStatus(OrderStatus.FAIL.getCode());
                    transReqService.insert(singleTransTransReq);
                }
            }
            //添加数据到MQ
            logger.info("【标的批量转让】map数据："+JSON.toJSONString(transMap));
            for(String prod_id:transMap.keySet()){
                logger.info("【标的批量转让】添加到MQ的数据："+JSON.toJSONString(transMap.get(prod_id)));
                prodMQOptionService.addTransProd(transMap.get(prod_id),prodBatchTransferRequestBo.getNotify_url());
            }

            transTransReq.setReturn_code(BusinessMsg.SUCCESS);
            transTransReq.setReturn_msg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));
            transTransReq.setStatus(FlowStatus.SUCCESS.getCode());
            transReqService.insert(transTransReq);
        }catch (Exception e){
            logger.error("【标的批量转让】========异常:",e);
            BaseResponse baseResponse=new BaseResponse();
            if(e instanceof BusinessException){
                baseResponse=((BusinessException) e).getBaseResponse();
            }else{
                baseResponse.setRecode(BusinessMsg.RUNTIME_EXCEPTION);
                baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.RUNTIME_EXCEPTION));
            }
            transTransReq.setReturn_code(baseResponse.getRecode());
            transTransReq.setReturn_msg(baseResponse.getRemsg());
            transTransReq.setStatus(FlowStatus.FAIL.getCode());
            transReqService.insert(transTransReq);
            throw new BusinessException(baseResponse);
        }

        return true;
    }
    @Resource(name = "amqpTemplate")
    private AmqpTemplate amqpTemplate;

    /**
     * 同步投标
     * @param baseRequest
     * @param prodProductInfo
     * @param prodInvestData
     * @return
     * @throws BusinessException
     */
    @SerialNo( transCode = TransConsts.BUY_CODE,description = TransConsts.BUY_NAME)
    @Override
    public ProdInvestReturnData invest(BaseRequest baseRequest, ProdProductinfo prodProductInfo,ProdInvestDataTb prodInvestData ) throws BusinessException {
        logger.info("【单个投标】========开始投标");
        validProdInfo(prodProductInfo);
        if (StringUtils.isNotBlank(baseRequest.getLink_trans_serial())) {
            //单个投标，明细的流水跟baserequest的流水一样
            prodInvestData.setLink_trans_serial(baseRequest.getLink_trans_serial());
        }
        ProdInvestReturnData prodInvestReturnData= singleInvest(baseRequest,prodProductInfo,prodInvestData);
        Map<String, BaseResponse> map =  buildAfterProcess(baseRequest,prodInvestReturnData.getSuccessData(),prodInvestReturnData.getErrorData());
        prodInvestReturnData.setOrderAfterProcessMap(map);
        BaseResponse baseResponse=new BaseResponse();

        try {
            baseResponse.setOrder_no(baseRequest.getOrder_no());
            if (prodInvestReturnData.getSuccessData().size()>0){
                baseResponse.setRecode(BusinessMsg.SUCCESS);
                baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));
                baseResponse.setOrder_status(FlowStatus.SUCCESS.getCode());
            }else {
                List<BaseErrorData> errorDataList = prodInvestReturnData.getErrorData();
                BaseErrorData errorData=errorDataList.get(0);
                baseResponse.setRecode(errorData.getError_no());
                baseResponse.setRemsg(errorData.getError_info());
                baseResponse.setOrder_status(FlowStatus.FAIL.getCode());
            }

            if (prodInvestData.getNotify_url()!=null&& !"".equals(prodInvestData.getNotify_url())){
                NotifyData notifyData=new NotifyData();
                notifyData.setNotifyUrl(prodInvestData.getNotify_url());
                notifyData.setMall_no(baseRequest.getMall_no());
                notifyData.setNotifyContent(JSON.toJSONString(baseResponse));
                notifyService.addNotify(notifyData);
                logger.info("【投标】-->丢入MQ成功-->order_no:"+baseResponse.getOrder_no());
            }
        }catch (Exception e){
            logger.info("异步通知发送失败",e);
        }
        logger.info("【单个投标】========结束投标");
        return prodInvestReturnData;
    }
    private ProdInvestReturnData singleInvest(BaseRequest baseRequest, ProdProductinfo prodProductInfo,ProdInvestDataTb prodInvestData ) throws BusinessException {
        logger.info("【单个投标singleInvest】========开始投标");
        ProdInvestReturnData prodInvestReturnData = new ProdInvestReturnData();
        List<ProdInvestSuccessData> successData = new ArrayList<ProdInvestSuccessData>();
        List<BaseErrorData> errorData = new ArrayList<BaseErrorData>();
        ProdProductinfo productInfo = prodSearchService.queryProdInfo(prodProductInfo.getPlat_no(), prodProductInfo.getProd_id());
        ProdInvestDataTb data = prodInvestData;
        try {
            //给北京银行特地写的逻辑 test bob
            if (productInfo!=null &&productInfo.getRemain_limit()!=null&&productInfo.getRemain_limit().compareTo(prodInvestData.getTrans_amt()) < 0) {
                BaseResponse baseResponse = new BaseResponse();
                baseResponse.setRecode(BusinessMsg.PROD_LIMIT_NOTENOUGH);
                baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.PROD_LIMIT_NOTENOUGH));
                throw new BusinessException(baseResponse);
            }
            Date expireDate=DateUtils.getDateWithoutTime(productInfo.getExpire_date());

            if (expireDate!=null&&DateUtils.today(DateUtils.DEF_FORMAT_NOTIME).compareTo(  expireDate ) > 0) {
                BaseResponse baseResponse = new BaseResponse();
                baseResponse.setRecode(BusinessMsg.OVER_DATE);
                baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.OVER_DATE));
                throw new BusinessException(baseResponse);
            }


            prodInvestmentExtService.invest(baseRequest, productInfo, data);
            ProdInvestSuccessData prodInvestSuccessData = new ProdInvestSuccessData();
            prodInvestSuccessData.setDetail_no(data.getDetail_no());
            prodInvestSuccessData.setPlatcust(data.getPlatcust());
            prodInvestSuccessData.setProd_id(prodProductInfo.getProd_id());
            prodInvestSuccessData.setTrans_amt(String.valueOf(data.getTrans_amt()));
            prodInvestSuccessData.setLink_trans_serial(data.getLink_trans_serial());
            Amtlist amtlist = new Amtlist();
            amtlist.setPlatcust(data.getPlatcust());
            if ("0".equals(data.getSubject_priority())) {
                amtlist.setAmttype(Tsubject.CASH.getCode());
            } else if ("1".equals(data.getSubject_priority())) {
                amtlist.setAmttype(Tsubject.FLOAT.getCode());
            }
            amtlist.setAmt(data.getTrans_amt());
            prodInvestSuccessData.setAmtlist(amtlist);
            successData.add(prodInvestSuccessData);
        } catch (BusinessException e) {
            //更新批量流水
            logger.info("【批量投标】========投标失败：" + e.getBaseResponse().getRemsg());
            BaseErrorData baseErrorData = new BaseErrorData();
            baseErrorData.setError_info(e.getBaseResponse().getRemsg());
            baseErrorData.setError_no(e.getBaseResponse().getRecode());
            baseErrorData.setDetail_no(data.getDetail_no());
            baseErrorData.setLink_trans_serial(data.getLink_trans_serial());
            errorData.add(baseErrorData);

            TransTransreq transTransreq = transReqService.queryTransTransReqByOrderno(prodInvestData.getDetail_no());
            if(null!=transTransreq){
                transTransreq.setStatus(FlowStatus.FAIL.getCode());
                transTransreq.setReturn_code(e.getBaseResponse().getRecode());
                transTransreq.setReturn_msg(e.getBaseResponse().getRemsg());
                transReqService.insert(transTransreq);
            }
        }


        prodInvestReturnData.setSuccessData(successData);
        prodInvestReturnData.setErrorData(errorData);
        return prodInvestReturnData;
    }
    private void validProdInfo(ProdProductinfo prodProductInfo) {
        ProdProductinfo productInfo = prodSearchService.queryProdInfo(prodProductInfo.getPlat_no(), prodProductInfo.getProd_id());
        if (null == productInfo) {
            logger.info("【批量投标】========标的不存在");
            BaseResponse baseResponse = new BaseResponse();
            baseResponse.setRecode(BusinessMsg.INVALID_PRODUCT_ID);
            baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.INVALID_PRODUCT_ID));
            throw new BusinessException(baseResponse);
        }
        if(!ProdState.PUBLISH.getCode().equals(productInfo.getProd_state())){
            BaseResponse baseResponse=new BaseResponse();
            baseResponse.setRecode(BusinessMsg.PROD_REJECT_INVEST);
            baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.PROD_REJECT_INVEST));
            throw new BusinessException(baseResponse);
        }
    }

    @SerialNo( transCode = TransConsts.BUY_CODE,description = TransConsts.BUY_NAME,isBatch=true)
    @Override
    public ProdInvestReturnData batchInvest(BaseRequest baseRequest, ProdProductinfo prodProductInfo,@SerialNoDetail List<ProdInvestDataTb> prodInvestDataList) throws BusinessException {
        logger.info("【批量投标】========开始批量投标");
        ProdInvestReturnData prodInvestReturnData = new ProdInvestReturnData();
        List<ProdInvestSuccessData> successData = new ArrayList<ProdInvestSuccessData>();
        List<BaseErrorData> errorData = new ArrayList<BaseErrorData>();
        validProdInfo(prodProductInfo);
        if(prodInvestDataList!=null) {
            for (ProdInvestDataTb data : prodInvestDataList) {
                ProdInvestReturnData singleInvestReturnData = singleInvest(baseRequest, prodProductInfo, data);
                if (singleInvestReturnData.getSuccessData() != null && singleInvestReturnData.getSuccessData().size() > 0) {
                    successData.add(singleInvestReturnData.getSuccessData().get(0));
                }
                if (singleInvestReturnData.getErrorData() != null && singleInvestReturnData.getErrorData().size() > 0) {
                    errorData.add(singleInvestReturnData.getErrorData().get(0));
                }
            }
        }
        prodInvestReturnData.setSuccessData(successData);
        prodInvestReturnData.setErrorData(errorData);

        Map<String, BaseResponse> map =  buildAfterProcess(baseRequest,successData,errorData);
        prodInvestReturnData.setOrderAfterProcessMap(map);

        return prodInvestReturnData;
    }

    private  Map<String, BaseResponse> buildAfterProcess(BaseRequest baseRequest,List<ProdInvestSuccessData> successData, List<BaseErrorData> errorData) {
        Map<String, BaseResponse> map = Maps.newHashMap();

        if (successData != null) {
            successData.forEach(succ -> {
                map.put(succ.getDetail_no(), new BaseResponse(BusinessMsg.SUCCESS,BusinessMsg.getMsg(BusinessMsg.SUCCESS)));
            });
        }
        if (errorData != null) {
            errorData.forEach(err -> {
                map.put(err.getDetail_no(), new BaseResponse(err.getError_no(),err.getError_info()));

            });
          boolean  parentOrder=errorData.size()==0;
            map.put(baseRequest.getOrder_no(),parentOrder?new BaseResponse(BusinessMsg.SUCCESS,BusinessMsg.getMsg(BusinessMsg.SUCCESS)):
                    new BaseResponse(BusinessMsg.FAIL,BusinessMsg.getMsg(BusinessMsg.FAIL)));
        }

        return map;
    }

    /**
     * 异步批量投标
     * @param prodInvestNoSynResquest
     * @return
     * @throws BusinessException
     */
    @SerialNo(transCode =TransConsts.BUY_CODE,description = TransConsts.BUY_NAME,isBatch = true,isAsync = true)
    @Override
    public BaseResponse batchInvestNoSync(ProdInvestNoSynResquest prodInvestNoSynResquest,boolean isNeedValidAuth) throws BusinessException {
        logger.info("【批量投标】========开始批量投标");
        Map<String, BaseResponse> orderAfterProcessMap = Maps.newHashMap();
        List<ProdInvestData> prodInvestDataList = prodInvestNoSynResquest.getDataArray();

        if (prodInvestDataList != null) {
            BaseResponse baseResponse = new BaseResponse(BusinessMsg.SUCCESS, BusinessMsg.getMsg(BusinessMsg.SUCCESS));
            try {

                prodMQOptionService.addInvests(prodInvestNoSynResquest, prodInvestDataList, prodInvestNoSynResquest.getNotify_url(),isNeedValidAuth);
            } catch (Exception e) {
                if (e instanceof BusinessException) {
                    baseResponse = ((BusinessException) e).getBaseResponse();
                } else {
                    baseResponse = new BaseResponse();
                    baseResponse.setRecode(BusinessMsg.RUNTIME_EXCEPTION);
                    baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.RUNTIME_EXCEPTION));
                }
                //更新批量流水
                logger.error("【批量投标】========投标失败orderno：" + prodInvestNoSynResquest.getOrder_no(), e);
            }
            for (ProdInvestData data : prodInvestDataList) {
               // prodMQOptionService.addInvest(prodInvestNoSynResquest, data, prodInvestNoSynResquest.getNotify_url());
                orderAfterProcessMap.put(data.getDetail_no(), baseResponse);

            }
        }
        BaseResponse baseModel = new BaseResponse();
        baseModel.setOrderAfterProcessMap(orderAfterProcessMap);
        logger.info("【批量投标】========批量投标成功");
        return baseModel;
    }

    public boolean applyInvest(ProdInvestApplyRequest prodInvestApplyRequest)throws BusinessException{
        try {
            logger.info("【出借授权申请】请求参数："+JSON.toJSONString(prodInvestApplyRequest));
            //并发控制
            boolean success = CacheUtil.getCache().setnx(Constants.PROD_INVEST_APPLY_LOCK_PREFIX + prodInvestApplyRequest.getOrder_no(),"true");
            if(!success){
                logger.info(String.format("【出借授权申请】申请已存在，直接返回|order_no:%s|platcust:%s",
                        prodInvestApplyRequest.getOrder_no(),prodInvestApplyRequest.getPlatcust()));
                return false;
            }
            TransTransreq transTransreq = transReqService.getTransTransReq(prodInvestApplyRequest.clone(), TransConsts.INVEST_APPLY_CODE, TransConsts.INVEST_APPLY_NAME, false);
            transTransreq.setStatus(FlowStatus.REQUESTSUCCESS.getCode());
            transReqService.insert(transTransreq);
            logger.info(String.format("【出借授权申请】流水插入成功|order_no:%s|platcust:%s",
                    prodInvestApplyRequest.getOrder_no(),prodInvestApplyRequest.getPlatcust()));
            return true;
        }catch (Exception e){
            logger.info(String.format("【出借授权申请】异常|order_no:%s|platcust:%s|error:",
                    prodInvestApplyRequest.getOrder_no(),prodInvestApplyRequest.getPlatcust()),e);
            return false;
        }finally {
            CacheUtil.getCache().del(Constants.PROD_INVEST_APPLY_LOCK_PREFIX+prodInvestApplyRequest.getOrder_no());
        }
    }

    /**
     * 异步批量投标确认
     * @param prodInvestNoSynResquest
     * @return
     * @throws BusinessException
     */
    @Override
    public BaseResponse batchInvestConfirmAsync(ProdInvestNoSynResquest prodInvestNoSynResquest) throws BusinessException {
        logger.info("【批量确认投标】========开始批量投标" + prodInvestNoSynResquest.getOrder_no());
        List<ProdInvestData> prodInvestDataList = prodInvestNoSynResquest.getDataArray();
        List<TransTransreq> failTransList = new ArrayList<>();
        //写入批次订单
        TransTransreq batchTransTransreq = transReqService.getTransTransReq(prodInvestNoSynResquest.clone(), TransConsts.BUY_CODE, TransConsts.BUY_NAME, true);
        transReqService.insert(batchTransTransreq);
        failTransList.add(batchTransTransreq);
        List<ProdInvestData> invests = new ArrayList<>();
        if (prodInvestDataList != null) {
            for (ProdInvestData data : prodInvestDataList) {
                //写入明细订单
                TransTransreq transTransreq = transReqService.getTransTransReq(prodInvestNoSynResquest.clone(), TransConsts.BUY_CODE, TransConsts.BUY_NAME, true);
                transTransreq.setOrder_no(data.getDetail_no());
                transTransreq.setOrigin_order_no(data.getOrigin_order_no());
                transTransreq.setPlatcust(data.getPlatcust());
                transTransreq.setNotify_url(prodInvestNoSynResquest.getNotify_url());
                BaseResponse baseResponse = transReqService.insert(transTransreq);
                if (baseResponse != null) {
                    logger.info("【批量确认投标】======== 明细单已经存在 " + JSON.toJSONString(data));
                    continue;
                }
                data.setLink_trans_serial(transTransreq.getTrans_serial());
                invests.add(data);
                failTransList.add(transTransreq);
            }

            BaseResponse baseResponse = new BaseResponse(BusinessMsg.SUCCESS, BusinessMsg.getMsg(BusinessMsg.SUCCESS));
            try {
                prodMQOptionService.addInvests(prodInvestNoSynResquest, prodInvestDataList, prodInvestNoSynResquest.getNotify_url(),  false);
            } catch (Exception e) {
                if (e instanceof BusinessException) {
                    baseResponse = ((BusinessException) e).getBaseResponse();
                } else {
                    baseResponse = new BaseResponse();
                    baseResponse.setRecode(BusinessMsg.RUNTIME_EXCEPTION);
                    baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.RUNTIME_EXCEPTION));
                }
                //更新批量流水
                logger.error("【批量投标】========投标失败orderno：" + prodInvestNoSynResquest.getOrder_no(), e);
            }

            if (!baseResponse.getRecode().equals(BusinessMsg.SUCCESS)) {
                for (TransTransreq transTransreq : failTransList) {
                    transTransreq.setReturn_code(baseResponse.getRecode());
                    transTransreq.setReturn_msg(baseResponse.getRemsg());
                    transTransreq.setStatus(FlowStatus.FAIL.getCode());
                    transReqService.insert(transTransreq);
                }

            }

            //更新批量流水
         //   logger.info("【批量投标】========投标失败：" + e.getBaseResponse().getRemsg());


        }


        logger.info("【批量投标】========批量投标成功");
        return new BaseResponse(BusinessMsg.SUCCESS, BusinessMsg.getMsg(BusinessMsg.SUCCESS));
    }

    @Transactional
    public boolean addShareList(ProdShareList prodShareList) {
        prodShareList.setCreate_by(SeqUtil.RANDOM_KEY);
        prodShareList.setCreate_time(DateUtils.today());
        prodShareListMapper.insert(prodShareList);
        return true;
    }

    @Transactional
    public boolean addTransferRecord(ProdTransferRecord transferRecord) throws BusinessException {
        transferRecordMapper.insert(transferRecord);
        return true;
    }

    @Override
    @Transactional
    public boolean delProdTransDate(String trans_serial) throws BusinessException {
        ProdShareListExample prodShareListExample=new ProdShareListExample();
        ProdShareListExample.Criteria prodShareListCriteria=prodShareListExample.createCriteria();
        prodShareListCriteria.andTrans_serialEqualTo(trans_serial);
        prodShareListMapper.deleteByExample(prodShareListExample);
        ProdTransferRecordExample transferRecordExample=new ProdTransferRecordExample();
        ProdTransferRecordExample.Criteria transferRecordCriteria=transferRecordExample.createCriteria();
        transferRecordCriteria.andTrans_serialEqualTo(trans_serial);
        transferRecordMapper.deleteByExample(transferRecordExample);
        return true;
    }

    private EaccUserinfo getEaccUserInfo(String mall_no,String mallcust){
        EaccUserinfoExample eaccUserinfoExample=new EaccUserinfoExample();
        EaccUserinfoExample.Criteria criteria=eaccUserinfoExample.createCriteria();
        criteria.andMall_noEqualTo(mall_no);
        criteria.andMallcustEqualTo(mallcust);
        List<EaccUserinfo> eaccUserinfoList=eaccUserinfoMapper.selectByExample(eaccUserinfoExample);
        if(eaccUserinfoList.size()>0){
            return eaccUserinfoList.get(0);
        }
        return null;
    }


    @Override
    public boolean nonAuthProdTransferApply(ProdTransferNonAuthRequest request) throws BusinessException{
        TransTransreq req =  transReqService.getTransTransReq(request,TransConsts.PRODTRANSFER_APPLY_CODE,TransConsts.PRODTRANSFER_APPLY_NAME,false);
        req.setStatus(FlowStatus.REQUESTSUCCESS.getCode());
        transReqService.insert(req);
        return true;
    }

    /**
     * 投资更新标的份额,添加交易明细
     * @param baseRequest
     * @param shareList
     * @param productInfo
     * @param realVol
     * @return
     * @throws BusinessException
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean saveInvestmentInfo(BaseRequest baseRequest,  ProdShareList shareList  ,ProdProductinfo productInfo, BigDecimal realVol) throws BusinessException{
        logger.info("【批量投标-异步】========单个投标更新剩余额度");
        Map<String, Object> params = new HashMap<>();
        params.put("prod_id", productInfo.getProd_id());
        params.put("plat_no", productInfo.getPlat_no());
        params.put("vol", realVol);
        params.put("update_by", SeqUtil.RANDOM_KEY);
        params.put("update_time",new Date());
        logger.info("更新标的剩余份额：", baseRequest.getOrder_no());
        logger.info("更新开始时间：" + new Date() + "，订单号：" + baseRequest.getOrder_no());
        custProdProdInfoMapper.subtractProdLimit(params);
        logger.info("更新结束时间：" + new Date() + "，订单号：" + baseRequest.getOrder_no());
        shareList.setCreate_time(new Date());
        shareList.setCreate_by(SeqUtil.RANDOM_KEY);
        logger.info("【新增投资交易明细】============ 参数:" + shareList.toString());
        logger.info("新增开始时间: " + new Date() + ",流水号: " + shareList.getTrans_serial());
        prodShareListMapper.insert(shareList);
        logger.info("新增结束时间: " + new Date() + ",流水号: " + shareList.getTrans_serial());
        return true;
    }

}
