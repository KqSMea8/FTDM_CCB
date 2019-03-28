package com.sunyard.sunfintech.user.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.sunyard.sunfintech.account.model.bo.FirstCertRequest;
import com.sunyard.sunfintech.account.model.bo.FirstCertResponse;
import com.sunyard.sunfintech.account.model.bo.QueryFirstCertRequest;
import com.sunyard.sunfintech.account.provider.IAccountOprationService;
import com.sunyard.sunfintech.account.provider.IAccountQueryService;

import com.sunyard.sunfintech.account.provider.IAccountTransferService;
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
import com.sunyard.sunfintech.custdao.mapper.CustAccountSubjectInfoMapper;
import com.sunyard.sunfintech.custdao.mapper.CustEaccCardInfoMapper;
import com.sunyard.sunfintech.custdao.mapper.CustEaccUserinfoMapper;
import com.sunyard.sunfintech.custdao.mapper.CustOpenconfigMapper;
import com.sunyard.sunfintech.dao.entity.*;
import com.sunyard.sunfintech.dao.mapper.*;
import com.sunyard.sunfintech.pub.model.MsgModel;
import com.sunyard.sunfintech.pub.model.NotifyData;
import com.sunyard.sunfintech.pub.provider.INotifyService;
import com.sunyard.sunfintech.pub.provider.ISendMsgService;
import com.sunyard.sunfintech.pub.provider.ISysParameterService;
import com.sunyard.sunfintech.pub.provider.ITransReqService;
import com.sunyard.sunfintech.thirdparty.provider.IAdapterService;
import com.sunyard.sunfintech.user.model.bo.*;
import com.sunyard.sunfintech.user.modulepublic.AuthCheckService;
import com.sunyard.sunfintech.user.provider.IProdSearchService;
import com.sunyard.sunfintech.user.provider.IUserAccountExtService;
import com.sunyard.sunfintech.user.provider.IUserAccountService;
import com.sunyard.sunfintech.user.provider.IUserBindCardService;
import com.sunyard.sunfintech.user.utils.EaccAccountamtlistOptionUtil;
import com.sunyard.sunfintech.core.util.URLConfigUtil;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 用户账户操作服务
 * Created by terry on 2017/5/15.
 */
@Service(interfaceClass = IUserAccountService.class)
@CacheConfig(cacheNames="userAccountService")
@org.springframework.stereotype.Service("userAccountService")
public class UserAccountService extends BaseServiceSimple implements IUserAccountService  {


    @Autowired
    private IAccountQueryService accountQueryService;

    @Autowired
    private ITransReqService transReqService;

    @Autowired
    private TransTransreqMapper transTransreqMapper;

    @Autowired
    private EaccAccountinfoMapper eaccAccountinfoMapper;

//    @Autowired
//    private CustOpenconfigMapper  custOpenconfigMapper;

    @Autowired
    private EaccCardinfoMapper eaccCardInfoMapper;

    @Autowired
    private EaccUserinfoMapper eaccUserInfoMapper;

    @Autowired
    private PlatPaycodeMapper platPaycodeMapper;

    @Autowired
    private IAccountOprationService accountOprationService;

    @Autowired
    private RwRechargeMapper rwRechargeMapper;

    @Autowired
    private ISendMsgService sendMsgService;

    @Autowired
    private ISysParameterService sysParameterService;

    @Autowired
    private IUserAccountExtService userAccountExtService;

    @Autowired
    private EaccCardcertMapper eaccCardcertMapper;

    @Autowired
    private AuthCheckService authCheckService;

    @Autowired
    private IAdapterService adapterService;

    @Autowired
    private EaccUserauthMapper eaccUserauthMapper;

    @Autowired
    private IProdSearchService prodSearchService;

    @Autowired
    private AccountSearchService accountSearchService;

    @Autowired
    private CustEaccCardInfoMapper custEaccCardInfoMapper;

    @Autowired
    private CustEaccUserinfoMapper custEaccUserinfoMapper;

    @Autowired
    private  CustAccountSubjectInfoMapper custAccountSubjectInfoMapper;

    @Autowired
    private IUserBindCardService userBindCardService;

    @Autowired
    private CustOpenconfigMapper custOpenconfigMapper;

    @Autowired
    private INotifyService notifyService;

    @Autowired
    private IAccountTransferService accountTransferService;

    @Autowired
    private PlatPlatinfoMapper platPlatinfoMapper;

    @Value("${deploy.environment}")
    private String deployEnvironment;

    @Value("${mock.switch}")
    private String mockSwitch;

//    @Autowired
//    private ICertService certService;

//    @Override
//    public boolean switchAccount(SwitchAccountRequestBo switchAccountRequestBo) throws BusinessException {
//        //记录流水
//        TransTransreq transTransReq= transReqService.getTransTransReq(switchAccountRequestBo.clone(),
//                TransConsts.CONVERSE_ACCOUNTS_CODE,TransConsts.CONVERSE_ACCOUNTS_NAME,false);
//        transReqService.insert(transTransReq);
//        try{
//            //查询用户账户
//            List<AccountSubjectInfo> accountSubjectInfoList=accountQueryService.
//                    queryAccountlist(switchAccountRequestBo.getMer_no(),switchAccountRequestBo.getPlatcust());
//
//            AccountSubjectInfo cashAccount = new AccountSubjectInfo();
//            AccountSubjectInfo floatAccount = new AccountSubjectInfo();
//            if(accountSubjectInfoList==null || accountSubjectInfoList.size()<4){
//                //账户不存在或账户异常，抛异常，记录流水
//                transTransReq.setReturn_code(BusinessMsg.INVALID_ACCOUNT);
//                transTransReq.setReturn_msg(BusinessMsg.getMsg(BusinessMsg.INVALID_ACCOUNT));
//                transTransReq.setStatus(FlowStatus.FAIL.getCode());
//                transReqService.insert(transTransReq);
//                BaseResponse baseResponse=new BaseResponse();
//                baseResponse.setRecode(transTransReq.getReturn_code());
//                baseResponse.setRemsg(transTransReq.getReturn_msg());
//                throw new BusinessException(baseResponse);
//            }
//            for(AccountSubjectInfo account:accountSubjectInfoList){
//                //如果是投资账户
//                if(account.getSub_subject().equals(Ssubject.INVEST.getCode())){
//                    if(account.getSubject().equals(Tsubject.CASH.getCode())){
//                        //现金投资账户
//                        cashAccount=account;
//                    }else if(account.getSubject().equals(Tsubject.FLOAT.getCode())){
//                        //在途投资账户
//                        floatAccount=account;
//                    }
//                }
//            }
//            List<EaccAccountamtlist> eaccAccountamtlists=new ArrayList<EaccAccountamtlist>();
//            //如果现金账户可用余额不足，查询在途账户
//            if(cashAccount.getT_balance().compareTo(switchAccountRequestBo.getAmt())<0){
//                BigDecimal leftAmt=switchAccountRequestBo.getAmt().subtract(cashAccount.getT_balance());
//                if(floatAccount.getT_balance().compareTo(leftAmt)<0){
//                    //如果两者余额之和小于转账金额，抛异常，记录流水
//                    transTransReq.setReturn_code(BusinessMsg.BALANCE_LACK);
//                    transTransReq.setReturn_msg(BusinessMsg.getMsg(BusinessMsg.BALANCE_LACK));
//                    transTransReq.setStatus(FlowStatus.FAIL.getCode());
//                    transReqService.insert(transTransReq);
//                    BaseResponse baseResponse=new BaseResponse();
//                    baseResponse.setRecode(transTransReq.getReturn_code());
//                    baseResponse.setRemsg(transTransReq.getReturn_msg());
//                    throw new BusinessException(baseResponse);
//                }
//                //现金账户和在途账户总余额足够
//                //现金账户扣款
//                EaccAccountamtlist eaccAccountamt1 = new EaccAccountamtlist();
//                eaccAccountamt1.setTrans_serial(transTransReq.getTrans_serial());
//                eaccAccountamt1.setPlat_no(switchAccountRequestBo.getMer_no());
//                eaccAccountamt1.setPlatcust(switchAccountRequestBo.getPlatcust());
//                eaccAccountamt1.setOppo_platcust(switchAccountRequestBo.getPlatcust());
//                eaccAccountamt1.setTrans_code(TransConsts.CONVERSE_ACCOUNTS_CODE);
//                eaccAccountamt1.setTrans_name(TransConsts.CONVERSE_ACCOUNTS_NAME);
//                eaccAccountamt1.setTrans_date(switchAccountRequestBo.getPartner_trans_date());
//                eaccAccountamt1.setTrans_time(switchAccountRequestBo.getPartner_trans_time());
//                eaccAccountamt1.setAmt(cashAccount.getT_balance());
//                eaccAccountamt1 = EaccAccountamtlistOptionUtil.setSubject(eaccAccountamt1,Tsubject.CASH.getCode(),
//                        Ssubject.INVEST.getCode(),Tsubject.CASH.getCode(),Ssubject.FINANCING.getCode());
//                eaccAccountamtlists.add(eaccAccountamt1);
//                //在途账户扣款
//                EaccAccountamtlist eaccAccountamt2 = new EaccAccountamtlist();
//                eaccAccountamt2.setTrans_serial(transTransReq.getTrans_serial());
//                eaccAccountamt2.setPlat_no(switchAccountRequestBo.getMer_no());
//                eaccAccountamt2.setPlatcust(switchAccountRequestBo.getPlatcust());
//                eaccAccountamt2.setOppo_platcust(switchAccountRequestBo.getPlatcust());
//                eaccAccountamt2.setTrans_code(TransConsts.CONVERSE_ACCOUNTS_CODE);
//                eaccAccountamt2.setTrans_name(TransConsts.CONVERSE_ACCOUNTS_NAME);
//                eaccAccountamt2.setTrans_date(switchAccountRequestBo.getPartner_trans_date());
//                eaccAccountamt2.setTrans_time(switchAccountRequestBo.getPartner_trans_time());
//                eaccAccountamt2.setAmt(leftAmt);
//                eaccAccountamt2 = EaccAccountamtlistOptionUtil.setSubject(eaccAccountamt2,Tsubject.FLOAT.getCode(),
//                        Ssubject.INVEST.getCode(),Tsubject.FLOAT.getCode(),Ssubject.FINANCING.getCode());
//                eaccAccountamtlists.add(eaccAccountamt2);
//            }else{
//                //现金账户余额足够
//                EaccAccountamtlist eaccAccountamt = new EaccAccountamtlist();
//                eaccAccountamt.setTrans_serial(transTransReq.getTrans_serial());
//                eaccAccountamt.setPlat_no(switchAccountRequestBo.getMer_no());
//                eaccAccountamt.setPlatcust(switchAccountRequestBo.getPlatcust());
//                eaccAccountamt.setOppo_platcust(switchAccountRequestBo.getPlatcust());
//                eaccAccountamt.setTrans_code(TransConsts.CONVERSE_ACCOUNTS_CODE);
//                eaccAccountamt.setTrans_name(TransConsts.CONVERSE_ACCOUNTS_NAME);
//                eaccAccountamt.setTrans_date(switchAccountRequestBo.getPartner_trans_date());
//                eaccAccountamt.setTrans_time(switchAccountRequestBo.getPartner_trans_time());
//                eaccAccountamt.setAmt(switchAccountRequestBo.getAmt());
//                eaccAccountamt= EaccAccountamtlistOptionUtil.setSubject(eaccAccountamt,Tsubject.CASH.getCode(),
//                        Ssubject.INVEST.getCode(),Tsubject.CASH.getCode(),Ssubject.FINANCING.getCode());
//                eaccAccountamtlists.add(eaccAccountamt);
//            }
//
//            //转账
//            accountTransferService.batchTransfer(eaccAccountamtlists);
//        }catch (Exception e){
//            //更新流水，抛异常
//            if(e instanceof BusinessException){
//                transTransReq.setReturn_code(((BusinessException) e).getBaseResponse().getRecode());
//                transTransReq.setReturn_msg(((BusinessException) e).getBaseResponse().getRemsg());
//            }else{
//                transTransReq.setReturn_code(BusinessMsg.DATEBASE_EXCEPTION);
//                transTransReq.setReturn_msg(BusinessMsg.getMsg(BusinessMsg.DATEBASE_EXCEPTION));
//            }
//            transTransReq.setStatus(FlowStatus.FAIL.getCode());
//            transReqService.insert(transTransReq);
//            BaseResponse baseResponse=new BaseResponse();
//            baseResponse.setRecode(transTransReq.getReturn_code());
//            baseResponse.setRemsg(transTransReq.getReturn_msg());
//            logger.error(e.getMessage());
//            throw new BusinessException(baseResponse);
//        }
//
//        //更新流水
//        transTransReq.setReturn_code(BusinessMsg.SUCCESS);
//        transTransReq.setReturn_msg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));
//        transTransReq.setStatus(FlowStatus.SUCCESS.getCode());
//        transReqService.insert(transTransReq);
//
//        return true;
//    }

    /**
     * 客户信息更新
     * pzy
     */
    @Override
    @Transactional
    public ChangeAccountInfoResponse  changeAccountInfo(ChangeAccountInfoRequest changeAccountInfoRequest)throws BusinessException {

        ChangeAccountInfoResponse changeAccountInfoResponse = new ChangeAccountInfoResponse();

        logger.error("【客户信息变更】-->进入dubbo-->order_no:" + changeAccountInfoRequest.getOrder_no());

        //插入业务流水
        TransTransreq transTransReq = transReqService.getTransTransReq(changeAccountInfoRequest.clone(),TransConsts.PLAT_UPDATE_CODE,TransConsts.PLAT_UPDATE_NAME, false);
        transTransReq.setPlatcust(changeAccountInfoRequest.getPlatcust());
        transReqService.insert(transTransReq);

        try{
            //查询获取客户信息
            EaccUserinfo eaccUserInfo = queryEaccUserInfoByMallNoAndPlatcust(changeAccountInfoRequest.getMall_no(),changeAccountInfoRequest.getPlatcust());
            if(null == eaccUserInfo){
                throw new BusinessException(BusinessMsg.INVALID_ACCOUNT,BusinessMsg.getMsg(BusinessMsg.INVALID_ACCOUNT) + "用户信息不存在");
            }
            if(!changeAccountInfoRequest.getCus_type().equals(eaccUserInfo.getCus_type())){
                throw new BusinessException(BusinessMsg.PARAMETER_ERROR,BusinessMsg.getMsg(BusinessMsg.PARAMETER_ERROR)+
                        ":传入客户类型与用户信息中客户类型信息不符");
            }
            Map<String,Object> sendParam=new HashMap<String,Object>();
            sendParam.put("mall_no",changeAccountInfoRequest.getMall_no());
            sendParam.put("mer_no",changeAccountInfoRequest.getMer_no());
            sendParam.put("plat_cust",changeAccountInfoRequest.getPlatcust());
            if(CusType.PERSONAL.getCode().equals(changeAccountInfoRequest.getCus_type())){
                sendParam.put("client_name",eaccUserInfo.getName());
                sendParam.put("id_kind","0");
                sendParam.put("id_no",eaccUserInfo.getId_code());
            }else if(CusType.COMPANY.getCode().equals(changeAccountInfoRequest.getCus_type())){
                sendParam.put("client_name",eaccUserInfo.getOrg_name());
                sendParam.put("id_kind","2");
                sendParam.put("id_no",StringUtils.isBlank(eaccUserInfo.getBank_license())?
                        eaccUserInfo.getBusiness_license():eaccUserInfo.getBank_license());
            }
            if(StringUtils.isNotBlank(changeAccountInfoRequest.getEmail())){
                sendParam.put("email",changeAccountInfoRequest.getEmail());
            }
            if(StringUtils.isNotBlank(changeAccountInfoRequest.getMobile())){
                sendParam.put("mobile",changeAccountInfoRequest.getMobile());
            }
            sendParam.put("host",sysParameterService.querySysParameter(changeAccountInfoRequest.getMall_no(), URLConfigUtil.EPAY_SERVER_KEY));
            sendParam.put("url",sysParameterService.querySysParameter(changeAccountInfoRequest.getMall_no(), URLConfigUtil.ICIS_CUSTOMERCHANGE));
            logger.info("【客户信息变更】请求三方参数："+JSON.toJSON(sendParam));
            Map<String,Object> resultMap = adapterService.isicCustomerChange(sendParam);
            logger.info("【客户信息变更】三方响应结果："+JSON.toJSON(resultMap));
            if(null==resultMap ||null==resultMap.get("order_status") ||"".equals(resultMap.get("order_status"))){
                throw new BusinessException(BusinessMsg.CALL_REMOTE_ERROR,BusinessMsg.getMsg(BusinessMsg.CALL_REMOTE_ERROR));
            }

            if(!OrderStatus.SUCCESS.getCode().equals(resultMap.get("order_status"))){
                transTransReq.setStatus(FlowStatus.FAIL.getCode());
                transTransReq.setReturn_code(BusinessMsg.FAIL);
                transTransReq.setReturn_msg(BusinessMsg.getMsg(BusinessMsg.FAIL)+","+resultMap.get("error_info"));
                transReqService.insert(transTransReq);
                changeAccountInfoResponse.setRecode(BusinessMsg.FAIL);
                changeAccountInfoResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.FAIL)+","+resultMap.get("error_info"));
                return changeAccountInfoResponse;
            }

            if(StringUtils.isNotBlank(changeAccountInfoRequest.getEmail()))
                eaccUserInfo.setEmail(changeAccountInfoRequest.getEmail());

            if(StringUtils.isNotBlank(changeAccountInfoRequest.getMobile()))
                eaccUserInfo.setMobile(changeAccountInfoRequest.getMobile());

            eaccUserInfoMapper.updateByPrimaryKey(eaccUserInfo);
            logger.error("【客户信息变更】-->修改用户信息成功-->order_no:" + changeAccountInfoRequest.getOrder_no());

            changeAccountInfoResponse.setRecode(BusinessMsg.SUCCESS);
            changeAccountInfoResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));

            transTransReq.setReturn_code(BusinessMsg.SUCCESS);
            transTransReq.setReturn_msg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));
            transTransReq.setStatus(FlowStatus.SUCCESS.getCode());
            transReqService.insert(transTransReq);
            logger.error("【客户信息变更】-->修改流水成功-->order_no:" + changeAccountInfoRequest.getOrder_no());

        }catch (Exception e){
            logger.error("【客户信息变更】-->异常-->order_no:"+changeAccountInfoRequest.getOrder_no(),e);

            BaseResponse baseResponse=new BaseResponse();
            if(e instanceof BusinessException){
                baseResponse=((BusinessException) e).getBaseResponse();
            }else{
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

        return changeAccountInfoResponse;

    }

    @Override
    public EaccAccountinfo queryEaccAccountInfo(String mall_no, String plat_no, String platcust) throws BusinessException {
        EaccAccountinfo eaccAccountinfo = null;
        try {

            EaccAccountinfoExample eaccAccountinfoExample = new EaccAccountinfoExample();
            EaccAccountinfoExample.Criteria criteria = eaccAccountinfoExample.createCriteria();

            if(StringUtils.isNotBlank(mall_no))
                criteria.andMall_noEqualTo(mall_no);

            if(StringUtils.isNotBlank(plat_no))
                criteria.andPlat_noEqualTo(plat_no);

            if(StringUtils.isNotBlank(platcust))
                criteria.andMallcustEqualTo(platcust);

            criteria.andEnabledEqualTo(Constants.ENABLED);

            List<EaccAccountinfo> eaccAccountInfoList = eaccAccountinfoMapper.selectByExample(eaccAccountinfoExample);

            if(eaccAccountInfoList != null && eaccAccountInfoList.size() == 1){
                eaccAccountinfo = eaccAccountInfoList.get(0);
            }else if(eaccAccountInfoList != null && eaccAccountInfoList.size() > 1){
                BaseResponse baseResponse=new BaseResponse();
                baseResponse.setRecode(BusinessMsg.DATEBASE_EXCEPTION);
                baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.DATEBASE_EXCEPTION)+",查询到多条平台用户信息，数据有误，请检查数据库中数据。");
                throw new BusinessException(baseResponse);
            }
        }catch (BusinessException e){
            BaseResponse baseResponse=new BaseResponse();
            baseResponse.setRecode(BusinessMsg.DATEBASE_EXCEPTION);
            baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.DATEBASE_EXCEPTION));
            throw new BusinessException(baseResponse);
        }

        return eaccAccountinfo;
    }

    @Override
    public EaccCardinfo queryEaccCardInfo(String mall_no, String mallcust, String card_no, String pay_channel) throws BusinessException {
        EaccCardinfo eaccCardInfo = null;
        try {
            EaccCardinfoExample eaccCardinfoExample = new EaccCardinfoExample();
            EaccCardinfoExample.Criteria criteria = eaccCardinfoExample.createCriteria();

            if(StringUtils.isNotBlank(mall_no))
                criteria.andMall_noEqualTo(mall_no);

            if(StringUtils.isNotBlank(mallcust))
                criteria.andMallcustEqualTo(mallcust);

            if(StringUtils.isNotBlank(card_no))
                criteria.andCard_noEqualTo(card_no);

            if(StringUtils.isNotBlank(pay_channel))
                criteria.andPay_channelEqualTo(pay_channel);

            criteria.andEnabledEqualTo(Constants.ENABLED);
            criteria.andStatusEqualTo(CardStatus.ACTIVE.getCode());

            List<EaccCardinfo> eaccCardInfoList = eaccCardInfoMapper.selectByExample(eaccCardinfoExample);

            if(eaccCardInfoList!=null && eaccCardInfoList.size()>0){
                eaccCardInfo=eaccCardInfoList.get(0);
            }
        }catch (BusinessException e){
            BaseResponse baseResponse=new BaseResponse();
            baseResponse.setRecode(BusinessMsg.DATEBASE_EXCEPTION);
            baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.DATEBASE_EXCEPTION));
            throw new BusinessException(baseResponse);
        }

        return eaccCardInfo;
    }

    @Override
    public List<EaccCardinfo> queryEaccCardInfolist(String mall_no, String mallcust, String card_no) throws BusinessException {
        try {
            EaccCardinfoExample eaccCardinfoExample = new EaccCardinfoExample();
            EaccCardinfoExample.Criteria criteria = eaccCardinfoExample.createCriteria();

            if(StringUtils.isNotBlank(mall_no))
                criteria.andMall_noEqualTo(mall_no);

            if(StringUtils.isNotBlank(mallcust))
                criteria.andMallcustEqualTo(mallcust);

            if(StringUtils.isNotBlank(card_no))
                criteria.andCard_noEqualTo(card_no);

            criteria.andEnabledEqualTo(Constants.ENABLED);
            criteria.andStatusEqualTo(CardStatus.ACTIVE.getCode());

            return eaccCardInfoMapper.selectByExample(eaccCardinfoExample);
        }catch (BusinessException e){
            BaseResponse baseResponse=new BaseResponse();
            baseResponse.setRecode(BusinessMsg.DATEBASE_EXCEPTION);
            baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.DATEBASE_EXCEPTION));
            throw new BusinessException(baseResponse);
        }
    }

    @Override
    public EaccCardinfo queryLastUnbindEaccCardInfo(String mall_no, String mallcust) throws BusinessException {
        EaccCardinfo eaccCardinfo = null;
        try {
            EaccCardinfoExample eaccCardinfoExample = new EaccCardinfoExample();
            EaccCardinfoExample.Criteria criteria = eaccCardinfoExample.createCriteria();

            if(StringUtils.isNotBlank(mall_no))
                criteria.andMall_noEqualTo(mall_no);

            if(StringUtils.isNotBlank(mallcust))
                criteria.andMallcustEqualTo(mallcust);

            criteria.andEnabledEqualTo(Constants.DISABLED);
            criteria.andStatusEqualTo(CardStatus.ACTIVE.getCode());
            eaccCardinfoExample.setOrderByClause("id desc");

            List<EaccCardinfo> eaccCardInfoList = eaccCardInfoMapper.selectByExample(eaccCardinfoExample);

            if(eaccCardInfoList!=null && eaccCardInfoList.size()>0){
                eaccCardinfo = eaccCardInfoList.get(0);
            }
        }catch (BusinessException e){
            BaseResponse baseResponse=new BaseResponse();
            baseResponse.setRecode(BusinessMsg.DATEBASE_EXCEPTION);
            baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.DATEBASE_EXCEPTION));
            throw new BusinessException(baseResponse);
        }
        return eaccCardinfo;
    }

    @Override
    public boolean checkCardLimit(String mall_no, String mallcust)throws BusinessException{
        List<EaccCardinfo> eaccCardInfoList = null;
        try{
            EaccCardinfoExample eaccCardinfoExample = new EaccCardinfoExample();
            EaccCardinfoExample.Criteria criteria = eaccCardinfoExample.createCriteria();
            if(StringUtils.isNotBlank(mall_no))
                criteria.andMall_noEqualTo(mall_no);

            if(StringUtils.isNotBlank(mallcust))
                criteria.andMallcustEqualTo(mallcust);

            criteria.andCard_typeEqualTo(CardType.DEBIT.getCode());
            criteria.andEnabledEqualTo(Constants.ENABLED);
            criteria.andStatusEqualTo(CardStatus.ACTIVE.getCode());

            eaccCardInfoList = eaccCardInfoMapper.selectByExample(eaccCardinfoExample);
            String card_bind_limit = sysParameterService.querySysParameter(mall_no,SysParamterKey.CARD_BIND_LIMIT);
            int card_bind_max = Integer.parseInt(card_bind_limit)-1;
            if (eaccCardInfoList == null || eaccCardInfoList.size()<0){
                return false;
            }
            if (eaccCardInfoList.size()>card_bind_max){
                return true;
            }
        }catch (BusinessException e){
            BaseResponse baseResponse=new BaseResponse();
            baseResponse.setRecode(BusinessMsg.DATEBASE_EXCEPTION);
            baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.DATEBASE_EXCEPTION));
            throw new BusinessException(baseResponse);
        }
        return false;
    }

    @Override
    public boolean checkCreditCardLimit(String mall_no, String mallcust)throws BusinessException{
        List<EaccCardinfo> eaccCardInfoList = null;
        try{
            EaccCardinfoExample eaccCardinfoExample = new EaccCardinfoExample();
            EaccCardinfoExample.Criteria criteria = eaccCardinfoExample.createCriteria();
            if(StringUtils.isNotBlank(mall_no))
                criteria.andMall_noEqualTo(mall_no);

            if(StringUtils.isNotBlank(mallcust))
                criteria.andMallcustEqualTo(mallcust);

            criteria.andCard_typeEqualTo(CardType.CREDIT.getCode());
            criteria.andEnabledEqualTo(Constants.ENABLED);
            criteria.andStatusEqualTo(CardStatus.ACTIVE.getCode());

            eaccCardInfoList = eaccCardInfoMapper.selectByExample(eaccCardinfoExample);
            String credit_card_bind_limit = sysParameterService.querySysParameter(mall_no,SysParamterKey.CREDIT_CARD_BIND_LIMIT);
            int credit_card_bind_max = Integer.parseInt(credit_card_bind_limit)-1;
            if (eaccCardInfoList == null || eaccCardInfoList.size()<0){
                return false;
            }
            if (eaccCardInfoList.size()>credit_card_bind_max){
                return true;
            }
        }catch (BusinessException e){
            BaseResponse baseResponse=new BaseResponse();
            baseResponse.setRecode(BusinessMsg.DATEBASE_EXCEPTION);
            baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.DATEBASE_EXCEPTION));
            throw new BusinessException(baseResponse);
        }
        return false;
    }
    @Override
    public boolean updateCardInfo(EaccCardinfo eaccCardInfo) throws BusinessException {
        try{
            eaccCardInfo.setMallcust(null);
            eaccCardInfo.setUpdate_by(SeqUtil.RANDOM_KEY);
            eaccCardInfo.setUpdate_time(new Date());
            eaccCardInfoMapper.updateByPrimaryKeySelective(eaccCardInfo);

        }catch (BusinessException e){
            BaseResponse baseResponse=new BaseResponse();
            baseResponse.setRecode(BusinessMsg.DATEBASE_EXCEPTION);
            baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.DATEBASE_EXCEPTION));
            throw new BusinessException(baseResponse);
        }
        return true;
    }

    @Override
    public EaccUserinfo queryEaccUserInfo(String mall_no,String mallcust,String id_code, String org_name) throws BusinessException {
        EaccUserinfo eaccUserInfo = null;
        try{

            EaccUserinfoExample eaccUserinfoExample = new EaccUserinfoExample();
            EaccUserinfoExample.Criteria criteria = eaccUserinfoExample.createCriteria();

            if(StringUtils.isNotBlank(mall_no))
                criteria.andMall_noEqualTo(mall_no);

            if(StringUtils.isNotBlank(mallcust))
                criteria.andMallcustEqualTo(mallcust);

            if(StringUtils.isNotBlank(id_code))
                criteria.andId_codeEqualTo(id_code);

            if (StringUtils.isNotBlank(org_name))
                criteria.andOrg_nameEqualTo(org_name);

            criteria.andEnabledEqualTo(Constants.ENABLED);

            List<EaccUserinfo> eaccCardinfoExample = eaccUserInfoMapper.selectByExample(eaccUserinfoExample);

            if(eaccCardinfoExample != null && eaccCardinfoExample.size() > 0){
                eaccUserInfo=eaccCardinfoExample.get(0);
            }
        }catch (BusinessException e){
            BaseResponse baseResponse=new BaseResponse();
            baseResponse.setRecode(BusinessMsg.DATEBASE_EXCEPTION);
            baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.DATEBASE_EXCEPTION));
            throw new BusinessException(baseResponse);
        }

        return eaccUserInfo;
    }

    @Override
    public EaccUserinfo queryEaccUserInfo(String mall_no, String mallcust, String id_code, String org_name, String... status) throws BusinessException {
        EaccUserinfo eaccUserInfo = null;
        try{

            EaccUserinfoExample eaccUserinfoExample = new EaccUserinfoExample();
            EaccUserinfoExample.Criteria criteria = eaccUserinfoExample.createCriteria();

            if(StringUtils.isNotBlank(mall_no))
                criteria.andMall_noEqualTo(mall_no);

            if(StringUtils.isNotBlank(mallcust))
                criteria.andMallcustEqualTo(mallcust);

            if(StringUtils.isNotBlank(id_code))
                criteria.andId_codeEqualTo(id_code);

            if (StringUtils.isNotBlank(org_name))
                criteria.andOrg_nameEqualTo(org_name);

            criteria.andEnabledIn(Arrays.asList(status));

            List<EaccUserinfo> eaccCardinfoExample = eaccUserInfoMapper.selectByExample(eaccUserinfoExample);

            if(eaccCardinfoExample != null && eaccCardinfoExample.size() > 0){
                eaccUserInfo=eaccCardinfoExample.get(0);
            }
        }catch (BusinessException e){
            BaseResponse baseResponse=new BaseResponse();
            baseResponse.setRecode(BusinessMsg.DATEBASE_EXCEPTION);
            baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.DATEBASE_EXCEPTION));
            throw new BusinessException(baseResponse);
        }

        return eaccUserInfo;
    }

    @Override
    public EaccUserinfo queryEaccUserInfoByMallNoAndPlatcust(String mall_no, String platcust) throws BusinessException{

        logger.info("【查询用户信息】-->集团号:"+ mall_no+"-->集团客户号:"+platcust);
        EaccUserinfoExample example=new EaccUserinfoExample();
        EaccUserinfoExample.Criteria criteria=example.createCriteria();
        if(StringUtils.isBlank(mall_no) && StringUtils.isBlank(platcust))
            throw new BusinessException(BusinessMsg.PARAMETER_LACK,"缺少mall_no或者platcust");
        criteria.andMall_noEqualTo(mall_no);
        criteria.andMallcustEqualTo(platcust);
        criteria.andEnabledEqualTo(Constants.ENABLED);
        List<EaccUserinfo> eaccUserInfos= eaccUserInfoMapper.selectByExample(example);
        if(eaccUserInfos.size() > 1){
            throw new BusinessException(BusinessMsg.getMsg(BusinessMsg.DATEBASE_EXCEPTION) + ",请检查用户信息，用户信息出现重复数据 \n"
                    + "mall_no：" + mall_no + " \n mallcust" +platcust);
        }else if(eaccUserInfos.size() < 1){
            return null;
        }
        return eaccUserInfos.get(0);
    }

    @Override
    public boolean updateEaccUserInfo(EaccUserinfo eaccUserinfo, String mall_no, String mallcust) throws BusinessException {
        EaccUserinfoExample eaccUserinfoExample=new EaccUserinfoExample();
        EaccUserinfoExample.Criteria criteria=eaccUserinfoExample.createCriteria();
        criteria.andMall_noEqualTo(mall_no);
        criteria.andMallcustEqualTo(mallcust);
        if(null == eaccUserinfo.getUpdate_by()) eaccUserinfo.setUpdate_by(SeqUtil.RANDOM_KEY);
        eaccUserinfo.setUpdate_time(new Date());
        eaccUserInfoMapper.updateByExampleSelective(eaccUserinfo,eaccUserinfoExample);
        return true;
    }

    @Override
    public boolean updateEaccUserInfo(EaccUserinfo eaccUserinfo) throws BusinessException {
        Integer updateRows=eaccUserInfoMapper.updateByPrimaryKey(eaccUserinfo);
        if(updateRows>0){
            return true;
        }
        return false;
    }

    @Override
    @Transactional(propagation = Propagation.NESTED)
    public boolean register(EaccAccountinfo eaccAccountinfo, EaccUserinfo eaccUserInfo) throws BusinessException {
        try{
            //先判断用户是否存在，如果存在，设置集团用户号
            EaccUserinfo oldEaccUserInfo = null;
            if(StringUtils.isEmpty(eaccUserInfo.getCus_type()) || CusType.PERSONAL.getCode().equals(eaccUserInfo.getCus_type())){
                oldEaccUserInfo=checkUserInfo(eaccAccountinfo.getMall_no(),eaccUserInfo.getName(),
                        eaccUserInfo.getId_code(),eaccAccountinfo.getPlat_no());
            }else if(StringUtils.isEmpty(eaccUserInfo.getCus_type()) || CusType.COMPANY.getCode().equals(eaccUserInfo.getCus_type())){
                oldEaccUserInfo=checkMallUserInfo(eaccAccountinfo.getMall_no(),eaccUserInfo.getOrg_name(),eaccAccountinfo.getPlat_no());
            }

            eaccAccountinfo.setEnabled(Constants.ENABLED);
            eaccAccountinfo.setCreate_by(eaccAccountinfo.getPlat_no());
            eaccAccountinfo.setCreate_time(DateUtils.today());
            eaccUserInfo.setIs_card_bind(IsUse.NO.getCode());
            if(StringUtils.isEmpty(eaccUserInfo.getEnabled())){
                eaccUserInfo.setEnabled(Constants.ENABLED);
            }
            eaccUserInfo.setCreate_by(SeqUtil.RANDOM_KEY);
            eaccUserInfo.setCreate_time(DateUtils.today());
            if(oldEaccUserInfo != null){
                if(Constants.ENABLED.equals(oldEaccUserInfo.getEnabled())){
                    eaccUserInfo.setMallcust(oldEaccUserInfo.getMallcust());
                    eaccUserInfo.setIs_card_bind(oldEaccUserInfo.getIs_card_bind());
                    eaccAccountinfo.setMallcust(oldEaccUserInfo.getMallcust());
                }
                eaccUserInfo.setId(oldEaccUserInfo.getId());
                if(null == eaccUserInfo.getUpdate_by()) eaccUserInfo.setUpdate_by(SeqUtil.RANDOM_KEY);
                eaccUserInfo.setUpdate_time(new Date());
                eaccUserInfoMapper.updateByPrimaryKeySelective(eaccUserInfo);
            }else{
                eaccUserInfoMapper.insert(eaccUserInfo);
            }
            List<EaccAccountinfo> eaccAccountinfoList=accountSearchService.queryEaccAccountInfo(
                    eaccAccountinfo.getMallcust(),eaccAccountinfo.getMall_no(),eaccAccountinfo.getPlat_no());
            if(eaccAccountinfoList.size()<=0){
                eaccAccountinfoMapper.insert(eaccAccountinfo);
            }
        }catch (BusinessException e){
            BaseResponse baseResponse=new BaseResponse();
            baseResponse.setRecode(BusinessMsg.DATEBASE_EXCEPTION);
            baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.DATEBASE_EXCEPTION));
            throw new BusinessException(baseResponse);
        }
        return true;
    }

    @Override
    @Transactional
    public boolean addBindCardInfo(EaccCardinfo eaccCardInfo) throws BusinessException {
        //添加绑卡信息
        try {
            eaccCardInfo.setCreate_time(DateUtils.today());
            eaccCardInfo.setEnabled(IsUse.YES.getCode());

//            eaccCardInfoMapper.insert(eaccCardInfo);
            if(null == eaccCardInfo.getUpdate_by()) eaccCardInfo.setUpdate_by(SeqUtil.RANDOM_KEY);
            eaccCardInfo.setUpdate_time(new Date());
            custEaccCardInfoMapper.insertEaccCardInfoReplace(eaccCardInfo);

            EaccUserinfo eaccUserInfo=new EaccUserinfo();
            eaccUserInfo.setIs_card_bind(IsUse.YES.getCode());
            updateEaccUserInfo(eaccUserInfo,eaccCardInfo.getMall_no(),eaccCardInfo.getMallcust());

            //修改绑卡状态
            /*
            Wrapper<EaccUserInfo> wrapper=new EntityWrapper<EaccUserInfo>();
            wrapper.where("mall_no={0} and mallcust={1}",eaccCardInfo.getMall_no(),eaccCardInfo.getMallcust());*/
            //eaccUserInfoMapper.update(eaccUserInfo,wrapper);
        }catch (BusinessException e){
            BaseResponse baseResponse=new BaseResponse();
            baseResponse.setRecode(BusinessMsg.DATEBASE_EXCEPTION);
            baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.DATEBASE_EXCEPTION));
            throw new BusinessException(baseResponse);
        }
        return true;
    }

    @Override
    public PlatPaycode queryPlatPaycode(String plat_no, String pay_code) throws BusinessException {
        PlatPaycode platPaycode = null;
        try{

            PlatPaycodeExample platPaycodeExample = new PlatPaycodeExample();
            PlatPaycodeExample.Criteria criteria = platPaycodeExample.createCriteria();
            criteria.andPlat_noEqualTo(plat_no);
            criteria.andPay_codeEqualTo(pay_code);
            criteria.andEnabledEqualTo(Constants.ENABLED);
            //platPaycodeExample.or(criteria);
            List<PlatPaycode> platPaycodeList = platPaycodeMapper.selectByExample(platPaycodeExample);

            /*Wrapper<PlatPayCode> platPaycodeWrapper=new EntityWrapper<PlatPayCode>();
            platPaycodeWrapper.eq("plat_no",plat_no);
            platPaycodeWrapper.eq("pay_code",pay_code);
            platPaycodeWrapper.eq("enabled",Constants.ENABLED);
            List<PlatPayCode> platPaycodeList=platPaycodeMapper.selectList(platPaycodeWrapper);*/

            if(platPaycodeList != null && platPaycodeList.size() > 0){
                platPaycode = platPaycodeList.get(0);
            }
        }catch (BusinessException e){
            BaseResponse baseResponse=new BaseResponse();
            baseResponse.setRecode(BusinessMsg.DATEBASE_EXCEPTION);
            baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.DATEBASE_EXCEPTION));
            throw new BusinessException(baseResponse);
        }
        logger.info("查询获取的platPaycode:"+JSON.toJSON(platPaycode));
        return platPaycode;
    }

    /**
     * 批量开户(四要素绑卡)
     * @author yaojp
     * @param baseRequests baseRequests请求参数
     * @param batchRegisterList 四要素验证请求参数
     * @return BatchRegisterReturnData
     */
    @Override
    public BatchRegisterReturnOldData batchRegister(BaseRequest baseRequests,
                                                 List<BatchRegisterRequestDetail> batchRegisterList) {
        //记录批量流水
        TransTransreq transTransReq=transReqService.getTransTransReq(baseRequests.clone(),
                TransConsts.BATCH_CARD_BIND_CODE,TransConsts.BATCH_CARD_BIND_NAME,true);
        transReqService.insert(transTransReq);

        // 设置返回参数
        BatchRegisterReturnOldData batchRegisterReturnData = new BatchRegisterReturnOldData();
        List<BatchRegisterResponseSuccessData> successDataList = new ArrayList<BatchRegisterResponseSuccessData>();
        List<BaseErrorData> errorDataList = new ArrayList<BaseErrorData>();
        if (batchRegisterList != null && batchRegisterList.size() > 0) {
            for (BatchRegisterRequestDetail batchRegisterRequestDetail : batchRegisterList) {
                try{
                    String platcust=userAccountExtService.userRegister(baseRequests,batchRegisterRequestDetail);
                    BatchRegisterResponseSuccessData successData = new BatchRegisterResponseSuccessData();
                    successData.setDetail_no(batchRegisterRequestDetail.getDetail_no());
                    successData.setPlatcust(platcust);
                    successData.setMobile(batchRegisterRequestDetail.getMobile());
                    successDataList.add(successData);

                    try{
                        if(MessageFlag.SEND_MESSAGE.getFlag().equals(batchRegisterRequestDetail.getMessage_flag())){
                            logger.info("批量开户（四要素绑卡）======四要素绑卡开户完成，发送通知短信，记录流水");
                            MsgModel msgModel=new MsgModel();
                            String mall_name=sysParameterService.querySysParameter(baseRequests.getMall_no(), SysParamterKey.MALL_NAME);
                            msgModel.setOrder_no(batchRegisterRequestDetail.getDetail_no());
                            msgModel.setPlat_no(baseRequests.getMer_no());
                            msgModel.setTrans_code(transTransReq.getTrans_code());
                            msgModel.setMobile(batchRegisterRequestDetail.getMobile());
                            String content=sysParameterService.querySysParameter(baseRequests.getMall_no(),MsgContent.OPEN_ACCOUNT_NOTIFY.getMsg());
                            if(StringUtils.isNotBlank(content)){
                                msgModel.setMsgContent(String.format(content,mall_name));
                                sendMsgService.addMsgToQueue(msgModel);
                            }
                        }
                    }catch (Exception e){
                        logger.info("【短信发送失败】======="+e);
                    }
                }catch (Exception e){
                    BaseResponse baseResponse=new BaseResponse();
                    if(e instanceof BusinessException){
                        baseResponse=((BusinessException)e).getBaseResponse();
                    }else{
                        logger.error("批量开户（四要素绑卡）======="+e);
                        baseResponse.setRecode(BusinessMsg.DATEBASE_EXCEPTION);
                        baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.DATEBASE_EXCEPTION));
                    }
                    BaseErrorData errorData = new BaseErrorData();
                    errorData.setDetail_no(batchRegisterRequestDetail.getDetail_no());
                    errorData.setError_info(baseResponse.getRemsg());
                    errorData.setError_no(baseResponse.getRecode());
                    errorDataList.add(errorData);
                }
            }
            batchRegisterReturnData.setSuccess_data_detail(successDataList);
            batchRegisterReturnData.setError_data_detail(errorDataList);
        }

        transTransReq.setReturn_code(BusinessMsg.SUCCESS);
        transTransReq.setReturn_msg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));
        transTransReq.setStatus(FlowStatus.SUCCESS.getCode());
        transReqService.insert(transTransReq);
        return batchRegisterReturnData;
    }

    /**
     * 批量开户(实名认证)
     * @author yaojp
     * @param baseRequest baseRequests请求参数
     * @param batchAuthenticationList 四要素验证请求参数
     * @return BatchAuthenticationReturnData
     */
    @Override
    public BatchAuthenticationReturnData batchAuthentication(BaseRequest baseRequest, List<BatchAuthenticationRequestDetail> batchAuthenticationList) throws BusinessException {
        logger.info("【批量开户(实名认证)】=========开始批量开户(实名认证)");
        //记录流水
        TransTransreq transTransreq=transReqService.getTransTransReq(baseRequest.clone(),TransConsts.BATCH_CERTIFY_CODE,
                TransConsts.BATCH_CERTIFY_NAME,true);
        transReqService.insert(transTransreq);

        BatchAuthenticationReturnData batchAuthenticationReturnData = new BatchAuthenticationReturnData();
        List<BatchAuthenticationResponseSuccessDetail> successDataList = new ArrayList<BatchAuthenticationResponseSuccessDetail>();
        List<BaseErrorData> errorDataList = new ArrayList<BaseErrorData>();
        if (batchAuthenticationList != null && batchAuthenticationList.size() > 0) {
            for (BatchAuthenticationRequestDetail batchAuthenticationRequestDetail : batchAuthenticationList) {
                validate(batchAuthenticationList);
                try {
                    String platcust=userAccountExtService.authentication(batchAuthenticationRequestDetail,baseRequest);
                    BatchAuthenticationResponseSuccessDetail successData = new BatchAuthenticationResponseSuccessDetail();
                    successData.setDetail_no(batchAuthenticationRequestDetail.getDetail_no());
                    successData.setPlatcust(platcust);
                    successData.setMobile(batchAuthenticationRequestDetail.getMobile());
                    successDataList.add(successData);

                    try{
                        if(MessageFlag.SEND_MESSAGE.getFlag().equals(batchAuthenticationRequestDetail.getMessage_flag())){
                            logger.info("批量开户（实名认证）=======实名认证开户完成，发送通知短信，记录流水");
                            MsgModel msgModel=new MsgModel();
                            String mall_name=sysParameterService.querySysParameter(baseRequest.getMall_no(), SysParamterKey.MALL_NAME);
                            msgModel.setOrder_no(batchAuthenticationRequestDetail.getDetail_no());
                            msgModel.setPlat_no(baseRequest.getMer_no());
                            msgModel.setTrans_code(transTransreq.getTrans_code());
                            msgModel.setMobile(batchAuthenticationRequestDetail.getMobile());
                            String content=sysParameterService.querySysParameter(baseRequest.getMall_no(),MsgContent.OPEN_ACCOUNT_NOTIFY.getMsg());
                            if(StringUtils.isNotBlank(content)){
                                msgModel.setMsgContent(String.format(content,mall_name));
                                msgModel.setMsg_type(CcbMsgType.OPEN_ACCOUNT_SUCCESS_MSG.getCode());
                                msgModel.setTrans_serial(transTransreq.getTrans_serial());
                                sendMsgService.addMsgToQueue(msgModel);
                            }
                        }
                    }catch (Exception e){
                        logger.info("【短信发送失败】======="+e);
                    }
                } catch (BusinessException e) {
                    BaseErrorData errorData = new BaseErrorData();
                    errorData.setDetail_no(batchAuthenticationRequestDetail.getDetail_no());
                    errorData.setError_info(e.getBaseResponse().getRemsg());
                    errorData.setError_no(e.getBaseResponse().getRecode());
                    errorDataList.add(errorData);
                }
            }
            batchAuthenticationReturnData.setSuccess_data_detail(successDataList);
            batchAuthenticationReturnData.setError_data_detail(errorDataList);
        }

        //更新流水
        transTransreq.setReturn_code(BusinessMsg.SUCCESS);
        transTransreq.setReturn_msg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));
        transTransreq.setStatus(FlowStatus.SUCCESS.getCode());
        transReqService.insert(transTransreq);

        return batchAuthenticationReturnData;
    }
    /**
     * 电子帐户开户
     * @param platplatcustRegister
     * @return platplatcustRegisterResponseData
     */
    @Transactional
    public PlatplatcustRegisterResponseData openEaccount(PlatplatcustRegisterRequest platplatcustRegister) throws BusinessException {
        logger.info("【电子账户开户】=========开始电子账户开户)");
        //记录流水
        TransTransreq transTransreq=transReqService.getTransTransReq(platplatcustRegister.clone(),TransConsts.PLATCUST_OPEN_CODE,
                TransConsts.PLATCUST_OPEN_NAME,false);
        transReqService.insert(transTransreq);

        String platCust = platplatcustRegister.getPlatcust(); //集团客户号
        PlatplatcustRegisterResponseAsyn platplatcustRegisterResponseAsyn = new PlatplatcustRegisterResponseAsyn();
        platplatcustRegisterResponseAsyn.setOrder_no(platplatcustRegister.getOrder_no());
        platplatcustRegisterResponseAsyn.setMall_no(platplatcustRegister.getMall_no());
        platplatcustRegisterResponseAsyn.setAuthed_amount(platplatcustRegister.getAuthed_amount());
        platplatcustRegisterResponseAsyn.setAuthed_limittime(platplatcustRegister.getAuthed_limittime());
        PlatplatcustRegisterResponseData platplatcustRegisterResponseData=null;


        //说明是新用户
        String eacc_Account=null;
        try{
            //判断是否在集团注册
            EaccUserinfo eaccUserinfo = this.checkUserInfo(platplatcustRegister.getMall_no(), platplatcustRegister.getName(),
                    platplatcustRegister.getId_code(), platplatcustRegister.getMer_no(), true);
            //注册虚拟账户
            EaccCardinfo eaccCardInfo=null;
            AccountSubjectInfo accountSubjectInfo = new AccountSubjectInfo();
            if(null != eaccUserinfo){ //集团注册过
                logger.info("【电子账户开户】=========已注册账号："+eaccUserinfo.toString());
                eacc_Account=eaccUserinfo.getEaccount();
                if(StringUtils.isNotBlank(platCust) && !platCust.equals(eaccUserinfo.getMallcust())){
                    logger.info("【电子账户开户】=========该用户已经在集团注册过，但是电子帐户号不一致，真实电子账户号是：" + eaccUserinfo.getMallcust());
                    throw new BusinessException(BusinessMsg.PARAMETER_ERROR, "该用户已经在集团注册过，但是电子帐户号不一致，真实电子账户号是：" + eaccUserinfo.getMallcust());
                }
                //查询用户绑卡信息 是否绑卡
                eaccCardInfo = queryEaccCardInfo(eaccUserinfo.getMall_no(),
                        eaccUserinfo.getMallcust(),null,null);
                if(eaccCardInfo!=null && !platplatcustRegister.getCard_no().equals(eaccCardInfo.getCard_no())){
                    logger.info("【电子账户开户】=========该用户已绑卡，且卡号与当前绑卡不一致，欲开户卡号为："+
                            platplatcustRegister.getCard_no()+"，已绑卡号为："+eaccCardInfo.getCard_no());
                    throw new BusinessException(BusinessMsg.PARAMETER_ERROR, "该用户已绑卡，且卡号与当前绑卡不一致，欲开户卡号为："+
                            platplatcustRegister.getCard_no()+"，已绑卡号为："+eaccCardInfo.getCard_no()+"，请先解绑");
                }
                accountSubjectInfo.setPlatcust(eaccUserinfo.getMallcust());//集团客户号
            }

            accountSubjectInfo.setAccount_type(AccountType.Platcust.getCode()+","+AccountType.ElectronicAccount.getCode()); //账户类型
            accountSubjectInfo.setPlat_no(platplatcustRegister.getMer_no()); //平台编号


            //获取pay_code
            logger.info("【电子账户开户】=========查询渠道信息，pay_code:098");
            PlatPaycode platPaycode=queryPlatPaycode(platplatcustRegister.getMer_no(),"098");
            if(platPaycode==null){
                logger.info("【电子账户开户】=========没有渠道信息，pay_code：098");
                throw new BusinessException(BusinessMsg.NO_CHANNEL_INFORMATION,"没有渠道信息，pay_code：098");
            }
            //组装信息调用fundservice 电子账户开户
            Map<String,Object> params=new HashMap<String,Object>();
            params=new HashMap<String,Object>();

            //根据password判断新老接口
            if(StringUtils.isBlank(platplatcustRegister.getRandom_key())){
                //老接口
                params.put("url",sysParameterService.querySysParameter(platplatcustRegister.getMall_no(), URLConfigUtil.OPEN_ACOUNT_OLD_KEY));
            }else{
                //新接口
                params.put("url",sysParameterService.querySysParameter(platplatcustRegister.getMall_no(), URLConfigUtil.OPEN_ACOUNT_KEY));
            }

            params.put("host",sysParameterService.querySysParameter(platplatcustRegister.getMall_no(),URLConfigUtil.EPAY_SERVER_KEY));
            params.put("partner_id",platPaycode.getPayment_plat_no());
            params.put("partner_serial_no",transTransreq.getTrans_serial());
            params.put("partner_trans_date", platplatcustRegister.getPartner_trans_date());
            params.put("partner_trans_time", platplatcustRegister.getPartner_trans_time());
            params.put("card_no", platplatcustRegister.getCard_no());
            params.put("id_kind","0");
            params.put("id_no", platplatcustRegister.getId_code());
            params.put("terminal_type",(StringUtils.isEmpty(platplatcustRegister.getTerminal_type())?"0": platplatcustRegister.getTerminal_type()));
            String type_chan=sysParameterService.querySysParameter(platplatcustRegister.getMall_no(),SysParamterKey.TYPE_CHAN);
            params.put("type_chan",type_chan);
            params.put("mobile", platplatcustRegister.getMobile());
            params.put("mobile_tel", platplatcustRegister.getPre_mobile());
            params.put("password", platplatcustRegister.getBank_acct_pwd());
            params.put("random_key", platplatcustRegister.getRandom_key());
            params.put("client_name", platplatcustRegister.getName());
            params.put("mall_no", platplatcustRegister.getMall_no());
            params.put("mer_no", platplatcustRegister.getMer_no());

            if(null == accountSubjectInfo.getPlatcust())
                accountSubjectInfo.setPlatcust(SeqUtil.getSeqNum());

            params.put("plat_cust", accountSubjectInfo.getPlatcust());
            params.put("cus_type",CusType.PERSONAL.getCode());
            params.put("open_account","1");
            params.put("is_bankcheck",platPaycode.getIs_bankcheck());
            params.put("func_code","3");
            //================老接口参数===============
            params.put("bank_acct_pwd",platplatcustRegister.getBank_acct_pwd());
            //========================================
            // 已开通电子账户的用户再次调用接口需传入 电子账户编号
            logger.info("【电子账户开户】=========原电子账户账号为：" + eacc_Account);
            if(StringUtils.isNotBlank(eacc_Account)){
                params.put("bank_acct_no",eacc_Account);
            }
            Map<String, Object> platcustOpenResponse;
            //老接口
            if (StringUtils.isBlank(platplatcustRegister.getRandom_key())) {
                logger.info("【电子账户开户】=========开始请求E支付开户，参数：" + JSON.toJSONString(params));
                platcustOpenResponse = adapterService.registerEaccountOld(params);
                logger.info("【电子账户开户】=========E支付返回，参数：" + JSON.toJSONString(platcustOpenResponse));
            } else {
                logger.info("【电子账户开户】=========开始请求cis支付开户，参数：" + JSON.toJSONString(params));
                platcustOpenResponse = adapterService.registerEaccount(params);
                logger.info("【电子账户开户】=========cis支付返回，参数：" + JSON.toJSONString(platcustOpenResponse));
            }
            if(FlowStatus.SUCCESS.getCode().equals(platcustOpenResponse.get("order_status"))){
                logger.info("【电子账户开户】=========请求第三方开户成功");
                //电子帐户开户成功
                logger.info("【电子账户开户】=========开始开通虚拟账户");
                accountSubjectInfo = accountOprationService.register(accountSubjectInfo);
//            platplatcustRegister.setPlatcust(accountSubjectInfo.getPlatcust());
                //写入eacc_userinfo
                eaccUserinfo = new EaccUserinfo();
                eaccUserinfo.setMallcust(accountSubjectInfo.getPlatcust());
                eaccUserinfo.setMall_no(platplatcustRegister.getMall_no());
                eaccUserinfo.setCus_type(CusType.PERSONAL.getCode());
                eaccUserinfo.setName(platplatcustRegister.getName());
                eaccUserinfo.setId_type(platplatcustRegister.getId_type());
                eaccUserinfo.setId_code(platplatcustRegister.getId_code());
                eaccUserinfo.setMobile(platplatcustRegister.getMobile());
                eaccUserinfo.setDefault_mobile(platplatcustRegister.getPre_mobile());
                eaccUserinfo.setDefault_card_no(platplatcustRegister.getCard_no());
                eaccUserinfo.setPwd_flg(Constants.YES);
                //写入eacc_accountinfo
                params=new HashMap<String,Object>();
                params.put("mall_no", platplatcustRegister.getMall_no());
                List<String>  AccOpenconfigList = custOpenconfigMapper.selectPlatNoByMallNo(params);
                for(String plat_no:AccOpenconfigList){
                    EaccAccountinfo eaccAccountinfo=new EaccAccountinfo();
                    eaccAccountinfo.setPlat_no(plat_no);
                    eaccAccountinfo.setMall_no(platplatcustRegister.getMall_no());
                    eaccAccountinfo.setMallcust(accountSubjectInfo.getPlatcust());
                    eaccAccountinfo.setPlatcust(SeqUtil.getSeqNum());

                    //注册平台账户和集团账户（如果集团账户未注册的话）
                    logger.info("【电子账户开户】=========开始注册平台集团用户");
                    register(eaccAccountinfo,eaccUserinfo);

                    if(StringUtils.isNotBlank(platplatcustRegister.getAuthed_type())){
                        //添加授权信息
                        userBindCardService.addAuthInfo(platplatcustRegister.getAuthed_type(),platplatcustRegister.getAuthed_amount(),
                                platplatcustRegister.getAuthed_limittime(),AuthStatus.ALREADY_AUTH.getCode(),
                                platplatcustRegister.getMall_no(),plat_no,eaccUserinfo.getMallcust());
                    }
                }
                //更新手机号码和电子账户信息
                logger.info("【电子账户开户】=========更新手机号码和电子账户信息");
                eaccUserinfo.setMobile(platplatcustRegister.getMobile());
                eaccUserinfo.setIs_card_bind(IsUse.YES.getCode());
                eaccUserinfo.setEaccount_flg(IsUse.YES.getCode());
                eaccUserinfo.setEaccount(String.valueOf(platcustOpenResponse.get("bank_elec_no")));
                if (StringUtils.isNoneBlank(platplatcustRegister.getRole())) {
                    String role_types = platplatcustRegister.getRole().replace("，", ",").trim();
                    String[] roleArray = role_types.split(",");
                    for (String role_type : roleArray) {
                        if (RoleType.INVESTOR.getCode().equals(role_type)) {
                            eaccUserinfo.setInvester(StringUtils.stringToByte(role_type));
                        } else if (RoleType.BORROWER.getCode().equals(role_type)) {
                            eaccUserinfo.setFinancier(StringUtils.stringToByte(role_type));
                        } else if (RoleType.ADVANCER.getCode().equals(role_type)) {
                            eaccUserinfo.setAdvancer(StringUtils.stringToByte(role_type));
                        } else if (RoleType.GUARANTOR.getCode().equals(role_type)) {
                            eaccUserinfo.setGuarantor(StringUtils.stringToByte(role_type));
                        }
                    }
                }

                EaccUserinfoExample eaccUserinfoExample=new EaccUserinfoExample();
                EaccUserinfoExample.Criteria criteria=eaccUserinfoExample.createCriteria();
                criteria.andMallcustEqualTo(eaccUserinfo.getMallcust());
                criteria.andMall_noEqualTo(eaccUserinfo.getMall_no());
                eaccUserInfoMapper.updateByExampleSelective(eaccUserinfo,eaccUserinfoExample);

                //更新或添加绑卡信息
                if (null != eaccCardInfo) {
                    if(platplatcustRegister.getPre_mobile().equals(eaccCardInfo.getMobile())){
                        logger.info("【电子账户开户】=========绑卡信息不一致");
                        eaccCardInfo.setMobile(platplatcustRegister.getPre_mobile());
                        //eaccCardInfo.setBindId(transTransreq.getTrans_serial());
                        eaccCardInfo.setRemark(BindCardType.EACCOPENBIND.getCode());
                        if(null == eaccCardInfo.getUpdate_by()) eaccCardInfo.setUpdate_by(SeqUtil.RANDOM_KEY);
                        eaccCardInfo.setUpdate_time(new Date());
                        eaccCardInfoMapper.updateByPrimaryKey(eaccCardInfo);
                    }
                }else{
                    logger.info("【电子账户开户】=========添加绑卡信息");
                    eaccCardInfo=new EaccCardinfo();
                    eaccCardInfo.setMall_no(platplatcustRegister.getMall_no());
                    eaccCardInfo.setMallcust(eaccUserinfo.getMallcust());
                    eaccCardInfo.setStatus(CardStatus.ACTIVE.getCode());
                    eaccCardInfo.setMobile(platplatcustRegister.getPre_mobile());
                    eaccCardInfo.setCard_type(CardType.DEBIT.getCode());
                    eaccCardInfo.setCard_no(platplatcustRegister.getCard_no());
                    eaccCardInfo.setBindId(transTransreq.getTrans_serial());
                    eaccCardInfo.setPay_channel(platPaycode.getChannel_id());
                    eaccCardInfo.setRemark(BindCardType.EACCOPENBIND.getCode());
                    addBindCardInfo(eaccCardInfo);
                }

               if(StringUtils.isNotBlank(platplatcustRegister.getAuthed_type())){
                    //添加授权信息
                   userBindCardService.addAuthInfo(platplatcustRegister.getAuthed_type(),platplatcustRegister.getAuthed_amount(),
                        platplatcustRegister.getAuthed_limittime(),AuthStatus.ALREADY_AUTH.getCode(),
                           platplatcustRegister.getMall_no(),platplatcustRegister.getMer_no(),eaccUserinfo.getMallcust());
               }
                //更新AccountSubjectInfo
                AccountSubjectInfo accountSubjectInfoParams=new AccountSubjectInfo();
                accountSubjectInfoParams.setCard_no(eaccUserinfo.getEaccount());
                accountOprationService.updateAccountSubjectInfo(accountSubjectInfoParams,
                        accountSubjectInfo.getPlatcust(),accountSubjectInfo.getPlat_no(),null,Ssubject.EACCOUNT.getCode());

                //处理完毕更新流水
                if(StringUtils.isEmpty(platplatcustRegister.getPlatcust())){
                    try{
                        logger.info("【电子账户开户】=========电子账户开户完成，发送通知短信，记录流水");
                        MsgModel msgModel=new MsgModel();
                        String mall_name=sysParameterService.querySysParameter(platplatcustRegister.getMall_no(), SysParamterKey.MALL_NAME);
                        msgModel.setOrder_no(platplatcustRegister.getOrder_no());
                        msgModel.setPlat_no(transTransreq.getPlat_no());
                        msgModel.setTrans_code(transTransreq.getTrans_code());
                        msgModel.setMobile(platplatcustRegister.getMobile());
                        String content=sysParameterService.querySysParameter(platplatcustRegister.getMall_no(),MsgContent.OPEN_ACCOUNT_NOTIFY.getMsg());
                        if(StringUtils.isNotBlank(content)){
                            msgModel.setMsgContent(String.format(content,mall_name));
                            //=========ccb参数==========
                            msgModel.setMsg_type(MsgType.OPEN_ACCOUNT_NOTIFY.getType());
                            msgModel.setAmount(null);
                            msgModel.setTrans_serial(transTransreq.getTrans_serial());
                            //==========================
                            sendMsgService.addMsgToQueue(msgModel);
                        }
                    }catch (Exception e){
                        logger.info("【短信发送失败】======="+e);
                    }
                }
                transTransreq.setReturn_code(BusinessMsg.SUCCESS);
                transTransreq.setReturn_msg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));
                transTransreq.setStatus(FlowStatus.SUCCESS.getCode());
                transReqService.insert(transTransreq);
                //返回结果
                platplatcustRegisterResponseData= new PlatplatcustRegisterResponseData();
                platplatcustRegisterResponseData.setPlatcust(eaccUserinfo.getMallcust());

                platplatcustRegisterResponseAsyn.setRecode(BusinessMsg.SUCCESS);
                platplatcustRegisterResponseAsyn.setRemsg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));
                platplatcustRegisterResponseAsyn.setPlatcust(eaccUserinfo.getMallcust());
            }else{
                logger.info("【电子账户开户】=========电子帐户开户失败，失败原因："+platcustOpenResponse.get("remsg"));
                throw new BusinessException(BusinessMsg.TIEDCARD_FAILED,"请求电子帐户开户失败，失败原因："+platcustOpenResponse.get("remsg"));
            }
        }catch (Exception e){
            //开户失败，删除已经开出的电子账户
            try{
                if(StringUtils.isEmpty(eacc_Account)){
                    userAccountExtService.delEaccAccount(platplatcustRegister.getPlatcust(), platplatcustRegister.getMall_no());
                }
            }catch (Exception ex){
                logger.info("【电子账户开户】=========电子账户开户失败，垃圾数据删除失败：",ex);
                //给管理员发送通知
                String content="";
                if(ex instanceof BusinessException){
                    content="(电子账户开户)电子账户开户失败，垃圾数据删除失败："+((BusinessException) ex).getErrorMsg();
                }else{
                    content="(电子账户开户)电子账户开户失败，垃圾数据删除失败："+ex.getMessage()+",platcust:"+ platplatcustRegister.getPlatcust();
                }
                sendMsgService.sendErrorToAdmin(content,transTransreq.getPlat_no());
            }
            BaseResponse baseResponse=new BaseResponse();
            if(e instanceof BusinessException){
                baseResponse=((BusinessException)e).getBaseResponse();
            }else{
                logger.error("【电子账户开户】=========电子账户开户处理失败："+e);
                baseResponse.setRecode(BusinessMsg.DATEBASE_EXCEPTION);
                baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.DATEBASE_EXCEPTION));
            }
            transTransreq.setReturn_code(baseResponse.getRecode());
            transTransreq.setReturn_msg(baseResponse.getRemsg());
            transTransreq.setStatus(FlowStatus.FAIL.getCode());
            transReqService.insert(transTransreq);
            platplatcustRegisterResponseAsyn.setRecode(baseResponse.getRecode());
            platplatcustRegisterResponseAsyn.setRemsg(baseResponse.getRemsg());
//            throw new BusinessException(baseResponse);
        }

        if(StringUtils.isNotBlank(platplatcustRegister.getNotify_url())){
            NotifyData notifyData=new NotifyData();
            notifyData.setMall_no(platplatcustRegister.getMall_no());
            notifyData.setNotifyUrl(platplatcustRegister.getNotify_url());
            notifyData.setNotifyContent(JSON.toJSONString(platplatcustRegisterResponseAsyn));
            notifyService.addNotify(notifyData);
        }

        return platplatcustRegisterResponseData;

    }
    @Override
    public EaccUserinfo checkUserInfo(String mall_no,String name,String id_card,String plat_no, boolean isMall) throws BusinessException {
        logger.info("开始检查用户是否已注册！");
        if(StringUtils.isBlank(name) || StringUtils.isBlank(id_card) || StringUtils.isBlank(mall_no)){
            logger.info("姓名，证件类型，证件号，集团号均不可为空，查询条件不满足，无法执行查询！");
            BaseResponse baseResponse=new BaseResponse();
            baseResponse.setRecode(BusinessMsg.METHODPARAMETER_ERROR);
            baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.METHODPARAMETER_ERROR)+"【姓名，证件类型，证件号均不可为空】");
            throw new BusinessException(baseResponse);
        }

        EaccUserinfoExample eaccUserinfoExample = new EaccUserinfoExample();
        EaccUserinfoExample.Criteria criteria = eaccUserinfoExample.createCriteria();
        criteria.andNameEqualTo(name);
        criteria.andId_codeEqualTo(id_card);
        criteria.andMall_noEqualTo(mall_no);
        List<String> enabledList=new ArrayList<>();
        enabledList.add(AcctStatus.ACTIVE.getCode());
        enabledList.add(AcctStatus.FORZEN.getCode());
        criteria.andEnabledIn(enabledList);
//        criteria.andEnabledEqualTo(Constants.ENABLED);

        List<EaccUserinfo> eaccUserInfoList = eaccUserInfoMapper.selectByExample(eaccUserinfoExample);

        if(eaccUserInfoList != null && eaccUserInfoList.size() > 0){
            EaccUserinfo eaccUserInfo = eaccUserInfoList.get(0);
            if(!isMall){
                if(plat_no==null || "".equals(plat_no)){
                    logger.info("平台号不可以为空，查询条件不满足，无法执行查询！");
                    BaseResponse baseResponse=new BaseResponse();
                    baseResponse.setRecode(BusinessMsg.METHODPARAMETER_ERROR);
                    baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.METHODPARAMETER_ERROR));
                    throw new BusinessException(baseResponse);
                }


                EaccAccountinfoExample eaccAccountinfoExample = new EaccAccountinfoExample();
                EaccAccountinfoExample.Criteria criteria2 = eaccAccountinfoExample.createCriteria();

                if(StringUtils.isNotBlank(plat_no))
                    criteria2.andPlat_noEqualTo(plat_no);

                criteria2.andMallcustEqualTo(eaccUserInfo.getMallcust());
                criteria2.andEnabledEqualTo(Constants.ENABLED);

                List<EaccAccountinfo> eaccAccountInfoList = eaccAccountinfoMapper.selectByExample(eaccAccountinfoExample);

                if(eaccAccountInfoList!=null && eaccAccountInfoList.size()>0){
                    return eaccUserInfo;
                }
            }else{
                return eaccUserInfo;
            }
        }
        return null;
    }

    public EaccUserinfo checkUserInfo(String mall_no,String name,String id_card,String plat_no) throws BusinessException {
        logger.info("开始检查用户是否已注册！");

        EaccUserinfoExample eaccUserinfoExample = new EaccUserinfoExample();
        EaccUserinfoExample.Criteria criteria = eaccUserinfoExample.createCriteria();
        criteria.andNameEqualTo(name);
        criteria.andId_codeEqualTo(id_card);
        criteria.andMall_noEqualTo(mall_no);
        List<String> enabledList=new ArrayList<>();
        enabledList.add(AcctStatus.ACTIVE.getCode());
        enabledList.add(AcctStatus.FORZEN.getCode());
        enabledList.add(AcctStatus.UNACTIVE.getCode());
        criteria.andEnabledIn(enabledList);
        eaccUserinfoExample.setOrderByClause(" enabled desc");

        List<EaccUserinfo> eaccUserInfoList = eaccUserInfoMapper.selectByExample(eaccUserinfoExample);

        if(eaccUserInfoList != null && eaccUserInfoList.size() > 0){
            return eaccUserInfoList.get(0);
        }
        return null;
    }

    public EaccUserinfo checkMallUserInfo(String mall_no,String org_name, String plat_no) throws BusinessException {
        logger.info("开始检查企业用户是否已注册！");

        EaccUserinfoExample eaccUserinfoExample = new EaccUserinfoExample();
        EaccUserinfoExample.Criteria criteria = eaccUserinfoExample.createCriteria();

        criteria.andOrg_nameEqualTo(org_name);
        criteria.andMall_noEqualTo(mall_no);
        List<String> enabledList=new ArrayList<>();
        enabledList.add(AcctStatus.ACTIVE.getCode());
        enabledList.add(AcctStatus.FORZEN.getCode());
        enabledList.add(AcctStatus.UNACTIVE.getCode());
        criteria.andEnabledIn(enabledList);

        List<EaccUserinfo> eaccUserInfoList = eaccUserInfoMapper.selectByExample(eaccUserinfoExample);

        if(eaccUserInfoList!=null && eaccUserInfoList.size()>0){
            return eaccUserInfoList.get(0);
        }
        return null;
    }

    @Override
    public EaccUserinfo checkMallUserInfo(String mall_no,String org_name, String plat_no, boolean isMall) throws BusinessException {
        logger.info("开始检查企业用户是否已注册！");
        if(StringUtils.isBlank(org_name) || StringUtils.isBlank(mall_no)){
            logger.info("企业名或集团号不可为空，查询条件不满足，无法执行查询！");
            BaseResponse baseResponse=new BaseResponse();
            baseResponse.setRecode(BusinessMsg.METHODPARAMETER_ERROR);
            baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.METHODPARAMETER_ERROR));
            throw new BusinessException(baseResponse);
        }

        EaccUserinfoExample eaccUserinfoExample = new EaccUserinfoExample();
        EaccUserinfoExample.Criteria criteria = eaccUserinfoExample.createCriteria();

        criteria.andOrg_nameEqualTo(org_name);
        criteria.andMall_noEqualTo(mall_no);
        List<String> enabledList=new ArrayList<>();
        enabledList.add(AcctStatus.ACTIVE.getCode());
        enabledList.add(AcctStatus.FORZEN.getCode());
        criteria.andEnabledIn(enabledList);

        List<EaccUserinfo> eaccUserInfoList = eaccUserInfoMapper.selectByExample(eaccUserinfoExample);

        if(eaccUserInfoList!=null && eaccUserInfoList.size()>0){
            EaccUserinfo eaccUserInfo=eaccUserInfoList.get(0);
            if(!isMall){
                if(plat_no==null || "".equals(plat_no)){
                    logger.info("平台号不可以为空，查询条件不满足，无法执行查询！");
                    BaseResponse baseResponse=new BaseResponse();
                    baseResponse.setRecode(BusinessMsg.METHODPARAMETER_ERROR);
                    baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.METHODPARAMETER_ERROR));
                    throw new BusinessException(baseResponse);
                }

                EaccAccountinfoExample eaccAccountinfoExample = new EaccAccountinfoExample();
                EaccAccountinfoExample.Criteria criteria2 = eaccAccountinfoExample.createCriteria();

                if(StringUtils.isNotBlank(plat_no))
                    criteria2.andPlat_noEqualTo(plat_no);

                criteria2.andMallcustEqualTo(eaccUserInfo.getMallcust());
                criteria2.andEnabledEqualTo(Constants.ENABLED);

                List<EaccAccountinfo> eaccAccountInfoList = eaccAccountinfoMapper.selectByExample(eaccAccountinfoExample);

                if(eaccAccountInfoList!=null && eaccAccountInfoList.size()>0){
                    return eaccUserInfo;
                }
            }else{
                return eaccUserInfo;
            }
        }
        return null;
    }

    @Override
    public boolean transferEaccount(TransferEaccountRequest transferEaccountRequest) throws BusinessException {
        //记录流水
        TransTransreq transTransreq=transReqService.getTransTransReq(transferEaccountRequest.clone(),
                TransConsts.CASH_TO_EACCOUNT_CODE,TransConsts.CASH_TO_EACCOUNT_NAME,false);
        transReqService.insert(transTransreq);

        try{
            //获取用户电子账户
            AccountSubjectInfo eAccountSubjectInfo=accountQueryService.queryFINANCINGPlatAccount(
                    transferEaccountRequest.getMall_no(),transferEaccountRequest.getPlatcust(),AccountType.ElectronicAccount.getCode(),
                    Tsubject.CASH.getCode(),Ssubject.EACCOUNT.getCode());

            //获取现金投资账户
            AccountSubjectInfo vAccountSubjectInfo=accountQueryService.queryAccount(transferEaccountRequest.getMer_no(),
                    transferEaccountRequest.getPlatcust(),Tsubject.CASH.getCode(),Ssubject.INVEST.getCode());
            if(eAccountSubjectInfo==null || vAccountSubjectInfo==null){
                //抛异常
                logger.info("【子账户现金转电子账户】==========现金账户或电子账户不存在，现金账户："+
                        vAccountSubjectInfo+",电子账户："+eAccountSubjectInfo);
                BaseResponse baseResponse=new BaseResponse();
                baseResponse.setRecode(BusinessMsg.INVALID_ACCOUNT);
                baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.INVALID_ACCOUNT)+"：现金账户或电子账户不存在");
                throw new BusinessException(baseResponse);
            }

            //虚拟账户数据修改
            EaccAccountamtlist eaccAccountamtlist=new EaccAccountamtlist();
            eaccAccountamtlist.setPlat_no(transferEaccountRequest.getMer_no());
            eaccAccountamtlist.setTrans_serial(transTransreq.getTrans_serial());
            eaccAccountamtlist.setTrans_date(transferEaccountRequest.getPartner_trans_date());
            eaccAccountamtlist.setTrans_time(transferEaccountRequest.getPartner_trans_time());
            eaccAccountamtlist.setPlatcust(vAccountSubjectInfo.getPlatcust());
            eaccAccountamtlist.setOppo_platcust(eAccountSubjectInfo.getPlatcust());
            eaccAccountamtlist=EaccAccountamtlistOptionUtil.setSubject(eaccAccountamtlist,
                    vAccountSubjectInfo.getSubject(),vAccountSubjectInfo.getSub_subject(),
                    eAccountSubjectInfo.getSubject(),eAccountSubjectInfo.getSub_subject());
            eaccAccountamtlist.setTrans_code(TransConsts.CASH_TO_EACCOUNT_CODE);
            eaccAccountamtlist.setTrans_name(TransConsts.CASH_TO_EACCOUNT_NAME);
            eaccAccountamtlist.setAmt(transferEaccountRequest.getAmt());
            eaccAccountamtlist.setOrder_no(transTransreq.getOrder_no());
            //虚拟账户转账
            accountTransferService.transfer(eaccAccountamtlist);
            transTransreq.setReturn_code(BusinessMsg.SUCCESS);
            transTransreq.setReturn_msg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));
        }catch (Exception e){
            BaseResponse baseResponse=new BaseResponse();
            if(e instanceof BusinessException){
                baseResponse=((BusinessException) e).getBaseResponse();
            }else{
                logger.error("【子账户现金转电子账户】==========数据库操作异常："+e);
                baseResponse.setRecode(BusinessMsg.DATEBASE_EXCEPTION);
                baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.DATEBASE_EXCEPTION));
            }
            transTransreq.setReturn_code(baseResponse.getRecode());
            transTransreq.setReturn_msg(baseResponse.getRemsg());
            transTransreq.setStatus(FlowStatus.FAIL.getCode());
            throw new BusinessException(baseResponse);
        }
        //更新流水
        transTransreq.setReturn_code(BusinessMsg.SUCCESS);
        transTransreq.setReturn_msg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));
        transTransreq.setStatus(FlowStatus.SUCCESS.getCode());
        transReqService.insert(transTransreq);
        return true;
    }

    @Override
    public PlatPaycode queryPayCodeByChannel(String mer_no, String channel) throws BusinessException {
        PlatPaycodeExample example=new PlatPaycodeExample();
        PlatPaycodeExample.Criteria criteria=example.createCriteria();
        criteria.andChannel_idEqualTo(channel);
        criteria.andPlat_noEqualTo(mer_no);
        List<PlatPaycode> platPaycodeList=platPaycodeMapper.selectByExample(example);
        if(platPaycodeList.size()>0){
            return platPaycodeList.get(0);
        }
        return null;
    }

    @Override
    public PlatplatcustRegisterResponseData openEaccountForRegularSubscribers(PlatplatcustRegisterRequest platplatcustRegisterCopy) throws BusinessException {
        logger.info("【电子账户开户】=========开始电子账户开户)");
        //记录流水
        TransTransreq transTransreq=transReqService.getTransTransReq(platplatcustRegisterCopy.clone(),TransConsts.PLATCUST_OPEN_CODE,
                TransConsts.PLATCUST_OPEN_NAME,false);
        transTransreq.setPlatcust(platplatcustRegisterCopy.getPlatcust());
        transReqService.insert(transTransreq);

        String platCust = platplatcustRegisterCopy.getPlatcust(); //集团客户号

        //说明是新用户
        try{
            //判断是否在集团注册
            EaccUserinfo eaccUserinfo = this.checkUserInfo(platplatcustRegisterCopy.getMall_no(), platplatcustRegisterCopy.getName(),
                    platplatcustRegisterCopy.getId_code(), platplatcustRegisterCopy.getMer_no(), true);
            //注册虚拟账户
            EaccCardinfo eaccCardInfo=null;
            String eacc_Account=null;
            AccountSubjectInfo accountSubjectInfo = new AccountSubjectInfo();
            if(null != eaccUserinfo){ //集团注册过
                logger.info("【电子账户开户】=========已注册账号："+eaccUserinfo.toString());
                eacc_Account=eaccUserinfo.getEaccount();
                if(StringUtils.isNotBlank(platCust) && !platCust.equals(eaccUserinfo.getMallcust())){
                    logger.info("【电子账户开户】=========该用户已经在集团注册过，但是电子帐户号不一致，真实电子账户号是：" + eaccUserinfo.getMallcust());
                    throw new BusinessException(BusinessMsg.PARAMETER_ERROR, "该用户已经在集团注册过，但是电子帐户号不一致，真实电子账户号是：" + eaccUserinfo.getMallcust());
                }
                //查询用户绑卡信息 是否绑卡
                eaccCardInfo = queryEaccCardInfo(eaccUserinfo.getMall_no(),
                        eaccUserinfo.getMallcust(),null,null);
                if(eaccCardInfo!=null && !platplatcustRegisterCopy.getCard_no().equals(eaccCardInfo.getCard_no())){
                    logger.info("【电子账户开户】=========该用户已绑卡，且卡号与当前绑卡不一致，欲开户卡号为："+
                            platplatcustRegisterCopy.getCard_no()+"，已绑卡号为："+eaccCardInfo.getCard_no());
                    throw new BusinessException(BusinessMsg.PARAMETER_ERROR, "该用户已绑卡，且卡号与当前绑卡不一致，欲开户卡号为："+
                            platplatcustRegisterCopy.getCard_no()+"，已绑卡号为："+eaccCardInfo.getCard_no()+"，请先解绑");
                }
                accountSubjectInfo.setPlatcust(eaccUserinfo.getMallcust());//集团客户号
            }
            accountSubjectInfo.setAccount_type(AccountType.ElectronicAccount.getCode()); //账户类型
            accountSubjectInfo.setPlat_no(platplatcustRegisterCopy.getMer_no()); //平台编号
            logger.info("【电子账户开户】=========开始开通虚拟账户");
            accountSubjectInfo = accountOprationService.register(accountSubjectInfo);

            //获取pay_code
            logger.info("【电子账户开户】=========查询渠道信息，pay_code:098");
            PlatPaycode platPaycode=queryPlatPaycode(platplatcustRegisterCopy.getMer_no(),"098");
            if(platPaycode==null){
                logger.info("【电子账户开户】=========没有渠道信息，pay_code：098");
                throw new BusinessException(BusinessMsg.NO_CHANNEL_INFORMATION,"没有渠道信息，pay_code：098");
            }
            //组装信息调用fundservice 电子账户开户
            Map<String,Object> params=new HashMap<String,Object>();

            params.put("partner_id",platPaycode.getPayment_plat_no());
            params.put("partner_serial_no",transTransreq.getTrans_serial());
            params.put("partner_trans_date",platplatcustRegisterCopy.getPartner_trans_date());
            params.put("partner_trans_time",platplatcustRegisterCopy.getPartner_trans_time());
//        params.put("trans_serial",transTransreq.getTrans_serial());
            params.put("card_no",platplatcustRegisterCopy.getCard_no());
            params.put("id_kind","0");
            params.put("id_no",platplatcustRegisterCopy.getId_code());
//        params.put("id_valid_type","2");
            params.put("terminal_type",(StringUtils.isEmpty(platplatcustRegisterCopy.getTerminal_type())?"0":platplatcustRegisterCopy.getTerminal_type()));
            String type_chan=sysParameterService.querySysParameter(platplatcustRegisterCopy.getMall_no(),SysParamterKey.TYPE_CHAN);
            params.put("type_chan",type_chan);
            params.put("mobile_tel",platplatcustRegisterCopy.getMobile());
            params.put("bank_acct_pwd",platplatcustRegisterCopy.getBank_acct_pwd());
            params.put("client_name",platplatcustRegisterCopy.getName());


            logger.info("【电子账户开户】=========开始请求E支付开户");
            PlatcustOpenResponse platcustOpenResponse= new PlatcustOpenResponse(); //TODO 杨磊 fundService.platcustOpenForRegularSubscribers(params,platplatcustRegister.getMall_no());
            if(platcustOpenResponse.getData()!=null && StringUtils.isNotBlank(platcustOpenResponse.getData().get(0).getBank_elec_no())){
                logger.info("【电子账户开户】=========请求第三方开户成功");
                //电子帐户开户成功
                //更新手机号码和电子账户信息
                logger.info("【电子账户开户】=========更新手机号码和电子账户信息");
                eaccUserinfo=queryEaccUserInfoByMallNoAndPlatcust(platplatcustRegisterCopy.getMall_no(),platplatcustRegisterCopy.getPlatcust());
                eaccUserinfo.setMobile(platplatcustRegisterCopy.getMobile());
                eaccUserinfo.setEaccount_flg(IsUse.YES.getCode());
                eaccUserinfo.setEaccount(platcustOpenResponse.getData().get(0).getBank_elec_no());
                eaccUserInfoMapper.updateByPrimaryKey(eaccUserinfo);

                //更新或添加绑卡信息
                if (null != eaccCardInfo) {
                    if(platplatcustRegisterCopy.getPre_mobile().equals(eaccCardInfo.getMobile())){
                        logger.info("【电子账户开户】=========绑卡信息不一致");
                        eaccCardInfo.setMobile(platplatcustRegisterCopy.getPre_mobile());
                        eaccCardInfo.setBindId(transTransreq.getTrans_serial());
                        eaccCardInfo.setRemark(BindCardType.EACCOPENBIND.getCode());
                        eaccCardInfo.setMallcust(null);
                        if(null == eaccCardInfo.getUpdate_by()) eaccCardInfo.setUpdate_by(SeqUtil.RANDOM_KEY);
                        eaccCardInfo.setUpdate_time(new Date());
                        eaccCardInfoMapper.updateByPrimaryKeySelective(eaccCardInfo);
                    }
                }else{
                    logger.info("【电子账户开户】=========添加绑卡信息");
                    eaccCardInfo=new EaccCardinfo();
                    eaccCardInfo.setMall_no(platplatcustRegisterCopy.getMall_no());
                    eaccCardInfo.setMallcust(eaccUserinfo.getMallcust());
                    eaccCardInfo.setStatus(CardStatus.ACTIVE.getCode());
                    eaccCardInfo.setMobile(platplatcustRegisterCopy.getPre_mobile());
                    eaccCardInfo.setCard_type(CardType.DEBIT.getCode());
                    eaccCardInfo.setCard_no(platplatcustRegisterCopy.getCard_no());
                    eaccCardInfo.setBindId(transTransreq.getTrans_serial());
                    eaccCardInfo.setPay_channel(platPaycode.getChannel_id());
                    eaccCardInfo.setRemark(BindCardType.EACCOPENBIND.getCode());
                    addBindCardInfo(eaccCardInfo);
                }

                //处理完毕更新流水
//                if(StringUtils.isEmpty(platplatcustRegister.getPlatcust())){
//                    logger.info("【电子账户开户】=========电子账户开户完成，发送通知短信，记录流水");
//                    MsgModel msgModel=new MsgModel();
//                    String mall_name=sysParameterService.querySysParameter(platplatcustRegister.getMall_no(),
//                            MsgContent.MALL_NAME_KEY.getMsg());
//                    msgModel.setOrder_no(platplatcustRegister.getOrder_no());
//                    msgModel.setPlat_no(transTransreq.getPlat_no());
//                    msgModel.setTrans_code(transTransreq.getTrans_code());
//                    msgModel.setMobile(platplatcustRegister.getMobile());
//                    msgModel.setMsgContent(String.format(MsgContent.OPEN_ACCOUNT_NOTIFY.getMsg(),mall_name));
//                    sendMsgService.addMsgToQueue(msgModel);
//                }
                transTransreq.setReturn_code(BusinessMsg.SUCCESS);
                transTransreq.setReturn_msg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));
                transTransreq.setStatus(FlowStatus.SUCCESS.getCode());
                transReqService.insert(transTransreq);
                //返回结果
                PlatplatcustRegisterResponseData platplatcustRegisterResponseData= new PlatplatcustRegisterResponseData();
                platplatcustRegisterResponseData.setPlatcust(eaccUserinfo.getMallcust());
                return platplatcustRegisterResponseData;
            }else{
                logger.info("【电子账户开户】=========电子帐户开户失败，失败原因："+platcustOpenResponse.getError_info());
                throw new BusinessException(BusinessMsg.TIEDCARD_FAILED,"请求电子帐户开户失败，失败原因："+platcustOpenResponse.getError_info());
            }
        }catch (Exception e){
            BaseResponse baseResponse=new BaseResponse();
            if(e instanceof BusinessException){
                baseResponse=((BusinessException)e).getBaseResponse();
            }else{
                logger.error("【电子账户开户】=========电子账户开户处理失败："+e);
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
    private  EaccUserinfo  getUserInfoData(BatchRegisterRequestDetail batchRegisterRequestDetail,EaccUserinfo userInfo,BaseRequest baseRequests,String seqNum){
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
    private EaccAccountinfo getAccountInfo(EaccAccountinfo accountinfo,BaseRequest baseRequests,String seqNum,String platcust){
        accountinfo.setMall_no(baseRequests.getMall_no());
        accountinfo.setPlat_no(baseRequests.getMer_no());
        accountinfo.setMallcust(seqNum);
        accountinfo.setPlatcust(platcust);
        return accountinfo;
    }

    public List<EaccCardinfo> queryCardInfoForMultiCards(String mall_no, String mallcust)throws BusinessException{
        List<EaccCardinfo> eaccCardInfoList= null;
        try {
            EaccCardinfoExample eaccCardinfoExample = new EaccCardinfoExample();
            EaccCardinfoExample.Criteria criteria = eaccCardinfoExample.createCriteria();

            if(StringUtils.isNotBlank(mall_no))
                criteria.andMall_noEqualTo(mall_no);

            if(StringUtils.isNotBlank(mallcust))
                criteria.andMallcustEqualTo(mallcust);

            criteria.andEnabledEqualTo(Constants.ENABLED);
            criteria.andStatusEqualTo(CardStatus.ACTIVE.getCode());

            eaccCardInfoList = eaccCardInfoMapper.selectByExample(eaccCardinfoExample);

        }catch (BusinessException e){
            BaseResponse baseResponse=new BaseResponse();
            baseResponse.setRecode(BusinessMsg.DATEBASE_EXCEPTION);
            baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.DATEBASE_EXCEPTION));
            throw new BusinessException(baseResponse);
        }
        return eaccCardInfoList;
    }

    @Override
    public List<EaccCardinfo> queryCardInfoForMultiCards(String mall_no, String mallcust, String... status) throws BusinessException {
        List<EaccCardinfo> eaccCardInfoList= null;
        try {
            EaccCardinfoExample eaccCardinfoExample = new EaccCardinfoExample();
            EaccCardinfoExample.Criteria criteria = eaccCardinfoExample.createCriteria();

            if(StringUtils.isNotBlank(mall_no))
                criteria.andMall_noEqualTo(mall_no);

            if(StringUtils.isNotBlank(mallcust))
                criteria.andMallcustEqualTo(mallcust);

            criteria.andEnabledIn(Arrays.asList(status));
            criteria.andStatusEqualTo(CardStatus.ACTIVE.getCode());

            eaccCardInfoList = eaccCardInfoMapper.selectByExample(eaccCardinfoExample);

        }catch (BusinessException e){
            BaseResponse baseResponse=new BaseResponse();
            baseResponse.setRecode(BusinessMsg.DATEBASE_EXCEPTION);
            baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.DATEBASE_EXCEPTION));
            throw new BusinessException(baseResponse);
        }
        return eaccCardInfoList;
    }

    @Override
    public FirstCertResponse firstCert(FirstCertRequest firstCertRequest) {
        FirstCertResponse firstCertResponse=new FirstCertResponse();
        firstCertResponse.setRecode(BusinessMsg.FAIL);
        firstCertResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.FAIL));
        //随机生成0.1到0.5之间的验证金额
        BigDecimal random_amt= MathUtil.getRandom(10,40).divide(new BigDecimal(100));
        DecimalFormat df = new DecimalFormat("0.00");
        BigDecimal amt=new BigDecimal(df.format(random_amt));

        TransTransreq transTransReq=transReqService.getTransTransReq(firstCertRequest.clone(),
                TransConsts.FIRST_CARDCERT_CODE,TransConsts.FIRST_CARDCERT_NAME,false);
        transTransReq.setPlatcust(firstCertRequest.getPlatcust());
        transReqService.insert(transTransReq);
        try {
            //todo 查询账户是否已经绑定
            EaccUserinfo eaccUserinfo= queryEaccUserInfoByMallNoAndPlatcust(firstCertRequest.getMall_no(),firstCertRequest.getPlatcust());
            if("1".equals(eaccUserinfo.getIs_card_bind())){
                logger.info("==============该对公账户已绑卡，不能重复调用该接口");
                throw new BusinessException(BusinessMsg.PARAMETER_ERROR, "该对公账户已绑卡，不能重复调用该接口");
            }

            EaccCardcert eaccCardcert=new EaccCardcert();
            eaccCardcert.setPlat_no(firstCertRequest.getMer_no());
            eaccCardcert.setPlatcust(firstCertRequest.getPlatcust());
            eaccCardcert.setOrder_no(firstCertRequest.getOrder_no());
            eaccCardcert.setAmt(amt);
            eaccCardcert.setAcct_name(firstCertRequest.getAcct_name());
            eaccCardcert.setAcct_no(firstCertRequest.getAcct_no());
            eaccCardcert.setOpen_branch(firstCertRequest.getOpen_branch());
            eaccCardcert.setOrg_no(firstCertRequest.getOrg_no());
            eaccCardcert.setStatus("0");//状态：0-待认证1-已认证

            int result=eaccCardcertMapper.insert(eaccCardcert);
            if(0==result){
                throw new BusinessException(BusinessMsg.DATEBASE_EXCEPTION,BusinessMsg.getMsg(BusinessMsg.DATEBASE_EXCEPTION));
            }
        }catch (Exception e){
            BaseResponse baseResponse=new BaseResponse();
            if(e instanceof BusinessException){
                baseResponse=((BusinessException)e).getBaseResponse();
            }else{
                logger.error("首笔认证失败："+e);
                baseResponse.setRecode(BusinessMsg.DATEBASE_EXCEPTION);
                baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.DATEBASE_EXCEPTION));
            }
            transTransReq.setReturn_code(baseResponse.getRecode());
            transTransReq.setReturn_msg(baseResponse.getRemsg());
            transTransReq.setStatus(FlowStatus.FAIL.getCode());
            transReqService.insert(transTransReq);
            throw new BusinessException(baseResponse);
        }
        transTransReq.setReturn_code(BusinessMsg.SUCCESS);
        transTransReq.setReturn_msg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));
        transTransReq.setStatus(FlowStatus.SUCCESS.getCode());
        transReqService.insert(transTransReq);

        firstCertResponse.setAmt(amt);
        firstCertResponse.setRecode(BusinessMsg.SUCCESS);
        firstCertResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));
        return firstCertResponse;
    }

    @Override
    public BaseResponse queryfirstCert(QueryFirstCertRequest queryFirstCertRequest) {
        BaseResponse baseResponse=new BaseResponse();
        baseResponse.setRecode(BusinessMsg.FAIL);
        baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.FAIL));
        TransTransreq transTransReq=transReqService.getTransTransReq(queryFirstCertRequest.clone(),
                TransConsts.QUERY_FIRST_CARDCERT_CODE,TransConsts.QUERY_FIRST_CARDCERT_NAME,false);
        transTransReq.setNotify_url(queryFirstCertRequest.getNotify_url());
        transReqService.insert(transTransReq);

        try {

            //查询原首笔认证流水记录
            EaccCardcertExample eaccCardcertExample=new EaccCardcertExample();
            EaccCardcertExample.Criteria criteria=eaccCardcertExample.createCriteria();
            criteria.andPlat_noEqualTo(queryFirstCertRequest.getMer_no());
            criteria.andOrder_noEqualTo(queryFirstCertRequest.getOrg_order_no());
            List<EaccCardcert> eaccCardcerts= eaccCardcertMapper.selectByExample(eaccCardcertExample);
            if(null==eaccCardcerts||eaccCardcerts.size()>1){
                logger.info("==============原订单不存在或者订单号记录大于1");
                throw new BusinessException(BusinessMsg.ORDER_NOEXISTENT,BusinessMsg.getMsg(BusinessMsg.ORDER_NOEXISTENT));
            }
            EaccCardcert eaccCardcert=eaccCardcerts.get(0);

            EaccUserinfo eaccUserinfo= queryEaccUserInfoByMallNoAndPlatcust(queryFirstCertRequest.getMall_no(),eaccCardcert.getPlatcust());
            if("1".equals(eaccUserinfo.getIs_card_bind())){
                logger.info("==============该对公账户已绑卡，不能重复调用该接口");
                throw new BusinessException(BusinessMsg.PARAMETER_ERROR, "该对公账户已绑卡，不能重复调用该接口");
            }

            logger.info("==============原认证订单信息："+eaccCardcert);
            if(!"0".equals(eaccCardcert.getStatus())){
                logger.info("==============原订单状态非待认证状态");
                throw new BusinessException(BusinessMsg.PARAMETER_ERROR,BusinessMsg.getMsg(BusinessMsg.PARAMETER_ERROR)+",原订单状态非待认证状态");
            }
            //  查询对应平台清算账户进出流水，对比是否存在对应对公账户，对应金额的打款流水
            logger.info("==============开始查询平台清算账户流水记录");
            //todo  将行内查询url放到数据库配置表中，再查询出来
            String host = sysParameterService.querySysParameter(queryFirstCertRequest.getMall_no(), URLConfigUtil.BANK_PAY_URL);
            String url = sysParameterService.querySysParameter(queryFirstCertRequest.getMall_no(), URLConfigUtil.EPAY_QUERY_ACCDETAIL);
            List<Map<String,Object>> bankPayRecordDataList = new ArrayList<Map<String,Object>>();
            Map<String,Object> bankpayMap=new HashMap<String,Object>();
            bankpayMap.put("acct_no",PlatAccType.PLATCLEAR);
            bankpayMap.put("flag","CUR");
            bankpayMap.put("from_date",DateUtils.getyyyyMMddDate());
            bankpayMap.put("to_date",DateUtils.getyyyyMMddDate());
            bankpayMap.put("type","C");
            bankPayRecordDataList.add(bankpayMap);

            Map<String, Object> sendParams = new HashMap<String, Object>();
            sendParams.put("trandata",bankPayRecordDataList );
            sendParams.put("third_batch_no", SeqUtil.getSeqNum());//合作商流水号为空，入账结果不确定
            sendParams.put("third_no", queryFirstCertRequest.getOrder_no());
            sendParams.put("tran_type","query");
            sendParams.put("cert_sign", "testsign");
            sendParams.put("host", host);
            sendParams.put("url", url);

            logger.info("==============请求参数:"+sendParams);
            Map<String,Object> resultMap=adapterService.queryListOfCompanyTransfer(sendParams);
            logger.info("==============查询结果:"+resultMap);

            if(null==resultMap||null==resultMap.get("array_tran_list")){
                logger.error("查询结果为空");
                throw new BusinessException(BusinessMsg.FAIL,"未查询到账户打款记录");
            }
            List<Map<String,Object>> array_tran_list= (List)resultMap.get("array_tran_list");
             boolean ifExistRecord=false;
            for(Map<String,Object> item:array_tran_list){
                if(item.get("oppo_acct").equals(eaccCardcert.getAcct_no())&&
                        new BigDecimal(item.get("tran_amt").toString()).compareTo(eaccCardcert.getAmt())==0){
                   ifExistRecord=true;
                  break;
             }
           }
           if(!ifExistRecord){
               logger.error("未查询到账户打款记录");
                throw new BusinessException(BusinessMsg.FAIL,"未查询到账户打款记录");
           }

            //假设存在流水且验证无误，则绑卡成功
            EaccCardinfo eaccCardinfo=new EaccCardinfo();
            eaccCardinfo.setStatus(CardStatus.ACTIVE.getCode());
            eaccCardinfo.setRemark(BindCardType.ORGBINGCARD.getCode());
            eaccCardinfo.setAcct_name(eaccCardcert.getAcct_name());
            eaccCardinfo.setCard_no(eaccCardcert.getAcct_no());
            eaccCardinfo.setOrg_no(eaccCardcert.getOrg_no());
            eaccCardinfo.setOpen_branch(eaccCardcert.getOpen_branch());

            eaccCardinfo.setMall_no(queryFirstCertRequest.getMall_no());
            eaccCardinfo.setMallcust(eaccCardcert.getPlatcust());
            eaccCardinfo.setBindId(transTransReq.getTrans_serial());
            addBindCardInfo(eaccCardinfo);
            logger.info("==============添加绑卡:"+eaccCardinfo);
            //更新首笔认证订单状态
            eaccCardcert.setStatus("1");
            eaccCardcertMapper.updateByPrimaryKey(eaccCardcert);

            //将验证金额从清算户转到存管户
            logger.info("==============开始调用行内转账业务==============");
            BaseResponse chargeResponse= new BaseResponse(); //TODO 杨磊 fundService.transAmtToPublicAccount(queryFirstCertRequest,eaccCardcert,transTransReq);
            //将线下还款结果以及响应的回调参数以|分割并保存于order_no字段中，返回给上一层 参数顺序： 状态|金额|platcust
            logger.info("==============线下转账结果=============="+chargeResponse.toString());
            String responseMsg=chargeResponse.getOrder_no()+"|"+eaccCardcert.getAmt()+"|"+eaccCardcert.getPlatcust();
            baseResponse.setOrder_no(responseMsg);

        } catch (Exception e) {
            if(e instanceof BusinessException){
                baseResponse=((BusinessException)e).getBaseResponse();
            }else{
                logger.error("首笔认证查询失败："+e);
                baseResponse.setRecode(BusinessMsg.DATEBASE_EXCEPTION);
                baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.DATEBASE_EXCEPTION));
            }
            transTransReq.setReturn_code(baseResponse.getRecode());
            transTransReq.setReturn_msg(baseResponse.getRemsg());
            transTransReq.setStatus(FlowStatus.FAIL.getCode());
            transReqService.insert(transTransReq);
            throw new BusinessException(baseResponse);
        }
        transTransReq.setReturn_code(BusinessMsg.SUCCESS);
        transTransReq.setReturn_msg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));
        transTransReq.setStatus(FlowStatus.SUCCESS.getCode());//模拟环境无法划账，暂定成功
        transReqService.insert(transTransReq);

        baseResponse.setRecode(BusinessMsg.SUCCESS);
        baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));
        return baseResponse;
    }

    @Override
    public EaccCardinfo queryCompanyCard(String mall_no, String mallcust) throws BusinessException {
        EaccCardinfo eaccCardInfo = null;
        try {
            EaccCardinfoExample eaccCardinfoExample = new EaccCardinfoExample();
            EaccCardinfoExample.Criteria criteria = eaccCardinfoExample.createCriteria();

            if(StringUtils.isNotBlank(mall_no))
                criteria.andMall_noEqualTo(mall_no);

            if(StringUtils.isNotBlank(mallcust))
                criteria.andMallcustEqualTo(mallcust);

            criteria.andEnabledEqualTo(Constants.ENABLED);
            criteria.andStatusNotEqualTo(CardStatus.FORZEN.getCode());

            List<EaccCardinfo> eaccCardInfoList = eaccCardInfoMapper.selectByExample(eaccCardinfoExample);

            if(eaccCardInfoList!=null && eaccCardInfoList.size()>0){
                eaccCardInfo=eaccCardInfoList.get(0);
            }
        }catch (BusinessException e){
            BaseResponse baseResponse=new BaseResponse();
            baseResponse.setRecode(BusinessMsg.DATEBASE_EXCEPTION);
            baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.DATEBASE_EXCEPTION));
            throw new BusinessException(baseResponse);
        }

        return eaccCardInfo;
    }

    @Override
    public String fourElementsRegister(RegisterRequest4 registerRequest4) throws BusinessException{
        TransTransreq transTransreq = transReqService.getTransTransReq(registerRequest4.clone(),TransConsts.APPLY_4ELEMENTS_REGISTER_CODE,
                TransConsts.APPLY_4ELEMENTS_REGISTER_NAME, false);
        String transSerial = transTransreq.getTrans_serial();
        transTransreq.setStatus(FlowStatus.REQUESTSUCCESS.getCode());
        try{
            transTransreq.setStatus(FlowStatus.REQUESTSUCCESS.getCode());
            transTransreq.setNotify_url(registerRequest4.getNotify_url());
            logger.info("【四要素开户】检验是否已经开户");
            EaccUserinfo eaccUserinfo = queryEaccUserInfo(registerRequest4.getMall_no(),null,
                    registerRequest4.getId_code(),registerRequest4.getName());
            if(eaccUserinfo != null){
                logger.info("【四要素开户】用户已经开户");
                transTransreq.setStatus(FlowStatus.FAIL.getCode());
                transTransreq.setReturn_code(BusinessMsg.FAIL);
                transTransreq.setReturn_msg(BusinessMsg.getMsg(BusinessMsg.FAIL));
                transTransreq.setRemark("该用户已开户，platcust:" + eaccUserinfo.getMallcust());
                BaseResponse baseResponse = transReqService.insert(transTransreq);
                if(baseResponse != null){
                    transTransreq.setTrans_serial(baseResponse.getTrans_serial());
                }
                throw new BusinessException(BusinessMsg.REPEAT_REGISTER, BusinessMsg.getMsg(BusinessMsg.REPEAT_REGISTER));
            }
            BaseResponse baseResponse = transReqService.insert(transTransreq);
            if(baseResponse == null){
                logger.info("【四要素开户】第一次插入，存入redis流水号：" + transTransreq.getTrans_serial());
                CacheUtil.getCache().set(Constants.OPEN_ACCOUNT_PARAMS_KEY + transTransreq.getTrans_serial(), JSON.toJSONString(registerRequest4));
                //保存24小时
                CacheUtil.getCache().expire(Constants.OPEN_ACCOUNT_PARAMS_KEY + transTransreq.getTrans_serial(), 24 * 3600);
            }else {
                transSerial = baseResponse.getTrans_serial();
            }
        }catch (Exception e){
            logger.info("【四要素开户】异常", e);
            transTransreq.setStatus(OrderStatus.FAIL.getCode());
            transTransreq.setReturn_code(BusinessMsg.FAIL);
            transTransreq.setReturn_msg(BusinessMsg.getMsg(BusinessMsg.FAIL));
            transReqService.insert(transTransreq);
            if(e instanceof BusinessException){
                throw e;
            }else {
                throw new BusinessException(BusinessMsg.RUNTIME_EXCEPTION, BusinessMsg.getMsg(BusinessMsg.RUNTIME_EXCEPTION));
            }
        }
        return transSerial;
    }

    /**
     * 更新用户充值信息表
     * @param rwRecharge
     * @return Boolean
     * @throws BusinessException
     */
    @Transactional
    @Override
    public Boolean updateRwRecharge(RwRecharge rwRecharge) throws BusinessException{
        RwRechargeExample example=new RwRechargeExample();
        RwRechargeExample.Criteria criteria=example.createCriteria();

        if(StringUtils.isNotBlank(rwRecharge.getPlat_no()))
            criteria.andPlat_noEqualTo(rwRecharge.getPlat_no());

        if(StringUtils.isBlank(rwRecharge.getOrder_no())){
            BaseResponse baseResponse=new BaseResponse();
            baseResponse.setRecode(BusinessMsg.DATEBASE_EXCEPTION);
            baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.DATEBASE_EXCEPTION) + "订单号传输失败 为空，用户充值信息更新失败");
            throw new BusinessException(baseResponse);
        }

        if(null!=rwRecharge.getPlatcust()){
            criteria.andPlatcustEqualTo(rwRecharge.getPlatcust());
        }

        criteria.andOrder_noEqualTo(rwRecharge.getOrder_no());
        logger.info("更新充值信息表》》》订单号为"+rwRecharge.getOrder_no());
        criteria.andEnabledEqualTo(Constants.ENABLED);
        rwRecharge.setId(null);
        rwRecharge.setOrder_no(null);
        rwRecharge.setTrans_serial(null);
        int upreturn= rwRechargeMapper.updateByExampleSelective(rwRecharge,example);
        if(upreturn<=0){
            BaseResponse baseResponse=new BaseResponse();
            baseResponse.setRecode(BusinessMsg.DATEBASE_EXCEPTION);
            baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.DATEBASE_EXCEPTION) + "用户充值信息更新失败");
            throw new BusinessException(baseResponse);
        }else if(upreturn>1){
            BaseResponse baseResponse=new BaseResponse();
            baseResponse.setRecode(BusinessMsg.DATEBASE_EXCEPTION);
            baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.DATEBASE_EXCEPTION) + "数据库有重复的订单号");
            throw new BusinessException(baseResponse);
        }
        return true;
    }

    @Override
    public void addEaccUserAuth(EaccUserauth eaccUserauth) throws BusinessException {
        eaccUserauth.setCreate_time(DateUtils.today());
        eaccUserauth.setEnabled(Constants.ENABLED);
        Integer insertRows = eaccUserauthMapper.insert(eaccUserauth);
        if (insertRows <= 0) {
            throw new BusinessException(BusinessMsg.DATA_INSERT_FAILED, BusinessMsg.getMsg(BusinessMsg.DATA_INSERT_FAILED));
        }
    }
    @Override
    public   List<EaccUserauth>  listEaccUserAuth(String mallno,String platno, String platcust) throws BusinessException {
        EaccUserauthExample eaccUserauthExample = new EaccUserauthExample();
        EaccUserauthExample.Criteria criteria = eaccUserauthExample.createCriteria();
        criteria.andMall_noEqualTo(mallno);
        if (!platno.equals(mallno)) {
            criteria.andPlat_noEqualTo(platno);
        }
        criteria.andPlatcustEqualTo(platcust);
        criteria.andEnabledEqualTo(Constants.ENABLED);
        List<EaccUserauth> eaccUserauths = eaccUserauthMapper.selectByExample(eaccUserauthExample);
        if (eaccUserauths == null) {
            throw new BusinessException(BusinessMsg.UNKNOW_ERROE, BusinessMsg.getMsg(BusinessMsg.UNKNOW_ERROE));
        }
        return eaccUserauths;
    }
    @Override
    public void updateEaccUserAuth(EaccUserauth updateEaccUserauth) throws BusinessException {
        eaccUserauthMapper.updateByPrimaryKeySelective(updateEaccUserauth);
    }

    @Override
    public void updateEaccUserAuthByPlatcust(EaccUserauth updateEaccUserauth) throws BusinessException {
        EaccUserauthExample example=new EaccUserauthExample();
        EaccUserauthExample.Criteria criteria=example.createCriteria();
        criteria.andMall_noEqualTo(updateEaccUserauth.getMall_no());
        criteria.andPlat_noEqualTo(updateEaccUserauth.getPlat_no());
        criteria.andPlatcustEqualTo(updateEaccUserauth.getPlatcust());
    }

    @Override
    public String threeElementsRegister(RegisterRequest3 registerRequest3) {
        TransTransreq transTransreq = transReqService.getTransTransReq(registerRequest3.clone(), TransConsts.APPLY_3ELEMENTS_REGISTER_CODE,
                TransConsts.APPLY_3ELEMENTS_REGISTER_NAME, false);
        String transSerial = transTransreq.getTrans_serial();
        transTransreq.setStatus(FlowStatus.REQUESTSUCCESS.getCode());
        try{
            transTransreq.setStatus(FlowStatus.REQUESTSUCCESS.getCode());
            transTransreq.setNotify_url(registerRequest3.getNotify_url());
            logger.info("【三要素开户】检验是否已经开户");
            EaccUserinfo eaccUserinfo = queryEaccUserInfo(registerRequest3.getMall_no(),null,
                    registerRequest3.getId_code(),registerRequest3.getName());
            if(eaccUserinfo != null){
                logger.info("【三要素开户】用户已经开户");
                transTransreq.setStatus(FlowStatus.FAIL.getCode());
                transTransreq.setReturn_code(BusinessMsg.FAIL);
                transTransreq.setReturn_msg(BusinessMsg.getMsg(BusinessMsg.FAIL));
                transTransreq.setRemark("该用户已开户，platcust:" + eaccUserinfo.getMallcust());
                transReqService.insert(transTransreq);
                throw new BusinessException(BusinessMsg.REPEAT_REGISTER, BusinessMsg.getMsg(BusinessMsg.REPEAT_REGISTER));
            }
            BaseResponse baseResponse = transReqService.insert(transTransreq);
            if(baseResponse == null){
                logger.info("【三要素开户】第一次插入，存入redis流水号：" + transTransreq.getTrans_serial());
                CacheUtil.getCache().set(Constants.OPEN_ACCOUNT_PARAMS_KEY + transTransreq.getTrans_serial(), JSON.toJSONString(registerRequest3));
                //保存24小时
                CacheUtil.getCache().expire(Constants.OPEN_ACCOUNT_PARAMS_KEY + transTransreq.getTrans_serial(), 24 * 3600);
            }else {
                transSerial = baseResponse.getTrans_serial();
            }
        }catch (Exception e){
            logger.info("【三要素开户】异常", e);
            transTransreq.setStatus(OrderStatus.FAIL.getCode());
            transTransreq.setReturn_code(BusinessMsg.FAIL);
            transTransreq.setReturn_msg(BusinessMsg.getMsg(BusinessMsg.FAIL));
            transReqService.insert(transTransreq);
            if(e instanceof BusinessException){
                throw e;
            }else {
                throw new BusinessException(BusinessMsg.RUNTIME_EXCEPTION, BusinessMsg.getMsg(BusinessMsg.RUNTIME_EXCEPTION));
            }
        }
        return transSerial;
    }

    /**
     * 修改预留手机号
     * @param changePreMobileRequest
     * @return
     */
    @Override
    public BaseResponse modifyMobile(ChangePreMobileRequest changePreMobileRequest) {
        BaseResponse baseResponse = new BaseResponse();
        TransTransreq transTransreq = transReqService.getTransTransReq(changePreMobileRequest.clone(), TransConsts.UPDATE_PRE_MOBILE_CODE,
                TransConsts.UPDATE_PRE_MOBILE_NAME, false);
        transTransreq.setPlatcust(changePreMobileRequest.getPlatcust());
        transReqService.insert(transTransreq);
        //查询用户信息
        EaccUserinfo userinfo = queryEaccUserInfo(changePreMobileRequest.getMall_no(), changePreMobileRequest.getPlatcust(),null , null);
        try {
            logger.info("【修改预留手机号】插入流水");
            if(userinfo == null){
                logger.info("【修改预留手机号】用户不存在==>platcust:" +
                        changePreMobileRequest.getPlatcust() + "card_no:" + changePreMobileRequest.getCard_no_old());
                transTransreq.setStatus(FlowStatus.FAIL.getCode());
                transTransreq.setReturn_code(BusinessMsg.FAIL);
                transTransreq.setReturn_msg(BusinessMsg.getMsg(BusinessMsg.FAIL));
                transTransreq.setRemark("该用户不存在，platcust：" + changePreMobileRequest.getPlatcust() +
                        ",card_no:" + changePreMobileRequest.getCard_no_old());
                transReqService.insert(transTransreq);
                baseResponse.setRecode(BusinessMsg.INVALID_ACCOUNT);
                baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.INVALID_ACCOUNT));
                return baseResponse;
            }
            List<EaccCardinfo> eaccCardinfoList = accountSearchService.queryEaccCardInfo(changePreMobileRequest.getMall_no(),
                    changePreMobileRequest.getPlatcust(), changePreMobileRequest.getCard_no_old(),changePreMobileRequest.getMobile_old(), null);
            if (eaccCardinfoList.size() == 0) {
                logger.info("【修改预留手机号】用户未绑定此卡==>platcust:" +
                        changePreMobileRequest.getPlatcust() + "card_no:" + changePreMobileRequest.getCard_no_old());
                transTransreq.setStatus(FlowStatus.FAIL.getCode());
                transTransreq.setReturn_code(BusinessMsg.FAIL);
                transTransreq.setReturn_msg(BusinessMsg.getMsg(BusinessMsg.FAIL));
                transTransreq.setRemark("用户未绑定此卡，platcust：" + changePreMobileRequest.getPlatcust() +
                        ",card_no:" + changePreMobileRequest.getCard_no_old());
                transReqService.insert(transTransreq);
                baseResponse.setRecode(BusinessMsg.TIEDCARDCHANNEL_NO);
                baseResponse.setRemsg("卡号或预留手机号错误");
                return baseResponse;
            }
//            Integer num=eaccCardinfoList.size();
//            for(EaccCardinfo cardinfo:eaccCardinfoList){
//                if (changePreMobileRequest.getMobile_old().equals(cardinfo.getMobile())) {
//                    break;
//                }
//                num--;
//            }
//            if(num==0){
//                logger.info("【修改预留手机号】传入的手机号与已绑定的手机号不一致");
//                transTransreq.setStatus(FlowStatus.FAIL.getCode());
//                transTransreq.setReturn_code(BusinessMsg.FAIL);
//                transTransreq.setReturn_msg(BusinessMsg.getMsg(BusinessMsg.FAIL));
//                transTransreq.setRemark("传入的手机号与已绑定的手机号不一致");
//                transReqService.insert(transTransreq);
//                baseResponse.setRecode(BusinessMsg.PHONE_NUMBER_ERROR);
//                baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.PHONE_NUMBER_ERROR));
//                return baseResponse;
//            }
            logger.info("【修改预留手机号】查询pay_code");
            PlatPaycode platPaycode = queryPlatPaycode(changePreMobileRequest.getMer_no(), changePreMobileRequest.getPay_code());
            if (platPaycode == null) {
                logger.info("【修改预留手机号】platPaycode不存在");
                transTransreq.setStatus(FlowStatus.FAIL.getCode());
                transTransreq.setReturn_code(BusinessMsg.FAIL);
                transTransreq.setReturn_msg(BusinessMsg.getMsg(BusinessMsg.FAIL));
                transTransreq.setRemark("platPaycode不存在,pay_code = " + changePreMobileRequest.getPay_code());
                transReqService.insert(transTransreq);
                baseResponse.setRecode(BusinessMsg.NO_CHANNEL_INFORMATION);
                baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.NO_CHANNEL_INFORMATION));
                return baseResponse;
            }
            logger.info("【修改预留手机号】开始鉴权");
            Map<String, Object> map = new HashMap<>();
            map.put("partner_id", platPaycode.getPayment_plat_no());
            map.put("partner_serial_no", transTransreq.getTrans_serial());
            map.put("partner_trans_date",transTransreq.getTrans_date());
            map.put("partner_trans_time",transTransreq.getTrans_time());
            map.put("channelId", platPaycode.getChannel_id());
            map.put("mall_no", changePreMobileRequest.getMall_no());
            map.put("mer_no", changePreMobileRequest.getMer_no());
            map.put("plat_cust", changePreMobileRequest.getPlatcust());
            map.put("card_no",changePreMobileRequest.getCard_no_old());
            map.put("mobile",changePreMobileRequest.getPre_mobile());
            //建行差异字段
            map.put("sendercomp_id",platPaycode.getPayment_plat_no());
            map.put("targetcomp_id","91000");
            EaccCardinfo eaccCardinfo=eaccCardinfoList.get(0);
            map.put("original_serial_no",eaccCardinfo.getBindId());
            map.put("host",sysParameterService.querySysParameter(changePreMobileRequest.getMall_no(), URLConfigUtil.EPAY_SERVER_KEY));
            map.put("url",sysParameterService.querySysParameter(changePreMobileRequest.getMall_no(), URLConfigUtil.PHONE_CHANGE_VERIFY_KEY));
            Map<String, Object> modifyMobileResponse = adapterService.modifyMobile(map);

            if(!BusinessMsg.SUCCESS.equals( modifyMobileResponse.get("recode").toString())){
                logger.info("【修改预留手机号】三方返回状态不是成功状态");
                baseResponse.setRecode(modifyMobileResponse.get("recode").toString());
                baseResponse.setRemsg(modifyMobileResponse.get("remsg").toString());
                throw new BusinessException(baseResponse);
            }
            logger.info("【修改预留手机号】无短验验证成功，修改用户的预留手机号");
            try{
                for(EaccCardinfo cardinfo:eaccCardinfoList){
                    cardinfo.setMobile(changePreMobileRequest.getPre_mobile());
                    if(null == cardinfo.getUpdate_by()) cardinfo.setUpdate_by(SeqUtil.RANDOM_KEY);
                    cardinfo.setUpdate_time(new Date());
                    eaccCardInfoMapper.updateByPrimaryKeySelective(cardinfo);
                }
                if (changePreMobileRequest.getCard_no_old().equals(userinfo.getDefault_card_no())) {
                    logger.info("【修改预留手机号】该卡号是默认卡号");
                    userinfo.setDefault_mobile(changePreMobileRequest.getPre_mobile());
                    userinfo.setUpdate_time(new Date());
                    eaccUserInfoMapper.updateByPrimaryKeySelective(userinfo);
                }
            }catch (Exception e){
                baseResponse.setRecode(BusinessMsg.DATEBASE_EXCEPTION);
                baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.DATEBASE_EXCEPTION));
                throw new BusinessException(baseResponse);
            }

            try {
                MsgModel msgModel = new MsgModel();
                String mall_name = sysParameterService.querySysParameter(changePreMobileRequest.getMall_no(), SysParamterKey.MALL_NAME);
                String msg_content = sysParameterService.querySysParameter(changePreMobileRequest.getMall_no(), MsgContent.UPDATE_PRE_MOBILE_NOTIFY.getMsg());
                msgModel.setOrder_no(transTransreq.getOrder_no());
                msgModel.setPlat_no(transTransreq.getPlat_no());
                msgModel.setTrans_code(transTransreq.getTrans_code());
                msgModel.setMobile(changePreMobileRequest.getPre_mobile());
                msgModel.setMall_no(changePreMobileRequest.getMall_no());
                msgModel.setMsgContent(String.format(msg_content, mall_name));
                sendMsgService.addMsgToQueue(msgModel);
            }catch (Exception e){
                logger.info("【变更预留手机号短信发送异常】-->order_no:"+transTransreq.getOrder_no(),e);
            }


        }catch (BusinessException e){
            transTransreq.setStatus(FlowStatus.FAIL.getCode());
            transTransreq.setReturn_code(baseResponse.getRecode());
            transTransreq.setReturn_msg(baseResponse.getRemsg());
            transReqService.insert(transTransreq);
            baseResponse.setRecode(e.getErrorCode());
            baseResponse.setRemsg(e.getErrorMsg());
            return baseResponse;
        }
        transTransreq.setStatus(FlowStatus.SUCCESS.getCode());
        transTransreq.setReturn_code(BusinessMsg.SUCCESS);
        transTransreq.setReturn_msg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));
        transReqService.insert(transTransreq);
        baseResponse.setRecode(BusinessMsg.SUCCESS);
        baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));
        return baseResponse;
    }

    /**
     * 授权申请
     * @param applyAuthOperaRequest
     * @return
     */
    @Override
    public BaseResponse applyAuthOpera(ApplyAuthOperaRequest applyAuthOperaRequest) {
        TransTransreq transTransreq = transReqService.getTransTransReq(applyAuthOperaRequest.clone(), TransConsts.APPLY_AUTH_OPERA_CODE,
                TransConsts.APPLY_AUTH_OPERA_NAME, false);
        transTransreq.setPlatcust(applyAuthOperaRequest.getPlatcust());
        BaseResponse oldTrans=transReqService.insert(transTransreq);
        if(oldTrans!=null){
            if(FlowStatus.SUCCESS.getCode().equals(transTransreq.getStatus()) || FlowStatus.FAIL.getCode().equals(transTransreq.getStatus())){
                throw new BusinessException(BusinessMsg.ORDERNUMBER_REPEATED,BusinessMsg.getMsg(BusinessMsg.ORDERNUMBER_REPEATED));
            }
            transTransreq.setTrans_serial(oldTrans.getTrans_serial());
        }
        BaseResponse baseResponse = new BaseResponse();
        try {
            if (!applyAuthOperaRequest.getAuthed_type().contains(AuthType.AUTH_INVEST.getCode())
                    &&!applyAuthOperaRequest.getAuthed_type().contains(AuthType.AUTH_RECHARGE.getCode())
                    &&!applyAuthOperaRequest.getAuthed_type().contains(AuthType.AUTH_REFUND.getCode())){
                logger.info(String.format("【授权申请】参数不正确|order_no:%s|trans_serial:%s|error:",
                        applyAuthOperaRequest.getOrder_no(), transTransreq.getTrans_serial()) );
                throw new BusinessException(BusinessMsg.PARAMETER_ERROR, BusinessMsg.getMsg(BusinessMsg.PARAMETER_ERROR));

            }
            logger.info("【授权申请】检查用户是否存在");
            EaccUserinfo eaccUserinfo = queryEaccUserInfoByMallNoAndPlatcust(applyAuthOperaRequest.getMall_no(),
                    applyAuthOperaRequest.getPlatcust());
            if (eaccUserinfo == null) {
                logger.info("【授权申请】用户不存在");
                throw new BusinessException(BusinessMsg.INVAILD_ACCOUNT,BusinessMsg.getMsg(BusinessMsg.INVAILD_ACCOUNT));
            }
            //准备发送验证码短信
            String preMobile = eaccUserinfo.getMobile();
            if(StringUtils.isNotBlank(eaccUserinfo.getDefault_mobile())){
                preMobile=eaccUserinfo.getDefault_mobile();
            }else{
                List<EaccCardinfo> eaccCardinfoList=queryEaccCardInfolist(applyAuthOperaRequest.getMall_no(),applyAuthOperaRequest.getPlatcust(),null);
                if(eaccCardinfoList.size()>0){
                    for(EaccCardinfo eaccCardinfo:eaccCardinfoList){
                        if(CardType.DEBIT.getCode().equals(eaccCardinfo.getCard_type())){
                            preMobile=eaccCardinfo.getMobile();
                            break;
                        }
                    }
                }
            }
            String identifyingCode = SeqUtil.getRadomNum();
            if("true".equals(mockSwitch)){
                identifyingCode="0000";
            }
            Integer maxErrorNum = Integer.valueOf(
                    sysParameterService.querySysParameter(applyAuthOperaRequest.getMall_no(), SysParamterKey.IDENTIFYING_CODE_ERROR_NUM));
            Integer identifyingTimeOut = Integer.valueOf(
                    sysParameterService.querySysParameter(applyAuthOperaRequest.getMall_no(), SysParamterKey.IDENTIFYING_CODE_TIME_OUT));
            String identifying_code_key = Constants.AUTH_APPLAY_IDENTIFYING_CODE_KEY + transTransreq.getTrans_serial();
            Map<String, Object> identifyingDataMap = new HashMap<>(2);
            identifyingDataMap.put("identifying_code", identifyingCode);
            identifyingDataMap.put("max_error_num", maxErrorNum);
            CacheUtil.getCache().set(identifying_code_key, JSON.toJSONString(identifyingDataMap));
            CacheUtil.getCache().expire(identifying_code_key, identifyingTimeOut);
            try {
                logger.info(String.format("【授权申请】根据流水号插入申请数据，trans_serial,【%s】", transTransreq.getTrans_serial()));
                CacheUtil.getCache().set(Constants.AUTH_APPLAY_KEY + transTransreq.getTrans_serial(), JSON.toJSONString(applyAuthOperaRequest));
                //保存24小时
                CacheUtil.getCache().expire(Constants.AUTH_APPLAY_KEY + transTransreq.getTrans_serial(), 24 * 3600);
                logger.info(String.format("【授权申请】发送短信验证码|order_no:%s|trans_serial:%s|pro_mobile:%s|identifying_code:%s",
                        transTransreq.getOrder_no(), transTransreq.getTrans_serial(), preMobile, identifyingCode));
                MsgModel msgModel = new MsgModel();
                String mall_name = sysParameterService.querySysParameter(applyAuthOperaRequest.getMall_no(), SysParamterKey.MALL_NAME);
                String msg_content = sysParameterService.querySysParameter(applyAuthOperaRequest.getMall_no(), MsgContent.AUTH_APPAY_NOTIFY.getMsg());
                msgModel.setOrder_no(applyAuthOperaRequest.getOrder_no());
                msgModel.setPlat_no(applyAuthOperaRequest.getMer_no());
                msgModel.setTrans_code(transTransreq.getTrans_code());
                msgModel.setMobile(preMobile);
                msgModel.setMall_no(applyAuthOperaRequest.getMall_no());
//                    msgModel.setPlatcust(eaccUserinfo.getMallcust());
                msgModel.setMsgContent(String.format(msg_content, mall_name, identifyingCode));
                sendMsgService.addMsgToQueue(msgModel);
            } catch (Exception e) {
                logger.info(String.format("【授权申请】短信发送失败|order_no:%s|trans_serial:%s|error:",
                        transTransreq.getOrder_no(), transTransreq.getTrans_serial()), e);
                throw new BusinessException(BusinessMsg.IDENTIFYING_CODE_SEND_ERROR, BusinessMsg.getMsg(BusinessMsg.IDENTIFYING_CODE_SEND_ERROR));
            }
        } catch (Exception e) {
            logger.error(String.format("【授权申请】异常|order_no:%s|trans_serial:%s|error:",
                    transTransreq.getOrder_no(), transTransreq.getTrans_serial()), e);
            if (e instanceof BusinessException) {
                baseResponse = ((BusinessException) e).getBaseResponse();
            } else {
                baseResponse.setRecode(BusinessMsg.RUNTIME_EXCEPTION);
                baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.RUNTIME_EXCEPTION));
            }
            baseResponse.setOrder_status(FlowStatus.FAIL.getCode());
            transTransreq.setReturn_code(baseResponse.getRecode());
            transTransreq.setReturn_msg(baseResponse.getRemsg());
            transTransreq.setStatus(baseResponse.getOrder_status());
            transReqService.insert(transTransreq);
            return baseResponse;
        }
        if(Constants.YES.equals(applyAuthOperaRequest.getIs_page())){
            transTransreq.setStatus(FlowStatus.REQUESTSUCCESS.getCode());
            transTransreq.setReturn_code(BusinessMsg.ACCEPTANCE_SUCCESS);
            transTransreq.setReturn_msg(BusinessMsg.getMsg(BusinessMsg.ACCEPTANCE_SUCCESS));
        }else{
            transTransreq.setStatus(FlowStatus.SUCCESS.getCode());
            transTransreq.setReturn_code(BusinessMsg.SUCCESS);
            transTransreq.setReturn_msg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));
        }
        transReqService.insert(transTransreq);
        baseResponse.setRecode(BusinessMsg.SUCCESS);
        baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));
        baseResponse.setOrder_status(FlowStatus.SUCCESS.getCode());
        return baseResponse;
    }

    /**
     * 业务授权确认
     * @param confirmAuthOperaRequest
     * @return
     */
    @Override
    public BaseResponse confirmAuthOpera(ConfirmAuthOperaRequest confirmAuthOperaRequest) {
        BaseResponse baseResponse = new BaseResponse();
        TransTransreq newTransTransreq = transReqService.getTransTransReq(confirmAuthOperaRequest.clone(), TransConsts.CONFIRM_AUTH_OPERA_CODE,
                TransConsts.CONFIRM_AUTH_OPERA_NAME, false);
        newTransTransreq.setOrigin_order_no(confirmAuthOperaRequest.getOrigin_order_no());
        BaseResponse oldResponse=transReqService.insert(newTransTransreq);
        ApplyAuthOperaRequest applyAuthOperaRequest=new ApplyAuthOperaRequest();
        if(oldResponse!=null && (FlowStatus.SUCCESS.getCode().equals(oldResponse.getOrder_status()) || FlowStatus.FAIL.getCode().equals(oldResponse.getOrder_status()))){
            return oldResponse;
        }else if(oldResponse!=null){
            newTransTransreq.setTrans_serial(oldResponse.getTrans_serial());
        }
        try {
            TransTransreq oldTransTransreq = transReqService.queryTransTransReqByOrderno(confirmAuthOperaRequest.getOrigin_order_no());
            if (oldTransTransreq == null) {
                logger.info(String.format("【业务授权确认】原流水号不存在：%s", confirmAuthOperaRequest.getOrigin_order_no()));
                throw new BusinessException(BusinessMsg.ORIGINAL_ORDER_NOEXISTENT, BusinessMsg.getMsg(BusinessMsg.ORIGINAL_ORDER_NOEXISTENT));
            }
//            if (FlowStatus.SUCCESS.getCode().equals(oldTransTransreq.getStatus()) || FlowStatus.FAIL.getCode().equals(oldTransTransreq.getStatus())) {
//                logger.info("【业务授权确认】原订单状态已是终态，不可重复确认");
//                throw new BusinessException(BusinessMsg.ORIGINAL_ORDER_PROCESSED, BusinessMsg.getMsg(BusinessMsg.ORIGINAL_ORDER_PROCESSED));
//            }
            //获取验证码信息
            String identifying_code_key = Constants.AUTH_APPLAY_IDENTIFYING_CODE_KEY + oldTransTransreq.getTrans_serial();
            Object identifyingCodeObj = CacheUtil.getCache().get(identifying_code_key);
            if (identifyingCodeObj == null) {
                logger.info("【业务授权确认】验证码无效或已失效|order_no:{}|origin_order_no:{}|trans_serial:{}",
                        confirmAuthOperaRequest.getOrder_no(), confirmAuthOperaRequest.getOrigin_order_no(), oldTransTransreq.getTrans_serial());
                throw new BusinessException(BusinessMsg.VERIFICATION_OUT_OF_DATE, BusinessMsg.getMsg(BusinessMsg.VERIFICATION_OUT_OF_DATE));
            }
            Map<String, Object> identifyingDataMap = JSON.parseObject((String)identifyingCodeObj, HashedMap.class);
            String identifyingCode = (String) identifyingDataMap.get("identifying_code");
            Integer maxErrorNum = (Integer) identifyingDataMap.get("max_error_num");
            if (maxErrorNum > 0) {
                if (!identifyingCode.equals(confirmAuthOperaRequest.getIdentifying_code())) {
                    //验证码错误
                    logger.info("【业务授权确认】验证码错误|order_no:{}|origin_order_no:{}|identifying_code:{}|real_identifying_code:{}|left_identify_num:{}",
                            confirmAuthOperaRequest.getOrder_no(), confirmAuthOperaRequest.getOrigin_order_no(), confirmAuthOperaRequest.getIdentifying_code(), identifyingCode, maxErrorNum);
                    maxErrorNum--;
                    identifyingDataMap.put("max_error_num", maxErrorNum);
                    CacheUtil.getCache().set(identifying_code_key, JSON.toJSONString(identifyingDataMap));
                    throw new BusinessException(BusinessMsg.VERIFICATION_ERROR, BusinessMsg.getMsg(BusinessMsg.VERIFICATION_ERROR));
                }
            } else {
                //验证码错误次数过多
                logger.info("【业务授权确认】验证码错误次数过多|order_no:{}|origin_order_no:{}|identifying_code:{}|real_identifying_code:{}",
                        confirmAuthOperaRequest.getOrder_no(), confirmAuthOperaRequest.getOrigin_order_no(), confirmAuthOperaRequest.getIdentifying_code(), identifyingCode);
                throw new BusinessException(BusinessMsg.VERIFICATION_ERROR_TOO_MUCH, BusinessMsg.getMsg(BusinessMsg.VERIFICATION_ERROR_TOO_MUCH));
            }
            logger.info("【业务授权确认】从redis中获取授权数据");
            Object objAuthOpera = CacheUtil.getCache().get(Constants.AUTH_APPLAY_KEY + oldTransTransreq.getTrans_serial());
            logger.info(objAuthOpera);
            if(objAuthOpera == null){
                logger.info("【业务授权确认】redis中不存在该订单信息");
                throw new BusinessException(BusinessMsg.ORIGINAL_ORDER_NOEXISTENT,BusinessMsg.getMsg(BusinessMsg.ORIGINAL_ORDER_NOEXISTENT));
            }
            applyAuthOperaRequest = JSON.parseObject((String) objAuthOpera, ApplyAuthOperaRequest.class);
            if(confirmAuthOperaRequest.getAuthed_limittime() != null){
                applyAuthOperaRequest.setAuthed_limittime(confirmAuthOperaRequest.getAuthed_limittime());
            }
            if(confirmAuthOperaRequest.getAuthed_amount() != null){
                applyAuthOperaRequest.setAuthed_amount(confirmAuthOperaRequest.getAuthed_amount());
            }
            //添加授权信息
            Map<String,Object> params=new HashMap<String,Object>();
            params.put("mall_no", confirmAuthOperaRequest.getMall_no());
            List<String>  AccOpenconfigList = custOpenconfigMapper.selectPlatNoByMallNo(params);
            for(String plat_no:AccOpenconfigList){
                userBindCardService.addAuthInfo(applyAuthOperaRequest.getAuthed_type(), applyAuthOperaRequest.getAuthed_amount(), applyAuthOperaRequest.getAuthed_limittime(),
                        AuthStatus.ALREADY_AUTH.getCode(), applyAuthOperaRequest.getMall_no(), plat_no, applyAuthOperaRequest.getPlatcust());
            }
            baseResponse.setOrder_status(FlowStatus.SUCCESS.getCode());
            baseResponse.setRecode(BusinessMsg.SUCCESS);
            baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));
        } catch (Exception e) {
            if (e instanceof BusinessException) {
                baseResponse = ((BusinessException) e).getBaseResponse();
            } else {
                baseResponse.setRecode(BusinessMsg.RUNTIME_EXCEPTION);
                baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.RUNTIME_EXCEPTION));
            }
            baseResponse.setOrder_status(FlowStatus.FAIL.getCode());
            logger.info(String.format("【业务授权确认】原订单号：【%s】处理失败", confirmAuthOperaRequest.getOrigin_order_no()), e);

            if(!BusinessMsg.VERIFICATION_ERROR.equals(baseResponse.getRecode())){
//                if(oldTransTransreq!=null){
//                    oldTransTransreq.setStatus(FlowStatus.FAIL.getCode());
//                    oldTransTransreq.setReturn_code(baseResponse.getRecode());
//                    oldTransTransreq.setReturn_msg(BusinessMsg.getMsg(baseResponse.getRecode()));
//                    transReqService.insert(oldTransTransreq);
//                }

                newTransTransreq.setStatus(FlowStatus.FAIL.getCode());
                newTransTransreq.setReturn_code(baseResponse.getRecode());
                newTransTransreq.setReturn_msg(baseResponse.getRemsg());
                transReqService.insert(newTransTransreq);
            }
            return baseResponse;
        }finally {
            try {

                if (confirmAuthOperaRequest.getNotify_url()!=null&& !"".equals(confirmAuthOperaRequest.getNotify_url())
                        &&(FlowStatus.SUCCESS.getCode().equals(baseResponse.getOrder_status())||FlowStatus.FAIL.getCode().equals(newTransTransreq.getStatus()))){
                    NotifyData notifyData=new NotifyData();
                    notifyData.setNotifyUrl(confirmAuthOperaRequest.getNotify_url());
                    notifyData.setMall_no(confirmAuthOperaRequest.getMall_no());
                    Map<String,Object> notifyContent=new HashMap<String,Object>();
                    notifyContent.put("order_status",baseResponse.getOrder_status());
                    notifyContent.put("order_no",confirmAuthOperaRequest.getOrder_no());
                    notifyContent.put("recode",baseResponse.getRecode());
                    notifyContent.put("remsg",baseResponse.getRemsg());
                    notifyContent.put("trans_date",baseResponse.getTrans_date());
                    notifyContent.put("authed_amount",null==applyAuthOperaRequest.getAuthed_amount()?"":applyAuthOperaRequest.getAuthed_amount());
                    notifyContent.put("authed_limittime",null==applyAuthOperaRequest.getAuthed_limittime()?"":applyAuthOperaRequest.getAuthed_limittime());
                    notifyData.setNotifyContent(JSON.toJSONString(notifyContent));
                    notifyService.addNotify(notifyData);
                    logger.info("【授权确认】-->丢入MQ成功-->order_no:"+newTransTransreq.getOrder_no());
                }
            }catch (Exception e){
                logger.info("异步通知发送失败",e);
            }


        }
//        oldTransTransreq.setStatus(FlowStatus.SUCCESS.getCode());
//        oldTransTransreq.setReturn_code(BusinessMsg.SUCCESS);
//        oldTransTransreq.setReturn_msg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));
//        transReqService.insert(oldTransTransreq);

        newTransTransreq.setStatus(FlowStatus.SUCCESS.getCode());
        newTransTransreq.setReturn_code(BusinessMsg.SUCCESS);
        newTransTransreq.setReturn_msg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));
        transReqService.insert(newTransTransreq);

        baseResponse.setRecode(BusinessMsg.SUCCESS);
        baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));
        return baseResponse;
    }
    /**
     * 业务授权取消
     * @param cancelAuthOperaRequest
     * @return
     */
    @Override
    public BaseResponse cancelAuthOpera(CancelAuthOperaRequest cancelAuthOperaRequest) {
        BaseResponse baseResponse = new BaseResponse();
        TransTransreq addTransTransreq = transReqService.getTransTransReq(cancelAuthOperaRequest.clone(), TransConsts.CANCEL_AUTH_OPERA_CODE,
                TransConsts.CANCEL_AUTH_OPERA_NAME, false);
        try {

            addTransTransreq.setPlatcust(cancelAuthOperaRequest.getPlatcust());
            List<EaccUserauth> eaccUserauths = listEaccUserAuth(cancelAuthOperaRequest.getMall_no(),cancelAuthOperaRequest.getMer_no(),cancelAuthOperaRequest.getPlatcust());
            if (eaccUserauths.size() == 0) {
                throw new BusinessException(BusinessMsg.NO_AUTH_DATA, BusinessMsg.getMsg(BusinessMsg.NO_AUTH_DATA));
            }
//            eaccUserauths.forEach((eaccUserauth) -> {
//                eaccUserauth.setAuthed_status(AuthStatus.NO_AUTH.getCode());
//                eaccUserauth.setUpdate_time(DateUtils.today());
//                eaccUserauth.setUpdate_by("cancelAuthOpera");
//            });
            userAccountExtService.deleteEaccUserAuth(eaccUserauths);
            List<Integer> updateIds = eaccUserauths.stream().map(EaccUserauth::getId).collect(Collectors.toList());
            addTransTransreq.setRemark("eaccUserauths.ids：" + StringUtils.join(updateIds, ","));
            logger.info(String.format("【业务授权取消】平台客户号：【%s】处理成功。 eaccUserauths.ids：【%s】", cancelAuthOperaRequest.getPlatcust(), StringUtils.join(updateIds, ",")));

            try {
                MsgModel msgModel = new MsgModel();
                String mall_name = sysParameterService.querySysParameter(cancelAuthOperaRequest.getMall_no(), SysParamterKey.MALL_NAME);
                String msg_content = sysParameterService.querySysParameter(cancelAuthOperaRequest.getMall_no(), MsgContent.CANCEL_AUTH_OPERA_NOTIFY.getMsg());
                msgModel.setOrder_no(cancelAuthOperaRequest.getOrder_no());
                msgModel.setPlat_no(cancelAuthOperaRequest.getMer_no());
                msgModel.setTrans_code(TransConsts.CANCEL_AUTH_OPERA_CODE);
                EaccUserinfo eaccUserinfo = accountQueryService.getEaccUserinfo(cancelAuthOperaRequest.getMall_no(),cancelAuthOperaRequest.getPlatcust());
                msgModel.setMobile(eaccUserinfo.getMobile());
                msgModel.setMall_no(cancelAuthOperaRequest.getMall_no());
                msgModel.setMsgContent(String.format(msg_content, mall_name));
                sendMsgService.addMsgToQueue(msgModel);
            }catch (Exception e){
                logger.info("【业务授权取消短信发送异常】-->order_no:"+addTransTransreq.getOrder_no(),e);
            }

            addTransTransreq.setStatus(FlowStatus.SUCCESS.getCode());
            addTransTransreq.setReturn_code(BusinessMsg.SUCCESS);
            addTransTransreq.setReturn_msg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));
            transReqService.insert(addTransTransreq);
            baseResponse.setOrder_status(FlowStatus.SUCCESS.getCode());
            baseResponse.setRecode(BusinessMsg.SUCCESS);
            baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));

        } catch (Exception e) {
            if (e instanceof BusinessException) {
                baseResponse = ((BusinessException) e).getBaseResponse();
            } else {
                baseResponse.setRecode(BusinessMsg.RUNTIME_EXCEPTION);
                baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.RUNTIME_EXCEPTION));
            }
            baseResponse.setOrder_status(FlowStatus.FAIL.getCode());
            logger.info(String.format("【业务授权取消】平台客户号：【%s】处理失败", cancelAuthOperaRequest.getPlatcust()));
            addTransTransreq.setStatus(FlowStatus.FAIL.getCode());
            addTransTransreq.setReturn_code(baseResponse.getRecode());
            addTransTransreq.setReturn_msg(baseResponse.getRemsg());
            transReqService.insert(addTransTransreq);
        }finally {
            if(FlowStatus.SUCCESS.getCode().equals(addTransTransreq.getStatus())||FlowStatus.FAIL.getCode().equals(addTransTransreq.getStatus())) {
                try {
                    baseResponse.setOrder_no(cancelAuthOperaRequest.getOrder_no());
                    NotifyData notifyData = new NotifyData();
                    notifyData.setMall_no(cancelAuthOperaRequest.getMall_no());
                    notifyData.setNotifyContent(JSON.toJSONString(baseResponse));
                    notifyData.setNotifyUrl(cancelAuthOperaRequest.getNotify_url());
                    notifyService.addNotify(notifyData);
                    logger.info("【授权取消】-->丢入MQ成功-->order_no:" + baseResponse.getOrder_no());

                } catch (Exception e) {
                    logger.info("【授权取消】异步通知失败" + e);
                }
            }
        }
        return baseResponse;
    }

    @Override
    public BaseResponse freezeAccount(FreezeAccountRequest freezeAccountRequest) throws BusinessException {
        logger.info("【客户冻结/解冻】开始：",freezeAccountRequest.getOrder_no());
        BaseResponse baseResponse = new BaseResponse();
        TransTransreq transTransreq = transReqService.getTransTransReq(freezeAccountRequest.clone(), TransConsts.FREEZE_ACCOUNT_CODE,
                TransConsts.FREEZE_ACCOUNT_NAME, false);
        transTransreq.setPlatcust(freezeAccountRequest.getPlatcust());
        transTransreq.setExt1(freezeAccountRequest.getTrans_type());
        transReqService.insert(transTransreq);
        try {
            if (!TRANSTYPE.Freeze.getCode().equals(freezeAccountRequest.getTrans_type()) &&
                    !TRANSTYPE.UNFreeze.getCode().equals(freezeAccountRequest.getTrans_type())) {
                logger.info("【客户冻结/解冻】：", "trans_type类型错误");
                throw new BusinessException(BusinessMsg.PARAMETER_ERROR, "trans_type类型错误");
            }
            String find_type=TRANSTYPE.Freeze.getCode().equals(freezeAccountRequest.getTrans_type())?AcctStatus.ACTIVE.getCode():AcctStatus.FORZEN.getCode();
            EaccUserinfo eaccUserinfo=accountQueryService.getEUserinfoByExist(freezeAccountRequest.getMall_no(),freezeAccountRequest.getPlatcust(),find_type);
            if(null==eaccUserinfo){
                if(TRANSTYPE.Freeze.getCode().equals(freezeAccountRequest.getTrans_type())){
                    logger.info("【客户冻结/解冻】：","客户账户非激活状态或账户不存在，无法冻结");
                    throw new BusinessException(BusinessMsg.PARAMETER_ERROR,"客户账户非激活状态或账户不存在，无法冻结");
                }else if (TRANSTYPE.UNFreeze.getCode().equals(freezeAccountRequest.getTrans_type())){
                    logger.info("【客户冻结/解冻】：","客户账户非冻结状态或账户不存在，无法解冻");
                    throw new BusinessException(BusinessMsg.PARAMETER_ERROR,"客户账户非冻结状态或账户不存在，无法解冻");
                }
            }
            Map<String,Object> sendParam=new HashMap<String,Object>();
            sendParam.put("mall_no",freezeAccountRequest.getMall_no());
            sendParam.put("mer_no",freezeAccountRequest.getMer_no());
            sendParam.put("plat_cust",freezeAccountRequest.getPlatcust());
            sendParam.put("client_name",eaccUserinfo.getName());
            sendParam.put("id_kind","0");
            if(CusType.PERSONAL.getCode().equals(eaccUserinfo.getCus_type())) {
                sendParam.put("id_no", eaccUserinfo.getId_code());
            }else {
                sendParam.put("id_no", StringUtils.isNotBlank(eaccUserinfo.getBank_license()) ?
                        eaccUserinfo.getBank_license():eaccUserinfo.getBusiness_license());
            }
            Map<String,Object> resultMap=null;
            if(TRANSTYPE.Freeze.getCode().equals(freezeAccountRequest.getTrans_type())){
                sendParam.put("host",sysParameterService.querySysParameter(freezeAccountRequest.getMall_no(), URLConfigUtil.EPAY_SERVER_KEY));
                sendParam.put("url",sysParameterService.querySysParameter(freezeAccountRequest.getMall_no(), URLConfigUtil.ICIS_CUSTOMERFREEZE));
                logger.info("【客户冻结/解冻】请求三方参数："+JSON.toJSON(sendParam));
                resultMap=adapterService.isicCustomerFreeze(sendParam);
            }else{
                sendParam.put("host",sysParameterService.querySysParameter(freezeAccountRequest.getMall_no(), URLConfigUtil.EPAY_SERVER_KEY));
                sendParam.put("url",sysParameterService.querySysParameter(freezeAccountRequest.getMall_no(), URLConfigUtil.ICIS_CUSTOMERUNFREEZE));
                logger.info("【客户冻结/解冻】请求三方参数："+JSON.toJSON(sendParam));
                resultMap=adapterService.isicCustomerUnfreeze(sendParam);
            }
            logger.info("【客户冻结/解冻】三方响应结果："+JSON.toJSON(resultMap));
            if(null==resultMap ||null==resultMap.get("order_status") ||"".equals(resultMap.get("order_status"))){
                throw new BusinessException(resultMap.get("recode")==null?BusinessMsg.CALL_REMOTE_ERROR:resultMap.get("recode").toString(),
                        resultMap.get("remsg")==null?BusinessMsg.getMsg(BusinessMsg.CALL_REMOTE_ERROR):resultMap.get("remsg").toString());
            }
            if(OrderStatus.SUCCESS.getCode().equals(resultMap.get("order_status"))){
                List<AccountSubjectInfo> subjectInfoList=accountQueryService.queryAccountlist(freezeAccountRequest.getMer_no(),freezeAccountRequest.getPlatcust(),find_type);

                List<EaccCardinfo> eaccCardinfoList=accountSearchService.queryEaccCardInfo(freezeAccountRequest.getMall_no(),freezeAccountRequest.getPlatcust(),find_type);

                userAccountExtService.updateAccountStatus(eaccUserinfo,subjectInfoList,eaccCardinfoList,freezeAccountRequest.getTrans_type());

                transTransreq.setStatus(FlowStatus.SUCCESS.getCode());
                transTransreq.setReturn_code(BusinessMsg.SUCCESS);
                transTransreq.setReturn_msg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));
                transReqService.insert(transTransreq);
                baseResponse.setRecode(BusinessMsg.SUCCESS);
                baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));
            }else {
                transTransreq.setStatus(FlowStatus.FAIL.getCode());
                transTransreq.setReturn_code(BusinessMsg.FAIL);
                transTransreq.setReturn_msg(BusinessMsg.getMsg(BusinessMsg.FAIL));
                transReqService.insert(transTransreq);
                baseResponse.setRecode(BusinessMsg.FAIL);
                baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.FAIL));
            }

        }catch(Exception e){
            logger.error("【客户冻结/解冻】异常：",e);
            if (e instanceof BusinessException) {
                baseResponse = ((BusinessException) e).getBaseResponse();
            } else {
                baseResponse.setRecode(BusinessMsg.RUNTIME_EXCEPTION);
                baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.RUNTIME_EXCEPTION));
            }
            transTransreq.setStatus(FlowStatus.FAIL.getCode());
            transTransreq.setReturn_code(baseResponse.getRecode());
            transTransreq.setReturn_msg(baseResponse.getRemsg());
            transReqService.insert(transTransreq);
        }
        return baseResponse;
    }

    @Override
    public BaseResponse unregAccount(UnregAccountRequest unregAccountRequest) {
        logger.info("【客户销户】开始：", unregAccountRequest.getOrder_no());
        BaseResponse baseResponse = new BaseResponse();
        TransTransreq transTransreq = transReqService.getTransTransReq(unregAccountRequest.clone(), TransConsts.UNREG_ACCOUNT_CODE,
                TransConsts.UNREG_ACCOUNT_NAME, false);
        transTransreq.setPlatcust(unregAccountRequest.getPlatcust());
        transTransreq.setExt1(unregAccountRequest.getTrans_type());
        transReqService.insert(transTransreq);
        try {
            EaccUserinfo eaccUserinfo = accountQueryService.getEUserinfoByExist(unregAccountRequest.getMall_no(), unregAccountRequest.getPlatcust());
            if (null == eaccUserinfo) {
                logger.info("【客户销户】：", "客户信息不存在");
                throw new BusinessException(BusinessMsg.PARAMETER_ERROR, "客户信息不存在");
            }
            List<AccountSubjectInfo> subjectInfoList =new ArrayList<AccountSubjectInfo>();
            //判断集团用户
            if(unregAccountRequest.getMer_no().equals(unregAccountRequest.getMall_no())){
                //根据集团号查询该集团下所有的平台
                PlatPlatinfoExample platExample = new PlatPlatinfoExample();
                PlatPlatinfoExample.Criteria platCriteria = platExample.createCriteria();
                platCriteria.andMall_noEqualTo(unregAccountRequest.getMall_no());
                List<PlatPlatinfo> platPlatinfoList = platPlatinfoMapper.selectByExample(platExample);
                //查询平台虚户
                for(PlatPlatinfo p : platPlatinfoList){
                    List<AccountSubjectInfo> mer_account_list = accountQueryService.queryAccountlist(p.getPlat_no(),unregAccountRequest.getPlatcust());
                    if(null!=mer_account_list && mer_account_list.size()>0){
                        subjectInfoList.addAll(mer_account_list);
                    }
                }
                //查寻电子账户
                List<AccountSubjectInfo> mall_account_list = accountQueryService.queryAccountlist(unregAccountRequest.getMall_no(),unregAccountRequest.getPlatcust());
                if(null!=mall_account_list && mall_account_list.size()>0){
                    subjectInfoList.addAll(mall_account_list);
                }
            }else{
                subjectInfoList = accountQueryService.queryAccountlist(unregAccountRequest.getMer_no(), unregAccountRequest.getPlatcust());
            }
            //校验用户账户余额是否为0，不为0不允许撤销

            if (null == subjectInfoList || 0 == subjectInfoList.size()) {
                logger.info("【客户销户】：", "客户账户不可用或不存在，不能销户");
                throw new BusinessException(BusinessMsg.PARAMETER_ERROR, "客户账户不可用或不存在，不能销户");
            }
            for(AccountSubjectInfo accountSubjectInfo_item:subjectInfoList){
                if(1==accountSubjectInfo_item.getT_balance().compareTo(BigDecimal.ZERO)){
                    logger.info("【客户销户】：", "客户账户资金不为0，不能销户");
                    throw new BusinessException(BusinessMsg.PARAMETER_ERROR, "客户账户资金不为0，不能销户");
                }
            }
            //查看是否存在待收资金
            String remain_amt = prodSearchService.getRemainAmtByPlacust(unregAccountRequest.getMer_no(), unregAccountRequest.getPlatcust());
            if (0 != BigDecimal.ZERO.compareTo(new BigDecimal(remain_amt))) {
                logger.info("【客户销户】：", "客户待收资金不为0，不能销户");
                throw new BusinessException(BusinessMsg.PARAMETER_ERROR, "客户待收资金不为0，不能销户");
            }

            //查看用户是否存在提现失败未退款或处理中资金
            List<RwWithdraw> rwWithdrawList = accountQueryService.getUnDoneRwWithdrawTransByPlatcust(unregAccountRequest.getMer_no(),unregAccountRequest.getPlatcust());
            if(rwWithdrawList.size()>0){
                logger.info("【客户销户】：", "客户存在提现处理中或失败未退款订单，不能销户");
                throw new BusinessException(BusinessMsg.PARAMETER_ERROR, "客户存在提现处理中或失败未退款订单，不能销户");
            }

            Map<String,Object> sendParam=new HashMap<String,Object>();
            sendParam.put("mall_no",unregAccountRequest.getMall_no());
            sendParam.put("mer_no",unregAccountRequest.getMer_no());
            sendParam.put("plat_cust",unregAccountRequest.getPlatcust());
            sendParam.put("client_name",eaccUserinfo.getName());
            sendParam.put("id_kind","0");
            if(CusType.PERSONAL.getCode().equals(eaccUserinfo.getCus_type())) {
                sendParam.put("id_no", eaccUserinfo.getId_code());
            }else {
                sendParam.put("id_no", StringUtils.isNotBlank(eaccUserinfo.getBank_license()) ?
                        eaccUserinfo.getBank_license():eaccUserinfo.getBusiness_license());
            }
            sendParam.put("host",sysParameterService.querySysParameter(unregAccountRequest.getMall_no(), URLConfigUtil.EPAY_SERVER_KEY));
            sendParam.put("url",sysParameterService.querySysParameter(unregAccountRequest.getMall_no(), URLConfigUtil.ICIS_CUSTOMERCANCEL));
            logger.info("【客户销户】请求三方参数："+JSON.toJSON(sendParam));
            Map<String,Object> resultMap=adapterService.isicCustomerCancel(sendParam);
            logger.info("【客户销户】三方响应结果："+JSON.toJSON(resultMap));
            if(null==resultMap ||null==resultMap.get("order_status") ||"".equals(resultMap.get("order_status"))){
                throw new BusinessException(BusinessMsg.CALL_REMOTE_ERROR,BusinessMsg.getMsg(BusinessMsg.CALL_REMOTE_ERROR));
            }
            if(OrderStatus.SUCCESS.getCode().equals(resultMap.get("order_status"))){
                List<EaccCardinfo> eaccCardinfoList = accountSearchService.queryEaccCardInfo(unregAccountRequest.getMall_no(), unregAccountRequest.getPlatcust(), null, null);

                userAccountExtService.updateAccountStatus(eaccUserinfo, subjectInfoList, eaccCardinfoList, unregAccountRequest.getTrans_type());

                transTransreq.setStatus(FlowStatus.SUCCESS.getCode());
                transTransreq.setReturn_code(BusinessMsg.SUCCESS);
                transTransreq.setReturn_msg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));
                transReqService.insert(transTransreq);
                baseResponse.setRecode(BusinessMsg.SUCCESS);
                baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));
                baseResponse.setOrder_status(FlowStatus.SUCCESS.getCode());
                try {
                    MsgModel msgModel = new MsgModel();
                    String mall_name = sysParameterService.querySysParameter(unregAccountRequest.getMall_no(), SysParamterKey.MALL_NAME);
                    String msg_content = sysParameterService.querySysParameter(unregAccountRequest.getMall_no(), MsgContent.UNREG_ACCOUNT_NOTIFY.getMsg());
                    msgModel.setOrder_no(transTransreq.getOrder_no());
                    msgModel.setPlat_no(transTransreq.getPlat_no());
                    msgModel.setTrans_code(transTransreq.getTrans_code());
                    msgModel.setMobile(eaccUserinfo.getMobile());
                    msgModel.setMall_no(unregAccountRequest.getMall_no());
                    msgModel.setMsgContent(String.format(msg_content, mall_name));
                    sendMsgService.addMsgToQueue(msgModel);
                }catch (Exception e){
                    logger.info("【销户短信发送异常】-->order_no:"+transTransreq.getOrder_no(),e);
                }

            }else {
                transTransreq.setStatus(FlowStatus.FAIL.getCode());
                transTransreq.setReturn_code(BusinessMsg.FAIL);
                transTransreq.setReturn_msg(BusinessMsg.getMsg(BusinessMsg.FAIL));
                transReqService.insert(transTransreq);
                baseResponse.setRecode(BusinessMsg.FAIL);
                baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.FAIL));
                baseResponse.setOrder_status(FlowStatus.FAIL.getCode());
            }

        } catch (Exception e) {
            logger.error("【客户销户】异常：", e);
            if (e instanceof BusinessException) {
                baseResponse = ((BusinessException) e).getBaseResponse();
            } else {
                baseResponse.setRecode(BusinessMsg.RUNTIME_EXCEPTION);
                baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.RUNTIME_EXCEPTION));
            }
            baseResponse.setOrder_status(FlowStatus.FAIL.getCode());
            transTransreq.setStatus(FlowStatus.FAIL.getCode());
            transTransreq.setReturn_code(baseResponse.getRecode());
            transTransreq.setReturn_msg(baseResponse.getRemsg());
            transReqService.insert(transTransreq);
            /*return baseResponse;*/
        } finally {
            try {
                baseResponse.setOrder_no(unregAccountRequest.getOrder_no());
                if ((FlowStatus.SUCCESS.getCode().equals(transTransreq.getStatus())||FlowStatus.FAIL.getCode().equals(transTransreq.getStatus()))) {
                    logger.info("【客户销户】异步通知开始：" + unregAccountRequest.getOrder_no());
                    NotifyData notifyData = new NotifyData();
                    notifyData.setNotifyUrl(unregAccountRequest.getNotify_url());
                    notifyData.setMall_no(unregAccountRequest.getMall_no());
                    notifyData.setNotifyContent(JSON.toJSONString(baseResponse));
                    notifyService.addNotify(notifyData);
                }
            } catch (Exception e) {
                logger.info("【客户销户】异步通知失败", e);
            }
        }
        return baseResponse;
    }

    @Transactional
    @Override
    public void changeTransSerialWhileSameOrderNo(TransTransreq transTransReq, RwRecharge rwRecharge) {
        transTransreqMapper.updateByPrimaryKeySelective(transTransReq);
        rwRechargeMapper.updateByPrimaryKeySelective(rwRecharge);
    }

    @Override
    public AuthInfoResponse queryAuthInfo(AuthInfoRequest authInfoRequest) {
        logger.info("【授权信息查询】开始查询:",JSON.toJSONString(authInfoRequest));
        AuthInfoResponse authInfoResponse=new AuthInfoResponse();
        try {
            List<EaccUserauth> eaccUserauthList = authCheckService.queryAuthInfo(authInfoRequest.getMall_no(),authInfoRequest.getMer_no(), authInfoRequest.getPlatcust());
            if(null ==eaccUserauthList || eaccUserauthList.size()==0){
                throw new BusinessException(BusinessMsg.FAIL, BusinessMsg.getMsg(BusinessMsg.FAIL)+"|未查询到相关授权信息");
            }
            authInfoResponse.setPlatcust(eaccUserauthList.get(0).getPlatcust());
            authInfoResponse.setAuthed_amount(eaccUserauthList.get(0).getAuthed_amount());
            authInfoResponse.setAuthed_limittime(eaccUserauthList.get(0).getAuthed_limittime());
            String auth_type=eaccUserauthList.get(0).getAuthed_type();
            StringBuffer sb=new StringBuffer();
            if(eaccUserauthList.size()>1){
                for(int i =1;i<eaccUserauthList.size();i++){
                    sb =sb.append(","+eaccUserauthList.get(i).getAuthed_type());
                }
            }
            if(sb.length()>0){
                auth_type=auth_type+sb;
            }
            authInfoResponse.setAuthed_type(auth_type);
            authInfoResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));
            authInfoResponse.setRecode(BusinessMsg.SUCCESS);
        }catch (Exception e){
            BaseResponse baseResponse=new BaseResponse();
            if (e instanceof BusinessException) {
                baseResponse = ((BusinessException) e).getBaseResponse();
            } else {
                baseResponse.setRecode(BusinessMsg.RUNTIME_EXCEPTION);
                baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.RUNTIME_EXCEPTION));
            }
            authInfoResponse.setRecode(baseResponse.getRecode());
            authInfoResponse.setRemsg(baseResponse.getRemsg());
        }
        return authInfoResponse;
    }

    @Override
    public BaseResponse switchAccount(SwitchAccountRequest switchAccountRequest) {
        BaseResponse baseResponse = new BaseResponse();
        TransTransreq transTransreq = transReqService.getTransTransReq(switchAccountRequest.clone(), TransConsts.CONVERSE_ACCOUNTS_CODE,
                TransConsts.CONVERSE_ACCOUNTS_NAME, false);
        transTransreq.setPlatcust(switchAccountRequest.getPlatcust());
        transReqService.insert(transTransreq);
        EaccAccountamtlist eaccAccountamtlist = new EaccAccountamtlist();
        eaccAccountamtlist.setOrder_no(switchAccountRequest.getOrder_no());
        //优先现金
        eaccAccountamtlist.setSubject(Tsubject.CASH.getCode());
        eaccAccountamtlist.setOppo_subject(Tsubject.CASH.getCode());
        try {
            if(switchAccountRequest.getAmt().compareTo(BigDecimal.ZERO) > 0){

            }
            if (SwitchType.INVEST_TO_FINANCING.getCode().equals(switchAccountRequest.getType())) {
                logger.info("【投融资转换】投资转融资，设置sub_subject为投资，oppo_sub_subject为融资");
                eaccAccountamtlist.setSub_subject(Ssubject.INVEST.getCode());
                eaccAccountamtlist.setOppo_sub_subject(Ssubject.FINANCING.getCode());
                eaccAccountamtlist.setRemark("投资转融资");
            }/* else if (SwitchType.FINANCING_TO_INVEST.getCode().equals(switchAccountRequest.getType())) {
                logger.info("【投融资转换】融资转投资，设置sub_subject为融资，oppo_sub_subject为投资");
                eaccAccountamtlist.setSub_subject(Ssubject.FINANCING.getCode());
                eaccAccountamtlist.setOppo_sub_subject(Ssubject.INVEST.getCode());
                eaccAccountamtlist.setRemark("融资转投资");
                //暂时不开放融资转投资
            } */ else {
                logger.info("【投融资转换】传入的type错误");
                throw new BusinessException(BusinessMsg.PARAMETER_ERROR, BusinessMsg.getMsg(BusinessMsg.PARAMETER_ERROR) + "type请传指定参数");
            }

            //设置公共参数
            eaccAccountamtlist.setTrans_serial(transTransreq.getTrans_serial());
            eaccAccountamtlist.setAmt(switchAccountRequest.getAmt());
            eaccAccountamtlist.setTrans_date(switchAccountRequest.getPartner_trans_date());
            eaccAccountamtlist.setTrans_time(switchAccountRequest.getPartner_trans_time());
            eaccAccountamtlist.setTrans_code(transTransreq.getTrans_code());
            eaccAccountamtlist.setTrans_name(transTransreq.getTrans_name());

            eaccAccountamtlist.setPlat_no(switchAccountRequest.getMer_no());
            eaccAccountamtlist.setOppo_plat_no(switchAccountRequest.getMer_no());
            eaccAccountamtlist.setPlatcust(switchAccountRequest.getPlatcust());
            eaccAccountamtlist.setOppo_platcust(switchAccountRequest.getPlatcust());

            logger.info("【投融资转换】开始转账");
            accountTransferService.transfer(eaccAccountamtlist);
            baseResponse.setRecode(BusinessMsg.SUCCESS);
            baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));
        } catch (Exception e) {
            if (e instanceof BusinessException) {
                baseResponse = ((BusinessException) e).getBaseResponse();
            } else {
                baseResponse.setRecode(BusinessMsg.RUNTIME_EXCEPTION);
                baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.RUNTIME_EXCEPTION));
            }
            logger.error("【投融资转换】更新流水状态为失败");
            transTransreq.setStatus(FlowStatus.FAIL.getCode());
            transTransreq.setReturn_code(baseResponse.getRecode());
            transTransreq.setReturn_msg(baseResponse.getRemsg());
            transReqService.insert(transTransreq);
            return baseResponse;
        }
        logger.info("【投融资转换】转换成功，记录流水状态为成功");
        transTransreq.setStatus(FlowStatus.SUCCESS.getCode());
        transTransreq.setReturn_code(baseResponse.getRecode());
        transTransreq.setReturn_msg(baseResponse.getRemsg());
        transReqService.insert(transTransreq);
        return baseResponse;
    }

    @Override
    public String companyRegister(CompanyRegisterRequest companyRegisterRequest) {
        TransTransreq transTransreq = transReqService.getTransTransReq(companyRegisterRequest.clone(),TransConsts.COMPANY_REGISTER_CODE,
                TransConsts.COMPANY_REGISTER_NAME, false);
        String transSerial = transTransreq.getTrans_serial();
        transTransreq.setStatus(FlowStatus.REQUESTSUCCESS.getCode());
        transTransreq.setNotify_url(companyRegisterRequest.getNotify_url());
        BaseResponse baseResponse = transReqService.insert(transTransreq);
        try{
            transTransreq.setStatus(FlowStatus.REQUESTSUCCESS.getCode());
            transTransreq.setNotify_url(companyRegisterRequest.getNotify_url());
            logger.info("【企业开户】检验是否已经开户");
            EaccUserinfo eaccUserinfo = checkMallUserInfo(companyRegisterRequest.getMall_no(), companyRegisterRequest.getOrg_name(), companyRegisterRequest.getMer_no(), true);
            if(eaccUserinfo != null){
                logger.info("【企业开户】该用户已注册");
                transTransreq.setStatus(FlowStatus.FAIL.getCode());
                transTransreq.setReturn_code(BusinessMsg.FAIL);
                transTransreq.setReturn_msg(BusinessMsg.getMsg(BusinessMsg.FAIL));
                transReqService.insert(transTransreq);
                throw new BusinessException(BusinessMsg.REPEAT_REGISTER, BusinessMsg.getMsg(BusinessMsg.REPEAT_REGISTER));
            }
            if(baseResponse == null){
                logger.info("【企业开户】第一次插入，添加redis流水号：" + transTransreq.getTrans_serial());
                CacheUtil.getCache().set(Constants.OPEN_ACCOUNT_PARAMS_KEY + transTransreq.getTrans_serial(), JSON.toJSONString(companyRegisterRequest));
                //保存24小时
                CacheUtil.getCache().expire(Constants.OPEN_ACCOUNT_PARAMS_KEY + transTransreq.getTrans_serial(), 24 * 3600);
            }else {
                transSerial = baseResponse.getTrans_serial();
            }
        }catch (Exception e){
            logger.info("【企业开户】异常", e);
            transTransreq.setStatus(FlowStatus.FAIL.getCode());
            transTransreq.setReturn_code(BusinessMsg.FAIL);
            transTransreq.setReturn_msg(BusinessMsg.getMsg(BusinessMsg.FAIL));
            transReqService.insert(transTransreq);
            if(e instanceof BusinessException){
                throw e;
            }else {
                throw new BusinessException(BusinessMsg.UNKNOW_ERROE, BusinessMsg.getMsg(BusinessMsg.UNKNOW_ERROE));
            }
        }
        return transSerial;
    }

    @Override
    @Transactional
    public BaseResponse setNewPassword(SetNewPasswordRequest setNewPasswordRequest) throws BusinessException {
        logger.info(String.format("【交易密码设置】开始|order_no:%s|params:%s",
                setNewPasswordRequest.getOrder_no(),JSON.toJSONString(setNewPasswordRequest)));
        String platcust=setNewPasswordRequest.getPlatcust();
        String redisKey=Constants.SET_PWD_PARAMS_KEY+setNewPasswordRequest.getTrans_serial();
        TransTransreq transTransreq=null;
        SetPwdRequest setPwdRequest=null;
        if(StringUtils.isBlank(setNewPasswordRequest.getTrans_serial())){
            transTransreq=transReqService.getTransTransReq(setNewPasswordRequest.clone(),
                    TransConsts.SET_PWD_CODE,TransConsts.SET_PWD_NAME,false);
            BaseResponse baseResponse=transReqService.insert(transTransreq);
            if(baseResponse!=null){
                throw new BusinessException(BusinessMsg.ORDERNUMBER_REPEATED,BusinessMsg.getMsg(BusinessMsg.ORDERNUMBER_REPEATED));
            }
        }
        BaseResponse baseResponse=new BaseResponse();
        try{
            if(transTransreq==null){
                Object setPasswordObj=CacheUtil.getCache().get(redisKey);
                if(setPasswordObj!=null){
                    setPwdRequest= JSON.parseObject((String)setPasswordObj,SetPwdRequest.class);
                    platcust=setPwdRequest.getPlatcust();
                    setNewPasswordRequest.setMall_no(setPwdRequest.getMall_no());
                    setNewPasswordRequest.setMer_no(setPwdRequest.getMer_no());
                    setNewPasswordRequest.setNotify_url(setPwdRequest.getNotify_url());
                }else{
                    logger.info(String.format("【交易密码设置】redis中找不到原数据|order_no:%s|platcust:%s",
                            setNewPasswordRequest.getOrder_no(),platcust));
                    throw new BusinessException(BusinessMsg.ORIGINAL_ORDER_NOEXISTENT,BusinessMsg.getMsg(BusinessMsg.ORIGINAL_ORDER_NOEXISTENT));
                }
                Object transreqObj=CacheUtil.getCache().get(Constants.TRANSREQ_ORDER_KEY+serviceName+":"+setPwdRequest.getOrder_no());
                if(transreqObj==null){
                    logger.info(String.format("【交易密码设置】redis中找不到原订单|order_no:%s|platcust:%s",
                            setNewPasswordRequest.getOrder_no(),platcust));
                    throw new BusinessException(BusinessMsg.ORIGINAL_ORDER_NOEXISTENT,BusinessMsg.getMsg(BusinessMsg.ORIGINAL_ORDER_NOEXISTENT));
                }
                transTransreq=JSON.parseObject((String) transreqObj,TransTransreq.class);
                userBindCardService.validMsgCode(transTransreq.getOrder_no(),setNewPasswordRequest.getIdentifying_code(),
                        setNewPasswordRequest.getTrans_serial(),"交易密码设置");
            }
            //验证账户状态
            EaccUserinfo eaccUserinfo=accountSearchService.queryEaccUserInfoByEaccAccountInfo(
                    setNewPasswordRequest.getMall_no(),setNewPasswordRequest.getMer_no(),platcust);
            if(null==eaccUserinfo){
                logger.info(String.format("【交易密码设置】用户不存在|order_no:%s|platcust:%s",
                        setNewPasswordRequest.getOrder_no(),platcust));
                throw new BusinessException(BusinessMsg.INVALID_ACCOUNT,BusinessMsg.getMsg(BusinessMsg.INVALID_ACCOUNT));
            }

            String tran_flag=TranFlag.SET.getCode();
            EaccCardinfo unActiveCardInfo=null;
            if(!Constants.YES.equals(eaccUserinfo.getPwd_flg())){
                List<EaccCardinfo> eaccCardinfoList=accountSearchService.queryEaccCardInfo(
                        setNewPasswordRequest.getMall_no(),platcust,null,CardStatus.ACTIVE.getCode());
                if(eaccCardinfoList.size()<=0){
                    logger.info(String.format("【交易密码设置】用户未绑卡或未激活|order_no:%s|platcust:%s",
                            setNewPasswordRequest.getOrder_no(),platcust));
                    throw new BusinessException(BusinessMsg.UNBIND_CARD_ERROR,BusinessMsg.getMsg(BusinessMsg.UNBIND_CARD_ERROR)+":用户未绑卡或未激活");
                }
                //激活账户
                unActiveCardInfo=eaccCardinfoList.get(0);
                AccountSubjectInfo updateAccountSubjectInfoParams=new AccountSubjectInfo();
                updateAccountSubjectInfoParams.setEnabled(AcctStatus.ACTIVE.getCode());
                accountOprationService.updateAccountSubjectInfo(updateAccountSubjectInfoParams,
                        platcust,setNewPasswordRequest.getMer_no(),null,null);
                unActiveCardInfo.setEnabled(AcctStatus.ACTIVE.getCode());
                updateCardInfo(unActiveCardInfo);
                EaccUserinfo updateEaccuserinfo=new EaccUserinfo();
                updateEaccuserinfo.setEnabled(AcctStatus.ACTIVE.getCode());
                updateEaccuserinfo.setPwd_flg(Constants.YES);
                updateEaccUserInfo(updateEaccuserinfo,setNewPasswordRequest.getMall_no(),eaccUserinfo.getMallcust());
            }else{
                tran_flag=TranFlag.RESET.getCode();
            }
            //TODO 设置密码
            Map<String,Object> sendParams = new HashMap<String, Object>();
            sendParams.put("mall_no",setNewPasswordRequest.getMall_no());
            sendParams.put("mer_no",setNewPasswordRequest.getMer_no());
            sendParams.put("id_kind",eaccUserinfo.getCus_type());
            sendParams.put("id_no",CusType.PERSONAL.getCode().equals(eaccUserinfo.getCus_type())?eaccUserinfo.getId_code():
                    (StringUtils.isEmpty(eaccUserinfo.getBank_license())?eaccUserinfo.getBusiness_license():eaccUserinfo.getBank_license()));
            sendParams.put("tran_flag",tran_flag);
            sendParams.put("plat_cust",eaccUserinfo.getMallcust());
            sendParams.put("random_key",setNewPasswordRequest.getRandom_key());
            sendParams.put("password",setNewPasswordRequest.getTrans_pwd());
            sendParams.put("card_no",unActiveCardInfo==null?null:unActiveCardInfo.getCard_no());
            sendParams.put("mobile_tel",unActiveCardInfo==null?null:unActiveCardInfo.getMobile());
            //==============CCB参数=====================
            sendParams.put("type",setNewPasswordRequest.getType());
            //==========================================
            sendParams.put("host",sysParameterService.querySysParameter(setNewPasswordRequest.getMall_no(),URLConfigUtil.EPAY_SERVER_KEY));
            sendParams.put("url",sysParameterService.querySysParameter(setNewPasswordRequest.getMall_no(),URLConfigUtil.ICIS_PASSWORDMODIFY));
            logger.info("【交易密码设置】==========请求第三方支付参数："+JSON.toJSON(sendParams));
            Map<String,Object> resultMap = adapterService.setOrResetPassword(sendParams);
            logger.info("【交易密码设置】==========第三方支付返回结果："+JSON.toJSON(resultMap));
            if(null == resultMap || null == resultMap.get("order_status") || "".equals(resultMap.get("order_status"))){
                throw new BusinessException(BusinessMsg.CALL_REMOTE_ERROR,BusinessMsg.getMsg(BusinessMsg.CALL_REMOTE_ERROR)+"第三方支付返回结果解析失败");
            }else{
                baseResponse.setRecode(MapUtils.getString(resultMap, "recode"));
                baseResponse.setRemsg(MapUtils.getString(resultMap, "remsg"));
                transTransreq.setReturn_code(MapUtils.getString(resultMap, "recode"));
                transTransreq.setReturn_msg(MapUtils.getString(resultMap, "remsg"));
                if(!OrderStatus.SUCCESS.getCode().equals((String)resultMap.get("order_status"))) {
                    logger.info("【交易密码设置】失败："+resultMap.get("order_status"));
                    throw new BusinessException(baseResponse);
                }
            }

            if(StringUtils.isNotBlank(setNewPasswordRequest.getNotify_url())){
                RegisterNotifyData registerNotifyData=new RegisterNotifyData();
                //获取授权信息
                List<EaccUserauth> userauthList=authCheckService.queryAuthInfo(setNewPasswordRequest.getMall_no(),setNewPasswordRequest.getMer_no(),platcust);
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
                }else if (setPwdRequest!=null){
                    registerNotifyData.setAuthed_type(setPwdRequest.getAuthed_type());
                    registerNotifyData.setAuthed_amount(setPwdRequest.getAuthed_amount());
                    registerNotifyData.setAuthed_limittime(setPwdRequest.getAuthed_limittime());
                }
                //给平台发送异步通知
                registerNotifyData.setRemsg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));
                registerNotifyData.setRecode(BusinessMsg.SUCCESS);
                registerNotifyData.setOrder_no(transTransreq.getOrder_no());
                registerNotifyData.setOrder_status(OrderStatus.SUCCESS.getCode());
                registerNotifyData.setOrder_info(OrderStatus.SUCCESS.getMsg());
                registerNotifyData.setPlatcust(platcust);
                NotifyData notifyData=new NotifyData();
                notifyData.setMall_no(setNewPasswordRequest.getMall_no());
                notifyData.setNotifyUrl(setNewPasswordRequest.getNotify_url());
                notifyData.setNotifyContent(JSON.toJSONString(registerNotifyData));
                notifyService.addNotify(notifyData);
                logger.info("【交易密码设置】添加到异步队列成功，order_no：" + setNewPasswordRequest.getOrder_no());
            }

            baseResponse.setRecode(BusinessMsg.SUCCESS);
            baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));
            transTransreq.setStatus(FlowStatus.SUCCESS.getCode());
            transTransreq.setReturn_code(BusinessMsg.SUCCESS);
            transTransreq.setReturn_msg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));
        }catch (Exception e){
            logger.error(String.format("【交易密码设置】异常|order_no:%s|error:",
                    setNewPasswordRequest.getOrder_no()),e);
            if (e instanceof BusinessException){
                baseResponse=((BusinessException) e).getBaseResponse();
            }else{
                baseResponse.setRecode(BusinessMsg.RUNTIME_EXCEPTION);
                baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.RUNTIME_EXCEPTION));
            }
            transTransreq.setStatus(FlowStatus.FAIL.getCode());
            transTransreq.setReturn_code(baseResponse.getRecode());
            transTransreq.setReturn_msg(baseResponse.getRemsg());
        }
        transReqService.insert(transTransreq);
        return baseResponse;
    }

    @Override
    public SetPwdRequest setPassword(SetPwdRequest setPwdRequest)  throws BusinessException {
        TransTransreq transTransreq = transReqService.getTransTransReq(setPwdRequest.clone(), TransConsts.SET_PWD_CODE, TransConsts.SET_PWD_NAME, false);
        String transSerial = transTransreq.getTrans_serial();
        transTransreq.setStatus(FlowStatus.REQUESTSUCCESS.getCode());
        BaseResponse baseResponse = transReqService.insert(transTransreq);
        try{
            logger.info("【设置密码】校验用户是否存在");
            EaccUserinfo eaccUserinfo = queryEaccUserInfoByMallNoAndPlatcust(setPwdRequest.getMall_no(), setPwdRequest.getPlatcust());
            if(eaccUserinfo == null){
                logger.info("【设置密码】用户不存在");
                transTransreq.setStatus(FlowStatus.FAIL.getCode());
                transTransreq.setReturn_code(BusinessMsg.FAIL);
                transTransreq.setReturn_msg(BusinessMsg.getMsg(BusinessMsg.FAIL));
                transTransreq.setRemark(BusinessMsg.getMsg(BusinessMsg.INVALID_ACCOUNT));
                transReqService.insert(transTransreq);
                throw new BusinessException(BusinessMsg.INVALID_ACCOUNT,BusinessMsg.getMsg(BusinessMsg.INVALID_ACCOUNT));
            }
            if(StringUtils.isEmpty(eaccUserinfo.getDefault_mobile())){
                logger.info(String.format("【设置密码】默认手机号为空，该用户没有可用绑卡数据，订单号：【%s】",
                        setPwdRequest.getOrder_no()));
                throw new BusinessException(BusinessMsg.NO_BIND_CARD_DATA);
            }
            //设置默认手机号
            setPwdRequest.setMobile(eaccUserinfo.getDefault_mobile());
            if(baseResponse == null){
                logger.info("【设置密码】第一次插入，添加redis流水号：" + transTransreq.getTrans_serial());
                CacheUtil.getCache().set(Constants.SET_PWD_PARAMS_KEY + transTransreq.getTrans_serial(), JSON.toJSONString(setPwdRequest));
                //保存24小时
                CacheUtil.getCache().expire(Constants.SET_PWD_PARAMS_KEY + transTransreq.getTrans_serial(), 24 * 3600);
            }else {
                transSerial = baseResponse.getTrans_serial();
            }
        }catch (Exception e){
            logger.info("【设置密码】异常", e);
            transTransreq.setStatus(OrderStatus.FAIL.getCode());
            transTransreq.setReturn_code(BusinessMsg.FAIL);
            transTransreq.setReturn_msg(BusinessMsg.getMsg(BusinessMsg.FAIL));
            transReqService.insert(transTransreq);
            if(e instanceof BusinessException){
                throw e;
            }else {
                throw new BusinessException(BusinessMsg.RUNTIME_EXCEPTION, BusinessMsg.getMsg(BusinessMsg.RUNTIME_EXCEPTION));
            }
        }
        setPwdRequest.setTrans_serial(transSerial);
        return setPwdRequest;
    }

    @Override
    public ModifyPwdRequest modifyPassword(ModifyPwdRequest modifyPwdRequest) {
        TransTransreq transTransreq = transReqService.getTransTransReq(modifyPwdRequest.clone(), TransConsts.UPDATE_PWD_CODE, TransConsts.UPDATE_PWD_NAME, false);
        String transSerial = transTransreq.getTrans_serial();
        transTransreq.setStatus(FlowStatus.REQUESTSUCCESS.getCode());
        BaseResponse baseResponse = transReqService.insert(transTransreq);
        try{
            logger.info("【修改密码】校验用户是否存在");
            EaccUserinfo eaccUserinfo = checkUserInfo(modifyPwdRequest.getMall_no(), modifyPwdRequest.getName(), modifyPwdRequest.getId_code(), modifyPwdRequest.getMer_no(), false);
            modifyPwdRequest.setType(CusType.PERSONAL.getCode());
            if(eaccUserinfo == null){
                eaccUserinfo = checkMallUserInfo(modifyPwdRequest.getMall_no(), modifyPwdRequest.getName(), null);
                if(eaccUserinfo == null) {
                    logger.info("【修改密码】用户不存在");
                    transTransreq.setStatus(FlowStatus.FAIL.getCode());
                    transTransreq.setReturn_code(BusinessMsg.FAIL);
                    transTransreq.setReturn_msg(BusinessMsg.getMsg(BusinessMsg.FAIL));
                    transTransreq.setRemark(BusinessMsg.getMsg(BusinessMsg.INVALID_ACCOUNT));
                    transReqService.insert(transTransreq);
                    throw new BusinessException(BusinessMsg.INVALID_ACCOUNT, BusinessMsg.getMsg(BusinessMsg.INVALID_ACCOUNT));
                }else {
                    modifyPwdRequest.setType(CusType.COMPANY.getCode());
                }
            }
            if(StringUtils.isEmpty(eaccUserinfo.getDefault_mobile())){
                logger.info(String.format("【修改密码】默认手机号为空，该用户没有可用绑卡数据，订单号：【%s】",
                        modifyPwdRequest.getOrder_no()));
                throw new BusinessException(BusinessMsg.NO_BIND_CARD_DATA, BusinessMsg.getMsg(BusinessMsg.NO_BIND_CARD_DATA));
            }
            //设置默认手机号
            modifyPwdRequest.setMobile(eaccUserinfo.getDefault_mobile());
            if(baseResponse == null){
                logger.info("【修改密码】第一次插入，存入redis流水号：" + transTransreq.getTrans_serial());
                CacheUtil.getCache().set(Constants.SET_PWD_PARAMS_KEY + transTransreq.getTrans_serial(), JSON.toJSONString(modifyPwdRequest));
                //保存24小时
                CacheUtil.getCache().expire(Constants.SET_PWD_PARAMS_KEY + transTransreq.getTrans_serial(), 24 * 3600);
            }else {
                transSerial = baseResponse.getTrans_serial();
            }
        }catch (Exception e){
            logger.info("【修改密码】异常", e);
            transTransreq.setStatus(OrderStatus.FAIL.getCode());
            transTransreq.setReturn_code(BusinessMsg.FAIL);
            transTransreq.setReturn_msg(BusinessMsg.getMsg(BusinessMsg.FAIL));
            transReqService.insert(transTransreq);
            if(e instanceof BusinessException){
                throw e;
            }else {
                throw new BusinessException(BusinessMsg.RUNTIME_EXCEPTION, BusinessMsg.getMsg(BusinessMsg.RUNTIME_EXCEPTION));
            }
        }
        modifyPwdRequest.setTransSerial(transSerial);
        return modifyPwdRequest;
    }


}
