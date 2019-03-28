package com.sunyard.sunfintech.user.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.dubbo.rpc.RpcException;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.sunyard.sunfintech.account.provider.IAccountQueryService;
import com.sunyard.sunfintech.account.provider.IAccountTransferService;
import com.sunyard.sunfintech.core.Constants;
import com.sunyard.sunfintech.core.TransConsts;
import com.sunyard.sunfintech.core.annotation.SerialNo;
import com.sunyard.sunfintech.core.base.BaseResponse;
import com.sunyard.sunfintech.core.base.BaseServiceSimple;
import com.sunyard.sunfintech.core.dic.*;
import com.sunyard.sunfintech.core.exception.BusinessException;
import com.sunyard.sunfintech.core.exception.BusinessMsg;
import com.sunyard.sunfintech.core.util.*;
import com.sunyard.sunfintech.custdao.mapper.CustAccountSubjectInfoMapper;
import com.sunyard.sunfintech.dao.entity.*;
import com.sunyard.sunfintech.dao.mapper.EaccFinancinfoMapper;
import com.sunyard.sunfintech.dao.mapper.EaccUserauthMapper;
import com.sunyard.sunfintech.dao.mapper.PayfeeInfoMapper;
import com.sunyard.sunfintech.dao.mapper.ProdProductinfoMapper;
import com.sunyard.sunfintech.pub.provider.IAuthCheckService;
import com.sunyard.sunfintech.pub.provider.ITransReqService;
import com.sunyard.sunfintech.pub.provider.ITransferService;
import com.sunyard.sunfintech.user.model.bo.*;
import com.sunyard.sunfintech.user.modulepublic.SysParameterService;
import com.sunyard.sunfintech.user.provider.IProdSearchService;
import com.sunyard.sunfintech.user.provider.IUserTransferService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

/**
 * 【功能描述】
 *
 * @author wyc  2018/3/11.
 */
@Service(interfaceClass =IUserTransferService.class)
@CacheConfig(cacheNames="userTransferService")
@org.springframework.stereotype.Service("userTransferService")
public class UserTransferService  extends BaseServiceSimple implements IUserTransferService {
    @Autowired
    private ITransReqService transReqService;
    @Autowired
    ITransferService transferService;
    @Autowired
    private IAccountTransferService newAccountTransferService;
    @Autowired
    private EaccUserauthMapper eaccUserauthMapper;
    @Autowired
    private IAuthCheckService authCheckService;
    @Autowired
    private IAccountQueryService accountQueryService;
    @Autowired
    private ProdProductinfoMapper prodProductInfoMapper;
    @Autowired
    private PayfeeInfoMapper payfeeInfoMapper;
    @Autowired
    private IProdSearchService prodSearchService;
    @Autowired
    private EaccFinancinfoMapper eaccFinancinfoMapper;
    @Autowired
    private SysParameterService sysParameterService;

    @Autowired
    private CustAccountSubjectInfoMapper custAccountSubjectInfoMapper;

    @Override
    @Transactional
    public void updateEaccUserAuth(List<EaccUserauth> eaccUserauths) {
        eaccUserauths.forEach((eaccUserauth) -> {
            eaccUserauthMapper.updateByPrimaryKeySelective(eaccUserauth);
        });
    }

    @SerialNo(transCode = TransConsts.PAY_FEE_CODE,description = TransConsts.PAY_FEE_NAME)
    @Override
    public BaseResponse payFee(PayFeeRequest payFeeRequest) throws BusinessException {
        return  payFeeEnque (payFeeRequest,TransConsts.PAY_FEE_CODE,TransConsts.PAY_FEE_NAME);
    }

    /**
     * 缴费和授权缴费接口公共方法
     * @param payFeeRequest
     * @return
     */
    private BaseResponse payFeeEnque(PayFeeRequest payFeeRequest,String transCode ,String transName) {
        BaseResponse baseResponse = new BaseResponse();
        Map<String, BaseResponse> map = Maps.newHashMap();
        try {

            if (TransConsts.AUTH_PAY_FEE_CODE.equals( transCode)) {
                logger.info(transName+"检查是否授权："+new Date()+"，订单号："+payFeeRequest.getOrder_no());
                boolean isSucc=    authCheckService.checkAuth(payFeeRequest.getMer_no(), payFeeRequest.getMall_no(), payFeeRequest.getPlatcust(), AuthType.AUTH_RECHARGE.getCode(),payFeeRequest.getAmt());
                logger.info(transName+"检查是否授权："+new Date()+"，订单号："+payFeeRequest.getOrder_no()+",结果："+isSucc);
                if (!isSucc){
                    throw new BusinessException(BusinessMsg.AUTH_ERROR,
                            String.format(BusinessMsg.AUTH_ERROR+"|授权数据失败|platcust:%s|auth_type:%s",payFeeRequest.getPlatcust(),AuthType.AUTH_RECHARGE.getCode()));
                }
            }
            String inPlatcust = validPayFeeRequest(payFeeRequest, transName);
            EaccAccountamtlist eaccAccountamtlist = new EaccAccountamtlist();
            eaccAccountamtlist.setOrder_no(payFeeRequest.getOrder_no());
            //优先现金
            eaccAccountamtlist.setSubject(Tsubject.CASH.getCode());
            eaccAccountamtlist.setOppo_subject(Tsubject.CASH.getCode());


            eaccAccountamtlist.setSub_subject(payFeeRequest.getAccount_type());
            String inOppSSubject = (StringUtils.isBlank(payFeeRequest.getPayee()) && StringUtils.isBlank(payFeeRequest.getProd_id())) ? Ssubject.PLAT.getCode() : payFeeRequest.getAccount_type();
            eaccAccountamtlist.setOppo_sub_subject(inOppSSubject);
            eaccAccountamtlist.setRemark(transName);
            //设置公共参数
            String transNo = payFeeRequest.getLink_trans_serial() == null ? SeqUtil.getSeqNum() : payFeeRequest.getLink_trans_serial();
            eaccAccountamtlist.setTrans_serial(transNo);
            eaccAccountamtlist.setAmt(payFeeRequest.getAmt());
            eaccAccountamtlist.setTrans_date(payFeeRequest.getPartner_trans_date());
            eaccAccountamtlist.setTrans_time(payFeeRequest.getPartner_trans_time());
            eaccAccountamtlist.setTrans_code(transCode);
            eaccAccountamtlist.setTrans_name(transName);

            eaccAccountamtlist.setPlat_no(payFeeRequest.getMer_no());
            eaccAccountamtlist.setOppo_plat_no(payFeeRequest.getMer_no());
            eaccAccountamtlist.setPlatcust(payFeeRequest.getPlatcust());
            eaccAccountamtlist.setOppo_platcust(inPlatcust);

            List<EaccAccountamtlist> list = new ArrayList<>();
            list.add(eaccAccountamtlist);

            PayfeeInfo payfeeInfo=new PayfeeInfo();
            payfeeInfo.setMall_no(payFeeRequest.getMall_no());
            payfeeInfo.setPlat_no(payFeeRequest.getMer_no());
            payfeeInfo.setPlatcust(payFeeRequest.getPlatcust());
            payfeeInfo.setPayee(payFeeRequest.getPayee());
            payfeeInfo.setAmt(payFeeRequest.getAmt());
            payfeeInfo.setAccount_type(payFeeRequest.getAccount_type());
            payfeeInfo.setProd_id(payFeeRequest.getProd_id());
            payfeeInfo.setOrder_no(payFeeRequest.getOrder_no());
            payfeeInfo.setTrans_serial(payFeeRequest.getLink_trans_serial());
            payfeeInfo.setEnabled(Constants.ENABLED);
            payfeeInfo.setRemark(payFeeRequest.getTips());
            payfeeInfo.setCreate_by(transCode);
            payfeeInfo.setCreate_time(new Date());
            payfeeInfo.setUpdate_time(new Date());

            payfeeInfoMapper.insert(payfeeInfo);
            // boolean isSucc = MQUtils.send(amqpTemplate, "ftdm.user.direct.exchange", "PayFeeQueue", list);
            try{
                if (TransConsts.PAY_FEE_CODE.equals(transCode))
                {
                    logger.info("转账开始时间："+new Date()+"，订单号："+payFeeRequest.getOrder_no());
                    newAccountTransferService.batchTransfer(list);
                    logger.info("转账结束时间："+new Date()+"，订单号："+payFeeRequest.getOrder_no());
                }else {
                    logger.info(String.format("【" + transName + "】开始进入TransferQueue队列|orderno:%s，transserial:%s", payFeeRequest.getOrder_no(), payFeeRequest.getLink_trans_serial()));
                    transferService.transfer(payFeeRequest, list);
                    logger.info(String.format("【" + transName + "】结束进入TransferQueue队列|orderno:%s，transserial:%s，是否成功：是", payFeeRequest.getOrder_no(), payFeeRequest.getLink_trans_serial()));
                }
            }catch (Exception e){
                //回滚限额
                if(StringUtils.isBlank(payFeeRequest.getProd_id())){
                    String payFeeToPlatRedisKey=Constants.PAY_FEE_DAY_LIMIT_KEY+payFeeRequest.getMer_no()+DateUtils.getyyyyMMddDate();
                    String bd100=String.valueOf(payFeeRequest.getAmt().multiply(new BigDecimal(100)));
                    CacheUtil.getCache().increment(payFeeToPlatRedisKey,0L-Long.parseLong(bd100.indexOf(".")>0?bd100.substring(0,bd100.indexOf(".")):bd100));
                }
                throw e;
            }



            baseResponse.setRecode(BusinessMsg.SUCCESS);
            baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));
            if (TransConsts.AUTH_PAY_FEE_CODE.equals( transCode)) {
                baseResponse.setOrder_status(OrderStatus.PROCESSING.getCode());
            }else{
                baseResponse.setOrder_status(OrderStatus.SUCCESS.getCode());
            }

        } catch (Exception e) {
            logger.error(e);
            if (e instanceof BusinessException) {
                baseResponse = ((BusinessException) e).getBaseResponse();
                baseResponse.setOrder_status(OrderStatus.FAIL.getCode());
            }else  if (e instanceof RpcException) {
                baseResponse.setRecode(BusinessMsg.CALL_REMOTE_ERROR);
                baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.CALL_REMOTE_ERROR));
                baseResponse.setOrder_status(OrderStatus.PROCESSING.getCode());
            } else {
                baseResponse.setRecode(BusinessMsg.RUNTIME_EXCEPTION);
                baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.RUNTIME_EXCEPTION));
                baseResponse.setOrder_status(OrderStatus.FAIL.getCode());
            }
            PayfeeInfo payfeeInfo=queryPayfeeInfo(payFeeRequest.getLink_trans_serial());
            if(payfeeInfo!=null){
                payfeeInfo.setEnabled(Constants.DISABLED);
                payfeeInfoMapper.updateByPrimaryKey(payfeeInfo);
            }


        } finally {
            map.put(payFeeRequest.getOrder_no(), baseResponse);
            baseResponse.setOrderAfterProcessMap(map);
        }

        return baseResponse;
    }

    private String validPayFeeRequest(PayFeeRequest payFeeRequest, String transName) {
        validate(payFeeRequest);
        if (!Ssubject.INVEST.getCode().equals(payFeeRequest.getAccount_type()) && !Ssubject.FINANCING.getCode().equals(payFeeRequest.getAccount_type())) {
            logger.info(String.format("【" + transName + "】账户类型只能是融资或者投资|orderno:%s", payFeeRequest.getAccount_type()));
            throw new BusinessException(BusinessMsg.PARAMETER_ERROR, BusinessMsg.getMsg(BusinessMsg.METHODPARAMETER_ERROR));
        }
        //查询出资人账户
        List<AccountSubjectInfo> outAccountList = accountQueryService.queryPlatAccountList(payFeeRequest.getMer_no(), payFeeRequest.getPlatcust(), null, payFeeRequest.getAccount_type());
        if (outAccountList == null || outAccountList.size() == 0) {
            logger.error("**********【" + transName + "】" + payFeeRequest.getOrder_no() + ">>查询不到出资人账户：**********");
            throw new BusinessException(BusinessMsg.PARAMETER_ERROR, "查询不到出资人账户");
        }
        String inPlatcust = "";
        if (StringUtils.isBlank(payFeeRequest.getPayee())&&StringUtils.isBlank(payFeeRequest.getProd_id())){
            List< AccountSubjectInfo > inAccountlist =  accountQueryService.queryPlatAccountList(payFeeRequest.getMer_no(), payFeeRequest.getMer_no(), null, Ssubject.PLAT.getCode());
            if (inAccountlist == null||inAccountlist.size()==0) {
                logger.info("【"+transName+"】-->平台账号不存在-->order_no:" + payFeeRequest.getOrder_no());
                throw new BusinessException(BusinessMsg.INVALID_ACCOUNT, BusinessMsg.getMsg(BusinessMsg.INVALID_ACCOUNT) + ",平台账号不存在:" + payFeeRequest.getOrder_no());
            }
            inPlatcust = inAccountlist.get(0).getPlatcust();

            //缴费到平台限额判断
            String singleLimited=sysParameterService.querySysParameter(payFeeRequest.getMall_no(), SysParamterKey.PAY_FEE_TO_PLAT_SINGLE_LIMITED);
            String allLimited=sysParameterService.querySysParameter(payFeeRequest.getMall_no(), SysParamterKey.PAY_FEE_TO_PLAT_All_LIMITED);
            if(StringUtils.isNotBlank(singleLimited)){
                BigDecimal singleLimitedBD=new BigDecimal(singleLimited);
                if(payFeeRequest.getAmt().compareTo(singleLimitedBD)>0){
                    //单笔超限
                    throw new BusinessException(BusinessMsg.LIMIT_OUT,BusinessMsg.getMsg(BusinessMsg.LIMIT_OUT));
                }
                if(StringUtils.isNotBlank(allLimited)){
                    BigDecimal allLimitedBD=new BigDecimal(allLimited);
                    //查询出当日全部的缴费到平台
                    String payFeeToPlatRedisKey=Constants.PAY_FEE_DAY_LIMIT_KEY+payFeeRequest.getMer_no()+DateUtils.getyyyyMMddDate();
                    String bd100=String.valueOf(payFeeRequest.getAmt().multiply(new BigDecimal(100)));
                    long payFeeToPlatLimitedX100=CacheUtil.getCache().increment(payFeeToPlatRedisKey,Long.parseLong(bd100.indexOf(".")>0?bd100.substring(0,bd100.indexOf(".")):bd100));
                    CacheUtil.getCache().expire(payFeeToPlatRedisKey,24*3600);
                    if(allLimitedBD.compareTo(new BigDecimal(payFeeToPlatLimitedX100).divide(new BigDecimal(100)))<0){
                        CacheUtil.getCache().increment(payFeeToPlatRedisKey,0L-Long.parseLong(bd100.indexOf(".")>0?bd100.substring(0,bd100.indexOf(".")):bd100));
                        //当日缴费到平台总额超限
                        throw new BusinessException(BusinessMsg.LIMIT_OUT,BusinessMsg.getMsg(BusinessMsg.LIMIT_OUT));
                    }
                }
            }

        }else {
            inPlatcust = payFeeRequest.getPayee();
            List<AccountSubjectInfo> inAccountlist = accountQueryService.queryFINANCINGPlatAccountlist(payFeeRequest.getMer_no(), inPlatcust, null, payFeeRequest.getAccount_type());
            if (StringUtils.isBlank (inPlatcust)||inAccountlist == null || inAccountlist.size() == 0) {
                throw new BusinessException(BusinessMsg.PARAMETER_ERROR, "查询不到收款人账户=" + inPlatcust + ",accountType=" + payFeeRequest.getAccount_type());
            }//validProd(payFeeRequest.getMer_no(),payFeeRequest.getOrder_no(),payFeeRequest.getPayee(),payFeeRequest.getProd_id());

            ProdProductinfo checkProductInfo =null;
            if (StringUtils.isNotBlank(payFeeRequest.getProd_id()))
                checkProductInfo =  queryProdInfo(payFeeRequest.getMer_no(), payFeeRequest.getProd_id());
            if (checkProductInfo == null) {
                logger.info(String.format("【缴费】标的不存在|prod_id:%s或者募集账号不存在|orderno:%s", payFeeRequest.getProd_id(), payFeeRequest.getOrder_no()));
                throw new BusinessException(BusinessMsg.INVALID_PRODUCT_ID, BusinessMsg.getMsg(BusinessMsg.INVALID_PRODUCT_ID) + "或者募集账号不存在");
            }

            List<ProdCompensationList>  prodCompensationLists= prodSearchService.queryProdCompensationList(payFeeRequest.getMer_no(),payFeeRequest.getProd_id(),payFeeRequest.getPayee());
            if (prodCompensationLists == null || prodCompensationLists.size() == 0) {
                List<EaccFinancinfo> eaccFinancinfos=queryEaccFinancinfos(payFeeRequest.getMer_no(),payFeeRequest.getProd_id(),payFeeRequest.getPlatcust());
                //判断是否是标的借款人
                if(eaccFinancinfos==null || eaccFinancinfos.size()==0) {
                    logger.info(String.format("【" + transName + "】\"收款人账户%s不是标的代偿账户|orderno:%s", payFeeRequest.getPayee(), payFeeRequest.getOrder_no()));
                    throw new BusinessException(BusinessMsg.PARAMETER_ERROR, "收款人账户" + payFeeRequest.getPayee() + "不是标" + payFeeRequest.getProd_id() + "的代偿账户");
                }
            }

            //判断单次缴费限额
            String payFeeLimited=sysParameterService.querySysParameter(payFeeRequest.getMall_no(), SysParamterKey.PAY_FEE_LIMITED);
            if(StringUtils.isBlank(payFeeLimited)){
                payFeeLimited=sysParameterService.querySysParameter(SysParamterKey.FTDM, SysParamterKey.PAY_FEE_LIMITED);
            }
            if(StringUtils.isNotBlank(payFeeLimited)){
                BigDecimal allVol=checkProductInfo.getTotal_limit();
                BigDecimal maxVol=allVol.multiply(new BigDecimal(payFeeLimited));
                if(payFeeRequest.getAmt().compareTo(maxVol)>0){
                    throw new BusinessException(BusinessMsg.LIMIT_OUT,BusinessMsg.getMsg(BusinessMsg.LIMIT_OUT));
                }
            }
            //判断总共缴费限额
            String payFeeSumLimited=sysParameterService.querySysParameter(payFeeRequest.getMall_no(), SysParamterKey.PAY_FEE_SUM_LIMITED);
            if(StringUtils.isBlank(payFeeSumLimited)){
                payFeeSumLimited=sysParameterService.querySysParameter(SysParamterKey.FTDM, SysParamterKey.PAY_FEE_SUM_LIMITED);
            }
            if(StringUtils.isNotBlank(payFeeSumLimited)){
                BigDecimal allVol=checkProductInfo.getTotal_limit();
                BigDecimal maxVol=allVol.multiply(new BigDecimal(payFeeSumLimited));
                List<PayfeeInfo> payfeeInfoList=queryPayfeeInfoList(payFeeRequest.getPlatcust(),payFeeRequest.getProd_id());
                BigDecimal alreadyPayfeeAmt=BigDecimal.ZERO;
                for(PayfeeInfo payfeeInfo:payfeeInfoList){
                    alreadyPayfeeAmt=alreadyPayfeeAmt.add(payfeeInfo.getAmt());
                }
                alreadyPayfeeAmt=alreadyPayfeeAmt.add(payFeeRequest.getAmt());
                if(alreadyPayfeeAmt.compareTo(maxVol)>0){
                    throw new BusinessException(BusinessMsg.LIMIT_OUT,BusinessMsg.getMsg(BusinessMsg.LIMIT_OUT));
                }
            }

        }
        return inPlatcust;
    }

    public List<EaccFinancinfo> queryEaccFinancinfos(String plat_no,String prod_id,String platcust){
        EaccFinancinfoExample example=new EaccFinancinfoExample();
        EaccFinancinfoExample.Criteria criteria=example.createCriteria();
        criteria.andProd_idEqualTo(prod_id);
        criteria.andPlat_noEqualTo(plat_no);
        criteria.andPlatcustEqualTo(platcust);
        criteria.andEnabledEqualTo(Constants.ENABLED);
        List<EaccFinancinfo> eaccFinancinfos = eaccFinancinfoMapper.selectByExample(example);
        return eaccFinancinfos;
    }

    public ProdProductinfo queryProdInfo(String plat_no, String prod_id) throws BusinessException {
        ProdProductinfoExample example=new ProdProductinfoExample();
        ProdProductinfoExample.Criteria criteria = example.createCriteria();
        if(StringUtils.isNotBlank(plat_no))
            criteria.andPlat_noEqualTo(plat_no);
        if(StringUtils.isNotBlank(prod_id))
            criteria.andProd_idEqualTo(prod_id);
        if(criteria.getAllCriteria().size()==0){
            BaseResponse baseResponse=new BaseResponse();
            baseResponse.setRecode(BusinessMsg.PARAMETER_LACK);
            baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.PARAMETER_LACK) );
            throw new BusinessException(baseResponse);
        }
        criteria.andEnabledEqualTo(Constants.ENABLED);
        List<ProdProductinfo> prodProductInfoList = prodProductInfoMapper.selectByExample(example);
        if(prodProductInfoList.size() > 1){
            BaseResponse baseResponse=new BaseResponse();
            baseResponse.setRecode(BusinessMsg.DATEBASE_EXCEPTION);
            baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.DATEBASE_EXCEPTION) + "----有重复的标");
            throw new BusinessException(baseResponse);
        }else if(prodProductInfoList.size() < 1){
            return null;
        }
        return prodProductInfoList.get(0);
    }
//    private boolean moq(List<EaccAccountamtlist> list) {
//
//        TransTransreq transTransReq = null;
//        List<EaccAccountamtlist> eaccAccountamtlist = null;
//        try {
//            eaccAccountamtlist = list;
//            logger.info("开始消费orderno=" + eaccAccountamtlist.get(0).getOrder_no());
//            transTransReq = new TransTransreq();
//            transTransReq.setOrder_no(eaccAccountamtlist.get(0).getOrder_no());
//            transTransReq.setTrans_serial(eaccAccountamtlist.get(0).getTrans_serial());
//            accountTransferService.batchTransfer(eaccAccountamtlist);
//
//            transTransReq.setStatus(OrderStatus.SUCCESS.getCode());
//            transTransReq.setReturn_code(BusinessMsg.SUCCESS);
//            transTransReq.setReturn_msg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));
//            transReqService.insert(transTransReq);
//        } catch (Exception e) {
//            e.printStackTrace();
//            BaseResponse baseResponse = new BaseResponse();
//            if (e instanceof BusinessException) {
//                baseResponse = ((BusinessException) e).getBaseResponse();
//            } else {
//                logger.error(e);
//                baseResponse.setRecode(BusinessMsg.DATEBASE_EXCEPTION);
//                baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.DATEBASE_EXCEPTION));
//            }
//            if (transTransReq != null) {
//                transTransReq.setStatus(FlowStatus.FAIL.getCode());
//                transTransReq.setReturn_code(baseResponse.getRecode());
//                transTransReq.setReturn_msg(baseResponse.getRemsg());
//                transTransReq.setRemark("消费异常");
//                transReqService.insert(transTransReq);
//            }
//            return false;
//        }
//        return true;
//    }

    @SerialNo(transCode = TransConsts.AUTH_PAY_FEE_CODE,description = TransConsts.AUTH_PAY_FEE_NAME,isAsync = true,isBatch = true)
    @Override
    public AuthPayFeeResponse authPayFee(AuthPayFeeRequest authPayFeeRequest) throws BusinessException {
        AuthPayFeeResponse authPayFeeResponse = new AuthPayFeeResponse();
        validate(authPayFeeRequest);
        List<AuthPayFeeRequestDetail> details = authPayFeeRequest.getAuthPayFeeRequestDetails();
        List<AuthPayFeeResponseDetail> authPayFeeResponseDetails=new ArrayList<>();
        if (details != null) {
            Map map = Maps.newHashMap();
            for (int i = 0; i < details.size(); i++) {
                AuthPayFeeRequestDetail detail=details.get(i);
                PayFeeRequest detailRequest = new PayFeeRequest();
                BeanUtils.copyProperties(authPayFeeRequest, detailRequest);
                BeanUtils.copyProperties(detail, detailRequest, getNullPropertyNames(detail));
                detailRequest.setOrder_no(detail.getDetail_no());
                BaseResponse baseResponse = payFeeEnque(detailRequest,TransConsts.AUTH_PAY_FEE_CODE,TransConsts.AUTH_PAY_FEE_NAME);
                map.putAll( baseResponse.getOrderAfterProcessMap());
                AuthPayFeeResponseDetail repsonseDetail=new AuthPayFeeResponseDetail();
                repsonseDetail.setOrder_status(baseResponse.getOrder_status());
                if (!BusinessMsg.SUCCESS.equals(baseResponse.getRecode())) {
                    repsonseDetail.setError_no(baseResponse.getRecode());
                    repsonseDetail.setError_info(baseResponse.getRemsg());
                }
                repsonseDetail.setQuery_id(detailRequest.getOrder_no());
                repsonseDetail.setProcess_date(DateUtils.todayfulldata());
                authPayFeeResponseDetails.add(repsonseDetail);
                cacheNotifyData(authPayFeeRequest,detail);
            }
            authPayFeeResponse.setOrderAfterProcessMap(map);
        }
        authPayFeeResponse.setOrderData(authPayFeeResponseDetails);
        authPayFeeResponse.setRecode(BusinessMsg.SUCCESS);
        authPayFeeResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));
        authPayFeeResponse.setOrder_no(authPayFeeRequest.getOrder_no());
        return authPayFeeResponse;
    }

    private void cacheNotifyData(AuthPayFeeRequest authPayFeeRequest,AuthPayFeeRequestDetail detail) {
        try {
            PayFeeNotify payFeeNotify = new PayFeeNotify();
            payFeeNotify.setPlatcust(detail.getPlatcust());
//            payFeeNotify.setOrder_status(trans.getStatus());
            payFeeNotify.setProd_id(detail.getProd_id());
            payFeeNotify.setMall_no(authPayFeeRequest.getMall_no());
            payFeeNotify.setTrans_amt(detail.getAmt());
            String key = Constants.AUTHPAYFEE_ORDER_KEY + serviceName + "_order:" + detail.getDetail_no();
            CacheUtil.getCache().set(key, JSON.toJSONString(payFeeNotify), 24 * 3600);
            logger.info("【授权缴费】缓存成功key=" + key);
        } catch (Exception e) {
            logger.error("【授权缴费】cacheNotifyDatay异常", e);
        }
    }

    public static String[] getNullPropertyNames (Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<String>();
        for(java.beans.PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) emptyNames.add(pd.getName());
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }
    @SerialNo(transCode = TransConsts.CANCEL_PAY_FEE_CODE,description = TransConsts.CANCEL_PAY_FEE_NAME)
    @Override
    public BaseResponse cancelPayFee(CancelPayFeeRequest cancelPayFeeRequest) throws BusinessException {
        BaseResponse baseResponse = new BaseResponse();
        Map<String, BaseResponse> map = Maps.newHashMap();
        map.put(cancelPayFeeRequest.getOrder_no(), baseResponse);
        validate(cancelPayFeeRequest);
        TransTransreq transTransreq = transReqService.queryTransTransReqByOrderno(cancelPayFeeRequest.getOri_order_no());
        if (transTransreq == null || !FlowStatus.SUCCESS.getCode().equals(transTransreq.getStatus())) {
            logger.info("【取消缴费】查询不到成功的缴费信息orderno:" + cancelPayFeeRequest.getOri_order_no());
            throw new BusinessException(BusinessMsg.PARAMETER_ERROR, "查询不到成功的缴费信息orderno:" + cancelPayFeeRequest.getOri_order_no());
        }
        PayfeeInfoExample payfeeInfoExample = new PayfeeInfoExample();
        PayfeeInfoExample.Criteria criteria = payfeeInfoExample.createCriteria();
        criteria.andOrder_noEqualTo(cancelPayFeeRequest.getOri_order_no());
        List<PayfeeInfo>  payfeeInfos=  payfeeInfoMapper.selectByExample(payfeeInfoExample);
        if (payfeeInfos==null||payfeeInfos.size()!=1){
            logger.info("【取消缴费】查询不到缴费记录信息orderno:" + cancelPayFeeRequest.getOri_order_no());
            throw new BusinessException(BusinessMsg.PARAMETER_ERROR, "查询不到成功的缴费信息orderno:" + cancelPayFeeRequest.getOri_order_no());
        }
        List<EaccAccountamtlist> eaccAccountamtlists = accountQueryService.queryEaccAccountamtlistByTransSerial(transTransreq.getTrans_serial());
        if (eaccAccountamtlists == null || eaccAccountamtlists.size() == 0) {
            logger.info("【取消缴费】查询不到成功的缴费流水信息orderno:" + cancelPayFeeRequest.getOri_order_no());
            throw new BusinessException(BusinessMsg.PARAMETER_ERROR, "查询不到成功的缴费流水信息orderno:" + cancelPayFeeRequest.getOri_order_no());
        } else {
            List<EaccAccountamtlist> backEaccAccountamtlist = getBackEaccAccountamtlist(eaccAccountamtlists, cancelPayFeeRequest.getOrder_no(), cancelPayFeeRequest.getLink_trans_serial(), TransConsts.CANCEL_PAY_FEE_CODE, TransConsts.CANCEL_PAY_FEE_NAME);
            if (backEaccAccountamtlist.size() == 0) {
                logger.info("【取消缴费】查询不到成功的缴费流水信息orderno:" + cancelPayFeeRequest.getOri_order_no());
                throw new BusinessException(BusinessMsg.PARAMETER_ERROR, "查询不到成功的缴费流水信息orderno:" + cancelPayFeeRequest.getOri_order_no());
            }


            logger.info(String.format("【缴费】开始进入TransferQueue队列|orderno:%s，transserial:%s", cancelPayFeeRequest.getOrder_no(), cancelPayFeeRequest.getLink_trans_serial()));
            //   boolean isSucc = MQUtils.send(amqpTemplate, "ftdm.user.direct.exchange", "PayFeeQueue", backEaccAccountamtlist);
//            transferService.transfer(cancelPayFeeRequest, backEaccAccountamtlist);
            try {

                PayfeeInfo payfeeInfo=payfeeInfos.get(0);
                payfeeInfo.setCancel_trans_serial(cancelPayFeeRequest.getLink_trans_serial());
                payfeeInfo.setCancel_order_no(cancelPayFeeRequest.getOrder_no());
                payfeeInfo.setUpdate_by("cancelpayfee");
                payfeeInfo.setUpdate_time(new Date());
                payfeeInfo.setEnabled(Constants.DISABLED);
                int i=payfeeInfoMapper.updateByPrimaryKeySelective(payfeeInfo);
                logger.info("取消缴费更新记录count"+i+"，订单号：" + cancelPayFeeRequest.getOrder_no());
//                payfeeInfoMapper.updateByPrimaryKeySelective(payfeeInfo);
                logger.info("转账开始时间：" + new Date() + "，订单号：" + cancelPayFeeRequest.getOrder_no());
                newAccountTransferService.batchTransfer(backEaccAccountamtlist);
                logger.info("转账结束时间：" + new Date() + "，订单号：" + cancelPayFeeRequest.getOrder_no());
                baseResponse.setOrder_status(OrderStatus.SUCCESS.getCode());
            }catch (RpcException e){
                baseResponse.setRecode(BusinessMsg.SUCCESS);
                baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));
                baseResponse.setOrder_status(OrderStatus.PROCESSING.getCode());
            }
            logger.info(String.format("【缴费】结束进入TransferQueue队列|orderno:%s，transserial:%s，是否成功：是", cancelPayFeeRequest.getOrder_no(), cancelPayFeeRequest.getLink_trans_serial()));
            baseResponse.setRecode(BusinessMsg.SUCCESS);
            baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));
            map.put(cancelPayFeeRequest.getOrder_no(), baseResponse);
        }
        baseResponse.setOrderAfterProcessMap(map);
        return baseResponse;
    }
    private List<EaccAccountamtlist> getBackEaccAccountamtlist(List<EaccAccountamtlist> eaccAccountamtlists,String orderNo,String transSerialNo,String transCode,String transName){
        List<EaccAccountamtlist> newEaccAccountamtlists=new ArrayList<>();
        for(EaccAccountamtlist eaccAccountamtlist:eaccAccountamtlists){
            if(AmtType.INCOME.getCode().equals(eaccAccountamtlist.getAmt_type())){
                //把出金参数当成反向转账参数进行转账
                eaccAccountamtlist.setAmt_type(null);
                eaccAccountamtlist.setCreate_time(null);
                eaccAccountamtlist.setUpdate_time(null);
                eaccAccountamtlist.setId(null);
                eaccAccountamtlist.setAccount_date(null);
                eaccAccountamtlist.setSwitch_state(null);
                eaccAccountamtlist.setSwitch_amt(null);
                eaccAccountamtlist.setTrans_code(transCode);
                eaccAccountamtlist.setTrans_name(transName);
                eaccAccountamtlist.setOrder_no(orderNo);
                eaccAccountamtlist.setTrans_serial(transSerialNo);
                newEaccAccountamtlists.add(eaccAccountamtlist);
            }
        }
        return newEaccAccountamtlists;
    }

    private List<PayfeeInfo> queryPayfeeInfoList(String platcust,String prod_id)throws BusinessException{
        PayfeeInfoExample example =new PayfeeInfoExample();
        PayfeeInfoExample.Criteria criteria=example.createCriteria();
        criteria.andEnabledEqualTo(Constants.ENABLED);
//        criteria.andPlatcustEqualTo(platcust);
        criteria.andProd_idEqualTo(prod_id);
        return payfeeInfoMapper.selectByExample(example);
    }

    private PayfeeInfo queryPayfeeInfo(String trans_serial){
        PayfeeInfoExample example =new PayfeeInfoExample();
        PayfeeInfoExample.Criteria criteria=example.createCriteria();
        criteria.andEnabledEqualTo(Constants.ENABLED);
        criteria.andTrans_serialEqualTo(trans_serial);
        List<PayfeeInfo> payfeeInfoList=payfeeInfoMapper.selectByExample(example);
        if(payfeeInfoList.size()>0){
            return payfeeInfoList.get(0);
        }
        return null;
    }
}
