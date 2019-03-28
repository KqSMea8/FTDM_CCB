package com.sunyard.sunfintech.account.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.sunyard.sunfintech.account.model.bo.BankReverse;
import com.sunyard.sunfintech.account.provider.IAccountOprationService;
import com.sunyard.sunfintech.account.provider.IAccountTransferAsynService;
import com.sunyard.sunfintech.account.provider.IAccountTransferService;
import com.sunyard.sunfintech.account.provider.IAccountTransferThirdParty;
import com.sunyard.sunfintech.core.base.BaseRequest;
import com.sunyard.sunfintech.core.base.BaseResponse;
import com.sunyard.sunfintech.core.base.BaseServiceSimple;
import com.sunyard.sunfintech.core.config.GlobalConfig;
import com.sunyard.sunfintech.core.dic.FlowStatus;
import com.sunyard.sunfintech.core.dic.TransferStatus;
import com.sunyard.sunfintech.core.exception.BusinessException;
import com.sunyard.sunfintech.core.exception.BusinessMsg;
import com.sunyard.sunfintech.core.util.DateUtils;
import com.sunyard.sunfintech.dao.entity.EaccAccountamtlist;
import com.sunyard.sunfintech.dao.entity.EaccTransTransreqWithBLOBs;
import com.sunyard.sunfintech.dao.entity.TransTransreq;
import com.sunyard.sunfintech.pub.provider.ITransReqService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by terry on 2017/9/13.
 */
@Service(interfaceClass = IAccountTransferAsynService.class)
@org.springframework.stereotype.Service
public class AccountTransferAsynService extends BaseServiceSimple implements IAccountTransferAsynService {

    @Autowired
    private IAccountTransferService accountTransferService;

    @Autowired
    private IAccountOprationService accountOprationService;

    @Autowired
    private IAccountTransferThirdParty accountTransferThirdParty;

    @Autowired
    private ITransReqService transReqService;

    @Override
    public Boolean transfer(EaccAccountamtlist eaccAccountamtlist) throws BusinessException {
        TransTransreq transTransreq=null;
        if(!"异步".equals(eaccAccountamtlist.getExt5())){
            BaseRequest baseRequest=new BaseRequest();
            baseRequest.setOrder_no(eaccAccountamtlist.getOrder_no());
            baseRequest.setPartner_trans_date(eaccAccountamtlist.getTrans_date());
            baseRequest.setPartner_trans_time(eaccAccountamtlist.getTrans_time());
            baseRequest.setMer_no(eaccAccountamtlist.getPlat_no());
            transTransreq=transReqService.getTransTransReq(baseRequest,
                    eaccAccountamtlist.getTrans_code(),eaccAccountamtlist.getTrans_name(),false);
            transTransreq.setOrder_no(eaccAccountamtlist.getOrder_no());
            transTransreq.setTrans_serial(eaccAccountamtlist.getTrans_serial());
            BaseResponse oldBaseResponse=transReqService.insert(transTransreq);
            if(oldBaseResponse!=null){
                logger.info(String.format("【单笔转账】原单已存在，不进行消费|trans_serial:%s|status:%s",
                        oldBaseResponse.getOrder_no(),oldBaseResponse.getTrans_serial()));
                throw new BusinessException(BusinessMsg.ORIGINAL_ORDER_NO_ERROR,BusinessMsg.getMsg(BusinessMsg.ORIGINAL_ORDER_NO_ERROR)+"转账订单重复，请检查订单");
            }
        }else{
            eaccAccountamtlist.setExt5(null);
        }
        try{
            Map<String,Object> returnMap=accountOprationService.doTransfer(eaccAccountamtlist);
            List<EaccTransTransreqWithBLOBs> thisEaccTrans= (List<EaccTransTransreqWithBLOBs>) returnMap.get("eaccountTransferList");
            if(thisEaccTrans.size()>0){
                //单笔代付
                accountTransferService.pay(thisEaccTrans.get(0));
            }
            if(transTransreq!=null){
                transTransreq.setStatus(FlowStatus.SUCCESS.getCode());
                transTransreq.setReturn_code(BusinessMsg.SUCCESS);
                transTransreq.setReturn_msg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));
                transReqService.insert(transTransreq);
            }
        }catch (Exception e){
            logger.error("【单笔转账】异常",e);
            if(transTransreq!=null){
                transTransreq.setStatus(FlowStatus.FAIL.getCode());
                if(e instanceof BusinessException){
                    BaseResponse baseResponse=((BusinessException)e).getBaseResponse();
                    transTransreq.setReturn_code(baseResponse.getRecode());
                    transTransreq.setReturn_msg(baseResponse.getRemsg());
                }else{
                    transTransreq.setReturn_code(BusinessMsg.UNKNOW_ERROE);
                    transTransreq.setReturn_msg(BusinessMsg.getMsg(BusinessMsg.UNKNOW_ERROE));
                }
                transReqService.insert(transTransreq);
            }
            throw e;
        }

        return true;
    }

    @Override
    public Integer batchTransfer(List<EaccAccountamtlist> eaccAccountamtlists) throws BusinessException {
        //添加流水
        TransTransreq transTransreq=null;
        EaccAccountamtlist teamEaccAccountamtlist=eaccAccountamtlists.get(0);
        if(!"异步".equals(teamEaccAccountamtlist.getExt5())){
            BaseRequest baseRequest=new BaseRequest();
            baseRequest.setOrder_no(teamEaccAccountamtlist.getOrder_no());
            baseRequest.setPartner_trans_date(teamEaccAccountamtlist.getTrans_date());
            baseRequest.setPartner_trans_time(teamEaccAccountamtlist.getTrans_time());
            baseRequest.setMer_no(teamEaccAccountamtlist.getPlat_no());
            transTransreq=transReqService.getTransTransReq(baseRequest,
                    teamEaccAccountamtlist.getTrans_code(),teamEaccAccountamtlist.getTrans_name(),false);
            transTransreq.setOrder_no(teamEaccAccountamtlist.getOrder_no());
            transTransreq.setTrans_serial(teamEaccAccountamtlist.getTrans_serial());
            BaseResponse oldBaseResponse=transReqService.insert(transTransreq);
            if(oldBaseResponse!=null){
                logger.info(String.format("【批量转账】原单已存在，不进行消费|trans_serial:%s|status:%s",
                        oldBaseResponse.getOrder_no(),oldBaseResponse.getTrans_serial()));
                throw new BusinessException(BusinessMsg.ORIGINAL_ORDER_NO_ERROR,BusinessMsg.getMsg(BusinessMsg.ORIGINAL_ORDER_NO_ERROR)+"转账订单重复，请检查订单");
            }
        }else{
            teamEaccAccountamtlist.setExt5(null);
        }
        List<EaccTransTransreqWithBLOBs> eaccTransTransreqList=new ArrayList<>();
        List<EaccAccountamtlist> eaccAccountamtlistList=new ArrayList<>();
        for(EaccAccountamtlist expense:eaccAccountamtlists){
            try{
                Map<String,Object> returnMap=accountOprationService.doTransfer(expense);
                List<EaccTransTransreqWithBLOBs> thisEaccTrans= (List<EaccTransTransreqWithBLOBs>) returnMap.get("eaccountTransferList");
                if(!("OnlyVirtual".equals(expense.getRemark()))){
                    eaccTransTransreqList.addAll(thisEaccTrans);
                }
                List<EaccAccountamtlist> resultEaccAccountamtlist= (List<EaccAccountamtlist>) returnMap.get("virtualTransferList");
                eaccAccountamtlistList.addAll(resultEaccAccountamtlist);
            }catch (BusinessException e){
                logger.error("【转账】转账异常：",e);
                logger.info("【转账】判断是否需要回滚转账记账数据，回滚数据条数"+eaccAccountamtlistList.size());
                if(eaccAccountamtlistList.size()>0){
                    logger.info("【转账】回滚数据："+ JSON.toJSONString(eaccAccountamtlistList, GlobalConfig.serializerFeature));
                    try{
                        accountOprationService.backTransfer(eaccAccountamtlistList);
                    }catch (Exception ex){
                        logger.error("【转账】回滚异常：",ex);
                        throw new BusinessException(BusinessMsg.DATEBASE_EXCEPTION, BusinessMsg.getMsg(BusinessMsg.DATEBASE_EXCEPTION)+"：转账回滚失败");
                    }
                }
                throw new BusinessException(e.getBaseResponse());
            }
        }


        try{
            //如有电子账户转账，开始电子账户转账
            if(eaccTransTransreqList.size()>1){
                //是否为代付
                List<EaccTransTransreqWithBLOBs> successTrans=new ArrayList<>();
                for(EaccTransTransreqWithBLOBs eaccTransTransreq:eaccTransTransreqList){
                    //单笔代付
                    logger.info("【批量转账】=======调用单笔代付");
                    try {
                        accountTransferService.pay(eaccTransTransreq);
                        successTrans.add(eaccTransTransreq);
                    }catch (Exception e){
                        BaseResponse baseResponse=new BaseResponse();
                        if(e instanceof BusinessException){
                            baseResponse=((BusinessException) e).getBaseResponse();
                            //如果超时，冲正
                            if("请求第三方超时".equals(baseResponse.getRemsg())){
                                logger.info("【批量转账】=======单笔代付请求超时，冲正");
                                BankReverse bankReverse=getBankReverse(eaccTransTransreq);
                                accountTransferThirdParty.addReverseToQueue(bankReverse);
                            }
                        }else{
                            baseResponse.setRecode(BusinessMsg.TRANS_FAIL);
                            baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.TRANS_FAIL)+":电子账户转账失败");
                        }
                        //如果是处理中，在上层已经发起冲正,如果是其它错误，无需冲正，此时只需要冲正之前转成功的
                        if(successTrans.size()>0){
                            for(EaccTransTransreqWithBLOBs trans:successTrans){
                                BankReverse bankReverse=getBankReverse(trans);
                                accountTransferThirdParty.addReverseToQueue(bankReverse);
                            }
                        }
                        throw new BusinessException(baseResponse);
                    }
                }

            }else if(eaccTransTransreqList.size()==1){
                //单笔代付
                logger.info("【批量转账】=======调用单笔代付");
                try {
                    accountTransferService.pay(eaccTransTransreqList.get(0));
                }catch (BusinessException e){
                    BaseResponse baseResponse=e.getBaseResponse();
                    if("请求第三方超时".equals(baseResponse.getRemsg())){
                        logger.info("【批量转账】=======单笔代付请求超时，冲正");
                        BankReverse bankReverse=getBankReverse(eaccTransTransreqList.get(0));
                        accountTransferThirdParty.addReverseToQueue(bankReverse);
                    }
                    throw e;
                }
            }
            if(transTransreq!=null){
                transTransreq.setStatus(FlowStatus.SUCCESS.getCode());
                transTransreq.setReturn_code(BusinessMsg.SUCCESS);
                transTransreq.setReturn_msg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));
            }
        }catch (Exception e){
            logger.error("【转账】转账异常：",e);
            BaseResponse baseResponse=new BaseResponse();
            if(e instanceof BusinessException){
                baseResponse=((BusinessException) e).getBaseResponse();
            }else{
                baseResponse.setRecode(BusinessMsg.TRANS_FAIL);
                baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.TRANS_FAIL));
            }
            if(transTransreq!=null){
                transTransreq.setStatus(FlowStatus.FAIL.getCode());
                transTransreq.setReturn_code(baseResponse.getRecode());
                transTransreq.setReturn_msg(baseResponse.getRemsg());
            }
            logger.info("【转账】判断是否需要回滚转账记账数据，回滚数据条数"+eaccAccountamtlistList.size());
            if(eaccAccountamtlistList.size()>0){
                logger.info("【转账】回滚数据："+ JSON.toJSONString(eaccAccountamtlistList, GlobalConfig.serializerFeature));
                try{
                    accountOprationService.backTransfer(eaccAccountamtlistList);
                }catch (Exception ex){
                    logger.error("【转账】回滚异常：",e);
                    throw new BusinessException(BusinessMsg.DATEBASE_EXCEPTION, BusinessMsg.getMsg(BusinessMsg.DATEBASE_EXCEPTION)+"：转账回滚失败");
                }
            }
            throw new BusinessException(BusinessMsg.TRANS_FAIL, BusinessMsg.getMsg(BusinessMsg.TRANS_FAIL));
        }finally {
            if(transTransreq!=null){
                transReqService.insert(transTransreq);
            }
        }
        return TransferStatus.SUCCESS.getCode();
    }

    private BankReverse getBankReverse(EaccTransTransreqWithBLOBs eaccTransTransreq){
        BankReverse bankReverse=new BankReverse();
        bankReverse.setMall_no(eaccTransTransreq.getMall_no());
        bankReverse.setOccur_balance(eaccTransTransreq.getTrans_amt());
        bankReverse.setOriginal_serial_no(eaccTransTransreq.getTrans_serial());
        bankReverse.setPartner_trans_date(DateUtils.getyyyyMMddDate());
        return bankReverse;
    }
}
