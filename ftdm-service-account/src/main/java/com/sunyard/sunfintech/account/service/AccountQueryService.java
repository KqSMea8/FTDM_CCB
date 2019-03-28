package com.sunyard.sunfintech.account.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.sunyard.sunfintech.account.model.bo.*;
import com.sunyard.sunfintech.account.provider.IAccountQueryService;
import com.sunyard.sunfintech.core.Constants;
import com.sunyard.sunfintech.core.base.BaseRequest;
import com.sunyard.sunfintech.core.base.BaseResponse;
import com.sunyard.sunfintech.core.base.BaseServiceSimple;
import com.sunyard.sunfintech.core.dic.*;
import com.sunyard.sunfintech.core.exception.BusinessException;
import com.sunyard.sunfintech.core.exception.BusinessMsg;
import com.sunyard.sunfintech.core.util.DateUtils;
import com.sunyard.sunfintech.core.util.StringUtils;
import com.sunyard.sunfintech.custdao.entity.CustProdShareList;
import com.sunyard.sunfintech.custdao.mapper.*;
import com.sunyard.sunfintech.dao.entity.*;
import com.sunyard.sunfintech.dao.mapper.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;

import java.math.BigDecimal;
import java.util.*;

/**
 * @author heroy
 * @version 2017/4/12
 */
@Service(interfaceClass = IAccountQueryService.class)
@CacheConfig(cacheNames="accountQueryService")
@org.springframework.stereotype.Service("accountQueryService")
public class AccountQueryService extends BaseServiceSimple implements IAccountQueryService {

	@Autowired
	private CustAccountSubjectInfoMapper custAccountSubjectInfoMapper;

	@Autowired
	private AccountSubjectInfoMapper accountSubjectInfoMapper;

	@Autowired
	private PlatCardinfoMapper platCardinfoMapper;

	@Autowired
	private EaccUserinfoMapper eaccUserinfoMapper;

	@Autowired
	private EaccCardinfoMapper eaccCardinfoMapper;

	@Autowired
	private EaccAccountamtlistMapper eaccAccountamtlistMapper;

	@Autowired
	private TransTransreqMapper transTransreqMapper;

    @Autowired
    private ProdProductinfoMapper prodProductInfoMapper;

    @Autowired
	private RwWithdrawMapper rwWithdrawMapper;

    @Autowired
	private CustEaccAccountamtlistMapper custEaccAccountamtlistMapper;

    @Autowired
	private CustProdShareListMapper custProdShareListMapper;

	@Override
	public List<AccountSubjectInfo> queryBalance(String AccountId) throws BusinessException {
		return custAccountSubjectInfoMapper.selectByAccountId(AccountId);
	}

	@Override
	public AccountSubjectInfo queryAccount(String plat_no, String platcust, String subject, String sub_subject) throws BusinessException {
		AccountSubjectInfoExample accountSubjectInfoExample = new AccountSubjectInfoExample();
		AccountSubjectInfoExample.Criteria criteria = accountSubjectInfoExample.createCriteria();
		criteria.andEnabledEqualTo(Constants.ENABLED);
		if (StringUtils.isNotBlank(plat_no))
			criteria.andPlat_noEqualTo(plat_no);
		if (StringUtils.isNotBlank(platcust)) {
			criteria.andPlatcustEqualTo(platcust);
		} else {
			criteria.andPlatcustEqualTo(plat_no);
		}
		if (StringUtils.isNotBlank(subject))
			criteria.andSubjectEqualTo(subject);
		if (StringUtils.isNotBlank(sub_subject))
			criteria.andSub_subjectEqualTo(sub_subject);

		List<AccountSubjectInfo> accountSubjectInfos = accountSubjectInfoMapper.selectByExample(accountSubjectInfoExample);
		if (accountSubjectInfos.size() > 1) {
			BaseResponse baseResponse = new BaseResponse();
			baseResponse.setRecode(BusinessMsg.DATEBASE_EXCEPTION);
			baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.DATEBASE_EXCEPTION) + "--数据库有重复的账户");
			throw new BusinessException(baseResponse);
		} else if (accountSubjectInfos.size() <= 0) {
			return null;
		}
		return accountSubjectInfos.get(0);
	}


	@Override
	public List<AccountSubjectInfo> queryAccountlist(String plat_no, String platcust) throws BusinessException {
		AccountSubjectInfoExample accountSubjectInfoExample = new AccountSubjectInfoExample();
		AccountSubjectInfoExample.Criteria criteria = accountSubjectInfoExample.createCriteria();
		criteria.andEnabledEqualTo(Constants.ENABLED);
		if (StringUtils.isNotBlank(plat_no))
			criteria.andPlat_noEqualTo(plat_no);
		if (StringUtils.isNotBlank(platcust))
			criteria.andPlatcustEqualTo(platcust);

		return accountSubjectInfoMapper.selectByExample(accountSubjectInfoExample);

	}

	@Override
	public List<AccountSubjectInfo> queryAccountlist(String plat_no, String platcust,String status) throws BusinessException {
		if(null==status || "".equals(status)){
			return queryAccountlist(plat_no,platcust);
		}
		AccountSubjectInfoExample accountSubjectInfoExample = new AccountSubjectInfoExample();
		AccountSubjectInfoExample.Criteria criteria = accountSubjectInfoExample.createCriteria();
		criteria.andEnabledEqualTo(status);
		if (StringUtils.isNotBlank(plat_no))
			criteria.andPlat_noEqualTo(plat_no);
		if (StringUtils.isNotBlank(platcust))
			criteria.andPlatcustEqualTo(platcust);
		return accountSubjectInfoMapper.selectByExample(accountSubjectInfoExample);
	}

	@Override
	public AccountSubjectInfo queryPlatAccount(String prod_id, String plat_no, String subject, String sub_subject) throws BusinessException {
		Map<String, Object> queryPlatAccountParams = new HashMap<>();
		queryPlatAccountParams.put("plat_no", plat_no);
		queryPlatAccountParams.put("prod_id", prod_id);
		queryPlatAccountParams.put("subject", subject);
		queryPlatAccountParams.put("sub_subject", sub_subject);

		List<AccountSubjectInfo> accountSubjectInfoList = custAccountSubjectInfoMapper.queryPlatAccount(queryPlatAccountParams);
		if (accountSubjectInfoList.size() == 1) {
			return accountSubjectInfoList.get(0);
		} else if (accountSubjectInfoList == null || accountSubjectInfoList.size() == 0) {
			//如果查询不到平台账号，抛异常
			logger.info("查询账户，账户不存在" + this.getClass().getName());
			BaseResponse baseResponse = new BaseResponse();
			baseResponse.setRecode(BusinessMsg.INVALID_ACCOUNT);
			baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.INVALID_ACCOUNT) + " ------账号不存在");
			throw new BusinessException(baseResponse);
		} else {
			BaseResponse baseResponse = new BaseResponse();
			baseResponse.setRecode(BusinessMsg.DATEBASE_EXCEPTION);
			baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.DATEBASE_EXCEPTION) + "--------数据库有重复的账户");
			throw new BusinessException(baseResponse);
		}
	}

	@Override
	public PlatCardinfo queryPlatCardinfo(String mall_no, String plat_no, String card_type) {
		PlatCardinfoExample platCardinfoExample = new PlatCardinfoExample();
		PlatCardinfoExample.Criteria criteria = platCardinfoExample.createCriteria();
		criteria.andEnabledEqualTo(Constants.ENABLED);
		criteria.andMall_noEqualTo(mall_no);
		criteria.andPlat_noEqualTo(plat_no);
		criteria.andCard_typeEqualTo(card_type);

		List<PlatCardinfo> platCardinfo = platCardinfoMapper.selectByExample(platCardinfoExample);
		if (platCardinfo.size() > 1) {
			BaseResponse baseResponse = new BaseResponse();
			baseResponse.setRecode(BusinessMsg.DATEBASE_EXCEPTION);
			baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.DATEBASE_EXCEPTION) + "--数据库有重复的账户");
			throw new BusinessException(baseResponse);
		} else if (platCardinfo.size() <= 0) {
			return null;
		}
		return platCardinfo.get(0);
	}

	@Override
	public AccountSubjectInfo queryFINANCINGPlatAccount(String plat_no, String platcust, String account_type, String subject, String sub_subject) throws BusinessException {

		AccountSubjectInfo accountSubjectInfo = null;
		try {
			AccountSubjectInfoExample accountSubjectInfoExample = new AccountSubjectInfoExample();
			AccountSubjectInfoExample.Criteria criteria = accountSubjectInfoExample.createCriteria();
			criteria.andEnabledEqualTo(Constants.ENABLED);

			if (StringUtils.isNotBlank(plat_no))
				criteria.andPlat_noEqualTo(plat_no);
			if (StringUtils.isNotBlank(platcust))
				criteria.andPlatcustEqualTo(platcust);
			if (StringUtils.isNotBlank(account_type))
				criteria.andAccount_typeEqualTo(account_type);
			if (StringUtils.isNotBlank(subject))
				criteria.andSubjectEqualTo(subject);
			if (StringUtils.isNotBlank(sub_subject))
				criteria.andSub_subjectEqualTo(sub_subject);

			List<AccountSubjectInfo> accountSubjectInfos = accountSubjectInfoMapper.selectByExample(accountSubjectInfoExample);
			if (accountSubjectInfos != null && accountSubjectInfos.size() > 0) {
				accountSubjectInfo = accountSubjectInfos.get(0);
			}
		} catch (BusinessException e) {
			BaseResponse baseResponse = new BaseResponse();
			baseResponse.setRecode(BusinessMsg.DATEBASE_EXCEPTION);
			baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.DATEBASE_EXCEPTION));
			throw new BusinessException(baseResponse);
		}

		return accountSubjectInfo;
	}


	/*
     *  pzy
     *  查询电子账户现金和在途
     */
	@Override
	public List<AccountSubjectInfo> queryFINANCINGPlatAccountlist(String plat_no, String platcust, String account_type, String sub_subject) throws BusinessException {
		List<AccountSubjectInfo> accountSubjectInfos = null;
		try {
			AccountSubjectInfoExample accountSubjectInfoExample = new AccountSubjectInfoExample();
			AccountSubjectInfoExample.Criteria criteria = accountSubjectInfoExample.createCriteria();
			criteria.andEnabledEqualTo(Constants.ENABLED);

			if (StringUtils.isNotBlank(plat_no))
				criteria.andPlat_noEqualTo(plat_no);

			if (StringUtils.isNotBlank(platcust))
				criteria.andPlatcustEqualTo(platcust);

			if (StringUtils.isNotBlank(account_type))
				criteria.andAccount_typeEqualTo(account_type);

			if (StringUtils.isNotBlank(sub_subject))
				criteria.andSub_subjectEqualTo(sub_subject);

			accountSubjectInfos = accountSubjectInfoMapper.selectByExample(accountSubjectInfoExample);
		} catch (BusinessException e) {
			BaseResponse baseResponse = new BaseResponse();
			baseResponse.setRecode(BusinessMsg.DATEBASE_EXCEPTION);
			baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.DATEBASE_EXCEPTION));
			throw new BusinessException(baseResponse);
		}
		return accountSubjectInfos;
	}

	@Override
	public List<AccountSubjectInfo> queryPlatAccountList(String plat_no, Object platcust, String subject, Object sub_subject) throws BusinessException {

		AccountSubjectInfoExample accountSubjectInfoExample = new AccountSubjectInfoExample();
		AccountSubjectInfoExample.Criteria criteria = accountSubjectInfoExample.createCriteria();
		criteria.andEnabledEqualTo(Constants.ENABLED);

		if (StringUtils.isNotBlank(plat_no))
			criteria.andPlat_noEqualTo(plat_no);
		if (platcust != null) {
			if (platcust instanceof String) {
				if (StringUtils.isNotBlank((String) platcust))
					criteria.andPlatcustEqualTo((String) platcust);
			} else if (platcust instanceof ArrayList) {
				List<String> platcust_list = (List<String>) platcust;
				if (platcust_list.size() > 0) {
					criteria.andPlatcustIn(platcust_list);
				}
			}
		}
		if (StringUtils.isNotBlank(subject))
			criteria.andSubjectEqualTo(subject);
		if (sub_subject != null) {
			if (sub_subject instanceof String) {
				if (StringUtils.isNotBlank((String) sub_subject))
					criteria.andSub_subjectEqualTo((String) sub_subject);
			} else if (sub_subject instanceof ArrayList) {
				List<String> sub_subject_list = (List<String>) sub_subject;
				if (sub_subject_list.size() > 0) {
					criteria.andSub_subjectIn(sub_subject_list);
				}
			}
		}

		return accountSubjectInfoMapper.selectByExample(accountSubjectInfoExample);
	}

	@Override
	public PlatCardinfo getPlatcardinfo(String plat_no, String card_type) throws BusinessException {
		PlatCardinfoExample platCardinfoExample = new PlatCardinfoExample();
		PlatCardinfoExample.Criteria platCardinfoExampleCriteria = platCardinfoExample.createCriteria();
		platCardinfoExampleCriteria.andPlat_noEqualTo(plat_no);
		platCardinfoExampleCriteria.andCard_typeEqualTo(card_type);
		List<PlatCardinfo> platCardinfoList = platCardinfoMapper.selectByExample(platCardinfoExample);
		if (platCardinfoList.size() > 0) {
			return platCardinfoList.get(0);
		} else {
			logger.info("【电子账户转账】=======找不到转出存管户，plat_no：" + plat_no);
			throw new BusinessException(BusinessMsg.INVALID_ACCOUNT,
					BusinessMsg.getMsg(BusinessMsg.INVALID_ACCOUNT) + "：找不到平台存管户");
		}
	}

	@Override
	public EaccUserinfo getEaccUserinfo(String mall_no, String mallcust) throws BusinessException {
		EaccUserinfoExample eaccUserinfoExample = new EaccUserinfoExample();
		EaccUserinfoExample.Criteria eaccUserinfoExampleCriteria = eaccUserinfoExample.createCriteria();
		eaccUserinfoExampleCriteria.andMall_noEqualTo(mall_no);
		eaccUserinfoExampleCriteria.andMallcustEqualTo(mallcust);
		eaccUserinfoExampleCriteria.andEnabledEqualTo(Constants.ENABLED);
		List<EaccUserinfo> eaccUserinfoList = eaccUserinfoMapper.selectByExample(eaccUserinfoExample);
		if (eaccUserinfoList.size() > 0) {
			EaccUserinfo eaccUserinfo = eaccUserinfoList.get(0);
			if (StringUtils.isEmpty(eaccUserinfo.getEaccount())) {
				logger.info("【电子账户转账】=======找不到转出电子账户号，platcust：" + mallcust);
				throw new BusinessException(BusinessMsg.INVALID_ACCOUNT,
						BusinessMsg.getMsg(BusinessMsg.INVALID_ACCOUNT) + "：找不到电子账户号");
			}
			return eaccUserinfo;
		} else {
			logger.info("【电子账户转账】=======找不到用户信息，platcust：" + mallcust);
			throw new BusinessException(BusinessMsg.INVALID_ACCOUNT,
					BusinessMsg.getMsg(BusinessMsg.INVALID_ACCOUNT) + "：找不到用户信息");
		}
	}

	public EaccUserinfo getEaccUserinfoByExist(String mall_no, String mallcust) throws BusinessException {
		EaccUserinfoExample eaccUserinfoExample = new EaccUserinfoExample();
		EaccUserinfoExample.Criteria eaccUserinfoExampleCriteria = eaccUserinfoExample.createCriteria();
		eaccUserinfoExampleCriteria.andMall_noEqualTo(mall_no);
		eaccUserinfoExampleCriteria.andMallcustEqualTo(mallcust);
		eaccUserinfoExampleCriteria.andEnabledEqualTo(Constants.ENABLED);
		List<EaccUserinfo> eaccUserinfoList = eaccUserinfoMapper.selectByExample(eaccUserinfoExample);
		if (eaccUserinfoList.size() > 0) {
			EaccUserinfo eaccUserinfo = eaccUserinfoList.get(0);
			if (StringUtils.isEmpty(eaccUserinfo.getEaccount())) {
				return null;
			}
			return eaccUserinfo;
		} else {
			return null;
		}
	}

	public EaccUserinfo getEUserinfoByExist(String mall_no, String mallcust) throws BusinessException {
		EaccUserinfoExample eaccUserinfoExample = new EaccUserinfoExample();
		EaccUserinfoExample.Criteria eaccUserinfoExampleCriteria = eaccUserinfoExample.createCriteria();
		eaccUserinfoExampleCriteria.andMall_noEqualTo(mall_no);
		eaccUserinfoExampleCriteria.andMallcustEqualTo(mallcust);
		eaccUserinfoExampleCriteria.andEnabledEqualTo(Constants.ENABLED);
		List<EaccUserinfo> eaccUserinfoList = eaccUserinfoMapper.selectByExample(eaccUserinfoExample);
		if (eaccUserinfoList.size() > 0) {
			EaccUserinfo eaccUserinfo = eaccUserinfoList.get(0);
			return eaccUserinfo;
		} else {
			return null;
		}
	}

	@Override
	public EaccUserinfo getEUserinfoByExist(String mall_no, String mallcust, String status) throws BusinessException {
		EaccUserinfoExample eaccUserinfoExample = new EaccUserinfoExample();
		EaccUserinfoExample.Criteria eaccUserinfoExampleCriteria = eaccUserinfoExample.createCriteria();
		eaccUserinfoExampleCriteria.andMall_noEqualTo(mall_no);
		eaccUserinfoExampleCriteria.andMallcustEqualTo(mallcust);
		if(StringUtils.isNotBlank(status)){
			eaccUserinfoExampleCriteria.andEnabledEqualTo(status);
		}
		List<EaccUserinfo> eaccUserinfoList = eaccUserinfoMapper.selectByExample(eaccUserinfoExample);
		if (eaccUserinfoList.size() > 0) {
			EaccUserinfo eaccUserinfo = eaccUserinfoList.get(0);
			return eaccUserinfo;
		} else {
			return null;
		}
	}

	@Override
	public EaccCardinfo getEaccCardInfo(String mall_no, String mallcust) throws BusinessException {
		EaccCardinfoExample eaccCardinfoExample = new EaccCardinfoExample();
		EaccCardinfoExample.Criteria criteria = eaccCardinfoExample.createCriteria();
		criteria.andMall_noEqualTo(mall_no);
		criteria.andMallcustEqualTo(mallcust);
		criteria.andEnabledEqualTo(Constants.ENABLED);
		criteria.andStatusEqualTo(CardStatus.ACTIVE.getCode());
		List<EaccCardinfo> eaccCardinfoList = eaccCardinfoMapper.selectByExample(eaccCardinfoExample);
		if (eaccCardinfoList.size() > 0) {
			return eaccCardinfoList.get(0);
		} else {
			return null;
		}
	}

	@Override
	public List<EaccCardinfo> getEaccCardInfoList(String mall_no, String mallcust) throws BusinessException {
		EaccCardinfoExample eaccCardinfoExample = new EaccCardinfoExample();
		EaccCardinfoExample.Criteria criteria = eaccCardinfoExample.createCriteria();
		criteria.andMall_noEqualTo(mall_no);
		criteria.andMallcustEqualTo(mallcust);
		criteria.andEnabledEqualTo(Constants.ENABLED);
		criteria.andStatusEqualTo(CardStatus.ACTIVE.getCode());
		eaccCardinfoExample.setOrderByClause(" id");
		return eaccCardinfoMapper.selectByExample(eaccCardinfoExample);
	}

	@Override
	public EaccCardinfo getEaccCardInfoByMallnoAndMallcustAndCardno(String mall_no, String mallcust, String card_no) throws BusinessException {
		EaccCardinfoExample eaccCardinfoExample = new EaccCardinfoExample();
		EaccCardinfoExample.Criteria criteria = eaccCardinfoExample.createCriteria();
		criteria.andEnabledEqualTo(Constants.ENABLED);

		criteria.andMall_noEqualTo(mall_no);
		criteria.andMallcustEqualTo(mallcust);
		criteria.andCard_noEqualTo(card_no);
		criteria.andStatusEqualTo(CardStatus.ACTIVE.getCode());
		criteria.andEnabledEqualTo(Constants.ENABLED);

		List<EaccCardinfo> eaccCardinfoList = eaccCardinfoMapper.selectByExample(eaccCardinfoExample);
		if (eaccCardinfoList.size() > 0) {
			return eaccCardinfoList.get(0);
		} else {
			logger.info("【绑卡信息找不到】-->找不到用户绑卡信息，mall_no:" + mall_no + ",platcust：" + mallcust + ",card_no:" + card_no);
			throw new BusinessException(BusinessMsg.INVALID_CARD_NUMBER,
					BusinessMsg.getMsg(BusinessMsg.INVALID_CARD_NUMBER));
		}

	}

	/**
	 * 资金变动明细查询
	 * @author RaoYL
	 * @param fundChangeRequest
	 * @return FundChangeResponse
	 * @throws BusinessException
	 */
	@Override
	public FundChangeResponse queryFundChange(FundChangeRequest fundChangeRequest) throws BusinessException {

		logger.info("【资金变动明细查询】order_no:" + fundChangeRequest.getOrder_no());

		FundChangeResponse fundChangeResponse = new FundChangeResponse();

		//查询账户是否存在的
		AccountSubjectInfo accountSubjectInfo = queryAccount(fundChangeRequest.getMer_no(), fundChangeRequest.getPlatcust(), Tsubject.CASH.getCode(), Ssubject.INVEST.getCode());
		if (accountSubjectInfo == null) {
			logger.info("【资金变动明细查询】账户不存在");

			BaseResponse baseResponse = new BaseResponse();
			baseResponse.setRecode(BusinessMsg.INVALID_ACCOUNT);
			baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.INVALID_ACCOUNT));
			throw new BusinessException(baseResponse);
		}

		if (fundChangeRequest.getPagenum() == null ) {
			fundChangeRequest.setPagenum(1);
		}

		if(fundChangeRequest.getPagesize() == null){
			fundChangeRequest.setPagesize(10);
		}
		PageHelper.startPage(fundChangeRequest.getPagenum(), fundChangeRequest.getPagesize());

		EaccAccountamtlistExample example = new EaccAccountamtlistExample();

		EaccAccountamtlistExample.Criteria criteria = example.createCriteria();

		if (StringUtils.isNotBlank(fundChangeRequest.getPlatcust()))
			criteria.andPlatcustEqualTo(fundChangeRequest.getPlatcust());

		if (StringUtils.isNotBlank(fundChangeRequest.getMer_no()))
			criteria.andPlat_noEqualTo(fundChangeRequest.getMer_no());

		if (StringUtils.isNotBlank(fundChangeRequest.getTrans_name()))
			criteria.andTrans_nameLike(fundChangeRequest.getTrans_name());

		if (fundChangeRequest.getStart_date() == null ) {
			fundChangeRequest.setStart_date(DateUtils.getYesterday());
		}

		if(fundChangeRequest.getEnd_date() == null){
			fundChangeRequest.setEnd_date(DateUtils.getNow());
		}

		criteria.andCreate_timeBetween(fundChangeRequest.getStart_date(), new Date(fundChangeRequest.getEnd_date().getTime() + 24 * 60 * 60 * 1000));

		List<FundChangeResponseDetail> fundChangeResponseDetailList = new ArrayList<FundChangeResponseDetail>();
		List<EaccAccountamtlist> eaccAccountamtlistArrayList = eaccAccountamtlistMapper.selectByExample(example);
		for (EaccAccountamtlist eaccAccountamtlist2 : eaccAccountamtlistArrayList) {
			FundChangeResponseDetail fundChangeResponseDetail = new FundChangeResponseDetail();

			fundChangeResponseDetail.setPlat_no(eaccAccountamtlist2.getPlat_no());
			fundChangeResponseDetail.setPlatcust(eaccAccountamtlist2.getPlatcust());
			fundChangeResponseDetail.setTrans_time(eaccAccountamtlist2.getTrans_time());
			fundChangeResponseDetail.setTrans_date(eaccAccountamtlist2.getTrans_date());
			fundChangeResponseDetail.setAmt(eaccAccountamtlist2.getAmt().toString());
			fundChangeResponseDetail.setAmt_type(eaccAccountamtlist2.getAmt_type());
			fundChangeResponseDetail.setTrans_name(eaccAccountamtlist2.getTrans_name());
			fundChangeResponseDetail.setOppo_platcust(eaccAccountamtlist2.getOppo_platcust());
			fundChangeResponseDetail.setRemark(eaccAccountamtlist2.getRemark());
			TransTransreqExample transTransreqExample = new TransTransreqExample();
			TransTransreqExample.Criteria transCriteria = transTransreqExample.createCriteria();
			transCriteria.andTrans_serialEqualTo(eaccAccountamtlist2.getTrans_serial());
			List<TransTransreq> transTransreqList = transTransreqMapper.selectByExample(transTransreqExample);
			if (transTransreqList == null || transTransreqList.size() <= 0) {
				fundChangeResponseDetail.setOrder_no(null);
			} else {
				fundChangeResponseDetail.setOrder_no(transTransreqList.get(0).getOrder_no());
			}
			fundChangeResponseDetailList.add(fundChangeResponseDetail);
		}
		fundChangeResponse.setData_detail(fundChangeResponseDetailList);
		fundChangeResponse.setRecode(BusinessMsg.SUCCESS);
		fundChangeResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));
		return fundChangeResponse;
	}




	/**
	 * 资金余额查询
	 * @author RaoYL
	 * @param accountBalanceRequest
	 * @return AccountBalanceResponse
	 * @throws BusinessException
	 */
	@Override
	public AccountBalanceResponse queryAccounBalance(AccountBalanceRequest accountBalanceRequest) throws BusinessException {

		logger.info("【资金余额查询】order_no:" + accountBalanceRequest.getOrder_no());
		AccountBalanceResponse accountBalanceResponse = new AccountBalanceResponse();
		BigDecimal bigDecimalall = BigDecimal.ZERO;
		//查询账户是否存在
		List<AccountSubjectInfo> accountSubjectInfos = queryAccountlist(accountBalanceRequest.getMer_no(), accountBalanceRequest.getAccount());
		if (accountSubjectInfos.size() == 0) {
			BaseResponse baseResponse = new BaseResponse();
			baseResponse.setRecode(BusinessMsg.INVALID_ACCOUNT);
			baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.INVALID_ACCOUNT) + ",账户不存在");
			throw new BusinessException(baseResponse);
		}
		for (AccountSubjectInfo a : accountSubjectInfos) {
			bigDecimalall = bigDecimalall.add(a.getN_balance());
		}
//		List<AccountSubjectInfo> accountSubjectInfo_f = queryFINANCINGPlatAccountlist(accountBalanceRequest.getMer_no(), accountBalanceRequest.getAccount(), AccountType.ElectronicAccount.getCode(), Ssubject.EACCOUNT.getCode());
//		for (AccountSubjectInfo a : accountSubjectInfo_f) {
//			bigDecimalall = bigDecimalall.add(a.getN_balance());
//		}
		AccountBalanceResponseDetail accountBalanceResponseDetail = new AccountBalanceResponseDetail();
		accountBalanceResponseDetail.setBalance(bigDecimalall);
		accountBalanceResponse.setData_detail(accountBalanceResponseDetail);
		accountBalanceResponse.setRecode(BusinessMsg.SUCCESS);
		accountBalanceResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));
		return accountBalanceResponse;
	}

	/**
	 * 账户余额明细查询
	 * @author RaoYL
	 * @param accountBalanceDetailRequest
	 * @return AccountBalanceDetailResponse
	 * @throws BusinessException
	 */
	@Override
	public AccountBalanceDetailResponse queryAccountBalanceDetail(AccountBalanceDetailRequest accountBalanceDetailRequest) throws BusinessException {
		logger.info("【账户余额明细查询】order_no:"+accountBalanceDetailRequest.getOrder_no());
        if(StringUtils.isBlank(accountBalanceDetailRequest.getAccount_type())){
            accountBalanceDetailRequest.setAccount_type(AccountType.Platcust.getCode());
        }

        AccountBalanceDetailResponse accountBalanceDetailResponse = new AccountBalanceDetailResponse();

        List<AccountBalanceDetailResponseDetail> accountBalanceDetailResponseDetails = new ArrayList<AccountBalanceDetailResponseDetail>();

        List<AccountSubjectInfo> accountSubjectInfos = new ArrayList<AccountSubjectInfo>();

        BaseResponse baseResponse = new BaseResponse();

        if(accountBalanceDetailRequest.getAccount_type().equals(AccountType.Platcust.getCode())){
            //查询用户账户是否存在
            accountSubjectInfos = queryAccountlist(accountBalanceDetailRequest.getMer_no(),accountBalanceDetailRequest.getAccount());
            if(accountSubjectInfos.size() == 0){
                baseResponse.setRecode(BusinessMsg.INVALID_ACCOUNT);
                baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.INVALID_ACCOUNT));
                throw new BusinessException(baseResponse);
            }
            for(AccountSubjectInfo accountSubjectInfo : accountSubjectInfos){
                if(accountSubjectInfo.getSub_subject().equals(Ssubject.INVEST.getCode())||accountSubjectInfo.getSub_subject().equals(Ssubject.FINANCING.getCode())){
					AccountBalanceDetailResponseDetail accountBalanceDetailResponseDetail = new AccountBalanceDetailResponseDetail();
                    accountBalanceDetailResponseDetail.setPlatcust(accountSubjectInfo.getPlatcust());
                    accountBalanceDetailResponseDetail.setPlat_no(accountSubjectInfo.getPlat_no());
                    accountBalanceDetailResponseDetail.setSubject(accountSubjectInfo.getSubject());
                    accountBalanceDetailResponseDetail.setSub_subject(accountSubjectInfo.getSub_subject());
                    accountBalanceDetailResponseDetail.setT_balance(accountSubjectInfo.getT_balance());
                    accountBalanceDetailResponseDetail.setN_balance(accountSubjectInfo.getN_balance());
                    accountBalanceDetailResponseDetail.setF_balance(accountSubjectInfo.getF_balance());
                    accountBalanceDetailResponseDetails.add(accountBalanceDetailResponseDetail);
                }
            }
        }else if(accountBalanceDetailRequest.getAccount_type().equals(AccountType.Plat.getCode())){
            //查询平台账户是否存在
            accountSubjectInfos = queryAccountlist(accountBalanceDetailRequest.getMer_no(),accountBalanceDetailRequest.getAccount());
            if(accountSubjectInfos.size() == 0){
                baseResponse.setRecode(BusinessMsg.INVALID_ACCOUNT);
                baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.INVALID_ACCOUNT));
                throw new BusinessException(baseResponse);
            }
            for(AccountSubjectInfo accountSubjectInfo : accountSubjectInfos){
                if(accountSubjectInfo.getSub_subject().equals(Ssubject.PLAT.getCode())
                        ||accountSubjectInfo.getSub_subject().equals(Ssubject.FEE.getCode())
                        ||accountSubjectInfo.getSub_subject().equals(Ssubject.EXPERIENCE.getCode())
                        ||accountSubjectInfo.getSub_subject().equals(Ssubject.VOUCHER.getCode())
                        ||accountSubjectInfo.getSub_subject().equals(Ssubject.PLUSRATE.getCode())
                        ||accountSubjectInfo.getSub_subject().equals(Ssubject.DEPOSIT.getCode())
                        ||accountSubjectInfo.getSub_subject().equals(Ssubject.PAYMENT.getCode())
                        ||accountSubjectInfo.getSub_subject().equals(Ssubject.INLINEPAYMENT.getCode())
                        ||accountSubjectInfo.getSub_subject().equals(Ssubject.INTEREST.getCode())
						|| Payment.isPayment(accountSubjectInfo.getSub_subject()) ){
					AccountBalanceDetailResponseDetail accountBalanceDetailResponseDetail = new AccountBalanceDetailResponseDetail();
                    accountBalanceDetailResponseDetail.setPlatcust(accountSubjectInfo.getPlatcust());
                    accountBalanceDetailResponseDetail.setPlat_no(accountSubjectInfo.getPlat_no());
                    accountBalanceDetailResponseDetail.setSubject(accountSubjectInfo.getSubject());
                    accountBalanceDetailResponseDetail.setSub_subject(accountSubjectInfo.getSub_subject());
                    accountBalanceDetailResponseDetail.setT_balance(accountSubjectInfo.getT_balance());
                    accountBalanceDetailResponseDetail.setN_balance(accountSubjectInfo.getN_balance());
                    accountBalanceDetailResponseDetail.setF_balance(accountSubjectInfo.getF_balance());
                    accountBalanceDetailResponseDetails.add(accountBalanceDetailResponseDetail);
                }
            }
        }else if(accountBalanceDetailRequest.getAccount_type().equals(AccountType.Product.getCode())){
            //查询标的账户(借款人募集账户)是否存在
            accountSubjectInfos = queryAccountlist(accountBalanceDetailRequest.getMer_no(),accountBalanceDetailRequest.getAccount());
            if(accountSubjectInfos.size() == 0){
                baseResponse.setRecode(BusinessMsg.INVALID_ACCOUNT);
                baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.INVALID_ACCOUNT));
                throw new BusinessException(baseResponse);
            }
            for(AccountSubjectInfo accountSubjectInfo : accountSubjectInfos){
                if(accountSubjectInfo.getSub_subject().equals(Ssubject.PROD.getCode())){
					AccountBalanceDetailResponseDetail accountBalanceDetailResponseDetail = new AccountBalanceDetailResponseDetail();
                    accountBalanceDetailResponseDetail.setPlatcust(accountSubjectInfo.getPlatcust());
                    accountBalanceDetailResponseDetail.setPlat_no(accountSubjectInfo.getPlat_no());
                    accountBalanceDetailResponseDetail.setSubject(accountSubjectInfo.getSubject());
                    accountBalanceDetailResponseDetail.setSub_subject(accountSubjectInfo.getSub_subject());
                    accountBalanceDetailResponseDetail.setT_balance(accountSubjectInfo.getT_balance());
                    accountBalanceDetailResponseDetail.setN_balance(accountSubjectInfo.getN_balance());
                    accountBalanceDetailResponseDetail.setF_balance(accountSubjectInfo.getF_balance());
                    accountBalanceDetailResponseDetails.add(accountBalanceDetailResponseDetail);
                }
            }
        }else{
            baseResponse.setRecode(BusinessMsg.UNKNOWN_ACCOUNT_TYPE);
            baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.UNKNOWN_ACCOUNT_TYPE));
            throw new BusinessException(baseResponse);
        }
        accountBalanceDetailResponse.setData_detail(accountBalanceDetailResponseDetails);
        accountBalanceDetailResponse.setOrder_no(accountBalanceDetailRequest.getOrder_no());
        accountBalanceDetailResponse.setRecode(BusinessMsg.SUCCESS);
        accountBalanceDetailResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));
		return accountBalanceDetailResponse;
	}

	public EaccountProdResponseToData queryEaccToAccoun(BaseRequest baseRequest)throws BusinessException{
	    logger.info("【份额流水查询】"+baseRequest.getOrder_no());
        EaccountProdResponseToData eaccountProdResponseToData=new EaccountProdResponseToData();
        List<EaccountProdResponseTo>eaccountProdResponseTos=new ArrayList<EaccountProdResponseTo>();
        List<ProdShareList>prodShareLists=new ArrayList<ProdShareList>();
		TransTransreqExample transTransreqExample = new TransTransreqExample();
		TransTransreqExample.Criteria criteria = transTransreqExample.createCriteria();
		criteria.andOrder_noEqualTo(baseRequest.getOrder_no());
        List<TransTransreq>transTransreqs=transTransreqMapper.selectByExample(transTransreqExample);
		if (transTransreqs.size()==0){
			throw  new BusinessException(BusinessMsg.ORIGINAL_ORDER_NOEXISTENT,BusinessMsg.getMsg(BusinessMsg.ORIGINAL_ORDER_NOEXISTENT));
		}
		String trans_serial = transTransreqs.get(0).getTrans_serial();
		//根据水流去查份额流水
		prodShareLists= custProdShareListMapper.selectQueryProdlist(trans_serial);

		if (prodShareLists.size()==0){
			logger.info("无此原交易");
			eaccountProdResponseToData.setRecode(BusinessMsg.ORIGINAL_ORDER_NOEXISTENT);
			eaccountProdResponseToData.setRemsg(BusinessMsg.getMsg(BusinessMsg.ORIGINAL_ORDER_NOEXISTENT));
			throw new BusinessException(eaccountProdResponseToData);
		}
		for(ProdShareList prodShareList1:prodShareLists){
			EaccountProdResponseTo eaccountProdResponseTo=new EaccountProdResponseTo();
            eaccountProdResponseTo.setStatus(prodShareList1.getStatus());
            eaccountProdResponseTo.setSelf_amt(prodShareList1.getSelf_amt());
            eaccountProdResponseTo.setCoupon_amt(prodShareList1.getCoupon_amt());
            eaccountProdResponseTo.setExperience_amt(prodShareList1.getExperience_amt());
            eaccountProdResponseTo.setPayout_amt(prodShareList1.getPayout_amt());
            eaccountProdResponseTo.setInterest(prodShareList1.getInterest());
			eaccountProdResponseTo.setAmt_type(prodShareList1.getAmt_type());
            eaccountProdResponseTo.setProd_id(prodShareList1.getProd_id());
			eaccountProdResponseTo.setIn_interest(prodShareList1.getIn_interest());
			eaccountProdResponseTo.setPlat_no(prodShareList1.getPlat_no());
			eaccountProdResponseTo.setPlatcust(prodShareList1.getPlatcust());
			eaccountProdResponseTo.setTrans_code(prodShareList1.getTrans_code());
			eaccountProdResponseTo.setTrans_date(prodShareList1.getTrans_date());
			eaccountProdResponseTo.setTrans_time(prodShareList1.getTrans_time());
			eaccountProdResponseTo.setVol(prodShareList1.getVol());
			eaccountProdResponseTos.add(eaccountProdResponseTo);
		}
		eaccountProdResponseToData.setData_detail(eaccountProdResponseTos);
		eaccountProdResponseToData.setOrder_no(baseRequest.getOrder_no());
		eaccountProdResponseToData.setRecode(BusinessMsg.SUCCESS);
		eaccountProdResponseToData.setRemsg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));
		return eaccountProdResponseToData;
    }

	/**
	 * 根据订单号查资金流水
	 * @param eaccountPordListRequest
	 * @return
	 * @throws BusinessException
	 */
	@Override
	public EaccountProdResponseData queryEaccAccoun(EaccountPordListRequest eaccountPordListRequest) throws BusinessException {
		logger.info("【资金流水查询】order_no:"+eaccountPordListRequest.getOrder_no());
		EaccountProdResponseData eaccountProdResponseData=new EaccountProdResponseData();
		List<EaccountProdListResponse> accountBalanceDetailResponseDetails = new ArrayList<EaccountProdListResponse>();
		List<EaccAccountamtlist> eacc=new ArrayList<EaccAccountamtlist>();
		BaseResponse baseResponse=new BaseResponse();
		TransTransreqExample transTransreqExample = new TransTransreqExample();
		TransTransreqExample.Criteria criteria = transTransreqExample.createCriteria();
		criteria.andOrder_noEqualTo(eaccountPordListRequest.getOrder_no());
		List<TransTransreq>transTransreqs=transTransreqMapper.selectByExample(transTransreqExample);
		if (transTransreqs.size()==0){
			throw  new BusinessException(BusinessMsg.ORIGINAL_ORDER_NOEXISTENT,BusinessMsg.getMsg(BusinessMsg.ORIGINAL_ORDER_NOEXISTENT));
		}
				String trans_serial = transTransreqs.get(0).getTrans_serial();
				eacc = custEaccAccountamtlistMapper.selectQueryEaccAccoun(trans_serial);
				if(eacc.size()==0){
					logger.info("无此原交易");
					baseResponse.setRecode(BusinessMsg.ORIGINAL_ORDER_NOEXISTENT);
					baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.ORIGINAL_ORDER_NOEXISTENT));
					throw new BusinessException(baseResponse);
				}
				for (EaccAccountamtlist eaccAccountamtlist : eacc) {
						EaccountProdListResponse eaccountProdListResponses = new EaccountProdListResponse();
						eaccountProdListResponses.setPlat_no(eaccAccountamtlist.getPlat_no());
						eaccountProdListResponses.setPlatcust(eaccAccountamtlist.getPlatcust());
						eaccountProdListResponses.setSubject(eaccAccountamtlist.getSubject());
						eaccountProdListResponses.setSub_subject(eaccAccountamtlist.getSub_subject());
						eaccountProdListResponses.setOppo_platcust(eaccAccountamtlist.getOppo_platcust());
						eaccountProdListResponses.setOppo_subject(eaccAccountamtlist.getOppo_subject());
						eaccountProdListResponses.setOppo_sub_subject(eaccAccountamtlist.getOppo_sub_subject());
						eaccountProdListResponses.setAmt_type(eaccAccountamtlist.getAmt_type());
						eaccountProdListResponses.setAmt(eaccAccountamtlist.getAmt());
						accountBalanceDetailResponseDetails.add(eaccountProdListResponses);
				}

		eaccountProdResponseData.setData_detail(accountBalanceDetailResponseDetails);
		eaccountProdResponseData.setOrder_no(eaccountPordListRequest.getOrder_no());
		eaccountProdResponseData.setRecode(BusinessMsg.SUCCESS);
		eaccountProdResponseData.setRemsg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));
		return eaccountProdResponseData;
	}

	/**
     *借款人募集账户(标的账户)余额查询
     * @author RaoYL
     * @param productBalanceRequest 借款人募集账户(标的账户)余额查询  验证请求参数
     * @return ProductBalanceResponse
     * @version 20180118
     */
    @Override
    public ProductBalanceResponse queryProductBalance(ProductBalanceRequest productBalanceRequest)throws BusinessException{
        logger.info("【借款人募集账户余额查询】order_no:"+productBalanceRequest.getOrder_no());
        ProductBalanceResponse productBalanceResponse = new ProductBalanceResponse();
        ProductBalanceResponseDetail productBalanceResponseDetail = new ProductBalanceResponseDetail();
        BaseResponse baseResponse = new BaseResponse();
        //查询标的是否存在
        ProdProductinfoExample example=new ProdProductinfoExample();
        ProdProductinfoExample.Criteria criteria = example.createCriteria();
        criteria.andPlat_noEqualTo(productBalanceRequest.getMer_no());
        criteria.andProd_idEqualTo(productBalanceRequest.getProd_id());
        criteria.andEnabledEqualTo(Constants.ENABLED);
        List<ProdProductinfo> prodProductInfoList = prodProductInfoMapper.selectByExample(example);
        if(prodProductInfoList.size() > 1){
            baseResponse.setRecode(BusinessMsg.DATEBASE_EXCEPTION);
            baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.DATEBASE_EXCEPTION) + "----有重复的标");
            throw new BusinessException(baseResponse);
        }else if(prodProductInfoList.size() < 1){
			baseResponse.setRecode(BusinessMsg.INVALID_PRODUCT_ID);
			baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.INVALID_PRODUCT_ID) + "----标的不存在");
			throw new BusinessException(baseResponse);
        }
        ProdProductinfo prodProductinfo = prodProductInfoList.get(0);
        if(prodProductinfo == null){
            baseResponse.setRecode(BusinessMsg.INVALID_PRODUCT_ID);
            baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.INVALID_PRODUCT_ID));
            throw new BusinessException(baseResponse);
        }

//        if(productBalanceRequest.getType() == null){
//            productBalanceRequest.setType(Tsubject.CASH.getCode());
//        }
        //查询标的账户是否存在
        List<AccountSubjectInfo> accountSubjectInfos = queryPlatAccountList(productBalanceRequest.getMer_no(),prodProductinfo.getProd_account(),productBalanceRequest.getType(),Ssubject.PROD.getCode());
        if(accountSubjectInfos.size() == 0){
            baseResponse.setRecode(BusinessMsg.INVALID_ACCOUNT);
            baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.INVALID_ACCOUNT));
            throw new BusinessException(baseResponse);
        }
        //获得标的账户的总额
        BigDecimal bigDecimal = BigDecimal.ZERO;
        for(AccountSubjectInfo accountSubjectInfo : accountSubjectInfos){
            bigDecimal = bigDecimal.add(accountSubjectInfo.getN_balance());
        }
        productBalanceResponseDetail.setBalance(bigDecimal);
        productBalanceResponse.setData_detail(productBalanceResponseDetail);
        productBalanceResponse.setRecode(BusinessMsg.SUCCESS);
        productBalanceResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));

        return productBalanceResponse;
    }

	@Override
	public List<RwWithdraw> getUnDoneRwWithdrawTransByPlatcust(String plat_no,String platcust) {
		RwWithdrawExample rwWithdrawExample = new RwWithdrawExample();
		RwWithdrawExample.Criteria criteria = rwWithdrawExample.createCriteria();
		criteria.andEnabledEqualTo(Constants.ENABLED);
		criteria.andPlat_noEqualTo(plat_no);
		criteria.andPlatcustEqualTo(platcust);
		criteria.andPay_statusEqualTo(PayStatus.PAYING.getCode());
		rwWithdrawExample.or(criteria.andPayment_statusEqualTo(PayStatus.PAYMENTFAILYES.getCode()));

		return rwWithdrawMapper.selectByExample(rwWithdrawExample);
	}

	@Override
	public List<EaccAccountamtlist> queryTransFlowList(EaccAccountamtlist eaccAccountamtlist) throws BusinessException {
		EaccAccountamtlistExample example = new EaccAccountamtlistExample();
		EaccAccountamtlistExample.Criteria criteria = example.createCriteria();
		criteria.andTrans_serialEqualTo(eaccAccountamtlist.getTrans_serial());
		criteria.andEnabledEqualTo(Constants.ENABLED);
		if(!StringUtils.isEmpty(eaccAccountamtlist.getPlat_no())){
			criteria.andPlat_noEqualTo(eaccAccountamtlist.getPlat_no());
		}
		if(!StringUtils.isEmpty(eaccAccountamtlist.getPlatcust())){
			criteria.andPlatcustEqualTo(eaccAccountamtlist.getPlatcust());
		}
		if(!StringUtils.isEmpty(eaccAccountamtlist.getSubject())){
			criteria.andSubjectEqualTo(eaccAccountamtlist.getSubject());
		}
		if(!StringUtils.isEmpty(eaccAccountamtlist.getSub_subject())){
			criteria.andSub_subjectEqualTo(eaccAccountamtlist.getSub_subject());
		}
		if(!StringUtils.isEmpty(eaccAccountamtlist.getAmt_type())){
			criteria.andAmt_typeEqualTo(eaccAccountamtlist.getAmt_type());
		}
//		if(!StringUtils.isEmpty(eaccAccountamtlist.getOppo_sub_subject())){
//			criteria.andOppo_sub_subjectEqualTo(eaccAccountamtlist.getOppo_sub_subject());
//		}
		List<EaccAccountamtlist> eaccAccountamtlistList=eaccAccountamtlistMapper.selectByExample(example);
		return eaccAccountamtlistList;
	}

	@Override
	public List<EaccAccountamtlist> queryEaccAccountamtlistByTransSerial(String trans_serial) throws BusinessException {
		EaccAccountamtlistExample eaccAccountamtlistExample=new EaccAccountamtlistExample();
		EaccAccountamtlistExample.Criteria criteria=eaccAccountamtlistExample.createCriteria();
		criteria.andTrans_serialEqualTo(trans_serial);
		criteria.andEnabledEqualTo(Constants.ENABLED);
		return eaccAccountamtlistMapper.selectByExample(eaccAccountamtlistExample);
	}
	@Override
	public List<EaccAccountamtlist> accountFlowListQuery(String platcust, String startDate, String endDate) throws BusinessException {
		EaccAccountamtlistExample example = new EaccAccountamtlistExample();
		EaccAccountamtlistExample.Criteria criteria = example.createCriteria();
		criteria.andPlatcustEqualTo(platcust);
		criteria.andEnabledEqualTo(Constants.ENABLED);
		criteria.andTrans_dateBetween(startDate,endDate);
		return eaccAccountamtlistMapper.selectByExample(example);
	}
	@Autowired
	private CustOpenconfigMapper custOpenconfigMapper;
    @Override
	public List<String> listOpenConfigByMallNo(String mallNo) throws BusinessException {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("mall_no", mallNo);
		List<String> accOpenconfigList = custOpenconfigMapper.selectPlatNoByMallNo(params);
		return accOpenconfigList;
	}


}
