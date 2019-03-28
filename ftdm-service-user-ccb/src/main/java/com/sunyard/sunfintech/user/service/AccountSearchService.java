package com.sunyard.sunfintech.user.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sunyard.sunfintech.account.provider.IAccountQueryService;
import com.sunyard.sunfintech.core.Constants;
import com.sunyard.sunfintech.core.base.BaseResponse;
import com.sunyard.sunfintech.core.base.BaseServiceSimple;
import com.sunyard.sunfintech.core.dic.*;
import com.sunyard.sunfintech.core.exception.BusinessException;
import com.sunyard.sunfintech.core.exception.BusinessMsg;
import com.sunyard.sunfintech.core.util.*;
import com.sunyard.sunfintech.custdao.mapper.CustPlatPlatinfoMapper;
import com.sunyard.sunfintech.dao.entity.*;
import com.sunyard.sunfintech.dao.mapper.*;
import com.sunyard.sunfintech.pub.provider.ISysParameterService;
import com.sunyard.sunfintech.thirdparty.provider.IAdapterService;
import com.sunyard.sunfintech.user.model.bo.*;
import com.sunyard.sunfintech.user.provider.IAccountSearchService;
import com.sunyard.sunfintech.user.provider.IProdSearchService;
import com.sunyard.sunfintech.user.provider.IUserAccountService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheConfig;

import java.util.*;

/**
 * Created by Zz on 2017/5/24.
 */
@Service(interfaceClass = IAccountSearchService.class)
@CacheConfig(cacheNames="accountSearchService")
@org.springframework.stereotype.Service
public class AccountSearchService extends BaseServiceSimple implements IAccountSearchService {

    @Value("${service.name}")
    private String serviceName;

    @Autowired
    private IAccountQueryService accountQueryService;

    @Autowired
    private IUserAccountService userAccountService;

    @Autowired
    private IProdSearchService prodSearchService;

    @Autowired
    private EaccUserinfoMapper eaccUserInfoMapper;

    @Autowired
    private EaccAccountinfoMapper eaccAccountInfoMapper;

    @Autowired
    private CustPlatPlatinfoMapper custPlatPlatinfoMapper;

    @Autowired
    private EaccCardinfoMapper eaccCardInfoMapper;

    @Autowired
    private RwRechargeMapper rwRechargeMapper;

    @Autowired
    private PlatPaycodeMapper platPayCodeMapper;

    @Autowired
    private EaccUserauthMapper eaccUserauthMapper;

    @Autowired
    private ISysParameterService sysParameterService;

    @Autowired
    private IAdapterService adapterService;

    /**
     *  pzy   -------- 以测
     *  电子账户查询
     */
    @Override
    public EaccountInfoResponse queryEaccountInfo(EaccountInfoRequest eaccountInfoRequest) throws BusinessException {

        logger.info("search:---------------电子账户查询");

        EaccountInfoResponse eaccountInfoResponse = new EaccountInfoResponse();

        if(StringUtils.isBlank(eaccountInfoRequest.getPid()) && StringUtils.isBlank(eaccountInfoRequest.getPlatcust())){
            BaseResponse baseResponse=new BaseResponse();
            baseResponse.setRecode(BusinessMsg.PARAMETER_ERROR);
            baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.PARAMETER_ERROR) + "------------请填写用户编号或者证件号");
            throw new BusinessException(baseResponse);
        }

        //查询用户账户是否存在
        EaccUserinfo eaccUserInfo = userAccountService.queryEaccUserInfo(eaccountInfoRequest.getMall_no(),eaccountInfoRequest.getPlatcust(),eaccountInfoRequest.getPid(),null);
        if(eaccUserInfo == null){
            BaseResponse baseResponse=new BaseResponse();
            baseResponse.setRecode(BusinessMsg.INVALID_ACCOUNT);
            baseResponse.setRemsg("信息有误或用户账户不存在");
            throw new BusinessException(baseResponse);
        }

        //接受用户信息
        List<EaccCardinfo> eaccCardInfoList = userAccountService.queryEaccCardInfolist(eaccUserInfo.getMall_no(),eaccUserInfo.getMallcust(),null);
        if(eaccCardInfoList.size()==0){
            BaseResponse baseResponse=new BaseResponse();
            baseResponse.setRecode(BusinessMsg.INVALID_ACCOUNT);
            baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.INVALID_ACCOUNT) + ",用户绑卡信息不存在");
            throw new BusinessException(baseResponse);
        }
        EaccCardinfo eaccCardInfo=eaccCardInfoList.get(0);

        EaccountInfoResponseDetail eaccountInfoResponseDetail = new EaccountInfoResponseDetail();
        eaccountInfoResponseDetail.setPlatcust(eaccUserInfo.getMallcust());
        eaccountInfoResponseDetail.setName(eaccUserInfo.getName());
        eaccountInfoResponseDetail.setBoundbankid(eaccCardInfo.getCard_no());
        eaccountInfoResponseDetail.setMobile(eaccCardInfo.getMobile());
        eaccountInfoResponseDetail.setPid(eaccUserInfo.getId_code());
        eaccountInfoResponseDetail.setEflg(eaccUserInfo.getEaccount_flg());

        eaccountInfoResponse.setRecode(BusinessMsg.SUCCESS);
        eaccountInfoResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));
        eaccountInfoResponse.setData_detail(eaccountInfoResponseDetail);

        return eaccountInfoResponse;
    }

    /**
     *  pzy  -------- 以测
     *  电子账户余额查询
     */
    @Override
    public EaccountBalanceResponse queryEaccountInfoBalance(EaccountBalanceRequest eaccountBalanceRequest) throws BusinessException {

        logger.info("search:---------------电子账户余额查询");

        EaccountBalanceResponse eaccountBalanceResponse = new EaccountBalanceResponse();

        //查询账户是否存在
        List<AccountSubjectInfo> accountSubjectInfos = accountQueryService.queryFINANCINGPlatAccountlist(eaccountBalanceRequest.getMall_no(),eaccountBalanceRequest.getPlatcust(), AccountType.ElectronicAccount.getCode(), Ssubject.EACCOUNT.getCode());

        if(CollectionUtils.isNotEmpty(accountSubjectInfos) && accountSubjectInfos.size() != 2){
            BaseResponse baseResponse=new BaseResponse();
            baseResponse.setRecode(BusinessMsg.INVALID_ACCOUNT);
            baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.INVALID_ACCOUNT) + ",电子信息有误，电子账户条数："+accountSubjectInfos.size());
            throw new BusinessException(baseResponse);
        }

        //查询用户账户是否存在
        EaccUserinfo eaccUserInfo = userAccountService.queryEaccUserInfoByMallNoAndPlatcust(eaccountBalanceRequest.getMall_no(),eaccountBalanceRequest.getPlatcust());
        if(eaccUserInfo == null){
            BaseResponse baseResponse=new BaseResponse();
            baseResponse.setRecode(BusinessMsg.INVALID_ACCOUNT);
            baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.INVALID_ACCOUNT) + ",用户信息不存在");
            throw new BusinessException(baseResponse);
        }

        if(eaccUserInfo.getEaccount_flg() == null || eaccUserInfo.getEaccount_flg().equals("0")){
            BaseResponse baseResponse=new BaseResponse();
            baseResponse.setRecode(BusinessMsg.INVALID_ACCOUNT);
            baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.INVALID_ACCOUNT) + ",电子账户未开通");
            throw new BusinessException(baseResponse);
        }

        List<EaccountBalanceResponseDetail> eaccountBalanceResponseDetails = new ArrayList<EaccountBalanceResponseDetail>();

        for(AccountSubjectInfo accountSubjectInfo : accountSubjectInfos){
            EaccountBalanceResponseDetail eaccountBalanceResponseDetail = new EaccountBalanceResponseDetail();
            eaccountBalanceResponseDetail.setSubject(accountSubjectInfo.getSubject());
            eaccountBalanceResponseDetail.setF_balance(accountSubjectInfo.getF_balance());
            eaccountBalanceResponseDetail.setN_balance(accountSubjectInfo.getN_balance());
            eaccountBalanceResponseDetail.setT_balance(accountSubjectInfo.getT_balance());
            eaccountBalanceResponseDetails.add(eaccountBalanceResponseDetail);
        }
        eaccountBalanceResponse.setData_detail(eaccountBalanceResponseDetails);
        eaccountBalanceResponse.setRecode(BusinessMsg.SUCCESS);
        eaccountBalanceResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));

        return eaccountBalanceResponse;
    }

    /**
	 * 查询电子账户信息
	 * @author Lid
	 * @param realEaccountRequest
	 * @return RealEaccountResponse
	 * @throws BusinessException
	 */
	@Override
	public RealEaccountResponse queryEaccount(RealEaccountRequest realEaccountRequest) throws BusinessException {
		RealEaccountResponse eaccountResponse = new RealEaccountResponse();
        logger.info("search:---------------查询电子账户信息");
        
        //查询账户是否存在
        List<AccountSubjectInfo> accountSubjectInfos = accountQueryService.queryAccountlist(realEaccountRequest.getMer_no(),realEaccountRequest.getPlatcust());
        if(accountSubjectInfos.size() == 0){
            BaseResponse baseResponse=new BaseResponse();
            baseResponse.setRecode(BusinessMsg.INVALID_ACCOUNT);
            baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.INVALID_ACCOUNT));
            throw new BusinessException(baseResponse);
        }

        //查询用户账户是否存在
        EaccUserinfo eaccUserInfo = userAccountService.queryEaccUserInfoByMallNoAndPlatcust(realEaccountRequest.getMall_no(),realEaccountRequest.getPlatcust());
        if(eaccUserInfo == null || eaccUserInfo.getEaccount_flg().equals("0")){
            BaseResponse baseResponse=new BaseResponse();
            baseResponse.setRecode(BusinessMsg.INVALID_ACCOUNT);
            baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.INVALID_ACCOUNT) + ",电子账户未开通");
            throw new BusinessException(baseResponse);
        }

		RealEaccountDetailResponse eaccountDetailResponse = new RealEaccountDetailResponse();
		eaccountDetailResponse.setEaccount(eaccUserInfo.getEaccount());
		String json = JSON.toJSONString(eaccountDetailResponse);
		eaccountResponse.setData(json);
		eaccountResponse.setRecode(BusinessMsg.SUCCESS);
		eaccountResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));
		return eaccountResponse;
	}

    @Override
    public String queryMallNoByPlatNo(String plat_no) throws BusinessException {

	    if(StringUtils.isBlank(plat_no))
	        throw new BusinessException(BusinessMsg.FAIL,"查询集团号时平台号必传");
        logger.info("【获取系统参数】-->【根据平台号查询集团号】-->从本地内存获取系统参数: " + plat_no);
        String dataKey = new StringBuilder(plat_no).append(Constants.DATA_SPLIT_WITH).append(plat_no)
                .append(Constants.DATA_SPLIT_WITH).append(plat_no).toString();
        String redisKey = Constants.getSysParamterKey(serviceName, dataKey);
        logger.debug("【获取系统参数】-->【根据平台号查询集团号】-->redisKey:" + redisKey);

        Object localValue = LocalCacheUtil.getCache(redisKey);
        if(null != localValue){
            return localValue.toString();
        }
        logger.info("【获取系统参数】-->【根据平台号查询集团号】-->开始从reids获取系统参数");

        if(null == CacheUtil.getCache().get(redisKey)){
            logger.info("【获取系统参数】-->【根据平台号查询集团号】-->reids中没有key所对应的系统参数");
            String mall = custPlatPlatinfoMapper.getMallNoByPlatNo(plat_no);
            if(StringUtils.isNotBlank(mall)){
                logger.info("【获取系统参数】-->【根据平台号查询集团号】-->把"+redisKey+":"+mall+"系统参数设置到reids中");
                //重新设置参数到缓存中
                CacheUtil.getCache().set(redisKey, mall);
                //设置本地参数
                LocalCacheUtil.setCache(redisKey, mall);
                return mall;
            }else{
                logger.info("【获取系统参数】-->【根据平台号查询集团号】-->获取不到集团号: " + plat_no + ",返回空值");
                //TODO ftdm-web: BillcheckBusiness.requestSortingFile 需要根据集团号查询，其它地方应该都是根据平台号查询，将这里返回空，不再抛出异常
                //throw new BusinessException(BusinessMsg.FAIL,"根据平台号查询集团号获取不到集团号");
                return StringUtils.EMPTY;
            }
        }

        LocalCacheUtil.setCache(redisKey, CacheUtil.getCache().get(redisKey).toString());
        return LocalCacheUtil.getCache(redisKey).toString();
    }

    @Override
    public String queryNoCacheMallNoByPlatNo(String plat_no) throws BusinessException {
        return custPlatPlatinfoMapper.getMallNoByPlatNo(plat_no);
    }

    @Override
    public List<String> queryPlatNoByMallNo(String mall_no) throws BusinessException {

	    if(StringUtils.isBlank(mall_no))
	        throw new BusinessException(BusinessMsg.FAIL,"查询平台号时集团号必传");
        logger.info("【获取系统参数】-->【根据集团号查询平台号】-->从本地内存获取系统参数: " + mall_no);
        String dataKey = new StringBuilder(mall_no).append(Constants.DATA_SPLIT_WITH).append(mall_no)
                .append(Constants.DATA_SPLIT_WITH).append(mall_no).toString();
        String redisKey = Constants.getSysParamterKey(serviceName, dataKey);
        logger.debug("【获取系统参数】-->【根据集团号查询平台号】-->redisKey:" + redisKey);

        String localValue = (String) LocalCacheUtil.getCache(redisKey);
        if(null != localValue){
            String[] platNos = localValue.split(",");
            List<String> platNoList = new ArrayList<>();
            for(String platNo : platNos){
                platNoList.add(platNo);
            }
            return platNoList;
        }
        logger.info("【获取系统参数】-->【根据平台号查询集团号】-->开始从reids获取系统参数");

        if(null == CacheUtil.getCache().get(redisKey)){
            logger.info("【获取系统参数】-->【根据集团号查询平台号】-->reids中没有key所对应的系统参数");
            List<String> platNos = custPlatPlatinfoMapper.getPlatNoByMallNo(mall_no);
            if(platNos.size() != 0){
                logger.info("【获取系统参数】-->【根据集团号查询平台号】-->把"+redisKey+":"+mall_no+"系统参数设置到reids中");
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < platNos.size(); i++) {
                    if (i == 0) {
                        sb.append(platNos.get(i));
                    }else {
                        sb.append(",").append(platNos.get(i));
                    }
                }
                //重新设置参数到缓存中
                CacheUtil.getCache().set(redisKey, sb.toString());
                //设置本地参数
                LocalCacheUtil.setCache(redisKey, sb.toString());
                return platNos;
            }else{
                logger.info("【获取系统参数】-->【根据集团号查询平台号】-->获取不到平台号: " + mall_no);
                throw new BusinessException(BusinessMsg.FAIL,"根据集团号查询集团号获取不到平台号");
            }
        }
        String strPlatNo = CacheUtil.getCache().get(redisKey).toString();
        LocalCacheUtil.setCache(redisKey, strPlatNo);
        String[] platNos = localValue.split(",");
        List<String> platNoList = new ArrayList<>();
        for(String platNo : platNos){
            platNoList.add(platNo);
        }
        return platNoList;
    }

    @Override
    public List<String> queryNoCachePlatNoByMallNo(String mall_no) throws BusinessException {
        return custPlatPlatinfoMapper.getPlatNoByMallNo(mall_no);
    }

    /**
     *  @author RaoYL
     *  @version 20180125
     *  账户信息查询
     */
    @Override
    public AccountInfoResponse queryAccountInfo(EaccountInfoRequest eaccountInfoRequest) throws BusinessException {

        logger.info(String.format("【用户信息查询】开始查询|order_no:%s|params:%s",
                eaccountInfoRequest.getOrder_no(),JSON.toJSONString(eaccountInfoRequest)));

        AccountInfoResponse accountInfoResponse = new AccountInfoResponse();

        if(StringUtils.isBlank(eaccountInfoRequest.getPid()) && StringUtils.isBlank(eaccountInfoRequest.getPlatcust())
                && StringUtils.isBlank(eaccountInfoRequest.getOrg_name())){
            logger.info(String.format("【用户信息查询】参数错误|order_no:%s|platcust:%s|id_code:%s",
                    eaccountInfoRequest.getOrder_no(),eaccountInfoRequest.getPlatcust(),eaccountInfoRequest.getPid()));
            BaseResponse baseResponse=new BaseResponse();
            baseResponse.setRecode(BusinessMsg.PARAMETER_ERROR);
            baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.PARAMETER_ERROR) + "|请填写用户编号或者证件号");
            throw new BusinessException(baseResponse);
        }

        //查询用户账户是否存在(包含已销户客户)
        EaccUserinfo eaccUserInfo = userAccountService.queryEaccUserInfo(
                eaccountInfoRequest.getMall_no(),eaccountInfoRequest.getPlatcust(),eaccountInfoRequest.getPid(),eaccountInfoRequest.getOrg_name(),
                AcctStatus.ACTIVE.getCode(),AcctStatus.FORZEN.getCode(),AcctStatus.UNACTIVE.getCode());
        if(eaccUserInfo == null){
            logger.info(String.format("【用户信息查询】用户不存在|order_no:%s|platcust:%s|id_code:%s",
                    eaccountInfoRequest.getOrder_no(),eaccountInfoRequest.getPlatcust(),eaccountInfoRequest.getPid()));
            BaseResponse baseResponse=new BaseResponse();
            baseResponse.setRecode(BusinessMsg.INVALID_ACCOUNT);
            baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.INVALID_ACCOUNT));
            throw new BusinessException(baseResponse);
        }

        //查询用户绑卡信息(不包含已解绑卡)
        List<EaccCardinfo> eaccCardinfoList = userAccountService.queryCardInfoForMultiCards(eaccUserInfo.getMall_no(),eaccUserInfo.getMallcust(),
                AcctStatus.ACTIVE.getCode(),AcctStatus.FORZEN.getCode());
        AccountInfoResponseDetail accountInfoResponseDetail = new AccountInfoResponseDetail();
        accountInfoResponseDetail.setPlatcust(eaccUserInfo.getMallcust());
        accountInfoResponseDetail.setName(eaccUserInfo.getName());
        accountInfoResponseDetail.setPid(eaccUserInfo.getId_code());
        accountInfoResponseDetail.setEflg(eaccUserInfo.getEaccount_flg()!=null?eaccUserInfo.getEaccount_flg():"0");
        accountInfoResponseDetail.setMobile(eaccUserInfo.getMobile());
        accountInfoResponseDetail.setCus_type(eaccUserInfo.getCus_type());
        accountInfoResponseDetail.setBank_license(eaccUserInfo.getBank_license());
        accountInfoResponseDetail.setOrg_name(eaccUserInfo.getOrg_name());
        accountInfoResponseDetail.setBusiness_license(eaccUserInfo.getBusiness_license());
        //TODO accountInfoResponseDetail.setPwd_flg();
        switch (eaccUserInfo.getEnabled()){
            case Constants.DISABLED:{
                accountInfoResponseDetail.setCancel_flg(Constants.ENABLED);
                accountInfoResponseDetail.setFreeze_flg(Constants.ENABLED);
                break;
            }
            case Constants.ENABLED:{
                accountInfoResponseDetail.setCancel_flg(Constants.DISABLED);
                accountInfoResponseDetail.setFreeze_flg(Constants.DISABLED);
                break;
            }
            case "2":{
                accountInfoResponseDetail.setCancel_flg(Constants.DISABLED);
                accountInfoResponseDetail.setFreeze_flg(Constants.ENABLED);
                break;
            }
        }
        List<EaccUserauth> eaccUserauthList=queryEaccUserAuth(eaccUserInfo.getMallcust(),
                eaccountInfoRequest.getMer_no(),eaccountInfoRequest.getMall_no(),AuthStatus.ALREADY_AUTH.getCode());
        if(eaccUserauthList.size()>0){
            List<AccountInfoResponseDetailAuthInfo> authInfoList=new ArrayList<>();
            for(EaccUserauth eaccUserauth:eaccUserauthList){
                AccountInfoResponseDetailAuthInfo authInfo=new AccountInfoResponseDetailAuthInfo();
                authInfo.setAuthed_amount(eaccUserauth.getAuthed_amount());
                authInfo.setAuthed_limittime(eaccUserauth.getAuthed_limittime());
                authInfo.setAuthed_type(eaccUserauth.getAuthed_type());
                authInfoList.add(authInfo);
            }
            accountInfoResponseDetail.setAuth_info(JSON.toJSONString(authInfoList));
        }
        List<CardInfoDetail> cardInfoDetailList = new ArrayList<>();
        if(eaccCardinfoList != null && eaccCardinfoList.size()!=0){
            for (EaccCardinfo cardinfo:eaccCardinfoList) {
                CardInfoDetail cardInfoDetail = new CardInfoDetail();
                cardInfoDetail.setMobile(cardinfo.getMobile());
                cardInfoDetail.setBoundbankid(cardinfo.getCard_no());
                cardInfoDetail.setType(CardType.CREDIT.getCode().equals(cardinfo.getCard_type())?"1":"0");
                PlatPaycode platPaycode=userAccountService.queryPayCodeByChannel(cardinfo.getMall_no(),cardinfo.getPay_channel());
                if(platPaycode!=null){
                    cardInfoDetail.setPay_code(platPaycode.getPay_code());
                }
                cardInfoDetailList.add(cardInfoDetail);
            }
        }
        accountInfoResponseDetail.setPwd_flg(eaccUserInfo.getPwd_flg()==null?Constants.NO:eaccUserInfo.getPwd_flg());
        accountInfoResponseDetail.setCardInfoDetail(cardInfoDetailList);
        accountInfoResponse.setRecode(BusinessMsg.SUCCESS);
        accountInfoResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));
        accountInfoResponse.setData_detail(accountInfoResponseDetail);

        return accountInfoResponse;
    }

    @Override
    public RwRecharge queryRwRecharge(String plat_no, String order_no) throws BusinessException {
        RwRechargeExample example=new RwRechargeExample();
        RwRechargeExample.Criteria criteria=example.createCriteria();

        if(StringUtils.isNotBlank(plat_no))
            criteria.andPlat_noEqualTo(plat_no);

        if(StringUtils.isNotBlank(order_no))
            criteria.andOrder_noEqualTo(order_no);

        criteria.andEnabledEqualTo(Constants.ENABLED);

        List<RwRecharge> platPayCodeList= rwRechargeMapper.selectByExample(example);

        if(platPayCodeList.size() > 1){
            BaseResponse baseResponse=new BaseResponse();
            baseResponse.setRecode(BusinessMsg.DATEBASE_EXCEPTION);
            baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.DATEBASE_EXCEPTION) + "请检查支付平台映射信息，平台映射信息出现出现重复数据 \n"
                    + "mall_no：" + plat_no + " \n order_no" + order_no);
            throw new BusinessException(baseResponse);
        }else if(platPayCodeList.size() < 1){
            return null;
        }
        return platPayCodeList.get(0);
    }

    @Override
    public EaccUserinfo checkUserinfo(ApplyQuickPayRequest applyQuickPayRequest) throws BusinessException {
        EaccUserinfo eaccUserInfo= null;
        //查询用户信息 是否有该用户
        eaccUserInfo = queryEaccUserInfoByEaccAccountInfo(applyQuickPayRequest.getMall_no(),applyQuickPayRequest.getMer_no(),applyQuickPayRequest.getPlatcust());

        if(null==eaccUserInfo){
            BaseResponse baseResponse=new BaseResponse();
            baseResponse.setRecode(BusinessMsg.INVALID_ACCOUNT);
            baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.INVALID_ACCOUNT)+"【eaccUserInfo】没有该用户");
            logger.info(applyQuickPayRequest.getMall_no(),applyQuickPayRequest.getMer_no(),applyQuickPayRequest.getPlatcust()+"eaccUserInfo表中没有该用户相关信息");
            throw new BusinessException(baseResponse);
        }else if(!applyQuickPayRequest.getId_code().equalsIgnoreCase(eaccUserInfo.getId_code())){
            BaseResponse baseResponse=new BaseResponse();
            baseResponse.setRecode(BusinessMsg.PARAMETER_ERROR);
            baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.PARAMETER_ERROR)+String.format("该证件号码【%s】与数据库用户信息不一致",applyQuickPayRequest.getId_code()));
            logger.info(applyQuickPayRequest.getId_code()+"<--该身份证与数据库中的【eaccUserInfo】身份证信息不一致");
            throw new BusinessException(baseResponse);
        }
        if(!applyQuickPayRequest.getName().equals(eaccUserInfo.getName())){
            BaseResponse baseResponse=new BaseResponse();
            baseResponse.setRecode(BusinessMsg.PARAMETER_ERROR);
            baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.PARAMETER_ERROR)+String.format("该用户姓名【%s】与数据库开户姓名不一致",applyQuickPayRequest.getName()));
            logger.info(applyQuickPayRequest.getName()+"<--该姓名与数据库中的【eaccUserInfo】姓名信息不一致");
            throw new BusinessException(baseResponse);
        }
        //查询用户绑卡信息 是否绑卡
        List<EaccCardinfo> eaccCardInfoList = queryEaccCardInfo(applyQuickPayRequest.getMall_no(),applyQuickPayRequest.getPlatcust(),applyQuickPayRequest.getCard_no(),CardStatus.ACTIVE.getCode());
        if(eaccCardInfoList.size() < 1){
            BaseResponse baseResponse=new BaseResponse();
            baseResponse.setRecode(BusinessMsg.UNBIND_CARD_ERROR);
            baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.UNBIND_CARD_ERROR)+String.format("该用户【%s】未绑卡，请先短验绑卡",applyQuickPayRequest.getCard_no()));
            logger.info("数据库【eaccCardInfo】没有该用户的绑卡信息");
            throw new BusinessException(baseResponse);
        }
        EaccCardinfo eaccCardInfo=null;
        for(EaccCardinfo ec:eaccCardInfoList){
            if(applyQuickPayRequest.getMobile().equals(ec.getMobile())){
                eaccCardInfo=ec;
                break;
            }
        }

        //判断绑卡信息与请求信息是否一致
        if(null== eaccCardInfo){
            BaseResponse baseResponse=new BaseResponse();
            baseResponse.setRecode(BusinessMsg.PARAMETER_ERROR);
            baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.PARAMETER_ERROR)+String.format("该银行卡【%s】与数据库绑卡信息不一致",applyQuickPayRequest.getCard_no()));
            logger.info("请求参数中的银行卡号与数据库中的【eaccCardInfo】绑卡信息不一致");
            throw new BusinessException(baseResponse);
        }
        /*//判断请求手机号与用户绑定手机是否一致
        if(!eaccCardInfo.getMobile().equals(applyQuickPayRequest.getMobile())){
            BaseResponse baseResponse=new BaseResponse();
            baseResponse.setRecode(BusinessMsg.PARAMETER_ERROR);
            baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.PARAMETER_ERROR)+String.format("手机号【%s】与用户绑定手机号不一致",applyQuickPayRequest.getMobile()));
            logger.info("请求的手机号与数据库中的【eaccCardInfo】绑卡信息手机号不一致");
            throw new BusinessException(baseResponse);
        }*/
        //判断是否信用卡绑卡
        if (BindCardType.BINDCREDITCARD.getCode().equals(eaccCardInfo.getRemark())){
            BaseResponse baseResponse=new BaseResponse();
            baseResponse.setRecode(BusinessMsg.PARAMETER_ERROR);
            baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.PARAMETER_ERROR)+String.format("【%s】该卡为三要素绑卡，不允许充值",eaccCardInfo.getCard_no()));
            logger.info("【%s】该卡为三要素绑卡，不允许充值",eaccCardInfo.getCard_no());
            throw new BusinessException(baseResponse);
        }
        return eaccUserInfo;
    }

    @Override
    public EaccUserinfo queryEaccUserInfoByEaccAccountInfo(String mall_no, String plat_no, String platcust) throws BusinessException{
        logger.info("EaccUserinfo》》"+mall_no,plat_no,platcust);
        EaccUserinfoExample example=new EaccUserinfoExample();
        EaccUserinfoExample.Criteria criteria=example.createCriteria();
        if(StringUtils.isNotBlank(mall_no)) {
            criteria.andMall_noEqualTo(mall_no);
        }
        if(StringUtils.isNotBlank(platcust)) {
            criteria.andMallcustEqualTo(platcust);
        }
        criteria.andEnabledEqualTo(Constants.ENABLED);
        List<EaccUserinfo> eaccUserInfos= eaccUserInfoMapper.selectByExample(example);

        if(eaccUserInfos.size() > 1){
            BaseResponse baseResponse=new BaseResponse();
            baseResponse.setRecode(BusinessMsg.DATEBASE_EXCEPTION);
            baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.DATEBASE_EXCEPTION) + "请检查用户信息，用户信息出现重复数据 \n"
                    + "mall_no：" + mall_no + " \n mallcust" +platcust);
            throw new BusinessException(baseResponse);
        }else if(eaccUserInfos.size() < 1){
            return null;
        }
        return eaccUserInfos.get(0);
    }

    @Override
    public EaccCardinfo queryEaccCardInfoByEaccAccountInfo(String mall_no, String plat_no, String platcust, String card_no,String pay_channel) throws BusinessException{
        logger.info("==============>>>>EaccCardinfo:mall_no"+mall_no+",plat_no"+plat_no+",platcust"+platcust+",card_no"+card_no+",pay_channel"+pay_channel);
        EaccCardinfoExample example=new EaccCardinfoExample();
        EaccCardinfoExample.Criteria criteria=example.createCriteria();
        criteria.andMall_noEqualTo(mall_no);
        criteria.andMallcustEqualTo(platcust);
        if (StringUtils.isNotEmpty(card_no)){
            criteria.andCard_noEqualTo(card_no);
        }
        criteria.andEnabledEqualTo(Constants.ENABLED);
        criteria.andStatusEqualTo(CardStatus.ACTIVE.getCode());
        if (StringUtils.isNotEmpty(pay_channel)){
            criteria.andPay_channelEqualTo(pay_channel);
        }
        List<EaccCardinfo> eaccCardInfos= eaccCardInfoMapper.selectByExample(example);

        if(eaccCardInfos.size() > 1){
            if (card_no!=null){
                BaseResponse baseResponse=new BaseResponse();
                baseResponse.setRecode(BusinessMsg.DATEBASE_EXCEPTION);
                baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.DATEBASE_EXCEPTION) + "平台客户绑卡信息出现重复数据，请检查平台客户绑卡信息，\n"
                        + "mall_no：" + mall_no + " \n mallcust" + platcust + " \n card_no" + card_no);
                throw new BusinessException(baseResponse);
            }else{
                BaseResponse baseResponse=new BaseResponse();
                baseResponse.setRecode(BusinessMsg.DATEBASE_EXCEPTION);
                baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.DATEBASE_EXCEPTION) + "平台客户绑卡信息出现重复数据，请检查该客户是否绑定多卡（如是，请传card_no） \n"
                        + "mall_no：" + mall_no + " \n mallcust" + platcust);
                throw new BusinessException(baseResponse);
            }

        }else if(eaccCardInfos.size() < 1){
            return null;
        }
        return eaccCardInfos.get(0);
    }


    /**
     * 查询支付平台映射信息
     * @param plat_no
     * @param pay_code
     * @return
     */
    @Override
    public PlatPaycode queryPlatPayCode(String plat_no, String pay_code) throws BusinessException {

        PlatPaycodeExample example=new PlatPaycodeExample();
        PlatPaycodeExample.Criteria criteria=example.createCriteria();

        if(StringUtils.isNotBlank(pay_code))
            criteria.andPay_codeEqualTo(pay_code);

        if(StringUtils.isNotBlank(plat_no))
            criteria.andPlat_noEqualTo(plat_no);

        criteria.andEnabledEqualTo(Constants.ENABLED);

        List<PlatPaycode> platPayCodeList= platPayCodeMapper.selectByExample(example);

        if(platPayCodeList.size() > 1){
            BaseResponse baseResponse=new BaseResponse();
            baseResponse.setRecode(BusinessMsg.DATEBASE_EXCEPTION);
            baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.DATEBASE_EXCEPTION) + "请检查支付平台映射信息，平台映射信息出现出现重复数据 \n"
                    + "mall_no：" + plat_no + " \n pay_code" + pay_code);
            throw new BusinessException(baseResponse);
        }else if(platPayCodeList.size() < 1){
            return null;
        }
        return platPayCodeList.get(0);
    }

    @Override
    public List<EaccCardinfo> queryEaccCardInfo(String mall_no, String platcust, String card_no, Object status) throws BusinessException {
        EaccCardinfoExample example=new EaccCardinfoExample();
        EaccCardinfoExample.Criteria criteria=example.createCriteria();
        criteria.andMall_noEqualTo(mall_no);
        criteria.andMallcustEqualTo(platcust);
        if (StringUtils.isNotEmpty(card_no)){
            criteria.andCard_noEqualTo(card_no);
        }
        criteria.andEnabledEqualTo(Constants.ENABLED);
        if(null!=status){
            if(status instanceof String ){
                criteria.andStatusEqualTo((String) status);
            }else if(status instanceof ArrayList){
                criteria.andStatusIn((List<String>) status);
            }
        }else{
            criteria.andStatusEqualTo(CardStatus.ACTIVE.getCode());
        }
        return eaccCardInfoMapper.selectByExample(example);
    }

    @Override
    public List<EaccCardinfo> queryEaccCardInfo(String mall_no, String platcust, String card_no, String mobile, Object status) throws BusinessException {
        EaccCardinfoExample example=new EaccCardinfoExample();
        EaccCardinfoExample.Criteria criteria=example.createCriteria();
        criteria.andMall_noEqualTo(mall_no);
        criteria.andMallcustEqualTo(platcust);
        if (StringUtils.isNotEmpty(card_no)){
            criteria.andCard_noEqualTo(card_no);
        }
        if (StringUtils.isNotEmpty(mobile)){
            criteria.andMobileEqualTo(mobile);
        }
        criteria.andEnabledEqualTo(Constants.ENABLED);
        if(null!=status){
            if(status instanceof String ){
                criteria.andStatusEqualTo((String) status);
            }else if(status instanceof ArrayList){
                criteria.andStatusIn((List<String>) status);
            }
        }else{
            criteria.andStatusEqualTo(CardStatus.ACTIVE.getCode());
        }
        return eaccCardInfoMapper.selectByExample(example);
    }

    public List<EaccCardinfo> queryEaccCardInfo(String mall_no, String platcust, String status) throws BusinessException {
        EaccCardinfoExample example=new EaccCardinfoExample();
        EaccCardinfoExample.Criteria criteria=example.createCriteria();
        criteria.andMall_noEqualTo(mall_no);
        criteria.andMallcustEqualTo(platcust);
        if(null!=status && !"".equals(status)){
            criteria.andEnabledEqualTo(status);
        }else{
            return queryEaccCardInfo(mall_no,platcust,null,null);
        }
        return eaccCardInfoMapper.selectByExample(example);
    }

    @Override
    public RwRecharge queryRwRechargeBySerial(String Trans_serial, String Host_req_serial_no, String Hsepay_order_no) {
        RwRechargeExample example=new RwRechargeExample();
        RwRechargeExample.Criteria criteria=example.createCriteria();

        if(StringUtils.isNotBlank(Trans_serial))
            criteria.andTrans_serialEqualTo(Trans_serial);

        if(StringUtils.isNotBlank(Host_req_serial_no))
            criteria.andHost_req_serial_noEqualTo(Host_req_serial_no);

        if(StringUtils.isNotBlank(Hsepay_order_no))
            criteria.andHsepay_order_noEqualTo(Hsepay_order_no);

        List<RwRecharge> rwRecharge=rwRechargeMapper.selectByExample(example);
        if(rwRecharge.size() > 1){
            BaseResponse baseResponse=new BaseResponse();
            baseResponse.setRecode(BusinessMsg.DATEBASE_EXCEPTION);
            baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.DATEBASE_EXCEPTION) + "请检查支付平台映射信息，平台映射信息出现出现重复数据 \n"
                    + "Trans_serial：" + Trans_serial + " \n Host_req_serial_no" + Host_req_serial_no+" \n Hsepay_order_no" + Hsepay_order_no);
            throw new BusinessException(baseResponse);
        }else if(rwRecharge.size() < 1){
            return null;
        }
        return rwRecharge.get(0);
    }

    @Override
    public List<EaccAccountinfo> queryEaccAccountInfo(String mallcust, String mall_no, String plat_no) throws BusinessException {
        EaccAccountinfoExample eaccAccountinfoExample=new EaccAccountinfoExample();
        EaccAccountinfoExample.Criteria criteria=eaccAccountinfoExample.createCriteria();
        criteria.andMall_noEqualTo(mall_no);
        criteria.andMallcustEqualTo(mallcust);
        criteria.andPlat_noEqualTo(plat_no);
        return eaccAccountInfoMapper.selectByExample(eaccAccountinfoExample);
    }

    @Override
    public List<EaccUserauth> queryEaccUserAuth(String platcust, String plat_no, String mall_no, String... status) throws BusinessException {
        EaccUserauthExample example=new EaccUserauthExample();
        EaccUserauthExample.Criteria criteria=example.createCriteria();
        criteria.andPlat_noEqualTo(plat_no);
        criteria.andMall_noEqualTo(mall_no);
        criteria.andPlatcustEqualTo(platcust);
        criteria.andEnabledEqualTo(Constants.ENABLED);
        if(status.length==1){
            criteria.andAuthed_statusEqualTo(status[0]);
        }else if(status.length>1){
            List<String> statusList=new ArrayList<>();
            statusList.addAll(Arrays.asList(status));
            criteria.andAuthed_statusIn(statusList);
        }
        return eaccUserauthMapper.selectByExample(example);
    }
    /**
     * 检查用户是否绑卡（不校验手机号码）
     */
    @Override
    public EaccUserinfo checkUserinfoWithoutMobile(CollectionRequest collectionRequest) throws BusinessException{
        EaccUserinfo eaccUserInfo= new EaccUserinfo();
//        EaccCardinfo eaccCardInfo= new EaccCardinfo();
        //查询用户信息 是否有该用户
        eaccUserInfo = queryEaccUserInfoByEaccAccountInfo(collectionRequest.getMall_no(),collectionRequest.getMer_no(),collectionRequest.getPlatcust());

        if(null==eaccUserInfo){
            BaseResponse baseResponse=new BaseResponse();
            baseResponse.setRecode(BusinessMsg.INVALID_ACCOUNT);
            baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.INVALID_ACCOUNT)+"【eaccUserInfo】没有该用户");
            logger.info(collectionRequest.getMall_no(),collectionRequest.getMer_no(),collectionRequest.getPlatcust()+"eaccUserInfo表中没有该用户相关信息");
            throw new BusinessException(baseResponse);
        }else if(!collectionRequest.getId_code().equals(eaccUserInfo.getId_code())){
            BaseResponse baseResponse=new BaseResponse();
            baseResponse.setRecode(BusinessMsg.PARAMETER_ERROR);
            baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.PARAMETER_ERROR)+String.format("该证件号码【%s】与数据库绑卡信息不一致",collectionRequest.getId_code()));
            logger.info(collectionRequest.getId_code()+"<--该身份证与数据库中的【eaccUserInfo】身份证信息不一致");
            throw new BusinessException(baseResponse);
        }
        if(!collectionRequest.getName().equals(eaccUserInfo.getName())){
            BaseResponse baseResponse=new BaseResponse();
            baseResponse.setRecode(BusinessMsg.PARAMETER_ERROR);
            baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.PARAMETER_ERROR)+String.format("该用户姓名【%s】与数据库开户姓名不一致",collectionRequest.getName()));
            logger.info(collectionRequest.getName()+"<--该姓名与数据库中的【eaccUserInfo】姓名信息不一致");
            throw new BusinessException(baseResponse);
        }
        //查询用户绑卡信息 是否绑卡
//        eaccCardInfo = queryEaccCardInfoByEaccAccountInfo(collectionRequest.getMall_no(),collectionRequest.getMer_no(),collectionRequest.getPlatcust(),collectionRequest.getCard_no(),null);
        //查询用户绑卡信息 是否绑卡
        List<EaccCardinfo> eaccCardInfoList = queryEaccCardInfo(collectionRequest.getMall_no(),collectionRequest.getPlatcust(),collectionRequest.getCard_no(),CardStatus.ACTIVE.getCode());
        if(eaccCardInfoList.size() < 1){
            BaseResponse baseResponse=new BaseResponse();
            baseResponse.setRecode(BusinessMsg.UNBIND_CARD_ERROR);
            baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.UNBIND_CARD_ERROR)+String.format("该用户【%s】未绑卡，请先短验绑卡",collectionRequest.getCard_no()));
            logger.info("数据库【eaccCardInfo】没有该用户的绑卡信息");
            throw new BusinessException(baseResponse);
        }
        EaccCardinfo eaccCardInfo=eaccCardInfoList.get(0);
/*        //判断绑卡信息与请求信息是否一致
        if(!eaccCardInfo.getCard_no().equals(collectionRequest.getCard_no())){
            BaseResponse baseResponse=new BaseResponse();
            baseResponse.setRecode(BusinessMsg.PARAMETER_ERROR);
            baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.PARAMETER_ERROR)+String.format("该银行卡【%s】与数据库绑卡信息不一致",collectionRequest.getCard_no()));
            logger.info("请求参数中的银行卡号与数据库中的【eaccCardInfo】绑卡信息不一致");
            throw new BusinessException(baseResponse);
        }*/
        //判断请求手机号与用户绑定手机是否一致
        if (collectionRequest.getMobile()!=null){
            boolean is_not_same_mobile=true;
            for(EaccCardinfo ec:eaccCardInfoList){
                if(collectionRequest.getMobile().equals(ec.getMobile())){
                    is_not_same_mobile=false;
                    eaccCardInfo=ec;
                    break;
                }
            }
            if(is_not_same_mobile){
                BaseResponse baseResponse=new BaseResponse();
                baseResponse.setRecode(BusinessMsg.PARAMETER_ERROR);
                baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.PARAMETER_ERROR)+String.format("手机号【%s】与用户绑定手机号不一致",collectionRequest.getMobile()));
                logger.info("请求的手机号与数据库中的【eaccCardInfo】绑卡信息手机号不一致");
                throw new BusinessException(baseResponse);
            }
        }
        //判断是否信用卡绑卡
        if (BindCardType.BINDCREDITCARD.getCode().equals(eaccCardInfo.getRemark())){
            BaseResponse baseResponse=new BaseResponse();
            baseResponse.setRecode(BusinessMsg.PARAMETER_ERROR);
            baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.PARAMETER_ERROR)+String.format("【%s】该卡为三要素绑卡，不允许充值",eaccCardInfo.getCard_no()));
            logger.info("【%s】该卡为三要素绑卡，不允许充值",eaccCardInfo.getCard_no());
            throw new BusinessException(baseResponse);
        }
        return eaccUserInfo;
    }

    @Override
    public RealEaccountBalanceResponseDetail queryRealEaccountBalance(RealEaccountBalance realEaccountBalance) throws BusinessException {

        RealEaccountBalanceResponseDetail realEaccountBalanceResponseDetail = new RealEaccountBalanceResponseDetail();

        //检查用户信息是否存在
        logger.info("【真实电子账户余额查询】检查用户信息是否存在-->order_no:"+realEaccountBalance.getOrder_no());
        EaccUserinfo eaccUserInfo = queryEaccUserInfoByEaccAccountInfo(realEaccountBalance.getMall_no(),realEaccountBalance.getMer_no(), realEaccountBalance.getPlatcust());
        if (eaccUserInfo == null) {
            logger.info("【真实电子账户余额查询】用户账号不存在" + "用户账户号：" + realEaccountBalance.getPlatcust()+"-->order_no:"+realEaccountBalance.getOrder_no());
            BaseResponse baseResponse = new BaseResponse();
            baseResponse.setRecode(BusinessMsg.INVALID_ACCOUNT);
            baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.INVALID_ACCOUNT) + ",用户信息不存在");
            throw new BusinessException(baseResponse);
        }

        if(StringUtils.isBlank(eaccUserInfo.getEaccount()) || Constants.NO.equals(eaccUserInfo.getEaccount_flg())){
            logger.info("【真实电子账户余额查询】用户账号不存在" + "用户账户号：" + realEaccountBalance.getPlatcust()+"-->order_no:"+realEaccountBalance.getOrder_no());
            BaseResponse baseResponse = new BaseResponse();
            baseResponse.setRecode(BusinessMsg.INVALID_ACCOUNT);
            baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.INVALID_ACCOUNT) + ",电子账户未开通");
            throw new BusinessException(baseResponse);
        }

        Map<String, Object> params = new HashMap<>();
        params.put("partner_id", realEaccountBalance.getMall_no());
        params.put("partner_serial_no", SeqUtil.getSeqNum());
        params.put("bank_acct_no", eaccUserInfo.getEaccount());
        params.put("cert_sign", "sunyard");

        params.put("host",sysParameterService.querySysParameter(realEaccountBalance.getMall_no(), URLConfigUtil.EPAY_SERVER_KEY));
        params.put("url",sysParameterService.querySysParameter(realEaccountBalance.getMall_no(), URLConfigUtil.CASHIER_BALANCE_QUERY));

        Map<String,Object> map = adapterService.queryRealEaccountBalance(params);
        if(null != map){
            String  msg = (String)map.get("msg");
            YunPayBaseErrorResponse errorResponse = JSONObject.parseObject(msg, YunPayBaseErrorResponse.class);
            if (errorResponse.getError_code() != null) {
                logger.info("【向E支付发送提现请求】返回异常" + errorResponse.toString("YunPayBaseErrorResponse"));
                BaseResponse baseResponse = new BaseResponse();
                baseResponse.setRecode(errorResponse.getError_code());
                baseResponse.setRemsg("E支付返回：" + errorResponse.getError_info());
                throw new BusinessException(baseResponse);
            }

            CashierBalanceQueryReturn cashierBalanceQueryReturn = JSONObject.parseObject(msg, CashierBalanceQueryReturn.class);
            if (cashierBalanceQueryReturn.getData() == null || cashierBalanceQueryReturn.getData().size() == 0) {
                BaseResponse baseResponse = new BaseResponse();
                baseResponse.setRecode(cashierBalanceQueryReturn.getError_code());
                baseResponse.setRemsg("E支付返回：" + cashierBalanceQueryReturn.getError_info());
                throw new BusinessException(baseResponse);
            }

            logger.info("【向E支付发送提现请求】-->返回具体明细数据"+cashierBalanceQueryReturn.getData().get(0).toString());

            realEaccountBalanceResponseDetail.setClient_name(cashierBalanceQueryReturn.getData().get(0).getClient_name());
            realEaccountBalanceResponseDetail.setEaccount(eaccUserInfo.getEaccount());
            realEaccountBalanceResponseDetail.setOpen_bank(cashierBalanceQueryReturn.getData().get(0).getOpen_bank());

            List<SubAcctBulkPackage> subAcctBulkPackage = cashierBalanceQueryReturn.getData().get(0).getSubAcctBulkPackages();
            realEaccountBalanceResponseDetail.setSubAcctBulkPackages(subAcctBulkPackage);

        }
        return realEaccountBalanceResponseDetail;
    }

    @Override
    public EaccUserinfo checkUserinfoWithoutMobile(CollectionDetail collectionRequest) throws BusinessException{
        EaccUserinfo eaccUserInfo= new EaccUserinfo();
//        EaccCardinfo eaccCardInfo= new EaccCardinfo();
        //查询用户信息 是否有该用户
        eaccUserInfo = queryEaccUserInfoByEaccAccountInfo(collectionRequest.getMall_no(),collectionRequest.getMer_no(),collectionRequest.getPlatcust());

        if(null==eaccUserInfo){
            BaseResponse baseResponse=new BaseResponse();
            baseResponse.setRecode(BusinessMsg.INVALID_ACCOUNT);
            baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.INVALID_ACCOUNT)+"【eaccUserInfo】没有该用户");
            logger.info(collectionRequest.getMall_no(),collectionRequest.getMer_no(),collectionRequest.getPlatcust()+"eaccUserInfo表中没有该用户相关信息");
            throw new BusinessException(baseResponse);
        }else if(!collectionRequest.getId_code().equals(eaccUserInfo.getId_code())){
            BaseResponse baseResponse=new BaseResponse();
            baseResponse.setRecode(BusinessMsg.PARAMETER_ERROR);
            baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.PARAMETER_ERROR)+String.format("该证件号码【%s】与数据库绑卡信息不一致",collectionRequest.getId_code()));
            logger.info(collectionRequest.getId_code()+"<--该身份证与数据库中的【eaccUserInfo】身份证信息不一致");
            throw new BusinessException(baseResponse);
        }
        if(!collectionRequest.getName().equals(eaccUserInfo.getName())){
            BaseResponse baseResponse=new BaseResponse();
            baseResponse.setRecode(BusinessMsg.PARAMETER_ERROR);
            baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.PARAMETER_ERROR)+String.format("该用户姓名【%s】与数据库开户姓名不一致",collectionRequest.getName()));
            logger.info(collectionRequest.getName()+"<--该姓名与数据库中的【eaccUserInfo】姓名信息不一致");
            throw new BusinessException(baseResponse);
        }
        //查询用户绑卡信息 是否绑卡
//        eaccCardInfo = queryEaccCardInfoByEaccAccountInfo(collectionRequest.getMall_no(),collectionRequest.getMer_no(),collectionRequest.getPlatcust(),collectionRequest.getCard_no(),null);
        //查询用户绑卡信息 是否绑卡
        List<EaccCardinfo> eaccCardInfoList = queryEaccCardInfo(collectionRequest.getMall_no(),collectionRequest.getPlatcust(),collectionRequest.getCard_no(),CardStatus.ACTIVE.getCode());
        if(eaccCardInfoList.size() < 1){
            BaseResponse baseResponse=new BaseResponse();
            baseResponse.setRecode(BusinessMsg.UNBIND_CARD_ERROR);
            baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.UNBIND_CARD_ERROR)+String.format("该用户【%s】未绑卡，请先短验绑卡",collectionRequest.getCard_no()));
            logger.info("数据库【eaccCardInfo】没有该用户的绑卡信息");
            throw new BusinessException(baseResponse);
        }
        EaccCardinfo eaccCardInfo=eaccCardInfoList.get(0);
/*        //判断绑卡信息与请求信息是否一致
        if(!eaccCardInfo.getCard_no().equals(collectionRequest.getCard_no())){
            BaseResponse baseResponse=new BaseResponse();
            baseResponse.setRecode(BusinessMsg.PARAMETER_ERROR);
            baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.PARAMETER_ERROR)+String.format("该银行卡【%s】与数据库绑卡信息不一致",collectionRequest.getCard_no()));
            logger.info("请求参数中的银行卡号与数据库中的【eaccCardInfo】绑卡信息不一致");
            throw new BusinessException(baseResponse);
        }*/
        //判断请求手机号与用户绑定手机是否一致
        if (collectionRequest.getMobile()!=null){
            boolean is_not_same_mobile=true;
            for(EaccCardinfo ec:eaccCardInfoList){
                if(collectionRequest.getMobile().equals(ec.getMobile())){
                    is_not_same_mobile=false;
                    eaccCardInfo=ec;
                    break;
                }
            }
            if(is_not_same_mobile){
                BaseResponse baseResponse=new BaseResponse();
                baseResponse.setRecode(BusinessMsg.PARAMETER_ERROR);
                baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.PARAMETER_ERROR)+String.format("手机号【%s】与用户绑定手机号不一致",collectionRequest.getMobile()));
                logger.info("请求的手机号与数据库中的【eaccCardInfo】绑卡信息手机号不一致");
                throw new BusinessException(baseResponse);
            }
        }
        //判断是否信用卡绑卡
        if (BindCardType.BINDCREDITCARD.getCode().equals(eaccCardInfo.getRemark())){
            BaseResponse baseResponse=new BaseResponse();
            baseResponse.setRecode(BusinessMsg.PARAMETER_ERROR);
            baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.PARAMETER_ERROR)+String.format("【%s】该卡为三要素绑卡，不允许充值",eaccCardInfo.getCard_no()));
            logger.info("【%s】该卡为三要素绑卡，不允许充值",eaccCardInfo.getCard_no());
            throw new BusinessException(baseResponse);
        }
        return eaccUserInfo;
    }
}
