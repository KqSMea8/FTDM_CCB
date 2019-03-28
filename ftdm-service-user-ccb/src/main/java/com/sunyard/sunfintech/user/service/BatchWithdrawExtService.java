package com.sunyard.sunfintech.user.service;


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
import com.sunyard.sunfintech.core.util.StringUtils;
import com.sunyard.sunfintech.dao.entity.*;
import com.sunyard.sunfintech.dao.mapper.RwWithdrawMapper;
import com.sunyard.sunfintech.pub.provider.ITransReqService;
import com.sunyard.sunfintech.user.model.bo.DateDetail;
import com.sunyard.sunfintech.user.model.bo.SuccessData;
import com.sunyard.sunfintech.user.provider.IAccountSearchService;
import com.sunyard.sunfintech.user.provider.IBatchWithdrawExtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by PengZY on 2017/7/10.
 */
@CacheConfig(cacheNames="batchWithdrawExtService")
@org.springframework.stereotype.Service
public class BatchWithdrawExtService extends BaseServiceSimple implements IBatchWithdrawExtService {


    @Autowired
    private ITransReqService transReqService;

    @Autowired
    private IAccountSearchService accountSearchService;

    @Autowired
    private RwWithdrawMapper rwWithdrawMapper;

    @Autowired
    private IAccountQueryService accountQueryService;

    @Autowired
    private IAccountTransferService accountTransferService;


    /**
     * 批量提现
     * @param plat_account   手续费现金账户
     * @param plat_account_inline   行内垫资现金账户
     * @param plat_account_inline_float   行内垫资在途账户
     * @param dateDetail   批量订单明细
     * @return SuccessData
     * @author pzy
     */
    @Override
    public SuccessData transfer(AccountSubjectInfo plat_account, AccountSubjectInfo plat_account_float, AccountSubjectInfo plat_account_inline, AccountSubjectInfo plat_account_inline_float, DateDetail dateDetail, BaseRequest baseRequest){

        logger.info("【批量提现】-->dubbo-->detail_no:" + dateDetail.getDetail_no());

        SuccessData successData = new SuccessData();

        validate(dateDetail);

        //记录业务流水表
        TransTransreq transTransReq = transReqService.getTransTransReq(baseRequest.clone(), TransConsts.BATCH_WITHDRAW_CODE, TransConsts.BATCH_WITHDRAW_NAME, true);
        transTransReq.setOrder_no(dateDetail.getDetail_no());
        transReqService.insert(transTransReq);

        try{

            if(!CardType.DEBIT.getCode().equals(dateDetail.getCard_type())
                    && !CardType.CREDIT.getCode().equals(dateDetail.getCard_type()))
                throw new BusinessException(BusinessMsg.PARAMETER_ERROR, BusinessMsg.getMsg(BusinessMsg.PARAMETER_ERROR) + ",卡类型有误");
            logger.info("【批量提现】-->卡类型正常-->detail_no:" + dateDetail.getDetail_no());


            //查询出平台的中间账户  用于普通转账
            AccountSubjectInfo account_middle = accountQueryService.queryAccount(baseRequest.getMer_no(),null, Tsubject.CASH.getCode(), dateDetail.getPay_code());
            if (account_middle == null) {
                logger.info("【批量提现】-->平台中间账户不存在-->detail_no:" + dateDetail.getDetail_no());
                throw new BusinessException(BusinessMsg.INVALID_ACCOUNT, BusinessMsg.getMsg(BusinessMsg.INVALID_ACCOUNT) + ",缺少平台中间户账户");
            }
            logger.info("【批量提现】-->平台中间账户存在-->detail_no:" + dateDetail.getDetail_no());

            //检查用户绑卡是否存在
            EaccCardinfo eaccCardInfo = accountSearchService.queryEaccCardInfoByEaccAccountInfo(baseRequest.getMall_no(), baseRequest.getMer_no(), dateDetail.getPlatcust(),dateDetail.getCard_no(),null);
            if (eaccCardInfo == null) {
                logger.info("【批量提现】-->绑卡账号不存在" + "用户账户号：" + dateDetail.getPlatcust());
                throw new BusinessException(BusinessMsg.INVALID_ACCOUNT, BusinessMsg.getMsg(BusinessMsg.INVALID_ACCOUNT) + "绑卡账号不存在" + "用户账户号：" + dateDetail.getPlatcust());
            }
            logger.info("【批量提现】-->用户绑卡信息存在-->detail_no:" + dateDetail.getDetail_no());


            //检查用户信息是否存在
            EaccUserinfo eaccUserInfo = accountSearchService.queryEaccUserInfoByEaccAccountInfo(baseRequest.getMall_no(), baseRequest.getMer_no(), dateDetail.getPlatcust());
            if (eaccUserInfo == null) {
                logger.info("【批量提现】-->用户账号不存在" + "-->用户账户号：" + dateDetail.getPlatcust()+"-->detail_no:" + dateDetail.getDetail_no());
                throw new BusinessException(BusinessMsg.INVALID_ACCOUNT, BusinessMsg.getMsg(BusinessMsg.INVALID_ACCOUNT) + "用户账号不存在" + "用户账户号：" + dateDetail.getPlatcust());
            }
            logger.info("【批量提现】-->用户信息存在-->detail_no:" + dateDetail.getDetail_no());

            logger.info("【批量提现】-->查询虚拟账户-->detail_no:" + dateDetail.getDetail_no());
            //用户现金账户
            AccountSubjectInfo accountSubjectInfo = accountQueryService.queryAccount(baseRequest.getMer_no(), dateDetail.getPlatcust(), Tsubject.CASH.getCode(), dateDetail.getWithdraw_type());
            //用户在途账户
            AccountSubjectInfo accountSubjectInfo_float = accountQueryService.queryAccount(baseRequest.getMer_no(), dateDetail.getPlatcust(), Tsubject.FLOAT.getCode(), dateDetail.getWithdraw_type());

            if (accountSubjectInfo == null || accountSubjectInfo_float == null) {
                logger.info("【批量提现】-->账户明细不存在-->用户账户号：" + dateDetail.getPlatcust()+"-->detail_no:" + dateDetail.getDetail_no());
                throw new BusinessException(BusinessMsg.INVALID_ACCOUNT, BusinessMsg.getMsg(BusinessMsg.INVALID_ACCOUNT) + "账户明细不存在" + "用户账户号：" + dateDetail.getPlatcust());
            }
            logger.info("【批量提现】-->账户明细账户信息存在-->detail_no:" + dateDetail.getDetail_no());

            //查询平台映射信息
            PlatPaycode platPayCode = accountSearchService.queryPlatPayCode(baseRequest.getMer_no(), dateDetail.getPay_code());
            if (platPayCode == null) {
                logger.info("【批量提现】-->支付平台映射信息不存在" + "客户编号：" + baseRequest.getMer_no() + "支付通道：" + dateDetail.getPay_code());
                throw new BusinessException(BusinessMsg.DATEBASE_EXCEPTION, BusinessMsg.getMsg(BusinessMsg.DATEBASE_EXCEPTION) + "支付平台映射信息不存在" + "客户编号："
                        + baseRequest.getMer_no() + "支付通道：" + dateDetail.getPay_code());
            }
            logger.info("【批量提现】-->平台映射信息存在-->detail_no:" + dateDetail.getDetail_no());

            BigDecimal bigDecimal_c =  accountSubjectInfo_float.getN_balance().add(accountSubjectInfo.getN_balance());
            BigDecimal bigDecimal_s = dateDetail.getAmt();
            if(dateDetail.getFee_amt() != null && dateDetail.getFee_amt().compareTo(BigDecimal.ZERO) > 0){
                bigDecimal_s = bigDecimal_s.add(dateDetail.getFee_amt());
            }

            if(bigDecimal_c.compareTo(bigDecimal_s) == -1){
                logger.info("【批量提现】-->账户余额不足，在途和现金的总余额不够-->detail_no:" + dateDetail.getDetail_no());
                throw new BusinessException(BusinessMsg.BALANCE_LACK, BusinessMsg.getMsg(BusinessMsg.BALANCE_LACK));
            }
            logger.info("【批量提现】-->账户总余额足够-->detail_no:" + dateDetail.getDetail_no());

            List<EaccAccountamtlist> eaccAccountamtlists = new ArrayList<EaccAccountamtlist>();

            //判断垫付状态     行内垫付
            logger.info("【批量提现】-->判断垫付类型-->detail_no:" + dateDetail.getDetail_no());
            if(dateDetail.getIs_advance().equals(AdvanceType.INLINEADVANCE.getCode())){
                logger.info("【批量提现】-->行内垫付-->detail_no:" + dateDetail.getDetail_no());

                //如果有手续费
                if(dateDetail.getFee_amt().compareTo(BigDecimal.ZERO) > 0){
                    logger.info("【批量提现】-->行内垫付有手续费-->detail_no:" + dateDetail.getDetail_no());

                    //如果账户现金资金充足
                    if(accountSubjectInfo.getN_balance().compareTo(bigDecimal_s) >= 0){

                        logger.info("【批量提现】-->行内垫付时现金资金充足-->detail_no:" + dateDetail.getDetail_no());

                        logger.info("【批量提现】-->转账手续费金额到手续费账户-->detail_no:" + dateDetail.getDetail_no());
                        //转账手续费金额到手续费账户
                        eaccAccountamtlists.add(eaccAccountamtlistTransfer(accountSubjectInfo,plat_account,transTransReq,dateDetail.getFee_amt(),dateDetail.getPay_code(),"批量提现：转账手续费金额到手续费账户"));

                        logger.info("【批量提现】-->转账提现金额到集团行内账户-->detail_no:" + dateDetail.getDetail_no());
                        //转账提现金额到集团行内账户
                        eaccAccountamtlists.add(eaccAccountamtlistTransfer(accountSubjectInfo,account_middle,transTransReq,dateDetail.getAmt(),dateDetail.getPay_code(),"批量提现：转账提现金额到集团行内账户"));

                        successData.setAdvance_amt(BigDecimal.ZERO);
                        dateDetail.setIs_advance(AdvanceType.NOADVANCE.getCode());

                        //当账户资金大于提现金额  小于提现金加手续费时
                    }else if(accountSubjectInfo.getN_balance().compareTo(dateDetail.getAmt()) > 0 && accountSubjectInfo.getN_balance().compareTo(bigDecimal_s) < 0) {

                        logger.info("【批量提现】-->当 现金资金 > 提现金额 , 现金资金 < 提现金额 + 手续费出一半时-->detail_no:" + dateDetail.getDetail_no());

                        logger.info("【批量提现】-->转账一半手续费金额到手续费账户  现金-->detail_no:" + dateDetail.getDetail_no());
                        //先转一半现金手续费手续费账户
                        eaccAccountamtlists.add(eaccAccountamtlistTransfer(accountSubjectInfo,plat_account,transTransReq,accountSubjectInfo.getN_balance().subtract(dateDetail.getAmt()),dateDetail.getPay_code(),"批量提现：转账手续费另一半到手续费账户 现金"));

                        logger.info("【批量提现】-->转账一半手续费金额到手续费账户  在途-->detail_no:" + dateDetail.getDetail_no());
                        //转账手续费到手续费账户
                        eaccAccountamtlists.add(eaccAccountamtlistTransfer(accountSubjectInfo_float,plat_account_float,transTransReq,bigDecimal_s.subtract(accountSubjectInfo.getN_balance()),dateDetail.getPay_code(),"批量提现：转账手续费另一半到手续费账户 在途"));

                        logger.info("【批量提现】-->转账提现金额到集团行内账户-->detail_no:" + dateDetail.getDetail_no());
                        //转账提现金额到集团行内账户
                        eaccAccountamtlists.add(eaccAccountamtlistTransfer(accountSubjectInfo,account_middle,transTransReq,dateDetail.getAmt(),dateDetail.getPay_code(),"批量提现：转账提现金额到集团行内账户"));

                        successData.setAdvance_amt(BigDecimal.ZERO);
                        dateDetail.setIs_advance(AdvanceType.NOADVANCE.getCode());


                    //当账户资金等于提现金额  小于提现金加手续费时
                    }else if(accountSubjectInfo.getN_balance().compareTo(dateDetail.getAmt()) == 0  && accountSubjectInfo.getN_balance().compareTo(bigDecimal_s) < 0) {

                        logger.info("【批量提现】-->当资金刚好够提现金额，手续费在途出的时候-->detail_no:" + dateDetail.getDetail_no());

                        logger.info("【批量提现】-->转账手续费到手续费账户  在途-->detail_no:" + dateDetail.getDetail_no());
                        //转账手续费到手续费账户
                        eaccAccountamtlists.add(eaccAccountamtlistTransfer(accountSubjectInfo_float,plat_account_float,transTransReq,bigDecimal_s.subtract(accountSubjectInfo.getN_balance()),dateDetail.getPay_code(),"批量提现：转账手续费到手续费账户 在途"));

                        logger.info("【批量提现】-->转账提现金额到集团行内账户 现金-->detail_no:" + dateDetail.getDetail_no());
                        //转账提现金额到集团行内账户
                        eaccAccountamtlists.add(eaccAccountamtlistTransfer(accountSubjectInfo,account_middle,transTransReq,dateDetail.getAmt(),dateDetail.getPay_code(),"批量提现：转账提现金额到集团行内账户 现金"));

                        successData.setAdvance_amt(BigDecimal.ZERO);
                        dateDetail.setIs_advance(AdvanceType.NOADVANCE.getCode());

                    }else{
                        logger.info("【批量提现】-->行内垫付时现金资金不充足，垫付出金-->detail_no:" + dateDetail.getDetail_no());

                        logger.info("【批量提现】-->转账手续费到手续费账户-->detail_no:" + dateDetail.getDetail_no());
                        //转账手续费到手续费账户
                        eaccAccountamtlists.add(eaccAccountamtlistTransfer(accountSubjectInfo_float,plat_account_float,transTransReq,dateDetail.getFee_amt(),null,"批量提现：转账手续费在途到手续费在途账户 在途"));

                        logger.info("【批量提现】-->用户在途金额转行内垫付在途-->detail_no:" + dateDetail.getDetail_no());
                        //用户在途金额转行内垫付在途
                        eaccAccountamtlists.add(eaccAccountamtlistTransfer(accountSubjectInfo_float,plat_account_inline_float,transTransReq,dateDetail.getAmt().subtract(accountSubjectInfo.getN_balance()),null,"批量提现：用户在途金额转行内垫付在途 在途"));

                        logger.info("【批量提现】-->行内垫付现金转用户现金-->detail_no:" + dateDetail.getDetail_no());
                        //行内垫付现金转用户现金
                        eaccAccountamtlists.add(eaccAccountamtlistTransfer(plat_account_inline,accountSubjectInfo,transTransReq,dateDetail.getAmt().subtract(accountSubjectInfo.getN_balance()),null,"批量提现：行内垫付现金转用户现金"));

                        logger.info("【批量提现】-->转账提现金额到集团行内账户-->detail_no:" + dateDetail.getDetail_no());
                        //转账提现金额到集团行内账户
                        eaccAccountamtlists.add(eaccAccountamtlistTransfer(accountSubjectInfo,account_middle,transTransReq,dateDetail.getAmt(),dateDetail.getPay_code(),"批量提现：转账提现金额到中间账户"));

                        successData.setAdvance_amt(dateDetail.getAmt().subtract(accountSubjectInfo.getN_balance()));

                    }


                }else {//如果没有手续费
                    logger.info("【批量提现】-->行内垫付无手续费-->detail_no:" + dateDetail.getDetail_no());

                    //如果账户现金资金充足
                    if(accountSubjectInfo.getN_balance().compareTo(bigDecimal_s) >= 0){

                        logger.info("【批量提现】-->行内垫付现金资金充足-->detail_no:" + dateDetail.getDetail_no());
                        //转账提现金额到中间账户
                        eaccAccountamtlists.add(eaccAccountamtlistTransfer(accountSubjectInfo,account_middle,transTransReq,dateDetail.getAmt(),dateDetail.getPay_code(),"批量提现：转账提现金额到中间账户"));

                        successData.setAdvance_amt(BigDecimal.ZERO);
                        dateDetail.setIs_advance(AdvanceType.NOADVANCE.getCode());

                    }else{
                        logger.info("【批量提现】-->行内垫付现金资金不充足，垫付出金-->detail_no:" + dateDetail.getDetail_no());

                        logger.info("【批量提现】-->用户在途金额转行内垫付在途-->detail_no:" + dateDetail.getDetail_no());
                        //用户在途金额转平台在途
                        eaccAccountamtlists.add(eaccAccountamtlistTransfer(accountSubjectInfo_float,plat_account_inline_float,transTransReq,bigDecimal_s.subtract(accountSubjectInfo.getN_balance()),dateDetail.getPay_code(),"批量提现：用户在途金额转行内垫付 在途"));

                        logger.info("【批量提现】-->行内垫付现金转用户现金-->detail_no:" + dateDetail.getDetail_no());
                        //平台现金转用户现金
                        eaccAccountamtlists.add(eaccAccountamtlistTransfer(plat_account_inline,accountSubjectInfo,transTransReq,bigDecimal_s.subtract(accountSubjectInfo.getN_balance()),dateDetail.getPay_code(),"批量提现：行内垫付现金转用户 现金"));

                        logger.info("【批量提现】-->转账提现金额到集团行内账户-->detail_no:" + dateDetail.getDetail_no());
                        //转账提现金额到中间账户
                        eaccAccountamtlists.add(eaccAccountamtlistTransfer(accountSubjectInfo,account_middle,transTransReq,dateDetail.getAmt(),dateDetail.getPay_code(),"批量提现：转账提现金额到集团行内账户"));


                        successData.setAdvance_amt(bigDecimal_s.subtract(accountSubjectInfo.getN_balance()));
                    }
                }
            }else if(dateDetail.getIs_advance().equals(AdvanceType.TOADVANCE.getCode())){//普通垫资

                logger.info("【批量提现】-->普通垫付-->detail_no:" + dateDetail.getDetail_no());

                /*if(StringUtils.isBlank(dateDetail.getAdvance_platcust())){
                    logger.info("【批量提现】-->垫资账户为空-->detail_no:" + dateDetail.getDetail_no());
                    throw new BusinessException(BusinessMsg.PARAMETER_LACK, BusinessMsg.getMsg(BusinessMsg.PARAMETER_LACK)+ ",垫资账户不能为空");
                }*/
                AccountSubjectInfo plat_account_d = accountQueryService.queryAccount(baseRequest.getMer_no(), baseRequest.getMer_no(), Tsubject.CASH.getCode(), Ssubject.PAYMENT.getCode());
                AccountSubjectInfo plat_account_d_f = accountQueryService.queryAccount(baseRequest.getMer_no(), baseRequest.getMer_no(), Tsubject.FLOAT.getCode(), Ssubject.PAYMENT.getCode());
                if(plat_account_d == null || plat_account_d_f == null){
                    logger.info("【批量提现】-->垫资账户不存在-->advance_platcust:"+baseRequest.getMer_no()+"-->detail_no:" + dateDetail.getDetail_no());
                    throw new BusinessException(BusinessMsg.INVALID_ACCOUNT, BusinessMsg.getMsg(BusinessMsg.INVALID_ACCOUNT)+ ",垫资账户不存在,advance_platcust:" + baseRequest.getMer_no());
                }

                //如果有手续费
                if(dateDetail.getFee_amt().compareTo(BigDecimal.ZERO) > 0){
                    logger.info("【批量提现】-->普通垫付有手续费-->detail_no:" + dateDetail.getDetail_no());
                    //如果账户现金资金充足
                    if(accountSubjectInfo.getN_balance().compareTo(bigDecimal_s) >= 0){

                        logger.info("【批量提现】-->普通垫付时现金资金充足-->detail_no:" + dateDetail.getDetail_no());

                        logger.info("【批量提现】-->转账手续费金额到手续费账户-->detail_no:" + dateDetail.getDetail_no());
                        //转账手续费金额到手续费账户
                        eaccAccountamtlists.add(eaccAccountamtlistTransfer(accountSubjectInfo,plat_account,transTransReq,dateDetail.getFee_amt(),null,"批量提现：转账手续费金额到手续费账户"));

                        logger.info("【批量提现】-->转账提现金额到中间账户-->detail_no:" + dateDetail.getDetail_no());
                        //转账提现金额到中间账户
                        eaccAccountamtlists.add(eaccAccountamtlistTransfer(accountSubjectInfo,account_middle,transTransReq,dateDetail.getAmt(),dateDetail.getPay_code(),"批量提现：转账提现金额到中间账户"));

                        successData.setAdvance_amt(BigDecimal.ZERO);
                        dateDetail.setIs_advance(AdvanceType.NOADVANCE.getCode());

                    //当账户资金大于提现金额  小于提现金加手续费时
                    }else if(accountSubjectInfo.getN_balance().compareTo(dateDetail.getAmt()) > 0 && accountSubjectInfo.getN_balance().compareTo(bigDecimal_s) < 0) {

                        logger.info("【批量提现】-->当 现金资金 > 提现金额  现金 < 提现金额 + 手续费出一半时-->detail_no:" + dateDetail.getDetail_no());

                        logger.info("【批量提现】-->转账一半手续费金额到手续费账户  现金-->detail_no:" + dateDetail.getDetail_no());
                        //先转一半现金手续费手续费账户
                        eaccAccountamtlists.add(eaccAccountamtlistTransfer(accountSubjectInfo,plat_account,transTransReq,accountSubjectInfo.getN_balance().subtract(dateDetail.getAmt()),null,"批量提现：转账手续费另一半到手续费账户 现金"));

                        logger.info("【批量提现】-->转账一半手续费金额到手续费账户  在途-->detail_no:" + dateDetail.getDetail_no());
                        //转账手续费到手续费账户
                        eaccAccountamtlists.add(eaccAccountamtlistTransfer(accountSubjectInfo_float,plat_account_float,transTransReq,bigDecimal_s.subtract(accountSubjectInfo.getN_balance()),null,"批量提现：转账手续费另一半到手续费账户"));

                        logger.info("【批量提现】-->转账提现金额到中间账户-->detail_no:" + dateDetail.getDetail_no());
                        //转账提现金额到集团行内账户
                        eaccAccountamtlists.add(eaccAccountamtlistTransfer(accountSubjectInfo,account_middle,transTransReq,dateDetail.getAmt(),dateDetail.getPay_code(),"批量提现：转账提现金额到中间账户"));

                        successData.setAdvance_amt(BigDecimal.ZERO);
                        dateDetail.setIs_advance(AdvanceType.NOADVANCE.getCode());


                        //当账户资金等于提现金额  小于提现金加手续费时
                    }else if(accountSubjectInfo.getN_balance().compareTo(dateDetail.getAmt()) == 0  && accountSubjectInfo.getN_balance().compareTo(bigDecimal_s) < 0) {

                        logger.info("【批量提现】-->当资金刚好够提现金额，手续费在途出的时候-->detail_no:" + dateDetail.getDetail_no());

                        logger.info("【批量提现】-->转账手续费到手续费账户  在途-->detail_no:" + dateDetail.getDetail_no());
                        //转账手续费到手续费账户
                        eaccAccountamtlists.add(eaccAccountamtlistTransfer(accountSubjectInfo_float,plat_account_float,transTransReq,bigDecimal_s.subtract(accountSubjectInfo.getN_balance()),null,"批量提现：转账手续费到手续费账户 在途"));

                        logger.info("【批量提现】-->转账提现金额到中间账户 现金-->detail_no:" + dateDetail.getDetail_no());
                        //转账提现金额到集团行内账户
                        eaccAccountamtlists.add(eaccAccountamtlistTransfer(accountSubjectInfo,account_middle,transTransReq,dateDetail.getAmt(),dateDetail.getPay_code(),"批量提现：转账提现金额到集团行内账户 现金"));

                        successData.setAdvance_amt(BigDecimal.ZERO);
                        dateDetail.setIs_advance(AdvanceType.NOADVANCE.getCode());

                    }else{
                        logger.info("【批量提现】-->行内垫付时现金资金不充足，垫付出金-->detail_no:" + dateDetail.getDetail_no());

                        logger.info("【批量提现】-->转账手续费到手续费账户-->detail_no:" + dateDetail.getDetail_no());
                        //转账手续费到手续费账户
                        eaccAccountamtlists.add(eaccAccountamtlistTransfer(accountSubjectInfo_float,plat_account_float,transTransReq,dateDetail.getFee_amt(),null,"批量提现：转账手续费在途到手续费在途账户"));

                        logger.info("【批量提现】-->用户在途金额转垫付账户在途-->detail_no:" + dateDetail.getDetail_no());
                        //用户在途金额转行内垫付在途
                        eaccAccountamtlists.add(eaccAccountamtlistTransfer(accountSubjectInfo_float,plat_account_d_f,transTransReq,dateDetail.getAmt().subtract(accountSubjectInfo.getN_balance()),null,"批量提现：用户在途金额转平台在途"));

                        logger.info("【批量提现】-->垫资账户现金转用户现金-->detail_no:" + dateDetail.getDetail_no());
                        //行内垫付现金转用户现金
                        eaccAccountamtlists.add(eaccAccountamtlistTransfer(plat_account_d,accountSubjectInfo,transTransReq,dateDetail.getAmt().subtract(accountSubjectInfo.getN_balance()),null,"批量提现：垫资用户现金转用户现金"));

                        logger.info("【批量提现】-->转账提现金额到中间账户-->detail_no:" + dateDetail.getDetail_no());
                        //转账提现金额到集团行内账户
                        eaccAccountamtlists.add(eaccAccountamtlistTransfer(accountSubjectInfo,account_middle,transTransReq,dateDetail.getAmt(),dateDetail.getPay_code(),"批量提现：转账提现金额到中间账户"));

                        successData.setAdvance_amt(dateDetail.getAmt().subtract(accountSubjectInfo.getN_balance()));

                    }
                }else {//如果没有手续费
                    logger.info("【批量提现】-->普通垫付无手续费-->detail_no:" + dateDetail.getDetail_no());

                    //如果账户现金资金充足
                    if(accountSubjectInfo.getN_balance().compareTo(bigDecimal_s) >= 0){

                        logger.info("【批量提现】-->普通垫付现金资金充足-->detail_no:" + dateDetail.getDetail_no());
                        //转账提现金额到中间账户
                        eaccAccountamtlists.add(eaccAccountamtlistTransfer(accountSubjectInfo,account_middle,transTransReq,dateDetail.getAmt(),dateDetail.getPay_code(),"批量提现：转账提现金额到中间账户"));

                        successData.setAdvance_amt(BigDecimal.ZERO);
                        dateDetail.setIs_advance(AdvanceType.NOADVANCE.getCode());

                    }else{
                        logger.info("【批量提现】-->普通垫付现金资金不充足，垫付出金-->detail_no:" + dateDetail.getDetail_no());

                        logger.info("【批量提现】-->用户在途金额转垫资账户在途-->detail_no:" + dateDetail.getDetail_no());
                        //用户在途金额转平台在途
                        eaccAccountamtlists.add(eaccAccountamtlistTransfer(accountSubjectInfo_float,plat_account_d_f,transTransReq,bigDecimal_s.subtract(accountSubjectInfo.getN_balance()),null,"批量提现：用户在途金额转平台在途"));

                        logger.info("【批量提现】-->垫资账户现金转用户现金-->detail_no:" + dateDetail.getDetail_no());
                        //平台现金转用户现金
                        eaccAccountamtlists.add(eaccAccountamtlistTransfer(plat_account_d,accountSubjectInfo,transTransReq,bigDecimal_s.subtract(accountSubjectInfo.getN_balance()),null,"批量提现：平台现金转用户现金"));

                        logger.info("【批量提现】-->转账提现金额到中间账户-->detail_no:" + dateDetail.getDetail_no());
                        //转账提现金额到中间账户
                        eaccAccountamtlists.add(eaccAccountamtlistTransfer(accountSubjectInfo,account_middle,transTransReq,dateDetail.getAmt(),dateDetail.getPay_code(),"批量提现：转账提现金额到中间账户"));

                        successData.setAdvance_amt(bigDecimal_s.subtract(accountSubjectInfo.getN_balance()));

                    }

                }
            }else if(dateDetail.getIs_advance().equals(AdvanceType.NOADVANCE.getCode())){ //如果不需要垫付
                logger.info("【批量提现】-->不需要垫付-->detail_no:" + dateDetail.getDetail_no());

                //如果有手续费
                if(dateDetail.getFee_amt().compareTo(BigDecimal.ZERO) > 0){
                    logger.info("【批量提现】-->不需要垫付有手续费-->detail_no:" + dateDetail.getDetail_no());

                    //如果用户账户现金充足
                    if(accountSubjectInfo.getN_balance().compareTo(bigDecimal_s) >= 0 ){
                        logger.info("【批量提现】-->不需要垫付现金充足-->detail_no:" + dateDetail.getDetail_no());

                        logger.info("【批量提现】-->转账手续费金额到手续费账户-->detail_no:" + dateDetail.getDetail_no());
                        //转账手续费金额到手续费账户
                        eaccAccountamtlists.add(eaccAccountamtlistTransfer(accountSubjectInfo,plat_account,transTransReq,dateDetail.getFee_amt(),null,"批量提现：转账手续费金额到手续费账户"));

                        logger.info("【批量提现】-->转账提现金额到中间账户-->detail_no:" + dateDetail.getDetail_no());
                        //转账提现金额到中间账户
                        eaccAccountamtlists.add(eaccAccountamtlistTransfer(accountSubjectInfo,account_middle,transTransReq,dateDetail.getAmt(),dateDetail.getPay_code(),"批量提现：转账提现金额到中间账户"));

                        successData.setAdvance_amt(BigDecimal.ZERO);

                    } else{

                        logger.info("【批量提现】-->不需要垫付，现金资金不足，直接抛异常-->detail_no:" + dateDetail.getDetail_no());

                        throw new BusinessException(BusinessMsg.BALANCE_LACK, BusinessMsg.getMsg(BusinessMsg.BALANCE_LACK)+ "，不需要垫付时， 账户现金不足");

                    }

                }else{

                    logger.info("【批量提现】-->不需要垫付，无手续费-->detail_no:" + dateDetail.getDetail_no());

                    //如果用户账户现金充足
                    if(accountSubjectInfo.getN_balance().compareTo(bigDecimal_s) >= 0 ){

                        logger.info("【批量提现】-->不需要垫付，资金充足-->detail_no:" + dateDetail.getDetail_no());
                        //转账提现金额到中间账户
                        eaccAccountamtlists.add(eaccAccountamtlistTransfer(accountSubjectInfo,account_middle,transTransReq,dateDetail.getAmt(),dateDetail.getPay_code(),"批量提现：转账提现金额到中间账户"));

                        successData.setAdvance_amt(BigDecimal.ZERO);

                    }else{//用户现金不足

                        logger.info("【批量提现】-->不需要垫付，资金不足，直接抛异常-->detail_no:" + dateDetail.getDetail_no());

                        throw new BusinessException(BusinessMsg.BALANCE_LACK, BusinessMsg.getMsg(BusinessMsg.BALANCE_LACK)+ "，不需要垫付时，账户现金不足");
                    }

                }

            }else{

                logger.info("【批量提现】-->是否垫资参数有误-->detail_no:" + dateDetail.getDetail_no());
                throw new BusinessException(BusinessMsg.PARAMETER_ERROR, BusinessMsg.getMsg(BusinessMsg.BALANCE_LACK)+ ",请检查【是否垫资】");

            }

            //判断对公  必填项
            if(dateDetail.getClient_property().equals(CusType.COMPANY.getCode())){
                if(StringUtils.isEmpty(dateDetail.getOpen_branch()) || StringUtils.isEmpty(dateDetail.getBank_id()) )
                    throw new BusinessException(BusinessMsg.PARAMETER_LACK,"对公缺少必要参数open_branch或bank_id");
            }

            logger.info("【批量提现】-->开始转账-->detail_no:" + dateDetail.getDetail_no());
            accountTransferService.batchFundTransfer(eaccAccountamtlists);
            logger.info("【批量提现】-->转账成功-->detail_no:" + dateDetail.getDetail_no());

            try {
                //添加提现信息
                logger.info("【批量提现】-->开始添加提现信息-->detail_no:" + dateDetail.getDetail_no());
                RwWithdraw rwWithdraw = new RwWithdraw();
                rwWithdraw.setTrans_serial(transTransReq.getTrans_serial());
                rwWithdraw.setTrans_date(transTransReq.getTrans_date());
                rwWithdraw.setTrans_time(transTransReq.getTrans_time());
                rwWithdraw.setPlat_no(baseRequest.getMer_no());
                rwWithdraw.setPlatcust(accountSubjectInfo.getPlatcust());
                rwWithdraw.setTrans_code(TransConsts.BATCH_WITHDRAW_CODE);
                rwWithdraw.setTrans_name(TransConsts.BATCH_WITHDRAW_NAME);
                rwWithdraw.setOut_amt(dateDetail.getAmt());
                rwWithdraw.setSubject(accountSubjectInfo.getSubject());
                rwWithdraw.setSub_subject(accountSubjectInfo.getSub_subject());
                rwWithdraw.setPay_code(dateDetail.getPay_code());
                if (dateDetail.getFee_amt().compareTo(BigDecimal.ZERO) > 0)
                    rwWithdraw.setFee_amt(dateDetail.getFee_amt());
                rwWithdraw.setOppo_account(eaccCardInfo.getCard_no());

                rwWithdraw.setClient_property(dateDetail.getClient_property());
                rwWithdraw.setCity_code(dateDetail.getCity_code());

                //对个人
                if (rwWithdraw.getClient_property().equals(CusType.PERSONAL.getCode())) {
                    rwWithdraw.setOpen_branch(eaccCardInfo.getOpen_branch());
                    rwWithdraw.setBank_id(eaccCardInfo.getBank_no());
                    if (StringUtils.isNotBlank(eaccUserInfo.getName())) {
                        rwWithdraw.setPayee_name(eaccUserInfo.getName());
                    } else {
                        rwWithdraw.setPayee_name(eaccUserInfo.getOrg_name());
                    }
                }

                //对公司
                if (rwWithdraw.getClient_property().equals(CusType.COMPANY.getCode())) {
                    rwWithdraw.setOpen_branch(dateDetail.getOpen_branch());
                    rwWithdraw.setBank_id(dateDetail.getBank_id());
                    rwWithdraw.setPayee_name(eaccCardInfo.getAcct_name());
                }

                //默认set为借记卡
                rwWithdraw.setCard_type(dateDetail.getCard_type());
                rwWithdraw.setBrabank_name(dateDetail.getBrabank_name());
                rwWithdraw.setIs_advance(dateDetail.getIs_advance());
                rwWithdraw.setAdvance_amt(successData.getAdvance_amt());
                rwWithdraw.setPay_status(PayStatus.INIT_PAY.getCode());
                rwWithdraw.setOrder_no(dateDetail.getDetail_no());
                rwWithdraw.setQuery_no_num(0);
                rwWithdraw.setNotify_url(dateDetail.getNotify_url());
                rwWithdraw.setRemark(dateDetail.getRemark());
                rwWithdraw.setEnabled(Constants.ENABLED);
                rwWithdraw.setCreate_time(new Date());
                rwWithdraw.setUpdate_time(new Date());
                rwWithdrawMapper.insert(rwWithdraw);
                logger.info("【批量提现】-->添加提现信息成功-->detail_no:" + dateDetail.getDetail_no());

            }catch (Exception e){
                logger.info("【批量提现】-->异常，准备反向回滚转账-->detail_no:" + dateDetail.getDetail_no(),e);
                accountTransferService.rollbackBatchWithdraw(transTransReq.getTrans_serial(),dateDetail.getDetail_no());
                throw new BusinessException(BusinessMsg.FAIL,"插入提现异常");
            }


//            RwWithdrawExample rwWithdrawExample = new RwWithdrawExample();
//            RwWithdrawExample.Criteria criteria = rwWithdrawExample.createCriteria();
//            criteria.andTrans_serialEqualTo(transTransReq.getTrans_serial());
//            criteria.andEnabledEqualTo(Constants.ENABLED);
//            List<RwWithdraw> rwWithdrawList = rwWithdrawMapper.selectByExample(rwWithdrawExample);
//            CacheUtil.getCache().leftPush(Constants.RW_WITHDRAW_REDIS_KEY+batchWithdrawRequest.getMall_no(), com.alibaba.fastjson.JSON.toJSONString(rwWithdrawList.get(0)));

            //更新业务流水
            transTransReq.setReturn_code(BusinessMsg.SUCCESS);
            transTransReq.setReturn_msg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));
            transTransReq.setPlatcust(dateDetail.getPlatcust());
            transTransReq.setPlat_no(baseRequest.getMer_no());
            transTransReq.setStatus(OrderStatus.REQUESTSUCCESS.getCode());
            transReqService.insert(transTransReq);

        }catch (Exception e) {

            logger.error("【批量提现】-->异常-->order_no:"+dateDetail.getDetail_no(),e);

            BaseResponse baseResponse = new BaseResponse();
            if (e instanceof BusinessException) {
                baseResponse = ((BusinessException) e).getBaseResponse();
            } else {
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

        return successData;

    }

    //提现转账
    public EaccAccountamtlist eaccAccountamtlistTransfer(AccountSubjectInfo accountSubjectInfo_oneself, AccountSubjectInfo accountSubjectInfo_rival, TransTransreq transTransReq, BigDecimal b, String pay_code, String rem) throws BusinessException {

        EaccAccountamtlist eaccAccountamtlist = new EaccAccountamtlist();

        //账户，科目，子科目
        eaccAccountamtlist.setPlat_no(accountSubjectInfo_oneself.getPlat_no());
        eaccAccountamtlist.setPlatcust(accountSubjectInfo_oneself.getPlatcust());
        eaccAccountamtlist.setSubject(accountSubjectInfo_oneself.getSubject());
        eaccAccountamtlist.setSub_subject(accountSubjectInfo_oneself.getSub_subject());

        //对手的账户，科目，子科目
        eaccAccountamtlist.setOppo_plat_no(accountSubjectInfo_rival.getPlat_no());
        eaccAccountamtlist.setOppo_platcust(accountSubjectInfo_rival.getPlatcust());
        eaccAccountamtlist.setOppo_subject(accountSubjectInfo_rival.getSubject());
        eaccAccountamtlist.setOppo_sub_subject(accountSubjectInfo_rival.getSub_subject());

        //交易流水号
        eaccAccountamtlist.setTrans_serial(transTransReq.getTrans_serial());
        //订单号
        eaccAccountamtlist.setOrder_no(transTransReq.getOrder_no());

        //出账的交易代码，交易名称
        eaccAccountamtlist.setTrans_code(TransConsts.BATCH_WITHDRAW_CODE);
        eaccAccountamtlist.setTrans_name(TransConsts.BATCH_WITHDRAW_NAME);
        eaccAccountamtlist.setTrans_date(transTransReq.getTrans_date());
        eaccAccountamtlist.setTrans_time(transTransReq.getTrans_time());


        eaccAccountamtlist.setPay_code(pay_code);
        eaccAccountamtlist.setRemark(rem);

        //转账金额
        eaccAccountamtlist.setAmt(b);

        return eaccAccountamtlist;
    }

}
