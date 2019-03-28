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
import com.sunyard.sunfintech.core.util.CacheUtil;
import com.sunyard.sunfintech.core.util.DateUtils;
import com.sunyard.sunfintech.core.util.SeqUtil;
import com.sunyard.sunfintech.core.util.StringUtils;
import com.sunyard.sunfintech.custdao.mapper.CustProdShareInallMapper;
import com.sunyard.sunfintech.dao.entity.*;
import com.sunyard.sunfintech.dao.mapper.*;
import com.sunyard.sunfintech.prod.model.bo.BatchCustRepay;
import com.sunyard.sunfintech.prod.model.bo.BatchCustRepayOld;
import com.sunyard.sunfintech.prod.model.bo.ProdRepayData;
import com.sunyard.sunfintech.prod.model.mq.ProdRefundData;
import com.sunyard.sunfintech.prod.provider.*;
import com.sunyard.sunfintech.pub.provider.IAuthCheckService;
import com.sunyard.sunfintech.pub.provider.ITransReqService;
import com.sunyard.sunfintech.pub.provider.ITransferService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

/**
 * Created by terry on 2017/7/13.
 */
@CacheConfig(cacheNames = "productRefundExtService")
@org.springframework.stereotype.Service
public class ProductRefundExtService extends BaseServiceSimple implements IProductRefundExtService {

    @Autowired
    private IAccountQueryService accountQueryService;

    @Autowired
    private IProdSearchService prodSearchService;

    @Autowired
    private IProdShareOptionService prodShareOptionService;

    @Autowired
    private IAccountTransferService accountTransferService;

    @Autowired
    private ProdRepaymentlistMapper repayMentListMapper;

    @Autowired
    private ProdShareListMapper prodShareListMapper;

    @Autowired
    private CustProdShareInallMapper custProdShareInallMapper;

    @Autowired
    private ITransferService transferService;

    @Autowired
    private IProductRefundDBOptionService productRefundDBOptionService;

    @Override
    @Transactional
    public boolean refundToAccount(BatchCustRepayOld custRepay, TransTransreq transTransreq, String subject) throws BusinessException {

        //判断是否有电子账户，如没有，继续执行
        AccountSubjectInfo eplatcust = accountQueryService.queryFINANCINGPlatAccount(custRepay.getMall_no(), custRepay.getCust_no(), AccountType.ElectronicAccount.getCode(),
                Tsubject.CASH.getCode(), Ssubject.EACCOUNT.getCode());

        //批量转账列表
        List<EaccAccountamtlist> batchEaccAccountamtList=new ArrayList<EaccAccountamtlist>();
        ProdProductinfo prodProductinfo=prodSearchService.queryProdInfo(custRepay.getPlat_no(),custRepay.getProd_id());
        String platcust=null;
        ProdShareInall queryProdShareInall =null;
        if(ProdType.CURRENT.getCode().equals(prodProductinfo.getProd_type())){
            List<ProdShareList> prodShareLists=prodShareOptionService.queryProdShareList(prodProductinfo.getPlat_no(), prodProductinfo.getProd_id(), custRepay.getCust_no());
            if(null==prodShareLists){
                logger.info("-------------明细表里没有匹配的prod_id:" + prodProductinfo.getProd_id() + "信息，请确认---------------");
                throw new BusinessException(BusinessMsg.PARAMETER_ERROR,
                        BusinessMsg.getMsg(BusinessMsg.PARAMETER_ERROR)+"：platcust:"+custRepay.getCust_no()+"，prod_id:"+prodProductinfo.getProd_id()+"的投标信息不存在");
            }
            BigDecimal saleAmt=BigDecimal.ZERO;
            for(ProdShareList prodShareList:prodShareLists){
                if(AmtType.INCOME.getCode().equals(prodShareList.getAmt_type())){
                    saleAmt=saleAmt.add(prodShareList.getVol());
                }else{
                    saleAmt=saleAmt.subtract(prodShareList.getVol());
                }
            }
            if(saleAmt.compareTo(BigDecimal.ZERO)<=0){
                logger.info("-------------用户" + custRepay.getCust_no() + "已还清，无需还款，请确认---------------");
                throw new BusinessException(BusinessMsg.PARAMETER_ERROR,
                        BusinessMsg.getMsg(BusinessMsg.PARAMETER_ERROR)+"：platcust:"+custRepay.getCust_no()+"，prod_id:"+prodProductinfo.getProd_id()+"的投资已还清，无需再次还款");
            }
            platcust=custRepay.getCust_no();
        }else{
            queryProdShareInall = prodSearchService.queryProdShareInall(prodProductinfo.getPlat_no(), prodProductinfo.getProd_id(), custRepay.getCust_no());
            if (null == queryProdShareInall) {
                logger.info("-------------份额表里没有匹配的prod_id:" + prodProductinfo.getProd_id() + "信息，请确认---------------");
                throw new BusinessException(BusinessMsg.PARAMETER_ERROR,
                        BusinessMsg.getMsg(BusinessMsg.PARAMETER_ERROR)+"：platcust:"+custRepay.getCust_no()+"，prod_id:"+prodProductinfo.getProd_id()+"的投标信息不存在");
            }
            platcust=queryProdShareInall.getPlatcust();
        }

        //投资人电子账户判断
        if (eplatcust != null && Tsubject.CASH.getCode().equals(subject)) {
            logger.info("【还款】==============该用户存在电子账户，暂不执行还款！");
            return false;
        }

        //还款
        logger.info("【还款】==============该用户不存在电子账户，执行还款！");

        logger.info("-------------体验金:" + custRepay.getExperience_amt() + "---------------");
        //体验金
        BigDecimal experience_amt = custRepay.getExperience_amt();
        //如果有体验金金额，那么减去体验金金额
        if (null != experience_amt && experience_amt.compareTo(BigDecimal.ZERO) > 0) {
            AccountSubjectInfo experiencePlatAccount = accountQueryService.queryAccount(prodProductinfo.getPlat_no(),
                    null, Tsubject.CASH.getCode(), Ssubject.EXPERIENCE.getCode());
            //体验金转账到平台体验金账户
            EaccAccountamtlist experienceAmt = new EaccAccountamtlist();
            experienceAmt.setTrans_serial(transTransreq.getTrans_serial());
            experienceAmt.setPlat_no(prodProductinfo.getPlat_no());
            experienceAmt.setPlatcust(prodProductinfo.getProd_account());
            experienceAmt.setSubject(Tsubject.CASH.getCode());
//            experienceAmt.setSubject(subject);
            experienceAmt.setSub_subject(Ssubject.PROD.getCode());
            experienceAmt.setOppo_plat_no(prodProductinfo.getPlat_no());
            experienceAmt.setOppo_platcust(experiencePlatAccount.getPlatcust());
            experienceAmt.setOppo_subject(experiencePlatAccount.getSubject());
            experienceAmt.setOppo_sub_subject(experiencePlatAccount.getSub_subject());
            experienceAmt.setAmt(experience_amt);
            experienceAmt.setTrans_code(transTransreq.getTrans_code());
            experienceAmt.setTrans_name(transTransreq.getTrans_name());
            experienceAmt.setTrans_date(transTransreq.getTrans_date());
            experienceAmt.setTrans_time(transTransreq.getTrans_time());
            experienceAmt.setOrder_no(transTransreq.getOrder_no());
            batchEaccAccountamtList.add(experienceAmt);
        }

        logger.info("-------------加息金:" + custRepay.getRates_amt() + "---------------");
        //加息金
        BigDecimal rates_amt = custRepay.getRates_amt();
        //如果有加息金金额
        if (null != rates_amt && rates_amt.compareTo(BigDecimal.ZERO) > 0) {
            logger.info("-------------加息金:" + custRepay.getRates_amt() + "----开始转账-----------");
            AccountSubjectInfo ratesAmtAccount = accountQueryService.queryAccount(prodProductinfo.getPlat_no(),
                    null, Tsubject.CASH.getCode(), Ssubject.PLUSRATE.getCode());
            //平台加息金账户转到投资人账户
            logger.info("-------------平台加息金账户转到投资人账户---------------");
            EaccAccountamtlist ratesAmt = new EaccAccountamtlist();
            ratesAmt.setTrans_serial(transTransreq.getTrans_serial());
            ratesAmt.setPlat_no(prodProductinfo.getPlat_no());
            ratesAmt.setPlatcust(ratesAmtAccount.getPlatcust());
            ratesAmt.setSubject(Tsubject.CASH.getCode());
            ratesAmt.setSub_subject(Ssubject.PLUSRATE.getCode());
            ratesAmt.setOppo_plat_no(prodProductinfo.getPlat_no());
            ratesAmt.setOppo_platcust(platcust);
            ratesAmt.setOppo_subject(Tsubject.CASH.getCode());
//            ratesAmt.setOppo_sub_subject(Ssubject.EACCOUNT.getCode());
            ratesAmt.setOppo_sub_subject(Ssubject.INVEST.getCode());
            ratesAmt.setAmt(rates_amt);
            ratesAmt.setTrans_code(transTransreq.getTrans_code());
            ratesAmt.setTrans_name(transTransreq.getTrans_name());
            ratesAmt.setTrans_date(transTransreq.getTrans_date());
            ratesAmt.setTrans_time(transTransreq.getTrans_time());
            ratesAmt.setOrder_no(transTransreq.getOrder_no());
            batchEaccAccountamtList.add(ratesAmt);
        }

        logger.info("-------------还款本金:" + custRepay.getReal_repay_amount() + "---------------");
        //还款本金
        BigDecimal real_repay_amount = custRepay.getReal_repay_amount();
//			BigDecimal real_repay_amount=custRepay.getReal_repay_amount();
        BigDecimal cust_repay_amount = BigDecimal.ZERO;
        //如果有本金金额
        if (null != real_repay_amount && real_repay_amount.compareTo(BigDecimal.ZERO) > 0) {
            //cust_repay_amount.add(real_repay_amount);
//				cust_repay_amount = new BigDecimal(real_repay_amount.intValue());
            cust_repay_amount = cust_repay_amount.add(real_repay_amount);
            logger.info("-------------计划还款本金a:" + custRepay.getReal_repay_amount() + "---------------");
        }

        //还款利息
        BigDecimal real_repay_val = BigDecimal.ZERO;
        if (custRepay.getReal_repay_val() != null) {
            real_repay_val = real_repay_val.add(custRepay.getReal_repay_val());
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
            custRepayAmt.setTrans_serial(transTransreq.getTrans_serial());
            custRepayAmt.setPlat_no(prodProductinfo.getPlat_no());
            custRepayAmt.setPlatcust(prodProductinfo.getProd_account());
            custRepayAmt.setSubject(subject);
            custRepayAmt.setSub_subject(Ssubject.PROD.getCode());
            custRepayAmt.setOppo_plat_no(prodProductinfo.getPlat_no());
            custRepayAmt.setOppo_platcust(platcust);
            custRepayAmt.setOppo_subject(subject);
//                custRepayAmt.setOppo_sub_subject(Ssubject.EACCOUNT.getCode());
            if(eplatcust != null){
                custRepayAmt.setOppo_sub_subject(Ssubject.EACCOUNT.getCode());
            }else{
                custRepayAmt.setOppo_sub_subject(Ssubject.INVEST.getCode());
            }
            custRepayAmt.setAmt(cust_repay_amount);
            custRepayAmt.setTrans_code(transTransreq.getTrans_code());
            custRepayAmt.setTrans_name(transTransreq.getTrans_name());
            custRepayAmt.setTrans_date(transTransreq.getTrans_date());
            custRepayAmt.setTrans_time(transTransreq.getTrans_time());
            custRepayAmt.setOrder_no(transTransreq.getOrder_no());
            batchEaccAccountamtList.add(custRepayAmt);
        }

        //还款本金
        real_repay_amount = custRepay.getReal_repay_amount();
        cust_repay_amount = BigDecimal.ZERO;
        //如果有本金金额
        if (null != real_repay_amount && real_repay_amount.compareTo(BigDecimal.ZERO) > 0) {
            cust_repay_amount = cust_repay_amount.add(real_repay_amount);
            logger.info("-------------计划还款本金a:" + custRepay.getReal_repay_amount() + "---------------");
        }

        //还款利息
        real_repay_val = BigDecimal.ZERO;
        if (custRepay.getReal_repay_val() != null) {
            real_repay_val = real_repay_val.add(custRepay.getReal_repay_val());
        }
        //如果有利息
        if (null != real_repay_val && real_repay_val.compareTo(BigDecimal.ZERO) > 0) {
            cust_repay_amount = cust_repay_amount.add(real_repay_val);
        }

        if (real_repay_amount == null) {
            real_repay_amount = BigDecimal.ZERO;
        }

        if (real_repay_amount == null) {
            real_repay_amount = BigDecimal.ZERO;
        }

        //更新产品购买明细表
        logger.info("-------------更新产品购买明细表---------------");
        List<ProdShareList> selectMore = prodShareOptionService.queryProdShareList(prodProductinfo.getPlat_no(), prodProductinfo.getProd_id(), custRepay.getCust_no());
        if(selectMore==null){
            logger.info("-----------未找到投资信息-----------");
            throw new BusinessException(BusinessMsg.PARAMETER_ERROR,
                    BusinessMsg.getMsg(BusinessMsg.PARAMETER_ERROR)+"：platcust:"+custRepay.getCust_no()+"，prod_id:"+prodProductinfo.getProd_id()+"的投标信息不存在");
        }
        ProdShareList selectOne=selectMore.get(0);
        selectOne.setId(null);
        selectOne.setVol(real_repay_amount);
        selectOne.setAmt_type(AmtType.EXPENSIVE.getCode());
        selectOne.setCreate_by(SeqUtil.RANDOM_KEY);
        selectOne.setCreate_time(DateUtils.today());
        selectOne.setTrans_serial(transTransreq.getTrans_serial());
        selectOne.setTrans_date(transTransreq.getTrans_date());
        selectOne.setTrans_time(transTransreq.getTrans_time());
        selectOne.setTrans_code(transTransreq.getTrans_code());

        //新增标的还款信息
        logger.info("-------------新增标的还款信息---------------");
        ProdRepaymentlist repaymentlist = new ProdRepaymentlist();
        repaymentlist.setPlat_no(prodProductinfo.getPlat_no());
        repaymentlist.setPlatcust(platcust);
        repaymentlist.setRepay_num(custRepay.getRepay_num());
        repaymentlist.setProd_id(custRepay.getProd_id());
        repaymentlist.setReal_repay_amount(custRepay.getReal_repay_amount());
        repaymentlist.setReal_repay_val(custRepay.getReal_repay_val());
        repaymentlist.setReal_repay_amt(custRepay.getReal_repay_amt());
        repaymentlist.setReal_repay_date(custRepay.getReal_repay_date());
        repaymentlist.setStatus(RepaymentStatus.SUCCPAY.getCode());
        repaymentlist.setIf_advance(AdvanceType.NOADVANCE.getCode());
        repaymentlist.setEnabled(Constants.ENABLED);
        repaymentlist.setReal_experience_amt(custRepay.getExperience_amt());
        repaymentlist.setReal_rates_amt(custRepay.getRates_amt());
        repaymentlist.setExt1(transTransreq.getTrans_serial());
        //只有定期标才更新份额表
        if(ProdType.PERIOD.getCode().equals(prodProductinfo.getProd_type())){
            logger.info("-------------更新投资人份额表-------更新--------");
//            queryProdShareInall.setTot_vol(queryProdShareInall.getTot_vol().subtract(real_repay_amount));
//            queryProdShareInall.setVol(queryProdShareInall.getVol().subtract(real_repay_amount));
//            prodShareInallMapper.updateByPrimaryKeySelective(queryProdShareInall);
            Map<String,Object> params=new HashMap<>();
            params.put("vol",real_repay_amount);
            params.put("prod_id",prodProductinfo.getProd_id());
            params.put("platcust",custRepay.getCust_no());
            params.put("plat_no",prodProductinfo.getPlat_no());
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
        logger.info("-------------新增产品购买明细表------更新---------");
        prodShareListMapper.insert(selectOne);
        logger.info("-------------新增标的还款信息-------更新--------");
        repayMentListMapper.insert(repaymentlist);

        logger.info("-------------转账---------------" );
        try{
            accountTransferService.batchTransfer(batchEaccAccountamtList);
        }catch (BusinessException e){
            BaseResponse baseResponse=new BaseResponse();
            baseResponse.setRecode(e.getErrorCode());
            baseResponse.setRemsg(e.getErrorMsg()+":=============================================【标的还款】");
            throw new BusinessException(baseResponse);
        }

        return true;
    }

    @Override
    @Transactional
    public boolean refundToEaccount(BatchCustRepayOld custRepay, TransTransreq transTransreq) throws BusinessException {

        //还款
        logger.info("【还款】==============该用户存在电子账户，执行还款！");
        //批量转账列表
        List<EaccAccountamtlist> batchEaccAccountamtList=new ArrayList<EaccAccountamtlist>();
        ProdProductinfo prodProductinfo=prodSearchService.queryProdInfo(custRepay.getPlat_no(),custRepay.getProd_id());
        String platcust=null;
        ProdShareInall queryProdShareInall =null;
        if(ProdType.CURRENT.getCode().equals(prodProductinfo.getProd_type())){
            List<ProdShareList> prodShareLists=prodShareOptionService.queryProdShareList(prodProductinfo.getPlat_no(), prodProductinfo.getProd_id(), custRepay.getCust_no());
            if(null==prodShareLists){
                logger.info("-------------明细表里没有匹配的prod_id:" + prodProductinfo.getProd_id() + "信息，请确认---------------");
                throw new BusinessException(BusinessMsg.PARAMETER_ERROR,
                        BusinessMsg.getMsg(BusinessMsg.PARAMETER_ERROR)+"：platcust:"+custRepay.getCust_no()+"，prod_id:"+prodProductinfo.getProd_id()+"的投标信息不存在");
            }
            BigDecimal saleAmt=BigDecimal.ZERO;
            for(ProdShareList prodShareList:prodShareLists){
                if(AmtType.INCOME.getCode().equals(prodShareList.getAmt_type())){
                    saleAmt=saleAmt.add(prodShareList.getVol());
                }else{
                    saleAmt=saleAmt.subtract(prodShareList.getVol());
                }
            }
            if(saleAmt.compareTo(BigDecimal.ZERO)<=0){
                logger.info("-------------用户" + custRepay.getCust_no() + "已还清，无需还款，请确认---------------");
                throw new BusinessException(BusinessMsg.PARAMETER_ERROR,
                        BusinessMsg.getMsg(BusinessMsg.PARAMETER_ERROR)+"：platcust:"+custRepay.getCust_no()+"，prod_id:"+prodProductinfo.getProd_id()+"的投资已还清，无需再次还款");
            }
            platcust=custRepay.getCust_no();
        }else{
            queryProdShareInall = prodSearchService.queryProdShareInall(prodProductinfo.getPlat_no(), prodProductinfo.getProd_id(), custRepay.getCust_no());
            if (null == queryProdShareInall) {
                logger.info("-------------份额表里没有匹配的prod_id:" + prodProductinfo.getProd_id() + "信息，请确认---------------");
                throw new BusinessException(BusinessMsg.PARAMETER_ERROR,
                        BusinessMsg.getMsg(BusinessMsg.PARAMETER_ERROR)+"：platcust:"+custRepay.getCust_no()+"，prod_id:"+prodProductinfo.getProd_id()+"的投标信息不存在");
            }
            platcust=queryProdShareInall.getPlatcust();
        }

        logger.info("-------------体验金:" + custRepay.getExperience_amt() + "---------------");
        //体验金
        BigDecimal experience_amt = custRepay.getExperience_amt();
        //如果有体验金金额，那么减去体验金金额
        if (null != experience_amt && experience_amt.compareTo(BigDecimal.ZERO) > 0) {
            AccountSubjectInfo experiencePlatAccount = accountQueryService.queryAccount(prodProductinfo.getPlat_no(),
                    null, Tsubject.CASH.getCode(), Ssubject.EXPERIENCE.getCode());
            //体验金转账到平台体验金账户
            EaccAccountamtlist experienceAmt = new EaccAccountamtlist();
            experienceAmt.setTrans_serial(transTransreq.getTrans_serial());
            experienceAmt.setPlat_no(prodProductinfo.getPlat_no());
            experienceAmt.setPlatcust(prodProductinfo.getProd_account());
            experienceAmt.setSubject(Tsubject.CASH.getCode());
            experienceAmt.setSub_subject(Ssubject.PROD.getCode());
            experienceAmt.setOppo_plat_no(prodProductinfo.getPlat_no());
            experienceAmt.setOppo_platcust(experiencePlatAccount.getPlatcust());
            experienceAmt.setOppo_subject(experiencePlatAccount.getSubject());
            experienceAmt.setOppo_sub_subject(experiencePlatAccount.getSub_subject());
            experienceAmt.setAmt(experience_amt);
            experienceAmt.setTrans_code(transTransreq.getTrans_code());
            experienceAmt.setTrans_name(transTransreq.getTrans_name());
            experienceAmt.setTrans_date(transTransreq.getTrans_date());
            experienceAmt.setTrans_time(transTransreq.getTrans_time());
            experienceAmt.setRemark("OnlyVirtual");
            experienceAmt.setOrder_no(transTransreq.getOrder_no());
            batchEaccAccountamtList.add(experienceAmt);
        }

        logger.info("-------------加息金:" + custRepay.getRates_amt() + "---------------");
        //加息金
        BigDecimal rates_amt = custRepay.getRates_amt();
        //如果有加息金金额
        if (null != rates_amt && rates_amt.compareTo(BigDecimal.ZERO) > 0) {
            logger.info("-------------加息金:" + custRepay.getRates_amt() + "----开始转账-----------");
            AccountSubjectInfo ratesAmtAccount = accountQueryService.queryAccount(prodProductinfo.getPlat_no(),
                    null, Tsubject.CASH.getCode(), Ssubject.PLUSRATE.getCode());
            //平台加息金账户转到投资人账户
            logger.info("-------------平台加息金账户转到投资人账户---------------");
            EaccAccountamtlist ratesAmt = new EaccAccountamtlist();
            ratesAmt.setTrans_serial(transTransreq.getTrans_serial());
            ratesAmt.setPlat_no(prodProductinfo.getPlat_no());
            ratesAmt.setPlatcust(ratesAmtAccount.getPlatcust());
            ratesAmt.setSubject(Tsubject.CASH.getCode());
            ratesAmt.setSub_subject(Ssubject.PLUSRATE.getCode());
            ratesAmt.setOppo_plat_no(prodProductinfo.getPlat_no());
            ratesAmt.setOppo_platcust(platcust);
            ratesAmt.setOppo_subject(Tsubject.CASH.getCode());
            ratesAmt.setOppo_sub_subject(Ssubject.EACCOUNT.getCode());
//            ratesAmt.setOppo_sub_subject(Ssubject.INVEST.getCode());
            ratesAmt.setAmt(rates_amt);
            ratesAmt.setTrans_code(transTransreq.getTrans_code());
            ratesAmt.setTrans_name(transTransreq.getTrans_name());
            ratesAmt.setTrans_date(transTransreq.getTrans_date());
            ratesAmt.setTrans_time(transTransreq.getTrans_time());
            ratesAmt.setRemark("OnlyVirtual");
            ratesAmt.setOrder_no(transTransreq.getOrder_no());
            batchEaccAccountamtList.add(ratesAmt);
        }

        logger.info("-------------还款本金:" + custRepay.getReal_repay_amount() + "---------------");
        //还款本金
        BigDecimal real_repay_amount = custRepay.getReal_repay_amount();
//			BigDecimal real_repay_amount=custRepay.getReal_repay_amount();
        BigDecimal cust_repay_amount = BigDecimal.ZERO;
        //如果有本金金额
        if (null != real_repay_amount && real_repay_amount.compareTo(BigDecimal.ZERO) > 0) {
            //cust_repay_amount.add(real_repay_amount);
//				cust_repay_amount = new BigDecimal(real_repay_amount.intValue());
            cust_repay_amount = cust_repay_amount.add(real_repay_amount);
            logger.info("-------------计划还款本金a:" + custRepay.getReal_repay_amount() + "---------------");
        }

        //还款利息
        BigDecimal real_repay_val = BigDecimal.ZERO;
        if (custRepay.getReal_repay_val() != null) {
            real_repay_val = real_repay_val.add(custRepay.getReal_repay_val());
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
            custRepayAmt.setTrans_serial(transTransreq.getTrans_serial());
            custRepayAmt.setPlat_no(prodProductinfo.getPlat_no());
            custRepayAmt.setPlatcust(prodProductinfo.getProd_account());
            custRepayAmt.setSubject(Tsubject.CASH.getCode());
            custRepayAmt.setSub_subject(Ssubject.PROD.getCode());
            custRepayAmt.setOppo_plat_no(prodProductinfo.getPlat_no());
            custRepayAmt.setOppo_platcust(platcust);
            custRepayAmt.setOppo_subject(Tsubject.CASH.getCode());
            custRepayAmt.setOppo_sub_subject(Ssubject.EACCOUNT.getCode());
//            custRepayAmt.setOppo_sub_subject(Ssubject.INVEST.getCode());
            custRepayAmt.setTrans_code(transTransreq.getTrans_code());
            custRepayAmt.setTrans_name(transTransreq.getTrans_name());
            custRepayAmt.setTrans_date(transTransreq.getTrans_date());
            custRepayAmt.setTrans_time(transTransreq.getTrans_time());
            custRepayAmt.setRemark("OnlyVirtual");
            custRepayAmt.setOrder_no(transTransreq.getOrder_no());
            if(SomeType.SOME_INVEST_AMT.getCode().equals(custRepay.getSome_type())){
                BigDecimal real_cash_amt=custRepay.getSome_cash_amt();
                EaccAccountamtlist floatRepayAmt= new EaccAccountamtlist();
                BeanUtils.copyProperties(custRepayAmt,floatRepayAmt);
                custRepayAmt.setAmt(real_cash_amt);
                floatRepayAmt.setAmt(cust_repay_amount.subtract(real_cash_amt));
                floatRepayAmt.setSubject(Tsubject.FLOAT.getCode());
                floatRepayAmt.setOppo_subject(Tsubject.FLOAT.getCode());
                batchEaccAccountamtList.add(floatRepayAmt);
            }else{
                custRepayAmt.setAmt(cust_repay_amount);
            }
            batchEaccAccountamtList.add(custRepayAmt);

        }

        //还款本金
        real_repay_amount = custRepay.getReal_repay_amount();
        cust_repay_amount = BigDecimal.ZERO;
        //如果有本金金额
        if (null != real_repay_amount && real_repay_amount.compareTo(BigDecimal.ZERO) > 0) {
            cust_repay_amount = cust_repay_amount.add(real_repay_amount);
            logger.info("-------------计划还款本金a:" + custRepay.getReal_repay_amount() + "---------------");
        }

        //还款利息
        real_repay_val = BigDecimal.ZERO;
        if (custRepay.getReal_repay_val() != null) {
            real_repay_val = real_repay_val.add(custRepay.getReal_repay_val());
        }
        //如果有利息
        if (null != real_repay_val && real_repay_val.compareTo(BigDecimal.ZERO) > 0) {
            cust_repay_amount = cust_repay_amount.add(real_repay_val);
        }

        if (real_repay_amount == null) {
            real_repay_amount = BigDecimal.ZERO;
        }

        if (real_repay_amount == null) {
            real_repay_amount = BigDecimal.ZERO;
        }

        //更新产品购买明细表
        logger.info("-------------更新产品购买明细表---------------");
        List<ProdShareList> selectMore = prodShareOptionService.queryProdShareList(prodProductinfo.getPlat_no(), prodProductinfo.getProd_id(), custRepay.getCust_no());
        if(selectMore==null){
            logger.info("-----------未找到投资信息-----------");
            throw new BusinessException(BusinessMsg.PARAMETER_ERROR,
                    BusinessMsg.getMsg(BusinessMsg.PARAMETER_ERROR)+"：platcust:"+custRepay.getCust_no()+"，prod_id:"+prodProductinfo.getProd_id()+"的投标信息不存在");
        }
        ProdShareList selectOne=selectMore.get(0);
        selectOne.setId(null);
        selectOne.setVol(real_repay_amount);
        selectOne.setAmt_type(AmtType.EXPENSIVE.getCode());
        selectOne.setCreate_by(SeqUtil.RANDOM_KEY);
        selectOne.setCreate_time(DateUtils.today());
        selectOne.setTrans_serial(transTransreq.getTrans_serial());
        selectOne.setTrans_date(transTransreq.getTrans_date());
        selectOne.setTrans_time(transTransreq.getTrans_time());
        selectOne.setTrans_code(transTransreq.getTrans_code());

        //新增标的还款信息
        logger.info("-------------新增标的还款信息---------------");
        ProdRepaymentlist repaymentlist = new ProdRepaymentlist();
        repaymentlist.setPlat_no(prodProductinfo.getPlat_no());
        repaymentlist.setPlatcust(platcust);
        repaymentlist.setRepay_num(custRepay.getRepay_num());
        repaymentlist.setProd_id(custRepay.getProd_id());
        repaymentlist.setReal_repay_amount(custRepay.getReal_repay_amount());
        repaymentlist.setReal_repay_val(custRepay.getReal_repay_val());
        repaymentlist.setReal_repay_amt(custRepay.getReal_repay_amt());
        repaymentlist.setReal_repay_date(custRepay.getReal_repay_date());
        repaymentlist.setStatus(RepaymentStatus.SUCCPAY.getCode());
        repaymentlist.setIf_advance(AdvanceType.NOADVANCE.getCode());
        repaymentlist.setEnabled(Constants.ENABLED);
        repaymentlist.setReal_experience_amt(custRepay.getExperience_amt());
        repaymentlist.setReal_rates_amt(custRepay.getRates_amt());
        repaymentlist.setExt1(transTransreq.getTrans_serial());
        //只有定期标才更新份额表
        if(ProdType.PERIOD.getCode().equals(prodProductinfo.getProd_type())){
            logger.info("-------------更新投资人份额表-------更新--------");
//            queryProdShareInall.setTot_vol(queryProdShareInall.getTot_vol().subtract(real_repay_amount));
//            queryProdShareInall.setVol(queryProdShareInall.getVol().subtract(real_repay_amount));
//            prodShareInallMapper.updateByPrimaryKeySelective(queryProdShareInall);
            Map<String,Object> params=new HashMap<>();
            params.put("vol",real_repay_amount);
            params.put("prod_id",prodProductinfo.getProd_id());
            params.put("platcust",custRepay.getCust_no());
            params.put("plat_no",prodProductinfo.getPlat_no());
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
        logger.info("-------------新增产品购买明细表------更新---------");
        prodShareListMapper.insert(selectOne);
        logger.info("-------------新增标的还款信息-------更新--------");
        repayMentListMapper.insert(repaymentlist);

        logger.info("-------------转账---------------" );
        try{
            accountTransferService.batchTransfer(batchEaccAccountamtList);
        }catch (BusinessException e){
            BaseResponse baseResponse=new BaseResponse();
            baseResponse.setRecode(e.getErrorCode());
            baseResponse.setRemsg(e.getErrorMsg()+":=============================================【标的还款】");
            throw new BusinessException(baseResponse);
        }

        return true;
    }

    @Override
    public boolean refundForAsyn(BatchCustRepay custRepay, TransTransreq transTransReq, ProdProductinfo prodProductinfo) throws BusinessException {
        //执行还款
        List<EaccAccountamtlist> batchEaccAccountamtList=new ArrayList<EaccAccountamtlist>();
        boolean eplatcustexist = false;
        ProdShareInall queryProdShareInall =null;

        EaccUserinfo eaccUserinfo=accountQueryService.getEUserinfoByExist(custRepay.getMall_no(),custRepay.getCust_no());
        if(eaccUserinfo==null){
            logger.info(String.format("【标的还款】用户信息不存在|platcust:%s",custRepay.getCust_no()));
            throw new BusinessException(BusinessMsg.INVALID_ACCOUNT,BusinessMsg.getMsg(BusinessMsg.INVALID_ACCOUNT));
        }
        if (!StringUtils.isEmpty(eaccUserinfo.getEaccount())){
            logger.info(String.format("【标的还款】用户存在电子账户，还款到电子账户|platcust:%s",custRepay.getCust_no()));
            eplatcustexist = true;
        }

        logger.info(String.format("【标的还款】体验金:%s",custRepay.getExperience_amt()));
        //体验金
        BigDecimal experience_amt = custRepay.getExperience_amt();
        //如果有体验金金额，那么减去体验金金额
        if (null != experience_amt && experience_amt.compareTo(BigDecimal.ZERO) > 0) {
            AccountSubjectInfo experiencePlatAccount = accountQueryService.queryAccount(prodProductinfo.getPlat_no(),
                    prodProductinfo.getPlat_no(), Tsubject.CASH.getCode(), Ssubject.EXPERIENCE.getCode());
            //体验金转账到平台体验金账户
            EaccAccountamtlist experienceAmt = new EaccAccountamtlist();
            experienceAmt.setOrder_no(custRepay.getDetail_no());
            experienceAmt.setTrans_serial(transTransReq.getTrans_serial());
            experienceAmt.setPlat_no(prodProductinfo.getPlat_no());
            experienceAmt.setPlatcust(prodProductinfo.getProd_account());
            experienceAmt.setSubject(Tsubject.CASH.getCode());
            experienceAmt.setSub_subject(Ssubject.PROD.getCode());
            experienceAmt.setOppo_plat_no(prodProductinfo.getPlat_no());
            experienceAmt.setOppo_platcust(experiencePlatAccount.getPlatcust());
            experienceAmt.setOppo_subject(experiencePlatAccount.getSubject());
            experienceAmt.setOppo_sub_subject(experiencePlatAccount.getSub_subject());
            experienceAmt.setAmt(experience_amt);
            experienceAmt.setTrans_code(transTransReq.getTrans_code());
            experienceAmt.setTrans_name(transTransReq.getTrans_name());
            experienceAmt.setTrans_date(transTransReq.getTrans_date());
            experienceAmt.setTrans_time(transTransReq.getTrans_time());
            batchEaccAccountamtList.add(experienceAmt);
        }

        logger.info(String.format("【标的还款】加息金:%s",custRepay.getRates_amt()));
        //加息金
        BigDecimal rates_amt = custRepay.getRates_amt();
        //如果有加息金金额
        if (null != rates_amt && rates_amt.compareTo(BigDecimal.ZERO) > 0) {
            logger.info("-------------加息金:" + custRepay.getRates_amt() + "----开始转账-----------");
            AccountSubjectInfo ratesAmtAccount = accountQueryService.queryAccount(prodProductinfo.getPlat_no(),
                    null, Tsubject.CASH.getCode(), Ssubject.PLUSRATE.getCode());
            //平台加息金账户转到投资人账户
            logger.info("-------------平台加息金账户转到投资人账户---------------");
            EaccAccountamtlist ratesAmt = new EaccAccountamtlist();
            ratesAmt.setOrder_no(custRepay.getDetail_no());
            ratesAmt.setTrans_serial(transTransReq.getTrans_serial());
            ratesAmt.setPlat_no(prodProductinfo.getPlat_no());
            ratesAmt.setPlatcust(ratesAmtAccount.getPlatcust());
            ratesAmt.setSubject(Tsubject.CASH.getCode());
            ratesAmt.setSub_subject(Ssubject.PLUSRATE.getCode());
            ratesAmt.setOppo_plat_no(prodProductinfo.getPlat_no());
            ratesAmt.setOppo_platcust(custRepay.getCust_no());
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
            batchEaccAccountamtList.add(ratesAmt);
        }

        logger.info("-------------还款本金:" + custRepay.getReal_repay_amount() + "---------------");
        //还款本金
        BigDecimal real_repay_amount = custRepay.getReal_repay_amount();
        BigDecimal cust_repay_amount = BigDecimal.ZERO;
        //如果有本金金额
        if (null != real_repay_amount && real_repay_amount.compareTo(BigDecimal.ZERO) > 0) {
            cust_repay_amount = cust_repay_amount.add(real_repay_amount);
            logger.info("-------------计划还款本金a:" + custRepay.getReal_repay_amount() + "---------------");
        }

        //还款利息
        BigDecimal real_repay_val = BigDecimal.ZERO;
        if (custRepay.getReal_repay_val() != null) {
            real_repay_val = real_repay_val.add(custRepay.getReal_repay_val());
        }
        //如果有利息
        if (null != real_repay_val && real_repay_val.compareTo(BigDecimal.ZERO) > 0) {
            cust_repay_amount = cust_repay_amount.add(real_repay_val);
        }

        //手续费
        BigDecimal repay_fee = custRepay.getRepay_fee();
        //如果有手续费金额，那么减去手续费金额
        if (null != repay_fee && repay_fee.compareTo(BigDecimal.ZERO) > 0) {
            cust_repay_amount = cust_repay_amount.subtract(repay_fee);
            logger.info("-------------计算还款金额b:" + cust_repay_amount + "---------------");
        }

        logger.info("-------------手续费:" + repay_fee + "---------------");
        if (null != repay_fee && repay_fee.compareTo(BigDecimal.ZERO) > 0) {
            logger.info("-------------手续费:" + repay_fee + "----开始转账-----------");
            AccountSubjectInfo repayFeeAccount = accountQueryService.queryAccount(prodProductinfo.getPlat_no(),
                    prodProductinfo.getPlat_no(), Tsubject.CASH.getCode(), Ssubject.FEE.getCode());
            logger.info("-------------手续费---------------");
            EaccAccountamtlist repayFee = new EaccAccountamtlist();
            repayFee.setOrder_no(custRepay.getDetail_no());
            repayFee.setTrans_serial(transTransReq.getTrans_serial());
            repayFee.setPlat_no(prodProductinfo.getPlat_no());
            repayFee.setPlatcust(prodProductinfo.getProd_account());
            repayFee.setSubject(Tsubject.CASH.getCode());
            repayFee.setSub_subject(Ssubject.PROD.getCode());
            repayFee.setOppo_plat_no(prodProductinfo.getPlat_no());
            repayFee.setOppo_platcust(repayFeeAccount.getPlatcust());
            repayFee.setOppo_subject(repayFeeAccount.getSubject());
            repayFee.setOppo_sub_subject(repayFeeAccount.getSub_subject());
            repayFee.setAmt(repay_fee);
            repayFee.setTrans_code(transTransReq.getTrans_code());
            repayFee.setTrans_name(transTransReq.getTrans_name());
            repayFee.setTrans_date(transTransReq.getTrans_date());
            repayFee.setTrans_time(transTransReq.getTrans_time());
//            repayFee.setRemark("OnlyVirtual");
            batchEaccAccountamtList.add(repayFee);
        }

        logger.info("-------------还款实际金额:" + cust_repay_amount + "---------------");
        //还款实际金额
        if (cust_repay_amount.compareTo(BigDecimal.ZERO) > 0) {
            logger.info("-------------还款实际金额:" + cust_repay_amount + "-------开始转账--------");
            EaccAccountamtlist custRepayAmt = new EaccAccountamtlist();
            custRepayAmt.setOrder_no(custRepay.getDetail_no());
            custRepayAmt.setTrans_serial(transTransReq.getTrans_serial());
            custRepayAmt.setPlat_no(prodProductinfo.getPlat_no());
            custRepayAmt.setPlatcust(prodProductinfo.getProd_account());
            custRepayAmt.setSubject(Tsubject.CASH.getCode());
            custRepayAmt.setSub_subject(Ssubject.PROD.getCode());
            custRepayAmt.setOppo_plat_no(prodProductinfo.getPlat_no());
            custRepayAmt.setOppo_platcust(custRepay.getCust_no());
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
        if(ProdType.PERIOD.getCode().equals(prodProductinfo.getProd_type())){
            queryProdShareInall = prodSearchService.queryProdShareInall(prodProductinfo.getPlat_no(), prodProductinfo.getProd_id(), custRepay.getCust_no());
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
        List<ProdShareList> selectMore = prodShareOptionService.queryProdShareList(prodProductinfo.getPlat_no(), prodProductinfo.getProd_id(), custRepay.getCust_no());
        if(selectMore==null){
            logger.info("【标的还款】========未找到投资信息");
            throw new BusinessException(BusinessMsg.PARAMETER_ERROR,
                    BusinessMsg.getMsg(BusinessMsg.PARAMETER_ERROR)+"：platcust:"+custRepay.getCust_no()+"，prod_id:"+prodProductinfo.getProd_id()+"的投标信息不存在");
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
        repaymentlist.setPlat_no(prodProductinfo.getPlat_no());
        repaymentlist.setPlatcust(custRepay.getCust_no());
        repaymentlist.setRepay_num(custRepay.getRepay_num());
        repaymentlist.setProd_id(custRepay.getProd_id());
        repaymentlist.setReal_repay_amount(custRepay.getReal_repay_amount());
        repaymentlist.setReal_repay_val(custRepay.getReal_repay_val());
        repaymentlist.setReal_repay_amt(custRepay.getReal_repay_amt());
        repaymentlist.setReal_repay_date(custRepay.getReal_repay_date());
        repaymentlist.setStatus(RepaymentStatus.SUCCPAY.getCode());
        repaymentlist.setIf_advance(AdvanceType.NOADVANCE.getCode());
        repaymentlist.setEnabled(Constants.ENABLED);
        repaymentlist.setReal_experience_amt(custRepay.getExperience_amt());
        repaymentlist.setReal_rates_amt(custRepay.getRates_amt());
        repaymentlist.setExt1(transTransReq.getTrans_serial());
        productRefundDBOptionService.doRefundDBOption(prodProductinfo,real_repay_amount,custRepay.getCust_no(),selectOne,repaymentlist);

        logger.info("【标的还款】========开始转账,参数："+ JSON.toJSON(batchEaccAccountamtList));
            try{
                //调用新转账接口
                BaseRequest baseRequest=new BaseRequest();
                baseRequest.setMer_no(custRepay.getPlat_no());
                baseRequest.setMall_no(custRepay.getMall_no());
                baseRequest.setPartner_trans_date(custRepay.getPartner_trans_date());
                baseRequest.setPartner_trans_time(custRepay.getPartner_trans_time());
                baseRequest.setOrder_no(custRepay.getDetail_no());
                transferService.transfer(baseRequest,batchEaccAccountamtList);
            }catch (BusinessException e){
                //回滚还款数据
                logger.error("【标的还款】========转账失败："+e.getErrorMsg());
                productRefundDBOptionService.backProdRefund(transTransReq);
                BaseResponse baseResponse=e.getBaseResponse();
                throw new BusinessException(baseResponse);
            }
        logger.info("【标的还款】========还款成功");
        return true;
    }
}
