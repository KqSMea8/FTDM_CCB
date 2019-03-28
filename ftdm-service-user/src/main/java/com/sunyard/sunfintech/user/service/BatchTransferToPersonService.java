package com.sunyard.sunfintech.user.service;

import com.alibaba.fastjson.JSON;
import com.sunyard.sunfintech.account.provider.IAccountQueryService;
import com.sunyard.sunfintech.core.Constants;
import com.sunyard.sunfintech.core.TransConsts;
import com.sunyard.sunfintech.core.base.BaseRequest;
import com.sunyard.sunfintech.core.base.BaseResponse;
import com.sunyard.sunfintech.core.base.BaseServiceSimple;
import com.sunyard.sunfintech.core.config.GlobalConfig;
import com.sunyard.sunfintech.core.dic.FlowStatus;
import com.sunyard.sunfintech.core.dic.OrderStatus;
import com.sunyard.sunfintech.core.dic.Ssubject;
import com.sunyard.sunfintech.core.dic.Tsubject;
import com.sunyard.sunfintech.core.exception.BusinessException;
import com.sunyard.sunfintech.core.exception.BusinessMsg;
import com.sunyard.sunfintech.core.util.CacheUtil;
import com.sunyard.sunfintech.core.util.DateUtils;
import com.sunyard.sunfintech.core.util.StringUtils;
import com.sunyard.sunfintech.custdao.mapper.CustPlatlimitMapper;
import com.sunyard.sunfintech.dao.entity.*;
import com.sunyard.sunfintech.dao.mapper.PlatTransCustMapper;
import com.sunyard.sunfintech.pub.model.NotifyData;
import com.sunyard.sunfintech.pub.provider.INotifyService;
import com.sunyard.sunfintech.pub.provider.ITransReqService;
import com.sunyard.sunfintech.pub.provider.ITransferService;
import com.sunyard.sunfintech.user.model.bo.BatchTransferToPersonNotifyData;
import com.sunyard.sunfintech.user.model.bo.BatchTransferToPersonRequestDeatil;
import com.sunyard.sunfintech.user.model.bo.BatchTransferToPersonResponseDetail;
import com.sunyard.sunfintech.user.model.bo.PlatfromToPersonData;
import com.sunyard.sunfintech.user.provider.IBatchTransferToPersonService;
import com.sunyard.sunfintech.user.provider.IPlatformOptionService;
import com.sunyard.sunfintech.user.provider.IPlatfromToPersonMQService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

/**
 * Created by PengZY on 2017/9/27.
 */
@CacheConfig(cacheNames = "batchTransferToPersonService")
@org.springframework.stereotype.Service
public class BatchTransferToPersonService extends BaseServiceSimple implements IBatchTransferToPersonService {

    @Autowired
    private IPlatfromToPersonMQService platfromToPersonMQService;

    @Autowired
    private ITransReqService transReqService;

    @Autowired
    private IAccountQueryService accountQueryService;

    @Autowired
    private INotifyService notifyService;

    @Autowired
    private IPlatformOptionService platformOptionService;

    @Autowired
    private ITransferService transferService;

    @Autowired
    private PlatTransCustMapper platTransCustMapper;

    @Autowired
    private CustPlatlimitMapper custPlatlimitMapper;

    @Override
    public BatchTransferToPersonResponseDetail batchTransferToPerson(AccountSubjectInfo plataccountSubjectInfo , BaseRequest baseRequest, BatchTransferToPersonRequestDeatil batchTransferToPersonRequestDeatil, String notify_url) throws BusinessException {
        logger.info("【批量平台转个人】明细编号：" + batchTransferToPersonRequestDeatil.getDetail_no());
        BatchTransferToPersonResponseDetail batchTransferToPersonResponseDetail = new BatchTransferToPersonResponseDetail();
        //记录流水
        TransTransreq transTransReq = null;
        transTransReq = transReqService.getTransTransReq(baseRequest.clone(), TransConsts.PLAT_TO_ACCOUNT_CODE, TransConsts.PLAT_TO_ACCOUNT_NAME, true);
        transTransReq.setOrder_no(batchTransferToPersonRequestDeatil.getDetail_no());
        transTransReq.setPlatcust(batchTransferToPersonRequestDeatil.getPlatcust());
        transTransReq.setNotify_url(notify_url);
        BaseResponse oldTrans=transReqService.insert(transTransReq);
        if(oldTrans!=null){
            throw new BusinessException(BusinessMsg.ORDERNUMBER_REPEATED, BusinessMsg.getMsg(BusinessMsg.ORDERNUMBER_REPEATED));
        }

        NotifyData notifyData = new NotifyData();
        notifyData.setMall_no(baseRequest.getMall_no());
        notifyData.setNotifyUrl(notify_url);

        BatchTransferToPersonNotifyData batchTransferToPersonNotifyData = new BatchTransferToPersonNotifyData();
        batchTransferToPersonNotifyData.setAmount(batchTransferToPersonRequestDeatil.getAmount());
        batchTransferToPersonNotifyData.setOrder_no(batchTransferToPersonRequestDeatil.getDetail_no());
        batchTransferToPersonNotifyData.setPlatcust(batchTransferToPersonRequestDeatil.getPlatcust());
        batchTransferToPersonNotifyData.setRecode(BusinessMsg.SUCCESS);
        batchTransferToPersonNotifyData.setRemsg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));

        try {
            //账户类型
            String sSubject;
            if (StringUtils.isEmpty(batchTransferToPersonRequestDeatil.getAccount_type())) {
                //默认为投资账户
                sSubject = Ssubject.INVEST.getCode();
            } else {
                if (!Ssubject.INVEST.getCode().equals(batchTransferToPersonRequestDeatil.getAccount_type())
                        && !Ssubject.FINANCING.getCode().equals(batchTransferToPersonRequestDeatil.getAccount_type())) {
                    logger.info("【批量平台转个人】=====account_type不是01也不是02,详细单号为：" + batchTransferToPersonRequestDeatil.getDetail_no());
                    throw new BusinessException(BusinessMsg.PARAMETER_ERROR, BusinessMsg.getMsg(BusinessMsg.PARAMETER_ERROR) + ">>account_type请传01或02");
                }
                sSubject = batchTransferToPersonRequestDeatil.getAccount_type();
            }

            //校验转让原因
            if (!platformOptionService.checkCauseType(baseRequest.getMer_no(), batchTransferToPersonRequestDeatil.getCause_type())) {
                BaseResponse baseResponse = new BaseResponse();
                baseResponse.setRecode(BusinessMsg.REASON_ILLEGAL);
                baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.REASON_ILLEGAL));
                throw new BusinessException(baseResponse);
            }

            logger.info("【批量平台转个人】电子账户未开通,走虚户转账判断电子账号是否存在");
            EaccUserinfo eaccUserinfo = accountQueryService.getEUserinfoByExist(baseRequest.getMall_no(), batchTransferToPersonRequestDeatil.getPlatcust());
            if (eaccUserinfo == null) {
                logger.info("【批量平台转个人】，用户信息不存在：" + batchTransferToPersonRequestDeatil.getPlatcust());
                throw new BusinessException(BusinessMsg.INVALID_ACCOUNT, BusinessMsg.getMsg(BusinessMsg.INVALID_ACCOUNT) + ",用户信息不存在");
            }

            AccountSubjectInfo bankAccountSubjectInfo = null;
            if (StringUtils.isNotBlank(eaccUserinfo.getEaccount_flg()) && eaccUserinfo.getEaccount_flg().equals("1")) {
                logger.info("【批量平台转个人】，查询电子账户明细是否存在：" + batchTransferToPersonRequestDeatil.getPlatcust());
                bankAccountSubjectInfo = accountQueryService.queryAccount(baseRequest.getMall_no(), batchTransferToPersonRequestDeatil.getPlatcust(), Tsubject.CASH.getCode(), Ssubject.EACCOUNT.getCode());
            } else {
                logger.info("【批量平台转个人】，查询投资或融资账户明细是否存在：" + batchTransferToPersonRequestDeatil.getPlatcust());
                bankAccountSubjectInfo = accountQueryService.queryAccount(baseRequest.getMer_no(), batchTransferToPersonRequestDeatil.getPlatcust(), Tsubject.CASH.getCode(), sSubject);
            }

            if (bankAccountSubjectInfo == null) {
                logger.info("【批量平台转个人】，账户不存在：" + batchTransferToPersonRequestDeatil.getPlatcust());
                throw new BusinessException(BusinessMsg.INVALID_ACCOUNT, BusinessMsg.getMsg(BusinessMsg.INVALID_ACCOUNT));
            }

            if (batchTransferToPersonRequestDeatil.getAmount().compareTo(BigDecimal.ZERO) < 1) {
                logger.info("【批量平台转个人】，金额不合法：" + batchTransferToPersonRequestDeatil.getAmount());
                throw new BusinessException(BusinessMsg.MONEY_ERROR, BusinessMsg.getMsg(BusinessMsg.MONEY_ERROR) + ",返现金额不合法");
            }

            Platlimit platlimit = platformOptionService.getPlatLimit(baseRequest.getMall_no(), baseRequest.getMer_no());
            if (null == platlimit) {
                logger.info("【批量平台转个人】，未查询到平台限额信息");
                throw new BusinessException(BusinessMsg.PARAMETER_ERROR, BusinessMsg.getMsg(BusinessMsg.PARAMETER_ERROR) + ",未查询到平台限额信息");
            }
            if (1 == batchTransferToPersonRequestDeatil.getAmount().compareTo(platlimit.getAmt())) {
                logger.info("【批量平台转个人】，转让金额大于平台剩余可转额度");
                throw new BusinessException(BusinessMsg.MONEY_ERROR, BusinessMsg.getMsg(BusinessMsg.MONEY_ERROR) + ",转让金额大于平台剩余可转额度");
            }

            EaccAccountamtlist eaccAccountamtlist = new EaccAccountamtlist();
            eaccAccountamtlist.setOrder_no(batchTransferToPersonRequestDeatil.getDetail_no());
            //账户，科目，子科目
            eaccAccountamtlist.setPlat_no(plataccountSubjectInfo.getPlat_no());
            eaccAccountamtlist.setPlatcust(plataccountSubjectInfo.getPlatcust());
            eaccAccountamtlist.setSubject(plataccountSubjectInfo.getSubject());
            eaccAccountamtlist.setSub_subject(plataccountSubjectInfo.getSub_subject());

            //对手的账户，科目，子科目
            eaccAccountamtlist.setOppo_platcust(bankAccountSubjectInfo.getPlatcust());
            eaccAccountamtlist.setOppo_subject(bankAccountSubjectInfo.getSubject());
            eaccAccountamtlist.setOppo_sub_subject(bankAccountSubjectInfo.getSub_subject());

            //交易流水号
            eaccAccountamtlist.setTrans_serial(transTransReq.getTrans_serial());

            //出账的交易代码，交易名称
            eaccAccountamtlist.setTrans_code(TransConsts.PLAT_TO_ACCOUNT_CODE);
            eaccAccountamtlist.setTrans_name(TransConsts.PLAT_TO_ACCOUNT_NAME);
            eaccAccountamtlist.setTrans_date(baseRequest.getPartner_trans_date());
            eaccAccountamtlist.setTrans_time(baseRequest.getPartner_trans_time());

            //转账金额
            eaccAccountamtlist.setAmt(batchTransferToPersonRequestDeatil.getAmount());

           /* PlatfromToPersonData platfromToPersonData = new PlatfromToPersonData();
            platfromToPersonData.setEaccAccountamtlist(eaccAccountamtlist);
            platfromToPersonData.setMall_no(baseRequest.getMall_no());
            platfromToPersonData.setOrder_no(batchTransferToPersonRequestDeatil.getDetail_no());
            platfromToPersonData.setNotify_url(notify_url);
            platfromToPersonData.setCause_type(batchTransferToPersonRequestDeatil.getCause_type());
            platfromToPersonMQService.addPlatfromToPerson(platfromToPersonData);*/
           //先同步记录plat_trans_cust流水
            PlatTransCust platTransCust = new PlatTransCust();
            platTransCust.setMall_no(baseRequest.getMall_no());
            platTransCust.setPlat_no(eaccAccountamtlist.getPlat_no());
            platTransCust.setOrder_no(eaccAccountamtlist.getOrder_no());
            platTransCust.setTrans_serial(eaccAccountamtlist.getTrans_serial());
            platTransCust.setPlatcust(eaccAccountamtlist.getOppo_platcust());
            platTransCust.setTotal_amt(eaccAccountamtlist.getAmt());
            platTransCust.setBalance(eaccAccountamtlist.getAmt());
            platTransCust.setTrans_date(eaccAccountamtlist.getTrans_date());
            platTransCust.setTrans_time(eaccAccountamtlist.getTrans_time());
            platTransCust.setSubject(eaccAccountamtlist.getSubject());
            platTransCust.setSub_subject(eaccAccountamtlist.getSub_subject());
            platTransCust.setOppo_subject(eaccAccountamtlist.getOppo_subject());
            platTransCust.setOppo_sub_subject(eaccAccountamtlist.getOppo_sub_subject());
            platTransCust.setCause_type(batchTransferToPersonRequestDeatil.getCause_type());
            //默认流水不激活
            platTransCust.setEnabled(Constants.DISABLED);
            platTransCust.setRemark(eaccAccountamtlist.getRemark());
            platTransCust.setCreate_by(eaccAccountamtlist.getPlat_no());
            platTransCust.setCreate_time(DateUtils.getNow());
            platTransCustMapper.insert(platTransCust);

            logger.info("【批量平台转个人】插入完成order_no：" + eaccAccountamtlist.getOrder_no());
            //修改限额表
            platlimit.setAmt(eaccAccountamtlist.getAmt());
            int result_num = custPlatlimitMapper.updateLimitAmt(platlimit);
            if (0 == result_num) {
                throw new BusinessException(BusinessMsg.MONEY_ERROR, BusinessMsg.getMsg(BusinessMsg.MONEY_ERROR) + ",转让金额大于平台剩余可转额度");
            }
            logger.info("插入完成order_no：" + platTransCust.getOrder_no());

            transferService.transfer(baseRequest,eaccAccountamtlist);

            batchTransferToPersonResponseDetail.setAmount(batchTransferToPersonRequestDeatil.getAmount());
            batchTransferToPersonResponseDetail.setDetail_no(batchTransferToPersonRequestDeatil.getDetail_no());
            batchTransferToPersonResponseDetail.setPlatcust(batchTransferToPersonRequestDeatil.getPlatcust());
            batchTransferToPersonResponseDetail.setStatus(FlowStatus.INPROCESS.getCode());
        } catch (Exception e) {
            logger.error("【批量平台转个人】平台转个人失败：", e);
            BaseResponse baseResponse = new BaseResponse();
            if (e instanceof BusinessException) {
                baseResponse = ((BusinessException) e).getBaseResponse();
            } else {
                logger.error(e);
                baseResponse.setRecode(OrderStatus.FAIL.getCode());
                baseResponse.setRemsg(OrderStatus.FAIL.getMsg());
            }
            transTransReq.setStatus(FlowStatus.FAIL.getCode());
            transTransReq.setReturn_code(baseResponse.getRecode());
            transTransReq.setReturn_msg(baseResponse.getRemsg());
            transReqService.insert(transTransReq);

            batchTransferToPersonNotifyData.setOrder_info(baseResponse.getRemsg());
            batchTransferToPersonNotifyData.setOrder_status(OrderStatus.FAIL.getCode());
            notifyData.setNotifyContent(JSON.toJSONString(batchTransferToPersonNotifyData, GlobalConfig.serializerFeature));
            notifyService.addNotify(notifyData);
            throw new  BusinessException(baseResponse);
        }
        return batchTransferToPersonResponseDetail;
    }

}
