package com.sunyard.sunfintech.user.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sunyard.sunfintech.account.provider.IAccountAssetService;
import com.sunyard.sunfintech.account.provider.IAccountQueryService;
import com.sunyard.sunfintech.account.provider.IAccountTransferService;
import com.sunyard.sunfintech.core.Constants;
import com.sunyard.sunfintech.core.TransConsts;
import com.sunyard.sunfintech.core.base.BaseResponse;
import com.sunyard.sunfintech.core.base.BaseServiceSimple;
import com.sunyard.sunfintech.core.config.GlobalConfig;
import com.sunyard.sunfintech.core.dic.*;
import com.sunyard.sunfintech.core.exception.BusinessException;
import com.sunyard.sunfintech.core.exception.BusinessMsg;
import com.sunyard.sunfintech.core.util.*;
import com.sunyard.sunfintech.custdao.mapper.CustRwWithdrawMapper;
import com.sunyard.sunfintech.dao.entity.*;
import com.sunyard.sunfintech.dao.mapper.EaccAccountamtlistMapper;
import com.sunyard.sunfintech.dao.mapper.RwWithdrawMapper;
import com.sunyard.sunfintech.dao.mapper.RwWithdrawThirdMapper;
import com.sunyard.sunfintech.dao.mapper.TransTransreqMapper;
import com.sunyard.sunfintech.pub.model.MsgModel;
import com.sunyard.sunfintech.pub.model.NotifyData;
import com.sunyard.sunfintech.pub.provider.INotifyService;
import com.sunyard.sunfintech.pub.provider.ISendMsgService;
import com.sunyard.sunfintech.pub.provider.ISysParameterService;
import com.sunyard.sunfintech.pub.provider.ITransReqService;
import com.sunyard.sunfintech.thirdparty.provider.IAdapterService;
import com.sunyard.sunfintech.user.model.bo.*;
import com.sunyard.sunfintech.user.provider.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

/**
 * Created by PengZY on 2018/1/11.
 */
@Service(interfaceClass = IRwWithdrawOptionService.class)
@org.springframework.stereotype.Service
public class RwWithdrawOptionService extends BaseServiceSimple implements IRwWithdrawOptionService {

    @Autowired
    private ITransReqService transReqService;

    @Autowired
    private IAccountQueryService accountQueryService;

    @Autowired
    private IBatchWithdrawExtService batchWithdrawExtService;

    @Autowired
    private CustRwWithdrawMapper custRwWithdrawMapper;

    @Autowired
    private RwWithdrawMapper rwWithdrawMapper;

    @Autowired
    private ISendMsgService sendMsgService;

    @Autowired
    private IAccountSearchService accountSearchService;

    @Autowired
    private IAdapterService adapterService;

    @Autowired
    private ISysParameterService sysParameterService;

    @Autowired
    private IAccountAssetService accountAssetService;

    @Autowired
    private IAccountTransferService accountTransferService;

    @Autowired
    private TransTransreqMapper transTransreqMapper;

    @Autowired
    private INotifyService notifyService;

    @Autowired
    private IRwWithdrawMQOptionSerivce rwWithdrawMQOptionSerivce;

    @Autowired
    private EaccAccountamtlistMapper eaccAccountamtlistMapper;

    @Autowired
    private IUserAccountService userAccountService;

    @Autowired
    private IPlatCacheService platCacheService;

    @Autowired
    private RwWithdrawThirdMapper rwWithdrawThirdMapper;

    /**
     * 批量提现
     * @author pzy
     * @param batchWithdrawRequest 参数
     * @return BatchWithdrawResponse
     * @throws BusinessException
     */
    @Override
    public BatchWithdrawResponse batchWithdraw(BatchWithdrawRequest batchWithdrawRequest) throws BusinessException {
        logger.info("【批量提现】-->dubbo-->开始-->order_no:"+batchWithdrawRequest.getOrder_no());

        BatchWithdrawResponse batchWithdrawResponse = new BatchWithdrawResponse();

        List<SuccessData> successDataList = new ArrayList<SuccessData>();
        List<ErrorData> errorDataList = new ArrayList<ErrorData>();

        //记录批量业务流水表
        TransTransreq transTransReq = transReqService.getTransTransReq(batchWithdrawRequest.clone(), TransConsts.BATCH_WITHDRAW_CODE, TransConsts.BATCH_WITHDRAW_NAME, true);
        transTransReq.setOrder_no(batchWithdrawRequest.getOrder_no());
        transReqService.insert(transTransReq);

        List<DateDetail> dateDetails = batchWithdrawRequest.getDatedetail();
        if (dateDetails == null || dateDetails.size() == 0 || batchWithdrawRequest.getTotal_num() != dateDetails.size()) {

            logger.info("【批量提现】-->批量提现数据明细size和数量对不上-->order_no:"+batchWithdrawRequest.getOrder_no());

            transTransReq.setReturn_code(BusinessMsg.PARAMETER_ERROR);
            transTransReq.setReturn_msg(BusinessMsg.getMsg(BusinessMsg.PARAMETER_ERROR));
            transTransReq.setStatus(FlowStatus.FAIL.getCode());
            transReqService.insert(transTransReq);

            BaseResponse baseResponse = new BaseResponse();
            baseResponse.setRecode(BusinessMsg.PARAMETER_ERROR);
            baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.PARAMETER_ERROR) + "数据明细size和数量对不上");
            throw new BusinessException(baseResponse);
        }
        logger.info("【批量提现】-->检查批量提现数据正常-->order_no:"+batchWithdrawRequest.getOrder_no());

        for (DateDetail dateDetail : dateDetails) {
            try{
                validate(dateDetail);
                dateDetail.setCard_type(dateDetail.getCard_type());
                dateDetail.setIs_advance(dateDetail.getIs_advance());
                dateDetail.setFee_amt(dateDetail.getFee_amt());
                dateDetail.setWithdraw_type(dateDetail.getWithdraw_type());
                dateDetail.setClient_property(dateDetail.getClient_property());

                TransTransreq transTransReqDetail = transReqService.getTransTransReq(batchWithdrawRequest.clone(), TransConsts.BATCH_WITHDRAW_CODE, TransConsts.BATCH_WITHDRAW_NAME, true);
                transTransReqDetail.setOrder_no(dateDetail.getDetail_no());
                transTransReqDetail.setPlatcust(dateDetail.getPlatcust());
                transReqService.insert(transTransReqDetail);

                try{
                    BatchRwWithdrawMQEntity batchRwWithdrawMQEntity = new BatchRwWithdrawMQEntity();
                    batchRwWithdrawMQEntity.setBaseRequest(batchWithdrawRequest.clone());
                    batchRwWithdrawMQEntity.setDateDetail(dateDetail);
                    rwWithdrawMQOptionSerivce.addBatchRwWithdraw(batchRwWithdrawMQEntity);
                    logger.info("【批量提现】-->丢入MQ成功-->order_no:"+dateDetail.getDetail_no());
                }catch (Exception e){
                    logger.error("【批量提现】-->丢入MQ异常-->order_no:"+dateDetail.getDetail_no(),e);
                }
                SuccessData successData = new SuccessData();
                successData.setAmt(dateDetail.getAmt());
                successData.setDetail_no(dateDetail.getDetail_no());
                successData.setPlatcust(dateDetail.getPlatcust());
                successDataList.add(successData);
            }catch (BusinessException e){
                BaseResponse baseResponse = e.getBaseResponse();
                ErrorData errorData = new ErrorData();
                errorData.setDetail_no(dateDetail.getDetail_no());
                errorData.setError_no(baseResponse.getRecode());
                errorData.setError_info(baseResponse.getRemsg());
                errorDataList.add(errorData);
            }
        }

        //更新业务流水
        transTransReq.setReturn_code(BusinessMsg.SUCCESS);
        transTransReq.setReturn_msg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));
        transTransReq.setStatus(OrderStatus.SUCCESS.getCode());
        transReqService.insert(transTransReq);
        logger.info("【批量提现】-->修改批次号订单成功-->order_no:"+batchWithdrawRequest.getOrder_no());

        batchWithdrawResponse.setRecode(BusinessMsg.SUCCESS);
        batchWithdrawResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));
        batchWithdrawResponse.setPlat_no(batchWithdrawRequest.getMer_no());
        batchWithdrawResponse.setOrder_no(batchWithdrawRequest.getOrder_no());
        batchWithdrawResponse.setFinish_datetime(DateUtils.todayStr(DateUtils.DEF_FORMAT_STRING));
        if (successDataList.size() > 0) {
            batchWithdrawResponse.setOrder_status(OrderStatus.PROCESSING.getCode());
            batchWithdrawResponse.setOrder_info(OrderStatus.PROCESSING.getMsg());
        } else {
            batchWithdrawResponse.setOrder_status(OrderStatus.FAIL.getCode());
            batchWithdrawResponse.setOrder_info(OrderStatus.FAIL.getMsg());
        }
        batchWithdrawResponse.setSuccess_num(successDataList.size());
        batchWithdrawResponse.setTotal_num(batchWithdrawRequest.getTotal_num());
        batchWithdrawResponse.setSuccess_data(JSON.toJSONString(successDataList, GlobalConfig.serializerFeature));
        batchWithdrawResponse.setError_data(JSON.toJSONString(errorDataList, GlobalConfig.serializerFeature));






        /*//手续费现金账户
        AccountSubjectInfo plat_account = accountQueryService.queryAccount(batchWithdrawRequest.getMer_no(), batchWithdrawRequest.getMer_no(), Tsubject.CASH.getCode(), Ssubject.FEE.getCode());

        //手续费在途账户
        AccountSubjectInfo plat_account_float = accountQueryService.queryAccount(batchWithdrawRequest.getMer_no(), batchWithdrawRequest.getMer_no(), Tsubject.FLOAT.getCode(), Ssubject.FEE.getCode());

        //随机数获取行内垫资子科目
        String subSubject = Payment.getRandom();

        //行内垫资现金账户
        AccountSubjectInfo plat_account_inline = accountQueryService.queryAccount(batchWithdrawRequest.getMer_no(), batchWithdrawRequest.getMer_no(), Tsubject.CASH.getCode(), subSubject);

        //行内垫资在途账户
        AccountSubjectInfo plat_account_inline_float = accountQueryService.queryAccount(batchWithdrawRequest.getMer_no(), batchWithdrawRequest.getMer_no(), Tsubject.FLOAT.getCode(), subSubject);

        if (plat_account == null || plat_account_float == null ||  plat_account_inline == null || plat_account_inline_float == null) {

            logger.info("【批量提现】平台账户不存在");

            transTransReq.setReturn_code(BusinessMsg.INVALID_ACCOUNT);
            transTransReq.setReturn_msg(BusinessMsg.getMsg(BusinessMsg.INVALID_ACCOUNT));
            transTransReq.setStatus(FlowStatus.FAIL.getCode());
            transReqService.insert(transTransReq);

            BaseResponse baseResponse = new BaseResponse();
            baseResponse.setRecode(BusinessMsg.INVALID_ACCOUNT);
            baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.INVALID_ACCOUNT) + ",平台账户不存在");
            throw new BusinessException(baseResponse);
        }
        logger.info("【批量提现】提现所用平台账户存在-->order_no:"+batchWithdrawRequest.getOrder_no());


        List<SuccessData> successDataList = new ArrayList<SuccessData>();
        List<ErrorData> errorDataList = new ArrayList<ErrorData>();
        for (DateDetail dateDetail : dateDetails) {

            try {

                if(StringUtils.isBlank(dateDetail.getDetail_no())){
                    throw new BusinessException(BusinessMsg.FAIL, "明细编号不能为空(detail_no)");
                }

                boolean detail_flag = CacheUtil.getCache().setnx(Constants.RW_WITHDRAW_REDIS_KEY + dateDetail.getDetail_no(), dateDetail.getDetail_no());
                if (!detail_flag) {
                    throw new BusinessException(BusinessMsg.FAIL, "明细编号不能(detail_no)重复");
                }
                CacheUtil.getCache().expire(Constants.RW_WITHDRAW_REDIS_KEY + dateDetail.getDetail_no(),24*60*60);

                SuccessData successData = batchWithdrawExtService.transfer(plat_account, plat_account_float, plat_account_inline, plat_account_inline_float, dateDetail, batchWithdrawRequest.clone());
                successData.setAmt(dateDetail.getAmt());
                successData.setDetail_no(dateDetail.getDetail_no());
                successData.setPlatcust(dateDetail.getPlatcust());
                successDataList.add(successData);
                logger.info("【批量提现】成功数据：" + JSON.toJSONString(successDataList, GlobalConfig.serializerFeature));
            } catch (BusinessException e) {
                BaseResponse baseResponse = e.getBaseResponse();
                ErrorData errorData = new ErrorData();
                errorData.setDetail_no(dateDetail.getDetail_no());
                errorData.setError_no(baseResponse.getRecode());
                errorData.setError_info(baseResponse.getRemsg());
                errorDataList.add(errorData);
                logger.info("【批量提现】失败数据：" + JSON.toJSONString(errorDataList, GlobalConfig.serializerFeature));
            }

        }

        //更新业务流水
        transTransReq.setReturn_code(BusinessMsg.SUCCESS);
        transTransReq.setReturn_msg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));
        transReqService.insert(transTransReq);


        batchWithdrawResponse.setRecode(BusinessMsg.SUCCESS);
        batchWithdrawResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));
        batchWithdrawResponse.setPlat_no(batchWithdrawRequest.getMer_no());
        batchWithdrawResponse.setOrder_no(batchWithdrawRequest.getOrder_no());
        batchWithdrawResponse.setFinish_datetime(DateUtils.todayStr(DateUtils.DEF_FORMAT_STRING));
        if (successDataList.size() > 0) {
            batchWithdrawResponse.setOrder_status(OrderStatus.PROCESSING.getCode());
            batchWithdrawResponse.setOrder_info(OrderStatus.PROCESSING.getMsg());
        } else {
            batchWithdrawResponse.setOrder_status(OrderStatus.FAIL.getCode());
            batchWithdrawResponse.setOrder_info(OrderStatus.FAIL.getMsg());
        }
        batchWithdrawResponse.setSuccess_num(successDataList.size());
        batchWithdrawResponse.setTotal_num(batchWithdrawRequest.getTotal_num());
        batchWithdrawResponse.setSuccess_data(JSON.toJSONString(successDataList, GlobalConfig.serializerFeature));
        batchWithdrawResponse.setError_data(JSON.toJSONString(errorDataList, GlobalConfig.serializerFeature));*/

        return batchWithdrawResponse;
    }

    /**
     * 提现申请
     * @author pzy
     * @param withdrawApplicationRequest 参数
     * @return BatchWithdrawResponse
     * @throws BusinessException
     */
    @Override
    public BatchWithdrawResponse withdrawApplication(WithdrawApplicationRequest withdrawApplicationRequest) throws BusinessException {
        logger.info("【批量提现申请】-->dubbo-->detail_no:" + withdrawApplicationRequest.getOrder_no());
        logger.info("【批量提现申请】-->dubbo-->data:" + withdrawApplicationRequest.toString());
        BatchWithdrawResponse batchWithdrawResponse = new BatchWithdrawResponse();
        List<SuccessData> successDataList = new ArrayList<SuccessData>();
        List<ErrorData> errorDataList = new ArrayList<ErrorData>();
        SuccessData successData = new SuccessData();
        ErrorData errorData = new ErrorData();

        //记录业务流水表
        TransTransreq transTransReq = transReqService.getTransTransReq(withdrawApplicationRequest.clone(), TransConsts.WITHDRAW_APPLICATION_CODE, TransConsts.WITHDRAW_APPLICATION_NAME, false);
        transTransReq.setPlatcust(withdrawApplicationRequest.getPlatcust());
        transReqService.insert(transTransReq);

        try{

            if(!withdrawApplicationRequest.getClient_property().equals(CusType.PERSONAL.getCode())
                    && !withdrawApplicationRequest.getClient_property().equals(CusType.COMPANY.getCode()))
                throw new BusinessException(BusinessMsg.PARAMETER_ERROR,"公私标示有误");

            if(withdrawApplicationRequest.getClient_property().equals(CusType.COMPANY.getCode())){

                if(StringUtils.isBlank(withdrawApplicationRequest.getBank_id()))
                    throw new BusinessException(BusinessMsg.PARAMETER_LACK,BusinessMsg.getMsg(BusinessMsg.PARAMETER_LACK)+",对公时缺少开户行号");

                if(StringUtils.isBlank(withdrawApplicationRequest.getOpen_branch()))
                    throw new BusinessException(BusinessMsg.PARAMETER_LACK,BusinessMsg.getMsg(BusinessMsg.PARAMETER_LACK)+",对公时缺少开户行");

            }

            if(!CardType.DEBIT.getCode().equals(withdrawApplicationRequest.getCard_type())
                    && !CardType.CREDIT.getCode().equals(withdrawApplicationRequest.getCard_type()))
                throw new BusinessException(BusinessMsg.PARAMETER_ERROR, BusinessMsg.getMsg(BusinessMsg.PARAMETER_ERROR) + ",卡类型有误");
            logger.info("【批量提现申请】-->卡类型正常-->detail_no:" + withdrawApplicationRequest.getOrder_no());

            //检查用户绑卡是否存在
            List<EaccCardinfo> eaccCardInfos = accountSearchService.queryEaccCardInfo(withdrawApplicationRequest.getMall_no(),withdrawApplicationRequest.getPlatcust(),withdrawApplicationRequest.getCard_no(),null);
            if (null == eaccCardInfos || eaccCardInfos.size() == 0) {
                logger.info("【批量提现申请】-->绑卡账号不存在" + "用户账户号：" + withdrawApplicationRequest.getPlatcust());
                throw new BusinessException(BusinessMsg.INVALID_ACCOUNT, BusinessMsg.getMsg(BusinessMsg.INVALID_ACCOUNT) + "绑卡账号不存在" + "用户账户号：" + withdrawApplicationRequest.getPlatcust());
            }
            EaccCardinfo eaccCardInfo = eaccCardInfos.get(0);
            logger.info("【批量提现申请】-->用户绑卡信息存在-->detail_no:" + withdrawApplicationRequest.getOrder_no());


            //检查用户信息是否存在
            EaccUserinfo eaccUserInfo = accountSearchService.queryEaccUserInfoByEaccAccountInfo(withdrawApplicationRequest.getMall_no(), withdrawApplicationRequest.getMer_no(), withdrawApplicationRequest.getPlatcust());
            if (eaccUserInfo == null) {
                logger.info("【批量提现申请】-->用户账号不存在" + "-->用户账户号：" + withdrawApplicationRequest.getPlatcust()+"-->detail_no:" + withdrawApplicationRequest.getOrder_no());
                throw new BusinessException(BusinessMsg.INVALID_ACCOUNT, BusinessMsg.getMsg(BusinessMsg.INVALID_ACCOUNT) + "用户账号不存在" + "用户账户号：" + withdrawApplicationRequest.getPlatcust());
            }
            logger.info("【批量提现申请】-->用户信息存在-->detail_no:" + withdrawApplicationRequest.getOrder_no());


            logger.info("【批量提现申请】-->查询虚拟账户-->detail_no:" + withdrawApplicationRequest.getOrder_no());

            //用户现金账户
            AccountSubjectInfo accountSubjectInfo = null;
            //用户在途账户
            AccountSubjectInfo accountSubjectInfo_float = null;
            if(!Ssubject.EACCOUNT.getCode().equals(withdrawApplicationRequest.getWithdraw_type())){
                accountSubjectInfo = accountQueryService.queryAccount(withdrawApplicationRequest.getMer_no(), withdrawApplicationRequest.getPlatcust(), Tsubject.CASH.getCode(), withdrawApplicationRequest.getWithdraw_type());
                accountSubjectInfo_float = accountQueryService.queryAccount(withdrawApplicationRequest.getMer_no(), withdrawApplicationRequest.getPlatcust(), Tsubject.FLOAT.getCode(), withdrawApplicationRequest.getWithdraw_type());
            }else {
                accountSubjectInfo = accountQueryService.queryAccount(withdrawApplicationRequest.getMall_no(), withdrawApplicationRequest.getPlatcust(), Tsubject.CASH.getCode(), withdrawApplicationRequest.getWithdraw_type());
                accountSubjectInfo_float = accountQueryService.queryAccount(withdrawApplicationRequest.getMall_no(), withdrawApplicationRequest.getPlatcust(), Tsubject.FLOAT.getCode(), withdrawApplicationRequest.getWithdraw_type());
            }

            if (accountSubjectInfo == null || accountSubjectInfo_float == null) {
                logger.info("【批量提现申请】-->账户明细不存在-->用户账户号：" + withdrawApplicationRequest.getPlatcust()+"-->detail_no:" + withdrawApplicationRequest.getOrder_no());
                throw new BusinessException(BusinessMsg.INVALID_ACCOUNT, BusinessMsg.getMsg(BusinessMsg.INVALID_ACCOUNT) + "账户明细不存在" + "用户账户号：" + withdrawApplicationRequest.getPlatcust());
            }
            logger.info("【批量提现申请】-->账户明细账户信息存在-->detail_no:" + withdrawApplicationRequest.getOrder_no());

            //查询平台映射信息
            PlatPaycode platPayCode = accountSearchService.queryPlatPayCode(withdrawApplicationRequest.getMer_no(), withdrawApplicationRequest.getPay_code());
            if (platPayCode == null) {
                logger.info("【批量提现申请】-->支付平台映射信息不存在" + "客户编号：" + withdrawApplicationRequest.getMer_no() + "支付通道：" + withdrawApplicationRequest.getPay_code());
                throw new BusinessException(BusinessMsg.DATEBASE_EXCEPTION, BusinessMsg.getMsg(BusinessMsg.DATEBASE_EXCEPTION) + "支付平台映射信息不存在" + "客户编号："
                        + withdrawApplicationRequest.getMer_no() + "支付通道：" + withdrawApplicationRequest.getPay_code());
            }
            logger.info("【批量提现申请】-->平台映射信息存在-->detail_no:" + withdrawApplicationRequest.getOrder_no());

            //添加提现信息
            logger.info("【批量提现申请】-->开始添加提现信息-->detail_no:" + withdrawApplicationRequest.getOrder_no());
            RwWithdraw rwWithdraw = new RwWithdraw();
            rwWithdraw.setTrans_serial(transTransReq.getTrans_serial());
            rwWithdraw.setTrans_date(transTransReq.getTrans_date());
            rwWithdraw.setTrans_time(transTransReq.getTrans_time());
            rwWithdraw.setPlat_no(withdrawApplicationRequest.getMer_no());
            rwWithdraw.setPlatcust(accountSubjectInfo.getPlatcust());
            rwWithdraw.setTrans_code(TransConsts.WITHDRAW_APPLICATION_CODE);
            rwWithdraw.setTrans_name(TransConsts.WITHDRAW_APPLICATION_NAME);
            rwWithdraw.setOut_amt(withdrawApplicationRequest.getAmt());
            rwWithdraw.setSubject(accountSubjectInfo.getSubject());
            rwWithdraw.setSub_subject(accountSubjectInfo.getSub_subject());
            rwWithdraw.setPay_code(withdrawApplicationRequest.getPay_code());
            rwWithdraw.setFee_amt(withdrawApplicationRequest.getFee_amt());
            rwWithdraw.setClient_property(withdrawApplicationRequest.getClient_property());
            rwWithdraw.setCity_code(withdrawApplicationRequest.getCity_code());

            rwWithdraw.setOppo_account(eaccCardInfo.getCard_no());

            if (StringUtils.isNotBlank(eaccUserInfo.getName())) {
                rwWithdraw.setPayee_name(eaccUserInfo.getName());
            } else {
                rwWithdraw.setPayee_name(eaccUserInfo.getOrg_name());
            }

            //对个人
            if (rwWithdraw.getClient_property().equals(CusType.PERSONAL.getCode())) {
                rwWithdraw.setOpen_branch(eaccCardInfo.getOpen_branch());
                rwWithdraw.setBank_id(eaccCardInfo.getBank_no());
            }

            //对公
            if (rwWithdraw.getClient_property().equals(CusType.COMPANY.getCode())) {
                rwWithdraw.setOpen_branch(withdrawApplicationRequest.getOpen_branch());
                rwWithdraw.setBank_id(withdrawApplicationRequest.getBank_id());
                rwWithdraw.setBrabank_name(withdrawApplicationRequest.getBrabank_name());
            }

            //默认set为借记卡
            rwWithdraw.setCard_type(withdrawApplicationRequest.getCard_type());
            rwWithdraw.setIs_advance(withdrawApplicationRequest.getIs_advance());
            rwWithdraw.setAdvance_amt(new BigDecimal("0"));
            rwWithdraw.setPay_status(PayStatus.INIT_PAY.getCode());
            rwWithdraw.setOrder_no(withdrawApplicationRequest.getOrder_no());
            rwWithdraw.setQuery_no_num(0);
            rwWithdraw.setNotify_url(withdrawApplicationRequest.getNotify_url());
            rwWithdraw.setRemark(withdrawApplicationRequest.getRemark());
            rwWithdraw.setEnabled(Constants.ENABLED);
            rwWithdraw.setCreate_time(new Date());
            rwWithdraw.setUpdate_time(new Date());
            if(StringUtils.isNotBlank(withdrawApplicationRequest.getCity_code())){
                rwWithdraw.setCity_code(withdrawApplicationRequest.getCity_code());
            }
            if(StringUtils.isNotBlank(withdrawApplicationRequest.getProvince_code())){
                rwWithdraw.setProvince_code(withdrawApplicationRequest.getProvince_code());
            }
            rwWithdrawMapper.insert(rwWithdraw);
            logger.info("【批量提现申请】-->添加提现信息成功-->detail_no:" + withdrawApplicationRequest.getOrder_no());

            successData.setAmt(withdrawApplicationRequest.getAmt());
            successData.setDetail_no(withdrawApplicationRequest.getOrder_no());
            successData.setPlatcust(withdrawApplicationRequest.getPlatcust());
            successDataList.add(successData);

            //更新业务流水
            transTransReq.setReturn_code(BusinessMsg.SUCCESS);
            transTransReq.setReturn_msg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));
            transTransReq.setStatus(OrderStatus.SUCCESS.getCode());
            transReqService.insert(transTransReq);
            logger.info("【批量提现申请】-->修改业务流水成功-->detail_no:" + withdrawApplicationRequest.getOrder_no());

        }catch (Exception e){
            logger.info("【批量提现申请】-->提现申请异常-->detail_no:" + withdrawApplicationRequest.getOrder_no(),e);
            BaseResponse baseResponse = new BaseResponse();
            if (e instanceof BusinessException) {
                baseResponse = ((BusinessException) e).getBaseResponse();
            } else {
                baseResponse.setRecode(BusinessMsg.DATEBASE_EXCEPTION);
                baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.DATEBASE_EXCEPTION));
            }
            errorData.setDetail_no(withdrawApplicationRequest.getOrder_no());
            errorData.setError_no(baseResponse.getRecode());
            errorData.setError_info(baseResponse.getRemsg());
            errorDataList.add(errorData);

            transTransReq.setReturn_code(baseResponse.getRecode());
            transTransReq.setReturn_msg(baseResponse.getRemsg());
            transTransReq.setStatus(OrderStatus.FAIL.getCode());
            transReqService.insert(transTransReq);

        }

        batchWithdrawResponse.setRecode(BusinessMsg.SUCCESS);
        batchWithdrawResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));
        batchWithdrawResponse.setPlat_no(withdrawApplicationRequest.getMer_no());
        batchWithdrawResponse.setOrder_no(withdrawApplicationRequest.getOrder_no());
        batchWithdrawResponse.setTrans_date(DateUtils.getyyyyMMddDate());
        batchWithdrawResponse.setFinish_datetime(DateUtils.todayStr(DateUtils.DEF_FORMAT_STRING));
        if (successDataList.size() > 0) {
            batchWithdrawResponse.setOrder_status(OrderStatus.SUCCESS.getCode());
            batchWithdrawResponse.setOrder_info(OrderStatus.SUCCESS.getMsg());
        } else {
            batchWithdrawResponse.setOrder_status(OrderStatus.FAIL.getCode());
            batchWithdrawResponse.setOrder_info(OrderStatus.FAIL.getMsg());
        }
        batchWithdrawResponse.setSuccess_num(successDataList.size());
        batchWithdrawResponse.setTotal_num(1);
        batchWithdrawResponse.setSuccess_data(JSON.toJSONString(successDataList, GlobalConfig.serializerFeature));
        batchWithdrawResponse.setError_data(JSON.toJSONString(errorDataList, GlobalConfig.serializerFeature));

        return batchWithdrawResponse;
    }

    /**
     * 提现确认
     * @author pzy
     * @param withdrawAffirmRequest 参数
     * @return BatchWithdrawResponse
     * @throws BusinessException
     */
    @Override
    public BatchWithdrawResponse withdrawAffirm(WithdrawAffirmRequest withdrawAffirmRequest) throws BusinessException {
        logger.info("【提现确认】-->dubbo-->detail_no:" + withdrawAffirmRequest.getOrder_no());

        BatchWithdrawResponse batchWithdrawResponse = new BatchWithdrawResponse();
        List<SuccessData> successDataList = new ArrayList<SuccessData>();
        List<ErrorData> errorDataList = new ArrayList<ErrorData>();

        //记录业务流水表
        TransTransreq transTransReq = transReqService.getTransTransReq(withdrawAffirmRequest.clone(), TransConsts.WITHDRAW_AFFIRM_CODE, TransConsts.WITHDRAW_AFFIRM_NAME, true);
        transTransReq.setOrder_no(withdrawAffirmRequest.getOrder_no());
        transReqService.insert(transTransReq);

        List<DateDetailAffirm> dateDetails = withdrawAffirmRequest.getDatedetail();
        if (dateDetails == null || dateDetails.size() == 0 || withdrawAffirmRequest.getTotal_num() != dateDetails.size()) {

            logger.info("【提现确认】-->提现确认数据明细size和数量对不上-->order_no:"+withdrawAffirmRequest.getOrder_no());

            transTransReq.setReturn_code(BusinessMsg.PARAMETER_ERROR);
            transTransReq.setReturn_msg(BusinessMsg.getMsg(BusinessMsg.PARAMETER_ERROR));
            transTransReq.setStatus(FlowStatus.FAIL.getCode());
            transReqService.insert(transTransReq);

            BaseResponse baseResponse = new BaseResponse();
            baseResponse.setRecode(BusinessMsg.PARAMETER_ERROR);
            baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.PARAMETER_ERROR) + "数据明细size和数量对不上");
            throw new BusinessException(baseResponse);
        }



        for (DateDetailAffirm dateDetail: withdrawAffirmRequest.getDatedetail()) {
            try{

                validate(dateDetail);

                dateDetail.setIs_advance(dateDetail.getIs_advance());
                dateDetail.setFee_amt(dateDetail.getFee_amt());
                dateDetail.setWithdraw_type(dateDetail.getWithdraw_type());
                dateDetail.setClient_property(dateDetail.getClient_property());
                dateDetail.setCard_type(dateDetail.getCard_type());

                TransTransreq transTransReqDetail = transReqService.getTransTransReq(withdrawAffirmRequest.clone(), TransConsts.WITHDRAW_AFFIRM_CODE, TransConsts.WITHDRAW_AFFIRM_NAME, true);
                transTransReqDetail.setOrder_no(dateDetail.getDetail_no());
                transTransReqDetail.setOrigin_order_no(dateDetail.getOrigin_order_no());
                transReqService.insert(transTransReqDetail);
                logger.info("【提现确认】-->修改明细订单流水成功-->order_no:"+dateDetail.getDetail_no());

                try{
                    RwWithdrawAffirmMQEntity rwWithdrawAffirmMQEntity = new RwWithdrawAffirmMQEntity();
                    rwWithdrawAffirmMQEntity.setBaseRequest(withdrawAffirmRequest.clone());
                    rwWithdrawAffirmMQEntity.setDateDetailAffirm(dateDetail);
                    rwWithdrawMQOptionSerivce.addRwWithdrawAffirm(rwWithdrawAffirmMQEntity);
                    logger.info("【提现确认】-->丢入MQ成功-->order_no:"+dateDetail.getDetail_no());
                }catch (Exception e){
                    logger.error("【提现确认】-->丢入MQ异常-->order_no:"+dateDetail.getDetail_no(),e);
                }
                SuccessData successData = new SuccessData();
                successData.setAmt(dateDetail.getAmt());
                successData.setDetail_no(dateDetail.getDetail_no());
                successData.setPlatcust(dateDetail.getPlatcust());
                successDataList.add(successData);
            }catch (BusinessException e){
                BaseResponse baseResponse = e.getBaseResponse();
                ErrorData errorData = new ErrorData();
                errorData.setDetail_no(dateDetail.getDetail_no());
                errorData.setError_no(baseResponse.getRecode());
                errorData.setError_info(baseResponse.getRemsg());
                errorDataList.add(errorData);
            }
        }
        //更新业务流水
        transTransReq.setReturn_code(BusinessMsg.SUCCESS);
        transTransReq.setReturn_msg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));
        transTransReq.setStatus(OrderStatus.SUCCESS.getCode());
        transReqService.insert(transTransReq);
        logger.info("【提现确认】-->修改批次号订单成功-->order_no:"+withdrawAffirmRequest.getOrder_no());

        batchWithdrawResponse.setRecode(BusinessMsg.SUCCESS);
        batchWithdrawResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));
        batchWithdrawResponse.setPlat_no(withdrawAffirmRequest.getMer_no());
        batchWithdrawResponse.setOrder_no(withdrawAffirmRequest.getOrder_no());
        batchWithdrawResponse.setFinish_datetime(DateUtils.todayStr(DateUtils.DEF_FORMAT_STRING));
        if (successDataList.size() > 0) {
            batchWithdrawResponse.setOrder_status(OrderStatus.PROCESSING.getCode());
            batchWithdrawResponse.setOrder_info(OrderStatus.PROCESSING.getMsg());
        } else {
            batchWithdrawResponse.setOrder_status(OrderStatus.FAIL.getCode());
            batchWithdrawResponse.setOrder_info(OrderStatus.FAIL.getMsg());
        }
        batchWithdrawResponse.setSuccess_num(successDataList.size());
        batchWithdrawResponse.setTotal_num(withdrawAffirmRequest.getTotal_num());
        batchWithdrawResponse.setSuccess_data(JSON.toJSONString(successDataList, GlobalConfig.serializerFeature));
        batchWithdrawResponse.setError_data(JSON.toJSONString(errorDataList, GlobalConfig.serializerFeature));

        return batchWithdrawResponse;
    }


    /**
     * 发往代付系统
     * @author pzy
     * @param rwWithdraw 参数
     * @throws BusinessException
     */
    @Override
    public void doThirdpartyRwWithdraw(RwWithdraw rwWithdraw) throws BusinessException {
        logger.info("【批量提现】-->【单笔代付准备发送前】-->order_no:" + rwWithdraw.getOrder_no());
        RwWithdrawThird rwWithdrawThird = getRwWithdrawThird(RwWithdrawType.WAIT_PAY.getCode(), rwWithdraw.getOrder_no(), rwWithdraw.getPayee_name(), rwWithdraw.getOppo_account(), rwWithdraw.getTrans_serial());
        TransTransreq transTransReq = null;
        try {
            transTransReq = transReqService.queryTransTransReqByOrderno(rwWithdraw.getOrder_no());
            logger.info("【批量提现】-->【单笔代付准备发送前】-->查询流水成功-->order_no:" + rwWithdraw.getOrder_no());
            PlatPaycode platPayCode = accountSearchService.queryPlatPayCode(rwWithdraw.getPlat_no(), rwWithdraw.getPay_code());
            String mall_no = accountSearchService.queryMallNoByPlatNo(rwWithdraw.getPlat_no());
            Map<String, Object> data = new HashMap<>();
            data.put("partner_id", platPayCode.getPayment_plat_no());//合作商编号
            data.put("partner_serial_no", rwWithdrawThird.getThird_no());//合作商流水，即批次号
            data.put("partner_trans_date", rwWithdraw.getTrans_date());//合作商交易日期
            data.put("partner_trans_time", rwWithdraw.getTrans_time());//合作商交易时间
            data.put("channelId", platPayCode.getChannel_id());//渠道编号
            data.put("occur_balance", rwWithdraw.getOut_amt().setScale(2,BigDecimal.ROUND_DOWN));//金额
            data.put("summary",rwWithdraw.getRemark());//摘要
            data.put("memo",rwWithdraw.getRemark());
            data.put("client_name", rwWithdraw.getPayee_name());//客户姓名
            data.put("card_no", rwWithdraw.getOppo_account());//银行卡号
            data.put("province_code",rwWithdraw.getProvince_code()==null?"1":rwWithdraw.getProvince_code());
            data.put("city_code",rwWithdraw.getCity_code());

            EaccUserinfo eaccUserinfo=accountQueryService.getEUserinfoByExist(mall_no,rwWithdraw.getPlatcust());
            if(eaccUserinfo==null){
                logger.info("用户信息不存在，platcust:"+rwWithdraw.getPlatcust());
                return;
            }
            data.put("id_no",eaccUserinfo.getId_code());
            data.put("brabank_name",StringUtils.isNotBlank(rwWithdraw.getBrabank_name())?rwWithdraw.getBrabank_name():rwWithdraw.getOpen_branch());
            data.put("bank_id", rwWithdraw.getBank_id());//开户行号（总行）
            if(CusType.PERSONAL.getCode().equals(rwWithdraw.getClient_property())){
                data.put("client_property","0");//存管对私1   代付对私0
            }else if(CusType.COMPANY.getCode().equals(rwWithdraw.getClient_property())){
                data.put("client_property","1");//存管对公2   代付对私1
            }
            //获取回调URL
            logger.info("【批量提现】获取回调URL-->order_no:" + rwWithdraw.getOrder_no());
            String return_ip = sysParameterService.querySysParameter(SysParamterKey.FTDM, URLConfigUtil.WEB_LOCAL_SERVER);
            String return_url = sysParameterService.querySysParameter(SysParamterKey.FTDM, URLConfigUtil.PAYPLAT_SINGLE_PAY_RETURN);
            if(StringUtils.isEmpty(return_ip)){
                logger.info("【批量提现】-->回调ip不存在-->order_no:" + rwWithdraw.getOrder_no());
                throw new BusinessException(BusinessMsg.FAIL,"代付异常,回调ip不存在");
            }
            if(StringUtils.isEmpty(return_url)){
                logger.info("【批量提现】-->回调url不存在-->order_no:" + rwWithdraw.getOrder_no());
                throw new BusinessException(BusinessMsg.FAIL,"代付异常,回调url不存在");
            }
            data.put("receive_url", return_ip + return_url);//后台回调地址
            data.put("func_code","0");
            String Base = sysParameterService.querySysParameter(mall_no, URLConfigUtil.EPAY_SERVER_KEY);
            logger.info("【批量提现】-->【单笔代付准备发送前】-->获取第三方服务请求-->order_no:" + rwWithdraw.getOrder_no());
            String URL = sysParameterService.querySysParameter(mall_no, URLConfigUtil.PAYPLAT_SINGLE_PAY);
            if("229".equals(platPayCode.getChannel_id())){
                URL=sysParameterService.querySysParameter(mall_no, URLConfigUtil.PAYPLAT_SINGLE_PAY_LL);
            }
            if(StringUtils.isEmpty(Base)){
                logger.info("【批量提现】-->请求ip不存在-->order_no:" + rwWithdraw.getOrder_no());
                throw new BusinessException(BusinessMsg.FAIL,"代付异常,请求ip不存在");
            }
            if(StringUtils.isEmpty(URL)){
                logger.info("【批量提现】-->请求url不存在-->order_no:" + rwWithdraw.getOrder_no());
                throw new BusinessException(BusinessMsg.FAIL,"代付异常,请求url不存在");
            }
            data.put("host", Base);
            data.put("url", URL);

//            RwWithdraw rwWithdrawnow = new RwWithdraw();
//            rwWithdrawnow.setPay_status(PayStatus.CCB_PAYING.getCode());
//            rwWithdrawnow.setId(rwWithdraw.getId());
//            int i = custRwWithdrawMapper.updateByIdAndPayStatusByInit(rwWithdrawnow);
            int i=updateRwWithdrawByIdAndStatus(rwWithdraw.getId(),PayStatus.CCB_ADD_SUCCESS.getCode(),PayStatus.CCB_PAYING.getCode());
            if(i<=0){
                logger.info("【批量提现】-->【修改提现状态为处理中失败,可能schedule并发异常】-->order_no:" + rwWithdraw.getOrder_no());
                return;
            }
            logger.info("【批量提现】-->【先修改提现状态为处理中】-->order_no:" + rwWithdraw.getOrder_no());

            logger.info("【批量提现】-->【单笔代付准备发送前】-->调用三方服务开始代付-->order_no:" + rwWithdraw.getOrder_no());
            Map<String, Object> map = adapterService.withdrawInBatch(data);
            logger.info("【批量提现】-->【单笔代付发送成功】-->返回:" + map.toString()+"-->order_no:" + rwWithdraw.getOrder_no());
            if(map.get("order_status") != null && String.valueOf(map.get("order_status")).equals(PayStatus.PAYING.getCode())){

//                RwWithdraw rwWithdrawUpdate = new RwWithdraw();
//                rwWithdrawUpdate.setPay_status(PayStatus.CCB_PAYING.getCode());
////                rwWithdrawUpdate.setPayment_status(OrderStatus.PROCESSING.getCode());
//                rwWithdrawUpdate.setRemark("处理中");
//                rwWithdrawUpdate.setUpdate_time(new Date());
//                rwWithdrawUpdate.setId(rwWithdraw.getId());
//                custRwWithdrawMapper.updateByIdAndPayStatusByProcessing(rwWithdrawUpdate);
                logger.info("【批量提现】-->【修改提现信息成功】-->order_no:" + rwWithdraw.getOrder_no());
            }else if(map.get("order_status") != null && String.valueOf(map.get("order_status")).equals(PayStatus.FAIL.getCode())){

//                RwWithdraw rwWithdrawUpdate = new RwWithdraw();
//                rwWithdrawUpdate.setPay_status(PayStatus.FAIL.getCode());
////                rwWithdrawUpdate.setPayment_status(PayStatus.PAYMENTFAILNO.getCode());
//                rwWithdrawUpdate.setRemark(PayStatus.FAIL.getMsg());
//                rwWithdrawUpdate.setUpdate_time(new Date());
//                rwWithdrawUpdate.setId(rwWithdraw.getId());
//                custRwWithdrawMapper.updateByIdAndPayStatusByProcessing(rwWithdrawUpdate);
                updateRwWithdrawByIdAndStatus(rwWithdraw.getId(),PayStatus.CCB_PAYING.getCode(),PayStatus.FAIL.getCode());
                //修改提现退款状态为退款处理中
                updateRwWithdrawByIdAndAccStatus(rwWithdraw.getId(), OrderStatus.REFUNDPROCESSING.getCode());
                //修改代付结果为失败
                updateRwWithdrawThirdStatus(rwWithdrawThird.getOrder_no(), rwWithdrawThird.getThird_no(), OrderStatus.FAIL.getCode(), map.get("remsg").toString());
                logger.info("【批量提现】-->【修改提现信息成功】-->order_no:" + rwWithdraw.getOrder_no());

//                accountTransferService.queryEaccAccountamtlistByTransSerialAndPlatcust(rwWithdraw.getTrans_serial());
//                logger.info("【批量提现】代付时返回交易失败未扣款,反向回滚成功--> order_no：" + rwWithdraw.getOrder_no());

                transTransReq.setReturn_code(BusinessMsg.FAIL);
                transTransReq.setReturn_msg(BusinessMsg.getMsg(BusinessMsg.FAIL)+","+map.get("remsg").toString());
                transTransReq.setStatus(OrderStatus.FAIL.getCode());
                transReqService.insert(transTransReq);
                logger.info("【批量提现】-->【修改订单信息成功】-->order_no:" + rwWithdraw.getOrder_no());

                try {
                    NotityPaymentReq notityPaymentReq = new NotityPaymentReq();
                    notityPaymentReq.setMall_no(mall_no);
                    notityPaymentReq.setPlat_no(rwWithdraw.getPlat_no());
                    notityPaymentReq.setPlatcust(rwWithdraw.getPlatcust());
                    notityPaymentReq.setOrder_no(rwWithdraw.getOrder_no());
                    notityPaymentReq.setOrder_amt(rwWithdraw.getOut_amt());
                    notityPaymentReq.setAdvance_amt(rwWithdraw.getAdvance_amt());
                    notityPaymentReq.setTrans_date(rwWithdraw.getTrans_date());
                    notityPaymentReq.setTrans_time(rwWithdraw.getTrans_time());
                    notityPaymentReq.setPay_order_no(rwWithdraw.getTrans_serial());
                    notityPaymentReq.setPay_finish_date(DateUtils.todayStr(DateUtils.DEF_FORMAT_NOTIME));
                    notityPaymentReq.setPay_finish_time(DateUtils.todayStr("hhmmss"));
                    if (rwWithdraw.getFee_amt() == null)
                        rwWithdraw.setFee_amt(BigDecimal.ZERO);
                    notityPaymentReq.setPay_amt(rwWithdraw.getFee_amt().add(rwWithdraw.getOut_amt()));

                    notityPaymentReq.setOrder_status(OrderStatus.FAIL.getCode());
                    notityPaymentReq.setError_no(OrderStatus.REFUNDPROCESSING.getCode());
                    notityPaymentReq.setError_info("交易失败，" + map.get("remsg").toString());

                    NotifyData notifyData = new NotifyData();
                    notifyData.setNotifyUrl(rwWithdraw.getNotify_url());
                    notifyData.setMall_no(mall_no);
                    notifyData.setNotifyContent(JSONObject.toJSONString(notityPaymentReq));
                    notifyService.addNotify(notifyData);
                    logger.info("【行内代付的通知或发起查询】添加异步通知队列成功-->通知数据：" + notifyData.toString());
                }catch (Exception e){
                    logger.error("【行内代付的通知或发起查询】-->通知时异常-->trans_serial:"+rwWithdraw.getOrder_no(),e);
                }
            }

        }catch (Exception e){
            logger.info("【批量提现】-->异常-->order_no:" + rwWithdraw.getOrder_no(),e);
            if (e instanceof BusinessException){

                logger.info("【批量提现】-->本地business异常，不修改订单等待下次代付-->order_no:" + rwWithdraw.getOrder_no(),e);

                transTransReq.setReturn_code(((BusinessException) e).getBaseResponse().getRecode());
                transTransReq.setReturn_msg(((BusinessException) e).getBaseResponse().getRemsg());
                transTransReq.setStatus(OrderStatus.PROCESSING.getCode());
            }else {
                logger.info("【批量提现】-->本地调用三方异常，修改订单状态为处理中-->order_no:" + rwWithdraw.getOrder_no(),e);

                RwWithdraw rwWithdrawUpdate = new RwWithdraw();
                rwWithdrawUpdate.setPay_status(PayStatus.PAYING.getCode());
                rwWithdrawUpdate.setRemark(PayStatus.PAYING.getMsg()+"代付请求时未知异常");
                rwWithdrawUpdate.setUpdate_time(new Date());
                rwWithdrawUpdate.setId(rwWithdraw.getId());
                custRwWithdrawMapper.updateByIdAndPayStatusByProcessing(rwWithdrawUpdate);
                logger.info("【批量提现】-->【修改提现信息成功】-->order_no:" + rwWithdraw.getOrder_no());

                transTransReq.setReturn_code(BusinessMsg.UNKNOW_ERROE);
                transTransReq.setReturn_msg(BusinessMsg.getMsg(BusinessMsg.UNKNOW_ERROE)+"代付请求时未知异常");
                transTransReq.setStatus(OrderStatus.PROCESSING.getCode());
                logger.info("【批量提现】-->【修改订单信息成功】-->order_no:" + rwWithdraw.getOrder_no());
            }
            transReqService.insert(transTransReq);

        }
    }

    private void updateRwWithdrawByIdAndAccStatus(Integer id, String acc_state) {
        RwWithdraw rwWithdraw = new RwWithdraw();
        rwWithdraw.setId(id);
        rwWithdraw.setAcct_state(acc_state);
        rwWithdrawMapper.updateByPrimaryKeySelective(rwWithdraw);
    }

    /**
     * 行内代付通知或发起查询具体处理
     * @author pzy
     * @param notifyEpayAgentPayRealReturnData 参数
     * @throws BusinessException
     */
    @Override
    public boolean doRwWithdrawNotify(NotifyEpayAgentPayRealReturnData notifyEpayAgentPayRealReturnData) throws BusinessException {
        String thirdNo = notifyEpayAgentPayRealReturnData.getPartner_serial_no();
        RwWithdrawThird rwWithdrawThird = queryRwWithdrawThridByThirdNo(thirdNo);
        if(rwWithdrawThird == null){
            return false;
        }
        if(PayStatus.SUCCESS.getCode().equals(notifyEpayAgentPayRealReturnData.getPay_status())){
            notifyEpayAgentPayRealReturnData.setPay_status(OrderStatus.SUCCESS.getCode());
        }else if(PayStatus.FAIL.getCode().equals(notifyEpayAgentPayRealReturnData.getPay_status())){
            notifyEpayAgentPayRealReturnData.setPay_status(OrderStatus.FAIL.getCode());
        }
        logger.info("【行内代付的通知或发起查询】-->流水号："+notifyEpayAgentPayRealReturnData.getPartner_serial_no());
        try {
            RwWithdrawExample example = new RwWithdrawExample();
            RwWithdrawExample.Criteria criteria = example.createCriteria();
            criteria.andOrder_noEqualTo(rwWithdrawThird.getOrder_no());
            List<RwWithdraw> list = rwWithdrawMapper.selectByExample(example);
            if(list.size() != 1){
                logger.info("【行内代付的通知或发起查询】根据订单号查询不到提现信息或者有多条order_no:{}", rwWithdrawThird.getOrder_no());
                return false;
            }
            RwWithdraw rwWithdraw = list.get(0);
            TransTransreq transTransreq = transReqService.queryTransTransReqByOrderno(rwWithdraw.getOrder_no());
            if(transTransreq == null){
                logger.info("【行内代付的通知或发起查询】-->根据此订单号找不到订单信息-->order_no:"+rwWithdraw.getOrder_no());
                return false;
            }

            String mall_no = accountSearchService.queryMallNoByPlatNo(rwWithdraw.getPlat_no());

            if (notifyEpayAgentPayRealReturnData.getPay_status().equals(OrderStatus.SUCCESS.getCode())) {
                if (!rwWithdraw.getPay_status().equals(PayStatus.CCB_PAYING.getCode())) {
                    logger.info("【行内代付的通知或发起查询】根据流水号查询到本地数据(【" + rwWithdraw.getPay_status() + "】)不是处理中，不是处理中的状态不能修改为成功-->order_no:"+rwWithdraw.getOrder_no());
                    return false;
                }
            } else if (notifyEpayAgentPayRealReturnData.getPay_status().equals(OrderStatus.PROCESSING.getCode())) {
                logger.info("【行内代付的通知或发起查询】处理中的通知不予理会-->order_no:"+rwWithdraw.getOrder_no());
                return false;
            } else {
                if (rwWithdraw.getPay_status().equals(PayStatus.FAIL.getCode())) {
                    logger.info("【行内代付的通知或发起查询】本地状态失败，再次通知失败不予理会-->order_no:"+rwWithdraw.getOrder_no());
                }
            }
            if (notifyEpayAgentPayRealReturnData.getPay_status().equals(OrderStatus.SUCCESS.getCode())) {
                logger.info("【行内代付的通知或发起查询】交易成功-->order_no:"+rwWithdraw.getOrder_no());
                transTransreq.setReturn_code(BusinessMsg.SUCCESS);
                transTransreq.setStatus(OrderStatus.SUCCESS.getCode());
                transTransreq.setReturn_msg("【行内代付的通知或发起查询】" + OrderStatus.SUCCESS.getMsg());
                transTransreq.setUpdate_time(new Date());
                logger.info("【行内代付的通知或发起查询】更新数据库流水表-->order_no:"+rwWithdraw.getOrder_no());
                transReqService.insert(transTransreq);
                logger.info("【行内代付的通知或发起查询】更新数据库流水表成功-->order_no:"+rwWithdraw.getOrder_no());

                logger.info("【行内代付的通知或发起查询】SUCCESS-->order_no:"+rwWithdraw.getOrder_no());
                RwWithdraw rwWithdrawUpdate = new RwWithdraw();
                rwWithdrawUpdate.setPay_status(PayStatus.SUCCESS.getCode());
                rwWithdrawUpdate.setFinish_time(DateUtils.todayStr(DateUtils.DEF_FORMAT_NOTIME));
                rwWithdrawUpdate.setPayment_date(StringUtils.isBlank(notifyEpayAgentPayRealReturnData.getDf_trans_date())?
                        DateUtils.getyyyyMMddDate():notifyEpayAgentPayRealReturnData.getDf_trans_date());
                rwWithdrawUpdate.setUpdate_time(new Date());
                rwWithdrawUpdate.setRemark("【行内代付的通知或发起查询】，交易成功");
                rwWithdrawUpdate.setId(rwWithdraw.getId());
                logger.info("【行内代付的通知或发起查询】开始更新数据库提现状态-->order_no:"+rwWithdraw.getOrder_no());
                int i = custRwWithdrawMapper.updateByIdAndPayStatusByProcessingCCB(rwWithdrawUpdate);
                if(i<=0){
                    return false;
                }
                //修改代付状态
                updateRwWithdrawThirdStatus(rwWithdraw.getOrder_no(), notifyEpayAgentPayRealReturnData.getPartner_serial_no(), OrderStatus.SUCCESS.getCode(), BusinessMsg.getMsg(BusinessMsg.SUCCESS));
                logger.info("【行内代付的通知或发起查询】更新数据库提现状态成功-->order_no:"+rwWithdraw.getOrder_no());

                try {
                    EaccAccountamtlist eaccAccountamtlist = new EaccAccountamtlist();
                    eaccAccountamtlist.setOrder_no(rwWithdraw.getOrder_no());
                    eaccAccountamtlist.setTrans_serial(rwWithdraw.getTrans_serial());
                    eaccAccountamtlist.setPlat_no(rwWithdraw.getPlat_no());
                    eaccAccountamtlist.setPlatcust(rwWithdraw.getPlat_no());
                    eaccAccountamtlist.setSubject(Tsubject.CASH.getCode());
                    eaccAccountamtlist.setSub_subject(rwWithdraw.getPay_code());
                    eaccAccountamtlist.setAmt(rwWithdraw.getOut_amt());
                    eaccAccountamtlist.setTrans_code(TransConsts.PLAT_MADDLE_ACCOUNTS_CODE);
                    eaccAccountamtlist.setTrans_name(TransConsts.PLAT_MADDLE_ACCOUNTS_NAME);
                    eaccAccountamtlist.setTrans_date(DateUtils.todayStr(DateUtils.DEF_FORMAT_NOTIME));
                    accountTransferService.queryEaccAccountamtlistWithdrawByTransSerial(eaccAccountamtlist);
                }catch (Exception e){
                    logger.error("【行内代付的通知或发起查询】中间户减款失败-->order_no:"+rwWithdraw.getOrder_no(),e);
                }

                logger.info("【行内代付的通知或发起查询】修改账期-->order_no:"+rwWithdraw.getOrder_no());
                try {
                    accountTransferService.queryEaccAccountamtlistByTransSerial(rwWithdraw.getTrans_serial(),
                            notifyEpayAgentPayRealReturnData.getDf_trans_date());
                    logger.info("【行内代付的通知或发起查询】修改账期成功-->order_no:"+rwWithdraw.getOrder_no());
                } catch (Exception e) {
                    logger.error("【行内代付的通知或发起查询】修改账期失败-->order_no:"+rwWithdraw.getOrder_no(),e);
                }

                try {
                    logger.info("【行内代付的通知或发起查询】【提现发送短信】-->发送通知短信给个人-->order_no:"+rwWithdraw.getOrder_no());
                    if (!Ssubject.FINANCING.getCode().equals(rwWithdraw.getSub_subject())) {
                        mall_no = accountSearchService.queryMallNoByPlatNo(rwWithdraw.getPlat_no());
                        logger.info("【行内代付的通知或发起查询】【提现发送短信】-->根据平台获取集团号成功-->order_no:"+rwWithdraw.getOrder_no());
                        EaccUserinfo eaccUserInfo = accountSearchService.queryEaccUserInfoByEaccAccountInfo(mall_no, null, rwWithdraw.getPlatcust());
                        if (eaccUserInfo == null) {
                            logger.info("【行内代付的通知或发起查询】【提现发送短信】用户信息不存在-->用户平台客户账号：mallcust：" + rwWithdraw.getPlatcust()+"-->order_no:"+rwWithdraw.getOrder_no());                            throw new BusinessException(BusinessMsg.INVALID_ACCOUNT, BusinessMsg.getMsg(BusinessMsg.INVALID_ACCOUNT) + ",用户账号不存在");
                        }
                        logger.info("【行内代付的通知或发起查询】【提现发送短信】用户信息存在-->order_no:"+rwWithdraw.getOrder_no());
                        MsgModel msgModel = new MsgModel();
                        String mall_name = sysParameterService.querySysParameter(mall_no, SysParamterKey.MALL_NAME);
                        logger.info("【行内代付的通知或发起查询】【提现发送短信】-->获取集团名称成功:"+mall_name+"-->order_no:"+rwWithdraw.getOrder_no());
                        //BigDecimal allAmt = sendMsgService.getAccountAllAmount(rwWithdraw.getPlatcust());
                        msgModel.setOrder_no(rwWithdraw.getOrder_no());
                        msgModel.setPlat_no(rwWithdraw.getPlat_no());
                        msgModel.setTrans_code(transTransreq.getTrans_code());
                        msgModel.setMobile(eaccUserInfo.getMobile());
                        EaccCardinfo eaccCardinfo = accountQueryService.getEaccCardInfoByMallnoAndMallcustAndCardno(mall_no, rwWithdraw.getPlatcust(), rwWithdraw.getOppo_account());
                        logger.info("【行内代付的通知或发起查询】【提现发送短信】-->获取绑卡信息成功-->order_no:"+rwWithdraw.getOrder_no());
                        if (StringUtils.isNotBlank(eaccCardinfo.getMobile()))
                            msgModel.setMobile(eaccCardinfo.getMobile());
                        msgModel.setMall_no(eaccUserInfo.getMall_no());
                        msgModel.setPlatcust(eaccUserInfo.getMallcust());
                        String content=sysParameterService.querySysParameter(mall_no,MsgContent.RW_WITHDRAW_TO_PLAT_NO.getMsg());
                        logger.info("【行内代付的通知或发起查询】【提现发送短信】-->获取短信文案成功:" + content + "-->order_no:"+rwWithdraw.getOrder_no());
                        if(StringUtils.isNotBlank(content)){
                            msgModel.setMsgContent(String.format(content,
                                    mall_name, NumberUtils.formatNumber(rwWithdraw.getOut_amt()), mall_name));
                            //=========ccb参数===========
                            msgModel.setTrans_serial(transTransreq.getTrans_serial());
                            //msgModel.setMsg_type(MsgType.REPAY_NOTIFY.getType());
                            msgModel.setAmount(rwWithdraw.getOut_amt());
                            sendMsgService.addMsgToQueue(msgModel);
                            logger.info("【行内代付的通知或发起查询】【提现发送短信】-->丢入短信队列成功-->order_no:"+rwWithdraw.getOrder_no());
                        }
                    } else {
                        logger.error("【行内代付的通知或发起查询】【融资人提现】-->不发短信-->order_no:"+rwWithdraw.getOrder_no());
                    }
                } catch (Exception e) {
                    logger.error("【行内代付的通知或发起查询】提现发送短信异常-->order_no:"+rwWithdraw.getOrder_no(), e);
                }


                try {
                    NotityPaymentReq notityPaymentReq = new NotityPaymentReq();
                    notityPaymentReq.setMall_no(mall_no);
                    notityPaymentReq.setPlat_no(rwWithdraw.getPlat_no());
                    notityPaymentReq.setPlatcust(rwWithdraw.getPlatcust());
                    notityPaymentReq.setOrder_no(rwWithdraw.getOrder_no());
                    notityPaymentReq.setOrder_amt(rwWithdraw.getOut_amt());
                    notityPaymentReq.setAdvance_amt(rwWithdraw.getAdvance_amt());
                    notityPaymentReq.setTrans_date(rwWithdraw.getTrans_date());
                    notityPaymentReq.setTrans_time(rwWithdraw.getTrans_time());
                    notityPaymentReq.setPay_order_no(rwWithdraw.getTrans_serial());
                    notityPaymentReq.setPay_finish_date(DateUtils.todayStr(DateUtils.DEF_FORMAT_NOTIME));
                    notityPaymentReq.setPay_finish_time(DateUtils.todayStr("hhmmss"));

                    if (rwWithdraw.getFee_amt() == null)
                        rwWithdraw.setFee_amt(BigDecimal.ZERO);

                    notityPaymentReq.setPay_amt(rwWithdraw.getFee_amt().add(rwWithdraw.getOut_amt()));
                    notityPaymentReq.setOrder_status(OrderStatus.SUCCESS.getCode());

                    NotifyData notifyData = new NotifyData();
                    notifyData.setNotifyUrl(rwWithdraw.getNotify_url());
                    notifyData.setMall_no(mall_no);
                    notifyData.setNotifyContent(JSONObject.toJSONString(notityPaymentReq));
                    if(StringUtils.isNotEmpty(rwWithdraw.getNotify_url())){
                        notifyService.addNotify(notifyData);
                    }
                    logger.info("【行内代付的通知或发起查询】添加异步通知队列成功-->通知数据：" + notifyData.toString()+"-->order_no:"+rwWithdraw.getOrder_no());
                }catch (Exception e){
                    logger.info("【行内代付的通知或发起查询】添加异步通知队列失败-->order_no:"+rwWithdraw.getOrder_no());
                }
                return true;

            } else if (notifyEpayAgentPayRealReturnData.getPay_status().equals(OrderStatus.FAIL.getCode())) {
                logger.info("【行内代付的通知或发起查询】交易失败已扣款-->order_no:"+rwWithdraw.getOrder_no());

                transTransreq.setReturn_code(BusinessMsg.FAIL);
                transTransreq.setStatus(OrderStatus.REFUNDPROCESSING.getCode());
                transTransreq.setReturn_msg("需管理台触发退款," + OrderStatus.REFUNDPROCESSING.getMsg() + "," + notifyEpayAgentPayRealReturnData.getFail_cause());
                transTransreq.setUpdate_time(new Date());
                logger.info("【行内代付的通知或发起查询】更新数据库流水表-->order_no:"+rwWithdraw.getOrder_no());
                transReqService.insert(transTransreq);
                logger.info("【行内代付的通知或发起查询】更新数据库流水表成功-->order_no:"+rwWithdraw.getOrder_no());

                RwWithdraw rwWithdrawUpdatefail = new RwWithdraw();
                rwWithdrawUpdatefail.setPay_status(PayStatus.FAIL.getCode());
                rwWithdrawUpdatefail.setAcct_state(OrderStatus.REFUNDPROCESSING.getCode());
                rwWithdrawUpdatefail.setPayment_status(PayStatus.PAYMENTFAILYES.getCode());
                rwWithdrawUpdatefail.setPayment_date(StringUtils.isBlank(notifyEpayAgentPayRealReturnData.getDf_trans_date()) ? DateUtils.todayStr(DateUtils.DEF_FORMAT_NOTIME) : notifyEpayAgentPayRealReturnData.getDf_trans_date());
                rwWithdrawUpdatefail.setFinish_time(DateUtils.todayStr(DateUtils.DEF_FORMAT_NOTIME));
                rwWithdrawUpdatefail.setUpdate_time(new Date());
                rwWithdrawUpdatefail.setRemark("【行内代付的通知或发起查询】，交易失败已扣款，需管理台来触发退款," + notifyEpayAgentPayRealReturnData.getFail_cause());
                rwWithdrawUpdatefail.setId(rwWithdraw.getId());
                logger.info("【行内代付的通知或发起查询】开始更新数据库提现状态-->order_no:"+rwWithdraw.getOrder_no());

                int i = custRwWithdrawMapper.updateByIdAndPayStatusByProcessingCCB(rwWithdrawUpdatefail);
                if(i<=0){
                    return false;
                }
                //修改代付状态
                updateRwWithdrawThirdStatus(rwWithdraw.getOrder_no(), notifyEpayAgentPayRealReturnData.getPartner_serial_no(), OrderStatus.FAIL.getCode(), notifyEpayAgentPayRealReturnData.getFail_cause());
                logger.info("【行内代付的通知或发起查询】更新数据库状态提现状态成功-->order_no:"+rwWithdraw.getOrder_no());

                try {
                    NotityPaymentReq notityPaymentReq = new NotityPaymentReq();
                    notityPaymentReq.setMall_no(mall_no);
                    notityPaymentReq.setPlat_no(rwWithdraw.getPlat_no());
                    notityPaymentReq.setPlatcust(rwWithdraw.getPlatcust());
                    notityPaymentReq.setOrder_no(rwWithdraw.getOrder_no());
                    notityPaymentReq.setOrder_amt(rwWithdraw.getOut_amt());
                    notityPaymentReq.setAdvance_amt(rwWithdraw.getAdvance_amt());
                    notityPaymentReq.setTrans_date(rwWithdraw.getTrans_date());
                    notityPaymentReq.setTrans_time(rwWithdraw.getTrans_time());
                    notityPaymentReq.setPay_order_no(rwWithdraw.getTrans_serial());
                    notityPaymentReq.setPay_finish_date(DateUtils.todayStr(DateUtils.DEF_FORMAT_NOTIME));
                    notityPaymentReq.setPay_finish_time(DateUtils.todayStr("hhmmss"));

                    if (rwWithdraw.getFee_amt() == null)
                        rwWithdraw.setFee_amt(BigDecimal.ZERO);

                    notityPaymentReq.setPay_amt(rwWithdraw.getFee_amt().add(rwWithdraw.getOut_amt()));
                    notityPaymentReq.setOrder_status(OrderStatus.REFUNDPROCESSING.getCode());
                    notityPaymentReq.setError_no(OrderStatus.REFUNDPROCESSING.getCode());
                    notityPaymentReq.setError_info("交易失败，" + OrderStatus.REFUNDPROCESSING.getCode() + "," + notifyEpayAgentPayRealReturnData.getFail_cause());

                    NotifyData notifyData = new NotifyData();
                    notifyData.setNotifyUrl(rwWithdraw.getNotify_url());
                    notifyData.setMall_no(mall_no);
                    notifyData.setNotifyContent(JSONObject.toJSONString(notityPaymentReq));
                    if(StringUtils.isNotEmpty(rwWithdraw.getNotify_url())){
                        notifyService.addNotify(notifyData);
                    }
                    logger.info("【行内代付的通知或发起查询】添加异步通知队列成功-->通知数据：" + notifyData.toString()+"-->order_no:"+rwWithdraw.getOrder_no());
                }catch (Exception e){
                    logger.info("【行内代付的通知或发起查询】添加异步通知队列失败-->order_no:"+rwWithdraw.getOrder_no());
                }
                return true;
            }
        }catch (Exception e){
            logger.error("【行内代付的通知或发起查询】-->流水号:"+notifyEpayAgentPayRealReturnData.getPartner_serial_no()+"-->异常:",e);
        }
        return true;

    }

    /**
     * 查询成功的提现
     * @author pzy
     * @param date 参数
     * @throws BusinessException
     */
    @Override
    public List<RwWithdraw> querySuccessRwWithdraw(String date) throws BusinessException {
        RwWithdrawExample example = new RwWithdrawExample();
        RwWithdrawExample.Criteria criteria = example.createCriteria();
        criteria.andPay_statusEqualTo(PayStatus.SUCCESS.getCode());
        criteria.andPayment_dateEqualTo(date);
        List<RwWithdraw> rwWithdraws = rwWithdrawMapper.selectByExample(example);
        return rwWithdraws;
    }

    /**
     * 查询自动退款的提现
     * @author pzy
     * @param count 参数
     * @throws BusinessException
     */
    @Override
    public List<RwWithdraw> queryAllAutomaticRefund(int count) throws BusinessException {
        logger.info("【提现退款】-->查询银联待退款的提现");
        String paycode = sysParameterService.querySysParameter("FTDM", "union_paycode");
        if(StringUtils.isBlank(paycode)) {
            logger.info("【提现退款】-->根据FTDM和union_paycode查询不到自动退款的pay_code");
            return null;
        }
        List<RwWithdraw> rwWithdrawList = custRwWithdrawMapper.getRefund(Arrays.asList(paycode.split(",")), count);
        if(rwWithdrawList.size() == 0){
            logger.info("【提现退款】-->没有银联待退款的提现");
            return null;
        }
        return rwWithdrawList;
    }

    /**
     * 处理自动退款的提现
     * @author pzy
     * @param rwWithdraw 参数
     * @throws BusinessException
     */
    @Override
    public BaseResponse doAutomaticRefund(RwWithdraw rwWithdraw) throws BusinessException {
        BaseResponse baseResponse = new BaseResponse();
        try {
            if (!PayStatus.FAIL.getCode().equals(rwWithdraw.getPay_status())
                    && !PayStatus.FAIL_DF.getCode().equals(rwWithdraw.getPay_status())
                    && !PayStatus.FAIL_ZDJZ.getCode().equals(rwWithdraw.getPay_status())) {
                logger.info("【提现退款】-->获取提现退款ip失败-->order_no:" + rwWithdraw.getOrder_no());
                baseResponse.setRecode(BusinessMsg.FAIL);
                baseResponse.setRemsg("提现状态不是失败状态，不允许退款");
                return baseResponse;
            }

            if (!OrderStatus.REFUNDPROCESSING.getCode().equals(rwWithdraw.getAcct_state())
                    && !OrderStatus.PAY_TO_PLAT_PROCESSING.getCode().equals(rwWithdraw.getAcct_state())
                    && rwWithdraw.getAcct_state() != null) {
                logger.info("【提现退款】-->获取提现退款ip失败-->order_no:" + rwWithdraw.getOrder_no());
                baseResponse.setRecode(BusinessMsg.FAIL);
                baseResponse.setRemsg("退款状态不是退款处理中，也不是回拨处理中，不允许退款");
                return baseResponse;
            }

            logger.info("【提现退款】-->开始处理退款-->order_no:" + rwWithdraw.getOrder_no());
            String mall_no = accountSearchService.queryMallNoByPlatNo(rwWithdraw.getPlat_no());
            PlatPaycode platPaycode = accountSearchService.queryPlatPayCode(rwWithdraw.getPlat_no(), rwWithdraw.getPay_code());
            String host = sysParameterService.querySysParameter(mall_no, URLConfigUtil.EPAY_SERVER_KEY);
            if (StringUtils.isBlank(host)) {
                logger.info("【提现退款】-->获取提现退款ip失败-->order_no:" + rwWithdraw.getOrder_no());
                baseResponse.setRecode(BusinessMsg.FAIL);
                baseResponse.setRemsg("获取提现退款ip失败,mall_no:" + mall_no + ",key:" + host);
                return baseResponse;
            }
            String url = sysParameterService.querySysParameter(mall_no, URLConfigUtil.PAYPLAT_REFUND);
            if (StringUtils.isBlank(url)) {
                logger.info("【提现退款】-->获取提现退款地址失败-->order_no:" + rwWithdraw.getOrder_no());
                baseResponse.setRecode(BusinessMsg.FAIL);
                baseResponse.setRemsg("获取提现退款地址失败,mall_no:" + mall_no + ",key:" + url);
                return baseResponse;
            }
            //根据平台号查询手续费账户
            String sub_subject = sysParameterService.querySysParameter(mall_no, SysParamterKey.FEE_ACCOUNT);//手续费账户
            //查询手续费
            EaccAccountamtlistExample example = new EaccAccountamtlistExample();
            EaccAccountamtlistExample.Criteria criteria = example.createCriteria();
            criteria.andTrans_serialEqualTo(rwWithdraw.getTrans_serial());
            criteria.andAmt_typeEqualTo(AmtType.EXPENSIVE.getCode());
            if (StringUtils.isNotBlank(sub_subject)) {
                criteria.andOppo_sub_subjectEqualTo(sub_subject);
            }
            List<EaccAccountamtlist> amtLists = eaccAccountamtlistMapper.selectByExample(example);
            logger.info("【提现退款】-->查询手续费退款成功-->条数:" + amtLists.size() + "-->order_no:" + rwWithdraw.getOrder_no());
            if (amtLists.size() != 0) {
                EaccAccountamtlist amtList = amtLists.get(0);
                //转手续费
                //不管是否清算，全部退现金
                EaccAccountamtlist eaccAccountamtlist = new EaccAccountamtlist();
                BeanUtils.copyProperties(amtList, eaccAccountamtlist);
                eaccAccountamtlist.setOrder_no(rwWithdraw.getOrder_no());
                eaccAccountamtlist.setTrans_serial(rwWithdraw.getTrans_serial());
                eaccAccountamtlist.setTrans_code(TransConsts.REFUND_CODE);
                eaccAccountamtlist.setTrans_name(TransConsts.REFUND_NAME);
                eaccAccountamtlist.setTrans_date(DateUtils.getyyyyMMddDate());
                eaccAccountamtlist.setTrans_time(DateUtils.format(new Date(), "HHmmss"));
                eaccAccountamtlist.setCreate_time(new Date());
                //设置手续费金额，不管是否一半现金一半在途，退总金额
                eaccAccountamtlist.setAmt(rwWithdraw.getFee_amt());
                //翻转
                eaccAccountamtlist.setPlat_no(amtList.getOppo_plat_no());
                eaccAccountamtlist.setPlatcust(amtList.getOppo_platcust());
                eaccAccountamtlist.setSubject(Tsubject.CASH.getCode());//现金
                eaccAccountamtlist.setSub_subject(amtList.getOppo_sub_subject());

                eaccAccountamtlist.setOppo_plat_no(amtList.getOppo_plat_no());
                eaccAccountamtlist.setOppo_platcust(amtList.getPlatcust());
                eaccAccountamtlist.setOppo_subject(Tsubject.CASH.getCode());//现金
                eaccAccountamtlist.setOppo_sub_subject(amtList.getSub_subject());

                accountTransferService.transfer(eaccAccountamtlist);
                logger.info("【提现退款】-->手续费退款成功-->order_no:" + rwWithdraw.getOrder_no());
            } else {
                logger.info("【提现退款】-->没有手续费-->order_no:" + rwWithdraw.getOrder_no());
            }

            //倒序排列的最后一条流水就是需要加款的账户
            EaccAccountamtlist eaccAccountamtlist = new EaccAccountamtlist();
            eaccAccountamtlist.setTrans_serial(rwWithdraw.getTrans_serial());
            eaccAccountamtlist.setOrder_no(rwWithdraw.getOrder_no());
            eaccAccountamtlist.setPlat_no(rwWithdraw.getPlat_no());
            eaccAccountamtlist.setPlatcust(rwWithdraw.getPlat_no());
            eaccAccountamtlist.setSubject(Tsubject.CASH.getCode());
            eaccAccountamtlist.setSub_subject(rwWithdraw.getPay_code());
            eaccAccountamtlist.setOppo_plat_no(rwWithdraw.getPlat_no());
            eaccAccountamtlist.setOppo_platcust(rwWithdraw.getPlatcust());
            eaccAccountamtlist.setOppo_subject(Tsubject.CASH.getCode());
            eaccAccountamtlist.setOppo_sub_subject(rwWithdraw.getSub_subject());
            eaccAccountamtlist.setTrans_code(TransConsts.REFUND_CODE);
            eaccAccountamtlist.setTrans_name(TransConsts.REFUND_NAME);
            eaccAccountamtlist.setTrans_date(DateUtils.getyyyyMMddDate());
            eaccAccountamtlist.setTrans_time(DateUtils.format(new Date(), "HHmmss"));
            eaccAccountamtlist.setAmt_type(AmtType.INCOME.getCode());
            eaccAccountamtlist.setAmt(rwWithdraw.getOut_amt());
            accountTransferService.fundTransfer(eaccAccountamtlist);
            logger.info("【提现退款】-->中间户退款到用户成功-->order_no:" + rwWithdraw.getOrder_no());

            //修改提现状态
            rwWithdraw.setAcct_state(PayStatus.REFUNDPSUCCESS.getCode());
            String remark = String.format("【%s】", "退款成功");
            rwWithdraw.setRemark(remark + rwWithdraw.getRemark());
            try {
                rwWithdrawMapper.updateByPrimaryKeySelective(rwWithdraw);
            } catch (Exception e) {
                //更新失败，可能字段过长
                logger.info("【提现退款】-->修改提现状态异常-->异常-->order_no:" + rwWithdraw.getOrder_no(), e);
                baseResponse.setRecode(BusinessMsg.FAIL);
                baseResponse.setRemsg("【提现退款】-->修改提现状态异常");
                return baseResponse;
            }
            //修改业务流水
            TransTransreq transTransreq = transReqService.queryTransTransReqByOrderNoAndBatchOrderNo(rwWithdraw.getTrans_serial(), rwWithdraw.getOrder_no());
            if (transTransreq == null) {
                logger.info("【提现退款】-->查询不到订单-->order_no:" + rwWithdraw.getOrder_no());
                baseResponse.setRecode(BusinessMsg.FAIL);
                baseResponse.setRemsg("修改订单时查询不到订单");
                return baseResponse;
            }
            transTransreq.setStatus(FlowStatus.FAIL.getCode());
            transTransreq.setReturn_msg(remark + transTransreq.getReturn_msg());
            transReqService.insert(transTransreq);
            logger.info("【提现退款】-->修改订单成功-->order_no:" + rwWithdraw.getOrder_no());

            try {
                NotityPaymentReq notityPaymentReq = new NotityPaymentReq();
                notityPaymentReq.setMall_no(mall_no);
                notityPaymentReq.setPlat_no(rwWithdraw.getPlat_no());
                notityPaymentReq.setPlatcust(rwWithdraw.getPlatcust());
                notityPaymentReq.setOrder_no(rwWithdraw.getOrder_no());
                notityPaymentReq.setOrder_amt(rwWithdraw.getOut_amt());
                notityPaymentReq.setTrans_date(rwWithdraw.getTrans_date());
                notityPaymentReq.setTrans_time(rwWithdraw.getTrans_time());
                notityPaymentReq.setPay_finish_date(DateUtils.todayStr(DateUtils.DEF_FORMAT_NOTIME));
                notityPaymentReq.setPay_finish_time(DateUtils.todayStr("hhmmss"));
                notityPaymentReq.setPay_amt(rwWithdraw.getFee_amt().add(rwWithdraw.getOut_amt()));
                notityPaymentReq.setOrder_status(OrderStatus.FAIL.getCode());
                notityPaymentReq.setError_no(OrderStatus.REFUNDPSUCCESS.getCode());
                notityPaymentReq.setError_info(remark + transTransreq.getReturn_msg());
                NotifyData notifyData = new NotifyData();
                notifyData.setNotifyContent(JSONObject.toJSONString(notityPaymentReq));
                notifyData.setMall_no(mall_no);
                notifyData.setNotifyUrl(rwWithdraw.getNotify_url());
                notifyService.addNotify(notifyData);
                logger.info("【提现退款】-->发送异步通知-->order_no:" + rwWithdraw.getOrder_no());
            } catch (Exception e1) {
                logger.info("【提现退款】发送异步通知异常-->order_no:" + rwWithdraw.getOrder_no(), e1);
            }

        } catch (Exception e) {
            logger.info("【提现退款】-->退款是未知异常-->order_no:" + rwWithdraw.getOrder_no(), e);
            baseResponse.setRecode(BusinessMsg.FAIL);
            baseResponse.setRemsg("退款是未知异常");
            return baseResponse;
        }
        baseResponse.setRecode(BusinessMsg.SUCCESS);
        baseResponse.setRemsg("退款成功");
        return baseResponse;
    }

    @Override
    public List<RwWithdraw> queryAutoAddValueOrders(Integer queryLimit) throws BusinessException {
        Map<String,Object> queryParams=new HashMap<>();
        queryParams.put("status",PayStatus.CCB_SEND_SUCCESS.getCode());
        queryParams.put("limit",queryLimit);
        //查询100条数据
        return custRwWithdrawMapper.queryRwWithdrawByStatus(queryParams);
    }

    @Override
    public void doAutoAddValueOrders(RwWithdraw rwWithdraw) throws BusinessException {
        RwWithdrawThird rwWithdrawThird = getRwWithdrawThird(RwWithdrawType.AUTH_ADD_VALUE.getCode(), rwWithdraw.getOrder_no(), rwWithdraw.getPayee_name(), rwWithdraw.getOppo_account(), null);
        //设置redis，防止重复提现
        String withdrawRedisKey=Constants.WITHDRAW_AUTO_ADD_VALUE_LOCK_KEY + rwWithdrawThird.getThird_no();
        Boolean passport=CacheUtil.getCache().setnx(withdrawRedisKey,rwWithdraw.getPay_status());
        if(passport){
            //设置3小时超时时间
            CacheUtil.getCache().expire(withdrawRedisKey,3600 * 3);
            PlatPaycode platPaycode=accountSearchService.queryPlatPayCode(rwWithdraw.getPlat_no(),rwWithdraw.getPay_code());
            if(platPaycode==null){
                logger.info(String.format("【自动加值】渠道号不存在|plat_no:%s|pay_code:%s",rwWithdraw.getPlat_no(),rwWithdraw.getPay_code()));
                throw new BusinessException(BusinessMsg.NO_CHANNEL_INFORMATION,BusinessMsg.getMsg(BusinessMsg.NO_CHANNEL_INFORMATION));
            }
            PlatCardinfo platCardinfo = accountQueryService.getPlatcardinfo(rwWithdraw.getPlat_no(),PlatAccType.PLATTRUST.getCode());
            if(platCardinfo==null){
                logger.info(String.format("【自动加值】未找到该平台的存管户|plat_no:%s",rwWithdraw.getPlat_no()));
                throw new BusinessException(BusinessMsg.PARAMETER_ERROR,BusinessMsg.getMsg(BusinessMsg.PARAMETER_ERROR)+"：未找到该平台的存管户");
            }
            String payer_card_no = sysParameterService.querySysParameter(SysParamterKey.FTDM,SysParamterKey.PAY_ACCOUNT+platPaycode.getChannel_id());
            if(StringUtils.isBlank(payer_card_no)){
                logger.info(String.format("【自动加值】支付公司备付金账户未配置|pay_code:%s|channel_id:%s",
                        platPaycode.getPay_code(),platPaycode.getChannel_id()));
                throw new BusinessException(BusinessMsg.PARAMETER_ERROR,BusinessMsg.getMsg(BusinessMsg.PARAMETER_ERROR)+"：支付公司备付金账户未配置");
            }
            String[] payer_card_list=payer_card_no.split(",");
//            String payee_card_no=sysParameterService.querySysParameter(SysParamterKey.FTDM,SysParamterKey.PLAT_PAY_ACCOUNT+platPaycode.getChannel_id());
//            if(StringUtils.isBlank(payee_card_no)){
//                logger.info(String.format("【自动加值】平台在支付公司的商户号未配置|pay_code:%s|channel_id:%s",
//                        platPaycode.getPay_code(),platPaycode.getChannel_id()));
//                throw new BusinessException(BusinessMsg.PARAMETER_ERROR,BusinessMsg.getMsg(BusinessMsg.PARAMETER_ERROR)+"：支付公司备付金账户未配置");
//            }
            String notify_url=sysParameterService.querySysParameter(SysParamterKey.FTDM,SysParamterKey.AUTO_ADD_VALUE_NOTIFY_URL);
            //拼接自动加值参数
            Map<String,Object> params=new HashMap<>();
            params.put("partner_id",platPaycode.getPayment_plat_no());
            params.put("partner_serial_no",rwWithdrawThird.getThird_no());
            params.put("partner_trans_date",rwWithdraw.getTrans_date());
            params.put("partner_trans_time",rwWithdraw.getTrans_time());
            params.put("payee_card_no",payer_card_list[0]);//收款方账号(支付公司备付金账户)
            params.put("payer_card_no",platCardinfo.getCard_no());//付款方账号(存管户)
            params.put("payer_cust_name",platCardinfo.getCard_name());//付款方开户名（存管户户名）
            params.put("occur_balance",rwWithdraw.getOut_amt().setScale(2,BigDecimal.ROUND_DOWN));
            params.put("channelId",platPaycode.getChannel_id());
            params.put("notify_url",notify_url);
            params.put("host", sysParameterService.querySysParameter(platPaycode.getMall_no(), URLConfigUtil.EPAY_SERVER_KEY));
            params.put("url",sysParameterService.querySysParameter(platPaycode.getMall_no(), URLConfigUtil.AUTO_ADD_VALUE));
            //调用自动加值
            logger.info("【自动加值】请求三方参数："+ JSON.toJSONString(params));
            Map<String,Object> returnParams=adapterService.autoAddValue(params);
            logger.info("【自动加值】三方返回参数："+ JSON.toJSONString(returnParams));
            if(FlowStatus.SUCCESS.getCode().equals(returnParams.get("order_status"))){
                //同步返回成功
                try{
                    CacheUtil.getCache().set(withdrawRedisKey,PayStatus.CCB_ADD_SUCCESS,3600 * 3);
                }catch (Exception e){
                    logger.error("【自动加值】redis连接失败：",e);
                }
                rwWithdraw.setPay_status(PayStatus.CCB_ADD_SUCCESS.getCode());
                updateRwWithdrawThirdStatus(rwWithdrawThird.getOrder_no(), rwWithdrawThird.getThird_no(), OrderStatus.SUCCESS.getCode(), BusinessMsg.getMsg(BusinessMsg.SUCCESS));
            }else if(FlowStatus.INPROCESS.getCode().equals(returnParams.get("order_status"))){
                //同步返回处理中
                rwWithdraw.setPay_status(PayStatus.CCB_ADDING.getCode());
            }else if(FlowStatus.FAIL.getCode().equals(returnParams.get("order_status"))){
                //同步返回失败，不更改订单状态，可以重试
                logger.info("【自动加值】自动加值同步返回失败|recode:{}|remsg:{}",returnParams.get("recode"),returnParams.get("remsg"));
                //当失败处理
                rwWithdraw.setPay_status(PayStatus.FAIL_ZDJZ.getCode());
                rwWithdraw.setAcct_state(OrderStatus.REFUNDPROCESSING.getCode());
                TransTransreq transTransreq=transReqService.queryTransTransReqByOrderNoAndBatchOrderNo(rwWithdraw.getTrans_serial(),rwWithdraw.getOrder_no());
                transTransreq.setReturn_code(String.valueOf(returnParams.get("recode")));
                transTransreq.setReturn_msg("自动加值失败："+ String.valueOf(returnParams.get("remsg")));
                transTransreq.setStatus(FlowStatus.FAIL.getCode());
                transReqService.insert(transTransreq);
                CacheUtil.getCache().del(withdrawRedisKey);
                try{
                    //发送异步通知
                    NotityPaymentReq notityPaymentReq = new NotityPaymentReq();
                    notityPaymentReq.setMall_no(platPaycode.getMall_no());
                    notityPaymentReq.setPlat_no(rwWithdraw.getPlat_no());
                    notityPaymentReq.setPlatcust(rwWithdraw.getPlatcust());
                    notityPaymentReq.setOrder_no(rwWithdraw.getOrder_no());
                    notityPaymentReq.setOrder_amt(rwWithdraw.getOut_amt());
                    notityPaymentReq.setAdvance_amt(rwWithdraw.getAdvance_amt());
                    notityPaymentReq.setTrans_date(rwWithdraw.getTrans_date());
                    notityPaymentReq.setTrans_time(rwWithdraw.getTrans_time());
                    notityPaymentReq.setPay_order_no(rwWithdraw.getTrans_serial());
                    notityPaymentReq.setPay_finish_date(DateUtils.todayStr(DateUtils.DEF_FORMAT_NOTIME));
                    notityPaymentReq.setPay_finish_time(DateUtils.todayStr("hhmmss"));
                    if (rwWithdraw.getFee_amt() == null)
                        rwWithdraw.setFee_amt(BigDecimal.ZERO);
                    notityPaymentReq.setPay_amt(rwWithdraw.getFee_amt().add(rwWithdraw.getOut_amt()));
                    notityPaymentReq.setOrder_status(OrderStatus.FAIL.getCode());
                    notityPaymentReq.setError_no(OrderStatus.REFUNDPROCESSING.getCode());
                    notityPaymentReq.setError_info(OrderStatus.REFUNDPROCESSING.getMsg()+":"+transTransreq.getReturn_msg());

                    NotifyData notifyData = new NotifyData();
                    notifyData.setNotifyUrl(rwWithdraw.getNotify_url());
                    notifyData.setMall_no(platPaycode.getMall_no());
                    notifyData.setNotifyContent(JSONObject.toJSONString(notityPaymentReq));
                    notifyService.addNotify(notifyData);
                    //发送通知邮件
                    sendMsgService.sendMail(String.format("订单号为%s的提现自动加值失败，失败原因：%s，平台可重新发起提现确认。",
                            transTransreq.getOrder_no(),transTransreq.getReturn_msg()),"自动加值失败提醒");
                }catch (Exception e){
                    logger.info("【自动加值】发送提现失败异步通知失败：",e);
                }
                logger.info("【退款前先修改提现表中的acct_state为退款处理中】");
                updateRwWithdrawByIdAndAccStatus(rwWithdraw.getId(), OrderStatus.REFUNDPROCESSING.getCode());
                updateRwWithdrawThirdStatus(rwWithdrawThird.getOrder_no(), rwWithdrawThird.getThird_no(), OrderStatus.FAIL.getCode(), transTransreq.getReturn_msg());
            }
        }else{
            Object oldStatus=CacheUtil.getCache().get(withdrawRedisKey);
            if(!rwWithdraw.getPay_status().equals(oldStatus)){
                //同步redis中状态到数据库
                rwWithdraw.setPay_status(String.valueOf(oldStatus));
            }
        }
        if(!PayStatus.CCB_SEND_SUCCESS.getCode().equals(rwWithdraw.getPay_status())){
            updateRwWithdrawByIdAndStatus(rwWithdraw.getId(),PayStatus.CCB_SEND_SUCCESS.getCode(),rwWithdraw.getPay_status());
        }
    }

    @Override
    public List<RwWithdraw> queryAutoAddValueProcessingOrders(Integer queryLimit) throws BusinessException {
        Map<String,Object> queryParams=new HashMap<>();
        queryParams.put("status",PayStatus.CCB_ADDING.getCode());
        queryParams.put("limit",queryLimit);
        //查询100条数据
        return custRwWithdrawMapper.queryRwWithdrawByStatus(queryParams);
    }

    @Override
    public void doAutoAddValueProcessingOrders(RwWithdraw rwWithdraw) throws BusinessException {
        RwWithdrawThird rwWithdrawThird = queryRwWithdrawThridByOrderNo(rwWithdraw.getOrder_no());
        if(rwWithdrawThird == null){
            logger.info("【自动加值】根据处理中的状态查不到提现关联表信息或者查到多条，订单号：{}", rwWithdraw.getOrder_no());
            return;
        }
        //查询自动加值状态
        PlatPaycode platPaycode = accountSearchService.queryPlatPayCode(rwWithdraw.getPlat_no(),rwWithdraw.getPay_code());
        if(platPaycode==null){
            logger.info(String.format("【自动加值查询】渠道号不存在|plat_no:%s|pay_code:%s",rwWithdraw.getPlat_no(),rwWithdraw.getPay_code()));
            throw new BusinessException(BusinessMsg.NO_CHANNEL_INFORMATION,BusinessMsg.getMsg(BusinessMsg.NO_CHANNEL_INFORMATION));
        }
        //拼接自动加值参数
        Map<String,Object> params=new HashMap<>();
        params.put("partner_id",platPaycode.getPayment_plat_no());
        params.put("partner_serial_no",SeqUtil.getSeqNum());
        params.put("original_serial_no", rwWithdrawThird.getThird_no());
        params.put("occur_balance",rwWithdraw.getOut_amt());
        params.put("channelId",platPaycode.getChannel_id());
        params.put("host", sysParameterService.querySysParameter(platPaycode.getMall_no(), URLConfigUtil.EPAY_SERVER_KEY));
        params.put("url",sysParameterService.querySysParameter(platPaycode.getMall_no(), URLConfigUtil.AUTO_ADD_VALUE_QUERY));
        //调用自动加值
        logger.info("【自动加值查询】请求三方参数："+ JSON.toJSONString(params));
        Map<String,Object> returnParams=adapterService.queryAutoAddValue(params);
        logger.info("【自动加值查询】三方返回参数："+ JSON.toJSONString(returnParams));
        if(FlowStatus.SUCCESS.getCode().equals(returnParams.get("order_status"))){
            rwWithdraw.setPay_status(PayStatus.CCB_ADD_SUCCESS.getCode());
            updateRwWithdrawThirdStatus(rwWithdrawThird.getOrder_no(), rwWithdrawThird.getThird_no(), OrderStatus.SUCCESS.getCode(), BusinessMsg.getMsg(BusinessMsg.SUCCESS));
        }else if(FlowStatus.INPROCESS.getCode().equals(returnParams.get("order_status"))){
            logger.info(String.format("【自动加值查询】主动查询返回处理中，不处理|trans_serial:%s",rwWithdraw.getTrans_serial()));
        }else if(FlowStatus.FAIL.getCode().equals(returnParams.get("order_status"))){
            String withdrawRedisKey=Constants.WITHDRAW_AUTO_ADD_VALUE_LOCK_KEY +rwWithdraw.getTrans_serial();
            CacheUtil.getCache().del(withdrawRedisKey);
            rwWithdraw.setPay_status(PayStatus.FAIL_ZDJZ.getCode());
            rwWithdraw.setAcct_state(OrderStatus.REFUNDPROCESSING.getCode());
            logger.info("【自动加值查询】自动加值查询到失败|recode:{}|remsg:{}",returnParams.get("recode"),returnParams.get("remsg"));
            TransTransreq transTransreq=transReqService.queryTransTransReqByOrderNoAndBatchOrderNo(rwWithdraw.getTrans_serial(),rwWithdraw.getOrder_no());
            transTransreq.setReturn_code(String.valueOf(returnParams.get("recode")));
            transTransreq.setReturn_msg("自动加值失败："+String.valueOf(returnParams.get("remsg")));
            transTransreq.setStatus(FlowStatus.FAIL.getCode());
            transReqService.insert(transTransreq);
            try{
                //发送异步通知
                NotityPaymentReq notityPaymentReq = new NotityPaymentReq();
                notityPaymentReq.setMall_no(platPaycode.getMall_no());
                notityPaymentReq.setPlat_no(rwWithdraw.getPlat_no());
                notityPaymentReq.setPlatcust(rwWithdraw.getPlatcust());
                notityPaymentReq.setOrder_no(rwWithdraw.getOrder_no());
                notityPaymentReq.setOrder_amt(rwWithdraw.getOut_amt());
                notityPaymentReq.setAdvance_amt(rwWithdraw.getAdvance_amt());
                notityPaymentReq.setTrans_date(rwWithdraw.getTrans_date());
                notityPaymentReq.setTrans_time(rwWithdraw.getTrans_time());
                notityPaymentReq.setPay_order_no(rwWithdraw.getTrans_serial());
                notityPaymentReq.setPay_finish_date(DateUtils.todayStr(DateUtils.DEF_FORMAT_NOTIME));
                notityPaymentReq.setPay_finish_time(DateUtils.todayStr("hhmmss"));
                if (rwWithdraw.getFee_amt() == null)
                    rwWithdraw.setFee_amt(BigDecimal.ZERO);
                notityPaymentReq.setPay_amt(rwWithdraw.getFee_amt().add(rwWithdraw.getOut_amt()));
                notityPaymentReq.setOrder_status(OrderStatus.FAIL.getCode());
                notityPaymentReq.setError_no(OrderStatus.REFUNDPROCESSING.getCode());
                notityPaymentReq.setError_info(OrderStatus.REFUNDPROCESSING.getMsg()+":"+transTransreq.getReturn_msg());

                NotifyData notifyData = new NotifyData();
                notifyData.setNotifyUrl(rwWithdraw.getNotify_url());
                notifyData.setMall_no(platPaycode.getMall_no());
                notifyData.setNotifyContent(JSONObject.toJSONString(notityPaymentReq));
                notifyService.addNotify(notifyData);
                //发送通知邮件
                sendMsgService.sendMail(String.format("订单号为%s的提现自动加值查询到失败，失败原因：%s，平台可重新发起提现确认。",
                        transTransreq.getOrder_no(),transTransreq.getReturn_msg()),"自动加值失败提醒");
            }catch (Exception e){
                logger.info("【自动加值查询】发送提现失败异步通知失败：",e);
            }
            logger.info("【退款前先修改提现表中的acct_state为退款处理中】");
            updateRwWithdrawByIdAndAccStatus(rwWithdraw.getId(), OrderStatus.REFUNDPROCESSING.getCode());
            updateRwWithdrawThirdStatus(rwWithdrawThird.getOrder_no(), rwWithdrawThird.getThird_no(), OrderStatus.FAIL.getCode(), transTransreq.getReturn_msg());
        }
        //允许状态由自动加值处理中改为加值成功或代发成功（允许重试）
        if(!PayStatus.CCB_ADDING.getCode().equals(rwWithdraw.getPay_status())){
            updateRwWithdrawByIdAndStatus(rwWithdraw.getId(),PayStatus.CCB_ADDING.getCode(),rwWithdraw.getPay_status());
        }
    }

    @Override
    public Integer updateRwWithdrawByIdAndStatus(Integer id, String old_status, String status) throws BusinessException {
        Map<String,Object> params=new HashMap<>();
        params.put("id",id);
        params.put("old_status",old_status);
        params.put("pay_status",status);
        return custRwWithdrawMapper.updateStatusByIdAndStatus(params);
    }

    /**
     * CCB-状态33
     * 查询现在到一天前【待处理】的提现
     * @param withdrawNum 提现数量
     * @return
     * @throws BusinessException
     */
    @Override
    public List<RwWithdraw> queryRwWithdrawByWaitSend(int withdrawNum) throws BusinessException {

        logger.info("【批量提现】-->查询待处理的提现");
        List<RwWithdraw> rwWithdrawList = custRwWithdrawMapper.selectWaitSend(withdrawNum);
        if(rwWithdrawList.size() == 0){
            logger.info("【批量提现】-->没有待处理的提现");
            return null;
        }
        return rwWithdrawList;
    }

    /**
     * CCB-执行代发提现
     * @param task
     */
    @Override
    public void doRwWithdrawByWaitSend(RwWithdraw task) {
        logger.info("【代发提现】===开始代发==order_no:" + task.getOrder_no());
        RwWithdrawThird rwWithdrawThird = getRwWithdrawThird(RwWithdrawType.WAIT_SEND.getCode(), task.getOrder_no(), task.getPayee_name(), task.getOppo_account(), null);
        String key = Constants.WITHDRAW_SEND_VALUE_LOCK_KEY + rwWithdrawThird.getThird_no();
        Map<String, Object> params = new HashMap<>();
        String mall_no;
        try {
            mall_no = accountSearchService.queryMallNoByPlatNo(task.getPlat_no());
            Boolean flag = CacheUtil.getCache().setnx(key, task.getPay_status());
            if(flag) {
                CacheUtil.getCache().expire(key, 12 * 3600);
                try {
                    PlatPaycode platPaycode = userAccountService.queryPlatPaycode(task.getPlat_no(), task.getPay_code());
                    String payNoJson = platCacheService.queryCardInfoToCache(task.getPlat_no(), PlatAccType.PLATTRUST.getCode());
                    //支付公司卡号，卡名称
                    String[] payCardInfo = sysParameterService.querySysParameter(SysParamterKey.FTDM,
                            SysParamterKey.PAY_ACCOUNT + platPaycode.getChannel_id()).split(",");
                    JSONObject payNoJsonObj = JSON.parseObject(payNoJson);
                    List<BankPayData> payDataList = new ArrayList<>();
                    BankPayData bankPayData = new BankPayData();
                    bankPayData.setSeq_no(SeqUtil.getSeqNum());
                    bankPayData.setCard_no(payCardInfo[0]);
                    bankPayData.setCust_name(payCardInfo[1]);
                    bankPayData.setTran_amt(task.getOut_amt().toString());
                    //行号和名称
//                    bankPayData.setBank_code("");//跨行必传
//                    bankPayData.setBank_name("");//跨行必传
                    payDataList.add(bankPayData);
                    params.put("partner_id", "cg001");//合作商编号
                    params.put("channelId", "04491610");//通道ID
                    params.put("third_batch_no", rwWithdrawThird.getThird_no());//流水号
                    params.put("tran_type", "payReal");//交易类型-行内:pay 跨行:payReal
                    params.put("third_no", payNoJsonObj.get("card_no").toString());//协议号,出款帐号
                    params.put("notify_url", sysParameterService.querySysParameter(SysParamterKey.FTDM, URLConfigUtil.NOTIFY_URL_WITHDRAW_SEND_KEY));//异步通知地址
                    params.put("trandata", JSON.toJSONString(payDataList));//具体数据
                    params.put("cert_sign", "123");//签名串
                    params.put("host", sysParameterService.querySysParameter(mall_no, URLConfigUtil.EPAY_SERVER_KEY));
                    params.put("url", sysParameterService.querySysParameter(mall_no, URLConfigUtil.SEND_VALUE));
                    logger.info("【代发提现】请求三方参数：{}", JSON.toJSONString(params));
                }catch (Exception e){
                    logger.info("【代发提现】没有发送给三方出异常不做任何处理，流水号：" + task.getTrans_serial(), e);
                    return;
                }
                Map<String, Object> resultMap = adapterService.payFromCompanyToUser(params);
                if (resultMap == null || "".equals(resultMap.get("order_status"))) {
                    logger.info("【代发提现】返回数据为空或者recode为空");
                    throw new BusinessException(BusinessMsg.CALL_REMOTE_ERROR, BusinessMsg.getMsg(BusinessMsg.CALL_REMOTE_ERROR));
                }
                if(OrderStatus.FAIL.getCode().equals(resultMap.get("order_status"))){
                    logger.info("【代发提现】三方返回失败，修改提现状态为失败，流水号："+ rwWithdrawThird.getThird_no());
                    updateRwWithdrawThirdStatus(rwWithdrawThird.getOrder_no(), rwWithdrawThird.getThird_no(), OrderStatus.FAIL.getCode(), String.valueOf(resultMap.get("remsg")));
                    updateRwWithdrawByIdAndStatus(task.getId(), PayStatus.CCB_INIT_PAY.getCode(), PayStatus.FAIL.getCode());
                    logger.info("【代发提现】修改订单状态为失败");
                    TransTransreq transTransreq = transReqService.queryTransTransReqByOrderno(task.getOrder_no());
                    transTransreq.setStatus(OrderStatus.FAIL.getCode());
                    transTransreq.setReturn_code(BusinessMsg.FAIL);
                    transTransreq.setReturn_msg(String.valueOf(resultMap.get("remsg")));
                    transReqService.insert(transTransreq);
                    logger.info("【代发提现】修改退款状态acct_state为30，退款处理中");
                    RwWithdraw rwWithdraw = new RwWithdraw();
                    rwWithdraw.setId(task.getId());
                    rwWithdraw.setAcct_state(OrderStatus.REFUNDPROCESSING.getCode());
                    rwWithdrawMapper.updateByPrimaryKeySelective(rwWithdraw);
                    logger.info("【代发提现】添加异步通知队列");
                    NotityPaymentReq notityPaymentReq = new NotityPaymentReq();
                    notityPaymentReq.setMall_no(mall_no);
                    notityPaymentReq.setPlat_no(task.getPlat_no());
                    notityPaymentReq.setPlatcust(task.getPlatcust());
                    notityPaymentReq.setOrder_no(task.getOrder_no());
                    notityPaymentReq.setOrder_amt(task.getOut_amt());
                    notityPaymentReq.setTrans_date(task.getTrans_date());
                    notityPaymentReq.setTrans_time(task.getTrans_time());
                    notityPaymentReq.setPay_finish_date(DateUtils.todayStr(DateUtils.DEF_FORMAT_NOTIME));
                    notityPaymentReq.setPay_finish_time(DateUtils.todayStr("hhmmss"));
                    notityPaymentReq.setPay_amt(task.getFee_amt().add(task.getOut_amt()));
                    notityPaymentReq.setOrder_status(OrderStatus.FAIL.getCode());
                    notityPaymentReq.setError_no(OrderStatus.REFUNDPROCESSING.getCode());
                    notityPaymentReq.setError_info(resultMap.get("remsg").toString());
                    NotifyData notifyData = new NotifyData();
                    notifyData.setNotifyContent(JSONObject.toJSONString(notityPaymentReq));
                    notifyData.setMall_no(mall_no);
                    notifyData.setNotifyUrl(task.getNotify_url());
                    notifyService.addNotify(notifyData);
                }else {
                    logger.info("【代发提现】三方返回不是失败，修改提现状态为代发处理中");
                    updateRwWithdrawByIdAndStatus(task.getId(), PayStatus.CCB_INIT_PAY.getCode(), PayStatus.CCB_SENDING.getCode());
                }
            }else {
                logger.info("【代发提现】并发，不做任何处理，流水号：" + task.getTrans_serial());
                return;
            }
        }catch (BusinessException e){
            logger.info("【代发提现】异常,修改订单状态为代发处理中，流水号：" + task.getTrans_serial(), e);
            updateRwWithdrawByIdAndStatus(task.getId(), PayStatus.CCB_INIT_PAY.getCode(), PayStatus.CCB_SENDING.getCode());
        }finally {
            CacheUtil.getCache().del(key);
        }
    }

    /**
     * 提现代发异步通知
     * @param map
     * @return
     */
    @Override
    public BaseResponse withdrawSendAsyn(Map<String, Object> map) {
        BaseResponse baseResponse = new BaseResponse();
        String thirdNo = map.get("third_batch_no").toString();
        RwWithdrawThird rwWithdrawThird = queryRwWithdrawThridByThirdNo(thirdNo);
        if(rwWithdrawThird == null){
            logger.info("【提现代发异步通知】查询不到该笔提现发给支付的关联表，流水号：" + thirdNo);
            baseResponse.setRecode(BusinessMsg.ORIGINAL_ORDER_NOEXISTENT);
            baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.ORIGINAL_ORDER_NOEXISTENT));
            return baseResponse;
        }
        List<BankPayPayNotifyData> bankPayPayNotifyDataList = JSON.parseArray(map.get("trandata").toString(),BankPayPayNotifyData.class);
        if(bankPayPayNotifyDataList == null || bankPayPayNotifyDataList.size() == 0){
            baseResponse.setRecode(BusinessMsg.PARAMETER_LACK);
            baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.PARAMETER_LACK));
            return baseResponse;
        }
        Map<String,Object> tranDataMap = BeanUtil.transBean2Map(bankPayPayNotifyDataList.get(0));
        String amt = tranDataMap.get("tran_amt").toString();
        String status = tranDataMap.get("status").toString();
        String fail_reason = tranDataMap.get("fail_reason").toString();
        RwWithdrawExample example = new RwWithdrawExample();
        RwWithdrawExample.Criteria criteria = example.createCriteria();
        criteria.andOrder_noEqualTo(rwWithdrawThird.getOrder_no());
        List<RwWithdraw> rwWithdraws = rwWithdrawMapper.selectByExample(example);
        if(rwWithdraws.size() == 0){
            logger.info("【提现代发异步通知】查询不到该笔提现，订单号：" + rwWithdrawThird.getOrder_no());
            baseResponse.setRecode(BusinessMsg.ORIGINAL_ORDER_NOEXISTENT);
            baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.ORIGINAL_ORDER_NOEXISTENT));
            return baseResponse;
        }
        RwWithdraw rwWithdraw = rwWithdraws.get(0);
        BigDecimal bd = new BigDecimal(amt);
        if(rwWithdraw.getOut_amt().compareTo(bd) != 0){
            baseResponse.setRecode(BusinessMsg.MONEY_ERROR);
            baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.MONEY_ERROR));
            return baseResponse;
        }
        if(PayStatus.CCB_SEND_SUCCESS.getCode().equals(rwWithdraw.getPay_status())){
            logger.info("【提现代发异步通知】该笔提现状态已经是代发成功，无需继续，流水号：" + rwWithdraw.getTrans_serial());
            baseResponse.setRecode(BusinessMsg.SUCCESS);
            baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));
            return baseResponse;
        }
        if("Y".equals(status)){
            logger.info("【提现代发异步通知】返回成功，修改提现状态为代发成功。订单号：" + rwWithdrawThird.getOrder_no());
            Integer count = updateRwWithdrawByIdAndStatus(rwWithdraw.getId(), PayStatus.CCB_SENDING.getCode(), PayStatus.CCB_SEND_SUCCESS.getCode());
            if(count == 0){
                //异步比同步快
                updateRwWithdrawByIdAndStatus(rwWithdraw.getId(), PayStatus.CCB_INIT_PAY.getCode(), PayStatus.CCB_SEND_SUCCESS.getCode());
            }
            updateRwWithdrawThirdStatus(rwWithdrawThird.getOrder_no(), rwWithdrawThird.getThird_no(), OrderStatus.SUCCESS.getCode(), BusinessMsg.getMsg(BusinessMsg.SUCCESS));
        }
        if("N".equals(status)){
            logger.info("【提现代发异步通知】返回失败，提现状态改为失败，并且给平台发送异步通知。订单号：" + rwWithdrawThird.getOrder_no());
            Integer count = updateRwWithdrawByIdAndStatus(rwWithdraw.getId(), PayStatus.CCB_SENDING.getCode(), PayStatus.FAIL_DF.getCode());
            if(count == 0 ){
                //异步比同步快
                updateRwWithdrawByIdAndStatus(rwWithdraw.getId(), PayStatus.CCB_INIT_PAY.getCode(), PayStatus.FAIL_DF.getCode());
            }
            updateRwWithdrawThirdStatus(rwWithdrawThird.getOrder_no(), rwWithdrawThird.getThird_no(), OrderStatus.FAIL.getCode(), fail_reason);
            logger.info("【退款前先修改提现表中的acct_state为退款处理中】");
            updateRwWithdrawByIdAndAccStatus(rwWithdraw.getId(), OrderStatus.REFUNDPROCESSING.getCode());
            TransTransreq transTransreq = transReqService.queryTransTransReqByOrderno(rwWithdraw.getOrder_no());
            logger.info("【行内代发的通知或发起查询】交易失败已扣款-->order_no:"+rwWithdraw.getOrder_no());

            transTransreq.setReturn_code(BusinessMsg.FAIL);
            transTransreq.setStatus(OrderStatus.FAIL.getCode());
            transTransreq.setReturn_msg("需管理台触发退款," + OrderStatus.REFUNDPROCESSING.getMsg() + "," + fail_reason);
            transTransreq.setUpdate_time(new Date());
            logger.info("【行内代发的通知或发起查询】更新数据库流水表-->order_no:"+rwWithdraw.getOrder_no());
            transReqService.insert(transTransreq);
            logger.info("【行内代发的通知或发起查询】更新数据库流水表成功-->order_no:"+rwWithdraw.getOrder_no());

            try {
                String mall_no = accountSearchService.queryMallNoByPlatNo(rwWithdraw.getPlat_no());
                NotityPaymentReq notityPaymentReq = new NotityPaymentReq();
                notityPaymentReq.setMall_no(mall_no);
                notityPaymentReq.setPlat_no(rwWithdraw.getPlat_no());
                notityPaymentReq.setPlatcust(rwWithdraw.getPlatcust());
                notityPaymentReq.setOrder_no(rwWithdraw.getOrder_no());
                notityPaymentReq.setOrder_amt(rwWithdraw.getOut_amt());
                notityPaymentReq.setAdvance_amt(rwWithdraw.getAdvance_amt());
                notityPaymentReq.setTrans_date(rwWithdraw.getTrans_date());
                notityPaymentReq.setTrans_time(rwWithdraw.getTrans_time());
                notityPaymentReq.setPay_order_no(rwWithdraw.getTrans_serial());
                notityPaymentReq.setPay_finish_date(DateUtils.todayStr(DateUtils.DEF_FORMAT_NOTIME));
                notityPaymentReq.setPay_finish_time(DateUtils.todayStr("hhmmss"));

                if (rwWithdraw.getFee_amt() == null)
                    rwWithdraw.setFee_amt(BigDecimal.ZERO);

                notityPaymentReq.setPay_amt(rwWithdraw.getFee_amt().add(rwWithdraw.getOut_amt()));
                notityPaymentReq.setOrder_status(OrderStatus.REFUNDPROCESSING.getCode());
                notityPaymentReq.setError_no(OrderStatus.REFUNDPROCESSING.getCode());
                notityPaymentReq.setError_info("交易失败，" + OrderStatus.REFUNDPROCESSING.getCode() + "," + fail_reason);

                NotifyData notifyData = new NotifyData();
                notifyData.setNotifyUrl(rwWithdraw.getNotify_url());
                notifyData.setMall_no(mall_no);
                notifyData.setNotifyContent(JSONObject.toJSONString(notityPaymentReq));
                notifyService.addNotify(notifyData);
                logger.info("【行内代发的通知或发起查询】添加异步通知队列成功-->通知数据：" + notifyData.toString()+"-->order_no:"+rwWithdraw.getOrder_no());
            }catch (Exception e){
                logger.info("【行内代发的通知或发起查询】添加异步通知队列失败-->order_no:"+rwWithdraw.getOrder_no());
            }
        }
        baseResponse.setRecode(BusinessMsg.SUCCESS);
        baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));
        return baseResponse;
    }

    @Override
    public RwWithdraw getRwWithdrawByTransSerialAndStatus(String trans_serial, String pay_status) throws BusinessException {
        RwWithdrawExample rwWithdrawExample=new RwWithdrawExample();
        RwWithdrawExample.Criteria criteria=rwWithdrawExample.createCriteria();
        if(StringUtils.isNotBlank(trans_serial)){
            criteria.andTrans_serialEqualTo(trans_serial);
        }
        if(StringUtils.isNotBlank(pay_status)){
            criteria.andPay_statusEqualTo(pay_status);
        }
        criteria.andEnabledEqualTo(Constants.ENABLED);
        List<RwWithdraw> rwWithdrawList=rwWithdrawMapper.selectByExample(rwWithdrawExample);
        if(rwWithdrawList.size()>0){
            return rwWithdrawList.get(0);
        }
        return null;
    }

    @Override
    public void batchWithdrawTrue(BatchWithdrawRequest batchWithdrawRequest, String remsg) {

    }

    @Override
    public RwWithdrawThird getRwWithdrawThird(String type, String orderNo, String name, String cardNo, String thirdNo) {
        RwWithdrawThird rwWithdrawThird = new RwWithdrawThird();
        rwWithdrawThird.setType(type);
        rwWithdrawThird.setOrder_no(orderNo);
        if(StringUtils.isNotBlank(name)) {
            rwWithdrawThird.setName(name);
        }
        if(StringUtils.isNotBlank(cardNo)){
            rwWithdrawThird.setCard_no(cardNo);
        }
        rwWithdrawThird.setCard_no(cardNo);
        if(StringUtils.isEmpty(thirdNo)) {
            rwWithdrawThird.setThird_no(SeqUtil.getSeqNum());
        }else {
            rwWithdrawThird.setThird_no(thirdNo);
        }
        rwWithdrawThird.setStatus(OrderStatus.PROCESSING.getCode());
        rwWithdrawThird.setCreate_time(new Date());
        rwWithdrawThirdMapper.insert(rwWithdrawThird);
        return rwWithdrawThird;
    }

    @Override
    public void updateRwWithdrawThirdStatus(String orderNo, String thirdNo, String status, String remark) {
        RwWithdrawThird rwWithdrawThird = new RwWithdrawThird();
        rwWithdrawThird.setStatus(status);
        if(StringUtils.isNoneEmpty(remark)){
            rwWithdrawThird.setRemark(remark);
        }
        rwWithdrawThird.setUpdate_time(new Date());
        RwWithdrawThirdExample example = new RwWithdrawThirdExample();
        RwWithdrawThirdExample.Criteria criteria = example.createCriteria();
        criteria.andStatusEqualTo(OrderStatus.PROCESSING.getCode());
        criteria.andOrder_noEqualTo(orderNo);
        criteria.andThird_noEqualTo(thirdNo);
        rwWithdrawThirdMapper.updateByExampleSelective(rwWithdrawThird, example);
    }

    @Override
    public RwWithdrawThird queryRwWithdrawThridByThirdNo(String thirdNo) {
        RwWithdrawThirdExample example = new RwWithdrawThirdExample();
        RwWithdrawThirdExample.Criteria criteria = example.createCriteria();
        criteria.andThird_noEqualTo(thirdNo);
        List<RwWithdrawThird> withdrawThirds = rwWithdrawThirdMapper.selectByExample(example);
        if(withdrawThirds.size() > 0){
            return withdrawThirds.get(0);
        }
        return null;
    }

    @Override
    public RwWithdrawThird queryRwWithdrawThridByOrderNo(String orderNo) {
        RwWithdrawThirdExample example = new RwWithdrawThirdExample();
        RwWithdrawThirdExample.Criteria criteria = example.createCriteria();
        criteria.andOrder_noEqualTo(orderNo);
        criteria.andStatusEqualTo(OrderStatus.PROCESSING.getCode());
        List<RwWithdrawThird> withdrawThirds = rwWithdrawThirdMapper.selectByExample(example);
        if(withdrawThirds.size() == 1){
            return withdrawThirds.get(0);
        }
        return null;
    }

    @Override
    public List<RwWithdrawThird> queryRwWithdrawThrid(int catchNum) {
        RwWithdrawThirdExample example = new RwWithdrawThirdExample();
        RwWithdrawThirdExample.Criteria criteria = example.createCriteria();
        criteria.andTypeEqualTo(RwWithdrawType.WAIT_PAY_PLAT.getCode());
        Date date = new Date();
        criteria.andCreate_timeBetween(DateUtils.beforeDate(date, 2), date);
        List<RwWithdrawThird> list = rwWithdrawThirdMapper.selectByExample(example);
        return list;
    }

    @Override
    public void queryPayToPlatStatus(RwWithdrawThird task) {
        RwWithdrawExample example = new RwWithdrawExample();
        RwWithdrawExample.Criteria criteria = example.createCriteria();
        criteria.andOrder_noEqualTo(task.getOrder_no());
        List<RwWithdraw> list = rwWithdrawMapper.selectByExample(example);
        if(list.size() != 1){
            logger.info("【查询代付到平台的状态】根据订单号查询提现表的数量不为1");
            throw new BusinessException(BusinessMsg.FAIL, "根据订单号查询提现表的数量不为1，订单号：" + task.getOrder_no());
        }
        logger.info("【查询代付到平台的状态】开始查询");
        doThirdpartyQueryRwWithdraw(list.get(0));
    }

    @Transactional
    @Override
    public BaseResponse payToPlat(RwWithdraw rwWithdraw) {
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setRecode(BusinessMsg.SUCCESS);
        baseResponse.setRemsg("回拨退款已成功发起，请耐心等待结果");
        //获取平台卡信息
        String mall_no = accountSearchService.queryMallNoByPlatNo(rwWithdraw.getPlat_no());
        String platCardInfo = sysParameterService.querySysParameter(mall_no, URLConfigUtil.PLAT_CARD_INFO);
        String[] platCardInfos = platCardInfo.split(",");
        if(platCardInfos.length != 6){
            baseResponse.setRecode(BusinessMsg.FAIL);
            baseResponse.setRemsg("平台卡信息存入有误，请查看plat_card_info");
            return baseResponse;
        }
        rwWithdraw.setId(null);
        rwWithdraw.setTrans_code(TransConsts.WITHDRAW_TO_PLAT_CODE);
        rwWithdraw.setTrans_name(TransConsts.WITHDRAW_TO_PLAT_NAME);
        rwWithdraw.setCreate_time(new Date());
        rwWithdraw.setUpdate_time(new Date());
        rwWithdraw.setTrans_serial(SeqUtil.getSeqNum());
        rwWithdraw.setNotify_url(null);
        rwWithdraw.setPayee_name(platCardInfos[0]);
        rwWithdraw.setOppo_account(platCardInfos[1]);
        rwWithdraw.setCity_code(platCardInfos[2]);
        rwWithdraw.setBrabank_name(platCardInfos[4]);
        rwWithdraw.setBank_id(platCardInfos[5]);
        rwWithdraw.setRemark("提现到存管总户");
        rwWithdrawMapper.insert(rwWithdraw);
        if (PayStatus.FAIL.getCode().equals(rwWithdraw.getPay_status())
                && OrderStatus.REFUNDPROCESSING.getCode().equals(rwWithdraw.getAcct_state())) {
            logger.info("【回拨退款】修改提现状态为回拨退款中");
            updateRwWithdrawByIdAndAccStatus(rwWithdraw.getId(), OrderStatus.PAY_TO_PLAT_PROCESSING.getCode());
            logger.info("【开始代付给平台】");
            PlatPaycode platPayCode = accountSearchService.queryPlatPayCode(rwWithdraw.getPlat_no(), rwWithdraw.getPay_code());
            Map<String, Object> data = new HashMap<>();
            data.put("partner_id", platPayCode.getPayment_plat_no());//合作商编号
            data.put("partner_serial_no", rwWithdraw.getTrans_serial());//合作商流水，即批次号
            data.put("partner_trans_date", rwWithdraw.getTrans_date());//合作商交易日期
            data.put("partner_trans_time", rwWithdraw.getTrans_time());//合作商交易时间
            data.put("channelId", platPayCode.getChannel_id());//渠道编号
            data.put("occur_balance", rwWithdraw.getOut_amt().setScale(2,BigDecimal.ROUND_DOWN));//金额
            data.put("summary",rwWithdraw.getRemark());//摘要
            data.put("memo",rwWithdraw.getRemark());
            data.put("province_code","1");
            data.put("client_name", platCardInfos[0]);//客户姓名
            data.put("card_no", platCardInfos[1]);//银行卡号
            data.put("city_code", platCardInfos[2]);//城市编码
//            data.put("id_no",platCardInfos[3]);//身份证 -- id_no不能传
            data.put("brabank_name", platCardInfos[4]);//开户行
            data.put("bank_id", platCardInfos[5]);//开户行号（总行）
//            data.put("client_property","0");//对私
            data.put("client_property","1");//对公
            //获取回调URL
            String return_ip = sysParameterService.querySysParameter(SysParamterKey.FTDM, URLConfigUtil.WEB_LOCAL_SERVER);
            String return_url = sysParameterService.querySysParameter(SysParamterKey.FTDM, URLConfigUtil.PAYPLAT_SINGLE_PAY_RETURN);
            if(StringUtils.isEmpty(return_ip)){
                logger.info("【批量提现】-->回调ip不存在-->order_no:" + rwWithdraw.getOrder_no());
                throw new BusinessException(BusinessMsg.FAIL,"代付异常,回调ip不存在");
            }
            if(StringUtils.isEmpty(return_url)){
                logger.info("【批量提现】-->回调url不存在-->order_no:" + rwWithdraw.getOrder_no());
                throw new BusinessException(BusinessMsg.FAIL,"代付异常,回调url不存在");
            }
            data.put("receive_url", return_ip + return_url);//后台回调地址
            data.put("func_code","0");
            String Base = sysParameterService.querySysParameter(mall_no, URLConfigUtil.EPAY_SERVER_KEY);
            logger.info("【批量提现】-->【单笔代付准备发送前】-->获取第三方服务请求-->order_no:" + rwWithdraw.getOrder_no());
            String URL = sysParameterService.querySysParameter(mall_no, URLConfigUtil.PAYPLAT_SINGLE_PAY);
            if("229".equals(platPayCode.getChannel_id())){
                URL=sysParameterService.querySysParameter(mall_no, URLConfigUtil.PAYPLAT_SINGLE_PAY_LL);
            }
            if(StringUtils.isEmpty(Base)){
                logger.info("请求ip不存在-->order_no:" + rwWithdraw.getOrder_no());
                throw new BusinessException(BusinessMsg.FAIL,"代付异常,请求ip不存在");
            }
            if(StringUtils.isEmpty(URL)){
                logger.info("请求url不存在-->order_no:" + rwWithdraw.getOrder_no());
                throw new BusinessException(BusinessMsg.FAIL,"代付异常,请求url不存在");
            }
            data.put("host", Base);
            data.put("url", URL);
            Map<String, Object> map = adapterService.withdrawInBatch(data);
            logger.info("【单笔代付发送成功】-->返回:" + map.toString()+"-->order_no:" + rwWithdraw.getOrder_no());
            if(map.get("order_status") != null && String.valueOf(map.get("order_status")).equals(PayStatus.FAIL.getCode())){
            }
        }else {
            baseResponse.setRecode(BusinessMsg.FAIL);
            baseResponse.setRemsg("提现状态必须是提现失败，退款处理中才能回拨退款");
            return baseResponse;
        }
        return baseResponse;
    }

    /**
     * 查询现在到一天前【待处理】的提现
     * @author pzy
     * @param withdrawNum 参数
     * @throws BusinessException
     */
    @Override
    public List<RwWithdraw> queryRwWithdrawByWaitPay(int withdrawNum) throws BusinessException {
        Map<String,Object> queryParams=new HashMap<>();
        queryParams.put("status",PayStatus.CCB_ADD_SUCCESS.getCode());
        queryParams.put("limit",withdrawNum);
        List<RwWithdraw> rwWithdrawList = custRwWithdrawMapper.queryRwWithdrawByStatus(queryParams);
        return rwWithdrawList;
    }

    /**
     * 查询一个小时之前到10天前【处理中】的提现
     * @author pzy
     * @param withdrawNum 参数
     * @throws BusinessException
     */
    @Override
    public List<RwWithdraw> queryRwWithdrawByPaying(int withdrawNum) throws BusinessException {
        logger.info("【批量提现】-->查询处理中的提现");
        Map<String,Object> queryParams=new HashMap<>();
        queryParams.put("status",PayStatus.CCB_PAYING.getCode());
        queryParams.put("limit",withdrawNum);
        List<RwWithdraw> rwWithdrawList = custRwWithdrawMapper.queryRwWithdrawByStatus(queryParams);
        if(rwWithdrawList.size() == 0){
            logger.info("【批量提现】-->没有处理中的提现");
            return null;
        }
        return rwWithdrawList;
    }

    /**
     * 查询处理中的提现发往代付查询具体结果
     * @author pzy
     * @param rwWithdraw 参数
     * @throws BusinessException
     */
    @Override
    public boolean doThirdpartyQueryRwWithdraw(RwWithdraw rwWithdraw) throws BusinessException {
        RwWithdrawThird rwWithdrawThird = queryRwWithdrawThridByOrderNo(rwWithdraw.getOrder_no());
        if(rwWithdrawThird == null){
            logger.info("【行内代付】根据处理中的状态查不到提现关联表信息或者查到多条，订单号：{}", rwWithdraw.getOrder_no());
            return false;
        }
        logger.info("【行内代付订单状态查询】从redis中获取支付接口URL-->order_no:"+rwWithdraw.getOrder_no());

        //查询plat_paycode
        PlatPaycode platPayCode = accountSearchService.queryPlatPayCode(rwWithdraw.getPlat_no(), rwWithdraw.getPay_code());

        String mall_no = accountSearchService.queryMallNoByPlatNo(rwWithdraw.getPlat_no());

        //获取请求URL
        logger.info("【行内代付订单状态查询】从redis中获取支付接口URL-->order_no:"+rwWithdraw.getOrder_no()+"，mall_no:"+mall_no);
        logger.info("【行内代付订单状态查询】获取代付系统查询ip-->order_no:"+rwWithdraw.getOrder_no());
        String Base = sysParameterService.querySysParameter(mall_no, URLConfigUtil.EPAY_SERVER_KEY);
        logger.info("【行内代付订单状态查询】获取代付系统服务地址请求-->order_no:"+rwWithdraw.getOrder_no());
        String URL = sysParameterService.querySysParameter(mall_no, URLConfigUtil.PAYPLAT_SINGLE_PAY_QUERY);

        Map<String, Object> data = new HashMap<>();
        data.put("partner_id", platPayCode.getPayment_plat_no());//合作商编号
        data.put("partner_serial_no", SeqUtil.getSeqNum());//合作商流水，代表本次查询动作
        data.put("original_serial_no", rwWithdrawThird.getThird_no());//原合作商流水号
        String occur_balance = rwWithdraw.getOut_amt().toString();
        data.put("occur_balance", occur_balance.substring(0, occur_balance.length() - 2));
        data.put("cert_sign", "sunyard");
        data.put("host", Base);
        data.put("url", URL);
        data.put("req_time", DateUtils.todayStr());

        Map<String, Object> map = adapterService.queryWithdraw(data);
        logger.info("【行内代付订单状态查询】-->代付系统返回:"+map.toString()+"-->order_no:"+rwWithdraw.getOrder_no());
        if(map.get("pay_status") != null && !String.valueOf(map.get("pay_status")).equals(OrderStatus.PROCESSING.getCode())){

            NotifyEpayAgentPayRealReturnData notifyEpayAgentPayRealReturnData = new NotifyEpayAgentPayRealReturnData();
            notifyEpayAgentPayRealReturnData.setPay_status(String.valueOf(map.get("pay_status")));
            notifyEpayAgentPayRealReturnData.setPartner_serial_no(rwWithdrawThird.getThird_no());
            notifyEpayAgentPayRealReturnData.setPay_finish_date(String.valueOf(map.get("pay_check_date")));

            if(map.get("remsg") != null)
                notifyEpayAgentPayRealReturnData.setFail_cause(String.valueOf(map.get("remsg")));

            doRwWithdrawNotify(notifyEpayAgentPayRealReturnData);
            if(RwWithdrawType.WAIT_PAY_PLAT.getCode().equals(rwWithdrawThird.getType())){
                if(PayStatus.SUCCESS.getCode().equals(notifyEpayAgentPayRealReturnData.getPay_status())){
                    logger.info("【检查是否可以退款】");
                    if(OrderStatus.PAY_TO_PLAT_PROCESSING.getCode().equals(rwWithdraw.getAcct_state())){
                        logger.info("【开始执行退款】");
                        doAutomaticRefund(rwWithdraw);
                    }
                }else if(PayStatus.FAIL.getCode().equals(notifyEpayAgentPayRealReturnData.getPay_status())){
                    logger.info("【退款前先修改提现表中的acct_state为退款处理中】");
                    updateRwWithdrawByIdAndAccStatus(rwWithdraw.getId(), OrderStatus.REFUNDPROCESSING.getCode());
                }
            }
        }
        return true;
    }


    public static void main(String[] args) {
        Map<String, Object> map = new HashMap<>();
        map.put("data","{}");
        RefundPayResponseData data = JSON.parseObject(map.get("data").toString(),RefundPayResponseData.class);
        if(null != data){
            System.out.print(data.toString());
        }
    }


}
