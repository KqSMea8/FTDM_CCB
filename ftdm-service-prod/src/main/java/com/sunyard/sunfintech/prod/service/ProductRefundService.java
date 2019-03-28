package com.sunyard.sunfintech.prod.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.sunyard.sunfintech.account.model.bo.BatchPayDetail;
import com.sunyard.sunfintech.account.model.bo.BatchPayQueryResponseDataDetail;
import com.sunyard.sunfintech.account.model.bo.BatchPayResponse;
import com.sunyard.sunfintech.account.provider.IAccountQueryService;
import com.sunyard.sunfintech.account.provider.IAccountTransferService;
import com.sunyard.sunfintech.account.provider.IAccountTransferThirdParty;
import com.sunyard.sunfintech.core.Constants;
import com.sunyard.sunfintech.core.TransConsts;
import com.sunyard.sunfintech.core.base.BaseRequest;
import com.sunyard.sunfintech.core.base.BaseResponse;
import com.sunyard.sunfintech.core.base.BaseServiceSimple;
import com.sunyard.sunfintech.core.config.GlobalConfig;
import com.sunyard.sunfintech.core.dic.*;
import com.sunyard.sunfintech.core.exception.BusinessException;
import com.sunyard.sunfintech.core.exception.BusinessMsg;
import com.sunyard.sunfintech.core.threads.SingleThreadPool;
import com.sunyard.sunfintech.core.util.CacheUtil;
import com.sunyard.sunfintech.core.util.DateUtils;
import com.sunyard.sunfintech.core.util.SeqUtil;
import com.sunyard.sunfintech.core.util.SysParamterKey;
import com.sunyard.sunfintech.custdao.mapper.CustBatchRepayDetailMapper;
import com.sunyard.sunfintech.dao.entity.*;
import com.sunyard.sunfintech.dao.mapper.*;
import com.sunyard.sunfintech.prod.autoexecute.RefundAutoExecute;
import com.sunyard.sunfintech.prod.job.RefundJobAction;
import com.sunyard.sunfintech.prod.model.bo.*;
import com.sunyard.sunfintech.prod.provider.*;
import com.sunyard.sunfintech.prod.utils.ProductPublicParams;
import com.sunyard.sunfintech.pub.provider.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Connection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

/**
 *
 * 2017-5-14
 *
 * @author dingy
 *
 */
@Service(interfaceClass = IProductRefundService.class)
@CacheConfig(cacheNames = "productRefundService")
@org.springframework.stereotype.Service("productRefundService")
public class ProductRefundService extends BaseServiceSimple implements IProductRefundService {

	/**
	 * 记录交易流水
	 */
	@Autowired
	private ITransReqService iTransReqService;
	/**
	 * 标的信息校验
	 */
	@Autowired
	private ProdShareInallMapper prodShareInallMapper;
	/**
	 * 更新还款计划表
	 */
	@Autowired
	private ProdProdrepayMapper prodRepayMapper;

	@Autowired
	private ProdRepaymentlistMapper repayMentListMapper;

	@Autowired
	private IProdSearchService prodSearchService;

	@Autowired
	private IAccountQueryService accountQueryService;

	@Autowired
	private IAccountTransferService accountTransferService;

	@Autowired
	private ProdShareListMapper prodShareListMapper;

//	@Autowired
//	private EaccUserinfoMapper eaccUserinfoMapper;

	@Autowired
	private ISysParameterService sysParameterService;
	@Autowired
	private ProdCompensationInfoMapper prodCompensationInfoMapper;

	@Autowired
	private ProdCompensationRepayMapper prodCompensationRepayMapper;

	//TODO 杨磊
//	@Autowired
//	private ISendMsgService sendMsgService;

	@Autowired
	private IProdShareOptionService prodShareOptionService;

	@Autowired
	private IProductRefundExtService productRefundExtService;

	@Autowired
	private IAccountTransferThirdParty accountTransferThirdParty;

	@Autowired
	private IEaccTransTransreqService eaccTransTransreqService;

	@Autowired
	private IProdMQOptionService prodMQOptionService;

	@Resource(name = "refundJobAction")
	private RefundJobAction refundJobAction;

	@Resource(name = "refundAutoExecute")
	private RefundAutoExecute refundAutoExecute;

	@Autowired
	private IOrderCheck orderCheck;

	@Autowired
	private EaccClearErrorInfoMapper eaccClearErrorInfoMapper;

	@Autowired
	private ISendMsgService sendMsgService;

	@Autowired
	private CustBatchRepayDetailMapper custBatchRepayDetailMapper;

	@Autowired
	private BatchRepayDetailMapper batchRepayDetailMapper;


	/**
	 * 借款人还款计划更新 2017年5月13日
	 * @author pengziyuan
	 * @param prodUpdateRepaymentPlanRequest
	 * @return ProdUpdateRepaymentPlanResponse
	 */
	@Override
	@Transactional
	public ProdUpdateRepaymentPlanResponse updateRefundPlan(ProdUpdateRepaymentPlanRequest prodUpdateRepaymentPlanRequest) throws BusinessException {

		ProdUpdateRepaymentPlanResponse prodUpdateRepaymentPlanResponse = new ProdUpdateRepaymentPlanResponse();

		logger.info("【借款人还款计划更新】-->进入dubbo-->order_no:"+prodUpdateRepaymentPlanRequest.getOrder_no());
		// 记录交易流水
		TransTransreq transTransReq = iTransReqService.getTransTransReq(prodUpdateRepaymentPlanRequest.clone(), TransConsts.REPAY_PLAN_CODE,TransConsts.REPAY_PLAN_NAME,false);
		iTransReqService.insert(transTransReq);
		logger.info("【借款人还款计划更新】-->添加流水成功-->order_no:"+prodUpdateRepaymentPlanRequest.getOrder_no());

		try{
			// 金额校验 是否小于0
			for (ProdUpdateRepaymentPlanRequestRepayPlan prodUpdateRepaymentPlanRequestRepayPlan : prodUpdateRepaymentPlanRequest.getRepay_plan_list_array()) {
				validate(prodUpdateRepaymentPlanRequestRepayPlan);
			}

			ProdProductinfo prodProductinfo = prodSearchService.queryProdInfo(prodUpdateRepaymentPlanRequest.getMer_no(),prodUpdateRepaymentPlanRequest.getProd_id());
			if(null == prodProductinfo){
				logger.info("【借款人还款计划更新】-->标的不存在-->prod_id:"+prodUpdateRepaymentPlanRequest.getProd_id()+"-->order_no:"+prodUpdateRepaymentPlanRequest.getOrder_no());
				throw new BusinessException(BusinessMsg.INVALID_PRODUCT_ID,BusinessMsg.getMsg(BusinessMsg.INVALID_PRODUCT_ID));
			}
			logger.info("【借款人还款计划更新】-->标的存在-->prod_id:"+prodUpdateRepaymentPlanRequest.getProd_id()+"-->order_no:"+prodUpdateRepaymentPlanRequest.getOrder_no());

			// 更新数据库
			prodSearchService.deleteProdProdRepay(prodUpdateRepaymentPlanRequest.getMer_no(),prodUpdateRepaymentPlanRequest.getProd_id());
			for (ProdUpdateRepaymentPlanRequestRepayPlan prodUpdateRepaymentPlanRequestRepayPlan : prodUpdateRepaymentPlanRequest.getRepay_plan_list_array()) {
				//业务删除该数据
				//插入新的还款计划
				ProdProdrepay prodrepay=new ProdProdrepay();
				prodrepay.setRepay_amt(prodUpdateRepaymentPlanRequestRepayPlan.getRepay_amt());
				prodrepay.setRepay_date(prodUpdateRepaymentPlanRequestRepayPlan.getRepay_date());
				prodrepay.setRepay_fee(prodUpdateRepaymentPlanRequestRepayPlan.getRepay_fee());
				prodrepay.setRepay_num(prodUpdateRepaymentPlanRequestRepayPlan.getRepay_num());
				prodrepay.setUpdate_time(new Date());
				prodrepay.setCreate_time(new Date());
				prodrepay.setPlat_no(prodUpdateRepaymentPlanRequest.getMer_no());
				prodrepay.setProd_id(prodUpdateRepaymentPlanRequest.getProd_id());
				prodrepay.setEnabled(Constants.ENABLED);
				prodrepay.setTrans_serial(transTransReq.getTrans_serial());
				prodrepay.setRemark(prodUpdateRepaymentPlanRequest.getRemark());
				prodRepayMapper.insert(prodrepay);
			}
			logger.info("【借款人还款计划更新】-->还款计划更新完成-->order_no:"+prodUpdateRepaymentPlanRequest.getOrder_no());

			// 更新流水记录 为 请求成功
			transTransReq.setReturn_code(BusinessMsg.SUCCESS);
			transTransReq.setReturn_msg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));
			transTransReq.setStatus(FlowStatus.SUCCESS.getCode());
			iTransReqService.insert(transTransReq);
			logger.info("【借款人还款计划更新】-->还更新流水成功-->order_no:"+prodUpdateRepaymentPlanRequest.getOrder_no());

			prodUpdateRepaymentPlanResponse.setOrder_status(OrderStatus.SUCCESS.getCode());
			prodUpdateRepaymentPlanResponse.setRecode(BusinessMsg.SUCCESS);
			prodUpdateRepaymentPlanResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));

		} catch (Exception e) {

			logger.error("【借款人还款计划更新】-->异常-->order_no:"+prodUpdateRepaymentPlanRequest.getOrder_no(),e);

			BaseResponse baseResponse=new BaseResponse();
			if(e instanceof BusinessException){
				baseResponse=((BusinessException) e).getBaseResponse();
			}else{
				logger.error(e);
				baseResponse.setRecode(BusinessMsg.DATEBASE_EXCEPTION);
				baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.DATEBASE_EXCEPTION));
			}
			// 更新流水记录
			transTransReq.setStatus(FlowStatus.FAIL.getCode());
			transTransReq.setReturn_code(BusinessMsg.DATEBASE_EXCEPTION);
			transTransReq.setReturn_msg(BusinessMsg.getMsg(BusinessMsg.DATEBASE_EXCEPTION));
			iTransReqService.insert(transTransReq);

			throw new BusinessException(baseResponse);
		}

		return prodUpdateRepaymentPlanResponse;
	}

	/**
	 * 一借多贷还款，暂不开放
	 * @param prodBatchRepayRequest
	 * @return
	 * @throws BusinessException
	 */
	@Override
	public List<ProdBusinessData> batchRefund(ProdBatchRepayOldRequest prodBatchRepayRequest) throws BusinessException {
		List<ProdBusinessData> prodBusinessDataList = new ArrayList<>();

		//记录业务流水
		TransTransreq transTransReq= iTransReqService.getTransTransReq(prodBatchRepayRequest.clone(),TransConsts.PRODREPAY_CODE,TransConsts.PRODREPAY_NAME,true);
		iTransReqService.insert(transTransReq);
		//验证标是否存在
		ProdProductinfo checkProductInfo = prodSearchService.queryProdInfo(prodBatchRepayRequest.getMer_no(),prodBatchRepayRequest.getProd_id());
		try{
			if(checkProductInfo == null){
				logger.info("【标的还款】========标的不存在");
				BaseResponse baseResponse=new BaseResponse();
				baseResponse.setRecode(BusinessMsg.INVALID_PRODUCT_ID);
				baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.INVALID_PRODUCT_ID));
				throw new BusinessException(baseResponse);
			}

			//如果是活期则不限制标的状态
			if(!(ProdType.CURRENT.getCode().equals(checkProductInfo.getProd_type()))){
				if(!ProdState.FOUND.getCode().equals(checkProductInfo.getProd_state())){

					logger.info("-----------当前标的状态为----------------" + checkProductInfo.getProd_state());

					transTransReq.setReturn_code(BusinessMsg.ESTABLISH_PRODUCT_FAILED);
					transTransReq.setReturn_msg(BusinessMsg.getMsg(BusinessMsg.ESTABLISH_PRODUCT_FAILED));
					transTransReq.setStatus(FlowStatus.FAIL.getCode());
					iTransReqService.insert(transTransReq);

					BaseResponse baseResponse=new BaseResponse();
					baseResponse.setRecode(BusinessMsg.ESTABLISH_PRODUCT_FAILED);
					baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.ESTABLISH_PRODUCT_FAILED) + "当前标未成立无法出账");
					throw new BusinessException(baseResponse);
				}
			}

			logger.info("【标的还款】========开始校验金额");
			//校验金额
			if (!checkCustRepayForBatchRefund(prodBatchRepayRequest)) {
				logger.info("【标的还款】======== 交易金额和明细金额之和不匹配!");
				//不足，更新流水
				transTransReq.setReturn_code(BusinessMsg.FAIL);
				transTransReq.setReturn_msg(BusinessMsg.getMsg(BusinessMsg.FAIL) +
						"交易金额和明细金额之和不匹配或者还款项与明细实际还款金额不匹配!");
				transTransReq.setStatus(FlowStatus.FAIL.getCode());
				iTransReqService.insert(transTransReq);

				BaseResponse baseResponse=new BaseResponse();
				baseResponse.setRecode(BusinessMsg.MONEY_ERROR);
				baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.MONEY_ERROR) +
						"交易金额和明细金额之和不匹配或者还款项与明细实际还款金额不匹配!");
				throw new BusinessException(baseResponse);
			}
			//检查标的余额
			List<AccountSubjectInfo> eaccCashAccount=accountQueryService.queryPlatAccountList(
					checkProductInfo.getPlat_no(),checkProductInfo.getProd_account(),null,Ssubject.PROD.getCode());

			if(eaccCashAccount.size()>0){
				BigDecimal allAmt=BigDecimal.ZERO;
				for(AccountSubjectInfo as:eaccCashAccount){
					allAmt=allAmt.add(as.getN_balance());
				}
			}
			//添加数据到缓存
			List<BatchCustRepayOld> custRepayList=prodBatchRepayRequest.getCustRepayList();
			List<String> refundStrList=new ArrayList<>();
			for(BatchCustRepayOld custRepay:custRepayList){
				//==================合成返回数据=================
				ProdBusinessData prodBusinessData=new ProdBusinessData();
				Amtlist amtlist = new Amtlist();
				prodBusinessData.setOrder_no(custRepay.getDetail_no());
				prodBusinessData.setProcess_date(DateUtils.formatDateToStr(new Date()));
				logger.info("【标的还款】========检查订单号重复");
				if(!orderCheck.checkOrder(custRepay.getDetail_no())) {
					logger.info("【标的还款】========订单号重复，不处理，返回处理中，order_no:" + custRepay.getDetail_no());
					prodBusinessData.setOrder_status(OrderStatus.PROCESSING.getCode());
				}else{
					//记录流水
					TransTransreq custRepayTransTransreq=iTransReqService.getTransTransReq(prodBatchRepayRequest, TransConsts.PRODREPAY_CODE,TransConsts.PRODREPAY_NAME,true);
					custRepayTransTransreq.setOrder_no(custRepay.getDetail_no());
					iTransReqService.insert(custRepayTransTransreq);
					//还款
					custRepay.setOrder_no(prodBatchRepayRequest.getOrder_no());
					custRepay.setMall_no(prodBatchRepayRequest.getMall_no());
					custRepay.setPlat_no(prodBatchRepayRequest.getMer_no());
					custRepay.setProd_id(prodBatchRepayRequest.getProd_id());
					custRepay.setIs_payoff(prodBatchRepayRequest.getIs_payoff());
					custRepay.setRepay_flag(prodBatchRepayRequest.getRepay_flag());
					//==================合成返回数据=================
					boolean isAccount=false;
					try{
						logger.info("【标的还款】========开始尝试虚拟账户交易");
						BigDecimal needCashAmt=custRepay.getReal_repay_amount();
						//手续费
						if(custRepay.getRepay_fee()!=null && custRepay.getRepay_fee().compareTo(BigDecimal.ZERO)>0){
							needCashAmt=needCashAmt.add(custRepay.getRepay_fee());
						}
						//利息
						if(custRepay.getReal_repay_val()!=null && custRepay.getReal_repay_val().compareTo(BigDecimal.ZERO)>0){
							needCashAmt=needCashAmt.add(custRepay.getReal_repay_val());
						}
						isAccount=productRefundExtService.refundToAccount(custRepay,custRepayTransTransreq,Tsubject.CASH.getCode());
						amtlist.setAmttype(ReturnAmtType.CASH.getCode());
						custRepay.setAmt_type(ReturnAmtType.CASH.getCode());
						if(custRepay.getRepay_fee()!=null && custRepay.getRepay_fee().compareTo(BigDecimal.ZERO)>0){
							custRepay.setReal_eaccount_amt(needCashAmt.subtract(custRepay.getRepay_fee()));
						}else{
							custRepay.setReal_eaccount_amt(needCashAmt);
						}
						if(!isAccount){
							logger.info("【标的还款】========用户有电子账户，终止交易");
							refundStrList.add(JSON.toJSONString(custRepay,GlobalConfig.serializerFeature));
							prodBusinessData.setOrder_status(OrderStatus.PROCESSING.getCode());
						}else{
							logger.info("【标的还款】========虚拟账户交易成功");
							prodBusinessData.setOrder_status(OrderStatus.SUCCESS.getCode());
							custRepayTransTransreq.setReturn_code(BusinessMsg.SUCCESS);
							custRepayTransTransreq.setReturn_msg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));
							Object amtObj=CacheUtil.getCache().getAndNotSetAlive("Prod"+checkProductInfo.getProd_account()+"AllAmt");
							logger.info("【标的还款】==========标的账户："+checkProductInfo.getProd_account()+
									"，已经需累计还款："+amtObj);
							if(amtObj!=null){
								BigDecimal prodFundAllAmt=(BigDecimal) amtObj;
								if(custRepay.getReal_repay_amount()!=null){
									prodFundAllAmt=prodFundAllAmt.subtract(custRepay.getReal_repay_amount());
								}
								if(custRepay.getReal_repay_val()!=null){
									prodFundAllAmt=prodFundAllAmt.subtract(custRepay.getReal_repay_val());
								}
								if(custRepay.getRepay_fee()!=null){
									prodFundAllAmt=prodFundAllAmt.subtract(custRepay.getRepay_fee());
								}
								logger.info("【标的还款】==========剩余需还款："+prodFundAllAmt);
								if(prodFundAllAmt.compareTo(BigDecimal.ZERO)<=0){
									logger.info("【标的还款】==========删除需还款金额缓存");
									CacheUtil.getCache().del("Prod"+checkProductInfo.getProd_account()+"AllAmt");
								}else{
									logger.info("【标的还款】==========重新设置需还款金额");
									CacheUtil.getCache().set("Prod"+checkProductInfo.getProd_account()+"AllAmt",prodFundAllAmt);
								}
							}
						}
					}catch (Exception e){
						logger.info("【标的还款】========虚拟账户交易失败:"+e);
						BaseResponse baseResponse=new BaseResponse();
						if(e instanceof BusinessException){
							baseResponse=((BusinessException) e).getBaseResponse();
						}else{
							baseResponse.setRecode(BusinessMsg.RUNTIME_EXCEPTION);
							baseResponse .setRemsg(BusinessMsg.getMsg(BusinessMsg.RUNTIME_EXCEPTION));
						}
						prodBusinessData.setOrder_status(OrderStatus.FAIL.getCode());
						custRepayTransTransreq.setReturn_code(baseResponse.getRecode());
						custRepayTransTransreq.setReturn_msg(baseResponse.getRemsg());
					}
					//只要不是处理中状态，更新流水状态
					if(!OrderStatus.PROCESSING.getCode().equals(prodBusinessData.getOrder_status())){
						custRepayTransTransreq.setStatus(prodBusinessData.getOrder_status());
						iTransReqService.insert(custRepayTransTransreq);
					}
					prodBusinessData.setQuery_id(custRepayTransTransreq.getTrans_serial());
				}

				prodBusinessData.setOrder_no(custRepay.getDetail_no());

				amtlist.setPlatcust(custRepay.getCust_no());
				amtlist.setAmt(custRepay.getReal_repay_amt());
				prodBusinessData.setAmtlist(amtlist);

				prodBusinessDataList.add(prodBusinessData);
				//==============================================
			}
			//将未处理的还款加入队列，队列名为"refund"+平台号
			if(refundStrList.size()>0){
				String nowKey="refund"+prodBatchRepayRequest.getMer_no();
				logger.info("【标的还款】========将未处理还款加入队列："+nowKey);
				String[] refundList=refundStrList.toArray(new String[refundStrList.size()]);
				CacheUtil.getCache().leftPushAll(nowKey, refundList);
				try{
					CacheUtil.getCache().addToSet("refundList",nowKey);
				}catch (Exception e){
					logger.error("【标的还款】=======set队列名称时异常：",e);
				}
			}

			//更新流水
			transTransReq.setReturn_code(BusinessMsg.SUCCESS);
			transTransReq.setReturn_msg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));
			transTransReq.setStatus(FlowStatus.SUCCESS.getCode());
			iTransReqService.insert(transTransReq);
		}catch (Exception e){
			BaseResponse baseResponse=new BaseResponse();
			if(e instanceof BusinessException){
				baseResponse=((BusinessException) e).getBaseResponse();
			}else{
				logger.info("【标的还款】=========异常：",e);
				baseResponse.setRecode(BusinessMsg.RUNTIME_EXCEPTION);
				baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.RUNTIME_EXCEPTION));
			}

			//更新流水
			transTransReq.setReturn_code(baseResponse.getRecode());
			transTransReq.setReturn_msg(baseResponse.getRemsg());
			transTransReq.setStatus(FlowStatus.FAIL.getCode());
			iTransReqService.insert(transTransReq);
			if(prodBusinessDataList.size()<=0){
				throw new BusinessException(baseResponse);
			}
			//更改订单状态
			for(ProdBusinessData data:prodBusinessDataList){
				if(data.getOrder_status().equals(OrderStatus.PROCESSING.getCode())){
					//更新订单状态
					data.setOrder_status(OrderStatus.FAIL.getCode());
					TransTransreq failTrans=iTransReqService.queryTransTransReqByOrderno(data.getOrder_no());
					failTrans.setReturn_code(baseResponse.getRecode());
					failTrans.setReturn_msg(baseResponse.getRemsg());
					failTrans.setStatus(OrderStatus.FAIL.getCode());
					iTransReqService.insert(failTrans);
				}
			}
		}

		logger.info("【标的还款】========="+prodBusinessDataList);
		return prodBusinessDataList;
	}

	@Override
	public boolean batchRefundAsyn(ProdBatchRepayRequest prodBatchRepayRequest) throws BusinessException {

		//记录业务流水
		TransTransreq transTransReq= iTransReqService.getTransTransReq(prodBatchRepayRequest.clone(),TransConsts.PRODREPAY_CODE,TransConsts.PRODREPAY_NAME,true);
		BaseResponse oldBatchTrans=iTransReqService.insert(transTransReq);
		if(oldBatchTrans!=null){
			throw new BusinessException(BusinessMsg.ORDERNUMBER_REPEATED,BusinessMsg.getMsg(BusinessMsg.ORDERNUMBER_REPEATED));
		}
		try{
			//校验金额
//			if (!checkCustRepayForBatchRefund(prodBatchRepayRequest)) {
//				logger.info(String.format("【标的还款】交易金额和明细金额之和不匹配|trans_serial:%s",
//						prodBatchRepayRequest.getOrder_no()));
//				throw new BusinessException(BusinessMsg.MONEY_ERROR,BusinessMsg.getMsg(BusinessMsg.MONEY_ERROR));
//			}

			//添加数据到缓存
			List<BatchCustRepay> custRepayList=prodBatchRepayRequest.getCustRepayList();
			Map<String,ProdProductinfo> productInfoMap=new HashMap<>(custRepayList.size());
			Map<String,List<BatchCustRepay>> productRefundMap=new HashMap<>();
			for(BatchCustRepay custRepay:custRepayList){
				//记录流水
				TransTransreq custRepayTransTransreq=iTransReqService.getTransTransReq(prodBatchRepayRequest.clone(), TransConsts.PRODREPAY_CODE,TransConsts.PRODREPAY_NAME,true);
				custRepayTransTransreq.setOrder_no(custRepay.getDetail_no());
				BaseResponse oldTrans=iTransReqService.insert(custRepayTransTransreq);
				if(oldTrans!=null){
					continue;
				}

				ProdProductinfo checkProductInfo=productInfoMap.get(custRepay.getProd_id());
				try{
					//验证标是否存在
					if(checkProductInfo==null){
						checkProductInfo = prodSearchService.queryProdInfo(prodBatchRepayRequest.getMer_no(),custRepay.getProd_id());
						productInfoMap.put(custRepay.getProd_id(),checkProductInfo);
					}

					custRepay.setMall_no(prodBatchRepayRequest.getMall_no());
					custRepay.setPlat_no(prodBatchRepayRequest.getMer_no());
					custRepay.setTransTransreq(custRepayTransTransreq);
					custRepay.setNotify_url(prodBatchRepayRequest.getNotify_url());
					//==================合成返回数据=================
					logger.info("【标的还款】========交易数据校验");
//					logger.info("【标的还款】========将还款数据放入MQ队列");
//					prodMQOptionService.addSingleRefund(custRepay,checkProductInfo,null);
					List<BatchCustRepay> alreadyList=productRefundMap.get(custRepay.getProd_id());
					if(alreadyList==null){
						alreadyList=new ArrayList<>();
					}
					alreadyList.add(custRepay);
					productRefundMap.put(custRepay.getProd_id(),alreadyList);
				}catch (Exception e){
					logger.info("【标的还款】========还款数据未加入队列"+e.getMessage());
					BaseResponse baseResponse=new BaseResponse();
					if(e instanceof BusinessException){
						baseResponse=((BusinessException) e).getBaseResponse();
					}else{
						baseResponse.setRecode(BusinessMsg.RUNTIME_EXCEPTION);
						baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.RUNTIME_EXCEPTION));
					}
					custRepayTransTransreq.setStatus(FlowStatus.FAIL.getCode());
					custRepayTransTransreq.setReturn_code(baseResponse.getRecode());
					custRepayTransTransreq.setReturn_msg(baseResponse.getRemsg());
					iTransReqService.insert(custRepayTransTransreq);
				}

			}

			//将数据放到MQ
			logger.info(String.format("【标的还款】将还款数据放入MQ队列|batch_order_no:%s|productRefundMap:%s",
					prodBatchRepayRequest.getOrder_no(),JSON.toJSONString(productRefundMap)));
			for(String key:productRefundMap.keySet()){
				List<BatchCustRepay> batchCustRepayList=productRefundMap.get(key);
				logger.info(String.format("【标的还款】标的合并后数据|prod_id:%s|batchCustRepayList:%s",
						key,JSON.toJSONString(batchCustRepayList)));
				try{
					prodMQOptionService.addRefund(batchCustRepayList,productInfoMap.get(key));
				}catch (Exception e){
					logger.info(String.format("【标的还款】还款数据放入MQ队列失败，将该批订单置为失败。|batch_order_no:%s",
							prodBatchRepayRequest.getOrder_no()));
					for(BatchCustRepay batchCustRepay:batchCustRepayList){
						TransTransreq oldTransreq=batchCustRepay.getTransTransreq();
						oldTransreq.setStatus(FlowStatus.FAIL.getCode());
						oldTransreq.setReturn_code(BusinessMsg.CALL_REMOTE_ERROR);
						oldTransreq.setReturn_msg(BusinessMsg.getMsg(BusinessMsg.CALL_REMOTE_ERROR)+":数据加入MQ失败");
						iTransReqService.insert(oldTransreq);
					}
				}
			}
			//更新流水
			transTransReq.setReturn_code(BusinessMsg.SUCCESS);
			transTransReq.setReturn_msg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));
			transTransReq.setStatus(FlowStatus.SUCCESS.getCode());
			iTransReqService.insert(transTransReq);
		}catch (Exception e){
			BaseResponse baseResponse=new BaseResponse();
			if(e instanceof BusinessException){
				baseResponse=((BusinessException) e).getBaseResponse();
			}else{
				baseResponse.setRecode(BusinessMsg.RUNTIME_EXCEPTION);
				baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.RUNTIME_EXCEPTION));
			}//更新流水
			transTransReq.setReturn_code(baseResponse.getRecode());
			transTransReq.setReturn_msg(baseResponse.getRemsg());
			transTransReq.setStatus(FlowStatus.FAIL.getCode());
			iTransReqService.insert(transTransReq);
			throw new BusinessException(baseResponse);
		}
		return true;
	}

	@Override
	public boolean getBatchEaccountTransData(String key,Long maxNum,Long maxThread,Long needThreadNum) throws BusinessException {
		//计算需开启的线程数
		Long nowThreadNum=CacheUtil.getCache().increment(SysParamterKey.PROD_REFUND_NOW_THREADS_NUM,0);
		needThreadNum=needThreadNum-nowThreadNum;
		logger.info("【标的还款】当前线程数：{}|最大线程数：{}|需新开线程数：{}",nowThreadNum,maxThread,needThreadNum);
		if(needThreadNum>0){
			for(;needThreadNum>0;needThreadNum--){
				CacheUtil.getCache().increment(SysParamterKey.PROD_REFUND_NOW_THREADS_NUM,1);
				SingleThreadPool.getThreadPool().execute(new Runnable() {
					@Override
					public void run() {
						buildData(key,maxNum);
						CacheUtil.getCache().increment(SysParamterKey.PROD_REFUND_NOW_THREADS_NUM,-1);
					}
				});
			}
		}
		goToPay();
		return true;
	}

	private void buildData(String key,Long maxNum){
		List<String> dataListBack=new ArrayList<>();
		try{
			PlatCardinfo platCardinfo=null;
			List<BatchPayDetail> batchPayDetailList=new ArrayList<>();
			BigDecimal allAmt=BigDecimal.ZERO;
			String trans_serial= SeqUtil.getSeqNum();
			String mall_no="";
			String plat_no="";

			//合成转账参数
			Map<String,Object> params=new HashMap<>();
			params.put("partner_id","10000001");
			params.put("partner_serial_no",trans_serial);
			params.put("partner_trans_date",DateUtils.getyyyyMMddDate());
			params.put("partner_trans_time",DateUtils.getHHmmssTime());
			params.put("transaction_flag","1");

			Map<String,Object> repayListMap=new HashMap<>();
			while (repayListMap.size()<maxNum){
				String refundListItemStr= String.valueOf(CacheUtil.getCache().rightPop(key));
				if(refundListItemStr!=null && !"null".equals(refundListItemStr)){
					String orderKey=null;
					BigDecimal trans_amt=BigDecimal.ZERO;
					Boolean addFlag=false;
					BatchPayDetail detail=null;
					try{
						BatchCustRepay batchCustRepay=JSON.parseObject(refundListItemStr,BatchCustRepay.class);
						//查询存管户
						mall_no=batchCustRepay.getMall_no();
						plat_no=batchCustRepay.getPlat_no();
						logger.info("【标的还款】=========查询存管户"+plat_no);
						if(platCardinfo==null){
							platCardinfo=accountQueryService.getPlatcardinfo(plat_no,PlatAccType.PLATTRUST.getCode());
						}
						//查询流水
						logger.info("【标的还款】=========查询流水"+batchCustRepay.getDetail_no());
						TransTransreq transTransreq=iTransReqService.queryTransTransReqByOrderno(batchCustRepay.getDetail_no());
						//查询用户信息
						logger.info("【标的还款】=========查询用户信息"+batchCustRepay.getCust_no());
						EaccUserinfo eaccUserinfo=accountQueryService.getEaccUserinfo(mall_no,batchCustRepay.getCust_no());

						detail= (BatchPayDetail) repayListMap.get(eaccUserinfo.getEaccount());
						trans_amt=batchCustRepay.getReal_eaccount_amt();
						if(trans_amt==null){
							transTransreq.setStatus(FlowStatus.FAIL.getCode());
							transTransreq.setReturn_code(BusinessMsg.TRANS_FAIL);
							transTransreq.setReturn_msg(BusinessMsg.getMsg(BusinessMsg.TRANS_FAIL)+"：数据异常，处理失败！");
							iTransReqService.insert(transTransreq);
							continue;
						}
						if(detail!=null){
							detail.setOccur_balance(detail.getOccur_balance().add(trans_amt));
						}else{
							detail=new BatchPayDetail();
							detail.setCard_no(eaccUserinfo.getEaccount());
							detail.setOccur_balance(trans_amt);
							detail.setSummary("标的还款");
						}
						allAmt=allAmt.add(trans_amt);
						addFlag=true;

						repayListMap.put(detail.getCard_no(),detail);
						params.put("details",JSON.toJSONString(detail));
						//记录电子账户转账流水
						logger.info("【标的还款】=========查询电子账户转账流水"+transTransreq.getTrans_serial());
						EaccTransTransreqWithBLOBs eaccTransTransreq=new EaccTransTransreqWithBLOBs();
						eaccTransTransreq.setParent_trans_serial(transTransreq.getTrans_serial());
						eaccTransTransreq.setStatus(Integer.valueOf(FlowStatus.INPROCESS.getCode()));
						eaccTransTransreq.setTrans_amt(trans_amt);
						eaccTransTransreq.setEaccount(platCardinfo.getCard_no());
						eaccTransTransreq.setOppo_eaccount(eaccUserinfo.getEaccount());
						eaccTransTransreq.setName(platCardinfo.getCard_name());
						eaccTransTransreq.setOppo_name(eaccUserinfo.getName());
						eaccTransTransreq.setProperty(Integer.valueOf(CusType.COMPANY.getCode()));
						eaccTransTransreq.setOppo_property(Integer.valueOf(CusType.PERSONAL.getCode()));
						eaccTransTransreq.setTrans_serial(trans_serial);
						eaccTransTransreq.setReq_params(JSON.toJSONString(params));
						logger.info("【标的还款】========添加电子账户转账流水（处理中）"+batchCustRepay.getDetail_no());
						eaccTransTransreqService.addFlow(eaccTransTransreq);
						//将订单消息添加到redis
						orderKey=trans_serial+eaccUserinfo.getEaccount();
						CacheUtil.getCache().leftPush(orderKey,refundListItemStr);
						CacheUtil.getCache().expire(orderKey,24*3600);
						dataListBack.add(refundListItemStr);
					}catch (Exception e){
						logger.error("【标的还款】========还款订单加入一借多贷失败：",e);
						if(orderKey!=null){
							CacheUtil.getCache().del(orderKey);
						}
						if(addFlag){
							//扣除转账金额
							detail.setOccur_balance(detail.getOccur_balance().subtract(trans_amt));
							allAmt=allAmt.subtract(trans_amt);
						}
						//将失败的数据丢回处理队列
						CacheUtil.getCache().leftPush(key,refundListItemStr);
					}
				}else{
					break;
				}
			}
			List<BatchRepayDetail> batchRepayDetailList=new ArrayList<>();
			for(String mapKey:repayListMap.keySet()){
				BatchPayDetail detail= (BatchPayDetail) repayListMap.get(mapKey);
				batchPayDetailList.add(detail);

				BatchRepayDetail batchRepayDetail=new BatchRepayDetail();
				batchRepayDetail.setCard_no(detail.getCard_no());
				batchRepayDetail.setOccur_balance(detail.getOccur_balance());
				batchRepayDetail.setSummary(detail.getSummary());
				batchRepayDetail.setTrans_serial(trans_serial);
				batchRepayDetail.setStatus(FlowStatus.INPROCESS.getCode());
				batchRepayDetailList.add(batchRepayDetail);
			}
			if(batchPayDetailList.size()<=0){
				return;
			}
//			params.put("total_num",batchPayDetailList.size());
//			params.put("total_balance",allAmt.toString());
//			params.put("third_no",platCardinfo.getCard_no());
//			params.put("details",JSON.toJSONString(batchPayDetailList));
			//将请求参数明细入库
			custBatchRepayDetailMapper.batchInsert(batchRepayDetailList);

			ProdRefundWaitPay prodRefundWaitPay=new ProdRefundWaitPay();
			prodRefundWaitPay.setMall_no(mall_no);
			prodRefundWaitPay.setPlat_no(plat_no);
			prodRefundWaitPay.setThird_no(platCardinfo.getCard_no());
			prodRefundWaitPay.setTotal_num(batchPayDetailList.size());
			prodRefundWaitPay.setTotal_balance(allAmt);
			prodRefundWaitPay.setTrans_serial(trans_serial);
			logger.info("【标的还款】将一借多贷订单加入待发送队列|trans_serial:{}",trans_serial);
			try{
				CacheUtil.getCache().leftPush(SysParamterKey.PROD_REFUND_WAIT_PAY,JSON.toJSONString(prodRefundWaitPay));
			}catch (Exception e){
				//更改数据状态为失败
				logger.error("【标的还款】将一借多贷订单加入待发送队列异常：",e);
				BatchRepayDetailExample example=new BatchRepayDetailExample();
				BatchRepayDetailExample.Criteria criteria=example.createCriteria();
				criteria.andTrans_serialEqualTo(trans_serial);
				BatchRepayDetail newBatchRepayDetail=new BatchRepayDetail();
				newBatchRepayDetail.setStatus(FlowStatus.FAIL.getCode());
				batchRepayDetailMapper.updateByExampleSelective(newBatchRepayDetail,example);
			}
		}catch (Exception e){
			logger.error("【还款批量代付】============处理失败，回滚数据："+e.getMessage());
			//回滚数据
			for(String data:dataListBack){
				CacheUtil.getCache().leftPush(key,data);
			}
		}
	}

	private void goToPay(){
		Object prodRefundWaitPayObj=CacheUtil.getCache().rightPop(SysParamterKey.PROD_REFUND_WAIT_PAY);
		if(prodRefundWaitPayObj!=null){
			ProdRefundWaitPay prodRefundWaitPay=JSON.parseObject(prodRefundWaitPayObj.toString(),ProdRefundWaitPay.class);
			String trans_serial=prodRefundWaitPay.getTrans_serial();
			logger.info("【标的还款】获取到待发送一借多贷订单|trans_serial:{}",trans_serial);
			BatchRepayDetailExample example=new BatchRepayDetailExample();
			BatchRepayDetailExample.Criteria criteria=example.createCriteria();
			criteria.andTrans_serialEqualTo(trans_serial);
			List<BatchRepayDetail> batchPayDetailList=batchRepayDetailMapper.selectByExample(example);
			if(batchPayDetailList.size()==prodRefundWaitPay.getTotal_num()){
				//如果数量相等，比较金额
				BigDecimal sumBD=BigDecimal.ZERO;
				for(BatchRepayDetail batchRepayDetail:batchPayDetailList){
					batchRepayDetail.setId(null);
					batchRepayDetail.setStatus(null);
					sumBD=sumBD.add(batchRepayDetail.getOccur_balance());
				}
				if(sumBD.compareTo(prodRefundWaitPay.getTotal_balance())==0){
					//如果金额相等，发送到支付
					//合成转账参数
					Map<String,Object> params=new HashMap<>();
					params.put("partner_id","10000001");
					params.put("partner_serial_no",trans_serial);
					params.put("partner_trans_date",DateUtils.getyyyyMMddDate());
					params.put("partner_trans_time",DateUtils.getHHmmssTime());
					params.put("transaction_flag","1");
					params.put("total_num",batchPayDetailList.size());
					params.put("total_balance",sumBD);
					params.put("third_no",prodRefundWaitPay.getThird_no());
					params.put("details",JSON.toJSONString(batchPayDetailList));
					logger.info("【标的还款】========请求支付");
					boolean paySuccess=true;
					try{
						BatchPayResponse batchPayResponse=accountTransferThirdParty.batchPay(params,prodRefundWaitPay.getMall_no());
						//data返回失败，或者未返回data数据，或者返回错误信息不为通讯超时，当失败处理
						if((batchPayResponse.getData()==null && !"027019".equals(batchPayResponse.getError_code())) ||
								(batchPayResponse.getData()!=null && "7".equals(batchPayResponse.getData().get(0).getPay_status()))){
							logger.info("【标的还款】========请求支付失败"+batchPayResponse.getError_info());
							throw new BusinessException(BusinessMsg.TRANS_FAIL,"请求支付失败"+batchPayResponse.getError_info());
						}
					}catch (Exception e){
						logger.error("【标的还款】请求一借多贷异常：",e);
						BaseResponse baseResponse=new BaseResponse();
						if(e instanceof BusinessException && "请求超时".equals(baseResponse.getRemsg())){
							logger.info("【标的还款】========一借多贷请求超时");
						}else{
							paySuccess=false;
							CacheUtil.getCache().leftPush(SysParamterKey.PROD_REFUND_WAIT_PAY,prodRefundWaitPayObj.toString());
						}
					}
					if(paySuccess){
						try{
							//在redis中设置转账状态查询缓存
							Map<String,Object> queryMap=new HashMap<>();
							queryMap.put("trans_serial",trans_serial);
							queryMap.put("mall_no",prodRefundWaitPay.getMall_no());
							queryMap.put("plat_no",prodRefundWaitPay.getPlat_no());
							queryMap.put("startTime",(new Date()).getTime());
							queryMap.put("lastTime",(new Date()).getTime());
							CacheUtil.getCache().addToSet(ProductPublicParams.fundPayWaitQueryListKey,ProductPublicParams.waitQueryOrder+trans_serial);
							CacheUtil.getCache().set(ProductPublicParams.waitQueryOrder+trans_serial,JSON.toJSONString(queryMap,GlobalConfig.serializerFeature));
						}catch (Exception e){
							logger.info("【标的还款】========redis缓存设置失败！");
						}
						//================================================================
						logger.info("【标的还款】========启动查询支付状态JOB");
						refundJobAction.startQueryBatchPayNew(trans_serial,prodRefundWaitPay.getMall_no(),prodRefundWaitPay.getPlat_no());
						//更改batch_repay_detail的状态
						BatchRepayDetail newBatchRepayDetail=new BatchRepayDetail();
						newBatchRepayDetail.setStatus(FlowStatus.REQUESTSUCCESS.getCode());
						batchRepayDetailMapper.updateByExampleSelective(newBatchRepayDetail,example);
					}

				}else{
					//报警
					String content="【北京银行】一借多贷金额异常|trans_serial："+trans_serial+
							"|redis key："+SysParamterKey.PROD_REFUND_WAIT_PAY+
							"|redis值"+prodRefundWaitPayObj.toString()+
							"|数据库金额："+sumBD+
							"|redis金额："+prodRefundWaitPay.getTotal_balance()+
							"|请核对数据后将正确的数据重新加入redis队列";
					logger.error("【标的还款】{}",content);
					sendMsgService.sendErrorToAdmin(content,prodRefundWaitPay.getPlat_no());
				}
			}else{
				//报警
				String content="【北京银行】一借多贷笔数异常|trans_serial："+trans_serial+
						"|redis key："+SysParamterKey.PROD_REFUND_WAIT_PAY+
						"|redis值"+prodRefundWaitPayObj.toString()+
						"|数据库笔数："+batchPayDetailList.size()+
						"|redis笔数："+prodRefundWaitPay.getTotal_num()+
						"|请核对数据后将正确的数据重新加入redis队列";
				logger.error("【标的还款】{}",content);
				sendMsgService.sendErrorToAdmin(content,prodRefundWaitPay.getPlat_no());
			}


		}
	}

	@Override
	public boolean executeBatchRefund(List<BatchPayQueryResponseDataDetail> detailList,String trans_serial) throws BusinessException {
		logger.info("【标的还款】===========开始批量还款 detailList："+JSON.toJSONString(detailList)+" trans_serial:"+trans_serial);
		int failed=0;
		for (BatchPayQueryResponseDataDetail detail:detailList){
			logger.info("【标的还款】===========开始单条还款，当前卡号："+detail.getCard_no());
			String nowKey=trans_serial+detail.getCard_no();
			long custRepaySize=CacheUtil.getCache().size(nowKey);
			logger.info("【标的还款】===========卡号包含的还款条数："+custRepaySize);
			int haveCardFailed=0;
			int haveCardSuccess=0;
			for(int i=0;i<custRepaySize;i++){
				Object repayDataObj=CacheUtil.getCache().rightPop(nowKey);
				if(repayDataObj!=null){
					BatchCustRepayOld batchCustRepay=JSON.parseObject(String.valueOf(repayDataObj),BatchCustRepayOld.class);
					TransTransreq transTransreq=iTransReqService.queryTransTransReqByOrderno(batchCustRepay.getDetail_no());
					EaccTransTransreqWithBLOBs eaccTransTransreq=eaccTransTransreqService.queryFlowByTransSerial(transTransreq.getTrans_serial());
					if(FlowStatus.INPROCESS.getCode().equals(eaccTransTransreq.getStatus().toString())){
						ProdProductinfo checkProductInfo = prodSearchService.queryProdInfo(batchCustRepay.getPlat_no(),batchCustRepay.getProd_id());
						if("3".equals(detail.getPay_status())){
							//交易成功
							try{
								logger.info("【标的还款】===========开始执行单条还款，流水号："+transTransreq.getTrans_serial());
								productRefundExtService.refundToEaccount(batchCustRepay,transTransreq);
								try{
									Object amtObj=CacheUtil.getCache().getAndNotSetAlive("Prod"+checkProductInfo.getProd_account()+"AllAmt");
									logger.info("【标的还款】==========标的账户："+checkProductInfo.getProd_account()+
											"，已经需累计还款："+amtObj);
									if(amtObj!=null){
										BigDecimal prodFundAllAmt=(BigDecimal) amtObj;
										if(batchCustRepay.getReal_repay_amount()!=null){
											prodFundAllAmt=prodFundAllAmt.subtract(batchCustRepay.getReal_repay_amount());
										}
										if(batchCustRepay.getReal_repay_val()!=null){
											prodFundAllAmt=prodFundAllAmt.subtract(batchCustRepay.getReal_repay_val());
										}
										if(batchCustRepay.getRepay_fee()!=null){
											prodFundAllAmt=prodFundAllAmt.subtract(batchCustRepay.getRepay_fee());
										}
										logger.info("【标的还款】==========剩余需还款："+prodFundAllAmt);
										if(prodFundAllAmt.compareTo(BigDecimal.ZERO)<=0){
											logger.info("【标的还款】==========删除需还款金额缓存");
											CacheUtil.getCache().del("Prod"+checkProductInfo.getProd_account()+"AllAmt");
										}else{
											logger.info("【标的还款】==========重新设置需还款金额");
											CacheUtil.getCache().set("Prod"+checkProductInfo.getProd_account()+"AllAmt",prodFundAllAmt);
										}
									}
								}catch (Exception ex){
									logger.error("【标的还款】==========redis操作异常：",ex);
									try{
										CacheUtil.getCache().del("Prod"+checkProductInfo.getProd_account()+"AllAmt");
									}catch (Exception exp){
										logger.info("【标的还款】==========redis删除失败，key:Prod"+checkProductInfo.getProd_account()+"AllAmt");
										//将需要删除的key放入数据库
										EaccClearErrorInfo eaccClearErrorInfo=new EaccClearErrorInfo();
										eaccClearErrorInfo.setClear_date("1");
										eaccClearErrorInfo.setEaccount("Prod"+checkProductInfo.getProd_account()+"AllAmt");
										eaccClearErrorInfo.setError_msg("标的还款redis删除失败："+exp.getMessage());
										eaccClearErrorInfoMapper.insert(eaccClearErrorInfo);
									}
								}
								logger.info("【标的还款】===========单条还款成功，记录流水");
								transTransreq.setStatus(FlowStatus.SUCCESS.getCode());
								transTransreq.setReturn_code(BusinessMsg.SUCCESS);
								transTransreq.setReturn_msg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));
								eaccTransTransreq.setStatus(Integer.valueOf(FlowStatus.SUCCESS.getCode()));
								haveCardSuccess++;
							}catch (Exception e){
								//操作失败后等待重试
								failed++;
								haveCardFailed++;
								logger.error("【标的还款】=========单条还款失败，回滚数据！");
								CacheUtil.getCache().leftPush(nowKey,JSON.toJSONString(batchCustRepay,GlobalConfig.serializerFeature));
								logger.error("【标的还款】=========单条还款失败：",e);
							}
						}else{
							//交易失败
							transTransreq.setStatus(FlowStatus.FAIL.getCode());
							transTransreq.setReturn_code(BusinessMsg.TRANS_FAIL);
							transTransreq.setReturn_msg(BusinessMsg.getMsg(BusinessMsg.TRANS_FAIL)+"："+detail.getFail_cause());
							eaccTransTransreq.setStatus(Integer.valueOf(FlowStatus.FAIL.getCode()));
							haveCardSuccess++;
							Object amtObj=CacheUtil.getCache().getAndNotSetAlive("Prod"+checkProductInfo.getProd_account()+"AllAmt");
							logger.info("【标的还款】==========标的账户："+checkProductInfo.getProd_account()+
									"，已经需累计还款："+amtObj);
							if(amtObj!=null) {
								BigDecimal prodFundAllAmt = (BigDecimal) amtObj;
								if (batchCustRepay.getReal_repay_amount() != null) {
									prodFundAllAmt = prodFundAllAmt.subtract(batchCustRepay.getReal_repay_amount());
								}
								if (batchCustRepay.getReal_repay_val() != null) {
									prodFundAllAmt = prodFundAllAmt.subtract(batchCustRepay.getReal_repay_val());
								}
								if (batchCustRepay.getRepay_fee() != null) {
									prodFundAllAmt = prodFundAllAmt.subtract(batchCustRepay.getRepay_fee());
								}
								logger.info("【标的还款】==========剩余需还款：" + prodFundAllAmt);
								if (prodFundAllAmt.compareTo(BigDecimal.ZERO) <= 0) {
									logger.info("【标的还款】==========删除需还款金额缓存");
									CacheUtil.getCache().del("Prod" + checkProductInfo.getProd_account() + "AllAmt");
								} else {
									logger.info("【标的还款】==========重新设置需还款金额");
									CacheUtil.getCache().set("Prod" + checkProductInfo.getProd_account() + "AllAmt", prodFundAllAmt);
								}
							}
						}
						//更新电子账户转账流水
						eaccTransTransreq.setRes_params(JSON.toJSONString(detail));
						eaccTransTransreqService.updateFlowByPrimaryKey(eaccTransTransreq);
						iTransReqService.insert(transTransreq);
					}
				}
			}
			if(haveCardFailed==0 && haveCardSuccess>0){
				//全部操作成功，删除key
				logger.info("【批量还款】==========全部操作成功，删除key");
				CacheUtil.getCache().del(nowKey);
			}
		}
		if(failed>0){
			logger.info("【批量还款】==========有失败操作，重试！");
			return false;
		}
		logger.info("【批量还款】==========还款全部成功！");
		return true;
	}

	@Override
	public boolean executeFailedRefund(String trans_serial, String plat_no) throws BusinessException {
		try{
			logger.info("【批量还款处理失败】一借多贷订单无此原交易，订单号："+trans_serial);
			List<EaccTransTransreqWithBLOBs> eaccTransTransreqList=eaccTransTransreqService.queryFlowByParentTransSerial(trans_serial);
			for(EaccTransTransreqWithBLOBs eaccTransTransreq:eaccTransTransreqList){
				try{
					//将电子账户流水置为失败
					eaccTransTransreq.setStatus(Integer.valueOf(FlowStatus.CONFIRMUNKOWN.getCode()));
					eaccTransTransreqService.updateFlowByPrimaryKey(eaccTransTransreq);
					//删除相关redis
					String nowKey=trans_serial+eaccTransTransreq.getOppo_eaccount();
					CacheUtil.getCache().del(nowKey);
					//将还款订单置为失败
					logger.info("【批量还款处理失败】一借多贷订单无此原交易，将还款订单置为失败，还款流水号："+eaccTransTransreq.getParent_trans_serial());
					TransTransreq transTransreq=iTransReqService.queryTransTransReqByOrderNoAndBatchOrderNo(
							eaccTransTransreq.getParent_trans_serial(),null);
					transTransreq.setStatus(FlowStatus.FAIL.getCode());
					transTransreq.setReturn_code(BusinessMsg.TRANS_FAIL);
					transTransreq.setReturn_msg(BusinessMsg.getMsg(BusinessMsg.TRANS_FAIL)+"：一借多贷无此原订单");
					iTransReqService.insert(transTransreq);
				}catch (Exception e){
					logger.error(e);
					logger.error("【批量还款处理失败】还款订单应为失败，需手动将订单置为失败，流水号："+eaccTransTransreq.getParent_trans_serial());
					//给管理员发送通知
					String content="(批量还款)还款订单应为失败，需手动将订单置为失败。流水号："+eaccTransTransreq.getParent_trans_serial();
					sendMsgService.sendErrorToAdmin(content,plat_no);
				}
			}
		}catch (Exception e){
			logger.info("【批量还款处理失败】一借多贷状态更新失败，订单号："+trans_serial+",error:",e);
			//给管理员发送通知
			String content="(批量还款)一借多贷状态更新失败，请将该笔一借多贷涉及到的全部订单更新为失败。订单号："+trans_serial;
			sendMsgService.sendErrorToAdmin(content,plat_no);
		}
		return true;
	}

	@Override
	public boolean startClosedFundQueryJob(String trans_serial, String mall_no) throws BusinessException {
		refundJobAction.startQueryBatchPayNew(trans_serial,mall_no,null);
		return true;
	}

	private boolean checkCustRepayForBatchRefund(ProdBatchRepayOldRequest prodBatchRepayRequest) {
		//实际还款金额
		BigDecimal real_repay_amt=BigDecimal.ZERO;
		for (BatchCustRepayOld custRepay : prodBatchRepayRequest.getCustRepayList()) {
			BigDecimal num = BigDecimal.ZERO;
			if (custRepay.getReal_repay_amount()!=null){
				num = num.add(custRepay.getReal_repay_amount());
			}

			if (custRepay.getRates_amt()!=null){
				num = num.add(custRepay.getRates_amt());
			}

			if (custRepay.getReal_repay_val()!=null){
				num = num.add(custRepay.getReal_repay_val());
			}

			if (custRepay.getExperience_amt()!=null){
				num = num.add(custRepay.getExperience_amt());
			}
			if (0!=custRepay.getReal_repay_amt().compareTo(num)){
				return false;
			}
			real_repay_amt = real_repay_amt.add(custRepay.getReal_repay_amt());
		}

		//校验金额
		if(0!=real_repay_amt.compareTo(prodBatchRepayRequest.getTrans_amt())){
			return false;
		}

		return true;
	}

	private ProdProdrepay queryProdProdpay(String plat_no,String prod_id,String repay_num){
		ProdProdrepayExample prodProdrepayExample=new ProdProdrepayExample();
		ProdProdrepayExample.Criteria criteria=prodProdrepayExample.createCriteria();
		criteria.andPlat_noEqualTo(plat_no);
		criteria.andProd_idEqualTo(prod_id);
		criteria.andRepay_numEqualTo(Integer.parseInt(repay_num));
		criteria.andEnabledEqualTo(Constants.ENABLED);
		List<ProdProdrepay> prodProdrepayList=prodRepayMapper.selectByExample(prodProdrepayExample);
		if(prodProdrepayList.size()>0){
			return prodProdrepayList.get(0);
		}
		return null;
	}

	public void toRefund(BatchCustRepayOld custRepay) throws BusinessException{
		TransTransreq transTransReq=iTransReqService.queryTransTransReqByOrderno(custRepay.getDetail_no());
		try{
			//验证标是否存在
			ProdProductinfo checkProductInfo = prodSearchService.queryProdInfo(custRepay.getPlat_no(),custRepay.getProd_id());
			if(checkProductInfo == null){
				logger.info("【标的还款】========标的不存在");
				BaseResponse baseResponse=new BaseResponse();
				baseResponse.setRecode(BusinessMsg.INVALID_PRODUCT_ID);
				baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.INVALID_PRODUCT_ID));
				throw new BusinessException(baseResponse);
			}
			//检查标的余额
			List<AccountSubjectInfo> eaccCashAccount=accountQueryService.queryPlatAccountList(
					custRepay.getPlat_no(),checkProductInfo.getProd_account(),null,Ssubject.PROD.getCode());

			BigDecimal cashLeft=BigDecimal.ZERO;
			if(eaccCashAccount.size()>0){
				BigDecimal allAmt=BigDecimal.ZERO;
				for(AccountSubjectInfo as:eaccCashAccount){
					if(Tsubject.CASH.getCode().equals(as.getSubject())){
						cashLeft=as.getN_balance();
					}
					allAmt=allAmt.add(as.getN_balance());
				}
			}

			boolean isAccount=false;
			try{

				logger.info("【标的还款】========开始尝试虚拟账户交易");
				BigDecimal needCashAmt=custRepay.getReal_repay_amount();
				//利息
				if(custRepay.getReal_repay_val()!=null && custRepay.getReal_repay_val().compareTo(BigDecimal.ZERO)>0){
					needCashAmt=needCashAmt.add(custRepay.getReal_repay_val());
				}
				if(cashLeft.compareTo(needCashAmt)>=0 || (cashLeft.compareTo(needCashAmt)<0 && cashLeft.compareTo(BigDecimal.ZERO)>0)){
					isAccount=productRefundExtService.refundToAccount(custRepay,transTransReq,Tsubject.CASH.getCode());
				}else{
					isAccount=productRefundExtService.refundToAccount(custRepay,transTransReq,Tsubject.FLOAT.getCode());
				}
				if(!isAccount){
					logger.info("【标的还款】========用户有电子账户，终止交易");
					String nowKey="refund"+custRepay.getPlat_no();
					logger.info("【标的还款】========将未处理还款加入队列："+nowKey);
					CacheUtil.getCache().leftPush(nowKey, JSON.toJSONString(custRepay,GlobalConfig.serializerFeature));
				}else{
					logger.info("【标的还款】========虚拟账户交易成功");
					transTransReq.setReturn_code(BusinessMsg.SUCCESS);
					transTransReq.setReturn_msg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));
					transTransReq.setStatus(FlowStatus.SUCCESS.getCode());
					Object amtObj=CacheUtil.getCache().getAndNotSetAlive("Prod"+checkProductInfo.getProd_account()+"AllAmt");
					logger.info("【标的还款】==========标的账户："+checkProductInfo.getProd_account()+
							"，已经需累计还款："+amtObj);
					if(amtObj!=null){
						BigDecimal prodFundAllAmt=(BigDecimal) amtObj;
						if(custRepay.getReal_repay_amount()!=null){
							prodFundAllAmt=prodFundAllAmt.subtract(custRepay.getReal_repay_amount());
						}
						if(custRepay.getReal_repay_val()!=null){
							prodFundAllAmt=prodFundAllAmt.subtract(custRepay.getReal_repay_val());
						}
						logger.info("【标的还款】==========剩余需还款："+prodFundAllAmt);
						if(prodFundAllAmt.compareTo(BigDecimal.ZERO)<=0){
							logger.info("【标的还款】==========删除需还款金额缓存");
							CacheUtil.getCache().del("Prod"+checkProductInfo.getProd_account()+"AllAmt");
						}else{
							logger.info("【标的还款】==========重新设置需还款金额");
							CacheUtil.getCache().set("Prod"+checkProductInfo.getProd_account()+"AllAmt",prodFundAllAmt);
						}
					}
				}
			}catch (Exception e){
				logger.info("【标的还款】========虚拟账户交易失败");
				Object amtObj=CacheUtil.getCache().getAndNotSetAlive("Prod"+checkProductInfo.getProd_account()+"AllAmt");
				logger.info("【标的还款】==========标的账户："+checkProductInfo.getProd_account()+
						"，已经需累计还款："+amtObj);
				if(amtObj!=null){
					BigDecimal prodFundAllAmt=(BigDecimal) amtObj;
					if(custRepay.getReal_repay_amount()!=null){
						prodFundAllAmt=prodFundAllAmt.subtract(custRepay.getReal_repay_amount());
					}
					if(custRepay.getReal_repay_val()!=null){
						prodFundAllAmt=prodFundAllAmt.subtract(custRepay.getReal_repay_val());
					}
					logger.info("【标的还款】==========剩余需还款："+prodFundAllAmt);
					if(prodFundAllAmt.compareTo(BigDecimal.ZERO)<=0){
						logger.info("【标的还款】==========删除需还款金额缓存");
						CacheUtil.getCache().del("Prod"+checkProductInfo.getProd_account()+"AllAmt");
					}else{
						logger.info("【标的还款】==========重新设置需还款金额");
						CacheUtil.getCache().set("Prod"+checkProductInfo.getProd_account()+"AllAmt",prodFundAllAmt);
					}
				}
				BaseResponse baseResponse=new BaseResponse();
				if(e instanceof BusinessException){
					baseResponse=((BusinessException) e).getBaseResponse();
				}else{
					baseResponse.setRecode(BusinessMsg.RUNTIME_EXCEPTION);
					baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.RUNTIME_EXCEPTION));
				}
				throw new BusinessException(baseResponse);
			}
		}catch (Exception e){
			BaseResponse baseResponse=new BaseResponse();
			if(e instanceof BusinessException){
				baseResponse=((BusinessException)e).getBaseResponse();
			}else{
				baseResponse.setRecode(BusinessMsg.RUNTIME_EXCEPTION);
				baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.RUNTIME_EXCEPTION));
			}
			transTransReq.setReturn_code(baseResponse.getRecode());
			transTransReq.setReturn_msg(baseResponse.getRemsg());
			transTransReq.setStatus(FlowStatus.FAIL.getCode());
			iTransReqService.insert(transTransReq);
		}
		//只要不是处理中状态，更新流水状态
		if(!OrderStatus.PROCESSING.getCode().equals(transTransReq.getStatus())){
			iTransReqService.insert(transTransReq);
		}

	}

	@Override
	public boolean addCompensationInfo(ProdCompensationInfo compensationInfo ) throws BusinessException {
		prodCompensationInfoMapper.insert(compensationInfo);
		return true;
	}

	@Override
	public boolean addCompensationRepay (ProdCompensationRepay prodCompensationRepay) throws BusinessException {
		prodCompensationRepayMapper.insert(prodCompensationRepay);
		return true;
	}

}
