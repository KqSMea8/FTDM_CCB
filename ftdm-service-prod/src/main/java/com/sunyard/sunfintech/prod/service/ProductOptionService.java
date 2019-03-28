package com.sunyard.sunfintech.prod.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.sunyard.sunfintech.account.provider.IAccountOprationService;
import com.sunyard.sunfintech.account.provider.IAccountQueryService;
import com.sunyard.sunfintech.account.provider.IAccountTransferService;
import com.sunyard.sunfintech.core.Constants;
import com.sunyard.sunfintech.core.TransConsts;
import com.sunyard.sunfintech.core.base.BaseRequest;
import com.sunyard.sunfintech.core.base.BaseResponse;
import com.sunyard.sunfintech.core.base.BaseServiceSimple;
import com.sunyard.sunfintech.core.dic.*;
import com.sunyard.sunfintech.core.exception.BusinessException;
import com.sunyard.sunfintech.core.exception.BusinessMsg;
import com.sunyard.sunfintech.core.util.*;
import com.sunyard.sunfintech.custdao.mapper.CustProdProductinfoMapper;
import com.sunyard.sunfintech.custdao.mapper.CustProdRepayMapper;
import com.sunyard.sunfintech.custdao.mapper.CustProdShareInallMapper;
import com.sunyard.sunfintech.custdao.mapper.CustProdShareListMapper;
import com.sunyard.sunfintech.dao.entity.*;
import com.sunyard.sunfintech.dao.mapper.*;
import com.sunyard.sunfintech.prod.model.bo.*;
import com.sunyard.sunfintech.prod.provider.IProdSearchService;
import com.sunyard.sunfintech.prod.provider.IProductOptionService;
import com.sunyard.sunfintech.prod.provider.IProductRefundDBOptionService;
import com.sunyard.sunfintech.pub.provider.ISendMsgService;
import com.sunyard.sunfintech.pub.provider.ISysParameterService;
import com.sunyard.sunfintech.pub.provider.ITransReqService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;


@Service(interfaceClass = IProductOptionService.class)
@CacheConfig(cacheNames="ProductOptionService")
@org.springframework.stereotype.Service
public class ProductOptionService extends BaseServiceSimple implements IProductOptionService {

    private final Logger logger = LogManager.getLogger(this.getClass());

	@Autowired
	private CustProdShareInallMapper custProdShareInallMapper;

	@Autowired
	private ProdShareListMapper prodShareListMapper;

	@Autowired
	private CustProdShareListMapper custProdShareListMapper;

    @Autowired
	private CustProdRepayMapper custProdRepayMapper;

	@Autowired
	private ProdProductinfoMapper prodProductInfoMapper;

	@Autowired
	private ProdShareInallMapper prodShareInallMapper;

	@Autowired
	private CustProdProductinfoMapper custProdProductinfoMapper;

	@Autowired
	private EaccFinancinfoMapper eaccFinancinfoMapper;

	@Autowired
	private ProdChargeoffMapper prodChargeoffMapper;

	@Autowired
	private IProdSearchService prodSearchService;

	@Autowired
	private IAccountQueryService accountQueryService;

	/*@Autowired
	private IAccountTransferService accountTransferService;*/

	@Autowired
	private ITransReqService transReqService;

	@Autowired
	private TransTransreqMapper transTransreqMapper;

	@Autowired
	private RwWithdrawMapper rwWithdrawMapper;

	@Autowired
	private ProductOptionExtService productoptionExtService ;

	@Autowired
	private EaccUserinfoMapper eaccUserinfoMapper;

	@Autowired
	private ISysParameterService sysParameterService;

	@Autowired
	private IAccountOprationService accountOprationService;

	@Autowired
	private IAccountTransferService accountTransferService;

	@Autowired
	private ProdCompensationListMapper prodCompensationListMapper;

	@Override
	public ProdPublishResponse publish(ProdPublishRequest prodPublishRequest) throws BusinessException {
		ProdPublishResponse prodPublishResponse = new ProdPublishResponse();

		TransTransreq transTransReq = transReqService.getTransTransReq(prodPublishRequest.clone(),TransConsts.PUBLISH_CODE,TransConsts.PUBLISH_NAME, false);
		transTransReq.setPlatcust(prodPublishRequest.getEaccFinancinfo().get(0).getCust_no());
		transReqService.insert(transTransReq);
		logger.info("【借款人募集申请】-->添加业务流水完成-->order_no:"+prodPublishRequest.getOrder_no());

		try{

			if(prodPublishRequest.getEaccFinancinfo().size() != 1){
				logger.info("【借款人募集申请】-->融资信息不存在-->order_no:"+prodPublishRequest.getOrder_no());
				throw new BusinessException(BusinessMsg.ACCOUNT_INFO_DIFF, "参数缺少融资人信息");
			}
			logger.info("【借款人募集申请】-->融资信息存在-->order_no:"+prodPublishRequest.getOrder_no());

			EaccFinancinfoDetail eaccFinancinfoDetail = prodPublishRequest.getEaccFinancinfo().get(0);

			if (!prodPublishRequest.getChargeOff_auto().equals(ChargeOffType.ZERO.getCode())) {
				logger.info("【借款人募集申请】-->出账方式有误-->order_no:"+prodPublishRequest.getOrder_no());
				throw new BusinessException(BusinessMsg.ACCOUNT_INFO_DIFF, "融资人出账方式有误,chargeOff_auto="+prodPublishRequest.getChargeOff_auto());
			}
			logger.info("【借款人募集申请】-->判断出账方式正确-->order_no:"+prodPublishRequest.getOrder_no());

			EaccUserinfo eaccUserinfo = queryEaccUserInfoByMallNoAndPlatcust(prodPublishRequest.getMall_no(),eaccFinancinfoDetail.getCust_no());
			if(null == eaccUserinfo){
				logger.info("【借款人募集申请】-->融资人信息不存在-->order_no:"+prodPublishRequest.getOrder_no());
				throw new BusinessException(BusinessMsg.INVALID_ACCOUNT, BusinessMsg.getMsg(BusinessMsg.INVALID_ACCOUNT)+",融资人信息不存在,platcust:"+eaccFinancinfoDetail.getCust_no());
			}
			logger.info("【借款人募集申请】-->融资人用户信息存在-->order_no:"+prodPublishRequest.getOrder_no());

			if(CusType.COMPANY.getCode().equals(eaccUserinfo.getCus_type())) {
				BigDecimal com_prod_limit = new BigDecimal(sysParameterService.querySysParameter(prodPublishRequest.getMall_no(), SysParamterKey.COM_PROD_LIMIT));
				if ((-1 == com_prod_limit.compareTo(prodPublishRequest.getTotal_limit()))) {
					logger.info("【借款人募集申请】-->对公标的金额超限-->order_no:" + prodPublishRequest.getOrder_no());
					throw new BusinessException(BusinessMsg.MONEY_ERROR, "对公标的金额超限");
				}
			}

			if(CusType.PERSONAL.getCode().equals(eaccUserinfo.getCus_type())){
				BigDecimal pri_prod_limit = new BigDecimal(sysParameterService.querySysParameter(prodPublishRequest.getMall_no(), SysParamterKey.PRI_PROD_LIMIT));
				if((-1 == pri_prod_limit.compareTo(prodPublishRequest.getTotal_limit()))) {
					logger.info("【借款人募集申请】-->对私标的金额超限-->order_no:" + prodPublishRequest.getOrder_no());
					throw new BusinessException(BusinessMsg.MONEY_ERROR, "对私标的金额超限");
				}
			}

			if (StringUtils.isNotBlank(eaccFinancinfoDetail.getTrustee_platcust())) {
				EaccUserinfo eaccTrusteeUserinfo = queryEaccUserInfoByMallNoAndPlatcust(prodPublishRequest.getMall_no(), eaccFinancinfoDetail.getTrustee_platcust());
				if (null == eaccTrusteeUserinfo) {
					logger.info("【借款人募集申请】-->受托人信息不存在-->order_no:" + prodPublishRequest.getOrder_no());
					throw new BusinessException(BusinessMsg.INVALID_ACCOUNT, BusinessMsg.getMsg(BusinessMsg.INVALID_ACCOUNT) + ",受托人信息不存在,trustee_platcust:" + eaccFinancinfoDetail.getTrustee_platcust());
				}
				logger.info("【借款人募集申请】-->受托人用户信息存在-->order_no:" + prodPublishRequest.getOrder_no());
			}

			ProdProductinfo checkProdProductInfo = prodSearchService.queryProdInfo(prodPublishRequest.getMer_no(), prodPublishRequest.getProd_id());
			if(checkProdProductInfo != null){
				logger.info("【借款人募集申请】-->已查询到相同标的ID的标的："+checkProdProductInfo.toString());
				throw new BusinessException(BusinessMsg.REPEATED_ID,BusinessMsg.getMsg(BusinessMsg.REPEATED_ID));
			}
			logger.info("【借款人募集申请】-->次标不存在可以发布-->order_no:"+prodPublishRequest.getOrder_no());

			AccountSubjectInfo accountSubjectInfo = new AccountSubjectInfo();
			accountSubjectInfo.setPlat_no(prodPublishRequest.getMer_no());
			accountSubjectInfo.setAccount_type(AccountType.Product.getCode());
			accountSubjectInfo.setStatus(CardStatus.ACTIVE.getCode());
			//accountSubjectInfo.setMall_platcust();
			accountSubjectInfo.setMap_platcust(eaccFinancinfoDetail.getCust_no());
			accountSubjectInfo = accountOprationService.register(accountSubjectInfo);
			logger.info("【借款人募集申请】-->注册标的账号成功-->order_no:"+prodPublishRequest.getOrder_no());

			ProdProductinfo prodProductinfo = new ProdProductinfo();
			prodProductinfo.setProd_id(prodPublishRequest.getProd_id());
			prodProductinfo.setPlat_no(prodPublishRequest.getMer_no());
			prodProductinfo.setProd_name(prodPublishRequest.getProd_name());
			prodProductinfo.setProd_type(prodPublishRequest.getProd_type());
			prodProductinfo.setTotal_limit(prodPublishRequest.getTotal_limit());
			prodProductinfo.setRemain_limit(prodPublishRequest.getTotal_limit());
			prodProductinfo.setSell_date(prodPublishRequest.getSell_date());
			prodProductinfo.setValue_date(prodPublishRequest.getValue_date());
			prodProductinfo.setValue_type(prodPublishRequest.getValue_type());
			prodProductinfo.setCreate_type(prodPublishRequest.getCreate_type());
			prodProductinfo.setExpire_date(prodPublishRequest.getExpire_date());
			prodProductinfo.setCycle(prodPublishRequest.getCycle());
			prodProductinfo.setCycle_unit(prodPublishRequest.getCycle_unit());
			prodProductinfo.setIst_year(prodPublishRequest.getIst_year());
			prodProductinfo.setRepay_type(prodPublishRequest.getRepay_type());
			prodProductinfo.setProd_state(ProdState.PUBLISH.getCode());
			prodProductinfo.setCharge_off_auto(prodPublishRequest.getChargeOff_auto());
			prodProductinfo.setRisk_lvl(prodPublishRequest.getRisk_lvl());
			prodProductinfo.setProd_info(prodPublishRequest.getProd_info());
			prodProductinfo.setBuyer_num_limit(String.valueOf(prodPublishRequest.getBuyer_num_limit()));
			prodProductinfo.setOverLimit(prodPublishRequest.getOverLimit());
			prodProductinfo.setOver_total_limit(String.valueOf(prodPublishRequest.getOver_total_limit()));
			prodProductinfo.setEnabled(Constants.ENABLED);
			prodProductinfo.setCreate_time(DateUtils.getNow());
			prodProductinfo.setCreate_type(prodPublishRequest.getCreate_type());
			prodProductinfo.setUpdate_time(DateUtils.getNow());
			prodProductinfo.setProd_account(accountSubjectInfo.getPlatcust());
			prodProductinfo.setSaled_limit(BigDecimal.ZERO);
			prodProductinfo.setEnabled(Constants.ENABLED);
			prodProductinfo.setAuto_flg(prodPublishRequest.getAuto_flg());
			List<ProdCompensationList> prodCompensationLists = new ArrayList<ProdCompensationList>();
			if(null != prodPublishRequest.getCompensationList() && prodPublishRequest.getCompensationList().size()>0) {
				for (ProdPublicCompensation prodPublicCompensation : prodPublishRequest.getCompensationList()) {

					validate(prodPublicCompensation);

					EaccUserinfo compensationUserinfo = queryEaccUserInfoByMallNoAndPlatcust(prodPublishRequest.getMall_no(),prodPublicCompensation.getPlatcust());

					if(null == compensationUserinfo){
						logger.info("【借款人募集申请】-->合作人信息不存在-->order_no:"+prodPublishRequest.getOrder_no());
						throw new BusinessException(BusinessMsg.INVALID_ACCOUNT, BusinessMsg.getMsg(BusinessMsg.INVALID_ACCOUNT)+",合作人信息有误,platcust:"+prodPublicCompensation.getPlatcust());
					}
					logger.info("【借款人募集申请】-->合作人用户信息存在-->order_no:"+prodPublishRequest.getOrder_no());
					ProdCompensationList prodCompensationList = new ProdCompensationList();
					prodCompensationList.setPlat_no(prodPublishRequest.getMer_no());
					prodCompensationList.setProd_id(prodProductinfo.getProd_id());
					prodCompensationList.setPlatcust(prodPublicCompensation.getPlatcust());
					prodCompensationList.setType("0");
					prodCompensationList.setOrder_no(prodPublishRequest.getOrder_no());
					prodCompensationList.setStatus(OrderStatus.SUCCESS.getCode());
					prodCompensationList.setEnabled(Constants.ENABLED);
					prodCompensationList.setCreate_time(DateUtils.getNow());
					prodCompensationList.setUpdate_time(DateUtils.getNow());
					prodCompensationLists.add(prodCompensationList);
				}
			}

			EaccFinancinfo eaccFinancinfo = new EaccFinancinfo();
			eaccFinancinfo.setProd_id(prodProductinfo.getProd_id());
			eaccFinancinfo.setPlat_no(prodProductinfo.getPlat_no());
			eaccFinancinfo.setPlatcust(eaccFinancinfoDetail.getCust_no());
			eaccFinancinfo.setReg_date(eaccFinancinfoDetail.getReg_date());
			eaccFinancinfo.setReg_time(eaccFinancinfoDetail.getReg_time());
			eaccFinancinfo.setFinanc_amt(eaccFinancinfoDetail.getFinanc_amt());
			eaccFinancinfo.setCycle(prodProductinfo.getCycle());
			eaccFinancinfo.setCycle_unit(prodProductinfo.getCycle_unit());
			eaccFinancinfo.setFinanc_int(eaccFinancinfoDetail.getFinanc_int());
			eaccFinancinfo.setRepay_type(prodProductinfo.getRepay_type());
			eaccFinancinfo.setUse_date(eaccFinancinfoDetail.getUse_date());
			eaccFinancinfo.setFinanc_purpose(eaccFinancinfoDetail.getFinanc_purpose());
			eaccFinancinfo.setTrustee_platcust(eaccFinancinfoDetail.getTrustee_platcust());
			//eaccFinancinfo.setBank_code(eaccFinancinfoDetail.getBank_code());
			eaccFinancinfo.setOpen_branch(eaccFinancinfoDetail.getOpen_branch());
			eaccFinancinfo.setWithdraw_account(eaccFinancinfoDetail.getWithdraw_account());
			eaccFinancinfo.setPayee_name(eaccFinancinfoDetail.getPayee_name());
			eaccFinancinfo.setAccount_type(eaccFinancinfoDetail.getAccount_type());
			eaccFinancinfo.setFee_int(eaccFinancinfoDetail.getFee_int());
			eaccFinancinfo.setEnabled(Constants.ENABLED);
			eaccFinancinfo.setCreate_time(DateUtils.getNow());
			eaccFinancinfo.setUpdate_time(DateUtils.getNow());

			try{
				productoptionExtService.publish(prodProductinfo,eaccFinancinfo,prodCompensationLists,prodPublishRequest.getOrder_no());
				logger.info("【借款人募集申请】-->添加相关表【EaccFinancinfo,ProdCompensationList,ProdProductinfo】成功-->order_no:" + prodPublishRequest.getOrder_no());
			}catch (Exception e){
				logger.error("【借款人募集申请】-->异常-->添加相关表【EaccFinancinfo,ProdCompensationList,ProdProductinfo】-->order_no:" + prodPublishRequest.getOrder_no(),e);

				accountOprationService.deleteAccountSubjectInfoByPlatcust(accountSubjectInfo.getPlatcust());
				logger.info("【借款人募集申请】-->异常-->回滚注册的账户成功-->platcust:"+accountSubjectInfo.getPlatcust()+"-->order_no:" + prodPublishRequest.getOrder_no());

				throw new BusinessException(BusinessMsg.FAIL,BusinessMsg.getMsg(BusinessMsg.FAIL)+",插入异常");
			}


			transTransReq.setReturn_code(BusinessMsg.SUCCESS);
			transTransReq.setReturn_msg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));
			transTransReq.setStatus(FlowStatus.SUCCESS.getCode());
			transReqService.insert(transTransReq);
			logger.info("【借款人募集申请】-->修改流水完成-->order_no:" + prodPublishRequest.getOrder_no());

			ProdPublishResponseData prodPublishResponseData = new ProdPublishResponseData();
			prodPublishResponseData.setProd_id(prodProductinfo.getProd_id());
			prodPublishResponseData.setProd_account(accountSubjectInfo.getPlatcust());
			prodPublishResponse.setRecode(BusinessMsg.SUCCESS);
			prodPublishResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));
			prodPublishResponse.setData_obj(prodPublishResponseData);

		} catch (Exception e) {

			logger.error("【借款人募集申请】异常-->order_no:" + prodPublishRequest.getOrder_no(),e);

			BaseResponse baseResponse=new BaseResponse();
			if(e instanceof BusinessException){
				baseResponse=((BusinessException) e).getBaseResponse();
			}else{
				baseResponse.setRecode(BusinessMsg.DATEBASE_EXCEPTION);
				baseResponse.setRemsg(BusinessMsg.getMsg( BusinessMsg.DATEBASE_EXCEPTION));
			}

			transTransReq.setReturn_code(baseResponse.getRecode());
			transTransReq.setReturn_msg(baseResponse.getRemsg());
			transTransReq.setStatus(FlowStatus.FAIL.getCode());
			transReqService.insert(transTransReq);

			throw new BusinessException(baseResponse);
		}

		return  prodPublishResponse;
	}



	@Override
	@Transactional
	public  ProdPublishResponseData publishOld(BaseRequest baseRequest, ProdProductinfo prodProductinfo  , List<EaccFinancinfoDetail>  eaccFinancinfo, List<ProdCompensationList> compensationlist ) throws BusinessException {

		logger.info("=======================开始=====标的发布");

		//判断标的融资人信息是否存在，并且判断金额是否超限
		if(null != eaccFinancinfo && eaccFinancinfo.size()>0) {
			String platcust = eaccFinancinfo.get(0).getCust_no();
			EaccUserinfoExample eaccUserinfoExample = new EaccUserinfoExample();
			eaccUserinfoExample.createCriteria().andMallcustEqualTo(platcust);
			List<EaccUserinfo> eaccUserinfoList = eaccUserinfoMapper.selectByExample(eaccUserinfoExample);
			if(null == eaccUserinfoList || eaccUserinfoList.size()<=0){
				logger.info("融资人信息有误");
				throw new BusinessException(BusinessMsg.ACCOUNT_INFO_DIFF, "融资人用户信息不存在");
			}else{
				//检查标的份额是否超限
				EaccUserinfo eaccUserinfo = eaccUserinfoList.get(0);
				//对公标的
				if(CusType.COMPANY.getCode().equals(eaccUserinfo.getCus_type())) {
					BigDecimal com_prod_limit = new BigDecimal(sysParameterService.querySysParameter(baseRequest.getMall_no(), "com_prod_limit"));
					if ((-1 == com_prod_limit.compareTo(prodProductinfo.getTotal_limit())))
						throw new BusinessException(BusinessMsg.MONEY_ERROR, "对公标的金额超限");
				}
				//对私标的
				if(CusType.PERSONAL.getCode().equals(eaccUserinfo.getCus_type())){
					BigDecimal pri_prod_limit = new BigDecimal(sysParameterService.querySysParameter(baseRequest.getMall_no(), "pri_prod_limit"));
					if((-1 == pri_prod_limit.compareTo(prodProductinfo.getTotal_limit())))
						throw new BusinessException(BusinessMsg.MONEY_ERROR, "对私标的金额超限");
				}

			}
			if (StringUtils.isNotBlank(eaccFinancinfo.get(0).getTrustee_platcust())) {
				EaccUserinfo eaccTrusteeUserinfo = queryEaccUserInfoByMallNoAndPlatcust(baseRequest.getMall_no(), eaccFinancinfo.get(0).getTrustee_platcust());
				if (null == eaccTrusteeUserinfo) {
					logger.info("【借款人募集申请】-->受托人信息不存在-->order_no:" + baseRequest.getOrder_no());
					throw new BusinessException(BusinessMsg.INVALID_ACCOUNT, BusinessMsg.getMsg(BusinessMsg.INVALID_ACCOUNT) + ",受托人信息不存在,trustee_platcust:" + eaccFinancinfo.get(0).getTrustee_platcust());
				}
				logger.info("【借款人募集申请】-->受托人用户信息存在-->order_no:" + baseRequest.getOrder_no());
			}
		}else{
			throw new BusinessException(BusinessMsg.ACCOUNT_INFO_DIFF, "参数缺少融资人信息");
		}

		//记录业务流水表
		TransTransreq transTransReq = transReqService.getTransTransReq(baseRequest,TransConsts.PUBLISH_CODE,TransConsts.PUBLISH_NAME, false);
		transReqService.insert(transTransReq);

		//检查标是否存在
		ProdProductinfo checkProdProductInfo = prodSearchService.queryProdInfo(baseRequest.getMer_no(), prodProductinfo.getProd_id());
		if(checkProdProductInfo != null){
			logger.info("已查询到相同标的ID的标的："+checkProdProductInfo.toString());

			transTransReq.setReturn_code(BusinessMsg.REPEATED_ID);
			transTransReq.setReturn_msg(BusinessMsg.getMsg(BusinessMsg.REPEATED_ID));
			transTransReq.setStatus(FlowStatus.FAIL.getCode());
			transReqService.insert(transTransReq);

			BaseResponse baseResponse=new BaseResponse();
			baseResponse.setRecode(BusinessMsg.REPEATED_ID);
			baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.REPEATED_ID));
			throw new BusinessException(baseResponse);
		}
		AccountSubjectInfo accountSubjectInfo = new AccountSubjectInfo();
		try {
			//AccountSubjectInfo accountSubjectInfo = new AccountSubjectInfo();
			accountSubjectInfo.setPlat_no(prodProductinfo.getPlat_no());
			accountSubjectInfo.setAccount_type(AccountType.Product.getCode());
			accountSubjectInfo.setStatus(CardStatus.ACTIVE.getCode());
			accountSubjectInfo = accountOprationService.register(accountSubjectInfo);
			//插入 prod_productinfo表的数据
			prodProductinfo.setProd_account(accountSubjectInfo.getPlatcust());
			prodProductinfo.setSaled_limit(BigDecimal.ZERO);
			prodProductinfo.setEnabled(Constants.ENABLED);
			if(null == prodProductinfo.getCreate_by()) prodProductinfo.setCreate_by(SeqUtil.RANDOM_KEY);
			if(null == prodProductinfo.getCreate_time()) prodProductinfo.setCreate_time(new Date());
			//prodProductinfo.setRemain_limit(BigDecimal.ZERO);
			prodProductInfoMapper.insert(prodProductinfo);

			//插入compensation_list 代偿还款表 的数据
			if(null != compensationlist && compensationlist.size()>0) {
				for (ProdCompensationList cl : compensationlist) {

					List<AccountSubjectInfo> accountSubjectInfoList = accountQueryService.queryAccountlist(baseRequest.getMer_no(),cl.getPlatcust());
					if(accountSubjectInfoList.size() == 0){

						logger.info("代偿账户不存在，账号："+cl.getPlatcust() + ",平台号：" + baseRequest.getMer_no());

						transTransReq.setReturn_code(BusinessMsg.INVALID_ACCOUNT);
						transTransReq.setReturn_msg(BusinessMsg.getMsg(BusinessMsg.INVALID_ACCOUNT));
						transTransReq.setStatus(FlowStatus.FAIL.getCode());
						transReqService.insert(transTransReq);

						BaseResponse baseResponse=new BaseResponse();
						baseResponse.setRecode(BusinessMsg.INVALID_ACCOUNT);
						baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.INVALID_ACCOUNT));
						throw new BusinessException(baseResponse);

					}


					cl.setPlat_no(prodProductinfo.getPlat_no());
					cl.setProd_id(prodProductinfo.getProd_id());
					cl.setPlatcust(cl.getPlatcust());
					cl.setType(cl.getType());
					cl.setOrder_no(baseRequest.getOrder_no());
					cl.setStatus(ShareStatus.SUCESS.getCode());
					cl.setEnabled(Constants.ENABLED);
					cl.setRemark(cl.getRemark());
					cl.setCreate_by(cl.getCreate_by());
					cl.setCreate_time(DateUtils.getNow());
					cl.setUpdate_by(cl.getUpdate_by());
					cl.setUpdate_time(DateUtils.getNow());
					//cl.setExt1(prodProductinfo.getExt1());
					//cl.setExt2(prodProductinfo.getExt2());
					prodCompensationListMapper.insert(cl);
					//compensationlistmapper.insert(cl);
				}
			}
			logger.info("publish params:"+prodProductinfo);

			//融资人判断放到最上面
			AccountSubjectInfo accountSubjectInfocheck = accountQueryService.queryAccount(baseRequest.getMer_no(),eaccFinancinfo.get(0).getCust_no(),Tsubject.CASH.getCode(),Ssubject.FINANCING.getCode());
			if(accountSubjectInfocheck == null){

				logger.info("融资账户不存在");

				transTransReq.setReturn_code(BusinessMsg.INVALID_ACCOUNT);
				transTransReq.setReturn_msg(BusinessMsg.getMsg(BusinessMsg.INVALID_ACCOUNT));
				transTransReq.setStatus(FlowStatus.FAIL.getCode());
				transReqService.insert(transTransReq);

				BaseResponse baseResponse=new BaseResponse();
				baseResponse.setRecode(BusinessMsg.INVALID_ACCOUNT);
				baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.INVALID_ACCOUNT) + "融资账户不存在");
				throw new BusinessException(baseResponse);
			}


			for (EaccFinancinfoDetail eaccFinancinfoDetail : eaccFinancinfo) {
				EaccFinancinfo eaccFinancinfo1 = new EaccFinancinfo();
				eaccFinancinfo1.setProd_id(prodProductinfo.getProd_id());
				eaccFinancinfo1.setPlat_no(prodProductinfo.getPlat_no());
				eaccFinancinfo1.setPlatcust(eaccFinancinfoDetail.getCust_no());
				eaccFinancinfo1.setReg_date(eaccFinancinfoDetail.getReg_date());
				eaccFinancinfo1.setReg_time(eaccFinancinfoDetail.getReg_time());
				eaccFinancinfo1.setFinanc_amt(eaccFinancinfoDetail.getFinanc_amt());
				eaccFinancinfo1.setCycle(prodProductinfo.getCycle());
				eaccFinancinfo1.setCycle_unit(prodProductinfo.getCycle_unit());
				eaccFinancinfo1.setFinanc_int(eaccFinancinfoDetail.getFinanc_int());
				eaccFinancinfo1.setRepay_type(prodProductinfo.getRepay_type());
				eaccFinancinfo1.setUse_date(eaccFinancinfoDetail.getUse_date());
				eaccFinancinfo1.setFinanc_purpose(eaccFinancinfoDetail.getFinanc_purpose());
				eaccFinancinfo1.setTrustee_platcust(eaccFinancinfoDetail.getTrustee_platcust());
				//eaccFinancinfo1.setBank_code(eaccFinancinfolist.getBank_code());
				eaccFinancinfo1.setOpen_branch(eaccFinancinfoDetail.getOpen_branch());
				eaccFinancinfo1.setWithdraw_account(eaccFinancinfoDetail.getWithdraw_account());
				//eaccFinancinfolist.setStatus();
				eaccFinancinfo1.setPayee_name(eaccFinancinfoDetail.getPayee_name());
				eaccFinancinfo1.setAccount_type(eaccFinancinfoDetail.getAccount_type());
				eaccFinancinfo1.setFee_int(eaccFinancinfoDetail.getFee_int());
				eaccFinancinfo1.setEnabled(Constants.ENABLED);
				eaccFinancinfo1.setCreate_time(DateUtils.getNow());
				eaccFinancinfo1.setUpdate_time(DateUtils.getNow());

				eaccFinancinfoMapper.insert(eaccFinancinfo1);
			}


		} catch (Exception e) {
			logger.error("标的发布异常：" + e);

			transTransReq.setReturn_code(BusinessMsg.DATEBASE_EXCEPTION);
			transTransReq.setReturn_msg(BusinessMsg.getMsg(BusinessMsg.DATEBASE_EXCEPTION));
			transTransReq.setStatus(FlowStatus.FAIL.getCode());
			transReqService.insert(transTransReq);

			BaseResponse baseResponse=new BaseResponse();
			if(e instanceof BusinessException){
				baseResponse=((BusinessException) e).getBaseResponse();
			}else{
				baseResponse.setRecode(BusinessMsg.DATEBASE_EXCEPTION);
				baseResponse.setRemsg(BusinessMsg.getMsg( BusinessMsg.DATEBASE_EXCEPTION));
			}
			throw new BusinessException(baseResponse);
		}





		ProdPublishResponseData prodPublishResponseData = new ProdPublishResponseData();
		prodPublishResponseData.setProd_id(prodProductinfo.getProd_id());
		prodPublishResponseData.setProd_account(accountSubjectInfo.getPlatcust());

		//更新业务流水
		transTransReq.setReturn_code(BusinessMsg.SUCCESS);
		transTransReq.setReturn_msg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));
		transTransReq.setStatus(FlowStatus.SUCCESS.getCode());
		transReqService.insert(transTransReq);

		return  prodPublishResponseData;
	}

	@Override
	public ProdEstablishOrAbortResponse establishOrAbort(ProdEstablishOrAbortRequest prodEstablishOrAbort) throws BusinessException {
		ProdEstablishOrAbortResponse response=new ProdEstablishOrAbortResponse();
		OrderData orderData=new OrderData();
		orderData.setProcess_date(DateUtils.formatDateToStr(new Date()));
		orderData.setOrder_no(prodEstablishOrAbort.getOrder_no());
		logger.info("**********【" + prodEstablishOrAbort.getOrder_no() + "】标的成废>>开始...**********");
		BaseResponse baseResponse = new BaseResponse();
		TransTransreq transTransReq = new TransTransreq();


		String lockKey = getLockKey(prodEstablishOrAbort.getMer_no() + prodEstablishOrAbort.getProd_id() + EstablishOrAbortFlag.ESTABLISH.getCode() + EstablishOrAbortFlag.ABORT.getCode());
		boolean lock = CacheUtil.getLock(lockKey);
		int lockCount = 0;
		if (!lock) {
			logger.info("【获取订单锁失败】","订单处理中，请勿重复提交");
			throw new BusinessException(BusinessMsg.RUNTIME_EXCEPTION, "订单处理中，请勿重复提交");
		}
		//记录标的成立业务流水表
		transTransReq = transReqService.getTransTransReq(prodEstablishOrAbort.clone(), TransConsts.ESTAB_CODE, TransConsts.ESTAB_NAME, false);
		transReqService.insert(transTransReq);
		logger.info("**********【" + prodEstablishOrAbort.getOrder_no() + "】标的成立>>记录标的成立流水成功**********");


		try {
			//判断标的是否存在
			ProdProductinfo prodInfo = prodSearchService.queryProdInfo(prodEstablishOrAbort.getMer_no(), prodEstablishOrAbort.getProd_id());
			if (prodInfo == null) {
				logger.error("**********【" + prodEstablishOrAbort.getOrder_no() + "】标的成废>>查询不到对应标信息**********");
				throw new BusinessException(BusinessMsg.PARAMETER_ERROR, String.format("根据平台编号【%s】和产品编号【%s】查询不到对应标信息", prodEstablishOrAbort.getMer_no(), prodEstablishOrAbort.getProd_id()));
			}
			if (!ProdState.PUBLISH.getCode().equals(prodInfo.getProd_state())) {
				logger.error("**********【" + prodEstablishOrAbort.getOrder_no() + "】标的成废>>该标已成标或者废标**********");
				throw new BusinessException(BusinessMsg.PARAMETER_ERROR, String.format("根据平台编号【%s】和产品编号【%s】查询到对应标不是标的发布状态，不可成标或废标", prodEstablishOrAbort.getMer_no(), prodEstablishOrAbort.getProd_id()));
			}
			if(ProdType.PERIOD.getCode().equals(prodInfo.getProd_type())&&prodInfo.getTotal_limit().compareTo(prodInfo.getSaled_limit() )!=0){
				logger.error("**********【" + prodEstablishOrAbort.getOrder_no() + "】标的成废>>已卖金额跟募集金额不一样**********");
				throw new BusinessException(BusinessMsg.PARAMETER_ERROR, String.format("平台编号【%s】和产品编号【%s】，已卖金额跟募集金额不一样，不可成标或废标", prodEstablishOrAbort.getMer_no(), prodEstablishOrAbort.getProd_id()));

			}
			String prod_type = prodInfo.getProd_type();
			if (prod_type == null || "".equals(prod_type)) {
				throw new BusinessException(BusinessMsg.PARAMETER_ERROR, String.format("查询出的标的信息中产品类型prod_type【%s】属性不能为空，否则无法判断是活期还是非活期", prod_type));
			}

			List<RepayPlanListObject> repayPlanListObjectList = prodEstablishOrAbort.getRepayPlanListObject();
			if (repayPlanListObjectList == null || repayPlanListObjectList.size() == 0) {
				throw new BusinessException(BusinessMsg.PARAMETER_LACK, "请求参数中传入的还款计划表信息repay_plan_list为空");
			}
			logger.info("**********【" + prodEstablishOrAbort.getOrder_no() + "】标的成立>>进入标的成立**********");

			//判断标的出账标识是否合规
			if (!ChargeOutType.AUTO.getCode().equals(prodInfo.getCharge_off_auto())) {
				throw new BusinessException(BusinessMsg.PARAMETER_ERROR, "只允许自动出账标的调用该接口");
			}
			//查询出标的账户
			List<AccountSubjectInfo> accountSubjectInfoList = accountQueryService.queryPlatAccountList(prodInfo.getPlat_no(), prodInfo.getProd_account(), null, Ssubject.PROD.getCode());
			logger.info("**********【" + prodEstablishOrAbort.getOrder_no() + "】标的成立>>查询出标的账户：**********" + JSON.toJSON(accountSubjectInfoList));
			if (accountSubjectInfoList == null || accountSubjectInfoList.size() == 0) {
				logger.error("**********【" + prodEstablishOrAbort.getOrder_no() + "】标的成立>>查询不到标的账户：**********" + JSON.toJSON(accountSubjectInfoList));
				throw new BusinessException(BusinessMsg.PARAMETER_ERROR, "查询不到标的账户");
			}
			//查询出融资人信息
			List<EaccFinancinfo> eaccFinancinfos = queryEaccFinancinfos(prodInfo.getPlat_no(), prodInfo.getProd_id());
			logger.info("**********【" + prodEstablishOrAbort.getOrder_no() + "】标的成立>>查询出融资人信息**********" + JSON.toJSON(eaccFinancinfos));
			if (eaccFinancinfos == null || eaccFinancinfos.size() == 0) {
				throw new BusinessException(BusinessMsg.PARAMETER_ERROR, String.format("根据平台编号【%s】和产品编号【%s】查询不到对应融资人信息", prodInfo.getPlat_no(), prodInfo.getProd_id()));
			}

			Map<String, Object> prodShareListParam = new HashMap<String, Object>(2);
			prodShareListParam.put("plat_no", prodEstablishOrAbort.getMer_no());
			prodShareListParam.put("prod_id", prodEstablishOrAbort.getProd_id());
			//先查询交易明细再存入份额
			List<ProdShareList> prodShareLists = custProdShareListMapper.queryProdShareSumList(prodShareListParam);
			logger.info("**********【" + prodEstablishOrAbort.getOrder_no() + "】标的成立>>查询出投资明细信息**********" + JSON.toJSON(prodShareLists));
			if (prodShareLists == null || prodShareLists.size() == 0) {
				logger.error("**********【" + prodEstablishOrAbort.getOrder_no() + "】标的成立>>查询不到投资明细信息**********");
				throw new BusinessException(BusinessMsg.PARAMETER_ERROR, String.format("根据平台编号【%s】、产品编号【%s】和份额明细状态【%s】查询不到产品明细信息", prodEstablishOrAbort.getMer_no(), prodEstablishOrAbort.getProd_id(), ShareStatus.WAIT.getCode()));
			}
			BigDecimal cashExtractSum = BigDecimal.ZERO;//标的现金账户余额
			BigDecimal floatExtractSum = BigDecimal.ZERO;//标的在途账户余额
			//自动出账
			logger.info("**********【" + prodEstablishOrAbort.getOrder_no() + "】标的成立>>开始自动出账**********");
			for (AccountSubjectInfo accountSubjectInfo : accountSubjectInfoList) {
				//标的现金账户
				if (Tsubject.CASH.getCode().equals(accountSubjectInfo.getSubject())) {
					cashExtractSum = cashExtractSum.add(accountSubjectInfo.getN_balance());
				}
				//标的在途账户
				if (Tsubject.FLOAT.getCode().equals(accountSubjectInfo.getSubject())) {
					floatExtractSum = floatExtractSum.add(accountSubjectInfo.getN_balance());
				}
			}
			BigDecimal prodNbalance = cashExtractSum.add(floatExtractSum);//标的可用余额总计
			BigDecimal financMoney = eaccFinancinfos.get(0).getFinanc_amt();//需要出账给融资人的钱
			logger.info("**********【" + prodEstablishOrAbort.getOrder_no() + "】标的成立>>转账前标的账户可用金额：**********" + JSON.toJSON(prodNbalance));
			//yanglei EaccUserinfo eplatcust = accountQueryService.getEaccUserinfoByExist(prodEstablishOrAbort.getMall_no(), eaccFinancinfos.get(0).getPlatcust());

			EaccAccountamtlist eaccAccountamtlist = new EaccAccountamtlist();
			eaccAccountamtlist = getEaccTransfer(prodInfo, transTransReq.getTrans_serial(), prodEstablishOrAbort, TransConsts.ESTAB_CODE, TransConsts.ESTAB_NAME);
			eaccAccountamtlist.setAmt_type(AmtType.EXPENSIVE.getCode());
			eaccAccountamtlist.setOrder_no(prodEstablishOrAbort.getOrder_no());
			eaccAccountamtlist.setSubject(Tsubject.CASH.getCode());
			eaccAccountamtlist.setSub_subject(Ssubject.PROD.getCode());
			eaccAccountamtlist.setOppo_platcust(eaccFinancinfos.get(0).getPlatcust());
			eaccAccountamtlist.setOppo_subject(Tsubject.CASH.getCode());
			//yanglei 出账直接转到融资人
//			if (eplatcust != null) {//存在电子账户
//				eaccAccountamtlist.setOppo_sub_subject(Ssubject.EACCOUNT.getCode());
//			} else {
//				eaccAccountamtlist.setOppo_sub_subject(Ssubject.FINANCING.getCode());
//			}
			eaccAccountamtlist.setOppo_sub_subject(Ssubject.FINANCING.getCode());
			eaccAccountamtlist.setAmt(prodNbalance);


			productoptionExtService.doEstablish(prodEstablishOrAbort, prodShareLists, repayPlanListObjectList, eaccAccountamtlist,prodInfo.getProd_type());
			//更新标的成立业务流水表
			transTransReq.setReturn_code(BusinessMsg.SUCCESS);
			transTransReq.setReturn_msg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));
			transTransReq.setStatus(FlowStatus.SUCCESS.getCode());
			transReqService.insert(transTransReq);

			orderData.setOrder_status(OrderStatus.SUCCESS.getCode());
			response.setRecode(BusinessMsg.SUCCESS);
			response.setRemsg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));
			logger.info("**********【" + prodEstablishOrAbort.getOrder_no() + "】标的成立>>更新标的成立业务流水表成功**********");
		} catch (Exception e) {
			logger.error("======【标的成立】异常", e);
			orderData.setOrder_status(OrderStatus.FAIL.getCode());
			baseResponse = new BaseResponse();
			if (e instanceof BusinessException) {
				baseResponse = ((BusinessException) e).getBaseResponse();
			} else {
				baseResponse.setRecode(BusinessMsg.DATEBASE_EXCEPTION);
				baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.DATEBASE_EXCEPTION));
			}
			transTransReq.setReturn_code(baseResponse.getRecode());
			transTransReq.setReturn_msg(baseResponse.getRemsg());
			transTransReq.setStatus(FlowStatus.FAIL.getCode());
			transReqService.insert(transTransReq);
			response.setRecode(baseResponse.getRecode());
			response.setRemsg(baseResponse.getRemsg());
		} finally {
			CacheUtil.unlock(lockKey);
		}
		orderData.setQuery_id(transTransReq.getTrans_serial());
		response.setOrderData(orderData);
		return response;
	}

	/**
	 * 查询融资信息
	 * @param plat_no 平台编号
	 * @param prod_id 标的编号
	 * @return
	 */
	public List<EaccFinancinfo> queryEaccFinancinfos(String plat_no,String prod_id){
		EaccFinancinfoExample example=new EaccFinancinfoExample();
		EaccFinancinfoExample.Criteria criteria=example.createCriteria();
		criteria.andProd_idEqualTo(prod_id);
		criteria.andPlat_noEqualTo(plat_no);
		criteria.andEnabledEqualTo(Constants.ENABLED);
		List<EaccFinancinfo> eaccFinancinfos = eaccFinancinfoMapper.selectByExample(example);
		return eaccFinancinfos;
	}

	/**
	 * 查询投资信息
	 * @param plat_no 平台编号
	 * @param prod_id 标的编号
	 * @return
	 */
	public List<ProdShareList> queryProdShareLists(String plat_no,String prod_id){
		ProdShareListExample example=new ProdShareListExample();
		ProdShareListExample.Criteria criteria=example.createCriteria();
		criteria.andProd_idEqualTo(prod_id);
		criteria.andPlat_noEqualTo(plat_no);
		criteria.andEnabledEqualTo(Constants.ENABLED);
		List<ProdShareList> prodShareLists = prodShareListMapper.selectByExample(example);
		return prodShareLists;
	}

	private void addShareInall(List<ProdShareList> prodShareLists){
		ProdShareInall prodShareInall = null;
		List<ProdShareInall> prodShareInalls = new ArrayList<ProdShareInall>();
		for(ProdShareList ps : prodShareLists){
			prodShareInall = new ProdShareInall();
			prodShareInall.setPlat_no(ps.getPlat_no());
			prodShareInall.setProd_id(ps.getProd_id());
			prodShareInall.setPlatcust(ps.getPlatcust());
			prodShareInall.setVol(ps.getVol());
			prodShareInall.setTot_vol(ps.getVol());
			prodShareInall.setEnabled(Constants.ENABLED);
			prodShareInall.setCreate_time(new Date());
			prodShareInall.setCreate_by(SeqUtil.RANDOM_KEY);
			prodShareInalls.add(prodShareInall);
		}
		custProdShareInallMapper.insertMore(prodShareInalls);
		prodShareInalls.clear();
	}

	private void addRepay(List<RepayPlanListObject> repayPlanListObjectList,String plat_no,String prod_id,String remark){
		List<ProdProdrepay> prodProdRepayList = new ArrayList<ProdProdrepay>();
		ProdProdrepay prodProdRepay = null;
		for(int i=0;i<repayPlanListObjectList.size();i++){
			prodProdRepay = new ProdProdrepay();
			prodProdRepay.setTrans_serial(SeqUtil.getSeqNum());
			prodProdRepay.setPlat_no(plat_no);
			prodProdRepay.setRepay_num(Integer.valueOf(repayPlanListObjectList.get(i).getRepay_num()));
			prodProdRepay.setProd_id(prod_id);
			prodProdRepay.setRepay_date(repayPlanListObjectList.get(i).getRepay_date());
			prodProdRepay.setRepay_amt(new BigDecimal(repayPlanListObjectList.get(i).getRepay_amt()));
			prodProdRepay.setRepay_fee(new BigDecimal(repayPlanListObjectList.get(i).getRepay_fee()));
			prodProdRepay.setEnabled(Constants.ENABLED);
			prodProdRepay.setRemark(remark);
			prodProdRepay.setCreate_time(new Date());
			prodProdRepayList.add(prodProdRepay);
		}
		custProdRepayMapper.insertMore(prodProdRepayList);
		prodProdRepayList.clear();
	}

	private EaccAccountamtlist getEaccTransfer(ProdProductinfo prodProductinfo,String trans_serial,BaseRequest prodEstablishOrAbort,String code,String name){
		EaccAccountamtlist eaccAccountamtlist = new EaccAccountamtlist();
		eaccAccountamtlist.setPlat_no(prodProductinfo.getPlat_no());
		eaccAccountamtlist.setOppo_plat_no(prodProductinfo.getPlat_no());
		eaccAccountamtlist.setPlatcust(prodProductinfo.getProd_account());
		eaccAccountamtlist.setTrans_serial(trans_serial);
		eaccAccountamtlist.setTrans_code(code);
		eaccAccountamtlist.setTrans_name(name);
		eaccAccountamtlist.setTrans_date(prodEstablishOrAbort.getPartner_trans_date());
		eaccAccountamtlist.setTrans_time(prodEstablishOrAbort.getPartner_trans_time());
		return eaccAccountamtlist;
	}


	/**
	 * 未转换
	 * @param platNo
	 * @param prod_account
	 * @param platcust
	 * @param amt
	 */
	public EaccAccountamtlist accTransferNoTrans(EaccAccountamtlist amtFlow,String trans_serial,String platNo,String prod_account,String platcust,BigDecimal amt,String code,String name,BaseRequest baseRequest){

		EaccAccountamtlist eaccAmtlist = new EaccAccountamtlist();
		eaccAmtlist.setPlat_no(platNo);
		eaccAmtlist.setTrans_serial(trans_serial);
		eaccAmtlist.setPlatcust(prod_account);
		eaccAmtlist.setSubject(Tsubject.FLOAT.getCode());
		eaccAmtlist.setTrans_code(code);
		eaccAmtlist.setTrans_name(name);
		eaccAmtlist.setTrans_date(baseRequest.getPartner_trans_date());
		eaccAmtlist.setTrans_time(baseRequest.getPartner_trans_time());
		eaccAmtlist.setSub_subject(Ssubject.PROD.getCode());
		eaccAmtlist.setOppo_plat_no(platNo);
		eaccAmtlist.setOppo_platcust(platcust);
		eaccAmtlist.setOppo_subject(Tsubject.FLOAT.getCode());
		eaccAmtlist.setOppo_sub_subject(amtFlow.getSub_subject());
		eaccAmtlist.setAmt(amt);
		return eaccAmtlist;
	}

	/**
	 * 完全转换
	 * @param platNo
	 * @param prod_account
	 * @param platcust
	 * @param amt
	 * @return
	 */
	public EaccAccountamtlist allAccTransfer(EaccAccountamtlist amtFlow,String trans_serial,String platNo,String prod_account,String platcust,BigDecimal amt,String code,String name,BaseRequest baseRequest){

		EaccAccountamtlist eaccAmtlist = new EaccAccountamtlist();
		eaccAmtlist.setPlat_no(platNo);
		eaccAmtlist.setTrans_serial(trans_serial);
		eaccAmtlist.setPlatcust(prod_account);
		eaccAmtlist.setTrans_code(code);
		eaccAmtlist.setTrans_name(name);
		eaccAmtlist.setTrans_date(baseRequest.getPartner_trans_date());
		eaccAmtlist.setTrans_time(baseRequest.getPartner_trans_time());
		eaccAmtlist.setSubject(Tsubject.CASH.getCode());
		eaccAmtlist.setSub_subject(Ssubject.PROD.getCode());
		eaccAmtlist.setOppo_plat_no(platNo);
		eaccAmtlist.setOppo_platcust(platcust);
		eaccAmtlist.setOppo_subject(Tsubject.CASH.getCode());
		eaccAmtlist.setOppo_sub_subject(amtFlow.getSub_subject());
		eaccAmtlist.setAmt(amt);
		return eaccAmtlist;
	}

	//成标转账
	public EaccAccountamtlist eaccAccountamtlistTransfer(AccountSubjectInfo accountSubjectInfo_oneself, AccountSubjectInfo accountSubjectInfo_rival,TransTransreq transTransReq, BigDecimal b) throws BusinessException{

		EaccAccountamtlist eaccAccountamtlist = new EaccAccountamtlist();

		//账户，科目，子科目
		eaccAccountamtlist.setPlat_no(accountSubjectInfo_oneself.getPlat_no());
		eaccAccountamtlist.setPlatcust(accountSubjectInfo_oneself.getPlatcust());
		eaccAccountamtlist.setSubject(accountSubjectInfo_oneself.getSubject());
		eaccAccountamtlist.setSub_subject(accountSubjectInfo_oneself.getSub_subject());

		//对手的账户，科目，子科目
		eaccAccountamtlist.setOppo_plat_no(accountSubjectInfo_rival.getPlat_no());
		eaccAccountamtlist.setOppo_platcust(accountSubjectInfo_rival.getPlatcust());
		eaccAccountamtlist.setOppo_subject(accountSubjectInfo_rival.getSubject());
		eaccAccountamtlist.setOppo_sub_subject(accountSubjectInfo_rival.getSub_subject());

		//交易流水号
		eaccAccountamtlist.setTrans_serial(transTransReq.getTrans_serial());

		//出账的交易代码，交易名称
		eaccAccountamtlist.setTrans_code(TransConsts.CHARGEOFF_CODE);
		eaccAccountamtlist.setTrans_name(TransConsts.CHARGEOFF_NAME);
		eaccAccountamtlist.setTrans_date(transTransReq.getTrans_date());
		eaccAccountamtlist.setTrans_time(transTransReq.getTrans_time());

		//转账金额
		eaccAccountamtlist.setAmt(b);

		return eaccAccountamtlist;
	}



	/**
	 * 插入/更新流水
	 * @param transTransReq
	 * @param baseRequest
	 * @param isBatch 是否批量，true是批量
	 * @param code 流水业务代码
	 * @param name 流水业务名称
	 */
	public void transSeq(TransTransreq transTransReq, BaseRequest baseRequest, Boolean isBatch, String code, String name){
		transTransReq = transReqService.getTransTransReq(baseRequest.clone(),code,name,isBatch);
		transReqService.insert(transTransReq);
	}

	/**
	 * 标的出账信息修改
	 * @author dingy
	 */
	@Transactional
	@Override
	public BaseResponse updateExpenditureInfo(BaseRequest baseRequest, EaccFinancinfo eaccFinancinfo) throws BusinessException {
		//记录业务流水
		TransTransreq transTransReq= transReqService.getTransTransReq(baseRequest.clone(),TransConsts.CHARGEOFF_UPDATE_CODE,TransConsts.CHARGEOFF_UPDATE_NAME,false);
        transReqService.insert(transTransReq);
        //判断参数Prod_id是否为空
        BaseResponse baseResponse=new BaseResponse();
        if(null==eaccFinancinfo.getProd_id()||("").equals(eaccFinancinfo.getProd_id().trim())){
            baseResponse.setRecode(BusinessMsg.PARAMETER_LACK);
            baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.PARAMETER_LACK));
            throw new BusinessException(baseResponse);
        }

        try {
			//判断有无该标
			prodSearchService.checkProdInfoIsFound(baseRequest.getMer_no(),eaccFinancinfo.getProd_id());
			//更新数据库
			EaccFinancinfoExample example=new EaccFinancinfoExample();
			EaccFinancinfoExample.Criteria criteria=example.createCriteria();
			criteria.andPlat_noEqualTo(baseRequest.getMer_no());
			criteria.andProd_idEqualTo(eaccFinancinfo.getProd_id());
			criteria.andEnabledEqualTo(Constants.ENABLED);
            Integer upreturn= eaccFinancinfoMapper.updateByExampleSelective(eaccFinancinfo, example);
            if(upreturn<1){
            	baseResponse.setRecode(BusinessMsg.INVALID_PRODUCT_ID);
                baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.INVALID_PRODUCT_ID));
                throw new BusinessException(baseResponse);
            	}

            baseResponse.setRecode(BusinessMsg.SUCCESS);
            baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));
            //更新业务流水为请求成功
            transTransReq.setStatus(FlowStatus.REQUESTSUCCESS.getCode());
            transTransReq.setReturn_code(BusinessMsg.SUCCESS);
            transTransReq.setReturn_msg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));
            transReqService.insert(transTransReq);
		} catch (BusinessException e){
			baseResponse.setRecode(e.getBaseResponse().getRecode());
            baseResponse.setRemsg(e.getBaseResponse().getRemsg());
            throw new BusinessException(baseResponse);
		}
		return baseResponse;
	}

	@Override
	public ProdAbortInvestmentResponse abortInvestment(ProdAbortInvestmentRequest baseRequest) {
		logger.info("**********【" + baseRequest.getOrder_no() + "】投资撤销>>开始...**********");
		String prodId = baseRequest.getProd_id();
		String platNo = baseRequest.getMer_no();
		String trans_order_no = baseRequest.getTrans_order_no();
		String lockKey = getLockKey(trans_order_no);
		//查询标的信息
		boolean lock = CacheUtil.getLock(lockKey);
		while (!lock) {
			logger.info("【获取订单锁失败】", "订单处理中，请勿重复提交");
			throw new BusinessException(BusinessMsg.RUNTIME_EXCEPTION, "订单处理中，请勿重复提交");
		}
		ProdAbortInvestmentResponse response = new ProdAbortInvestmentResponse();
		ProdAbortInvestment prodBusinessData=new ProdAbortInvestment();

		TransTransreq transTransReq = null;
		transTransReq = transReqService.getTransTransReq(baseRequest.clone(), TransConsts.INVEST_ABORT_CODE, TransConsts.INVEST_ABORT_NAME, false);
		String trans_serial = transTransReq.getTrans_serial();
		transReqService.insert(transTransReq);
		logger.info("******【" + baseRequest.getOrder_no() + "】投资撤销>>记录流水成功******");

		//根据原交易订单号查询对应的投资业务流水记录
		TransTransreq ttReq = transReqService.queryTransTransReqByOrderno(trans_order_no);
		logger.info("******【" + baseRequest.getTrans_order_no() + "】投资撤销>>根据原始订单号查出投资流水：******" + JSON.toJSON(ttReq));
		try {
			if ("3".equals(ttReq.getStatus())) {//3代表这笔订单号已经撤销
				logger.error("******【" + baseRequest.getOrder_no() + "】投资撤销>>根据投资流水可知该笔订单号已经被撤销******");
				throw new BusinessException(BusinessMsg.PARAMETER_ERROR, String.format("该投资订单【%s】已经撤销", trans_order_no));
			}
			ProdProductinfo prodProductInfo = prodSearchService.queryProdInfo(platNo, prodId);
			if (prodProductInfo == null) {//未查到标的信息
				logger.error("******【" + baseRequest.getOrder_no() + "】投资撤销>>未查询到标的信息******");
				throw new BusinessException(BusinessMsg.INVALID_PRODUCT_ID, BusinessMsg.getMsg(BusinessMsg.INVALID_PRODUCT_ID));
			}

			String prodState = prodProductInfo.getProd_state();
			if (prodState == null || "".equals(prodState)) {
				logger.error("******【" + baseRequest.getOrder_no() + "】投资撤销>>未查询到标的信息******");
				throw new BusinessException(BusinessMsg.PARAMETER_ERROR, String.format("查询出的标的信息中产品状态prodState【%s】属性不能为空，否则无法判断是否成立", prodState));
			}
			if (ProdState.FOUND.getCode().equals(prodState)) {
				logger.error("******【" + baseRequest.getOrder_no() + "】投资撤销>>标的信息是已成立标，不满足投资撤销条件******");
				throw new BusinessException(BusinessMsg.PARAMETER_ERROR, "该标的信息是已成立标，不满足投资撤销条件");
			}
			ProdShareListExample psExample = new ProdShareListExample();
			ProdShareListExample.Criteria psCriteria = psExample.createCriteria();
			psCriteria.andPlat_noEqualTo(prodProductInfo.getPlat_no());
			psCriteria.andTrans_serialEqualTo(ttReq.getTrans_serial());
			psCriteria.andProd_idEqualTo(prodProductInfo.getProd_id());
			psCriteria.andEnabledEqualTo(Constants.ENABLED);
			List<ProdShareList> prodShareLists = prodShareListMapper.selectByExample(psExample);
			logger.info("******【" + baseRequest.getOrder_no() + "】投资撤销>>查询出投资明细信息******" + JSON.toJSON(prodShareLists));

			//查询撤资人的份额明细
			if (prodShareLists == null || prodShareLists.size() == 0) {
				logger.error("******【" + baseRequest.getOrder_no() + "】投资撤销>>未查询到投资明细信息******");
				throw new BusinessException(BusinessMsg.PARAMETER_ERROR, String.format("根据流水编号【%s】查询不到投资明细信息", ttReq.getTrans_serial()));
			}

			Amtlist amtlist=new Amtlist();

			List<EaccAccountamtlist> list = new ArrayList<EaccAccountamtlist>();
			//根据Trans_serial、Plat_no、Platcust、Subject、Sub_subject查询现金交易流水
			EaccAccountamtlist allEaccAccountamtlist = new EaccAccountamtlist();
			allEaccAccountamtlist.setTrans_serial(ttReq.getTrans_serial());
			allEaccAccountamtlist.setAmt_type(AmtType.EXPENSIVE.getCode());
			List<EaccAccountamtlist> eaccAccountamtlists = accountQueryService.queryTransFlowList(allEaccAccountamtlist);
			logger.info("******【" + baseRequest.getOrder_no() + "】投资撤销>>查询出所有资金流水记录：******" + JSON.toJSON(eaccAccountamtlists));
			if ((null == eaccAccountamtlists || eaccAccountamtlists.size() <= 0)) {
				logger.error("******【" + baseRequest.getOrder_no() + "】投资撤销>>未查询到现金和在途资金流水记录******");
				throw new BusinessException(BusinessMsg.PARAMETER_ERROR, String.format("根据客户号【%s】、交易流水号【%s】未查询到资金流水记录", ttReq.getPlatcust(), ttReq.getTrans_serial()));
			}
			BigDecimal accountAmt = BigDecimal.ZERO;
			for (EaccAccountamtlist amtFlow : eaccAccountamtlists) {
				//撤销现金资金
				if (amtFlow.getAmt().compareTo(BigDecimal.ZERO) <= 0) continue;

				if (amtFlow.getPlatcust().equals(ttReq.getPlatcust())) {
					accountAmt = accountAmt.add(amtFlow.getAmt());
				}
				if (amtFlow.getSubject().equals(Tsubject.FLOAT.getCode())) {
					if (null == amtFlow.getSwitch_state()||"".equals(amtFlow.getSwitch_state())) {
						amtFlow.setSwitch_state("0");
					}
					if (SwitchState.PARTSWITCH.getCode().equals(amtFlow.getSwitch_state())) {
						//部分转换 已转换的是标的现金转投资人现金，部分未转换的是标的在途转投资人在途
						EaccAccountamtlist amtCashTrans = new EaccAccountamtlist();
						if (amtFlow.getAmt().compareTo(amtFlow.getSwitch_amt()) < 0) {
							throw new BusinessException(BusinessMsg.LIMIT_OUT, String.format("变动金额【%s】小于已转换金额【%s】，请确认！", amtFlow.getAmt(), amtFlow.getSwitch_amt()));
						}
						BigDecimal amt = amtFlow.getAmt().subtract(amtFlow.getSwitch_amt());//未转换金额

						copyEaccAccountAmtList(amtCashTrans,amtFlow);
						//已转换的
						amtCashTrans.setSubject(Tsubject.CASH.getCode());
						amtCashTrans.setAmt(amtFlow.getSwitch_amt());
						list.add(amtCashTrans);
						//未转（在途）

						EaccAccountamtlist amtFloatTrans = new EaccAccountamtlist();
						amtFloatTrans.setSubject(Tsubject.FLOAT.getCode());
						amtFloatTrans.setAmt(amt);
						amtFloatTrans.setSwitch_amt(BigDecimal.ZERO);
						amtFloatTrans.setSwitch_state(SwitchState.NOSWITCH.getCode());
						list.add(amtFloatTrans);

					} else if (SwitchState.ALLSWITCH.getCode().equals(amtFlow.getSwitch_state())) {
						//完全转换 标的现金转投资人现金
						amtFlow.setSubject(Tsubject.CASH.getCode());
						list.add(amtFlow);
					}else if (SwitchState.NOSWITCH.getCode().equals(amtFlow.getSwitch_state())){
						list.add(amtFlow);
					}
				}else{
					list.add(amtFlow);
				}
			}
			if (accountAmt.compareTo(BigDecimal.ZERO) > 0) {
				amtlist.setAmt(accountAmt);
				//amtlist.setAmttype(Tsubject.CASH.getCode());
				amtlist.setPlatcust(ttReq.getPlatcust());
			}

			List<EaccAccountamtlist> rollBackList = new ArrayList<EaccAccountamtlist>();
			logger.info("组装后原转账流水："+JSON.toJSON(list));
			BigDecimal vol = BigDecimal.ZERO;
			if (list.size() > 0) {
				for (EaccAccountamtlist org_eacc : list) {
					//计算还原份额
					vol=vol.add(org_eacc.getAmt());
					EaccAccountamtlist do_eacc = new EaccAccountamtlist();
					do_eacc.setPlat_no(org_eacc.getOppo_plat_no());
					do_eacc.setOppo_plat_no(org_eacc.getPlat_no());
					do_eacc.setTrans_serial(trans_serial);
					do_eacc.setTrans_code(TransConsts.INVEST_ABORT_CODE);
					do_eacc.setTrans_name(TransConsts.INVEST_ABORT_NAME);
					do_eacc.setTrans_date(baseRequest.getPartner_trans_date());
					do_eacc.setTrans_time(baseRequest.getPartner_trans_time());
					do_eacc.setAmt_type(AmtType.EXPENSIVE.getCode());
					do_eacc.setAmt(org_eacc.getAmt());
					String oppo_platcust=org_eacc.getOppo_platcust();
					String oppo_subject=org_eacc.getOppo_subject();
					String oppo_sub_subject=org_eacc.getOppo_sub_subject();
					String platcust=org_eacc.getPlatcust();
					String subject=org_eacc.getSubject();
					String sub_subject=org_eacc.getSub_subject();
					do_eacc.setPlatcust(oppo_platcust);
					do_eacc.setSubject(oppo_subject);
					do_eacc.setSub_subject(oppo_sub_subject);
					do_eacc.setOppo_subject(subject);
					do_eacc.setOppo_sub_subject(sub_subject);
					do_eacc.setOppo_platcust(platcust);
					do_eacc.setOrder_no(baseRequest.getOrder_no());
					do_eacc.setCreate_time(new Date());
					rollBackList.add(do_eacc);
				}
				logger.info("******【" + baseRequest.getOrder_no() + "】投资撤销>>撤销转账流水生成完成：******" + JSON.toJSON(rollBackList));
			}else{
				logger.error("******【" + baseRequest.getOrder_no() + "】投资撤销>>无可撤销流水******");
				throw new BusinessException(BusinessMsg.PARAMETER_ERROR, "无可撤销流水");
			}

			//添加份额明细
			ProdShareList prodShareList = new ProdShareList();
			prodShareList.setVol(vol);
			prodShareList.setTrans_serial(trans_serial);
			prodShareList.setTrans_code(TransConsts.INVEST_ABORT_CODE);
			prodShareList.setTrans_date(DateUtils.format(new Date(), DateUtils.DEF_FORMAT_NOTIME));
			prodShareList.setTrans_time(DateUtils.format(new Date(), DateUtils.DEF_FORMAT));
			prodShareList.setStatus("1");
			prodShareList.setAmt_type("1");
			prodShareList.setPlat_no(platNo);
			prodShareList.setProd_id(prodProductInfo.getProd_id());
			prodShareList.setPlatcust(ttReq.getPlatcust());
			prodShareList.setEnabled(Constants.ENABLED);

			productoptionExtService.doAbortInvestment(rollBackList,prodShareLists,prodShareList,prodProductInfo);
			ttReq.setStatus("3");//撤销完改变为3，标识该投资订单已经撤销了
			ttReq.setTrans_serial(null);
			transTransreqMapper.updateByPrimaryKeySelective(ttReq);
			logger.info("******【" + baseRequest.getOrder_no() + "】投资撤销>>更新原投资流水状态为投资订单已被撤销******");

			//更新业务流水表 trans_transreq
			transTransReq.setReturn_code(BusinessMsg.SUCCESS);
			transTransReq.setReturn_msg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));
			transTransReq.setStatus(FlowStatus.SUCCESS.getCode());
			transReqService.insert(transTransReq);

			prodBusinessData.setAmtlist(amtlist);
			prodBusinessData.setProcess_date(DateUtils.formatDateToStr(new Date()));
			prodBusinessData.setOrder_status(OrderStatus.SUCCESS.getCode());
			response.setRecode(BusinessMsg.SUCCESS);
			response.setRemsg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));
			response.setData_obj(prodBusinessData);
			logger.info("******【" + baseRequest.getOrder_no() + "】投资撤销>>更新该流水状态为成功******");
			return response;
		} catch (Exception e) {
			logger.error("【投资撤销】异常",e);
			if (e instanceof BusinessException) {
				BaseResponse baseResponse = ((BusinessException) e).getBaseResponse();
				transTransReq.setReturn_code(baseResponse.getRecode());
				transTransReq.setReturn_msg(baseResponse.getRemsg());
				transTransReq.setStatus(FlowStatus.FAIL.getCode());
				transReqService.insert(transTransReq);
				response.setRecode(baseResponse.getRecode());
				response.setRemsg(baseResponse.getRemsg());
				return response;
			} else {
				transTransReq.setReturn_code(BusinessMsg.DATEBASE_EXCEPTION);
				transTransReq.setReturn_msg(BusinessMsg.getMsg(BusinessMsg.DATEBASE_EXCEPTION));
				transTransReq.setStatus(FlowStatus.FAIL.getCode());
				transReqService.insert(transTransReq);
				response.setRecode(BusinessMsg.DATEBASE_EXCEPTION);
				response.setRemsg(BusinessMsg.getMsg(BusinessMsg.DATEBASE_EXCEPTION));
				return response;
			}
		} finally {
			CacheUtil.unlock(lockKey);
		}
	}

	private void copyEaccAccountAmtList(EaccAccountamtlist amtFloatTrans, EaccAccountamtlist amtFlow) {
		amtFloatTrans.setTrans_serial(amtFlow.getTrans_serial());
		amtFloatTrans.setPlat_no(amtFlow.getPlat_no());
		amtFloatTrans.setPlatcust(amtFlow.getPlatcust());
		amtFloatTrans.setSubject(amtFlow.getSubject());
		amtFloatTrans.setSub_subject(amtFlow.getSub_subject());
		amtFloatTrans.setAmt_type(amtFlow.getAmt_type());
		amtFloatTrans.setOppo_platcust(amtFlow.getOppo_platcust());
		amtFloatTrans.setOppo_subject(amtFlow.getOppo_subject());
		amtFloatTrans.setOppo_sub_subject(amtFlow.getOppo_sub_subject());
		amtFloatTrans.setAmt(amtFlow.getAmt());
		amtFloatTrans.setSwitch_state(amtFlow.getSwitch_state());
		amtFloatTrans.setSwitch_amt(amtFlow.getSwitch_amt());
		amtFloatTrans.setEnabled(amtFlow.getEnabled());
		amtFloatTrans.setOrder_no(amtFlow.getOrder_no());
	}

	@Override
	public ProdEstablishOrAbortResponse establishOrAbortOld(ProdEstablishOrAbortRequestOld prodEstablishOrAbort) {
		logger.info("**********【"+prodEstablishOrAbort.getOrder_no()+"】标的成废>>开始...**********");
		ProdEstablishOrAbortResponse response=new ProdEstablishOrAbortResponse();
		OrderData orderData=new OrderData();
		orderData.setProcess_date(DateUtils.formatDateToStr(new Date()));
		orderData.setOrder_no(prodEstablishOrAbort.getOrder_no());
		BaseResponse baseResponse = new BaseResponse();
		TransTransreq transTransReq = new TransTransreq();
		ProdProductinfo prodProductinfo = new ProdProductinfo();

		String lockKey = getLockKey(prodEstablishOrAbort.getMer_no() + prodEstablishOrAbort.getProd_id() + EstablishOrAbortFlag.ESTABLISH.getCode() + EstablishOrAbortFlag.ABORT.getCode());
		boolean lock = CacheUtil.getLock(lockKey);
		if (!lock) {
			logger.info("【获取订单锁失败】","订单处理中，请勿重复提交");
			throw new BusinessException(BusinessMsg.RUNTIME_EXCEPTION, "订单处理中，请勿重复提交");
		}
		//记录流水
		if(EstablishOrAbortFlag.ESTABLISH.getCode().equals(prodEstablishOrAbort.getFlag())) {
			//记录标的成立业务流水表
			transTransReq = transReqService.getTransTransReq(prodEstablishOrAbort.clone(), TransConsts.ESTAB_CODE, TransConsts.ESTAB_NAME, false);
			transReqService.insert(transTransReq);
			logger.info("**********【" + prodEstablishOrAbort.getOrder_no() + "】标的成立>>记录标的成立流水成功**********");
		}else if(EstablishOrAbortFlag.ABORT.getCode().equals(prodEstablishOrAbort.getFlag())) {
			//记录标的废除业务流水表
			transTransReq = transReqService.getTransTransReq(prodEstablishOrAbort.clone(), TransConsts.ABANDON_CODE, TransConsts.ABANDON_NAME, false);
			transReqService.insert(transTransReq);
			logger.info("**********【" + prodEstablishOrAbort.getOrder_no() + "】标的废除>>记录标的废除业务流水表成功**********");
		}

		//判断标的是否存在
		try{
			ProdProductinfo prodInfo = prodSearchService.queryProdInfo(prodEstablishOrAbort.getMer_no(),prodEstablishOrAbort.getProd_id());
			if(prodInfo==null){
				logger.error("**********【"+prodEstablishOrAbort.getOrder_no()+"】标的成废>>查询不到对应标信息**********");
				throw new BusinessException(BusinessMsg.PARAMETER_ERROR,String.format("根据平台编号【%s】和产品编号【%s】查询不到对应标信息", prodEstablishOrAbort.getMer_no(),prodEstablishOrAbort.getProd_id()));
			}
			if(!ProdState.PUBLISH.getCode().equals(prodInfo.getProd_state())){
				logger.error("**********【"+prodEstablishOrAbort.getOrder_no()+"】标的成废>>该标已成标或者废标**********");
				throw new BusinessException(BusinessMsg.PARAMETER_ERROR,String.format("根据平台编号【%s】和产品编号【%s】查询到对应标不是标的发布状态，不可成标或废标", prodEstablishOrAbort.getMer_no(),prodEstablishOrAbort.getProd_id()));
			}

			String prod_type = prodInfo.getProd_type();
			if(prod_type==null || "".equals(prod_type)){
				logger.error("**********【"+prodEstablishOrAbort.getOrder_no()+"】标的成废>>无法判断该标的为活期还是非活期**********");
				throw new BusinessException(BusinessMsg.PARAMETER_ERROR,String.format("查询出的标的信息中产品类型prod_type【%s】属性不能为空，否则无法判断是活期还是非活期",prod_type));
			}
			if("1".equals(prod_type)){
				logger.error("**********【"+prodEstablishOrAbort.getOrder_no()+"】标的成废>>该标是活期标，活期标不能成标**********");
				throw new BusinessException(BusinessMsg.PARAMETER_ERROR,"该标是活期标，活期标不能成标");
			}

			//判断成废标志是否合规
			if(!EstablishOrAbortFlag.ESTABLISH.getCode().equals(prodEstablishOrAbort.getFlag()) && !EstablishOrAbortFlag.ABORT.getCode().equals(prodEstablishOrAbort.getFlag())){
				throw new BusinessException(BusinessMsg.PARAMETER_ERROR,String.format("传入的成废标志flag【%s】参数不满足规则",prodEstablishOrAbort.getFlag()));
			}

			//标的成立
			if(EstablishOrAbortFlag.ESTABLISH.getCode().equals(prodEstablishOrAbort.getFlag())){
				logger.info("**********【"+prodEstablishOrAbort.getOrder_no()+"】标的成立>>进入标的成立**********");

				//判断标的出账标识是否合规
				if(!ChargeOutType.AUTO.getCode().equals(prodInfo.getCharge_off_auto()) && !ChargeOutType.MANUAL.getCode().equals(prodInfo.getCharge_off_auto())){
					throw new BusinessException(BusinessMsg.PARAMETER_ERROR,"标的信息出账标识为空，不满足规则，请确认！");
				}

				FunddataObject funddataObject = prodEstablishOrAbort.getFunddataObject();//分佣说明
				BigDecimal payoutAmt = BigDecimal.ZERO;
				if(funddataObject!=null){
					//验证现金在途转账标志参数是否合规
					if(!Tsubject.CASH.getCode().equals(funddataObject.getPayout_plat_type()) && !Tsubject.FLOAT.getCode().equals(funddataObject.getPayout_plat_type())){
						throw new BusinessException(BusinessMsg.PARAMETER_ERROR,String.format("传入的手续费现金在途标志payout_plat_type【%s】参数错误",funddataObject.getPayout_plat_type()));
					}
					payoutAmt = funddataObject.getPayout_amt();//手续费钱
				}

				Map<String,Object> prodShareListParam=new HashMap<String,Object>(2);
				prodShareListParam.put("plat_no",prodEstablishOrAbort.getMer_no());
				prodShareListParam.put("prod_id",prodEstablishOrAbort.getProd_id());
				//先查询交易明细再存入份额
				List<ProdShareList> prodShareLists = custProdShareListMapper.queryProdShareSumList(prodShareListParam);
				logger.info("**********【"+prodEstablishOrAbort.getOrder_no()+"】标的成立>>查询出投资明细信息**********"+JSON.toJSON(prodShareLists));
				if(prodShareLists==null||prodShareLists.size()==0){
					logger.error("**********【"+prodEstablishOrAbort.getOrder_no()+"】标的成立>>查询不到投资明细信息**********");
					throw new BusinessException(BusinessMsg.PARAMETER_ERROR,String.format("根据平台编号【%s】、产品编号【%s】和份额明细状态【%s】查询不到产品明细信息", prodEstablishOrAbort.getMer_no(),prodEstablishOrAbort.getProd_id(),ShareStatus.WAIT.getCode()));
				}
				//新增标的份额 prod_share_inall
		//		addShareInall(prodShareLists);
			//	logger.info("**********【"+prodEstablishOrAbort.getOrder_no()+"】标的成立>>新增标的份额成功**********");

				//新增还款计划 prod_prodRepay
				List<RepayPlanListObject> repayPlanListObjectList = prodEstablishOrAbort.getRepayPlanListObject();
				if(repayPlanListObjectList==null || repayPlanListObjectList.size()==0){
					throw new BusinessException(BusinessMsg.PARAMETER_LACK,"请求参数中传入的还款计划表信息repay_plan_list为空");
				}
		//	addRepay(repayPlanListObjectList,prodEstablishOrAbort.getMer_no(),prodEstablishOrAbort.getProd_id(),prodEstablishOrAbort.getRemark());
			//	logger.info("**********【"+prodEstablishOrAbort.getOrder_no()+"】标的成立>>新增标的还款计划成功**********");

				//更新标的信息为成立
				/*ProdProductinfoExample example=new ProdProductinfoExample();
				ProdProductinfoExample.Criteria criteria=example.createCriteria();
				criteria.andPlat_noEqualTo(prodEstablishOrAbort.getMer_no());
				criteria.andProd_idEqualTo(prodEstablishOrAbort.getProd_id());
				criteria.andEnabledEqualTo(Constants.ENABLED);
				prodProductinfo.setProd_state(ProdState.FOUND.getCode());
				prodProductinfo.setUpdate_time(new Date());
				prodProductinfo.setUpdate_by(SeqUtil.RANDOM_KEY);*/
		//		prodProductInfoMapper.updateByExampleSelective(prodProductinfo,example);
			//	logger.info("**********【"+prodEstablishOrAbort.getOrder_no()+"】标的成立>>更新标的信息为成立**********");

				List<EaccAccountamtlist> eaccAccountamtlistList = new ArrayList<EaccAccountamtlist>();
				EaccAccountamtlist eaccAccountamtlist = new EaccAccountamtlist();
				BigDecimal cashExtractSum = BigDecimal.ZERO;//标的现金账户余额
				BigDecimal floatExtractSum = BigDecimal.ZERO;//标的在途账户余额
				//标的账户
				List<AccountSubjectInfo> accountSubjectInfoList = accountQueryService.queryPlatAccountList(prodInfo.getPlat_no(),prodInfo.getProd_account(), null , Ssubject.PROD.getCode());
				logger.info("**********【"+prodEstablishOrAbort.getOrder_no()+"】标的成立>>查询出标的账户：**********"+JSON.toJSON(accountSubjectInfoList));
				if(accountSubjectInfoList==null || accountSubjectInfoList.size()==0){
					logger.error("**********【"+prodEstablishOrAbort.getOrder_no()+"】标的成立>>查询不到标的账户：**********"+JSON.toJSON(accountSubjectInfoList));
					throw new BusinessException(BusinessMsg.PARAMETER_ERROR,"查询不到标的账户");
				}
				for(AccountSubjectInfo accountSubjectInfo : accountSubjectInfoList){

					//标的现金账户
					if(Tsubject.CASH.getCode().equals(accountSubjectInfo.getSubject())){
						cashExtractSum = cashExtractSum.add(accountSubjectInfo.getN_balance());
					}

					//标的在途账户
					if(Tsubject.FLOAT.getCode().equals(accountSubjectInfo.getSubject())){
						floatExtractSum = floatExtractSum.add(accountSubjectInfo.getN_balance());
					}
				}
				BigDecimal prodNbalance = cashExtractSum.add(floatExtractSum);//标的可用余额总计
				logger.info("**********【"+prodEstablishOrAbort.getOrder_no()+"】标的成立>>转账前标的账户可用金额：**********"+JSON.toJSON(prodNbalance));
				List<SubdataObject> subdataObjects = prodEstablishOrAbort.getSubdataObjectList();//分账成标

				BigDecimal subAmtSum = BigDecimal.ZERO;//分账的钱总计
				if(subdataObjects!=null && subdataObjects.size()>0){
					for (SubdataObject subdataObject : subdataObjects){
						subAmtSum = subAmtSum.add(new BigDecimal(subdataObject.getAmt()));
					}
				}

				//标的可用余额总计<手续费钱+分账费
				if(prodNbalance.compareTo(payoutAmt.add(subAmtSum))<0){
					throw new BusinessException(BusinessMsg.SUM_NOTENOUGH,String.format("出账金额不足，标的可用余额【%s】小于手续费【%s】和分账钱【%s】",prodNbalance.toString(),payoutAmt.toString(),subAmtSum.toString()));
				}

				//标的转手续费
				if(funddataObject!=null){
					logger.info("**********【"+prodEstablishOrAbort.getOrder_no()+"】标的成立>>开始标的转手续费**********");
					AccountSubjectInfo accSubjectInfo =  accountQueryService.queryAccount(prodInfo.getPlat_no(),"",funddataObject.getPayout_plat_type(),Ssubject.FEE.getCode());
					if(accSubjectInfo==null){
						throw new BusinessException(BusinessMsg.INVALID_ACCOUNT,"查询不到手续费账户");
					}
					if(payoutAmt.compareTo(BigDecimal.ZERO)>0){
						eaccAccountamtlist = getEaccTransfer(prodInfo,transTransReq.getTrans_serial(),prodEstablishOrAbort,TransConsts.ESTAB_CODE,TransConsts.ESTAB_NAME);
						eaccAccountamtlist.setSubject(funddataObject.getPayout_plat_type());
						eaccAccountamtlist.setSub_subject(Ssubject.PROD.getCode());
						eaccAccountamtlist.setOppo_platcust(accSubjectInfo.getPlatcust());
						eaccAccountamtlist.setOppo_subject(funddataObject.getPayout_plat_type());
						eaccAccountamtlist.setOppo_sub_subject(Ssubject.FEE.getCode());
						eaccAccountamtlist.setAmt(payoutAmt);
						eaccAccountamtlistList.add(eaccAccountamtlist);
					}
					logger.info("**********【"+prodEstablishOrAbort.getOrder_no()+"】标的成立>>结束标的转手续费**********");
				}

				//标的转分账
				if(subdataObjects!=null && subdataObjects.size()>0){
					logger.info("**********【"+prodEstablishOrAbort.getOrder_no()+"】标的成立>>开始标的转分账**********");
					for (SubdataObject subdataObject : subdataObjects){
						//判断分账成标的分账用户是不是在委托/代偿名单中
						logger.info("**********【"+prodEstablishOrAbort.getOrder_no()+"】标的成立>>分账参数："+JSON.toJSON(subdataObject));
						/*ProdCompensationListExample prodCompensationListExample = new ProdCompensationListExample();
						ProdCompensationListExample.Criteria prodComListcriteria = prodCompensationListExample.createCriteria();
						prodComListcriteria.andPlat_noEqualTo(prodEstablishOrAbort.getMer_no());
						prodComListcriteria.andPlatcustEqualTo(subdataObject.getPlat_cust());
						prodComListcriteria.andEnabledEqualTo(Constants.ENABLED);
						List<ProdCompensationList> prodCompensationLists = prodCompensationListMapper.selectByExample(prodCompensationListExample);
						if(prodCompensationLists==null || prodCompensationLists.size()==0){
							throw new BusinessException(BusinessMsg.INVALID_ACCOUNT,"分账用户不在委托/代偿名单中");
						}*/
						//EaccUserinfo subplatcust=accountQueryService.getEaccUserinfoByExist(prodEstablishOrAbort.getMall_no(),subdataObject.getPlat_cust());
//						AccountSubjectInfo subplatcust = accountQueryService.queryFINANCINGPlatAccount(prodEstablishOrAbort.getMall_no(), subdataObject.getPlat_cust(), AccountType.ElectronicAccount.getCode(),
//								Tsubject.CASH.getCode(), Ssubject.EACCOUNT.getCode());
						BigDecimal sub_amt=subdataObject.getAmt()==null?BigDecimal.ZERO:new BigDecimal(subdataObject.getAmt());
						if(sub_amt.compareTo(BigDecimal.ZERO)>0){
							eaccAccountamtlist = getEaccTransfer(prodInfo,transTransReq.getTrans_serial(),prodEstablishOrAbort,TransConsts.ESTAB_CODE,TransConsts.ESTAB_NAME);
							eaccAccountamtlist.setSubject(Tsubject.CASH.getCode());
							eaccAccountamtlist.setSub_subject(Ssubject.PROD.getCode());
							eaccAccountamtlist.setOppo_platcust(subdataObject.getPlat_cust());
							eaccAccountamtlist.setOppo_subject(Tsubject.CASH.getCode());
							eaccAccountamtlist.setOppo_sub_subject(Ssubject.FINANCING.getCode());
							eaccAccountamtlist.setAmt(sub_amt);
							eaccAccountamtlistList.add(eaccAccountamtlist);
						}
					}
					logger.info("**********【"+prodEstablishOrAbort.getOrder_no()+"】标的成立>>结束标的转分账**********");
				}


				//标的转平台风险金分账
				BigDecimal platSubAmt = BigDecimal.ZERO;
				List<PlatSubData> platSubDataList =  prodEstablishOrAbort.getPlatSubDataObject();
				AccountSubjectInfo fxjAccountSubjectInfo = accountQueryService.queryAccount(prodEstablishOrAbort.getMer_no(),prodEstablishOrAbort.getMer_no(), Tsubject.CASH.getCode(),Ssubject.DEPOSIT.getCode());
				if(platSubDataList!=null && platSubDataList.size()>0){
					logger.info("打印请求参数:"+platSubDataList.toString());
					platSubAmt = platSubDataList.get(0).getAmt();
					if(platSubAmt.compareTo(BigDecimal.ZERO)>0){
						eaccAccountamtlist = getEaccTransfer(prodInfo,transTransReq.getTrans_serial(),prodEstablishOrAbort,TransConsts.ESTAB_CODE,TransConsts.ESTAB_NAME);
						eaccAccountamtlist.setSubject(Tsubject.CASH.getCode());
						eaccAccountamtlist.setSub_subject(Ssubject.PROD.getCode());
						eaccAccountamtlist.setOppo_platcust(fxjAccountSubjectInfo.getPlatcust());
						eaccAccountamtlist.setOppo_subject(Tsubject.CASH.getCode());
						eaccAccountamtlist.setOppo_sub_subject(fxjAccountSubjectInfo.getSub_subject());
						eaccAccountamtlist.setAmt(platSubAmt);
						eaccAccountamtlistList.add(eaccAccountamtlist);
						logger.info("金额："+platSubAmt+"打印组装参数:"+eaccAccountamtlist.toString());
					}
				}

				//自动出账
				if(ChargeOutType.AUTO.getCode().equals(prodInfo.getCharge_off_auto())){
					logger.info("**********【"+prodEstablishOrAbort.getOrder_no()+"】标的成立>>开始自动出账**********");
				/*=====================================自动出账开始出账=============================================*/
					//查询出融资人信息
					List<EaccFinancinfo> eaccFinancinfos = queryEaccFinancinfos(prodInfo.getPlat_no(),prodInfo.getProd_id());
					logger.info("**********【"+prodEstablishOrAbort.getOrder_no()+"】标的成立>>查询出融资人信息**********"+JSON.toJSON(eaccFinancinfos));
					if(eaccFinancinfos==null || eaccFinancinfos.size()==0){
						throw new BusinessException(BusinessMsg.PARAMETER_ERROR,String.format("根据平台编号【%s】和产品编号【%s】查询不到对应融资人信息", prodInfo.getPlat_no(),prodInfo.getProd_id()));
					}
					BigDecimal financMoney = eaccFinancinfos.get(0).getFinanc_amt();//需要出账给融资人的钱

					//标的转手续费和分账后的钱
					BigDecimal syProdSum = prodNbalance.subtract(payoutAmt).subtract(subAmtSum).subtract(platSubAmt);
					logger.info("**********【"+prodEstablishOrAbort.getOrder_no()+"】标的成立>>标的转在途和分账后的钱：**********"+JSON.toJSON(syProdSum));

					//EaccUserinfo eplatcust=accountQueryService.getEaccUserinfoByExist(prodEstablishOrAbort.getMall_no(),eaccFinancinfos.get(0).getPlatcust());

					//标的转融资人
					logger.info("**********【"+prodEstablishOrAbort.getOrder_no()+"】标的成立>>标的转融资人转账开始**********");
					eaccAccountamtlist = getEaccTransfer(prodInfo,transTransReq.getTrans_serial(),prodEstablishOrAbort,TransConsts.ESTAB_CODE,TransConsts.ESTAB_NAME);
					eaccAccountamtlist.setSubject(Tsubject.CASH.getCode());
					eaccAccountamtlist.setSub_subject(Ssubject.PROD.getCode());
					eaccAccountamtlist.setOppo_platcust(eaccFinancinfos.get(0).getPlatcust());
					eaccAccountamtlist.setOppo_subject(Tsubject.CASH.getCode());
					eaccAccountamtlist.setOppo_sub_subject(Ssubject.FINANCING.getCode());
					/*if (eplatcust != null) {//存在电子账户
						eaccAccountamtlist.setOppo_sub_subject(Ssubject.EACCOUNT.getCode());
					}else {
						eaccAccountamtlist.setOppo_sub_subject(Ssubject.FINANCING.getCode());
					}*/
					eaccAccountamtlist.setAmt(syProdSum);
					eaccAccountamtlistList.add(eaccAccountamtlist);
					logger.info("**********【"+prodEstablishOrAbort.getOrder_no()+"】标的成立>>标的转融资人转账结束**********");
					logger.info("**********【"+prodEstablishOrAbort.getOrder_no()+"】标的成立>>结束自动出账**********");
				}

				for (EaccAccountamtlist ea : eaccAccountamtlistList) {
					ea.setOrder_no(prodEstablishOrAbort.getOrder_no());
				}

				logger.info("**********【" + prodEstablishOrAbort.getOrder_no() + "】标的成立>>开始批量转账**********" + JSON.toJSON(eaccAccountamtlistList));
				productoptionExtService.doEstablish(prodEstablishOrAbort, prodShareLists, repayPlanListObjectList, eaccAccountamtlistList, prodInfo.getProd_type());
				//accountTransferService.batchTransfer(eaccAccountamtlistList);
				logger.info("**********【" + prodEstablishOrAbort.getOrder_no() + "】标的成立>>批量转账完成**********");

				//更新标的成立业务流水表
				transTransReq.setReturn_code(BusinessMsg.SUCCESS);
				transTransReq.setReturn_msg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));
				transTransReq.setStatus(FlowStatus.SUCCESS.getCode());
				transReqService.insert(transTransReq);
				orderData.setOrder_status(OrderStatus.SUCCESS.getCode());
				response.setRecode(BusinessMsg.SUCCESS);
				response.setRemsg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));
				logger.info("**********【"+prodEstablishOrAbort.getOrder_no()+"】标的成立>>更新标的成立业务流水表成功**********");
			}

			//标的废除
			if(EstablishOrAbortFlag.ABORT.getCode().equals(prodEstablishOrAbort.getFlag())){
				logger.info("**********【"+prodEstablishOrAbort.getOrder_no()+"】标的废除>>进入标的废除**********");
//				//记录标的废除业务流水表
//				transTransReq = transReqService.getTransTransReq(prodEstablishOrAbort.clone(),TransConsts.ABANDON_CODE,TransConsts.ABANDON_NAME,false);
//				transReqService.insert(transTransReq);
//				logger.info("**********【"+prodEstablishOrAbort.getOrder_no()+"】标的废除>>记录标的废除业务流水表成功**********");

				ProdShareListExample example=new ProdShareListExample();
				ProdShareListExample.Criteria criteria=example.createCriteria();
				criteria.andPlat_noEqualTo(prodEstablishOrAbort.getMer_no());
				criteria.andProd_idEqualTo(prodEstablishOrAbort.getProd_id());
				criteria.andEnabledEqualTo(Constants.ENABLED);
				criteria.andStatusEqualTo("1");
				List<ProdShareList> prodShareLists =  prodShareListMapper.selectByExample(example);//该标的所有投资人
				logger.info("**********【"+prodEstablishOrAbort.getOrder_no()+"】标的废除>>查询出该标的所有投资信息：**********"+JSON.toJSON(prodShareLists));

				if(prodShareLists!=null && prodShareLists.size()>0){
					List<EaccAccountamtlist> list = new ArrayList<EaccAccountamtlist>();
					List<ProdShareList> prodShareListAddList = new ArrayList<ProdShareList>();
					BigDecimal limit = BigDecimal.ZERO;
					ProdProductinfoExample prexample=new ProdProductinfoExample();
					for(ProdShareList prodShareList : prodShareLists){
						logger.info("**********【"+prodEstablishOrAbort.getOrder_no()+"】标的废除>>进行标的对该投资的废除：**********"+JSON.toJSON(prodShareList));
						//根据Trans_serial、Plat_no、Platcust、Subject、Sub_subject查询现金交易流水
						EaccAccountamtlist cashEaccAccountamtlist = new EaccAccountamtlist();
						cashEaccAccountamtlist.setTrans_serial(prodShareList.getTrans_serial());
						cashEaccAccountamtlist.setPlatcust(prodShareList.getPlatcust());
						cashEaccAccountamtlist.setSubject(Tsubject.CASH.getCode());
						logger.info("**********【"+prodEstablishOrAbort.getOrder_no()+"】标的废除>>开始查询现金资金流水记录：**********"+JSON.toJSON(cashEaccAccountamtlist));
						List<EaccAccountamtlist> cashEaccAccountamtlists = accountTransferService.queryTransFlowList(cashEaccAccountamtlist);
						logger.info("**********【"+prodEstablishOrAbort.getOrder_no()+"】标的废除>>查询出现金资金流水记录：**********"+JSON.toJSON(cashEaccAccountamtlists));
						BigDecimal cashAmt=BigDecimal.ZERO;
						BigDecimal floatAmt=BigDecimal.ZERO;
						if(cashEaccAccountamtlists!=null && cashEaccAccountamtlists.size()>0 ){
							for(EaccAccountamtlist amtFlow:cashEaccAccountamtlists){
								if(amtFlow.getAmt().compareTo(BigDecimal.ZERO)>0){
									limit = limit.add(amtFlow.getAmt());
									cashAmt=cashAmt.add(amtFlow.getAmt());
									//标的现金转投资人现金
//									EaccAccountamtlist eaccAmtlist = allAccTransfer(amtFlow,prodShareList.getTrans_serial(),prodInfo.getPlat_no(),prodInfo.getProd_account(),prodShareList.getPlatcust(),amtFlow.getAmt(),TransConsts.INVEST_ABORT_CODE,TransConsts.INVEST_ABORT_NAME,prodEstablishOrAbort);;
//									list.add(eaccAmtlist);
									amtFlow.setTrans_serial(transTransReq.getTrans_serial());
									amtFlow.setTrans_code(TransConsts.ABANDON_CODE);
									amtFlow.setTrans_name(TransConsts.ABANDON_NAME);
									amtFlow.setTrans_date(prodEstablishOrAbort.getPartner_trans_date());
									amtFlow.setTrans_time(prodEstablishOrAbort.getPartner_trans_time());
									list.add(amtFlow);
								}
							}
						}

						//根据Trans_serial、Plat_no、Platcust、Subject、Sub_subject查询在途交易流水
						EaccAccountamtlist floatEaccAccountlist = new EaccAccountamtlist();
						floatEaccAccountlist.setTrans_serial(prodShareList.getTrans_serial());
						floatEaccAccountlist.setPlatcust(prodShareList.getPlatcust());
						floatEaccAccountlist.setSubject(Tsubject.FLOAT.getCode());
						logger.info("**********【"+prodEstablishOrAbort.getOrder_no()+"】标的废除>>开始查询在途资金流水记录：**********"+JSON.toJSON(floatEaccAccountlist));
						List<EaccAccountamtlist> floatEaccAccountlists = accountTransferService.queryTransFlowList(floatEaccAccountlist);
						logger.info("**********【"+prodEstablishOrAbort.getOrder_no()+"】标的废除>>查询出在途资金流水记录：**********"+JSON.toJSON(floatEaccAccountlists));
						if(floatEaccAccountlists!=null && floatEaccAccountlists.size()>0){
							for(EaccAccountamtlist amtFlow:floatEaccAccountlists){
								if(amtFlow.getAmt().compareTo(BigDecimal.ZERO)>0){
									limit = limit.add(amtFlow.getAmt());
									floatAmt=floatAmt.add(amtFlow.getAmt());
									if(amtFlow.getSwitch_state()==null)
										amtFlow.setSwitch_state("0");
									//未转换
									if(SwitchState.NOSWITCH.getCode().equals(amtFlow.getSwitch_state())){
//										EaccAccountamtlist eaccAmtlist = accTransferNoTrans(amtFlow,transTransReq.getTrans_serial(),prodInfo.getPlat_no(),prodInfo.getProd_account(),prodShareList.getPlatcust(),amtFlow.getAmt(),TransConsts.INVEST_ABORT_CODE,TransConsts.INVEST_ABORT_NAME,prodEstablishOrAbort);
//										list.add(eaccAmtlist);
										amtFlow.setTrans_serial(transTransReq.getTrans_serial());
										amtFlow.setTrans_code(TransConsts.ABANDON_CODE);
										amtFlow.setTrans_name(TransConsts.ABANDON_NAME);
										amtFlow.setTrans_date(prodEstablishOrAbort.getPartner_trans_date());
										amtFlow.setTrans_time(prodEstablishOrAbort.getPartner_trans_time());
										list.add(amtFlow);
									}else if(SwitchState.PARTSWITCH.getCode().equals(amtFlow.getSwitch_state())){
										//部分转换 已转换的是标的现金转投资人现金，部分未转换的是标的在途转投资人在途
										EaccAccountamtlist amtFlowCopy = new EaccAccountamtlist();
										BeanUtils.copyProperties(amtFlow, amtFlowCopy);
										BigDecimal amt = BigDecimal.ZERO;
										if(amtFlow.getAmt().compareTo(amtFlow.getSwitch_amt())<0){
											baseResponse = new BaseResponse();
											baseResponse.setRecode(BusinessMsg.LIMIT_OUT);
											String remsg = String.format("=======【标的废除】======变动金额【%s】小于已转换金额【%s】，请确认！",amtFlow.getAmt(),amtFlow.getSwitch_amt());
											baseResponse.setRemsg(remsg);
											throw new BusinessException(baseResponse);
										}
										amt = amtFlow.getAmt().subtract(amtFlow.getSwitch_amt());//未转换金额
										//部分未转换的
//										EaccAccountamtlist noEaccAmtlist = accTransferNoTrans(amtFlow,transTransReq.getTrans_serial(),prodInfo.getPlat_no(),prodInfo.getProd_account(),prodShareList.getPlatcust(),amt,TransConsts.INVEST_ABORT_CODE,TransConsts.INVEST_ABORT_NAME,prodEstablishOrAbort);
//										list.add(noEaccAmtlist);
										amtFlow.setTrans_serial(transTransReq.getTrans_serial());
										amtFlow.setSubject(Tsubject.FLOAT.getCode());
										amtFlow.setOppo_subject(Tsubject.FLOAT.getCode());
										amtFlow.setTrans_code(TransConsts.ABANDON_CODE);
										amtFlow.setTrans_name(TransConsts.ABANDON_NAME);
										amtFlow.setTrans_date(prodEstablishOrAbort.getPartner_trans_date());
										amtFlow.setTrans_time(prodEstablishOrAbort.getPartner_trans_time());
										amtFlow.setAmt(amt);
										amtFlow.setSwitch_amt(BigDecimal.ZERO);
										amtFlow.setSwitch_state(SwitchState.NOSWITCH.getCode());
										list.add(amtFlow);

										//已转换的
//										EaccAccountamtlist alredyEaccamt = allAccTransfer(amtFlow,transTransReq.getTrans_serial(),prodInfo.getPlat_no(),prodInfo.getProd_account(),prodShareList.getPlatcust(),amtFlow.getSwitch_amt(),TransConsts.INVEST_ABORT_CODE,TransConsts.INVEST_ABORT_NAME,prodEstablishOrAbort);
//										list.add(alredyEaccamt);
										amtFlowCopy.setTrans_serial(transTransReq.getTrans_serial());
										amtFlowCopy.setSubject(Tsubject.CASH.getCode());
										amtFlowCopy.setOppo_subject(Tsubject.CASH.getCode());
										amtFlowCopy.setTrans_code(TransConsts.ABANDON_CODE);
										amtFlowCopy.setTrans_name(TransConsts.ABANDON_NAME);
										amtFlowCopy.setTrans_date(prodEstablishOrAbort.getPartner_trans_date());
										amtFlowCopy.setTrans_time(prodEstablishOrAbort.getPartner_trans_time());
										amtFlowCopy.setAmt(amtFlow.getSwitch_amt());
										list.add(amtFlowCopy);
									}else{
										//完全转换 标的现金转投资人现金
//										EaccAccountamtlist eaccamt = allAccTransfer(amtFlow,transTransReq.getTrans_serial(),prodInfo.getPlat_no(),prodInfo.getProd_account(),prodShareList.getPlatcust(),amtFlow.getSwitch_amt(),TransConsts.INVEST_ABORT_CODE,TransConsts.INVEST_ABORT_NAME,prodEstablishOrAbort);
//										list.add(eaccamt);
										amtFlow.setTrans_serial(transTransReq.getTrans_serial());
										amtFlow.setSubject(Tsubject.CASH.getCode());
										amtFlow.setOppo_subject(Tsubject.CASH.getCode());
										amtFlow.setTrans_code(TransConsts.ABANDON_CODE);
										amtFlow.setTrans_name(TransConsts.ABANDON_NAME);
										amtFlow.setTrans_date(prodEstablishOrAbort.getPartner_trans_date());
										amtFlow.setTrans_time(prodEstablishOrAbort.getPartner_trans_time());
										amtFlow.setAmt(amtFlow.getSwitch_amt());
										list.add(amtFlow);
									}
								}
							}
						}
						if((cashEaccAccountamtlists==null || cashEaccAccountamtlists.size()<=0) &&
								(floatEaccAccountlists==null || floatEaccAccountlists.size()<=0)){
							logger.error("**********【"+prodEstablishOrAbort.getOrder_no()+"】标的废除>>未查询到现金和在途资金流水记录：**********");
							baseResponse.setRecode(BusinessMsg.PARAMETER_LACK);
							String errorMsg = String.format("======【标的废除】=======根据客户号【%s】、交易流水号【%s】未查询到资金流水记录",
									prodShareList.getPlatcust(),prodShareList.getTrans_serial());
							baseResponse.setRemsg(errorMsg);
							throw new BusinessException(baseResponse);
						}
						//添加份额明细
						ProdShareList prodShareListAdd = new ProdShareList();
						BigDecimal vol = BigDecimal.ZERO;
						if(cashEaccAccountamtlists!=null && cashEaccAccountamtlists.size()>0){
							vol = vol.add(cashAmt);
							prodShareListAdd.setVol(vol);
						}else if(floatEaccAccountlists!=null && floatEaccAccountlists.size()>0){
							vol = vol.add(floatAmt);
							prodShareListAdd.setVol(vol);
						}
						prodShareListAdd.setTrans_serial(transTransReq.getTrans_serial());
						prodShareListAdd.setTrans_code(TransConsts.ABANDON_CODE);
						prodShareListAdd.setTrans_date(DateUtils.format(new Date(),DateUtils.DEF_FORMAT_NOTIME));
						prodShareListAdd.setTrans_time(DateUtils.format(new Date(),DateUtils.DEF_FORMAT));
						prodShareListAdd.setStatus("1");
						prodShareListAdd.setAmt_type("1");
						prodShareListAdd.setPlat_no(prodInfo.getPlat_no());
						prodShareListAdd.setProd_id(prodInfo.getProd_id());
						prodShareListAdd.setPlatcust(prodShareList.getPlatcust());
						prodShareListAdd.setEnabled(Constants.ENABLED);
						prodShareListAdd.setCreate_by(SeqUtil.RANDOM_KEY);
						prodShareListAdd.setCreate_time(new Date());
						prodShareListAddList.add(prodShareListAdd);
					}
					Map<String,Object> map = new HashMap<String,Object>();
					map.put("vol",limit);
					map.put("prod_id",prodInfo.getProd_id());
					map.put("plat_no",prodInfo.getPlat_no());
					map.put("update_time",new Date());
					map.put("update_by",SeqUtil.RANDOM_KEY);
					try{
						if(list.size()>0){
							logger.info("**********【"+prodEstablishOrAbort.getOrder_no()+"】标的废除>>开始转账：**********"+JSON.toJSON(list));
							for(EaccAccountamtlist ea : list){
								ea.setOrder_no(prodEstablishOrAbort.getOrder_no());
							}
							accountTransferService.batchTransferRollBack(list);
							logger.info("**********【"+prodEstablishOrAbort.getOrder_no()+"】标的废除>>转账成功**********");
						}

						custProdShareListMapper.insertMore(prodShareListAddList);
						logger.info("**********【"+prodEstablishOrAbort.getOrder_no()+"】标的废除>>插入投资明细数据成功**********");

						custProdProductinfoMapper.updateProd(map);
						logger.info("**********【"+prodEstablishOrAbort.getOrder_no()+"】标的废除>>更新标的份额成功**********");
					}catch (BusinessException b){
						throw new BusinessException(b.getBaseResponse());
					}
				}

				//更新标的信息为作废 prod_productInfo
				ProdProductinfoExample pexample=new ProdProductinfoExample();
				ProdProductinfoExample.Criteria pcriteria=pexample.createCriteria();
				pcriteria.andPlat_noEqualTo(prodEstablishOrAbort.getMer_no());
				pcriteria.andProd_idEqualTo(prodEstablishOrAbort.getProd_id());
				pcriteria.andEnabledEqualTo(Constants.ENABLED);
				prodProductinfo.setUpdate_time(new Date());
				prodProductinfo.setUpdate_by(SeqUtil.RANDOM_KEY);
				prodProductinfo.setProd_state(ProdState.LOSE.getCode());
				prodProductInfoMapper.updateByExampleSelective(prodProductinfo,pexample);
				logger.info("**********【"+prodEstablishOrAbort.getOrder_no()+"】标的废除>>更新标的信息为作废成功**********");

				//更新业务流水表 trans_transreq
				transTransReq.setReturn_code(BusinessMsg.SUCCESS);
				transTransReq.setReturn_msg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));
				transTransReq.setStatus(FlowStatus.SUCCESS.getCode());
				transReqService.insert(transTransReq);
				orderData.setOrder_status(OrderStatus.SUCCESS.getCode());
				response.setRecode(BusinessMsg.SUCCESS);
				response.setRemsg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));
				logger.info("**********【"+prodEstablishOrAbort.getOrder_no()+"】标的废除>>更新业务流水表成功**********");
			}
		}catch (Exception e){
			logger.error("error:",e);
			orderData.setOrder_status(OrderStatus.FAIL.getCode());
			baseResponse=new BaseResponse();
			if(e instanceof BusinessException){
				baseResponse = ((BusinessException) e).getBaseResponse();
			}else{
				baseResponse.setRecode(BusinessMsg.DATEBASE_EXCEPTION);
				baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.DATEBASE_EXCEPTION));
			}
			transTransReq.setReturn_code(baseResponse.getRecode());
			transTransReq.setReturn_msg(baseResponse.getRemsg());
			transTransReq.setStatus(FlowStatus.FAIL.getCode());
			transReqService.insert(transTransReq);
			response.setRecode(baseResponse.getRecode());
			response.setRemsg(baseResponse.getRemsg());
		}finally {
			CacheUtil.unlock(lockKey);
		}
		orderData.setQuery_id(transTransReq.getTrans_serial());
		response.setOrderData(orderData);
		return response;
	}

	@Override
	public ProdTruncationResponse endProd(ProdTruncationResquest prodTruncationResquest) throws BusinessException {
		logger.info("【结标】-->开始dubbo-->order_no:"+prodTruncationResquest.getOrder_no());

		ProdTruncationResponse prodTruncationResponse = new ProdTruncationResponse();

		TransTransreq transTransReq= transReqService.getTransTransReq(prodTruncationResquest.clone(),TransConsts.CHARGEOFF_UPDATE_CODE,TransConsts.CHARGEOFF_UPDATE_NAME,false);
		transReqService.insert(transTransReq);
		logger.info("【结标】-->插入订单成功-->order_no:"+prodTruncationResquest.getOrder_no());

		try{
			ProdProductinfo prodProductinfo = prodSearchService.queryProdInfo(prodTruncationResquest.getMer_no(),prodTruncationResquest.getProd_id());
			if(prodProductinfo==null){
				logger.error("【结标】-->order_no:"+prodTruncationResquest.getOrder_no());
				throw new BusinessException(BusinessMsg.PARAMETER_ERROR,String.format("根据平台编号【%s】和产品编号【%s】查询不到对应标信息", prodTruncationResquest.getMer_no(),prodTruncationResquest.getProd_id()));
			}
			if(ProdState.OVER.getCode().equals(prodProductinfo.getProd_state())){
				logger.error("【结标】-->order_no:"+prodTruncationResquest.getOrder_no());
				throw new BusinessException(BusinessMsg.PARAMETER_ERROR,String.format("根据平台编号【%s】和产品编号【%s】查询到对应标是结标状态，不可再次结标", prodTruncationResquest.getMer_no(),prodTruncationResquest.getProd_id()));
			}
			ProdShareInallExample prodShareInallExample = new ProdShareInallExample();
			ProdShareInallExample.Criteria prodShareInallcriteria = prodShareInallExample.createCriteria();
			prodShareInallcriteria.andPlat_noEqualTo(prodTruncationResquest.getMer_no());
			prodShareInallcriteria.andProd_idEqualTo(prodTruncationResquest.getProd_id());
			prodShareInallcriteria.andEnabledEqualTo(Constants.ENABLED);
			List<ProdShareInall> prodShareInalls = prodShareInallMapper.selectByExample(prodShareInallExample);
			BigDecimal remain_vol = BigDecimal.ZERO;
			for (ProdShareInall prodShareInall : prodShareInalls) {
				remain_vol = remain_vol.add(prodShareInall.getTot_vol());
			}
			//更新标的信息为完成状态（截标状态）
			ProdProductinfoExample pexample=new ProdProductinfoExample();
			ProdProductinfoExample.Criteria pcriteria=pexample.createCriteria();
			pcriteria.andPlat_noEqualTo(prodTruncationResquest.getMer_no());
			pcriteria.andProd_idEqualTo(prodTruncationResquest.getProd_id());
			pcriteria.andEnabledEqualTo(Constants.ENABLED);
			prodProductinfo.setUpdate_time(new Date());
			prodProductinfo.setUpdate_by(SeqUtil.RANDOM_KEY);
			prodProductinfo.setProd_state(ProdState.OVER.getCode());
			prodProductInfoMapper.updateByExampleSelective(prodProductinfo,pexample);
			logger.info("【结标】-->标的废除>>更新标的信息为完成状态成功-->order_no:"+prodTruncationResquest.getOrder_no());

			transTransReq.setStatus(OrderStatus.SUCCESS.getCode());
			transTransReq.setReturn_code(BusinessMsg.SUCCESS);
			transTransReq.setReturn_msg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));
			transReqService.insert(transTransReq);
			logger.info("【结标】-->修改订单状态成功-->order_no:"+prodTruncationResquest.getOrder_no());

			prodTruncationResponse.setProd_state(ProdState.OVER.getCode());
			prodTruncationResponse.setIs_finished(IsFinished.YES.getCode());
			if(remain_vol.compareTo(BigDecimal.ZERO) > 0)
				prodTruncationResponse.setIs_finished(IsFinished.NO.getCode());
			prodTruncationResponse.setRemain_vol(remain_vol);

			prodTruncationResponse.setOrder_status(OrderStatus.SUCCESS.getCode());
			prodTruncationResponse.setRecode(BusinessMsg.SUCCESS);
			prodTruncationResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));

		}catch (Exception e){
			logger.info("【结标】-->异常-->order_no:"+prodTruncationResquest.getOrder_no(),e);
			BaseResponse baseResponse = new BaseResponse();
			if (e instanceof BusinessException) {
				baseResponse = ((BusinessException) e).getBaseResponse();
			} else {
				baseResponse.setRecode(BusinessMsg.UNKNOW_ERROE);
				baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.UNKNOW_ERROE));
			}

			transTransReq.setStatus(OrderStatus.FAIL.getCode());
			transTransReq.setReturn_code(baseResponse.getRecode());
			transTransReq.setReturn_msg(baseResponse.getRemsg());
			transReqService.insert(transTransReq);
			logger.info("【结标】-->异常-->修改订单状态为失败成功-->order_no:"+prodTruncationResquest.getOrder_no());

			prodTruncationResponse.setOrder_status(OrderStatus.FAIL.getCode());
			prodTruncationResponse.setRecode(baseResponse.getRecode());
			prodTruncationResponse.setRemsg(baseResponse.getRemsg());
		}
		return prodTruncationResponse;
	}

	public EaccUserinfo queryEaccUserInfoByMallNoAndPlatcust(String mall_no, String platcust) throws BusinessException{

		logger.info("【查询用户信息】-->集团号:"+ mall_no+"-->集团客户号:"+platcust);
		EaccUserinfoExample example=new EaccUserinfoExample();
		EaccUserinfoExample.Criteria criteria=example.createCriteria();
		if(StringUtils.isBlank(mall_no) && StringUtils.isBlank(platcust))
			throw new BusinessException(BusinessMsg.PARAMETER_LACK,"缺少mall_no或者platcust");
		criteria.andMall_noEqualTo(mall_no);
		criteria.andMallcustEqualTo(platcust);
		criteria.andEnabledEqualTo(Constants.ENABLED);
		List<EaccUserinfo> eaccUserInfos= eaccUserinfoMapper.selectByExample(example);
		if(eaccUserInfos.size() > 1){
			throw new BusinessException(BusinessMsg.getMsg(BusinessMsg.DATEBASE_EXCEPTION) + ",请检查用户信息，用户信息出现重复数据 \n"
					+ "mall_no：" + mall_no + " \n mallcust" +platcust);
		}else if(eaccUserInfos.size() < 1){
			return null;
		}
		return eaccUserInfos.get(0);
	}


}
