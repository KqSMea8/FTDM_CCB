package com.sunyard.sunfintech.user.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sunyard.sunfintech.account.provider.IAccountOprationService;
import com.sunyard.sunfintech.account.provider.IAccountQueryService;
import com.sunyard.sunfintech.core.Constants;
import com.sunyard.sunfintech.core.TransConsts;
import com.sunyard.sunfintech.core.base.BaseErrorData;
import com.sunyard.sunfintech.core.base.BaseRequest;
import com.sunyard.sunfintech.core.base.BaseResponse;
import com.sunyard.sunfintech.core.base.BaseServiceSimple;
import com.sunyard.sunfintech.core.dic.*;
import com.sunyard.sunfintech.core.exception.BusinessException;
import com.sunyard.sunfintech.core.exception.BusinessMsg;
import com.sunyard.sunfintech.core.util.*;
import com.sunyard.sunfintech.custdao.mapper.CustEaccUserAuthMapper;
import com.sunyard.sunfintech.dao.entity.*;
import com.sunyard.sunfintech.dao.mapper.EaccUserauthMapper;
import com.sunyard.sunfintech.pub.model.MsgModel;
import com.sunyard.sunfintech.pub.model.NotifyData;
import com.sunyard.sunfintech.pub.provider.*;
import com.sunyard.sunfintech.thirdparty.provider.IAdapterService;
import com.sunyard.sunfintech.user.model.bo.*;
import com.sunyard.sunfintech.user.provider.IAccountSearchService;
import com.sunyard.sunfintech.user.provider.IUserAccountExtService;
import com.sunyard.sunfintech.user.provider.IUserAccountService;
import com.sunyard.sunfintech.user.provider.IUserBindCardService;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

/**
 * 用户绑卡服务
 * Created by terry on 2017/5/16.
 */
@Service(interfaceClass = IUserBindCardService.class)
@CacheConfig(cacheNames="userBindCardService")
@org.springframework.stereotype.Service("userBindCardService")
public class UserBindCardService extends BaseServiceSimple implements IUserBindCardService {

    @Autowired
    private ITransReqService transReqService;

    @Autowired
    private IUserAccountService userAccountService;

    @Autowired
    private IAccountOprationService accountOprationService;

    @Autowired
    private IAdapterService adapterService;

    @Autowired
    private ISendMsgService sendMsgService;

    @Autowired
    private ISysParameterService sysParameterService;

    @Autowired
    private IAccountSearchService accountSearchService;

    @Value("${deploy.environment}")
    private String deployEnvironment;

//    @Autowired
//    private CustOpenconfigMapper custOpenconfigMapper;

    @Autowired
    private INotifyService notifyService;

    @Autowired
    private CustEaccUserAuthMapper custEaccUserAuthMapper;

    @Autowired
    private EaccUserauthMapper eaccUserAuthMapper;

    @Autowired
    private IAccountQueryService accountQueryService;

    @Autowired
    private IUserAccountExtService userAccountExtService;

    @Autowired
    private IAuthCheckService authCheckService;

    @Value("${mock.switch}")
    private String mockSwitch;



    @Override
    public boolean bingCardByMsg(ApplyBindCardRequestBo applyBindCardRequestBo) throws BusinessException {
        logger.info("【短验绑卡申请】==========开始短验绑卡申请，参数："+applyBindCardRequestBo.toString());
        //记录流水
        TransTransreq transTransReq= transReqService.getTransTransReq(applyBindCardRequestBo.clone(),
                TransConsts.CARD_BIND_APPLY_CODE, TransConsts.CARD_BIND_APPLY_NAME,false);
        try{
            //查询相应绑卡通道
            PlatPaycode platPaycode=userAccountService.queryPlatPaycode(applyBindCardRequestBo.getMer_no(),
                    applyBindCardRequestBo.getPay_code());
            if(platPaycode==null){
                //渠道不存在,抛异常，平台客户号错误
                logger.info("【短验绑卡申请】==========渠道不存在，pay_code："+applyBindCardRequestBo.getPay_code());
                throw new BusinessException(BusinessMsg.NO_CHANNEL_INFORMATION,BusinessMsg.getMsg(BusinessMsg.NO_CHANNEL_INFORMATION)+"：请检查pay_code是否合法");
            }

            /*------------------------------申请绑卡前先验证是否用户开户注册，是否绑卡(申请中不开户)--------------------------*/
            EaccCardinfo eaccCardInfo =null;
            EaccUserinfo eInfo = null;
            EaccUserinfo eaccUserInfo=null;
            boolean flag = false;//未注册
            BaseResponse baseResponse = new BaseResponse();
            if(!Constants.OLD_INTERFACE.equals(applyBindCardRequestBo.getPlatcust())){
                eaccUserInfo = userAccountService.queryEaccUserInfoByMallNoAndPlatcust(applyBindCardRequestBo.getMall_no(), applyBindCardRequestBo.getPlatcust());
                if (eaccUserInfo == null || CusType.COMPANY.getCode().equals(eaccUserInfo.getCus_type())) {
                    String msg = String.format("根据商户集团编号【%s】和集团客户号【%s】查询不到该用户信息", applyBindCardRequestBo.getMall_no(), applyBindCardRequestBo.getPlatcust());
                    logger.info("【短验绑卡申请】=========" + msg);
                    throw new BusinessException(BusinessMsg.PARAMETER_ERROR,"查询不到该用户信息或者该用户是企业用户");
                }
                boolean isOverCardLimit =true;
                if(StringUtils.isBlank(applyBindCardRequestBo.getCard_type())){
                    applyBindCardRequestBo.setCard_type(CardType.DEBIT.getCode());
                }
                if(CardType.DEBIT.getCode().equals(applyBindCardRequestBo.getCard_type())){
                    isOverCardLimit=userAccountService.checkCardLimit(applyBindCardRequestBo.getMall_no(),  applyBindCardRequestBo.getPlatcust());
                    logger.info("【短验绑卡申请】");
                }else if(CardType.CREDIT.getCode().equals(applyBindCardRequestBo.getCard_type())){
                    isOverCardLimit=userAccountService.checkCreditCardLimit(applyBindCardRequestBo.getMall_no(),  applyBindCardRequestBo.getPlatcust());
                }
                if (isOverCardLimit) {
                    logger.info("【短验绑卡申请】=========个人借记卡绑卡数量超过限制。");
                    throw new BusinessException(BusinessMsg.CARD_NUMBER_OUT_LIMIT, BusinessMsg.getMsg(
                            BusinessMsg.CARD_NUMBER_OUT_LIMIT) + "：客户【" +  applyBindCardRequestBo.getPlatcust() + "】个人借记卡绑卡数量超过限制");
                }
                //查询出卡信息，判断是否绑卡
//                eaccCardInfo = userAccountService.queryEaccCardInfo(applyBindCardRequestBo.getMall_no(),
//                        applyBindCardRequestBo.getPlatcust(), applyBindCardRequestBo.getCard_no(), null);
//                if (eaccCardInfo != null) {
//                    logger.info("【短验绑卡申请】=========用户已绑卡，卡号为：" + eaccCardInfo.getCard_no());
//                    if (applyBindCardRequestBo.getCard_no().equals(eaccCardInfo.getCard_no())) {
//                        throw new BusinessException(BusinessMsg.REPEAT_BINDING, BusinessMsg.getMsg(
//                                BusinessMsg.REPEAT_BINDING) + "：绑卡信息与请求绑卡信息一致，无需重复绑卡");
//                    } else {
//                        throw new BusinessException(BusinessMsg.REPEAT_BINDING, BusinessMsg.getMsg(
//                                BusinessMsg.REPEAT_BINDING) + "：绑卡信息与请求绑卡信息不一致，请检查传入卡号是否正确");
//                    }
//                }
            }else{
                eaccUserInfo = userAccountService.checkUserInfo(applyBindCardRequestBo.getMall_no(),
                        applyBindCardRequestBo.getName(),applyBindCardRequestBo.getId_code(),applyBindCardRequestBo.getMer_no(),true);
            }
            //TODO 调用绑卡接口
            Map<String,Object> params=new HashMap<String,Object>();
            params.put("partner_id",platPaycode.getPayment_plat_no());
            params.put("partner_serial_no",transTransReq.getTrans_serial());
            params.put("partner_trans_date",applyBindCardRequestBo.getPartner_trans_date());
            params.put("partner_trans_time",applyBindCardRequestBo.getPartner_trans_time());
            params.put("client_name", eaccUserInfo==null?applyBindCardRequestBo.getName():eaccUserInfo.getName());
            params.put("id_kind", "0");
            params.put("id_no",  eaccUserInfo==null?applyBindCardRequestBo.getId_code():eaccUserInfo.getId_code());
            params.put("mobile_tel",applyBindCardRequestBo.getPre_mobile());
            params.put("card_no",applyBindCardRequestBo.getCard_no());
            params.put("func_code","1");
            params.put("sendercomp_id",platPaycode.getPayment_plat_no());
            params.put("targetcomp_id","91000");
            params.put("pay_bankacct_type","0");
            params.put("mall_no",applyBindCardRequestBo.getMall_no());
            params.put("mer_no",applyBindCardRequestBo.getMer_no());
            params.put("plat_cust",eaccUserInfo==null?"":eaccUserInfo.getMallcust());
            params.put("open_account","0");
            params.put("is_bankcheck",platPaycode.getIs_bankcheck());
            //==========建行差异字段==========
            params.put("partner_terminal_id","宝付必填");
            params.put("cus_type","1");
            params.put("channelId",platPaycode.getChannel_id());


            //=======================================
            //=========雅酷必填字段============
            logger.info("remark："+applyBindCardRequestBo.getRemark());
            if (StringUtils.isBlank(applyBindCardRequestBo.getRemark()) || "null".equals(applyBindCardRequestBo.getRemark())) {
                params.put("partner_userid",transTransReq.getTrans_serial());
            }else{
                params.put("partner_userid",applyBindCardRequestBo.getRemark());
            }

            if(!Constants.OLD_INTERFACE.equals(applyBindCardRequestBo.getPlatcust())){
                //新接口
                params.put("host", sysParameterService.querySysParameter(applyBindCardRequestBo.getMall_no(), URLConfigUtil.EPAY_SERVER_KEY));
                params.put("url", sysParameterService.querySysParameter(applyBindCardRequestBo.getMall_no(), URLConfigUtil.BAND_ACCOUNT_VERIFY_KEY));
            }else{
                //旧接口
                params.put("host", sysParameterService.querySysParameter(applyBindCardRequestBo.getMall_no(), URLConfigUtil.EPAY_SERVER_KEY));
                params.put("url", sysParameterService.querySysParameter(applyBindCardRequestBo.getMall_no(), URLConfigUtil.BAND_ACCOUNT_VERIFY_KEY_OLD));
            }
            logger.info("【短验绑卡申请】请求三方参数："+JSON.toJSON(params));

            //====================
            Map<String,Object> MD5Map = new HashMap<String,Object>();
            MD5Map.put("card_no",applyBindCardRequestBo.getCard_no());
            MD5Map.put("order_no",applyBindCardRequestBo.getOrder_no());
            MD5Map.put("plat_cust",applyBindCardRequestBo.getPlatcust());
            MD5Map.put("moblie",applyBindCardRequestBo.getPre_mobile());
            Integer paramsHash=MD5Map.hashCode();
            CacheUtil.getCache().set(Constants.BIND_CARD_CACHE_KEY+applyBindCardRequestBo.getOrder_no(),paramsHash);
            //====================


            //===================================
            Map<String, Object> msgBindCardResponse = new HashMap<>();
            if(!Constants.OLD_INTERFACE.equals(applyBindCardRequestBo.getPlatcust())){
                msgBindCardResponse=adapterService.isicBindCard(params);
            }else{
                msgBindCardResponse=adapterService.bindAccountVerify(params);
            }
            //判断是否成功
            logger.info("【短验绑卡申请】=========E支付返回："+msgBindCardResponse.toString());
            if(!OrderStatus.SUCCESS.getCode().equals(msgBindCardResponse.get("order_status"))){
                logger.info("【短验绑卡申请】=========绑卡申请失败"+msgBindCardResponse.get("remsg"));
                throw new BusinessException(BusinessMsg.TIEDCARD_FAILED,"绑卡申请失败："+msgBindCardResponse.get("remsg"));
            }
            logger.info("【短验绑卡申请】=========绑卡申请成功");
            //更新流水
            transTransReq.setReturn_code(BusinessMsg.SUCCESS);
            transTransReq.setReturn_msg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));
            transTransReq.setStatus(FlowStatus.SUCCESS.getCode());
            transTransReq.setPlatcust(applyBindCardRequestBo.getPlatcust());
            transReqService.insert(transTransReq);
            logger.info("【短验绑卡申请】=========短验绑卡申请完成\n\n\n");
            return true;
        }catch (Exception e){
            logger.error("【短验绑卡申请】=========异常:",e);
            BaseResponse baseResponse=new BaseResponse();
            if(e instanceof BusinessException){
                baseResponse=((BusinessException) e).getBaseResponse();
            }else{
                baseResponse.setRecode(BusinessMsg.RUNTIME_EXCEPTION);
                baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.RUNTIME_EXCEPTION));
            }
            transTransReq.setReturn_code(baseResponse.getRecode());
            transTransReq.setReturn_msg(baseResponse.getRemsg());
            transTransReq.setStatus(FlowStatus.FAIL.getCode());
            transTransReq.setPlatcust(applyBindCardRequestBo.getPlatcust());
            transReqService.insert(transTransReq);
            throw new BusinessException(baseResponse);
        }


    }

    @Override
    @Transactional
    public String confirmBindCard(ConfirmBindCardBo confirmBindCardBo) throws BusinessException {
        logger.info("【短验绑卡确认】=========请求参数："+JSON.toJSON(confirmBindCardBo));

        String plcust = "";
        TransTransreq transTransReq = null;
        BaseResponse baseResponse = new BaseResponse();
        PlatPaycode platPaycode = null;
        EaccCardinfo eaccCardInfo = null;
        EaccUserinfo eaccUserInfo=null;

        // 校验申请参数与确认参数是否匹配
        Map<String,Object> MD5Map = new HashMap<String,Object>();
        MD5Map.put("card_no",confirmBindCardBo.getCard_no());
        MD5Map.put("order_no",confirmBindCardBo.getOrigin_order_no());
        MD5Map.put("plat_cust",confirmBindCardBo.getPlatcust());
        MD5Map.put("moblie",confirmBindCardBo.getPre_mobile());
        Integer paramsHash=MD5Map.hashCode();
        Object hashObj= CacheUtil.getCache().get(Constants.BIND_CARD_CACHE_KEY+confirmBindCardBo.getOrigin_order_no());
        if(hashObj!=null){
            if(!Objects.equals(paramsHash, (Integer) hashObj)){
                logger.info("申请确认参数不匹配"+confirmBindCardBo.getOrigin_order_no());
                baseResponse.setRecode(BusinessMsg.URL_UNLAWFUL_REQUEST);
                baseResponse.setRemsg("申请与确认参数不一致");
                throw  new BusinessException(baseResponse);
            }
        }


        //记录短验绑卡确认业务流水表 trans_transreq
        transTransReq = transReqService.getTransTransReq(confirmBindCardBo.clone(), TransConsts.CARD_BIND_CONFIRM_CODE, TransConsts.CARD_BIND_CONFIRM_NAME,false);
        transTransReq.setPlatcust(confirmBindCardBo.getPlatcust());
        transReqService.insert(transTransReq);
        logger.info("【短验绑卡确认】=========记录短验绑卡确认流水成功，trans_serial："+transTransReq.getTrans_serial());
        String readyPlatcust=SeqUtil.getSeqNum();
        try{
            //默认绑卡类型
            if(StringUtils.isBlank(confirmBindCardBo.getCard_type())){
                confirmBindCardBo.setCard_type(CardType.DEBIT.getCode());
            }
            TransTransreq ttReq = transReqService.queryTransTransReqByOrderno(confirmBindCardBo.getOrigin_order_no());
            if (ttReq == null || StringUtils.isEmpty(ttReq.getTrans_serial())) {
                baseResponse.setRecode(BusinessMsg.NVALID_PLAT_NO);
                baseResponse.setRemsg("根据原绑卡申请订单号查询不到原绑卡申请流水，请确认！");
                throw new BusinessException(baseResponse);
            }

            //获得第三方通道，用于查询卡信息
            platPaycode=userAccountService.queryPlatPaycode(confirmBindCardBo.getMer_no(),
                    confirmBindCardBo.getPay_code());
            if(platPaycode==null){
                //渠道不存在,抛异常
                baseResponse.setRecode(BusinessMsg.NVALID_PLAT_NO);
                baseResponse.setRemsg("【短验绑卡确认】=========短验绑卡确认渠道不存在，trans_serial："+transTransReq.getTrans_serial());
                throw new BusinessException(baseResponse);
            }
            /*------------------------------确认绑卡前先验证是否用户开户注册，是否绑卡--------------------------*/

            if(!Constants.OLD_INTERFACE.equals(confirmBindCardBo.getPlatcust())){
                eaccUserInfo = userAccountService.queryEaccUserInfoByMallNoAndPlatcust(confirmBindCardBo.getMall_no(), confirmBindCardBo.getPlatcust());
                if (eaccUserInfo == null || CusType.COMPANY.getCode().equals(eaccUserInfo.getCus_type())) {
                    String msg = String.format("根据商户集团编号【%s】和集团客户号【%s】查询不到该用户信息", confirmBindCardBo.getMall_no(), confirmBindCardBo.getPlatcust());
                    logger.info("【短验绑卡确认】" + msg);
                    baseResponse.setRecode(BusinessMsg.PARAMETER_ERROR);
                    baseResponse.setRemsg("查询不到该用户信息或者该用户是企业用户");
                    throw new BusinessException(baseResponse);
                }
            }else{
                eaccUserInfo = userAccountService.checkUserInfo(confirmBindCardBo.getMall_no(), confirmBindCardBo.getName(),confirmBindCardBo.getId_code(),confirmBindCardBo.getMer_no(),true);
                logger.info("【短验绑卡确认】userinfo:"+JSONObject.toJSONString(eaccUserInfo));
            }
            if (eaccUserInfo != null){
                plcust=eaccUserInfo.getMallcust();
            }

            /*--------------------------------开始发送确认绑卡请求------------------------------------*/

            logger.info("==================" + "开始组装确认绑卡请求参数，trans_serial：" + transTransReq.getTrans_serial());

            Map<String, Object> params = new HashMap<String, Object>();
            //==============================新接口================================
            params.put("partner_id", platPaycode.getPayment_plat_no());
            params.put("partner_serial_no", ttReq.getTrans_serial());
            params.put("partner_trans_date", confirmBindCardBo.getPartner_trans_date());
            params.put("partner_trans_time", confirmBindCardBo.getPartner_trans_time());
            params.put("client_name", eaccUserInfo==null?confirmBindCardBo.getName():eaccUserInfo.getName());
            params.put("id_kind", "0");
            params.put("id_no",  eaccUserInfo==null?confirmBindCardBo.getId_code():eaccUserInfo.getId_code());
            params.put("mobile_tel", confirmBindCardBo.getPre_mobile());
            params.put("card_no", confirmBindCardBo.getCard_no());
            params.put("func_code", "2");
            params.put("verify_info", confirmBindCardBo.getIdentifying_code());
            params.put("sendercomp_id", platPaycode.getPayment_plat_no());
            params.put("targetcomp_id", "91000");
            params.put("pay_bankacct_type", "0");
            params.put("mall_no", confirmBindCardBo.getMall_no());
            params.put("mer_no", confirmBindCardBo.getMer_no());
            params.put("plat_cust",  eaccUserInfo==null?confirmBindCardBo.getPlatcust():eaccUserInfo.getMallcust());
            params.put("open_account", "0");
            params.put("is_bankcheck", platPaycode.getIs_bankcheck());
            //==========建行差异字段==========
            params.put("partner_terminal_id", "宝付必填");
            params.put("cus_type","1");
            params.put("channelId", platPaycode.getChannel_id());
//              params.put("bank_id","非必填");
            //=======================================
            //=========雅酷必填字段============
            logger.info("remark：" + confirmBindCardBo.getRemark());
            if (StringUtils.isBlank(confirmBindCardBo.getRemark()) || "null".equals(confirmBindCardBo.getRemark())) {
                params.put("partner_userid", ttReq.getTrans_serial());
            } else {
                params.put("partner_userid", confirmBindCardBo.getRemark());
            }
            if(!Constants.OLD_INTERFACE.equals(confirmBindCardBo.getPlatcust())){
                //新接口
                params.put("host", sysParameterService.querySysParameter(confirmBindCardBo.getMall_no(), URLConfigUtil.EPAY_SERVER_KEY));
                params.put("url", sysParameterService.querySysParameter(confirmBindCardBo.getMall_no(), URLConfigUtil.BAND_ACCOUNT_VERIFY_KEY));
            }else{
                //旧接口
                params.put("host", sysParameterService.querySysParameter(confirmBindCardBo.getMall_no(), URLConfigUtil.EPAY_SERVER_KEY));
                params.put("url", sysParameterService.querySysParameter(confirmBindCardBo.getMall_no(), URLConfigUtil.BAND_ACCOUNT_VERIFY_KEY_OLD));
            }
            //===================================
            //短信确认绑卡
            logger.info("==================" + "开始发送确认绑卡请求" + JSON.toJSON(params));

            Map<String, Object> msgBindCardResponse = new HashMap<>();
            if(!Constants.OLD_INTERFACE.equals(confirmBindCardBo.getPlatcust())){
                msgBindCardResponse=adapterService.isicBindCard(params);
            }else{
                msgBindCardResponse=adapterService.bindAccountVerify(params);
            }
            logger.info("返回msgBindCardResponse：" + JSON.toJSON(msgBindCardResponse));
            if (!OrderStatus.SUCCESS.getCode().equals(msgBindCardResponse.get("order_status"))) {
                String recode = (String) msgBindCardResponse.get("recode");
                if ("001003".equals(recode)) {
                    baseResponse.setRecode(BusinessMsg.VERIFICATION_ERROR);
                    baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.VERIFICATION_ERROR));
                    throw new BusinessException(baseResponse);
                } else if ("001002".equals(recode)) {
                    baseResponse.setRecode(BusinessMsg.VERIFICATION_OUT_OF_DATE);
                    baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.VERIFICATION_OUT_OF_DATE));
                    throw new BusinessException(baseResponse);
                } else if ("020513".equals(recode)) {
                    baseResponse.setRecode(BusinessMsg.VERIFICATION_ERROR_TOO_MUCH);
                    baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.VERIFICATION_ERROR_TOO_MUCH));
                    throw new BusinessException(baseResponse);
                } else if (BusinessMsg.CFCA_AUTH_FAILED.equals(recode)) {
                    throw new BusinessException(BusinessMsg.CFCA_AUTH_FAILED, BusinessMsg.getMsg(BusinessMsg.CFCA_AUTH_FAILED));
                } else {
                    baseResponse.setRecode(BusinessMsg.FOUR_VERIFY_FAILED);
                    baseResponse.setRemsg("msgBindCardResponse：短验确认接口返回数据异常：" + msgBindCardResponse.get("remsg"));
                    throw new BusinessException(baseResponse);
                }
            }
            //=============================以下代码为了兼容老接口=========================
            if (Constants.OLD_INTERFACE.equals(confirmBindCardBo.getPlatcust()) && StringUtils.isBlank(plcust)) {//未注册
               //开户注册
               AccountSubjectInfo accountSubjectInfo = new AccountSubjectInfo();
               accountSubjectInfo.setPlat_no(confirmBindCardBo.getMer_no());
               accountSubjectInfo.setMall_platcust(readyPlatcust);
               accountSubjectInfo.setAccount_type(AccountType.Platcust.getCode());
               accountSubjectInfo.setPlatcust(readyPlatcust);
               accountSubjectInfo = accountOprationService.register(accountSubjectInfo);
               plcust = accountSubjectInfo.getPlatcust();
               logger.info("【短验绑卡确认】=========短验绑卡开户成功，trans_serial：" + transTransReq.getTrans_serial());
               //用户注册
               EaccAccountinfo eaccAccountInfo = new EaccAccountinfo();
               eaccAccountInfo.setMall_no(confirmBindCardBo.getMall_no());
               eaccAccountInfo.setPlat_no(confirmBindCardBo.getMer_no());
               eaccAccountInfo.setPlatcust(accountSubjectInfo.getPlatcust());
               eaccAccountInfo.setMallcust(accountSubjectInfo.getPlatcust());
               EaccUserinfo eaccUserInfo_new = new EaccUserinfo();
               eaccUserInfo_new.setMall_no(confirmBindCardBo.getMall_no());
               eaccUserInfo_new.setMallcust(accountSubjectInfo.getPlatcust());
               eaccUserInfo_new.setCus_type(CusType.PERSONAL.getCode());
               eaccUserInfo_new.setId_type("1");
               eaccUserInfo_new.setId_code(confirmBindCardBo.getId_code());
               eaccUserInfo_new.setMobile(confirmBindCardBo.getPre_mobile());
               eaccUserInfo_new.setName(confirmBindCardBo.getName());
               eaccUserInfo_new.setDefault_card_no(confirmBindCardBo.getCard_no());
               eaccUserInfo_new.setDefault_mobile(confirmBindCardBo.getPre_mobile());
               userAccountService.register(eaccAccountInfo, eaccUserInfo_new);
               logger.info("【短验绑卡确认】=========短验绑卡用户开户注册成功，trans_serial：" + transTransReq.getTrans_serial());
            }else if(!Constants.OLD_INTERFACE.equals(confirmBindCardBo.getPlatcust())){
                plcust=confirmBindCardBo.getPlatcust();
            }
            //===================================================================================
            //开始绑卡
            logger.info("【短验绑卡确认】=========开始绑卡,platcust:" + (StringUtils.isEmpty(plcust) ? "null" : plcust) + "，trans_serial：" + transTransReq.getTrans_serial());
            eaccCardInfo = userAccountService.queryEaccCardInfo(confirmBindCardBo.getMall_no(),
                    plcust, confirmBindCardBo.getCard_no(), platPaycode.getChannel_id());
            logger.info("【短验绑卡确认】=========开始绑卡,eacc_cardinfo:" + JSON.toJSONString(eaccCardInfo) + "，trans_serial：" + transTransReq.getTrans_serial());
            if (eaccCardInfo == null || !confirmBindCardBo.getPre_mobile().equals(eaccCardInfo.getMobile())) {
                eaccCardInfo = new EaccCardinfo();
                eaccCardInfo.setMall_no(confirmBindCardBo.getMall_no());
                eaccCardInfo.setCard_no(confirmBindCardBo.getCard_no());
                eaccCardInfo.setCard_type(confirmBindCardBo.getCard_type());
                eaccCardInfo.setMallcust(plcust);
                eaccCardInfo.setBank_no((String)msgBindCardResponse.get("bank_id"));
                eaccCardInfo.setPay_channel(platPaycode.getChannel_id());
                eaccCardInfo.setBindId(ttReq.getTrans_serial());
                eaccCardInfo.setMobile(confirmBindCardBo.getPre_mobile());
                eaccCardInfo.setStatus(CardStatus.ACTIVE.getCode());
                eaccCardInfo.setRemark(BindCardType.MSGBINDCARD.getCode());
                userAccountService.addBindCardInfo(eaccCardInfo);
                try {
                    MsgModel msgModel = new MsgModel();
                    String mall_name = sysParameterService.querySysParameter(confirmBindCardBo.getMall_no(), MsgContent.MALL_NAME_KEY.getMsg());
                    msgModel.setOrder_no(baseResponse.getOrder_no());
                    msgModel.setPlat_no(transTransReq.getPlat_no());
                    msgModel.setTrans_code(transTransReq.getTrans_code());
                    msgModel.setMobile(confirmBindCardBo.getPre_mobile());

                    if ("".equals(confirmBindCardBo.getPlatcust()) || confirmBindCardBo.getPlatcust() == null) {
                        logger.info("短验绑卡确认完成，发送开户通知短信，trans_serial：" + transTransReq.getTrans_serial());
                        String content=sysParameterService.querySysParameter(confirmBindCardBo.getMall_no(),MsgContent.OPEN_ACCOUNT_NOTIFY.getMsg());
                        if(StringUtils.isNotBlank(content)) {
                            msgModel.setMsgContent(String.format(MsgContent.OPEN_ACCOUNT_NOTIFY.getMsg(), mall_name));
                        }
                    } else {
                        //发送绑卡短信
                        logger.info("短验绑卡确认完成，发送绑卡通知短信，trans_serial：" + transTransReq.getTrans_serial());
                        //根据card_type来判断发送对应短信
                        String content="";
                       if(CardType.DEBIT.getCode().equals(eaccCardInfo.getCard_type())){
                           content=sysParameterService.querySysParameter(confirmBindCardBo.getMall_no(),MsgContent.BIND_CARD_DEBIT_NOTIFY.getMsg());
                           if(StringUtils.isNotBlank(content)) {
                               msgModel.setMsgContent(String.format(content, mall_name));
                           }
                       }else if (CardType.CREDIT.getCode().equals(eaccCardInfo.getCard_type())){
                           content=sysParameterService.querySysParameter(confirmBindCardBo.getMall_no(),MsgContent.BIND_CARD_CREDIT_NOTIFY.getMsg());
                           if(StringUtils.isNotBlank(content)) {
                               msgModel.setMsgContent(String.format(content, mall_name));
                           }
                       }
                    }
                    if(StringUtils.isNotBlank(msgModel.getMsgContent())){
                        sendMsgService.addMsgToQueue(msgModel);
                    }
                } catch (Exception e) {
                    logger.info("【短信发送失败】=======，trans_serial：" + transTransReq.getTrans_serial() + "|" + e);
                }
            }
            //绑卡成功后检查默认卡是否已绑定
            if(eaccUserInfo!=null && StringUtils.isBlank(eaccUserInfo.getDefault_card_no())){
                eaccUserInfo.setDefault_card_no(confirmBindCardBo.getCard_no());
                eaccUserInfo.setDefault_mobile(confirmBindCardBo.getPre_mobile());
                userAccountService.updateEaccUserInfo(eaccUserInfo,confirmBindCardBo.getMall_no(),eaccUserInfo.getMallcust());
            }
            //更新流水
            transTransReq.setReturn_code(BusinessMsg.SUCCESS);
            transTransReq.setReturn_msg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));
            transTransReq.setStatus(FlowStatus.SUCCESS.getCode());
            transReqService.insert(transTransReq);
            logger.info("更新短验绑卡确认业务流水表成功");

            return plcust;
        }catch (Exception e){
            if(e instanceof BusinessException){
                baseResponse = ((BusinessException) e).getBaseResponse();
            }else{
                logger.error("【短验绑卡确认】========异常：",e);
                baseResponse.setRecode(BusinessMsg.DATEBASE_EXCEPTION);
                baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.DATEBASE_EXCEPTION));
            }
            transTransReq.setReturn_code(baseResponse.getRecode());
            transTransReq.setReturn_msg(baseResponse.getRemsg());
            transTransReq.setStatus(FlowStatus.FAIL.getCode());
            transReqService.insert(transTransReq);
            throw new BusinessException(baseResponse);
        }
    }

    @Override
    public UnBindCardReturn bathUnbindCard(BaseRequest baseRequest, List<UnBindCardRequestDetail> unBindCardRequestDetail) throws BusinessException {
        //记录批量流水
        TransTransreq transTransReq=transReqService.getTransTransReq(baseRequest.clone(), TransConsts.CARD_CHANGE_CODE, TransConsts.CARD_CHANGE_NAME,true);
        transReqService.insert(transTransReq);

        UnBindCardReturn unBindCardReturn=new UnBindCardReturn();
        List<UnBindCardSuccess> successDataList=new ArrayList<UnBindCardSuccess>();
        List<BaseErrorData> errorDataList=new ArrayList<BaseErrorData>();
        for(UnBindCardRequestDetail detail:unBindCardRequestDetail){
            try {
                if(StringUtils.isBlank(detail.getPay_code())){
                    detail.setPay_code("099");
                    //如果平台不传pay_code ，则解绑全部绑卡信息
                    validate(detail);
                    unbindAllCard(baseRequest,detail);
                }else{
                    validate(detail);
                    unbindCard(baseRequest,detail);
                }
                UnBindCardSuccess success=new UnBindCardSuccess();
                success.setDetail_no(detail.getDetail_no());
                success.setMobile(detail.getMobile());
                success.setPlatcust(detail.getPlatcust());
                successDataList.add(success);
                //删除绑卡缓存
            }catch (BusinessException e){
                BaseErrorData error=new BaseErrorData();
                error.setDetail_no(detail.getDetail_no());
                error.setError_no(e.getBaseResponse().getRecode());
                error.setError_info(e.getBaseResponse().getRemsg());
                errorDataList.add(error);
            }
        }
        unBindCardReturn.setSuccessDataList(successDataList);
        unBindCardReturn.setErrorDataList(errorDataList);
        transTransReq.setReturn_code(BusinessMsg.SUCCESS);
        transTransReq.setReturn_msg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));
        transTransReq.setStatus(FlowStatus.SUCCESS.getCode());
        return unBindCardReturn;
    }

    private void unbindAllCard(BaseRequest baseRequest, UnBindCardRequestDetail detail) {
        //记录流水
        TransTransreq transTransReq=transReqService.getTransTransReq(baseRequest.clone(), TransConsts.BATCH_CARD_UNBIND_CODE, TransConsts.BATCH_CARD_UNBIND_NAME,true);
        transTransReq.setOrder_no(detail.getDetail_no());
        transTransReq.setPlatcust(detail.getPlatcust());
        transReqService.insert(transTransReq);

        try{
            logger.info("解绑参数："+JSON.toJSON(detail));

            EaccUserinfo oldEaccUserInfo=accountQueryService.getEUserinfoByExist(baseRequest.getMall_no(),detail.getPlatcust());
            if(oldEaccUserInfo==null){
                logger.info("【解绑】用户不存在|platcust:{}",detail.getPlatcust());
                throw new BusinessException(BusinessMsg.INVALID_ACCOUNT,BusinessMsg.getMsg(BusinessMsg.INVALID_ACCOUNT));
            }
            PlatPaycode platPaycode=userAccountService.queryPlatPaycode(baseRequest.getMer_no(),detail.getPay_code());
            if(platPaycode==null){
                logger.info("【解绑】paycode不存在|pay_code:{}",detail.getPay_code());
                throw new BusinessException(BusinessMsg.NO_CHANNEL_INFORMATION,BusinessMsg.getMsg(BusinessMsg.NO_CHANNEL_INFORMATION));
            }

            //获取绑卡信息
            logger.info("【解绑】======开始获取绑卡信息");
            List<String> status=new ArrayList<String>();
            status.add(Constants.ENABLED);
            status.add(Constants.DISABLED);
            List<EaccCardinfo> eaccCardInfos = accountSearchService.queryEaccCardInfo(baseRequest.getMall_no(),detail.getPlatcust(),detail.getCard_no_old(),status);
            logger.info("【解绑】======开始获取绑卡信息:"+JSON.toJSON(eaccCardInfos));


            if(eaccCardInfos==null || eaccCardInfos.size()<=0){
                BaseResponse baseResponse=new BaseResponse();
                baseResponse.setRecode(BusinessMsg.UNBIND_CARD_ERROR);
                baseResponse.setRemsg("未获取到绑卡信息！");
                throw new BusinessException(baseResponse);
            }

            if(!Constants.OLD_INTERFACE.equals(detail.getInterface_version())){
                Map<String, Object> params = new HashMap<String, Object>();
                params.put("host", sysParameterService.querySysParameter(baseRequest.getMall_no(), URLConfigUtil.EPAY_SERVER_KEY));
                params.put("url", sysParameterService.querySysParameter(baseRequest.getMall_no(), URLConfigUtil.ICIS_CUSTOMERUNBIND));
                params.put("partner_id", platPaycode.getPayment_plat_no());
                params.put("partner_serial_no", transTransReq.getTrans_serial());
                params.put("partner_trans_date", baseRequest.getPartner_trans_date());
                params.put("partner_trans_time", baseRequest.getPartner_trans_time());
                params.put("original_serial_no", eaccCardInfos.get(0).getBindId());
                params.put("mall_no", baseRequest.getMall_no());
                params.put("mer_no", baseRequest.getMer_no());
                params.put("plat_cust", eaccCardInfos.get(0).getMallcust());
                params.put("card_no", eaccCardInfos.get(0).getCard_no());
                //=======ccb参数=======
                params.put("channelId", platPaycode.getChannel_id());
                params.put("cus_type", oldEaccUserInfo.getCus_type());
                //=========================
                logger.info("【解绑】======开始解绑");
                logger.info("【解绑】请求三方参数：" + JSON.toJSON(params));
                Map<String, Object> resultMap = adapterService.isicCardUnbing(params);
                logger.info("【解绑】三方参数响应参数：" + JSON.toJSON(resultMap));
                if (null == resultMap || null == resultMap.get("order_status") || "".equals(resultMap.get("order_status"))) {
                    throw new BusinessException(BusinessMsg.CALL_REMOTE_ERROR, BusinessMsg.getMsg(BusinessMsg.CALL_REMOTE_ERROR));
                }
                if (!OrderStatus.SUCCESS.getCode().equals(resultMap.get("order_status"))) {
                    transTransReq.setStatus(FlowStatus.FAIL.getCode());
                    transTransReq.setReturn_code(BusinessMsg.FAIL);
                    transTransReq.setReturn_msg((String) resultMap.get("remsg"));
                    transReqService.insert(transTransReq);
                    throw new BusinessException((String) resultMap.get("recode"), (String) resultMap.get("remsg"));
                }
            }
            //批量删除对应卡信息

            userAccountExtService.unBindCard(eaccCardInfos);

            //解绑成功后检查默认卡是否已解绑
            logger.info("【解绑】oldEaccUserInfo:{}|old_card_no:{}",JSON.toJSONString(oldEaccUserInfo),detail.getCard_no_old());
            if(oldEaccUserInfo!=null && detail.getCard_no_old().equals(oldEaccUserInfo.getDefault_card_no())){
                logger.info("【解绑】解绑卡号和默认卡号一致");
                List<EaccCardinfo> eaccCardinfoList=accountQueryService.getEaccCardInfoList(baseRequest.getMall_no(),detail.getPlatcust());
                for(EaccCardinfo nowEaccCardinfo:eaccCardinfoList){
                    if(!(nowEaccCardinfo.getCard_no().equals(detail.getCard_no_old())
                            && platPaycode.getChannel_id().equals(nowEaccCardinfo.getPay_channel()))){
                        oldEaccUserInfo.setDefault_card_no(nowEaccCardinfo.getCard_no());
                        oldEaccUserInfo.setDefault_mobile(nowEaccCardinfo.getMobile());
                        break;
                    }
                }
                if(detail.getCard_no_old().equals(oldEaccUserInfo.getDefault_card_no())){
                    oldEaccUserInfo.setDefault_mobile(null);
                    oldEaccUserInfo.setDefault_card_no(null);
                }
                oldEaccUserInfo.setIs_card_bind(IsUse.NO.getCode());
                logger.info("【解绑】新oldEaccUserInfo:{}",JSON.toJSONString(oldEaccUserInfo));
                userAccountService.updateEaccUserInfo(oldEaccUserInfo);
            }
            String bindKey=baseRequest.getMer_no()+detail.getCard_no_old()+platPaycode.getPay_code();
            CacheUtil.getCache().del(bindKey);
            logger.info("【解绑】======解绑完成\n\n\n");
            transTransReq.setReturn_code(BusinessMsg.SUCCESS);
            transTransReq.setReturn_msg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));
            transTransReq.setStatus(FlowStatus.SUCCESS.getCode());
            transReqService.insert(transTransReq);
        }catch (Exception e){
            logger.error(e);
            BaseResponse baseResponse=new BaseResponse();
            if(e instanceof BusinessException){
                baseResponse.setRecode(((BusinessException) e).getBaseResponse().getRecode());
                baseResponse.setRemsg(((BusinessException) e).getBaseResponse().getRemsg());
            }else{
                baseResponse.setRecode(BusinessMsg.DATEBASE_EXCEPTION);
                baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.DATEBASE_EXCEPTION));
            }
            transTransReq.setReturn_code(baseResponse.getRecode());
            transTransReq.setReturn_msg(baseResponse.getRemsg());
            transTransReq.setStatus(FlowStatus.FAIL.getCode());
            transReqService.insert(transTransReq);
            throw new BusinessException(baseResponse);
        }
    }

    @Transactional
    public void unbindCard(BaseRequest baseRequest, UnBindCardRequestDetail detail) throws BusinessException {
        //参数校验
        logger.info("【解绑】======开始解绑参数验证");
        validate(detail);
        logger.info("【解绑】======解绑参数验证通过");

        //记录流水
        TransTransreq transTransReq=transReqService.getTransTransReq(baseRequest.clone(), TransConsts.BATCH_CARD_UNBIND_CODE, TransConsts.BATCH_CARD_UNBIND_NAME,true);
        transTransReq.setOrder_no(detail.getDetail_no());
        transTransReq.setPlatcust(detail.getPlatcust());
        transReqService.insert(transTransReq);

        try{
            logger.info("解绑参数："+JSON.toJSON(detail));

            EaccUserinfo oldEaccUserInfo=accountQueryService.getEUserinfoByExist(baseRequest.getMall_no(),detail.getPlatcust());
            if(oldEaccUserInfo==null){
                logger.info("【解绑】用户不存在|platcust:{}",detail.getPlatcust());
                throw new BusinessException(BusinessMsg.INVALID_ACCOUNT,BusinessMsg.getMsg(BusinessMsg.INVALID_ACCOUNT));
            }
            PlatPaycode platPaycode=userAccountService.queryPlatPaycode(baseRequest.getMer_no(),detail.getPay_code());
            if(platPaycode==null){
                logger.info("【解绑】paycode不存在|pay_code:{}",detail.getPay_code());
                throw new BusinessException(BusinessMsg.NO_CHANNEL_INFORMATION,BusinessMsg.getMsg(BusinessMsg.NO_CHANNEL_INFORMATION));
            }
            //获取绑卡信息
            logger.info("【解绑】======开始获取绑卡信息");
            List<EaccCardinfo> eaccCardInfoList=new ArrayList<>();
            if("099".equals(detail.getPay_code())){
                eaccCardInfoList=userAccountService.queryEaccCardInfolist(baseRequest.getMall_no(),
                        detail.getPlatcust(),detail.getCard_no_old());
            }else{
                EaccCardinfo eaccCardInfo=userAccountService.queryEaccCardInfo(baseRequest.getMall_no(),
                        detail.getPlatcust(),detail.getCard_no_old(),platPaycode.getChannel_id());
                if(eaccCardInfo!=null){
                    eaccCardInfoList.add(eaccCardInfo);
                }
            }
            if(eaccCardInfoList.size()<=0){
                BaseResponse baseResponse=new BaseResponse();
                baseResponse.setRecode(BusinessMsg.UNBIND_CARD_ERROR);
                baseResponse.setRemsg("解绑卡号错误！");
                throw new BusinessException(baseResponse);
            }else if(eaccCardInfoList.size()==1){
                if(eaccCardInfoList.get(0).getStatus().equals("0")){
                    BaseResponse baseResponse=new BaseResponse();
                    baseResponse.setRecode(BusinessMsg.UNBIND_CARD_ERROR);
                    baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.UNBIND_CARD_ERROR)+":绑卡审核中");
                    throw new BusinessException(baseResponse);
                }
            }
            EaccCardinfo eaccCardInfo=eaccCardInfoList.get(0);
            //获取系统参数，判断是否走解绑接口
            if(!Constants.OLD_INTERFACE.equals(detail.getInterface_version())){
                //获取paycode
                logger.info("【解绑】======开始获取paycode");
                Map<String,Object> params=new HashMap<String,Object>();
                params.put("host",sysParameterService.querySysParameter(baseRequest.getMall_no(), URLConfigUtil.EPAY_SERVER_KEY));
                params.put("url",sysParameterService.querySysParameter(baseRequest.getMall_no(), URLConfigUtil.ICIS_CUSTOMERUNBIND));
                params.put("partner_id",platPaycode.getPayment_plat_no());
                params.put("partner_serial_no",transTransReq.getTrans_serial());
                params.put("partner_trans_date",baseRequest.getPartner_trans_date());
                params.put("partner_trans_time",baseRequest.getPartner_trans_time());
                params.put("original_serial_no",eaccCardInfo.getBindId());
                params.put("mall_no",baseRequest.getMall_no());
                params.put("mer_no",baseRequest.getMer_no());
                params.put("plat_cust",eaccCardInfo.getMallcust());
                params.put("card_no",eaccCardInfo.getCard_no());
                //=======ccb参数=======
                params.put("channelId",platPaycode.getChannel_id());
                params.put("cus_type",oldEaccUserInfo.getCus_type());
                //=========================
                logger.info("【解绑】======开始解绑");
                logger.info("【解绑】请求三方参数："+JSON.toJSON(params));
                Map<String,Object> resultMap= adapterService.isicCardUnbing(params);
                logger.info("【解绑】三方参数响应参数："+JSON.toJSON(resultMap));
                if(null==resultMap ||null==resultMap.get("order_status") ||"".equals(resultMap.get("order_status"))){
                    throw new BusinessException(BusinessMsg.CALL_REMOTE_ERROR,BusinessMsg.getMsg(BusinessMsg.CALL_REMOTE_ERROR));
                }
                if(!OrderStatus.SUCCESS.getCode().equals(resultMap.get("order_status"))){
                    transTransReq.setStatus(FlowStatus.FAIL.getCode());
                    transTransReq.setReturn_code(BusinessMsg.FAIL);
                    transTransReq.setReturn_msg((String) resultMap.get("remsg"));
                    transReqService.insert(transTransReq);
                    throw new BusinessException((String) resultMap.get("recode"),(String) resultMap.get("remsg"));
                }
            }
            //更新绑卡数据删除标志
            logger.info("【解绑】======开始更新绑卡数据删除标志");
            for(EaccCardinfo cardinfo:eaccCardInfoList){
                cardinfo.setEnabled(Constants.DISABLED);
                userAccountService.updateCardInfo(cardinfo);
            }
            logger.info("【解绑】======更新用户绑卡标识");
            //解绑成功后检查默认卡是否已解绑
            logger.info("【解绑】oldEaccUserInfo:{}|old_card_no:{}",JSON.toJSONString(oldEaccUserInfo),detail.getCard_no_old());
            if(detail.getCard_no_old().equals(oldEaccUserInfo.getDefault_card_no())){
                logger.info("【解绑】解绑卡号和默认卡号一致");
                List<EaccCardinfo> eaccCardinfoList=accountQueryService.getEaccCardInfoList(baseRequest.getMall_no(),detail.getPlatcust());
                for(EaccCardinfo nowEaccCardinfo:eaccCardinfoList){
                    if(!(nowEaccCardinfo.getCard_no().equals(detail.getCard_no_old())
                            && platPaycode.getChannel_id().equals(nowEaccCardinfo.getPay_channel()))){
                        oldEaccUserInfo.setDefault_card_no(nowEaccCardinfo.getCard_no());
                        oldEaccUserInfo.setDefault_mobile(nowEaccCardinfo.getMobile());
                        break;
                    }
                }
                if(detail.getCard_no_old().equals(oldEaccUserInfo.getDefault_card_no())){
                    oldEaccUserInfo.setDefault_mobile(null);
                    oldEaccUserInfo.setDefault_card_no(null);
                }
                oldEaccUserInfo.setIs_card_bind(IsUse.NO.getCode());
                logger.info("【解绑】新oldEaccUserInfo:{}",JSON.toJSONString(oldEaccUserInfo));
                userAccountService.updateEaccUserInfo(oldEaccUserInfo);
            }
            String bindKey=baseRequest.getMer_no()+detail.getCard_no_old()+platPaycode.getPay_code();
            CacheUtil.getCache().del(bindKey);
            logger.info("【解绑】======解绑完成\n\n\n");
            transTransReq.setReturn_code(BusinessMsg.SUCCESS);
            transTransReq.setReturn_msg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));
            transTransReq.setStatus(FlowStatus.SUCCESS.getCode());
            transReqService.insert(transTransReq);
        }catch (Exception e){
            logger.error(e);
            BaseResponse baseResponse=new BaseResponse();
            if(e instanceof BusinessException){
                baseResponse.setRecode(((BusinessException) e).getBaseResponse().getRecode());
                baseResponse.setRemsg(((BusinessException) e).getBaseResponse().getRemsg());
            }else{
                baseResponse.setRecode(BusinessMsg.DATEBASE_EXCEPTION);
                baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.DATEBASE_EXCEPTION));
            }
            transTransReq.setReturn_code(baseResponse.getRecode());
            transTransReq.setReturn_msg(baseResponse.getRemsg());
            transTransReq.setStatus(FlowStatus.FAIL.getCode());
            transReqService.insert(transTransReq);
            throw new BusinessException(baseResponse);
        }
    }

    @Override
    @Transactional
    public boolean personBindCard(PersonBindCardRequest personBindCardRequest) throws BusinessException {
        //记录流水
        TransTransreq transTransreq=transReqService.getTransTransReq(personBindCardRequest.clone(),TransConsts.CREDIT_CARD_BIND_CODE,
                TransConsts.CREDIT_CARD_BIND_NAME,false);
        transTransreq.setPlatcust(personBindCardRequest.getPlatcust());
        transReqService.insert(transTransreq);
        BaseResponse baseResponse=new BaseResponse();
        EaccUserinfo eaccUserinfo = null;
        try{
            //检查卡类型
            logger.info("【信用卡绑卡】=========检查卡类型");
            if(CardType.DEBIT.getCode().equals(personBindCardRequest.getCard_type())){
                logger.info("【信用卡绑卡】=========不支持借计卡绑卡");
                throw new BusinessException(BusinessMsg.TIEDCARD_FAILED,BusinessMsg.getMsg(
                        BusinessMsg.TIEDCARD_FAILED)+"：不支持借计卡绑卡");
            }
            //使用id_code和name作为检查用户是否存在的唯一标识
            logger.info("【信用卡绑卡】=========检查该用户是否存在");
            if(personBindCardRequest.getName()!=null && !"".equals(personBindCardRequest.getName()) && personBindCardRequest.getId_code()!=null && !"".equals(personBindCardRequest.getId_code())){
                eaccUserinfo=userAccountService.checkUserInfo(personBindCardRequest.getMall_no(),personBindCardRequest.getName(),
                                personBindCardRequest.getId_code(),personBindCardRequest.getMer_no(),true);
                if(eaccUserinfo==null){
                    baseResponse.setRecode(BusinessMsg.PARAMETER_ERROR);
                    baseResponse.setRemsg("传入参数有误，根据name和id_code查询不到该用户信息");
                    throw new BusinessException(baseResponse);
                }else if(eaccUserinfo.getMallcust()==null || "".equals(eaccUserinfo.getMallcust())){
                    baseResponse.setRecode(BusinessMsg.PARAMETER_ERROR);
                    baseResponse.setRemsg("根据name和id_code查询到的用户信息的平台客户号与传入的platcust不一致");
                    throw new BusinessException(baseResponse);
                }
            }
            //检查该用户是否绑卡
            logger.info("【信用卡绑卡】=========检查该用户是否已绑卡");
            //限制绑定信用卡张数不大于300
            boolean isOverCardLimit = userAccountService.checkCreditCardLimit(personBindCardRequest.getMall_no(),
                    personBindCardRequest.getPlatcust());
            if (isOverCardLimit){
                logger.info("【信用卡绑卡】=========个人信用卡绑卡数量超过限制。");
                throw new BusinessException(BusinessMsg.CARD_NUMBER_OUT_LIMIT,BusinessMsg.getMsg(
                        BusinessMsg.CARD_NUMBER_OUT_LIMIT)+"：客户【"+personBindCardRequest.getPlatcust()+"】个人信用卡绑卡数量超过限制");
            }
            EaccCardinfo oldEaccCardinfo=userAccountService.queryEaccCardInfo(personBindCardRequest.getMall_no(),
                    personBindCardRequest.getPlatcust(),personBindCardRequest.getCard_no(),null);
            if(oldEaccCardinfo!=null){
                String bind_credit_card_no=personBindCardRequest.getCard_no();
                String credit_card_no=oldEaccCardinfo.getCard_no();
                logger.info("【信用卡绑卡】=========用户已绑卡，卡号为："+credit_card_no);
                if(bind_credit_card_no.equals(credit_card_no)){
                    logger.info("【信用卡绑卡】=========绑卡信息与请求绑卡信息一致继续渠道绑卡。");
                    throw new BusinessException(BusinessMsg.REPEAT_BINDING, BusinessMsg.getMsg(
                                BusinessMsg.REPEAT_BINDING)+"：绑卡信息与请求绑卡信息一致，无需重复绑卡");
                }else {
                    throw new BusinessException(BusinessMsg.REPEAT_BINDING, BusinessMsg.getMsg(
                            BusinessMsg.REPEAT_BINDING)+"：绑卡信息与请求绑卡信息不一致，请检查传入卡号是否正确");
                }
            }
            logger.info("【信用卡绑卡】=========查询paycode："+personBindCardRequest.getPay_code());
            PlatPaycode platPaycode=userAccountService.queryPlatPaycode(
                    personBindCardRequest.getMer_no(),personBindCardRequest.getPay_code());
            if(platPaycode==null){
                logger.info("【信用卡绑卡】=========找不到渠道信息");
                throw new BusinessException(BusinessMsg.NO_CHANNEL_INFORMATION,
                        BusinessMsg.getMsg(BusinessMsg.NO_CHANNEL_INFORMATION));
            }
            EaccCardinfo eaccCardinfo=new EaccCardinfo();
            //信用卡用户
            if(CardType.CREDIT.getCode().equals(personBindCardRequest.getCard_type())){
                Map<String,Object> personBindCardResponse=null;
                logger.info("【信用卡绑卡】=========走支付公司通道");
                //绑卡
                logger.info("【信用卡绑卡】=========用户未绑卡，开始绑卡");
                Map<String,Object> params=new HashMap<String,Object>();
                params.put("partner_id",platPaycode.getPayment_plat_no());
                params.put("partner_serial_no",transTransreq.getTrans_serial());
                params.put("partner_trans_date",personBindCardRequest.getPartner_trans_date());
                params.put("partner_trans_time",personBindCardRequest.getPartner_trans_time());
                params.put("client_name",personBindCardRequest.getName());
                params.put("id_kind","0");
                params.put("id_no",personBindCardRequest.getId_code());
                params.put("card_no",personBindCardRequest.getCard_no());
                params.put("sendercomp_id",platPaycode.getPayment_plat_no());
                params.put("targetcomp_id","91000");
                params.put("channelId",platPaycode.getChannel_id());
                params.put("pay_bankacct_type","0");
                //=====ccb参数=====
                params.put("partner_terminal_id","");
                params.put("host",sysParameterService.querySysParameter(personBindCardRequest.getMall_no(), URLConfigUtil.EPAY_SERVER_KEY));
                params.put("url",sysParameterService.querySysParameter(personBindCardRequest.getMall_no(), URLConfigUtil.BAND_ACCOUNT_VERIFY3_KEY));
                logger.info("【信用卡绑卡】=========开始调用E支付绑卡，参数："+params);
                personBindCardResponse=adapterService.threeElementsBindCard(params);
                logger.info("【信用卡绑卡】=========E支付返回"+personBindCardResponse.toString());
                if(OrderStatus.FAIL.getCode().equals(personBindCardResponse.get("order_status"))){
                    logger.info("【信用卡绑卡】=========绑卡失败："+personBindCardResponse.get("remsg"));
                    throw new BusinessException(BusinessMsg.TIEDCARD_FAILED,BusinessMsg.getMsg(BusinessMsg.TIEDCARD_FAILED)+"："+personBindCardResponse.get("remsg"));
                }
                eaccCardinfo.setStatus(CardStatus.ACTIVE.getCode());
                eaccCardinfo.setRemark(BindCardType.BINDCREDITCARD.getCode());
                eaccCardinfo.setCard_no(personBindCardRequest.getCard_no());
                eaccCardinfo.setMall_no(personBindCardRequest.getMall_no());
                eaccCardinfo.setMallcust(personBindCardRequest.getPlatcust());
                eaccCardinfo.setCard_type(personBindCardRequest.getCard_type());
                eaccCardinfo.setAcct_name(personBindCardRequest.getName());
                eaccCardinfo.setPay_channel(platPaycode.getChannel_id());
                eaccCardinfo.setBindId(transTransreq.getTrans_serial());
            }
            logger.info("【信用卡绑卡】=========添加绑卡数据");
            userAccountService.addBindCardInfo(eaccCardinfo);
            //logger.info("【信用卡绑卡】=========删除解绑回滚标识");
            //CacheUtil.getCache().del("unlockCardUser"+personBindCardRequest.getPlatcust());
            logger.info("【信用卡绑卡】=========绑卡完成\n\n\n");
        }catch (Exception e){
            if(e instanceof BusinessException){
                baseResponse=((BusinessException)e).getBaseResponse();
                logger.info("【信用卡绑卡】=========绑卡失败"+baseResponse.getRemsg());
            }else{
                logger.error("【信用卡绑卡】=========未知异常："+e);
                baseResponse.setRecode(BusinessMsg.DATEBASE_EXCEPTION);
                baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.DATEBASE_EXCEPTION));
            }
            transTransreq.setReturn_code(baseResponse.getRecode());
            transTransreq.setReturn_msg(baseResponse.getRemsg());
            transTransreq.setStatus(FlowStatus.FAIL.getCode());
            transReqService.insert(transTransreq);
            throw new BusinessException(baseResponse);
        }
        try{
            if(!"".equals(personBindCardRequest.getPlatcust()) && personBindCardRequest.getPlatcust()!=null){
                logger.info("信用卡绑卡完成，发送通知短信");
                MsgModel msgModel=new MsgModel();
                String mall_name=sysParameterService.querySysParameter(personBindCardRequest.getMall_no(),
                        MsgContent.MALL_NAME_KEY.getMsg());
                msgModel.setOrder_no(baseResponse.getOrder_no());
                msgModel.setPlat_no(transTransreq.getPlat_no());
                msgModel.setTrans_code(transTransreq.getTrans_code());
//                msgModel.setMobile(eaccUserinfo.getMobile());
                msgModel.setMall_no(eaccUserinfo.getMall_no());
                msgModel.setPlatcust(eaccUserinfo.getMallcust());
                String content=sysParameterService.querySysParameter(personBindCardRequest.getMall_no(),MsgContent.BIND_CARD_CREDIT_NOTIFY.getMsg());
                if(StringUtils.isNotBlank(content)){
                    msgModel.setMsgContent(String.format(content,mall_name));
                    sendMsgService.addMsgToQueue(msgModel);
                }
            }
        }catch (Exception e){
            logger.info("【短信发送失败】======="+e);
        }
        transTransreq.setReturn_code(BusinessMsg.SUCCESS);
        transTransreq.setReturn_msg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));
        transTransreq.setStatus(FlowStatus.SUCCESS.getCode());
        transReqService.insert(transTransreq);
        return true;
    }

    @Override
    public UnBindCardReturn batchUnbindCardForMultiCards(BaseRequest baseRequest,List<UnBindCardForMultiCardsRequestDetail> details) throws BusinessException{

        //记录批量流水
        TransTransreq transTransReq=transReqService.getTransTransReq(baseRequest.clone(),TransConsts.BATCH_CARD_UNBIND_CODE,TransConsts.BATCH_CARD_UNBIND_NAME,true);
        transReqService.insert(transTransReq);

        UnBindCardReturn unBindCardReturn=new UnBindCardReturn();
        List<UnBindCardSuccess> successDataList=new ArrayList<UnBindCardSuccess>();
        List<BaseErrorData> errorDataList=new ArrayList<BaseErrorData>();
        for(UnBindCardForMultiCardsRequestDetail detail:details){
            try {
                unbindCardForMultiCards(baseRequest,detail);
                UnBindCardSuccess success=new UnBindCardSuccess();
                success.setDetail_no(detail.getDetail_no());
                success.setMobile(detail.getMobile());
                success.setPlatcust(detail.getPlatcust());
                successDataList.add(success);
            }catch (BusinessException e){
                BaseErrorData error=new BaseErrorData();
                error.setDetail_no(detail.getDetail_no());
                error.setError_no(e.getBaseResponse().getRecode());
                error.setError_info(e.getBaseResponse().getRemsg());
                errorDataList.add(error);
            }
            logger.info("【批量解绑】=========检查用户：【"+detail.getPlatcust()+"："+detail.getCard_no_old()+"】是否还有绑卡");
            EaccCardinfo exisEaccCardinfo=userAccountService.queryEaccCardInfo(baseRequest.getMall_no(),
                    detail.getPlatcust(),null,null);
            if (exisEaccCardinfo==null) {
                logger.info("【批量解绑】=========用户：【"+detail.getPlatcust()+"："+detail.getCard_no_old()+"】没有绑卡信息，更新用户绑卡标识");
                EaccUserinfo eaccUserinfo = new EaccUserinfo();
                eaccUserinfo.setIs_card_bind(IsUse.NO.getCode());
                userAccountService.updateEaccUserInfo(eaccUserinfo, baseRequest.getMall_no(), detail.getPlatcust());
            }
        }
        unBindCardReturn.setSuccessDataList(successDataList);
        unBindCardReturn.setErrorDataList(errorDataList);
        transTransReq.setReturn_code(BusinessMsg.SUCCESS);
        transTransReq.setReturn_msg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));
        transTransReq.setStatus(FlowStatus.SUCCESS.getCode());
        transReqService.insert(transTransReq);
        return unBindCardReturn;
    }

    @Override
    public BatchChangeCardReturn batchChangeCard(BaseRequest baseRequest,List<BatchChangeCardRequestDetail> batchChangeCardRequestList) throws BusinessException{

        //记录批量换卡（先绑卡再解绑）流水
        TransTransreq transTransreq = transReqService.getTransTransReq(baseRequest.clone(),TransConsts.BATCH_CHANGE_CARD_CODE,
                TransConsts.BATCH_CHANGE_CARD_NAME,true);
        transReqService.insert(transTransreq);
        //查询客户信息,检查该用户是否已经注册
        BatchChangeCardReturn batchChangeCardReturn = new BatchChangeCardReturn();
        List<BatchChangeCardSuccessData> successDataList = new ArrayList<BatchChangeCardSuccessData>();
        List<BatchChangeCardErrorData> errorDataList = new ArrayList<BatchChangeCardErrorData>();
        for(BatchChangeCardRequestDetail changeCardRequestDetail:batchChangeCardRequestList ){
            try{
                changeCard(baseRequest,changeCardRequestDetail);
                BatchChangeCardSuccessData successData = new BatchChangeCardSuccessData();
                successData.setDetail_no(changeCardRequestDetail.getDetail_no());
                successData.setMobile(changeCardRequestDetail.getMobile());
                successData.setPlatcust(changeCardRequestDetail.getPlatcust());
                successDataList.add(successData);
            }catch (BusinessException e){
                BatchChangeCardErrorData errorData = new BatchChangeCardErrorData();
                errorData.setDetail_no(changeCardRequestDetail.getDetail_no());
                errorData.setError_no(e.getBaseResponse().getRecode());
                errorData.setError_info(e.getBaseResponse().getRemsg());
                errorDataList.add(errorData);
            }
        }
        batchChangeCardReturn.setSuccessDataList(successDataList);
        batchChangeCardReturn.setErrorDataList(errorDataList);
        //更新批量流水
        transTransreq.setReturn_code(BusinessMsg.SUCCESS);
        transTransreq.setReturn_msg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));
        transTransreq.setStatus(FlowStatus.SUCCESS.getCode());
        transReqService.insert(transTransreq);
        return batchChangeCardReturn;
    }

    @Override
    @Transactional
    public BaseResponse changeCardByMsg(ChangeCardByMsgConfirm changeCardByMsgConfirm) throws BusinessException {
        logger.info("【短验换卡确认】======开始");
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setRecode(BusinessMsg.FAIL);
        baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.FAIL));

        TransTransreq transTransreq = transReqService.getTransTransReq(changeCardByMsgConfirm.clone(), TransConsts.MESSAGE_CARD_CHANGECONFIRM_CODE, TransConsts.MESSAGE_CARD_CHANGECONFIRM_NAME, false);
        transTransreq.setPlatcust(changeCardByMsgConfirm.getPlatcust());
        transReqService.insert(transTransreq);
        TransChangeCard transChangeCard=new TransChangeCard();
        EaccUserinfo eaccUserinfo = userAccountService.queryEaccUserInfoByMallNoAndPlatcust(changeCardByMsgConfirm.getMall_no(), changeCardByMsgConfirm.getPlatcust());
        EaccCardinfo newCardinfo = userAccountService.queryEaccCardInfo(changeCardByMsgConfirm.getMall_no(),
                changeCardByMsgConfirm.getPlatcust(), changeCardByMsgConfirm.getCard_no(), null);
        EaccCardinfo oldCardinfo = userAccountService.queryEaccCardInfo(changeCardByMsgConfirm.getMall_no(),
                changeCardByMsgConfirm.getPlatcust(), changeCardByMsgConfirm.getCard_no_old(), null);
        String paycode = changeCardByMsgConfirm.getPay_code();
        JSONObject jsonObject=new JSONObject();
        String cache_detail="";
        try {
            cache_detail = CacheUtil.getCache().get(changeCardByMsgConfirm.getOrigin_order_no() + changeCardByMsgConfirm.getPlatcust() + TransConsts.MESSAGE_CARD_CHANGEREQUEST_CODE).toString();
            if ("".equals(cache_detail)) {
                baseResponse.setRecode(BusinessMsg.NVALID_PLAT_NO);
                baseResponse.setRemsg("根据原换卡申请订单号查询不到原换卡申请流水，请确认！");
                throw new BusinessException(baseResponse);
            }
        }catch(Exception ex){
            baseResponse.setRecode(BusinessMsg.NVALID_PLAT_NO);
            baseResponse.setRemsg("获取短验换卡申请流水异常或原申请已换卡成功");
            throw new BusinessException(baseResponse);
        }
        try {
            jsonObject=JSON.parseObject(cache_detail);
            if(!jsonObject.get("card_no").equals(changeCardByMsgConfirm.getCard_no())||!jsonObject.get("card_no_old").equals(changeCardByMsgConfirm.getCard_no_old())||
                    !jsonObject.get("mobile_old").equals(changeCardByMsgConfirm.getMobile_old())||!jsonObject.get("pre_mobile").equals(changeCardByMsgConfirm.getMobile())
                    ||!jsonObject.get("pay_code").equals(changeCardByMsgConfirm.getPay_code())){
                baseResponse.setRecode(BusinessMsg.PARAMETER_ERROR);
                baseResponse.setRemsg("换卡确认请求参数与请求参数不一致！");
                throw new BusinessException(baseResponse);
            }

            TransTransreq ttReq = transReqService.queryTransTransReqByOrdernoAndPlatcust(changeCardByMsgConfirm.getOrigin_order_no(),changeCardByMsgConfirm.getPlatcust(),TransConsts.MESSAGE_CARD_CHANGEREQUEST_CODE);
            if (ttReq == null || StringUtils.isEmpty(ttReq.getTrans_serial())) {
                baseResponse.setRecode(BusinessMsg.NVALID_PLAT_NO);
                baseResponse.setRemsg("根据原换卡申请订单号查询不到原换卡申请流水，请确认！");
                throw new BusinessException(baseResponse);
            }


            logger.info("【短验换卡确认】=========检查该用户是否存在");
            if (eaccUserinfo == null) {
                logger.info("【短验换卡确认】=========用户不存在，platcust：" + changeCardByMsgConfirm.getPlatcust());
                throw new BusinessException(BusinessMsg.INVALID_ACCOUNT, BusinessMsg.getMsg(BusinessMsg.INVALID_ACCOUNT) + "：该用户未注册");
            }

            if(!eaccUserinfo.getName().equals(changeCardByMsgConfirm.getName())){
                logger.info("【短验换卡确认】=========用户信息有误，platcust：" + changeCardByMsgConfirm.getPlatcust());
                throw new BusinessException(BusinessMsg.INVALID_ACCOUNT, BusinessMsg.getMsg(BusinessMsg.INVALID_ACCOUNT) + "：该客户号与姓名不匹配");
            }

            if (oldCardinfo == null) {
                baseResponse.setRecode(BusinessMsg.PARAMETER_ERROR);
                baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.PARAMETER_ERROR) + "：未找到客户号【"+changeCardByMsgConfirm.getPlatcust()+"】对于老卡卡号【" + changeCardByMsgConfirm.getCard_no_old() + "】的绑卡信息");
                throw new BusinessException(baseResponse);
            }

            if (!oldCardinfo.getMobile().equals(changeCardByMsgConfirm.getMobile_old())) {
                baseResponse.setRecode(BusinessMsg.PARAMETER_ERROR);
                baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.PARAMETER_ERROR) + "：输入原卡卡号与原预留手机号和实际绑卡信息不一致");
                throw new BusinessException(baseResponse);
            }

            logger.info("【短验换卡确认】=========查询paycode：" + paycode);
            PlatPaycode platPaycode = userAccountService.queryPlatPaycode(changeCardByMsgConfirm.getMer_no(), paycode);
            if (platPaycode == null) {
                logger.info("【短验换卡确认】=========找不到渠道信息");
                throw new BusinessException(BusinessMsg.NO_CHANNEL_INFORMATION,
                        BusinessMsg.getMsg(BusinessMsg.NO_CHANNEL_INFORMATION));
            }

            //检查卡类型
            logger.info("【短验换卡确认】=========检查卡类型");
            if (CardType.CREDIT.getCode().equals(oldCardinfo.getCard_type())) {
                logger.info("【短验换卡确认】=========暂不支持信用卡换绑卡");
                throw new BusinessException(BusinessMsg.TIEDCARD_FAILED, BusinessMsg.getMsg(
                        BusinessMsg.TIEDCARD_FAILED) + "：暂不支持信用卡换绑卡");
            }
            if (newCardinfo != null) {
                if (CardType.CREDIT.getCode().equals(newCardinfo.getCard_type())) {
                    logger.info("【短验换卡确认】=========暂不支持信用卡换绑卡");
                    throw new BusinessException(BusinessMsg.TIEDCARD_FAILED, BusinessMsg.getMsg(
                            BusinessMsg.TIEDCARD_FAILED) + "：暂不支持信用卡换绑卡");
                }
                if (!newCardinfo.getCard_no().equals(changeCardByMsgConfirm.getCard_no_old())) {
                    baseResponse.setRecode(BusinessMsg.TIEDCARD);
                    baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.TIEDCARD) + "：卡号【" + changeCardByMsgConfirm.getCard_no() + "】");
                    throw new BusinessException(baseResponse);
                } else {
                    if (newCardinfo.getMobile().equals(changeCardByMsgConfirm.getMobile())) {
                        baseResponse.setRecode(BusinessMsg.METHODPARAMETER_ERROR);
                        baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.METHODPARAMETER_ERROR) + "：换卡信息与原卡信息一致，无需换卡【" + changeCardByMsgConfirm.getCard_no() + "】");
                        throw new BusinessException(baseResponse);
                    }
                }
            }

            //检查用户是否对公用户
            if (CusType.COMPANY.getCode().equals(eaccUserinfo.getCus_type())) {
                logger.info("【短验换卡确认】=========对公用户不允许换绑卡");
                throw new BusinessException(BusinessMsg.DATEBASE_EXCEPTION, BusinessMsg.getMsg(BusinessMsg.DATEBASE_EXCEPTION) + "：对公用户不允许换绑卡");
            }

            logger.info("==================" + "开始组装确认绑卡请求参数");

            Map<String, Object> params = new HashMap<String, Object>();
            params.put("partner_id", platPaycode.getPayment_plat_no());
            params.put("partner_serial_no", ttReq.getTrans_serial());
            params.put("partner_trans_date", changeCardByMsgConfirm.getPartner_trans_date());
            params.put("partner_trans_time", changeCardByMsgConfirm.getPartner_trans_time());
            params.put("client_name", changeCardByMsgConfirm.getName());
            params.put("id_kind", "0");
            params.put("id_no", eaccUserinfo.getId_code());
            params.put("mobile_tel", changeCardByMsgConfirm.getMobile());
            params.put("card_no", changeCardByMsgConfirm.getCard_no());
            params.put("func_code", "2");
            params.put("verify_info", changeCardByMsgConfirm.getIdentifying_code());
            params.put("sendercomp_id", platPaycode.getPayment_plat_no());
            params.put("targetcomp_id", "91000");
            params.put("pay_bankacct_type", "0");
            //==========建行差异字段==========
            params.put("partner_terminal_id", "宝付必填");
            params.put("channelId", platPaycode.getChannel_id());
            //雅酷必填字段
            params.put("partner_userid",changeCardByMsgConfirm.getRemark());
            params.put("host",sysParameterService.querySysParameter(changeCardByMsgConfirm.getMall_no(), URLConfigUtil.EPAY_SERVER_KEY));
            params.put("url",sysParameterService.querySysParameter(changeCardByMsgConfirm.getMall_no(), URLConfigUtil.BAND_ACCOUNT_VERIFY_KEY_OLD));
//        params.put("bank_id","非必填");
            //=======================================
            //短信确认绑卡
            logger.info("==================" + "开始发送确认绑卡请求" + JSON.toJSON(params));
            Map<String,Object> msgBindCardResponse= adapterService.confirm4ElementsBindCard(params);
           /* Map<String, Object> msgBindCardResponse = new HashMap<String, Object>();
            msgBindCardResponse.put("success", true);*/
            logger.info("返回msgBindCardResponse：" + JSON.toJSON(msgBindCardResponse));
            if (!OrderStatus.SUCCESS.getCode().equals(msgBindCardResponse.get("order_status"))) {
//                if ("001003".equals(msgBindCardResponse.get("recode"))) {
//                    baseResponse.setRecode(BusinessMsg.VERIFICATION_ERROR);
//                    baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.VERIFICATION_ERROR));
//                    throw new BusinessException(baseResponse);
//                } else if ("001002".equals(msgBindCardResponse.get("recode"))) {
//                    baseResponse.setRecode(BusinessMsg.VERIFICATION_OUT_OF_DATE);
//                    baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.VERIFICATION_OUT_OF_DATE));
//                    throw new BusinessException(baseResponse);
//                } else if ("020513".equals(msgBindCardResponse.get("recode"))) {
//                    baseResponse.setRecode(BusinessMsg.VERIFICATION_ERROR_TOO_MUCH);
//                    baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.VERIFICATION_ERROR_TOO_MUCH));
//                    throw new BusinessException(baseResponse);
//                } else if (BusinessMsg.CFCA_AUTH_FAILED.equals(msgBindCardResponse.get("recode"))) {
//                    throw new BusinessException(BusinessMsg.CFCA_AUTH_FAILED, BusinessMsg.getMsg(BusinessMsg.CFCA_AUTH_FAILED));
//                } else {
//                    baseResponse.setRecode(BusinessMsg.FOUR_VERIFY_FAILED);
//                    baseResponse.setRemsg("msgBindCardResponse：短验确认接口返回数据异常：" + (String) msgBindCardResponse.get("remsg"));
//                    throw new BusinessException(baseResponse);
//                }

                baseResponse.setRecode(String.valueOf(msgBindCardResponse.get("recode")));
                baseResponse.setRemsg(String.valueOf(msgBindCardResponse.get("remsg")));
                throw new BusinessException(baseResponse);
            }

            //开始绑卡
            if (newCardinfo == null) {
                oldCardinfo.setMobile(changeCardByMsgConfirm.getMobile());
                oldCardinfo.setCard_no(changeCardByMsgConfirm.getCard_no());
                oldCardinfo.setBindId(ttReq.getTrans_serial());
                userAccountService.updateCardInfo(oldCardinfo);
            }else{
                newCardinfo.setMobile(changeCardByMsgConfirm.getMobile());
                newCardinfo.setCard_no(changeCardByMsgConfirm.getCard_no());
                newCardinfo.setBindId(ttReq.getTrans_serial());
                userAccountService.updateCardInfo(newCardinfo);
            }
            CacheUtil.getCache().del(changeCardByMsgConfirm.getOrigin_order_no()+changeCardByMsgConfirm.getPlatcust()+TransConsts.MESSAGE_CARD_CHANGEREQUEST_CODE);
        } catch (Exception e) {
            if (e instanceof BusinessException) {
                baseResponse = ((BusinessException) e).getBaseResponse();
                transTransreq.setReturn_code(baseResponse.getRecode());
                transTransreq.setReturn_msg(baseResponse.getRemsg());
                transTransreq.setStatus(FlowStatus.FAIL.getCode());
                transReqService.insert(transTransreq);
                throw new BusinessException(baseResponse);
            } else {
                logger.error("【短验绑卡确认】========异常：" + e);
                transTransreq.setReturn_code(BusinessMsg.DATEBASE_EXCEPTION);
                transTransreq.setReturn_msg(BusinessMsg.getMsg(BusinessMsg.DATEBASE_EXCEPTION));
                transTransreq.setStatus(FlowStatus.FAIL.getCode());
                transReqService.insert(transTransreq);
                throw new BusinessException(BusinessMsg.DATEBASE_EXCEPTION);
            }
        }

            try {
                if ("".equals(changeCardByMsgConfirm.getPlatcust()) || changeCardByMsgConfirm.getPlatcust() == null) {
                    logger.info("短验绑卡确认完成，发送通知短信");
                    MsgModel msgModel = new MsgModel();
                    String mall_name = sysParameterService.querySysParameter(changeCardByMsgConfirm.getMall_no(),
                            MsgContent.MALL_NAME_KEY.getMsg());
                    msgModel.setOrder_no(baseResponse.getOrder_no());
                    msgModel.setPlat_no(changeCardByMsgConfirm.getMer_no());
                    msgModel.setTrans_code(transTransreq.getTrans_code());
//                    msgModel.setMobile(eaccUserinfo.getMobile());
                    msgModel.setMall_no(eaccUserinfo.getMall_no());
                    msgModel.setPlatcust(eaccUserinfo.getMallcust());
                    String content=sysParameterService.querySysParameter(changeCardByMsgConfirm.getMall_no(),MsgContent.OPEN_ACCOUNT_NOTIFY.getMsg());
                    if(StringUtils.isNotBlank(content)){
                        msgModel.setMsgContent(String.format(content, mall_name));
                        sendMsgService.addMsgToQueue(msgModel);
                    }
                }
            } catch (Exception ex) {
                logger.info("【短信发送失败】=======" + ex);
            }

            //更新流水
            transTransreq.setReturn_code(BusinessMsg.SUCCESS);
            transTransreq.setReturn_msg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));
            transTransreq.setStatus(FlowStatus.SUCCESS.getCode());
            transReqService.insert(transTransreq);
            logger.info("更新短验换卡确认业务流水表成功");

            transChangeCard.setOrder_no(changeCardByMsgConfirm.getOrder_no());
            transChangeCard.setTrans_serial(transTransreq.getTrans_serial());
            transChangeCard.setPlat_no(changeCardByMsgConfirm.getMer_no());
            transChangeCard.setPlatcust(changeCardByMsgConfirm.getPlatcust());
            transChangeCard.setTrans_date(changeCardByMsgConfirm.getPartner_trans_date());
            transChangeCard.setTrans_time(changeCardByMsgConfirm.getPartner_trans_time());
            transChangeCard.setStatus(FlowStatus.SUCCESS.getCode());
            transChangeCard.setCard_no_old(changeCardByMsgConfirm.getCard_no_old());
            transChangeCard.setMobile_old(changeCardByMsgConfirm.getMobile_old());
            transChangeCard.setCard_no(changeCardByMsgConfirm.getCard_no());
            transChangeCard.setPre_mobile(changeCardByMsgConfirm.getMobile());
            transChangeCard.setCard_type(jsonObject.get("card_type")==null?"1":jsonObject.get("card_type").toString());
            transChangeCard.setPay_code(changeCardByMsgConfirm.getPay_code());
            transChangeCard.setName(eaccUserinfo.getName());
            transChangeCard.setEnabled(FlowStatus.SUCCESS.getCode());
            transReqService.insert(transChangeCard);

            baseResponse.setRecode(BusinessMsg.SUCCESS);
            baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));

            return baseResponse;
        }

    @Override
    public BaseResponse changeCardByMsgReq(ChangeCardByMsgRequest changeCardByMsgRequest) throws BusinessException {
        logger.info("【短验换卡申请】======开始");
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setRecode(BusinessMsg.FAIL);
        baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.FAIL));

        TransTransreq transTransreq = transReqService.getTransTransReq(changeCardByMsgRequest.clone(), TransConsts.MESSAGE_CARD_CHANGEREQUEST_CODE, TransConsts.MESSAGE_CARD_CHANGEREQUEST_NAME, false);
        transTransreq.setPlatcust(changeCardByMsgRequest.getPlatcust());
        transReqService.insert(transTransreq);

        EaccUserinfo eaccUserinfo = userAccountService.queryEaccUserInfoByMallNoAndPlatcust(changeCardByMsgRequest.getMall_no(), changeCardByMsgRequest.getPlatcust());
        EaccCardinfo newCardinfo = userAccountService.queryEaccCardInfo(changeCardByMsgRequest.getMall_no(),
                changeCardByMsgRequest.getPlatcust(), changeCardByMsgRequest.getCard_no(), null);
        EaccCardinfo oldCardinfo = userAccountService.queryEaccCardInfo(changeCardByMsgRequest.getMall_no(),
                changeCardByMsgRequest.getPlatcust(), changeCardByMsgRequest.getCard_no_old(), null);
        String paycode = changeCardByMsgRequest.getPay_code();
        try {
            logger.info("【短验换卡申请】=========检查该用户是否存在");
            if (eaccUserinfo == null) {
                logger.info("【短验换卡申请】=========用户不存在，platcust：" + changeCardByMsgRequest.getPlatcust());
                throw new BusinessException(BusinessMsg.INVALID_ACCOUNT, BusinessMsg.getMsg(BusinessMsg.INVALID_ACCOUNT) + "：该用户未注册");
            }

            if (!eaccUserinfo.getName().equals(changeCardByMsgRequest.getName())) {
                logger.info("【短验换卡申请】=========用户信息有误，platcust：" + changeCardByMsgRequest.getPlatcust());
                throw new BusinessException(BusinessMsg.INVALID_ACCOUNT, BusinessMsg.getMsg(BusinessMsg.INVALID_ACCOUNT) + "：该客户号与姓名不匹配");
            }

            if (oldCardinfo == null) {
                baseResponse.setRecode(BusinessMsg.PARAMETER_ERROR);
                baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.PARAMETER_ERROR) + "：未找到客户号【" + changeCardByMsgRequest.getPlatcust() + "】对于老卡卡号【" + changeCardByMsgRequest.getCard_no_old() + "】的绑卡信息");
                throw new BusinessException(baseResponse);
            }

            if (!oldCardinfo.getMobile().equals(changeCardByMsgRequest.getMobile_old())) {
                baseResponse.setRecode(BusinessMsg.PARAMETER_ERROR);
                baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.PARAMETER_ERROR) + "：输入原卡卡号与原预留手机号和实际绑卡信息不一致");
                throw new BusinessException(baseResponse);
            }

            logger.info("【短验换卡申请】=========查询paycode：" + paycode);
            PlatPaycode platPaycode = userAccountService.queryPlatPaycode(changeCardByMsgRequest.getMer_no(), paycode);
            if (platPaycode == null) {
                logger.info("【短验换卡申请】=========找不到渠道信息");
                throw new BusinessException(BusinessMsg.NO_CHANNEL_INFORMATION,
                        BusinessMsg.getMsg(BusinessMsg.NO_CHANNEL_INFORMATION));
            }

            //检查卡类型
            logger.info("【短验换卡申请】=========检查卡类型");
            if (CardType.CREDIT.getCode().equals(oldCardinfo.getCard_type())) {
                logger.info("【短验换卡申请】=========暂不支持信用卡换绑卡");
                throw new BusinessException(BusinessMsg.TIEDCARD_FAILED, BusinessMsg.getMsg(
                        BusinessMsg.TIEDCARD_FAILED) + "：暂不支持信用卡换绑卡");
            }
            if (newCardinfo != null) {
                if (CardType.CREDIT.getCode().equals(newCardinfo.getCard_type())) {
                    logger.info("【短验换卡申请】=========暂不支持信用卡换绑卡");
                    throw new BusinessException(BusinessMsg.TIEDCARD_FAILED, BusinessMsg.getMsg(
                            BusinessMsg.TIEDCARD_FAILED) + "：暂不支持信用卡换绑卡");
                }
                if (!newCardinfo.getCard_no().equals(changeCardByMsgRequest.getCard_no_old())) {
                    baseResponse.setRecode(BusinessMsg.TIEDCARD);
                    baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.TIEDCARD) + "：卡号【" + changeCardByMsgRequest.getCard_no() + "】");
                    throw new BusinessException(baseResponse);
                } else {
                    if (newCardinfo.getMobile().equals(changeCardByMsgRequest.getPre_mobile())) {
                        baseResponse.setRecode(BusinessMsg.METHODPARAMETER_ERROR);
                        baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.METHODPARAMETER_ERROR) + "：换卡信息与原卡信息一致，无需换卡【" + changeCardByMsgRequest.getCard_no() + "】");
                        throw new BusinessException(baseResponse);
                    }
                }
            }

            //检查用户是否对公用户
            if (CusType.COMPANY.getCode().equals(eaccUserinfo.getCus_type())) {
                logger.info("【短验换卡申请】=========对公用户不允许换绑卡");
                throw new BusinessException(BusinessMsg.DATEBASE_EXCEPTION, BusinessMsg.getMsg(BusinessMsg.DATEBASE_EXCEPTION) + "：对公用户不允许换绑卡");
            }

            logger.info("==================" + "开始组装确认绑卡请求参数");
            //TODO 调用绑卡接口
            Map<String,Object> params=new HashMap<String,Object>();
            params.put("partner_id",platPaycode.getPayment_plat_no());
            params.put("partner_serial_no",transTransreq.getTrans_serial());
            params.put("partner_trans_date",changeCardByMsgRequest.getPartner_trans_date());
            params.put("partner_trans_time",changeCardByMsgRequest.getPartner_trans_time());
            params.put("client_name",changeCardByMsgRequest.getName());
            params.put("id_kind","0");
            params.put("id_no",eaccUserinfo.getId_code());
            params.put("mobile_tel",changeCardByMsgRequest.getPre_mobile());
            params.put("card_no",changeCardByMsgRequest.getCard_no());
            params.put("func_code","1");
            params.put("sendercomp_id",platPaycode.getPayment_plat_no());
            params.put("targetcomp_id","91000");
            params.put("pay_bankacct_type","0");
            //==========建行差异字段==========
            params.put("partner_terminal_id","宝付必填");
            params.put("channelId",platPaycode.getChannel_id());
            //雅酷必填字段
            params.put("partner_userid",changeCardByMsgRequest.getRemark());
            params.put("host",sysParameterService.querySysParameter(changeCardByMsgRequest.getMall_no(), URLConfigUtil.EPAY_SERVER_KEY));
            params.put("url",sysParameterService.querySysParameter(changeCardByMsgRequest.getMall_no(), URLConfigUtil.BAND_ACCOUNT_VERIFY_KEY_OLD));
//        params.put("bank_id","非必填");
            //=======================================
            Map<String,Object> msgBindCardResponse= adapterService.apply4ElementsBindCard(params);
           /* Map<String,Object> msgBindCardResponse= new HashMap<String,Object>();
            msgBindCardResponse.put("success",true);*/
            //判断是否成功
            logger.info("【短验换卡申请】=========E支付返回："+msgBindCardResponse.toString());
            if(OrderStatus.SUCCESS.getCode().equals(msgBindCardResponse.get("order_status"))){
                logger.info("【短验换卡申请】=========短验换卡申请成功");
                transTransreq.setReturn_msg(BusinessMsg.getMsg(BusinessMsg.SUCCESS)+"：短验换卡申请成功");
            }else{
                logger.info("【短验换卡申请】=========绑卡申请失败"+msgBindCardResponse.get("remsg"));
                transTransreq.setReturn_msg(BusinessMsg.getMsg(BusinessMsg.TIEDCARD_FAILED)+"：短验换卡申请失败，");
                throw new BusinessException(BusinessMsg.TIEDCARD_FAILED,"短验换卡申请失败："+msgBindCardResponse.get("remsg"));
            }
            CacheUtil.getCache().set(transTransreq.getOrder_no()+transTransreq.getPlatcust()+transTransreq.getTrans_code(), JSON.toJSONString(changeCardByMsgRequest));
        }catch (Exception e){
            if(e instanceof BusinessException){
                baseResponse=((BusinessException) e).getBaseResponse();
            }
            transTransreq.setReturn_code(baseResponse.getRecode());
            transTransreq.setReturn_msg(baseResponse.getRemsg());
            transTransreq.setStatus(FlowStatus.FAIL.getCode());
            transReqService.insert(transTransreq);
            throw new BusinessException(baseResponse);
        }


        logger.info("【短验换卡申请】=========短验换卡申请完成\n\n\n");
        //更新流水
        transTransreq.setReturn_code(BusinessMsg.SUCCESS);
        transTransreq.setReturn_msg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));
        transTransreq.setStatus(FlowStatus.SUCCESS.getCode());
        transReqService.insert(transTransreq);
        logger.info("更新短验换卡申请业务流水表成功");

        baseResponse.setRecode(BusinessMsg.SUCCESS);
        baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));

        return baseResponse;
    }

    @Override
    public void apply4ElementsRegister(Apply4ElementsRegisterRequest applyRequest) throws BusinessException {
        logger.info(String.format("【四要素开户申请】开始|order_no:%s|trans_serial:%s",
                applyRequest.getOrigin_order_no(),applyRequest.getTrans_serial()));
        TransTransreq transTransreq=null;
        if(StringUtils.isBlank(applyRequest.getOrigin_order_no())){
            //原订单号为空，订单由BOB页面发起
            transTransreq=transReqService.getTransTransReq(applyRequest.clone(),
                    TransConsts.CONFIRM_4ELEMENTS_REGISTER_CODE,TransConsts.CONFIRM_4ELEMENTS_REGISTER_NAME,false);
            BaseResponse baseResponse=transReqService.insert(transTransreq);
            if(baseResponse!=null && !FlowStatus.FAIL.getCode().equals(baseResponse.getOrder_status())
                    && !FlowStatus.SUCCESS.getCode().equals(baseResponse.getOrder_status())){
                transTransreq.setTrans_serial(baseResponse.getTrans_serial());
            }
        }
        TransTransreq oldTransTransreq=null;
        try{
            //判断订单状态
            if(transTransreq!=null){
                oldTransTransreq=transTransreq;
            }else{
                Object transreqObj=CacheUtil.getCache().get(Constants.TRANSREQ_ORDER_KEY+serviceName+applyRequest.getOrigin_order_no());
                if(transreqObj==null){
                    oldTransTransreq=transReqService.queryTransTransReqByOrderno(applyRequest.getOrigin_order_no());
                }else{
                    oldTransTransreq= JSON.parseObject((String)transreqObj,TransTransreq.class);
                }
            }
            if(oldTransTransreq==null){
                //原订单不存在
                logger.info(String.format("【四要素开户申请】原订单在redis和transtransreq表中均不存在|origin_order_no:%s|trans_serial:%s",
                        applyRequest.getOrigin_order_no(),applyRequest.getTrans_serial()));
                throw new BusinessException(BusinessMsg.ORIGINAL_ORDER_NOEXISTENT,BusinessMsg.getMsg(BusinessMsg.ORIGINAL_ORDER_NOEXISTENT));
            }
            if(FlowStatus.SUCCESS.getCode().equals(oldTransTransreq.getStatus()) || FlowStatus.FAIL.getCode().equals(oldTransTransreq.getStatus())){
                logger.info(String.format("【四要素开户申请】原订单已为终态，无需处理|origin_order_no:%s|trans_serial:%s",
                        applyRequest.getOrigin_order_no(),applyRequest.getTrans_serial()));
                return;
            }
            //从redis获取开户信息
            RegisterRequest4 registerRequest4=null;
            if(transTransreq==null){
                Object openAccountParamsObj=CacheUtil.getCache().get(Constants.OPEN_ACCOUNT_PARAMS_KEY+applyRequest.getTrans_serial());
                if(openAccountParamsObj==null){
                    //原订单不存在
                    logger.info(String.format("【四要素开户申请】原订单在redis中不存在，无法获取开户参数|origin_order_no:%s|trans_serial:%s",
                            applyRequest.getOrigin_order_no(),applyRequest.getTrans_serial()));
                    throw new BusinessException(BusinessMsg.ORIGINAL_ORDER_NOEXISTENT,BusinessMsg.getMsg(BusinessMsg.ORIGINAL_ORDER_NOEXISTENT));
                }
                registerRequest4 = JSON.parseObject((String)openAccountParamsObj,RegisterRequest4.class);
            }else{
                registerRequest4=new RegisterRequest4();
                registerRequest4.setName(applyRequest.getName());
                registerRequest4.setId_type(applyRequest.getId_type());
                registerRequest4.setId_code(applyRequest.getId_code());
                registerRequest4.setMobile(applyRequest.getMobile());
                registerRequest4.setEmail(applyRequest.getEmail());
                registerRequest4.setSex(applyRequest.getSex());
                registerRequest4.setBirthday(applyRequest.getBirthday());
                registerRequest4.setOpen_branch(applyRequest.getOpen_branch());
                registerRequest4.setCard_no(applyRequest.getCard_no());
                registerRequest4.setCard_type(applyRequest.getCard_type());
                registerRequest4.setPay_code(applyRequest.getPay_code());
                registerRequest4.setRole(applyRequest.getRole());
                registerRequest4.setAuthed_type(applyRequest.getAuthed_type());
                registerRequest4.setAuthed_amount(applyRequest.getAuthed_amount());
                registerRequest4.setAuthed_limittime(applyRequest.getAuthed_limittime());
                registerRequest4.setPre_mobile(applyRequest.getPre_mobile());
                registerRequest4.setOrder_no(applyRequest.getOrder_no());
                registerRequest4.setPartner_trans_date(applyRequest.getPartner_trans_date());
                registerRequest4.setPartner_trans_time(applyRequest.getPartner_trans_time());
                registerRequest4.setMall_no(applyRequest.getMall_no());
                registerRequest4.setMall_name(applyRequest.getMall_name());
                registerRequest4.setMer_no(applyRequest.getMer_no());
                registerRequest4.setMer_name(applyRequest.getMer_name());
                registerRequest4.setRemark(applyRequest.getRemark());
            }
//            sendMsg(applyRequest.getTrans_serial(),applyRequest.getOrigin_order_no(),registerRequest4.getPre_mobile(),registerRequest4.getMall_no(),registerRequest4.getMer_no(),TransConsts.APPLY_4ELEMENTS_REGISTER_CODE,"四要素开户申请");
            // 查询相应绑卡通道
            PlatPaycode platPaycode = userAccountService.queryPlatPaycode(registerRequest4.getMer_no(), registerRequest4.getPay_code());
            if(platPaycode==null){
                logger.info(String.format("【四要素开户申请】pay_code不合法|origin_order_no:%s|paycode:%s",
                        applyRequest.getOrigin_order_no(),registerRequest4.getPay_code()));
                BaseResponse baseResponse=new BaseResponse();
                baseResponse.setRecode(BusinessMsg.NO_CHANNEL_INFORMATION);
                baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.NO_CHANNEL_INFORMATION)+"：请检查pay_code是否合法");
                throw new BusinessException(baseResponse);
            }
            Map<String,Object> accountVerifyParams=new HashMap<String,Object>(17);
            accountVerifyParams.put("partner_id",platPaycode.getPayment_plat_no());
            accountVerifyParams.put("partner_serial_no",oldTransTransreq.getTrans_serial());
            accountVerifyParams.put("partner_trans_date",registerRequest4.getPartner_trans_date());
            accountVerifyParams.put("partner_trans_time",registerRequest4.getPartner_trans_time());
            accountVerifyParams.put("client_name",registerRequest4.getName());
            accountVerifyParams.put("id_kind","0");
            accountVerifyParams.put("id_no",registerRequest4.getId_code());
            accountVerifyParams.put("card_no",registerRequest4.getCard_no());
            accountVerifyParams.put("mobile_tel",registerRequest4.getPre_mobile());
//            accountVerifyParams.put("verify_info",applyRequest.getIdentifying_code());
            accountVerifyParams.put("func_code","1");
            accountVerifyParams.put("mall_no",registerRequest4.getMall_no());
            accountVerifyParams.put("mer_no",registerRequest4.getMer_no());
            //accountVerifyParams.put("plat_cust",registerRequest4.getPlatcust());
            //accountVerifyParams.put("pay_bankacct_type","0");
            //accountVerifyParams.put("sendercomp_id",platPaycode.getPayment_plat_no());
            accountVerifyParams.put("open_account","1");
            accountVerifyParams.put("is_bankcheck",platPaycode.getIs_bankcheck());
            accountVerifyParams.put("cus_type","1");
            String role_types=registerRequest4.getRole().replace("，",",").trim();
            String[] roleArray=role_types.split(",");
            for(String role_type:roleArray){
                if(RoleType.INVESTOR.getCode().equals(role_type)){
                    accountVerifyParams.put("cjr_role","1");
                }else if(RoleType.BORROWER.getCode().equals(role_type)){
                    accountVerifyParams.put("jkr_role","1");
                }else if(RoleType.ADVANCER.getCode().equals(role_type)){
                    accountVerifyParams.put("dzr_role","1");
                }else if(RoleType.GUARANTOR.getCode().equals(role_type)){
                    accountVerifyParams.put("dbr_role","1");
                }
            }
            //======ccb参数=====
            //accountVerifyParams.put("partner_terminal_id","");
            accountVerifyParams.put("channelId",platPaycode.getChannel_id());
            //=====================================================
            //=========雅酷必填字段============
            accountVerifyParams.put("partner_userid",registerRequest4.getRemark());
            //===================================
            //=================支付公司收单的参数==========

            //==============================================
            accountVerifyParams.put("host",sysParameterService.querySysParameter(registerRequest4.getMall_no(), URLConfigUtil.EPAY_SERVER_KEY));
            accountVerifyParams.put("url",sysParameterService.querySysParameter(registerRequest4.getMall_no(), URLConfigUtil.BAND_ACCOUNT_VERIFY_KEY));
            Map<String,Object> thirdPartyBaseResponse= adapterService.isicBindCard(accountVerifyParams);
            logger.info("【四要素开户申请】三方响应结果："+JSON.toJSON(thirdPartyBaseResponse));
            if(!OrderStatus.SUCCESS.getCode().equals(thirdPartyBaseResponse.get("order_status"))){
                logger.info(String.format("【四要素开户申请】身份校验未通过|origin_order_no:%s|error_msg:%s",
                        oldTransTransreq.getOrigin_order_no(),thirdPartyBaseResponse.get("remsg")));
                BaseResponse baseResponse=new BaseResponse();
                baseResponse.setRecode(BusinessMsg.FOUR_VERIFY_FAILED);
                baseResponse.setRemsg(String.format(BusinessMsg.getMsg(BusinessMsg.FOUR_VERIFY_FAILED)+"|%s",
                        MapUtils.getString(thirdPartyBaseResponse,"remsg","身份校验未通过")));
                throw new BusinessException(baseResponse);
            }
            oldTransTransreq.setStatus(FlowStatus.REQUESTSUCCESS.getCode());
            transReqService.insert(oldTransTransreq);

        }catch (Exception e){
            logger.error(String.format("【四要素开户申请】异常|order_no:%s|trans_serial:%s|error:",
                    applyRequest.getOrigin_order_no(),applyRequest.getTrans_serial()),e);
            BaseResponse baseResponse=new BaseResponse();
            if(e instanceof BusinessException){
                baseResponse=((BusinessException) e).getBaseResponse();
            }else{
                baseResponse.setRecode(BusinessMsg.RUNTIME_EXCEPTION);
                baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.RUNTIME_EXCEPTION));
            }

            throw new BusinessException(baseResponse);
        }
    }

    /**
     * 获取验证码(设置密码)
     * @param vcodeRequest
     * @throws Exception
     * @throws BusinessException
     */
    @Override
    public void getCode4Password(VcodeRequest vcodeRequest) throws Exception, BusinessException {
        Object obj=CacheUtil.getCache().get(Constants.SET_PWD_PARAMS_KEY+ vcodeRequest.getTrans_serial());
        if(obj==null){
            //原订单不存在
            logger.info(String.format("【修改密码】原订单在redis中不存在，无法获取开户参数|trans_serial:%s",
                     vcodeRequest.getTrans_serial()));
            throw new BusinessException(BusinessMsg.ORIGINAL_ORDER_NOEXISTENT,BusinessMsg.getMsg(BusinessMsg.ORIGINAL_ORDER_NOEXISTENT));
        }
        TransTransreq transTransreq=  transReqService.queryTransTransReqByOrderNoAndBatchOrderNo(vcodeRequest.getTrans_serial(),vcodeRequest.getOrigin_order_no());
        SendMsg sendMsg=createMsgModel(transTransreq.getTrans_code(),obj);
        sendMsg(vcodeRequest.getTrans_serial(),  vcodeRequest.getOrigin_order_no(),sendMsg );
    }

    /**
     * 获取验证码(企业开户)
     * @param vcodeRequest
     * @throws Exception
     * @throws BusinessException
     */
    @Override
    public void getCode4Company(VcodeRequest vcodeRequest) throws Exception, BusinessException {
        Object obj=CacheUtil.getCache().get(Constants.OPEN_ACCOUNT_PARAMS_KEY+ vcodeRequest.getTrans_serial());
        if(obj==null){
            //原订单不存在
            logger.info(String.format("【修改密码】原订单在redis中不存在，无法获取开户参数|trans_serial:%s",
                     vcodeRequest.getTrans_serial()));
            throw new BusinessException(BusinessMsg.ORIGINAL_ORDER_NOEXISTENT,BusinessMsg.getMsg(BusinessMsg.ORIGINAL_ORDER_NOEXISTENT));
        }
        TransTransreq transTransreq=  transReqService.queryTransTransReqByOrderNoAndBatchOrderNo(vcodeRequest.getTrans_serial(),vcodeRequest.getOrigin_order_no());
        SendMsg sendMsg=createMsgModel(transTransreq.getTrans_code(),obj);
        sendMsg(vcodeRequest.getTrans_serial(),  vcodeRequest.getOrigin_order_no(),sendMsg );
    }

    private SendMsg createMsgModel(String trans_code, Object openAccountParamsObj) {
        SendMsg sendMsg=new SendMsg();
        sendMsg.setTransCode(trans_code);
        if (TransConsts.APPLY_4ELEMENTS_REGISTER_CODE.equals( trans_code)){
            ApplyOrgRegisterRequest applyOrgRegisterRequest = JSON.parseObject((String)openAccountParamsObj,ApplyOrgRegisterRequest.class);
            sendMsg.setMobile( applyOrgRegisterRequest.getMobile());
            sendMsg.setMallNo( applyOrgRegisterRequest.getMall_no());
            sendMsg.setMerNo(applyOrgRegisterRequest.getMer_no());
            sendMsg.setTransName(TransConsts.APPLY_4ELEMENTS_REGISTER_NAME);
            sendMsg.setContent(sysParameterService.querySysParameter(applyOrgRegisterRequest.getMall_no(),MsgContent.FOUR_ELEMENT_OPEN_ACCOUNT_IDENTIFYING.getMsg()));

        }else if (TransConsts.SET_PWD_CODE.equals( trans_code)){
            SetPwdRequest setPwdRequest = JSON.parseObject((String)openAccountParamsObj,SetPwdRequest.class);
            sendMsg.setMobile( setPwdRequest.getMobile());
            sendMsg.setMallNo( setPwdRequest.getMall_no());
            sendMsg.setMerNo(setPwdRequest.getMer_no());
            sendMsg.setTransName(TransConsts.SET_PWD_NAME);
            sendMsg.setContent(sysParameterService.querySysParameter(setPwdRequest.getMall_no(),MsgContent.SET_NEW_PASSWORD_IDENTIFYING.getMsg()));

        }
        else if (TransConsts.UPDATE_PWD_CODE.equals(trans_code)){
            ModifyPwdRequest modifyPwdRequest = JSON.parseObject((String)openAccountParamsObj,ModifyPwdRequest.class);
            sendMsg.setMobile( modifyPwdRequest.getMobile());
            sendMsg.setMallNo( modifyPwdRequest.getMall_no());
            sendMsg.setMerNo(modifyPwdRequest.getMer_no());
            sendMsg.setTransName(TransConsts.UPDATE_PWD_CODE);
            sendMsg.setContent(sysParameterService.querySysParameter(modifyPwdRequest.getMall_no(),MsgContent.RESET_PASSWORD_IDENTIFYING.getMsg()));

        }else if (TransConsts.COMPANY_REGISTER_CODE.equals(trans_code)){
            CompanyRegisterRequest companyRegisterRequest = JSON.parseObject((String)openAccountParamsObj,CompanyRegisterRequest.class);
            sendMsg.setMobile( companyRegisterRequest.getMobile());
            sendMsg.setMallNo( companyRegisterRequest.getMall_no());
            sendMsg.setMerNo(companyRegisterRequest.getMer_no());
            sendMsg.setTransName(TransConsts.UPDATE_PWD_CODE);
            sendMsg.setContent(sysParameterService.querySysParameter(companyRegisterRequest.getMall_no(),MsgContent.ORG_OPEN_ACCOUNT_IDENTIFYING.getMsg()));

        }else{
            logger.info("trans_code="+trans_code+"不需要发短信");
            throw new BusinessException(BusinessMsg.PARAMETER_ERROR,BusinessMsg.getMsg(BusinessMsg.PARAMETER_ERROR)  );

        }
        return sendMsg;
    }
    private void sendMsg(String transSerial,String originOrderNo,String mobile,String mallNo,String platNo,String transCode,String logTitle){
        SendMsg sendMsg=new SendMsg( );
        sendMsg.setMobile(mobile);
        sendMsg.setMallNo(mallNo);
        sendMsg.setMerNo(platNo);
        sendMsg.setTransCode(transCode);
        sendMsg.setTransName(logTitle);
        sendMsg(  transSerial,  originOrderNo,  sendMsg);
    }
    /**
     * 根据流水号生成短信码，并推送到发短信队列
     * @param transSerial 流水号
     * @param originOrderNo 原始单号
     * @param sendMsg 短信内容
     */
    private void sendMsg(String transSerial,String originOrderNo,SendMsg sendMsg) {

        String identifyingCode=SeqUtil.getRadomNum();
        if("true".equals(mockSwitch)){
            identifyingCode="0000";
        }
        Integer maxErrorNum=Integer.valueOf(
                sysParameterService.querySysParameter(sendMsg.getMallNo(),Constants.IDENTIFYING_CODE_ERROR_NUM));
        Integer identifyingTimeOut=Integer.valueOf(
                sysParameterService.querySysParameter(sendMsg.getMallNo(),Constants.IDENTIFYING_CODE_TIME_OUT));
        String identifying_code_key=Constants.OPEN_ACCOUNT_IDENTIFYING_CODE_KEY+transSerial;
        logger.info("【发送短信验证码】存入redis中的key：" + identifying_code_key);
        Map<String,Object> identifyingDataMap=new HashMap<>(2);
        identifyingDataMap.put("identifying_code",identifyingCode);
        identifyingDataMap.put("max_error_num",maxErrorNum);
        Boolean success=CacheUtil.getCache().setnx(identifying_code_key,JSON.toJSONString(identifyingDataMap));
        if(success){
            CacheUtil.getCache().expire(identifying_code_key,identifyingTimeOut);
        }else{
            logger.info(String.format("【%s】验证码在%s秒只能发送一次|order_no:%s|trans_serial:%s",
                    sendMsg.getTransName(), identifyingTimeOut,originOrderNo,transSerial));
            throw new BusinessException(BusinessMsg.IDENTIFYING_CODE_SEND_TOO_MUCH, String.valueOf(CacheUtil.getCache().ttl(identifying_code_key)));
        }
        //发送验证码短信
        String preMobile= sendMsg.getMobile();
        try {
            if (!StringUtils.isEmpty(preMobile)) {
                logger.info(String.format("【%s】发送短信验证码|order_no:%s|trans_serial:%s|pro_mobile:%s|identifying_code:%s",
                        sendMsg.getTransName(), originOrderNo,transSerial,preMobile,identifyingCode));
                MsgModel msgModel = new MsgModel();
                String mall_name = sysParameterService.querySysParameter(sendMsg.getMallNo(), MsgContent.MALL_NAME_KEY.getMsg());
                String msg_content=sendMsg.getContent();
                msgModel.setOrder_no(originOrderNo);
                msgModel.setPlat_no(sendMsg.getMerNo());
                msgModel.setTrans_code(sendMsg.getTransCode());
                msgModel.setMobile(preMobile);
                msgModel.setMall_no(sendMsg.getMallNo());
                msgModel.setMsgContent(String.format(msg_content,identifyingCode, mall_name));
                sendMsgService.addMsgToQueue(msgModel);
            }
        } catch (Exception ex) {
            logger.error(String.format("【%s】短信发送失败|order_no:%s|trans_serial:%s|error:", sendMsg.getTransName(), originOrderNo,transSerial),ex);
            throw new BusinessException(BusinessMsg.IDENTIFYING_CODE_SEND_ERROR,BusinessMsg.getMsg(BusinessMsg.IDENTIFYING_CODE_SEND_ERROR));
        }
    }

    @Override
//    @Transactional
    public BatchRegisterReturnData confirm4ElementsRegister(Confirm4ElementsRegisterRequest confirmRequest) throws BusinessException {
        logger.info(String.format("【四要素开户确认】开始|order_no:%s|origin_order_no:%s|params:%s",
                confirmRequest.getOrder_no(),confirmRequest.getOrigin_order_no(),JSON.toJSONString(confirmRequest)));
        TransTransreq transTransreq=null;
        //设置redis缓存，防止重复开户
        String registerLockKey=Constants.REGISTER_LOCK_KEY+confirmRequest.getMer_no()+confirmRequest.getId_code()+confirmRequest.getName();
        if(StringUtils.isBlank(confirmRequest.getOrigin_order_no())){
            boolean lockResult=CacheUtil.getCache().setnx(registerLockKey,confirmRequest.getOrder_no());
            if(!lockResult){
                throw new BusinessException(BusinessMsg.ORIGINAL_ORDER_NO_ERROR,"请勿重复提交开户信息");
            }
            CacheUtil.getCache().expire(registerLockKey,3600);

            transTransreq=transReqService.getTransTransReq(confirmRequest.clone(),
                    TransConsts.CONFIRM_4ELEMENTS_REGISTER_CODE,TransConsts.CONFIRM_4ELEMENTS_REGISTER_NAME,false);
            BaseResponse baseResponse=transReqService.insert(transTransreq);
            if(baseResponse!=null){
                if(FlowStatus.SUCCESS.getCode().equals(baseResponse.getOrder_status())
                        || FlowStatus.FAIL.getCode().equals(baseResponse.getOrder_status())){
                    CacheUtil.getCache().del(registerLockKey);
                    throw new BusinessException(BusinessMsg.ORDERNUMBER_REPEATED,BusinessMsg.getMsg(BusinessMsg.ORDERNUMBER_REPEATED));
                }else{
                    transTransreq.setTrans_serial(baseResponse.getTrans_serial());
                }
            }
        }

        TransTransreq oldTransTransreq=null;
        RegisterRequest4 registerRequest=null;
        BatchRegisterReturnData batchRegisterReturnData=new BatchRegisterReturnData();
        NotifyData notifyData=null;
        RegisterNotifyData registerNotifyData=null;
        try{
            //判断订单状态
            Object transreqObj=CacheUtil.getCache().get(Constants.TRANSREQ_ORDER_KEY+serviceName+confirmRequest.getOrigin_order_no());
            if(transTransreq==null){
                if(transreqObj==null){
                    oldTransTransreq=transReqService.queryTransTransReqByOrderno(confirmRequest.getOrigin_order_no());
                }else{
                    oldTransTransreq= JSON.parseObject((String)transreqObj,TransTransreq.class);
                }
                if(oldTransTransreq==null){
                    //原订单不存在
                    logger.info(String.format("【四要素开户确认】原订单在redis和transtransreq表中均不存在|order_no:%s|origin_order_no:%s",
                            confirmRequest.getOrigin_order_no(),confirmRequest.getOrigin_order_no()));
                    throw new BusinessException(BusinessMsg.ORIGINAL_ORDER_NOEXISTENT,BusinessMsg.getMsg(BusinessMsg.ORIGINAL_ORDER_NOEXISTENT));
                }
                if(FlowStatus.SUCCESS.getCode().equals(oldTransTransreq.getStatus()) || FlowStatus.FAIL.getCode().equals(oldTransTransreq.getStatus())){
                    logger.info(String.format("【四要素开户确认】原订单已为终态，无需处理|order_no:%s|origin_order_no:%s",
                            confirmRequest.getOrigin_order_no(),confirmRequest.getOrigin_order_no()));
                    throw new BusinessException(BusinessMsg.ORDERNUMBER_REPEATED,BusinessMsg.getMsg(BusinessMsg.ORDERNUMBER_REPEATED));
                }
                //从redis获取开户信息
                Object openAccountParamsObj=CacheUtil.getCache().get(Constants.OPEN_ACCOUNT_PARAMS_KEY+oldTransTransreq.getTrans_serial());
                if(openAccountParamsObj==null){
                    //原订单不存在
                    logger.info(String.format("【四要素开户确认】原订单在redis中不存在，无法获取开户参数|order_no:%s|origin_order_no:%s",
                            confirmRequest.getOrigin_order_no(),confirmRequest.getOrigin_order_no()));
                    throw new BusinessException(BusinessMsg.ORIGINAL_ORDER_NOEXISTENT,BusinessMsg.getMsg(BusinessMsg.ORIGINAL_ORDER_NOEXISTENT));
                }
                registerRequest= JSON.parseObject((String)openAccountParamsObj,RegisterRequest4.class);
            }else{
                registerRequest=new RegisterRequest4();
                registerRequest.setPlatcust(confirmRequest.getPlatcust());
                registerRequest.setName(confirmRequest.getName());
                registerRequest.setId_type(confirmRequest.getId_type());
                registerRequest.setId_code(confirmRequest.getId_code());
                registerRequest.setMobile(confirmRequest.getMobile());
                registerRequest.setEmail(confirmRequest.getEmail());
                registerRequest.setSex(confirmRequest.getSex());
                registerRequest.setBirthday(confirmRequest.getBirthday());
                registerRequest.setOpen_branch(confirmRequest.getOpen_branch());
                registerRequest.setCard_no(confirmRequest.getCard_no());
                registerRequest.setCard_type(confirmRequest.getCard_type());
                registerRequest.setPay_code(confirmRequest.getPay_code());
                registerRequest.setRole(confirmRequest.getRole());
                registerRequest.setAuthed_type(confirmRequest.getAuthed_type());
                registerRequest.setAuthed_amount(confirmRequest.getAuthed_amount());
                registerRequest.setAuthed_limittime(confirmRequest.getAuthed_limittime());
                registerRequest.setPre_mobile(confirmRequest.getPre_mobile());
                registerRequest.setOrder_no(confirmRequest.getOrder_no());
                registerRequest.setPartner_trans_date(confirmRequest.getPartner_trans_date());
                registerRequest.setPartner_trans_time(confirmRequest.getPartner_trans_time());
                registerRequest.setMall_no(confirmRequest.getMall_no());
                registerRequest.setMall_name(confirmRequest.getMall_name());
                registerRequest.setMer_no(confirmRequest.getMer_no());
                registerRequest.setMer_name(confirmRequest.getMer_name());
                registerRequest.setRemark(confirmRequest.getRemark());
                registerRequest.setNotify_url(confirmRequest.getNotify_url());
                oldTransTransreq=transTransreq;
            }
            logger.info("【四要素开户确认】开户确认参数："+JSON.toJSONString(registerRequest));
            //异步通知参数
            registerNotifyData=new RegisterNotifyData();
            registerNotifyData.setOrder_no(oldTransTransreq.getOrder_no());
            notifyData=new NotifyData();
            notifyData.setMall_no(registerRequest.getMall_no());
            notifyData.setNotifyUrl(registerRequest.getNotify_url());

            //走开户流程
            String platcust=confirm4ElementsDoRegister(confirmRequest,registerRequest,oldTransTransreq);
            if(platcust.contains("null")){
                String[] errorInfo=platcust.split(",");
                transTransreq=null;
                throw new BusinessException(errorInfo[1],errorInfo[2]);
            }
            //发送开户成功短信
            if(StringUtils.isBlank(registerRequest.getPlatcust())){
                String preMobile=registerRequest.getPre_mobile();
                try {
                    if (!StringUtils.isEmpty(preMobile)) {
                        logger.info(String.format("【四要素开户确认】发送开户成功短信|order_no:%s|origin_order_no:%s|pro_mobile:%s",
                                confirmRequest.getOrigin_order_no(),confirmRequest.getOrigin_order_no(),preMobile));
                        MsgModel msgModel = new MsgModel();
                        String mall_name = sysParameterService.querySysParameter(registerRequest.getMall_no(),
                                MsgContent.MALL_NAME_KEY.getMsg());
                        String msg_content=sysParameterService.querySysParameter(registerRequest.getMall_no(),
                                MsgContent.OPEN_ACCOUNT_NOTIFY.getMsg());
                        if(StringUtils.isNotBlank(confirmRequest.getPlatcust())){
                            msg_content=sysParameterService.querySysParameter(registerRequest.getMall_no(),
                                    MsgContent.ACCOUNT_ACTIVATION_NOTIFY.getMsg());
                        }
                        msgModel.setOrder_no(confirmRequest.getOrigin_order_no());
                        msgModel.setPlat_no(registerRequest.getMer_no());
                        msgModel.setTrans_code( TransConsts.CONFIRM_4ELEMENTS_REGISTER_CODE);

                        msgModel.setMobile(preMobile);
                        msgModel.setMall_no(registerRequest.getMall_no());
//                    msgModel.setPlatcust(eaccUserinfo.getMallcust());
                        msgModel.setMsgContent(String.format(msg_content, mall_name));
                        sendMsgService.addMsgToQueue(msgModel);
                    }
                } catch (Exception ex) {
                    logger.info(String.format("【四要素开户确认】开户成功通知短信发送失败|order_no:%s|origin_order_no:%s|error:",
                            confirmRequest.getOrigin_order_no(),confirmRequest.getOrigin_order_no()),ex);
                }
            }
            if(transTransreq!=null){
                transTransreq.setReturn_code(BusinessMsg.SUCCESS);
                transTransreq.setReturn_msg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));
                transTransreq.setStatus(FlowStatus.SUCCESS.getCode());
            }
            batchRegisterReturnData.setPlatcust(platcust);
            batchRegisterReturnData.setOrigin_order_no(confirmRequest.getOrigin_order_no());
            logger.error(String.format("【四要素开户确认】开户成功|order_no:%s|origin_order_no:%s",
                    confirmRequest.getOrigin_order_no(),confirmRequest.getOrigin_order_no()));
            oldTransTransreq.setReturn_code(BusinessMsg.SUCCESS);
            oldTransTransreq.setReturn_msg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));
            oldTransTransreq.setStatus(FlowStatus.SUCCESS.getCode());
            transReqService.insert(oldTransTransreq);

            registerNotifyData.setRemsg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));
            registerNotifyData.setRecode(BusinessMsg.SUCCESS);
            registerNotifyData.setOrder_status(OrderStatus.SUCCESS.getCode());
            registerNotifyData.setOrder_info(OrderStatus.SUCCESS.getMsg());
            registerNotifyData.setPlatcust(platcust);

            //从数据库中取出授权信息
            List<EaccUserauth> userauthList=authCheckService.queryAuthInfo(registerRequest.getMall_no(),registerRequest.getMer_no(),platcust);
            if(userauthList!=null && userauthList.size()>0){
                StringBuilder authType=new StringBuilder("");
                for(EaccUserauth userauth:userauthList){
                    authType.append(userauth.getAuthed_type()).append(",");
                }
                String authTypeStr="";
                if(authType.length()>0){
                    authTypeStr=authType.substring(0,authType.length()-1);
                }
                registerNotifyData.setAuthed_type(authTypeStr);
                registerNotifyData.setAuthed_amount(userauthList.get(0).getAuthed_amount());
                registerNotifyData.setAuthed_limittime(userauthList.get(0).getAuthed_limittime());
            }else{
                registerNotifyData.setAuthed_type(registerRequest.getAuthed_type());
                registerNotifyData.setAuthed_amount(registerRequest.getAuthed_amount());
                registerNotifyData.setAuthed_limittime(registerRequest.getAuthed_limittime());
            }

            return batchRegisterReturnData;
        }catch (Exception e){
            logger.error(String.format("【四要素开户确认】异常|order_no:%s|origin_order_no:%s|error:",
                    confirmRequest.getOrigin_order_no(),confirmRequest.getOrigin_order_no()),e);
            BaseResponse baseResponse=new BaseResponse();
            if(e instanceof BusinessException){
                baseResponse=((BusinessException) e).getBaseResponse();
            }else{
                baseResponse.setRecode(BusinessMsg.RUNTIME_EXCEPTION);
                baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.RUNTIME_EXCEPTION));
            }
            if(transTransreq!=null){
                //只有在张金涛页面过来的才发失败的异步通知
                transTransreq.setReturn_code(baseResponse.getRecode());
                transTransreq.setReturn_msg(baseResponse.getRemsg());
                transTransreq.setStatus(FlowStatus.FAIL.getCode());
                if(registerNotifyData!=null){
                    if(StringUtils.isNotBlank(confirmRequest.getPlatcust())){
                        registerNotifyData.setPlatcust(confirmRequest.getPlatcust());
                    }
                    registerNotifyData.setRemsg(transTransreq.getReturn_msg());
                    registerNotifyData.setRecode(transTransreq.getReturn_code());
                    registerNotifyData.setOrder_status(OrderStatus.FAIL.getCode());
                    registerNotifyData.setOrder_info(OrderStatus.FAIL.getMsg());
                    registerNotifyData.setAuthed_amount(confirmRequest.getAuthed_amount());
                    registerNotifyData.setAuthed_limittime(confirmRequest.getAuthed_limittime());
                }
            }
            throw new BusinessException(baseResponse);
        }finally {
            CacheUtil.getCache().del(registerLockKey);
            if(transTransreq!=null && (FlowStatus.SUCCESS.getCode().equals(transTransreq.getStatus())
                    || FlowStatus.FAIL.getCode().equals(transTransreq.getStatus()))){
                transReqService.insert(transTransreq);
                logger.info("notifyData:"+JSON.toJSONString(notifyData)+",registerNotifyData:"+JSON.toJSONString(registerNotifyData));
                if(notifyData!=null && registerNotifyData!=null && StringUtils.isNotBlank(notifyData.getNotifyUrl())){
                    notifyData.setNotifyContent(JSON.toJSONString(registerNotifyData));
                    notifyService.addNotify(notifyData);
                }
            }else if(oldTransTransreq!=null && FlowStatus.SUCCESS.getCode().equals(oldTransTransreq.getStatus())){
                logger.info("notifyData:"+JSON.toJSONString(notifyData)+",registerNotifyData:"+JSON.toJSONString(registerNotifyData));
                if(notifyData!=null && registerNotifyData!=null && StringUtils.isNotBlank(notifyData.getNotifyUrl())){
                    notifyData.setNotifyContent(JSON.toJSONString(registerNotifyData));
                    notifyService.addNotify(notifyData);
                }
            }
        }
    }

    private String confirm4ElementsDoRegister(Confirm4ElementsRegisterRequest confirmRequest,RegisterRequest4 registerRequest, TransTransreq oldTransTransreq) throws BusinessException {
        logger.info(String.format("【四要素开户确认】开始开户逻辑|origin_order_no:%s|params:%s|",
                confirmRequest.getOrigin_order_no(),JSON.toJSONString(registerRequest)));

        // 查询相应绑卡通道
        PlatPaycode platPaycode = userAccountService.queryPlatPaycode(registerRequest.getMer_no(), registerRequest.getPay_code());
        if(platPaycode==null){
            logger.info(String.format("【四要素开户确认】pay_code不合法|origin_order_no:%s|paycode:%s",
                    confirmRequest.getOrigin_order_no(),registerRequest.getPay_code()));
            BaseResponse baseResponse=new BaseResponse();
            baseResponse.setRecode(BusinessMsg.NO_CHANNEL_INFORMATION);
            baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.NO_CHANNEL_INFORMATION)+"：请检查pay_code是否合法");
            throw new BusinessException(baseResponse);
        }
        //同一个用户在一个平台只可以注册一次，在一个集团可以注册多次，但只有一个账户
        EaccUserinfo oldEaccUserInfo=userAccountService.checkUserInfo(registerRequest.getMall_no(),registerRequest.getName(),
                registerRequest.getId_code(), registerRequest.getMer_no(),true);

        String mallcust=SeqUtil.getSeqNum();
        if(oldEaccUserInfo!=null){
            mallcust=oldEaccUserInfo.getMallcust();
        }
        String platcust=SeqUtil.getSeqNum();
        //四要素绑卡接口
        Map<String,Object> accountVerifyParams=new HashMap<String,Object>(17);
        if(StringUtils.isNotBlank(registerRequest.getPlatcust())){
            //如果已经在平台注册
            if(oldEaccUserInfo==null){
                throw new BusinessException(BusinessMsg.INVALID_ACCOUNT,"待激活用户不存在");
            }
            //判断platcust是否正确
            if(!oldEaccUserInfo.getMallcust().equals(registerRequest.getPlatcust())){
                throw new BusinessException(BusinessMsg.PARAMETER_ERROR,"platcust不匹配");
            }
            //判断四要素是否正确
            logger.info("【四要素开户确认】用户已在平台注册,判断已绑卡号和当前卡号是否一致，如果一致，直接返回平台用户号！");
            List<EaccCardinfo> cardinfoList=accountSearchService.queryEaccCardInfo(registerRequest.getMall_no(),oldEaccUserInfo.getMallcust(),registerRequest.getCard_no(),null);
            if(cardinfoList.size()>0){
                EaccCardinfo oldEaccCardinfo=cardinfoList.get(0);
                if(registerRequest.getPre_mobile().equals(oldEaccCardinfo.getMobile())){
                    if(Constants.YES.equals(oldEaccUserInfo.getPwd_flg())){
                        return oldEaccUserInfo.getMallcust();
                    }
                }else{
                    logger.info("【四要素开户确认】用户已在平台注册,已绑卡手机号与预留手机号不一致！原手机号："+oldEaccCardinfo.getMobile());
                    throw new BusinessException(BusinessMsg.TIEDCARD_FAILED,"预留手机号不匹配");
                }
            }else{
                if(StringUtils.isNotBlank(registerRequest.getPlatcust())){
                    throw new BusinessException(BusinessMsg.PARAMETER_ERROR,"银行卡号不匹配");
                }
            }
            //激活，设置交易密码
            Map<String,Object> sendParams = new HashMap<String, Object>();
            BaseResponse baseResponse = new BaseResponse();
            sendParams.put("mall_no",confirmRequest.getMall_no());
            sendParams.put("mer_no",confirmRequest.getMer_no());
            sendParams.put("id_kind",registerRequest.getId_type());
            sendParams.put("id_no",registerRequest.getId_code());
            sendParams.put("tran_flag",TranFlag.SET.getCode());
            sendParams.put("plat_cust",registerRequest.getPlatcust());
            //四要素信息
            sendParams.put("card_no",registerRequest.getCard_no());
            sendParams.put("mobile_tel",registerRequest.getPre_mobile());

            sendParams.put("random_key",confirmRequest.getRandom_key());
            sendParams.put("password",confirmRequest.getTrans_pwd());
            sendParams.put("host",sysParameterService.querySysParameter(confirmRequest.getMall_no(),URLConfigUtil.EPAY_SERVER_KEY));
            sendParams.put("url",sysParameterService.querySysParameter(confirmRequest.getMall_no(),URLConfigUtil.ICIS_PASSWORDMODIFY));
            logger.info("【账户激活，交易密码设置】==========请求第三方支付参数："+JSON.toJSON(sendParams));
            Map<String,Object> resultMap = adapterService.setOrResetPassword(sendParams);
            logger.info("【账户激活，交易密码设置】==========第三方支付返回结果："+JSON.toJSON(resultMap));
            if(null == resultMap || null == resultMap.get("order_status") || ".".equals(resultMap.get("order_status"))){
                throw new BusinessException(BusinessMsg.CALL_REMOTE_ERROR,BusinessMsg.getMsg(BusinessMsg.CALL_REMOTE_ERROR)+"第三方支付返回结果解析失败");
            }
            if(!OrderStatus.SUCCESS.getCode().equals(resultMap.get("order_status"))) {
                baseResponse.setRecode(BusinessMsg.FAIL);
                baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.FAIL));
                oldTransTransreq.setStatus(FlowStatus.FAIL.getCode());
                oldTransTransreq.setReturn_code(resultMap.get("recode").toString());
                oldTransTransreq.setReturn_msg(resultMap.get("remsg").toString());
                throw new BusinessException(MapUtils.getString(resultMap,"recode",BusinessMsg.SET_PASSWORD_ERROR),MapUtils.getString(resultMap,"remsg",BusinessMsg.getMsg(BusinessMsg.SET_PASSWORD_ERROR)));
            }
        }else{
            if(oldEaccUserInfo!=null){
                List<EaccCardinfo> cardinfos = userAccountService.queryCardInfoForMultiCards(registerRequest.getMall_no(), oldEaccUserInfo.getMallcust());
                if(cardinfos.size() > 0){
                    logger.info("【四要素开户确认，开户】有绑卡信息");
                    for(EaccCardinfo eaccCardinfo : cardinfos){
                        if(platPaycode.getChannel_id().equals(eaccCardinfo.getPay_channel()) && registerRequest.getCard_no().equals(eaccCardinfo.getCard_no())){
                            if(registerRequest.getPre_mobile().equals(eaccCardinfo.getMobile())){
                                return oldEaccUserInfo.getMallcust();
                            }else {
                                throw new BusinessException(BusinessMsg.MOBILE_ERROR, BusinessMsg.getMsg(BusinessMsg.MOBILE_ERROR) + "-请先解绑");
                            }
                        }
                    }
                }
            }
            //开户
            accountVerifyParams.put("partner_id",platPaycode.getPayment_plat_no());
            accountVerifyParams.put("partner_serial_no",oldTransTransreq.getTrans_serial());
            accountVerifyParams.put("partner_trans_date",registerRequest.getPartner_trans_date() != null?registerRequest.getPartner_trans_date():confirmRequest.getPartner_trans_date());
            accountVerifyParams.put("partner_trans_time",registerRequest.getPartner_trans_time() != null?registerRequest.getPartner_trans_time():confirmRequest.getPartner_trans_time());
            accountVerifyParams.put("client_name",registerRequest.getName()!= null?registerRequest.getName():confirmRequest.getName());
            accountVerifyParams.put("id_kind","0");
            accountVerifyParams.put("id_no",registerRequest.getId_code()!= null?registerRequest.getId_code():confirmRequest.getId_code());
            accountVerifyParams.put("card_no",registerRequest.getCard_no()!= null?registerRequest.getCard_no():confirmRequest.getCard_no());
            accountVerifyParams.put("mobile_tel",registerRequest.getPre_mobile()!= null?registerRequest.getPre_mobile():confirmRequest.getPre_mobile());
            accountVerifyParams.put("verify_info",confirmRequest.getIdentifying_code());
            if(deployEnvironment.equals("CCB")) {
                accountVerifyParams.put("func_code", "2");
            }
            if(deployEnvironment.equals("BOB")){
                accountVerifyParams.put("func_code", "2");
//                accountVerifyParams.put("func_code", "3");
            }
            accountVerifyParams.put("mall_no",registerRequest.getMall_no()!=null?registerRequest.getMall_no():confirmRequest.getMall_no());
            accountVerifyParams.put("mer_no",registerRequest.getMer_no()!=null?registerRequest.getMer_no():confirmRequest.getMer_no());
            accountVerifyParams.put("plat_cust",mallcust);
            accountVerifyParams.put("random_key",confirmRequest.getRandom_key());
            accountVerifyParams.put("passwod",confirmRequest.getTrans_pwd());
            accountVerifyParams.put("type",confirmRequest.getType());
            //accountVerifyParams.put("pay_bankacct_type","0");
            //accountVerifyParams.put("sendercomp_id",platPaycode.getPayment_plat_no());
            accountVerifyParams.put("open_account","1");
            accountVerifyParams.put("is_bankcheck",platPaycode.getIs_bankcheck());
            accountVerifyParams.put("cus_type","1");
            String role_types=registerRequest.getRole().replace("，",",").trim();
            String[] roleArray=role_types.split(",");
            for(String role_type:roleArray){
                if(RoleType.INVESTOR.getCode().equals(role_type)){
                    accountVerifyParams.put("cjr_role","1");
                }else if(RoleType.BORROWER.getCode().equals(role_type)){
                    accountVerifyParams.put("jkr_role","1");
                }else if(RoleType.ADVANCER.getCode().equals(role_type)){
                    accountVerifyParams.put("dzr_role","1");
                }else if(RoleType.GUARANTOR.getCode().equals(role_type)){
                    accountVerifyParams.put("dbr_role","1");
                }
            }
            //======ccb参数=====
            //accountVerifyParams.put("partner_terminal_id","");
            accountVerifyParams.put("channelId",platPaycode.getChannel_id());
            //=====================================================
            //=========雅酷必填字段============
            //accountVerifyParams.put("partner_userid",registerRequest.getRemark());
            //===================================
            //=================支付公司收单的参数==========

            //==============================================
            accountVerifyParams.put("host",sysParameterService.querySysParameter(registerRequest.getMall_no(), URLConfigUtil.EPAY_SERVER_KEY));
            accountVerifyParams.put("url",sysParameterService.querySysParameter(registerRequest.getMall_no(), URLConfigUtil.BAND_ACCOUNT_VERIFY_KEY));
            Map<String,Object> thirdPartyBaseResponse= adapterService.isicBindCard(accountVerifyParams);
            logger.info("【四要素开户确认】三方响应结果："+JSON.toJSON(thirdPartyBaseResponse));
            if(!OrderStatus.SUCCESS.getCode().equals(thirdPartyBaseResponse.get("order_status"))){
                logger.info(String.format("【四要素开户确认】身份校验未通过|origin_order_no:%s|error_msg:%s",
                        oldTransTransreq.getOrigin_order_no(),thirdPartyBaseResponse.get("remsg")));
                if(OrderStatus.PROCESSING.getCode().equals(thirdPartyBaseResponse.get("order_status"))){
                    return String.format("null,%s,%s",
                            thirdPartyBaseResponse.get("recode"),thirdPartyBaseResponse.get("remsg"));
                }
                BaseResponse baseResponse=new BaseResponse();
                baseResponse.setRecode(BusinessMsg.FOUR_VERIFY_FAILED);
                baseResponse.setRemsg(String.format(BusinessMsg.getMsg(BusinessMsg.FOUR_VERIFY_FAILED)+"|%s",
                        MapUtils.getString(thirdPartyBaseResponse,"remsg","身份校验未通过")));
                throw new BusinessException(baseResponse);
            }
        }

        doRegister(mallcust,platcust, registerRequest, platPaycode.getChannel_id(), oldTransTransreq);
        return mallcust;
    }

    @Override
    public BatchRegisterReturnData apply3ElementsRegister(Apply3ElementsRegisterRequest applyRequest) throws BusinessException {
        logger.info(String.format("【三要素开户申请】开始|origin_order_no:%s",
                applyRequest.getOrigin_order_no()));
        TransTransreq transTransreq=null;
        //设置redis缓存，防止重复开户
        String registerLockKey=Constants.REGISTER_LOCK_KEY+applyRequest.getMer_no()+applyRequest.getId_code()+applyRequest.getName();
        if(StringUtils.isBlank(applyRequest.getOrigin_order_no())){
            boolean lockResult=CacheUtil.getCache().setnx(registerLockKey,applyRequest.getOrder_no());
            if(!lockResult){
                throw new BusinessException(BusinessMsg.ORIGINAL_ORDER_NO_ERROR,"请勿重复提交开户信息");
            }
            CacheUtil.getCache().expire(registerLockKey,3600);

            transTransreq=transReqService.getTransTransReq(applyRequest.clone(),
                    TransConsts.APPLY_3ELEMENTS_REGISTER_CODE,TransConsts.APPLY_3ELEMENTS_REGISTER_NAME,false);
            transTransreq.setRemark(applyRequest.getOrigin_order_no());
            BaseResponse baseResponse=transReqService.insert(transTransreq);
            if(baseResponse!=null){
                CacheUtil.getCache().del(registerLockKey);
                throw new BusinessException(BusinessMsg.ORDERNUMBER_REPEATED,BusinessMsg.getMsg(BusinessMsg.ORDERNUMBER_REPEATED));
            }
        }
        TransTransreq oldTransTransreq=null;
        RegisterRequest3 registerRequest=null;
        BatchRegisterReturnData batchRegisterReturnData=new BatchRegisterReturnData();
        try{
            //判断订单状态
            if(transTransreq==null){
                Object transreqObj=CacheUtil.getCache().get(Constants.TRANSREQ_ORDER_KEY+serviceName+ applyRequest.getOrigin_order_no());
                if(transreqObj==null){
                    oldTransTransreq=transReqService.queryTransTransReqByOrderno(applyRequest.getOrigin_order_no());
                }else{
                    oldTransTransreq= JSON.parseObject((String)transreqObj,TransTransreq.class);
                }
                if(oldTransTransreq==null){
                    //原订单不存在
                    logger.info(String.format("【三要素开户申请】原订单在redis和transtransreq表中均不存在|origin_order_no:%s",
                            applyRequest.getOrigin_order_no()));
                    throw new BusinessException(BusinessMsg.ORIGINAL_ORDER_NOEXISTENT,BusinessMsg.getMsg(BusinessMsg.ORIGINAL_ORDER_NOEXISTENT));
                }
                if(FlowStatus.SUCCESS.getCode().equals(oldTransTransreq.getStatus()) || FlowStatus.FAIL.getCode().equals(oldTransTransreq.getStatus())){
                    logger.info(String.format("【三要素开户申请】原订单已为终态，无需处理|origin_order_no:%s",
                            applyRequest.getOrigin_order_no()));
                    throw new BusinessException(BusinessMsg.ORDERNUMBER_REPEATED,BusinessMsg.getMsg(BusinessMsg.ORDERNUMBER_REPEATED));
                }
                //从redis获取开户信息
                Object openAccountParamsObj=CacheUtil.getCache().get(Constants.OPEN_ACCOUNT_PARAMS_KEY+oldTransTransreq.getTrans_serial());
                if(openAccountParamsObj==null){
                    //原订单不存在
                    logger.info(String.format("【三要素开户申请】原订单在redis中不存在，无法获取开户参数|origin_order_no:%s",
                            applyRequest.getOrigin_order_no()));
                    throw new BusinessException(BusinessMsg.ORIGINAL_ORDER_NOEXISTENT,BusinessMsg.getMsg(BusinessMsg.ORIGINAL_ORDER_NOEXISTENT));
                }
                registerRequest= JSON.parseObject((String)openAccountParamsObj,RegisterRequest3.class) ;
            }else{
                registerRequest=new RegisterRequest3();
                registerRequest.setName(applyRequest.getName());
                registerRequest.setId_type(applyRequest.getId_type());
                registerRequest.setId_code(applyRequest.getId_code());
                registerRequest.setMobile(applyRequest.getMobile());
                registerRequest.setEmail(applyRequest.getEmail());
                registerRequest.setSex(applyRequest.getSex());
                registerRequest.setBirthday(applyRequest.getBirthday());
                registerRequest.setOpen_branch(applyRequest.getOpen_branch());
                registerRequest.setCard_no(applyRequest.getCard_no());
                registerRequest.setCard_type(applyRequest.getCard_type());
                registerRequest.setPay_code(applyRequest.getPay_code());
                registerRequest.setRole(applyRequest.getRole());
                registerRequest.setAuthed_type(applyRequest.getAuthed_type());
                registerRequest.setAuthed_amount(applyRequest.getAuthed_amount());
                registerRequest.setAuthed_limittime(applyRequest.getAuthed_limittime());
                registerRequest.setOrder_no(applyRequest.getOrder_no());
                registerRequest.setPartner_trans_date(applyRequest.getPartner_trans_date());
                registerRequest.setPartner_trans_time(applyRequest.getPartner_trans_time());
                registerRequest.setMall_no(applyRequest.getMall_no());
                registerRequest.setMall_name(applyRequest.getMall_name());
                registerRequest.setMer_no(applyRequest.getMer_no());
                registerRequest.setMer_name(applyRequest.getMer_name());
                registerRequest.setRemark(applyRequest.getRemark());
                registerRequest.setNotify_url(applyRequest.getNotify_url());
                oldTransTransreq=transTransreq;
            }

            //走开户流程
            String platcust= apply3ElementsDoRegister(applyRequest,registerRequest,oldTransTransreq);
            //发送开户成功短信
            String preMobile=registerRequest.getMobile();
            try {
                if (!StringUtils.isEmpty(preMobile)) {
                    logger.info(String.format("【三要素开户申请】发送开户成功短信|origin_order_no:%s|pro_mobile:%s",
                            applyRequest.getOrigin_order_no(),preMobile));
                    MsgModel msgModel = new MsgModel();
                    String mall_name = sysParameterService.querySysParameter(registerRequest.getMall_no(), SysParamterKey.MALL_NAME);
                    String msg_content=sysParameterService.querySysParameter(registerRequest.getMall_no(),
                            MsgContent.OPEN_ACCOUNT_NOTIFY.getMsg());
                    msgModel.setOrder_no(applyRequest.getOrigin_order_no());
                    msgModel.setPlat_no(registerRequest.getMer_no());
                    msgModel.setTrans_code(TransConsts.APPLY_3ELEMENTS_REGISTER_CODE);
                    msgModel.setMobile(preMobile);
                    msgModel.setMall_no(registerRequest.getMall_no());
//                    msgModel.setPlatcust(eaccUserinfo.getMallcust());
                    if(StringUtils.isNotBlank(msg_content)){
                        msgModel.setMsgContent(String.format(msg_content, mall_name));
                        sendMsgService.addMsgToQueue(msgModel);
                    }
                }
            } catch (Exception ex) {
                logger.info(String.format("【三要素开户申请】开户成功通知短信发送失败|origin_order_no:%s|error:",
                        applyRequest.getOrigin_order_no()),ex);
            }
            if(transTransreq!=null){
                transTransreq.setReturn_code(BusinessMsg.SUCCESS);
                transTransreq.setReturn_msg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));
                transTransreq.setStatus(FlowStatus.SUCCESS.getCode());
            }
            batchRegisterReturnData.setPlatcust(platcust);
            batchRegisterReturnData.setOrigin_order_no(applyRequest.getOrigin_order_no());
            logger.error(String.format("【三要素开户申请】开户成功|origin_order_no:%s",
                    applyRequest.getOrigin_order_no()));
            oldTransTransreq.setStatus(FlowStatus.SUCCESS.getCode());
            transReqService.insert(oldTransTransreq);

            if(StringUtils.isNotBlank(registerRequest.getNotify_url())){
                //给平台发送异步通知
                RegisterNotifyData registerNotifyData=new RegisterNotifyData();
                registerNotifyData.setRemsg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));
                registerNotifyData.setRecode(BusinessMsg.SUCCESS);
                registerNotifyData.setOrder_no(oldTransTransreq.getOrder_no());
                registerNotifyData.setOrder_status(OrderStatus.SUCCESS.getCode());
                registerNotifyData.setOrder_info(OrderStatus.SUCCESS.getMsg());
                registerNotifyData.setPlatcust(platcust);

                //从数据库中取出授权信息
                List<EaccUserauth> userauthList=authCheckService.queryAuthInfo(registerRequest.getMall_no(),registerRequest.getMer_no(),platcust);
                if(userauthList!=null && userauthList.size()>0){
                    StringBuilder authType=new StringBuilder("");
                    for(EaccUserauth userauth:userauthList){
                        authType.append(userauth.getAuthed_type()).append(",");
                    }
                    String authTypeStr="";
                    if(authType.length()>0){
                        authTypeStr=authType.substring(0,authType.length()-1);
                    }
                    registerNotifyData.setAuthed_type(authTypeStr);
                    registerNotifyData.setAuthed_amount(userauthList.get(0).getAuthed_amount());
                    registerNotifyData.setAuthed_limittime(userauthList.get(0).getAuthed_limittime());
                }else{
                    registerNotifyData.setAuthed_type(registerRequest.getAuthed_type());
                    registerNotifyData.setAuthed_amount(registerRequest.getAuthed_amount());
                    registerNotifyData.setAuthed_limittime(registerRequest.getAuthed_limittime());
                }

                NotifyData notifyData=new NotifyData();
                notifyData.setMall_no(registerRequest.getMall_no());
                notifyData.setNotifyUrl(registerRequest.getNotify_url());
                notifyData.setNotifyContent(JSON.toJSONString(registerNotifyData));
                notifyService.addNotify(notifyData);
            }
            return batchRegisterReturnData;
        }catch (Exception e){
            logger.error(String.format("【三要素开户申请】异常|origin_order_no:%s|error:",
                    applyRequest.getOrigin_order_no()),e);
            BaseResponse baseResponse=new BaseResponse();
            if(e instanceof BusinessException){
                baseResponse=((BusinessException) e).getBaseResponse();
            }else{
                baseResponse.setRecode(BusinessMsg.RUNTIME_EXCEPTION);
                baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.RUNTIME_EXCEPTION));
            }
            if(transTransreq!=null){
                transTransreq.setReturn_code(baseResponse.getRecode());
                transTransreq.setReturn_msg(baseResponse.getRemsg());
                transTransreq.setStatus(FlowStatus.FAIL.getCode());
            }
            throw new BusinessException(baseResponse);
        }finally {
            CacheUtil.getCache().del(registerLockKey);
            if(transTransreq!=null){
                transReqService.insert(transTransreq);
            }
        }
    }

    private String apply3ElementsDoRegister(Apply3ElementsRegisterRequest applyRequest, RegisterRequest3 registerRequest, TransTransreq oldTransTransreq) throws BusinessException {
        logger.info(String.format("【三要素开户申请】开始开户逻辑|origin_order_no:%s|params:%s|",
                applyRequest.getOrigin_order_no(),JSON.toJSONString(registerRequest)));

        // 查询相应绑卡通道
        PlatPaycode platPaycode = userAccountService.queryPlatPaycode(registerRequest.getMer_no(), registerRequest.getPay_code());
        if(platPaycode==null){
            logger.info(String.format("【三要素开户申请】pay_code不合法|origin_order_no:%s|paycode:%s",
                    applyRequest.getOrigin_order_no(),registerRequest.getPay_code()));
            BaseResponse baseResponse=new BaseResponse();
            baseResponse.setRecode(BusinessMsg.NO_CHANNEL_INFORMATION);
            baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.NO_CHANNEL_INFORMATION)+"：请检查pay_code是否合法");
            throw new BusinessException(baseResponse);
        }

        //同一个用户在一个平台只可以注册一次，在一个集团可以注册多次，但只有一个账户
        EaccUserinfo oldEaccUserInfo=userAccountService.checkUserInfo(registerRequest.getMall_no(),registerRequest.getName(),
                registerRequest.getId_code(), registerRequest.getMer_no(),false);
        //如果已经在平台注册
        if(oldEaccUserInfo!=null){
            logger.info("【三要素开户申请】用户已在平台注册,判断已绑卡号和当前卡号是否一致，如果一致，直接返回平台用户号！");
            List<EaccCardinfo> cardinfoList=accountSearchService.queryEaccCardInfo(registerRequest.getMall_no(),oldEaccUserInfo.getMallcust(),null,null);
            if(cardinfoList.size()>0){
                EaccCardinfo oldEaccCardinfo=cardinfoList.get(0);
                if(registerRequest.getCard_no().equals(oldEaccCardinfo.getCard_no())){
//                    //检查授权信息
//                    List<EaccUserauth> eaccUserauthList=accountSearchService.queryEaccUserAuth(
//                            oldEaccUserInfo.getMallcust(),registerRequest.getMer_no(),oldEaccUserInfo.getMall_no(),AuthStatus.ALREADY_AUTH.getCode());
//                    if(eaccUserauthList.size()>0){
//                        return oldEaccUserInfo.getMallcust();
//                    }
                    if(Constants.YES.equals(oldEaccUserInfo.getPwd_flg())){
                        return oldEaccUserInfo.getMallcust();
                    }
                }else{
                    logger.info("【三要素开户申请】用户已在平台注册,已绑卡与欲绑卡不一致！卡号："+oldEaccCardinfo.getCard_no());
                    throw new BusinessException(BusinessMsg.TIEDCARD_FAILED,
                            BusinessMsg.getMsg(BusinessMsg.TIEDCARD_FAILED)+"用户已在平台注册,已绑卡与欲绑卡不一致！卡号："+oldEaccCardinfo.getCard_no());
                }
            }
        }

        String mallcust=SeqUtil.getSeqNum();
        if(oldEaccUserInfo!=null){
            mallcust=oldEaccUserInfo.getMallcust();
        }
        String platcust=SeqUtil.getSeqNum();
        //请求支付三要素鉴权
        Map<String,Object> params=new HashMap<String,Object>();
        params.put("partner_id",platPaycode.getPayment_plat_no());
        params.put("partner_serial_no",oldTransTransreq.getTrans_serial());
        params.put("partner_trans_date",registerRequest.getPartner_trans_date());
        params.put("partner_trans_time",registerRequest.getPartner_trans_time());
        params.put("client_name",registerRequest.getName());
        params.put("id_kind","0");
        params.put("id_no",registerRequest.getId_code());
        params.put("card_no",registerRequest.getCard_no());
        params.put("sendercomp_id",platPaycode.getPayment_plat_no());
        params.put("targetcomp_id","91000");
        params.put("func_code","3");
        params.put("mall_no",registerRequest.getMall_no());
        params.put("mer_no",registerRequest.getMer_no());
        params.put("plat_cust",platcust);
        params.put("random_key",applyRequest.getRandom_key());
        params.put("passwod",applyRequest.getTrans_pwd());
        params.put("type",applyRequest.getType());
        params.put("mobile",registerRequest.getMobile());
        params.put("channelId",platPaycode.getChannel_id());
        params.put("pay_bankacct_type","0");
        params.put("cus_type","1");
        String role_types=registerRequest.getRole().replace("，",",").trim();
        String[] roleArray=role_types.split(",");
        for(String role_type:roleArray){
            if(RoleType.INVESTOR.getCode().equals(role_type)){
                params.put("cjr_role","1");
            }else if(RoleType.BORROWER.getCode().equals(role_type)){
                params.put("jkr_role","1");
            }else if(RoleType.ADVANCER.getCode().equals(role_type)){
                params.put("dzr_role","1");
            }else if(RoleType.GUARANTOR.getCode().equals(role_type)){
                params.put("dbr_role","1");
            }
        }
        //=====ccb参数=====
        params.put("partner_terminal_id","");
        if(null!=params.get("email") && "".equals(params.get("email"))){
            params.put("email",params.get("email"));
        }
        if(null!=params.get("sex") && "".equals(params.get("sex"))){
            params.put("sex",params.get("sex"));
        }
        params.put("open_account","1");
        params.put("is_bankcheck",platPaycode.getIs_bankcheck());
        params.put("host",sysParameterService.querySysParameter(registerRequest.getMall_no(), URLConfigUtil.EPAY_SERVER_KEY));
        params.put("url",sysParameterService.querySysParameter(registerRequest.getMall_no(), URLConfigUtil.BAND_ACCOUNT_VERIFY_KEY));
        logger.info("【三要素开户申请】开始调用E支付绑卡，参数："+params);
        Map<String,Object> personBindCardResponse=adapterService.isicBindCard(params);
        logger.info("【三要素开户申请】E支付返回:"+personBindCardResponse.toString());
        if(!OrderStatus.SUCCESS.getCode().equals(personBindCardResponse.get("order_status"))){
            logger.info(String.format("【三要素开户申请】身份校验未通过|origin_order_no:%s|error_msg:%s",
                    applyRequest.getOrigin_order_no(),personBindCardResponse.get("remsg")));
            BaseResponse baseResponse=new BaseResponse();
            baseResponse.setRecode(BusinessMsg.FOUR_VERIFY_FAILED);
            baseResponse.setRemsg(String.format(BusinessMsg.getMsg(BusinessMsg.FOUR_VERIFY_FAILED)+"|%s",
                    MapUtils.getString(personBindCardResponse,"remsg","身份校验未通过")));
            throw new BusinessException(baseResponse);
        }

//        //设置交易密码
//        Map<String,Object> sendParams = new HashMap<String, Object>();
//        BaseResponse baseResponse = new BaseResponse();
//        sendParams.put("mall_no",applyRequest.getMall_no());
//        sendParams.put("mer_no",applyRequest.getMer_no());
//        sendParams.put("id_kind","2");
//        sendParams.put("id_no","");
//        sendParams.put("tran_flag","0");
//        sendParams.put("random_key",applyRequest.getRandom_key());
//        sendParams.put("password",applyRequest.getTrans_pwd());
//        sendParams.put("host",sysParameterService.querySysParameter(applyRequest.getMall_no(),URLConfigUtil.EPAY_SERVER_KEY));
//        sendParams.put("url",sysParameterService.querySysParameter(applyRequest.getMall_no(),URLConfigUtil.ICIS_PASSWORDMODIFY));
//        logger.info("【三要素开户申请】==========请求第三方支付参数："+JSON.toJSON(sendParams));
//        Map<String,Object> resultMap = adapterService.setOrResetPassword(sendParams);
//        logger.info("【三要素开户申请】==========第三方支付返回结果："+JSON.toJSON(resultMap));
//        if(null == resultMap || null == resultMap.get("order_status") || ".".equals(resultMap.get("order_status"))){
//            throw new BusinessException(BusinessMsg.CALL_REMOTE_ERROR,BusinessMsg.getMsg(BusinessMsg.CALL_REMOTE_ERROR)+"第三方支付返回结果解析失败");
//        }
//        if(!OrderStatus.SUCCESS.getCode().equals(resultMap.get("order_status"))) {
//            baseResponse.setRecode(BusinessMsg.FAIL);
//            baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.FAIL));
//            oldTransTransreq.setStatus(FlowStatus.FAIL.getCode());
//            oldTransTransreq.setReturn_code(resultMap.get("recode").toString());
//            oldTransTransreq.setReturn_msg(resultMap.get("remsg").toString());
//            throw new BusinessException(resultMap.get("recode").toString(),resultMap.get("remsg").toString());
//        }

        doRegister(mallcust,platcust,registerRequest,platPaycode.getChannel_id(),oldTransTransreq);

        return mallcust;
    }
    @Override
    public BaseResponse applyOrgRegisterOpening(ApplyOrgRegisterOpeningRequest applyRequests)throws  BusinessException{
        logger.info("【境外开户申请】开始|order_no:%s", applyRequests.getOrder_no());
        TransTransreq transTransreq=null;
        transTransreq=transReqService.getTransTransReq(applyRequests.clone(),
                TransConsts.OVERSEAS_OPENING_CODE,TransConsts.OVERSEAS_OPENING_NAME,false);
        transTransreq.setRemark(applyRequests.getOrder_no());
        transTransreq.setNotify_url(applyRequests.getNotify_url());
        BaseResponse baseResponse=transReqService.insert(transTransreq);
        if(baseResponse!=null){
            throw new BusinessException(BusinessMsg.ORIGINAL_ORDER_NO_ERROR,"请勿重复提交开户信息");
        }
        TransTransreq oldTransTransreq=new TransTransreq();
        CompanyRegisterRequest registerRequest=null;
        BaseResponse registerOpeningRequest=new BaseResponse();
            try {
                registerRequest=new CompanyRegisterRequest();
                registerRequest.setPlatcust(applyRequests.getPlatcust());
                registerRequest.setAbroad_name(applyRequests.getAbroad_name());
                registerRequest.setMobile(applyRequests.getMobile());
                registerRequest.setEmail(applyRequests.getEmail());
                registerRequest.setBusiness_license(applyRequests.getBusiness_license());
                registerRequest.setBank_license(applyRequests.getBank_license());
                registerRequest.setOpen_branch(applyRequests.getOpen_branch());
                registerRequest.setCard_no(applyRequests.getCard_no());
                registerRequest.setCard_type(applyRequests.getCard_type());
                registerRequest.setRole(applyRequests.getRole());
                registerRequest.setAuthed_amount(applyRequests.getAuthed_amount());
                registerRequest.setAuthed_limittime(applyRequests.getAuthed_limittime());
                registerRequest.setAuthed_type(applyRequests.getAuthed_type());
                registerRequest.setOrder_no(applyRequests.getOrder_no());
                registerRequest.setPartner_trans_date(applyRequests.getPartner_trans_date());
                registerRequest.setPartner_trans_time(applyRequests.getPartner_trans_time());
                registerRequest.setMall_no(applyRequests.getMall_no());
                registerRequest.setMall_name(applyRequests.getMall_name());
                registerRequest.setMer_no(applyRequests.getMer_no());
                registerRequest.setMer_name(applyRequests.getMer_name());
                registerRequest.setRemark(applyRequests.getRemark());
                registerRequest.setNotify_url(applyRequests.getNotify_url());
                registerRequest.setId_code(applyRequests.getId_code());
                registerRequest.setId_type(applyRequests.getId_type());
                registerRequest.setPre_mobile(applyRequests.getPre_mobile());
              /*  oldTransTransreq=transTransreq;*/
              //开户
                String platcust= applyOrgRegisterOpening(applyRequests,registerRequest,transTransreq);
               /* registerOpeningRequest.setOrder_no(applyRequests.getOrder_no());*/
                /*registerOpeningRequest.setPlatcust(applyRequests.getPlatcust());*/
               if (StringUtils.isNoneBlank(platcust)){
                   logger.info("境外开户受理成功",registerRequest.getOrder_no());
                   oldTransTransreq.setStatus(FlowStatus.INPROCESS.getCode());
                   oldTransTransreq.setNotify_url(registerRequest.getNotify_url());
                   transReqService.insert(oldTransTransreq);

                   RegisterNotifyData registerNotifyData=new RegisterNotifyData();
                   registerNotifyData.setRemsg(BusinessMsg.getMsg(BusinessMsg.ACCEPTANCE_SUCCESS));
                   registerNotifyData.setRecode(BusinessMsg.ACCEPTANCE_SUCCESS);
                   registerNotifyData.setOrder_no(registerRequest.getOrder_no());
                   registerNotifyData.setOrder_status(OrderStatus.PROCESSING.getCode());
                   registerNotifyData.setOrder_info(OrderStatus.PROCESSING.getMsg());
                   registerNotifyData.setPlatcust(platcust);
                   NotifyData notifyData=new NotifyData();
                   notifyData.setMall_no(registerRequest.getMall_no());
                   notifyData.setNotifyUrl(registerRequest.getNotify_url());
                   notifyData.setNotifyContent(JSON.toJSONString(registerNotifyData));
                   notifyService.addNotify(notifyData);
               }
            }catch (Exception e){
                logger.error(String.format("【境外开户申请】异常|order_no:%s|error:",
                        applyRequests.getOrder_no()),e);
                throw e;
            }


        return registerOpeningRequest ;
    }

    @Override
    public BatchRegisterReturnData applyOrgRegister(ApplyOrgRegisterRequest applyRequest)throws BusinessException {

        logger.info(String.format("【对公开户申请】开始|origin_order_no:%s",  applyRequest.getOrigin_order_no()));
        TransTransreq transTransreq=null;
        //给平台发送异步通知
        RegisterNotifyData registerNotifyData=new RegisterNotifyData();
        //设置redis缓存，防止重复开户
        String registerLockKey=Constants.REGISTER_LOCK_KEY+applyRequest.getMer_no()+applyRequest.getOrg_name();
        if(StringUtils.isBlank(applyRequest.getOrigin_order_no())){
            boolean lockResult=CacheUtil.getCache().setnx(registerLockKey,applyRequest.getOrder_no());
            if(!lockResult){
                throw new BusinessException(BusinessMsg.ORIGINAL_ORDER_NO_ERROR,"请勿重复提交开户信息");
            }
            CacheUtil.getCache().expire(registerLockKey,3600);

            transTransreq=transReqService.getTransTransReq(applyRequest.clone(),
                    TransConsts.COMPANY_REGISTER_CODE,TransConsts.COMPANY_REGISTER_NAME,false);
            transTransreq.setRemark(applyRequest.getOrigin_order_no());
            transTransreq.setNotify_url(applyRequest.getNotify_url());
            BaseResponse baseResponse=transReqService.insert(transTransreq);
            if(baseResponse!=null){
                CacheUtil.getCache().del(registerLockKey);
                throw new BusinessException(BusinessMsg.ORDERNUMBER_REPEATED,BusinessMsg.getMsg(BusinessMsg.ORDERNUMBER_REPEATED));
            }
        }
        TransTransreq oldTransTransreq=null;
        CompanyRegisterRequest registerRequest=null;
        BatchRegisterReturnData batchRegisterReturnData=new BatchRegisterReturnData();
        try{
            //判断订单状态
            Object transreqObj=CacheUtil.getCache().get(Constants.TRANSREQ_ORDER_KEY+serviceName+applyRequest.getOrigin_order_no());
            if(transTransreq==null){
                if(transreqObj==null){
                    oldTransTransreq=transReqService.queryTransTransReqByOrderno(applyRequest.getOrigin_order_no());
                }else{
                    oldTransTransreq= JSON.parseObject((String)transreqObj,TransTransreq.class);
                }
                if(oldTransTransreq==null){
                    //原订单不存在
                    logger.info(String.format("【对公开户申请】原订单在redis和transtransreq表中均不存在|origin_order_no:%s",
                            applyRequest.getOrigin_order_no()));
                    throw new BusinessException(BusinessMsg.ORIGINAL_ORDER_NOEXISTENT,BusinessMsg.getMsg(BusinessMsg.ORIGINAL_ORDER_NOEXISTENT));
                }
                if(FlowStatus.SUCCESS.getCode().equals(oldTransTransreq.getStatus()) || FlowStatus.FAIL.getCode().equals(oldTransTransreq.getStatus())){
                    logger.info(String.format("【对公开户申请】原订单已为终态，无需处理|origin_order_no:%s",
                            applyRequest.getOrigin_order_no()));
                    throw new BusinessException(BusinessMsg.ORDERNUMBER_REPEATED,BusinessMsg.getMsg(BusinessMsg.ORDERNUMBER_REPEATED));
                }
                
                validMsgCode(applyRequest.getOrigin_order_no(),applyRequest.getIdentifying_code(),applyRequest.getTrans_serial(),applyRequest.getOrigin_order_no());

                //从redis获取开户信息
                Object openAccountParamsObj=CacheUtil.getCache().get(Constants.OPEN_ACCOUNT_PARAMS_KEY+oldTransTransreq.getTrans_serial());
                if(openAccountParamsObj==null){
                    //原订单不存在
                    logger.info(String.format("【对公开户申请】原订单在redis中不存在，无法获取开户参数|origin_order_no:%s",
                            applyRequest.getOrigin_order_no()));
                    throw new BusinessException(BusinessMsg.ORIGINAL_ORDER_NOEXISTENT,BusinessMsg.getMsg(BusinessMsg.ORIGINAL_ORDER_NOEXISTENT));
                }
                registerRequest= JSON.parseObject( (String) openAccountParamsObj,CompanyRegisterRequest.class);
                validMsgCode(applyRequest.getOrigin_order_no(),applyRequest.getIdentifying_code(), oldTransTransreq.getTrans_serial(),"对公开户申请");

            }else{
                registerRequest=new CompanyRegisterRequest();
                registerRequest.setPlatcust(applyRequest.getPlatcust());
                registerRequest.setOrg_name(applyRequest.getOrg_name());
                registerRequest.setMobile(applyRequest.getMobile());
                registerRequest.setEmail(applyRequest.getEmail());
                registerRequest.setBusiness_license(applyRequest.getBusiness_license());
                registerRequest.setBank_license(applyRequest.getBank_license());
                registerRequest.setOpen_branch(applyRequest.getOpen_branch());
                registerRequest.setCard_no(applyRequest.getCard_no());
                registerRequest.setCard_type(applyRequest.getCard_type());
                registerRequest.setRole(applyRequest.getRole());
                registerRequest.setAuthed_amount(applyRequest.getAuthed_amount());
                registerRequest.setAuthed_limittime(applyRequest.getAuthed_limittime());
                registerRequest.setAuthed_type(applyRequest.getAuthed_type());
                registerRequest.setOrder_no(applyRequest.getOrder_no());
                registerRequest.setPartner_trans_date(applyRequest.getPartner_trans_date());
                registerRequest.setPartner_trans_time(applyRequest.getPartner_trans_time());
                registerRequest.setMall_no(applyRequest.getMall_no());
                registerRequest.setMall_name(applyRequest.getMall_name());
                registerRequest.setMer_no(applyRequest.getMer_no());
                registerRequest.setMer_name(applyRequest.getMer_name());
                registerRequest.setRemark(applyRequest.getRemark());
                registerRequest.setNotify_url(applyRequest.getNotify_url());
                oldTransTransreq=transTransreq;
            }

            //走开户流程
            String platcust= applyOrgDoRegister(applyRequest,registerRequest,oldTransTransreq);

//            transTransreq.setReturn_code(BusinessMsg.SUCCESS);
//            transTransreq.setReturn_msg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));
            batchRegisterReturnData.setPlatcust(StringUtils.isNoneBlank(platcust)?platcust:applyRequest.getPlatcust());
            batchRegisterReturnData.setOrigin_order_no(applyRequest.getOrigin_order_no());

            if(StringUtils.isNoneBlank(platcust)){
                logger.info(String.format("【对公开户申请】开户成功|origin_order_no:%s",
                        applyRequest.getOrigin_order_no()));

                oldTransTransreq.setStatus(FlowStatus.INPROCESS.getCode());
                oldTransTransreq.setNotify_url(registerRequest.getNotify_url());
                registerNotifyData.setRemsg(BusinessMsg.getMsg(BusinessMsg.ACCEPTANCE_SUCCESS));
                registerNotifyData.setOrder_status(OrderStatus.PROCESSING.getCode());
                registerNotifyData.setOrder_info(OrderStatus.PROCESSING.getMsg());
                registerNotifyData.setRecode(BusinessMsg.ACCEPTANCE_SUCCESS);

                registerNotifyData.setPlatcust(platcust);
            }else{
                oldTransTransreq.setStatus(FlowStatus.SUCCESS.getCode());
                oldTransTransreq.setNotify_url(registerRequest.getNotify_url());
                registerNotifyData.setRemsg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));
                registerNotifyData.setOrder_status(OrderStatus.SUCCESS.getCode());
                registerNotifyData.setOrder_info(OrderStatus.SUCCESS.getMsg());
                registerNotifyData.setRecode(BusinessMsg.SUCCESS);

                registerNotifyData.setPlatcust(registerRequest.getPlatcust());
            }

            //从数据库中取出授权信息
            List<EaccUserauth> userauthList=authCheckService.queryAuthInfo(registerRequest.getMall_no(),registerRequest.getMer_no(),registerNotifyData.getPlatcust());
            if(userauthList!=null && userauthList.size()>0){
                StringBuilder authType=new StringBuilder("");
                for(EaccUserauth userauth:userauthList){
                    authType.append(userauth.getAuthed_type()).append(",");
                }
                String authTypeStr="";
                if(authType.length()>0){
                    authTypeStr=authType.substring(0,authType.length()-1);
                }
                registerNotifyData.setAuthed_type(authTypeStr);
                registerNotifyData.setAuthed_amount(userauthList.get(0).getAuthed_amount());
                registerNotifyData.setAuthed_limittime(userauthList.get(0).getAuthed_limittime());
            }else{
                registerNotifyData.setAuthed_type(registerRequest.getAuthed_type());
                registerNotifyData.setAuthed_amount(registerRequest.getAuthed_amount());
                registerNotifyData.setAuthed_limittime(registerRequest.getAuthed_limittime());
            }
            return batchRegisterReturnData;
        }catch (Exception e){
            logger.error(String.format("【对公开户申请】异常|origin_order_no:%s|error:",
                    applyRequest.getOrigin_order_no()),e);
            BaseResponse baseResponse=new BaseResponse();
            if(e instanceof BusinessException){
                baseResponse=((BusinessException) e).getBaseResponse();
            }else{
                baseResponse.setRecode(BusinessMsg.RUNTIME_EXCEPTION);
                baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.RUNTIME_EXCEPTION));
            }
            if(!BusinessMsg.VERIFICATION_OUT_OF_DATE.equals(baseResponse.getRecode()) &&
                    !BusinessMsg.VERIFICATION_ERROR.equals(baseResponse.getRecode()) && transTransreq!=null){
                transTransreq.setReturn_code(baseResponse.getRecode());
                transTransreq.setReturn_msg(baseResponse.getRemsg());
                transTransreq.setStatus(FlowStatus.FAIL.getCode());
                transReqService.insert(transTransreq);

                registerNotifyData.setRemsg(BusinessMsg.getMsg(BusinessMsg.FAIL));
                registerNotifyData.setOrder_status(OrderStatus.FAIL.getCode());
                registerNotifyData.setOrder_info(OrderStatus.FAIL.getMsg());
                registerNotifyData.setRecode(BusinessMsg.FAIL);
            }
            throw new BusinessException(baseResponse);
        }finally {
            CacheUtil.getCache().del(registerLockKey);
            if(registerRequest!=null && StringUtils.isNotBlank(registerNotifyData.getOrder_status())){
                registerNotifyData.setOrder_no(oldTransTransreq!=null?oldTransTransreq.getOrder_no():applyRequest.getOrder_no());
                if(StringUtils.isBlank(registerNotifyData.getAuthed_type())){
                    registerNotifyData.setAuthed_type(registerRequest.getAuthed_type());
                }
                if(StringUtils.isBlank(registerNotifyData.getAuthed_limittime())){
                    registerNotifyData.setAuthed_limittime(registerRequest.getAuthed_limittime());
                }
                if(registerNotifyData.getAuthed_amount()==null){
                    registerNotifyData.setAuthed_amount(registerRequest.getAuthed_amount());
                }

                NotifyData notifyData=new NotifyData();
                notifyData.setMall_no(registerRequest.getMall_no());
                notifyData.setNotifyUrl(registerRequest.getNotify_url());
                notifyData.setNotifyContent(JSON.toJSONString(registerNotifyData));
                notifyService.addNotify(notifyData);
            }
        }
    }

    /**
     * 验证短信码
     * @param originOrderNo
     * @param idCode
     * @param trans_serial
     * @param logTitle
     */
    @Override
    public void validMsgCode(String originOrderNo,String idCode, String trans_serial,String logTitle) {
        //获取验证码信息
        String identifying_code_key= Constants.OPEN_ACCOUNT_IDENTIFYING_CODE_KEY+trans_serial;
        Object identifyingCodeObj= CacheUtil.getCache().get(identifying_code_key);
        if(identifyingCodeObj==null){
            logger.info("【%s】验证码无效或已失效|order_no:%s|origin_order_no:%s|trans_serial:%s",
                    logTitle, originOrderNo,originOrderNo,trans_serial);
            logger.info("【验证短信验证码】key：" + identifying_code_key);
            throw new BusinessException(BusinessMsg.VERIFICATION_OUT_OF_DATE,BusinessMsg.getMsg(BusinessMsg.VERIFICATION_OUT_OF_DATE));
        }
        Map<String,Object> identifyingDataMap= JSON.parseObject((String)identifyingCodeObj, HashedMap.class);
        String identifyingCode= (String) identifyingDataMap.get("identifying_code");
        Integer maxErrorNum= (Integer) identifyingDataMap.get("max_error_num");
        if(maxErrorNum>0){
            if(!identifyingCode.equals(idCode)){
                //验证码错误
                logger.info("【%s】验证码错误|order_no:%s|origin_order_no:%s|identifying_code:%s|real_identifying_code:%s|left_identify_num:%s",
                        logTitle,originOrderNo,originOrderNo,idCode,identifyingCode,maxErrorNum);
                maxErrorNum--;
                identifyingDataMap.put("max_error_num",maxErrorNum);
                CacheUtil.getCache().set(identifying_code_key,JSON.toJSONString(identifyingDataMap));
                throw new BusinessException(BusinessMsg.VERIFICATION_ERROR,BusinessMsg.getMsg(BusinessMsg.VERIFICATION_ERROR));
            }
        }else{
            //验证码错误次数过多
            logger.info("【%s】验证码错误次数过多|order_no:%s|origin_order_no:%s|identifying_code:%s|real_identifying_code:%s",
                    logTitle, originOrderNo,originOrderNo,idCode,identifyingCode);
            throw new BusinessException(BusinessMsg.VERIFICATION_ERROR_TOO_MUCH,BusinessMsg.getMsg(BusinessMsg.VERIFICATION_ERROR_TOO_MUCH));
        }
    }

    @Override
    public boolean bindCardByNoMsg(BindCardRequest bindCardRequest) throws BusinessException {
        TransTransreq transTransreq=transReqService.getTransTransReq(bindCardRequest.clone(), TransConsts.CARD_BIND_CODE,
                TransConsts.CARD_BIND_NAME,false);
        //异步通知地址存在流水中
        transTransreq.setNotify_url(bindCardRequest.getNotify_url());
        transTransreq.setPlatcust(bindCardRequest.getPlatcust());
        transReqService.insert(transTransreq);
        try{
            //检查卡类型
            logger.info("【无短验绑卡】=========检查卡类型，order_no:"+bindCardRequest.getOrder_no());
            if(CardType.CREDIT.getCode().equals(bindCardRequest.getCard_type())){
                logger.info("【无短验绑卡】=========暂不支持信用卡绑卡");
                throw new BusinessException(BusinessMsg.TIEDCARD_FAILED, BusinessMsg.getMsg(
                        BusinessMsg.TIEDCARD_FAILED)+"：暂不支持信用卡绑卡");
            }
            //检查用户是否存在
            EaccUserinfo eaccUserinfo=userAccountService.queryEaccUserInfoByMallNoAndPlatcust(bindCardRequest.getMall_no(),bindCardRequest.getPlatcust());
            if(eaccUserinfo==null){
                logger.info("【无短验绑卡】=========用户不存在，platcust："+bindCardRequest.getPlatcust());
                    throw new BusinessException(BusinessMsg.INVALID_ACCOUNT, BusinessMsg.getMsg(BusinessMsg.INVALID_ACCOUNT)+"：该用户未注册");
            }
//            if(CusType.PERSONAL.getCode().equals(bindCardRequest.getType())){//个人用户
//                if(StringUtils.isBlank(bindCardRequest.getId_type())||StringUtils.isBlank(bindCardRequest.getId_code())
//                        ||StringUtils.isBlank(bindCardRequest.getName())||StringUtils.isBlank(bindCardRequest.getCard_no())){
//                    throw new BusinessException(BusinessMsg.PARAMETER_LACK,BusinessMsg.getMsg(BusinessMsg.PARAMETER_LACK));
//                }
//                logger.info("【无短验绑卡】=========检查该个人用户是否存在，order_no:"+bindCardRequest.getOrder_no());
//                eaccUserinfo=userAccountService.checkUserInfo(bindCardRequest.getMall_no(),
//                        bindCardRequest.getName(),bindCardRequest.getId_code(),bindCardRequest.getMer_no(),true);
//                if(eaccUserinfo==null){
//                    logger.info("【无短验绑卡】=========用户不存在，platcust："+bindCardRequest.getPlatcust());
//                    throw new BusinessException(BusinessMsg.INVALID_ACCOUNT, BusinessMsg.getMsg(BusinessMsg.INVALID_ACCOUNT)+"：该用户未注册");
//                }
//            }else if(CusType.COMPANY.getCode().equals(bindCardRequest.getType())){//对公用户
//                if(StringUtils.isBlank(bindCardRequest.getOrg_no())||StringUtils.isBlank(bindCardRequest.getAcct_name())
//                        ||StringUtils.isBlank(bindCardRequest.getAcct_name())||StringUtils.isBlank(bindCardRequest.getOpen_branch())){
//                    throw new BusinessException(BusinessMsg.PARAMETER_LACK,BusinessMsg.getMsg(BusinessMsg.PARAMETER_LACK));
//                }
//                logger.info("【无短验绑卡】=========检查该对公用户是否存在，order_no:"+bindCardRequest.getOrder_no());
//                eaccUserinfo=userAccountService.checkMallUserInfo(bindCardRequest.getMall_no(),
//                        bindCardRequest.getAcct_name(),bindCardRequest.getMer_no(),true);
//                if(eaccUserinfo==null){
//                    logger.info("【无短验绑卡】=========用户不存在，platcust："+bindCardRequest.getPlatcust());
//                    throw new BusinessException(BusinessMsg.INVALID_ACCOUNT, BusinessMsg.getMsg(BusinessMsg.INVALID_ACCOUNT)+"：该用户未注册");
//                }
//            }else{
//                throw new BusinessException(BusinessMsg.PARAMETER_ERROR,"用户类型错误");
//            }
            EaccCardinfo oldEaccCardinfo =null;
            //限制绑定借记卡张数不大于10
            boolean isOverCardLimit = userAccountService.checkCardLimit(bindCardRequest.getMall_no(),
                    bindCardRequest.getPlatcust());
            if (isOverCardLimit){
                logger.info("【无短验绑卡】=========个人借记卡绑卡数量超过限制。，order_no:"+bindCardRequest.getOrder_no());
                throw new BusinessException(BusinessMsg.CARD_NUMBER_OUT_LIMIT,BusinessMsg.getMsg(
                        BusinessMsg.CARD_NUMBER_OUT_LIMIT)+"：客户【"+bindCardRequest.getPlatcust()+"】个人借记卡绑卡数量超过限制");
            }
            if(CusType.PERSONAL.getCode().equals(bindCardRequest.getType())) {
                logger.info("【无短验绑卡】=========检查该个人用户是否已绑卡，order_no:"+bindCardRequest.getOrder_no());
                oldEaccCardinfo = userAccountService.queryEaccCardInfo(bindCardRequest.getMall_no(),
                        bindCardRequest.getPlatcust(), bindCardRequest.getCard_no(), null);
                if (oldEaccCardinfo != null) {
                    String bind_card_no = bindCardRequest.getCard_no();
                    String card_no = oldEaccCardinfo.getCard_no();
                    logger.info("【无短验绑卡】=========该个人用户已绑卡，卡号为：" + card_no);
                    if (bind_card_no.equals(card_no)) {
                        logger.info("【无短验绑卡】=========绑卡信息与请求绑卡信息一致继续渠道绑卡。");
                        throw new BusinessException(BusinessMsg.REPEAT_BINDING, BusinessMsg.getMsg(
                                BusinessMsg.REPEAT_BINDING) + "：绑卡信息与请求绑卡信息一致，无需重复绑卡");
                    } else {
                        throw new BusinessException(BusinessMsg.REPEAT_BINDING, BusinessMsg.getMsg(
                                BusinessMsg.REPEAT_BINDING) + "：绑卡信息与请求绑卡信息不一致，请检查传入卡号是否正确");
                    }
                }
            }
            if(CusType.COMPANY.getCode().equals(bindCardRequest.getType())) {
                logger.info("【无短验绑卡】=========检查该对公用户是否已绑卡，order_no:"+bindCardRequest.getOrder_no());
                oldEaccCardinfo = userAccountService.queryCompanyCard(bindCardRequest.getMall_no(), bindCardRequest.getPlatcust());
                if (oldEaccCardinfo != null) {
                    String bind_card_no = bindCardRequest.getAcct_no();
                    String card_no = oldEaccCardinfo.getAcct_no();
                    if ("1".equals(oldEaccCardinfo.getStatus())){
                        logger.info("【无短验绑卡】=========该对公用户已绑卡，卡号为：" + card_no);
                        if (bind_card_no.equals(card_no)) {
                            logger.info("【无短验绑卡】=========绑卡信息与请求绑卡信息一致继续渠道绑卡。");
                            if(CusType.COMPANY.getCode().equals(bindCardRequest.getType())) {
                                throw new BusinessException(BusinessMsg.REPEAT_BINDING, BusinessMsg.getMsg(
                                        BusinessMsg.REPEAT_BINDING) + "：绑卡信息与请求绑卡信息一致，无需重复绑卡");
                            }
                        } else {
                            throw new BusinessException(BusinessMsg.REPEAT_BINDING, BusinessMsg.getMsg(
                                    BusinessMsg.REPEAT_BINDING) + "：绑卡信息与请求绑卡信息不一致，请检查传入卡号是否正确");
                        }
                    }else {
                        logger.info("【无短验绑卡】=========该对公用户已绑卡，卡号为：" + card_no);
                        if (bind_card_no.equals(card_no)) {
                            logger.info("【无短验绑卡】=========绑卡信息重复，此卡正在审核，无需重复申请");
                            if(CusType.COMPANY.getCode().equals(bindCardRequest.getType())) {
                                throw new BusinessException(BusinessMsg.REPEAT_BINDING, BusinessMsg.getMsg(
                                        BusinessMsg.REPEAT_BINDING) + "：绑卡信息重复，此卡正在审核，无需重复申请");
                            }
                        } else {
                            throw new BusinessException(BusinessMsg.REPEAT_BINDING, BusinessMsg.getMsg(
                                    BusinessMsg.REPEAT_BINDING) + "：此客户有卡正在审核中，不可重复申请");
                        }
                    }
                }
            }
            logger.info("【无短验绑卡】=========查询paycode："+bindCardRequest.getPay_code()+"，order_no:"+bindCardRequest.getOrder_no());
            PlatPaycode platPaycode=userAccountService.queryPlatPaycode(
                    bindCardRequest.getMer_no(),bindCardRequest.getPay_code());
            if(platPaycode==null){
                logger.info("【无短验绑卡】=========找不到渠道信息");
                throw new BusinessException(BusinessMsg.NO_CHANNEL_INFORMATION,
                        BusinessMsg.getMsg(BusinessMsg.NO_CHANNEL_INFORMATION));
            }
            EaccCardinfo eaccCardinfo=new EaccCardinfo();
            //判断是普通用户还是对公用户
            if(CusType.PERSONAL.getCode().equals(bindCardRequest.getType())){
                //个人用户
                Map<String,Object>  noMsgBindCardResponse = null;
                if(IsBankCheck.NO.getCode().equals(platPaycode.getIs_bankcheck())){
                    logger.info("【无短验绑卡】=========走四要素通道，order_no:"+bindCardRequest.getOrder_no());
                    Map<String,Object> params=new HashMap<String,Object>();
                    params.put("partner_id",platPaycode.getPayment_plat_no());
                    params.put("partner_serial_no",transTransreq.getTrans_serial());
                    params.put("partner_trans_date",bindCardRequest.getPartner_trans_date());
                    params.put("partner_trans_time",bindCardRequest.getPartner_trans_time());
                    params.put("client_name",bindCardRequest.getName());
                    params.put("id_kind","0");
                    params.put("id_no",bindCardRequest.getId_code());
                    params.put("mobile_tel",bindCardRequest.getPre_mobile());
                    params.put("card_no",bindCardRequest.getCard_no());
                    params.put("func_code","3");
                    params.put("sendercomp_id",platPaycode.getPayment_plat_no());
                    params.put("targetcomp_id","91000");
                    params.put("channelId",platPaycode.getChannel_id());
                    params.put("pay_bankacct_type","0");
                    //=========雅酷必填字段============
                    params.put("partner_userid",bindCardRequest.getRemark());
                    //===================================
                    String host = sysParameterService.querySysParameter(bindCardRequest.getMall_no(), URLConfigUtil.EPAY_SERVER_KEY);
                    String url = sysParameterService.querySysParameter(bindCardRequest.getMall_no(), URLConfigUtil.BAND_ACCOUNT_VERIFY_KEY_OLD);
                    params.put("host",host);
                    params.put("url",url);
                    noMsgBindCardResponse= adapterService.apply4ElementsBindCard(params);
                    logger.info("返回msgBindCardResponse："+noMsgBindCardResponse.toString());
                    if(!OrderStatus.SUCCESS.getCode().equals(noMsgBindCardResponse.get("order_status"))){
                        logger.info("【无短验绑卡】=========绑卡失败："+noMsgBindCardResponse.get("remsg"));
                        throw new BusinessException(BusinessMsg.TIEDCARD_FAILED, BusinessMsg.getMsg(BusinessMsg.TIEDCARD_FAILED)+"："+noMsgBindCardResponse.get("remsg"));
                    }
                }else{
                    logger.info("【无短验绑卡】=========走支付公司通道，order_no:"+bindCardRequest.getOrder_no());
                    //绑卡
                    logger.info("【无短验绑卡】=========用户未绑卡，开始绑卡");
                    Map<String,Object> params=new HashMap<String,Object>();
                    params.put("partner_id",platPaycode.getPayment_plat_no());
                    params.put("partner_serial_no",transTransreq.getTrans_serial());
                    params.put("partner_trans_date",bindCardRequest.getPartner_trans_date());
                    params.put("partner_trans_time",bindCardRequest.getPartner_trans_time());
                    params.put("client_name",bindCardRequest.getName());
                    params.put("id_kind","0");
                    params.put("id_no",bindCardRequest.getId_code());
                    params.put("card_no",bindCardRequest.getCard_no());
                    params.put("mobile_tel",bindCardRequest.getPre_mobile());
                    params.put("sendercomp_id",platPaycode.getPayment_plat_no());
                    params.put("targetcomp_id","91000");
                    params.put("channelId",platPaycode.getChannel_id());
                    //=====ccb参数=====
                    params.put("partner_terminal_id","");
                    //=========雅酷必填字段============
                    params.put("partner_userid",bindCardRequest.getRemark());
                    //===================================

                    String host = sysParameterService.querySysParameter(bindCardRequest.getMall_no(), URLConfigUtil.EPAY_SERVER_KEY);
                    String url = sysParameterService.querySysParameter(bindCardRequest.getMall_no(), URLConfigUtil.BIND_REAL_NOMSG);
                    params.put("host",host);
                    params.put("url",url);
                    logger.info("【无短验绑卡】=========开始调用E支付绑卡，参数："+params);
                    noMsgBindCardResponse=adapterService.apply4ElementsBindCard(params);
                    logger.info("【无短验绑卡】=========E支付返回"+noMsgBindCardResponse.toString());
                    if(!OrderStatus.SUCCESS.getCode().equals(noMsgBindCardResponse.get("order_status"))){
                        logger.info("【无短验绑卡】=========绑卡失败："+noMsgBindCardResponse.get("remsg"));
                        throw new BusinessException(BusinessMsg.TIEDCARD_FAILED, BusinessMsg.getMsg(BusinessMsg.TIEDCARD_FAILED)+"："+noMsgBindCardResponse.get("remsg"));
                    }
                }
                eaccCardinfo.setStatus(CardStatus.ACTIVE.getCode());
                eaccCardinfo.setBank_no((String)noMsgBindCardResponse.get("bank_no"));
                eaccCardinfo.setRemark(BindCardType.NOMSGBINDCARD.getCode());
                eaccCardinfo.setCard_no(bindCardRequest.getCard_no());
                eaccCardinfo.setCard_type(bindCardRequest.getCard_type());
                eaccCardinfo.setMobile(bindCardRequest.getPre_mobile());
                eaccCardinfo.setAcct_name(bindCardRequest.getName());
            }else{
                //对公用户
                eaccCardinfo.setStatus(CardStatus.UNACTIVE.getCode());
                eaccCardinfo.setRemark(BindCardType.ORGBINGCARD.getCode());
                eaccCardinfo.setAcct_name(bindCardRequest.getAcct_name());
                eaccCardinfo.setCard_no(bindCardRequest.getAcct_no());
                eaccCardinfo.setOrg_no(bindCardRequest.getOrg_no());
                eaccCardinfo.setOpen_branch(bindCardRequest.getOpen_branch());
            }
            eaccCardinfo.setMall_no(bindCardRequest.getMall_no());
            eaccCardinfo.setMallcust(eaccUserinfo.getMallcust());
            eaccCardinfo.setPay_channel(platPaycode.getChannel_id());
            eaccCardinfo.setBindId(transTransreq.getTrans_serial());
            if(oldEaccCardinfo==null){
                logger.info("【无短验绑卡】=========添加绑卡数据");
                userAccountService.addBindCardInfo(eaccCardinfo);
            }
            logger.info("【无短验绑卡】=========绑卡完成\n\n\n");
        }catch (Exception e){
            BaseResponse baseResponse=new BaseResponse();
            if(e instanceof BusinessException){
                baseResponse=((BusinessException)e).getBaseResponse();
                logger.info("【无短验绑卡】=========绑卡失败"+baseResponse.getRemsg());
            }else{
                logger.error("【无短验绑卡】=========未知异常："+e);
                baseResponse.setRecode(BusinessMsg.DATEBASE_EXCEPTION);
                baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.DATEBASE_EXCEPTION));
            }
            transTransreq.setReturn_code(baseResponse.getRecode());
            transTransreq.setReturn_msg(baseResponse.getRemsg());
            transTransreq.setStatus(FlowStatus.FAIL.getCode());
            transReqService.insert(transTransreq);
            throw new BusinessException(baseResponse);
        }

        transTransreq.setReturn_code(BusinessMsg.SUCCESS);
        transTransreq.setReturn_msg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));
        transTransreq.setStatus(FlowStatus.SUCCESS.getCode());
        transReqService.insert(transTransreq);
        return true;
    }

    @Override
    @Transactional
    public BaseResponse confirmOrgRegister(ConfirmOrgRegisterRequest confirmOrgRegisterRequest) throws BusinessException {
        logger.info(String.format("【对公绑卡审核】开始对公绑卡审核|origin_order_no:%s|params:%s",
                confirmOrgRegisterRequest.getOrigin_order_no(),JSON.toJSONString(confirmOrgRegisterRequest)));
        BaseResponse baseResponse=new BaseResponse();
        try{
            TransTransreq oldTransTransreq=transReqService.queryTransTransReqByOrderno(confirmOrgRegisterRequest.getOrigin_order_no());
            if(oldTransTransreq==null){
                logger.info(String.format("【对公绑卡审核】原订单不存在|origin_order_no:%s",  confirmOrgRegisterRequest.getOrigin_order_no()));
                throw new BusinessException(BusinessMsg.ORIGINAL_ORDER_NOEXISTENT,BusinessMsg.getMsg(BusinessMsg.ORIGINAL_ORDER_NOEXISTENT));
            }
            OrgRegisterNotifyData orgRegisterNotifyData=new OrgRegisterNotifyData();
            orgRegisterNotifyData.setOrder_no(oldTransTransreq.getOrder_no());
            orgRegisterNotifyData.setTrans_date(DateUtils.getyyyyMMddDate());
            orgRegisterNotifyData.setTrans_time(DateUtils.getHHmmssTime());
            orgRegisterNotifyData.setOrder_status(OrderStatus.SUCCESS.getCode());
            orgRegisterNotifyData.setPlatcust(confirmOrgRegisterRequest.getPlatcust());
            orgRegisterNotifyData.setPlat_no(oldTransTransreq.getPlat_no());
            String mall_no=accountSearchService.queryMallNoByPlatNo(oldTransTransreq.getPlat_no());
            orgRegisterNotifyData.setMall_no(mall_no);

            List<String> statusList=new ArrayList<>();
            statusList.add(CardStatus.UNACTIVE.getCode());
            statusList.add(CardStatus.ACTIVE.getCode());
            List<EaccCardinfo> eaccCardinfoList=accountSearchService.queryEaccCardInfo(
                    mall_no,confirmOrgRegisterRequest.getPlatcust(),null,statusList);
            if(eaccCardinfoList.size()<=0){
                logger.info(String.format("【对公绑卡审核】用户未绑卡|origin_order_no:%s|platcust:%s",
                        oldTransTransreq.getOrder_no(),confirmOrgRegisterRequest.getPlatcust()));
                throw new BusinessException(BusinessMsg.UNBIND_CARD_ERROR,BusinessMsg.getMsg(BusinessMsg.UNBIND_CARD_ERROR)+":用户未绑卡");
            }

            EaccUserinfo updateEaccuserinfo=accountQueryService.getEUserinfoByExist(mall_no,confirmOrgRegisterRequest.getPlatcust(),null);
            if(updateEaccuserinfo==null){
                logger.info(String.format("【对公绑卡审核】用户信息不存在|origin_order_no:%s|platcust:%s",
                        oldTransTransreq.getOrder_no(),confirmOrgRegisterRequest.getPlatcust()));
                throw new BusinessException(BusinessMsg.INVALID_ACCOUNT,BusinessMsg.getMsg(BusinessMsg.INVALID_ACCOUNT)+":用户信息不存在");
            }
            //激活账户
            EaccCardinfo unActiveCardInfo=null;
            for(EaccCardinfo cardinfo:eaccCardinfoList){
                if(StringUtils.isEmpty(confirmOrgRegisterRequest.getCard_no())){
                    if(cardinfo.getBindId().equals(oldTransTransreq.getTrans_serial())){
                        unActiveCardInfo=cardinfo;
                    }
                }else{
                    if(cardinfo.getCard_no().equals(confirmOrgRegisterRequest.getCard_no())){
                        unActiveCardInfo=cardinfo;
                    }
                }
            }
            if(unActiveCardInfo==null){
                logger.info(String.format("【对公绑卡审核】用户未绑卡|origin_order_no:%s|platcust:%s",
                        oldTransTransreq.getOrder_no(),confirmOrgRegisterRequest.getPlatcust()));
                throw new BusinessException(BusinessMsg.UNBIND_CARD_ERROR,BusinessMsg.getMsg(BusinessMsg.UNBIND_CARD_ERROR)+":用户未绑卡");
            }
            AccountSubjectInfo updateAccountSubjectInfoParams=new AccountSubjectInfo();

            /**
             pwd_flag=0，enabled=1，cardinfo的status=0  绑卡
             pwd_flag=0，enabled=1，cardinfo的status=1  激活
             pwd_flag=1，enabled=1，cardinfo的status=0  绑卡
             pwd_flag=1，enabled=2，cardinfo的status=0  开户
             */
            int transFlag=-1;//0：激活 1：开户 2：绑卡

            if(AcctStatus.ACTIVE.getCode().equals(updateEaccuserinfo.getEnabled())){
                //enabled=1
                if(Constants.YES.equals(updateEaccuserinfo.getPwd_flg())){
                    //pwd_flag=1
                    transFlag=2;
                }else if(Constants.NO.equals(updateEaccuserinfo.getPwd_flg())){
                    //pwd_flag=0;
                    if(AcctStatus.ACTIVE.getCode().equals(unActiveCardInfo.getStatus())){
                        //status=1;
                        transFlag=0;
                    }else if(AcctStatus.UNACTIVE.getCode().equals(unActiveCardInfo.getStatus())){
                        //statis=0;
                        transFlag=2;
                    }
                }
            }else if(AcctStatus.FORZEN.getCode().equals(updateEaccuserinfo.getEnabled())){
                //enabled=2;
                if(Constants.YES.equals(updateEaccuserinfo.getPwd_flg()) && AcctStatus.UNACTIVE.getCode().equals(unActiveCardInfo.getStatus())){
                    transFlag=1;
                }
            }

            //判断审核状态
            if(FlowStatus.SUCCESS.getCode().equals(confirmOrgRegisterRequest.getConfirm_status())){
                logger.info(String.format("【对公绑卡审核】审核状态为通过|origin_order_no:%s",
                        confirmOrgRegisterRequest.getOrigin_order_no()));

                switch (transFlag){
                    case 0:{
                        //激活
                        logger.info("【对公绑卡审核】激活操作|order_no:{}",confirmOrgRegisterRequest.getOrigin_order_no());
                        Map<String,Object> sendParams = new HashMap<String, Object>();
                        sendParams.put("mall_no",mall_no);
                        sendParams.put("mer_no",oldTransTransreq.getPlat_no());
                        sendParams.put("id_kind",updateEaccuserinfo.getId_type());
                        sendParams.put("id_no",updateEaccuserinfo.getId_code());
                        sendParams.put("tran_flag",TranFlag.SET.getCode());
                        sendParams.put("plat_cust",updateEaccuserinfo.getMallcust());
                        //四要素信息
                        sendParams.put("card_no",unActiveCardInfo.getCard_no());
                        sendParams.put("mobile_tel",unActiveCardInfo.getMobile());

                        sendParams.put("random_key",confirmOrgRegisterRequest.getRandom_key());
                        sendParams.put("password",confirmOrgRegisterRequest.getPassword());
                        sendParams.put("host",sysParameterService.querySysParameter(mall_no,URLConfigUtil.EPAY_SERVER_KEY));
                        sendParams.put("url",sysParameterService.querySysParameter(mall_no,URLConfigUtil.ICIS_PASSWORDMODIFY));
                        logger.info("【对公绑卡审核，交易密码设置】==========请求第三方支付参数："+JSON.toJSON(sendParams));
                        Map<String,Object> resultMap = adapterService.setOrResetPassword(sendParams);
                        logger.info("【对公绑卡审核，交易密码设置】==========第三方支付返回结果："+JSON.toJSON(resultMap));
                        if(null == resultMap || null == resultMap.get("order_status") || ".".equals(resultMap.get("order_status"))){
                            throw new BusinessException(BusinessMsg.CALL_REMOTE_ERROR,BusinessMsg.getMsg(BusinessMsg.CALL_REMOTE_ERROR)+"第三方支付返回结果解析失败");
                        }
                        if(!OrderStatus.SUCCESS.getCode().equals(resultMap.get("order_status"))) {
                            baseResponse.setRecode(BusinessMsg.FAIL);
                            baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.FAIL));
                            oldTransTransreq.setStatus(FlowStatus.FAIL.getCode());
                            oldTransTransreq.setReturn_code(resultMap.get("recode").toString());
                            oldTransTransreq.setReturn_msg(resultMap.get("remsg").toString());
                            throw new BusinessException(MapUtils.getString(resultMap,"recode",
                                    BusinessMsg.SET_PASSWORD_ERROR),MapUtils.getString(resultMap,"remsg",BusinessMsg.getMsg(BusinessMsg.SET_PASSWORD_ERROR)));
                        }
                        updateEaccuserinfo.setPwd_flg(Constants.YES);
                        break;
                    }
                    case 1:{
                        //开户
                        logger.info("【对公绑卡审核】开户操作|order_no:{}",confirmOrgRegisterRequest.getOrigin_order_no());
                        Map<String,Object> params=new HashMap<>();
                        params.put("host",sysParameterService.querySysParameter(mall_no, URLConfigUtil.EPAY_SERVER_KEY));
                        params.put("url",sysParameterService.querySysParameter(mall_no, URLConfigUtil.ICIS_CUSTOMERUNFREEZE));
                        params.put("mall_no",mall_no);
                        params.put("mer_no",oldTransTransreq.getPlat_no());
                        params.put("plat_cust",confirmOrgRegisterRequest.getPlatcust());
                        params.put("id_kind","2");
                        if(CusType.PERSONAL.getCode().equals(updateEaccuserinfo.getCus_type())){
                            logger.info("【境外用户激活】");
                            params.put("id_kind", updateEaccuserinfo.getId_code());
                            if("01".equals(updateEaccuserinfo.getId_type())){
                                logger.info("【境外用户激活】国外护照");
                                params.put("id_kind", "1");
                            }
                        }
                        if("BOB".equals(deployEnvironment)) {
                            params.put("id_no", updateEaccuserinfo.getId_code());
                        }
                        //建行差异字段
                        if("CCB".equals(deployEnvironment)) {
                            params.put("id_no", StringUtils.isBlank(updateEaccuserinfo.getBank_license()) ?
                                    updateEaccuserinfo.getBusiness_license() : updateEaccuserinfo.getBank_license());
                            params.put("client_name", updateEaccuserinfo.getOrg_name());
                        }
                        logger.info("【对公绑卡审核，账户解冻】order_no:{}|解冻请求参数：{}",
                                confirmOrgRegisterRequest.getOrigin_order_no(),JSON.toJSONString(params));
                        Map<String,Object> responseParams = adapterService.isicCustomerUnfreeze(params);
                        logger.info("【对公绑卡审核，账户解冻】order_no:{}|解冻返回参数：{}",
                                confirmOrgRegisterRequest.getOrigin_order_no(),JSON.toJSONString(responseParams));
                        if(!FlowStatus.SUCCESS.getCode().equals(responseParams.get("order_status"))){
                            throw new BusinessException(String.valueOf(responseParams.get("recode")),String.valueOf(responseParams.get("remsg")));
                        }
                        List<EaccUserauth> eaccUserauthList=accountSearchService.queryEaccUserAuth(
                                confirmOrgRegisterRequest.getPlatcust(),oldTransTransreq.getPlat_no(),
                                mall_no,AuthStatus.ALREADY_AUTH.getCode(),AuthStatus.NO_AUTH.getCode());
                        for(EaccUserauth eaccUserauth:eaccUserauthList){
                            if(AuthStatus.NO_AUTH.getCode().equals(eaccUserauth.getAuthed_status())){
                                eaccUserauth.setAuthed_status(AuthStatus.ALREADY_AUTH.getCode());
                            }
                        }
                        userAccountExtService.updateEaccUserAuth(eaccUserauthList);
                        break;
                    }
                    case 2:{
                        //绑卡
                        logger.info("【对公绑卡审核】绑卡操作|order_no:{}",confirmOrgRegisterRequest.getOrigin_order_no());
                        PlatPaycode platPaycode=userAccountService.queryPayCodeByChannel(oldTransTransreq.getPlat_no(),unActiveCardInfo.getPay_channel());
                        Map<String,Object> params=new HashMap<String,Object>(17);
                        params.put("partner_id", platPaycode.getPayment_plat_no());
                        params.put("partner_serial_no", oldTransTransreq.getTrans_serial());
                        params.put("partner_trans_date", oldTransTransreq.getTrans_date());
                        params.put("partner_trans_time", oldTransTransreq.getTrans_time());
                        params.put("client_name", updateEaccuserinfo.getName());
                        params.put("id_kind", "2");
                        params.put("id_no",  updateEaccuserinfo.getBank_license() == null ? updateEaccuserinfo.getBusiness_license() : updateEaccuserinfo.getBank_license());
                        params.put("mobile_tel", updateEaccuserinfo.getMobile());
                        params.put("card_no", unActiveCardInfo.getCard_no());
                        if("CCB".equals(deployEnvironment)) {
                            params.put("func_code", "2");
                        }
                        if("BOB".equals(deployEnvironment)){
                            params.put("func_code","3");
                        }
                        params.put("sendercomp_id", platPaycode.getPayment_plat_no());
                        params.put("targetcomp_id", "91000");
                        params.put("pay_bankacct_type", "0");
                        params.put("mall_no", mall_no);
                        params.put("mer_no", mall_no);
                        params.put("plat_cust",  updateEaccuserinfo.getMallcust());
                        params.put("open_account", "0");
                        params.put("is_bankcheck", "1");
                        //==========建行差异字段==========
                        params.put("partner_terminal_id", "宝付必填");
                        params.put("cus_type","2");
                        params.put("channelId", platPaycode.getChannel_id());
                        //=======================================
                        //=========雅酷必填字段============
                        params.put("partner_userid", oldTransTransreq.getTrans_serial());
                        //新接口
                        params.put("host", sysParameterService.querySysParameter(mall_no, URLConfigUtil.EPAY_SERVER_KEY));
                        params.put("url", sysParameterService.querySysParameter(mall_no, URLConfigUtil.BAND_ACCOUNT_VERIFY_KEY));

                        logger.info("【对公绑卡审核】order_no:{}|绑卡请求参数：{}",
                                confirmOrgRegisterRequest.getOrigin_order_no(),JSON.toJSONString(params));
                        Map<String,Object> msgBindCardResponse=adapterService.isicBindCard(params);
                        logger.info("【对公绑卡审核】order_no:{}|绑卡返回参数：{}",
                                confirmOrgRegisterRequest.getOrigin_order_no(),JSON.toJSON(msgBindCardResponse));
                        if (!OrderStatus.SUCCESS.getCode().equals(msgBindCardResponse.get("order_status"))) {
                            logger.info(String.format("【对公绑卡审核】身份校验未通过|origin_order_no:%s|error_msg:%s",
                                    oldTransTransreq.getOrigin_order_no(),msgBindCardResponse.get("remsg")));
                            String recode = (String) msgBindCardResponse.get("recode");
                            if ("001003".equals(recode)) {
                                baseResponse.setRecode(BusinessMsg.VERIFICATION_ERROR);
                                baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.VERIFICATION_ERROR));
                                throw new BusinessException(baseResponse);
                            } else if ("001002".equals(recode)) {
                                baseResponse.setRecode(BusinessMsg.VERIFICATION_OUT_OF_DATE);
                                baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.VERIFICATION_OUT_OF_DATE));
                                throw new BusinessException(baseResponse);
                            } else if ("020513".equals(recode)) {
                                baseResponse.setRecode(BusinessMsg.VERIFICATION_ERROR_TOO_MUCH);
                                baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.VERIFICATION_ERROR_TOO_MUCH));
                                throw new BusinessException(baseResponse);
                            } else if (BusinessMsg.CFCA_AUTH_FAILED.equals(recode)) {
                                throw new BusinessException(BusinessMsg.CFCA_AUTH_FAILED, BusinessMsg.getMsg(BusinessMsg.CFCA_AUTH_FAILED));
                            } else {
                                baseResponse.setRecode(BusinessMsg.FOUR_VERIFY_FAILED);
                                baseResponse.setRemsg("对公绑卡：" + msgBindCardResponse.get("remsg"));
                                throw new BusinessException(baseResponse);
                            }
                        }
                        updateEaccuserinfo.setDefault_mobile(updateEaccuserinfo.getMobile());
                        updateEaccuserinfo.setDefault_card_no(unActiveCardInfo.getCard_no());
                        break;
                    }
                    default:{
                        logger.info("【对公绑卡审核】开户绑卡数据异常，无法判断操作类型|order_no:{}|platcust:{}",
                                confirmOrgRegisterRequest.getOrigin_order_no(),confirmOrgRegisterRequest.getPlatcust());
                        throw new BusinessException(BusinessMsg.PARAMETER_ERROR,"开户绑卡数据异常，无法判断操作类型");
                    }
                }
                updateAccountSubjectInfoParams.setEnabled(AcctStatus.ACTIVE.getCode());
                unActiveCardInfo.setEnabled(AcctStatus.ACTIVE.getCode());
                unActiveCardInfo.setStatus(AcctStatus.ACTIVE.getCode());
                updateEaccuserinfo.setEnabled(AcctStatus.ACTIVE.getCode());

                orgRegisterNotifyData.setRecode(BusinessMsg.SUCCESS);
                orgRegisterNotifyData.setRemsg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));
            }else{
                logger.info(String.format("【对公绑卡审核】审核状态为不通过|origin_order_no:%s|error_info:%s",
                        confirmOrgRegisterRequest.getOrigin_order_no(),confirmOrgRegisterRequest.getConfirm_note()));

                updateAccountSubjectInfoParams.setEnabled(AcctStatus.UNACTIVE.getCode());
                if(transFlag==1){
                    updateEaccuserinfo.setEnabled(AcctStatus.UNACTIVE.getCode());
                }

                unActiveCardInfo.setEnabled(AcctStatus.UNACTIVE.getCode());
                unActiveCardInfo.setStatus(AcctStatus.FORZEN.getCode());

                orgRegisterNotifyData.setOrder_status(OrderStatus.FAIL.getCode());
                orgRegisterNotifyData.setRecode(BusinessMsg.FOUR_VERIFY_FAILED);
                orgRegisterNotifyData.setRemsg(BusinessMsg.getMsg(BusinessMsg.FOUR_VERIFY_FAILED));
                orgRegisterNotifyData.setError_no(BusinessMsg.FOUR_VERIFY_FAILED);
                orgRegisterNotifyData.setError_info(confirmOrgRegisterRequest.getOrigin_order_no());

            }
            if(transFlag!=2){
                accountOprationService.updateAccountSubjectInfo(updateAccountSubjectInfoParams,
                        confirmOrgRegisterRequest.getPlatcust(),oldTransTransreq.getPlat_no(),null,null);
            }
            userAccountService.updateEaccUserInfo(updateEaccuserinfo);
            userAccountService.updateCardInfo(unActiveCardInfo);

            baseResponse.setRecode(BusinessMsg.SUCCESS);
            baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));
            //发送异步通知
            NotifyData notifyData=new NotifyData();
            notifyData.setNotifyUrl(oldTransTransreq.getNotify_url());
            notifyData.setMall_no(mall_no);
            notifyData.setNotifyContent(JSON.toJSONString(orgRegisterNotifyData));
            notifyService.addNotify(notifyData);

            oldTransTransreq.setStatus(orgRegisterNotifyData.getOrder_status());
            oldTransTransreq.setReturn_code(orgRegisterNotifyData.getRecode());
            oldTransTransreq.setReturn_msg(orgRegisterNotifyData.getRemsg());
            transReqService.insert(oldTransTransreq);
        }catch (Exception e){
            logger.error("【对公绑卡审核】异常：",e);
            if(e instanceof BusinessException){
                baseResponse=((BusinessException) e).getBaseResponse();
            }else{
                baseResponse.setRecode(BusinessMsg.RUNTIME_EXCEPTION);
                baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.RUNTIME_EXCEPTION));
            }
        }
        return baseResponse;
    }

    @Override
    public BaseResponse checkCard(CheckCardRequest checkCardRequest) throws BusinessException {
        logger.info(String.format("【绑卡核验】开始绑卡核验|order_no:%s|params:%s",
                checkCardRequest.getOrder_no(),JSON.toJSONString(checkCardRequest)));
        BaseResponse baseResponse=new BaseResponse();
        try{
            //如果platcust为空，不校验platcust
            List<String> statusList=new ArrayList<>();
            statusList.add(CardStatus.ACTIVE.getCode());
            statusList.add(CardStatus.UNACTIVE.getCode());
            List<EaccCardinfo> eaccCardinfoList=accountSearchService.queryEaccCardInfo(
                    checkCardRequest.getMall_no(),checkCardRequest.getPlatcust(),checkCardRequest.getCard_no(),checkCardRequest.getPre_mobile(),statusList);
            if(eaccCardinfoList.size()>0){
                EaccCardinfo eaccCardinfo=eaccCardinfoList.get(0);
                EaccUserinfo eaccUserinfo=accountSearchService.queryEaccUserInfoByEaccAccountInfo(
                        checkCardRequest.getMall_no(),checkCardRequest.getMer_no(),eaccCardinfo.getMallcust());
                if(eaccUserinfo==null){
                    logger.info(String.format("【绑卡核验】用户信息不存在|order_no:%s|card_no:%s|mobile:%s|platcust:%s",
                            checkCardRequest.getOrder_no(),checkCardRequest.getCard_no(),
                            checkCardRequest.getPre_mobile(),eaccCardinfo.getMallcust()));
                    baseResponse.setRecode(BusinessMsg.INVALID_ACCOUNT);
                    baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.INVALID_ACCOUNT)+":用户信息不存在");
                    return baseResponse;
                }else{

                    if(StringUtils.isBlank(checkCardRequest.getClient_property())){
                        checkCardRequest.setClient_property(CusType.PERSONAL.getCode());
                    }

                    if(!CusType.PERSONAL.getCode().equals(checkCardRequest.getClient_property())
                            && !CusType.COMPANY.getCode().equals(checkCardRequest.getClient_property()) ){
                        logger.info(String.format("【绑卡核验】公私标示有误|order_no:%s|client_property:%s",
                                checkCardRequest.getOrder_no(),checkCardRequest.getClient_property()));
                        baseResponse.setRecode(BusinessMsg.FOUR_VERIFY_FAILED);
                        baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.FOUR_VERIFY_FAILED)+":公私标示有误");
                        return baseResponse;
                    }

                    //校验名称
                    String name;
                    if(CusType.PERSONAL.getCode().equals(eaccUserinfo.getCus_type())){
                        //个人
                        name = eaccUserinfo.getName();
                    }else {
                        name = eaccUserinfo.getOrg_name();
                    }
                    if(!checkCardRequest.getName().equals(name)){
                        logger.info(String.format("【绑卡核验】名称不一致|order_no:%s|platcust:%s|db_name:%s|req_name:%s",
                                checkCardRequest.getOrder_no(),eaccCardinfo.getMallcust(),eaccUserinfo.getName(),checkCardRequest.getName()));
                        baseResponse.setRecode(BusinessMsg.FOUR_VERIFY_FAILED);
                        baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.FOUR_VERIFY_FAILED)+":名称不一致");
                        return baseResponse;
                    }
                    //校验证件号
                    if(CusType.PERSONAL.getCode().equals(checkCardRequest.getClient_property())){
                        //个人用户判断身份证号
                        if(!eaccUserinfo.getId_code().equals(checkCardRequest.getPid())){
                            logger.info(String.format("【绑卡核验】个人证件号不一致|order_no:%s|platcust:%s|db_pid:%s|req_pid:%s",
                                    checkCardRequest.getOrder_no(),eaccCardinfo.getMallcust(),
                                    eaccUserinfo.getId_code(),checkCardRequest.getPid()));
                            baseResponse.setRecode(BusinessMsg.FOUR_VERIFY_FAILED);
                            baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.FOUR_VERIFY_FAILED)+":证件号不一致");
                            return baseResponse;
                        }
                    }else if(CusType.COMPANY.getCode().equals(checkCardRequest.getClient_property())){
                        //对公用户判断企业社会信用码
                        if(!checkCardRequest.getPid().equals(eaccUserinfo.getBusiness_license()) &&
                                !checkCardRequest.getPid().equals(eaccUserinfo.getBank_license())){
                            logger.info(String.format("【绑卡核验】企业证件号不一致|order_no:%s|platcust:%s|db_pid:%s|req_pid:%s",
                                    checkCardRequest.getOrder_no(),eaccCardinfo.getMallcust(),
                                    eaccUserinfo.getBusiness_license()+"/"+eaccUserinfo.getBank_license(),checkCardRequest.getPid()));
                            baseResponse.setRecode(BusinessMsg.FOUR_VERIFY_FAILED);
                            baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.FOUR_VERIFY_FAILED)+":证件号不一致");
                            return baseResponse;
                        }
                    }
                }
                //校验预留手机号
                if(StringUtils.isNotBlank(checkCardRequest.getPre_mobile()) && !checkCardRequest.getPre_mobile().equals(eaccCardinfo.getMobile())){
                    //卡号与手机号不一致
                    logger.info(String.format("【绑卡核验】手机号不一致|order_no:%s|card_no:%s|db_mobile:%s|req_mobile:%s",
                            checkCardRequest.getOrder_no(),checkCardRequest.getCard_no(),eaccCardinfo.getMobile(),checkCardRequest.getPre_mobile()));
                    baseResponse.setRecode(BusinessMsg.FOUR_VERIFY_FAILED);
                    baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.FOUR_VERIFY_FAILED)+":手机号不一致");
                    return baseResponse;
                }
                //校验platcust
                if(StringUtils.isEmpty(checkCardRequest.getPlatcust())){
                    if(eaccCardinfo.getMallcust().equals(checkCardRequest.getPlatcust())){
                        //验证通过
                        logger.info(String.format("【绑卡核验】验证通过|order_no:%s|card_no:%s|mobile:%s|platcust:%s",
                                checkCardRequest.getOrder_no(),checkCardRequest.getCard_no(),
                                checkCardRequest.getPre_mobile(),checkCardRequest.getPlatcust()));
                        baseResponse.setRecode(BusinessMsg.SUCCESS);
                        baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));
                    }else{
                        //用户账户与绑卡信息不一致
                        logger.info(String.format("【绑卡核验】用户账户与绑卡信息不一致|order_no:%s|card_no:%s|" +
                                        "mobile:%s|db_platcust:%s|req_platcust:%s",
                                checkCardRequest.getOrder_no(),checkCardRequest.getCard_no(),
                                checkCardRequest.getPre_mobile(),eaccCardinfo.getMallcust(),checkCardRequest.getPlatcust()));
                        baseResponse.setRecode(BusinessMsg.FOUR_VERIFY_FAILED);
                        baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.FOUR_VERIFY_FAILED)+":用户账户与绑卡信息不一致");
                    }
                }else{
                    //验证通过
                    logger.info(String.format("【绑卡核验】验证通过|order_no:%s|card_no:%s|mobile:%s",
                            checkCardRequest.getOrder_no(),checkCardRequest.getCard_no(),checkCardRequest.getPre_mobile()));
                    baseResponse.setRecode(BusinessMsg.SUCCESS);
                    baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));
                }
            }else{
                //在存管未绑卡
                logger.info(String.format("【绑卡核验】在存管未绑卡|order_no:%s|card_no:%s",
                        checkCardRequest.getOrder_no(),checkCardRequest.getCard_no()));
                baseResponse.setRecode(BusinessMsg.UNBIND_CARD_ERROR);
                baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.UNBIND_CARD_ERROR)+":未绑卡或用户信息不存在");
            }
        }catch (Exception e){
            logger.info(String.format("【绑卡核验】异常|order_no:%s|error:",
                    checkCardRequest.getOrder_no()),e);
            baseResponse.setRecode(BusinessMsg.FOUR_VERIFY_FAILED);
            baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.FOUR_VERIFY_FAILED));
        }
        return baseResponse;
    }

    @Override
    public boolean orgBindCard(BindCardRequest bindCardRequest) throws BusinessException {
        TransTransreq transTransreq=transReqService.getTransTransReq(bindCardRequest.clone(), TransConsts.CARD_BIND_CODE,
                TransConsts.CARD_BIND_NAME,false);
        //异步通知地址存在流水中
        transTransreq.setNotify_url(bindCardRequest.getNotify_url());
        transTransreq.setPlatcust(bindCardRequest.getPlatcust());
        transReqService.insert(transTransreq);
        //bindCardRequest.setPay_code("099");
        try{
            //检查卡类型
            logger.info("【对公绑卡】=========检查卡类型");
            if(CardType.CREDIT.getCode().equals(bindCardRequest.getCard_type())){
                logger.info("【对公绑卡】=========暂不支持信用卡绑卡");
                throw new BusinessException(BusinessMsg.TIEDCARD_FAILED, BusinessMsg.getMsg(
                        BusinessMsg.TIEDCARD_FAILED)+"：暂不支持信用卡绑卡");
            }
            if(StringUtils.isBlank(bindCardRequest.getOrg_no())||StringUtils.isBlank(bindCardRequest.getAcct_name())
                    ||StringUtils.isBlank(bindCardRequest.getAcct_no())||StringUtils.isBlank(bindCardRequest.getOpen_branch())){
                throw new BusinessException(BusinessMsg.PARAMETER_LACK,BusinessMsg.getMsg(BusinessMsg.PARAMETER_LACK));
            }
            logger.info("【对公绑卡】=========检查该对公用户是否存在");
            EaccUserinfo eaccUserinfo=userAccountService.queryEaccUserInfo(bindCardRequest.getMall_no(),
                    bindCardRequest.getPlatcust(),null,null);
            if(eaccUserinfo==null){
                logger.info("【对公绑卡】=========用户不存在，platcust："+bindCardRequest.getPlatcust());
                throw new BusinessException(BusinessMsg.INVALID_ACCOUNT, BusinessMsg.getMsg(BusinessMsg.INVALID_ACCOUNT)+"：该用户未注册");
            }
            EaccCardinfo oldEaccCardinfo =null;
            logger.info("【对公绑卡】=========检查该对公用户是否已绑卡");
            oldEaccCardinfo = userAccountService.queryCompanyCard(bindCardRequest.getMall_no(), bindCardRequest.getPlatcust());
            if (oldEaccCardinfo != null) {
                String card_no = oldEaccCardinfo.getCard_no();
                if ("1".equals(oldEaccCardinfo.getStatus())){
                    logger.info("【对公绑卡】=========该对公用户已绑卡，卡号为：" + card_no);
                    throw new BusinessException(BusinessMsg.REPEAT_BINDING, BusinessMsg.getMsg(
                            BusinessMsg.REPEAT_BINDING)+"，已绑卡，请勿重复绑卡");
                }else {
                    logger.info("【对公绑卡】=========该对公用户已绑卡，卡号为：" + card_no);
                    throw new BusinessException(BusinessMsg.REPEAT_BINDING, BusinessMsg.getMsg(
                            BusinessMsg.REPEAT_BINDING)+"，绑卡审核中，请勿重复提交");
                }
            }
            if(StringUtils.isBlank(bindCardRequest.getPay_code())){
                bindCardRequest.setPay_code("099");
            }
            logger.info("【对公绑卡】=========查询paycode："+bindCardRequest.getPay_code());
            PlatPaycode platPaycode=userAccountService.queryPlatPaycode(
                    bindCardRequest.getMer_no(),bindCardRequest.getPay_code());
            if(platPaycode==null){
                logger.info("【对公绑卡】=========找不到渠道信息");
                throw new BusinessException(BusinessMsg.NO_CHANNEL_INFORMATION,
                        BusinessMsg.getMsg(BusinessMsg.getMsg(BusinessMsg.NO_CHANNEL_INFORMATION)));
            }
            EaccCardinfo eaccCardinfo=new EaccCardinfo();
            //对公用户
            eaccCardinfo.setStatus(CardStatus.UNACTIVE.getCode());
            eaccCardinfo.setRemark(BindCardType.ORGBINGCARD.getCode());
            eaccCardinfo.setAcct_name(bindCardRequest.getAcct_name());
            eaccCardinfo.setCard_no(bindCardRequest.getAcct_no());
            eaccCardinfo.setOrg_no(bindCardRequest.getOrg_no());
            eaccCardinfo.setOpen_branch(bindCardRequest.getOpen_branch());
            eaccCardinfo.setMall_no(bindCardRequest.getMall_no());
            eaccCardinfo.setMallcust(bindCardRequest.getPlatcust());
            eaccCardinfo.setPay_channel(platPaycode.getChannel_id());
            eaccCardinfo.setBindId(transTransreq.getTrans_serial());
            if(oldEaccCardinfo==null){
                logger.info("【对公绑卡】=========添加绑卡数据");
                userAccountService.addBindCardInfo(eaccCardinfo);
            }
            logger.info("【对公绑卡】=========绑卡完成\n\n\n");
        }catch (Exception e){
            BaseResponse baseResponse=new BaseResponse();
            if(e instanceof BusinessException){
                baseResponse=((BusinessException)e).getBaseResponse();
                logger.info("【对公绑卡】=========绑卡失败"+baseResponse.getRemsg());
            }else{
                logger.error("【对公绑卡】=========未知异常："+e);
                baseResponse.setRecode(BusinessMsg.DATEBASE_EXCEPTION);
                baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.DATEBASE_EXCEPTION));
            }
            transTransreq.setReturn_code(baseResponse.getRecode());
            transTransreq.setReturn_msg(baseResponse.getRemsg());
            transTransreq.setStatus(FlowStatus.FAIL.getCode());
            transReqService.insert(transTransreq);
            throw new BusinessException(baseResponse);
        }

        transTransreq.setReturn_code(BusinessMsg.SUCCESS);
        transTransreq.setReturn_msg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));
        transTransreq.setStatus(FlowStatus.INPROCESS.getCode());
        transReqService.insert(transTransreq);

        //给平台发送异步通知
        RegisterNotifyData registerNotifyData=new RegisterNotifyData();
        registerNotifyData.setRemsg(BusinessMsg.getMsg(BusinessMsg.ACCEPTANCE_SUCCESS));
        registerNotifyData.setRecode(BusinessMsg.ACCEPTANCE_SUCCESS);
        registerNotifyData.setOrder_no(bindCardRequest.getOrder_no());
        registerNotifyData.setOrder_status(OrderStatus.PROCESSING.getCode());
        registerNotifyData.setOrder_info(OrderStatus.PROCESSING.getMsg());
        registerNotifyData.setTrans_date(DateUtils.getyyyyMMddDate());
        registerNotifyData.setTrans_time(DateUtils.getHHmmssTime());
        registerNotifyData.setPlatcust(bindCardRequest.getPlatcust());
        registerNotifyData.setPlat_no(bindCardRequest.getMer_no());
        NotifyData notifyData=new NotifyData();
        notifyData.setMall_no(bindCardRequest.getMall_no());
        notifyData.setNotifyUrl(bindCardRequest.getNotify_url());
        notifyData.setNotifyContent(JSON.toJSONString(registerNotifyData));
        notifyService.addNotify(notifyData);
        return true;
    }

    private String applyOrgDoRegister(ApplyOrgRegisterRequest applyRequest, CompanyRegisterRequest registerRequest, TransTransreq oldTransTransreq) throws BusinessException {
        logger.info(String.format("【对公开户申请】开始开户逻辑|origin_order_no:%s|params:%s|",
                applyRequest.getOrigin_order_no(),JSON.toJSONString(registerRequest)));

        //同一个用户在一个平台只可以注册一次，在一个集团可以注册多次，但只有一个账户
        EaccUserinfo oldEaccUserInfo=userAccountService.checkMallUserInfo(registerRequest.getMall_no(),
                registerRequest.getOrg_name(), registerRequest.getMer_no(),false);
        // 查询相应绑卡通道
        PlatPaycode platPaycode = userAccountService.queryPlatPaycode(registerRequest.getMer_no(), "099");
        if(platPaycode==null){
            logger.info(String.format("【对公开户申请】pay_code不合法|origin_order_no:%s|paycode:099",
                    applyRequest.getOrigin_order_no()));
            BaseResponse baseResponse=new BaseResponse();
            baseResponse.setRecode(BusinessMsg.NO_CHANNEL_INFORMATION);
            baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.NO_CHANNEL_INFORMATION)+"：请检查pay_code是否合法");
            throw new BusinessException(baseResponse);
        }
        //如果已经在平台注册
        if(oldEaccUserInfo!=null){
            logger.info("【对公开户申请】用户已在平台注册,判断已绑卡号和当前卡号是否一致，如果一致，直接返回平台用户号！");
            List<String> statusList=new ArrayList<>();
            statusList.add(CardStatus.UNACTIVE.getCode());
            statusList.add(CardStatus.ACTIVE.getCode());
            List<EaccCardinfo> cardinfoList=accountSearchService.queryEaccCardInfo(registerRequest.getMall_no(),oldEaccUserInfo.getMallcust(),null,statusList);
            if(cardinfoList.size()>0){
                EaccCardinfo oldEaccCardinfo=cardinfoList.get(0);
                if(registerRequest.getCard_no().equals(oldEaccCardinfo.getCard_no())){
                    if(CardStatus.UNACTIVE.getCode().equals(oldEaccCardinfo.getStatus())){
                        //如果卡未激活，为重复提交，直接幂等返回
//                        return oldEaccUserInfo.getMallcust();
                        throw new BusinessException(BusinessMsg.REGISTERED,"开户审核中，请勿重复提交！");
                    }else{
                        if(Constants.YES.contains(oldEaccUserInfo.getPwd_flg())){
                            throw new BusinessException(BusinessMsg.REGISTERED,"已开户，请勿重复开户！");
                        }
                    }
                }else{
                    logger.info("【对公开户申请】用户已在平台注册,已绑卡与欲绑卡不一致！卡号："+oldEaccCardinfo.getCard_no());
                    throw new BusinessException(BusinessMsg.TIEDCARD_FAILED,
                            BusinessMsg.getMsg(BusinessMsg.TIEDCARD_FAILED)+"用户已在平台注册,已绑卡与欲绑卡不一致！卡号："+oldEaccCardinfo.getCard_no());
                }
            }
        }

        String mallcust=SeqUtil.getSeqNum();
        if(oldEaccUserInfo!=null){
            mallcust=oldEaccUserInfo.getMallcust();
        }
        if(StringUtils.isBlank(registerRequest.getPlatcust())){
            Map<String,Object> accountVerifyParams=new HashMap<String,Object>(26);
            accountVerifyParams.put("partner_id",platPaycode.getPayment_plat_no());
            accountVerifyParams.put("partner_serial_no",oldTransTransreq.getTrans_serial());
            accountVerifyParams.put("partner_trans_date",registerRequest.getPartner_trans_date()!=null?registerRequest.getPartner_trans_date():applyRequest.getPartner_trans_date());
            accountVerifyParams.put("partner_trans_time",registerRequest.getPartner_trans_time()!=null?registerRequest.getPartner_trans_time():applyRequest.getPartner_trans_time());
            accountVerifyParams.put("client_name",registerRequest.getOrg_name()!=null?registerRequest.getOrg_name():applyRequest.getOrg_name());
            accountVerifyParams.put("id_kind","2");
            String id_no=StringUtils.isBlank(registerRequest.getBank_license())?registerRequest.getBusiness_license():registerRequest.getBank_license();
            logger.info(String.format("【对公开户申请】bank_license:%s|business_license:%s|id_no:%s",
                    registerRequest.getBank_license(),registerRequest.getBusiness_license(),id_no));
            accountVerifyParams.put("id_no",id_no);
            accountVerifyParams.put("card_no",registerRequest.getCard_no()!=null?registerRequest.getCard_no():applyRequest.getCard_no());
            accountVerifyParams.put("mobile_tel",registerRequest.getMobile());
            //accountVerifyParams.put("verify_info",confirmRequest.getIdentifying_code());
            if("CCB".equals(deployEnvironment)) {
                accountVerifyParams.put("func_code", "2");
            }
            if("BOB".equals(deployEnvironment)){
                accountVerifyParams.put("func_code","3");
            }
            accountVerifyParams.put("mall_no",applyRequest.getMall_no()!=null?applyRequest.getMall_no():registerRequest.getMall_no());
            accountVerifyParams.put("mer_no",applyRequest.getMer_no()!=null?applyRequest.getMer_no():registerRequest.getMer_no());
            accountVerifyParams.put("plat_cust",mallcust);
            accountVerifyParams.put("random_key",applyRequest.getRandom_key());
            accountVerifyParams.put("passwod",applyRequest.getTrans_pwd());
            accountVerifyParams.put("type",applyRequest.getType());
            //accountVerifyParams.put("pay_bankacct_type","0");
            //accountVerifyParams.put("sendercomp_id",platPaycode.getPayment_plat_no());
            accountVerifyParams.put("open_account","1");
            accountVerifyParams.put("is_bankcheck",platPaycode.getIs_bankcheck());
            accountVerifyParams.put("cus_type","2");
            String role_types=registerRequest.getRole().replace("，",",").trim();
            String[] roleArray=role_types.split(",");
            for(String role_type:roleArray){
                if(RoleType.INVESTOR.getCode().equals(role_type)){
                    accountVerifyParams.put("cjr_role","1");
                }else if(RoleType.BORROWER.getCode().equals(role_type)){
                    accountVerifyParams.put("jkr_role","1");
                }else if(RoleType.ADVANCER.getCode().equals(role_type)){
                    accountVerifyParams.put("dzr_role","1");
                }else if(RoleType.GUARANTOR.getCode().equals(role_type)){
                    accountVerifyParams.put("dbr_role","1");
                }
            }
            //======ccb参数=====
            //accountVerifyParams.put("partner_terminal_id","");
            accountVerifyParams.put("channelId",platPaycode.getChannel_id());
            //=====================================================
            //=========雅酷必填字段============
            //accountVerifyParams.put("partner_userid",registerRequest.getRemark());
            //===================================
            //=================支付公司收单的参数==========

            //==============================================
            accountVerifyParams.put("host",sysParameterService.querySysParameter(registerRequest.getMall_no(), URLConfigUtil.EPAY_SERVER_KEY));
            accountVerifyParams.put("url",sysParameterService.querySysParameter(registerRequest.getMall_no(), URLConfigUtil.BAND_ACCOUNT_VERIFY_KEY));
            Map<String,Object> thirdPartyBaseResponse= adapterService.isicBindCard(accountVerifyParams);
            if(!OrderStatus.SUCCESS.getCode().equals(thirdPartyBaseResponse.get("order_status"))){
                logger.info(String.format("【对公开户申请】身份校验未通过|origin_order_no:%s|error_msg:%s",
                        oldTransTransreq.getOrigin_order_no(),thirdPartyBaseResponse.get("remsg")));
                BaseResponse baseResponse=new BaseResponse();
                baseResponse.setRecode(BusinessMsg.FOUR_VERIFY_FAILED);
                baseResponse.setRemsg(String.format(BusinessMsg.getMsg(BusinessMsg.FOUR_VERIFY_FAILED)+"|%s",
                        MapUtils.getString(thirdPartyBaseResponse,"remsg","身份校验未通过")));
                throw new BusinessException(baseResponse);
            }
            doOrgRegister(mallcust,registerRequest,oldEaccUserInfo,oldTransTransreq);
        }else{
            ConfirmOrgRegisterRequest confirmOrgRegisterRequest=new ConfirmOrgRegisterRequest();
            confirmOrgRegisterRequest.setPlatcust(mallcust);
            confirmOrgRegisterRequest.setConfirm_note("对公账户激活");
            confirmOrgRegisterRequest.setConfirm_status(FlowStatus.SUCCESS.getCode());
            confirmOrgRegisterRequest.setOrigin_order_no(oldTransTransreq.getOrder_no());
            confirmOrgRegisterRequest.setRandom_key(applyRequest.getRandom_key());
            confirmOrgRegisterRequest.setPassword(applyRequest.getTrans_pwd());
            confirmOrgRegisterRequest.setCard_no(applyRequest.getCard_no());
            BaseResponse baseResponse=confirmOrgRegister(confirmOrgRegisterRequest);
            if(!BusinessMsg.SUCCESS.equals(baseResponse.getRecode())){
                logger.info("【对公账户激活】激活失败："+baseResponse.getRemsg());
                //激活失败
                throw new BusinessException(baseResponse);
            }
            //激活成功，授权
            addAuthInfo(registerRequest.getAuthed_type(),registerRequest.getAuthed_amount(),
                    registerRequest.getAuthed_limittime(),AuthStatus.ALREADY_AUTH.getCode(),registerRequest.getMall_no(),registerRequest.getMer_no(),mallcust);
            mallcust=null;
        }

        return mallcust;
    }




    private  String applyOrgRegisterOpening(ApplyOrgRegisterOpeningRequest applyRequests,CompanyRegisterRequest registerRequest,TransTransreq transTransreq){
        logger.info(String.format("【境外开户申请】开始开户逻辑|order_no:%s|params:%s|",
                applyRequests.getOrder_no(),JSON.toJSONString(applyRequests)));
        EaccUserinfo oldEaccUserInfo=userAccountService.checkUserInfo(applyRequests.getMall_no(),
                applyRequests.getAbroad_name(),applyRequests.getId_code(), applyRequests.getMer_no(),false);
        PlatPaycode platPaycode = userAccountService.queryPlatPaycode(applyRequests.getMer_no(), "099");
        if(platPaycode==null){
            logger.info(String.format("【境外开户申请】pay_code不合法|order_no:%s|paycode:099",
                    applyRequests.getOrder_no()));
            BaseResponse baseResponse=new BaseResponse();
            baseResponse.setRecode(BusinessMsg.NO_CHANNEL_INFORMATION);
            baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.NO_CHANNEL_INFORMATION)+"：请检查pay_code是否合法");
            throw new BusinessException(baseResponse);
        }
        if(oldEaccUserInfo!=null){
            logger.info("【境外开户申请】用户已在平台注册,判断已绑卡号和当前卡号是否一致，如果一致，直接返回平台用户号！");
            List<String> statusList=new ArrayList<>();
            statusList.add(CardStatus.UNACTIVE.getCode());
            statusList.add(CardStatus.ACTIVE.getCode());
            List<EaccCardinfo> cardinfoList=accountSearchService.queryEaccCardInfo(applyRequests.getMall_no(),oldEaccUserInfo.getMallcust(),applyRequests.getCard_no(),statusList);
            if(cardinfoList.size()>0){
                EaccCardinfo oldEaccCardinfo=cardinfoList.get(0);
                if(applyRequests.getPre_mobile().equals(oldEaccCardinfo.getMobile())){
                    if(CardStatus.UNACTIVE.getCode().equals(oldEaccCardinfo.getStatus())){
                        //如果卡未激活，为重复提交，直接幂等返回
//                        return oldEaccUserInfo.getMallcust();
                        throw new BusinessException(BusinessMsg.REGISTERED,"开户审核中，请勿重复提交！");
                    }else {
                        return oldEaccCardinfo.getMallcust();
                    }
                }else{
                    logger.info("【境外开户申请】用户已在平台注册,手机号不一致！mobile:{}|order_no:{}",oldEaccCardinfo.getMobile(),applyRequests.getOrder_no());
                    throw new BusinessException(BusinessMsg.TIEDCARD_FAILED,
                            BusinessMsg.getMsg(BusinessMsg.TIEDCARD_FAILED)+"用户已在平台注册,手机号不一致！");
                }
            }
        }
        String mallcust=SeqUtil.getSeqNum();
        if(oldEaccUserInfo!=null){
            mallcust=oldEaccUserInfo.getMallcust();
        }
        Map<String,Object> accountVerifyParams=new HashMap<String,Object>(26);
        accountVerifyParams.put("partner_id",platPaycode.getPayment_plat_no());
        accountVerifyParams.put("partner_serial_no",transTransreq.getTrans_serial());
        accountVerifyParams.put("partner_trans_date",applyRequests.getPartner_trans_date()!=null?applyRequests.getPartner_trans_date():applyRequests.getPartner_trans_date());
        accountVerifyParams.put("partner_trans_time",applyRequests.getPartner_trans_time()!=null?applyRequests.getPartner_trans_time():applyRequests.getPartner_trans_time());
        accountVerifyParams.put("client_name",applyRequests.getAbroad_name()!=null?applyRequests.getAbroad_name():applyRequests.getAbroad_name());
        accountVerifyParams.put("id_kind","2");
        String id_no=StringUtils.isBlank(applyRequests.getBank_license())?applyRequests.getBusiness_license():applyRequests.getBank_license();
        logger.info(String.format("【境外开户申请】bank_license:%s|business_license:%s|id_no:%s",
                applyRequests.getBank_license(),applyRequests.getBusiness_license(),id_no));
        accountVerifyParams.put("id_no",applyRequests.getId_code());
        accountVerifyParams.put("card_no",applyRequests.getCard_no()!=null?applyRequests.getCard_no():applyRequests.getCard_no());
        accountVerifyParams.put("mobile_tel",applyRequests.getMobile());
        //accountVerifyParams.put("verify_info",confirmRequest.getIdentifying_code());
        if("CCB".equals(deployEnvironment)) {
            accountVerifyParams.put("func_code", "2");
        }
        if("BOB".equals(deployEnvironment)){
            accountVerifyParams.put("func_code","3");
        }
        accountVerifyParams.put("mall_no",applyRequests.getMall_no()!=null?applyRequests.getMall_no():applyRequests.getMall_no());
        accountVerifyParams.put("mer_no",applyRequests.getMer_no()!=null?applyRequests.getMer_no():applyRequests.getMer_no());
        accountVerifyParams.put("plat_cust",mallcust);
        accountVerifyParams.put("random_key",applyRequests.getRandom_key());
        accountVerifyParams.put("passwod",applyRequests.getTrans_pwd());
        accountVerifyParams.put("type",applyRequests.getId_type());
        //accountVerifyParams.put("pay_bankacct_type","0");
        //accountVerifyParams.put("sendercomp_id",platPaycode.getPayment_plat_no());
        accountVerifyParams.put("open_account","1");
        accountVerifyParams.put("is_bankcheck",platPaycode.getIs_bankcheck());
        accountVerifyParams.put("cus_type","2");
        String role_types=applyRequests.getRole().replace("，",",").trim();
        String[] roleArray=role_types.split(",");
        for(String role_type:roleArray){
            if(RoleType.INVESTOR.getCode().equals(role_type)){
                accountVerifyParams.put("cjr_role","1");
            }else if(RoleType.BORROWER.getCode().equals(role_type)){
                accountVerifyParams.put("jkr_role","1");
            }else if(RoleType.ADVANCER.getCode().equals(role_type)){
                accountVerifyParams.put("dzr_role","1");
            }else if(RoleType.GUARANTOR.getCode().equals(role_type)){
                accountVerifyParams.put("dbr_role","1");
            }
        }
        //======ccb参数=====
        //accountVerifyParams.put("partner_terminal_id","");
        accountVerifyParams.put("channelId",platPaycode.getChannel_id());
        //=====================================================
        //=========雅酷必填字段============
        //accountVerifyParams.put("partner_userid",registerRequest.getRemark());
        //===================================
        //=================支付公司收单的参数==========

        //==============================================
        accountVerifyParams.put("host",sysParameterService.querySysParameter(applyRequests.getMall_no(), URLConfigUtil.EPAY_SERVER_KEY));
        accountVerifyParams.put("url",sysParameterService.querySysParameter(applyRequests.getMall_no(), URLConfigUtil.BAND_ACCOUNT_VERIFY_KEY));
        Map<String,Object> thirdPartyBaseResponse= adapterService.isicBindCard(accountVerifyParams);
        if(!OrderStatus.SUCCESS.getCode().equals(thirdPartyBaseResponse.get("order_status"))){
            logger.info(String.format("【境外开户申请】身份校验未通过|origin_order_no:%s|error_msg:%s",
                    transTransreq.getOrigin_order_no(),thirdPartyBaseResponse.get("remsg")));
            BaseResponse baseResponse=new BaseResponse();
            baseResponse.setRecode(BusinessMsg.FOUR_VERIFY_FAILED);
            baseResponse.setRemsg(String.format(BusinessMsg.getMsg(BusinessMsg.FOUR_VERIFY_FAILED)+"|%s",
                    MapUtils.getString(thirdPartyBaseResponse,"remsg","身份校验未通过")));
            throw new BusinessException(baseResponse);
        }
        doOrgRegister(mallcust,registerRequest,oldEaccUserInfo,transTransreq);

        return  mallcust;
    }

    private void doOrgRegister(String mallcust,CompanyRegisterRequest registerRequest,EaccUserinfo oldEaccUserInfo,TransTransreq oldTransTransreq)throws BusinessException{
        String platcust= SeqUtil.getSeqNum();
        String role_types=registerRequest.getRole().replace("，",",").trim();

        AccountSubjectInfo accountSubjectInfo = new AccountSubjectInfo();
        // 设置账户明细信息
        accountSubjectInfo.setPlat_no(registerRequest.getMer_no());
        accountSubjectInfo.setAccount_type(AccountType.Platcust.getCode());
        accountSubjectInfo.setPlatcust(mallcust);
        accountSubjectInfo.setEnabled(AcctStatus.FORZEN.getCode());

        // 注册账户明细信息
        accountOprationService.register(accountSubjectInfo);

        if(oldEaccUserInfo==null){
            // 设置平台账户映射信息
            EaccAccountinfo eaccAccountinfo = new EaccAccountinfo();
            eaccAccountinfo=getAccountInfo(eaccAccountinfo,registerRequest,mallcust,platcust);

            // 设置集团客户信息
            EaccUserinfo eaccUserInfo = new EaccUserinfo();
            eaccUserInfo = getUserInfoData(registerRequest, eaccUserInfo, registerRequest, mallcust);
            if (StringUtils.isBlank(registerRequest.getId_type())){
                eaccUserInfo.setCus_type(CusType.COMPANY.getCode());
                eaccUserInfo.setEnabled(AcctStatus.FORZEN.getCode());
                eaccUserInfo.setDefault_mobile(registerRequest.getMobile());
                eaccUserInfo.setDefault_card_no(registerRequest.getCard_no());
                eaccUserInfo.setPwd_flg(Constants.YES);
            }else {
                eaccUserInfo.setCus_type(CusType.PERSONAL.getCode());
                eaccUserInfo.setEnabled(AcctStatus.FORZEN.getCode());
                eaccUserInfo.setDefault_mobile(registerRequest.getMobile());
                eaccUserInfo.setDefault_card_no(registerRequest.getCard_no());
                eaccUserInfo.setName(registerRequest.getAbroad_name());
                eaccUserInfo.setId_code(registerRequest.getId_code());
                eaccUserInfo.setMall_no(registerRequest.getMall_no());
                eaccUserInfo.setId_type(registerRequest.getId_type());
                eaccUserInfo.setPwd_flg(Constants.YES);
            }
            String[] roleArray=role_types.split(",");
            for(String role_type:roleArray){
                if(RoleType.INVESTOR.getCode().equals(role_type)){
                    eaccUserInfo.setInvester(StringUtils.stringToByte(role_type));
                }else if(RoleType.BORROWER.getCode().equals(role_type)){
                    eaccUserInfo.setFinancier(StringUtils.stringToByte(role_type));
                }else if(RoleType.ADVANCER.getCode().equals(role_type)){
                    eaccUserInfo.setAdvancer(StringUtils.stringToByte(role_type));
                }else if(RoleType.GUARANTOR.getCode().equals(role_type)){
                    eaccUserInfo.setGuarantor(StringUtils.stringToByte(role_type));
                }
            }

//            Map<String,Object> params=new HashMap<String,Object>();
//            params.put("mall_no",registerRequest.getMall_no());
//            List<String>  AccOpenconfigList = custOpenconfigMapper.selectPlatNoByMallNo(params);
//            Map<String,Object> params=new HashMap<String,Object>();
//            params.put("mall_no",registerRequest.getMall_no());
//            List<String>  AccOpenconfigList = custOpenconfigMapper.selectPlatNoByMallNo(params);
            List<String>  AccOpenconfigList =accountQueryService.listOpenConfigByMallNo(registerRequest.getMall_no());
            for(String plat_no:AccOpenconfigList){
                eaccAccountinfo.setPlat_no(plat_no);
                // 注册集团客户和平台账户信息
                userAccountService.register(eaccAccountinfo, eaccUserInfo);
            }
        }else {
            oldEaccUserInfo.setPwd_flg(IsUse.YES.getCode());
            oldEaccUserInfo.setUpdate_time(new Date());
            userAccountService.updateEaccUserInfo(oldEaccUserInfo);
        }
        //绑卡并修改绑卡状态
        EaccCardinfo eaccCardInfo=new EaccCardinfo();

        eaccCardInfo.setMall_no(registerRequest.getMall_no());
        eaccCardInfo.setCard_no(registerRequest.getCard_no());
        eaccCardInfo.setCard_type(registerRequest.getCard_type());
        eaccCardInfo.setMallcust(mallcust);
        eaccCardInfo.setOpen_branch(registerRequest.getOpen_branch());
        eaccCardInfo.setStatus(CardStatus.UNACTIVE.getCode());
        eaccCardInfo.setBindId(oldTransTransreq.getTrans_serial());
        eaccCardInfo.setRemark(BindCardType.FOURFOCUSBINGCARD.getCode());
        eaccCardInfo.setPay_channel(StringUtils.isBlank(registerRequest.getPay_code())?"099":registerRequest.getPay_code());
        eaccCardInfo.setMobile(registerRequest.getPre_mobile());
        userAccountService.addBindCardInfo(eaccCardInfo);

        //添加授权信息
        addAuthInfo(registerRequest.getAuthed_type(),registerRequest.getAuthed_amount(),
                registerRequest.getAuthed_limittime(),AuthStatus.NO_AUTH.getCode(),registerRequest.getMall_no(),registerRequest.getMer_no(),mallcust);
    }

    private void doRegister(String mallcust, String platcust,RegisterRequest3 registerRequest,String channelId,TransTransreq oldTransTransreq)throws BusinessException{
        String pre_mobile=null;
        if(registerRequest instanceof RegisterRequest4){
            pre_mobile=((RegisterRequest4)registerRequest).getPre_mobile();
        }
        String role_types=registerRequest.getRole().replace("，",",").trim();

        AccountSubjectInfo accountSubjectInfo = new AccountSubjectInfo();
        // 设置账户明细信息
        accountSubjectInfo.setPlat_no(registerRequest.getMer_no());
        accountSubjectInfo.setAccount_type(AccountType.Platcust.getCode());
        accountSubjectInfo.setPlatcust(mallcust);
        if(StringUtils.isEmpty(pre_mobile)){
            accountSubjectInfo.setEnabled(AcctStatus.FORZEN.getCode());
        }

        // 注册账户明细信息
        accountOprationService.register(accountSubjectInfo);

        // 设置平台账户映射信息
        EaccAccountinfo eaccAccountinfo = new EaccAccountinfo();
        eaccAccountinfo=getAccountInfo(eaccAccountinfo,registerRequest,mallcust,platcust);

        // 设置集团客户信息
        EaccUserinfo eaccUserInfo = new EaccUserinfo();
        eaccUserInfo = getUserInfoData(registerRequest, eaccUserInfo, registerRequest, mallcust);
        eaccUserInfo.setDefault_card_no(registerRequest.getCard_no());
        eaccUserInfo.setDefault_mobile(registerRequest.getMobile());
        eaccUserInfo.setCus_type(CusType.PERSONAL.getCode());
        if(StringUtils.isEmpty(pre_mobile)){
            eaccUserInfo.setEnabled(AcctStatus.FORZEN.getCode());
        }
        eaccUserInfo.setDefault_mobile(pre_mobile);
        eaccUserInfo.setDefault_card_no(registerRequest.getCard_no());
        eaccUserInfo.setPwd_flg(Constants.YES);
        String[] roleArray=role_types.split(",");
        for(String role_type:roleArray){
            if(RoleType.INVESTOR.getCode().equals(role_type)){
                eaccUserInfo.setInvester(StringUtils.stringToByte(role_type));
            }else if(RoleType.BORROWER.getCode().equals(role_type)){
                eaccUserInfo.setFinancier(StringUtils.stringToByte(role_type));
            }else if(RoleType.ADVANCER.getCode().equals(role_type)){
                eaccUserInfo.setAdvancer(StringUtils.stringToByte(role_type));
            }else if(RoleType.GUARANTOR.getCode().equals(role_type)){
                eaccUserInfo.setGuarantor(StringUtils.stringToByte(role_type));
            }
        }

        //绑卡并修改绑卡状态
        EaccCardinfo eaccCardInfo=new EaccCardinfo();

        eaccCardInfo.setMall_no(registerRequest.getMall_no());
        eaccCardInfo.setCard_no(registerRequest.getCard_no());
//        eaccCardInfo.setCard_type(StringUtils.isBlank(registerRequest.getCard_type())?CardType.DEBIT.getCode():registerRequest.getCard_type());
        eaccCardInfo.setMallcust(mallcust);
        eaccCardInfo.setOpen_branch(registerRequest.getOpen_branch());
        if(null!=channelId){
            eaccCardInfo.setPay_channel(channelId);
        }
        eaccCardInfo.setStatus(CardStatus.ACTIVE.getCode());
        eaccCardInfo.setBindId(oldTransTransreq.getTrans_serial());
        if(!StringUtils.isEmpty(pre_mobile)){
            eaccCardInfo.setRemark(BindCardType.FOURFOCUSBINGCARD.getCode());
            eaccCardInfo.setMobile(pre_mobile);
            eaccCardInfo.setCard_type(CardType.DEBIT.getCode());
        }else{
            eaccCardInfo.setRemark(BindCardType.BINDCREDITCARD.getCode());
            eaccCardInfo.setCard_type(CardType.CREDIT.getCode());
        }

        //需要执行插入操作
        List<String>  AccOpenconfigList =accountQueryService.listOpenConfigByMallNo(registerRequest.getMall_no());
        for(String plat_no:AccOpenconfigList){
            eaccAccountinfo.setPlat_no(plat_no);
            // 注册集团客户和平台账户信息
            userAccountService.register(eaccAccountinfo, eaccUserInfo);
        }

        //添加绑卡信息
        if(StringUtils.isBlank(registerRequest.getPlatcust())){
            userAccountService.addBindCardInfo(eaccCardInfo);
        }

        //添加授权信息
        addAuthInfo(registerRequest.getAuthed_type(),registerRequest.getAuthed_amount(),
                registerRequest.getAuthed_limittime(),AuthStatus.ALREADY_AUTH.getCode(),
                registerRequest.getMall_no(),registerRequest.getMer_no(),mallcust);

    }

    private void changeCard(BaseRequest baseRequest, BatchChangeCardRequestDetail changeCardRequestDetail) throws BusinessException{
        //批量换卡参数验证（四要素验证）
        logger.info("【批量换卡】======开始批量换卡参数验证");
        validate(changeCardRequestDetail);
        logger.info("【批量换卡】======批量换卡参数验证通过");
        //记录批量换卡（先绑卡再解绑）流水
        TransTransreq transTransreq = transReqService.getTransTransReq(baseRequest.clone(),TransConsts.BATCH_CHANGE_CARD_CODE,TransConsts.BATCH_CHANGE_CARD_NAME,true);
        transTransreq.setOrder_no(changeCardRequestDetail.getDetail_no());
        transTransreq.setPlatcust(changeCardRequestDetail.getPlatcust());
        transReqService.insert(transTransreq);
        try{
            //先对新老卡信息进行绑卡验证
            logger.info("【批量换卡】======对新卡信息做绑卡验证");
            EaccUserinfo eaccUserinfo = userAccountService.queryEaccUserInfoByMallNoAndPlatcust(baseRequest.getMall_no(),changeCardRequestDetail.getPlatcust());
            EaccCardinfo newCardinfo = userAccountService.queryEaccCardInfo(baseRequest.getMall_no(),
                    changeCardRequestDetail.getPlatcust(),changeCardRequestDetail.getCard_no(),null);
            EaccCardinfo oldCardinfo = userAccountService.queryEaccCardInfo(baseRequest.getMall_no(),
                    changeCardRequestDetail.getPlatcust(),changeCardRequestDetail.getCard_no_old(),null);
            String paycode = null;

            logger.info("【批量换卡】=========检查该用户是否存在");
            if(eaccUserinfo==null){
                logger.info("【批量换卡】=========用户不存在，platcust："+changeCardRequestDetail.getPlatcust());
                throw new BusinessException(BusinessMsg.INVALID_ACCOUNT, BusinessMsg.getMsg(BusinessMsg.INVALID_ACCOUNT)+"：该用户未注册");
            }
            if(!eaccUserinfo.getName().equals(changeCardRequestDetail.getName())){
                logger.info("【批量换卡】=========用户信息有误，platcust：" + changeCardRequestDetail.getPlatcust());
                throw new BusinessException(BusinessMsg.INVALID_ACCOUNT, BusinessMsg.getMsg(BusinessMsg.INVALID_ACCOUNT) + "：该客户号与姓名不匹配");
            }
            logger.info("【批量换卡】=========检查该用户原卡是否绑定");
            if(oldCardinfo == null){
                BaseResponse baseResponse=new BaseResponse();
                baseResponse.setRecode(BusinessMsg.UNBIND_CARD_ERROR);
                baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.UNBIND_CARD_ERROR)+"：根据卡号【"+changeCardRequestDetail.getCard_no_old()+"】未找到老卡绑卡信息");
                throw new BusinessException(baseResponse);
            }
            if (!oldCardinfo.getMobile().equals(changeCardRequestDetail.getMobile_old())) {
                BaseResponse baseResponse=new BaseResponse();
                baseResponse.setRecode(BusinessMsg.PARAMETER_ERROR);
                baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.PARAMETER_ERROR) + "：输入的原卡预留手机号和原卡实际绑卡信息不一致");
                throw new BusinessException(baseResponse);
            }
            if (changeCardRequestDetail.getPay_code()!=null){
                paycode= changeCardRequestDetail.getPay_code();
            } else {
                if (oldCardinfo.getPay_channel()!=null){
                    paycode= oldCardinfo.getPay_channel();
                } else {
                    //TODO 等银行谈好智能路由改造，目前默认040；
                    paycode= "040";
                }
            }

            logger.info("【批量换卡】=========查询paycode："+paycode);
            PlatPaycode platPaycode=userAccountService.queryPlatPaycode(baseRequest.getMer_no(),paycode);
            if(platPaycode==null){
                logger.info("【批量换卡】=========找不到渠道信息");
                throw new BusinessException(BusinessMsg.NO_CHANNEL_INFORMATION,
                        BusinessMsg.getMsg(BusinessMsg.NO_CHANNEL_INFORMATION));
            }
            //检查卡类型
            logger.info("【批量换卡】=========检查卡类型");
            if(CardType.CREDIT.getCode().equals(oldCardinfo.getCard_type()) ){
                logger.info("【批量换卡】=========暂不支持信用卡换绑卡");
                throw new BusinessException(BusinessMsg.TIEDCARD_FAILED, BusinessMsg.getMsg(
                        BusinessMsg.TIEDCARD_FAILED)+"：暂不支持信用卡换绑卡");
            }
            if (newCardinfo!=null ){
                if (CardType.CREDIT.getCode().equals(newCardinfo.getCard_type())){
                    logger.info("【批量换卡】=========暂不支持信用卡换绑卡");
                    throw new BusinessException(BusinessMsg.TIEDCARD_FAILED, BusinessMsg.getMsg(
                            BusinessMsg.TIEDCARD_FAILED)+"：暂不支持信用卡换绑卡");
                }
            }

            //检查用户是否对公用户
            if (CusType.COMPANY.getCode().equals(eaccUserinfo.getCus_type())){
                logger.info("【批量换卡】=========对公用户不允许换绑卡");
                throw new BusinessException(BusinessMsg.DATEBASE_EXCEPTION, BusinessMsg.getMsg(BusinessMsg.DATEBASE_EXCEPTION)+"：对公用户不允许换绑卡");
            }

            //该客户有老卡绑卡信息且新卡也已绑定，更换预留手机号
            if(newCardinfo != null){
                //更换老卡的预留手机号
                if (!changeCardRequestDetail.getCard_no().equals(changeCardRequestDetail.getCard_no_old())){
                    BaseResponse baseResponse=new BaseResponse();
                    baseResponse.setRecode(BusinessMsg.TIEDCARD);
                    baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.TIEDCARD)+"：卡号【"+changeCardRequestDetail.getCard_no()+"】");
                    throw new BusinessException(baseResponse);
                }
                if (!changeCardRequestDetail.getMobile().equals(oldCardinfo.getMobile())) {
                    oldCardinfo.setMobile(changeCardRequestDetail.getMobile());
                    userAccountService.updateCardInfo(oldCardinfo);
                }
            } else {
                //该客户有老卡绑卡信息且新卡未绑定，新卡走四要素验证绑卡
                //个人用户
                Map<String,Object> noMsgBindCardResponse=null;
                logger.info("【批量换卡】=========走四要素通道");
                Map<String,Object> params=new HashMap<String,Object>();
                params.put("partner_id",platPaycode.getPayment_plat_no());
                params.put("partner_serial_no",transTransreq.getTrans_serial());
                params.put("partner_trans_date",baseRequest.getPartner_trans_date());
                params.put("partner_trans_time",baseRequest.getPartner_trans_time());
                params.put("client_name",changeCardRequestDetail.getName());
                params.put("id_kind","0");
                params.put("id_no",eaccUserinfo.getId_code());
                params.put("mobile_tel",changeCardRequestDetail.getMobile());
                params.put("card_no",changeCardRequestDetail.getCard_no());
                params.put("func_code","3");
                params.put("sendercomp_id",platPaycode.getPayment_plat_no());
                params.put("targetcomp_id","91000");
                params.put("channelId",platPaycode.getChannel_id());
                params.put("pay_bankacct_type","0");
                params.put("host",sysParameterService.querySysParameter(baseRequest.getMall_no(), URLConfigUtil.EPAY_SERVER_KEY));
                params.put("url",sysParameterService.querySysParameter(baseRequest.getMall_no(), URLConfigUtil.BAND_ACCOUNT_VERIFY_KEY_OLD));
                Map<String,Object> msgBindCardResponse= adapterService.confirm4ElementsBindCard(params);
//                    Map<String,Object> msgBindCardResponse = new HashMap<String,Object>();
//                    msgBindCardResponse.put("success",true);
                logger.info("返回msgBindCardResponse："+msgBindCardResponse.toString());
                if(!OrderStatus.SUCCESS.getCode().equals(msgBindCardResponse.get("order_status"))){
                    logger.info("【批量换卡】=========绑卡失败："+msgBindCardResponse.get("remsg"));
                    throw new BusinessException(BusinessMsg.TIEDCARD_FAILED, BusinessMsg.getMsg(BusinessMsg.TIEDCARD_FAILED)+"："+msgBindCardResponse.get("remsg"));
                }
                EaccCardinfo bindCardinfo = new EaccCardinfo();
                bindCardinfo.setStatus(CardStatus.ACTIVE.getCode());
                bindCardinfo.setBank_no(String.valueOf(msgBindCardResponse.get("bank_no")));
                bindCardinfo.setRemark(BindCardType.NOMSGBINDCARD.getCode());
                bindCardinfo.setCard_no(changeCardRequestDetail.getCard_no());
                bindCardinfo.setCard_type(CardType.DEBIT.getCode());
                bindCardinfo.setMobile(changeCardRequestDetail.getMobile());
                bindCardinfo.setAcct_name(changeCardRequestDetail.getName());
                bindCardinfo.setMall_no(baseRequest.getMall_no());
                bindCardinfo.setMallcust(changeCardRequestDetail.getPlatcust());
                bindCardinfo.setPay_channel(platPaycode.getChannel_id());
                bindCardinfo.setBindId(transTransreq.getTrans_serial());

                logger.info("【批量换卡】=========添加绑卡数据");
                userAccountService.addBindCardInfo(bindCardinfo);
                logger.info("【批量换卡】=========绑卡完成\n\n\n");

                //更新绑卡数据删除标志
                logger.info("【批量换卡】======开始更新绑卡数据删除标志");
                oldCardinfo.setEnabled(Constants.DISABLED);
                userAccountService.updateCardInfo(oldCardinfo);

                logger.info("【批量换卡】======解绑完成\n\n\n");
                transTransreq.setReturn_code(BusinessMsg.SUCCESS);
                transTransreq.setReturn_msg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));
                transTransreq.setStatus(FlowStatus.SUCCESS.getCode());
                transReqService.insert(transTransreq);
            }
        }catch(Exception e){
            logger.error(e);
            BaseResponse baseResponse=new BaseResponse();
            if(e instanceof BusinessException){
                baseResponse.setRecode(((BusinessException) e).getBaseResponse().getRecode());
                baseResponse.setRemsg(((BusinessException) e).getBaseResponse().getRemsg());
            }else{
                baseResponse.setRecode(BusinessMsg.DATEBASE_EXCEPTION);
                baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.DATEBASE_EXCEPTION));
            }
            transTransreq.setReturn_code(baseResponse.getRecode());
            transTransreq.setReturn_msg(baseResponse.getRemsg());
            transTransreq.setStatus(FlowStatus.FAIL.getCode());
            transReqService.insert(transTransreq);
            throw new BusinessException(baseResponse);
        }
    }

    @Transactional
    public void unbindCardForMultiCards(BaseRequest baseRequest,UnBindCardForMultiCardsRequestDetail detail) throws BusinessException{
        //参数校验
        logger.info("【批量解绑】======开始解绑参数验证");
        validate(detail);
        logger.info("【批量解绑】======解绑参数验证通过");

        //记录流水
        TransTransreq transTransReq=transReqService.getTransTransReq(baseRequest.clone(),TransConsts.BATCH_CARD_UNBIND_CODE,TransConsts.BATCH_CARD_UNBIND_NAME,true);
        transTransReq.setOrder_no(detail.getDetail_no());
        transTransReq.setPlatcust(detail.getPlatcust());
        transReqService.insert(transTransReq);

        try{
            //获取绑卡信息
            logger.info("【批量解绑】======开始获取绑卡信息");
            EaccCardinfo eaccCardInfo=userAccountService.queryEaccCardInfo(baseRequest.getMall_no(),
                    detail.getPlatcust(),detail.getCard_no_old(),null);
            if(eaccCardInfo==null){
                BaseResponse baseResponse=new BaseResponse();
                baseResponse.setRecode(BusinessMsg.UNBIND_CARD_ERROR);
                baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.UNBIND_CARD_ERROR)+"：根据卡号【"+detail.getCard_no_old()+"】未找到绑卡信息");
                throw new BusinessException(baseResponse);
            }else{
                if(!detail.getCard_no_old().equals(eaccCardInfo.getCard_no())/* && !detail.getCard_no_old().equals(eaccCardInfo.getAcct_no())*/){
                    throw new BusinessException(BusinessMsg.UNBIND_CARD_ERROR,BusinessMsg.getMsg(BusinessMsg.UNBIND_CARD_ERROR)+"：已绑卡与解绑卡号不一致");
                }
                if(eaccCardInfo.getStatus().equals("0")){
                    BaseResponse baseResponse=new BaseResponse();
                    baseResponse.setRecode(BusinessMsg.UNBIND_CARD_ERROR);
                    baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.UNBIND_CARD_ERROR)+":绑卡审核中");
                    throw new BusinessException(baseResponse);
                }
            }

            //更新绑卡数据删除标志
            logger.info("【批量解绑】======开始更新绑卡数据删除标志");
            eaccCardInfo.setEnabled(Constants.DISABLED);
            userAccountService.updateCardInfo(eaccCardInfo);

//            logger.info("【解绑】======设置解绑标识");
//            CacheUtil.getCache().set("unlockCardUser"+detail.getPlatcust(),"0",900000);
            logger.info("【批量解绑】======解绑完成\n\n\n");
            transTransReq.setReturn_code(BusinessMsg.SUCCESS);
            transTransReq.setReturn_msg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));
            transTransReq.setStatus(FlowStatus.SUCCESS.getCode());
            transReqService.insert(transTransReq);
        }catch (Exception e){
            logger.error(e);
            BaseResponse baseResponse=new BaseResponse();
            if(e instanceof BusinessException){
                baseResponse.setRecode(((BusinessException) e).getBaseResponse().getRecode());
                baseResponse.setRemsg(((BusinessException) e).getBaseResponse().getRemsg());
            }else{
                baseResponse.setRecode(BusinessMsg.DATEBASE_EXCEPTION);
                baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.DATEBASE_EXCEPTION));
            }
            transTransReq.setReturn_code(baseResponse.getRecode());
            transTransReq.setReturn_msg(baseResponse.getRemsg());
            transTransReq.setStatus(FlowStatus.FAIL.getCode());
            transReqService.insert(transTransReq);
            throw new BusinessException(baseResponse);
        }
    }

    /**
     * 将四要素请求开户信息转换成集团客户信息
     *
     */
    private EaccAccountinfo getAccountInfo(EaccAccountinfo accountinfo, BaseRequest baseRequests, String seqNum, String platcust){
        accountinfo.setMall_no(baseRequests.getMall_no());
        accountinfo.setPlat_no(baseRequests.getMer_no());
        accountinfo.setMallcust(seqNum);
        accountinfo.setPlatcust(platcust);
        return accountinfo;
    }

    /**
     * 将四要素请求开户信息转换成集团客户信息
     *
     */
    private EaccUserinfo getUserInfoData(RegisterRequest3 registerRequest, EaccUserinfo userInfo, BaseRequest baseRequests, String seqNum){
        userInfo.setName(registerRequest.getName());
        userInfo.setId_type(registerRequest.getId_type());
        userInfo.setId_code(registerRequest.getId_code());
        userInfo.setMobile(registerRequest.getMobile());
        userInfo.setEmail(registerRequest.getEmail());
        userInfo.setSex(registerRequest.getSex());
        userInfo.setRemark(registerRequest.getRemark());
        userInfo.setMall_no(baseRequests.getMall_no());
        userInfo.setMallcust(seqNum);
        userInfo.setReg_time(DateUtils.getNow());
        //TODO 角色信息
        return userInfo;
    }

    /**
     * 将四要素请求开户信息转换成对公客户信息
     *
     */
    private EaccUserinfo getUserInfoData(CompanyRegisterRequest registerRequest, EaccUserinfo userInfo, BaseRequest baseRequests, String seqNum){
        userInfo.setName(registerRequest.getOrg_name());
        userInfo.setOrg_name(registerRequest.getOrg_name());
        userInfo.setBank_license(registerRequest.getBank_license());
        userInfo.setBusiness_license(registerRequest.getBusiness_license());
        userInfo.setMobile(registerRequest.getMobile());
        userInfo.setEmail(registerRequest.getEmail());
        userInfo.setRemark(registerRequest.getRemark());
        userInfo.setMall_no(baseRequests.getMall_no());
        userInfo.setMallcust(seqNum);
        userInfo.setReg_time(DateUtils.getNow());
        //TODO 角色信息
        return userInfo;
    }

    @Override
    @Transactional
    public void addAuthInfo(String auth_types, BigDecimal authed_amount,String authed_limittime, String authed_status, String mall_no, String plat_no, String platcust)throws BusinessException{
        if(StringUtils.isNotBlank(auth_types)){
            //先删除授权信息
            String cacheKey="authInfo:"+plat_no+platcust;
            CacheUtil.getCache().del(cacheKey);
            List<EaccUserauth> eaccUserauthList=authCheckService.queryAuthInfoAllStatus(mall_no,plat_no,platcust);
            if(eaccUserauthList!=null && eaccUserauthList.size()>0){
                for(EaccUserauth eu:eaccUserauthList){
                    eaccUserAuthMapper.deleteByPrimaryKey(eu.getId());
                }
            }

            auth_types=auth_types.replaceAll("，",",").trim();
            //插入授权信息
            EaccUserauth eaccUserauth=new EaccUserauth();
            eaccUserauth.setEnabled(Constants.ENABLED);
            eaccUserauth.setMall_no(mall_no);
            eaccUserauth.setPlat_no(plat_no);
            eaccUserauth.setPlatcust(platcust);
            eaccUserauth.setAuthed_amount(authed_amount);
            eaccUserauth.setAuthed_limittime(authed_limittime);
            eaccUserauth.setAuthed_status(authed_status);
            String[] authTypeList=auth_types.split(",");
            for(String auth_type:authTypeList){
                for (AuthType e : AuthType.values()) {
                    if(e.getCode().equals(auth_type)){
                        eaccUserauth.setAuthed_type(auth_type);
                        eaccUserAuthMapper.insert(eaccUserauth);
                    }
                }
            }
        }
    }
}
