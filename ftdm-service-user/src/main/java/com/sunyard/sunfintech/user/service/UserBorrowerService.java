package com.sunyard.sunfintech.user.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sunyard.sunfintech.account.provider.IAccountQueryService;
import com.sunyard.sunfintech.account.provider.IAccountTransferService;
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
import com.sunyard.sunfintech.custdao.mapper.CustEaccUserinfoMapper;
import com.sunyard.sunfintech.dao.entity.*;
import com.sunyard.sunfintech.dao.mapper.*;
import com.sunyard.sunfintech.pub.provider.IAuthCheckService;
import com.sunyard.sunfintech.pub.provider.ISysParameterService;
import com.sunyard.sunfintech.pub.provider.ITransReqService;
import com.sunyard.sunfintech.thirdparty.model.*;
import com.sunyard.sunfintech.thirdparty.provider.IAdapterService;
import com.sunyard.sunfintech.user.model.bo.*;
import com.sunyard.sunfintech.user.model.bo.PlatSubData;
import com.sunyard.sunfintech.user.model.bo.SubdataObject;
import com.sunyard.sunfintech.user.modelold.bo.*;
import com.sunyard.sunfintech.user.provider.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

/**
 * @author Zz
 * 
 * 2017年5月15日
 */
@Service(interfaceClass = IUserBorrowerService.class)
@CacheConfig(cacheNames="userBorrowerService")
@org.springframework.stereotype.Service("userBorrowerService")
public class UserBorrowerService extends BaseServiceSimple implements IUserBorrowerService {

	@Autowired
    private IAccountTransferService accountTransferService;

    @Autowired
    private IAccountQueryService accountQueryService;

    @Autowired
    private ITransReqService transReqService;

	@Autowired
	private IProdRepayMQOptionService prodRepayMQOptionService;

    @Autowired
    private ProdBorrowerRealrepayMapper prodBorrowerRealrepayMapper;
    
    @Autowired
    private ProdCompensationInfoMapper prodCompensationInfoMapper;

	@Autowired
	private IProdCompensationInfoService productRefundService;
    @Autowired
    private IProdSearchService prodSearchService;

	@Autowired
	private ProdCompensationRepayMapper prodCompensationRepayMapper;

	@Autowired
	private IBatchRepayExtService batchRepayExtService;

	@Autowired
	private TransTransreqMapper transTransreqMapper;

	@Autowired
	private IAccountTransferService newAccountTransferExecuteService;

	@Autowired
	private IAuthCheckService authCheckService;

	@Autowired
	private ISysParameterService sysParameterService;

	@Autowired
	private IAdapterService adapterService;

	@Autowired
	private IAccountSearchService accountSearchService;

	@Autowired
	private IUserAccountService iUserAccountService;

	@Autowired
	private CustEaccUserinfoMapper custEaccUserinfoMapper;

	@Autowired
	private AccountSearchService accountSearchServicel;


//	@Autowired
//	private EaccFinancinfoMapper eaccFinancinfoMapper;

	public boolean batchRepayAsyn(BatchRepayAsynRequest batchRepayRequest)throws BusinessException{

		logger.info("【借款人还款申请】-->开始进入dubbo-->order_no:"+batchRepayRequest.getOrder_no());

		TransTransreq transTransReq = transReqService.getTransTransReq(batchRepayRequest.clone(), TransConsts.BORROWREPAY_CODE, TransConsts.BORROWREPAY_NAME, true);
		transTransReq.setOrder_no(batchRepayRequest.getOrder_no());
		transReqService.insert(transTransReq);
		logger.info("【借款人还款申请】-->添加批次流水成功-->order_no:"+batchRepayRequest.getOrder_no());

		try{
			for(BatchRepayRequestDetail detail : batchRepayRequest.getBatchRepayRequestDetailList()){

				validate(detail);

				TransTransreq singleTransTransReq = transReqService.getTransTransReq(batchRepayRequest.clone(), TransConsts.BORROWREPAY_CODE, TransConsts.BORROWREPAY_NAME, true);
				singleTransTransReq.setPlatcust(detail.getPlatcust());
				singleTransTransReq.setOrder_no(detail.getDetail_no());
				transReqService.insert(singleTransTransReq);
				logger.info("【借款人还款申请】-->添加明细流水成功-->order_no:"+detail.getDetail_no());

				try{
					BatchRepayAsynMQEntity batchRepayAsynMQEntity = new BatchRepayAsynMQEntity();
					batchRepayAsynMQEntity.setBaseRequest(batchRepayRequest.clone());
					batchRepayAsynMQEntity.setBatchRepayRequestDetail(detail);
					batchRepayAsynMQEntity.setNotify(batchRepayRequest.getNotify_url());
					prodRepayMQOptionService.addBatchRepayAsyn(batchRepayAsynMQEntity);
					logger.info("【借款人还款申请】-->丢入mq成功-->order_no:"+detail.getDetail_no());

				}catch (BusinessException e){
					logger.error("【借款人还款申请】-->丢入mq异常-->order_no:"+detail.getDetail_no(),e);
				}
			}

			transTransReq.setReturn_code(BusinessMsg.SUCCESS);
			transTransReq.setReturn_msg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));
			transTransReq.setStatus(FlowStatus.SUCCESS.getCode());
			transReqService.insert(transTransReq);
			logger.info("【借款人还款申请】-->修改批次流水成功-->order_no:"+batchRepayRequest.getOrder_no());

		}catch (Exception e){

			logger.error("【借款人还款申请】-->异常-->order_no:"+batchRepayRequest.getOrder_no(),e);

			BaseResponse baseResponse=new BaseResponse();
			if(e instanceof BusinessException){
				baseResponse=((BusinessException) e).getBaseResponse();
			}else{
				baseResponse.setRecode(BusinessMsg.DATEBASE_EXCEPTION);
				baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.DATEBASE_EXCEPTION));
			}
			//更新批量流水
			transTransReq.setReturn_code(baseResponse.getRecode());
			transTransReq.setReturn_msg(baseResponse.getRemsg());
			transTransReq.setStatus(FlowStatus.FAIL.getCode());
			transReqService.insert(transTransReq);

			throw new BusinessException(baseResponse.getRecode(),baseResponse.getRemsg());

		}
		return true;
	}
    
   /*
    *   pzy
    *  借款人批量还款  同步老接口
    */
	@Override
	public BatchRepayResponse batchRepayOld(BatchRepayRequestOld batchRepayRequest) throws BusinessException {

    	logger.info("开始借款人批量还款");

    	//记录业务流水
		TransTransreq transTransReq = transReqService.getTransTransReq(batchRepayRequest.clone(),TransConsts.BORROWREPAY_CODE,TransConsts.BORROWREPAY_NAME, true);
		transTransReq.setOrder_no(batchRepayRequest.getOrder_no());
		transReqService.insert(transTransReq);

		List<BatchRepaySuccessData> successList = new ArrayList<BatchRepaySuccessData>();
		List<BatchRepayErrorData> errorList = new ArrayList<BatchRepayErrorData>();

		for(BatchRepayRequestDetailOld detail : batchRepayRequest.getBatchRepayRequestDetailList()){

			try{
				BatchRepaySuccessData batchRepaySuccessData = batchRepayExtService.onePatchRepay(batchRepayRequest,detail);
				successList.add(batchRepaySuccessData);
			}catch (BusinessException e){
				BatchRepayErrorData batchRepayErrorData=new BatchRepayErrorData();
				batchRepayErrorData.setDetail_no(detail.getDetail_no());
				batchRepayErrorData.setError_info(e.getBaseResponse().getRemsg());
				batchRepayErrorData.setError_no(e.getBaseResponse().getRecode());
				errorList.add(batchRepayErrorData);
			}
		}

		//流水更新
        transTransReq.setReturn_code(BusinessMsg.SUCCESS);
        transTransReq.setReturn_msg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));
        transTransReq.setStatus(FlowStatus.SUCCESS.getCode());
        transReqService.insert(transTransReq);

		BatchRepayResponse batchRepayResponse = new BatchRepayResponse();
		batchRepayResponse.setSuccessDataList(successList);
		batchRepayResponse.setErrorDataList(errorList);
		batchRepayResponse.setRecode(BusinessMsg.SUCCESS);
		batchRepayResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));
		batchRepayResponse.setOrder_no(batchRepayRequest.getOrder_no());
		batchRepayResponse.setPlat_no(batchRepayRequest.getMer_no());
		batchRepayResponse.setFinish_datetime(DateUtils.todayStr(DateUtils.DEF_FORMAT_STRING));
		if(successList.size() > 0){
			batchRepayResponse.setOrder_status(OrderStatus.SUCCESS.getCode());
			batchRepayResponse.setOrder_info(OrderStatus.SUCCESS.getMsg());
		}else{
			batchRepayResponse.setOrder_status(OrderStatus.FAIL.getCode());
			batchRepayResponse.setOrder_info(OrderStatus.FAIL.getMsg());
		}
		batchRepayResponse.setTotal_num(batchRepayRequest.getBatchRepayRequestDetailList().size());
		batchRepayResponse.setSuccess_num(successList.size());

        return batchRepayResponse;

	}

	@Override
	public boolean batchRepayAsynOld(BatchRepayAsynRequestOld batchRepayRequest) throws BusinessException {
		logger.info("【借款人批量还款old】-->开始借款人批量还款-->order_no:"+batchRepayRequest.getOrder_no());

		TransTransreq transTransReq = transReqService.getTransTransReq(batchRepayRequest.clone(), TransConsts.BORROWREPAY_CODE, TransConsts.BORROWREPAY_NAME, true);
		transTransReq.setOrder_no(batchRepayRequest.getOrder_no());
		transReqService.insert(transTransReq);
		logger.info("【借款人批量还款old】-->添加批次号流水成功-->order_no:"+batchRepayRequest.getOrder_no());

		try{
			for(BatchRepayRequestDetailOld detail : batchRepayRequest.getBatchRepayRequestDetailList()){
				BatchRepayRequestDetailAsyn batchRepayRequestDetailAsyn = new BatchRepayRequestDetailAsyn();
				TransTransreq singleTransTransReq = transReqService.getTransTransReq(batchRepayRequest.clone(), TransConsts.BORROWREPAY_CODE, TransConsts.BORROWREPAY_NAME, true);
				singleTransTransReq.setOrder_no(detail.getDetail_no());
				singleTransTransReq.setPlatcust(detail.getPlatcust());
				transReqService.insert(singleTransTransReq);
				logger.info("【借款人批量还款old】-->添加明细流水成功-->order_no:"+detail.getDetail_no());
				try{
					//BeanUtils.copyProperties(detail, batchRepayRequestDetailAsyn);
					batchRepayRequestDetailAsyn.setDetail_no(detail.getDetail_no());
					batchRepayRequestDetailAsyn.setProd_id(detail.getProd_id());
					batchRepayRequestDetailAsyn.setRepay_num(detail.getRepay_num());
					batchRepayRequestDetailAsyn.setRepay_date(detail.getRepay_date());
					batchRepayRequestDetailAsyn.setRepay_amt(detail.getRepay_amt());
					batchRepayRequestDetailAsyn.setReal_repay_date(detail.getReal_repay_date());
					batchRepayRequestDetailAsyn.setReal_repay_amt(detail.getReal_repay_amt());
					batchRepayRequestDetailAsyn.setPlatcust(detail.getPlatcust());
					batchRepayRequestDetailAsyn.setTrans_amt(detail.getTrans_amt());
					batchRepayRequestDetailAsyn.setFee_amt(detail.getFee_amt());
					batchRepayRequestDetailAsyn.setRemark(detail.getRemark());
					batchRepayRequestDetailAsyn.setSubdata(detail.getSubdata());
					batchRepayRequestDetailAsyn.setSubdataObjectList(detail.getSubdataObjectList());
					batchRepayRequestDetailAsyn.setPlat_subdata(detail.getPlat_subdata());
					batchRepayRequestDetailAsyn.setPlatSubDataObject(detail.getPlatSubDataObject());
					prodRepayMQOptionService.addBorrow(batchRepayRequest.clone(), batchRepayRequestDetailAsyn, batchRepayRequest.getNotify_url());
					logger.info("【借款人批量还款old】-->丢入mq成功-->order_no:"+detail.getDetail_no());
				}catch (Exception e){
					logger.info("【借款人批量还款old】-->丢入mq异常-->order_no:"+detail.getDetail_no(),e);
				}
			}
			transTransReq.setReturn_code(BusinessMsg.SUCCESS);
			transTransReq.setReturn_msg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));
			transTransReq.setStatus(FlowStatus.SUCCESS.getCode());
			transReqService.insert(transTransReq);
			logger.info("【借款人批量还款old】-->修改批次号流水成功-->order_no:"+batchRepayRequest.getOrder_no());
		}catch (Exception e){
			logger.info("【借款人批量还款old】-->异常-->order_no:"+batchRepayRequest.getOrder_no(),e);
			BaseResponse baseResponse=new BaseResponse();
			if(e instanceof BusinessException){
				baseResponse=((BusinessException) e).getBaseResponse();
			}else{
				baseResponse.setRecode(BusinessMsg.RUNTIME_EXCEPTION);
				baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.RUNTIME_EXCEPTION));
			}
			//更新批量流水
			transTransReq.setReturn_code(baseResponse.getRecode());
			transTransReq.setReturn_msg(baseResponse.getRemsg());
			transTransReq.setStatus(FlowStatus.FAIL.getCode());
			transReqService.insert(transTransReq);
		}
		return false;
	}

	/*
	 * 代偿还款
	 * pzy
	 */
	@Override
	@Transactional
	public SubstituteRepayResponse substituteRepay(SubstituteRepayRequest substituteRepayRequest) throws BusinessException {

		SubstituteRepayResponse substituteRepayResponse = new SubstituteRepayResponse();

		logger.info("【代偿还款】-->进入dubbo-->order_no:"+substituteRepayRequest.getOrder_no());

		//记录业务流水表
		TransTransreq transTransReq = transReqService.getTransTransReq(substituteRepayRequest.clone(),TransConsts.PRODCOMPENSATORYREPAY_CODE,TransConsts.PRODCOMPENSATORYREPAY_NAME, false);
		transTransReq.setPlatcust(substituteRepayRequest.getPlatcust());
		transReqService.insert(transTransReq);
		logger.info("【代偿还款】-->添加流水成功-->order_no:"+substituteRepayRequest.getOrder_no());

    	try{

			logger.info("【代偿还款】-->根据交易密码和交易密码随机数判断是否检查授权数据-->order_no:"+substituteRepayRequest.getOrder_no());
			if(StringUtils.isBlank(substituteRepayRequest.getTrans_pwd()) && StringUtils.isBlank(substituteRepayRequest.getRandom_key())) {
				boolean isSucc = authCheckService.checkAuth(substituteRepayRequest.getMer_no(), substituteRepayRequest.getMall_no(), substituteRepayRequest.getCompensation_platcust(), AuthType.AUTH_REFUND.getCode(), substituteRepayRequest.getTrans_amt());
				logger.info("【代偿还款】-->代偿还款检查是否授权-->订单号：" + substituteRepayRequest.getOrder_no() + ",结果：" + isSucc);
				if (!isSucc) {
					throw new BusinessException(BusinessMsg.AUTH_ERROR,
							String.format(BusinessMsg.AUTH_ERROR + "|授权数据失败|platcust:%s|auth_type:%s", substituteRepayRequest.getCompensation_platcust(), AuthType.AUTH_REFUND.getCode()));
				}
			}

			ProdProductinfo prodProductInfo = prodSearchService.queryProdInfo(substituteRepayRequest.getMer_no(), substituteRepayRequest.getProd_id());
			if(prodProductInfo == null){
				logger.info("【代偿还款】-->标不存在-->prod_id"+substituteRepayRequest.getProd_id()+"-->order_no:"+substituteRepayRequest.getOrder_no());
				throw new BusinessException(BusinessMsg.INVALID_PRODUCT_ID,BusinessMsg.getMsg(BusinessMsg.INVALID_PRODUCT_ID)  + "-------------标不存在" + substituteRepayRequest.getProd_id());
			}
			logger.info("【代偿还款】-->标的存在-->order_no:"+substituteRepayRequest.getOrder_no());

			AccountSubjectInfo prodAccountSubjectInfo = accountQueryService.queryAccount(prodProductInfo.getPlat_no(),prodProductInfo.getProd_account(),Tsubject.CASH.getCode(),Ssubject.PROD.getCode());
			if(prodAccountSubjectInfo == null){
				logger.info("【代偿还款】-->标的账户不存在-->prod_account:"+prodProductInfo.getProd_account()+"-->order_no:"+substituteRepayRequest.getOrder_no());
				throw new BusinessException(BusinessMsg.PARAMETER_ERROR,BusinessMsg.getMsg(BusinessMsg.PARAMETER_ERROR) + ",标的账户不存在");
			}
			logger.info("【代偿还款】-->标的账户存在-->order_no:"+substituteRepayRequest.getOrder_no());

			//检查标的融资信息
			List<EaccFinancinfo> listEaccFinancinfo = prodSearchService.queryEaccFinancinfo(prodProductInfo.getPlat_no(),prodProductInfo.getProd_id(),substituteRepayRequest.getPlatcust());
			if(listEaccFinancinfo == null || listEaccFinancinfo.size() == 0){
				logger.info("【代偿还款】-->请求账户与融资人提取账户信息不一致-->platcust:"+substituteRepayRequest.getPlatcust());
				throw new BusinessException(BusinessMsg.ACCOUNT_INFO_DIFF,BusinessMsg.getMsg(BusinessMsg.ACCOUNT_INFO_DIFF));
			}
			logger.info("【代偿还款】-->请求账户与融资人提取账户信息一致-->order_no:"+substituteRepayRequest.getOrder_no());

			//检查借款人账户是否存在
			AccountSubjectInfo custAccountSubjectInfo = accountQueryService.queryAccount(prodProductInfo.getPlat_no(), substituteRepayRequest.getPlatcust(), Tsubject.CASH.getCode(),Ssubject.FINANCING.getCode());
			if(custAccountSubjectInfo == null){
				logger.info( "【代偿还款】-->借款人账户不存在-->platcust:"+substituteRepayRequest.getPlatcust());
				throw new BusinessException(BusinessMsg.INVALID_ACCOUNT,BusinessMsg.getMsg(BusinessMsg.INVALID_ACCOUNT) + ",借款人账户不存在");
			}
			logger.info( "【代偿还款】-->借款人账户存在-->order_no:"+substituteRepayRequest.getOrder_no());

			AccountSubjectInfo compensationPlatcustAccountSubjectInfo = null;
			if(!RepayType.COMPENSATORY.getCode().equals(substituteRepayRequest.getRepay_type())
					&& !RepayType.ENTRUST.getCode().equals(substituteRepayRequest.getRepay_type())){
				logger.info( "【代偿还款】-->没有该还款类型-->order_no:"+substituteRepayRequest.getOrder_no());
				throw new BusinessException(BusinessMsg.PARAMETER_ERROR,BusinessMsg.getMsg(BusinessMsg.PARAMETER_ERROR) + "没有改还款类型");
			}
			logger.info( "【代偿还款】-->还款类型正确-->order_no:"+substituteRepayRequest.getOrder_no());

			List<ProdCompensationList> prodCompensationLists = prodSearchService.queryProdCompensationList(prodProductInfo.getPlat_no(),prodProductInfo.getProd_id(),substituteRepayRequest.getCompensation_platcust());
			if(null == prodCompensationLists || prodCompensationLists.size() == 0){
				logger.info( "【代偿还款】-->代偿账户没有报备-->order_no:"+substituteRepayRequest.getOrder_no());
				throw new BusinessException(BusinessMsg.PARAMETER_ERROR,BusinessMsg.getMsg(BusinessMsg.PARAMETER_ERROR) + ",代偿账户没有报备");
			}
			logger.info( "【代偿还款】-->代偿账户报备成功-->order_no:"+substituteRepayRequest.getOrder_no());

			//检查代偿人账户是否存在
			compensationPlatcustAccountSubjectInfo = accountQueryService.queryAccount(prodProductInfo.getPlat_no(), substituteRepayRequest.getCompensation_platcust(), Tsubject.CASH.getCode(),Ssubject.FINANCING.getCode());
			if(compensationPlatcustAccountSubjectInfo == null){
				logger.info( "【代偿还款】-->代偿人账户不存在-->compensation_platcust:"+substituteRepayRequest.getCompensation_platcust()+"-->order_no:"+substituteRepayRequest.getOrder_no());
				throw new BusinessException(BusinessMsg.ACCOUNT_INFO_DIFF,BusinessMsg.getMsg(BusinessMsg.ACCOUNT_INFO_DIFF) + ",代偿人账户不存在");
			}
			logger.info( "【代偿还款】-->代偿人账户存在-->order_no:"+substituteRepayRequest.getOrder_no());

			//记录标的代偿还款表
			ProdCompensationInfo prodCompensationInfo = new ProdCompensationInfo();
			prodCompensationInfo.setTrans_serial(transTransReq.getTrans_serial());
			prodCompensationInfo.setPlat_no(prodProductInfo.getPlat_no());
			prodCompensationInfo.setProd_id(prodProductInfo.getProd_id());
			prodCompensationInfo.setCompensation_platcust(substituteRepayRequest.getCompensation_platcust());
			prodCompensationInfo.setRepay_num(substituteRepayRequest.getRepay_num());
			prodCompensationInfo.setRepay_date(substituteRepayRequest.getRepay_date());
			prodCompensationInfo.setRepay_amt(substituteRepayRequest.getRepay_amt());
			prodCompensationInfo.setReal_repay_date(substituteRepayRequest.getReal_repay_date());
			prodCompensationInfo.setReal_repay_amt(substituteRepayRequest.getReal_repay_amt());
			prodCompensationInfo.setRepay_type(substituteRepayRequest.getRepay_type());
			prodCompensationInfo.setOrder_no(substituteRepayRequest.getOrder_no());
			prodCompensationInfo.setEnabled(Constants.ENABLED);
			prodCompensationInfo.setCreate_time(new Date());
			prodCompensationInfo.setUpdate_time(new Date());
			//prodCompensationInfoMapper.insert(prodCompensationInfo);
			productRefundService.addCompensationInfo(prodCompensationInfo);
			logger.info( "【代偿还款】-->代偿还款添加成功-->order_no:"+substituteRepayRequest.getOrder_no());

			//转账      先转实际金额到标的账户
			List<EaccAccountamtlist> eaccAccountamtlists = new ArrayList<EaccAccountamtlist>();
			EaccAccountamtlist eaccAccountamtlist = new EaccAccountamtlist();
			eaccAccountamtlist.setOrder_no(transTransReq.getOrder_no());
			eaccAccountamtlist.setTrans_serial(transTransReq.getTrans_serial());
			eaccAccountamtlist.setPlat_no(prodProductInfo.getPlat_no());
			eaccAccountamtlist.setPlatcust(compensationPlatcustAccountSubjectInfo.getPlatcust());
			eaccAccountamtlist.setSubject(compensationPlatcustAccountSubjectInfo.getSubject());
			eaccAccountamtlist.setSub_subject(compensationPlatcustAccountSubjectInfo.getSub_subject());
			eaccAccountamtlist.setTrans_code(TransConsts.PRODCOMPENSATORYREPAY_CODE);
			eaccAccountamtlist.setTrans_name(TransConsts.PRODCOMPENSATORYREPAY_NAME);
			eaccAccountamtlist.setAmt_type(AmtType.EXPENSIVE.getCode());
			eaccAccountamtlist.setOppo_plat_no(prodAccountSubjectInfo.getPlat_no());
			eaccAccountamtlist.setOppo_platcust(prodAccountSubjectInfo.getPlatcust());
			eaccAccountamtlist.setOppo_subject(prodAccountSubjectInfo.getSubject());
			eaccAccountamtlist.setOppo_sub_subject(prodAccountSubjectInfo.getSub_subject());
			eaccAccountamtlist.setAmt(substituteRepayRequest.getReal_repay_amt());
			eaccAccountamtlist.setRemark("代偿还款，转实际金额到标的账户");
			eaccAccountamtlist.setTrans_date(transTransReq.getTrans_date());
			eaccAccountamtlist.setTrans_time(transTransReq.getTrans_time());
			eaccAccountamtlists.add(eaccAccountamtlist);
			newAccountTransferExecuteService.batchTransfer(eaccAccountamtlists);
			logger.info( "【代偿还款】-->转账成功-->order_no:"+substituteRepayRequest.getOrder_no());

			transTransReq.setReturn_code(BusinessMsg.SUCCESS);
			transTransReq.setReturn_msg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));
			transTransReq.setStatus(FlowStatus.SUCCESS.getCode());
			transReqService.insert(transTransReq);
			logger.info( "【代偿还款】-->修改流水成功->order_no:"+substituteRepayRequest.getOrder_no());

			substituteRepayResponse.setRecode(BusinessMsg.SUCCESS);
			substituteRepayResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));

			OrderData orderData = new OrderData();
			orderData.setOrder_no(substituteRepayRequest.getOrder_no());
			orderData.setOrder_status(OrderStatus.SUCCESS.getCode());
			orderData.setProcess_date(DateUtils.todayStr(DateUtils.DEF_FORMAT));
			orderData.setQuery_id(transTransReq.getTrans_serial());
			substituteRepayResponse.setData(JSON.toJSONString(orderData, GlobalConfig.serializerFeature));

		}catch (Exception e){
			logger.error("【代偿还款】-->异常-->order_no:"+transTransReq.getOrder_no(), e);

			BaseResponse baseResponse = new BaseResponse();
			if (e instanceof BusinessException) {
				baseResponse = ((BusinessException) e).getBaseResponse();

				//更新流水
				transTransReq.setReturn_code(baseResponse.getRecode());
				transTransReq.setReturn_msg(baseResponse.getRemsg());
				transTransReq.setStatus(FlowStatus.FAIL.getCode());
				transReqService.insert(transTransReq);

				throw new BusinessException(baseResponse);

			} else {
				baseResponse.setRecode(BusinessMsg.DUBBO_TIMEOUT);
				baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.DUBBO_TIMEOUT));

				substituteRepayResponse.setRecode(BusinessMsg.DUBBO_TIMEOUT);
				substituteRepayResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.DUBBO_TIMEOUT));

				OrderData orderData = new OrderData();
				orderData.setOrder_no(substituteRepayRequest.getOrder_no());
				orderData.setOrder_status(OrderStatus.PROCESSING.getCode());
				orderData.setProcess_date(DateUtils.todayStr(DateUtils.DEF_FORMAT));
				orderData.setQuery_id(transTransReq.getTrans_serial());
				substituteRepayResponse.setOrderData(orderData);

			}
		}

		return substituteRepayResponse;
	}


	/*
	 * 标的代偿（委托）还款   老接口
	 * pzy
	 */
	@Override
	public SubstituteRepayResponse substituteRepayOld(BaseRequest baserequest, SubstituteRepayRequestOld substituteRepayRequest) throws BusinessException {

		logger.info("【标的代偿（委托）还款】,进入dubbo,order_no:"+substituteRepayRequest.getOrder_no());

		//记录业务流水表
		TransTransreq transTransReq = transReqService.getTransTransReq(baserequest.clone(),TransConsts.PRODCOMPENSATORYREPAY_CODE,TransConsts.PRODCOMPENSATORYREPAY_NAME, false);
		transTransReq.setPlatcust(substituteRepayRequest.getPlatcust());
		transReqService.insert(transTransReq);
		SubstituteRepayResponse substituteRepayResponse = new SubstituteRepayResponse();

		try{
			//效验传过来的参数金额是否一致    交易金额 = 实际还款金额 + 手续费金额
			if(substituteRepayRequest.getFee_amt() == null)
				substituteRepayRequest.setFee_amt(BigDecimal.ZERO);

			if(substituteRepayRequest.getReal_repay_amt().add(substituteRepayRequest.getFee_amt()).compareTo(substituteRepayRequest.getTrans_amt()) != 0){
				logger.info( "【标的代偿（委托）还款】交易金额 = 实际还款金额 + 手续费金额");
				throw new BusinessException(BusinessMsg.MONEY_ERROR,BusinessMsg.getMsg(BusinessMsg.MONEY_ERROR) + "，交易金额 = 实际还款金额 + 手续费金额");
			}

			//检查标是否存在
			ProdProductinfo prodProductInfo = prodSearchService.queryProdInfo(baserequest.getMer_no(), substituteRepayRequest.getProd_id());
			if(prodProductInfo == null){
				logger.info("【标的代偿（委托）还款】标不存在");
				throw new BusinessException(BusinessMsg.INVALID_PRODUCT_ID,BusinessMsg.getMsg(BusinessMsg.INVALID_PRODUCT_ID)  + "，标不存在" + substituteRepayRequest.getProd_id());
			}

			//查询标的账户
			AccountSubjectInfo prodAccountSubjectInfo = accountQueryService.queryAccount(prodProductInfo.getPlat_no(),prodProductInfo.getProd_account(), Tsubject.CASH.getCode(),Ssubject.PROD.getCode());
			if(prodAccountSubjectInfo == null){
				logger.info("【标的代偿（委托）还款】标的账户不存在");
				throw new BusinessException(BusinessMsg.INVALID_ACCOUNT,BusinessMsg.getMsg(BusinessMsg.INVALID_ACCOUNT) + "，标的账户不存在");
			}

			//检查标的融资信息
			List<EaccFinancinfo> listEaccFinancinfo = prodSearchService.queryEaccFinancinfo(prodProductInfo.getPlat_no(),prodProductInfo.getProd_id());
			if(listEaccFinancinfo == null || listEaccFinancinfo.size() == 0){
				logger.info("【标的代偿（委托）还款】请求账户与融资人提取账户信息不一致");
				throw new BusinessException(BusinessMsg.ACCOUNT_INFO_DIFF,BusinessMsg.getMsg(BusinessMsg.ACCOUNT_INFO_DIFF));
			}

			//检查借款人账户是否存在
			AccountSubjectInfo custAccountSubjectInfo = accountQueryService.queryAccount(prodProductInfo.getPlat_no(), substituteRepayRequest.getPlatcust(), Tsubject.CASH.getCode(),Ssubject.FINANCING.getCode());
			if(custAccountSubjectInfo == null){
				logger.info( "【标的代偿（委托）还款】借款人账户不存在");
				throw new BusinessException(BusinessMsg.INVALID_ACCOUNT,BusinessMsg.getMsg(BusinessMsg.INVALID_ACCOUNT) + "，借款人账户不存在");
			}

			AccountSubjectInfo compensationPlatcustAccountSubjectInfo = null;

			if(!"0".equals(substituteRepayRequest.getRepay_type()) && !"1".equals(substituteRepayRequest.getRepay_type()) && !"2".equals(substituteRepayRequest.getRepay_type())){
				logger.info( "【标的代偿（委托）还款】还款类型不正确");
				throw new BusinessException(BusinessMsg.PARAMETER_ERROR,BusinessMsg.getMsg(BusinessMsg.PARAMETER_ERROR) + "，还款类型不正确");
			}

			//风险金代偿
			if("2".equals(substituteRepayRequest.getRepay_type())){
				//查询风险金代偿客户编号
				compensationPlatcustAccountSubjectInfo = accountQueryService.queryAccount(substituteRepayRequest.getMer_no(),substituteRepayRequest.getMer_no(), Tsubject.CASH.getCode(),Ssubject.DEPOSIT.getCode());
				if(compensationPlatcustAccountSubjectInfo==null){
					logger.info("【标的代偿（委托）还款】风险金账户不存在");
					throw new BusinessException(BusinessMsg.INVALID_ACCOUNT,BusinessMsg.getMsg(BusinessMsg.INVALID_ACCOUNT) + "风险金账户不存在");
				}
			}else{
				if(StringUtils.isBlank(substituteRepayRequest.getCompensation_platcust())){
					logger.info("【标的代偿（委托）还款】compensation_platcust不能为空");
					throw new BusinessException(BusinessMsg.PARAMETER_LACK,BusinessMsg.getMsg(BusinessMsg.PARAMETER_LACK) + "compensation_platcust不能为空");
				}
				boolean check_platcust = false;
				List<ProdCompensationList> prodCompensationLists = prodSearchService.queryProdCompensationList(prodProductInfo.getPlat_no(),prodProductInfo.getProd_id());

				for(ProdCompensationList prodCompensationList : prodCompensationLists){

					if(prodCompensationList.getPlatcust().equals(substituteRepayRequest.getCompensation_platcust())) {
						check_platcust = true;

						break;
					}
				}

				if(!check_platcust){
					logger.info( "【标的代偿（委托）还款】代偿人账户不存在");
					throw new BusinessException(BusinessMsg.PARAMETER_ERROR,BusinessMsg.getMsg(BusinessMsg.PARAMETER_ERROR) + ",代偿账户没有报备");
				}

				//检查代偿人账户是否存在
				compensationPlatcustAccountSubjectInfo = accountQueryService.queryAccount(prodProductInfo.getPlat_no(), substituteRepayRequest.getCompensation_platcust(), Tsubject.CASH.getCode(),Ssubject.FINANCING.getCode());
				if(compensationPlatcustAccountSubjectInfo == null){

					logger.info( "【标的代偿（委托）还款】代偿人账户不存在");
					throw new BusinessException(BusinessMsg.ACCOUNT_INFO_DIFF,BusinessMsg.getMsg(BusinessMsg.ACCOUNT_INFO_DIFF) + "---------------代偿人账户不存在");
				}
			}

			//查询手续费的账户
			AccountSubjectInfo feeAccountSubjectInfo = accountQueryService.queryAccount(prodProductInfo.getPlat_no(),null, Tsubject.CASH.getCode(),Ssubject.FEE.getCode());
			if(feeAccountSubjectInfo == null){
				logger.info("【标的代偿（委托）还款】手续费账户不存在");
				throw new BusinessException(BusinessMsg.PARAMETER_ERROR,BusinessMsg.getMsg(BusinessMsg.PARAMETER_ERROR) + ",手续费账户不存在");
			}

			//记录标的代偿还款表
			ProdCompensationInfo prodCompensationInfo = new ProdCompensationInfo();
			prodCompensationInfo.setTrans_serial(transTransReq.getTrans_serial());
			prodCompensationInfo.setPlat_no(prodProductInfo.getPlat_no());
			prodCompensationInfo.setProd_id(prodProductInfo.getProd_id());
			prodCompensationInfo.setCompensation_platcust(substituteRepayRequest.getCompensation_platcust());
			prodCompensationInfo.setRepay_num(substituteRepayRequest.getRepay_num());
			prodCompensationInfo.setRepay_date(substituteRepayRequest.getRepay_date());
			prodCompensationInfo.setRepay_amt(substituteRepayRequest.getRepay_amt());
			prodCompensationInfo.setReal_repay_date(substituteRepayRequest.getReal_repay_date());
			prodCompensationInfo.setReal_repay_amt(substituteRepayRequest.getReal_repay_amt());
			prodCompensationInfo.setFee_amt(substituteRepayRequest.getFee_amt());
			prodCompensationInfo.setRepay_type(substituteRepayRequest.getRepay_type());
			prodCompensationInfo.setOrder_no(baserequest.getOrder_no());
			prodCompensationInfo.setEnabled(Constants.ENABLED);
			prodCompensationInfo.setCreate_time(new Date());
			prodCompensationInfo.setUpdate_time(new Date());
			prodCompensationInfoMapper.insert(prodCompensationInfo);

			//转账      先转实际金额到标的账户
			List<EaccAccountamtlist> eaccAccountamtlists = new ArrayList<EaccAccountamtlist>();
			EaccAccountamtlist eaccAccountamtlist = new EaccAccountamtlist();
			eaccAccountamtlist.setTrans_serial(transTransReq.getTrans_serial());
			eaccAccountamtlist.setOrder_no(transTransReq.getOrder_no());
			eaccAccountamtlist.setPlat_no(prodProductInfo.getPlat_no());
			eaccAccountamtlist.setPlatcust(compensationPlatcustAccountSubjectInfo.getPlatcust());
			eaccAccountamtlist.setSubject(compensationPlatcustAccountSubjectInfo.getSubject());
			eaccAccountamtlist.setSub_subject(compensationPlatcustAccountSubjectInfo.getSub_subject());
			eaccAccountamtlist.setTrans_code(TransConsts.PRODCOMPENSATORYREPAY_CODE);
			eaccAccountamtlist.setTrans_name(TransConsts.PRODCOMPENSATORYREPAY_NAME);
			eaccAccountamtlist.setAmt_type(AmtType.EXPENSIVE.getCode());
			eaccAccountamtlist.setOppo_plat_no(prodAccountSubjectInfo.getPlat_no());
			eaccAccountamtlist.setOppo_platcust(prodAccountSubjectInfo.getPlatcust());
			eaccAccountamtlist.setOppo_subject(prodAccountSubjectInfo.getSubject());
			eaccAccountamtlist.setOppo_sub_subject(prodAccountSubjectInfo.getSub_subject());
			eaccAccountamtlist.setAmt(substituteRepayRequest.getReal_repay_amt());
			eaccAccountamtlist.setRemark("标的代偿（委托）还款，转实际金额到标的账户");
			eaccAccountamtlist.setTrans_date(transTransReq.getTrans_date());
			eaccAccountamtlist.setTrans_time(transTransReq.getTrans_time());
			eaccAccountamtlists.add(eaccAccountamtlist);

			//转账      后转手续费到手续费的账户
			if(substituteRepayRequest.getFee_amt().compareTo(BigDecimal.ZERO) > 0) {
				eaccAccountamtlist = new EaccAccountamtlist();
				eaccAccountamtlist.setTrans_serial(transTransReq.getTrans_serial());
				eaccAccountamtlist.setOrder_no(transTransReq.getOrder_no());
				eaccAccountamtlist.setPlat_no(prodProductInfo.getPlat_no());
				eaccAccountamtlist.setPlatcust(compensationPlatcustAccountSubjectInfo.getPlatcust());
				eaccAccountamtlist.setSubject(compensationPlatcustAccountSubjectInfo.getSubject());
				eaccAccountamtlist.setSub_subject(compensationPlatcustAccountSubjectInfo.getSub_subject());
				eaccAccountamtlist.setTrans_code(TransConsts.COMPENSATORYREPAY_FEE_CODE);
				eaccAccountamtlist.setTrans_name(TransConsts.COMPENSATORYREPAY_FEE_NAME);
				eaccAccountamtlist.setAmt_type(AmtType.EXPENSIVE.getCode());
				eaccAccountamtlist.setOppo_plat_no(feeAccountSubjectInfo.getPlat_no());
				eaccAccountamtlist.setOppo_platcust(feeAccountSubjectInfo.getPlatcust());
				eaccAccountamtlist.setOppo_subject(feeAccountSubjectInfo.getSubject());
				eaccAccountamtlist.setOppo_sub_subject(feeAccountSubjectInfo.getSub_subject());
				eaccAccountamtlist.setAmt(substituteRepayRequest.getFee_amt());
				eaccAccountamtlist.setRemark("标的代偿（委托）还款，转手续费到手续费账户");
				eaccAccountamtlist.setTrans_date(transTransReq.getTrans_date());
				eaccAccountamtlist.setTrans_time(transTransReq.getTrans_time());
				eaccAccountamtlists.add(eaccAccountamtlist);
			}

			accountTransferService.batchTransfer(eaccAccountamtlists);

			//更新业务流水
			transTransReq.setReturn_code(BusinessMsg.SUCCESS);
			transTransReq.setReturn_msg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));
			transTransReq.setStatus(FlowStatus.SUCCESS.getCode());
			transReqService.insert(transTransReq);

			substituteRepayResponse.setRecode(BusinessMsg.SUCCESS);
			substituteRepayResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));
			OrderData orderData = new OrderData();
			orderData.setOrder_no(baserequest.getOrder_no());
			orderData.setOrder_status(OrderStatus.SUCCESS.getCode());
			orderData.setProcess_date(DateUtils.todayStr(DateUtils.DEF_FORMAT));
			orderData.setQuery_id(transTransReq.getTrans_serial());
			substituteRepayResponse.setOrderData(orderData);
		}catch (Exception e){
			logger.error("【标的代偿（委托）还款】：", e);

			if (e instanceof BusinessException) {
				BaseResponse baseResponse = ((BusinessException) e).getBaseResponse();
				//更新流水
				transTransReq.setReturn_code(baseResponse.getRecode());
				transTransReq.setReturn_msg(baseResponse.getRemsg());
				transTransReq.setStatus(FlowStatus.FAIL.getCode());
				transReqService.insert(transTransReq);

				throw new BusinessException(baseResponse);
			} else {
				logger.error("标的代偿（委托）还款未知异常修改处理中" + e);
				substituteRepayResponse.setRecode(BusinessMsg.SUCCESS);
				substituteRepayResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));
				OrderData orderData = new OrderData();
				orderData.setOrder_no(baserequest.getOrder_no());
				orderData.setOrder_status(OrderStatus.PROCESSING.getCode());
				orderData.setProcess_date(DateUtils.todayStr(DateUtils.DEF_FORMAT));
				orderData.setQuery_id(transTransReq.getTrans_serial());
				substituteRepayResponse.setOrderData(orderData);
			}
		}

		return substituteRepayResponse;
	}


	public List<EaccUserinfo> queryUserInfo(String name,String mall_no,String id_code ){
		List<EaccUserinfo> eaccUserinfos = new ArrayList<>();
		try {
			eaccUserinfos=custEaccUserinfoMapper.userInFo(name,mall_no,id_code);
		}catch (Exception e){
			logger.error("查询异常",e);
		}

		return eaccUserinfos;
	}

	/**
	 * 签约申请
	 * @param
	 * @return
	 * @throws BusinessException
	 */
	public ContractApplyResponseDetail contractApp(ContractApplyRequest contractApplyRequest)throws  BusinessException{
		logger.info("【签约申请】-->进入dubbo-->order_no:"+contractApplyRequest.getOrder_no());
		ContractApplyResponse contractApplyResponseList1=new ContractApplyResponse();
		ContractApplyResponseDetail contractApplyResponseDetail=new ContractApplyResponseDetail();
		TransTransreq transTransReq=new TransTransreq();
		transTransReq = transReqService.getTransTransReq(contractApplyRequest.clone(),TransConsts.CONTRACT_PAY_FEE_CODE,TransConsts.CONTRACT_PAY_FEE_NAME, false);
		transReqService.insert(transTransReq);
		logger.info("【签约申请】-->添加流水成功-->order_no:"+contractApplyRequest.getOrder_no());
		//根据身份证查询用户信息

		//注掉一下代码，因为凤金有先签约在开户的场景
		/*List<EaccUserinfo> userinfo =queryUserInfo(contractApplyRequest.getName(),contractApplyRequest.getMall_no(),contractApplyRequest.getId_code());
		EaccUserinfo eaccUserinfo=new EaccUserinfo();
		for(EaccUserinfo eaccUserinfo1:userinfo){
			eaccUserinfo.setMallcust(eaccUserinfo1.getMallcust());
		}
		if(userinfo.size()==0){
			logger.info("用户信息不存在,或以冻结",contractApplyRequest.getOrder_no());
			throw  new BusinessException(BusinessMsg.INVAILD_ACCOUNT_DOUJIE, BusinessMsg.getMsg(BusinessMsg.INVAILD_ACCOUNT_DOUJIE));
		}
		String mall_no=contractApplyRequest.getMall_no();
		String card_no=contractApplyRequest.getCard_no();
		List<EaccCardinfo> eaccCardinfo1=accountSearchServicel.queryEaccCardInfo(mall_no,eaccUserinfo.getMallcust(),card_no,null);
		EaccCardinfo eaccCardinfo=new EaccCardinfo();
		for(EaccCardinfo eaccCardinfo2:eaccCardinfo1){
			eaccCardinfo.setEnabled(eaccCardinfo2.getEnabled());
		}
		if(eaccCardinfo1.size()==0){
			logger.info("用户信息不存在",contractApplyRequest.getOrder_no());
			throw  new BusinessException(BusinessMsg.INVALID_ACCOUNT, BusinessMsg.getMsg(BusinessMsg.INVALID_ACCOUNT));
		}
		if("2".equals(eaccCardinfo.getEnabled())){
			logger.info("已冻结",contractApplyRequest.getOrder_no());
			throw  new BusinessException(BusinessMsg.ACCOUNT_FROZEN, BusinessMsg.getMsg(BusinessMsg.ACCOUNT_FROZEN));
		}*/

		CacheUtil.getCache().set(Constants.APP_LOCK_KEY + transTransReq.getOrder_no(), JSON.toJSONString(contractApplyRequest));
		//保存24小时
		CacheUtil.getCache().expire(Constants.APP_LOCK_KEY + transTransReq.getTrans_serial(), 1800);


		try {
			PlatPaycode platPayCode = accountSearchService.queryPlatPayCode(contractApplyRequest.getMer_no(), contractApplyRequest.getPay_code());
			if (platPayCode==null){
				throw  new BusinessException(BusinessMsg.NO_CHANNEL_INFORMATION,BusinessMsg.getMsg(BusinessMsg.NO_CHANNEL_INFORMATION));
			}
			String host = sysParameterService.querySysParameter(contractApplyRequest.getMall_no(), URLConfigUtil.EPAY_SERVER_KEY);
			String url = sysParameterService.querySysParameter(contractApplyRequest.getMall_no(), URLConfigUtil.EPAY_AGR_BIND);
			Map<String,Object>params=new HashMap<>();
			params.put("partner_id",platPayCode.getPayment_plat_no());
			params.put("partner_serial_no",transTransReq.getTrans_serial());
			params.put("partner_trans_date",transTransReq.getTrans_date());
			params.put("partner_trans_time",transTransReq.getTrans_time());
			params.put("client_name",contractApplyRequest.getName());
			params.put("id_kind","0");
			params.put("id_no",contractApplyRequest.getId_code());
			params.put("card_no",contractApplyRequest.getCard_no());
			params.put("mobile_tel",contractApplyRequest.getMobile());
			params.put("func_code","1");
			params.put("channelid",platPayCode.getChannel_id());
			params.put("url",url);
			params.put("host",host);

			logger.info("【 签约申请】三方请求参数："+ JSON.toJSONString(params));
			Map<String,Object> res=adapterService.queryContractApp(params);
			logger.info("【 签约申请】三方返回参数："+ JSON.toJSONString(res));
			/*List<JSONObject> list = (List) res.get("data");
			ContractApplyResponse contractApplyResponse=new ContractApplyResponse();
			for (JSONObject jo:list){
				contractApplyResponse.setPay_code(contractApplyRequest.getPay_code());
				contractApplyResponse.setStatus(jo.getString("order_status"));
				contractApplyResponseList1.add(contractApplyResponse);
			}*/
			ContractApplyResponse contractApplyResponse=new ContractApplyResponse();
			contractApplyResponse.setPay_code(contractApplyRequest.getPay_code());
			contractApplyResponse.setStatus("0");

			contractApplyResponseDetail.setOrder_no(contractApplyRequest.getOrder_no());
		/*	if("4".equals(res.get("order_status"))){
				logger.info("未签约，或者签约失效执行签约",contractApplyRequest.getOrder_no());
				contractApplyResponseDetail.setData_detail(contractApplyResponseList1);
				contractApplyResponseDetail.setRecode(BusinessMsg.ACCEPTANCE_SUCCESS);
				contractApplyResponseDetail.setOrder_status(FlowStatus.INPROCESS.getCode());
				contractApplyResponseDetail.setRemsg(BusinessMsg.getMsg(BusinessMsg.ACCEPTANCE_SUCCESS));
				transTransReq.setStatus(FlowStatus.SUCCESS.getCode());
				transTransReq.setReturn_msg(BusinessMsg.getMsg(BusinessMsg.ACCEPTANCE_SUCCESS));
				transTransReq.setReturn_code(BusinessMsg.ACCEPTANCE_SUCCESS);
				transReqService.insert(transTransReq);
			}else*/ if (OrderStatus.SUCCESS.getCode().equals(res.get("order_status"))){
				logger.info("支付返回代确认签约,");
				contractApplyResponseDetail.setData_detail(contractApplyResponse);
				contractApplyResponseDetail.setRecode(BusinessMsg.SUCCESS);
				contractApplyResponseDetail.setRemsg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));
				contractApplyResponseDetail.setOrder_status(OrderStatus.SUCCESS.getCode());
				transTransReq.setStatus(FlowStatus.SUCCESS.getCode());
				transTransReq.setReturn_msg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));
				transTransReq.setReturn_code(BusinessMsg.SUCCESS);
				transReqService.insert(transTransReq);
			}else {
				logger.info("支付返回错误信息",res.get("remsg"));
				throw  new BusinessException(res.get("recode").toString(),res.get("remsg").toString());
			}
		}catch (Exception e) {
			logger.error("【签约申请】==============异常：%s",e);
			BaseResponse baseResponse=new BaseResponse();
			if (e instanceof BusinessException){
				baseResponse=((BusinessException) e).getBaseResponse();
				transTransReq.setStatus(FlowStatus.FAIL.getCode());
				transTransReq.setReturn_msg(baseResponse.getRemsg());
				transTransReq.setReturn_code(baseResponse.getRecode());
				transReqService.insert(transTransReq);
				contractApplyResponseDetail.setRemsg(baseResponse.getRemsg());
				contractApplyResponseDetail.setRecode(baseResponse.getRecode());
				contractApplyResponseDetail.setOrder_status(OrderStatus.FAIL.getCode());
			}else{
				contractApplyResponseDetail.setRemsg(BusinessMsg.getMsg(BusinessMsg.FAIL));
				contractApplyResponseDetail.setRecode(BusinessMsg.FAIL);
				contractApplyResponseDetail.setOrder_no(contractApplyRequest.getOrder_no());
				contractApplyResponseDetail.setOrder_status(OrderStatus.FAIL.getCode());
				transTransReq.setStatus(FlowStatus.FAIL.getCode());
				transTransReq.setReturn_msg(BusinessMsg.getMsg(BusinessMsg.FAIL));
				transTransReq.setReturn_code(BusinessMsg.FAIL);
				transReqService.insert(transTransReq);
			}

		}

		return contractApplyResponseDetail;
	}

	/**
	 * 签约查询
	 */
	public ContractApplyResponseDetail contractStatus(ContractApplyRequest contractApplyRequest)throws  BusinessException {
		ContractApplyResponseDetail contractApplyResponseDetail=new ContractApplyResponseDetail();
		logger.info("【签约查询】-->进入dubbo-->order_no:"+contractApplyRequest.getOrder_no());
		ContractApplyResponse contractApplyResponseList=new ContractApplyResponse();

		try {
			PlatPaycode platPayCode = accountSearchService.queryPlatPayCode(contractApplyRequest.getMer_no(), contractApplyRequest.getPay_code());
			if(platPayCode==null){
				logger.info("渠道信息错误");
				throw  new BusinessException(BusinessMsg.NO_CHANNEL_INFORMATION,BusinessMsg.getMsg(BusinessMsg.NO_CHANNEL_INFORMATION));
			}
			String host = sysParameterService.querySysParameter(contractApplyRequest.getMall_no(), URLConfigUtil.EPAY_SERVER_KEY);
			String url = sysParameterService.querySysParameter(contractApplyRequest.getMall_no(), URLConfigUtil.EPAY_AGR_BIND_STATUS);

			Map<String,Object>res=new HashMap<>();
			res.put("partner_id",platPayCode.getPayment_plat_no());
			res.put("partner_serial_no", SeqUtil.getSeqNum());
			res.put("partner_trans_date",DateUtils.getyyyyMMddDate());
			res.put("partner_trans_time",DateUtils.getHHmmssTime());
			res.put("client_name",contractApplyRequest.getName());
			res.put("id_kind","0");
			res.put("id_no",contractApplyRequest.getId_code());
			res.put("card_no",contractApplyRequest.getCard_no());
			res.put("mobile_tel",contractApplyRequest.getMobile());
			res.put("channelid",platPayCode.getChannel_id());
			res.put("url",url);
			res.put("host",host);
			logger.info("【 签约查询】三方请求参数："+ JSON.toJSONString(res));
			Map<String,Object> params=adapterService.queryContractAppTo(res);
			logger.info("【 签约查询】三方返回参数："+ JSON.toJSONString(params));
			List<JSONObject> list = (List) params.get("data");
			ContractApplyResponse contractApplyResponse=new ContractApplyResponse();
			for(JSONObject jo:list){
				contractApplyResponse.setStatus((String) params.get("order_status"));
				contractApplyResponse.setPay_code(jo.getString("channelid"));

			}
			/*String order_status="2";
			contractApplyResponse.setStatus("4");
			contractApplyResponse.setPay_code(contractApplyRequest.getPay_code());
			contractApplyResponseList.add(contractApplyResponse);*/

			/*if("2".equals(params.get("order_status"))){
				contractApplyResponseDetail.setData_detail(contractApplyResponseList);
				contractApplyResponseDetail.setOrder_no(contractApplyRequest.getOrder_no());
				contractApplyResponseDetail.setRecode(BusinessMsg.SUCCESS);
				contractApplyResponseDetail.setRemsg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));
				contractApplyResponseDetail.setOrder_status("1");

			}else*/

			if(BusinessMsg.SUCCESS.equals(params.get("recode"))){
				contractApplyResponseDetail.setData_detail(contractApplyResponse);
				contractApplyResponseDetail.setOrder_no(contractApplyRequest.getOrder_no());
				contractApplyResponseDetail.setRecode(BusinessMsg.SUCCESS);
				contractApplyResponseDetail.setRemsg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));
			} else if (OrderStatus.FAIL.getCode().equals(params.get("order_status"))){
				logger.info("支付异常信息", contractApplyRequest.getOrder_no());
				throw new BusinessException(params.get("recode").toString(), params.get("remsg").toString());
			}
		} catch (Exception e){
			BaseResponse baseResponse=new BaseResponse();
			logger.info("【签约查询】异常:"+contractApplyRequest.getOrder_no());
			if (e instanceof BusinessException){
				baseResponse=((BusinessException) e).getBaseResponse();
				contractApplyResponseDetail.setRemsg(baseResponse.getRemsg());
				contractApplyResponseDetail.setRecode(baseResponse.getRecode());
			}else{
				contractApplyResponseDetail.setRemsg(BusinessMsg.getMsg(BusinessMsg.FAIL));
				contractApplyResponseDetail.setRecode(BusinessMsg.FAIL);
				contractApplyResponseDetail.setOrder_no(contractApplyRequest.getOrder_no());
			}
		}
		return contractApplyResponseDetail;
	}


		/**
         * 签约确认
         *
         */
	public ContractStatusReponse contractConfirm(ContractConfirmRequest contractConfirmRequest)throws  BusinessException{
		ContractStatusReponse baseResponse=new ContractStatusReponse();
		logger.info("【签约确认】-->进入dubbo-->order_no:"+contractConfirmRequest.getOrder_no());
		TransTransreq transTransReq=new TransTransreq();
		transTransReq = transReqService.getTransTransReq(contractConfirmRequest.clone(),TransConsts.CONTRACTT_PAY_TT_CODE,TransConsts.CONTRACTT_PAY_FEE_NAME, false);
		transReqService.insert(transTransReq);
		try {
			Object openAccountParamsObj=CacheUtil.getCache().get(Constants.APP_LOCK_KEY+contractConfirmRequest.getOrigin_order_no());
			if(openAccountParamsObj==null){
				logger.info(String.format("【签约确认】原订单在redis中不存在，无法获取签约参数|order_no:%s|origin_order_no:%s",
						contractConfirmRequest.getOrder_no(),contractConfirmRequest.getOrigin_order_no()));
				throw new BusinessException(BusinessMsg.ORIGINAL_ORDER_NOEXISTENT,BusinessMsg.getMsg(BusinessMsg.ORIGINAL_ORDER_NOEXISTENT));
			}
			ContractApplyRequest contractApplyRequest=JSON.parseObject((String) openAccountParamsObj,ContractApplyRequest.class);
			PlatPaycode platPayCode = accountSearchService.queryPlatPayCode(contractApplyRequest.getMer_no(), contractApplyRequest.getPay_code());
			if (platPayCode==null){
				throw  new BusinessException(BusinessMsg.NO_CHANNEL_INFORMATION,BusinessMsg.getMsg(BusinessMsg.NO_CHANNEL_INFORMATION));
			}
			String host = sysParameterService.querySysParameter(contractApplyRequest.getMall_no(), URLConfigUtil.EPAY_SERVER_KEY);
			String url = sysParameterService.querySysParameter(contractApplyRequest.getMall_no(), URLConfigUtil.EPAY_AGR_BIND);

			TransTransreq transTransReq1 = transReqService.queryTransTransReqByOrderno(contractConfirmRequest.getOrigin_order_no());
			Map<String,Object>params=new HashMap<>();
			params.put("partner_id",platPayCode.getPayment_plat_no());
			params.put("partner_serial_no",transTransReq1.getTrans_serial());
			params.put("partner_trans_date",transTransReq.getTrans_date());
			params.put("partner_trans_time",transTransReq.getTrans_time());
			params.put("client_name",contractApplyRequest.getName());
			params.put("id_kind","0");
			params.put("id_no",contractApplyRequest.getId_code());
			params.put("card_no",contractApplyRequest.getCard_no());
			params.put("mobile_tel",contractApplyRequest.getMobile());
			params.put("func_code","2");
			params.put("channelid",platPayCode.getChannel_id());
			params.put("verify_info",contractConfirmRequest.getIdentifying_code());
			params.put("url",url);
			params.put("host",host);
			logger.info("【 签约确认】三方请求参数："+ JSON.toJSONString(params));
			Map<String,Object> res=adapterService.queryContractApp(params);
			logger.info("【 签约确认】三方返回参数："+ JSON.toJSONString(params));
/*
			List<JSONObject> list = (List) res.get("data");
*/
			/*for (JSONObject jo:list){
				baseResponse1.setOrder_status(jo.getString("order_status"));
			}*/
			;
			if(OrderStatus.SUCCESS.getCode().equals(res.get("order_status"))){
				logger.info("支付返回成功");
				transTransReq.setStatus(FlowStatus.SUCCESS.getCode());
				transTransReq.setReturn_msg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));
				transTransReq.setReturn_code(BusinessMsg.SUCCESS);
				transReqService.insert(transTransReq);
				baseResponse.setOrder_no(contractConfirmRequest.getOrder_no());
				baseResponse.setRecode(BusinessMsg.SUCCESS);
				baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));
				baseResponse.setOrder_status("1");
				baseResponse.setStatus("2");

			}else {
				/*baseResponse1.setOrder_status("2");
				baseResponse1.setRemsg((String) res.get("error_info"));
				baseResponse1.setRecode(BusinessMsg.FAIL);
				baseResponse1.setOrder_no(contractConfirmRequest.getOrder_no());*/
				throw  new BusinessException(res.get("recode").toString(),res.get("remsg").toString());
			}
			if(res.get("recode").equals("001003")){
				logger.info("请求超时",contractConfirmRequest.getOrder_no());
				throw new BusinessException(BusinessMsg.VERIFICATION_ERROR,BusinessMsg.getMsg(BusinessMsg.VERIFICATION_ERROR));
			}
		}catch (Exception e){
			logger.error("【签约确认】==============异常：%s",e);
			BaseResponse baseResponse1=new BaseResponse();
			if (e instanceof BusinessException){
				baseResponse1=((BusinessException) e).getBaseResponse();
				if(!baseResponse1.getRecode().equals(BusinessMsg.VERIFICATION_ERROR)){
					transTransReq.setStatus(FlowStatus.FAIL.getCode());
					transTransReq.setOrder_no(contractConfirmRequest.getOrigin_order_no());
					transTransReq.setReturn_msg(baseResponse.getRemsg());
					transTransReq.setReturn_code(baseResponse.getRecode());
					transTransReq.setOrder_no(contractConfirmRequest.getOrigin_order_no());
					transReqService.insert(transTransReq);
				}
				baseResponse.setOrder_status(FlowStatus.FAIL.getCode());
				baseResponse.setRemsg(baseResponse1.getRemsg());
				baseResponse.setRecode(baseResponse1.getRecode());
				baseResponse.setOrder_no(contractConfirmRequest.getOrder_no());
			}else {
				baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.FAIL));
				baseResponse.setRecode(BusinessMsg.FAIL);
				baseResponse.setOrder_no(contractConfirmRequest.getOrder_no());
				baseResponse.setOrder_status(FlowStatus.FAIL.getCode());
			}
		}

		return  baseResponse;
	}


	/*
	 * 借款人还款代偿
	 * pzy
	 */
	@Override
	public CompensateRepayResponse compensateRepay(CompensateRepayRequest compensateRepayRequest) throws BusinessException {

		logger.info("【借款人还款代偿】-->进入dubbo-->order_no:"+compensateRepayRequest.getOrder_no());
		CompensateRepayResponse compensateRepayResponse = new CompensateRepayResponse();
		TransTransreq transTransReq = transReqService.getTransTransReq(compensateRepayRequest.clone(),TransConsts.BORROWREPAY_COMPENSATORYREPAY_CODE,TransConsts.BORROWREPAY_COMPENSATORYREPAY_NAME, false);
		transTransReq.setPlatcust(compensateRepayRequest.getPlatcust());
		transReqService.insert(transTransReq);
		logger.info("【借款人还款代偿】-->添加流水成功-->order_no:"+compensateRepayRequest.getOrder_no());

		try{

			ProdProductinfo prodProductInfo = prodSearchService.queryProdInfo(compensateRepayRequest.getMer_no(), compensateRepayRequest.getProd_id());
			if(prodProductInfo == null){
				logger.info("【借款人还款代偿】-->标不存在-->prod_id:"+compensateRepayRequest.getProd_id()+"-->order_no:" + compensateRepayRequest.getOrder_no());
				throw new BusinessException(BusinessMsg.INVALID_PRODUCT_ID,BusinessMsg.getMsg(BusinessMsg.INVALID_PRODUCT_ID)  + ",标不存在" + compensateRepayRequest.getProd_id());
			}
			logger.info("【借款人还款代偿】-->标存在-->order_no:"+compensateRepayRequest.getOrder_no());

			//查询借款人的账户
			AccountSubjectInfo accountSubjectInfo = accountQueryService.queryAccount(compensateRepayRequest.getMer_no(),compensateRepayRequest.getPlatcust(), Tsubject.CASH.getCode(),Ssubject.FINANCING.getCode());
			if(accountSubjectInfo == null){
				logger.info("【借款人还款代偿】-->借款人账号不存在-->platcust:"+compensateRepayRequest.getPlatcust()+"-->order_no:"+compensateRepayRequest.getOrder_no());
				throw new BusinessException(BusinessMsg.INVALID_ACCOUNT,BusinessMsg.getMsg(BusinessMsg.INVALID_ACCOUNT) + ",借款人账号不存在");
			}
			logger.info("【借款人还款代偿】-->借款人账号存在-->order_no:"+compensateRepayRequest.getOrder_no());

			//查询代偿人的账户
			AccountSubjectInfo compensationAccountSubjectInfo = accountQueryService.queryAccount(compensateRepayRequest.getMer_no(),compensateRepayRequest.getCompensation_platcust(), Tsubject.CASH.getCode(),Ssubject.FINANCING.getCode());
			if(compensationAccountSubjectInfo == null){
				logger.info("【借款人还款代偿】代偿人账户不存在");
				throw new BusinessException(BusinessMsg.INVALID_ACCOUNT,BusinessMsg.getMsg(BusinessMsg.INVALID_ACCOUNT) + "代偿人账户不存在");
			}
			logger.info("【借款人还款代偿】-->代偿人账户存在-->order_no:"+compensateRepayRequest.getOrder_no());

			List<ProdCompensationList> prodCompensationLists = prodSearchService.queryProdCompensationList(prodProductInfo.getPlat_no(),prodProductInfo.getProd_id(),compensateRepayRequest.getCompensation_platcust());
			if(null == prodCompensationLists || prodCompensationLists.size() == 0){
				logger.info( "【借款人还款代偿（委托）】，代偿账户没有报备");
				throw new BusinessException(BusinessMsg.PARAMETER_ERROR,BusinessMsg.getMsg(BusinessMsg.PARAMETER_ERROR));
			}
			logger.info("【借款人还款代偿】-->代偿人账户正常报备-->order_no:"+compensateRepayRequest.getOrder_no());

			ProdCompensationRepay prodCompensationRepay = new ProdCompensationRepay();
			prodCompensationRepay.setTrans_serial(transTransReq.getTrans_serial());
			prodCompensationRepay.setPlat_no(accountSubjectInfo.getPlat_no());
			prodCompensationRepay.setProd_id(compensateRepayRequest.getProd_id());
			prodCompensationRepay.setRepay_amt(compensateRepayRequest.getRepay_amt());
			prodCompensationRepay.setOrder_no(compensateRepayRequest.getOrder_no());
			prodCompensationRepay.setEnabled(Constants.ENABLED);
			prodCompensationRepay.setCreate_time(new Date());
			prodCompensationRepay.setUpdate_time(new Date());
			prodCompensationRepay.setCompensation_platcust(compensateRepayRequest.getCompensation_platcust());
			//prodCompensationRepayMapper.insert(prodCompensationRepay);
			productRefundService.addCompensationRepay(prodCompensationRepay);
			logger.info("【借款人还款代偿】-->借款人还款代偿表添加成功-->order_no:"+compensateRepayRequest.getOrder_no());

			//转账      借款人转到代偿人
			List<EaccAccountamtlist> eaccAccountamtlists = new ArrayList<>();
			EaccAccountamtlist eaccAccountamtlist = new EaccAccountamtlist();
			eaccAccountamtlist.setOrder_no(compensateRepayRequest.getOrder_no());
			eaccAccountamtlist.setTrans_serial(transTransReq.getTrans_serial());
			eaccAccountamtlist.setPlat_no(accountSubjectInfo.getPlat_no());
			eaccAccountamtlist.setPlatcust(accountSubjectInfo.getPlatcust());
			eaccAccountamtlist.setSubject(accountSubjectInfo.getSubject());
			eaccAccountamtlist.setSub_subject(accountSubjectInfo.getSub_subject());
			eaccAccountamtlist.setTrans_code(TransConsts.BORROWREPAY_COMPENSATORYREPAY_CODE);
			eaccAccountamtlist.setTrans_name(TransConsts.BORROWREPAY_COMPENSATORYREPAY_NAME);
			eaccAccountamtlist.setAmt_type(AmtType.EXPENSIVE.getCode());
			eaccAccountamtlist.setOppo_plat_no(compensationAccountSubjectInfo.getPlat_no());
			eaccAccountamtlist.setOppo_platcust(compensationAccountSubjectInfo.getPlatcust());
			eaccAccountamtlist.setOppo_subject(compensationAccountSubjectInfo.getSubject());
			eaccAccountamtlist.setOppo_sub_subject(compensationAccountSubjectInfo.getSub_subject());
			eaccAccountamtlist.setAmt(compensateRepayRequest.getRepay_amt());
			eaccAccountamtlist.setTrans_date(transTransReq.getTrans_date());
			eaccAccountamtlist.setTrans_time(transTransReq.getTrans_time());
			eaccAccountamtlists.add(eaccAccountamtlist);

			//批量转账
			newAccountTransferExecuteService.batchTransfer(eaccAccountamtlists);
			logger.info("【借款人还款代偿】-->转账成功-->order_no:"+compensateRepayRequest.getOrder_no());

			//更新业务流水
			transTransReq.setReturn_code(BusinessMsg.SUCCESS);
			transTransReq.setReturn_msg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));
			transTransReq.setStatus(OrderStatus.SUCCESS.getCode());
			transReqService.insert(transTransReq);
			logger.info("【借款人还款代偿】-->流水修改成功-->order_no:"+compensateRepayRequest.getOrder_no());

			compensateRepayResponse.setRecode(BusinessMsg.SUCCESS);
			compensateRepayResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));
			OrderData orderData = new OrderData();
			orderData.setQuery_id(transTransReq.getTrans_serial());
			orderData.setOrder_status(OrderStatus.SUCCESS.getCode());
			orderData.setProcess_date(DateUtils.todayStr(DateUtils.DEF_FORMAT));
			compensateRepayResponse.setOrder_no(compensateRepayRequest.getOrder_no());
			compensateRepayResponse.setData(JSON.toJSONString(orderData, GlobalConfig.serializerFeature));
		}catch (Exception e){

			logger.error("【借款人还款代偿】-->异常-->order_no:" + compensateRepayRequest.getOrder_no(),e);

			BaseResponse baseResponse=new BaseResponse();
			if(e instanceof BusinessException){
				baseResponse=((BusinessException) e).getBaseResponse();

				//更新流水
				transTransReq.setStatus(FlowStatus.FAIL.getCode());
				transTransReq.setReturn_code(baseResponse.getRecode());
				transTransReq.setReturn_msg(baseResponse.getRemsg());
				transReqService.insert(transTransReq);

				compensateRepayResponse.setRecode(baseResponse.getRecode());
				compensateRepayResponse.setRemsg(baseResponse.getRemsg());
				compensateRepayResponse.setOrder_no(compensateRepayRequest.getOrder_no());

				OrderData orderData = new OrderData();
				orderData.setQuery_id(transTransReq.getTrans_serial());
				orderData.setOrder_status(OrderStatus.FAIL.getCode());
				orderData.setProcess_date(DateUtils.todayStr(DateUtils.DEF_FORMAT));
				compensateRepayResponse.setData(JSON.toJSONString(orderData, GlobalConfig.serializerFeature));

			}else{
				logger.error(e);
				baseResponse.setRecode(BusinessMsg.DUBBO_TIMEOUT);
				baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.DUBBO_TIMEOUT));

				compensateRepayResponse.setRecode(BusinessMsg.DUBBO_TIMEOUT);
				compensateRepayResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.DUBBO_TIMEOUT));
				compensateRepayResponse.setOrder_no(compensateRepayRequest.getOrder_no());

				OrderData orderData = new OrderData();
				orderData.setQuery_id(transTransReq.getTrans_serial());
				orderData.setOrder_status(OrderStatus.PROCESSING.getCode());
				orderData.setProcess_date(DateUtils.todayStr(DateUtils.DEF_FORMAT));
				compensateRepayResponse.setData(JSON.toJSONString(orderData, GlobalConfig.serializerFeature));

			}
		}
		return compensateRepayResponse;
	}

	@Override
	public CompensateRepayResponse compensateRepayOld(CompensateRepayRequestOld compensateRepayRequest) throws BusinessException {

		logger.info("【借款人还款代偿（委托）】");
		CompensateRepayResponse compensateRepayResponse = new CompensateRepayResponse();
		TransTransreq transTransReq = new TransTransreq();

		try{

			//记录业务流水表
			transTransReq = transReqService.getTransTransReq(compensateRepayRequest.clone(),TransConsts.BORROWREPAY_COMPENSATORYREPAY_CODE,TransConsts.BORROWREPAY_COMPENSATORYREPAY_NAME, false);
			transTransReq.setPlatcust(compensateRepayRequest.getPlatcust());
			transReqService.insert(transTransReq);


			//检查标是否存在
			ProdProductinfo prodProductInfo = prodSearchService.queryProdInfo(compensateRepayRequest.getMer_no(), compensateRepayRequest.getProd_id());
			if(prodProductInfo == null){
				logger.info("【借款人还款代偿（委托）】标不存在");
				throw new BusinessException(BusinessMsg.INVALID_PRODUCT_ID,BusinessMsg.getMsg(BusinessMsg.INVALID_PRODUCT_ID)  + ",标不存在" + compensateRepayRequest.getProd_id());
			}


			//查询借款人的账户
			AccountSubjectInfo accountSubjectInfo = accountQueryService.queryAccount(compensateRepayRequest.getMer_no(),compensateRepayRequest.getPlatcust(), Tsubject.CASH.getCode(),Ssubject.FINANCING.getCode());
			if(accountSubjectInfo == null){
				logger.info("【借款人还款代偿（委托）】借款人账号不存在");
				throw new BusinessException(BusinessMsg.INVALID_ACCOUNT,BusinessMsg.getMsg(BusinessMsg.INVALID_ACCOUNT) + "借款人账号不存在");
			}

			ProdCompensationRepay prodCompensationRepay = new ProdCompensationRepay();
			List<EaccAccountamtlist> eaccAccountamtlists = new ArrayList<EaccAccountamtlist>();
			AccountSubjectInfo compensationAccountSubjectInfo = null;
			EaccAccountamtlist eaccAccountamtlist = new EaccAccountamtlist();
			if(compensateRepayRequest.getCompensation_type()==null||"".equals(compensateRepayRequest.getCompensation_type())||CompensationType.DEBIT.getCode().equals(compensateRepayRequest.getCompensation_type())||CompensationType.CREDIT.getCode().equals(compensateRepayRequest.getCompensation_type())){
				if(compensateRepayRequest.getCompensation_platcust()==null || "".equals(compensateRepayRequest.getCompensation_platcust())){
					logger.info("【借款人还款代偿（委托）】代偿人平台客户号不能为空");
					throw new BusinessException(BusinessMsg.PARAMETER_LACK,BusinessMsg.getMsg(BusinessMsg.PARAMETER_LACK) + "代偿人平台客户号不能为空");
				}

				boolean check_platcust = false;
				List<ProdCompensationList> prodCompensationLists = prodSearchService.queryProdCompensationList(prodProductInfo.getPlat_no(),prodProductInfo.getProd_id());

				for(ProdCompensationList prodCompensationList : prodCompensationLists){

					if(prodCompensationList.getPlatcust().equals(compensateRepayRequest.getCompensation_platcust())) {
						check_platcust = true;

						break;
					}
				}
				if(!check_platcust){
					logger.info( "【借款人还款代偿（委托）】，代偿账户没有报备");
					throw new BusinessException(BusinessMsg.PARAMETER_ERROR,BusinessMsg.getMsg(BusinessMsg.PARAMETER_ERROR));
				}

				//查询代偿人的账户
				compensationAccountSubjectInfo = accountQueryService.queryAccount(compensateRepayRequest.getMer_no(),compensateRepayRequest.getCompensation_platcust(), Tsubject.CASH.getCode(),Ssubject.FINANCING.getCode());

				if(compensationAccountSubjectInfo == null){
					logger.info("【借款人还款代偿（委托）】代偿人账户不存在");
					throw new BusinessException(BusinessMsg.INVALID_ACCOUNT,BusinessMsg.getMsg(BusinessMsg.INVALID_ACCOUNT) + "代偿人账户不存在");
				}
				prodCompensationRepay.setCompensation_platcust(compensateRepayRequest.getCompensation_platcust());
				eaccAccountamtlist.setRemark("借款人还款给代偿人");
			}else if(CompensationType.DXJDC.getCode().equals(compensateRepayRequest.getCompensation_type())){
				//查询风险金代偿客户编号
				compensationAccountSubjectInfo = accountQueryService.queryAccount(compensateRepayRequest.getMer_no(),compensateRepayRequest.getMer_no(), Tsubject.CASH.getCode(),Ssubject.DEPOSIT.getCode());
				if(compensationAccountSubjectInfo==null){
					logger.info("【借款人还款代偿（委托）】风险金账户不存在");
					throw new BusinessException(BusinessMsg.INVALID_ACCOUNT,BusinessMsg.getMsg(BusinessMsg.INVALID_ACCOUNT) + "风险金账户不存在");
				}

				prodCompensationRepay.setCompensation_platcust(compensationAccountSubjectInfo.getPlatcust());
				eaccAccountamtlist.setRemark("借款人还款给风险代偿");
			}else{
				logger.info("【借款人还款代偿（委托）】代偿人类型不正确");
				throw new BusinessException(BusinessMsg.PARAMETER_ERROR,BusinessMsg.getMsg(BusinessMsg.PARAMETER_ERROR) + "代偿人类型不正确");
			}

			prodCompensationRepay.setTrans_serial(transTransReq.getTrans_serial());
			prodCompensationRepay.setPlat_no(accountSubjectInfo.getPlat_no());
			prodCompensationRepay.setProd_id(compensateRepayRequest.getProd_id());
			prodCompensationRepay.setRepay_amt(compensateRepayRequest.getRepay_amt());
			prodCompensationRepay.setOrder_no(compensateRepayRequest.getOrder_no());
			prodCompensationRepay.setEnabled(Constants.ENABLED);
			prodCompensationRepay.setCreate_time(new Date());
			prodCompensationRepay.setUpdate_time(new Date());
			prodCompensationRepayMapper.insert(prodCompensationRepay);

			//转账      借款人转到代偿人
			eaccAccountamtlist.setTrans_serial(transTransReq.getTrans_serial());
			eaccAccountamtlist.setOrder_no(transTransReq.getOrder_no());
			eaccAccountamtlist.setPlat_no(accountSubjectInfo.getPlat_no());
			eaccAccountamtlist.setPlatcust(accountSubjectInfo.getPlatcust());
			eaccAccountamtlist.setSubject(accountSubjectInfo.getSubject());
			eaccAccountamtlist.setSub_subject(accountSubjectInfo.getSub_subject());
			eaccAccountamtlist.setTrans_code(TransConsts.BORROWREPAY_COMPENSATORYREPAY_CODE);
			eaccAccountamtlist.setTrans_name(TransConsts.BORROWREPAY_COMPENSATORYREPAY_NAME);
			eaccAccountamtlist.setAmt_type(AmtType.EXPENSIVE.getCode());
			eaccAccountamtlist.setOppo_plat_no(compensationAccountSubjectInfo.getPlat_no());
			eaccAccountamtlist.setOppo_platcust(compensationAccountSubjectInfo.getPlatcust());
			eaccAccountamtlist.setOppo_subject(compensationAccountSubjectInfo.getSubject());
			eaccAccountamtlist.setOppo_sub_subject(compensationAccountSubjectInfo.getSub_subject());
			eaccAccountamtlist.setAmt(compensateRepayRequest.getRepay_amt());
			eaccAccountamtlist.setTrans_date(transTransReq.getTrans_date());
			eaccAccountamtlist.setTrans_time(transTransReq.getTrans_time());
			eaccAccountamtlists.add(eaccAccountamtlist);

			//转手续费
			if(compensateRepayRequest.getFee_amt()!=null && compensateRepayRequest.getFee_amt().compareTo(BigDecimal.ZERO)>0){
				AccountSubjectInfo accountSubjectInfoFee = accountQueryService.queryAccount(compensateRepayRequest.getMer_no(),compensateRepayRequest.getMer_no(), Tsubject.CASH.getCode(),Ssubject.DEPOSIT.getCode());
				if(accountSubjectInfoFee==null){
					logger.info("【借款人还款代偿（委托）】手续费账户不存在");
					throw new BusinessException(BusinessMsg.INVALID_ACCOUNT,BusinessMsg.getMsg(BusinessMsg.INVALID_ACCOUNT) + "手续费账户不存在");
				}
				eaccAccountamtlist = new EaccAccountamtlist();
				eaccAccountamtlist.setTrans_serial(transTransReq.getTrans_serial());
				eaccAccountamtlist.setOrder_no(transTransReq.getOrder_no());
				eaccAccountamtlist.setPlat_no(accountSubjectInfo.getPlat_no());
				eaccAccountamtlist.setPlatcust(accountSubjectInfo.getPlatcust());
				eaccAccountamtlist.setSubject(accountSubjectInfo.getSubject());
				eaccAccountamtlist.setSub_subject(accountSubjectInfo.getSub_subject());
				eaccAccountamtlist.setTrans_code(TransConsts.BORROWREPAY_COMPENSATORYREPAY_CODE);
				eaccAccountamtlist.setTrans_name(TransConsts.BORROWREPAY_COMPENSATORYREPAY_NAME);
				eaccAccountamtlist.setAmt_type(AmtType.EXPENSIVE.getCode());
				eaccAccountamtlist.setOppo_plat_no(accountSubjectInfoFee.getPlat_no());
				eaccAccountamtlist.setOppo_platcust(accountSubjectInfoFee.getPlatcust());
				eaccAccountamtlist.setOppo_subject(accountSubjectInfoFee.getSubject());
				eaccAccountamtlist.setOppo_sub_subject(Ssubject.FEE.getCode());
				eaccAccountamtlist.setAmt(compensateRepayRequest.getFee_amt());
				eaccAccountamtlist.setRemark("借款人还款给代偿转手续费");
				eaccAccountamtlist.setTrans_date(transTransReq.getTrans_date());
				eaccAccountamtlist.setTrans_time(transTransReq.getTrans_time());
				eaccAccountamtlists.add(eaccAccountamtlist);
			}
			//批量转账
			accountTransferService.batchTransfer(eaccAccountamtlists);

			//更新业务流水
			transTransReq.setReturn_code(BusinessMsg.SUCCESS);
			transTransReq.setReturn_msg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));
			transTransReq.setStatus(OrderStatus.SUCCESS.getCode());
			transReqService.insert(transTransReq);

			compensateRepayResponse.setRecode(BusinessMsg.SUCCESS);
			compensateRepayResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));
			OrderData orderData = new OrderData();
			orderData.setOrder_no(compensateRepayRequest.getOrder_no());
			orderData.setOrder_status(OrderStatus.SUCCESS.getCode());
			orderData.setProcess_date(DateUtils.todayStr(DateUtils.DEF_FORMAT));
			orderData.setQuery_id(transTransReq.getTrans_serial());
			compensateRepayResponse.setOrderData(orderData);
			compensateRepayResponse.setSign(compensateRepayRequest.getSign());

		}catch (Exception e){

			logger.error("【借款人还款代偿（委托）】：",e);

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
			throw new BusinessException(baseResponse.getRecode(),baseResponse.getRemsg());
		}
		return compensateRepayResponse;
	}

	/*
     * 融资人分账
     * wuml
    */
	@Override
	public BorrowerSubAccountResponse sub_account(BorrowerSubAccountRequest borrowerSubAccountRequest) throws BusinessException {
		OrderData orderData = new OrderData();
		orderData.setOrder_status(OrderStatus.FAIL.getCode());

		BorrowerSubAccountResponse borrowerSubAccountResponse=new BorrowerSubAccountResponse();
		borrowerSubAccountResponse.setRecode(BusinessMsg.FAIL);
		borrowerSubAccountResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.FAIL));

		logger.info("**********【"+borrowerSubAccountRequest.getOrder_no()+"】融资人分账>>开始...**********");
		TransTransreq transTransReq = transReqService.getTransTransReq(borrowerSubAccountRequest.clone(),TransConsts.BORROW_SUB_ACCOUNT_CODE,TransConsts.BORROW_SUB_ACCOUNT_NAME, false);
		transReqService.insert(transTransReq);
		orderData.setQuery_id(transTransReq.getTrans_serial());

		try {
			ProdProductinfo prodInfo = prodSearchService.queryProdInfo(borrowerSubAccountRequest.getMer_no(),borrowerSubAccountRequest.getProd_id());
			if(prodInfo==null){
				logger.error("**********【"+borrowerSubAccountRequest.getOrder_no()+"】融资人分账>>查询不到对应标信息**********");
				borrowerSubAccountResponse.setRecode(BusinessMsg.PARAMETER_ERROR);
				String errorMsg = String.format("根据平台编号【%s】和产品编号【%s】查询不到对应标信息", borrowerSubAccountRequest.getMer_no(),borrowerSubAccountRequest.getProd_id());
				borrowerSubAccountResponse.setRemsg(errorMsg);
				throw new BusinessException(borrowerSubAccountResponse);
			}else if (!ProdState.FOUND.getCode().equals(prodInfo.getProd_state())){
				logger.error("**********【"+borrowerSubAccountRequest.getOrder_no()+"】融资人分账>>标的非成立状态，不能分账**********");
				borrowerSubAccountResponse.setRecode(BusinessMsg.PARAMETER_ERROR);
				borrowerSubAccountResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.PARAMETER_ERROR)+",标的非成立状态，不能分账");
				throw new BusinessException(borrowerSubAccountResponse);
			}
			//查询融资人信息
//			EaccFinancinfoExample example=new EaccFinancinfoExample();
//			EaccFinancinfoExample.Criteria criteria=example.createCriteria();
//			criteria.andProd_idEqualTo(prodInfo.getProd_id());
//			criteria.andPlat_noEqualTo(prodInfo.getPlat_no());
//			criteria.andEnabledEqualTo(Constants.ENABLED);
//			List<EaccFinancinfo> eaccFinancinfos = eaccFinancinfoMapper.selectByExample(example);
			List<EaccFinancinfo> eaccFinancinfos=prodSearchService.queryEaccFinancinfos(prodInfo.getPlat_no(),prodInfo.getProd_id());
			if(null==eaccFinancinfos||1!=eaccFinancinfos.size()){
				logger.error("**********【"+borrowerSubAccountRequest.getOrder_no()+"】融资人分账>>查询不到对应融资人或融资人数量大于1**********");
				borrowerSubAccountResponse.setRecode(BusinessMsg.PARAMETER_ERROR);
				String errorMsg = String.format("根据平台编号【%s】和产品编号【%s】查询不到对应融资人信息", borrowerSubAccountRequest.getMer_no(),borrowerSubAccountRequest.getProd_id());
				borrowerSubAccountResponse.setRemsg(errorMsg);
				throw new BusinessException(borrowerSubAccountResponse);
			}

			//校验分佣客户分佣
			List<SubdataObject> subdataObjectList=borrowerSubAccountRequest.getSubdataObjectList();
			BigDecimal sub_amt=BigDecimal.ZERO;
			if(null!=subdataObjectList&&subdataObjectList.size()>0){
				boolean check_platcust = false;
				List<ProdCompensationList> prodCompensationLists = prodSearchService.queryProdCompensationList(borrowerSubAccountRequest.getMer_no(),borrowerSubAccountRequest.getProd_id());
				for(SubdataObject sItem:subdataObjectList){
					if (null == sItem.getAmt() || -1 == new BigDecimal(sItem.getAmt().toString()).compareTo(BigDecimal.ZERO)) {
						logger.info( "---------------subdata里amt金额参数不能小于0");
						borrowerSubAccountResponse.setRecode(BusinessMsg.PARAMETER_ERROR);
						borrowerSubAccountResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.PARAMETER_ERROR) + ",subdata里amt金额参数不能小于0");
						throw new BusinessException(borrowerSubAccountResponse);
					} else {
						for (ProdCompensationList prodCompensationList : prodCompensationLists) {
							if (prodCompensationList.getPlatcust().equals(sItem.getPlat_cust())) {
								sub_amt = sub_amt.add(new BigDecimal(sItem.getAmt()));
								check_platcust = true;
								break;
							}
						}
					}
					if(!check_platcust){
						logger.info( "---------------代偿人账户不存在");
						borrowerSubAccountResponse.setRecode(BusinessMsg.PARAMETER_ERROR);
						borrowerSubAccountResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.PARAMETER_ERROR)+",代偿人账户没有报备");
						throw new BusinessException(borrowerSubAccountResponse);
					}
				}
			}
			/*else if(!eaccFinancinfos.get(0).getTrustee_platcust().equals(SubdataObjectList.get(0).getPlat_cust())){
				borrowerSubAccountResponse.setRecode(BusinessMsg.PARAMETER_ERROR);
				String errorMsg = String.format("根据平台编号【%s】和产品编号【%s】查询不到对应分账账户信息", borrowerSubAccountRequest.getMer_no(),borrowerSubAccountRequest.getProd_id());
				borrowerSubAccountResponse.setRemsg(errorMsg);
				throw new BusinessException(borrowerSubAccountResponse);
			}*/

			//校验手续费分佣
			FunddataObject funddataObject = borrowerSubAccountRequest.getFunddataObject();//分佣说明
			BigDecimal payoutAmt = BigDecimal.ZERO;
			if(null!=funddataObject){
				//验证现金在途转账标志参数是否合规
				if(!Tsubject.CASH.getCode().equals(funddataObject.getPayout_plat_type()) && !Tsubject.FLOAT.getCode().equals(funddataObject.getPayout_plat_type())){
					borrowerSubAccountResponse.setRecode(BusinessMsg.PARAMETER_ERROR);
					String errorMsg = String.format("传入的手续费现金在途标志payout_plat_type【%s】参数错误",funddataObject.getPayout_plat_type());
					borrowerSubAccountResponse.setRemsg(errorMsg);
					throw new BusinessException(borrowerSubAccountResponse);
				}
				if(null==funddataObject.getPayout_amt()||-1==funddataObject.getPayout_amt().compareTo(BigDecimal.ZERO)){
					logger.info( "---------------funddata里payout_amt金额参数不能小于0");
					borrowerSubAccountResponse.setRecode(BusinessMsg.PARAMETER_ERROR);
					borrowerSubAccountResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.PARAMETER_ERROR)+",funddata里payout_amt金额参数不能小于0");
					throw new BusinessException(borrowerSubAccountResponse);
				}
				payoutAmt = funddataObject.getPayout_amt();//手续费钱
			}
			//校验风险金分佣
			List<PlatSubData> platSubDataObject=borrowerSubAccountRequest.getPlatSubDataObject();
			BigDecimal plat_sub_amt = BigDecimal.ZERO;
			if(null!=platSubDataObject&&platSubDataObject.size()>0){
				if(null==platSubDataObject.get(0).getAmt()||-1==platSubDataObject.get(0).getAmt().compareTo(BigDecimal.ZERO)){
					logger.info( "---------------plat_subdata里amt金额参数不能小于0");
					borrowerSubAccountResponse.setRecode(BusinessMsg.PARAMETER_ERROR);
					borrowerSubAccountResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.PARAMETER_ERROR)+",plat_subdata里amt金额参数不能小于0");
					throw new BusinessException(borrowerSubAccountResponse);
				}
				plat_sub_amt=platSubDataObject.get(0).getAmt();
			}
			BigDecimal trans_amt=borrowerSubAccountRequest.getAmt();
			if(null==trans_amt||-1==trans_amt.compareTo(BigDecimal.ZERO)){
				logger.info( "---------------amt金额参数不能小于0");
				borrowerSubAccountResponse.setRecode(BusinessMsg.PARAMETER_ERROR);
				borrowerSubAccountResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.PARAMETER_ERROR)+",amt金额参数不能小于0");
				throw new BusinessException(borrowerSubAccountResponse);
			}
		/*	String lockKey = getLockKey(borrowerSubAccountRequest.getMer_no()+borrowerSubAccountRequest.getProd_id()+TransConsts.BORROW_SUB_ACCOUNT_CODE);
			boolean lock = CacheUtil.getLock(lockKey);
			while (!lock) {
				sleep(50);
				lock = CacheUtil.getLock(lockKey);
			}*/
			List<EaccAccountamtlist> eaccAccountamtlists = new ArrayList<EaccAccountamtlist>();
			//查询借款人账户
			List<AccountSubjectInfo> accountSubjectInfoList = accountQueryService.queryPlatAccountList(eaccFinancinfos.get(0).getPlat_no(), eaccFinancinfos.get(0).getPlatcust(), null, Ssubject.FINANCING.getCode());

			if (1 == trans_amt.compareTo(BigDecimal.ZERO)) {
				//分账客户的账户
				if (null == eaccFinancinfos.get(0).getTrustee_platcust() || "".equals(eaccFinancinfos.get(0).getTrustee_platcust())) {
					logger.info("---------------受托账户未登记");
					borrowerSubAccountResponse.setRecode(BusinessMsg.INVALID_ACCOUNT);
					borrowerSubAccountResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.INVALID_ACCOUNT) + ",受托账户未登记");
					throw new BusinessException(borrowerSubAccountResponse);
				}
				List<AccountSubjectInfo> trust_accountSubjectInfoList = accountQueryService.queryPlatAccountList(eaccFinancinfos.get(0).getPlat_no(), eaccFinancinfos.get(0).getTrustee_platcust(), null, Ssubject.FINANCING.getCode());
				BigDecimal cashExtractSum_source = BigDecimal.ZERO;//借款人现金账户余额
				BigDecimal floatExtractSum_source = BigDecimal.ZERO;//借款人在途账户余额
				if (null == accountSubjectInfoList || 0 == accountSubjectInfoList.size() || null == trust_accountSubjectInfoList || 0 == trust_accountSubjectInfoList.size()) {
					logger.info("-----------借款人或受托人客户号不存在");

					borrowerSubAccountResponse.setRecode(BusinessMsg.INVALID_ACCOUNT);
					borrowerSubAccountResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.INVALID_ACCOUNT) + ",借款人或受托人客户号不存在");
					throw new BusinessException(borrowerSubAccountResponse);
				}
				for (AccountSubjectInfo accountSubjectInfo : accountSubjectInfoList) {
					if (Tsubject.CASH.getCode().equals(accountSubjectInfo.getSubject())) {
						cashExtractSum_source = cashExtractSum_source.add(accountSubjectInfo.getN_balance());
					}
					if (Tsubject.FLOAT.getCode().equals(accountSubjectInfo.getSubject())) {
						floatExtractSum_source = floatExtractSum_source.add(accountSubjectInfo.getN_balance());
					}
				}
				if (-1 == cashExtractSum_source.add(floatExtractSum_source).compareTo(sub_amt.add(payoutAmt).add(plat_sub_amt).add(trans_amt))) {
					borrowerSubAccountResponse.setRecode(BusinessMsg.SUM_NOTENOUGH);
					String errorMsg = String.format("分账金额不足，借款人可用余额【%s】", (cashExtractSum_source.add(floatExtractSum_source)).toString());
					borrowerSubAccountResponse.setRemsg(errorMsg);
					logger.info(errorMsg);
					throw new BusinessException(borrowerSubAccountResponse);
				}
				//融资人分账到分账客户
				logger.info("**********【" + borrowerSubAccountRequest.getOrder_no() + "】融资分账>>融资人分账到分账客户开始**********");
				EaccAccountamtlist eaccAccountamtlist = new EaccAccountamtlist();
				eaccAccountamtlist.setOrder_no(borrowerSubAccountRequest.getOrder_no());
				eaccAccountamtlist.setTrans_serial(transTransReq.getTrans_serial());
				eaccAccountamtlist.setPlat_no(accountSubjectInfoList.get(0).getPlat_no());
				eaccAccountamtlist.setPlatcust(accountSubjectInfoList.get(0).getPlatcust());
				eaccAccountamtlist.setSubject(Tsubject.CASH.getCode());
				eaccAccountamtlist.setSub_subject(Ssubject.FINANCING.getCode());
				eaccAccountamtlist.setTrans_code(TransConsts.BORROW_SUB_TRUSTACCOUNT_CODE);
				eaccAccountamtlist.setTrans_name(TransConsts.BORROW_SUB_TRUSTACCOUNT_NAME);
				eaccAccountamtlist.setAmt_type(AmtType.EXPENSIVE.getCode());
				eaccAccountamtlist.setOppo_plat_no(accountSubjectInfoList.get(0).getPlat_no());
				eaccAccountamtlist.setOppo_platcust(eaccFinancinfos.get(0).getTrustee_platcust());
				eaccAccountamtlist.setOppo_subject(Tsubject.CASH.getCode());
				eaccAccountamtlist.setOppo_sub_subject(Ssubject.FINANCING.getCode());
				eaccAccountamtlist.setAmt(trans_amt);
				eaccAccountamtlist.setRemark("借款人转分账客户分佣");
				eaccAccountamtlist.setTrans_date(transTransReq.getTrans_date());
				eaccAccountamtlist.setTrans_time(transTransReq.getTrans_time());
				eaccAccountamtlists.add(eaccAccountamtlist);
				logger.info("**********【" + borrowerSubAccountRequest.getOrder_no() + "】融资分账>>融资人分账到分账客户结束**********");
			}


			if(1==payoutAmt.compareTo(BigDecimal.ZERO)){
				AccountSubjectInfo accSubjectInfo =  accountQueryService.queryAccount(eaccFinancinfos.get(0).getPlat_no(),eaccFinancinfos.get(0).getPlat_no(),funddataObject.getPayout_plat_type(),Ssubject.FEE.getCode());
				if(accSubjectInfo==null){
					logger.info( "---------------查询不到手续费账户");
					borrowerSubAccountResponse.setRecode(BusinessMsg.INVALID_ACCOUNT);
					borrowerSubAccountResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.INVALID_ACCOUNT)+",查询不到手续费账户");
					throw new BusinessException(borrowerSubAccountResponse);
				}
				logger.info("**********【"+borrowerSubAccountRequest.getOrder_no()+"】融资分账>>融资人分账到平台手续费开始**********");
				EaccAccountamtlist eaccAccountamtlist_platfree = new EaccAccountamtlist();
				eaccAccountamtlist_platfree.setOrder_no(borrowerSubAccountRequest.getOrder_no());
				eaccAccountamtlist_platfree.setTrans_serial(transTransReq.getTrans_serial());
				eaccAccountamtlist_platfree.setPlat_no(accountSubjectInfoList.get(0).getPlat_no());
				eaccAccountamtlist_platfree.setPlatcust(accountSubjectInfoList.get(0).getPlatcust());
				eaccAccountamtlist_platfree.setSubject(funddataObject.getPayout_plat_type());
				eaccAccountamtlist_platfree.setSub_subject(Ssubject.FINANCING.getCode());
				eaccAccountamtlist_platfree.setTrans_code(TransConsts.BORROW_SUB_FEEACCOUNT_CODE);
				eaccAccountamtlist_platfree.setTrans_name(TransConsts.BORROW_SUB_FEEACCOUNT_NAME);
				eaccAccountamtlist_platfree.setAmt_type(AmtType.EXPENSIVE.getCode());
				eaccAccountamtlist_platfree.setOppo_plat_no(accountSubjectInfoList.get(0).getPlat_no());
				eaccAccountamtlist_platfree.setOppo_platcust(accSubjectInfo.getPlatcust());
				eaccAccountamtlist_platfree.setOppo_subject(accSubjectInfo.getSubject());
				eaccAccountamtlist_platfree.setOppo_sub_subject(accSubjectInfo.getSub_subject());
				eaccAccountamtlist_platfree.setAmt(payoutAmt);
				eaccAccountamtlist_platfree.setRemark("借款人转手续费分佣");
				eaccAccountamtlist_platfree.setTrans_date(transTransReq.getTrans_date());
				eaccAccountamtlist_platfree.setTrans_time(transTransReq.getTrans_time());
				eaccAccountamtlists.add(eaccAccountamtlist_platfree);
				logger.info("**********【"+borrowerSubAccountRequest.getOrder_no()+"】融资分账>>融资人分账到平台手续费结束**********");
			}

			if(1==plat_sub_amt.compareTo(BigDecimal.ZERO)){
				logger.info("**********【"+borrowerSubAccountRequest.getOrder_no()+"】融资分账>>融资人分账到风险金开始**********");
				AccountSubjectInfo fxjAccountSubjectInfo = accountQueryService.queryAccount(eaccFinancinfos.get(0).getPlat_no(),eaccFinancinfos.get(0).getPlat_no(), Tsubject.CASH.getCode(),Ssubject.DEPOSIT.getCode());
				if(fxjAccountSubjectInfo==null){
					logger.info( "---------------查询不到风险金账户");
					borrowerSubAccountResponse.setRecode(BusinessMsg.INVALID_ACCOUNT);
					borrowerSubAccountResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.INVALID_ACCOUNT)+",查询不到风险金账户");
					throw new BusinessException(borrowerSubAccountResponse);
				}
				EaccAccountamtlist eaccAccountamtlist_fxj = new EaccAccountamtlist();
				eaccAccountamtlist_fxj.setOrder_no(borrowerSubAccountRequest.getOrder_no());
				eaccAccountamtlist_fxj.setTrans_serial(transTransReq.getTrans_serial());
				eaccAccountamtlist_fxj.setPlat_no(accountSubjectInfoList.get(0).getPlat_no());
				eaccAccountamtlist_fxj.setPlatcust(accountSubjectInfoList.get(0).getPlatcust());
				eaccAccountamtlist_fxj.setSubject(Tsubject.CASH.getCode());
				eaccAccountamtlist_fxj.setSub_subject(Ssubject.FINANCING.getCode());
				eaccAccountamtlist_fxj.setTrans_code(TransConsts.BORROW_SUB_DEPOSITACCOUNT_CODE);
				eaccAccountamtlist_fxj.setTrans_name(TransConsts.BORROW_SUB_DEPOSITACCOUNT_NAME);
				eaccAccountamtlist_fxj.setAmt_type(AmtType.EXPENSIVE.getCode());
				eaccAccountamtlist_fxj.setOppo_plat_no(fxjAccountSubjectInfo.getPlat_no());
				eaccAccountamtlist_fxj.setOppo_platcust(fxjAccountSubjectInfo.getPlatcust());
				eaccAccountamtlist_fxj.setOppo_subject(fxjAccountSubjectInfo.getSubject());
				eaccAccountamtlist_fxj.setOppo_sub_subject(fxjAccountSubjectInfo.getSub_subject());
				eaccAccountamtlist_fxj.setAmt(plat_sub_amt);
				eaccAccountamtlist_fxj.setRemark("借款人转风险金分佣");
				eaccAccountamtlist_fxj.setTrans_date(transTransReq.getTrans_date());
				eaccAccountamtlist_fxj.setTrans_time(transTransReq.getTrans_time());
				eaccAccountamtlists.add(eaccAccountamtlist_fxj);

				logger.info("**********【"+borrowerSubAccountRequest.getOrder_no()+"】融资分账>>融资人分账到风险金结束**********");
			}
			if(null!=subdataObjectList&&0<subdataObjectList.size()){
				logger.info("**********【"+borrowerSubAccountRequest.getOrder_no()+"】融资分账>>融资人分账到代偿人开始**********");
				for(SubdataObject sItem:subdataObjectList){
					EaccAccountamtlist eaccAccountamtlist_each = new EaccAccountamtlist();
					eaccAccountamtlist_each.setOrder_no(borrowerSubAccountRequest.getOrder_no());
					eaccAccountamtlist_each.setTrans_serial(transTransReq.getTrans_serial());
					eaccAccountamtlist_each.setPlat_no(accountSubjectInfoList.get(0).getPlat_no());
					eaccAccountamtlist_each.setPlatcust(accountSubjectInfoList.get(0).getPlatcust());
					eaccAccountamtlist_each.setSubject(Tsubject.CASH.getCode());
					eaccAccountamtlist_each.setSub_subject(Ssubject.FINANCING.getCode());
					eaccAccountamtlist_each.setTrans_code(TransConsts.BORROW_SUB_COMACCOUNT_CODE);
					eaccAccountamtlist_each.setTrans_name(TransConsts.BORROW_SUB_COMACCOUNT_NAME);
					eaccAccountamtlist_each.setAmt_type(AmtType.EXPENSIVE.getCode());
					eaccAccountamtlist_each.setOppo_plat_no(accountSubjectInfoList.get(0).getPlat_no());
					eaccAccountamtlist_each.setOppo_platcust(sItem.getPlat_cust());
					eaccAccountamtlist_each.setOppo_subject(Tsubject.CASH.getCode());
					eaccAccountamtlist_each.setOppo_sub_subject(Ssubject.FINANCING.getCode());
					eaccAccountamtlist_each.setAmt(new BigDecimal(sItem.getAmt().toString()));
					eaccAccountamtlist_each.setRemark("借款人转代偿人分佣");
					eaccAccountamtlist_each.setTrans_date(transTransReq.getTrans_date());
					eaccAccountamtlist_each.setTrans_time(transTransReq.getTrans_time());
					eaccAccountamtlists.add(eaccAccountamtlist_each);
				}
				logger.info("**********【"+borrowerSubAccountRequest.getOrder_no()+"】融资分账>>融资人分账到代偿人结束**********");
			}
			//调用转账新接口
			logger.info("**********调用转账接口，转账列表：**********"+ JSON.toJSON(eaccAccountamtlists));
			if(null!=eaccAccountamtlists &&eaccAccountamtlists.size()>0){
				newAccountTransferExecuteService.batchTransfer(eaccAccountamtlists);
				//accountTransferService.batchTransfer(eaccAccountamtlists);
			}

			orderData.setProcess_date(DateUtils.todayStr(DateUtils.DEF_FORMAT_STRING));
			orderData.setOrder_status(OrderStatus.SUCCESS.getCode());
			orderData.setOrder_no(borrowerSubAccountRequest.getOrder_no());
			borrowerSubAccountResponse.setOrderData(orderData);
			borrowerSubAccountResponse.setRecode(BusinessMsg.SUCCESS);
			borrowerSubAccountResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));

			//更新业务流水
			transTransReq.setReturn_code(BusinessMsg.SUCCESS);
			transTransReq.setReturn_msg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));
			transTransReq.setStatus(OrderStatus.SUCCESS.getCode());
			transReqService.insert(transTransReq);
		}
		catch (Exception e){
			logger.error(e);
			if(e instanceof  BusinessException){
				borrowerSubAccountResponse.setRecode(((BusinessException) e).getBaseResponse().getRecode());
				borrowerSubAccountResponse.setRemsg(((BusinessException) e).getBaseResponse().getRemsg());
			}else {
                borrowerSubAccountResponse.setRecode(BusinessMsg.DATEBASE_EXCEPTION);
                borrowerSubAccountResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.DATEBASE_EXCEPTION));
            }

			transTransReq.setReturn_code(borrowerSubAccountResponse.getRecode());
			transTransReq.setReturn_msg(borrowerSubAccountResponse.getRemsg());
			transTransReq.setStatus(FlowStatus.FAIL.getCode());
			transReqService.insert(transTransReq);
			throw new BusinessException(borrowerSubAccountResponse);
		}/*finally {
			CacheUtil.unlock(lockKey);
		}*/

		return borrowerSubAccountResponse;
	}


	/**
	 * 查询长时间处理中的借款人还款代偿，标的代偿还款
	 */
	@Override
	public List<TransTransreq> queryAllProcessOrder() throws BusinessException {
		logger.info("【补偿开始】时间:"+DateUtils.todayStr());
		TransTransreqExample transTransreqExample=new TransTransreqExample();
		TransTransreqExample.Criteria transTransreqCriteria=transTransreqExample.createCriteria();
		transTransreqCriteria.andCreate_timeBetween(DateUtils.getYesterday(),DateUtils.addHour(DateUtils.getNow(),-2));
		List<String> transCodeList=new ArrayList<>();
		transCodeList.add(TransConsts.PRODCOMPENSATORYREPAY_CODE);
		transCodeList.add(TransConsts.BORROWREPAY_COMPENSATORYREPAY_CODE);
		transTransreqCriteria.andTrans_codeIn(transCodeList);
		transTransreqCriteria.andStatusEqualTo(FlowStatus.INPROCESS.getCode());
		transTransreqCriteria.andEnabledEqualTo(Constants.ENABLED);
		List<TransTransreq> processOrders=transTransreqMapper.selectByExample(transTransreqExample);
		return processOrders;
	}

	/**
	 * 补偿借款人还款代偿，标的代偿还款
	 * @throws BusinessException
	 */
	@Override
	public void doAllProcessOrder(TransTransreq transTransreq) throws BusinessException {
		if(StringUtils.isBlank(transTransreq.getOrder_no()))
			return;
		logger.info("【补偿】流水号:"+transTransreq.getTrans_serial());
		List<EaccAccountamtlist> eaccAccountamtlists = accountTransferService.queryEaccAccountamtlistByTransSerial(transTransreq.getTrans_serial());
		if(eaccAccountamtlists.size() > 0){
			transTransreq.setStatus(OrderStatus.SUCCESS.getCode());
			transTransreq.setReturn_code(BusinessMsg.SUCCESS);
			transTransreq.setReturn_msg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));
			transTransreqMapper.updateByPrimaryKey(transTransreq);
			logger.info("【补偿】有交易，修改状态为成功，流水号:"+transTransreq.getTrans_serial());
		}else {
			transTransreq.setStatus(OrderStatus.FAIL.getCode());
			transTransreq.setReturn_code(BusinessMsg.FAIL);
			transTransreq.setReturn_msg(BusinessMsg.getMsg(BusinessMsg.FAIL));
			transTransreqMapper.updateByPrimaryKey(transTransreq);
			logger.info("【补偿】无交易，修改状态为失败，流水号:"+transTransreq.getTrans_serial());
		}
	}




}
