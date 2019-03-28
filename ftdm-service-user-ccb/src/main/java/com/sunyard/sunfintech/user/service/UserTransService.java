package com.sunyard.sunfintech.user.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sunyard.sunfintech.account.model.bo.BatchPayDetail;
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
import com.sunyard.sunfintech.custdao.mapper.CustRwRechargeMapper;
import com.sunyard.sunfintech.custdao.mapper.CustTransTransreqMapper;
import com.sunyard.sunfintech.dao.entity.*;
import com.sunyard.sunfintech.dao.mapper.PlatPaycodeMapper;
import com.sunyard.sunfintech.dao.mapper.RwRechargeMapper;
import com.sunyard.sunfintech.dao.mapper.RwWithdrawMapper;
import com.sunyard.sunfintech.pub.model.NotifyData;
import com.sunyard.sunfintech.pub.provider.INotifyService;
import com.sunyard.sunfintech.pub.provider.ISysParameterService;
import com.sunyard.sunfintech.pub.provider.ITransReqService;
import com.sunyard.sunfintech.thirdparty.provider.IAdapterService;
import com.sunyard.sunfintech.user.model.bo.*;
import com.sunyard.sunfintech.user.provider.*;
import org.apache.commons.collections.MapUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;


/**
 * @author wml
 * <p>
 * 2018年1月06日
 */
@Service(interfaceClass = IUserTransService.class)
@CacheConfig(cacheNames = "userTransService")
@org.springframework.stereotype.Service("userTransService")
public class UserTransService extends BaseServiceSimple implements IUserTransService {
    private final Logger logger = LogManager.getLogger(this.getClass());
    @Autowired
    private ITransReqService transReqService;

    @Autowired
    private IAccountSearchService accountSearchService;

    @Autowired
    private IUserAccountService userAccountService;

    @Autowired
    private RwRechargeMapper rwRechargeMapper;

    @Autowired
    private IAdapterService adapterService;

    @Autowired
    private ISysParameterService sysParameterService;

    @Autowired
    private IRechargeOptionService rechargeOptionService;

    @Autowired
    private IAccountQueryService accountQueryService;

    @Autowired
    private CustRwRechargeMapper custRwRechargeMapper;

    @Autowired
    private PlatPaycodeMapper platPaycodeMapper;

    @Autowired
    private IPlatCacheService platCacheService;

    @Autowired
    private RwWithdrawMapper rwWithdrawMapper;

    @Autowired
    private INotifyService notifyService;
    @Autowired
    private CustTransTransreqMapper custTransTransreqMapper;

    /*@Autowired
    private IAccountTransferService newAccountTransferService;*/

    @Autowired
    private IAccountAssetService accountAssetService;

    @Autowired
    private IAccountTransferService accountTransferService;

    @Override
    public ApplyQuickPayResponse applyQuickPay(ApplyQuickPayRequest applyQuickPayRequest) throws BusinessException {
        RwRecharge rwRecharge = new RwRecharge();
        ApplyBaseResponse applyBaseResponse = new ApplyBaseResponse();
        ApplyQuickPayResponse applyQuickPayResponse = new ApplyQuickPayResponse();
        //根据订单查询是否为原单重试
        TransTransreq transTransReq = new TransTransreq();
        try{
            transTransReq=transReqService.queryTransTransReqByOrderno(applyQuickPayRequest.getOrder_no());
        }catch (BusinessException ex){
            // 查不到订单时候
            transTransReq =null;
        }
        boolean is_sameOrderNo=false;
        if(null != transTransReq ){
            logger.info("【快捷充值申请】原订单流水："+JSON.toJSON(transTransReq));
            RwRechargeExample example=new RwRechargeExample();
            RwRechargeExample.Criteria criteria=example.createCriteria();
            criteria.andPlat_noEqualTo(applyQuickPayRequest.getMer_no());
            criteria.andOrder_noEqualTo(applyQuickPayRequest.getOrder_no());
            criteria.andEnabledEqualTo(Constants.ENABLED);
            List<RwRecharge> rwRechargeList= rwRechargeMapper.selectByExample(example);
            if(rwRechargeList.size()>1){
                logger.info("【快捷充值申请】数据库rw_recharge有多条相同申请订单，order_no:"+applyQuickPayRequest.getOrder_no());
                throw new BusinessException(BusinessMsg.DATEBASE_EXCEPTION, BusinessMsg.getMsg(BusinessMsg.DATEBASE_EXCEPTION) + "，数据库有重复的订单号");
            }else if(rwRechargeList.size() == 1){
                rwRecharge=rwRechargeList.get(0);
                logger.info("【快捷充值申请】原充值记录："+JSON.toJSON(rwRecharge));
                //如果原单非处理中，则不允许原单重试
                if(!OrderStatus.PROCESSING.getCode().equals(rwRecharge.getStatus()) && !OrderStatus.REQUESTSUCCESS.getCode().equals(rwRecharge.getStatus())){
                    throw new BusinessException(BusinessMsg.PARAMETER_ERROR, BusinessMsg.getMsg(BusinessMsg.PARAMETER_ERROR) + "，该订单已有终态，不可重新提交");
                }
                is_sameOrderNo=true;
            }
            String new_trans_serial=SeqUtil.getSeqNum();
            transTransReq.setTrans_serial(new_trans_serial);
            rwRecharge.setTrans_serial(new_trans_serial);
        }else{
            transTransReq = transReqService.getTransTransReq(applyQuickPayRequest.clone(), TransConsts.QUICK_RECHARGE_CODE, TransConsts.QUICK_RECHARGE_NAME, false);
            transTransReq.setPlatcust(applyQuickPayRequest.getPlatcust());
            transReqService.insert(transTransReq);
        }
        try {
            //金额校验
            if (applyQuickPayRequest.getAmt().compareTo(BigDecimal.ZERO) <= 0) {
                logger.info("【快捷充值申请】金额不能小于等于零<<");
                transTransReq.setReturn_code(BusinessMsg.MONEY_ERROR);
                transTransReq.setReturn_msg(BusinessMsg.getMsg(BusinessMsg.MONEY_ERROR));
                transTransReq.setStatus(OrderStatus.REQUESTFAIL.getCode());
                transReqService.insert(transTransReq);
                throw new BusinessException(BusinessMsg.MONEY_ERROR, BusinessMsg.getMsg(BusinessMsg.MONEY_ERROR));
            }
            //查询plat_paycode 获取合作商编号 支付通道
            PlatPaycode platPayCode = accountSearchService.queryPlatPayCode(applyQuickPayRequest.getMer_no(), applyQuickPayRequest.getPay_code());
            if (null == platPayCode || null == platPayCode.getPayment_plat_no()) {
                logger.info("=======【快捷充值申请】数据库没有平台【" + applyQuickPayRequest.getMer_no() + "】支付通道【" + applyQuickPayRequest.getPay_code() + "】的支付映射相关信息=======");
                throw new BusinessException(BusinessMsg.PARAMETER_ERROR, BusinessMsg.getMsg(BusinessMsg.METHODPARAMETER_ERROR) + ":数据库没有该支付通道相关信息");
            }
            applyQuickPayRequest.setPay_channel(platPayCode.getChannel_id());
            EaccUserinfo eaccUserinfo = accountSearchService.checkUserinfo(applyQuickPayRequest);
            if (null == eaccUserinfo) {
                logger.info("=======【快捷充值申请】客户信息不存在=======");
                throw new BusinessException(BusinessMsg.INVALID_ACCOUNT, BusinessMsg.getMsg(BusinessMsg.INVALID_ACCOUNT) + "没有该用户");
            }

            //验证手机号码是否为空
            if (!PayCode.ZJ.getCode().equals(platPayCode.getChannel_id()) && null == applyQuickPayRequest.getMobile()) {
                throw new BusinessException(BusinessMsg.PARAMETER_ERROR, BusinessMsg.getMsg(BusinessMsg.PARAMETER_ERROR) + ":不是中金通道、手机号码不能为空");
            }
            if (CardType.CREDIT.getCode().equals(applyQuickPayRequest.getCard_type())) {
                throw new BusinessException(BusinessMsg.PARAMETER_ERROR, BusinessMsg.getMsg(BusinessMsg.PARAMETER_ERROR) + "！目前不支持信用卡快捷充值");
                //sendParams.put("cvv2",);信用卡填0
                //sendParams.put("valid_date",);信用卡填
            }

            String WEB_LOCAL_SERVER = sysParameterService.querySysParameter(SysParamterKey.FTDM, URLConfigUtil.OUT_WEB_LOCAL_SERVER);
            String NOTIFY_URL = sysParameterService.querySysParameter(SysParamterKey.FTDM, URLConfigUtil.RECHARGE_RESULT_NOTIFY);
            NOTIFY_URL = WEB_LOCAL_SERVER + NOTIFY_URL;
            logger.info("【快捷充值申请】获取回调通知地址" + NOTIFY_URL);


            String seriNo = transTransReq.getTrans_serial();
            //记录用户充值信息表

            rwRecharge.setCard_no(applyQuickPayRequest.getCard_no());
            rwRecharge.setMobile(applyQuickPayRequest.getMobile());
            rwRecharge.setCreate_time(DateUtils.today(DateUtils.DEF_FORMAT));
            rwRecharge.setCreate_by(applyQuickPayRequest.getMall_no());
            rwRecharge.setTrans_amt(applyQuickPayRequest.getAmt());//充值金额
            rwRecharge.setReturn_url(NOTIFY_URL);//同步回调地址
            rwRecharge.setTrans_serial(seriNo);//交易流水号 业务流水表相同
            rwRecharge.setOrder_no(applyQuickPayRequest.getOrder_no());//订单号
            rwRecharge.setPlat_no(applyQuickPayRequest.getMer_no());//平台编号
            rwRecharge.setPlatcust(applyQuickPayRequest.getPlatcust());//平台客户号
            rwRecharge.setTrans_code(TransConsts.QUICK_RECHARGE_CODE);//交易代码
            rwRecharge.setTrans_name(TransConsts.QUICK_RECHARGE_NAME);//交易名称
            rwRecharge.setTrans_date(applyQuickPayRequest.getPartner_trans_date());//交易日期
            rwRecharge.setTrans_time(applyQuickPayRequest.getPartner_trans_time());//交易时间
            rwRecharge.setPay_code(applyQuickPayRequest.getPay_code());//支付编号
            rwRecharge.setReques_time(DateUtils.today());//交易请求时间
            rwRecharge.setStatus(OrderStatus.PROCESSING.getCode());//处理状态
            rwRecharge.setNotify_url(applyQuickPayRequest.getNotify_url());//商户异步通知URL
            rwRecharge.setEnabled(Constants.ENABLED);//删除标记
            rwRecharge.setSelf_bank_flag(IsSelfBank.NO.getCode());//默认非本行卡
            Map<String, Object> sendParams = new HashMap<String, Object>();
            //默认投资账户
            rwRecharge.setCharge_type(Ssubject.INVEST.getCode());
            if(null!=applyQuickPayRequest.getCharge_type() && !"".equals(applyQuickPayRequest.getCharge_type())){
                if(Ssubject.FINANCING.getCode().equals(applyQuickPayRequest.getCharge_type())){
                    rwRecharge.setCharge_type(Ssubject.FINANCING.getCode());
                }else if(Ssubject.EACCOUNT.getCode().equals(applyQuickPayRequest.getCharge_type())||null!=eaccUserinfo.getEaccount()){
                    sendParams.put("cr_acct", eaccUserinfo.getEaccount());//电子账号
                    sendParams.put("cr_acct_type", 0);//0 借记卡
                    sendParams.put("cr_acct_name", Ssubject.EACCOUNT.getCode());//03 用户电子账户
                    rwRecharge.setCharge_type(Ssubject.EACCOUNT.getCode());
                }else {
                    rwRecharge.setCharge_type(Ssubject.INVEST.getCode());
                }
            }
            logger.info("【快捷充值申请】准备记录rwRecharge表流水："+JSON.toJSON(rwRecharge));
            if(is_sameOrderNo){
                //原单则更新
                logger.info("【快捷充值申请】原单重试，根据id更新充值记录表,并且更改订单流水号");
                userAccountService.changeTransSerialWhileSameOrderNo(transTransReq,rwRecharge);
            }else{
                //不是原单则添加
                rwRecharge.setId(null);
                rwRechargeMapper.insert(rwRecharge);
            }
            logger.info("【快捷充值申请】记录用户充值信息流水：" + rwRecharge);
            applyBaseResponse.setQuery_id(seriNo);
            applyBaseResponse.setProcess_date(DateUtils.todayfulldata());
            //设置请求参数 如果有电子账户  这三个参数是必传的 如果没有电子账户 则根据入参充值到相应账户
            //todo  如果是融宝
            if (applyQuickPayRequest.getPay_code().equals(PayCode.RBDF.getCode()) || applyQuickPayRequest.getPay_code().equals(PayCode.RBDS.getCode())) {
                //todo 目前没特殊项
            }

            //是否进行短验申请
            if(null!=platPayCode.getIs_msgcheck()&&"1".equals(platPayCode.getIs_msgcheck())){
                String msg_host = sysParameterService.querySysParameter(SysParamterKey.FTDM, URLConfigUtil.EPAY_SERVER_KEY);
                String msg_url = sysParameterService.querySysParameter(SysParamterKey.FTDM, URLConfigUtil.AGENT_COLLECTION_VERIFY);
                msg_url = msg_host + msg_url;
                sendParams.put("url_msgRequest", msg_url);
                logger.info("【快捷充值申请】获取支付发短信接口地址：" + msg_url);
            }

            sendParams.put("partner_id", platPayCode.getPayment_plat_no());
            sendParams.put("partner_serial_no", seriNo);//平台流水
            sendParams.put("partner_trans_date", applyQuickPayRequest.getPartner_trans_date());
            sendParams.put("partner_trans_time", applyQuickPayRequest.getPartner_trans_time());
            sendParams.put("prod_code", TransConsts.QUICK_RECHARGE_CODE);//产品编号
            sendParams.put("prod_name", TransConsts.QUICK_RECHARGE_NAME);//产品名称
            sendParams.put("currency_code", applyQuickPayRequest.getCurrency_code()); //币种
            sendParams.put("summary", "快捷充值");//摘要
            sendParams.put("client_name", applyQuickPayRequest.getName());//客户姓名
            sendParams.put("card_no", applyQuickPayRequest.getCard_no());//银行卡号
            sendParams.put("id_no", applyQuickPayRequest.getId_code());//证件号
            sendParams.put("receive_url", NOTIFY_URL);//后台回调地址 异步通知地址
            sendParams.put("channelId", platPayCode.getChannel_id());//支付通道
            sendParams.put("verify_mode", IsUse.YES.getCode());//验证模式 1需要短信验证 0不需要
            sendParams.put("cert_sign", "quick_pay_test");//签名串
            sendParams.put("mobile_tel", applyQuickPayRequest.getMobile());//手机号
            sendParams.put("partner_userid", applyQuickPayRequest.getPlatcust());//商户用户编号
            //todo 判断是否是信用卡 目前明确不能有信用卡
            //证件类型  如果id_type是1  则id_kind是0
            if (IdType.IDENTITY_CARD.getCode().equals(applyQuickPayRequest.getId_type())) {
                sendParams.put("id_kind", "0");
            } else {
                sendParams.put("id_kind", applyQuickPayRequest.getId_type());
            }
            sendParams.put("occur_balance", applyQuickPayRequest.getAmt());//金额
            String host ="";
            String url="";
            if(null!=platPayCode.getIs_bankcheck()&&"0".equals(platPayCode.getIs_bankcheck())){
                //支付公司收单
                host=sysParameterService.querySysParameter(SysParamterKey.FTDM, URLConfigUtil.EPAY_SERVER_KEY);
                url=sysParameterService.querySysParameter(SysParamterKey.FTDM, URLConfigUtil.AGENT_QUICK_PAY_URL);
            }else{
                //银行收单
                host=sysParameterService.querySysParameter(SysParamterKey.FTDM, URLConfigUtil.EPAY_SERVER_KEY);
                url=sysParameterService.querySysParameter(SysParamterKey.FTDM, URLConfigUtil.BANK_QUICK_PAY_URL);
            }
           /* String host = sysParameterService.querySysParameter(applyQuickPayRequest.getMall_no(), URLConfigUtil.EPAY_SERVER_KEY);
            String url = sysParameterService.querySysParameter(applyQuickPayRequest.getMall_no(), URLConfigUtil.QUICK_PAY_URL);*/
            logger.info("【快捷充值申请】order_no:"+applyQuickPayRequest.getOrder_no()+",请求支付地址：" + host + url);
            sendParams.put("host", host);
            sendParams.put("url", url);

            //根据支付通道特殊性添加特殊字段
            sendParams.put("terminal_id","0");
            sendParams.put("terminal_type","1");
            sendParams.put("partner_userid",applyQuickPayRequest.getPlatcust());
            sendParams.put("prod_name","快捷充值");
            sendParams.put("registtime",DateUtils.today("yyyy-MM-dd HH:mm:ss"));
            sendParams.put("lastloginterminalid","192.0.0.168");
            sendParams.put("issetpaypwd","0");

            logger.info("【快捷充值申请】 快捷支付申请开始...请求参数："+JSON.toJSON(sendParams));
            Map<String, Object> resultMap = adapterService.applyQuickPay(sendParams);
            logger.info("【快捷充值申请】 响应结果：" + JSON.toJSON(resultMap));
            if (null == resultMap || null == resultMap.get("order_status")) {
                transTransReq.setReturn_code(BusinessMsg.CALL_REMOTE_ERROR);
                transTransReq.setReturn_msg(BusinessMsg.getMsg(BusinessMsg.CALL_REMOTE_ERROR));
                transTransReq.setStatus(OrderStatus.REQUESTFAIL.getCode());
                transReqService.insert(transTransReq);
                //支付请求失败
                applyQuickPayResponse.setRecode(BusinessMsg.CALL_REMOTE_ERROR);
                applyQuickPayResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.CALL_REMOTE_ERROR));
                logger.info("【快捷充值申请】 响应结果：" + "无响应参数");
                return applyQuickPayResponse;
            }
            String order_status = resultMap.get("order_status").toString();
            if (!OrderStatus.FAIL.getCode().equals(order_status)) {
                if (null != resultMap.get("host_req_serial_no")) {
                    applyBaseResponse.setHost_req_serial_no(resultMap.get("host_req_serial_no").toString());//返回渠道流水号
                    rwRecharge.setHost_req_serial_no(resultMap.get("host_req_serial_no").toString());//充值信息表请求三方流水号
                }
                if (null != resultMap.get("hsepay_order_no")) {
                    rwRecharge.setHsepay_order_no(resultMap.get("hsepay_order_no").toString());
                }
                if (null != resultMap.get("self_bank_flag")) {
                    rwRecharge.setSelf_bank_flag(resultMap.get("self_bank_flag").toString());
                }
                //更新业务流水为请求成功
                transTransReq.setStatus(FlowStatus.REQUESTSUCCESS.getCode());
                transTransReq.setReturn_code(OrderStatus.REQUESTSUCCESS.getCode());
                transTransReq.setReturn_msg(OrderStatus.REQUESTSUCCESS.getMsg());
                transReqService.insert(transTransReq);
                rwRecharge.setOrder_no(applyQuickPayRequest.getOrder_no());
                rwRecharge.setReturn_msg(OrderStatus.REQUESTSUCCESS.getMsg());
                rwRecharge.setReturn_code(OrderStatus.REQUESTSUCCESS.getCode());
                rwRecharge.setStatus(OrderStatus.REQUESTSUCCESS.getCode());
                rwRecharge.setUpdate_by(applyQuickPayRequest.getMer_no());
                rwRecharge.setUpdate_time(DateUtils.today());
                rwRecharge.setConfirm_time(DateUtils.today(DateUtils.DEF_FORMAT));
                rwRecharge.setLast_time(DateUtils.today(DateUtils.DEF_FORMAT));
                userAccountService.updateRwRecharge(rwRecharge);

                applyQuickPayResponse.setRecode(BusinessMsg.SUCCESS);
                applyQuickPayResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));   //"msg", "快捷支付申请成功！");
            } else {
                //更新业务流水请求失败
                transTransReq.setStatus(FlowStatus.FAIL.getCode());
                transTransReq.setUpdate_time(DateUtils.today(DateUtils.DEF_FORMAT));
                transTransReq.setReturn_msg(OrderStatus.REQUESTFAIL.getMsg());
                transTransReq.setReturn_code(OrderStatus.REQUESTFAIL.getCode());
                transReqService.insert(transTransReq);
                //更新用户充值信息表 状态为请求失败
                rwRecharge.setReturn_code(OrderStatus.REQUESTFAIL.getCode());
                rwRecharge.setReturn_msg(OrderStatus.REQUESTFAIL.getMsg());
                rwRecharge.setOrder_no(applyQuickPayRequest.getOrder_no());
                rwRecharge.setStatus(OrderStatus.REQUESTFAIL.getCode());
                rwRecharge.setUpdate_by(applyQuickPayRequest.getMer_no());
                rwRecharge.setUpdate_time(DateUtils.today());
                rwRecharge.setConfirm_time(DateUtils.today(DateUtils.DEF_FORMAT));
                rwRecharge.setLast_time(DateUtils.today(DateUtils.DEF_FORMAT));
                userAccountService.updateRwRecharge(rwRecharge);

                applyBaseResponse.setError_no(resultMap.get("recode")==null?BusinessMsg.FAIL:resultMap.get("recode").toString());
                applyBaseResponse.setError_info(resultMap.get("remsg")==null?BusinessMsg.getMsg(BusinessMsg.FAIL):resultMap.get("remsg").toString());

                applyQuickPayResponse.setRecode(resultMap.get("recode")==null?BusinessMsg.FAIL:resultMap.get("recode").toString());
                applyQuickPayResponse.setRemsg(resultMap.get("remsg")==null?BusinessMsg.getMsg(BusinessMsg.FAIL):resultMap.get("remsg").toString());
            }
            applyBaseResponse.setOrder_status(order_status);
            applyQuickPayResponse.setData_detail(applyBaseResponse);
            return applyQuickPayResponse;
        } catch (BusinessException e) {
            logger.error("【快捷充值申请】异常", e);
            //确保在rwRecharge入库的情况下再更新rwRecharge记录
            if (null != rwRecharge.getOrder_no() && !"".equals(rwRecharge.getOrder_no())) {
                rwRecharge.setReturn_msg(OrderStatus.REQUESTFAIL.getMsg());
                rwRecharge.setStatus(OrderStatus.REQUESTFAIL.getCode());
                rwRecharge.setUpdate_by(applyQuickPayRequest.getMer_no());
                rwRecharge.setUpdate_time(DateUtils.today());
                rwRecharge.setConfirm_time(DateUtils.today(DateUtils.DEF_FORMAT));
                rwRecharge.setLast_time(DateUtils.today(DateUtils.DEF_FORMAT));
                userAccountService.updateRwRecharge(rwRecharge);
            }
            transTransReq.setStatus(FlowStatus.REQUESTFAIL.getCode());
            transTransReq.setReturn_code(e.getBaseResponse().getRecode());
            transTransReq.setReturn_msg(e.getBaseResponse().getRemsg());
            transReqService.insert(transTransReq);
            applyBaseResponse.setError_no(e.getBaseResponse().getRecode());
            applyBaseResponse.setError_info(e.getBaseResponse().getRemsg());
            applyBaseResponse.setOrder_status(OrderStatus.FAIL.getCode());
            applyQuickPayResponse.setRecode(e.getBaseResponse().getRecode());
            applyQuickPayResponse.setRemsg(e.getBaseResponse().getRemsg());
            applyQuickPayResponse.setData_detail(applyBaseResponse);
        }
        return applyQuickPayResponse;
    }

    @Override
    public ConfirmQuickPayResponse confirmQuickPay(ConfirmQuickPayRequest confirmQuickPayRequest) throws BusinessException {
        //根据原申请订单号查询快捷充值请求流水
        logger.info("【快捷充值确认】=========开始，确认订单号：" + confirmQuickPayRequest.getOrder_no());
        TransTransreq transTransreq = transReqService.queryTransTransReqByOrderno(confirmQuickPayRequest.getOrigin_order_no());
        if(null==transTransreq){
            logger.info("【快捷充值确认】=========根据原充值申请订单号查不到充值请求信息，请重新申请");
            throw new BusinessException(BusinessMsg.PARAMETER_ERROR, BusinessMsg.getMsg(BusinessMsg.PARAMETER_ERROR) + ",根据原充值申请订单号查不到充值请求信息，请重新申请");
        }
        //记录快捷请求确认流水
        TransTransreq transTransreq1 = transReqService.getTransTransReq(confirmQuickPayRequest.clone(), TransConsts.QUICK_CONFIRM_CODE, TransConsts.QUICK_CONFIRM_NAME, false);
        transTransreq1.setOrigin_order_no(confirmQuickPayRequest.getOrigin_order_no());
        transTransreq1.setPlatcust(transTransreq.getPlatcust());
        transReqService.insert(transTransreq1);
        //为了配合老接口兼容
        if(null==confirmQuickPayRequest.getPlatcust()||"".equals(confirmQuickPayRequest.getPlatcust())){
            confirmQuickPayRequest.setPlatcust(transTransreq.getPlatcust());
        }
        ConfirmQuickPayResponseDetail dataDetail = new ConfirmQuickPayResponseDetail();
        ConfirmQuickPayResponse confirmQuickPayResponse = new ConfirmQuickPayResponse();
        confirmQuickPayResponse.setData_detail(dataDetail);
        //平台处理日期
        dataDetail.setProcess_date(DateUtils.todayfulldata());
        //平台流水号
        dataDetail.setQuery_id(transTransreq.getTrans_serial());


        RwRecharge rwRecharge=new RwRecharge();
        try {
            //查询原申请订单号在充值信息表中的记录
            logger.info("【快捷充值确认】=========查询原充值请求流水");
            rwRecharge = accountSearchService.queryRwRecharge(confirmQuickPayRequest.getMer_no(), confirmQuickPayRequest.getOrigin_order_no());
            if (rwRecharge == null || ((!OrderStatus.REQUESTSUCCESS.getCode().equals(rwRecharge.getStatus()))&&(!OrderStatus.PROCESSING.getCode().equals(rwRecharge.getStatus())))) {
                logger.info("【快捷充值确认】=========查询不到原申请订单，或者原订单已经是终态，请勿重复确认");
                throw new BusinessException(BusinessMsg.PARAMETER_ERROR, BusinessMsg.getMsg(BusinessMsg.PARAMETER_ERROR) + ",查询不到原申请订单，或者原订单已经是终态，请勿重复确认");
            }
            logger.info("【快捷充值确认】=========查询原充值请求流水结束");

            rwRecharge.setTrans_code(TransConsts.QUICK_CONFIRM_CODE);
            rwRecharge.setTrans_name(TransConsts.QUICK_CONFIRM_NAME);
            rwRecharge.setOrder_no(confirmQuickPayRequest.getOrigin_order_no());

            //查询plat_paycode 获取合作商编号
            logger.info("【快捷充值确认】=========查询pay_code信息，确认订单号：" + confirmQuickPayRequest.getOrder_no());
            PlatPaycode platPayCode = accountSearchService.queryPlatPayCode(confirmQuickPayRequest.getMer_no(), confirmQuickPayRequest.getPay_code());
            if (null == platPayCode) {
                logger.info("【快捷充值确认】=========渠道不存在：" + confirmQuickPayRequest.getPay_code());
                throw new BusinessException(BusinessMsg.PARAMETER_ERROR, BusinessMsg.getMsg(BusinessMsg.PARAMETER_ERROR) + ",根据平台编号和支付通道查询不到渠道信息");
            }
            logger.info("【快捷充值确认】=========查询pay_code信息完成");

            Map<String, Object> sendParams = new HashMap<>();
            sendParams.put("partner_id", platPayCode.getPayment_plat_no());
            sendParams.put("partner_serial_no", rwRecharge.getTrans_serial());
            sendParams.put("verify_info", confirmQuickPayRequest.getIdentifying_code());
            sendParams.put("channelId",platPayCode.getChannel_id());
            sendParams.put("cert_sign", "quick_pay_test");

            String host ="";
            String url="";
            if(null!=platPayCode.getIs_bankcheck()&&"0".equals(platPayCode.getIs_bankcheck())){
                //支付公司收单
                host =sysParameterService.querySysParameter(SysParamterKey.FTDM, URLConfigUtil.EPAY_SERVER_KEY);
                url=sysParameterService.querySysParameter(SysParamterKey.FTDM, URLConfigUtil.AGENT_CONFIRM_QUICK_PAY_URL);
            }else{
                //银行收单
                host =sysParameterService.querySysParameter(SysParamterKey.FTDM, URLConfigUtil.EPAY_SERVER_KEY);
                url=sysParameterService.querySysParameter(SysParamterKey.FTDM, URLConfigUtil.BANK_CONFIRM_QUICK_PAY_URL);
            }

           /* String host = sysParameterService.querySysParameter(confirmQuickPayRequest.getMall_no(), URLConfigUtil.EPAY_SERVER_KEY);
            String url = sysParameterService.querySysParameter(confirmQuickPayRequest.getMall_no(), URLConfigUtil.CONFIRM_QUICK_PAY_URL);*/
            sendParams.put("host", host);
            sendParams.put("url", url);
            logger.info("【快捷充值确认】=========获取支付链接参数完成:"+host+url);

            logger.info("【快捷充值确认】=========获取支付响应开始，确认订单号：" + confirmQuickPayRequest.getOrder_no()+",请求参数"+sendParams);
            Map<String,Object> resultMap=adapterService.confirmyQuickPay(sendParams);

            //只要请求了三方就把状态改为处理中
            rwRecharge.setStatus(OrderStatus.PROCESSING.getCode());
            rwRecharge.setReturn_code(BusinessMsg.SUCCESS);
            rwRecharge.setReturn_msg("处理中");
            rwRechargeMapper.updateByPrimaryKeySelective(rwRecharge);

            logger.info("【快捷充值确认】=========获取支付响应完成：" + confirmQuickPayRequest.getOrder_no()+"======响应结果："+JSON.toJSON(resultMap));
            if(null==resultMap||null==resultMap.get("order_status")){
                logger.info("【快捷充值确认】=========返回参数不正确,确认订单：" + confirmQuickPayRequest.getOrder_no());
                confirmQuickPayResponse.setRecode(BusinessMsg.CALL_REMOTE_ERROR);
                confirmQuickPayResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.CALL_REMOTE_ERROR));
                return confirmQuickPayResponse;
            }
            String order_status=resultMap.get("order_status").toString();
            if(null!=resultMap.get("pay_finish_date") && !"".equals(resultMap.get("pay_finish_date"))){
                rwRecharge.setPayment_date(resultMap.get("pay_finish_date").toString());
            }else{
                rwRecharge.setPayment_date(DateUtils.getyyyyMMddDate());
            }
            if(null!=resultMap.get("self_bank_flag") && !"".equals(resultMap.get("self_bank_flag"))){
                rwRecharge.setSelf_bank_flag(IsSelfBank.YES.getCode().equals(resultMap.get("self_bank_flag").toString())?IsSelfBank.YES.getCode():IsSelfBank.NO.getCode());
            }
            dataDetail.setOrder_status(order_status);
            if(OrderStatus.SUCCESS.getCode().equals(order_status)){
                logger.info("【快捷充值确认】===========第三方支付返回状态交易成功");
                //如果充值成功 向用户账户表中添加金额
                rwRecharge.setLast_time(DateUtils.today(DateUtils.DEF_FORMAT));
                rwRecharge.setReturn_msg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));
                rwRecharge.setReturn_code(BusinessMsg.SUCCESS);
                rwRecharge.setStatus(OrderStatus.SUCCESS.getCode());
                rwRecharge.setUpdate_by(confirmQuickPayRequest.getMer_no());
                rwRecharge.setOrder_no(confirmQuickPayRequest.getOrigin_order_no());
                rwRecharge.setConfirm_time(DateUtils.today(DateUtils.DEF_FORMAT));
                rwRecharge.setUpdate_time(DateUtils.today());

                confirmQuickPayResponse.setRecode(BusinessMsg.SUCCESS);
                confirmQuickPayResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));
            } else if (OrderStatus.PROCESSING.getCode().equals(order_status)) {
                //更新充值信息
                Object recode = resultMap.get("recode");
                Object remsg = resultMap.get("remsg");
                logger.info("【快捷充值确认】===========第三方支付返回状态处理中");
                dataDetail.setError_info(remsg == null ? "处理中" : remsg.toString());
                dataDetail.setError_no(recode == null ? OrderStatus.PROCESSING.getCode() : recode.toString());
                rwRecharge.setStatus(OrderStatus.PROCESSING.getCode());
                confirmQuickPayResponse.setRecode(recode == null ? OrderStatus.PROCESSING.getCode() : recode.toString());
                confirmQuickPayResponse.setRemsg(remsg == null ? "处理中" : remsg.toString());
            } else if (OrderStatus.FAIL.getCode().equals(order_status)) {
                dataDetail.setError_info(resultMap.get("remsg")==null?BusinessMsg.getMsg(BusinessMsg.FAIL):resultMap.get("remsg").toString());
                dataDetail.setError_no(resultMap.get("recode")==null?BusinessMsg.FAIL:resultMap.get("recode").toString());
                rwRecharge.setStatus(OrderStatus.FAIL.getCode());
                rwRecharge.setReturn_msg(resultMap.get("remsg")==null?BusinessMsg.getMsg(BusinessMsg.FAIL):resultMap.get("remsg").toString());
                rwRecharge.setReturn_code(resultMap.get("recode")==null?BusinessMsg.FAIL:resultMap.get("recode").toString());
                logger.info("【快捷充值确认】===========第三方支付返回状态交易失败");
                confirmQuickPayResponse.setRecode(resultMap.get("recode")==null?BusinessMsg.FAIL:resultMap.get("recode").toString());
                confirmQuickPayResponse.setRemsg(resultMap.get("remsg")==null?BusinessMsg.getMsg(BusinessMsg.FAIL):resultMap.get("remsg").toString());
            } else {
                dataDetail.setError_info(BusinessMsg.getMsg(BusinessMsg.UNKNOW_ERROE));
                dataDetail.setError_no(BusinessMsg.UNKNOW_ERROE);
                dataDetail.setOrder_status(OrderStatus.FAIL.getCode());
                rwRecharge.setStatus(OrderStatus.REQUESTSUCCESS.getCode());
                confirmQuickPayResponse.setRecode(BusinessMsg.UNKNOW_ERROE);
                confirmQuickPayResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.UNKNOW_ERROE));
            }
            //调用陈瑞接口
            logger.info("请求dorecharge参数："+rwRecharge.toString()+",确认订单号："+confirmQuickPayRequest.getOrder_no());
            Boolean flag = rechargeOptionService.doRecharge(rwRecharge, confirmQuickPayRequest.getMall_no(),confirmQuickPayRequest.getOrder_no(), TransConsts.QUICK_CONFIRM_CODE);
            logger.info("返回flag："+flag);
            if (!flag) {
                // 陈瑞接口返回false ，表示处理中
                transTransreq.setStatus(FlowStatus.REQUESTSUCCESS.getCode());
                transTransreq.setReturn_code(BusinessMsg.SUCCESS);
                transTransreq.setReturn_msg(BusinessMsg.getMsg(BusinessMsg.SUCCESS) + "：充值订单处理中");
                transReqService.insert(transTransreq);
                confirmQuickPayResponse.setRecode(BusinessMsg.SUCCESS);
                confirmQuickPayResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.SUCCESS) + "：充值订单处理中");
                dataDetail.setError_no(BusinessMsg.SUCCESS);
                dataDetail.setError_info(BusinessMsg.getMsg(BusinessMsg.SUCCESS) + "：充值订单处理中");
                dataDetail.setOrder_status(OrderStatus.PROCESSING.getCode());
            } else {
                if(OrderStatus.SUCCESS.getCode().equals(order_status)){
                    transTransreq.setStatus(FlowStatus.CONFIRMSUCCESS.getCode());
                }else if(OrderStatus.FAIL.getCode().equals(order_status)){
                    transTransreq.setStatus(FlowStatus.CONFIRMFAIL.getCode());
                }else{
                    transTransreq.setStatus(FlowStatus.INPROCESS.getCode());
                }
                transReqService.insert(transTransreq);
            }
            //transReqService.insert(transTransreq);
            confirmQuickPayResponse.setData_detail(dataDetail);
            logger.info("【快捷充值确认】===========同步响应结果："+confirmQuickPayResponse.toString());
            if(OrderStatus.SUCCESS.getCode().equals(dataDetail.getOrder_status())||
                    OrderStatus.FAIL.getCode().equals(dataDetail.getOrder_status())) {
                //组装异步通知参数
                Map<String, Object> notify_msg = new HashMap<String, Object>();
                notify_msg.put("plat_no", confirmQuickPayRequest.getMer_no());
                notify_msg.put("mall_no", confirmQuickPayRequest.getMall_no());
                notify_msg.put("platcust", confirmQuickPayRequest.getPlatcust());
                notify_msg.put("type", "1");
                notify_msg.put("order_no", rwRecharge.getOrder_no());
                notify_msg.put("order_amt", rwRecharge.getTrans_amt());
                notify_msg.put("trans_date", rwRecharge.getTrans_date());
                notify_msg.put("trans_time", rwRecharge.getTrans_time());
                notify_msg.put("pay_finish_date",DateUtils.getyyyyMMddDate());
                notify_msg.put("pay_finish_time",DateUtils.getHHmmssTime());
                notify_msg.put("sign", "sign");
                notify_msg.put("order_status", dataDetail.getOrder_status());
                notify_msg.put("pay_order_no", rwRecharge.getHsepay_order_no());
                notify_msg.put("host_req_serial_no", rwRecharge.getHost_req_serial_no());
                notify_msg.put("pay_amt", rwRecharge.getTrans_amt());
                notify_msg.put("notify_url", rwRecharge.getNotify_url());
                confirmQuickPayResponse.setNotify_msg(notify_msg);
                if(OrderStatus.FAIL.getCode().equals(dataDetail.getOrder_status())){
                    notify_msg.put("error_no", dataDetail.getError_no());
                    notify_msg.put("error_info", dataDetail.getError_info());
                    notify_msg.put("recode", StringUtils.isBlank(dataDetail.getError_no())?BusinessMsg.FAIL:dataDetail.getError_no());
                    notify_msg.put("remsg", StringUtils.isBlank(dataDetail.getError_info())?BusinessMsg.getMsg(BusinessMsg.FAIL):dataDetail.getError_info());
                } else if(OrderStatus.SUCCESS.getCode().equals(dataDetail.getOrder_status())){
                    notify_msg.put("recode", BusinessMsg.SUCCESS);
                    notify_msg.put("remsg", BusinessMsg.getMsg(BusinessMsg.SUCCESS));
                }
                confirmQuickPayResponse.setNotify_msg(notify_msg);
            }
            ConfirmQuickPayResponseDetail data_detail=confirmQuickPayResponse.getData_detail();
            //同步响应处理成功或者处理中 向平台发异步
            logger.info("【快捷充值确认】服务端收到的响应："+JSON.toJSON(confirmQuickPayResponse));
            if(OrderStatus.SUCCESS.getCode().equals(data_detail.getOrder_status())||
                    OrderStatus.FAIL.getCode().equals(data_detail.getOrder_status())){
                Map<String, Object> requestMap =confirmQuickPayResponse.getNotify_msg();
                if(null==requestMap|| null == requestMap.get("notify_url")){
                    logger.info("【快捷充值同步发送异步通知】异常：异步通知参数缺失，order_no:"+confirmQuickPayResponse.getOrder_no());
                }else{
                    String notify_url=requestMap.get("notify_url").toString();
                    requestMap.remove("notify_url");
                    String data = JSONObject.toJSONString(requestMap);
                    logger.info("【充值异步回调】异步通知平台处理数据：" + data);
                    logger.info("【充值异步回调】异步地址：" + notify_url);

                    NotifyData notifyData = new NotifyData();
                    notifyData.setNotifyContent(data);
                    notifyData.setNotifyUrl(notify_url);
                    notifyData.setMall_no(confirmQuickPayRequest.getMall_no());
                    notifyService.addNotify(notifyData);
                }
            }
            return confirmQuickPayResponse;
        } catch (Exception e) {
            logger.error("【快捷充值确认】ERROR:",e);
            if(e instanceof  BusinessException ){
                BaseResponse baseResponse = ((BusinessException) e).getBaseResponse();
                dataDetail.setOrder_status(OrderStatus.PROCESSING.getCode());
                dataDetail.setError_no(baseResponse.getRecode());
                dataDetail.setError_info(baseResponse.getRemsg());
                transTransreq.setStatus(FlowStatus.INPROCESS.getCode());
                transTransreq.setReturn_code(baseResponse.getRecode());
                transTransreq.setReturn_msg(baseResponse.getRemsg());
                transReqService.insert(transTransreq);
                confirmQuickPayResponse.setRecode(baseResponse.getRecode());
                confirmQuickPayResponse.setRemsg(baseResponse.getRemsg());
            } else{
                //更新流水
                dataDetail.setOrder_status(OrderStatus.PROCESSING.getCode());
                dataDetail.setError_no(BusinessMsg.DATEBASE_EXCEPTION);
                dataDetail.setError_info(BusinessMsg.getMsg(BusinessMsg.DATEBASE_EXCEPTION));
                transTransreq.setStatus(FlowStatus.INPROCESS.getCode());
                transTransreq.setReturn_code(BusinessMsg.DATEBASE_EXCEPTION);
                transTransreq.setReturn_msg(BusinessMsg.getMsg(BusinessMsg.DATEBASE_EXCEPTION));
                transReqService.insert(transTransreq);
                confirmQuickPayResponse.setRecode(BusinessMsg.DATEBASE_EXCEPTION);
                confirmQuickPayResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.DATEBASE_EXCEPTION));
            }
            logger.info("【快捷充值确认】===========同步响应结果："+confirmQuickPayResponse.toString());
            return confirmQuickPayResponse;
        }
    }

    @Override
    public GetewayPayResponse gatewayRechargeRequest(GetewayPayRequest getewayPayRequest) throws BusinessException {
        GetewayPayResponse payResponse = new GetewayPayResponse();
        logger.info("=======【网关充值】开始,订单号：+"+getewayPayRequest.getOrder_no()+"=======");
        TransTransreq transTransReq = transReqService.getTransTransReq(getewayPayRequest.clone(),
                TransConsts.GATEWAY_RECHARGE_CODE, TransConsts.GATEWAY_RECHARGE_NAME, false);
        transReqService.insert(transTransReq);
        logger.info("=======【网关充值】记录流水完成，流水号："+transTransReq.getTrans_serial()+"=======");

        if (getewayPayRequest.getAmt().compareTo(BigDecimal.ZERO) < 0) {
            logger.info("=======【网关充值】金额输入不合法！=======");
            transTransReq.setReturn_code(BusinessMsg.CALL_REMOTE_ERROR);
            transTransReq.setReturn_msg(BusinessMsg.getMsg(BusinessMsg.CALL_REMOTE_ERROR));
            transTransReq.setStatus(OrderStatus.REQUESTFAIL.getCode());
            transReqService.insert(transTransReq);
            throw new BusinessException(BusinessMsg.MONEY_ERROR,BusinessMsg.getMsg(BusinessMsg.MONEY_ERROR));
        }

        EaccUserinfo eaccUserInfo = accountSearchService.queryEaccUserInfoByEaccAccountInfo(getewayPayRequest.getMall_no(), getewayPayRequest.getMer_no(), getewayPayRequest.getPlatcust());
        if (null == eaccUserInfo) {
            logger.info("=======【网关充值】客户信息不存在=======");
            transTransReq.setReturn_code(BusinessMsg.INVALID_ACCOUNT);
            transTransReq.setReturn_msg(BusinessMsg.getMsg(BusinessMsg.INVALID_ACCOUNT) + "没有该用户");
            transTransReq.setStatus(OrderStatus.REQUESTFAIL.getCode());
            transReqService.insert(transTransReq);
            throw new BusinessException(BusinessMsg.INVALID_ACCOUNT,BusinessMsg.getMsg(BusinessMsg.INVALID_ACCOUNT) + "没有该用户");
        }
        if (getewayPayRequest.getCard_no() == null && getewayPayRequest.getBankcode() == null) {
            logger.info("=======【网关充值】银行编号和卡号必须输入一项！=======");
            transTransReq.setReturn_code(BusinessMsg.METHODPARAMETER_ERROR);
            transTransReq.setReturn_msg(BusinessMsg.getMsg(BusinessMsg.METHODPARAMETER_ERROR) + ":银行编号和卡号必须输入一项！");
            transTransReq.setStatus(OrderStatus.REQUESTFAIL.getCode());
            transReqService.insert(transTransReq);
            throw new BusinessException(BusinessMsg.METHODPARAMETER_ERROR,BusinessMsg.getMsg(BusinessMsg.METHODPARAMETER_ERROR) + ":银行编号和卡号必须输入一项！");
        }

        PlatPaycode platPayCode = accountSearchService.queryPlatPayCode(getewayPayRequest.getMer_no(), getewayPayRequest.getPay_code());
        if (null == platPayCode || null == platPayCode.getPayment_plat_no()) {
            logger.info("=======数据库没有平台【" + getewayPayRequest.getMer_no() + "】支付通道【" + getewayPayRequest.getPay_code() + "】的支付映射相关信息=======");
            transTransReq.setReturn_code(BusinessMsg.PARAMETER_ERROR);
            transTransReq.setReturn_msg(BusinessMsg.getMsg(BusinessMsg.METHODPARAMETER_ERROR) + ":数据库没有该支付通道相关信息");
            transTransReq.setStatus(OrderStatus.REQUESTFAIL.getCode());
            transReqService.insert(transTransReq);
            throw new BusinessException(BusinessMsg.PARAMETER_ERROR,BusinessMsg.getMsg(BusinessMsg.METHODPARAMETER_ERROR) + ":数据库没有该支付通道相关信息");
        }

        //记录用户充值信息表
        RwRecharge rwRecharge = new RwRecharge();
        rwRecharge.setId(null);
        rwRecharge.setCard_no(getewayPayRequest.getCard_no());
        rwRecharge.setCreate_time(DateUtils.today(DateUtils.DEF_FORMAT));
        rwRecharge.setCreate_by(getewayPayRequest.getMall_no());
        rwRecharge.setTrans_amt(getewayPayRequest.getAmt());//充值金额
        rwRecharge.setCharge_type(getewayPayRequest.getCharge_type());//充值账户类型  投资或者融资
        rwRecharge.setTrans_serial(transTransReq.getTrans_serial());//交易流水号
        rwRecharge.setOrder_no(getewayPayRequest.getOrder_no());//订单号
        rwRecharge.setPlat_no(getewayPayRequest.getMer_no());//平台编号
        rwRecharge.setPlatcust(getewayPayRequest.getPlatcust());//平台客户号
        rwRecharge.setTrans_code(TransConsts.GATEWAY_RECHARGE_CODE);//交易代码
        rwRecharge.setTrans_name(TransConsts.GATEWAY_RECHARGE_NAME);//交易名称
        rwRecharge.setTrans_date(getewayPayRequest.getPartner_trans_date());//交易日期
        rwRecharge.setTrans_time(getewayPayRequest.getPartner_trans_time());//交易时间
        rwRecharge.setPay_code(getewayPayRequest.getPay_code());//支付编号
        rwRecharge.setReques_time(DateUtils.today());//交易请求时间
        rwRecharge.setStatus(OrderStatus.PROCESSING.getCode());//处理状态
        rwRecharge.setReturn_url(getewayPayRequest.getReturn_url());//同步回调地址
        rwRecharge.setNotify_url(getewayPayRequest.getNotify_url());//商户异步通知URL
        rwRecharge.setEnabled(Constants.ENABLED);//删除标记
        rwRecharge.setClient_property(getewayPayRequest.getClient_property());
        rwRecharge.setSelf_bank_flag(IsSelfBank.NO.getCode());//默认非本行卡
        AccountSubjectInfo eplatcust = accountQueryService.queryFINANCINGPlatAccount(getewayPayRequest.getMall_no(), getewayPayRequest.getPlatcust(), AccountType.ElectronicAccount.getCode(),
                null, Ssubject.EACCOUNT.getCode());
        if (eplatcust != null) {
            rwRecharge.setCharge_type(Ssubject.EACCOUNT.getCode());//电子账户
        }
        rwRechargeMapper.insert(rwRecharge);
        logger.info("=======【网关充值】记录用户充值信息流水：======="+rwRecharge);

        String WEB_LOCAL_SERVER = sysParameterService.querySysParameter(SysParamterKey.FTDM, URLConfigUtil.OUT_WEB_LOCAL_SERVER);
        String NOTIFY_URL = sysParameterService.querySysParameter(SysParamterKey.FTDM, URLConfigUtil.RECHARGE_RESULT_NOTIFY);
        NOTIFY_URL = WEB_LOCAL_SERVER  + NOTIFY_URL;
        logger.info("=======【网关充值】获取回调通知地址=======" + NOTIFY_URL);
        //获取请求URL
        String Base = sysParameterService.querySysParameter(SysParamterKey.FTDM, URLConfigUtil.EPAY_SERVER_KEY);
        String URL = sysParameterService.querySysParameter(SysParamterKey.FTDM, URLConfigUtil.GATEWAY_PAY_URL);
        logger.info("=======【网关充值】E支付请求地址=======" + Base+URL);
        //设置请求参数
        Map<String, Object> sendParams = new HashMap<String, Object>();
        sendParams.put("host",Base);
        sendParams.put("url",URL);
        sendParams.put("partner_id", platPayCode.getPayment_plat_no());    //合作商编号
        sendParams.put("partner_serial_no", transTransReq.getTrans_serial());    //合作商流水
        sendParams.put("partner_trans_date", getewayPayRequest.getPartner_trans_date());    //合作商交易日期
        sendParams.put("partner_trans_time", getewayPayRequest.getPartner_trans_time());    //合作商交易时间
        sendParams.put("prod_code", TransConsts.GATEWAY_RECHARGE_CODE);    //产品编号
        sendParams.put("prod_name", TransConsts.GATEWAY_RECHARGE_NAME);    //产品名称
        if (getewayPayRequest.getCard_no() != null) {
            sendParams.put("card_no", getewayPayRequest.getCard_no());        //银行卡号
        }
        sendParams.put("currency_code", getewayPayRequest.getCurrency_code());  //币种
        sendParams.put("occur_balance", getewayPayRequest.getAmt());  //金额
        sendParams.put("summary", "0");    //摘要
        if(CardType.CREDIT.getCode().equals(getewayPayRequest.getCard_type())){
            sendParams.put("pay_bankacct_type", "1");//支付卡类型-信用卡
        }else {
            sendParams.put("pay_bankacct_type", "0");//支付卡类型借记卡
        }

        sendParams.put("client_property", "0");//默认个人网银
        if (CusType.PERSONAL.getCode().equals(getewayPayRequest.getClient_property())) {
            sendParams.put("client_property", "0");//账户类型-个人
        }else if(CusType.COMPANY.getCode().equals(getewayPayRequest.getClient_property())){
            sendParams.put("client_property", "1");//账户类型-企业.
        }

        if (getewayPayRequest.getBankcode() != null) {
            sendParams.put("bank_id", getewayPayRequest.getBankcode());//行号
        }
        sendParams.put("pickup_url", getewayPayRequest.getReturn_url());//前台回调地址(展示页面接受通知的地址)
        sendParams.put("receive_url", NOTIFY_URL);//后台回调地址(充值回调通知)
        sendParams.put("p2p_terminal_type", TransChannel.WEB.getCode());//终端类型
        sendParams.put("partner_userid", getewayPayRequest.getPlatcust());//商户用户编号
        sendParams.put("user_info_identify_type", "1");//实名认证类型
        sendParams.put("user_info_identify_state", "1");//实名认证状态
        sendParams.put("user_info_dt_register", DateUtils.format(eaccUserInfo.getCreate_time(),DateUtils.DEF_FORMAT_STRING));//用户注册时间
        sendParams.put("id_kind", eaccUserInfo.getId_type());//证件类型
        sendParams.put("id_no", eaccUserInfo.getId_code()==null?"":eaccUserInfo.getId_code());//证件号码
        sendParams.put("client_name", eaccUserInfo.getName()==null?"":eaccUserInfo.getName());//客户姓名
        sendParams.put("mobile_tel", eaccUserInfo.getMobile()==null?"":eaccUserInfo.getMobile());//客户手机号
        sendParams.put("channelId", platPayCode.getChannel_id());//通道ID
        //		sendParams.put("channelId", "023");//通道ID
        sendParams.put("cert_sign", "quick_pay_test");//签名串
//		sendParams.put("cert_sign", getewayPayRequest.getSign());//签名串
        sendParams.put("occur_balance", getewayPayRequest.getAmt());//金额

        logger.info("=======【网关充值】请求支付开始，请求参数======="+sendParams);
        Map<String,Object> resultMap = adapterService.applyGatewayPay(sendParams);
        //远程调用失败
        if (resultMap == null) {
            logger.info("=======【网关充值】第三方请求失败！无响应参数=======");
            rwRecharge.setConfirm_time(DateUtils.today("yyyyMMddHHmmss"));
            rwRecharge.setStatus(OrderStatus.REQUESTFAIL.getCode());
            rwRecharge.setReturn_msg(OrderStatus.REQUESTFAIL.getMsg());
            userAccountService.updateRwRecharge(rwRecharge);

            transTransReq.setReturn_code(BusinessMsg.CALL_REMOTE_ERROR);
            transTransReq.setReturn_msg(BusinessMsg.getMsg(BusinessMsg.CALL_REMOTE_ERROR));
            transTransReq.setStatus(OrderStatus.REQUESTFAIL.getCode());
            transReqService.insert(transTransReq);

            throw new BusinessException(transTransReq.getReturn_code(),transTransReq.getReturn_msg());
        }
        logger.info("=======【网关充值】第三方响应参数:" + resultMap +"=======");

        //支付完成时间，如果为空则默认使用当前时间
        if (resultMap.get("pay_finish_date") != null) {
            rwRecharge.setPayment_date(resultMap.get("pay_finish_date").toString());
        }else{
            rwRecharge.setPayment_date(DateUtils.getyyyyMMddDate());
        }

        if(OrderStatus.FAIL.getCode().equals(resultMap.get("order_status"))){
            logger.info("=======【网关充值】" + resultMap.get("remsg") + "云融惠付返回错误信息，第三方请求失败！=======");
            rwRecharge.setConfirm_time(DateUtils.today("yyyyMMddHHmmss"));
            rwRecharge.setStatus(OrderStatus.REQUESTFAIL.getCode());
            rwRecharge.setReturn_msg(OrderStatus.REQUESTFAIL.getMsg());
            userAccountService.updateRwRecharge(rwRecharge);
            //更新流水,抛异常
            transTransReq.setReturn_code(resultMap.get("recode")==null?BusinessMsg.FAIL:resultMap.get("recode").toString());
            transTransReq.setReturn_msg(BusinessMsg.getMsg(resultMap.get("remsg") + "返回错误信息，第三方请求失败！"));
            transTransReq.setStatus(OrderStatus.REQUESTFAIL.getCode());
            transReqService.insert(transTransReq);

            throw new BusinessException(resultMap.get("recode")==null?BusinessMsg.FAIL:resultMap.get("recode").toString(),resultMap.get("remsg")==null?BusinessMsg.getMsg(BusinessMsg.FAIL):resultMap.get("remsg")+ "返回错误信息，第三方请求失败！");
        }

        if (resultMap.get("view") != null) {
            logger.info("=======【网关充值】:支付网关页面=======");
            payResponse.setView(resultMap.get("view").toString());//支付网关页面
        }

        if (resultMap.get("hsepay_order_no") != null) {
            rwRecharge.setHsepay_order_no(resultMap.get("hsepay_order_no").toString());
        }

        if (resultMap.get("host_req_serial_no") != null) {
            rwRecharge.setHost_req_serial_no(resultMap.get("host_req_serial_no").toString());
        }
        rwRecharge.setUpdate_by(getewayPayRequest.getMer_no());
        rwRecharge.setStatus(OrderStatus.PROCESSING.getCode());
        rwRecharge.setConfirm_time(DateUtils.today("yyyyMMddHHmmss"));
        rwRecharge.setReturn_msg(OrderStatus.REQUESTSUCCESS.getMsg());
        userAccountService.updateRwRecharge(rwRecharge);
        //更新流水
        transTransReq.setReturn_code(BusinessMsg.SUCCESS);
        transTransReq.setReturn_msg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));
        transTransReq.setStatus(OrderStatus.REQUESTSUCCESS.getCode());
        transReqService.insert(transTransReq);
        payResponse.setRecode(BusinessMsg.SUCCESS);
        payResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));

        return payResponse;
    }

    @Override
    public NotifyConfirmQPres notifyCharge(NotityPaymentRequest notityPaymentRequest) throws BusinessException {
        logger.info("========【充值异步回调】回调参数：" + JSON.toJSONString(notityPaymentRequest));
        Map<String, Object> requestMap = new HashMap<>();
        TransTransreq transTransreq = new TransTransreq();
        NotifyConfirmQPres notifyConfirmQPres = new NotifyConfirmQPres();

        transTransreq.setEnabled(Constants.ENABLED);
        List<NotityPaymentData> list = notityPaymentRequest.getData();
        try {
            if (null == list || 0 == list.size()) {
                logger.info("【充值异步回调】E支付回调参数为空，返回false");
                notifyConfirmQPres.setStatus(false);
                return notifyConfirmQPres;
            }

            NotityPaymentData notityPaymentData = list.get(0);
            String payStatus = notityPaymentData.getPay_status();

            //根据流水号查询充值信息 交易是否成功
            RwRecharge rwRecharge = accountSearchService.queryRwRechargeBySerial(notityPaymentData.getPartner_serial_no(),
                    null, null);

            if (null == rwRecharge) {
                logger.info("【充值异步回调】找不到充值订单，流水号：" + notityPaymentData.getPartner_serial_no());
                notifyConfirmQPres.setStatus(false);
                return notifyConfirmQPres;
            }

            if (FlowStatus.SUCCESS.getCode().equals(rwRecharge.getStatus())
                    || FlowStatus.FAIL.getCode().equals(rwRecharge.getStatus())
                    || FlowStatus.REQUESTFAIL.getCode().equals(rwRecharge.getStatus())
                    || FlowStatus.CONFIRMFAIL.getCode().equals(rwRecharge.getStatus())) {
                logger.info("【充值异步回调】当前充值订单已为终态：" + rwRecharge.getStatus() + "，流水号：" + notityPaymentData.getPartner_serial_no());
                logger.info("【充值异步回调】不做业务处理");
                notifyConfirmQPres.setStatus(true);
                return notifyConfirmQPres;
            }

            String mall_no = accountSearchService.queryMallNoByPlatNo(rwRecharge.getPlat_no());
            String apply_order_no = null;
            if (TransConsts.QUICK_CONFIRM_CODE.equals(rwRecharge.getTrans_code())||TransConsts.QUICK_RECHARGE_CODE.equals(rwRecharge.getTrans_code())) {
                StringBuffer key=new StringBuffer(mall_no).append(TransConsts.QUICK_CONFIRM_CODE).append(rwRecharge.getOrder_no());
                logger.info("【充值异步回调】充值回调去redis获取原确认订单号，key:"+key);
                apply_order_no=CacheUtil.getCache().get(key.toString()).toString();
                logger.info("【充值异步回调】获取的充值确认订单号:"+apply_order_no);
                if(null==apply_order_no){
                    return null;
                }
                logger.info("【充值异步回调】此回调为快捷充值，订单号：" + apply_order_no);
            } else if (TransConsts.GATEWAY_RECHARGE_CODE.equals(rwRecharge.getTrans_code())) {
                apply_order_no = rwRecharge.getOrder_no();
                logger.info("【充值异步回调】此回调为网关充值，订单号：" + apply_order_no);
            }

            //查询用户信息
            // EaccUserinfo eaccUserinfo = accountSearchService.queryEaccUserInfoByEaccAccountInfo(rwRecharge.getCreate_by(), rwRecharge.getPlat_no(), rwRecharge.getPlatcust());
            requestMap.put("plat_no", rwRecharge.getPlat_no());
            requestMap.put("mall_no", mall_no);
            requestMap.put("order_no", rwRecharge.getOrder_no());
            requestMap.put("platcust", rwRecharge.getPlatcust());
            requestMap.put("type", "1");
            requestMap.put("order_amt", rwRecharge.getTrans_amt());
            requestMap.put("trans_date", rwRecharge.getTrans_date());
            requestMap.put("trans_time", rwRecharge.getTrans_time());
            requestMap.put("pay_order_no", notityPaymentData.getHsepay_order_no());//支付订单号
            requestMap.put("pay_finish_date", notityPaymentData.getPay_finish_date());
            requestMap.put("pay_finish_time", notityPaymentData.getPay_finish_time());
            requestMap.put("pay_amt", notityPaymentData.getOrder_balance());
            requestMap.put("sign", notityPaymentData.getCert_sign());
            requestMap.put("host_req_serial_no", notityPaymentData.getHost_req_serial_no());//支付通道流水号

            rwRecharge.setHsepay_order_no(notityPaymentData.getHsepay_order_no());
            rwRecharge.setHost_req_serial_no(notityPaymentData.getHost_req_serial_no());
            rwRecharge.setUpdate_time(DateUtils.today(DateUtils.DEF_FORMAT));
            rwRecharge.setConfirm_time(DateUtils.today(DateUtils.DEF_FORMAT));
            rwRecharge.setPayment_date(notityPaymentData.getPay_finish_date()==null?DateUtils.getyyyyMMddDate():notityPaymentData.getPay_finish_date());
            //如果支付状态是成功 则给用户账户加款
            if (CcbPayStatus.SUCCESS.getCode().equals(payStatus)) {
                logger.info("【充值异步回调】充值成功，流水：" + rwRecharge.getTrans_serial());
                //修改用户充值信息表状态 状态为成功
                rwRecharge.setReturn_code(BusinessMsg.SUCCESS);
                rwRecharge.setReturn_msg(OrderStatus.SUCCESS.getMsg());
                rwRecharge.setLast_time(DateUtils.today(DateUtils.DEF_FORMAT));
                rwRecharge.setStatus(FlowStatus.SUCCESS.getCode());
                rwRecharge.setNotify_msg(notityPaymentData.getRemark());
                if (TransConsts.QUICK_RECHARGE_CODE.equals(rwRecharge.getTrans_code())) {
                    rwRecharge.setTrans_code(TransConsts.QUICK_CONFIRM_CODE);
                    rwRecharge.setTrans_name(TransConsts.QUICK_CONFIRM_NAME);
                }
                requestMap.put("order_status", OrderStatus.SUCCESS.getCode());
                requestMap.put("recode", BusinessMsg.SUCCESS);
                requestMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.SUCCESS));
            } else if (CcbPayStatus.FAIL.getCode().equals(payStatus)) {
                logger.info("【充值异步回调】充值失败，流水：" + rwRecharge.getTrans_serial());
                //修改用户充值信息表状态 状态失败
                rwRecharge.setReturn_code(BusinessMsg.FAIL);
                rwRecharge.setReturn_msg(notityPaymentData.getRemark());
                rwRecharge.setLast_time(DateUtils.today(DateUtils.DEF_FORMAT));
                rwRecharge.setStatus(FlowStatus.FAIL.getCode());
                rwRecharge.setNotify_msg(notityPaymentData.getRemark());
                rwRecharge.setRemark("充值异步通知充值失败");
                //如果是快捷充值，改变交易代码为快捷充值确认
                if (TransConsts.QUICK_RECHARGE_CODE.equals(rwRecharge.getTrans_code())) {
                    rwRecharge.setTrans_code(TransConsts.QUICK_CONFIRM_CODE);
                    rwRecharge.setTrans_name(TransConsts.QUICK_CONFIRM_NAME);
                }
                requestMap.put("order_status", OrderStatus.FAIL.getCode());
                requestMap.put("error_no", BusinessMsg.PAYMENT_FAILED);
                requestMap.put("error_info", notityPaymentData.getRemark());
                requestMap.put("recode", BusinessMsg.PAYMENT_FAILED);
                requestMap.put("remsg", notityPaymentData.getRemark());
            }
            String data = JSONObject.toJSONString(requestMap);
            logger.info("【充值异步回调】异步通知平台处理数据：" + data);
            String URL = rwRecharge.getNotify_url();
            logger.info("【充值异步回调】异步地址：" + URL);
            notifyConfirmQPres.setData(data);
            notifyConfirmQPres.setStatus(true);
            notifyConfirmQPres.setURL(URL);
            Boolean doSuccess = rechargeOptionService.doRecharge(rwRecharge, mall_no, apply_order_no, "充值异步回调");
            logger.info("【快捷充值接收支付异步回调】调用doRecharge结果："+doSuccess+",申请订单号："+rwRecharge.getOrder_no()+",确认订单号:"+apply_order_no+",订单结果："+rwRecharge.getStatus());
            if (doSuccess && (OrderStatus.SUCCESS.getCode().equals(rwRecharge.getStatus())
                    || OrderStatus.FAIL.getCode().equals(rwRecharge.getStatus())
                    || OrderStatus.CONFIRMSUCCESS.getCode().equals(rwRecharge.getStatus())
                    || OrderStatus.CONFIRMFAIL.getCode().equals(rwRecharge.getStatus()))) {
                logger.info("【充值异步回调】准备发送回调:"+rwRecharge.getStatus()+",支付pay_status:"+payStatus);
                notifyConfirmQPres.setStatus(true);

                if (TransConsts.QUICK_CONFIRM_CODE.equals(rwRecharge.getTrans_code())||TransConsts.QUICK_RECHARGE_CODE.equals(rwRecharge.getTrans_code())) {
                    StringBuffer key=new StringBuffer(mall_no).append(TransConsts.QUICK_CONFIRM_CODE).append(rwRecharge.getOrder_no());
                    logger.info("【充值异步回调】充值回调去redis获取原确认订单号，key:"+key);
                    CacheUtil.getCache().del(key.toString());
                }

            } else {
                logger.info("【充值异步回调】不发送回调，存管订单状态："+rwRecharge.getStatus()+",支付pay_status:"+payStatus);
                notifyConfirmQPres.setStatus(false);
            }
            notifyConfirmQPres.setMall_no(mall_no);
            return notifyConfirmQPres;
        } catch (Exception e) {
            logger.error("【充值异步回调】异常：", e);
            notifyConfirmQPres.setStatus(false);
            return notifyConfirmQPres;
        }
    }

    @Override
    public RwRecharge queryEpayStatus(RwRecharge rwRecharge) throws BusinessException {
        logger.info("【充值订单状态查询】入参:" + rwRecharge.toString());
        //查询集团号
        String mall_no = accountSearchService.queryMallNoByPlatNo(rwRecharge.getPlat_no());
        String seqNum = SeqUtil.getSeqNum();
        //查询plat_paycode
        PlatPaycode platPayCode = accountSearchService.queryPlatPayCode(rwRecharge.getPlat_no(), rwRecharge.getPay_code());
        //获取请求URL
        logger.info("【充值订单状态查询】查询获取plat_pay_code:" + platPayCode.toString());
        String host = sysParameterService.querySysParameter(SysParamterKey.FTDM, URLConfigUtil.EPAY_SERVER_KEY);
        String url = sysParameterService.querySysParameter(SysParamterKey.FTDM, URLConfigUtil.QUERY_PAY_STATUS);
        //获取确认订单号
        TransTransreq transTransreq = transReqService.queryTransTransReqByOrderno(rwRecharge.getOrder_no());
        logger.info("【充值订单状态查询】查询获取第三方IP:" + host + url);
        Map<String, Object> data = new HashMap<>();
        data.put("partner_id", platPayCode.getPayment_plat_no());//合作商编号
        data.put("partner_serial_no", seqNum);//合作商流水，代表本次查询动作
        data.put("original_serial_no", rwRecharge.getTrans_serial());//原合作商流水号
        data.put("occur_balance", rwRecharge.getTrans_amt());
        data.put("cert_sign", "sunyard");
        data.put("host",host);
        data.put("url",url);
        //请求第三方
        try {
            Map<String,Object> resultMap = adapterService.queryPayStatus(data);
            if (null==resultMap ||null ==resultMap.get("order_status")||"".equals(resultMap.get("order_status"))) {
                logger.info("【充值订单状态查询】:远程调用失败,检查接口和ip是否通畅");
                return null;
            }
            if(null!=resultMap.get("pay_check_date") && !"".equals(resultMap.get("pay_check_date"))){
                rwRecharge.setPayment_date(resultMap.get("pay_check_date").toString());
            }else{
                rwRecharge.setPayment_date(DateUtils.getyyyyMMddDate());
            }
            if("013033".equals(resultMap.get("recode"))&& true==DateUtils.addOrSubDate(rwRecharge.getCreate_time(),Calendar.MINUTE,30).before(new Date())){
                resultMap.put("order_status",OrderStatus.FAIL.getCode());
            }
            if (OrderStatus.SUCCESS.getCode().equals(resultMap.get("order_status").toString())) {
                logger.info("请求第三方查询订单状态-----交易成功-----更新数据库状态---加款**");
                logger.info("【充值订单状态查询】订单查询成功，返回订单状态order_status:" + resultMap.get("order_status").toString() + "原充值金额:" + rwRecharge.getTrans_amt());
                rwRecharge.setStatus(OrderStatus.SUCCESS.getCode());
                rwRecharge.setUpdate_time(DateUtils.today(DateUtils.DEF_FORMAT));
                rwRecharge.setReturn_code(BusinessMsg.SUCCESS);
                rwRecharge.setReturn_msg(OrderStatus.SUCCESS.getMsg());
                //如果支付那边没传self_bank_flag标识，默认为非本行卡
                if(null==rwRecharge.getSelf_bank_flag()||"".equals(rwRecharge.getSelf_bank_flag())){
                    rwRecharge.setSelf_bank_flag(IsSelfBank.NO.getCode());
                    if(null!=resultMap.get("self_bank_flag")){
                        rwRecharge.setSelf_bank_flag("0".equals(resultMap.get("self_bank_flag"))?IsSelfBank.YES.getCode():IsSelfBank.NO.getCode());
                    }
                }
                Boolean hasRecharged = false;
                try {
                    //充值加款
                    logger.info("传入doRecharge接口参数："+JSON.toJSON(rwRecharge));
                    hasRecharged = rechargeOptionService.doRecharge(rwRecharge, mall_no, transTransreq.getOrder_no(), "queryEpayStatus");
                } catch (Exception e) {
                    logger.info("=========充值异常：", e);
                    rwRecharge.setStatus(OrderStatus.PROCESSING.getCode());
                    rwRecharge.setUpdate_time(DateUtils.today(DateUtils.DEF_FORMAT));
                }
                logger.info("【充值订单状态查询】========rechargeOptionService.doRecharge返回值:" + hasRecharged);
                //判断是否充值成功
                if (!hasRecharged) {
                    //如果返回false，说明没有加款成功,状态设置成处理中
                    logger.info("【充值订单状态查询】========调用充值加款返回false，状态设置成处理中");
                    rwRecharge.setStatus(OrderStatus.PROCESSING.getCode());
                    rwRecharge.setUpdate_time(DateUtils.today(DateUtils.DEF_FORMAT));
                }
            } else if (OrderStatus.FAIL.getCode().equals(resultMap.get("order_status").toString())) {
                logger.info("【充值订单状态查询】========该订单为交易失败" + rwRecharge.getOrder_no());
                Boolean hasRecharged = false;
                try {
                    rwRecharge.setStatus(OrderStatus.FAIL.getCode());
                    rwRecharge.setUpdate_time(DateUtils.today(DateUtils.DEF_FORMAT));
                    rwRecharge.setReturn_code(resultMap.get("recode").toString());
                    rwRecharge.setReturn_msg(resultMap.get("remsg").toString());
                    hasRecharged = rechargeOptionService.doRecharge(rwRecharge, mall_no, transTransreq.getOrder_no(), "queryEpayStatus");
                } catch (Exception e) {
                    logger.info("=========更新流水异常：", e);
                    rwRecharge.setStatus(OrderStatus.PROCESSING.getCode());
                    rwRecharge.setUpdate_time(DateUtils.today(DateUtils.DEF_FORMAT));
                }
                logger.info("【充值订单状态查询】=========rechargeOptionService.doRecharge返回值:" + hasRecharged);
                //判断是否调用成功
                if (!hasRecharged) {
                    //如果返回false，说明没有调用成功,状态设置成处理中
                    logger.info("【充值订单状态查询】=========调用充值加款返回false，状态设置成处理中");
                    rwRecharge.setStatus(OrderStatus.PROCESSING.getCode());
                    rwRecharge.setUpdate_time(DateUtils.today(DateUtils.DEF_FORMAT));
                }
            } else {
                logger.info("【充值订单状态查询】===========支付返回状态不是成功也不是失败");
            }
            logger.info("【充值订单状态查询】===========调用第三方结束，返回结果：" + rwRecharge);
        } catch (BusinessException e) {
            logger.info("【充值订单状态查询】===========调用第三方异常:" + e.getBaseResponse().getRemsg());
        }
        return rwRecharge;
    }


    //代扣充值
    @Override
    public CollectionResponse collectionService(CollectionRequest collectionRequest) throws BusinessException {
        //记录流水
        TransTransreq transTransReq = transReqService.getTransTransReq(collectionRequest.clone(), TransConsts.COLLECTION_CODE, TransConsts.COLLECTION_NAME, false);
        BaseResponse transbaseResponse= transReqService.insert(transTransReq);
        CollectionResponse collectionResponse = new CollectionResponse();
        CollectionDataResponse collectionDataResponse = new CollectionDataResponse();
        collectionDataResponse.setQuery_id(transTransReq.getTrans_serial());
        collectionDataResponse.setProcess_date(DateUtils.formatDateToStr(new Date()));
        if(null !=transbaseResponse){
/*            logger.info("【代扣充值】【"+collectionRequest.getOrder_no()+"】【记录流水插入失败，订单号重复】");
            throw new BusinessException(BusinessMsg.ORIGINAL_ORDER_NO_ERROR,BusinessMsg.getMsg(BusinessMsg.ORIGINAL_ORDER_NO_ERROR));*/
            collectionResponse.setRecode(transbaseResponse.getRecode());
            collectionResponse.setRemsg(transbaseResponse.getRemsg());
            collectionResponse.setOrder_status(transbaseResponse.getOrder_status());
            return collectionResponse;
        }
        logger.info("【代扣充值】【"+transTransReq.getTrans_serial()+"】【记录流水成功】");

        try {
            //金额校验
            if (collectionRequest.getAmt().compareTo(BigDecimal.ZERO) <= 0) {
                logger.info("【代扣充值】【" + transTransReq.getTrans_serial() + "】【金额错误】");
                throw new BusinessException(BusinessMsg.MONEY_ERROR, BusinessMsg.getMsg(BusinessMsg.MONEY_ERROR));
            }
            PlatPaycode platPayCode = accountSearchService.queryPlatPayCode(collectionRequest.getMer_no(), collectionRequest.getPay_code());
            if (null == platPayCode) {
                logger.info("【代扣充值】pay_code无效");
                throw new BusinessException(BusinessMsg.NO_CHANNEL_INFORMATION, String.format("数据库没有平台【%s】支付通道【%s】的支付映射相关信息", collectionRequest.getMer_no(), collectionRequest.getPay_code()));
            }
            //检查是否有该用户以及验证用户的绑卡信息  卡号 身份证
            EaccUserinfo eaccUserinfo = accountSearchService.checkUserinfoWithoutMobile(collectionRequest);
            logger.info("【代扣充值】eaccUserinfo：" + eaccUserinfo);
            if (CardType.CREDIT.getCode().equals(collectionRequest.getCard_type())) {
                logger.info("【代扣充值】【" + transTransReq.getTrans_serial() + "】【信用卡！目前不支持信用卡代扣充值");
                throw new BusinessException(BusinessMsg.PARAMETER_ERROR, String.format("【%s】是信用卡！目前不支持信用卡代扣充值", collectionRequest.getCard_type()));
            }

            String WEB_LOCAL_SERVER = sysParameterService.querySysParameter(SysParamterKey.FTDM, URLConfigUtil.WEB_LOCAL_SERVER);
            String NOTIFY_URL = sysParameterService.querySysParameter(SysParamterKey.FTDM, URLConfigUtil.COLLECTION_URL_NOTIFY);
            NOTIFY_URL = WEB_LOCAL_SERVER + NOTIFY_URL;
            //记录用户充值信息表
            RwRecharge rwRecharge = new RwRecharge();
            rwRecharge.setCreate_time(DateUtils.today(DateUtils.DEF_FORMAT));
            rwRecharge.setCreate_by(collectionRequest.getMall_no());
            rwRecharge.setTrans_amt(collectionRequest.getAmt());//充值金额
            rwRecharge.setReturn_url(NOTIFY_URL);//同步回调地址
            rwRecharge.setTrans_serial(transTransReq.getTrans_serial());//交易流水号 业务流水表相同
            rwRecharge.setOrder_no(collectionRequest.getOrder_no());//订单号
            rwRecharge.setPlat_no(collectionRequest.getMer_no());//平台编号
            rwRecharge.setPlatcust(collectionRequest.getPlatcust());//平台客户号
            rwRecharge.setTrans_code(TransConsts.COLLECTION_CODE);//交易代码
            rwRecharge.setTrans_name(TransConsts.COLLECTION_NAME);//交易名称
            rwRecharge.setTrans_date(collectionRequest.getPartner_trans_date());//交易日期
            rwRecharge.setTrans_time(collectionRequest.getPartner_trans_time());//交易时间
            rwRecharge.setPay_code(collectionRequest.getPay_code());//支付编号
            rwRecharge.setReques_time(DateUtils.today());//交易请求时间
            rwRecharge.setStatus(OrderStatus.PROCESSING.getCode());//处理状态
            rwRecharge.setNotify_url(collectionRequest.getNotify_url());//商户异步通知URL
            rwRecharge.setEnabled(Constants.ENABLED);//删除标记
            rwRecharge.setSelf_bank_flag(IsSelfBank.NO.getCode());//默认非本行卡
/*            if (null != eaccUserinfo.getEaccount()) {
                rwRecharge.setCharge_type(Ssubject.EACCOUNT.getCode());
            } else if (null != collectionRequest.getCharge_type()) {
                rwRecharge.setCharge_type(collectionRequest.getCharge_type());
            } else {
                rwRecharge.setCharge_type(Ssubject.INVEST.getCode());
            }*/
            //充值Charge_type默认为投资用户
/*          如果传02融资账户，则记账到融资账户在途虚户，
            如果传03电子账户，调用电子账户转账，并记账到投资账户现金虚户，
            如果传01投资账户，则记账到投资账户在途虚户；*/
            Map<String, Object> sendParams = new HashMap<String, Object>();
            rwRecharge.setCharge_type(Ssubject.INVEST.getCode());
            if(null!=collectionRequest.getCharge_type() && !"".equals(collectionRequest.getCharge_type())){
                if(Ssubject.FINANCING.getCode().equals(collectionRequest.getCharge_type())){
                    rwRecharge.setCharge_type(Ssubject.FINANCING.getCode());
                }else if(Ssubject.EACCOUNT.getCode().equals(collectionRequest.getCharge_type())||null!=eaccUserinfo.getEaccount()){
                    sendParams.put("cr_acct", eaccUserinfo.getEaccount());//电子账号
                    sendParams.put("cr_acct_type", 0);//0 借记卡
                    sendParams.put("cr_acct_name", Ssubject.EACCOUNT.getCode());//03 用户电子账户
                    rwRecharge.setCharge_type(Ssubject.EACCOUNT.getCode());
                }else {
                    rwRecharge.setCharge_type(Ssubject.INVEST.getCode());
                }
            }
            rwRechargeMapper.insert(rwRecharge);

    /*        String lockKey = getLockKey(collectionRequest.getOrder_no());
            boolean lock = CacheUtil.getLock(lockKey);
            while (!lock) {
                sleep(100);//没取到锁50毫秒后重试
                lock = CacheUtil.getLock(lockKey);
            }*/

            //设置请求参数 如果有电子账户  这三个参数是必传的 如果没有电子账户 则根据入参充值到相应账户
/*            if (null != eaccUserinfo.getEaccount()) {
                sendParams.put("cr_acct", eaccUserinfo.getEaccount());//电子账号
                sendParams.put("cr_acct_type", 0);//0 借记卡
                sendParams.put("cr_acct_name", Ssubject.EACCOUNT.getCode());//03 用户电子账户
            }*/
            sendParams.put("partner_id", platPayCode.getPayment_plat_no());
            sendParams.put("partner_serial_no", rwRecharge.getTrans_serial());//平台流水
            sendParams.put("partner_trans_date", collectionRequest.getPartner_trans_date());
            sendParams.put("partner_trans_time", collectionRequest.getPartner_trans_time());
            sendParams.put("prod_code", TransConsts.COLLECTION_CODE);//产品编号
            sendParams.put("prod_name", TransConsts.COLLECTION_NAME);//产品名称
            sendParams.put("currency_code", collectionRequest.getCurrency_code()); //币种
            sendParams.put("summary", collectionRequest.getRemark());//摘要
            sendParams.put("client_name", collectionRequest.getName());//客户姓名
            sendParams.put("card_no", collectionRequest.getCard_no());//银行卡号
            sendParams.put("id_no", collectionRequest.getId_code());//证件号
            sendParams.put("receive_url", rwRecharge.getReturn_url());//后台回调地址 异步通知地址
            sendParams.put("channelId", platPayCode.getChannel_id());//支付通道
            sendParams.put("verify_mode", IsUse.YES.getCode());//验证模式 1需要短信验证 0不需要
            sendParams.put("cert_sign", "quick_pay_test");//签名串
            sendParams.put("mobile_tel", collectionRequest.getMobile());//手机号
            sendParams.put("partner_userid", collectionRequest.getPlatcust());//商户用户编号
            //证件类型  如果id_type是1  则id_kind是0
            if (IdType.IDENTITY_CARD.getCode().equals(collectionRequest.getId_type())) {
                sendParams.put("id_kind", "0");
            } else {
                sendParams.put("id_kind", collectionRequest.getId_type());
            }
            sendParams.put("occur_balance", collectionRequest.getAmt());//金额

            String host = sysParameterService.querySysParameter(collectionRequest.getMall_no(), URLConfigUtil.EPAY_SERVER_KEY);
            String url = sysParameterService.querySysParameter(collectionRequest.getMall_no(), URLConfigUtil.COLLECTION_URL);
            sendParams.put("host", host);
            sendParams.put("url", url);

            logger.info("【代扣充值】【" + transTransReq.getTrans_serial() + "】【请求的数据: " + JSON.toJSON(sendParams) + "】");
            RwRecharge rwRechargeForCharge = accountSearchService.queryRwRecharge(collectionRequest.getMer_no(), collectionRequest.getOrder_no());

            try {
                Map<String, Object> resultMap = adapterService.payFromUserToCompany(sendParams);
                logger.info("【代扣充值】三方接口响应参数" + JSON.toJSON(resultMap));
                if (null == resultMap || null == resultMap.get("order_status") || "".equals(resultMap.get("order_status"))) {
                    logger.info("【代扣充值】【" + transTransReq.getTrans_serial() + "】【第三方接口返回值解析失败】");
                    throw new BusinessException(BusinessMsg.CALL_REMOTE_ERROR,
                            BusinessMsg.getMsg(BusinessMsg.CALL_REMOTE_ERROR) + "：第三方接口返回值解析失败");
                }
                //String order_status = resultMap.get("order_status").toString();
//                collectionResponse = updateForProcessing(resultMap, collectionRequest, rwRecharge);

                logger.info("【代扣充值】=========获取支付响应完成：" + collectionRequest.getOrder_no()+"======响应结果："+JSON.toJSON(resultMap));
                if(null==resultMap||null==resultMap.get("order_status")){
                    logger.info("【代扣充值】=========返回参数不正确,确认订单：" + collectionRequest.getOrder_no());
                    collectionResponse.setRecode(BusinessMsg.CALL_REMOTE_ERROR);
                    collectionResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.CALL_REMOTE_ERROR));
                    return collectionResponse;
                }
                String order_status=resultMap.get("order_status").toString();
                if(null!=resultMap.get("pay_finish_date") && !"".equals(resultMap.get("pay_finish_date"))){
                    rwRechargeForCharge.setPayment_date(resultMap.get("pay_finish_date").toString());
                }else{
                    rwRecharge.setPayment_date(DateUtils.getyyyyMMddDate());
                }
                if(null!=resultMap.get("self_bank_flag") && !"".equals(resultMap.get("self_bank_flag"))){
                    rwRechargeForCharge.setSelf_bank_flag(IsSelfBank.YES.getCode().equals(resultMap.get("self_bank_flag").toString())?IsSelfBank.YES.getCode():IsSelfBank.NO.getCode());
                }
                collectionDataResponse.setOrder_status(order_status);
                rwRechargeForCharge.setStatus(order_status);
                if(OrderStatus.SUCCESS.getCode().equals(order_status)){
                    logger.info("【代扣充值】===========第三方支付返回状态交易成功");
                    //如果充值成功 向用户账户表中添加金额
                    rwRechargeForCharge.setLast_time(DateUtils.today(DateUtils.DEF_FORMAT));
                    rwRechargeForCharge.setReturn_msg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));
                    rwRechargeForCharge.setReturn_code(BusinessMsg.SUCCESS);

                    rwRechargeForCharge.setUpdate_by(collectionRequest.getMer_no());
                    rwRechargeForCharge.setOrder_no(collectionRequest.getOrder_no());
                    rwRechargeForCharge.setConfirm_time(DateUtils.today(DateUtils.DEF_FORMAT));
                    rwRechargeForCharge.setUpdate_time(DateUtils.today());
                    collectionResponse.setRecode(resultMap.get("recode").toString());
                    collectionResponse.setRemsg(resultMap.get("remsg").toString());
                } else if (OrderStatus.PROCESSING.getCode().equals(order_status)) {
                    //更新充值信息
                    logger.info("【代扣充值】===========第三方支付返回状态处理中");
                    rwRechargeForCharge.setReturn_msg(resultMap.get("remsg").toString());
                    rwRechargeForCharge.setReturn_code(resultMap.get("recode").toString());
                    collectionResponse.setRecode(resultMap.get("recode").toString());
                    collectionResponse.setRemsg(resultMap.get("remsg").toString());
                } else if (OrderStatus.FAIL.getCode().equals(order_status)) {
                    collectionDataResponse.setError_info(resultMap.get("remsg").toString());
                    collectionDataResponse.setError_no(resultMap.get("recode").toString());
                    rwRechargeForCharge.setReturn_msg(resultMap.get("remsg")==null?BusinessMsg.getMsg(BusinessMsg.PAYMENT_FAILED):BusinessMsg.getMsg(BusinessMsg.PAYMENT_FAILED)+":"+resultMap.get("remsg").toString());
                    rwRechargeForCharge.setReturn_code(BusinessMsg.PAYMENT_FAILED);
                    logger.info("【代扣充值】===========第三方支付返回状态交易失败");
                    collectionResponse.setRecode(resultMap.get("recode").toString());
                    collectionResponse.setRemsg(resultMap.get("remsg").toString());
                } else {
                    rwRechargeForCharge.setReturn_msg(BusinessMsg.getMsg(BusinessMsg.UNKNOW_ERROE));
                    rwRechargeForCharge.setReturn_code(BusinessMsg.UNKNOW_ERROE);
                    collectionDataResponse.setError_info(BusinessMsg.getMsg(BusinessMsg.UNKNOW_ERROE));
                    collectionDataResponse.setError_no(BusinessMsg.UNKNOW_ERROE);
                    collectionDataResponse.setOrder_status(OrderStatus.FAIL.getCode());
                    collectionResponse.setRecode(BusinessMsg.UNKNOW_ERROE);
                    collectionResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.UNKNOW_ERROE));
                }
                //调用doRecharge接口
                logger.info("请求dorecharge参数："+rwRechargeForCharge.toString()+",确认订单号："+collectionRequest.getOrder_no());

                Boolean flag = rechargeOptionService.doRecharge(rwRechargeForCharge, collectionRequest.getMall_no(),collectionRequest.getOrder_no(), TransConsts.COLLECTION_CODE);
                logger.info("返回flag："+flag);
                if (!flag) {
                    // 陈瑞接口返回false ，表示处理中
                    transTransReq.setStatus(FlowStatus.INPROCESS.getCode());
                    transTransReq.setReturn_code(BusinessMsg.SUCCESS);
                    transTransReq.setReturn_msg(BusinessMsg.getMsg(BusinessMsg.SUCCESS) + "：充值订单处理中");
                } else {
                    if(OrderStatus.SUCCESS.getCode().equals(order_status)){
                        transTransReq.setStatus(FlowStatus.CONFIRMSUCCESS.getCode());
                    }else if(OrderStatus.FAIL.getCode().equals(order_status)){
                        transTransReq.setStatus(FlowStatus.CONFIRMFAIL.getCode());
                    }else{
                        transTransReq.setStatus(FlowStatus.INPROCESS.getCode());
                    }
                }
                transReqService.insert(transTransReq);
                collectionResponse.setData_detail(collectionDataResponse);
                logger.info("【代扣充值】===========同步响应结果："+collectionResponse.toString());
            } catch (Exception e) {
                logger.error("【代扣充值】调用第三方异常:", e);
                BaseResponse baseResponse = new BaseResponse();
                if (e instanceof BusinessException) {
                    baseResponse = ((BusinessException) e).getBaseResponse();
                } else {
                    baseResponse.setRecode(BusinessMsg.DATEBASE_EXCEPTION);
                    baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.DATEBASE_EXCEPTION));
                }
                collectionResponse.setRecode(baseResponse.getRecode());
                collectionResponse.setRemsg(baseResponse.getRemsg());
                collectionDataResponse.setOrder_status(OrderStatus.PROCESSING.getCode());
                collectionResponse.setData_detail(collectionDataResponse);
                transTransReq.setStatus(FlowStatus.INPROCESS.getCode());
                transTransReq.setReturn_code(baseResponse.getRecode());
                transTransReq.setReturn_msg(baseResponse.getRemsg());
                transReqService.insert(transTransReq);
            } /*finally {
            CacheUtil.unlock(lockKey);
        }*/
        }catch (Exception e){
            logger.error("【代扣充值】调用第三方之前异常:", e);
            BaseResponse baseResponse = new BaseResponse();
            if (e instanceof BusinessException) {
                baseResponse = ((BusinessException) e).getBaseResponse();
            } else {
                baseResponse.setRecode(BusinessMsg.DATEBASE_EXCEPTION);
                baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.DATEBASE_EXCEPTION));
            }
            collectionResponse.setRecode(baseResponse.getRecode());
            collectionResponse.setRemsg(baseResponse.getRemsg());
            collectionDataResponse.setOrder_status(OrderStatus.FAIL.getCode());
            collectionResponse.setData_detail(collectionDataResponse);
            transTransReq.setStatus(FlowStatus.FAIL.getCode());
            transTransReq.setReturn_code(BusinessMsg.FAIL);
            transTransReq.setReturn_msg(BusinessMsg.getMsg(BusinessMsg.FAIL));
            transReqService.insert(transTransReq);
        }
        return collectionResponse;
    }


    /*@Transactional
    public CollectionResponse updateForProcessing(Map<String, Object> resultMap, CollectionRequest collectionRequest, RwRecharge rwRecharge) throws BusinessException {
        CollectionResponse collectionResponse = new CollectionResponse();
        collectionResponse.setOrder_no(rwRecharge.getOrder_no());
        CollectionDataResponse collectionDataResponse = new CollectionDataResponse();
        collectionDataResponse.setQuery_id(rwRecharge.getTrans_serial());
        collectionDataResponse.setProcess_date(DateUtils.todayfulldata());
        TransTransreq transTransReq = new TransTransreq();
        transTransReq.setTrans_serial(rwRecharge.getTrans_serial());

        rwRecharge.setUpdate_time(DateUtils.today());
        rwRecharge.setConfirm_time(DateUtils.today(DateUtils.DEF_FORMAT));
        rwRecharge.setLast_time(DateUtils.today(DateUtils.DEF_FORMAT));
        if (null != resultMap.get("pay_finish_date") && !"".equals(resultMap.get("pay_finish_date"))) {
            rwRecharge.setPayment_date(resultMap.get("pay_finish_date").toString());
        }
        String order_status = resultMap.get("order_status").toString();
        String return_code = MapUtils.getString(resultMap, "recode", "");
        String return_msg = MapUtils.getString(resultMap, "remsg", "");
        if (OrderStatus.SUCCESS.getCode().equals(order_status)) {
            //交易成功
            logger.info("【代扣充值】【" + rwRecharge.getTrans_serial() + "】【交易成功】");
            //更新充值信息表 请求确认时间
            rwRecharge.setReturn_code(OrderStatus.SUCCESS.getCode());
            rwRecharge.setReturn_msg(OrderStatus.SUCCESS.getMsg());
            rwRecharge.setStatus(OrderStatus.SUCCESS.getCode());
            int isUpdate = updateReCharge(rwRecharge);
            if (isUpdate == 1) {
                //============代扣充值加款============
                EaccAccountamtlist eaccAccountamtlist = new EaccAccountamtlist();
                eaccAccountamtlist.setOrder_no(rwRecharge.getOrder_no());
                eaccAccountamtlist.setTrans_serial(rwRecharge.getTrans_serial());
                if (null != rwRecharge && Ssubject.EACCOUNT.getCode().equals(rwRecharge.getCharge_type())) {
                    eaccAccountamtlist.setPlat_no(collectionRequest.getMall_no());
                } else {
                    eaccAccountamtlist.setPlat_no(rwRecharge.getPlat_no());
                }
                eaccAccountamtlist.setPlatcust(rwRecharge.getPlatcust());
                eaccAccountamtlist.setSubject(Tsubject.FLOAT.getCode());
                eaccAccountamtlist.setSub_subject(rwRecharge.getCharge_type());
                eaccAccountamtlist.setAmt(rwRecharge.getTrans_amt());
                eaccAccountamtlist.setTrans_code(TransConsts.COLLECTION_CODE);
                eaccAccountamtlist.setTrans_name(TransConsts.COLLECTION_NAME);
                eaccAccountamtlist.setTrans_date(rwRecharge.getTrans_date());
                eaccAccountamtlist.setTrans_time(rwRecharge.getTrans_time());
                eaccAccountamtlist.setPay_code(rwRecharge.getPay_code());
                eaccAccountamtlist.setAmt_type(AmtType.INCOME.getCode());
                eaccAccountamtlist.setCreate_by(rwRecharge.getPlat_no());
//                newAccountTransferService.singleCharge(eaccAccountamtlist);
                accountAssetService.charge(eaccAccountamtlist);
                logger.info("【代扣充值】【" + rwRecharge.getTrans_serial() + "】【加款成功】");
                //更新业务流水为请求成功
                transTransReq.setStatus(FlowStatus.SUCCESS.getCode());
                transTransReq.setReturn_code(OrderStatus.SUCCESS.getCode());
                transTransReq.setReturn_msg(OrderStatus.SUCCESS.getMsg());
                transReqService.insert(transTransReq);
            }
            //返回订单状态
            collectionResponse.setRecode(BusinessMsg.SUCCESS);
            collectionResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));
            collectionDataResponse.setOrder_status(OrderStatus.SUCCESS.getCode());
            collectionResponse.setData_detail(collectionDataResponse);
        } else if (OrderStatus.FAIL.getCode().equals(order_status)) {
            //交易失败
            logger.info("【代扣充值】【" + rwRecharge.getTrans_serial() + "】【交易失败】");
            //更新充值信息表
            rwRecharge.setReturn_code(return_code);
            rwRecharge.setReturn_msg(return_msg);
            rwRecharge.setStatus(OrderStatus.FAIL.getCode());
            custRwRechargeMapper.updateByTransSerialSelective(rwRecharge);
            //更新业务流水为请求成功
            transTransReq.setStatus(FlowStatus.FAIL.getCode());
            transTransReq.setReturn_code(return_code);
            transTransReq.setReturn_msg(return_msg);
            transReqService.insert(transTransReq);
            //返回订单状态
            collectionResponse.setRecode(return_code);
            collectionResponse.setRemsg(return_msg);
            collectionDataResponse.setOrder_status(OrderStatus.FAIL.getCode());
            collectionDataResponse.setError_no(return_code);
            collectionDataResponse.setError_info(return_msg);
            collectionResponse.setData_detail(collectionDataResponse);
        } else {
            //交易处理中
            logger.info("【代扣充值】【" + rwRecharge.getTrans_serial() + "】【交易处理中】");
            //更新充值信息表
            rwRecharge.setReturn_code(return_code);
            rwRecharge.setReturn_msg(return_msg);
            rwRecharge.setStatus(OrderStatus.PROCESSING.getCode());
            custRwRechargeMapper.updateByTransSerialSelective(rwRecharge);
            transTransReq.setStatus(FlowStatus.INPROCESS.getCode());
            transTransReq.setReturn_code(return_code);
            transTransReq.setReturn_msg(return_msg);
            transReqService.insert(transTransReq);
            //返回订单状态
            collectionResponse.setRecode(return_code);
            collectionResponse.setRemsg(return_msg);
            collectionDataResponse.setOrder_status(OrderStatus.PROCESSING.getCode());
            collectionDataResponse.setError_no(return_code);
            collectionDataResponse.setError_info(return_msg);
            collectionResponse.setData_detail(collectionDataResponse);
        }
        return collectionResponse;
    }

    private Integer updateReCharge(RwRecharge rwRecharge)throws BusinessException{
        RwRechargeExample rwRechargeExample=new RwRechargeExample();
        RwRechargeExample.Criteria criteria=rwRechargeExample.createCriteria();
        criteria.andTrans_serialEqualTo(rwRecharge.getTrans_serial());
        criteria.andStatusEqualTo(FlowStatus.INPROCESS.getCode());
        criteria.andOrder_noEqualTo(rwRecharge.getOrder_no());
        criteria.andEnabledEqualTo(Constants.ENABLED);
        return rwRechargeMapper.updateByExampleSelective(rwRecharge,rwRechargeExample);
    }
*/
    /**
     * 借款人线下还款
     *
     * @author bobguo
     */
    @Override
    public OfferChargeResponse repayOffLine(RepayOffLineRequest repayOffLineRequest) throws BusinessException {

        TransTransreq transTransReq = null;
        //记录借款人线下还款业务流水表 trans_transreq
        transTransReq = transReqService.getTransTransReq(repayOffLineRequest.clone(), TransConsts.BORROW_REPAY_CODE, TransConsts.BORROW_REPAY_NAME, false);
        String trans_serial = transTransReq.getTrans_serial();
        transReqService.insert(transTransReq);
        logger.info("记录借款人线下还款流水成功");

        OfferChargeResponse offerChargeResponse = new OfferChargeResponse();
        OrderData orderData = new OrderData();
        offerChargeResponse.setOrderData(orderData);


        try {
            //校验充值金额是否合规
            if (repayOffLineRequest.getAmt() == null || repayOffLineRequest.getAmt().compareTo(BigDecimal.ZERO) < 1) {
                throw new BusinessException(BusinessMsg.RECHARGE_WRONGFUL, BusinessMsg.getMsg(BusinessMsg.RECHARGE_WRONGFUL));
            }

            //校验总金额与明细金额是否匹配
            List<PlatcustListDetail> platcustList = repayOffLineRequest.getPlatcustlistdetail();
            BigDecimal listAmt = BigDecimal.ZERO;

            if (null == platcustList || 0 == platcustList.size()) {
                throw new BusinessException(BusinessMsg.PARAMETER_LACK, "==========platcustlistdetail>>：" + BusinessMsg.getMsg(BusinessMsg.PARAMETER_LACK));
            }

            for (PlatcustListDetail platcustListDetail : platcustList) {
                //校验账户是否存在
                AccountSubjectInfo accountSubjectInfo = accountQueryService.queryAccount(repayOffLineRequest.getMer_no(), platcustListDetail.getPlatcust(), Tsubject.CASH.getCode(), Ssubject.FINANCING.getCode());
                if (null == accountSubjectInfo) {
                    throw new BusinessException(BusinessMsg.PARAMETER_ERROR, BusinessMsg.getMsg(BusinessMsg.PARAMETER_ERROR) + "【" + platcustListDetail.getPlatcust() + "】不存在");
                }
                //效验金额大于0
                if (platcustListDetail.getAmt() == null || platcustListDetail.getAmt().compareTo(BigDecimal.ZERO) < 1) {
                    throw new BusinessException(BusinessMsg.RECHARGE_WRONGFUL, BusinessMsg.getMsg(BusinessMsg.RECHARGE_WRONGFUL));
                }
                listAmt = listAmt.add(platcustListDetail.getAmt());
            }

            //校验总金额
            if (listAmt.compareTo(repayOffLineRequest.getAmt()) != 0) {
                throw new BusinessException(BusinessMsg.MONEY_ERROR, BusinessMsg.getMsg(BusinessMsg.MONEY_ERROR) + "总金额与明细金额不匹配");
            }
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("mall_no", repayOffLineRequest.getMall_no());
            params.put("plat_no", repayOffLineRequest.getMer_no());
            params.put("order_no", repayOffLineRequest.getOrder_no());
            params.put("amount", repayOffLineRequest.getAmt());
            params.put("notify_url", repayOffLineRequest.getNotify_url());
            params.put("trans_serial", trans_serial);
            params.put("partner_trans_date", repayOffLineRequest.getPartner_trans_date());
            params.put("partner_trans_time", repayOffLineRequest.getPartner_trans_time());
            //异步回调地址，数据库需配置
            String host = sysParameterService.querySysParameter(SysParamterKey.FTDM, URLConfigUtil.WEB_LOCAL_SERVER);
            String ownUrl = sysParameterService.querySysParameter(SysParamterKey.FTDM, URLConfigUtil.REPAY_OFFLINE_NOTIFY_URL);
            ownUrl = host + ownUrl;
            logger.info("异步回调url:" + ownUrl);
            params.put("ownUrl", ownUrl);//记录我的web地址
            params.put("trans_serial", params.get("trans_serial").toString());
            //行内划转pay_code 020
            PlatPaycode platPayCode = accountSearchService.queryPlatPayCode(repayOffLineRequest.getMer_no(), "020");
            if (null == platPayCode) {
                throw new BusinessException(BusinessMsg.DATEBASE_EXCEPTION, "未查询到行内划转渠道配置信息");
            }
            String partner_id = platPayCode.getPayment_plat_no();

            //String partner_id = getPayment_plat_no(params.get("plat_no").toString());

            Map<String, Object> eMap = new HashMap<String, Object>();
            //出金一方
            String payNoJson = platCacheService.queryCardInfoToCache(params.get("plat_no").toString(), PlatAccType.PLATCLEAR.getCode());
            JSONObject payNoJsonObj = JSON.parseObject(payNoJson);
            //入金一方
            String cardJson = platCacheService.queryCardInfoToCache(params.get("plat_no").toString(), PlatAccType.PLATTRUST.getCode());
            JSONObject cardJsonObj = JSON.parseObject(cardJson);
            eMap.put("partner_id", partner_id);
            eMap.put("partner_serial_no", params.get("trans_serial").toString());
            eMap.put("partner_trans_date", params.get("partner_trans_date").toString());
            eMap.put("partner_trans_time", params.get("partner_trans_time").toString());
            eMap.put("occur_balance", new BigDecimal(params.get("amount").toString()));
            if (params.get("order_no").toString().length() > 15) {
                eMap.put("summary", params.get("order_no").toString().substring(params.get("order_no").toString().length() - 10));
            } else {
                eMap.put("summary", params.get("order_no").toString());
            }
            eMap.put("bank_id", "04031000");
            eMap.put("card_no", cardJsonObj.get("card_no").toString());//入金一方卡号
            eMap.put("client_name", cardJsonObj.get("card_name").toString());//入金一方户名
            eMap.put("dr_acct", payNoJsonObj.get("card_no").toString());//出金一方卡号
            eMap.put("dr_acct_name", payNoJsonObj.get("card_name").toString());//出金一方户名
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
                trandata_map.put("seq_no", trans_serial);
                trandata_map.put("tran_amt", repayOffLineRequest.getAmt());
                trandata.add(trandata_map);
                eMap.put("trandata", trandata);
            }
            String host2 = sysParameterService.querySysParameter(SysParamterKey.FTDM, URLConfigUtil.EPAY_SERVER_KEY);
            String url = sysParameterService.querySysParameter(SysParamterKey.FTDM, URLConfigUtil.AGENT_PAY_REAL);
            eMap.put("host", host2);
            eMap.put("url", url);

            params.put("requestTime", new Date());//记录请求开始时间
            params.put("bank_no", payNoJsonObj.get("card_no").toString());

            //调E支付的
            logger.info("======准备向E支付发送借款人线下还款请求的数据:eMap>> " + JSON.toJSON(eMap));
            Map<String, Object> resultMap = adapterService.transferOfAccountInBank(eMap);
            logger.info("===========【借款人线下还款】收到三方响应参数：" + resultMap);

            logger.info("======E支付返回数据：" + JSON.toJSON(resultMap));
            if (null == resultMap || "".equals(resultMap.get("order_status"))) {
                logger.info("===========【借款人线下还款】三方响应为空，");
                throw new BusinessException(BusinessMsg.CALL_REMOTE_ERROR, BusinessMsg.getMsg(BusinessMsg.CALL_REMOTE_ERROR) + "：第三方接口调用失败");
            }

            RwRecharge rwRecharge = null;
            List<RwRecharge> rwRecharges = new ArrayList<RwRecharge>();

            List<EaccAccountamtlist> eaccAccountamtlistList = new ArrayList<EaccAccountamtlist>();
            String order_status = resultMap.get("order_status").toString();
            orderData.setOrder_status(order_status);
            if (OrderStatus.PROCESSING.getCode().equals(order_status)) {//处理中
                //插入充值表
                for (PlatcustListDetail platcustListDetail : platcustList) {
                    rwRecharge = getRechange(params, "0", platcustListDetail.getPlatcust(), OrderStatus.PROCESSING.getCode(),
                            platcustListDetail.getAmt(), TransConsts.BORROW_REPAY_CODE, TransConsts.BORROW_REPAY_NAME,
                            MapUtils.getString(params, "host_req_serial_no", ""), false, Ssubject.FINANCING.getCode());
                    rwRecharge.setSelf_bank_flag(IsSelfBank.YES.getCode());//默认本行卡
                    if(null!=resultMap.get("pay_finish_date") && !"".equals(resultMap.get("pay_finish_date"))){
                        rwRecharge.setPayment_date(resultMap.get("pay_finish_date").toString());
                    }else{
                        rwRecharge.setPayment_date(DateUtils.getyyyyMMddDate());
                    }
                    rwRecharges.add(rwRecharge);
                }
                custRwRechargeMapper.insertBatch(rwRecharges);
                logger.info("============插入处理中的充值表完成============");
                offerChargeResponse.setRecode(BusinessMsg.SUCCESS);
                offerChargeResponse.setOrder_status(OrderStatus.PROCESSING.getCode());
                offerChargeResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));
                transTransReq.setReturn_code(BusinessMsg.SUCCESS);
                transTransReq.setReturn_msg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));
                transTransReq.setStatus(FlowStatus.REQUESTSUCCESS.getCode());
            } else if (OrderStatus.SUCCESS.getCode().equals(order_status)) {//交易成功
                //插入充值表
                for (PlatcustListDetail platcustListDetail : platcustList) {
                    rwRecharge = getRechange(params, "0", platcustListDetail.getPlatcust(), OrderStatus.SUCCESS.getCode(), platcustListDetail.getAmt(), TransConsts.BORROW_REPAY_CODE, TransConsts.BORROW_REPAY_NAME,
                            MapUtils.getString(params, "host_req_serial_no", ""), true, Ssubject.FINANCING.getCode());
                    rwRecharge.setSelf_bank_flag(IsSelfBank.YES.getCode());//默认非本行卡
                    if(null!=resultMap.get("pay_finish_date") && !"".equals(resultMap.get("pay_finish_date"))){
                        rwRecharge.setPayment_date(resultMap.get("pay_finish_date").toString());
                    }else{
                        rwRecharge.setPayment_date(DateUtils.getyyyyMMddDate());
                    }
                    rwRecharges.add(rwRecharge);

                    AccountSubjectInfo eplatcust = accountQueryService.queryFINANCINGPlatAccount(rwRecharge.getPlat_no(), rwRecharge.getPlatcust(), AccountType.ElectronicAccount.getCode(),
                            Tsubject.CASH.getCode(), Ssubject.EACCOUNT.getCode());

                    EaccAccountamtlist eaccAccountamtlist = new EaccAccountamtlist();
                    eaccAccountamtlist.setOrder_no(repayOffLineRequest.getOrder_no());
                    eaccAccountamtlist.setTrans_serial(trans_serial);
                    eaccAccountamtlist.setPlat_no(repayOffLineRequest.getMer_no());
                    eaccAccountamtlist.setPlatcust(platcustListDetail.getPlatcust());
                    eaccAccountamtlist.setSubject(Tsubject.CASH.getCode());
                    eaccAccountamtlist.setSub_subject(Ssubject.FINANCING.getCode());
                    eaccAccountamtlist.setAmt(platcustListDetail.getAmt());
                    eaccAccountamtlist.setTrans_code(TransConsts.BORROW_REPAY_CODE);
                    eaccAccountamtlist.setTrans_name(TransConsts.BORROW_REPAY_NAME);
                    eaccAccountamtlist.setTrans_date(DateUtils.todayStr(DateUtils.DEF_FORMAT_NOTIME));
                    eaccAccountamtlist.setTrans_time(DateUtils.todayStr("HHmmss"));
                    eaccAccountamtlist.setPay_code("020");
                    if (null != params.get("remark") && !"".equals(params.get("remark"))) {
                        eaccAccountamtlist.setRemark(params.get("remark").toString());
                    }
                    eaccAccountamtlistList.add(eaccAccountamtlist);
                }
                custRwRechargeMapper.insertBatch(rwRecharges);
                logger.info("============插入交易成功的充值表完成============");
                //投资人批量加款
                accountAssetService.batchCharge(eaccAccountamtlistList);
                //accountAssetService.batchCharge(eaccAccountamtlistList);
                logger.info("===============借款人线下还款>>借款人批量加款成功==============");

                offerChargeResponse.setRecode(BusinessMsg.SUCCESS);
                offerChargeResponse.setOrder_status(OrderStatus.SUCCESS.getCode());
                offerChargeResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));
                transTransReq.setReturn_code(BusinessMsg.SUCCESS);
                transTransReq.setReturn_msg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));
                transTransReq.setStatus(FlowStatus.SUCCESS.getCode());

            } else if (OrderStatus.FAIL.getCode().equals(order_status)) {//交易失败
                //插入充值表
                for (PlatcustListDetail platcustListDetail : platcustList) {
                    rwRecharge = getRechange(params, "0", platcustListDetail.getPlatcust(), OrderStatus.FAIL.getCode(),
                            platcustListDetail.getAmt(), TransConsts.BORROW_REPAY_CODE, TransConsts.BORROW_REPAY_NAME,
                            MapUtils.getString(params, "host_req_serial_no", ""), true, Ssubject.FINANCING.getCode());
                    rwRecharge.setSelf_bank_flag(IsSelfBank.YES.getCode());//默认非本行卡
                    if(null!=resultMap.get("pay_finish_date") && !"".equals(resultMap.get("pay_finish_date"))){
                        rwRecharge.setPayment_date(resultMap.get("pay_finish_date").toString());
                    }else{
                        rwRecharge.setPayment_date(DateUtils.getyyyyMMddDate());
                    }
                    rwRecharges.add(rwRecharge);
                }
                custRwRechargeMapper.insertBatch(rwRecharges);
                logger.info("============插入交易失败的充值表完成============");
                offerChargeResponse.setRecode(MapUtils.getString(params, "recode", BusinessMsg.FAIL));
                offerChargeResponse.setOrder_status(OrderStatus.FAIL.getCode());
                offerChargeResponse.setRemsg(MapUtils.getString(params, "remsg", BusinessMsg.getMsg(BusinessMsg.FAIL)));
                transTransReq.setReturn_code(BusinessMsg.FAIL);
                transTransReq.setReturn_msg(BusinessMsg.getMsg(BusinessMsg.FAIL));
                transTransReq.setStatus(FlowStatus.FAIL.getCode());
            }
            offerChargeResponse.setOrderData(orderData);
            //更新流水
            transReqService.insert(transTransReq);
            try{
                if (null != orderData.getOrder_status() && !"".equals(orderData.getOrder_status())&&!OrderStatus.PROCESSING.getCode().equals(orderData.getOrder_status())) {
                    logger.info("开始发送借款人线下充值异步通知");
                    //发送借款人线下还款异步通知
                    OfferPlatAsynRequest offerPlatAsynRequest = new OfferPlatAsynRequest();
                    offerPlatAsynRequest.setPlat_no(repayOffLineRequest.getMer_no());
                    offerPlatAsynRequest.setAmt(repayOffLineRequest.getAmt());
                    offerPlatAsynRequest.setOrder_no(repayOffLineRequest.getOrder_no());
                    offerPlatAsynRequest.setMall_no(repayOffLineRequest.getMall_no());
                    offerPlatAsynRequest.setData_detail(repayOffLineRequest.getPlatcustlistdetail());
                    offerPlatAsynRequest.setCode(orderData.getOrder_status());
                    String asynRqString = JSON.toJSONString(offerPlatAsynRequest, GlobalConfig.serializerFeature);
                    if (OrderStatus.SUCCESS.getCode().equals(orderData.getOrder_status())) {//交易成功
                        offerPlatAsynRequest.setMsg(OrderStatus.SUCCESS.getMsg());
                    } else if (OrderStatus.FAIL.getCode().equals(orderData.getOrder_status())) {//交易失败
                        offerPlatAsynRequest.setMsg(OrderStatus.FAIL.getMsg());
                    }
                    logger.info("【准备发送借款人线下还款异步通知】=============url:" + repayOffLineRequest.getNotify_url() + ",json:" + asynRqString);
                    NotifyData notifyData = new NotifyData();
                    notifyData.setMall_no(repayOffLineRequest.getMall_no());
                    notifyData.setNotifyUrl(repayOffLineRequest.getNotify_url());
                    notifyData.setNotifyContent(asynRqString);
                    notifyService.addNotify(notifyData);
                }
            }catch (Exception e){
                logger.info("异步通知发送失败，订单号：" + repayOffLineRequest.getOrder_no(), e);
            }
        } catch (Exception e) {
            logger.error("【借款人线下充值】异常", e);
            if (e instanceof BusinessException) {
                BaseResponse baseResponse = ((BusinessException) e).getBaseResponse();
                transTransReq.setReturn_code(baseResponse.getRecode());
                transTransReq.setReturn_msg(baseResponse.getRemsg());
                transTransReq.setStatus(FlowStatus.FAIL.getCode());
                transReqService.insert(transTransReq);
                orderData.setOrder_status(OrderStatus.FAIL.getCode());
                offerChargeResponse.setRecode(baseResponse.getRecode());
                offerChargeResponse.setOrder_status(OrderStatus.FAIL.getCode());
                offerChargeResponse.setRemsg(baseResponse.getRemsg());
            } else {
                transTransReq.setReturn_code(BusinessMsg.DATEBASE_EXCEPTION);
                transTransReq.setReturn_msg(BusinessMsg.getMsg(BusinessMsg.DATEBASE_EXCEPTION));
                transTransReq.setStatus(FlowStatus.INPROCESS.getCode());
                transReqService.insert(transTransReq);
                orderData.setOrder_status(OrderStatus.FAIL.getCode());
                offerChargeResponse.setRecode(BusinessMsg.DATEBASE_EXCEPTION);
                offerChargeResponse.setOrder_status(OrderStatus.FAIL.getCode());
                offerChargeResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.DATEBASE_EXCEPTION));
            }
        }
        return offerChargeResponse;
    }

    /**
     * 借款人线下还款异步回调
     *
     * @param params
     * @return RepayOffLineNotifyResponse
     * @throws BusinessException
     * @author bobguo
     */
    @Transactional
    @Override
    public RepayOffLineNotifyResponse borrowRepayAsyn(Map<String, Object> params) throws BusinessException {
        logger.info("=============借款人线下还款异步通知开始=============");
        logger.info("++++++++++++++++++++++++params+++++++++++++++++++:" + params);
        RepayOfflineNotifyRequest repayOfflineNotifyRequest = new RepayOfflineNotifyRequest();
        repayOfflineNotifyRequest.setPartner_id(params.get("partner_id").toString());
        repayOfflineNotifyRequest.setChannelId(params.get("channelId").toString());
        repayOfflineNotifyRequest.setThird_batch_no(params.get("third_batch_no").toString());
        repayOfflineNotifyRequest.setTran_type(params.get("tran_type").toString());
        repayOfflineNotifyRequest.setThird_no(params.get("third_no").toString());
        repayOfflineNotifyRequest.setTrandata(params.get("trandata").toString());
        repayOfflineNotifyRequest.setCert_sign(params.get("cert_sign").toString());
        RepayOffLineNotifyResponse repayOffLineNotifyResponse = new RepayOffLineNotifyResponse();
        BaseResponse baseResponse = new BaseResponse();
        //校验transdata
        if (repayOfflineNotifyRequest.getTrandataDetail() == null || repayOfflineNotifyRequest.getTrandataDetail().size() < 1) {
            baseResponse.setRecode(BusinessMsg.UNKNOW_REMOTE_STATUS);
            baseResponse.setRecode(BusinessMsg.getMsg(BusinessMsg.UNKNOW_REMOTE_STATUS) + "缺少Trandata参数");
            throw new BusinessException(baseResponse);
        }

        try {
            //查询充值记录表
            //String seqNo = repayOfflineNotifyRequest.getTrandataDetail().get(0).getSeq_no();
            String trans_serial = repayOfflineNotifyRequest.getThird_batch_no();
            //TODO 暂时不兼容批量处理
            RwRecharge r = accountSearchService.queryRwRechargeBySerial(trans_serial, null, null);
            logger.info("【借款人线下处理订单】："+JSON.toJSON(r));
            if(null==r){
                throw new BusinessException(BusinessMsg.ORDER_NOEXISTENT,BusinessMsg.getMsg(BusinessMsg.ORDER_NOEXISTENT));
            }
            List<RwRecharge> rwRecharges = new ArrayList<RwRecharge>();
            rwRecharges.add(r);
            BigDecimal totalAmt = new BigDecimal(repayOfflineNotifyRequest.getTrandataDetail().get(0).getTran_amt());
            BigDecimal toConfirmAmt = BigDecimal.ZERO;
            for (RwRecharge rwRecharge : rwRecharges) {
                toConfirmAmt = toConfirmAmt.add(rwRecharge.getTrans_amt());
            }

            if (toConfirmAmt.compareTo(totalAmt) != 0) {
                baseResponse.setRecode(BusinessMsg.PARAMETER_ERROR);
                baseResponse.setRecode(BusinessMsg.getMsg(BusinessMsg.PARAMETER_ERROR) + "行内转账金额与充值记录表不一致");
                throw new BusinessException(baseResponse);
            }

            EaccAccountamtlist eaccAccountamtlist;
            List<RwRecharge> updateRwRecharges = new ArrayList<RwRecharge>();
            List<EaccAccountamtlist> eaccAccountamtlistList = new ArrayList<EaccAccountamtlist>();
            List<PlatcustListDetail> platcustListDetails = new ArrayList<PlatcustListDetail>();

            for (RwRecharge rwRecharge : rwRecharges) {
                if ("Y".equals(repayOfflineNotifyRequest.getTrandataDetail().get(0).getStatus())) {
//                    RwRecharge rwRecharge1 = new RwRecharge();
//                    rwRecharge1.setId(rwRecharge.getId());
                    rwRecharge.setStatus(OrderStatus.SUCCESS.getCode());
                    rwRecharge.setUpdate_time(new Date());
                    updateRwRecharges.add(rwRecharge);

                    AccountSubjectInfo accountSubjectInfo = accountQueryService.queryAccount(rwRecharge.getPlat_no(), rwRecharge.getPlatcust(), Tsubject.CASH.getCode(), Ssubject.FINANCING.getCode());
                    if (accountSubjectInfo == null) {
                        String msg = String.format("根据plat_no【%s】和platcust【%s】查询不到该投资账户：" + rwRecharge.getPlat_no(), rwRecharge.getPlatcust());
                        throw new BusinessException(BusinessMsg.PARAMETER_ERROR, msg);
                    }
                    eaccAccountamtlist = getEaccTransfer(accountSubjectInfo,rwRecharge.getTrans_serial(),TransConsts.BORROW_REPAY_CODE,TransConsts.BORROW_REPAY_NAME);
                    eaccAccountamtlist.setOrder_no(rwRecharge.getOrder_no());
                    eaccAccountamtlist.setSubject(Tsubject.CASH.getCode());
                    eaccAccountamtlist.setSub_subject(Ssubject.FINANCING.getCode());
                    eaccAccountamtlist.setAmt(rwRecharge.getTrans_amt());
                    eaccAccountamtlist.setPay_code(rwRecharge.getPay_code());
                    eaccAccountamtlistList.add(eaccAccountamtlist);

                } else if ("N".equals(repayOfflineNotifyRequest.getTrandataDetail().get(0).getStatus())) {
//                    RwRecharge rwRecharge1 = new RwRecharge();
//                    rwRecharge1.setId(rwRecharge.getId());
                    rwRecharge.setStatus(OrderStatus.FAIL.getCode());
                    rwRecharge.setUpdate_time(new Date());
                    updateRwRecharges.add(rwRecharge);
                } else {
                    throw new BusinessException(BusinessMsg.PARAMETER_ERROR, "传入处理状态参数不对");
                }
                PlatcustListDetail platcustListDetail = new PlatcustListDetail();
                platcustListDetail.setAmt(rwRecharge.getTrans_amt());
                platcustListDetail.setPlatcust(rwRecharge.getPlatcust());
                platcustListDetails.add(platcustListDetail);
            }
            //批量更新充值信息表
            if (updateRwRecharges != null){
                for(RwRecharge rr:updateRwRecharges){
                    rwRechargeMapper.updateByPrimaryKeySelective(rr);
                    TransTransreq tt= transReqService.queryTransTransReqByOrderno(rr.getOrder_no());
                    tt.setReturn_code(BusinessMsg.SUCCESS);
                    tt.setReturn_msg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));
                    tt.setStatus(FlowStatus.SUCCESS.getCode());
                    transReqService.insert(tt);
                }
                // custRwRechargeMapper.updateBatchByPrimaryKeySelective(updateRwRecharges);
            }

            //批量投资人加款
            if (eaccAccountamtlistList != null&&eaccAccountamtlistList.size()>0) {
                //批量加款
                accountAssetService.batchCharge(eaccAccountamtlistList);
            }

            repayOffLineNotifyResponse.setPlat_no(rwRecharges.get(0).getPlat_no());
            repayOffLineNotifyResponse.setOrder_no(rwRecharges.get(0).getOrder_no());
            repayOffLineNotifyResponse.setAmt(repayOfflineNotifyRequest.getTrandataDetail().get(0).getTran_amt());
            repayOffLineNotifyResponse.setPlatcustListDetail(platcustListDetails);
            repayOffLineNotifyResponse.setCode(repayOfflineNotifyRequest.getTrandataDetail().get(0).getStatus());
            repayOffLineNotifyResponse.setNotifyUrl(rwRecharges.get(0).getNotify_url());
            return repayOffLineNotifyResponse;
        } catch (Exception e) {
            logger.error("error:",e);
            if (e instanceof BusinessException) {
                baseResponse = ((BusinessException) e).getBaseResponse();
                throw new BusinessException(baseResponse);
            } else {
                throw new BusinessException(BusinessMsg.DATEBASE_EXCEPTION);
            }
        }
    }
    private EaccAccountamtlist getEaccTransfer(AccountSubjectInfo accountSubjectInfo, String trans_serial, String code, String name){
        EaccAccountamtlist eaccAccountamtlist = new EaccAccountamtlist();
        eaccAccountamtlist.setPlat_no(accountSubjectInfo.getPlat_no());
        eaccAccountamtlist.setOppo_plat_no(accountSubjectInfo.getPlat_no());
        eaccAccountamtlist.setPlatcust(accountSubjectInfo.getPlatcust());
        eaccAccountamtlist.setTrans_serial(trans_serial);
        eaccAccountamtlist.setTrans_code(code);
        eaccAccountamtlist.setTrans_name(name);
        eaccAccountamtlist.setTrans_date(DateUtils.getyyyyMMddDate());
        eaccAccountamtlist.setTrans_time(DateUtils.getHHmmssTime());
        return eaccAccountamtlist;
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

    private RwRecharge getRechange(Map<String, Object> params, String client_property, String platcust, String status, BigDecimal amt, String code, String name, String seqNo, boolean flag, String charge_type) {
        RwRecharge rwRecharge = new RwRecharge();
        rwRecharge.setTrans_serial(params.get("trans_serial").toString());
        rwRecharge.setPlatcust(platcust);
        rwRecharge.setOrder_no(params.get("order_no").toString());//订单号
        rwRecharge.setCharge_type(charge_type);
        rwRecharge.setPlat_no(params.get("plat_no").toString());//平台编号
        if (amt == null) {
            rwRecharge.setTrans_amt(new BigDecimal(params.get("amount").toString()));
        } else {
            rwRecharge.setTrans_amt(amt);
        }
        rwRecharge.setTrans_code(code);//交易代码
        rwRecharge.setTrans_name(name);//交易名称
        rwRecharge.setPay_code("020");
        rwRecharge.setClient_property(client_property);
        rwRecharge.setHost_req_serial_no(seqNo);
        rwRecharge.setTrans_date(params.get("partner_trans_date").toString());//交易日期
        rwRecharge.setTrans_time(params.get("partner_trans_time").toString());//交易时间
        rwRecharge.setReques_time((Date) params.get("requestTime"));//交易请求开始时间
        rwRecharge.setConfirm_time(new Date());//交易请求确认时间
        if (flag) {
            rwRecharge.setLast_time(new Date());
        }
        if (params.get("error_no") != null && params.get("error_info") != null) {
            rwRecharge.setReturn_code(params.get("error_no").toString());
            rwRecharge.setReturn_msg(params.get("error_info").toString());
        }
        rwRecharge.setReturn_url(params.get("ownUrl").toString());
        rwRecharge.setNotify_url(params.get("notify_url").toString());//商户异步通知URL
        rwRecharge.setStatus(status);//处理状态
        rwRecharge.setEnabled(Constants.ENABLED);//删除标记
        rwRecharge.setCreate_time(new Date());//创建时间
        rwRecharge.setReturn_msg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));
        rwRecharge.setExt5(params.get("bank_no").toString());
        return rwRecharge;
    }

//    void refund(RwWithdraw rwWithdraw){
//        //根据平台号查询手续费账户
//        String sub_subject = sysParameterService.querySysParameter(rwWithdraw.getPlat_no(), SysParamterKey.FEE_ACCOUNT);//手续费账户
//        //查询手续费
//        EaccAccountamtlist accountamtlist = new EaccAccountamtlist();
//        accountamtlist.setTrans_serial(rwWithdraw.getTrans_serial());
//        accountamtlist.setAmt_type(AmtType.EXPENSIVE.getCode());
//        accountamtlist.setOppo_sub_subject(sub_subject);
//        List<EaccAccountamtlist> amtLists = billCheckService.selectAmtlist(accountamtlist); // 手续费(可能有两条，现金加在途)
//        if (amtLists.size() != 0) {
//            EaccAccountamtlist amtList = amtLists.get(0);
//            logger.info("有手续费，退款流水号：" + amtList.getTrans_serial());
//            //转手续费
//            //不管是否清算，全部退现金
//            EaccAccountamtlist eaccAccountamtlist = new EaccAccountamtlist();
//            BeanUtils.copyProperties(amtList, eaccAccountamtlist);
//            eaccAccountamtlist.setOrder_no(rwWithdraw.getOrder_no());
//            eaccAccountamtlist.setTrans_serial(rwWithdraw.getTrans_serial());
//            eaccAccountamtlist.setTrans_code(TransConsts.REFUND_CODE);
//            eaccAccountamtlist.setTrans_name(TransConsts.REFUND_NAME);
//            eaccAccountamtlist.setTrans_date(DateUtils.getyyyyMMddDate());
//            eaccAccountamtlist.setTrans_time(DateUtils.format(new Date(), "HHmmss"));
//            eaccAccountamtlist.setCreate_time(new Date());
//            //设置手续费金额，不管是否一半现金一半在途，退总金额
//            eaccAccountamtlist.setAmt(rwWithdraw.getFee_amt());
//            //翻转
//            eaccAccountamtlist.setPlat_no(amtList.getOppo_plat_no());
//            eaccAccountamtlist.setPlatcust(amtList.getOppo_platcust());
//            eaccAccountamtlist.setSubject(Tsubject.CASH.getCode());//现金
//            eaccAccountamtlist.setSub_subject(amtList.getOppo_sub_subject());
//
//            eaccAccountamtlist.setOppo_plat_no(amtList.getOppo_plat_no());
//            eaccAccountamtlist.setOppo_platcust(amtList.getPlatcust());
//            eaccAccountamtlist.setOppo_subject(Tsubject.CASH.getCode());//现金
//            eaccAccountamtlist.setOppo_sub_subject(amtList.getSub_subject());
//
//            accountTransferService.transfer(eaccAccountamtlist);
//        } else {
//            logger.info("==========没有手续费");
//        }
//
//        //倒序排列的最后一条流水就是需要加款的账户
//        EaccAccountamtlist eaccAccountamtlist = new EaccAccountamtlist();
//        eaccAccountamtlist.setTrans_serial(rwWithdraw.getTrans_serial());
//        eaccAccountamtlist.setOrder_no(rwWithdraw.getOrder_no());
//        eaccAccountamtlist.setPlat_no(rwWithdraw.getPlat_no());
//        eaccAccountamtlist.setPlatcust(rwWithdraw.getPlat_no());
//        eaccAccountamtlist.setSubject(Tsubject.CASH.getCode());
//        eaccAccountamtlist.setSub_subject(rwWithdraw.getPay_code());
//        eaccAccountamtlist.setOppo_plat_no(rwWithdraw.getPlat_no());
//        eaccAccountamtlist.setOppo_platcust(rwWithdraw.getPlatcust());
//        eaccAccountamtlist.setOppo_subject(Tsubject.CASH.getCode());
//        eaccAccountamtlist.setOppo_sub_subject(rwWithdraw.getSub_subject());
//        eaccAccountamtlist.setTrans_code(TransConsts.REFUND_CODE);
//        eaccAccountamtlist.setTrans_name(TransConsts.REFUND_NAME);
//        eaccAccountamtlist.setTrans_date(DateUtils.getyyyyMMddDate());
//        eaccAccountamtlist.setTrans_time(DateUtils.format(new Date(), "HHmmss"));
//        eaccAccountamtlist.setAmt_type(AmtType.INCOME.getCode());
//        eaccAccountamtlist.setAmt(rwWithdraw.getOut_amt());
//        accountTransferService.fundTransfer(eaccAccountamtlist);
//
//        //修改提现状态
//        rwWithdraw.setPay_status(PayStatus.FAIL.getCode());
//        rwWithdraw.setAcct_state(PayStatus.REFUNDPSUCCESS.getCode());
//        rwWithdraw.setAcct_state(OrderStatus.REFUNDPSUCCESS.getCode());
//        String remark = String.format("【%s】", "退款成功");
//        rwWithdraw.setRemark(remark + rwWithdraw.getRemark());
//        try {
//            rwWithdrawMapper.updateByPrimaryKeySelective(rwWithdraw);
//        } catch (Exception e) {
//            //更新失败，可能字段过长
//            logger.info("==========追加提现表失败原因异常", e);
//        }
//        //修改业务流水
//        TransTransreq transTransreq = transReqService.queryTransTransReqByOrderNoAndBatchOrderNo(rwWithdraw.getTrans_serial(), rwWithdraw.getOrder_no());
//        if (transTransreq == null) {
//            logger.info("==========找不到业务流水");
//            return;
//        }
//        transTransreq.setReturn_code(OrderStatus.FAIL.getCode());
//        transTransreq.setStatus(FlowStatus.FAIL.getCode());
//        transTransreq.setReturn_msg(remark + transTransreq.getReturn_msg());
//        transTransreq.setUpdate_by(rwWithdraw.getUpdate_by());
//        transReqService.insert(transTransreq);
//    }

    @Override
    public   List<TransTransreq>  queryTransProcessing() throws BusinessException {

        String limit = sysParameterService.querySysParameter(SysParamterKey.FTDM, SysParamterKey.PAYFEE_TRANS_CODE);
        if (StringUtils.isBlank(limit)) {
            limit = "10";
        }
        String[] transCodes = {TransConsts.PAY_FEE_CODE, TransConsts.AUTH_PAY_FEE_CODE};
        Map<String, Object> map = new HashMap<>();

        map.put("limit", limit);
        map.put("trans_codes", transCodes);

        List<TransTransreq> transTransreqs = custTransTransreqMapper.queryProcessingPayfee(map);
        return transTransreqs;
    }

    @Override
    public ApplyQuickPayResponse agrPayApply(ApplyQuickPayRequest applyQuickPayRequest) {
        RwRecharge rwRecharge = new RwRecharge();
        ApplyBaseResponse applyBaseResponse = new ApplyBaseResponse();
        ApplyQuickPayResponse applyQuickPayResponse = new ApplyQuickPayResponse();
        //根据订单查询是否为原单重试
        TransTransreq transTransReq = new TransTransreq();
        try{
            transTransReq=transReqService.queryTransTransReqByOrderno(applyQuickPayRequest.getOrder_no());
        }catch (BusinessException ex){
            // 查不到订单时候
            transTransReq =null;
        }
        boolean is_sameOrderNo=false;
        if(null != transTransReq ){
            logger.info("【协议支付申请】原订单流水："+JSON.toJSON(transTransReq));
            RwRechargeExample example=new RwRechargeExample();
            RwRechargeExample.Criteria criteria=example.createCriteria();
            criteria.andPlat_noEqualTo(applyQuickPayRequest.getMer_no());
            criteria.andOrder_noEqualTo(applyQuickPayRequest.getOrder_no());
            criteria.andEnabledEqualTo(Constants.ENABLED);
            List<RwRecharge> rwRechargeList= rwRechargeMapper.selectByExample(example);
            if(rwRechargeList.size()>1){
                logger.info("【协议支付申请】数据库rw_recharge有多条相同申请订单，order_no:"+applyQuickPayRequest.getOrder_no());
                throw new BusinessException(BusinessMsg.DATEBASE_EXCEPTION, BusinessMsg.getMsg(BusinessMsg.DATEBASE_EXCEPTION) + "，数据库有重复的订单号");
            }else if(rwRechargeList.size() == 1){
                rwRecharge=rwRechargeList.get(0);
                logger.info("【协议支付申请】原充值记录："+JSON.toJSON(rwRecharge));
                //如果原单非处理中，则不允许原单重试
                if(!OrderStatus.PROCESSING.getCode().equals(rwRecharge.getStatus()) && !OrderStatus.REQUESTSUCCESS.getCode().equals(rwRecharge.getStatus())){
                    throw new BusinessException(BusinessMsg.PARAMETER_ERROR, BusinessMsg.getMsg(BusinessMsg.PARAMETER_ERROR) + "，该订单已有终态，不可重新提交");
                }
                is_sameOrderNo=true;
            }
            String new_trans_serial=SeqUtil.getSeqNum();
            transTransReq.setTrans_serial(new_trans_serial);
            rwRecharge.setTrans_serial(new_trans_serial);
        }else{
            transTransReq = transReqService.getTransTransReq(applyQuickPayRequest.clone(), TransConsts.QUICK_RECHARGE_CODE, TransConsts.QUICK_RECHARGE_NAME, false);
            transTransReq.setPlatcust(applyQuickPayRequest.getPlatcust());
            transReqService.insert(transTransReq);
        }
        try {
            //金额校验
            if (applyQuickPayRequest.getAmt().compareTo(BigDecimal.ZERO) <= 0) {
                logger.info("【协议支付申请】金额不能小于等于零<<");
                transTransReq.setReturn_code(BusinessMsg.MONEY_ERROR);
                transTransReq.setReturn_msg(BusinessMsg.getMsg(BusinessMsg.MONEY_ERROR));
                transTransReq.setStatus(OrderStatus.REQUESTFAIL.getCode());
                transReqService.insert(transTransReq);
                throw new BusinessException(BusinessMsg.MONEY_ERROR, BusinessMsg.getMsg(BusinessMsg.MONEY_ERROR));
            }
            //查询plat_paycode 获取合作商编号 支付通道
            PlatPaycode platPayCode = accountSearchService.queryPlatPayCode(applyQuickPayRequest.getMer_no(), applyQuickPayRequest.getPay_code());
            if (null == platPayCode || null == platPayCode.getPayment_plat_no()) {
                logger.info("=======【协议支付申请】数据库没有平台【" + applyQuickPayRequest.getMer_no() + "】支付通道【" + applyQuickPayRequest.getPay_code() + "】的支付映射相关信息=======");
                throw new BusinessException(BusinessMsg.PARAMETER_ERROR, BusinessMsg.getMsg(BusinessMsg.METHODPARAMETER_ERROR) + ":数据库没有该支付通道相关信息");
            }
            applyQuickPayRequest.setPay_channel(platPayCode.getChannel_id());
            EaccUserinfo eaccUserinfo = accountSearchService.checkUserinfo(applyQuickPayRequest);
            if (null == eaccUserinfo) {
                logger.info("=======【协议支付申请】客户信息不存在=======");
                throw new BusinessException(BusinessMsg.INVALID_ACCOUNT, BusinessMsg.getMsg(BusinessMsg.INVALID_ACCOUNT) + "没有该用户");
            }

            //验证手机号码是否为空
            if (!PayCode.ZJ.getCode().equals(platPayCode.getChannel_id()) && null == applyQuickPayRequest.getMobile()) {
                throw new BusinessException(BusinessMsg.PARAMETER_ERROR, BusinessMsg.getMsg(BusinessMsg.PARAMETER_ERROR) + ":不是中金通道、手机号码不能为空");
            }
            if (CardType.CREDIT.getCode().equals(applyQuickPayRequest.getCard_type())) {
                throw new BusinessException(BusinessMsg.PARAMETER_ERROR, BusinessMsg.getMsg(BusinessMsg.PARAMETER_ERROR) + "！目前不支持信用卡快捷充值");
                //sendParams.put("cvv2",);信用卡填0
                //sendParams.put("valid_date",);信用卡填
            }

            String WEB_LOCAL_SERVER = sysParameterService.querySysParameter(SysParamterKey.FTDM, URLConfigUtil.OUT_WEB_LOCAL_SERVER);
            String NOTIFY_URL = sysParameterService.querySysParameter(SysParamterKey.FTDM, URLConfigUtil.RECHARGE_RESULT_NOTIFY);
            NOTIFY_URL = WEB_LOCAL_SERVER + NOTIFY_URL;
            logger.info("【协议支付申请】获取回调通知地址" + NOTIFY_URL);


            String seriNo = transTransReq.getTrans_serial();
            //记录用户充值信息表

            rwRecharge.setCard_no(applyQuickPayRequest.getCard_no());
            rwRecharge.setMobile(applyQuickPayRequest.getMobile());
            rwRecharge.setCreate_time(DateUtils.today(DateUtils.DEF_FORMAT));
            rwRecharge.setCreate_by(applyQuickPayRequest.getMall_no());
            rwRecharge.setTrans_amt(applyQuickPayRequest.getAmt());//充值金额
            rwRecharge.setReturn_url(NOTIFY_URL);//同步回调地址
            rwRecharge.setTrans_serial(seriNo);//交易流水号 业务流水表相同
            rwRecharge.setOrder_no(applyQuickPayRequest.getOrder_no());//订单号
            rwRecharge.setPlat_no(applyQuickPayRequest.getMer_no());//平台编号
            rwRecharge.setPlatcust(applyQuickPayRequest.getPlatcust());//平台客户号
            rwRecharge.setTrans_code(TransConsts.QUICK_RECHARGE_CODE);//交易代码
            rwRecharge.setTrans_name(TransConsts.QUICK_RECHARGE_NAME);//交易名称
            rwRecharge.setTrans_date(applyQuickPayRequest.getPartner_trans_date());//交易日期
            rwRecharge.setTrans_time(applyQuickPayRequest.getPartner_trans_time());//交易时间
            rwRecharge.setPay_code(applyQuickPayRequest.getPay_code());//支付编号
            rwRecharge.setReques_time(DateUtils.today());//交易请求时间
            rwRecharge.setStatus(OrderStatus.PROCESSING.getCode());//处理状态
            rwRecharge.setNotify_url(applyQuickPayRequest.getNotify_url());//商户异步通知URL
            rwRecharge.setEnabled(Constants.ENABLED);//删除标记
            rwRecharge.setSelf_bank_flag(IsSelfBank.NO.getCode());//默认非本行卡
            Map<String, Object> sendParams = new HashMap<String, Object>();
            //默认投资账户
            rwRecharge.setCharge_type(Ssubject.INVEST.getCode());
            if(null!=applyQuickPayRequest.getCharge_type() && !"".equals(applyQuickPayRequest.getCharge_type())){
                if(Ssubject.FINANCING.getCode().equals(applyQuickPayRequest.getCharge_type())){
                    rwRecharge.setCharge_type(Ssubject.FINANCING.getCode());
                }else if(Ssubject.EACCOUNT.getCode().equals(applyQuickPayRequest.getCharge_type())||null!=eaccUserinfo.getEaccount()){
                    sendParams.put("cr_acct", eaccUserinfo.getEaccount());//电子账号
                    sendParams.put("cr_acct_type", 0);//0 借记卡
                    sendParams.put("cr_acct_name", Ssubject.EACCOUNT.getCode());//03 用户电子账户
                    rwRecharge.setCharge_type(Ssubject.EACCOUNT.getCode());
                }else {
                    rwRecharge.setCharge_type(Ssubject.INVEST.getCode());
                }
            }
            logger.info("【协议支付申请】准备记录rwRecharge表流水："+JSON.toJSON(rwRecharge));
            if(is_sameOrderNo){
                //原单则更新
                logger.info("【协议支付申请】原单重试，根据id更新充值记录表,并且更改订单流水号");
                userAccountService.changeTransSerialWhileSameOrderNo(transTransReq,rwRecharge);
            }else{
                //不是原单则添加
                rwRecharge.setId(null);
                rwRechargeMapper.insert(rwRecharge);
            }
            logger.info("【协议支付申请】记录用户充值信息流水：" + rwRecharge);
            applyBaseResponse.setQuery_id(seriNo);
            applyBaseResponse.setProcess_date(DateUtils.todayfulldata());
            //设置请求参数 如果有电子账户  这三个参数是必传的 如果没有电子账户 则根据入参充值到相应账户
            //todo  如果是融宝
            if (applyQuickPayRequest.getPay_code().equals(PayCode.RBDF.getCode()) || applyQuickPayRequest.getPay_code().equals(PayCode.RBDS.getCode())) {
                //todo 目前没特殊项
            }

            //是否进行短验申请
            if(null!=platPayCode.getIs_msgcheck()&&"1".equals(platPayCode.getIs_msgcheck())){
                String msg_host = sysParameterService.querySysParameter(SysParamterKey.FTDM, URLConfigUtil.EPAY_SERVER_KEY);
                String msg_url = sysParameterService.querySysParameter(SysParamterKey.FTDM, URLConfigUtil.AGENT_COLLECTION_VERIFY);
                msg_url = msg_host + msg_url;
                sendParams.put("url_msgRequest", msg_url);
                logger.info("【协议支付申请】获取支付发短信接口地址：" + msg_url);
            }

            sendParams.put("partner_id", platPayCode.getPayment_plat_no());
            sendParams.put("partner_serial_no", seriNo);//平台流水
            sendParams.put("partner_trans_date", applyQuickPayRequest.getPartner_trans_date());
            sendParams.put("partner_trans_time", applyQuickPayRequest.getPartner_trans_time());
            sendParams.put("prod_code", TransConsts.QUICK_RECHARGE_CODE);//产品编号
            sendParams.put("prod_name", TransConsts.QUICK_RECHARGE_NAME);//产品名称
            sendParams.put("currency_code", applyQuickPayRequest.getCurrency_code()); //币种
            sendParams.put("summary", "协议支付申请");//摘要
            sendParams.put("client_name", applyQuickPayRequest.getName());//客户姓名
            sendParams.put("card_no", applyQuickPayRequest.getCard_no());//银行卡号
            sendParams.put("id_no", applyQuickPayRequest.getId_code());//证件号
            sendParams.put("receive_url", NOTIFY_URL);//后台回调地址 异步通知地址
            sendParams.put("channelId", platPayCode.getChannel_id());//支付通道
            sendParams.put("verify_mode", IsUse.YES.getCode());//验证模式 1需要短信验证 0不需要
            sendParams.put("cert_sign", "agrPayApply");//签名串
            sendParams.put("mobile_tel", applyQuickPayRequest.getMobile());//手机号
            sendParams.put("partner_userid", applyQuickPayRequest.getPlatcust());//商户用户编号
            //todo 判断是否是信用卡 目前明确不能有信用卡
            //证件类型  如果id_type是1  则id_kind是0
            if (IdType.IDENTITY_CARD.getCode().equals(applyQuickPayRequest.getId_type())) {
                sendParams.put("id_kind", "0");
            } else {
                sendParams.put("id_kind", applyQuickPayRequest.getId_type());
            }
            sendParams.put("occur_balance", applyQuickPayRequest.getAmt());//金额
            String host ="";
            String url="";
            if(null!=platPayCode.getIs_bankcheck()&&"0".equals(platPayCode.getIs_bankcheck())){
                //支付公司收单
                host=sysParameterService.querySysParameter(SysParamterKey.FTDM, URLConfigUtil.EPAY_SERVER_KEY);
                url=sysParameterService.querySysParameter(SysParamterKey.FTDM, URLConfigUtil.AGENT_QUICK_PAY_URL);
            }else{
                //银行收单
                host=sysParameterService.querySysParameter(SysParamterKey.FTDM, URLConfigUtil.EPAY_SERVER_KEY);
                url=sysParameterService.querySysParameter(SysParamterKey.FTDM, URLConfigUtil.AGR_PAY_APPLY);
            }
           /* String host = sysParameterService.querySysParameter(applyQuickPayRequest.getMall_no(), URLConfigUtil.EPAY_SERVER_KEY);
            String url = sysParameterService.querySysParameter(applyQuickPayRequest.getMall_no(), URLConfigUtil.QUICK_PAY_URL);*/
            logger.info("【协议支付申请】order_no:"+applyQuickPayRequest.getOrder_no()+",请求支付地址：" + host + url);
            sendParams.put("host", host);
            sendParams.put("url", url);

            //根据支付通道特殊性添加特殊字段
            sendParams.put("terminal_id","0");
            sendParams.put("terminal_type","1");
            sendParams.put("partner_userid",applyQuickPayRequest.getPlatcust());
            sendParams.put("prod_name","快捷充值");
            sendParams.put("registtime",DateUtils.today("yyyy-MM-dd HH:mm:ss"));
            sendParams.put("lastloginterminalid","192.0.0.168");
            sendParams.put("issetpaypwd","0");

            logger.info("【协议支付申请】 快捷支付申请开始...请求参数："+JSON.toJSON(sendParams));
            Map<String, Object> resultMap = adapterService.agrPayApply(sendParams);
            logger.info("【协议支付申请】 响应结果：" + JSON.toJSON(resultMap));
            if (null == resultMap || null == resultMap.get("order_status")) {
                transTransReq.setReturn_code(BusinessMsg.CALL_REMOTE_ERROR);
                transTransReq.setReturn_msg(BusinessMsg.getMsg(BusinessMsg.CALL_REMOTE_ERROR));
                transTransReq.setStatus(OrderStatus.REQUESTFAIL.getCode());
                transReqService.insert(transTransReq);
                //支付请求失败
                applyQuickPayResponse.setRecode(BusinessMsg.CALL_REMOTE_ERROR);
                applyQuickPayResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.CALL_REMOTE_ERROR));
                logger.info("【协议支付申请】 响应结果：" + "无响应参数");
                return applyQuickPayResponse;
            }
            String order_status = resultMap.get("order_status").toString();
            if (!OrderStatus.FAIL.getCode().equals(order_status)) {
                if (null != resultMap.get("host_req_serial_no")) {
                    applyBaseResponse.setHost_req_serial_no(resultMap.get("host_req_serial_no").toString());//返回渠道流水号
                    rwRecharge.setHost_req_serial_no(resultMap.get("host_req_serial_no").toString());//充值信息表请求三方流水号
                }
                if (null != resultMap.get("hsepay_order_no")) {
                    rwRecharge.setHsepay_order_no(resultMap.get("hsepay_order_no").toString());
                }
                if (null != resultMap.get("self_bank_flag")) {
                    rwRecharge.setSelf_bank_flag(resultMap.get("self_bank_flag").toString());
                }
                //更新业务流水为请求成功
                transTransReq.setStatus(FlowStatus.REQUESTSUCCESS.getCode());
                transTransReq.setReturn_code(OrderStatus.REQUESTSUCCESS.getCode());
                transTransReq.setReturn_msg(OrderStatus.REQUESTSUCCESS.getMsg());
                transReqService.insert(transTransReq);
                rwRecharge.setOrder_no(applyQuickPayRequest.getOrder_no());
                rwRecharge.setReturn_msg(OrderStatus.REQUESTSUCCESS.getMsg());
                rwRecharge.setReturn_code(OrderStatus.REQUESTSUCCESS.getCode());
                rwRecharge.setStatus(OrderStatus.REQUESTSUCCESS.getCode());
                rwRecharge.setUpdate_by(applyQuickPayRequest.getMer_no());
                rwRecharge.setUpdate_time(DateUtils.today());
                rwRecharge.setConfirm_time(DateUtils.today(DateUtils.DEF_FORMAT));
                rwRecharge.setLast_time(DateUtils.today(DateUtils.DEF_FORMAT));
                userAccountService.updateRwRecharge(rwRecharge);

                applyQuickPayResponse.setRecode(BusinessMsg.SUCCESS);
                applyQuickPayResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));   //"msg", "快捷支付申请成功！");
            } else {
                //更新业务流水请求失败
                transTransReq.setStatus(FlowStatus.FAIL.getCode());
                transTransReq.setUpdate_time(DateUtils.today(DateUtils.DEF_FORMAT));
                transTransReq.setReturn_msg(OrderStatus.REQUESTFAIL.getMsg());
                transTransReq.setReturn_code(OrderStatus.REQUESTFAIL.getCode());
                transReqService.insert(transTransReq);
                //更新用户充值信息表 状态为请求失败
                rwRecharge.setReturn_code(OrderStatus.REQUESTFAIL.getCode());
                rwRecharge.setReturn_msg(OrderStatus.REQUESTFAIL.getMsg());
                rwRecharge.setOrder_no(applyQuickPayRequest.getOrder_no());
                rwRecharge.setStatus(OrderStatus.REQUESTFAIL.getCode());
                rwRecharge.setUpdate_by(applyQuickPayRequest.getMer_no());
                rwRecharge.setUpdate_time(DateUtils.today());
                rwRecharge.setConfirm_time(DateUtils.today(DateUtils.DEF_FORMAT));
                rwRecharge.setLast_time(DateUtils.today(DateUtils.DEF_FORMAT));
                userAccountService.updateRwRecharge(rwRecharge);

                applyBaseResponse.setError_no(resultMap.get("recode")==null?BusinessMsg.FAIL:resultMap.get("recode").toString());
                applyBaseResponse.setError_info(resultMap.get("remsg")==null?BusinessMsg.getMsg(BusinessMsg.FAIL):resultMap.get("remsg").toString());

                applyQuickPayResponse.setRecode(resultMap.get("recode")==null?BusinessMsg.FAIL:resultMap.get("recode").toString());
                applyQuickPayResponse.setRemsg(resultMap.get("remsg")==null?BusinessMsg.getMsg(BusinessMsg.FAIL):resultMap.get("remsg").toString());
            }
            applyBaseResponse.setOrder_status(order_status);
            applyQuickPayResponse.setData_detail(applyBaseResponse);
            return applyQuickPayResponse;
        } catch (BusinessException e) {
            logger.error("【协议支付申请】异常", e);
            //确保在rwRecharge入库的情况下再更新rwRecharge记录
            if (null != rwRecharge.getOrder_no() && !"".equals(rwRecharge.getOrder_no())) {
                rwRecharge.setReturn_msg(OrderStatus.REQUESTFAIL.getMsg());
                rwRecharge.setStatus(OrderStatus.REQUESTFAIL.getCode());
                rwRecharge.setUpdate_by(applyQuickPayRequest.getMer_no());
                rwRecharge.setUpdate_time(DateUtils.today());
                rwRecharge.setConfirm_time(DateUtils.today(DateUtils.DEF_FORMAT));
                rwRecharge.setLast_time(DateUtils.today(DateUtils.DEF_FORMAT));
                userAccountService.updateRwRecharge(rwRecharge);
            }
            transTransReq.setStatus(FlowStatus.REQUESTFAIL.getCode());
            transTransReq.setReturn_code(e.getBaseResponse().getRecode());
            transTransReq.setReturn_msg(e.getBaseResponse().getRemsg());
            transReqService.insert(transTransReq);
            applyBaseResponse.setError_no(e.getBaseResponse().getRecode());
            applyBaseResponse.setError_info(e.getBaseResponse().getRemsg());
            applyBaseResponse.setOrder_status(OrderStatus.FAIL.getCode());
            applyQuickPayResponse.setRecode(e.getBaseResponse().getRecode());
            applyQuickPayResponse.setRemsg(e.getBaseResponse().getRemsg());
            applyQuickPayResponse.setData_detail(applyBaseResponse);
        }
        return applyQuickPayResponse;
    }

    @Override
    public ConfirmQuickPayResponse agrPayConfirm(ConfirmQuickPayRequest confirmQuickPayRequest) {
        //根据原申请订单号查询快捷充值请求流水
        logger.info("【协议支付确认】=========开始，确认订单号：" + confirmQuickPayRequest.getOrder_no());
        TransTransreq transTransreq = transReqService.queryTransTransReqByOrderno(confirmQuickPayRequest.getOrigin_order_no());
        if(null==transTransreq){
            logger.info("【协议支付确认】=========根据原充值申请订单号查不到充值请求信息，请重新申请");
            throw new BusinessException(BusinessMsg.PARAMETER_ERROR, BusinessMsg.getMsg(BusinessMsg.PARAMETER_ERROR) + ",根据原充值申请订单号查不到充值请求信息，请重新申请");
        }
        //记录快捷请求确认流水
        TransTransreq transTransreq1 = transReqService.getTransTransReq(confirmQuickPayRequest.clone(), TransConsts.QUICK_CONFIRM_CODE, TransConsts.QUICK_CONFIRM_NAME, false);
        transTransreq1.setOrigin_order_no(confirmQuickPayRequest.getOrigin_order_no());
        transTransreq1.setPlatcust(transTransreq.getPlatcust());
        transReqService.insert(transTransreq1);
        //为了配合老接口兼容
        if(null==confirmQuickPayRequest.getPlatcust()||"".equals(confirmQuickPayRequest.getPlatcust())){
            confirmQuickPayRequest.setPlatcust(transTransreq.getPlatcust());
        }
        ConfirmQuickPayResponseDetail dataDetail = new ConfirmQuickPayResponseDetail();
        ConfirmQuickPayResponse confirmQuickPayResponse = new ConfirmQuickPayResponse();
        confirmQuickPayResponse.setData_detail(dataDetail);
        //平台处理日期
        dataDetail.setProcess_date(DateUtils.todayfulldata());
        //平台流水号
        dataDetail.setQuery_id(transTransreq.getTrans_serial());


        RwRecharge rwRecharge=new RwRecharge();
        try {
            //查询原申请订单号在充值信息表中的记录
            logger.info("【协议支付确认】=========查询原充值请求流水");
            rwRecharge = accountSearchService.queryRwRecharge(confirmQuickPayRequest.getMer_no(), confirmQuickPayRequest.getOrigin_order_no());
            if (rwRecharge == null || ((!OrderStatus.REQUESTSUCCESS.getCode().equals(rwRecharge.getStatus()))&&(!OrderStatus.PROCESSING.getCode().equals(rwRecharge.getStatus())))) {
                logger.info("【协议支付确认】=========查询不到原申请订单，或者原订单已经是终态，请勿重复确认");
                throw new BusinessException(BusinessMsg.PARAMETER_ERROR, BusinessMsg.getMsg(BusinessMsg.PARAMETER_ERROR) + ",查询不到原申请订单，或者原订单已经是终态，请勿重复确认");
            }
            logger.info("【协议支付确认】=========查询原充值请求流水结束");

            rwRecharge.setTrans_code(TransConsts.QUICK_CONFIRM_CODE);
            rwRecharge.setTrans_name(TransConsts.QUICK_CONFIRM_NAME);
            rwRecharge.setOrder_no(confirmQuickPayRequest.getOrigin_order_no());

            //查询plat_paycode 获取合作商编号
            logger.info("【协议支付确认】=========查询pay_code信息，确认订单号：" + confirmQuickPayRequest.getOrder_no());
            PlatPaycode platPayCode = accountSearchService.queryPlatPayCode(confirmQuickPayRequest.getMer_no(), confirmQuickPayRequest.getPay_code());
            if (null == platPayCode) {
                logger.info("【协议支付确认】=========渠道不存在：" + confirmQuickPayRequest.getPay_code());
                throw new BusinessException(BusinessMsg.PARAMETER_ERROR, BusinessMsg.getMsg(BusinessMsg.PARAMETER_ERROR) + ",根据平台编号和支付通道查询不到渠道信息");
            }
            logger.info("【协议支付确认】=========查询pay_code信息完成");

            Map<String, Object> sendParams = new HashMap<>();
            sendParams.put("partner_id", platPayCode.getPayment_plat_no());
            sendParams.put("partner_serial_no", rwRecharge.getTrans_serial());
            sendParams.put("verify_info", confirmQuickPayRequest.getIdentifying_code());
            sendParams.put("channelId",platPayCode.getChannel_id());
            sendParams.put("cert_sign", "quick_pay_test");

            String host ="";
            String url="";
            if(null!=platPayCode.getIs_bankcheck()&&"0".equals(platPayCode.getIs_bankcheck())){
                //支付公司收单
                host =sysParameterService.querySysParameter(SysParamterKey.FTDM, URLConfigUtil.EPAY_SERVER_KEY);
                url=sysParameterService.querySysParameter(SysParamterKey.FTDM, URLConfigUtil.AGENT_CONFIRM_QUICK_PAY_URL);
            }else{
                //银行收单
                host =sysParameterService.querySysParameter(SysParamterKey.FTDM, URLConfigUtil.EPAY_SERVER_KEY);
                url=sysParameterService.querySysParameter(SysParamterKey.FTDM, URLConfigUtil.AGR_PAY_CONFIRM);
            }

           /* String host = sysParameterService.querySysParameter(confirmQuickPayRequest.getMall_no(), URLConfigUtil.EPAY_SERVER_KEY);
            String url = sysParameterService.querySysParameter(confirmQuickPayRequest.getMall_no(), URLConfigUtil.CONFIRM_QUICK_PAY_URL);*/
            sendParams.put("host", host);
            sendParams.put("url", url);
            logger.info("【协议支付确认】=========获取支付链接参数完成:"+host+url);

            logger.info("【协议支付确认】=========获取支付响应开始，确认订单号：" + confirmQuickPayRequest.getOrder_no()+",请求参数"+sendParams);
            Map<String,Object> resultMap=adapterService.agrPayConfirm(sendParams);

            //只要请求了三方就把状态改为处理中
            rwRecharge.setStatus(OrderStatus.PROCESSING.getCode());
            rwRecharge.setReturn_code(BusinessMsg.SUCCESS);
            rwRecharge.setReturn_msg("处理中");
            rwRechargeMapper.updateByPrimaryKeySelective(rwRecharge);

            logger.info("【协议支付确认】=========获取支付响应完成：" + confirmQuickPayRequest.getOrder_no()+"======响应结果："+JSON.toJSON(resultMap));
            if(null==resultMap||null==resultMap.get("order_status")){
                logger.info("【协议支付确认】=========返回参数不正确,确认订单：" + confirmQuickPayRequest.getOrder_no());
                confirmQuickPayResponse.setRecode(BusinessMsg.CALL_REMOTE_ERROR);
                confirmQuickPayResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.CALL_REMOTE_ERROR));
                return confirmQuickPayResponse;
            }
            String order_status=resultMap.get("order_status").toString();
            if(null!=resultMap.get("pay_finish_date") && !"".equals(resultMap.get("pay_finish_date"))){
                rwRecharge.setPayment_date(resultMap.get("pay_finish_date").toString());
            }else{
                rwRecharge.setPayment_date(DateUtils.getyyyyMMddDate());
            }
            if(null!=resultMap.get("self_bank_flag") && !"".equals(resultMap.get("self_bank_flag"))){
                rwRecharge.setSelf_bank_flag(IsSelfBank.YES.getCode().equals(resultMap.get("self_bank_flag").toString())?IsSelfBank.YES.getCode():IsSelfBank.NO.getCode());
            }
            dataDetail.setOrder_status(order_status);
            if(OrderStatus.SUCCESS.getCode().equals(order_status)){
                logger.info("【协议支付确认】===========第三方支付返回状态交易成功");
                //如果充值成功 向用户账户表中添加金额
                rwRecharge.setLast_time(DateUtils.today(DateUtils.DEF_FORMAT));
                rwRecharge.setReturn_msg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));
                rwRecharge.setReturn_code(BusinessMsg.SUCCESS);
                rwRecharge.setStatus(OrderStatus.SUCCESS.getCode());
                rwRecharge.setUpdate_by(confirmQuickPayRequest.getMer_no());
                rwRecharge.setOrder_no(confirmQuickPayRequest.getOrigin_order_no());
                rwRecharge.setConfirm_time(DateUtils.today(DateUtils.DEF_FORMAT));
                rwRecharge.setUpdate_time(DateUtils.today());

                confirmQuickPayResponse.setRecode(BusinessMsg.SUCCESS);
                confirmQuickPayResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));
            } else if (OrderStatus.PROCESSING.getCode().equals(order_status)) {
                //更新充值信息
                Object recode = resultMap.get("recode");
                Object remsg = resultMap.get("remsg");
                logger.info("【协议支付确认】===========第三方支付返回状态处理中");
                dataDetail.setError_info(remsg == null ? "处理中" : remsg.toString());
                dataDetail.setError_no(recode == null ? OrderStatus.PROCESSING.getCode() : recode.toString());
                rwRecharge.setStatus(OrderStatus.PROCESSING.getCode());
                confirmQuickPayResponse.setRecode(recode == null ? OrderStatus.PROCESSING.getCode() : recode.toString());
                confirmQuickPayResponse.setRemsg(remsg == null ? "处理中" : remsg.toString());
            } else if (OrderStatus.FAIL.getCode().equals(order_status)) {
                dataDetail.setError_info(resultMap.get("remsg")==null?BusinessMsg.getMsg(BusinessMsg.FAIL):resultMap.get("remsg").toString());
                dataDetail.setError_no(resultMap.get("recode")==null?BusinessMsg.FAIL:resultMap.get("recode").toString());
                rwRecharge.setStatus(OrderStatus.FAIL.getCode());
                rwRecharge.setReturn_msg(resultMap.get("remsg")==null?BusinessMsg.getMsg(BusinessMsg.FAIL):resultMap.get("remsg").toString());
                rwRecharge.setReturn_code(resultMap.get("recode")==null?BusinessMsg.FAIL:resultMap.get("recode").toString());
                logger.info("【协议支付确认】===========第三方支付返回状态交易失败");
                confirmQuickPayResponse.setRecode(resultMap.get("recode")==null?BusinessMsg.FAIL:resultMap.get("recode").toString());
                confirmQuickPayResponse.setRemsg(resultMap.get("remsg")==null?BusinessMsg.getMsg(BusinessMsg.FAIL):resultMap.get("remsg").toString());
            } else {
                dataDetail.setError_info(BusinessMsg.getMsg(BusinessMsg.UNKNOW_ERROE));
                dataDetail.setError_no(BusinessMsg.UNKNOW_ERROE);
                dataDetail.setOrder_status(OrderStatus.FAIL.getCode());
                rwRecharge.setStatus(OrderStatus.REQUESTSUCCESS.getCode());
                confirmQuickPayResponse.setRecode(BusinessMsg.UNKNOW_ERROE);
                confirmQuickPayResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.UNKNOW_ERROE));
            }
            //调用陈瑞接口
            logger.info("请求dorecharge参数："+rwRecharge.toString()+",确认订单号："+confirmQuickPayRequest.getOrder_no());
            Boolean flag = rechargeOptionService.doRecharge(rwRecharge, confirmQuickPayRequest.getMall_no(),confirmQuickPayRequest.getOrder_no(), TransConsts.QUICK_CONFIRM_CODE);
            logger.info("返回flag："+flag);
            if (!flag) {
                // 陈瑞接口返回false ，表示处理中
                transTransreq.setStatus(FlowStatus.REQUESTSUCCESS.getCode());
                transTransreq.setReturn_code(BusinessMsg.SUCCESS);
                transTransreq.setReturn_msg(BusinessMsg.getMsg(BusinessMsg.SUCCESS) + "：充值订单处理中");
                transReqService.insert(transTransreq);
                confirmQuickPayResponse.setRecode(BusinessMsg.SUCCESS);
                confirmQuickPayResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.SUCCESS) + "：充值订单处理中");
                dataDetail.setError_no(BusinessMsg.SUCCESS);
                dataDetail.setError_info(BusinessMsg.getMsg(BusinessMsg.SUCCESS) + "：充值订单处理中");
                dataDetail.setOrder_status(OrderStatus.PROCESSING.getCode());
            } else {
                if(OrderStatus.SUCCESS.getCode().equals(order_status)){
                    transTransreq.setStatus(FlowStatus.CONFIRMSUCCESS.getCode());
                }else if(OrderStatus.FAIL.getCode().equals(order_status)){
                    transTransreq.setStatus(FlowStatus.CONFIRMFAIL.getCode());
                }else{
                    transTransreq.setStatus(FlowStatus.INPROCESS.getCode());
                }
                transReqService.insert(transTransreq);
            }
            //transReqService.insert(transTransreq);
            confirmQuickPayResponse.setData_detail(dataDetail);
            logger.info("【协议支付确认】===========同步响应结果："+confirmQuickPayResponse.toString());
            if(OrderStatus.SUCCESS.getCode().equals(dataDetail.getOrder_status())||
                    OrderStatus.FAIL.getCode().equals(dataDetail.getOrder_status())) {
                //组装异步通知参数
                Map<String, Object> notify_msg = new HashMap<String, Object>();
                notify_msg.put("plat_no", confirmQuickPayRequest.getMer_no());
                notify_msg.put("mall_no", confirmQuickPayRequest.getMall_no());
                notify_msg.put("platcust", confirmQuickPayRequest.getPlatcust());
                notify_msg.put("type", "1");
                notify_msg.put("order_no", rwRecharge.getOrder_no());
                notify_msg.put("order_amt", rwRecharge.getTrans_amt());
                notify_msg.put("trans_date", rwRecharge.getTrans_date());
                notify_msg.put("trans_time", rwRecharge.getTrans_time());
                notify_msg.put("pay_finish_date",DateUtils.getyyyyMMddDate());
                notify_msg.put("pay_finish_time",DateUtils.getHHmmssTime());
                notify_msg.put("sign", "sign");
                notify_msg.put("order_status", dataDetail.getOrder_status());
                notify_msg.put("pay_order_no", rwRecharge.getHsepay_order_no());
                notify_msg.put("host_req_serial_no", rwRecharge.getHost_req_serial_no());
                notify_msg.put("pay_amt", rwRecharge.getTrans_amt());
                notify_msg.put("notify_url", rwRecharge.getNotify_url());
                confirmQuickPayResponse.setNotify_msg(notify_msg);
                if(OrderStatus.FAIL.getCode().equals(dataDetail.getOrder_status())){
                    notify_msg.put("error_no", dataDetail.getError_no());
                    notify_msg.put("error_info", dataDetail.getError_info());
                    notify_msg.put("recode", StringUtils.isBlank(dataDetail.getError_no())?BusinessMsg.FAIL:dataDetail.getError_no());
                    notify_msg.put("remsg", StringUtils.isBlank(dataDetail.getError_info())?BusinessMsg.getMsg(BusinessMsg.FAIL):dataDetail.getError_info());
                } else if(OrderStatus.SUCCESS.getCode().equals(dataDetail.getOrder_status())){
                    notify_msg.put("recode", BusinessMsg.SUCCESS);
                    notify_msg.put("remsg", BusinessMsg.getMsg(BusinessMsg.SUCCESS));
                }
                confirmQuickPayResponse.setNotify_msg(notify_msg);
            }
            ConfirmQuickPayResponseDetail data_detail=confirmQuickPayResponse.getData_detail();
            //同步响应处理成功或者处理中 向平台发异步
            logger.info("【协议支付确认】服务端收到的响应："+JSON.toJSON(confirmQuickPayResponse));
            if(OrderStatus.SUCCESS.getCode().equals(data_detail.getOrder_status())||
                    OrderStatus.FAIL.getCode().equals(data_detail.getOrder_status())){
                Map<String, Object> requestMap =confirmQuickPayResponse.getNotify_msg();
                if(null==requestMap|| null == requestMap.get("notify_url")){
                    logger.info("【协议支付同步发送异步通知】异常：异步通知参数缺失，order_no:"+confirmQuickPayResponse.getOrder_no());
                }else{
                    String notify_url=requestMap.get("notify_url").toString();
                    requestMap.remove("notify_url");
                    String data = JSONObject.toJSONString(requestMap);
                    logger.info("【协议支付异步回调】异步通知平台处理数据：" + data);
                    logger.info("【协议支付异步回调】异步地址：" + notify_url);

                    NotifyData notifyData=new NotifyData();
                    notifyData.setNotifyContent(data);
                    notifyData.setNotifyUrl(notify_url);
                    notifyData.setMall_no(confirmQuickPayRequest.getMall_no());
                    notifyService.addNotify(notifyData);
                }
            }
            return confirmQuickPayResponse;
        } catch (Exception e) {
            logger.error("【协议支付确认】ERROR:",e);
            if(e instanceof  BusinessException ){
                BaseResponse baseResponse = ((BusinessException) e).getBaseResponse();
                dataDetail.setOrder_status(OrderStatus.PROCESSING.getCode());
                dataDetail.setError_no(baseResponse.getRecode());
                dataDetail.setError_info(baseResponse.getRemsg());
                transTransreq.setStatus(FlowStatus.INPROCESS.getCode());
                transTransreq.setReturn_code(baseResponse.getRecode());
                transTransreq.setReturn_msg(baseResponse.getRemsg());
                transReqService.insert(transTransreq);
                confirmQuickPayResponse.setRecode(baseResponse.getRecode());
                confirmQuickPayResponse.setRemsg(baseResponse.getRemsg());
            } else{
                //更新流水
                dataDetail.setOrder_status(OrderStatus.PROCESSING.getCode());
                dataDetail.setError_no(BusinessMsg.DATEBASE_EXCEPTION);
                dataDetail.setError_info(BusinessMsg.getMsg(BusinessMsg.DATEBASE_EXCEPTION));
                transTransreq.setStatus(FlowStatus.INPROCESS.getCode());
                transTransreq.setReturn_code(BusinessMsg.DATEBASE_EXCEPTION);
                transTransreq.setReturn_msg(BusinessMsg.getMsg(BusinessMsg.DATEBASE_EXCEPTION));
                transReqService.insert(transTransreq);
                confirmQuickPayResponse.setRecode(BusinessMsg.DATEBASE_EXCEPTION);
                confirmQuickPayResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.DATEBASE_EXCEPTION));
            }
            logger.info("【协议支付确认】===========同步响应结果："+confirmQuickPayResponse.toString());
            return confirmQuickPayResponse;
        }
    }

    @Override
    public BatchCollectionResponse batchCollectionService(BatchCollectionRequest collectionRequest) {
        //记录流水
        logger.info("批量代扣，"+ JSON.toJSONString(collectionRequest));
        TransTransreq transTransReq = transReqService.getTransTransReq(collectionRequest.clone(), TransConsts.COLLECTION_CODE, TransConsts.COLLECTION_NAME, true);
        BaseResponse transbaseResponse= transReqService.insert(transTransReq);
        BatchCollectionResponse collectionDataResponse = new BatchCollectionResponse();
        String WEB_LOCAL_SERVER="";
        String NOTIFY_URL="";
        String host="";
        String url ="";
        List<CollectionDetail> data_detail =new ArrayList<CollectionDetail>();
        PlatPaycode platPayCode =null;
        List<RwRecharge> rwRecharges=new ArrayList<RwRecharge>();
        if(null !=transbaseResponse){
/*            logger.info("【代扣充值】【"+collectionRequest.getOrder_no()+"】【记录流水插入失败，订单号重复】");
            throw new BusinessException(BusinessMsg.ORIGINAL_ORDER_NO_ERROR,BusinessMsg.getMsg(BusinessMsg.ORIGINAL_ORDER_NO_ERROR));*/
            collectionDataResponse.setRecode(transbaseResponse.getRecode());
            collectionDataResponse.setRemsg(transbaseResponse.getRemsg());
            collectionDataResponse.setOrder_status(transbaseResponse.getOrder_status());
            return collectionDataResponse;
        }
        logger.info("【批量代扣充值】【"+transTransReq.getTrans_serial()+"】【记录流水成功】");

        try {
            //金额校验
            data_detail=collectionRequest.getCollectionDetailList();
            if(data_detail.size() != collectionRequest.getTotal_num()){
                throw new BusinessException(BusinessMsg.PARAMETER_ERROR, "总笔数和明细笔数不一致");
            }

            platPayCode = accountSearchService.queryPlatPayCode(collectionRequest.getMer_no(), collectionRequest.getPay_code());
            if (null == platPayCode) {
                logger.info("【代扣充值】pay_code无效");
                throw new BusinessException(BusinessMsg.NO_CHANNEL_INFORMATION, String.format("数据库没有平台【%s】支付通道【%s】的支付映射相关信息", collectionRequest.getMer_no(), collectionRequest.getPay_code()));
            }
            BigDecimal detail_sum_amt=BigDecimal.ZERO;
            for(CollectionDetail data_detail_item:data_detail){
                detail_sum_amt=detail_sum_amt.add(data_detail_item.getAmt());
                data_detail_item.setNotify_url(collectionRequest.getNotify_url());
                data_detail_item.setPay_code(collectionRequest.getPay_code());
            }
            if(0!=collectionRequest.getTotal_balance().compareTo(detail_sum_amt)){
                throw new BusinessException(BusinessMsg.PARAMETER_ERROR, "总金额和明细金额汇总不一致");
            }
            WEB_LOCAL_SERVER = sysParameterService.querySysParameter(SysParamterKey.FTDM, URLConfigUtil.WEB_LOCAL_SERVER);
            NOTIFY_URL = sysParameterService.querySysParameter(SysParamterKey.FTDM, URLConfigUtil.COLLECTION_URL_NOTIFY);
            NOTIFY_URL = WEB_LOCAL_SERVER + NOTIFY_URL;

            host = sysParameterService.querySysParameter(collectionRequest.getMall_no(), URLConfigUtil.EPAY_SERVER_KEY);
            url = sysParameterService.querySysParameter(collectionRequest.getMall_no(), URLConfigUtil.BATCH_COLLECTION);

            logger.info("批量代扣，data_detail"+JSON.toJSON(data_detail));
            for(CollectionDetail data_detail_item:data_detail){
                RwRecharge r = doBatchCollection(collectionRequest, data_detail_item, NOTIFY_URL,collectionDataResponse);
                if(null!=r){
                    rwRecharges.add(r);
                }
            }
            logger.info("批量代扣，rwRecharges"+JSON.toJSON(rwRecharges));
        } catch (Exception e) {
            logger.error("【批量代扣】异常:", e);
            BaseResponse baseResponse = new BaseResponse();
            if (e instanceof BusinessException) {
                baseResponse = ((BusinessException) e).getBaseResponse();
            } else {
                baseResponse.setRecode(BusinessMsg.DATEBASE_EXCEPTION);
                baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.DATEBASE_EXCEPTION));
            }
            collectionDataResponse.setRecode(baseResponse.getRecode());
            collectionDataResponse.setRemsg(baseResponse.getRemsg());
            collectionDataResponse.setOrder_status(OrderStatus.FAIL.getCode());
            transTransReq.setStatus(FlowStatus.FAIL.getCode());
            transTransReq.setReturn_code(baseResponse.getRecode());
            transTransReq.setReturn_msg(baseResponse.getRemsg());
            transReqService.insert(transTransReq);
            return collectionDataResponse;
        }

        try {
            collectionDataResponse.getData().addAll(sendBatchCollection(transTransReq,rwRecharges,host,url,platPayCode.getChannel_id(),collectionRequest.getMall_no()).getData());
        }catch (BusinessException be){
            collectionDataResponse.setRecode(BusinessMsg.SUCCESS);
            collectionDataResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));
            transReqService.insert(transTransReq);
            return collectionDataResponse;
        }
        collectionDataResponse.setRecode(BusinessMsg.SUCCESS);
        collectionDataResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));
        return collectionDataResponse;
    }

    private RwRecharge doBatchCollection(BatchCollectionRequest collectionRequest, CollectionDetail data_detail_item, String notify_url, BatchCollectionResponse collectionDataResponse) {
        TransTransreq transTransReqDetail = transReqService.getTransTransReq(collectionRequest.clone(), TransConsts.COLLECTION_CODE, TransConsts.COLLECTION_NAME, true);
        transTransReqDetail.setOrder_no(data_detail_item.getDetail_no());
        transTransReqDetail.setPlatcust(data_detail_item.getPlatcust());
        transReqService.insert(transTransReqDetail);
        RwRecharge rwRecharge = new RwRecharge();
        //检查是否有该用户以及验证用户的绑卡信息  卡号 身份证
        try {
            data_detail_item.setMall_no(collectionRequest.getMall_no());
            data_detail_item.setMer_no(collectionRequest.getMer_no());
            data_detail_item.setPartner_trans_date(collectionRequest.getPartner_trans_date());
            data_detail_item.setPartner_trans_time(collectionRequest.getPartner_trans_time());
            EaccUserinfo eaccUserinfo = accountSearchService.checkUserinfoWithoutMobile(data_detail_item);
            logger.info("【代扣充值】eaccUserinfo：" + eaccUserinfo);
            if (null != data_detail_item.getCard_type() && CardType.CREDIT.getCode().equals(data_detail_item.getCard_type())) {
                logger.info("【代扣充值】【" + transTransReqDetail.getOrder_no() + "】【信用卡！目前不支持信用卡代扣充值");
                throw new BusinessException(BusinessMsg.PARAMETER_ERROR, String.format("【%s】是信用卡！目前不支持信用卡代扣充值", data_detail_item.getCard_type()));
            }
            //记录用户充值信息表
            rwRecharge.setCreate_time(DateUtils.today(DateUtils.DEF_FORMAT));
            rwRecharge.setCreate_by(collectionRequest.getMall_no());
            rwRecharge.setTrans_amt(data_detail_item.getAmt());//充值金额
            rwRecharge.setReturn_url(notify_url);//同步回调地址
            rwRecharge.setTrans_serial(transTransReqDetail.getTrans_serial());//交易流水号 业务流水表相同
            rwRecharge.setOrder_no(data_detail_item.getDetail_no());//订单号
            rwRecharge.setPlat_no(collectionRequest.getMer_no());//平台编号
            rwRecharge.setPlatcust(data_detail_item.getPlatcust());//平台客户号
            rwRecharge.setTrans_code(TransConsts.COLLECTION_CODE);//交易代码
            rwRecharge.setTrans_name(TransConsts.COLLECTION_NAME);//交易名称
            rwRecharge.setTrans_date(collectionRequest.getPartner_trans_date());//交易日期
            rwRecharge.setTrans_time(collectionRequest.getPartner_trans_time());//交易时间
            rwRecharge.setPay_code(collectionRequest.getPay_code());//支付编号
            rwRecharge.setReques_time(DateUtils.today());//交易请求时间
            rwRecharge.setStatus(OrderStatus.PROCESSING.getCode());//处理状态
            rwRecharge.setNotify_url(collectionRequest.getNotify_url());//商户异步通知URL
            rwRecharge.setEnabled(Constants.ENABLED);//删除标记
            rwRecharge.setSelf_bank_flag(IsSelfBank.NO.getCode());//默认非本行卡
            rwRecharge.setCharge_type(Ssubject.INVEST.getCode());
            rwRecharge.setClient_property("0");
            if (data_detail_item.getClient_property() != null) {
                if (!CusType.PERSONAL.getCode().equals(data_detail_item.getClient_property()) && !CusType.COMPANY.getCode().equals(data_detail_item.getClient_property())) {
                    throw new BusinessException(BusinessMsg.PARAMETER_ERROR, "用户公私类型错误");
                }
                rwRecharge.setClient_property(data_detail_item.getClient_property());
            }

            if (null != data_detail_item.getCharge_type() && !"".equals(data_detail_item.getCharge_type())) {
                if (!Ssubject.INVEST.getCode().equals(data_detail_item.getCharge_type()) && !Ssubject.FINANCING.getCode().equals(data_detail_item.getCharge_type())) {
                    logger.info("【代扣充值】【" + transTransReqDetail.getOrder_no() + "】投融资账户类型错误");
                    throw new BusinessException(BusinessMsg.PARAMETER_ERROR, "投融资账户类型错误");
                }
                if (Ssubject.FINANCING.getCode().equals(data_detail_item.getCharge_type())) {
                    rwRecharge.setCharge_type(Ssubject.FINANCING.getCode());
                } else if (Ssubject.EACCOUNT.getCode().equals(data_detail_item.getCharge_type()) || null != eaccUserinfo.getEaccount()) {
                    rwRecharge.setCharge_type(Ssubject.EACCOUNT.getCode());
                } else {
                    rwRecharge.setCharge_type(Ssubject.INVEST.getCode());
                }
            }
            rwRecharge.setCard_no(data_detail_item.getCard_no());
            rwRecharge.setExt1(data_detail_item.getName());
            rwRechargeMapper.insert(rwRecharge);
        } catch (Exception e) {
            logger.error("【代扣充值】调用第三方之前异常:", e);
            BaseResponse baseResponse = new BaseResponse();
            if (e instanceof BusinessException) {
                baseResponse = ((BusinessException) e).getBaseResponse();
            } else {
                baseResponse.setRecode(BusinessMsg.DATEBASE_EXCEPTION);
                baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.DATEBASE_EXCEPTION));
            }
            BatchCollectionResponse.Data d=new BatchCollectionResponse.Data();
            d.setStatus(OrderStatus.FAIL.getCode());
            d.setDetail_no(data_detail_item.getDetail_no());
            d.setAmt(data_detail_item.getAmt());
            d.setError_no(baseResponse.getRecode());
            d.setError_info(baseResponse.getRemsg());
            collectionDataResponse.getData().add(d);
            transTransReqDetail.setStatus(FlowStatus.FAIL.getCode());
            transTransReqDetail.setReturn_code(baseResponse.getRecode());
            transTransReqDetail.setReturn_msg(baseResponse.getRemsg());
            transReqService.insert(transTransReqDetail);
            return null;
        }
        return rwRecharge;
    }
    public BatchCollectionResponse sendBatchCollection(TransTransreq transTransReq,List<RwRecharge> rwRecharges,String host,String url,String channal_id,String mall_no)throws BusinessException{
        BatchCollectionResponse batchCollectionResponse=new BatchCollectionResponse();
        PlatCardinfo platCardinfo=accountQueryService.getPlatcardinfo(transTransReq.getPlat_no(),PlatAccType.PLATTRUST.getCode());
        Map<String,Object> params=new HashMap<>();
        String trans_serial=transTransReq.getTrans_serial();
        params.put("host",host);
        params.put("url",url);
        params.put("partner_id","10000001");
        params.put("partner_serial_no",trans_serial);
        params.put("partner_trans_date",DateUtils.getyyyyMMddDate());
        params.put("partner_trans_time",DateUtils.getHHmmssTime());
        params.put("third_no",platCardinfo.getCard_no());
        params.put("sendercomp_id","10000001");
        params.put("targetcomp_id","91000");
        params.put("channelId",channal_id);
        List<BatchPayDetail> detailList=new ArrayList<>();
        BigDecimal allAmt=BigDecimal.ZERO;
        for(RwRecharge rw:rwRecharges){
            BatchPayDetail detail=new BatchPayDetail();
            detail.setDetail_no(rw.getTrans_serial());
            detail.setOccur_balance(rw.getTrans_amt());
            detail.setClient_name(rw.getExt1());
            detail.setCard_no(rw.getCard_no());
            //detail.setBank_id(rw.getb);
            detail.setClient_property(rw.getClient_property());
            detail.setSummary(platCardinfo.getCard_no().substring(platCardinfo.getCard_no().length()-5)
                    +"->"+rw.getCard_no().substring(rw.getCard_no().length()-5));
            allAmt=allAmt.add(rw.getTrans_amt());
            detailList.add(detail);
        }
        params.put("total_num",rwRecharges.size());
        params.put("total_balance",allAmt.toString());
        params.put("details",JSON.toJSONString(detailList, GlobalConfig.serializerFeature));

        if (deployEnvironment.equals("DEV")){
            for(RwRecharge rw:rwRecharges){
                RwRecharge rwRechargeForCharge = accountSearchService.queryRwRecharge(rw.getPlat_no(), rw.getOrder_no());
                rwRechargeForCharge.setPayment_date(DateUtils.getyyyyMMddDate());
                rwRechargeForCharge.setStatus(OrderStatus.SUCCESS.getCode());
                Boolean flag = rechargeOptionService.doRecharge(rwRechargeForCharge, mall_no,rwRechargeForCharge.getOrder_no(), TransConsts.COLLECTION_CODE);
                logger.info("【批量代扣】返回flag："+flag);
                BatchCollectionResponse.Data d=new BatchCollectionResponse.Data();
                d.setStatus(OrderStatus.SUCCESS.getCode());
                d.setDetail_no(rw.getOrder_no());
                d.setAmt(rw.getTrans_amt());
                batchCollectionResponse.getData().add(d);
            }
            return batchCollectionResponse;
        }

        logger.info("【批量代扣】请求三方参数："+JSON.toJSON(params));
        Map<String,Object> result_map=adapterService.batchCollection(params);
        logger.info("【批量代扣】三方响应参数："+JSON.toJSON(result_map));
        if(!OrderStatus.SUCCESS.getCode().equals(result_map.get("order_status"))){
            throw new BusinessException(result_map.get("recode").toString(),result_map.get("remsg").toString());
        }

        BatchPayResponse response =new BatchPayResponse();
        List<BatchPayResponseData> batchPayResponseDataList =new ArrayList<BatchPayResponseData>();
        try {
            response = JSON.parseObject(result_map.get("data").toString(), BatchPayResponse.class);
            batchPayResponseDataList = response.getData();
        }catch (Exception e){
            logger.error("【批量代扣】第三方参数解析失败",e);
            throw new BusinessException(BusinessMsg.CALL_REMOTE_ERROR,
                    BusinessMsg.getMsg(BusinessMsg.CALL_REMOTE_ERROR) + "：第三方接口返回的数据无法解析");
        }

        if(response.getData()!=null){
            for(RwRecharge rw:rwRecharges){
                String detail_trans_serial=rw.getTrans_serial();
                for(BatchPayResponseData data:batchPayResponseDataList){
                    if(detail_trans_serial.equals(data.getPartner_serial_no())){
                        RwRecharge rwRechargeForCharge = accountSearchService.queryRwRecharge(rw.getPlat_no(), rw.getOrder_no());
                        if(data.getPay_finish_date()!=null){
                            rwRechargeForCharge.setPayment_date(data.getPay_finish_date());
                        } else{
                            rwRechargeForCharge.setPayment_date(DateUtils.getyyyyMMddDate());
                        }
                        if(null!=data.getSelf_bank_flag() && !"".equals(data.getSelf_bank_flag())){
                            rwRechargeForCharge.setSelf_bank_flag(IsSelfBank.YES.getCode().equals(data.getSelf_bank_flag().toString())?IsSelfBank.YES.getCode():IsSelfBank.NO.getCode());
                        }
                        String status=FlowStatus.INPROCESS.getCode();
                        if("3".equals(data.getPay_status())){
                            //交易成功
                            status=FlowStatus.SUCCESS.getCode();
                        }else if("2".equals(data.getPay_status())){
                            //处理中
                            status=FlowStatus.INPROCESS.getCode();
                        }else{
                            //交易失败
                            status=FlowStatus.FAIL.getCode();
                        }
                        rwRechargeForCharge.setStatus(status);
                        //进行转账，并且更新流水，记录响应信息
                        logger.info("【批量代扣】调用doRecharge:"+JSON.toJSON(rwRechargeForCharge));
                        Boolean flag = rechargeOptionService.doRecharge(rwRechargeForCharge, mall_no,rwRechargeForCharge.getOrder_no(), TransConsts.COLLECTION_CODE);
                        logger.info("【批量代扣】返回flag："+flag);
                        BatchCollectionResponse.Data d=new BatchCollectionResponse.Data();
                        d.setStatus(status);
                        d.setDetail_no(rw.getOrder_no());
                        d.setAmt(rw.getTrans_amt());
                        batchCollectionResponse.getData().add(d);
                        break;
                    }
                }
            }
        }
        return batchCollectionResponse;
    }
}
