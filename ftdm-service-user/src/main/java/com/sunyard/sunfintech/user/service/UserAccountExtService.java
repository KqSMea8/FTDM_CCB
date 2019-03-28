package com.sunyard.sunfintech.user.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.dubbo.rpc.RpcException;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.sunyard.sunfintech.account.provider.IAccountOprationService;
import com.sunyard.sunfintech.account.provider.IAccountQueryService;
import com.sunyard.sunfintech.account.provider.IAccountTransferService;
import com.sunyard.sunfintech.core.Constants;
import com.sunyard.sunfintech.core.TransConsts;
import com.sunyard.sunfintech.core.annotation.SerialNo;
import com.sunyard.sunfintech.core.base.BaseRequest;
import com.sunyard.sunfintech.core.base.BaseResponse;
import com.sunyard.sunfintech.core.base.BaseServiceSimple;
import com.sunyard.sunfintech.core.dic.*;
import com.sunyard.sunfintech.core.exception.BusinessException;
import com.sunyard.sunfintech.core.exception.BusinessMsg;
import com.sunyard.sunfintech.core.util.*;
import com.sunyard.sunfintech.custdao.mapper.CustAccountSubjectInfoMapper;
import com.sunyard.sunfintech.custdao.mapper.CustEaccCardInfoMapper;
import com.sunyard.sunfintech.custdao.mapper.CustEaccUserinfoMapper;
import com.sunyard.sunfintech.dao.entity.*;
import com.sunyard.sunfintech.dao.mapper.*;
import com.sunyard.sunfintech.pub.provider.IAuthCheckService;
import com.sunyard.sunfintech.pub.provider.ISysParameterService;
import com.sunyard.sunfintech.pub.provider.ITransReqService;
import com.sunyard.sunfintech.pub.provider.ITransferService;
import com.sunyard.sunfintech.thirdparty.provider.IAdapterService;
import com.sunyard.sunfintech.user.model.bo.*;
import com.sunyard.sunfintech.user.provider.IProdSearchService;
import com.sunyard.sunfintech.user.provider.IUserAccountExtService;
import com.sunyard.sunfintech.user.provider.IUserAccountService;
import com.sunyard.sunfintech.user.provider.IUserBindCardService;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

/**
 * Created by terry on 2017/7/10.
 */
@Service(interfaceClass =IUserAccountExtService.class)
@CacheConfig(cacheNames="userAccountExtService")
@org.springframework.stereotype.Service("userAccountExtService")
public class UserAccountExtService extends BaseServiceSimple implements IUserAccountExtService {

    @Autowired
    private IUserAccountService userAccountService;
    @Autowired
    ITransferService transferService;
    @Autowired
    private ITransReqService transReqService;

    @Autowired
    private IAccountQueryService accountQueryService;

    @Autowired
    private EaccCardinfoMapper eaccCardInfoMapper;

    @Autowired
    private IAccountOprationService accountOprationService;

    @Autowired
    private CustEaccUserinfoMapper custEaccUserinfoMapper;
    @Autowired
    private EaccUserauthMapper eaccUserauthMapper;

    @Autowired
    private AccountSubjectInfoMapper accountSubjectInfoMapper;
    @Autowired
    private ProdProductinfoMapper prodProductInfoMapper;

    @Autowired
    private IAccountTransferService newAccountTransferService;

    @Autowired
    private IAdapterService adapterService;

    @Autowired
    private ISysParameterService sysParameterService;

    @Autowired
    private IUserBindCardService userBindCardService;

    @Autowired
    private UnRegisterUserInfoMapper unRegisterUserInfoMapper;

    @Autowired
    private EaccUserinfoMapper eaccUserinfoMapper;

    @Autowired
    private EaccUserauthMapper eaccUserAuthMapper;

    @Override
    @Transactional
    public void updateEaccUserAuth(List<EaccUserauth> eaccUserauths) {
        eaccUserauths.forEach((eaccUserauth) -> {
            eaccUserauthMapper.updateByPrimaryKeySelective(eaccUserauth);
        });
    }
    @Override
    @Transactional
    public void deleteEaccUserAuth(List<EaccUserauth> eaccUserauths) {
        eaccUserauths.forEach((eaccUserauth) -> {
            eaccUserauthMapper.deleteByPrimaryKey(eaccUserauth.getId());
            String cacheKey=Constants.CHECK_AUTH_CACHE_KEY+eaccUserauth.getMall_no()+eaccUserauth.getPlat_no()+eaccUserauth.getPlatcust()+eaccUserauth.getAuthed_type();
            CacheUtil.getCache().del(cacheKey);
        });
    }
    @Override
    public String userRegister(BaseRequest baseRequests, BatchRegisterRequestDetail batchRegisterRequestDetail) throws BusinessException {
        //记录单条流水
        TransTransreq transTransReq=transReqService.getTransTransReq(baseRequests.clone(),
                TransConsts.BATCH_CARD_BIND_CODE, TransConsts.BATCH_CARD_BIND_NAME,true);
        transTransReq.setOrder_no(batchRegisterRequestDetail.getDetail_no());
        transReqService.insert(transTransReq);

        validate(batchRegisterRequestDetail);
        try{
            logger.info("【批量开户(四要素绑卡)】========开始运行用户注册");
            //同一个用户在一个平台只可以注册一次，在一个集团可以注册多次，但只有一个账户
            EaccUserinfo oldEaccUserInfo=null;
            if(StringUtils.isEmpty(batchRegisterRequestDetail.getCus_type()) || CusType.PERSONAL.getCode().equals(batchRegisterRequestDetail.getCus_type())){
                //普通用户注册
                oldEaccUserInfo=userAccountService.checkUserInfo(baseRequests.getMall_no(),batchRegisterRequestDetail.getName(),
                        batchRegisterRequestDetail.getId_code(), baseRequests.getMer_no(),true);
            }else{
                //企业用户注册
                BaseResponse baseResponse=new BaseResponse();
                baseResponse.setRecode(BusinessMsg.NO_PERMISSION);
                baseResponse.setRemsg("该接口仅限个人用户调用，请检查cus_type");
                throw new BusinessException(baseResponse);
            }
            //如果已经在平台注册
            if(oldEaccUserInfo!=null){
                logger.info("【批量开户(四要素绑卡)】========用户已在平台注册,判断已绑卡号和当前卡号是否一致，如果一致，直接返回平台用户号！");
                EaccCardinfoExample eaccCardinfoExample=new EaccCardinfoExample();
                EaccCardinfoExample.Criteria criteria=eaccCardinfoExample.createCriteria();
                criteria.andMall_noEqualTo(oldEaccUserInfo.getMall_no());
                criteria.andMallcustEqualTo(oldEaccUserInfo.getMallcust());
                criteria.andCard_noEqualTo(batchRegisterRequestDetail.getCard_no());
                criteria.andEnabledEqualTo(Constants.ENABLED);
                List<EaccCardinfo> cardinfoList=eaccCardInfoMapper.selectByExample(eaccCardinfoExample);
                if(cardinfoList.size()==1){
                    EaccCardinfo cardinfo=cardinfoList.get(0);
                    if(batchRegisterRequestDetail.getCard_no().equals(cardinfo.getCard_no())){
                        //检查授权信息
                        List<EaccUserauth> eaccUserauthList=authCheckService.queryAuthInfo(baseRequests.getMall_no(),baseRequests.getMer_no(),cardinfo.getMallcust());
                        if(StringUtils.isNotBlank(batchRegisterRequestDetail.getAuthed_type())){
                            if(eaccUserauthList.size()>0){
                                String[] authType=batchRegisterRequestDetail.getAuthed_type().replaceAll("，",",").split(",");
                                if(authType.length==eaccUserauthList.size()){
                                    Boolean asSame=true;
                                    for(EaccUserauth ua:eaccUserauthList){
                                        if(!ua.getAuthed_limittime().contains(batchRegisterRequestDetail.getAuthed_limittime()) ||
                                                ua.getAuthed_amount().compareTo(batchRegisterRequestDetail.getAuthed_amount())!=0){
                                            //授权信息不一致
                                            asSame=false;
                                            break;
                                        }
                                    }
                                    if(asSame){
                                        return oldEaccUserInfo.getMallcust();
                                    }
                                }
                            }
                        }else{
                            if(eaccUserauthList.size()>0){
                                //删除授权信息
                                EaccUserauthExample example = new EaccUserauthExample();
                                EaccUserauthExample.Criteria eaccUserauthCriteria = example.createCriteria();
                                eaccUserauthCriteria.andMall_noEqualTo(baseRequests.getMall_no());
                                eaccUserauthCriteria.andPlat_noEqualTo(baseRequests.getMer_no());
                                eaccUserauthCriteria.andPlatcustEqualTo(cardinfo.getMallcust());
                                eaccUserAuthMapper.deleteByExample(example);
                            }
                            return oldEaccUserInfo.getMallcust();
                        }
                    }else{
                        logger.info("【批量开户(四要素绑卡)】========用户已在平台注册,已绑卡与欲绑卡不一致！卡号："+cardinfo.getCard_no());
                        throw new BusinessException(BusinessMsg.TIEDCARD_FAILED,
                                BusinessMsg.getMsg(BusinessMsg.TIEDCARD_FAILED)+"用户已在平台注册,已绑卡与欲绑卡不一致！卡号："+cardinfo.getCard_no());
                    }
                }else if(cardinfoList.size()>1){
                    logger.info("【批量开户(四要素绑卡)】========用户绑卡数据异常，同一用户绑定了多张卡");
                    throw new BusinessException(BusinessMsg.TIEDCARD_FAILED,
                            BusinessMsg.getMsg(BusinessMsg.TIEDCARD_FAILED)+"：用户绑卡数据异常，该用户绑定了"+cardinfoList.size()+"张卡");
                }
            }

            // 查询相应绑卡通道
            PlatPaycode platPaycode = userAccountService.queryPlatPaycode(baseRequests.getMer_no(), batchRegisterRequestDetail.getPay_code());
            if(platPaycode==null){
                logger.info("【批量开户(四要素绑卡)】========pay_code："+batchRegisterRequestDetail.getPay_code()+"不合法");
                BaseResponse baseResponse=new BaseResponse();
                baseResponse.setRecode(BusinessMsg.NO_CHANNEL_INFORMATION);
                baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.NO_CHANNEL_INFORMATION)+"：请检查pay_code是否合法");
                throw new BusinessException(baseResponse);
            }

            String bank_no=null;
            if(StringUtils.isEmpty(batchRegisterRequestDetail.getCus_type()) || CusType.PERSONAL.getCode().equals(batchRegisterRequestDetail.getCus_type())){
                //调用实名认证接口
                Map<String,Object> accountVerifyParams=new HashMap<String,Object>(15);
                accountVerifyParams.put("partner_serial_no",transTransReq.getTrans_serial());
                accountVerifyParams.put("partner_trans_date",baseRequests.getPartner_trans_date());
                accountVerifyParams.put("partner_trans_time",baseRequests.getPartner_trans_time());
                accountVerifyParams.put("client_name",batchRegisterRequestDetail.getName());
                accountVerifyParams.put("partner_id",platPaycode.getPayment_plat_no());
                if(CardType.DEBIT.getCode().equals(batchRegisterRequestDetail.getCard_type())){
                    //借记卡
                    accountVerifyParams.put("card_no",batchRegisterRequestDetail.getCard_no());
                }else {
                    logger.info("【批量开户(四要素绑卡)】========card_type:"+batchRegisterRequestDetail.getPay_code()+"不可用");
                    BaseResponse baseResponse=new BaseResponse();
                    baseResponse.setRecode(BusinessMsg.PARAMETER_ERROR);
                    if(CardType.CREDIT.getCode().equals(batchRegisterRequestDetail.getCard_type())){
                        //信用卡，不可信用卡绑卡
                        baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.PARAMETER_ERROR)+"：不可用信用卡绑卡，请检查card_type");
                    }else{
                        baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.PARAMETER_ERROR)+"：未知卡类型，请检查card_type");
                    }
                    throw new BusinessException(baseResponse);
                }
                accountVerifyParams.put("id_kind","0");
                accountVerifyParams.put("id_no",batchRegisterRequestDetail.getId_code());
                accountVerifyParams.put("mobile_tel",batchRegisterRequestDetail.getPre_mobile());
                accountVerifyParams.put("pay_bankacct_type","0");
                accountVerifyParams.put("sendercomp_id",platPaycode.getPayment_plat_no());
                //======ccb参数=====
                accountVerifyParams.put("partner_terminal_id","");
                accountVerifyParams.put("channelId",platPaycode.getChannel_id());
                //=====================================================
                //=========雅酷必填字段============
                accountVerifyParams.put("partner_userid",batchRegisterRequestDetail.getRemark());
                //===================================
                //=================支付公司收单的参数==========
                accountVerifyParams.put("func_code","3");
                //==============================================
                accountVerifyParams.put("host", sysParameterService.querySysParameter(baseRequests.getMall_no(), URLConfigUtil.EPAY_SERVER_KEY));
                accountVerifyParams.put("url", sysParameterService.querySysParameter(baseRequests.getMall_no(), URLConfigUtil.BAND_ACCOUNT_VERIFY_KEY_OLD));
                Map<String, Object> msgBindCardResponse=adapterService.bindAccountVerify(accountVerifyParams);
                //判断是否成功
                logger.info("【批量开户(四要素绑卡)】=========E支付返回："+msgBindCardResponse.toString());
                if(!OrderStatus.SUCCESS.getCode().equals(msgBindCardResponse.get("order_status"))){
                    logger.info("【批量开户(四要素绑卡)】=========身份校验未通过"+msgBindCardResponse.get("remsg"));
                    throw new BusinessException(BusinessMsg.CERTIFICATION_ERROR,
                            BusinessMsg.getMsg(BusinessMsg.CERTIFICATION_ERROR)+msgBindCardResponse.get("remsg"));
                }
                bank_no=String.valueOf(msgBindCardResponse.get("bank_no"));
                logger.info("【批量开户(四要素绑卡)】=========身份校验通过");
            }

            AccountSubjectInfo accountSubjectInfo = new AccountSubjectInfo();
            // 设置账户明细信息
            accountSubjectInfo.setPlat_no(baseRequests.getMer_no());
            accountSubjectInfo.setAccount_type(AccountType.Platcust.getCode());
            if(oldEaccUserInfo!=null){
                accountSubjectInfo.setPlatcust(oldEaccUserInfo.getMallcust());
            }

            // 注册账户明细信息
            accountSubjectInfo = accountOprationService.register(accountSubjectInfo);

            // 获取集团客户号
            String seqNum = accountSubjectInfo.getPlatcust();
            // 生成平台客户号
            String platcust= SeqUtil.getSeqNum();

            // 设置平台账户映射信息
            EaccAccountinfo eaccAccountinfo = new EaccAccountinfo();
            eaccAccountinfo=getAccountInfo(eaccAccountinfo,baseRequests,seqNum,platcust);

            // 设置集团客户信息
            EaccUserinfo eaccUserInfo = new EaccUserinfo();
            eaccUserInfo = getUserInfoData(batchRegisterRequestDetail, eaccUserInfo, baseRequests, seqNum);
            eaccUserInfo.setDefault_mobile(batchRegisterRequestDetail.getPre_mobile());
            eaccUserInfo.setDefault_card_no(batchRegisterRequestDetail.getCard_no());
            eaccUserInfo.setPwd_flg(Constants.NO);

            List<String>  AccOpenconfigList =accountQueryService.  listOpenConfigByMallNo(baseRequests.getMall_no());
            for(String plat_no:AccOpenconfigList){
                eaccAccountinfo.setPlat_no(plat_no);
                // 注册集团客户和平台账户信息
                userAccountService.register(eaccAccountinfo, eaccUserInfo);
                userBindCardService.addAuthInfo(batchRegisterRequestDetail.getAuthed_type(),
                        batchRegisterRequestDetail.getAuthed_amount(), batchRegisterRequestDetail.getAuthed_limittime(),
                        AuthStatus.ALREADY_AUTH.getCode(), baseRequests.getMall_no(), plat_no, seqNum);
            }
            //绑卡并修改绑卡状态
            EaccCardinfo eaccCardInfo=new EaccCardinfo();
            eaccCardInfo.setMall_no(baseRequests.getMall_no());
            eaccCardInfo.setCard_no(batchRegisterRequestDetail.getCard_no());
            eaccCardInfo.setCard_type(batchRegisterRequestDetail.getCard_type());
            eaccCardInfo.setMallcust(seqNum);
            eaccCardInfo.setBank_no(bank_no);
            eaccCardInfo.setOpen_branch(batchRegisterRequestDetail.getOpen_branch());
            eaccCardInfo.setPay_channel(platPaycode.getChannel_id());
            eaccCardInfo.setStatus(CardStatus.ACTIVE.getCode());
            eaccCardInfo.setBindId(transTransReq.getTrans_serial());
            eaccCardInfo.setRemark(BindCardType.FOURFOCUSBINGCARD.getCode());
            eaccCardInfo.setMobile(batchRegisterRequestDetail.getPre_mobile());
            userAccountService.addBindCardInfo(eaccCardInfo);


            transTransReq.setReturn_code(BusinessMsg.SUCCESS);
            transTransReq.setReturn_msg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));
            transTransReq.setStatus(FlowStatus.SUCCESS.getCode());
            transReqService.insert(transTransReq);

            return seqNum;
        }catch (Exception e){
            BaseResponse baseResponse=new BaseResponse();
            if(e instanceof BusinessException){
                baseResponse=((BusinessException) e).getBaseResponse();
            }else{
                logger.error("【批量开户(四要素绑卡)】========数据库操作异常"+e);
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
    public String authentication(BatchAuthenticationRequestDetail batchAuthenticationRequestDetail, BaseRequest baseRequest) throws BusinessException {
        //记录流水
        TransTransreq transTransreq=transReqService.getTransTransReq(baseRequest.clone(), TransConsts.BATCH_CERTIFY_CODE,
                TransConsts.BATCH_CERTIFY_NAME,true);
        transTransreq.setOrder_no(batchAuthenticationRequestDetail.getDetail_no());
        transReqService.insert(transTransreq);
        try{
            // 生成集团客户号
//            String seqNum = SeqUtil.getSeqNum();

            //同一个用户在一个平台只可以注册一次，在一个集团可以注册多次，但只有一个账户
            EaccUserinfo oldEaccUserInfo=null;
            if(StringUtils.isEmpty(batchAuthenticationRequestDetail.getCus_type()) ||
                    CusType.PERSONAL.getCode().equals(batchAuthenticationRequestDetail.getCus_type())){
                //普通用户注册
                if(StringUtils.isEmpty(batchAuthenticationRequestDetail.getMobile())||
                        StringUtils.isEmpty(batchAuthenticationRequestDetail.getId_code())||StringUtils.isEmpty(batchAuthenticationRequestDetail.getName())){
                    logger.info("【批量开户(实名认证)】=========个人客户实名认证手机号，姓名，身份证号必填");
                    throw new BusinessException(BusinessMsg.METHODPARAMETER_ERROR, BusinessMsg.getMsg(BusinessMsg.METHODPARAMETER_ERROR));
                }
                oldEaccUserInfo=userAccountService.checkUserInfo(baseRequest.getMall_no(),batchAuthenticationRequestDetail.getName(),
                        batchAuthenticationRequestDetail.getId_code(), baseRequest.getMer_no(),true);
            }else{
                if(StringUtils.isEmpty(batchAuthenticationRequestDetail.getBusiness_license())&&StringUtils.isEmpty(batchAuthenticationRequestDetail.getBank_license())){
                    logger.info("【批量开户(实名认证)】=========企业客户营业执照编号或社会信用代码证必须选填一个");
                    throw new BusinessException(BusinessMsg.METHODPARAMETER_ERROR, BusinessMsg.getMsg(BusinessMsg.METHODPARAMETER_ERROR));
                }
                oldEaccUserInfo=userAccountService.checkMallUserInfo(baseRequest.getMall_no(),batchAuthenticationRequestDetail.getOrg_name(),
                        baseRequest.getMer_no(),true);
            }
            if(oldEaccUserInfo!=null){
                logger.info("【批量开户(实名认证)】========用户已在平台注册，直接返回集团客户号！");
                return oldEaccUserInfo.getMallcust();
            }

            //调用行内实名认证接口，099通道请开启
//            if(StringUtils.isEmpty(batchAuthenticationRequestDetail.getCus_type()) ||
//                    CusType.PERSONAL.getCode().equals(batchAuthenticationRequestDetail.getCus_type())){
//                OutsideResponse outsideResponse=null;
//                try{
//                    outsideResponse=outsideService.cert(batchAuthenticationRequestDetail.getId_code()
//                            ,batchAuthenticationRequestDetail.getName());
//                }catch (Exception e){
//                    logger.info("【批量开户(实名认证)】========请求行内实名认证失败！\nError:"+e);
//                    BaseResponse baseResponse=new BaseResponse();
//                    baseResponse.setRecode(BusinessMsg.CERTIFICATION_ERROR);
//                    baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.CERTIFICATION_ERROR)+"：请求行内实名认证失败");
//                    throw new BusinessException(baseResponse);
//                }
//
//                if(!outsideResponse.getResult()){
//                    logger.info("【批量开户(实名认证)】========姓名“"+batchAuthenticationRequestDetail.getName()+
//                            "”，身份证号“"+batchAuthenticationRequestDetail.getId_code()+"”实名认证未通过！");
//                    BaseResponse baseResponse=new BaseResponse();
//                    baseResponse.setRecode(BusinessMsg.CERTIFICATION_ERROR);
//                    baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.CERTIFICATION_ERROR)+"：实名认证未通过");
//                    throw new BusinessException(baseResponse);
//                }
//            }

            //调用E支付实名认证接口，017通道请开启
            logger.info("【批量开户(实名认证)】========cust_type:"+batchAuthenticationRequestDetail.getCus_type());
            if(StringUtils.isEmpty(batchAuthenticationRequestDetail.getCus_type()) ||
                    CusType.PERSONAL.getCode().equals(batchAuthenticationRequestDetail.getCus_type())) {
                PlatPaycode platPaycode = userAccountService.queryPlatPaycode(baseRequest.getMer_no(), "017");
                if (platPaycode == null) {
                    throw new BusinessException(BusinessMsg.UNKNOW_CHANNEL, BusinessMsg.getMsg(BusinessMsg.UNKNOW_CHANNEL));
                }
                Map<String, Object> verifyParams = new HashMap<>();
                verifyParams.put("partner_id", platPaycode.getPayment_plat_no());
                verifyParams.put("partner_serial_no", transTransreq.getTrans_serial());
                verifyParams.put("partner_trans_date", transTransreq.getTrans_date());
                verifyParams.put("partner_trans_time", transTransreq.getTrans_time());
                verifyParams.put("client_name", batchAuthenticationRequestDetail.getName());
                verifyParams.put("id_no", batchAuthenticationRequestDetail.getId_code());
                verifyParams.put("id_kind", "0");
                verifyParams.put("pay_bankacct_type", "0");
                verifyParams.put("card_no", batchAuthenticationRequestDetail);
                verifyParams.put("mobile_tel", batchAuthenticationRequestDetail.getMobile());
                verifyParams.put("sendercomp_id", platPaycode.getPayment_plat_no());
                verifyParams.put("targetcomp_id", "91000");
                //默认通过验证
                Map<String, Object> responseMap = new HashedMap();//TODO 杨磊 fundService.accountVerify071(verifyParams, baseRequest.getMall_no());
                if (!(boolean) (responseMap.get("success"))) {
                    //认证失败
                    logger.info("【批量开户(实名认证)】========姓名“" + batchAuthenticationRequestDetail.getName() +
                            "”，身份证号“" + batchAuthenticationRequestDetail.getId_code() + "”实名认证未通过！");
                    BaseResponse baseResponse = new BaseResponse();
                    baseResponse.setRecode(BusinessMsg.CERTIFICATION_ERROR);
                    baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.CERTIFICATION_ERROR) + "：实名认证未通过");
                    throw new BusinessException(baseResponse);
                }
            }

            // 设置账户明细信息
            AccountSubjectInfo accountSubjectInfo = new AccountSubjectInfo();
            accountSubjectInfo.setPlat_no(baseRequest.getMer_no());
            accountSubjectInfo.setAccount_type(AccountType.Platcust.getCode());
            //上面已经校验过一次，为什么下面的if还要校验？
            if(StringUtils.isEmpty(batchAuthenticationRequestDetail.getCus_type()) ||
                    CusType.PERSONAL.getCode().equals(batchAuthenticationRequestDetail.getCus_type())){
                //普通用户注册
                oldEaccUserInfo=userAccountService.checkUserInfo(baseRequest.getMall_no(),batchAuthenticationRequestDetail.getName(),
                        batchAuthenticationRequestDetail.getId_code(), baseRequest.getMer_no(),false);
            }else{
                //企业用户注册
                oldEaccUserInfo=userAccountService.checkMallUserInfo(baseRequest.getMall_no(),batchAuthenticationRequestDetail.getOrg_name(),
                        baseRequest.getMer_no(),false);
            }
            if(oldEaccUserInfo!=null){
                accountSubjectInfo.setPlatcust(oldEaccUserInfo.getMallcust());
            }
            //开户
            accountSubjectInfo = accountOprationService.register(accountSubjectInfo);

            // 获取集团客户号
            String seqNum = accountSubjectInfo.getPlatcust();

            //设置平台客户号
            String platcust= SeqUtil.getSeqNum();

            // 设置集团客户信息
            EaccUserinfo eaccUserInfo = new EaccUserinfo();
            eaccUserInfo = getUserInfoDataFromAuthen(batchAuthenticationRequestDetail, eaccUserInfo, baseRequest,
                    seqNum);
            eaccUserInfo.setPwd_flg(Constants.NO);

            // 设置平台账户映射信息
            EaccAccountinfo eaccAccountinfo = new EaccAccountinfo();
            eaccAccountinfo = getAccountInfo(eaccAccountinfo, baseRequest, seqNum, platcust);

//            Map<String,Object> params=new HashMap<String,Object>();
//            // params.put("platcust",accountSubjectInfo.getPlatcust());
//            //List<AccountSubjectInfo> accountSubjectInfoList=custAccountSubjectInfoMapper.queryAccountPlatNoList(params);
//            params.put("mall_no",baseRequest.getMall_no());
//            List<String> AccOpenconfigList = custOpenconfigMapper.selectPlatNoByMallNo(params);
            List<String>  AccOpenconfigList =accountQueryService.  listOpenConfigByMallNo(baseRequest.getMall_no());
            for(String plat_no:AccOpenconfigList){
                eaccAccountinfo.setPlat_no(plat_no);
                // 注册集团客户和平台账户信息
                userAccountService.register(eaccAccountinfo, eaccUserInfo);
                userBindCardService.addAuthInfo(batchAuthenticationRequestDetail.getAuthed_type(),
                        batchAuthenticationRequestDetail.getAuthed_amount(), batchAuthenticationRequestDetail.getAuthed_limittime(),
                        AuthStatus.ALREADY_AUTH.getCode(), baseRequest.getMall_no(), plat_no, seqNum);
            }

            transTransreq.setReturn_code(BusinessMsg.SUCCESS);
            transTransreq.setReturn_msg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));
            transTransreq.setStatus(FlowStatus.SUCCESS.getCode());
            transReqService.insert(transTransreq);

            return seqNum;
        }catch (Exception e){
            BaseResponse baseResponse=new BaseResponse();
            if(e instanceof BusinessException){
                baseResponse=((BusinessException) e).getBaseResponse();
            }else{
                logger.error("【批量开户(实名认证)】========单个开户异常：",e);
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

    @Override
    @Transactional
    public void delEaccAccount(String platcust, String mall_no) throws BusinessException {
        if(StringUtils.isEmpty(platcust)){
            throw new BusinessException(BusinessMsg.PARAMETER_ERROR,"删除电子账户映射户数据参数错误，platcust为空");
        }
        AccountSubjectInfoExample accountSubjectInfoExample=new AccountSubjectInfoExample();
        AccountSubjectInfoExample.Criteria criteria=accountSubjectInfoExample.createCriteria();
        criteria.andEnabledEqualTo(Constants.ENABLED);
        criteria.andPlat_noEqualTo(mall_no);
        criteria.andSub_subjectEqualTo(Ssubject.EACCOUNT.getCode());
        criteria.andPlatcustEqualTo(platcust);
        criteria.andN_balanceEqualTo(BigDecimal.ZERO);
        Integer delRows=accountSubjectInfoMapper.deleteByExample(accountSubjectInfoExample);
        if(delRows>2){
            throw new BusinessException(BusinessMsg.DATEBASE_EXCEPTION,"删除超过2条电子账户映射户数据，数据异常！platcust:"+platcust);
        }else if(delRows==0){
            throw new BusinessException(BusinessMsg.DATEBASE_EXCEPTION,"删除0条电子账户映射户数据，删除失败，请手动删除！platcust:"+platcust);
        }
    }


    /**
     * 将实名认证请求开户信息转换成集团客户信息
     *
     */
    private EaccUserinfo getUserInfoDataFromAuthen(BatchAuthenticationRequestDetail batchAuthenticationRequestDetail,
                                                   EaccUserinfo userInfo, BaseRequest baseRequests, String seqNum) {
        userInfo.setName(batchAuthenticationRequestDetail.getName());
        userInfo.setId_code(batchAuthenticationRequestDetail.getId_code());
        userInfo.setMobile(batchAuthenticationRequestDetail.getMobile());
        userInfo.setEmail(batchAuthenticationRequestDetail.getEmail());
        userInfo.setSex(batchAuthenticationRequestDetail.getSex());
        userInfo.setCus_type(batchAuthenticationRequestDetail.getCus_type());
        userInfo.setOrg_name(batchAuthenticationRequestDetail.getOrg_name());
        userInfo.setBank_license(batchAuthenticationRequestDetail.getBank_license());
        userInfo.setBusiness_license(batchAuthenticationRequestDetail.getBusiness_license());
        userInfo.setRemark(batchAuthenticationRequestDetail.getRemark());
        userInfo.setMall_no(baseRequests.getMall_no());
        userInfo.setMallcust(seqNum);
        userInfo.setReg_time(DateUtils.getNow());
        userInfo.setIs_card_bind(IsUse.NO.getCode());
        return userInfo;
    }

    /**
     * 将四要素请求开户信息转换成集团客户信息
     *
     */
    private EaccUserinfo getUserInfoData(BatchRegisterRequestDetail batchRegisterRequestDetail, EaccUserinfo userInfo, BaseRequest baseRequests, String seqNum){
        userInfo.setName(batchRegisterRequestDetail.getName());
        userInfo.setId_type(batchRegisterRequestDetail.getId_type());
        userInfo.setId_code(batchRegisterRequestDetail.getId_code());
        userInfo.setMobile(batchRegisterRequestDetail.getMobile());
        userInfo.setEmail(batchRegisterRequestDetail.getEmail());
        userInfo.setSex(batchRegisterRequestDetail.getSex());
        userInfo.setCus_type(batchRegisterRequestDetail.getCus_type());
        userInfo.setRemark(batchRegisterRequestDetail.getRemark());
        userInfo.setMall_no(baseRequests.getMall_no());
        userInfo.setMallcust(seqNum);
        userInfo.setReg_time(DateUtils.getNow());
        return userInfo;
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

    @Transactional(rollbackFor=Exception.class)
    public void updateAccountStatus(EaccUserinfo eaccUserinfo, List<AccountSubjectInfo> subjectInfoList, List<EaccCardinfo> eaccCardinfoList, String trans_type) {
        BaseResponse baseResponse = new BaseResponse();
        String enable_status = null;
        if(TRANSTYPE.Freeze.getCode().equals(trans_type)){
            enable_status=AcctStatus.FORZEN.getCode();
        }else if(TRANSTYPE.UNFreeze.getCode().equals(trans_type)){
            enable_status=AcctStatus.ACTIVE.getCode();
        }else if(TRANSTYPE.UNuse.getCode().equals(trans_type)){
            enable_status=AcctStatus.UNACTIVE.getCode();
            eaccUserinfo.setDefault_mobile(null);
            eaccUserinfo.setDefault_card_no(null);
        }
        eaccUserinfo.setEnabled(enable_status);
        if(null == eaccUserinfo.getUpdate_by()) eaccUserinfo.setUpdate_by(SeqUtil.RANDOM_KEY);
        eaccUserinfo.setUpdate_time(new Date());
        if(TRANSTYPE.UNuse.getCode().equals(trans_type)){
            //销户
            UnRegisterUserInfo unRegisterUserInfo=new UnRegisterUserInfo();
            BeanUtils.copyProperties(eaccUserinfo,unRegisterUserInfo);
            unRegisterUserInfo.setId(null);
            unRegisterUserInfoMapper.insert(unRegisterUserInfo);
            eaccUserinfoMapper.deleteByPrimaryKey(eaccUserinfo.getId());
        }else{
            int eacc_userinfo_result=custEaccUserinfoMapper.updateEaccUserInfoStatusById(eaccUserinfo);
            logger.info("【客户冻结/解冻或销户】：","更新用户信息表失败，影响行数："+eacc_userinfo_result);
            if(0==eacc_userinfo_result){
                throw new BusinessException(BusinessMsg.DATEBASE_EXCEPTION,BusinessMsg.getMsg(BusinessMsg.DATEBASE_EXCEPTION));
            }
        }
        if(null!=subjectInfoList && subjectInfoList.size()>0){
//            int account_subject_info_result=0;
//            for (AccountSubjectInfo accountSubjectInfo:subjectInfoList) {
//                accountSubjectInfo.setEnabled(enable_status);
//                int result=custAccountSubjectInfoMapper.updateAccountSubjectInfoListByIds(accountSubjectInfo);
//                account_subject_info_result+=result;
//            }
            int account_subject_info_result=accountOprationService.batchUpdateAccountStatus(subjectInfoList,enable_status);
            logger.info("【客户冻结/解冻或销户】：","更新用户账户资金表失败，影响行数："+account_subject_info_result);
            if(0==account_subject_info_result){
                throw new BusinessException(BusinessMsg.DATEBASE_EXCEPTION,BusinessMsg.getMsg(BusinessMsg.DATEBASE_EXCEPTION));
            }
        }
        if(TRANSTYPE.UNuse.getCode().equals(trans_type) && null!=eaccCardinfoList && eaccCardinfoList.size()>0){
            logger.info("【客户冻结/解冻或销户】销户操作，需要删除用户卡信息");
            int ecc_cardinfo_result=0;
            for (EaccCardinfo eaccCardinfo:eaccCardinfoList) {
                eaccCardinfo.setEnabled(enable_status);
                if(null == eaccCardinfo.getUpdate_by()) eaccCardinfo.setUpdate_by(SeqUtil.RANDOM_KEY);
                eaccCardinfo.setUpdate_time(new Date());
                //int cardinfo_result=custEaccCardInfoMapper.updateEaccCardinfoListByIds(eaccCardinfo);
                //直接删除绑卡信息
                int cardinfo_result = eaccCardInfoMapper.deleteByPrimaryKey(eaccCardinfo.getId());
                ecc_cardinfo_result+=cardinfo_result;
            }
            logger.info("【客户冻结/解冻或销户】：","更新用户绑卡表失败，影响行数："+ecc_cardinfo_result);
            if(0==ecc_cardinfo_result){
                throw new BusinessException(BusinessMsg.DATEBASE_EXCEPTION,BusinessMsg.getMsg(BusinessMsg.DATEBASE_EXCEPTION));
            }
        }
    }




    @SerialNo(transCode = TransConsts.PAY_FEE_CODE,description = TransConsts.PAY_FEE_NAME)
    @Override
    public BaseResponse payFee( PayFeeRequest payFeeRequest) throws BusinessException {
        return  payFeeEnque (payFeeRequest,TransConsts.PAY_FEE_CODE,TransConsts.PAY_FEE_NAME);
    }
    @Autowired
    private IAuthCheckService authCheckService;
    /**
     * 缴费和授权缴费接口公共方法
     * @param payFeeRequest
     * @return
     */
    private BaseResponse payFeeEnque(PayFeeRequest payFeeRequest,String transCode ,String transName) {
        BaseResponse baseResponse = new BaseResponse();
        Map<String, BaseResponse> map = Maps.newHashMap();
        try {
            if (TransConsts.AUTH_PAY_FEE_CODE.equals( transCode)) {
                logger.info(transName+"检查是否授权："+new Date()+"，订单号："+payFeeRequest.getOrder_no());
                boolean isSucc=    authCheckService.checkAuth(payFeeRequest.getMer_no(), payFeeRequest.getMall_no(), payFeeRequest.getPlatcust(), AuthType.AUTH_RECHARGE.getCode(),payFeeRequest.getAmt());
                logger.info(transName+"检查是否授权："+new Date()+"，订单号："+payFeeRequest.getOrder_no()+",结果："+isSucc);
                if (!isSucc){
                    throw new BusinessException(BusinessMsg.AUTH_ERROR,
                            String.format(BusinessMsg.AUTH_ERROR+"|授权数据失败|platcust:%s|auth_type:%s",payFeeRequest.getPlatcust(),AuthType.AUTH_RECHARGE.getCode()));
                }
            }
            String inPlatcust = validPayFeeRequest(payFeeRequest, transName);
            EaccAccountamtlist eaccAccountamtlist = new EaccAccountamtlist();
            eaccAccountamtlist.setOrder_no(payFeeRequest.getOrder_no());
            //优先现金
            eaccAccountamtlist.setSubject(Tsubject.CASH.getCode());
            eaccAccountamtlist.setOppo_subject(Tsubject.CASH.getCode());


            eaccAccountamtlist.setSub_subject(payFeeRequest.getAccount_type());
            String inOppSSubject = (StringUtils.isBlank(payFeeRequest.getPayee()) && StringUtils.isBlank(payFeeRequest.getProd_id())) ? Ssubject.PLAT.getCode() : payFeeRequest.getAccount_type();
            eaccAccountamtlist.setOppo_sub_subject(inOppSSubject);
            eaccAccountamtlist.setRemark(transName);
            //设置公共参数
            String transNo = payFeeRequest.getLink_trans_serial() == null ? SeqUtil.getSeqNum() : payFeeRequest.getLink_trans_serial();
            eaccAccountamtlist.setTrans_serial(transNo);
            eaccAccountamtlist.setAmt(payFeeRequest.getAmt());
            eaccAccountamtlist.setTrans_date(payFeeRequest.getPartner_trans_date());
            eaccAccountamtlist.setTrans_time(payFeeRequest.getPartner_trans_time());
            eaccAccountamtlist.setTrans_code(transCode);
            eaccAccountamtlist.setTrans_name(transName);

            eaccAccountamtlist.setPlat_no(payFeeRequest.getMer_no());
            eaccAccountamtlist.setOppo_plat_no(payFeeRequest.getMer_no());
            eaccAccountamtlist.setPlatcust(payFeeRequest.getPlatcust());
            eaccAccountamtlist.setOppo_platcust(inPlatcust);

            List<EaccAccountamtlist> list = new ArrayList<>();
            list.add(eaccAccountamtlist);

            // boolean isSucc = MQUtils.send(amqpTemplate, "ftdm.user.direct.exchange", "PayFeeQueue", list);
            if (TransConsts.PAY_FEE_CODE.equals(transCode))
            {
                    logger.info("转账开始时间："+new Date()+"，订单号："+payFeeRequest.getOrder_no());
                    newAccountTransferService.batchTransfer(list);
                    logger.info("转账结束时间："+new Date()+"，订单号："+payFeeRequest.getOrder_no());


            }else {
                logger.info(String.format("【" + transName + "】开始进入TransferQueue队列|orderno:%s，transserial:%s", payFeeRequest.getOrder_no(), payFeeRequest.getLink_trans_serial()));
                transferService.transfer(payFeeRequest, list);
                logger.info(String.format("【" + transName + "】结束进入TransferQueue队列|orderno:%s，transserial:%s，是否成功：是", payFeeRequest.getOrder_no(), payFeeRequest.getLink_trans_serial()));
            }



            baseResponse.setRecode(BusinessMsg.SUCCESS);
            baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));
            if (TransConsts.AUTH_PAY_FEE_CODE.equals( transCode)) {
                baseResponse.setOrder_status(OrderStatus.PROCESSING.getCode());
            }else{
                baseResponse.setOrder_status(OrderStatus.SUCCESS.getCode());
            }

        } catch (Exception e) {
            if (e instanceof BusinessException) {
                baseResponse = ((BusinessException) e).getBaseResponse();
                baseResponse.setOrder_status(OrderStatus.FAIL.getCode());
            }else  if (e instanceof RpcException) {
                baseResponse.setRecode(BusinessMsg.CALL_REMOTE_ERROR);
                baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.CALL_REMOTE_ERROR));
                baseResponse.setOrder_status(OrderStatus.PROCESSING.getCode());
            } else {
                baseResponse.setRecode(BusinessMsg.RUNTIME_EXCEPTION);
                baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.RUNTIME_EXCEPTION));
                baseResponse.setOrder_status(OrderStatus.FAIL.getCode());
            }


        } finally {
            map.put(payFeeRequest.getOrder_no(), baseResponse);
            baseResponse.setOrderAfterProcessMap(map);
        }

        return baseResponse;
    }

    private String validPayFeeRequest(PayFeeRequest payFeeRequest, String transName) {
        validate(payFeeRequest);
        if (!Ssubject.INVEST.getCode().equals(payFeeRequest.getAccount_type()) && !Ssubject.FINANCING.getCode().equals(payFeeRequest.getAccount_type())) {
            logger.info(String.format("【" + transName + "】账户类型只能是融资或者投资|orderno:%s", payFeeRequest.getAccount_type()));
            throw new BusinessException(BusinessMsg.PARAMETER_ERROR, BusinessMsg.getMsg(BusinessMsg.METHODPARAMETER_ERROR));
        }
        //查询出资人账户
        List<AccountSubjectInfo> outAccountList = accountQueryService.queryPlatAccountList(payFeeRequest.getMer_no(), payFeeRequest.getPlatcust(), null, payFeeRequest.getAccount_type());
        if (outAccountList == null || outAccountList.size() == 0) {
            logger.error("**********【" + transName + "】" + payFeeRequest.getOrder_no() + ">>查询不到出资人账户：**********");
            throw new BusinessException(BusinessMsg.PARAMETER_ERROR, "查询不到出资人账户");
        }
        String inPlatcust = "";
        if (StringUtils.isBlank(payFeeRequest.getPayee())&&StringUtils.isBlank(payFeeRequest.getProd_id())){
            List< AccountSubjectInfo > inAccountlist =  accountQueryService.queryPlatAccountList(payFeeRequest.getMer_no(), null, null, Ssubject.PLAT.getCode());
            if (inAccountlist == null||inAccountlist.size()==0) {
                logger.info("【"+transName+"】-->平台账号不存在-->order_no:" + payFeeRequest.getOrder_no());
                throw new BusinessException(BusinessMsg.INVALID_ACCOUNT, BusinessMsg.getMsg(BusinessMsg.INVALID_ACCOUNT) + ",平台账号不存在:" + payFeeRequest.getOrder_no());
            }
            inPlatcust = inAccountlist.get(0).getPlatcust();

        }else {
            inPlatcust = payFeeRequest.getPayee();
            List<AccountSubjectInfo> inAccountlist = accountQueryService.queryFINANCINGPlatAccountlist(payFeeRequest.getMer_no(), inPlatcust, null, payFeeRequest.getAccount_type());
            if (StringUtils.isBlank (inPlatcust)||inAccountlist == null || inAccountlist.size() == 0) {
                throw new BusinessException(BusinessMsg.PARAMETER_ERROR, "查询不到收款人账户=" + inPlatcust + ",accountType=" + payFeeRequest.getAccount_type());
            }//validProd(payFeeRequest.getMer_no(),payFeeRequest.getOrder_no(),payFeeRequest.getPayee(),payFeeRequest.getProd_id());

            ProdProductinfo checkProductInfo =null;
            if (StringUtils.isNotBlank(payFeeRequest.getProd_id()))
              checkProductInfo =  queryProdInfo(payFeeRequest.getMer_no(), payFeeRequest.getProd_id());
            if (checkProductInfo == null) {
                logger.info(String.format("【缴费】标的不存在|prod_id:%s或者募集账号不存在|orderno:%s", payFeeRequest.getProd_id(), payFeeRequest.getOrder_no()));
                throw new BusinessException(BusinessMsg.INVALID_PRODUCT_ID, BusinessMsg.getMsg(BusinessMsg.INVALID_PRODUCT_ID) + "或者募集账号不存在");
            }

//            List<ProdCompensationList>  prodCompensationLists= prodSearchService   .queryProdCompensationList(payFeeRequest.getMer_no(),payFeeRequest.getProd_id(),payFeeRequest.getPayee());
//            if (prodCompensationLists == null || prodCompensationLists.size() == 0) {
//                logger.info(String.format("【" + transName + "】\"收款人账户%s不是标的代偿账户|orderno:%s",payFeeRequest.getPayee(),payFeeRequest.getOrder_no()));
//                throw new BusinessException(BusinessMsg.PARAMETER_ERROR, "收款人账户" + payFeeRequest.getPayee()+ "不是标"+payFeeRequest.getProd_id()+"的代偿账户");
//            }
        }
        return inPlatcust;
    }
    public ProdProductinfo queryProdInfo(String plat_no, String prod_id) throws BusinessException {
        ProdProductinfoExample example=new ProdProductinfoExample();
        ProdProductinfoExample.Criteria criteria = example.createCriteria();
        if(StringUtils.isNotBlank(plat_no))
            criteria.andPlat_noEqualTo(plat_no);
        if(StringUtils.isNotBlank(prod_id))
            criteria.andProd_idEqualTo(prod_id);
        if(criteria.getAllCriteria().size()==0){
            BaseResponse baseResponse=new BaseResponse();
            baseResponse.setRecode(BusinessMsg.PARAMETER_LACK);
            baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.PARAMETER_LACK) );
            throw new BusinessException(baseResponse);
        }
        criteria.andEnabledEqualTo(Constants.ENABLED);
        List<ProdProductinfo> prodProductInfoList = prodProductInfoMapper.selectByExample(example);
        if(prodProductInfoList.size() > 1){
            BaseResponse baseResponse=new BaseResponse();
            baseResponse.setRecode(BusinessMsg.DATEBASE_EXCEPTION);
            baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.DATEBASE_EXCEPTION) + "----有重复的标");
            throw new BusinessException(baseResponse);
        }else if(prodProductInfoList.size() < 1){
            return null;
        }
        return prodProductInfoList.get(0);
    }
//    private boolean moq(List<EaccAccountamtlist> list) {
//
//        TransTransreq transTransReq = null;
//        List<EaccAccountamtlist> eaccAccountamtlist = null;
//        try {
//            eaccAccountamtlist = list;
//            logger.info("开始消费orderno=" + eaccAccountamtlist.get(0).getOrder_no());
//            transTransReq = new TransTransreq();
//            transTransReq.setOrder_no(eaccAccountamtlist.get(0).getOrder_no());
//            transTransReq.setTrans_serial(eaccAccountamtlist.get(0).getTrans_serial());
//            accountTransferService.batchTransfer(eaccAccountamtlist);
//
//            transTransReq.setStatus(OrderStatus.SUCCESS.getCode());
//            transTransReq.setReturn_code(BusinessMsg.SUCCESS);
//            transTransReq.setReturn_msg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));
//            transReqService.insert(transTransReq);
//        } catch (Exception e) {
//            e.printStackTrace();
//            BaseResponse baseResponse = new BaseResponse();
//            if (e instanceof BusinessException) {
//                baseResponse = ((BusinessException) e).getBaseResponse();
//            } else {
//                logger.error(e);
//                baseResponse.setRecode(BusinessMsg.DATEBASE_EXCEPTION);
//                baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.DATEBASE_EXCEPTION));
//            }
//            if (transTransReq != null) {
//                transTransReq.setStatus(FlowStatus.FAIL.getCode());
//                transTransReq.setReturn_code(baseResponse.getRecode());
//                transTransReq.setReturn_msg(baseResponse.getRemsg());
//                transTransReq.setRemark("消费异常");
//                transReqService.insert(transTransReq);
//            }
//            return false;
//        }
//        return true;
//    }

    @SerialNo(transCode = TransConsts.AUTH_PAY_FEE_CODE,description = TransConsts.AUTH_PAY_FEE_NAME,isAsync = true,isBatch = true)
    @Override
    public AuthPayFeeResponse authPayFee(AuthPayFeeRequest authPayFeeRequest) throws BusinessException {
        AuthPayFeeResponse authPayFeeResponse = new AuthPayFeeResponse();
        validate(authPayFeeRequest);
        List<AuthPayFeeRequestDetail> details = authPayFeeRequest.getAuthPayFeeRequestDetails();
        List<AuthPayFeeResponseDetail> authPayFeeResponseDetails=new ArrayList<>();
        if (details != null) {
            Map map = Maps.newHashMap();
            for (int i = 0; i < details.size(); i++) {
                AuthPayFeeRequestDetail detail=details.get(i);
                PayFeeRequest detailRequest = new PayFeeRequest();
                BeanUtils.copyProperties(authPayFeeRequest, detailRequest);
                BeanUtils.copyProperties(detail, detailRequest, getNullPropertyNames(detail));
                detailRequest.setOrder_no(detail.getDetail_no());
                cacheNotifyData(authPayFeeRequest,detail);
                BaseResponse baseResponse = payFeeEnque(detailRequest,TransConsts.AUTH_PAY_FEE_CODE,TransConsts.AUTH_PAY_FEE_NAME);
                map.putAll( baseResponse.getOrderAfterProcessMap());
                AuthPayFeeResponseDetail repsonseDetail=new AuthPayFeeResponseDetail();
                repsonseDetail.setOrder_status(baseResponse.getOrder_status());
                if (!BusinessMsg.SUCCESS.equals(baseResponse.getRecode())) {
                    repsonseDetail.setError_no(baseResponse.getRecode());
                    repsonseDetail.setError_info(baseResponse.getRemsg());
                }
                repsonseDetail.setQuery_id(detailRequest.getOrder_no());
                repsonseDetail.setProcess_date(DateUtils.todayfulldata());
                authPayFeeResponseDetails.add(repsonseDetail);
            }
            authPayFeeResponse.setOrderAfterProcessMap(map);
        }
        authPayFeeResponse.setOrderData(authPayFeeResponseDetails);
        authPayFeeResponse.setRecode(BusinessMsg.SUCCESS);
        authPayFeeResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));
        authPayFeeResponse.setOrder_no(authPayFeeRequest.getOrder_no());
        return authPayFeeResponse;
    }

    private void cacheNotifyData(AuthPayFeeRequest authPayFeeRequest,AuthPayFeeRequestDetail detail) {
        try {
            PayFeeNotify payFeeNotify = new PayFeeNotify();
            payFeeNotify.setPlatcust(detail.getPlatcust());
//            payFeeNotify.setOrder_status(trans.getStatus());
            payFeeNotify.setProd_id(detail.getProd_id());
            payFeeNotify.setMall_no(authPayFeeRequest.getMall_no());
            payFeeNotify.setTrans_amt(detail.getAmt());
            String key = Constants.AUTHPAYFEE_ORDER_KEY + serviceName + "_order:" + detail.getDetail_no();
            CacheUtil.getCache().set(key, JSON.toJSONString(payFeeNotify), 24 * 3600);
            logger.info("【授权缴费】缓存成功key=" + key);
        } catch (Exception e) {
            logger.error("【授权缴费】cacheNotifyDatay异常", e);
        }
    }

    public static String[] getNullPropertyNames (Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<String>();
        for(java.beans.PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) emptyNames.add(pd.getName());
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }
    @SerialNo(transCode = TransConsts.CANCEL_PAY_FEE_CODE,description = TransConsts.CANCEL_PAY_FEE_NAME)
    @Override
    public BaseResponse cancelPayFee(CancelPayFeeRequest cancelPayFeeRequest) throws BusinessException {
        BaseResponse baseResponse = new BaseResponse();
        Map<String, BaseResponse> map = Maps.newHashMap();
        map.put(cancelPayFeeRequest.getOrder_no(), baseResponse);
        validate(cancelPayFeeRequest);
        TransTransreq transTransreq = transReqService.queryTransTransReqByOrderno(cancelPayFeeRequest.getOri_order_no());
        if (transTransreq == null || !FlowStatus.SUCCESS.getCode().equals(transTransreq.getStatus())) {
            logger.info("【取消缴费】查询不到成功的缴费信息orderno:" + cancelPayFeeRequest.getOri_order_no());
            throw new BusinessException(BusinessMsg.PARAMETER_ERROR, "查询不到成功的缴费信息orderno:" + cancelPayFeeRequest.getOri_order_no());
        }

        List<EaccAccountamtlist> eaccAccountamtlists = accountQueryService.queryEaccAccountamtlistByTransSerial(transTransreq.getTrans_serial());
        if (eaccAccountamtlists == null || eaccAccountamtlists.size() == 0) {
            logger.info("【取消缴费】查询不到成功的缴费流水信息orderno:" + cancelPayFeeRequest.getOri_order_no());
            throw new BusinessException(BusinessMsg.PARAMETER_ERROR, "查询不到成功的缴费流水信息orderno:" + cancelPayFeeRequest.getOri_order_no());
        } else {
            List<EaccAccountamtlist> backEaccAccountamtlist = getBackEaccAccountamtlist(eaccAccountamtlists, cancelPayFeeRequest.getOrder_no(), cancelPayFeeRequest.getLink_trans_serial(), TransConsts.CANCEL_PAY_FEE_CODE, TransConsts.CANCEL_PAY_FEE_NAME);
            if (backEaccAccountamtlist.size() == 0) {
                logger.info("【取消缴费】查询不到成功的缴费流水信息orderno:" + cancelPayFeeRequest.getOri_order_no());
                throw new BusinessException(BusinessMsg.PARAMETER_ERROR, "查询不到成功的缴费流水信息orderno:" + cancelPayFeeRequest.getOri_order_no());
            }


            logger.info(String.format("【缴费】开始进入TransferQueue队列|orderno:%s，transserial:%s", cancelPayFeeRequest.getOrder_no(), cancelPayFeeRequest.getLink_trans_serial()));
            //   boolean isSucc = MQUtils.send(amqpTemplate, "ftdm.user.direct.exchange", "PayFeeQueue", backEaccAccountamtlist);
//            transferService.transfer(cancelPayFeeRequest, backEaccAccountamtlist);
            try {
                logger.info("转账开始时间：" + new Date() + "，订单号：" + cancelPayFeeRequest.getOrder_no());
                newAccountTransferService.batchTransfer(backEaccAccountamtlist);
                logger.info("转账结束时间：" + new Date() + "，订单号：" + cancelPayFeeRequest.getOrder_no());
                baseResponse.setOrder_status(OrderStatus.SUCCESS.getCode());
            }catch (RpcException e){
                baseResponse.setRecode(BusinessMsg.SUCCESS);
                baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));
                baseResponse.setOrder_status(OrderStatus.PROCESSING.getCode());
            }
            logger.info(String.format("【缴费】结束进入TransferQueue队列|orderno:%s，transserial:%s，是否成功：是", cancelPayFeeRequest.getOrder_no(), cancelPayFeeRequest.getLink_trans_serial()));
            baseResponse.setRecode(BusinessMsg.SUCCESS);
            baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));
            map.put(cancelPayFeeRequest.getOrder_no(), baseResponse);
        }
        baseResponse.setOrderAfterProcessMap(map);
        return baseResponse;
    }

    @Transactional
    @Override
    public void unBindCard(List<EaccCardinfo> eaccCardInfos) {
        for(EaccCardinfo eaccCardinfo: eaccCardInfos){
            eaccCardInfoMapper.deleteByPrimaryKey(eaccCardinfo.getId());
        }
    }

    private List<EaccAccountamtlist> getBackEaccAccountamtlist(List<EaccAccountamtlist> eaccAccountamtlists,String orderNo,String transSerialNo,String transCode,String transName){
        List<EaccAccountamtlist> newEaccAccountamtlists=new ArrayList<>();
        for(EaccAccountamtlist eaccAccountamtlist:eaccAccountamtlists){
            if(AmtType.INCOME.getCode().equals(eaccAccountamtlist.getAmt_type())){
                //把出金参数当成反向转账参数进行转账
                eaccAccountamtlist.setAmt_type(null);
                eaccAccountamtlist.setCreate_time(null);
                eaccAccountamtlist.setUpdate_time(null);
                eaccAccountamtlist.setId(null);
                eaccAccountamtlist.setAccount_date(null);
                eaccAccountamtlist.setSwitch_state(null);
                eaccAccountamtlist.setSwitch_amt(null);
                eaccAccountamtlist.setTrans_code(transCode);
                eaccAccountamtlist.setTrans_name(transName);
                eaccAccountamtlist.setOrder_no(orderNo);
                eaccAccountamtlist.setTrans_serial(transSerialNo);
                newEaccAccountamtlists.add(eaccAccountamtlist);
            }
        }
        return newEaccAccountamtlists;
    }
}
