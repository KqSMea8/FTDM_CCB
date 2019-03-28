package com.sunyard.sunfintech.account.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.sunyard.sunfintech.account.enums.AMTTYPE;
import com.sunyard.sunfintech.account.model.bo.*;
import com.sunyard.sunfintech.account.provider.IAccountAssetService;
import com.sunyard.sunfintech.account.provider.IAccountQueryService;
import com.sunyard.sunfintech.account.provider.IAccountTransferService;
import com.sunyard.sunfintech.account.provider.IAccountTransferThirdParty;
import com.sunyard.sunfintech.core.TransConsts;
import com.sunyard.sunfintech.core.base.BaseResponse;
import com.sunyard.sunfintech.core.base.BaseServiceSimple;
import com.sunyard.sunfintech.core.config.GlobalConfig;
import com.sunyard.sunfintech.core.dic.CusType;
import com.sunyard.sunfintech.core.dic.FlowStatus;
import com.sunyard.sunfintech.core.dic.Payment;
import com.sunyard.sunfintech.core.dic.Ssubject;
import com.sunyard.sunfintech.core.exception.BusinessException;
import com.sunyard.sunfintech.core.exception.BusinessMsg;
import com.sunyard.sunfintech.core.util.CacheUtil;
import com.sunyard.sunfintech.core.util.DateUtils;
import com.sunyard.sunfintech.core.util.SeqUtil;
import com.sunyard.sunfintech.custdao.mapper.CustAccountSubjectInfoMapper;
import com.sunyard.sunfintech.dao.entity.AccountSubjectInfo;
import com.sunyard.sunfintech.dao.entity.EaccAccountamtlist;
import com.sunyard.sunfintech.dao.entity.EaccTransTransreqWithBLOBs;
import com.sunyard.sunfintech.pub.provider.IEaccTransTransreqService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

/**
 * @author heroy
 * @version 2017/4/12
 */
@Service(interfaceClass = IAccountAssetService.class)
@CacheConfig(cacheNames="accountAssetService")
@org.springframework.stereotype.Service("accountAssetService")
public class AccountAssetService extends BaseServiceSimple implements IAccountAssetService {
	
    @Autowired
	private CustAccountSubjectInfoMapper custAccountSubjectInfoMapper;

	@Autowired
	private IAccountTransferThirdParty accountTransferThirdParty;

	@Autowired
	private IEaccTransTransreqService eaccTransTransreqService;

	@Autowired
	private IAccountTransferService accountTransferService;

	@Autowired
	private IAccountQueryService accountQueryService;

//	@Autowired
//	private PlatPlatinfoMapper platPlatinfoMapper;
	
    @Override
    @Transactional(propagation = Propagation.NESTED)
    public Boolean charge(EaccAccountamtlist income) {
		logger.info("加款进来第一步，扣款参数流水："+income.toString());
		String keys="charge"+income.getTrans_serial();
		Boolean isRunning=false;
		try {
			//如果是快捷充值、代扣充值、网关充值、平台充值，做幂等
			if(TransConsts.QUICK_CONFIRM_CODE.equals(income.getTrans_code()) || //快捷充值确认
					TransConsts.QUICK_RECHARGE_CODE.equals(income.getTrans_code()) || //快捷充值
					TransConsts.COLLECTION_CODE.equals(income.getTrans_code()) || //代扣充值
					TransConsts.GATEWAY_RECHARGE_CODE.equals(income.getTrans_code()) || //网关充值
					TransConsts.PLAT_CHARGE_CODE.equals(income.getTrans_code())){ //平台充值
                isRunning=CacheUtil.getCache().setnx(keys,"1");
                if(!isRunning){
                    throw new RuntimeException("干杯[]~(￣▽￣)~*干完杯撒酒疯掀桌(╯‵□′)╯︵┻━┻");
                }
                Boolean exitflag=false;
                do{
                    CacheUtil.getCache().expire(keys,360);
                    Long ttl=CacheUtil.getCache().ttl(keys);
                    if(ttl<=360){
                        exitflag=true;
                    }
                }while (!exitflag);
				List<EaccAccountamtlist> eaccAccountamtlistList=accountTransferService.queryEaccAccountamtlistByTransSerial(income.getTrans_serial());
				if(eaccAccountamtlistList.size()>0){
					return true;
				}
			}
			AccountSubjectInfo accountSubjectInfo = null;
			if(Ssubject.EACCOUNT.getCode().equals(income.getSub_subject())){
				List<AccountSubjectInfo> accountSubjectInfoList=accountQueryService.queryPlatAccountList(
						null,income.getPlatcust(),income.getSubject(),income.getSub_subject());
				accountSubjectInfo=accountSubjectInfoList.get(0);
			}else{
				List<AccountSubjectInfo> accountSubjectInfoList=accountQueryService.queryPlatAccountList(
						income.getPlat_no(),income.getPlatcust(),income.getSubject(),income.getSub_subject());
				accountSubjectInfo=accountSubjectInfoList.get(0);
			}
			if(accountSubjectInfo==null){
				//账户不存在
				BaseResponse baseResponse = new BaseResponse();
				baseResponse.setRecode(BusinessMsg.INVALID_ACCOUNT);
				baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.INVALID_ACCOUNT)+"：转账失败，加款人账户不存在");
				throw new BusinessException(baseResponse);
			}
			//扣款流水将对手账户反向即为加款流水
			income.setAmt_type(AMTTYPE.Income.getCode());

			income.setPlat_no(accountSubjectInfo.getPlat_no());

			//通过消息队列添加流水
			logger.info("记录加款资金流水："+income.toString());

			//加款
			income.setId(accountSubjectInfo.getId());
			if(null == income.getUpdate_by()) income.setUpdate_by(SeqUtil.RANDOM_KEY);
			income.setUpdate_time(new Date());
			int rows=custAccountSubjectInfoMapper.addAmt(income);
			if(rows<=0){
				BaseResponse baseResponse = new BaseResponse();
				baseResponse.setRecode(BusinessMsg.SQL_ERROR);
				baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.SQL_ERROR)+"：加款失败");
				throw new BusinessException(baseResponse);
			}

			income.setId(null);
			accountTransferService.addTransFlow(income);
//			jmsProducer.sendMessage(MqQueneNames.EACC_ACCOUNTAMTLIST_ADD, income);

			//通过消息队列添加流水
			//jmsTemplate.convertAndSend(MqQueneNames.EACC_ACCOUNTAMTLIST_ADD	, expense);
		} catch (Exception e) {
			BaseResponse baseResponse=new BaseResponse();
			if(e instanceof BusinessException){
				baseResponse=((BusinessException) e).getBaseResponse();
			}else{
				logger.error(e);
				baseResponse.setRecode(BusinessMsg.DATEBASE_EXCEPTION);
				baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.DATEBASE_EXCEPTION));
			}
			throw new BusinessException(baseResponse);
		}
		finally {
			if(isRunning){
				CacheUtil.getCache().del(keys);
			}
		}
		return true;
    }

	@Override
	@Transactional
	public boolean batchCharge(List<EaccAccountamtlist> eaccAccountamtlists) {
		for(EaccAccountamtlist eaccAccountamtlist :eaccAccountamtlists){
            charge(eaccAccountamtlist);
        }
        return true;
	}

	@Override
    @Transactional(propagation = Propagation.NESTED)
    public Boolean withdraw(EaccAccountamtlist expense) {
		logger.info("扣款进来第一步，扣款参数流水："+expense.toString());
//		String lockKey = getLockKey(expense.getPlatcust());
		try {
			//扣款流水将对手账户反向即为加款流水
//			expense.setAmt_type(AMTTYPE.Expense.getCode());
//			boolean lock = CacheUtil.getLock(lockKey);
//			/*CacheUtil.getCache().set("test：test1234","123245678");
//			CacheUtil.getCache().get("test：test1234")	;*/
//			while (!lock) {
//				sleep(50);//没取到锁50毫秒后重试
//				lock = CacheUtil.getLock(lockKey);
//			}
			AccountSubjectInfo accountSubjectInfo = null;
			if(Ssubject.EACCOUNT.getCode().equals(expense.getSub_subject())){
				List<AccountSubjectInfo> accountSubjectInfoList=accountQueryService.queryPlatAccountList(
						null,expense.getPlatcust(),expense.getSubject(),expense.getSub_subject());
				accountSubjectInfo=accountSubjectInfoList.get(0);
			}else{
				List<AccountSubjectInfo> accountSubjectInfoList=accountQueryService.queryPlatAccountList(
						expense.getPlat_no(),expense.getPlatcust(),expense.getSubject(),expense.getSub_subject());
				accountSubjectInfo=accountSubjectInfoList.get(0);
			}
			if(accountSubjectInfo==null){
				logger.info("转账失败，扣款人账户不存在");
				//账户不存在
				BaseResponse baseResponse = new BaseResponse();
				baseResponse.setRecode(BusinessMsg.INVALID_ACCOUNT);
				baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.INVALID_ACCOUNT)+"：转账失败，扣款人账户不存在");
				throw new BusinessException(baseResponse);
			}
			//检查可用余额
			logger.info("账户"+accountSubjectInfo.getPlatcust()+"，可用金额"+accountSubjectInfo.getN_balance()+"，转账金额:"+expense.getAmt());
			if(accountSubjectInfo.getN_balance().compareTo(expense.getAmt())<0){
				logger.info("转账失败，账户余额不足，转账参数：" +accountSubjectInfo.toString());
				BaseResponse baseResponse = new BaseResponse();
				baseResponse.setRecode(BusinessMsg.TRANSFER_FAILED_RETRY);
				baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.TRANSFER_FAILED_RETRY));
				throw new BusinessException(baseResponse);
			}

			expense.setPlat_no(accountSubjectInfo.getPlat_no());

			expense.setId(accountSubjectInfo.getId());
			if(null == expense.getUpdate_by()) expense.setUpdate_by(SeqUtil.RANDOM_KEY);
			expense.setUpdate_time(new Date());
            int successCount=custAccountSubjectInfoMapper.substractAmt(expense);//传入的id为账户id
			if(successCount<=0){
				logger.info("转账失败，现金/在途账户余额不足，转账参数：" +accountSubjectInfo.toString());
				BaseResponse baseResponse = new BaseResponse();
				baseResponse.setRecode(BusinessMsg.TRANSFER_FAILED_RETRY);
				baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.TRANSFER_FAILED_RETRY));
				throw new BusinessException(baseResponse);
			}
//			CacheUtil.unlock(lockKey);

			//添加转账流水
			//通过消息队列添加流水
			expense.setId(null);
			expense.setAmt_type(AMTTYPE.Expense.getCode());
			logger.info("记录扣款资金流水："+expense.toString());
			accountTransferService.addTransFlow(expense);
//			jmsProducer.sendMessage(MqQueneNames.EACC_ACCOUNTAMTLIST_ADD, expense);

			//通过消息队列添加流水
			//jmsTemplate.convertAndSend(MqQueneNames.EACC_ACCOUNTAMTLIST_ADD	, expense);
		} catch (Exception e) {
    		BaseResponse baseResponse=new BaseResponse();
    		if(e instanceof BusinessException){
    			baseResponse=((BusinessException) e).getBaseResponse();
			}else{
    			logger.error(e);
    			baseResponse.setRecode(BusinessMsg.DATEBASE_EXCEPTION);
    			baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.DATEBASE_EXCEPTION));
			}
//			CacheUtil.unlock(lockKey);
			throw new BusinessException(baseResponse);
		}
		return true;
    }

//	@Override
//	@Transactional
//	public boolean batchCharge(List<EaccAccountamtlist> eaccAccountamtlist) throws BusinessException {
//
//    	try{
//			PlatPlatinfoExample platPlatinfoExample = new PlatPlatinfoExample();
//			PlatPlatinfoExample.Criteria criteria = platPlatinfoExample.createCriteria();
//			criteria.andEnabledEqualTo(Constants.ENABLED);
//			criteria.andPlat_noEqualTo(eaccAccountamtlist.get(0).getPlat_no());
//			List<PlatPlatinfo> platPlatinfoList = platPlatinfoMapper.selectByExample(platPlatinfoExample);
//			if(platPlatinfoList==null || platPlatinfoList.size()==0){
//				logger.info("平台信息表对应平台没有配置集团信息，或者找不到该平台信息");
//				return false;
//			}
//
//			//查询平台账户
//			AccountSubjectInfo plataccountSubjectInfo = accountQueryService.queryAccount(eaccAccountamtlist.get(0).getPlat_no(),null, Tsubject.CASH.getCode(), Ssubject.PLAT.getCode());
//			if (plataccountSubjectInfo == null){
//				logger.error("【线下充值/线下还款】平台账户不存在");
//				throw new BusinessException(BusinessMsg.INVALID_ACCOUNT,BusinessMsg.getMsg(BusinessMsg.INVALID_ACCOUNT) +  "，平台账户不存在");
//			}
//
//			List<EaccTransTransreqWithBLOBs> eaccTransTransreqList=new ArrayList<EaccTransTransreqWithBLOBs>();
//			EaccAccountamtlist eAccountamtlist2 = null;
//			for(EaccAccountamtlist eAccountamtlist:eaccAccountamtlist){
//				//虚拟账户加款
//				charge(eAccountamtlist);
//
//				try{
//					if(Ssubject.EACCOUNT.getCode().equals(eAccountamtlist.getSub_subject())){
//						eAccountamtlist2 = new EaccAccountamtlist();
//						BeanUtils.copyProperties(eAccountamtlist2, eAccountamtlist);
//						eAccountamtlist2.setPlat_no(eAccountamtlist.getPlat_no());
//						eAccountamtlist2.setOppo_plat_no(eAccountamtlist.getPlat_no());
//						eAccountamtlist2.setSubject(plataccountSubjectInfo.getSubject());
//						eAccountamtlist2.setSub_subject(plataccountSubjectInfo.getSub_subject());
//						eAccountamtlist2.setPlatcust(plataccountSubjectInfo.getPlatcust());
//						eAccountamtlist2.setOppo_subject(eAccountamtlist.getSubject());
//						eAccountamtlist2.setOppo_sub_subject(eAccountamtlist.getSub_subject());
//						eAccountamtlist2.setOppo_platcust(eAccountamtlist.getPlatcust());
//						eAccountamtlist2.setAmt(eAccountamtlist.getAmt());
//						eaccTransTransreqList.add(eaccountTrans(eAccountamtlist2,platPlatinfoList.get(0).getMall_no()));
//					}
//				}catch (BusinessException e){
//					logger.error(e);
//					throw new BusinessException(e.getBaseResponse());
//				}
//			}
//			if(eaccTransTransreqList.size()==1){
//				pay(eaccTransTransreqList.get(0));
//			}else if(eaccTransTransreqList.size()>1){
//				batchPay(eaccTransTransreqList);
//			}
//		}catch (Exception e){
//			BaseResponse baseResponse=new BaseResponse();
//			if(e instanceof BusinessException){
//				baseResponse=((BusinessException) e).getBaseResponse();
//			}else{
//				logger.error(e);
//				baseResponse.setRecode(BusinessMsg.DATEBASE_EXCEPTION);
//				baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.DATEBASE_EXCEPTION));
//			}
//			throw new BusinessException(baseResponse);
//		}
//		return true;
//	}

	@Override
	@Transactional(propagation = Propagation.NESTED)
	public Boolean negativeWithdraw(EaccAccountamtlist expense) {
		logger.info("vip扣款："+expense.toString());
		try {
			//扣款流水将对手账户反向即为加款流水
			expense.setAmt_type(AMTTYPE.Expense.getCode());
			AccountSubjectInfo accountSubjectInfo = accountQueryService.queryAccount(expense.getPlat_no(),expense.getPlatcust(),expense.getSubject(),expense.getSub_subject());
			if(accountSubjectInfo==null){
				logger.info("转账失败，扣款人账户不存在");
				BaseResponse baseResponse = new BaseResponse();
				baseResponse.setRecode(BusinessMsg.INVALID_ACCOUNT);
				baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.INVALID_ACCOUNT)+",转账失败，扣款人账户不存在");
				throw new BusinessException(baseResponse);
			}
			//检查可用余额
			//如果是平台垫付账户或者行内垫付账户可以记为负账
			logger.info("vip扣款，检查是否是行内垫付账户");
			if(!accountSubjectInfo.getSub_subject().equals(Ssubject.INLINEPAYMENT.getCode())
					&& !Payment.isPayment(accountSubjectInfo.getSub_subject())){
				logger.info("不是行内垫付账户");
				if(accountSubjectInfo.getN_balance().compareTo(expense.getAmt())<0){
					logger.info("转账失败，账户余额不足，可用金额"+accountSubjectInfo.getN_balance()+"，转账金额:"+expense.getAmt()+ "转账参数：" +accountSubjectInfo.toString());
					BaseResponse baseResponse = new BaseResponse();
					baseResponse.setRecode(BusinessMsg.BALANCE_LACK);
					baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.BALANCE_LACK));
					throw new BusinessException(baseResponse);
				}
			}
			expense.setId(accountSubjectInfo.getId());
			int i = 0;
			if(null == expense.getUpdate_by()) expense.setUpdate_by(SeqUtil.RANDOM_KEY);
			expense.setUpdate_time(new Date());
			if(!accountSubjectInfo.getSub_subject().equals(Ssubject.INLINEPAYMENT.getCode())
					&& !Payment.isPayment(accountSubjectInfo.getSub_subject())){
				i = custAccountSubjectInfoMapper.substractAmt(expense);//传入的id为账户id
			}else {
				i = custAccountSubjectInfoMapper.substractAmtVIP(expense);//传入的id为账户id
			}

			if(i < 1){
				logger.info("vip转账失败，账户余额不足，转账参数：" +accountSubjectInfo.toString());
				throw new BusinessException(BusinessMsg.BALANCE_LACK, "出账账户余额不足，出账platcust：" + accountSubjectInfo.getPlatcust());
			}

			//添加转账流水
			//通过消息队列添加流水
			expense.setId(null);
			logger.info("记录扣款资金流水："+expense.toString());
			accountTransferService.addTransFlow(expense);

		} catch (Exception e) {
			BaseResponse baseResponse=new BaseResponse();
			if(e instanceof BusinessException){
				baseResponse=((BusinessException) e).getBaseResponse();
			}else{
				logger.error(e);
				baseResponse.setRecode(BusinessMsg.DATEBASE_EXCEPTION);
				baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.DATEBASE_EXCEPTION));
			}
			throw new BusinessException(baseResponse);
		}
		return true;
	}

	public boolean pay(EaccTransTransreqWithBLOBs eaccTransTransreq)throws BusinessException{
		Map<String,Object> params=new HashMap<>();
		params.put("partner_id","10000001");
		params.put("partner_serial_no",eaccTransTransreq.getTrans_serial());
		params.put("partner_trans_date", DateUtils.getyyyyMMddDate());
		params.put("partner_trans_time",DateUtils.getHHmmssTime());
		params.put("occur_balance",eaccTransTransreq.getTrans_amt());
		params.put("summary","pay for eaccount");//用户账户转账路径
		params.put("client_name",eaccTransTransreq.getOppo_name());
		params.put("card_no",eaccTransTransreq.getOppo_eaccount());
		params.put("receive_url","http://www.sunyard.com");
		if(CusType.PERSONAL.getCode().equals(String.valueOf(eaccTransTransreq.getOppo_property()))){
			params.put("client_property","0");
		}else{
			params.put("client_property","1");
		}
		params.put("remark",eaccTransTransreq.getEaccount().substring(eaccTransTransreq.getEaccount().length()-5)
				+"->"+eaccTransTransreq.getOppo_eaccount().substring(eaccTransTransreq.getOppo_eaccount().length()-5));//电子账户转账路径
		params.put("prcptcd","04031000");
		params.put("bank_id","04031000");
		params.put("dr_acct",eaccTransTransreq.getEaccount());
		params.put("dr_acct_name",eaccTransTransreq.getName());
		if(CusType.PERSONAL.getCode().equals(String.valueOf(eaccTransTransreq.getProperty()))){
			params.put("dr_acct_type","0");
		}else{
			params.put("dr_acct_type","3");
		}
		EpayAgentPayRealResponse response=accountTransferThirdParty.eaccTransfer(params,eaccTransTransreq.getMall_no());
		logger.info("【线下充值/还款代付】=========E支付返回："+response.toString());
		eaccTransTransreq.setReq_params(JSON.toJSONString(params, GlobalConfig.serializerFeature));
		eaccTransTransreq.setRes_params(JSON.toJSONString(response, GlobalConfig.serializerFeature));
		if(response.getData()==null || !(("3".trim()).equals(response.getData().get(0).getPay_status().trim()))){
			logger.info("【线下充值/还款代付】=========电子账户转账失败");
			//记录电子账户转账失败流水
			eaccTransTransreq.setStatus(Integer.valueOf(FlowStatus.FAIL.getCode()));
			eaccTransTransreqService.addFlow(eaccTransTransreq);
			throw new BusinessException(BusinessMsg.PAYMENT_FAILED,"电子账户转账失败");
		}
		//记录电子账户转账成功流水
		eaccTransTransreq.setStatus(Integer.valueOf(FlowStatus.SUCCESS.getCode()));
		eaccTransTransreqService.addFlow(eaccTransTransreq);
		return true;
	}

	public boolean batchPay(List<EaccTransTransreqWithBLOBs> eaccTransTransreqList)throws BusinessException{
		Map<String,Object> params=new HashMap<>();
		String trans_serial=eaccTransTransreqList.get(0).getParent_trans_serial();
		params.put("partner_id","10000001");
		params.put("partner_serial_no",trans_serial);
		params.put("partner_trans_date",DateUtils.getyyyyMMddDate());
		params.put("partner_trans_time",DateUtils.getHHmmssTime());
		params.put("third_no",eaccTransTransreqList.get(0).getEaccount());
		params.put("transaction_flag","0");
		List<BatchPayDetail> detailList=new ArrayList<>();
		BigDecimal allAmt=BigDecimal.ZERO;
		for(EaccTransTransreqWithBLOBs eaccTransTransreq:eaccTransTransreqList){
			BatchPayDetail detail=new BatchPayDetail();
			detail.setOccur_balance(eaccTransTransreq.getTrans_amt());
			detail.setCard_no(eaccTransTransreq.getOppo_eaccount());
			detail.setSummary(eaccTransTransreq.getEaccount().substring(eaccTransTransreq.getEaccount().length()-5)
					+"->"+eaccTransTransreq.getOppo_eaccount().substring(eaccTransTransreq.getOppo_eaccount().length()-5));
			allAmt=allAmt.add(eaccTransTransreq.getTrans_amt());
			params.put("details",JSON.toJSONString(detail));
			eaccTransTransreq.setReq_params(JSON.toJSONString(params,GlobalConfig.serializerFeature));
			detailList.add(detail);
		}
		params.put("total_num",eaccTransTransreqList.size());
		params.put("total_balance",allAmt.toString());
		params.put("details",JSON.toJSONString(detailList));
		try{
			BatchPayResponse response=accountTransferThirdParty.batchPay(params,eaccTransTransreqList.get(0).getMall_no());

			logger.info("【线下充值/还款批量代付】=========E支付返回："+response.toString());
			if(response.getData()!=null){
				//线程休眠0.5秒
				boolean exitFlag=false;
				String status=FlowStatus.INPROCESS.getCode();
				int maxQueryNum=10;
				do{
					Thread.sleep(500);
					//查询交易状态
					Map<String,Object> queryParams=getQueryParams(trans_serial);
					BatchPayQueryResponse queryResponse=accountTransferThirdParty.batchPayQuery(queryParams,eaccTransTransreqList.get(0).getMall_no());
					if(queryResponse.getData()!=null){
						List<BatchPayQueryResponseDataDetail> dataList=queryResponse.getData().get(0).getDetail();
						if("3".equals(dataList.get(0).getPay_status())){
							//交易成功
							status=FlowStatus.SUCCESS.getCode();
							exitFlag=true;
						}else if("7".equals(dataList.get(0).getPay_status())){
							//交易失败
							status=FlowStatus.FAIL.getCode();
							exitFlag=true;
						}
					}else{
						//请求失败
						logger.info("【线下充值/还款批量代付】=========订单状态查询失败："+queryResponse.getError_info());
						exitFlag=true;
					}
					if(maxQueryNum<=0){
						exitFlag=true;
					}
					maxQueryNum--;
				}while (!exitFlag);
				for(EaccTransTransreqWithBLOBs eaccTransTransreq:eaccTransTransreqList){
					//记录电子账户转账流水
					eaccTransTransreq.setStatus(Integer.valueOf(status));
					eaccTransTransreqService.addFlow(eaccTransTransreq);
				}
			}else{
				logger.info("【线下充值/还款批量代付】=========电子账户转账失败："+response.getError_info());
				//记录电子账户转账失败流水
				for(EaccTransTransreqWithBLOBs eaccTransTransreq:eaccTransTransreqList){
					eaccTransTransreq.setRes_params(JSON.toJSONString(response,GlobalConfig.serializerFeature));
					eaccTransTransreq.setStatus(Integer.valueOf(FlowStatus.FAIL.getCode()));
					eaccTransTransreqService.addFlow(eaccTransTransreq);
				}
				throw new BusinessException(BusinessMsg.PAYMENT_FAILED,"电子账户转账失败："+response.getError_info());
			}
		}catch (Exception e){
			logger.info("【线下充值/还款批量代付】=========电子账户转账失败："+e);
			BaseResponse baseResponse=new BaseResponse();
			if(e instanceof BusinessException){
				baseResponse=((BusinessException) e).getBaseResponse();
			}else{
				baseResponse.setRecode(BusinessMsg.RUNTIME_EXCEPTION);
				baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.RUNTIME_EXCEPTION));
			}
			//记录电子账户转账失败流水
			for(EaccTransTransreqWithBLOBs eaccTransTransreq:eaccTransTransreqList){
				eaccTransTransreq.setRes_params(baseResponse.getRemsg());
				eaccTransTransreq.setStatus(Integer.valueOf(FlowStatus.FAIL.getCode()));
				eaccTransTransreqService.addFlow(eaccTransTransreq);
			}
			throw new BusinessException(baseResponse);
		}
		return true;
	}

	private Map<String,Object> getQueryParams(String serial_no){
		Map<String,Object> queryParams=new HashMap<>();
		/**
		 *
		 partner_id	C8	合作商编号	Y
		 partner_serial_no	C32	合作商流水，代表本次查询动作，建议格式：yyyymmddhhmmss	Y
		 partner_trans_date	C8	合作商交易日期	Y
		 partner_trans_time	C6	合作商交易时间	Y
		 original_serial_no	C32	原合作商流水号，原批量代收、批量代付合作商流水号	Y
		 cert_sign	C500	签名串	Y
		 */
		queryParams.put("partner_id","10000001");
		queryParams.put("partner_serial_no",SeqUtil.getSeqNum());
		queryParams.put("partner_trans_date",DateUtils.getyyyyMMddDate());
		queryParams.put("partner_trans_time",DateUtils.getHHmmssTime());
		queryParams.put("original_serial_no",serial_no);
		return queryParams;
	}

//	private EaccTransTransreqWithBLOBs eaccountTrans(EaccAccountamtlist eaccAccountamtlist,String mall_no)throws BusinessException{
//		//查询电子账户号
//		String outAccount=null;
//		String inAccount=null;
//		String outAccountName=null;
//		String inAccountName=null;
//		boolean outPerson=false;
//		boolean inPerson=false;
//		if(Ssubject.EACCOUNT.getCode().equals(eaccAccountamtlist.getSub_subject())){
//			//如果转出账户是电子账户，查询电子账户号
//			EaccUserinfo eaccUserinfo=accountQueryService.getEaccUserinfo(mall_no,eaccAccountamtlist.getPlatcust());
//			outAccount=eaccUserinfo.getEaccount();
//			outAccountName=eaccUserinfo.getName();
//			outPerson=true;
//		}else{
//			//如果转出账户不是电子账户号，查询存管户
//			PlatCardinfo platCardinfo=accountQueryService.getPlatcardinfo(eaccAccountamtlist.getPlat_no(),PlatAccType.PLATTRUST.getCode());
//			outAccount=platCardinfo.getCard_no();
//			outAccountName=platCardinfo.getCard_name();
//			outPerson=false;
//		}
//		if(Ssubject.EACCOUNT.getCode().equals(eaccAccountamtlist.getOppo_sub_subject())){
//			//如果转入账户是电子账户，查询电子账户号
//			EaccUserinfo eaccUserinfo=accountQueryService.getEaccUserinfo(mall_no,eaccAccountamtlist.getOppo_platcust());
//			inAccount=eaccUserinfo.getEaccount();
//			inAccountName=eaccUserinfo.getName();
//			inPerson=true;
//		}else{
//			//如果转入账户不是电子账户号，查询存管户
//			PlatCardinfo platCardinfo=accountQueryService.getPlatcardinfo(eaccAccountamtlist.getOppo_plat_no(),PlatAccType.PLATTRUST.getCode());
//			inAccount=platCardinfo.getCard_no();
//			inAccountName=platCardinfo.getCard_name();
//			inPerson=false;
//		}
//		EaccTransTransreqWithBLOBs eaccTransTransreq=new EaccTransTransreqWithBLOBs();
//		eaccTransTransreq.setMall_no(mall_no);
//		eaccTransTransreq.setParent_trans_serial(eaccAccountamtlist.getTrans_serial());
//		eaccTransTransreq.setTrans_serial(SeqUtil.getSeqNum());
//		eaccTransTransreq.setEaccount(outAccount);
//		eaccTransTransreq.setName(outAccountName);
//		if(outPerson){
//			eaccTransTransreq.setProperty(Integer.valueOf(CusType.PERSONAL.getCode()));
//		}else{
//			eaccTransTransreq.setProperty(Integer.valueOf(CusType.COMPANY.getCode()));
//		}
//		eaccTransTransreq.setOppo_eaccount(inAccount);
//		eaccTransTransreq.setOppo_name(inAccountName);
//		if(inPerson){
//			eaccTransTransreq.setOppo_property(Integer.valueOf(CusType.PERSONAL.getCode()));
//		}else{
//			eaccTransTransreq.setOppo_property(Integer.valueOf(CusType.COMPANY.getCode()));
//		}
//		eaccTransTransreq.setTrans_amt(eaccAccountamtlist.getAmt());
//		return eaccTransTransreq;
//	}
}
