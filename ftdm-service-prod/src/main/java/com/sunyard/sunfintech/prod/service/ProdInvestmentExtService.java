package com.sunyard.sunfintech.prod.service;

import com.alibaba.fastjson.JSON;
import com.sunyard.sunfintech.account.provider.IAccountQueryService;
import com.sunyard.sunfintech.account.provider.IAccountTransferService;
import com.sunyard.sunfintech.core.Constants;
import com.sunyard.sunfintech.core.TransConsts;
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
import com.sunyard.sunfintech.dao.mapper.*;
import com.sunyard.sunfintech.prod.model.bo.*;
import com.sunyard.sunfintech.prod.provider.*;
import com.sunyard.sunfintech.prod.utils.EaccAccountamtlistOptionUtil;
import com.sunyard.sunfintech.pub.model.MsgModel;
import com.sunyard.sunfintech.pub.provider.*;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

import static com.sunyard.sunfintech.core.util.CacheUtil.getLock;

/**
 * Created by terry on 2017/7/9.
 */
@CacheConfig(cacheNames = "prodInvestmentExtService")
@org.springframework.stereotype.Service
public class ProdInvestmentExtService extends BaseServiceSimple implements IProdInvestmentExtService {

    @Autowired
    private ProdShareListMapper prodShareListMapper;

    @Autowired
    private ProdShareInallMapper prodShareInallMapper;

    @Autowired
    private IAccountTransferService accountTransferService;

    @Autowired
    private CustProdShareInallMapper custProdShareInallMapper;

    @Autowired
    private ProdRepaymentlistMapper repayMentListMapper;

    @Autowired
    private IAccountQueryService accountQueryService;

    @Autowired
    ProdCompensationListMapper prodCompensationListMapper;

    @Autowired
    private IProdSearchService prodSearchService;

    @Autowired
    private IProdShareOptionService prodShareOptionService;

    @Autowired
    private ITransReqService transReqService;

    @Autowired
    private IProductInvestmentService productInvestmentService;

    @Autowired
    private EaccUserinfoMapper eaccUserinfoMapper;

    @Autowired
    private CustProdProdInfoMapper custProdProdInfoMapper;

    @Autowired
    private ISysParameterService sysParameterService;

    @Autowired
    private ISendMsgService sendMsgService;

    @Autowired
    private ProdBorrowerRealrepayMapper prodBorrowerRealrepayMapper;

    @Autowired
    private IProdTransferService prodTransferService;

    @Autowired
    private  AmqpTemplate amqpTemplate;

    @Autowired
    private ITransferService transferService;

    @Autowired
    private IAuthCheckService authCheckService;

    /**
     * 单个投标
     * @param baseRequest
     * @param productInfo
     * @param prodInvestData
     * @return
     * @throws BusinessException
     */
    @Override
    @Transactional
    public boolean invest(BaseRequest baseRequest, ProdProductinfo productInfo, ProdInvestDataTb prodInvestData) throws BusinessException {
        logger.error(String.format("【批量投标】========单个投标开始，order_no:%s|platcust:%s|prod_id:%s",baseRequest.getOrder_no(),prodInvestData.getPlatcust(),productInfo.getProd_id()));
        try {
           investOption(baseRequest,productInfo,prodInvestData);
        } catch (Exception e) {
            //数据库操作错误，记录流水，抛异常
            BaseResponse baseResponse = new BaseResponse();
            if (e instanceof BusinessException) {
                baseResponse = ((BusinessException) e).getBaseResponse();
                logger.info("【批量投标】========单个投标失败" + baseResponse.getRemsg());
            } else {
                logger.error("【批量投标】========数据库操作异常：",e );
                baseResponse.setRecode(BusinessMsg.DATEBASE_EXCEPTION);
                 baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.DATEBASE_EXCEPTION));
            }
            throw new BusinessException(baseResponse);
        }
        return true;
    }

    /**
     * 异步投标队列消费
     * @param baseRequest
     * @param prodInvestData
     * @return
     * @throws BusinessException
     */
//    @Transactional
    @Override
    public boolean investForNoSyn(BaseRequest baseRequest, ProdInvestData prodInvestData) throws BusinessException {
        try {
            ProdProductinfo productInfo = prodSearchService.queryProdInfo(baseRequest.getMer_no(), prodInvestData.getProd_id());
            if (null == productInfo) {
                logger.info("【批量投标】========标的不存在");
                BaseResponse baseResponse = new BaseResponse();
                baseResponse.setRecode(BusinessMsg.INVALID_PRODUCT_ID);
                baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.INVALID_PRODUCT_ID));
                throw new BusinessException(baseResponse);
            }
            if (ProdState.FOUND.getCode().equals(productInfo.getProd_state())) {
                BaseResponse baseResponse = new BaseResponse();
                baseResponse.setRecode(BusinessMsg.SUBJECT_SUCCESS);
                baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.SUBJECT_SUCCESS));
                throw new BusinessException(baseResponse);
            }
            logger.info("【批量投标-异步】========单个投标开始，用户：" + prodInvestData.getPlatcust() + "，标的：" + productInfo.getProd_id()+"，订单号："+prodInvestData.getDetail_no());

            investOption(baseRequest,productInfo,prodInvestData);
            logger.info("【批量投标-异步】========单个投标成功，用户：" + prodInvestData.getPlatcust() + "，标的：" + productInfo.getProd_id()+"，订单号："+prodInvestData.getDetail_no());
        } catch (Exception e) {
            //数据库操作错误，记录流水，抛异常
            BaseResponse baseResponse = new BaseResponse();
            if (e instanceof BusinessException) {
                baseResponse = ((BusinessException) e).getBaseResponse();
                logger.info("【批量投标-异步】========单个投标失败" + baseResponse.getRemsg());
            } else {
                logger.error("【批量投标-异步】========数据库操作异常：" , e);
                baseResponse.setRecode(BusinessMsg.FAIL);
                baseResponse.setRemsg("investForNoSyn方法异常");
            }
            throw new BusinessException(baseResponse);
        }
        return true;
    }

    public boolean investOption(BaseRequest baseRequest, ProdProductinfo productInfo, ProdInvestData prodInvestData) {
        if (StringUtils.isBlank(prodInvestData.getLink_trans_serial())) {
            throw new BusinessException(BusinessMsg.METHODPARAMETER_ERROR, BusinessMsg.getMsg(BusinessMsg.METHODPARAMETER_ERROR));
        }
        String transTransNo = prodInvestData.getLink_trans_serial();
        //校验手续费参数
        Commission commission = prodInvestData.getCommissionObj();
        validate(prodInvestData);
        if (commission != null) {
            validate(commission);
        }
        //===========投标数据校验================
        checkInvestData(prodInvestData);

        String plat_no = productInfo.getPlat_no();

        //检查用户是否绑卡
        String flag = sysParameterService.querySysParameter(baseRequest.getMall_no(), SysParamterKey.BIND_CARD_SWITCH);
        if ("ON".equals(flag)) {
            EaccCardinfo eaccCardinfo = accountQueryService.getEaccCardInfo(baseRequest.getMall_no(), prodInvestData.getPlatcust());
            if (eaccCardinfo == null) {
                throw new BusinessException(BusinessMsg.UNBIND_CARD_ERROR, BusinessMsg.getMsg(BusinessMsg.UNBIND_CARD_ERROR));
            }
        }
        //获取标的账户
//        AccountSubjectInfo platAccount = null;
        //批量转账列表
        List<EaccAccountamtlist> batchEaccAccountamtList = new ArrayList<EaccAccountamtlist>();
        //合成标的转账数据
        EaccAccountamtlist prodAmt = new EaccAccountamtlist();
        prodAmt.setOrder_no(prodInvestData.getDetail_no());
        prodAmt.setPlat_no(plat_no);
        prodAmt.setPlatcust(prodInvestData.getPlatcust());
        prodAmt.setSub_subject(Ssubject.INVEST.getCode());
        prodAmt.setTrans_date(baseRequest.getPartner_trans_date());
        prodAmt.setTrans_time(baseRequest.getPartner_trans_time());
        if ("0".equals(prodInvestData.getSubject_priority())
                || Tsubject.CASH.getCode().equals(prodInvestData.getSubject_priority())) {
            //现金优先
//            platAccount = accountQueryService.queryPlatAccount(productInfo.getProd_id(), productInfo.getPlat_no(),
//                    Tsubject.CASH.getCode(), Ssubject.PROD.getCode());
            prodAmt.setSubject(Tsubject.CASH.getCode());
            prodAmt.setOppo_subject(Tsubject.CASH.getCode());
        } else if ("1".equals(prodInvestData.getSubject_priority())
                || Tsubject.FLOAT.getCode().equals(prodInvestData.getSubject_priority())) {
            //在途优先
//            platAccount = accountQueryService.queryPlatAccount(productInfo.getProd_id(), productInfo.getPlat_no(),
//                    Tsubject.FLOAT.getCode(), Ssubject.PROD.getCode());
            prodAmt.setSubject(Tsubject.FLOAT.getCode());
            prodAmt.setOppo_subject(Tsubject.FLOAT.getCode());
        }
        prodAmt.setOppo_platcust(productInfo.getProd_account());
        prodAmt.setOppo_sub_subject(Ssubject.PROD.getCode());
        prodAmt.setAmt(prodInvestData.getSelf_amt());
        prodAmt.setTrans_serial(transTransNo);
        prodAmt.setTrans_code(TransConsts.BUY_CODE);
        prodAmt.setTrans_name(TransConsts.BUY_NAME + "：购买人转账给标的");
        batchEaccAccountamtList.add(prodAmt);
        //标的转账金额=交易金额-体验金-抵用券-手续费=自费金额
        BigDecimal experience_amt = prodInvestData.getExperience_amt();
        BigDecimal coupon_amt = prodInvestData.getCoupon_amt();
        //如果有体验金金额，那么减去体验金金额
        if (null != experience_amt && experience_amt.compareTo(BigDecimal.ZERO) > 0) {
            //体验金转入标的账户
            EaccAccountamtlist experienceAmt = new EaccAccountamtlist();
            AccountSubjectInfo experiencePlatAccount = accountQueryService.queryAccount(productInfo.getPlat_no(),
                    productInfo.getPlat_no(), Tsubject.CASH.getCode(), Ssubject.EXPERIENCE.getCode());
            experienceAmt.setPlatcust(experiencePlatAccount.getPlatcust());
            experienceAmt.setOppo_platcust(productInfo.getProd_account());
            experienceAmt = EaccAccountamtlistOptionUtil.setSubject(experienceAmt, experiencePlatAccount.getSubject(),
                    experiencePlatAccount.getSub_subject(), prodAmt.getSubject(), prodAmt.getOppo_sub_subject(),
                    baseRequest.getPartner_trans_date(), baseRequest.getPartner_trans_time());
            experienceAmt.setPlat_no(plat_no);
            experienceAmt.setAmt(experience_amt);
            experienceAmt.setTrans_serial(transTransNo);
            experienceAmt.setTrans_code(TransConsts.BUY_CODE);
            experienceAmt.setTrans_name(TransConsts.BUY_NAME + "：体验金转账给标的");
            experienceAmt.setOrder_no(prodInvestData.getDetail_no());
            batchEaccAccountamtList.add(experienceAmt);
        }
        //如果有抵用券，那么减去抵用券金额
        if (null != coupon_amt && coupon_amt.compareTo(BigDecimal.ZERO) > 0) {
            //抵用券转入标的账户
            EaccAccountamtlist couponAmt = new EaccAccountamtlist();
            AccountSubjectInfo couponPlatAccount = accountQueryService.queryAccount(productInfo.getPlat_no(),
                    productInfo.getPlat_no(), Tsubject.CASH.getCode(), Ssubject.VOUCHER.getCode());
            couponAmt.setPlatcust(couponPlatAccount.getPlatcust());
            couponAmt.setOppo_platcust(productInfo.getProd_account());
            couponAmt = EaccAccountamtlistOptionUtil.setSubject(couponAmt, couponPlatAccount.getSubject(),
                    couponPlatAccount.getSub_subject(), prodAmt.getSubject(), prodAmt.getOppo_sub_subject(),
                    baseRequest.getPartner_trans_date(), baseRequest.getPartner_trans_time());
            couponAmt.setPlat_no(plat_no);
            couponAmt.setAmt(coupon_amt);
            couponAmt.setTrans_serial(transTransNo);
            couponAmt.setTrans_code(TransConsts.BUY_CODE);
            couponAmt.setTrans_name(TransConsts.BUY_NAME + "：抵用券转账给标的");
            couponAmt.setOrder_no(prodInvestData.getDetail_no());
            batchEaccAccountamtList.add(couponAmt);
        }
        if (commission != null) {
            //合成手续费转账数据
            EaccAccountamtlist payoutAmt = new EaccAccountamtlist();
            payoutAmt.setAmt(commission.getPayout_amt());
            payoutAmt.setPlat_no(plat_no);
            AccountSubjectInfo feePlatAccount = null;
            if (Tsubject.CASH.getCode().equals(commission.getPayout_plat_type())) {
                //可提优先（现金）
                feePlatAccount = accountQueryService.queryAccount(productInfo.getPlat_no(),
                        productInfo.getPlat_no(), Tsubject.CASH.getCode(), Ssubject.FEE.getCode());
                payoutAmt = EaccAccountamtlistOptionUtil.setSubject(payoutAmt, Tsubject.CASH.getCode(),
                        Ssubject.INVEST.getCode(), Tsubject.CASH.getCode(), Ssubject.FEE.getCode(),
                        baseRequest.getPartner_trans_date(), baseRequest.getPartner_trans_time());
            } else if (Tsubject.FLOAT.getCode().equals(commission.getPayout_plat_type())) {
                //可投优先
                feePlatAccount = accountQueryService.queryAccount(productInfo.getPlat_no(),
                        productInfo.getPlat_no(), Tsubject.FLOAT.getCode(), Ssubject.FEE.getCode());
                payoutAmt = EaccAccountamtlistOptionUtil.setSubject(payoutAmt, Tsubject.FLOAT.getCode(),
                        Ssubject.INVEST.getCode(), Tsubject.FLOAT.getCode(), Ssubject.FEE.getCode(),
                        baseRequest.getPartner_trans_date(), baseRequest.getPartner_trans_time());
            } else {
                //参数错误，抛异常，记录流水
//                transTransReq.setReturn_code(BusinessMsg.PARAMETER_ERROR);
//                transTransReq.setReturn_msg(BusinessMsg.getMsg(BusinessMsg.PARAMETER_ERROR));
//                transTransReq.setStatus(FlowStatus.FAIL.getCode());
//                transReqService.insert(transTransReq);
                BaseResponse baseResponse = new BaseResponse();
                baseResponse.setRecode(BusinessMsg.PARAMETER_ERROR);
                baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.PARAMETER_ERROR));
                throw new BusinessException(baseResponse);
            }
            payoutAmt.setPlatcust(prodInvestData.getPlatcust());
            payoutAmt.setOppo_platcust(feePlatAccount.getPlatcust());
            payoutAmt.setTrans_serial(transTransNo);
            payoutAmt.setTrans_code(TransConsts.BUY_CODE);
            payoutAmt.setTrans_name(TransConsts.BUY_NAME + "：购买人转账到手续费");
            payoutAmt.setOrder_no(prodInvestData.getDetail_no());
            batchEaccAccountamtList.add(payoutAmt);
        }

        //获取prodInfo
        BigDecimal realVol = null;
        if (commission != null) {
            realVol = prodInvestData.getTrans_amt().subtract(commission.getPayout_amt());
        } else {
            realVol = prodInvestData.getTrans_amt();
        }
        String lockKey = "Investment" + prodInvestData.getPlatcust();
        try {
            //getLockBySleep(lockKey, prodInvestData.getDetail_no());
            ProdShareList shareList = getInvestShareList(prodInvestData, baseRequest, productInfo.getProd_id(), transTransNo);
            String dataKey = Constants.PROD_INVEST_NOTIFY_DATA + prodInvestData.getDetail_no();
            baseRequest.setRemark(prodInvestData.getPlatcust()+"|"+prodInvestData.getTrans_amt().toString()+"|"+productInfo.getProd_id()+"|"+realVol.toString());
            CacheUtil.getCache().set(dataKey, JSON.toJSONString(baseRequest), 7 * 3600);
            prodTransferService.saveInvestAsyncData(baseRequest, shareList, batchEaccAccountamtList, productInfo, realVol);
        } catch (Exception e) {
            logger.error("【批量投标】========异常：", e);
            CacheUtil.unlock(lockKey);
            if (e instanceof BusinessException) {
                throw e;
            } else {
                throw new BusinessException(BusinessMsg.RUNTIME_EXCEPTION, "投资失败");
            }
        }
        return true;
    }

    private void getLockBySleep(String lockKey,String orderid) {
        Boolean lock= CacheUtil.getLock(lockKey);
        logger.info("orderid="+orderid+"========lockKey:"+lockKey+",lock:"+lock);
        int count=0;
        while (!lock) {
            try {
                Thread.sleep(100);
                if (count++>10*60){
                    logger.info("orderid="+orderid+"========lockKey:"+lockKey+"线程休眠异常");
                    throw new BusinessException(BusinessMsg.RUNTIME_EXCEPTION,BusinessMsg.getMsg(BusinessMsg.RUNTIME_EXCEPTION)+"：线程休眠异常");
                }
            }catch (InterruptedException e){
                throw new BusinessException(BusinessMsg.RUNTIME_EXCEPTION,BusinessMsg.getMsg(BusinessMsg.RUNTIME_EXCEPTION)+"：线程休眠异常");
            }

            lock = CacheUtil.getLock(lockKey);
        }
    }

    public boolean prodIdRefundOption(BaseRequest baseRequest, ProdProductinfo productInfo, BatchCustRepay batchCustRepay, TransTransreq transTransReq){
        //执行还款
        List<EaccAccountamtlist> batchEaccAccountamtList=new ArrayList<EaccAccountamtlist>();
        boolean eplatcustexist = false;
        String platcust=null;
        ProdShareInall queryProdShareInall =null;

        EaccUserinfo eaccUserinfo=accountQueryService.getEUserinfoByExist(baseRequest.getMall_no(),batchCustRepay.getCust_no());
        if(eaccUserinfo==null){
            logger.info("用户信息不存在"+batchCustRepay.getCust_no());
            throw new BusinessException(BusinessMsg.INVALID_ACCOUNT,BusinessMsg.getMsg(BusinessMsg.INVALID_ACCOUNT));
        }
        if (!StringUtils.isEmpty(eaccUserinfo.getEaccount())){
            logger.info("-------------该用户电子账户存在---------------");
            eplatcustexist = true;
        }

        BigDecimal rates_amt_ext = BigDecimal.ZERO;
        BigDecimal experience_amt_ext = BigDecimal.ZERO;
        if(batchCustRepay.getRates_amt()!=null){
            rates_amt_ext = rates_amt_ext.add(batchCustRepay.getRates_amt());
        }
        if(batchCustRepay.getExperience_amt()!=null){
            experience_amt_ext = experience_amt_ext.add(batchCustRepay.getExperience_amt());
        }
        if(batchCustRepay.getReal_repay_amt().compareTo(rates_amt_ext.add(experience_amt_ext).add(batchCustRepay.getReal_repay_amount()).add(batchCustRepay.getReal_repay_val()))!=0){
            BaseResponse baseResponse=new BaseResponse();
            baseResponse.setRecode(BusinessMsg.MONEY_ERROR);
            baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.MONEY_ERROR) + "---------------实际还款金额  不等于（实际还款本金+体验金+加息金+利息+手续费）");
            throw new BusinessException(baseResponse);
        }

        logger.info("-------------体验金:" + batchCustRepay.getExperience_amt() + "---------------");
        //体验金
        BigDecimal experience_amt = batchCustRepay.getExperience_amt();
        //如果有体验金金额，那么减去体验金金额
        if (null != experience_amt && experience_amt.compareTo(BigDecimal.ZERO) > 0) {
            AccountSubjectInfo experiencePlatAccount = accountQueryService.queryAccount(productInfo.getPlat_no(),
                    productInfo.getPlat_no(), Tsubject.CASH.getCode(), Ssubject.EXPERIENCE.getCode());
            //体验金转账到平台体验金账户
            EaccAccountamtlist experienceAmt = new EaccAccountamtlist();
            experienceAmt.setTrans_serial(transTransReq.getTrans_serial());
            experienceAmt.setPlat_no(productInfo.getPlat_no());
            experienceAmt.setPlatcust(productInfo.getProd_account());
            experienceAmt.setSubject(Tsubject.CASH.getCode());
            experienceAmt.setSub_subject(Ssubject.PROD.getCode());
            experienceAmt.setOppo_plat_no(productInfo.getPlat_no());
            experienceAmt.setOppo_platcust(experiencePlatAccount.getPlatcust());
            experienceAmt.setOppo_subject(experiencePlatAccount.getSubject());
            experienceAmt.setOppo_sub_subject(experiencePlatAccount.getSub_subject());
            experienceAmt.setAmt(experience_amt);
            experienceAmt.setTrans_code(transTransReq.getTrans_code());
            experienceAmt.setTrans_name(transTransReq.getTrans_name());
            experienceAmt.setTrans_date(transTransReq.getTrans_date());
            experienceAmt.setTrans_time(transTransReq.getTrans_time());
//            experienceAmt.setRemark("OnlyVirtual");
            batchEaccAccountamtList.add(experienceAmt);
        }

        logger.info("-------------加息金:" + batchCustRepay.getRates_amt() + "---------------");
        //加息金
        BigDecimal rates_amt = batchCustRepay.getRates_amt();
        //如果有加息金金额
        if (null != rates_amt && rates_amt.compareTo(BigDecimal.ZERO) > 0) {
            logger.info("-------------加息金:" + batchCustRepay.getRates_amt() + "----开始转账-----------");
            AccountSubjectInfo ratesAmtAccount = accountQueryService.queryAccount(productInfo.getPlat_no(),
                    null, Tsubject.CASH.getCode(), Ssubject.PLUSRATE.getCode());
            //平台加息金账户转到投资人账户
            logger.info("-------------平台加息金账户转到投资人账户---------------");
            EaccAccountamtlist ratesAmt = new EaccAccountamtlist();
            ratesAmt.setTrans_serial(transTransReq.getTrans_serial());
            ratesAmt.setPlat_no(productInfo.getPlat_no());
            ratesAmt.setPlatcust(ratesAmtAccount.getPlatcust());
            ratesAmt.setSubject(Tsubject.CASH.getCode());
            ratesAmt.setSub_subject(Ssubject.PLUSRATE.getCode());
            ratesAmt.setOppo_plat_no(productInfo.getPlat_no());
            ratesAmt.setOppo_platcust(batchCustRepay.getCust_no());
            ratesAmt.setOppo_subject(Tsubject.CASH.getCode());
            if (eplatcustexist) {
                ratesAmt.setOppo_sub_subject(Ssubject.EACCOUNT.getCode());
            } else {
                ratesAmt.setOppo_sub_subject(Ssubject.INVEST.getCode());
            }
            ratesAmt.setAmt(rates_amt);
            ratesAmt.setTrans_code(transTransReq.getTrans_code());
            ratesAmt.setTrans_name(transTransReq.getTrans_name());
            ratesAmt.setTrans_date(transTransReq.getTrans_date());
            ratesAmt.setTrans_time(transTransReq.getTrans_time());
//            ratesAmt.setRemark("OnlyVirtual");
            batchEaccAccountamtList.add(ratesAmt);
        }

        logger.info("-------------还款本金:" + batchCustRepay.getReal_repay_amount() + "---------------");
        //还款本金
        BigDecimal real_repay_amount = batchCustRepay.getReal_repay_amount();
//			BigDecimal real_repay_amount=custRepay.getReal_repay_amount();
        BigDecimal cust_repay_amount = BigDecimal.ZERO;
        //如果有本金金额
        if (null != real_repay_amount && real_repay_amount.compareTo(BigDecimal.ZERO) > 0) {
            //cust_repay_amount.add(real_repay_amount);
//				cust_repay_amount = new BigDecimal(real_repay_amount.intValue());
            cust_repay_amount = cust_repay_amount.add(real_repay_amount);
            logger.info("-------------计划还款本金a:" + batchCustRepay.getReal_repay_amount() + "---------------");
        }

        //还款利息
        BigDecimal real_repay_val = BigDecimal.ZERO;
        if (batchCustRepay.getReal_repay_val() != null) {
            real_repay_val = real_repay_val.add(batchCustRepay.getReal_repay_val());
        }
        //如果有利息
        if (null != real_repay_val && real_repay_val.compareTo(BigDecimal.ZERO) > 0) {
            cust_repay_amount = cust_repay_amount.add(real_repay_val);
        }

        logger.info("-------------还款实际金额:" + cust_repay_amount + "---------------");
        //还款实际金额
        if (cust_repay_amount.compareTo(BigDecimal.ZERO) > 0) {
            logger.info("-------------还款实际金额:" + cust_repay_amount + "-------开始转账--------");
            EaccAccountamtlist custRepayAmt = new EaccAccountamtlist();
            custRepayAmt.setTrans_serial(transTransReq.getTrans_serial());
            custRepayAmt.setPlat_no(productInfo.getPlat_no());
            custRepayAmt.setPlatcust(productInfo.getProd_account());
            custRepayAmt.setSubject(Tsubject.CASH.getCode());
            custRepayAmt.setSub_subject(Ssubject.PROD.getCode());
            custRepayAmt.setOppo_plat_no(productInfo.getPlat_no());
            custRepayAmt.setOppo_platcust(batchCustRepay.getCust_no());
            custRepayAmt.setOppo_subject(Tsubject.CASH.getCode());
            if (eplatcustexist) {
                custRepayAmt.setOppo_sub_subject(Ssubject.EACCOUNT.getCode());
            } else {
                custRepayAmt.setOppo_sub_subject(Ssubject.INVEST.getCode());
            }
            custRepayAmt.setAmt(cust_repay_amount);
            custRepayAmt.setTrans_code(transTransReq.getTrans_code());
            custRepayAmt.setTrans_name(transTransReq.getTrans_name());
            custRepayAmt.setTrans_date(transTransReq.getTrans_date());
            custRepayAmt.setTrans_time(transTransReq.getTrans_time());
//            custRepayAmt.setRemark("OnlyVirtual");
            batchEaccAccountamtList.add(custRepayAmt);
        }

        //更新投资人份额表
        logger.info("-------------更新投资人份额表---------------");
        if(ProdType.PERIOD.getCode().equals(productInfo.getProd_type())){
            queryProdShareInall = prodSearchService.queryProdShareInall(productInfo.getPlat_no(), productInfo.getProd_id(), batchCustRepay.getCust_no());
            if (queryProdShareInall == null) {
                BaseResponse baseResponse = new BaseResponse();
                baseResponse.setRecode(BusinessMsg.INVALID_ACCOUNT);
                baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.INVALID_ACCOUNT) + ":该用户未投标，找不到投标份额。");
                throw new BusinessException(baseResponse);

            }
        }

        if (real_repay_amount == null) {
            real_repay_amount = BigDecimal.ZERO;
        }

        //更新产品购买明细表
        List<ProdShareList> selectMore = prodShareOptionService.queryProdShareList(productInfo.getPlat_no(), productInfo.getProd_id(), batchCustRepay.getCust_no());
        if(selectMore==null){
            logger.info("【标的还款】========未找到投资信息");
            throw new BusinessException(BusinessMsg.PARAMETER_ERROR,
                    BusinessMsg.getMsg(BusinessMsg.PARAMETER_ERROR)+"：platcust:"+batchCustRepay.getCust_no()+"，prod_id:"+productInfo.getProd_id()+"的投标信息不存在");
        }
        ProdShareList selectOne=selectMore.get(0);
        selectOne.setId(null);
        selectOne.setVol(real_repay_amount);
        selectOne.setAmt_type(AmtType.EXPENSIVE.getCode());
        selectOne.setCreate_by(SeqUtil.RANDOM_KEY);
        selectOne.setCreate_time(DateUtils.today());
        selectOne.setTrans_serial(transTransReq.getTrans_serial());
        selectOne.setTrans_date(transTransReq.getTrans_date());
        selectOne.setTrans_time(transTransReq.getTrans_time());
        selectOne.setTrans_code(transTransReq.getTrans_code());

        //新增标的还款信息
        logger.info("【标的还款】========新增标的还款信息");
        ProdRepaymentlist repaymentlist = new ProdRepaymentlist();
        repaymentlist.setPlat_no(productInfo.getPlat_no());
        repaymentlist.setPlatcust(batchCustRepay.getCust_no());
        repaymentlist.setRepay_num(batchCustRepay.getRepay_num());
        repaymentlist.setProd_id(productInfo.getProd_id());
        repaymentlist.setReal_repay_amount(batchCustRepay.getReal_repay_amount());
        repaymentlist.setReal_repay_val(batchCustRepay.getReal_repay_val());
        repaymentlist.setReal_repay_amt(batchCustRepay.getReal_repay_amt());
        repaymentlist.setRepay_date(batchCustRepay.getRepay_date());
        repaymentlist.setReal_repay_date(batchCustRepay.getReal_repay_date());
        repaymentlist.setStatus(RepaymentStatus.SUCCPAY.getCode());
        repaymentlist.setIf_advance(AdvanceType.NOADVANCE.getCode());
        repaymentlist.setEnabled(Constants.ENABLED);
        repaymentlist.setReal_experience_amt(batchCustRepay.getExperience_amt());
        repaymentlist.setReal_rates_amt(batchCustRepay.getRates_amt());
        //只有定期标才更新份额表
        if(ProdType.PERIOD.getCode().equals(productInfo.getProd_type())){
            logger.info("【标的还款】========更新投资人份额表");
//            queryProdShareInall.setTot_vol(queryProdShareInall.getTot_vol().subtract(real_repay_amount));
//            queryProdShareInall.setVol(queryProdShareInall.getVol().subtract(real_repay_amount));
//            prodShareInallMapper.updateByPrimaryKeySelective(queryProdShareInall);
            Map<String,Object> params=new HashMap<>();
            params.put("vol",real_repay_amount);
            params.put("prod_id",productInfo.getProd_id());
            params.put("platcust",batchCustRepay.getCust_no());
            params.put("plat_no",productInfo.getPlat_no());
            params.put("update_by",SeqUtil.RANDOM_KEY);
            params.put("update_time",new Date());
            int updateCounts=custProdShareInallMapper.subtractInallVol(params);
            if(updateCounts<=0){
                //份额不足
                logger.info("【标的还款】========剩余待还款份额不足");
                throw new BusinessException(BusinessMsg.REPAYMENT_FAILED,BusinessMsg.getMsg(BusinessMsg.REPAYMENT_FAILED)+"，剩余待还款份额不足");
            }else if(updateCounts>1){
                //数据异常
                logger.info("【标的还款】========有重复份额数据，数据异常");
                throw new BusinessException(BusinessMsg.REPAYMENT_FAILED,BusinessMsg.getMsg(BusinessMsg.REPAYMENT_FAILED)+"，数据异常");
            }
        }
        logger.info("【标的还款】========新增产品购买明细表");
        prodShareListMapper.insert(selectOne);
        logger.info("【标的还款】========新增标的还款信息");
        repayMentListMapper.insert(repaymentlist);

        logger.info("【标的还款】========开始转账" );
        try{
            accountTransferService.batchTransfer(batchEaccAccountamtList);
        }catch (BusinessException e){
            logger.error("【标的还款】========转账失败："+e.getErrorMsg());
            BaseResponse baseResponse=e.getBaseResponse();
            CacheUtil.getCache().set("prod_repay_key_"+transTransReq.getTrans_serial(),"1");
            throw new BusinessException(baseResponse);

        }
        logger.info("【标的还款】========还款成功");
        return true;
    }

    public boolean investOption(BaseRequest baseRequest, ProdProductinfo productInfo, ProdInvestDataTb prodInvestData){
        if (StringUtils.isBlank( prodInvestData.getLink_trans_serial())){
            throw new BusinessException(BusinessMsg.METHODPARAMETER_ERROR,BusinessMsg.getMsg(BusinessMsg.METHODPARAMETER_ERROR));
        }
        String transSerialNo=prodInvestData.getLink_trans_serial();
        //===========投标数据校验================
        checkInvestData(prodInvestData);

        String plat_no=productInfo.getPlat_no();

        //检查用户是否绑卡
        String flag=sysParameterService.querySysParameter(baseRequest.getMall_no(),SysParamterKey.BIND_CARD_SWITCH);
        if("ON".equals(flag)){
            EaccCardinfo eaccCardinfo=accountQueryService.getEaccCardInfo(baseRequest.getMall_no(),prodInvestData.getPlatcust());
            if(eaccCardinfo==null){
                throw new BusinessException(BusinessMsg.UNBIND_CARD_ERROR,BusinessMsg.getMsg(BusinessMsg.UNBIND_CARD_ERROR));
            }
        }
        //批量转账列表
        List<EaccAccountamtlist> batchEaccAccountamtList = new ArrayList<EaccAccountamtlist>();
        //合成标的转账数据
        EaccAccountamtlist prodAmt = new EaccAccountamtlist();
        prodAmt.setOrder_no(prodInvestData.getDetail_no());
        prodAmt.setPlat_no(plat_no);
        prodAmt.setPlatcust(prodInvestData.getPlatcust());
        prodAmt.setSub_subject(Ssubject.INVEST.getCode());
        prodAmt.setTrans_date(baseRequest.getPartner_trans_date());
        prodAmt.setTrans_time(baseRequest.getPartner_trans_time());
        if ("0".equals(prodInvestData.getSubject_priority())
                || Tsubject.CASH.getCode().equals(prodInvestData.getSubject_priority())) {
            //现金优先
            prodAmt.setSubject(Tsubject.CASH.getCode());
            prodAmt.setOppo_subject(Tsubject.CASH.getCode());
        } else if("1".equals(prodInvestData.getSubject_priority())
                || Tsubject.FLOAT.getCode().equals(prodInvestData.getSubject_priority())) {
            //在途优先
            prodAmt.setSubject(Tsubject.FLOAT.getCode());
            prodAmt.setOppo_subject(Tsubject.FLOAT.getCode());
        }
        prodAmt.setOppo_platcust(productInfo.getProd_account());
        prodAmt.setOppo_sub_subject(Ssubject.PROD.getCode());
        prodAmt.setAmt(prodInvestData.getSelf_amt());
        prodAmt.setTrans_serial(transSerialNo);
        prodAmt.setTrans_code(TransConsts.BUY_CODE);
        prodAmt.setTrans_name(TransConsts.BUY_NAME + "：购买人转账给标的");
        batchEaccAccountamtList.add(prodAmt);
        //标的转账金额=交易金额-体验金-抵用券-手续费=自费金额
        BigDecimal experience_amt = prodInvestData.getExperience_amt();
        BigDecimal coupon_amt = prodInvestData.getCoupon_amt();
        //如果有体验金金额，那么减去体验金金额
        if (null != experience_amt && experience_amt.compareTo(BigDecimal.ZERO) > 0) {
            //体验金转入标的账户
            EaccAccountamtlist experienceAmt = new EaccAccountamtlist();
            AccountSubjectInfo experiencePlatAccount = accountQueryService.queryAccount(productInfo.getPlat_no(),
                    productInfo.getPlat_no(), Tsubject.CASH.getCode(), Ssubject.EXPERIENCE.getCode());
            experienceAmt.setPlatcust(experiencePlatAccount.getPlatcust());
            experienceAmt.setOppo_platcust(productInfo.getProd_account());
            experienceAmt = EaccAccountamtlistOptionUtil.setSubject(experienceAmt, experiencePlatAccount.getSubject(),
                    experiencePlatAccount.getSub_subject(), prodAmt.getSubject(), prodAmt.getOppo_sub_subject(),
                    baseRequest.getPartner_trans_date(),baseRequest.getPartner_trans_time());
            experienceAmt.setPlat_no(plat_no);
            experienceAmt.setAmt(experience_amt);
            experienceAmt.setTrans_serial(transSerialNo);
            experienceAmt.setTrans_code(TransConsts.BUY_CODE);
            experienceAmt.setTrans_name(TransConsts.BUY_NAME + "：体验金转账给标的");
            experienceAmt.setOrder_no(prodInvestData.getDetail_no());
            batchEaccAccountamtList.add(experienceAmt);
        }
        //如果有抵用券，那么减去抵用券金额
        if (null != coupon_amt && coupon_amt.compareTo(BigDecimal.ZERO) > 0) {
            //抵用券转入标的账户
            EaccAccountamtlist couponAmt = new EaccAccountamtlist();
            AccountSubjectInfo couponPlatAccount = accountQueryService.queryAccount(productInfo.getPlat_no(),
                    productInfo.getPlat_no(), Tsubject.CASH.getCode(), Ssubject.VOUCHER.getCode());
            couponAmt.setPlatcust(couponPlatAccount.getPlatcust());
            couponAmt.setOppo_platcust(productInfo.getProd_account());
            couponAmt = EaccAccountamtlistOptionUtil.setSubject(couponAmt, couponPlatAccount.getSubject(),
                    couponPlatAccount.getSub_subject(), prodAmt.getSubject(), prodAmt.getOppo_sub_subject(),
                    baseRequest.getPartner_trans_date(),baseRequest.getPartner_trans_time());
            couponAmt.setPlat_no(plat_no);
            couponAmt.setAmt(coupon_amt);
            couponAmt.setTrans_serial(transSerialNo);
            couponAmt.setTrans_code(TransConsts.BUY_CODE);
            couponAmt.setTrans_name(TransConsts.BUY_NAME + "：抵用券转账给标的");
            couponAmt.setOrder_no(prodInvestData.getDetail_no());
            batchEaccAccountamtList.add(couponAmt);
        }

        //获取prodInfo
        BigDecimal realVol =  prodInvestData.getTrans_amt();
        //如果额度充足
        //更新标的剩余额度
        logger.info("【批量投标】========单个投标更新剩余额度");
        Map<String,Object> params=new HashMap<>();
        params.put("prod_id",productInfo.getProd_id());
        params.put("plat_no",productInfo.getPlat_no());
        params.put("vol",realVol);
        params.put("update_by",SeqUtil.RANDOM_KEY);
        params.put("update_time",new Date());
        logger.info("更新标的剩余份额：",baseRequest.getOrder_no());
        logger.info("更新开始时间："+new Date()+"，订单号："+baseRequest.getOrder_no());
        custProdProdInfoMapper.subtractProdLimit(params);
        logger.info("更新结束时间："+new Date()+"，订单号："+baseRequest.getOrder_no());
        //新增购买明细
        ProdShareList shareList = getProdShareList(prodInvestData, baseRequest, productInfo.getProd_id(), transSerialNo);
        shareList.setAmt_type(AmtType.INCOME.getCode());
        shareList.setCreate_by(SeqUtil.RANDOM_KEY);
        shareList.setCreate_time(DateUtils.today());
        logger.info("【批量投标】========单个投标插入购买明细");
        prodShareListMapper.insert(shareList);

        //批量转账
        logger.info("【批量投标】========开始转账");
        try {
            logger.info("转账开始时间："+new Date()+"，订单号："+baseRequest.getOrder_no());
            accountTransferService.batchTransfer(batchEaccAccountamtList);
            logger.info("转账结束时间："+new Date()+"，订单号："+baseRequest.getOrder_no());
        }catch (BusinessException e){
            logger.error("【批量投标】========转账失败："+e.getErrorMsg());
            BaseResponse baseResponse=e.getBaseResponse();
            throw new BusinessException(baseResponse);
        }

        try{
            logger.info("【批量投标】========发送通知短信");
            EaccUserinfo eaccUserinfo=getEaccUserInfo(baseRequest.getMall_no(),prodInvestData.getPlatcust());
            if(eaccUserinfo!=null){
                MsgModel msgModel=new MsgModel();
                String mall_name=sysParameterService.querySysParameter(baseRequest.getMall_no(),
                        MsgContent.MALL_NAME_KEY.getMsg());
                BigDecimal allAmt=sendMsgService.getAccountAllAmount(prodInvestData.getPlatcust());
                msgModel.setOrder_no(baseRequest.getOrder_no());
                msgModel.setPlat_no(plat_no);
                msgModel.setTrans_code(TransConsts.BUY_CODE);
                msgModel.setMall_no(eaccUserinfo.getMall_no());
                msgModel.setPlatcust(eaccUserinfo.getMallcust());
                String content=sysParameterService.querySysParameter(baseRequest.getMall_no(),MsgContent.INVEST_SUCCESS_NOTIFY.getMsg());
                if(StringUtils.isNotBlank(content)){
                    msgModel.setMsgContent(String.format(content,
                            mall_name, NumberUtils.formatNumber(prodInvestData.getTrans_amt()),NumberUtils.formatNumber(allAmt)));
                    //=========ccb参数===========
                    msgModel.setTrans_serial(transSerialNo);
                    msgModel.setMsg_type(MsgType.INVEST_SUCCESS_NOTIFY.getType());
                    msgModel.setAmount(prodInvestData.getTrans_amt());
                    //===========================
                    sendMsgService.addMsgToQueue(msgModel);
                }
            }
        }catch (Exception e){
            logger.info("【短信发送异常】========"+e.getCause());
        }

        return true;
    }

    @Override
//    @Transactional
    public boolean transProd(ProdTransferDebtRequestBo prodTransferDebtRequestBo, TransTransreq transTransReq) throws BusinessException {
        boolean updateTransreqFlag=false;
        if(transTransReq==null){
            transTransReq = transReqService.queryTransTransReqByOrderno(prodTransferDebtRequestBo.getDetail_no());
            updateTransreqFlag=true;
        }
        try {
            if(updateTransreqFlag && StringUtils.isBlank(prodTransferDebtRequestBo.getOrigin_order_no())
                    && !Constants.OLD_INTERFACE.equals(prodTransferDebtRequestBo.getInterface_version())){
                //检查是否授权
                Boolean authStatus=authCheckService.checkAuth(prodTransferDebtRequestBo.getMer_no(),prodTransferDebtRequestBo.getMall_no(),
                        prodTransferDebtRequestBo.getDeal_platcust(), AuthType.AUTH_INVEST.getCode(),prodTransferDebtRequestBo.getTrans_amt());
                if(!authStatus){
                    logger.info(String.format("【标的转让】授权信息校验失败|platcust:%s|交易金额：%s",
                            prodTransferDebtRequestBo.getDeal_platcust(),prodTransferDebtRequestBo.getTrans_amt()));
                    throw new BusinessException(BusinessMsg.AUTH_CHECK_FAIL,BusinessMsg.getMsg(BusinessMsg.AUTH_CHECK_FAIL));
                }
            }
            logger.info("【标的转让】========开始单个转让"+"，订单号："+prodTransferDebtRequestBo.getDetail_no());
            //检查用户是否绑卡
            String flag=sysParameterService.querySysParameter(prodTransferDebtRequestBo.getMall_no(),SysParamterKey.BIND_CARD_SWITCH);
            if("ON".equals(flag)){
                EaccCardinfo eaccCardinfo=accountQueryService.getEaccCardInfo(prodTransferDebtRequestBo.getMall_no(),prodTransferDebtRequestBo.getDeal_platcust());
                if(eaccCardinfo==null){
                    throw new BusinessException(BusinessMsg.UNBIND_CARD_ERROR,"受让人"+BusinessMsg.getMsg(BusinessMsg.UNBIND_CARD_ERROR));
                }
            }
            //检查转让人账户是否存在
            List<AccountSubjectInfo> outAccount = accountQueryService.queryAccountlist(prodTransferDebtRequestBo.getMer_no(),
                    prodTransferDebtRequestBo.getPlatcust());
            if (outAccount.size() < 4) {
                BaseResponse baseResponse = new BaseResponse();
                baseResponse.setRecode(BusinessMsg.INVALID_ACCOUNT);
                baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.INVALID_ACCOUNT) + "：转让人账号异常或账号不存在");
                throw new BusinessException(baseResponse);
            }
            //检查受让人账户是否存在
            List<AccountSubjectInfo> inAccount = accountQueryService.queryAccountlist(prodTransferDebtRequestBo.getMer_no(),
                    prodTransferDebtRequestBo.getDeal_platcust());
            if (inAccount.size() < 4) {
                logger.error("【标的转让】========受让人账号异常或账号不存在，受让人：" + prodTransferDebtRequestBo.getDeal_platcust());
                BaseResponse baseResponse = new BaseResponse();
                baseResponse.setRecode(BusinessMsg.INVALID_ACCOUNT);
                baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.INVALID_ACCOUNT) + "：受让人账号异常或账号不存在");
                throw new BusinessException(baseResponse);
            }
            //检查标的是否成标和转让人份额是否足够
            logger.error("【标的转让】========检查标的是否成标和转让人份额是否足够,标的：" +
                    prodTransferDebtRequestBo.getProd_id() + "，转让人：" + prodTransferDebtRequestBo.getPlatcust());
            prodSearchService.checkProdInfoIsFound(prodTransferDebtRequestBo.getMer_no(), prodTransferDebtRequestBo.getProd_id());
            //TODO 检查份额是否充足
            prodSearchService.checkProdShareInallVol(prodTransferDebtRequestBo.getMer_no(),
                    prodTransferDebtRequestBo.getProd_id(), prodTransferDebtRequestBo.getPlatcust(),
                    prodTransferDebtRequestBo.getTrans_share());
            //转账
            EaccAccountamtlist expenseAccount = new EaccAccountamtlist();
            expenseAccount.setPlat_no(prodTransferDebtRequestBo.getMer_no());
            expenseAccount.setPlatcust(prodTransferDebtRequestBo.getDeal_platcust());
            expenseAccount.setOppo_platcust(prodTransferDebtRequestBo.getPlatcust());
            expenseAccount.setTrans_serial(transTransReq.getTrans_serial());
            expenseAccount.setTrans_date(prodTransferDebtRequestBo.getPartner_trans_date());
            expenseAccount.setTrans_time(prodTransferDebtRequestBo.getPartner_trans_time());
            expenseAccount.setTrans_code(TransConsts.TRANSFERDEBT_CODE);
            expenseAccount.setTrans_name(TransConsts.TRANSFERDEBT_NAME + "：受让人转账给转让人");
            expenseAccount.setOrder_no(prodTransferDebtRequestBo.getDetail_no());
            if ("0".equals(prodTransferDebtRequestBo.getSubject_priority())) {
                //现金优先
                expenseAccount = EaccAccountamtlistOptionUtil.setSubject(expenseAccount, Tsubject.CASH.getCode(),
                        Ssubject.INVEST.getCode(), Tsubject.CASH.getCode(), Ssubject.INVEST.getCode(),
                        prodTransferDebtRequestBo.getPartner_trans_date(),prodTransferDebtRequestBo.getPartner_trans_time());
            } else {
                //在途优先
                expenseAccount = EaccAccountamtlistOptionUtil.setSubject(expenseAccount, Tsubject.FLOAT.getCode(),
                        Ssubject.INVEST.getCode(), Tsubject.FLOAT.getCode(), Ssubject.INVEST.getCode(),
                        prodTransferDebtRequestBo.getPartner_trans_date(),prodTransferDebtRequestBo.getPartner_trans_time());
            }
            expenseAccount.setAmt(prodTransferDebtRequestBo.getDeal_amount());
            List<EaccAccountamtlist> eaccAccountamtlist = new ArrayList<>();
            eaccAccountamtlist.add(expenseAccount);

            //获取收益出资平台
            AccountSubjectInfo platAccount = null;
            //转让收益收取
            if (prodTransferDebtRequestBo.getTransfer_income().compareTo(BigDecimal.ZERO) > 0) {
                logger.info("【标的转让】========收取转让收益");
                EaccAccountamtlist earningsAccount=new EaccAccountamtlist();
                earningsAccount.setTrans_serial(transTransReq.getTrans_serial());
                earningsAccount.setPlat_no(prodTransferDebtRequestBo.getMer_no());
                earningsAccount.setOppo_platcust(prodTransferDebtRequestBo.getPlatcust());
                earningsAccount.setTrans_code(TransConsts.TRANSFERDEBT_CODE);
                //设置转账金额，转账
                if ("0".equals(prodTransferDebtRequestBo.getSubject_priority())
                        || Tsubject.CASH.getCode().equals(prodTransferDebtRequestBo.getSubject_priority())) {
                    //现金优先
                    platAccount = accountQueryService.queryAccount(prodTransferDebtRequestBo.getMer_no(),
                            prodTransferDebtRequestBo.getMer_no(), Tsubject.CASH.getCode(), Ssubject.PLAT.getCode());
                    earningsAccount = EaccAccountamtlistOptionUtil.setSubject(earningsAccount, Tsubject.CASH.getCode(),
                            Ssubject.PLAT.getCode(), Tsubject.CASH.getCode(), Ssubject.INVEST.getCode(),
                            prodTransferDebtRequestBo.getPartner_trans_date(),prodTransferDebtRequestBo.getPartner_trans_time());
                } else if("1".equals(prodTransferDebtRequestBo.getSubject_priority())
                        || Tsubject.FLOAT.getCode().equals(prodTransferDebtRequestBo.getSubject_priority())) {
                    //在途优先
                    platAccount = accountQueryService.queryAccount(prodTransferDebtRequestBo.getMer_no(),
                            prodTransferDebtRequestBo.getMer_no(), Tsubject.FLOAT.getCode(), Ssubject.PLAT.getCode());
                    earningsAccount = EaccAccountamtlistOptionUtil.setSubject(earningsAccount, Tsubject.FLOAT.getCode(),
                            Ssubject.PLAT.getCode(), Tsubject.FLOAT.getCode(), Ssubject.INVEST.getCode(),
                            prodTransferDebtRequestBo.getPartner_trans_date(),prodTransferDebtRequestBo.getPartner_trans_time());
                }
                //判断收益出资账户
                if (prodTransferDebtRequestBo.getIncome_acct().equals(PlatAccType.PLATTRUST.getCode())) {
                    //平台出资
                    //调用转账接口，从平台转入转让人账户
                    if (platAccount != null) {
                        earningsAccount.setPlatcust(platAccount.getPlatcust());
                    } else {
                        logger.error("【标的转让】========平台收益账号不存在，平台：" + prodTransferDebtRequestBo.getMer_no());
                        BaseResponse baseResponse = new BaseResponse();
                        baseResponse.setRecode(BusinessMsg.INVALID_ACCOUNT);
                        baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.INVALID_ACCOUNT) + "：平台收益账号不存在");
                        throw new BusinessException(baseResponse);
                    }
                } else {
                    //个人出资
                    earningsAccount.setPlatcust(prodTransferDebtRequestBo.getIncome_acct());
                    earningsAccount.setSub_subject(Ssubject.INVEST.getCode());
                }
                earningsAccount.setAmt(prodTransferDebtRequestBo.getTransfer_income());
                earningsAccount.setTrans_name(TransConsts.TRANSFERDEBT_NAME + "：收益出资方转账给转让人");
                earningsAccount.setOrder_no(prodTransferDebtRequestBo.getDetail_no());
                eaccAccountamtlist.add(earningsAccount);
            }

            //使用抵用券
            if(null!=prodTransferDebtRequestBo.getCoupon_amt() &&
                    prodTransferDebtRequestBo.getCoupon_amt().compareTo(BigDecimal.ZERO)>0){
                logger.info("【标的转让】========使用抵用券");
                AccountSubjectInfo couponPlatAccount=accountQueryService.queryAccount(
                        prodTransferDebtRequestBo.getMer_no(),prodTransferDebtRequestBo.getMer_no(),Tsubject.CASH.getCode(),Ssubject.VOUCHER.getCode());
                if(couponPlatAccount==null){
                    throw new BusinessException(BusinessMsg.INVALID_ACCOUNT,BusinessMsg.getMsg(BusinessMsg.INVALID_ACCOUNT)+"：抵用券子账户不存在");
                }
                //抵用券金额从抵用券子账户转入转让人账户
                EaccAccountamtlist couponAccount=new EaccAccountamtlist();
                couponAccount.setTrans_serial(transTransReq.getTrans_serial());
                couponAccount.setPlat_no(prodTransferDebtRequestBo.getMer_no());
                couponAccount.setTrans_code(TransConsts.TRANSFERDEBT_CODE);
                couponAccount.setAmt(prodTransferDebtRequestBo.getCoupon_amt());
                couponAccount.setPlatcust(couponPlatAccount.getPlatcust());
                couponAccount.setOppo_platcust(prodTransferDebtRequestBo.getPlatcust());
                couponAccount=EaccAccountamtlistOptionUtil.setSubject(couponAccount,couponPlatAccount.getSubject(),
                        couponPlatAccount.getSub_subject(),Tsubject.CASH.getCode(),Ssubject.INVEST.getCode(),
                        prodTransferDebtRequestBo.getPartner_trans_date(),prodTransferDebtRequestBo.getPartner_trans_time());
                couponAccount.setTrans_name(TransConsts.TRANSFERDEBT_NAME + "：抵用券金额从抵用券子账户转入转让人账户");
                couponAccount.setOrder_no(prodTransferDebtRequestBo.getDetail_no());
                eaccAccountamtlist.add(couponAccount);
            }

            //收取手续费
            if (null != prodTransferDebtRequestBo.getCommissionObject() || null != prodTransferDebtRequestBo.getCommission_extObject()) {
                AccountSubjectInfo feePlatAccount = null;
                EaccAccountamtlist feeAccount=new EaccAccountamtlist();
                feeAccount.setTrans_serial(transTransReq.getTrans_serial());
                feeAccount.setPlat_no(prodTransferDebtRequestBo.getMer_no());
                feeAccount.setTrans_code(TransConsts.TRANSFERDEBT_CODE);
                //从转让人收取手续费
                if (null != prodTransferDebtRequestBo.getCommissionObject() &&
                        prodTransferDebtRequestBo.getCommissionObject().getPayout_amt().compareTo(BigDecimal.ZERO)>0) {
                    logger.info("【标的转让】========从转让人收取手续费");
                    feeAccount.setAmt(prodTransferDebtRequestBo.getCommissionObject().getPayout_amt());
                    if ("01".equals(prodTransferDebtRequestBo.getCommissionObject().getPayout_plat_type())) {
                        //现金
                        feePlatAccount = accountQueryService.queryAccount(prodTransferDebtRequestBo.getMer_no(),
                                prodTransferDebtRequestBo.getMer_no(), Tsubject.CASH.getCode(), Ssubject.FEE.getCode());
                        feeAccount = EaccAccountamtlistOptionUtil.setSubject(feeAccount, Tsubject.CASH.getCode(),
                                Ssubject.INVEST.getCode(), Tsubject.CASH.getCode(), Ssubject.FEE.getCode(),
                                prodTransferDebtRequestBo.getPartner_trans_date(),prodTransferDebtRequestBo.getPartner_trans_time());
                    } else {
                        //在途
                        feePlatAccount = accountQueryService.queryAccount(prodTransferDebtRequestBo.getMer_no(),
                                prodTransferDebtRequestBo.getMer_no(), Tsubject.CASH.getCode(), Ssubject.FEE.getCode());
                        feeAccount = EaccAccountamtlistOptionUtil.setSubject(feeAccount, Tsubject.FLOAT.getCode(),
                                Ssubject.INVEST.getCode(), Tsubject.FLOAT.getCode(), Ssubject.FEE.getCode(),
                                prodTransferDebtRequestBo.getPartner_trans_date(),prodTransferDebtRequestBo.getPartner_trans_time());
                    }
                    if (feePlatAccount == null) {
                        BaseResponse baseResponse = new BaseResponse();
                        baseResponse.setRecode(BusinessMsg.INVALID_ACCOUNT);
                        baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.INVALID_ACCOUNT)+"：手续费子账户不存在");
                        throw new BusinessException(baseResponse);
                    }
                    feeAccount.setPlatcust(prodTransferDebtRequestBo.getPlatcust());
                    feeAccount.setOppo_platcust(feePlatAccount.getPlatcust());
                    feeAccount.setTrans_name(TransConsts.TRANSFERDEBT_NAME + "：手续费从转让人转给平台");
                    feeAccount.setOrder_no(prodTransferDebtRequestBo.getDetail_no());
                    eaccAccountamtlist.add(feeAccount);
                }
                //从受让人收取手续费
                if (null != prodTransferDebtRequestBo.getCommission_extObject() &&
                        prodTransferDebtRequestBo.getCommission_extObject().getPayout_amt().compareTo(BigDecimal.ZERO)>0) {
                    logger.info("【标的转让】========从受让人收取手续费");
                    feeAccount=new EaccAccountamtlist();
                    feeAccount.setTrans_serial(transTransReq.getTrans_serial());
                    feeAccount.setPlat_no(prodTransferDebtRequestBo.getMer_no());
                    feeAccount.setTrans_code(TransConsts.TRANSFERDEBT_CODE);
                    feeAccount.setAmt(prodTransferDebtRequestBo.getCommission_extObject().getPayout_amt());
                    if ("01".equals(prodTransferDebtRequestBo.getCommission_extObject().getPayout_plat_type())) {
                        //现金
                        feePlatAccount = accountQueryService.queryAccount(prodTransferDebtRequestBo.getMer_no(),
                                prodTransferDebtRequestBo.getMer_no(), Tsubject.CASH.getCode(), Ssubject.FEE.getCode());
                        feeAccount = EaccAccountamtlistOptionUtil.setSubject(feeAccount, Tsubject.CASH.getCode(),
                                Ssubject.INVEST.getCode(), Tsubject.CASH.getCode(), Ssubject.FEE.getCode(),
                                prodTransferDebtRequestBo.getPartner_trans_date(),prodTransferDebtRequestBo.getPartner_trans_time());
                    } else {
                        //在途
                        feePlatAccount = accountQueryService.queryAccount(prodTransferDebtRequestBo.getMer_no(),
                                prodTransferDebtRequestBo.getMer_no(), Tsubject.CASH.getCode(), Ssubject.FEE.getCode());
                        feeAccount = EaccAccountamtlistOptionUtil.setSubject(feeAccount, Tsubject.FLOAT.getCode(),
                                Ssubject.INVEST.getCode(), Tsubject.FLOAT.getCode(), Ssubject.FEE.getCode(),
                                prodTransferDebtRequestBo.getPartner_trans_date(),prodTransferDebtRequestBo.getPartner_trans_time());
                    }
                    if (feePlatAccount == null) {
                        BaseResponse baseResponse = new BaseResponse();
                        baseResponse.setRecode(BusinessMsg.INVALID_ACCOUNT);
                        baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.INVALID_ACCOUNT)+"：手续费子账户不存在");
                        throw new BusinessException(baseResponse);
                    }
                    feeAccount.setPlatcust(prodTransferDebtRequestBo.getDeal_platcust());
                    feeAccount.setOppo_platcust(feePlatAccount.getPlatcust());
                    feeAccount.setTrans_name(TransConsts.TRANSFERDEBT_NAME + "：手续费从受让人转给平台");
                    feeAccount.setOrder_no(prodTransferDebtRequestBo.getDetail_no());
                    eaccAccountamtlist.add(feeAccount);
                }
            }

            //更新份额表
            logger.info("【标的转让】========更新份额");
            ProdShareInall prodShareInallSaler = new ProdShareInall();
            prodShareInallSaler.setPlat_no(prodTransferDebtRequestBo.getMer_no());
            prodShareInallSaler.setProd_id(prodTransferDebtRequestBo.getProd_id());
            prodShareInallSaler.setPlatcust(prodTransferDebtRequestBo.getPlatcust());
            ProdShareInall prodShareInallBuyer = new ProdShareInall();
            prodShareInallBuyer.setPlat_no(prodTransferDebtRequestBo.getMer_no());
            prodShareInallBuyer.setProd_id(prodTransferDebtRequestBo.getProd_id());
            prodShareInallBuyer.setPlatcust(prodTransferDebtRequestBo.getDeal_platcust());
            // ===================以平台用户号，给需要操作的数据加锁，防止其它操作============================
            String lockKey = getLockKey("Investment"+prodShareInallBuyer.getPlatcust());
            boolean lock = getLock(lockKey);
            while (!lock) {
                sleep(50);//没取到锁50毫秒后重试
                lock = getLock(lockKey);
            }
            try{
                productInvestmentService.prodTrans(prodShareInallBuyer, prodShareInallSaler, prodTransferDebtRequestBo.getTrans_share());
            }catch (Exception ex){
                logger.info("更新份额异常：",ex);
                throw ex;
            }finally {
                CacheUtil.unlock(lockKey);
            }
            // =========================================================================================================
            ProdShareList prodShareListOut = getProdShareList(prodTransferDebtRequestBo,transTransReq);
            prodShareListOut.setAmt_type(AmtType.EXPENSIVE.getCode());
            ProdShareList prodShareListIn = getProdShareList(prodTransferDebtRequestBo,transTransReq);
            prodShareListIn.setPlatcust(prodTransferDebtRequestBo.getDeal_platcust());
            prodShareListIn.setAmt_type(AmtType.INCOME.getCode());
            ProdTransferRecord transferRecord = getTransferRecord(prodTransferDebtRequestBo);
            try {
                prodTransferService.prodTransTransfer(prodTransferDebtRequestBo,prodShareListOut,prodShareListIn,transferRecord,eaccAccountamtlist,updateTransreqFlag);
                try{
                    //转账
                    if(updateTransreqFlag){
                        transferService.transfer(prodTransferDebtRequestBo,eaccAccountamtlist);
                    }else{
                        accountTransferService.batchTransfer(eaccAccountamtlist);
                    }
                }catch (Exception e){
                    logger.error("【标的转让】转账同步返回异常，回滚转让明细：",e);
                    productInvestmentService.delProdTransDate(prodShareListOut.getTrans_serial());
                    throw e;
                }
            }catch (Exception e){
                logger.error("【标的转让】========",e);
                logger.info("【标的转让】========反向更新份额");
                productInvestmentService.backProdTrans(prodShareInallBuyer,prodShareInallSaler,prodTransferDebtRequestBo.getTrans_share());
                if(e instanceof BusinessException){
                    throw e;
                }else{
                    throw new BusinessException(BusinessMsg.RUNTIME_EXCEPTION,BusinessMsg.getMsg(BusinessMsg.DATEBASE_EXCEPTION));
                }
            }
        } catch (Exception e) {
            logger.error("【标的转让】========" , e);
            BaseResponse baseResponse = new BaseResponse();
            if (e instanceof BusinessException) {
                baseResponse = ((BusinessException) e).getBaseResponse();
            } else {
                baseResponse.setRecode(BusinessMsg.DATEBASE_EXCEPTION);
                baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.DATEBASE_EXCEPTION));
            }
            //更新流水
            if(updateTransreqFlag){
                transTransReq.setReturn_code(baseResponse.getRecode());
                transTransReq.setReturn_msg(baseResponse.getRemsg());
                transTransReq.setStatus(FlowStatus.FAIL.getCode());
                transReqService.insert(transTransReq);
            }
            throw new BusinessException(baseResponse);
        }

//        if(updateTransreqFlag){
//            transTransReq.setReturn_code(BusinessMsg.SUCCESS);
//            transTransReq.setReturn_msg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));
//            transTransReq.setStatus(FlowStatus.SUCCESS.getCode());
//            transReqService.insert(transTransReq);
//        }

        return true;
    }

    @Override
    public boolean prodRepayForNoSyn(BaseRequest baseRequest, BatchCustRepay batchCustRepay) throws BusinessException {
        ProdBatchRepayAsynRequest prodBatchRepayAsynRequest = (ProdBatchRepayAsynRequest)baseRequest;
        //TODO 记录业务流水
        TransTransreq transTransReq = transReqService.queryTransTransReqByOrderno(batchCustRepay.getDetail_no());
        ProdProductinfo checkProductInfo = null;
        try {
            //验证标是否存在
            checkProductInfo = prodSearchService.queryProdInfo(prodBatchRepayAsynRequest.getMer_no(),prodBatchRepayAsynRequest.getProd_id());
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
            logger.info("【标的还款-异步】========单个还款开始，用户：" + batchCustRepay.getCust_no() + "，标的：" + checkProductInfo.getProd_id()+"，订单号："+batchCustRepay.getDetail_no());

            prodIdRefundOption(baseRequest,checkProductInfo,batchCustRepay,transTransReq);
            //更新业务流水
            transTransReq.setReturn_code(BusinessMsg.SUCCESS);
            transTransReq.setReturn_msg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));
            transTransReq.setStatus(FlowStatus.SUCCESS.getCode());
            try{
                transReqService.insert(transTransReq);
            }catch (Exception e){
                logger.error("流水记录失败："+transTransReq.getTrans_serial());

            }
        } catch (Exception e) {
            //数据库操作错误，记录流水，抛异常
            BaseResponse baseResponse = new BaseResponse();
            if (e instanceof BusinessException) {
                baseResponse = ((BusinessException) e).getBaseResponse();
                logger.info("【标的还款-异步】========单个还款失败" + baseResponse.getRemsg());
            } else {
                logger.error("【标的还款-异步】========数据库操作异常：" + e);
                baseResponse.setRecode(transTransReq.getReturn_code());
                baseResponse.setRemsg(transTransReq.getReturn_msg());
            }

            //查询该条流水份额有没有
            String value = (String)CacheUtil.getCache().get("prod_repay_key_"+transTransReq.getTrans_serial());
            if("1".equals(value)){//说明是转账异常
                ProdShareListExample prodShareListExample = new ProdShareListExample();
                ProdShareListExample.Criteria criteria = prodShareListExample.createCriteria();
                criteria.andTrans_serialEqualTo(transTransReq.getTrans_serial());
                criteria.andEnabledEqualTo(Constants.ENABLED);
                List<ProdShareList> prodShareLists = prodShareListMapper.selectByExample(prodShareListExample);
                logger.info("【标的还款-异步】prodShareLists======="+JSON.toJSON(prodShareLists)+"size==="+prodShareLists.size());
                if(prodShareLists.size()>0){
                    int i = prodShareListMapper.deleteByExample(prodShareListExample);
                    logger.info("【标的还款-异步】deleteByExample返回值为："+i);
                    if(i==1){
                        logger.info("【标的还款-异步】删除未回滚份额成功======="+JSON.toJSON(prodShareLists)+"size==="+prodShareLists.size());
                    }
                }

                ProdShareInallExample prodShareInallExample = new ProdShareInallExample();
                ProdShareInallExample.Criteria pinCria = prodShareInallExample.createCriteria();
                pinCria.andPlatcustEqualTo(batchCustRepay.getCust_no());
                pinCria.andPlat_noEqualTo(baseRequest.getMer_no());
                pinCria.andProd_idEqualTo(checkProductInfo.getProd_id());
                pinCria.andEnabledEqualTo(Constants.ENABLED);
                List<ProdShareInall> prodShareInalls = prodShareInallMapper.selectByExample(prodShareInallExample);
                logger.info("【标的还款-异步】prodShareInalls======="+JSON.toJSON(prodShareInalls)+"size==="+prodShareInalls.size());
                if(prodShareInalls.size()>0){
                    ProdShareInall prodShareInall = new ProdShareInall();
                    prodShareInall.setVol(batchCustRepay.getReal_repay_amount());
                    Map<String,Object> params=new HashMap<>();
                    params.put("prod_id",checkProductInfo.getProd_id());
                    params.put("platcust",batchCustRepay.getCust_no());
                    params.put("plat_no",baseRequest.getMer_no());
                    params.put("vol",batchCustRepay.getReal_repay_amount());
                    params.put("update_by",SeqUtil.RANDOM_KEY);
                    params.put("update_time",new Date());
                    int y = custProdShareInallMapper.addInallVol(params);
                    if(y>0){
                        logger.info("【标的还款-异步】addInallVol===份额表添加成功====");
                    }
                }
            }
            throw new BusinessException(baseResponse);
        }
        return true;
    }

    private boolean checkInvestData(ProdInvestData prodInvestData) {
        BigDecimal trans_amout = prodInvestData.getTrans_amt();
        BigDecimal experience_amt = BigDecimal.ZERO;
        BigDecimal coupon_amt = BigDecimal.ZERO;
        BigDecimal self_amt = BigDecimal.ZERO;
        BigDecimal fee_amt = BigDecimal.ZERO;
        if (trans_amout.compareTo(BigDecimal.ZERO)<1){
            BaseResponse baseResponse = new BaseResponse();
            baseResponse.setRecode(BusinessMsg.PARAMETER_ERROR);
            baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.PARAMETER_ERROR) + "：投标金额有误");
            logger.error("【批量投标】========投标金额校验不通过：" + baseResponse.getRemsg());
            throw new BusinessException(baseResponse);
        }
        if (prodInvestData.getExperience_amt() != null) {
            experience_amt = prodInvestData.getExperience_amt();
            if (experience_amt.compareTo(BigDecimal.ZERO)<0){
                BaseResponse baseResponse = new BaseResponse();
                baseResponse.setRecode(BusinessMsg.PARAMETER_ERROR);
                baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.PARAMETER_ERROR) + "：投标金额有误");
                logger.error("【批量投标】========投标金额校验不通过：" + baseResponse.getRemsg());
                throw new BusinessException(baseResponse);
            }
        }
        if (prodInvestData.getCoupon_amt() != null) {
            coupon_amt = prodInvestData.getCoupon_amt();
            if (coupon_amt.compareTo(BigDecimal.ZERO)<0){
                BaseResponse baseResponse = new BaseResponse();
                baseResponse.setRecode(BusinessMsg.PARAMETER_ERROR);
                baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.PARAMETER_ERROR) + "：投标金额有误");
                logger.error("【批量投标】========投标金额校验不通过：" + baseResponse.getRemsg());
                throw new BusinessException(baseResponse);
            }
        }
        if (prodInvestData.getSelf_amt() != null) {
            self_amt = prodInvestData.getSelf_amt();
            if (self_amt.compareTo(BigDecimal.ZERO)<0){
                BaseResponse baseResponse = new BaseResponse();
                baseResponse.setRecode(BusinessMsg.PARAMETER_ERROR);
                baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.PARAMETER_ERROR) + "：投标金额有误");
                logger.error("【批量投标】========投标金额校验不通过：" + baseResponse.getRemsg());
                throw new BusinessException(baseResponse);
            }
        }
        if (prodInvestData.getCommissionObj() != null) {
            fee_amt = prodInvestData.getCommissionObj().getPayout_amt();
            if (fee_amt.compareTo(BigDecimal.ZERO)<0){
                BaseResponse baseResponse = new BaseResponse();
                baseResponse.setRecode(BusinessMsg.PARAMETER_ERROR);
                baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.PARAMETER_ERROR) + "：投标金额有误");
                logger.error("【批量投标】========投标金额校验不通过：" + baseResponse.getRemsg());
                throw new BusinessException(baseResponse);
            }
        }
        BigDecimal real_amt = experience_amt.add(coupon_amt).add(self_amt).add(fee_amt);
        if (real_amt.compareTo(trans_amout) != 0) {
            BaseResponse baseResponse = new BaseResponse();
            baseResponse.setRecode(BusinessMsg.PARAMETER_ERROR);
            baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.PARAMETER_ERROR) + "：投标金额有误");
            logger.error("【批量投标】========投标金额校验不通过：" + baseResponse.getRemsg());
            throw new BusinessException(baseResponse);
        }
        logger.error("【批量投标】========投标金额校验通过");
        return true;
    }

    private boolean checkInvestData(ProdInvestDataTb prodInvestData) {
        BigDecimal trans_amout = prodInvestData.getTrans_amt();
        BigDecimal experience_amt = BigDecimal.ZERO;
        BigDecimal coupon_amt = BigDecimal.ZERO;
        BigDecimal self_amt = BigDecimal.ZERO;
        BigDecimal fee_amt = BigDecimal.ZERO;

        if (trans_amout.compareTo(BigDecimal.ZERO)<1){
            BaseResponse baseResponse = new BaseResponse();
            baseResponse.setRecode(BusinessMsg.PARAMETER_ERROR);
            baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.PARAMETER_ERROR) + "：投标金额有误");
            logger.error("【批量投标】========投标金额校验不通过：" + baseResponse.getRemsg());
            throw new BusinessException(baseResponse);
        }
        if (prodInvestData.getExperience_amt() != null) {
            experience_amt = prodInvestData.getExperience_amt();
            if (experience_amt.compareTo(BigDecimal.ZERO)<0){
                BaseResponse baseResponse = new BaseResponse();
                baseResponse.setRecode(BusinessMsg.PARAMETER_ERROR);
                baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.PARAMETER_ERROR) + "：投标金额有误");
                logger.error("【批量投标】========投标金额校验不通过：" + baseResponse.getRemsg());
                throw new BusinessException(baseResponse);
            }
        }
        if (prodInvestData.getCoupon_amt() != null) {
            coupon_amt = prodInvestData.getCoupon_amt();
            if (coupon_amt.compareTo(BigDecimal.ZERO)<0){
                BaseResponse baseResponse = new BaseResponse();
                baseResponse.setRecode(BusinessMsg.PARAMETER_ERROR);
                baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.PARAMETER_ERROR) + "：投标金额有误");
                logger.error("【批量投标】========投标金额校验不通过：" + baseResponse.getRemsg());
                throw new BusinessException(baseResponse);
            }
        }
        if (prodInvestData.getSelf_amt() != null) {
            self_amt = prodInvestData.getSelf_amt();
            if (self_amt.compareTo(BigDecimal.ZERO)<0){
                BaseResponse baseResponse = new BaseResponse();
                baseResponse.setRecode(BusinessMsg.PARAMETER_ERROR);
                baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.PARAMETER_ERROR) + "：投标金额有误");
                logger.error("【批量投标】========投标金额校验不通过：" + baseResponse.getRemsg());
                throw new BusinessException(baseResponse);
            }
        }
        BigDecimal real_amt = experience_amt.add(coupon_amt).add(self_amt).add(fee_amt);
        if (real_amt.compareTo(trans_amout) != 0) {
            BaseResponse baseResponse = new BaseResponse();
            baseResponse.setRecode(BusinessMsg.PARAMETER_ERROR);
            baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.PARAMETER_ERROR) + "：投标金额有误");
            logger.error("【批量投标】========投标金额校验不通过：" + baseResponse.getRemsg());
            throw new BusinessException(baseResponse);
        }
        logger.error("【批量投标】========投标金额校验通过");
        return true;
    }
private ProdShareList getInvestShareList(ProdInvestData prodInvestData, BaseRequest baseRequest, String prod_id, String trans_serial){
    ProdShareList shareList=
    getProdShareList(prodInvestData, baseRequest, prod_id, trans_serial);
    shareList.setAmt_type(AmtType.INCOME.getCode());
    shareList.setEnabled(Constants.DISABLED);
    return shareList;
}
    public ProdShareList getProdShareList(ProdInvestData prodInvestData, BaseRequest baseRequest, String prod_id, String trans_serial) {
        ProdShareList prodShareList = new ProdShareList();
        prodShareList.setTrans_serial(trans_serial);
        prodShareList.setTrans_code(TransConsts.BUY_CODE);
        prodShareList.setPlat_no(baseRequest.getMer_no());
        prodShareList.setPlatcust(prodInvestData.getPlatcust());
        prodShareList.setProd_id(prod_id);
        if (prodInvestData.getCommissionObj() == null) {
            prodShareList.setVol(prodInvestData.getTrans_amt());
        } else {
            prodShareList.setVol(prodInvestData.getTrans_amt().subtract(prodInvestData.getCommissionObj().getPayout_amt()));
            prodShareList.setFee_priority(prodInvestData.getCommissionObj().getPayout_plat_type());
        }
        prodShareList.setIn_interest(prodInvestData.getIn_interest());
        prodShareList.setTrans_date(baseRequest.getPartner_trans_date());
        prodShareList.setTrans_time(baseRequest.getPartner_trans_time());
        prodShareList.setStatus(ShareStatus.SUCESS.getCode());
        prodShareList.setEnabled(Constants.ENABLED);
        prodShareList.setCreate_by(prodShareList.getPlat_no());
        prodShareList.setCreate_time(DateUtils.today());
        prodShareList.setSelf_amt(prodInvestData.getSelf_amt());
        prodShareList.setCoupon_amt(prodInvestData.getCoupon_amt());
        prodShareList.setExperience_amt(prodInvestData.getExperience_amt());
        prodShareList.setSelf_priority(prodInvestData.getSubject_priority());
        //TODO 加息券？？？
        //prodShareList.setInterest();
        return prodShareList;
    }

    public ProdShareList getProdShareList(ProdInvestDataTb prodInvestData, BaseRequest baseRequest, String prod_id, String trans_serial) {
        ProdShareList prodShareList = new ProdShareList();
        prodShareList.setTrans_serial(trans_serial);
        prodShareList.setTrans_code(TransConsts.BUY_CODE);
        prodShareList.setPlat_no(baseRequest.getMer_no());
        prodShareList.setPlatcust(prodInvestData.getPlatcust());
        prodShareList.setProd_id(prod_id);
        prodShareList.setVol(prodInvestData.getTrans_amt());
        prodShareList.setIn_interest(prodInvestData.getIn_interest());
        prodShareList.setTrans_date(baseRequest.getPartner_trans_date());
        prodShareList.setTrans_time(baseRequest.getPartner_trans_time());
        prodShareList.setStatus(ShareStatus.SUCESS.getCode());
        prodShareList.setEnabled(Constants.ENABLED);
        prodShareList.setCreate_by(prodShareList.getPlat_no());
        prodShareList.setCreate_time(DateUtils.today());
        prodShareList.setSelf_amt(prodInvestData.getSelf_amt());
        prodShareList.setCoupon_amt(prodInvestData.getCoupon_amt());
        prodShareList.setExperience_amt(prodInvestData.getExperience_amt());
        prodShareList.setSelf_priority(prodInvestData.getSubject_priority());
        //TODO 加息券？？？
        //prodShareList.setInterest();
        return prodShareList;
    }

    private ProdShareList getProdShareList(ProdTransferDebtRequestBo prodTransferDebtRequestBo,TransTransreq transTransreq) throws BusinessException {
        //添加shareList数据
        ProdShareList prodShareList = new ProdShareList();
        prodShareList.setTrans_serial(transTransreq.getTrans_serial());
        prodShareList.setTrans_code(TransConsts.TRANSFERDEBT_CODE);
        prodShareList.setPlat_no(prodTransferDebtRequestBo.getMer_no());
        prodShareList.setPlatcust(prodTransferDebtRequestBo.getPlatcust());
        prodShareList.setProd_id(prodTransferDebtRequestBo.getProd_id());
        prodShareList.setTrans_date(prodTransferDebtRequestBo.getPartner_trans_date());
        prodShareList.setTrans_time(prodTransferDebtRequestBo.getPartner_trans_time());
        prodShareList.setVol(prodTransferDebtRequestBo.getTrans_share());
        //TODO prodShareList.setIn_interest();
        prodShareList.setStatus(ShareStatus.SUCESS.getCode());
        prodShareList.setStatus(ShareStatus.SUCESS.getCode());
        prodShareList.setEnabled(Constants.ENABLED);
        prodShareList.setCreate_by(prodShareList.getPlat_no());
        prodShareList.setCreate_time(DateUtils.today());
        prodShareList.setSelf_amt(prodTransferDebtRequestBo.getDeal_amount());
        prodShareList.setCoupon_amt(prodTransferDebtRequestBo.getCoupon_amt());
        prodShareList.setSelf_priority(prodTransferDebtRequestBo.getSubject_priority());
        if (prodTransferDebtRequestBo.getCommissionObject() != null) {
            prodShareList.setFee_priority(prodTransferDebtRequestBo.getCommissionObject().getPayout_plat_type());
        }
        if (prodTransferDebtRequestBo.getCommission_extObject() != null) {
            prodShareList.setFee_priority(prodTransferDebtRequestBo.getCommission_extObject().getPayout_plat_type());
        }
        //TODO 加息券？？？
        //prodShareList.setInterest();
        return prodShareList;
    }

    public ProdTransferRecord getTransferRecord(ProdTransferDebtRequestBo prodTransferDebtRequestBo) {
        ProdTransferRecord transferRecord = new ProdTransferRecord();
        transferRecord.setTrans_serial(SeqUtil.getSeqNum());//交易流水号
        transferRecord.setPlat_no(prodTransferDebtRequestBo.getMer_no());
        transferRecord.setPtrans_date(prodTransferDebtRequestBo.getPartner_trans_date());
        transferRecord.setPartner_trans_time(prodTransferDebtRequestBo.getPartner_trans_time());
        transferRecord.setOrder_no(prodTransferDebtRequestBo.getOrder_no());
        transferRecord.setPlatcust(prodTransferDebtRequestBo.getPlatcust());
        transferRecord.setTrans_share(prodTransferDebtRequestBo.getTrans_share());
        transferRecord.setProd_id(prodTransferDebtRequestBo.getProd_id());
        transferRecord.setTrans_amt(prodTransferDebtRequestBo.getTrans_amt());
        transferRecord.setDeal_amout(prodTransferDebtRequestBo.getDeal_amount());
        transferRecord.setInvest_amt(prodTransferDebtRequestBo.getDeal_amount());
        transferRecord.setExtract_amt(prodTransferDebtRequestBo.getDeal_amount());
        transferRecord.setDeal_platcust(prodTransferDebtRequestBo.getDeal_platcust());
        transferRecord.setCommission(prodTransferDebtRequestBo.getCommission());
        transferRecord.setCommission_ext(prodTransferDebtRequestBo.getCommission_ext());
        transferRecord.setPublish_date(prodTransferDebtRequestBo.getPublish_date());
        transferRecord.setTransfer_income(prodTransferDebtRequestBo.getTransfer_income());
        transferRecord.setIncome_acct(prodTransferDebtRequestBo.getIncome_acct());
        transferRecord.setRelated_prod_ids(prodTransferDebtRequestBo.getRelated_prod_ids());
        transferRecord.setDeal_date(DateUtils.today());
        transferRecord.setEnabled(Constants.ENABLED);
        transferRecord.setCreate_by(transferRecord.getDeal_platcust());
        transferRecord.setCreate_time(transferRecord.getDeal_date());
        return transferRecord;
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
}
