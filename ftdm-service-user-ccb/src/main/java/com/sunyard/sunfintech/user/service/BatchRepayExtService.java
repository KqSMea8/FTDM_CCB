package com.sunyard.sunfintech.user.service;

import com.sunyard.sunfintech.account.provider.IAccountQueryService;
import com.sunyard.sunfintech.account.provider.IAccountTransferService;
import com.sunyard.sunfintech.core.Constants;
import com.sunyard.sunfintech.core.TransConsts;
import com.sunyard.sunfintech.core.base.BaseResponse;
import com.sunyard.sunfintech.core.base.BaseServiceSimple;
import com.sunyard.sunfintech.core.dic.*;
import com.sunyard.sunfintech.core.exception.BusinessException;
import com.sunyard.sunfintech.core.exception.BusinessMsg;
import com.sunyard.sunfintech.core.util.DateUtils;
import com.sunyard.sunfintech.dao.entity.*;
import com.sunyard.sunfintech.dao.mapper.ProdBorrowerRealrepayMapper;
import com.sunyard.sunfintech.pub.provider.ITransReqService;
import com.sunyard.sunfintech.user.model.bo.BatchRepaySuccessData;
import com.sunyard.sunfintech.user.modelold.bo.BatchRepayRequestDetailOld;
import com.sunyard.sunfintech.user.modelold.bo.BatchRepayRequestOld;
import com.sunyard.sunfintech.user.provider.IBatchRepayExtService;
import com.sunyard.sunfintech.user.provider.IProdSearchService;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by PengZY on 2017/7/10.
 */
@CacheConfig(cacheNames="batchRepayExtService")
@org.springframework.stereotype.Service
public class BatchRepayExtService extends BaseServiceSimple implements IBatchRepayExtService{

    @Autowired
    private IAccountTransferService accountTransferService;

    @Autowired
    private IAccountQueryService accountQueryService;

    @Autowired
    private ITransReqService transReqService;

    @Autowired
    private ProdBorrowerRealrepayMapper prodBorrowerRealrepayMapper;

    @Autowired
    private IProdSearchService prodSearchService;

    @Override
    @Transactional
    public BatchRepaySuccessData onePatchRepay(BatchRepayRequestOld batchRepayRequest, BatchRepayRequestDetailOld detail){

        //记录业务流水
        TransTransreq transTransReq_transfer = transReqService.getTransTransReq(batchRepayRequest.clone(), TransConsts.BORROWREPAY_CODE,TransConsts.BORROWREPAY_NAME, true);
        transTransReq_transfer.setOrder_no(detail.getDetail_no());
        transTransReq_transfer.setPlatcust(detail.getPlatcust());
        transReqService.insert(transTransReq_transfer);

        BatchRepaySuccessData batchRepaySuccessData = new BatchRepaySuccessData();

        try{
            validate(detail);


            //查询标是否存在
            ProdProductinfo productInfo = prodSearchService.queryProdInfo(batchRepayRequest.getMer_no(),detail.getProd_id());
            if(productInfo == null){

                BaseResponse baseResponse=new BaseResponse();
                baseResponse.setRecode(BusinessMsg.INVALID_PRODUCT_ID);
                baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.INVALID_PRODUCT_ID)  + ",标不存在  标的id：" + detail.getProd_id());
                throw new BusinessException(baseResponse);
            }

            //检查标的账户是否存在
            AccountSubjectInfo prodAccountSubjectInfo = accountQueryService.queryAccount(productInfo.getPlat_no(),productInfo.getProd_account(), Tsubject.CASH.getCode(), Ssubject.PROD.getCode());
            if(prodAccountSubjectInfo == null){

                BaseResponse baseResponse=new BaseResponse();
                baseResponse.setRecode(BusinessMsg.INVALID_ACCOUNT);
                baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.INVALID_ACCOUNT) + ",标的账户不存在   账户：" + productInfo.getProd_account());
                throw new BusinessException(baseResponse);
            }

            //检查借款人账户是否存在
            AccountSubjectInfo custAccountSubjectInfo = accountQueryService.queryAccount(batchRepayRequest.getMer_no(), detail.getPlatcust(), Tsubject.CASH.getCode(),Ssubject.FINANCING.getCode());
            if(custAccountSubjectInfo == null){

                BaseResponse baseResponse=new BaseResponse();
                baseResponse.setRecode(BusinessMsg.ACCOUNT_INFO_DIFF);
                baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.ACCOUNT_INFO_DIFF) + ",借款人账户不存在  请检查融资账户："+detail.getPlatcust());
                throw new BusinessException(baseResponse);
            }

            //查询手续费账户
            AccountSubjectInfo feeAccountSubjectInfo = accountQueryService.queryAccount(productInfo.getPlat_no(),null, Tsubject.CASH.getCode(),Ssubject.FEE.getCode());
            if(feeAccountSubjectInfo == null){

                BaseResponse baseResponse=new BaseResponse();
                baseResponse.setRecode(BusinessMsg.INVALID_ACCOUNT);
                baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.INVALID_ACCOUNT) + ",手续费账户不存在 平台号：" + productInfo.getPlat_no());
                throw new BusinessException(baseResponse);
            }

            //判断是否有手续费
            if(detail.getFee_amt() != null && detail.getFee_amt().compareTo(BigDecimal.ZERO) > 0){
                //检查手续费和实际还款金额是否等于交易金额
                if(detail.getTrans_amt().compareTo(detail.getFee_amt().add(detail.getReal_repay_amt())) != 0){

                    BaseResponse baseResponse=new BaseResponse();
                    baseResponse.setRecode(BusinessMsg.MONEY_ERROR);
                    baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.MONEY_ERROR) + "---------------交易金额  不等于 （实际还款金额+手续费金额）");
                    throw new BusinessException(baseResponse);
                }
            }

            //插入进借款人信息表
            ProdBorrowerRealrepay prodBorrowerRealrepay = new ProdBorrowerRealrepay();
            prodBorrowerRealrepay.setBorrower_id(detail.getDetail_no());//融资编号
            prodBorrowerRealrepay.setPlat_no(productInfo.getPlat_no());//平台编号
            prodBorrowerRealrepay.setRepay_num(detail.getRepay_num());//还款期别
            prodBorrowerRealrepay.setProd_id(detail.getProd_id());//产品编号
            prodBorrowerRealrepay.setRepay_date(DateUtils.formatDateToStr(detail.getRepay_date()));//计划还款日期
            prodBorrowerRealrepay.setRepay_amt(detail.getRepay_amt());//计划还款金额
            prodBorrowerRealrepay.setRepay_fee(detail.getFee_amt());//计划还款手续费
            prodBorrowerRealrepay.setReal_repay_amt(detail.getReal_repay_amt());//实际还款金额
            prodBorrowerRealrepay.setReal_repay_fee(detail.getFee_amt());//实际还款手续费
            prodBorrowerRealrepay.setPlatcust(detail.getPlatcust());//借款人编号
            prodBorrowerRealrepay.setRemark(detail.getRemark());//备注
            prodBorrowerRealrepay.setCreate_time(DateUtils.getNow());//创建时间
            prodBorrowerRealrepay.setUpdate_time(DateUtils.getNow());//修改时间
            prodBorrowerRealrepay.setTrans_date(batchRepayRequest.getPartner_trans_date());//交易日期
            prodBorrowerRealrepay.setTrans_time(batchRepayRequest.getPartner_trans_time());//交易时间
            prodBorrowerRealrepay.setEnabled(Constants.ENABLED);//删除标记
            prodBorrowerRealrepay.setStatus(RepaymentStatus.SUCCPAY.getCode());//借款人还款状态
            prodBorrowerRealrepayMapper.insert(prodBorrowerRealrepay);


            List<EaccAccountamtlist> eaccAccountamtlists = new ArrayList<>();

            //转账   借款人还款给标的账户
            logger.info("借款人批量还款   -------------------->      借款人还款给标的账户");

            EaccAccountamtlist eaccAccountamtlist = new EaccAccountamtlist();

            //科目
            eaccAccountamtlist.setPlat_no(productInfo.getPlat_no());
            eaccAccountamtlist.setPlatcust(custAccountSubjectInfo.getPlatcust());
            eaccAccountamtlist.setSubject(custAccountSubjectInfo.getSubject());
            eaccAccountamtlist.setSub_subject(custAccountSubjectInfo.getSub_subject());

            eaccAccountamtlist.setOrder_no(transTransReq_transfer.getOrder_no());
            eaccAccountamtlist.setTrans_serial(transTransReq_transfer.getTrans_serial());
            eaccAccountamtlist.setTrans_code(TransConsts.BORROWREPAY_CODE);
            eaccAccountamtlist.setTrans_name(TransConsts.BORROWREPAY_NAME);
            eaccAccountamtlist.setTrans_date(batchRepayRequest.getPartner_trans_date());
            eaccAccountamtlist.setTrans_time(batchRepayRequest.getPartner_trans_time());
            eaccAccountamtlist.setAmt_type(AmtType.EXPENSIVE.getCode());

            //对手科目
            eaccAccountamtlist.setOppo_plat_no(prodAccountSubjectInfo.getPlat_no());
            eaccAccountamtlist.setOppo_platcust(prodAccountSubjectInfo.getPlatcust());
            eaccAccountamtlist.setOppo_subject(prodAccountSubjectInfo.getSubject());
            eaccAccountamtlist.setOppo_sub_subject(prodAccountSubjectInfo.getSub_subject());

            eaccAccountamtlist.setAmt(detail.getReal_repay_amt());
            eaccAccountamtlist.setRemark("借款人批量还款，借款人还款给标的账户");

            eaccAccountamtlists.add(eaccAccountamtlist);

            //判断是否有手续费
            if(detail.getFee_amt() != null && detail.getFee_amt().compareTo(BigDecimal.ZERO) > 0){

                logger.info("借款人批量还款   -------------------->      借款人还款给平台自有   手续费");
                EaccAccountamtlist eacc=new EaccAccountamtlist();
                BeanUtils.copyProperties(eacc,eaccAccountamtlist);
                //借款人还款给平台自有账户
                //对手科目
                eacc.setOppo_plat_no(feeAccountSubjectInfo.getPlat_no());
                eacc.setOppo_platcust(feeAccountSubjectInfo.getPlatcust());
                eacc.setOppo_subject(feeAccountSubjectInfo.getSubject());
                eacc.setOppo_sub_subject(feeAccountSubjectInfo.getSub_subject());

                eacc.setAmt(detail.getFee_amt());
                eacc.setRemark("借款人批量还款，借款人还款给平台自有手续费");

                eaccAccountamtlists.add(eacc);
            }


            int redo = 100;     //重试机制
            while(redo > 0){//重试100次
                try{
                    accountTransferService.batchTransfer(eaccAccountamtlists);
                    Thread.sleep((new Random()).nextInt(100));
                    break; //执行成功后直接退出此循环
                }catch(BusinessException b){
                    redo--;
                    BaseResponse baseResponseError = b.getBaseResponse();
                    if(!baseResponseError.getRecode().equals("654321")) {
                        throw new BusinessException(b.getBaseResponse());
                    }
                }
            }

            transTransReq_transfer.setReturn_code(BusinessMsg.SUCCESS);
            transTransReq_transfer.setReturn_msg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));
            transTransReq_transfer.setStatus(FlowStatus.SUCCESS.getCode());
            transReqService.insert(transTransReq_transfer);

            batchRepaySuccessData.setDetail_no(detail.getDetail_no());
            batchRepaySuccessData.setProd_id(productInfo.getProd_id());
            batchRepaySuccessData.setPlatcust(custAccountSubjectInfo.getPlatcust());
            batchRepaySuccessData.setTrans_amt(detail.getTrans_amt());


        }catch (Exception e){
            logger.error("借款人批量还款失败 ：批量订单号："+detail.getDetail_no(),e);
            BaseResponse baseResponse = new BaseResponse();
            if (e instanceof BusinessException) {
                baseResponse = ((BusinessException) e).getBaseResponse();
            } else {
                logger.error("借款人批量还款失败------------------" + e);
                baseResponse.setRecode(BusinessMsg.DATEBASE_EXCEPTION);
                baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.DATEBASE_EXCEPTION));
            }
            //更新流水
            transTransReq_transfer.setReturn_code(baseResponse.getRecode());
            transTransReq_transfer.setReturn_msg(baseResponse.getRemsg());
            transTransReq_transfer.setStatus(FlowStatus.FAIL.getCode());
            transReqService.insert(transTransReq_transfer);

            throw new BusinessException(baseResponse);
        }

        return batchRepaySuccessData;

    }
}
