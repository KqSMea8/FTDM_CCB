package com.sunyard.sunfintech.user.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sunyard.sunfintech.account.model.bo.BankReverse;
import com.sunyard.sunfintech.account.provider.*;
import com.sunyard.sunfintech.core.Constants;
import com.sunyard.sunfintech.core.TransConsts;
import com.sunyard.sunfintech.core.base.BaseRequest;
import com.sunyard.sunfintech.core.base.BaseResponse;
import com.sunyard.sunfintech.core.base.BaseServiceSimple;
import com.sunyard.sunfintech.core.config.GlobalConfig;
import com.sunyard.sunfintech.core.dic.*;
import com.sunyard.sunfintech.core.exception.BusinessException;
import com.sunyard.sunfintech.core.exception.BusinessMsg;
import com.sunyard.sunfintech.core.util.*;
import com.sunyard.sunfintech.custdao.mapper.CustPlatTransCustMapper;
import com.sunyard.sunfintech.custdao.mapper.CustPlatlimitMapper;
import com.sunyard.sunfintech.dao.entity.*;
import com.sunyard.sunfintech.dao.mapper.*;
import com.sunyard.sunfintech.pub.model.MsgModel;
import com.sunyard.sunfintech.pub.model.NotifyData;
import com.sunyard.sunfintech.pub.provider.*;
import com.sunyard.sunfintech.thirdparty.provider.IAdapterService;
import com.sunyard.sunfintech.user.model.bo.*;
import com.sunyard.sunfintech.user.provider.*;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.core.annotation.Order;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

/**
 * Created by wubin on 2017/5/23.
 */

@Service(interfaceClass = IPlatformOptionService.class)
@CacheConfig(cacheNames="platformOptionService")
@org.springframework.stereotype.Service("platformOptionService")
public class PlatformOptionService extends BaseServiceSimple implements IPlatformOptionService{

    @Autowired
    private IAccountQueryService accountQueryService;

    /*@Autowired
    private IAccountTransferService accountTransferService;*/

    @Autowired
    private IAccountFrozenService accountFrozenService;

    @Autowired
    private ITransReqService transReqService;

    @Autowired
    private PlatformFeeQueryService platformFeeQueryService;

    @Autowired
    private RwWithdrawMapper rwWithdrawMapper;

    @Autowired
    private RwRechargeMapper rwRechargeMapper;

    @Autowired
    private IAccountAssetService accountAssetService;

    @Autowired
    private ISendMsgService sendMsgService;

    @Autowired
    private IAccountSearchService accountSearchService;

    @Autowired
    private IPlatCacheService platCacheService;

    @Autowired
    private ISysParameterService sysParameterService;

    @Autowired
    private CustPlatlimitMapper custplatlimitMapper;

    @Autowired
    private PlatlimitMapper platlimitMapper;

    @Autowired
    private PlatTransCustMapper platTransCustMapper;

    @Autowired
    private ComParamMapper comParamMapper;

    @Autowired
    private IBatchTransferToPersonService batchTransferToPersonService;

    @Autowired
    private IAdapterService adapterService;

    @Autowired
    private PlatPaycodeMapper platPaycodeMapper;

    @Autowired
    private CustPlatTransCustMapper custPlatTransCustMapper;

    @Autowired
    private ITransferService transferService;

    @Autowired
    private IAccountTransferService accountTransferService;

    @Autowired
    private IAccountTransferThirdParty accountTransferThirdParty;

    @Autowired
    private IUserAccountService userAccountService;

    @Value("${deploy.environment}")
    private String deployEnvironment;

    @Autowired
    private INotifyService notifyService;

    //校验转账原因
    @Override
    public boolean checkCauseType(String plat_no,String cause_type){
        ComParamExample comParamExample=new ComParamExample();
        ComParamExample.Criteria com_criteria=comParamExample.createCriteria();
        com_criteria.andPlat_noEqualTo(plat_no);
        com_criteria.andParam_codeEqualTo(ComParamType.PLATTRANSACCOUNT.getCode());
        com_criteria.andParam_valueEqualTo(cause_type);
        List<ComParam>comParamslist=  comParamMapper.selectByExample(comParamExample);
        if(null==comParamslist|| 0==comParamslist.size()){
            return false;
        }
        return true;
    }

    //获取限额
    @Override
    public Platlimit getPlatLimit(String mall_no,String mer_no){
        PlatlimitExample platlimitExample=new PlatlimitExample();
        PlatlimitExample.Criteria criteria=platlimitExample.createCriteria();
        criteria.andMall_noEqualTo(mall_no);
        criteria.andPlat_noEqualTo(mer_no);
        criteria.andTypeEqualTo("10");
        criteria.andEnabledEqualTo(Constants.ENABLED);
        List<Platlimit> platlimitlist= platlimitMapper.selectByExample(platlimitExample);
        if(null==platlimitlist||platlimitlist.size()==0){
            return null;
        }
        return platlimitlist.get(0);
    }


    /**
     *  @author pzy
     *  平台转帐个人
     * @param transferToPersonRequest
     * @return TransferToPersonResponse
     * @throws BusinessException 平台转帐个人失败，抛异常
     */
    @Override
    public BaseResponse transferToPerson(TransferToPersonRequest transferToPersonRequest) throws BusinessException {
        BaseResponse baseResponse = new BaseResponse();
        //记录流水
        TransTransreq transTransReq = transReqService.getTransTransReq(transferToPersonRequest.clone(), TransConsts.PLAT_TO_ACCOUNT_CODE, TransConsts.PLAT_TO_ACCOUNT_NAME, false);
        transTransReq.setPlatcust(transferToPersonRequest.getPlatcust());
        transTransReq.setRemark(transferToPersonRequest.getTips());
        BaseResponse oldTrans=transReqService.insert(transTransReq);
        if(oldTrans!=null){
            throw new BusinessException(BusinessMsg.ORDERNUMBER_REPEATED, BusinessMsg.getMsg(BusinessMsg.ORDERNUMBER_REPEATED));
        }

        AccountSubjectInfo plataccountSubjectInfo =null;
        AccountSubjectInfo bankAccountSubjectInfo =null;
        Platlimit platlimit =new Platlimit();
        try {
            //校验平台账户
            if(!Ssubject.PLAT.getCode().equals(transferToPersonRequest.getPlat_account())){
                logger.info("【平台转个人】平台账户不是11，平台账户不合法，请求参数为："+transferToPersonRequest.getPlat_account());
                throw new BusinessException(BusinessMsg.PARAMETER_ERROR, BusinessMsg.getMsg(BusinessMsg.PARAMETER_ERROR) + "，平台账户不合法");
            }
            //账户类型
            String sSubject;
            if (StringUtils.isEmpty(transferToPersonRequest.getAccount_type())) {
                //默认为投资账户
                sSubject = Ssubject.INVEST.getCode();
            } else {
                if (!Ssubject.INVEST.getCode().equals(transferToPersonRequest.getAccount_type())
                        && !Ssubject.FINANCING.getCode().equals(transferToPersonRequest.getAccount_type())) {
                    logger.info("【平台转个人】=====account_type不是01也不是02");
                    throw new BusinessException(BusinessMsg.PARAMETER_ERROR, BusinessMsg.getMsg(BusinessMsg.PARAMETER_ERROR) + ">>account_type请传01或02");
                }
                sSubject = transferToPersonRequest.getAccount_type();
            }
            //校验转让原因
            if (!checkCauseType(transferToPersonRequest.getMer_no(), transferToPersonRequest.getCause_type())) {
                baseResponse.setRecode(BusinessMsg.REASON_ILLEGAL);
                baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.REASON_ILLEGAL));
                throw new BusinessException(baseResponse);
            }
            //校验金额
            if (transferToPersonRequest.getAmount().compareTo(BigDecimal.ZERO) < 1) {
                baseResponse.setRecode(BusinessMsg.MONEY_ERROR);
                baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.MONEY_ERROR));
                throw new BusinessException(baseResponse);
            }
            //查询平台账户
            plataccountSubjectInfo = accountQueryService.queryAccount(transferToPersonRequest.getMer_no(), transferToPersonRequest.getMer_no(), Tsubject.CASH.getCode(), transferToPersonRequest.getPlat_account());
            if (plataccountSubjectInfo == null) {
                logger.info("【平台转个人】，平台账户不存在");
                throw new BusinessException(BusinessMsg.INVALID_ACCOUNT, BusinessMsg.getMsg(BusinessMsg.INVALID_ACCOUNT) + "平台账户不存在");
            }


            if(Ssubject.INVEST.getCode().equals(sSubject)){
                bankAccountSubjectInfo=accountQueryService.queryAccount(transferToPersonRequest.getMall_no(), transferToPersonRequest.getPlatcust(), Tsubject.CASH.getCode(), Ssubject.EACCOUNT.getCode());
            }
            if (bankAccountSubjectInfo == null) {
                logger.info("【平台转个人】，电子账户不存在");
                //现金投、融资账户
                bankAccountSubjectInfo = accountQueryService.queryAccount(transferToPersonRequest.getMer_no(), transferToPersonRequest.getPlatcust(), Tsubject.CASH.getCode(), sSubject);
                if (bankAccountSubjectInfo == null) {
                    logger.info("【平台转个人】，投资或融资账户不存在");
                    throw new BusinessException(BusinessMsg.INVALID_ACCOUNT, BusinessMsg.getMsg(BusinessMsg.INVALID_ACCOUNT) + "投资或融资账户不存在");
                }
            }

            platlimit = getPlatLimit(transferToPersonRequest.getMall_no(), transferToPersonRequest.getMer_no());
            if (null == platlimit) {
                baseResponse.setRecode(BusinessMsg.PARAMETER_ERROR);
                baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.PARAMETER_ERROR) + ",未查询到平台限额信息");
                throw new BusinessException(baseResponse);
            }
            if (1 == transferToPersonRequest.getAmount().compareTo(platlimit.getAmt())) {
                baseResponse.setRecode(BusinessMsg.MONEY_ERROR);
                baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.MONEY_ERROR) + ",转让金额大于平台剩余可转额度");
                throw new BusinessException(baseResponse);
            }
        } catch (Exception e) {
            logger.error("平台转个人失败：", e);
            if (e instanceof BusinessException) {
                baseResponse = ((BusinessException) e).getBaseResponse();
            } else {
                logger.error(e);
                baseResponse.setRecode(BusinessMsg.DATEBASE_EXCEPTION);
                baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.DATEBASE_EXCEPTION));
            }
            //更新流水
            transTransReq.setStatus(FlowStatus.FAIL.getCode());
            transTransReq.setReturn_code(baseResponse.getRecode());
            transTransReq.setReturn_msg(baseResponse.getRemsg());
            transReqService.insert(transTransReq);
            throw new BusinessException(baseResponse);
        }

            logger.info("【平台转个人】，始插入平台转个人订单记录order_no：" + transferToPersonRequest.getOrder_no());
            //插入成功转账流水
            PlatTransCust platTransCust = new PlatTransCust();
            platTransCust.setMall_no(transferToPersonRequest.getMall_no());
            platTransCust.setPlat_no(transferToPersonRequest.getMer_no());
            platTransCust.setOrder_no(transferToPersonRequest.getOrder_no());
            platTransCust.setTrans_serial(transTransReq.getTrans_serial());
            platTransCust.setPlatcust(transferToPersonRequest.getPlatcust());
            platTransCust.setTotal_amt(transferToPersonRequest.getAmount());
            platTransCust.setBalance(transferToPersonRequest.getAmount());
            platTransCust.setTrans_date(transferToPersonRequest.getPartner_trans_date());
            platTransCust.setTrans_time(transferToPersonRequest.getPartner_trans_time());
            platTransCust.setSubject(plataccountSubjectInfo.getSubject());
            platTransCust.setSub_subject(plataccountSubjectInfo.getSub_subject());
            platTransCust.setOppo_subject(bankAccountSubjectInfo.getSubject());
            platTransCust.setOppo_sub_subject(bankAccountSubjectInfo.getSub_subject());
            platTransCust.setCause_type(transferToPersonRequest.getCause_type());
            platTransCust.setEnabled(Constants.ENABLED);
            platTransCust.setRemark(transferToPersonRequest.getRemark());
            platTransCust.setCreate_by(transferToPersonRequest.getMer_no());
            platTransCust.setCreate_time(DateUtils.getNow());
            platTransCustMapper.insert(platTransCust);
            // 执行转账交易
            try {
                logger.info("【平台转个人】，开始转账");
                EaccAccountamtlist eaccAccountamtlist = new EaccAccountamtlist();
                eaccAccountamtlist.setOrder_no(transferToPersonRequest.getOrder_no());
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
                eaccAccountamtlist.setTrans_date(transferToPersonRequest.getPartner_trans_date());
                eaccAccountamtlist.setTrans_time(transferToPersonRequest.getPartner_trans_time());

                //转账金额
                eaccAccountamtlist.setAmt(transferToPersonRequest.getAmount());

                //调用新的转账接口
                logger.info("【平台转个人】开始转账："+JSON.toJSON(eaccAccountamtlist));
                //newAccountTransferService.transfer(eaccAccountamtlist);
                accountTransferService.transfer(eaccAccountamtlist);



            } catch (Exception e) {
                logger.info("【平台转个人】，转账异常：", e);
                PlatTransCustExample platTransCustExample=new PlatTransCustExample();
                PlatTransCustExample.Criteria criteria=platTransCustExample.createCriteria();
                criteria.andPlat_noEqualTo(transferToPersonRequest.getMer_no());
                criteria.andOrder_noEqualTo(transferToPersonRequest.getOrder_no());
                platTransCustMapper.deleteByExample(platTransCustExample);
                if(e instanceof BusinessException){
                    baseResponse=((BusinessException) e).getBaseResponse();
                }else{
                    baseResponse.setRecode(BusinessMsg.RUNTIME_EXCEPTION);
                    baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.RUNTIME_EXCEPTION));
                }
                transTransReq.setStatus(FlowStatus.FAIL.getCode());
                transTransReq.setReturn_code(baseResponse.getRecode());
                transTransReq.setReturn_msg(baseResponse.getRemsg());
                transReqService.insert(transTransReq);
                throw new BusinessException(baseResponse);
            }

            try {
                logger.info("【平台转个人】，添加限额");
                platlimit.setAmt(transferToPersonRequest.getAmount());
                int result_num = custplatlimitMapper.updateLimitAmt(platlimit);
                if (0 == result_num) {
                    throw new BusinessException(BusinessMsg.MONEY_ERROR, BusinessMsg.getMsg(BusinessMsg.MONEY_ERROR) + ",转让金额大于平台剩余可转额度");
                }
                logger.info("插入完成order_no：" + platTransCust.getOrder_no());
                logger.info("【平台转个人】，限额添加完成");
            } catch (Exception ex) {
                //todo 补偿机制
                //newAccountTransferService.transfer(eaccAccountamtlist);
                accountTransferService.queryEaccAccountamtlistByTransSerialAndPlatcust(transTransReq.getTrans_serial());
                baseResponse.setRecode(BusinessMsg.DATEBASE_EXCEPTION);
                baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.DATEBASE_EXCEPTION) + "，限额明细插入失败或更新限额异常");

                transTransReq.setStatus(FlowStatus.FAIL.getCode());
                transTransReq.setReturn_code(baseResponse.getRecode());
                transTransReq.setReturn_msg(baseResponse.getRemsg());
                transReqService.insert(transTransReq);
                throw new BusinessException(baseResponse);
            }
            //更新流水
            transTransReq.setStatus(FlowStatus.SUCCESS.getCode());
            transTransReq.setReturn_code(BusinessMsg.SUCCESS);
            transTransReq.setReturn_msg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));
            transReqService.insert(transTransReq);

            baseResponse.setRecode(BusinessMsg.SUCCESS);
            baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));
            try {

                if(!"9".equals(transferToPersonRequest.getCause_type())){
                    logger.info("【平台转账个人】========发送通知短信给个人");
                    //检查用户信息是否存在
                    logger.info("检查用户信息是否存在");
                    EaccUserinfo eaccUserInfo = accountSearchService.queryEaccUserInfoByEaccAccountInfo(transferToPersonRequest.getMall_no(), transferToPersonRequest.getMer_no(), transferToPersonRequest.getPlatcust());
                    if (eaccUserInfo == null) {
                        logger.info("用户信息不存在" + "用户信息账号：mallcust：" + transferToPersonRequest.getPlatcust());
                        throw new BusinessException(BusinessMsg.INVALID_ACCOUNT, BusinessMsg.getMsg(BusinessMsg.INVALID_ACCOUNT) + ",用户账号不存在");
                    }
                    logger.info("用户信息存在");
                    MsgModel msgModel = new MsgModel();
                    String mall_name = sysParameterService.querySysParameter(transferToPersonRequest.getMall_no(), SysParamterKey.MALL_NAME);
                    BigDecimal allAmt = sendMsgService.getAccountAllAmount(transferToPersonRequest.getPlatcust());
                    msgModel.setOrder_no(transferToPersonRequest.getOrder_no());
                    msgModel.setPlat_no(transTransReq.getPlat_no());
                    msgModel.setTrans_code(transTransReq.getTrans_code());
    //                msgModel.setMobile(eaccUserInfo.getMobile());
                    msgModel.setMall_no(eaccUserInfo.getMall_no());
                    msgModel.setPlatcust(eaccUserInfo.getMallcust());
                    String content=sysParameterService.querySysParameter(transferToPersonRequest.getMall_no(),MsgContent.REPAY_NOTIFY.getMsg());
                    if(StringUtils.isNotBlank(content)){
                        msgModel.setMsgContent(String.format(content,
                                mall_name, NumberUtils.formatNumber(transferToPersonRequest.getAmount()), mall_name));
                        //=========ccb参数===========
                        msgModel.setTrans_serial(transTransReq.getTrans_serial());
                        msgModel.setMsg_type(MsgType.REPAY_NOTIFY.getType());
                        msgModel.setAmount(transferToPersonRequest.getAmount());
                        //===========================
                        sendMsgService.addMsgToQueue(msgModel);
                    }
                }
            } catch (Exception e) {
                logger.error("平台转个人发送短信失败", e);
            }
        return baseResponse;
    }






    /**
     *  （批量）平台转帐个人
     * @param batchTransferToPersonRequest
     * @return BatchTransferToPersonResponse 平台转帐个人返回数据
     * @throws BusinessException 平台转帐个人失败，抛异常
     */
    @Override
    public BatchTransferToPersonResponse batchTransferToPerson(BatchTransferToPersonRequest batchTransferToPersonRequest) throws BusinessException {

        BatchTransferToPersonResponse batchTransferToPersonResponse = new BatchTransferToPersonResponse();

        List<BatchTransferToPersonResponseDetail> batchTransferToPersonResponseDetails = new ArrayList<>();

        BaseRequest baseRequest = batchTransferToPersonRequest;

        logger.info("【批量平台转个人】,批次号："+batchTransferToPersonRequest.getOrder_no());
        //记录流水
        TransTransreq transTransReq = transReqService.getTransTransReq(batchTransferToPersonRequest.clone(),TransConsts.PLAT_TO_ACCOUNT_CODE,TransConsts.PLAT_TO_ACCOUNT_NAME,true);
        try{
            transReqService.insert(transTransReq);
        }catch (Exception e){
            throw new BusinessException(BusinessMsg.RUNTIME_EXCEPTION,BusinessMsg.getMsg(BusinessMsg.RUNTIME_EXCEPTION));
        }
        try {
            //校验平台账户
            if(!Ssubject.PLAT.getCode().equals(batchTransferToPersonRequest.getPlat_account())){
                logger.info("【平台转个人】平台账户不是11，平台账户不合法，请求参数为："+batchTransferToPersonRequest.getPlat_account());
                throw new BusinessException(BusinessMsg.PARAMETER_ERROR, BusinessMsg.getMsg(BusinessMsg.PARAMETER_ERROR) + "，平台账户不合法");
            }
            //查询平台账户
            AccountSubjectInfo plataccountSubjectInfo = accountQueryService.queryAccount(batchTransferToPersonRequest.getMer_no(), batchTransferToPersonRequest.getMer_no(), Tsubject.CASH.getCode(), batchTransferToPersonRequest.getPlat_account());
            if (plataccountSubjectInfo == null) {
                logger.info("平台账户不存在");
                throw new BusinessException(BusinessMsg.INVALID_ACCOUNT, BusinessMsg.getMsg(BusinessMsg.INVALID_ACCOUNT) + "平台账户不存在");
            }
            for (BatchTransferToPersonRequestDeatil batchTransferToPersonRequestDeatil : batchTransferToPersonRequest.getBatchTransferToPersonRequestDeatils()) {
                BatchTransferToPersonResponseDetail batchTransferToPersonResponseDetail = new BatchTransferToPersonResponseDetail();
                try {
                    batchTransferToPersonResponseDetail = batchTransferToPersonService.batchTransferToPerson(plataccountSubjectInfo,baseRequest,batchTransferToPersonRequestDeatil,batchTransferToPersonRequest.getNotify_url());
                }catch (BusinessException b){
                    batchTransferToPersonResponseDetail.setAmount(batchTransferToPersonRequestDeatil.getAmount());
                    batchTransferToPersonResponseDetail.setDetail_no(batchTransferToPersonRequestDeatil.getDetail_no());
                    batchTransferToPersonResponseDetail.setPlatcust(batchTransferToPersonRequestDeatil.getPlatcust());
                    batchTransferToPersonResponseDetail.setStatus(FlowStatus.FAIL.getCode());
                    batchTransferToPersonResponseDetail.setError_code(b.getBaseResponse().getRecode());
                    batchTransferToPersonResponseDetail.setError_info(b.getBaseResponse().getRemsg());
                }
                batchTransferToPersonResponseDetails.add(batchTransferToPersonResponseDetail);
            }

            batchTransferToPersonResponse.setOrder_status(OrderStatus.PROCESSING.getCode());
            batchTransferToPersonResponse.setOrder_info(OrderStatus.PROCESSING.getMsg());

            //更新流水
            transTransReq.setStatus(OrderStatus.SUCCESS.getCode());
            transTransReq.setReturn_code(BusinessMsg.SUCCESS);
            transTransReq.setReturn_msg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));
            transReqService.insert(transTransReq);
        }catch (Exception e){
            logger.error("平台转个人失败：",e);
            BaseResponse baseResponse=new BaseResponse();
            if(e instanceof BusinessException){
                baseResponse=((BusinessException) e).getBaseResponse();
            }else{
                logger.error(e);
                baseResponse.setRecode(BusinessMsg.DATEBASE_EXCEPTION);
                baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.DATEBASE_EXCEPTION));
            }
            //更新流水
            transTransReq.setStatus(FlowStatus.FAIL.getCode());
            transTransReq.setReturn_code(baseResponse.getRecode());
            transTransReq.setReturn_msg(baseResponse.getRemsg());
            transReqService.insert(transTransReq);
            throw new BusinessException(baseResponse);
        }
        return batchTransferToPersonResponse;
    }

    /**
     * 个人转帐平台
     * @param transferToPersonRequest
     * @return
     * @throws BusinessException
     */
    public BaseResponse transferFromPerson(TransferToPersonRequest transferToPersonRequest)throws BusinessException{
        BaseResponse baseResponse = new BaseResponse();
//
//        logger.error("【个人转帐平台】");
//
//        //记录流水
//        TransTransreq transTransReq = transReqService.getTransTransReq(transferToPersonRequest.clone(),TransConsts.TO_PLAT_ACCOUNT_CODE,TransConsts.TO_PLAT_ACCOUNT_NAME,false);
//        transReqService.insert(transTransReq);
//
//        try{
//            //查询电子账户，优先转电子账户
//            AccountSubjectInfo bankAccountSubjectInfo = accountQueryService.queryAccount(transferToPersonRequest.getMall_no(),transferToPersonRequest.getPlatcust(),Tsubject.CASH.getCode(), Ssubject.EACCOUNT.getCode());
//            if (bankAccountSubjectInfo == null){
//                logger.error("【个人转帐平台】电子账户不存在");
//                //电子账户不存在就转投资人的现金账户
//                bankAccountSubjectInfo = accountQueryService.queryAccount(transferToPersonRequest.getMer_no(),transferToPersonRequest.getPlatcust(),Tsubject.CASH.getCode(),Ssubject.INVEST.getCode());
//                if(bankAccountSubjectInfo == null){
//                    logger.info("【个人转帐平台】投资账户不存在" );
//
//                    throw new BusinessException(BusinessMsg.INVALID_ACCOUNT,BusinessMsg.getMsg(BusinessMsg.INVALID_ACCOUNT) +  "，个人投资账户不存在");
//                }
//            }
//
//            //查询平台账户
//            AccountSubjectInfo plataccountSubjectInfo = accountQueryService.queryAccount(transferToPersonRequest.getMer_no(),null, Tsubject.CASH.getCode(), Ssubject.PLAT.getCode());
//            if (plataccountSubjectInfo == null){
//                logger.info("【个人转帐平台】平台账户不存在");
//
//                throw new BusinessException(BusinessMsg.INVALID_ACCOUNT,BusinessMsg.getMsg(BusinessMsg.INVALID_ACCOUNT) +  "，平台账户不存在");
//            }
//
//            EaccAccountamtlist eaccAccountamtlist = new EaccAccountamtlist();
//            eaccAccountamtlist.setTrans_serial(transTransReq.getTrans_serial());
//            eaccAccountamtlist.setPlat_no(bankAccountSubjectInfo.getPlat_no());
//            eaccAccountamtlist.setPlatcust(bankAccountSubjectInfo.getPlatcust());
//            eaccAccountamtlist.setSubject(bankAccountSubjectInfo.getSubject());
//            eaccAccountamtlist.setSub_subject(bankAccountSubjectInfo.getSub_subject());
//            eaccAccountamtlist.setOppo_platcust(plataccountSubjectInfo.getPlatcust());
//            eaccAccountamtlist.setOppo_subject(plataccountSubjectInfo.getSubject());
//            eaccAccountamtlist.setOppo_sub_subject(plataccountSubjectInfo.getSub_subject());
//            eaccAccountamtlist.setTrans_code(TransConsts.TO_PLAT_ACCOUNT_CODE);
//            eaccAccountamtlist.setTrans_name(TransConsts.TO_PLAT_ACCOUNT_NAME);
//            eaccAccountamtlist.setTrans_date(transferToPersonRequest.getPartner_trans_date());
//            eaccAccountamtlist.setTrans_time(transferToPersonRequest.getPartner_trans_time());
//            eaccAccountamtlist.setAmt(transferToPersonRequest.getAmount());
//            accountTransferService.transfer(eaccAccountamtlist);
//
//            //更新流水
//            transTransReq.setStatus(FlowStatus.SUCCESS.getCode());
//            transTransReq.setReturn_code(BusinessMsg.SUCCESS);
//            transTransReq.setReturn_msg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));
//            transReqService.insert(transTransReq);
//
//            baseResponse.setRecode(BusinessMsg.SUCCESS);
//            baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));
//
//        }catch (Exception e){
//            logger.error("个人转平台失败：",e);
//
//            BaseResponse baseResponse2 = new BaseResponse();
//            if(e instanceof BusinessException){
//                baseResponse2=((BusinessException) e).getBaseResponse();
//            }else{
//                logger.error(e);
//                baseResponse2.setRecode(BusinessMsg.DATEBASE_EXCEPTION);
//                baseResponse2.setRemsg(BusinessMsg.getMsg(BusinessMsg.DATEBASE_EXCEPTION));
//            }
//
//            //更新流水
//            transTransReq.setStatus(FlowStatus.FAIL.getCode());
//            transTransReq.setReturn_code(baseResponse2.getRecode());
//            transTransReq.setReturn_msg(baseResponse2.getRemsg());
//            transReqService.insert(transTransReq);
//        }

        return baseResponse;
    }

    /**
     *  @author pzy
     *  平台不同账户转账
     * @param transferToPlatformRequest
     * @return TransferToPlatformResponse
     * @throws BusinessException 平台不同账户转账失败，抛异常
     */
    @Override
    public TransferToPlatformResponse transferToPlatform(TransferToPlatformRequest transferToPlatformRequest) throws BusinessException {
        TransferToPlatformResponse transferToPlatformResponse = new TransferToPlatformResponse();

        //记录流水
        TransTransreq transTransReq = transReqService.getTransTransReq(transferToPlatformRequest.clone(),TransConsts.PLAT_CONVERSE_ACCOUNTS_CODE,TransConsts.PLAT_CONVERSE_ACCOUNTS_NAME,false);
        transReqService.insert(transTransReq);

        try{
            //校验提现金额
            if (transferToPlatformRequest.getAmt() == null || transferToPlatformRequest.getAmt().compareTo(BigDecimal.ZERO) < 1) {
                BaseResponse baseResponse = new BaseResponse();
                baseResponse.setRecode(BusinessMsg.MONEY_ERROR);
                baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.MONEY_ERROR));
                throw new BusinessException(baseResponse);
            }

            //查询来源账户
            AccountSubjectInfo sourcePlataccountSubjectInfo = accountQueryService.queryAccount(transferToPlatformRequest.getMer_no(), null, Tsubject.CASH.getCode(), transferToPlatformRequest.getSource_account());
            if (sourcePlataccountSubjectInfo == null){
                BaseResponse baseResponse=new BaseResponse();
                baseResponse.setRecode(BusinessMsg.INVALID_ACCOUNT);
                baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.INVALID_ACCOUNT) + "来源账户不存在");
                throw new BusinessException(baseResponse);
            }


            //查询来源账户
            AccountSubjectInfo destPlataccountSubjectInfo = accountQueryService.queryAccount(transferToPlatformRequest.getMer_no(), null, Tsubject.CASH.getCode(), transferToPlatformRequest.getDest_account());
            if (destPlataccountSubjectInfo == null){
                BaseResponse baseResponse=new BaseResponse();
                baseResponse.setRecode(BusinessMsg.INVALID_ACCOUNT);
                baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.INVALID_ACCOUNT) + "目标账户不存在");
                throw new BusinessException(baseResponse);
            }

            EaccAccountamtlist eaccAccountamtlist = new EaccAccountamtlist();
            eaccAccountamtlist.setOrder_no(transferToPlatformRequest.getOrder_no());
            //账户，科目，子科目
            eaccAccountamtlist.setPlat_no(sourcePlataccountSubjectInfo.getPlat_no());
            eaccAccountamtlist.setPlatcust(sourcePlataccountSubjectInfo.getPlatcust());
            eaccAccountamtlist.setSubject(sourcePlataccountSubjectInfo.getSubject());
            eaccAccountamtlist.setSub_subject(sourcePlataccountSubjectInfo.getSub_subject());

            //对手的账户，科目，子科目
            eaccAccountamtlist.setOppo_platcust(destPlataccountSubjectInfo.getPlatcust());
            eaccAccountamtlist.setOppo_subject(destPlataccountSubjectInfo.getSubject());
            eaccAccountamtlist.setOppo_sub_subject(destPlataccountSubjectInfo.getSub_subject());

            //交易流水号
            eaccAccountamtlist.setTrans_serial(transTransReq.getTrans_serial());

            //出账的交易代码，交易名称
            eaccAccountamtlist.setTrans_code(TransConsts.TRANSFER_ACCOUNTS_CODE);
            eaccAccountamtlist.setTrans_name(TransConsts.TRANSFER_ACCOUNTS_NAME);
            eaccAccountamtlist.setTrans_date(transferToPlatformRequest.getPartner_trans_date());
            eaccAccountamtlist.setTrans_time(transferToPlatformRequest.getPartner_trans_time());

            //转账金额
            eaccAccountamtlist.setAmt(transferToPlatformRequest.getAmt());

//            newAccountTransferService.transfer(eaccAccountamtlist);
            accountTransferService.transfer(eaccAccountamtlist);

            transferToPlatformResponse.setRecode(BusinessMsg.SUCCESS);
            transferToPlatformResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));

            //更新流水
            transTransReq.setStatus(FlowStatus.SUCCESS.getCode());
            transTransReq.setReturn_code(BusinessMsg.SUCCESS);
            transTransReq.setReturn_msg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));
            transReqService.insert(transTransReq);

        }catch (Exception e){

            BaseResponse baseResponse=new BaseResponse();
            if(e instanceof BusinessException){
                baseResponse=((BusinessException) e).getBaseResponse();
            }else{
                logger.error(e);
                baseResponse.setRecode(BusinessMsg.DATEBASE_EXCEPTION);
                baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.DATEBASE_EXCEPTION));
            }

            //更新流水
            transferToPlatformResponse.setRecode(baseResponse.getRecode());
            transferToPlatformResponse.setRemsg(baseResponse.getRemsg());
            transTransReq.setStatus(FlowStatus.FAIL.getCode());
            transTransReq.setReturn_code(baseResponse.getRecode());
            transTransReq.setReturn_msg(baseResponse.getRemsg());
            transReqService.insert(transTransReq);
        }

        return transferToPlatformResponse;
    }

//    @Override
//    public List<EaccAccountamtlist> downAccountList(String platcust, String startDate, String endDate) throws BusinessException {
//        List<EaccAccountamtlist> eaccAccountamtlistList=null;
//            try {
//                 eaccAccountamtlistList=platformFeeQueryService.accountFlowListQuery(platcust,startDate,endDate);
//            }catch (Exception e){
//                BaseResponse baseResponse=new BaseResponse();
//                if(e instanceof BusinessException){
//                    baseResponse=((BusinessException) e).getBaseResponse();
//                }else{
//                    baseResponse.setRecode(BusinessMsg.DATEBASE_EXCEPTION);
//                    baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.DATEBASE_EXCEPTION));
//                }
//                throw new BusinessException(baseResponse);
//            }
//        return eaccAccountamtlistList;
//    }

    @Override
    public BaseResponse rollback_plat2person(TransferToPersonBackRequest transferToPersonBackRequest) {
        BaseResponse baseResponse=new BaseResponse();
        baseResponse.setRecode(BusinessMsg.SUCCESS);
        baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));
        logger.info("【平台转个人撤销】:"+JSON.toJSON(transferToPersonBackRequest));

        //记录流水
        TransTransreq transTransReq = transReqService.getTransTransReq(transferToPersonBackRequest.clone(),TransConsts.PLAT_TO_ACCOUNT_BACK_CODE,TransConsts.PLAT_TO_ACCOUNT_BACK_NAME,false);
        transReqService.insert(transTransReq);

        //锁定撤销订单
        String lockKey = getLockKey(transferToPersonBackRequest.getMer_no()+transferToPersonBackRequest.getOri_order_no());
        boolean lock = CacheUtil.getLock(lockKey);
        int lockCount=0;
        while (!lock) {
            while (!lock) {
                sleep(50);
                lock = CacheUtil.getLock(lockKey);
                lockCount++;
                if (lockCount >= 5) {
                    logger.info("【平台转个人撤销】尝试获取锁超过最大次数，放弃");
                    throw new  BusinessException(BusinessMsg.SERVER_BUSY,BusinessMsg.getMsg(BusinessMsg.SERVER_BUSY));
                }
            }
        }
        try{

            PlatTransCustExample platTransCustExample=new PlatTransCustExample();
            PlatTransCustExample.Criteria criteria=platTransCustExample.createCriteria();
            criteria.andOrder_noEqualTo(transferToPersonBackRequest.getOri_order_no());
            criteria.andMall_noEqualTo(transferToPersonBackRequest.getMall_no());
            criteria.andPlat_noEqualTo(transferToPersonBackRequest.getMer_no());
            criteria.andEnabledEqualTo(Constants.ENABLED);
            List<PlatTransCust> platTransCustslist= platTransCustMapper.selectByExample(platTransCustExample);
            if(null==platTransCustslist||platTransCustslist.size()==0){
                baseResponse.setRecode(BusinessMsg.PARAMETER_ERROR);
                baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.PARAMETER_ERROR)+",未查询到平台转个人订单信息");
                throw new BusinessException(baseResponse);
            }
            PlatTransCust platTransCust=platTransCustslist.get(0);

            if(0==BigDecimal.ZERO.compareTo(platTransCust.getBalance())){
                baseResponse.setRecode(BusinessMsg.PARAMETER_ERROR);
                baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.PARAMETER_ERROR)+",订单已撤销,不可重复撤销");
                throw new BusinessException(baseResponse);
            }
            if(0!=transferToPersonBackRequest.getAmount().compareTo(platTransCust.getBalance())){
                baseResponse.setRecode(BusinessMsg.MONEY_ERROR);
                baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.MONEY_ERROR)+",请求撤销金额与原转账金额不符");
                throw new BusinessException(baseResponse);
            }


            //查询平台账户
            AccountSubjectInfo plataccountSubjectInfo = accountQueryService.queryAccount(platTransCust.getPlat_no(),platTransCust.getPlat_no(), platTransCust.getSubject(), platTransCust.getSub_subject());
            if (plataccountSubjectInfo == null){

                logger.info("平台账户不存在" );
                baseResponse.setRecode(BusinessMsg.INVALID_ACCOUNT);
                baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.INVALID_ACCOUNT)+"平台账户不存在");
                throw new BusinessException(baseResponse);
            }

            //查询电子账户
            AccountSubjectInfo bankAccountSubjectInfo = accountQueryService.queryAccount(platTransCust.getMall_no(),platTransCust.getPlatcust(), Tsubject.CASH.getCode(), Ssubject.EACCOUNT.getCode());
            if (bankAccountSubjectInfo == null){
                logger.info("电子账户不存在" );
                //现金投资账户
                bankAccountSubjectInfo = accountQueryService.queryAccount(platTransCust.getPlat_no(),platTransCust.getPlatcust(),Tsubject.CASH.getCode(),platTransCust.getOppo_sub_subject());

                if(bankAccountSubjectInfo == null){

                    logger.info("投资或融资账户不存在" );
                    baseResponse.setRecode(BusinessMsg.INVALID_ACCOUNT);
                    baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.INVALID_ACCOUNT)+"投资或融资账户不存在");
                    throw new BusinessException(baseResponse);
                }
            }

            if(0==BigDecimal.ZERO.compareTo(platTransCust.getBalance())){
                baseResponse.setRecode(BusinessMsg.PARAMETER_ERROR);
                baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.PARAMETER_ERROR)+",订单已撤销,不可重复撤销");
                throw new BusinessException(baseResponse);
            }
            //进行转账以及扣除额度
            doRollBack(transferToPersonBackRequest,plataccountSubjectInfo,bankAccountSubjectInfo,platTransCust,transTransReq);

        }catch (Exception e){
            logger.error("平台转个人撤销失败：",e);
            if(e instanceof BusinessException){
                baseResponse = ((BusinessException) e).getBaseResponse();
            }else{
                logger.error(e);
                baseResponse.setRecode(BusinessMsg.DATEBASE_EXCEPTION);
                baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.DATEBASE_EXCEPTION));
            }

            //更新流水
            transTransReq.setStatus(FlowStatus.FAIL.getCode());
            transTransReq.setReturn_code(baseResponse.getRecode());
            transTransReq.setReturn_msg(baseResponse.getRemsg());
            transReqService.insert(transTransReq);
            return baseResponse;
        }finally {
            CacheUtil.unlock(lockKey);
        }
        transTransReq.setStatus(FlowStatus.SUCCESS.getCode());
        transTransReq.setReturn_code(BusinessMsg.SUCCESS);
        transTransReq.setReturn_msg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));
        transReqService.insert(transTransReq);


        return baseResponse;
    }

    /**
     *  资金冻结解冻
     * @param freezeFundRequest
     * @return FreezeFundResponse 冻结解冻成功
     * @throws BusinessException 冻结解冻失败，抛异常
     */
    @Override
    public FreezeFundResponse freezeFund(FreezeFundRequest freezeFundRequest) {

        logger.info("【资金冻结解冻】-->dubbo-->order_no:"+freezeFundRequest.getOrder_no());

        FreezeFundResponse freezeFundResponse = new FreezeFundResponse();

        //记录业务流水
        TransTransreq transTransReq = new TransTransreq();

        //交易代码 交易名称
        String trans_code = null;
        String trans_name = null;

        //判断是冻结还是解冻
        boolean b = true;
        if (AmtType.FROZEN.getCode().equals(freezeFundRequest.getFreeze_flg())) {
            trans_code = TransConsts.FUND_FREEZE_CODE;
            trans_name = TransConsts.FUND_FREEZE_NAME;
        } else {
            trans_code = TransConsts.FUND_UNFROZEN_CODE;
            trans_name = TransConsts.FUND_UNFROZEN_NAME;
            b = false;
        }

        transTransReq = transReqService.getTransTransReq(freezeFundRequest.clone(), trans_code, trans_name, false);
        transTransReq.setPlatcust(freezeFundRequest.getPlatcust());
        transReqService.insert(transTransReq);
        logger.info("【资金冻结解冻】-->添加流水成功-->order_no:"+freezeFundRequest.getOrder_no());

        try {

            AccountSubjectInfo plataccountSubjectInfo = null;
            //查询账户是否存在
            if (Ssubject.INVEST.getCode().equals(freezeFundRequest.getAccount_type())) {
                plataccountSubjectInfo = accountQueryService.queryAccount(freezeFundRequest.getMer_no(), freezeFundRequest.getPlatcust(), Tsubject.CASH.getCode(), Ssubject.INVEST.getCode());
            } else if (Ssubject.FINANCING.getCode().equals(freezeFundRequest.getAccount_type())) {
                plataccountSubjectInfo = accountQueryService.queryAccount(freezeFundRequest.getMer_no(), freezeFundRequest.getPlatcust(), Tsubject.CASH.getCode(), Ssubject.FINANCING.getCode());
            } else if (Ssubject.EACCOUNT.getCode().equals(freezeFundRequest.getAccount_type())) {
                plataccountSubjectInfo = accountQueryService.queryAccount(freezeFundRequest.getMall_no(), freezeFundRequest.getPlatcust(), Tsubject.CASH.getCode(), Ssubject.EACCOUNT.getCode());
            } else {
                logger.info("【资金冻结解冻】-->账户类型不存在-->order_no:" + freezeFundRequest.getOrder_no());
                throw new BusinessException(BusinessMsg.PARAMETER_ERROR, BusinessMsg.getMsg(BusinessMsg.PARAMETER_ERROR) + "，账户类型不存在");
            }

            if (plataccountSubjectInfo == null) {
                logger.info("【资金冻结解冻】-->账户不存在-->order_no:" + freezeFundRequest.getOrder_no());
                throw new BusinessException(BusinessMsg.INVALID_ACCOUNT, BusinessMsg.getMsg(BusinessMsg.INVALID_ACCOUNT));
            }
            EaccAccountamtlist eaccAccountamtlist = new EaccAccountamtlist();
            eaccAccountamtlist.setTrans_serial(transTransReq.getTrans_serial());
            eaccAccountamtlist.setOrder_no(transTransReq.getOrder_no());
            eaccAccountamtlist.setPlat_no(plataccountSubjectInfo.getPlat_no());
            eaccAccountamtlist.setPlatcust(plataccountSubjectInfo.getPlatcust());
            eaccAccountamtlist.setSubject(plataccountSubjectInfo.getSubject());
            eaccAccountamtlist.setSub_subject(plataccountSubjectInfo.getSub_subject());
            eaccAccountamtlist.setTrans_code(trans_code);
            eaccAccountamtlist.setTrans_name(trans_name);
            eaccAccountamtlist.setTrans_date(freezeFundRequest.getPartner_trans_date());
            eaccAccountamtlist.setTrans_time(freezeFundRequest.getPartner_trans_time());
            eaccAccountamtlist.setAmt(freezeFundRequest.getAmount());
            if (b) {
                eaccAccountamtlist.setAmt_type(AmtType.FROZEN.getCode());
                accountFrozenService.freeze(eaccAccountamtlist);
                logger.info("【资金冻结解冻】-->冻结成功-->order_no:"+freezeFundRequest.getOrder_no());
            } else {
                if (StringUtils.isBlank(freezeFundRequest.getFreeze_order_no())) {
                    logger.info("【资金冻结解冻】-->解冻时原订单号不能为空-->order_no:"+freezeFundRequest.getOrder_no());
                    throw new BusinessException(BusinessMsg.PARAMETER_LACK, BusinessMsg.getMsg(BusinessMsg.PARAMETER_LACK)+",解冻时原订单号不能为空");
                }
                TransTransreq transTransReqByOrderno = transReqService.queryTransTransReqByOrderno(freezeFundRequest.getFreeze_order_no());
                eaccAccountamtlist.setTrans_serial(transTransReqByOrderno.getTrans_serial());
                eaccAccountamtlist = accountTransferService.queryTransFlow(eaccAccountamtlist);
                eaccAccountamtlist.setAmt_type(AmtType.UNFROZEN.getCode());
                accountFrozenService.unfreeze(eaccAccountamtlist);
                logger.info("【资金冻结解冻】-->解冻成功-->order_no:"+freezeFundRequest.getOrder_no());
            }

            FreezeFundData freezeFundData = new FreezeFundData();
            freezeFundData.setOrder_no(freezeFundRequest.getOrder_no());
            freezeFundData.setOrder_status(OrderStatus.SUCCESS.getCode());
            freezeFundData.setProcess_date(DateUtils.todayStr(DateUtils.DEF_FORMAT_STRING));
            freezeFundData.setQuery_id(eaccAccountamtlist.getTrans_serial());
            freezeFundData.setAmount(eaccAccountamtlist.getAmt());
            freezeFundData.setPlatcust(plataccountSubjectInfo.getPlatcust());

            freezeFundResponse.setRecode(BusinessMsg.SUCCESS);
            freezeFundResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));
            freezeFundResponse.setFreezeFundData(freezeFundData);

            //更新流水
            transTransReq.setStatus(FlowStatus.SUCCESS.getCode());
            transTransReq.setReturn_code(BusinessMsg.SUCCESS);
            transTransReq.setReturn_msg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));
            transReqService.insert(transTransReq);

        } catch (Exception e) {

            logger.error("【资金冻结解冻】-->异常-->order_no:"+freezeFundRequest.getOrder_no(), e);

            BaseResponse baseResponse = new BaseResponse();
            if (e instanceof BusinessException) {
                baseResponse = ((BusinessException) e).getBaseResponse();
            } else {
                logger.error(e);
                baseResponse.setRecode(BusinessMsg.UNKNOW_ERROE);
                baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.UNKNOW_ERROE));
            }

            //更新流水
            transTransReq.setStatus(FlowStatus.FAIL.getCode());
            transTransReq.setReturn_code(baseResponse.getRecode());
            transTransReq.setReturn_msg(baseResponse.getRemsg());
            transReqService.insert(transTransReq);

            throw new BusinessException(baseResponse);
        }

        return freezeFundResponse;
    }


    @Transactional
    public void doRollBack(TransferToPersonBackRequest transferToPersonBackRequest, AccountSubjectInfo plataccountSubjectInfo, AccountSubjectInfo bankAccountSubjectInfo,
                           PlatTransCust platTransCust, TransTransreq transTransReq) throws Exception {

        EaccAccountamtlist eaccAccountamtlist = new EaccAccountamtlist();
        eaccAccountamtlist.setOrder_no(transferToPersonBackRequest.getOrder_no());
        //账户，科目，子科目
        eaccAccountamtlist.setPlat_no(plataccountSubjectInfo.getPlat_no());
        eaccAccountamtlist.setPlatcust(bankAccountSubjectInfo.getPlatcust());
        eaccAccountamtlist.setSubject(bankAccountSubjectInfo.getSubject());
        eaccAccountamtlist.setSub_subject(bankAccountSubjectInfo.getSub_subject());

        //对手的账户，科目，子科目
        eaccAccountamtlist.setOppo_plat_no(plataccountSubjectInfo.getPlat_no());
        eaccAccountamtlist.setOppo_platcust(plataccountSubjectInfo.getPlatcust());
        eaccAccountamtlist.setOppo_subject(plataccountSubjectInfo.getSubject());
        eaccAccountamtlist.setOppo_sub_subject(plataccountSubjectInfo.getSub_subject());

        //交易流水号
        eaccAccountamtlist.setTrans_serial(transTransReq.getTrans_serial());

        //出账的交易代码，交易名称
        eaccAccountamtlist.setTrans_code(TransConsts.PLAT_TO_ACCOUNT_BACK_CODE);
        eaccAccountamtlist.setTrans_name(TransConsts.PLAT_TO_ACCOUNT_BACK_NAME);
        eaccAccountamtlist.setTrans_date(transferToPersonBackRequest.getPartner_trans_date());
        eaccAccountamtlist.setTrans_time(transferToPersonBackRequest.getPartner_trans_time());

        //转账金额
        eaccAccountamtlist.setAmt(transferToPersonBackRequest.getAmount());

        //调用新转账接口
        //newAccountTransferService.transfer(eaccAccountamtlist);
        accountTransferService.transfer(eaccAccountamtlist);

        //更新剩余限额额度
        platTransCust.setBalance(transferToPersonBackRequest.getAmount());

        custPlatTransCustMapper.substractBalance(platTransCust);
    }

    private String getPayment_plat_no(String plat_no) throws BusinessException {
        PlatPaycodeExample platPaycodeExample = new PlatPaycodeExample();
        PlatPaycodeExample.Criteria criteria = platPaycodeExample.createCriteria();
        criteria.andPlat_noEqualTo(plat_no);
        criteria.andPay_codeEqualTo("020");
        criteria.andEnabledEqualTo(Constants.ENABLED);
        List<PlatPaycode> platPaycodeList = platPaycodeMapper.selectByExample(platPaycodeExample);
        if (platPaycodeList.size() <= 0) {
            logger.info("=======找不到pay_code：020=============");
            throw new BusinessException(BusinessMsg.NO_CHANNEL_INFORMATION,
                    BusinessMsg.getMsg(BusinessMsg.NO_CHANNEL_INFORMATION) + "：转账失败，没有相应渠道信息");
        }
        return platPaycodeList.get(0).getPayment_plat_no();
    }
    /**
     * 平台充值
     * @param chargeRequest
     * @throws BusinessException
     */
    @Override
    public ChargeResponse charge(ChargeRequest chargeRequest)throws BusinessException {
        logger.info("================【平台充值】开始==============参数：" + JSON.toJSON(chargeRequest));
        //记录平台充值业务流水表
        TransTransreq transTransReq = transReqService.getTransTransReq(chargeRequest.clone(), TransConsts.PLAT_CHARGE_CODE, TransConsts.PLAT_CHARGE_NAME, false);
        String trans_serial = transTransReq.getTrans_serial();
        transReqService.insert(transTransReq);
        logger.info("记录平台充值流水成功");
        try {
            //校验充值金额是否合规
            if (chargeRequest.getAmount() == null || chargeRequest.getAmount().compareTo(BigDecimal.ZERO) < 1) {
                logger.info("================【平台充值】金额错误==============");
                throw new BusinessException(BusinessMsg.MONEY_ERROR, BusinessMsg.getMsg(BusinessMsg.MONEY_ERROR));
            }

            if (!("0".equals(chargeRequest.getRecharge_type())) && !("1".equals(chargeRequest.getRecharge_type()))) {
                logger.info("================【平台充值】充值类型错误==============");
                throw new BusinessException(BusinessMsg.PARAMETER_ERROR, BusinessMsg.getMsg(BusinessMsg.PARAMETER_ERROR) + "充值类型错误，0-自有金充值");
            }

            String host = sysParameterService.querySysParameter(SysParamterKey.FTDM, URLConfigUtil.EPAY_SERVER_KEY);
            String url = sysParameterService.querySysParameter(SysParamterKey.FTDM, URLConfigUtil.AGENT_PAY_REAL);
            // 平台充值异步回调地址
            String ownUrl = sysParameterService.querySysParameter(SysParamterKey.FTDM, URLConfigUtil.QUICK_PAY_URL_NOTIFY);
            String ftdm_url = sysParameterService.querySysParameter(SysParamterKey.FTDM, URLConfigUtil.WEB_LOCAL_SERVER);
            //行内划转pay_code 020
            PlatPaycode platPayCode = accountSearchService.queryPlatPayCode(chargeRequest.getMer_no(), "020");
            if (null == platPayCode) {
                throw new BusinessException(BusinessMsg.DATEBASE_EXCEPTION, "未查询到行内划转渠道配置信息");
            }
            String partner_id = platPayCode.getPayment_plat_no();
            ownUrl = ftdm_url + ownUrl;

            Map<String, Object> eMap = new HashMap<String, Object>();
            //出金一方
            String payNoJson = platCacheService.queryCardInfoToCache(chargeRequest.getMer_no().toString(), ("0".equals(chargeRequest.getRecharge_type().toString()) ? PlatAccType.PLATOWN.getCode() : PlatAccType.PLATCASH.getCode()));
            JSONObject payNoJsonObj = JSON.parseObject(payNoJson);
            //入金一方
            String cardJson = platCacheService.queryCardInfoToCache(chargeRequest.getMer_no().toString(), PlatAccType.PLATTRUST.getCode());
            RwRecharge rwRecharge = new RwRecharge();

            rwRecharge.setTrans_serial(trans_serial);
            rwRecharge.setPlatcust(chargeRequest.getMer_no().toString());
            rwRecharge.setOrder_no(chargeRequest.getOrder_no());//订单号
            rwRecharge.setCharge_type(Ssubject.PLAT.getCode());//合规后限制为自有
            rwRecharge.setPlat_no(chargeRequest.getMer_no().toString());//平台编号
            rwRecharge.setTrans_amt(chargeRequest.getAmount());
            rwRecharge.setTrans_code(TransConsts.PLAT_CHARGE_CODE);//交易代码
            rwRecharge.setTrans_name(TransConsts.PLAT_CHARGE_NAME);//交易名称
            rwRecharge.setPay_code("020");
            rwRecharge.setClient_property("1");
            rwRecharge.setHost_req_serial_no(trans_serial);
            rwRecharge.setTrans_date(chargeRequest.getPartner_trans_date());//交易日期
            rwRecharge.setTrans_time(chargeRequest.getPartner_trans_time());//交易时间
            rwRecharge.setReques_time(new Date());//交易请求开始时间
            rwRecharge.setConfirm_time(new Date());//交易请求确认时间
            rwRecharge.setReturn_url(ownUrl);
            rwRecharge.setNotify_url(chargeRequest.getNotify_url());//商户异步通知URL
            rwRecharge.setStatus(OrderStatus.PROCESSING.getCode());//处理状态
            rwRecharge.setEnabled(Constants.ENABLED);//删除标记
            rwRecharge.setCreate_time(new Date());//创建时间
            rwRecharge.setReturn_msg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));
            rwRecharge.setExt5(payNoJsonObj.get("card_no").toString());
            rwRecharge.setReturn_code(BusinessMsg.SUCCESS);
            rwRecharge.setReturn_msg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));
            //如果支付那边没传self_bank_flag标识，默认为非本行卡
            rwRecharge.setSelf_bank_flag(IsSelfBank.NO.getCode());
            rwRechargeMapper.insert(rwRecharge);

            JSONObject cardJsonObj = JSON.parseObject(cardJson);
            eMap.put("partner_id", partner_id);
            eMap.put("partner_serial_no", trans_serial);
            eMap.put("partner_trans_date", chargeRequest.getPartner_trans_date());
            eMap.put("partner_trans_time", chargeRequest.getPartner_trans_time());
            eMap.put("occur_balance", chargeRequest.getAmount());
            if (chargeRequest.getOrder_no().toString().length() > 15) {
                eMap.put("summary", chargeRequest.getOrder_no().toString().substring(chargeRequest.getOrder_no().toString().length() - 10));
            } else {
                eMap.put("summary", chargeRequest.getOrder_no());
            }
            eMap.put("bank_id", "04031000");
            eMap.put("card_no", cardJsonObj.get("card_no").toString());//入金一方卡号
            eMap.put("client_name", cardJsonObj.get("card_name").toString());//入金一方户名
            eMap.put("dr_acct", payNoJsonObj.get("card_no").toString());//出金一方卡号
            eMap.put("dr_acct_name", payNoJsonObj.get("card_name").toString());//出金一方户名
            eMap.put("receive_url", ownUrl);
            eMap.put("client_property", "1");//公私标识,0-个人，1-公司
            eMap.put("remark", "platCharge");
            eMap.put("prcptcd", "04031000");//大额行号
            eMap.put("dr_acct_type", "0");//出金一方账户类型,0-借记卡,1-贷记卡,2-存折,3-对公户
            eMap.put("bank_no", payNoJsonObj.get("card_no").toString());//意义不明。。。。
            eMap.put("host", host);
            eMap.put("url", url);
            if (deployEnvironment.equals("CCB")) {
                eMap.put("third_no", payNoJsonObj.get("pay_no").toString());
                eMap.put("tran_type", "pay");
                eMap.put("channelId", platPayCode.getChannel_id());
                JSONArray trandata = new JSONArray();
                Map<String, Object> trandata_map = new HashMap<String, Object>();
                trandata_map.put("card_no", cardJsonObj.get("card_no").toString());
                trandata_map.put("cust_name", cardJsonObj.get("card_name").toString());
                trandata_map.put("seq_no", trans_serial);
                trandata_map.put("tran_amt", chargeRequest.getAmount());
                trandata.add(trandata_map);
                eMap.put("trandata", trandata);
            }
            //调E支付的
            logger.info("======准备向E支付发送平台充值请求的数据:eMap>> " + JSON.toJSON(eMap));

            //返回充值请求结果
            Map<String, Object> resultMap = adapterService.transferOfAccountInBank(eMap);
            logger.info("===========【平台充值】收到三方响应参数：" + resultMap);
            if (null == resultMap) {
                logger.info("===========【平台充值】三方响应为空，");
                throw new BusinessException(BusinessMsg.CALL_REMOTE_ERROR, BusinessMsg.getMsg(BusinessMsg.CALL_REMOTE_ERROR) + "：第三方接口调用失败");
            }
            ChargeResponse chargeResponse = new ChargeResponse();
            OrderData orderData = new OrderData();
            orderData.setQuery_id(trans_serial);
            orderData.setOrder_no(chargeRequest.getOrder_no());
            orderData.setProcess_date(DateUtils.formatDateToStr(new Date()));


            if (null != resultMap.get("self_bank_flag")) {
                rwRecharge.setSelf_bank_flag("0".equals(resultMap.get("self_bank_flag")) ? IsSelfBank.YES.getCode() : IsSelfBank.NO.getCode());
            }

            if (OrderStatus.SUCCESS.getCode().equals(resultMap.get("order_status"))) {
                orderData.setOrder_status(OrderStatus.SUCCESS.getCode());

                EaccAccountamtlist eaccAccountamtlist = new EaccAccountamtlist();
                eaccAccountamtlist.setOrder_no(chargeRequest.getOrder_no());
                eaccAccountamtlist.setTrans_serial(trans_serial);
                eaccAccountamtlist.setPlat_no(chargeRequest.getMer_no().toString());
                eaccAccountamtlist.setPlatcust(chargeRequest.getMer_no().toString());
                eaccAccountamtlist.setSubject(IsSelfBank.YES.getCode().equals(rwRecharge.getSelf_bank_flag()) ? Tsubject.CASH.getCode() : Tsubject.FLOAT.getCode());
                eaccAccountamtlist.setSub_subject(Ssubject.PLAT.getCode());
                eaccAccountamtlist.setAmt(chargeRequest.getAmount());
                eaccAccountamtlist.setTrans_code(TransConsts.PLAT_CHARGE_CODE);
                eaccAccountamtlist.setTrans_name(TransConsts.PLAT_CHARGE_NAME);
                eaccAccountamtlist.setTrans_date(chargeRequest.getPartner_trans_date());
                eaccAccountamtlist.setTrans_time(chargeRequest.getPartner_trans_time());
                eaccAccountamtlist.setPay_code("020");
                //调用新转账接口
//                newAccountTransferService.singleCharge(eaccAccountamtlist);
                accountAssetService.charge(eaccAccountamtlist);
                logger.info("============平台自有加款成功============");
                rwRecharge.setReturn_code(BusinessMsg.SUCCESS);
                rwRecharge.setReturn_msg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));
                rwRecharge.setStatus(OrderStatus.SUCCESS.getCode());

                logger.info("===========【平台充值】成功");
                transTransReq.setReturn_code(BusinessMsg.SUCCESS);
                transTransReq.setReturn_msg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));
                transTransReq.setStatus(FlowStatus.SUCCESS.getCode());
            } else if (OrderStatus.PROCESSING.getCode().equals(resultMap.get("order_status"))) {
                orderData.setOrder_status(OrderStatus.PROCESSING.getCode());
                //插入充值表
                rwRecharge.setReturn_code(BusinessMsg.SUCCESS);
                rwRecharge.setReturn_msg(OrderStatus.PROCESSING.getMsg());
                rwRecharge.setStatus(OrderStatus.PROCESSING.getCode());
                logger.info("===========【平台充值】处理中");
                transTransReq.setReturn_code(BusinessMsg.SUCCESS);
                transTransReq.setReturn_msg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));
                transTransReq.setStatus(FlowStatus.REQUESTSUCCESS.getCode());
            } else if (OrderStatus.FAIL.getCode().equals(resultMap.get("order_status"))) {
                orderData.setOrder_status(OrderStatus.FAIL.getCode());
                rwRecharge.setReturn_code(resultMap.get("recode").toString());
                rwRecharge.setReturn_msg(resultMap.get("remsg").toString());
                rwRecharge.setStatus(OrderStatus.FAIL.getCode());
                logger.info("===========【平台充值】失败");
                transTransReq.setReturn_code(resultMap.get("recode").toString());
                transTransReq.setReturn_msg(resultMap.get("remsg").toString());
                transTransReq.setStatus(FlowStatus.FAIL.getCode());
            } else {
                throw new BusinessException(resultMap.get("recode").toString(), resultMap.get("remsg").toString());
            }
            RwRecharge rwRechargeForCharge = accountSearchService.queryRwRecharge(rwRecharge.getPlat_no(), rwRecharge.getOrder_no());
            if (OrderStatus.PROCESSING.getCode().equals(rwRechargeForCharge.getStatus())) {
                rwRechargeForCharge.setStatus(rwRecharge.getStatus());
                rwRechargeForCharge.setSelf_bank_flag(rwRecharge.getSelf_bank_flag());
                rwRechargeForCharge.setReturn_code(rwRecharge.getReturn_code());
                rwRechargeForCharge.setReturn_msg(rwRecharge.getReturn_msg());
                rwRechargeMapper.updateByPrimaryKeySelective(rwRechargeForCharge);
            }
            transReqService.insert(transTransReq);
            chargeResponse.setRecode(resultMap.get("recode").toString());
            chargeResponse.setRemsg(resultMap.get("remsg").toString());
            chargeResponse.setOrderData(orderData);

            //发送异步通知
            orderData = chargeResponse.getOrderData();
            if(OrderStatus.FAIL.getCode().equals(rwRecharge.getStatus())|| OrderStatus.SUCCESS.getCode().equals(rwRecharge.getStatus())){
                String chargeAsynRqString="";
                String notify_url = chargeRequest.getNotify_url();//平台地址
                PlatAsynRequest platAsynRequest = new PlatAsynRequest();
                platAsynRequest.setPlat_no(chargeRequest.getMer_no());
                platAsynRequest.setAmt(chargeRequest.getAmount());
                platAsynRequest.setOrder_no(chargeRequest.getOrder_no());
                platAsynRequest.setMall_no(chargeRequest.getMall_no());
                if(OrderStatus.SUCCESS.getCode().equals(orderData.getOrder_status())){
                    platAsynRequest.setCode("1");
                    platAsynRequest.setMsg(OrderStatus.SUCCESS.getMsg());
                }
                else if(OrderStatus.FAIL.getCode().equals(orderData.getOrder_status())){
                    platAsynRequest.setMsg(BusinessMsg.getMsg(BusinessMsg.FAIL));
                    platAsynRequest.setCode("2");
                }
                chargeAsynRqString = JSON.toJSONString(platAsynRequest, GlobalConfig.serializerFeature);
                logger.info("【准备发送平台充值异步通知】=============url:"+notify_url+",json:"+chargeAsynRqString);
                NotifyData notifyData = new NotifyData();
                notifyData.setNotifyContent(chargeAsynRqString);
                notifyData.setNotifyUrl(notify_url);
                notifyData.setMall_no(chargeRequest.getMall_no());
                notifyService.addNotify(notifyData);
            }
            return chargeResponse;
        } catch (Exception e) {
            logger.error("【平台充值】异常：", e);
            if (e instanceof BusinessException) {
                BaseResponse baseResponse = ((BusinessException) e).getBaseResponse();
                transTransReq.setReturn_code(baseResponse.getRecode());
                transTransReq.setReturn_msg(baseResponse.getRemsg());
                transTransReq.setStatus(FlowStatus.FAIL.getCode());
            } else {
                transTransReq.setReturn_code(BusinessMsg.DATEBASE_EXCEPTION);
                transTransReq.setReturn_msg(BusinessMsg.getMsg(BusinessMsg.DATEBASE_EXCEPTION));
                transTransReq.setStatus(FlowStatus.FAIL.getCode());
            }
            transReqService.insert(transTransReq);
            throw new BusinessException(transTransReq.getReturn_code(), transTransReq.getReturn_msg());
        }
    }

    /**
     * 平台充值异步
     * @param map
     * @throws BusinessException
     */
    @Transactional
    public Map<String,Object> platChargeAsyn(Map<String,Object> map)throws BusinessException{

        logger.info("================进入平台充值异步业务方法==============参数："+JSON.toJSON(map));
        Map<String,Object> changeBussMap = new HashMap<String,Object>();
        String trans_serial = map.get("third_batch_no").toString();
        List<BankPayPayNotifyData> bankPayPayNotifyDataList = JSON.parseArray(map.get("trandata").toString(),BankPayPayNotifyData.class);
        Map<String,Object> tranDataMap = BeanUtil.transBean2Map(bankPayPayNotifyDataList.get(0));
        String seqNo = tranDataMap.get("seq_no").toString();
        String amt = tranDataMap.get("tran_amt").toString();
        String status = tranDataMap.get("status").toString();
        changeBussMap.put("amt",amt);
        TransTransreq transTransreq=transReqService.queryTransTransReqByOrderNoAndBatchOrderNo(trans_serial,null);
        if(transTransreq==null){
            return null;
        }
        //获取充值信息
        RwRechargeExample example=new RwRechargeExample();
        RwRechargeExample.Criteria criteria=example.createCriteria();
        criteria.andTrans_serialEqualTo(trans_serial);
        criteria.andTrans_codeEqualTo(TransConsts.PLAT_CHARGE_CODE);
        criteria.andTrans_nameEqualTo(TransConsts.PLAT_CHARGE_NAME);
        criteria.andEnabledEqualTo(Constants.ENABLED);
        List<RwRecharge> rwRecharges= rwRechargeMapper.selectByExample(example);
        logger.info("================查询出RwRecharge==============List<RwRecharge>："+JSON.toJSON(rwRecharges));
        if(rwRecharges==null || rwRecharges.size()==0){
            throw new BusinessException(BusinessMsg.PARAMETER_ERROR,"查询不到RwRecharge信息");
        }
        //如果订单已经是终态，则不再异步通知平台
        if(OrderStatus.SUCCESS.getCode().equals(rwRecharges.get(0).getStatus())||
                OrderStatus.FAIL.getCode().equals(rwRecharges.get(0).getStatus())||
                OrderStatus.CONFIRMSUCCESS.getCode().equals(rwRecharges.get(0).getStatus())||
                OrderStatus.CONFIRMFAIL.getCode().equals(rwRecharges.get(0).getStatus())){
            logger.info("【平台充值异步回调】数据库记录已经是终态，不发送平台异步通知");
            return null;
        }
        changeBussMap.put("order_no",rwRecharges.get(0).getOrder_no());
        changeBussMap.put("plat_no",rwRecharges.get(0).getPlat_no());
        changeBussMap.put("notify",rwRecharges.get(0).getNotify_url());
        changeBussMap.put("mall_no", accountSearchService.queryMallNoByPlatNo(rwRecharges.get(0).getPlat_no()));
        if("Y".equals(status)){
            //更新re状态为处理成功
            RwRecharge rwRecharge = new RwRecharge();
            rwRecharge.setStatus(OrderStatus.SUCCESS.getCode());
            rwRecharge.setReturn_code(BusinessMsg.SUCCESS);
            rwRecharge.setReturn_msg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));
            rwRecharge.setUpdate_time(new Date());
            example.clear();
            RwRechargeExample.Criteria criteria1=example.createCriteria();
            criteria1.andIdEqualTo(rwRecharges.get(0).getId());
            criteria1.andStatusNotEqualTo(OrderStatus.SUCCESS.getCode());
            int isUpdate = rwRechargeMapper.updateByExampleSelective(rwRecharge,example);
            logger.info("==========更新完成：更新rwRecharges状态为处理成功===========");
            changeBussMap.put("code","1");
            changeBussMap.put("msg",OrderStatus.SUCCESS.getMsg());
            /*=============================处理成功业务=====================================*/
            //平台自有加钱
            AccountSubjectInfo accountSubjectInfo =  accountQueryService.queryAccount(rwRecharges.get(0).getPlat_no(),rwRecharges.get(0).getPlatcust(),Tsubject.CASH.getCode(),Ssubject.PLAT.getCode());
            if(accountSubjectInfo==null){
                throw new BusinessException(BusinessMsg.PARAMETER_ERROR,"平台自有账户查询不到");
            }
            logger.info("==========平台充值：查询出平台自有账户：==========="+JSON.toJSON(accountSubjectInfo));
            if (isUpdate==1){
                EaccAccountamtlist eaccAccountamtlist = new EaccAccountamtlist();
                eaccAccountamtlist.setOrder_no(rwRecharges.get(0).getOrder_no());
                eaccAccountamtlist.setTrans_serial(rwRecharges.get(0).getTrans_serial());
                eaccAccountamtlist.setPlat_no(accountSubjectInfo.getPlat_no());
                eaccAccountamtlist.setPlatcust(accountSubjectInfo.getPlatcust());
                eaccAccountamtlist.setSubject(Tsubject.CASH.getCode());
                eaccAccountamtlist.setSub_subject(Ssubject.PLAT.getCode());
                eaccAccountamtlist.setAmt(new BigDecimal(amt));
                eaccAccountamtlist.setTrans_code(TransConsts.PLAT_CHARGE_CODE);
                eaccAccountamtlist.setTrans_name(TransConsts.PLAT_CHARGE_NAME);
                eaccAccountamtlist.setTrans_date(rwRecharges.get(0).getTrans_date());
                eaccAccountamtlist.setTrans_time(rwRecharges.get(0).getTrans_time());
                eaccAccountamtlist.setPay_code("020");
                eaccAccountamtlist.setTrans_date(rwRecharges.get(0).getTrans_date());
                eaccAccountamtlist.setTrans_time(rwRecharges.get(0).getTrans_time());
//                newAccountTransferService.singleCharge(eaccAccountamtlist);
                accountAssetService.charge(eaccAccountamtlist);
                logger.info("==========平台充值：平台自有账户充值成功===========");
                transTransreq.setStatus(OrderStatus.SUCCESS.getCode());
                transTransreq.setReturn_code(BusinessMsg.SUCCESS);
                transTransreq.setReturn_msg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));

            }
        }else if("N".equals(status)){
            changeBussMap.put("code","2");
            /*=============================处理失败业务=====================================*/
            rwRecharges.get(0).setStatus(OrderStatus.FAIL.getCode());
            //todo 包装云龙汇付参数
/*            rwRecharges.get(0).setReturn_code(OrderStatus.SUCCESS.getCode());
            rwRecharges.get(0).setReturn_msg(OrderStatus.SUCCESS.getMsg());*/
            rwRechargeMapper.updateByPrimaryKey(rwRecharges.get(0));
            logger.info("==========更新完成：更新rwRecharges状态为处理失败===========");

            transTransreq.setStatus(OrderStatus.FAIL.getCode());
            transTransreq.setReturn_code(BusinessMsg.FAIL);
            transTransreq.setReturn_msg(BusinessMsg.getMsg(BusinessMsg.FAIL));
        }else{
            throw new BusinessException(BusinessMsg.PARAMETER_ERROR,"传入处理状态参数不对");
        }
        transReqService.insert(transTransreq);
        try {
            if ("1".equals(changeBussMap.get("code")) || "2".equals(changeBussMap.get("code"))) {
                PlatAsynRequest platAsynRequest = new PlatAsynRequest();
                platAsynRequest.setPlat_no(changeBussMap.get("plat_no").toString());
                platAsynRequest.setAmt(new BigDecimal(changeBussMap.get("amt").toString()));
                platAsynRequest.setCode(changeBussMap.get("code").toString());
                platAsynRequest.setOrder_no(changeBussMap.get("order_no").toString());
                platAsynRequest.setMsg(changeBussMap.get("msg").toString());
                platAsynRequest.setSign("");
                String withDrawAsynRqString = JSON.toJSONString(platAsynRequest, GlobalConfig.serializerFeature);
                NotifyData notifyData = new NotifyData();
                notifyData.setNotifyContent(withDrawAsynRqString);
                notifyData.setNotifyUrl(changeBussMap.get("notify").toString());
                notifyData.setMall_no(changeBussMap.get("mall_no").toString());
                notifyService.addNotify(notifyData);
            }
        }catch (Exception e){
            logger.info("通知平台异常，订单号：{}", changeBussMap.get("order_no"));
        }
        return changeBussMap;
    }


    /**
     * 平台提现
     * @param withdrawRequest
     * @throws BusinessException
     */
    @Override
    public WithdrawResponse withdraw(WithdrawRequest withdrawRequest)throws BusinessException{
        WithdrawResponse withdrawResponse = new WithdrawResponse();
        logger.info("【平台提现】-->进入dubbo-->order_no:"+withdrawRequest.getOrder_no());

        TransTransreq transTransReq = transReqService.getTransTransReq(withdrawRequest.clone(), TransConsts.PLAT_WITHDRAW_CODE, TransConsts.PLAT_WITHDRAW_NAME, false);
        transReqService.insert(transTransReq);
        logger.info("【平台提现】-->流水添加成功-->order_no:" + withdrawRequest.getOrder_no());
        RwWithdraw withdraw=new RwWithdraw();
        String cacheKey=Constants.PLAT_WITHDRAW_READY_AMT_KEY+withdrawRequest.getMer_no();
        String nowMoney100=String.valueOf(withdrawRequest.getAmount().multiply(new BigDecimal(100)));
        long nowMoney100Long=Long.parseLong(nowMoney100.indexOf(".")>0?nowMoney100.substring(0,nowMoney100.indexOf(".")):nowMoney100);
        long nowProcessMoneyX100=CacheUtil.getCache().increment(cacheKey,nowMoney100Long);
        try{

            AccountSubjectInfo accountSubjectInfo = accountQueryService.queryAccount(withdrawRequest.getMer_no(),withdrawRequest.getMer_no(), Tsubject.CASH.getCode(),Ssubject.PLAT.getCode());
            if(accountSubjectInfo == null)
                throw new BusinessException(BusinessMsg.INVALID_ACCOUNT,BusinessMsg.getMsg(BusinessMsg.INVALID_ACCOUNT)+",平台账户不存在");

            BigDecimal processMoneyBD=BigDecimal.valueOf(nowProcessMoneyX100).divide(new BigDecimal(100),2,BigDecimal.ROUND_DOWN);
            if(BigDecimal.ZERO.compareTo(accountSubjectInfo.getN_balance().subtract(processMoneyBD)) > 0 ){
                throw new BusinessException(BusinessMsg.BALANCE_LACK,BusinessMsg.getMsg(BusinessMsg.BALANCE_LACK));
            }
//            CacheUtil.getCache().increment(cacheKey,nowProcessMoneyX100);

            //平台提现限额判断
            String singleLimited=sysParameterService.querySysParameter(withdrawRequest.getMall_no(), SysParamterKey.PLAT_WITHDRAW_SINGLE_LIMITED);
            String allLimited=sysParameterService.querySysParameter(withdrawRequest.getMall_no(), SysParamterKey.PLAT_WITHDRAW_All_LIMITED);
            if(StringUtils.isNotBlank(singleLimited)){
                BigDecimal singleLimitedBD=new BigDecimal(singleLimited);
                if(withdrawRequest.getAmount().compareTo(singleLimitedBD)>0){
                    //单笔超限
                    throw new BusinessException(BusinessMsg.LIMIT_OUT,BusinessMsg.getMsg(BusinessMsg.LIMIT_OUT));
                }
                if(StringUtils.isNotBlank(allLimited)){
                    BigDecimal allLimitedBD=new BigDecimal(allLimited);
                    //查询出当日全部的平台提现
                    String platWithdrawRedisKey=Constants.PLAT_WITHDRAW_DAY_LIMIT_KEY+withdrawRequest.getMer_no()+DateUtils.getyyyyMMddDate();
                    long platWithdrawLimitedX100=CacheUtil.getCache().increment(platWithdrawRedisKey,nowMoney100Long);
                    CacheUtil.getCache().expire(platWithdrawRedisKey,24*3600);
                    if(allLimitedBD.compareTo(new BigDecimal(platWithdrawLimitedX100).divide(new BigDecimal(100),2,BigDecimal.ROUND_DOWN))<0){
                        CacheUtil.getCache().increment(platWithdrawRedisKey,0L-nowMoney100Long);
                        //当日平台提现总额超限
                        throw new BusinessException(BusinessMsg.LIMIT_OUT,BusinessMsg.getMsg(BusinessMsg.LIMIT_OUT));
                    }

                }
            }

            //出金一方
            String payNoJson = platCacheService.queryCardInfoToCache(withdrawRequest.getMer_no(), PlatAccType.PLATTRUST.getCode());
            JSONObject payNoJsonObj = JSON.parseObject(payNoJson);
            logger.info("【平台提现】-->出金参数："+payNoJsonObj.toString());

            //入金一方
            String cardJson = platCacheService.queryCardInfoToCache(withdrawRequest.getMer_no(), PlatAccType.PLATOWN.getCode());
            JSONObject cardJsonObj = JSON.parseObject(cardJson);
            logger.info("【平台提现】入金参数："+cardJsonObj.toString());

            RwWithdraw rwWithdraw = new RwWithdraw();
            rwWithdraw.setTrans_serial(transTransReq.getTrans_serial());
            rwWithdraw.setTrans_date(transTransReq.getTrans_date());
            rwWithdraw.setTrans_time(transTransReq.getTrans_time());
            rwWithdraw.setPlat_no(withdrawRequest.getMer_no());
            rwWithdraw.setPlatcust(withdrawRequest.getMer_no());
            rwWithdraw.setTrans_code(TransConsts.PLAT_WITHDRAW_CODE);
            rwWithdraw.setTrans_name(TransConsts.PLAT_WITHDRAW_NAME);
            rwWithdraw.setOut_amt(withdrawRequest.getAmount());
            rwWithdraw.setSubject(accountSubjectInfo.getSubject());
            rwWithdraw.setSub_subject(accountSubjectInfo.getSub_subject());
            rwWithdraw.setPay_code("020");
            rwWithdraw.setClient_property(CusType.COMPANY.getCode());

            rwWithdraw.setOppo_account(cardJsonObj.get("card_no").toString());
            rwWithdraw.setPayee_name(cardJsonObj.get("card_name").toString());

            //默认set为借记卡
            rwWithdraw.setCard_type(CardType.DEBIT.getCode());
            rwWithdraw.setPay_status(OrderStatus.PROCESSING.getCode());
            rwWithdraw.setOrder_no(withdrawRequest.getOrder_no());
            rwWithdraw.setQuery_no_num(0);
            rwWithdraw.setNotify_url(withdrawRequest.getNotify_url());
            rwWithdraw.setRemark(withdrawRequest.getRemark());
            rwWithdraw.setEnabled(Constants.ENABLED);
            rwWithdraw.setCreate_time(new Date());
            rwWithdraw.setUpdate_time(new Date());
            rwWithdraw.setPayment_date(DateUtils.getyyyyMMddDate());
            rwWithdrawMapper.insert(rwWithdraw);
            logger.info("【平台提现】-->添加提现表数据成功-->order_no:" + withdrawRequest.getOrder_no());

            RwWithdrawExample rwWithdrawExample=new RwWithdrawExample();
            RwWithdrawExample.Criteria criteria = rwWithdrawExample.createCriteria();
            criteria.andPlat_noEqualTo(rwWithdraw.getPlat_no());
            criteria.andTrans_serialEqualTo(rwWithdraw.getTrans_serial());
            criteria.andEnabledEqualTo(Constants.ENABLED);
            List<RwWithdraw> withdrawList=rwWithdrawMapper.selectByExample(rwWithdrawExample);
            if(null == withdrawList || withdrawList.size()!=1){
                logger.info("===========【平台提现】rw表记录出错，记录数不为1");
                throw new BusinessException(BusinessMsg.DATEBASE_EXCEPTION, BusinessMsg.getMsg(BusinessMsg.DATEBASE_EXCEPTION) + "，提现记录出错");
            }
            withdraw=withdrawList.get(0);

            EaccTransTransreqWithBLOBs eaccTransTransreqWithBLOBs = new EaccTransTransreqWithBLOBs();
            eaccTransTransreqWithBLOBs.setParent_trans_serial(transTransReq.getTrans_serial());
            eaccTransTransreqWithBLOBs.setTrans_serial(transTransReq.getTrans_serial());
            eaccTransTransreqWithBLOBs.setEaccount(payNoJsonObj.get("card_no").toString());
            eaccTransTransreqWithBLOBs.setName(payNoJsonObj.get("card_name").toString());
            eaccTransTransreqWithBLOBs.setOppo_eaccount(cardJsonObj.get("card_no").toString());
            eaccTransTransreqWithBLOBs.setOppo_name(cardJsonObj.get("card_name").toString());
            eaccTransTransreqWithBLOBs.setMall_no(withdrawRequest.getMall_no());
            eaccTransTransreqWithBLOBs.setOppo_property(Integer.parseInt(CusType.COMPANY.getCode()));
            eaccTransTransreqWithBLOBs.setProperty(Integer.parseInt(CusType.COMPANY.getCode()));
            eaccTransTransreqWithBLOBs.setTrans_amt(withdrawRequest.getAmount());

            if("BOB".equals(deployEnvironment)){
                accountTransferService.pay(eaccTransTransreqWithBLOBs);
            }else{
                //行内划转pay_code 020
                PlatPaycode platPayCode = accountSearchService.queryPlatPayCode(withdrawRequest.getMer_no(), "020");
                if (null == platPayCode) {
                    throw new BusinessException(BusinessMsg.DATEBASE_EXCEPTION, "未查询到行内划转渠道配置信息");
                }
                String partner_id = platPayCode.getPayment_plat_no();

                Map<String, Object> eMap = new HashMap<String, Object>();
                eMap.put("partner_id", partner_id);
                eMap.put("partner_serial_no", transTransReq.getTrans_serial());
                eMap.put("partner_trans_date", withdrawRequest.getPartner_trans_date());
                eMap.put("partner_trans_time", withdrawRequest.getPartner_trans_time());
                eMap.put("occur_balance", withdrawRequest.getAmount());
                if (withdrawRequest.getOrder_no().length() > 15) {
                    eMap.put("summary", withdrawRequest.getOrder_no().substring(withdrawRequest.getOrder_no().length() - 10));
                } else {
                    eMap.put("summary", withdrawRequest.getOrder_no());
                }
                eMap.put("bank_id", "04031000");
                eMap.put("card_no", cardJsonObj.get("card_no").toString());//入金一方卡号
                eMap.put("client_name", cardJsonObj.get("card_name").toString());//入金一方户名
                eMap.put("dr_acct", payNoJsonObj.get("card_no").toString());//出金一方卡号
                eMap.put("dr_acct_name", payNoJsonObj.get("card_name").toString());//出金一方户名
                //异步回调地址，数据库需配置
                String host = sysParameterService.querySysParameter(SysParamterKey.FTDM, URLConfigUtil.WEB_LOCAL_SERVER);
                String ownUrl = sysParameterService.querySysParameter(SysParamterKey.FTDM, URLConfigUtil.REPAY_OFFLINE_NOTIFY_URL);
                ownUrl = host + ownUrl;
                logger.info("异步回调url:" + ownUrl);
                eMap.put("receive_url", ownUrl);
                eMap.put("client_property", "1");//公私标识,0-个人，1-公司
                eMap.put("remark", "repayCharge");
                eMap.put("prcptcd", "04031000");//大额行号
                eMap.put("dr_acct_type", "0");//出金一方账户类型,0-借记卡,1-贷记卡,2-存折,3-对公户
                if (deployEnvironment.equals("CCB")) {
                    eMap.put("third_no", payNoJsonObj.get("pay_no").toString());
                    eMap.put("tran_type", "pay");
                    eMap.put("channelId", platPayCode.getChannel_id());
                    JSONArray trandata = new JSONArray();
                    Map<String, Object> trandata_map = new HashMap<String, Object>();
                    trandata_map.put("card_no", cardJsonObj.get("card_no").toString());
                    trandata_map.put("cust_name", cardJsonObj.get("card_name").toString());
                    trandata_map.put("seq_no", transTransReq.getTrans_serial());
                    trandata_map.put("tran_amt", withdrawRequest.getAmount());
                    trandata.add(trandata_map);
                    eMap.put("trandata", trandata);
                }
                String host2 = sysParameterService.querySysParameter(SysParamterKey.FTDM, URLConfigUtil.EPAY_SERVER_KEY);
                String url = sysParameterService.querySysParameter(SysParamterKey.FTDM, URLConfigUtil.AGENT_PAY_REAL);
                eMap.put("host", host2);
                eMap.put("url", url);
                //调E支付的
                logger.info("======平台提现请求的数据:eMap>> " + JSON.toJSON(eMap));
                Map<String, Object> resultMap = adapterService.transferOfAccountInBank(eMap);
                logger.info("===========【平台提现】收到三方响应参数：" + resultMap);

                logger.info("======E支付返回数据：" + JSON.toJSON(resultMap));
                if (null == resultMap || "".equals(resultMap.get("order_status"))) {
                    logger.info("===========【平台提现】三方响应为空，");
                    throw new BusinessException(BusinessMsg.CALL_REMOTE_ERROR, BusinessMsg.getMsg(BusinessMsg.CALL_REMOTE_ERROR) + "：第三方接口调用失败");
                }
            }
            logger.info("【平台提现】-->行内8851转账成功-->order_no:" + withdrawRequest.getOrder_no());
            try {
                EaccAccountamtlist eaccAccountamtlist = new EaccAccountamtlist();
                eaccAccountamtlist.setTrans_name(TransConsts.PLAT_WITHDRAW_NAME);
                eaccAccountamtlist.setTrans_code(TransConsts.PLAT_WITHDRAW_CODE);
                eaccAccountamtlist.setTrans_date(withdrawRequest.getPartner_trans_date());
                eaccAccountamtlist.setTrans_time(withdrawRequest.getPartner_trans_time());
                eaccAccountamtlist.setPlat_no(accountSubjectInfo.getPlat_no());
                eaccAccountamtlist.setPlatcust(accountSubjectInfo.getPlatcust());
                eaccAccountamtlist.setSubject(accountSubjectInfo.getSubject());
                eaccAccountamtlist.setSub_subject(accountSubjectInfo.getSub_subject());
                eaccAccountamtlist.setOrder_no(withdrawRequest.getOrder_no());
                eaccAccountamtlist.setTrans_serial(transTransReq.getTrans_serial());
                eaccAccountamtlist.setAmt(withdrawRequest.getAmount());
                eaccAccountamtlist.setAmt_type(AmtType.EXPENSIVE.getCode());
                accountAssetService.withdraw(eaccAccountamtlist);

                withdraw.setPay_status(PayStatus.SUCCESS.getCode());
                rwWithdrawMapper.updateByPrimaryKeySelective(withdraw);
            }catch (Exception e){
                logger.info("【平台提现】-->自有户减款是失败-->order_no:" + withdrawRequest.getOrder_no(),e);

                StringBuilder sb = new StringBuilder();
                if("BOB".equals(deployEnvironment)){
                    sb.append("【北京银行】");
                }
                sb.append("(平台提现)提现虚拟户扣款失败，进行冲正，请注意订单："+eaccTransTransreqWithBLOBs.getTrans_serial()+"，的冲正结果。");
                sendMsgService.sendErrorToAdmin(sb.toString(), null);

               throw new BusinessException(BusinessMsg.PAYMENT_FAILED,"自有户减款是失败");
            }
            transTransReq.setReturn_code(BusinessMsg.SUCCESS);
            transTransReq.setReturn_msg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));
            transTransReq.setStatus(OrderStatus.SUCCESS.getCode());
            transReqService.insert(transTransReq);

            OrderData orderData = new OrderData();
            orderData.setOrder_status(OrderStatus.SUCCESS.getCode());
            orderData.setOrder_no(withdrawRequest.getOrder_no());
            orderData.setQuery_id(transTransReq.getTrans_serial());
            orderData.setProcess_date(DateUtils.todayStr());
            withdrawResponse.setOrderData(orderData);
            withdrawResponse.setRecode(BusinessMsg.SUCCESS);
            withdrawResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));

        }catch (Exception e){

            logger.info("【平台提现】-->异常-->order_no:"+withdrawRequest.getOrder_no(),e);
            BaseResponse baseResponse = new BaseResponse();
            if (e instanceof BusinessException){
                baseResponse = ((BusinessException)e).getBaseResponse();
                transTransReq.setReturn_code(baseResponse.getRecode());
                transTransReq.setReturn_msg(baseResponse.getRemsg());

                if(baseResponse.getRecode().equals(BusinessMsg.PAYMENT_FAILED)){
                    BankReverse bankReverse = new BankReverse();
                    bankReverse.setMall_no(withdrawRequest.getMall_no());
                    bankReverse.setOccur_balance(withdrawRequest.getAmount());
                    bankReverse.setOriginal_serial_no(transTransReq.getTrans_serial());
                    bankReverse.setPartner_trans_date(withdrawRequest.getPartner_trans_date());
                    accountTransferThirdParty.addReverseToQueue(bankReverse);
                    //限额回滚
                    String platWithdrawRedisKey=Constants.PLAT_WITHDRAW_DAY_LIMIT_KEY+withdrawRequest.getMer_no()+DateUtils.getyyyyMMddDate();
                    CacheUtil.getCache().increment(platWithdrawRedisKey,0L-nowMoney100Long);
                }

            }else {
                baseResponse.setRecode(BusinessMsg.UNKNOW_ERROE);
                baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.UNKNOW_ERROE));
                transTransReq.setReturn_code(BusinessMsg.UNKNOW_ERROE);
                transTransReq.setReturn_msg(BusinessMsg.getMsg(BusinessMsg.UNKNOW_ERROE));
            }
            withdraw.setPay_status(PayStatus.FAIL.getCode());
            rwWithdrawMapper.updateByPrimaryKeySelective(withdraw);
            transTransReq.setStatus(OrderStatus.FAIL.getCode());
            transReqService.insert(transTransReq);
            logger.info("【平台提现】失败后修改订单成功");

            throw new BusinessException(baseResponse);

        }finally {
            CacheUtil.getCache().increment(cacheKey,0L-nowMoney100Long);
        }
        return withdrawResponse;
    }

    @Transactional
    public Map<String,Object> platWithdrawAsyn(Map<String,Object> map)throws BusinessException{
        logger.info("【平台提现异步回调】："+JSON.toJSON(map));
        Map<String,Object> withdrawBussMap = new HashMap<String,Object>();
        String orderNo = map.get("third_batch_no").toString();
        List<BankPayPayNotifyData> bankPayPayNotifyDataList = JSON.parseArray(map.get("trandata").toString(),BankPayPayNotifyData.class);
        Map<String,Object> tranDataMap = BeanUtil.transBean2Map(bankPayPayNotifyDataList.get(0));
        String seqNo = tranDataMap.get("seq_no").toString();
        String amt = tranDataMap.get("tran_amt").toString();
        String status = tranDataMap.get("status").toString();
        withdrawBussMap.put("amt",amt);
        withdrawBussMap.put("order_no",orderNo);
        RwWithdrawExample example=new RwWithdrawExample();
        RwWithdrawExample.Criteria criteria=example.createCriteria();
        criteria.andHost_req_serial_noEqualTo(seqNo);
        criteria.andTrans_codeEqualTo(TransConsts.PLAT_WITHDRAW_CODE);
        criteria.andTrans_nameEqualTo(TransConsts.PLAT_WITHDRAW_NAME);
        criteria.andEnabledEqualTo(Constants.ENABLED);
        List<RwWithdraw> rwWithdraws = rwWithdrawMapper.selectByExample(example);
        logger.info("【平台提现异步回调】查询出RwWithdraw==============List<RwWithdraw>："+JSON.toJSON(rwWithdraws));
        if(rwWithdraws==null || rwWithdraws.size()==0){
            throw new BusinessException(BusinessMsg.PARAMETER_ERROR,"查询不到RwWithdraw信息");
        }
        withdrawBussMap.put("plat_no",rwWithdraws.get(0).getPlat_no());
        withdrawBussMap.put("notify",rwWithdraws.get(0).getNotify_url());
        withdrawBussMap.put("mall_no",accountSearchService.queryMallNoByPlatNo(rwWithdraws.get(0).getPlat_no()));

        TransTransreq transTransreq=transReqService.queryTransTransReqByOrderNoAndBatchOrderNo(rwWithdraws.get(0).getTrans_serial(),null);
        if(transTransreq==null){
            return null;
        }

        //平台提现解冻扣钱
        AccountSubjectInfo accountSubjectInfo =  accountQueryService.queryAccount(rwWithdraws.get(0).getPlat_no(),rwWithdraws.get(0).getPlatcust(),Tsubject.CASH.getCode(),Ssubject.PLAT.getCode());
        logger.info("【平台提现异步回调】：查询出平台自有账户==========="+JSON.toJSON(accountSubjectInfo));
        if(accountSubjectInfo==null){
            throw new BusinessException(BusinessMsg.PARAMETER_ERROR,"平台自有账户查询不到");
        }
        logger.info("【平台提现异步回调】：开始平台自有资金解冻===========");
        EaccAccountamtlist eaccAccountamtlist = new EaccAccountamtlist();
        eaccAccountamtlist.setTrans_serial(rwWithdraws.get(0).getTrans_serial());
        eaccAccountamtlist.setPlat_no(accountSubjectInfo.getPlat_no());
        eaccAccountamtlist.setPlatcust(accountSubjectInfo.getPlatcust());
        eaccAccountamtlist.setSubject(Tsubject.CASH.getCode());
        eaccAccountamtlist.setSub_subject(rwWithdraws.get(0).getSub_subject());
        eaccAccountamtlist.setAmt(new BigDecimal(amt));
        eaccAccountamtlist.setTrans_date(rwWithdraws.get(0).getTrans_date());
        eaccAccountamtlist.setTrans_time(rwWithdraws.get(0).getTrans_time());
        eaccAccountamtlist = accountTransferService.queryTransFlow(eaccAccountamtlist);
        accountFrozenService.unfreeze(eaccAccountamtlist);
        logger.info("==========平台提现异步：平台自有资金解冻完成===========");
        if("Y".equals(status)){
            withdrawBussMap.put("code","1");
            /*=============================处理成功业务=====================================*/
            //更新RwWithdraw状态
            RwWithdraw rwWithdraw = new RwWithdraw();
            rwWithdraw.setAcct_state(AcctState.Reach.getCode());
            rwWithdraw.setPay_status(PayStatus.SUCCESS.getCode());
            rwWithdraw.setUpdate_time(new Date());
            example.clear();
            RwWithdrawExample.Criteria criteria1=example.createCriteria();
            criteria1.andIdEqualTo(rwWithdraws.get(0).getId());
            criteria1.andPay_statusNotEqualTo(PayStatus.SUCCESS.getCode());
            int isUpdate =rwWithdrawMapper.updateByExampleSelective(rwWithdraw,example);
            logger.info("【平台提现异步回调】更新完成：更新rwWithdraws状态为处理成功===========");
            //开始扣款
            if (isUpdate==1){
                eaccAccountamtlist.setTrans_code(TransConsts.PLAT_WITHDRAW_CODE);
                eaccAccountamtlist.setTrans_name(TransConsts.PLAT_WITHDRAW_NAME);
                eaccAccountamtlist.setPay_code("020");
                accountAssetService.withdraw(eaccAccountamtlist);
                logger.info("【平台提现异步回调】平台提现异步：平台自有资金扣款完成===========");
            }
            transTransreq.setReturn_code(BusinessMsg.SUCCESS);
            transTransreq.setReturn_msg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));
        }else if("N".equals(status)){
            withdrawBussMap.put("code","2");
            /*=============================处理失败业务=====================================*/
            rwWithdraws.get(0).setAcct_state(AcctState.Reach.getCode());
            rwWithdraws.get(0).setPay_status(PayStatus.FAIL.getCode());
            rwWithdrawMapper.updateByPrimaryKey(rwWithdraws.get(0));
            logger.info("【平台提现异步回调】更新完成：更新rwWithdraws状态为处理失败===========");
            transTransreq.setReturn_code(BusinessMsg.FAIL);
            transTransreq.setReturn_msg(BusinessMsg.getMsg(BusinessMsg.FAIL));
        }else{
            throw new BusinessException(BusinessMsg.PARAMETER_ERROR,"传入处理状态参数不对");
        }
        transReqService.insert(transTransreq);
        if("1".equals(withdrawBussMap.get("code"))|| "2".equals(withdrawBussMap.get("code"))){
            PlatAsynRequest platAsynRequest = new PlatAsynRequest();
            platAsynRequest.setPlat_no(withdrawBussMap.get("plat_no").toString());
            platAsynRequest.setAmt(new BigDecimal(withdrawBussMap.get("amt").toString()));
            platAsynRequest.setCode(withdrawBussMap.get("code").toString());
            platAsynRequest.setOrder_no(withdrawBussMap.get("order_no").toString());
            platAsynRequest.setMsg(withdrawBussMap.get("msg").toString());
            platAsynRequest.setSign("");
            String withDrawAsynRqString = JSON.toJSONString(platAsynRequest, GlobalConfig.serializerFeature);
            NotifyData notifyData = new NotifyData();
            notifyData.setNotifyContent(withDrawAsynRqString);
            notifyData.setNotifyUrl(withdrawBussMap.get("notify").toString());
            notifyData.setMall_no(withdrawBussMap.get("mall_no").toString());
            notifyService.addNotify(notifyData);
        }
        return withdrawBussMap;
    }

    public static void main(String[] args) {
        long aa=123456;
        BigDecimal bb=BigDecimal.valueOf(aa).divide(new BigDecimal(100));
        System.out.println(bb);
    }
}
