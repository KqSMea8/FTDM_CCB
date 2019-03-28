package com.sunyard.sunfintech.account.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.sunyard.sunfintech.account.enums.AMTTYPE;
import com.sunyard.sunfintech.account.provider.IAccountFrozenService;
import com.sunyard.sunfintech.account.provider.IAccountQueryService;
import com.sunyard.sunfintech.account.provider.IAccountTransferService;
import com.sunyard.sunfintech.core.TransConsts;
import com.sunyard.sunfintech.core.base.BaseResponse;
import com.sunyard.sunfintech.core.base.BaseServiceSimple;
import com.sunyard.sunfintech.core.dic.AmtType;
import com.sunyard.sunfintech.core.dic.Ssubject;
import com.sunyard.sunfintech.core.dic.SwitchState;
import com.sunyard.sunfintech.core.dic.Tsubject;
import com.sunyard.sunfintech.core.exception.BusinessException;
import com.sunyard.sunfintech.core.exception.BusinessMsg;
import com.sunyard.sunfintech.core.util.CacheUtil;
import com.sunyard.sunfintech.core.util.SeqUtil;
import com.sunyard.sunfintech.core.util.StringUtils;
import com.sunyard.sunfintech.custdao.mapper.CustAccountSubjectInfoMapper;
import com.sunyard.sunfintech.dao.entity.AccountSubjectInfo;
import com.sunyard.sunfintech.dao.entity.EaccAccountamtlist;
import com.sunyard.sunfintech.dao.mapper.EaccAccountamtlistMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author heroy
 * @version 2017/4/12
 */
@Service(interfaceClass = IAccountFrozenService.class)
@CacheConfig(cacheNames="accountFrozenService")
@org.springframework.stereotype.Service("accountFrozenService")
public class AccountFrozenService extends BaseServiceSimple implements IAccountFrozenService {
	
	@Autowired
	private CustAccountSubjectInfoMapper custAccountSubjectInfoMapper;

	@Autowired
	private EaccAccountamtlistMapper eaccAccountamtlistMapper;

	@Autowired
	private IAccountTransferService accountTransferService;

	@Autowired
	private IAccountQueryService accountQueryService;

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
				isRunning= CacheUtil.getCache().setnx(keys,"1");
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
	public Boolean freeze(EaccAccountamtlist expense) throws BusinessException{
		logger.info("【资金冻结】-->"+expense.toString());
		try {
			//扣款流水将对手账户反向即为加款流水
			expense.setAmt_type(AmtType.FROZEN.getCode());
			expense.setTrans_code(TransConsts.FUND_FREEZE_CODE);
			expense.setTrans_name(TransConsts.FUND_FREEZE_NAME);

			AccountSubjectInfo accountSubjectInfo = custAccountSubjectInfoMapper.selectByUnionKey(expense);
			//检查可用余额
			if(accountSubjectInfo.getN_balance().compareTo(expense.getAmt())<0){
				BaseResponse baseResponse = new BaseResponse();
				baseResponse.setRecode(BusinessMsg.FAIL);
				baseResponse.setRemsg(String.format("账户可冻结余额不足，可用金额【%s】，转账金额【%s】", accountSubjectInfo.getN_balance(),expense.getAmt()));
				throw new BusinessException(baseResponse);
			}
			expense.setId(accountSubjectInfo.getId());
			if(null == expense.getUpdate_by()) expense.setUpdate_by(SeqUtil.RANDOM_KEY);
			if(null == expense.getUpdate_time()) expense.setUpdate_time(new Date());
			custAccountSubjectInfoMapper.freezeAmt(expense);//传入的id为账户id
			expense.setId(null);

			accountTransferService.addTransFlow(expense);

		} catch (Exception e) {
			BaseResponse baseResponse=new BaseResponse();
			if(e instanceof BusinessException){
				baseResponse=((BusinessException) e).getBaseResponse();
			}else{
				logger.error(e);
				baseResponse.setRecode(BusinessMsg.RUNTIME_EXCEPTION);
				baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.RUNTIME_EXCEPTION));
			}
			throw new BusinessException(baseResponse);
		}
		return true;
	}

	@Override
	@Transactional
	public Boolean unfreeze(EaccAccountamtlist unfrozen) throws BusinessException{
		logger.info("【资金解冻】-->" + unfrozen.toString());
		try {
			//查询交易流水
			EaccAccountamtlist eaccAccountamtlist = eaccAccountamtlistMapper.selectByPrimaryKey(unfrozen.getId());
			if(eaccAccountamtlist == null){
				BaseResponse baseResponse = new BaseResponse();
				baseResponse.setRecode(BusinessMsg.FAIL);
				baseResponse.setRemsg("交易流水不能为空");
				throw new BusinessException(baseResponse);
			}

			if(StringUtils.isBlank(eaccAccountamtlist.getSwitch_state())){

				//查询科目信息
				AccountSubjectInfo accountSubjectInfo = custAccountSubjectInfoMapper.selectByUnionKey(unfrozen);
				//解冻
				noConversion(accountSubjectInfo,eaccAccountamtlist);

			}

			//未转换
			else if(StringUtils.isNotBlank(eaccAccountamtlist.getSwitch_state()) && eaccAccountamtlist.getSwitch_state().equals(SwitchState.NOSWITCH.getCode())){

				//查询科目信息
				AccountSubjectInfo accountSubjectInfo = custAccountSubjectInfoMapper.selectByUnionKey(unfrozen);
				//解冻
				noConversion(accountSubjectInfo,eaccAccountamtlist);

			}

			//全部转换
			else if(StringUtils.isNotBlank(eaccAccountamtlist.getSwitch_state()) && eaccAccountamtlist.getSwitch_state().equals(SwitchState.ALLSWITCH.getCode())){

				//查询科目信息
				unfrozen.setSubject(Tsubject.CASH.getCode());
				AccountSubjectInfo accountSubjectInfo = custAccountSubjectInfoMapper.selectByUnionKey(unfrozen);
				//解冻
				allConversion(accountSubjectInfo,eaccAccountamtlist);

			}

			//部分转换
			else if(StringUtils.isNotBlank(eaccAccountamtlist.getSwitch_state()) && eaccAccountamtlist.getSwitch_state().equals(SwitchState.PARTSWITCH.getCode())){

				//查询科目信息
				AccountSubjectInfo accountSubjectInfo1 = custAccountSubjectInfoMapper.selectByUnionKey(unfrozen);
				//未转换金额
				eaccAccountamtlist.setAmt(eaccAccountamtlist.getAmt().subtract(eaccAccountamtlist.getSwitch_amt()));
				//解冻
				noConversion(accountSubjectInfo1,eaccAccountamtlist);

				//查询科目信息
				unfrozen.setSubject(Tsubject.CASH.getCode());
				//查询账户
				AccountSubjectInfo accountSubjectInfo2 = custAccountSubjectInfoMapper.selectByUnionKey(unfrozen);
				//设置已转换金额
				eaccAccountamtlist.setAmt(eaccAccountamtlist.getSwitch_amt());
				//解冻
				allConversion(accountSubjectInfo2,eaccAccountamtlist);


			}

			unfrozen.setAmt_type(AMTTYPE.UnFrozen.getCode());
			unfrozen.setTrans_name(TransConsts.FUND_UNFROZEN_NAME);
			unfrozen.setTrans_code(TransConsts.FUND_UNFROZEN_CODE);
			unfrozen.setId(null);
			accountTransferService.addTransFlow(unfrozen);

		} catch (Exception e) {
			BaseResponse baseResponse=new BaseResponse();
			if(e instanceof BusinessException){
				baseResponse=((BusinessException) e).getBaseResponse();
			}else{
				logger.error(e);
				baseResponse.setRecode(BusinessMsg.RUNTIME_EXCEPTION);
				baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.RUNTIME_EXCEPTION));
			}
			throw new BusinessException(baseResponse);
		}
		return true;
	}

	//全部转换
	public boolean allConversion(AccountSubjectInfo accountSubjectInfo,EaccAccountamtlist eaccAccountamtlist) throws BusinessException{
		try{
			//检查可用余额
			if(accountSubjectInfo.getF_balance().compareTo(eaccAccountamtlist.getAmt())<0){

				BaseResponse baseResponse = new BaseResponse();
				baseResponse.setRecode(BusinessMsg.FAIL);
				baseResponse.setRemsg(String.format("账户可解冻余额不足，可用金额【%s】， 转账金额【%s】", accountSubjectInfo.getF_balance(),eaccAccountamtlist.getAmt()));
				throw new BusinessException(baseResponse);
			}
            eaccAccountamtlist.setId(accountSubjectInfo.getId());
			if(null == eaccAccountamtlist.getUpdate_by()) eaccAccountamtlist.setUpdate_by(SeqUtil.RANDOM_KEY);
			eaccAccountamtlist.setUpdate_time(new Date());
			custAccountSubjectInfoMapper.unfreezeAmt(eaccAccountamtlist);//传入的id为账户id
		} catch (Exception e) {
			BaseResponse baseResponse=new BaseResponse();
			if(e instanceof BusinessException){
				baseResponse=((BusinessException) e).getBaseResponse();
			}else{
				logger.error(e);
				baseResponse.setRecode(BusinessMsg.RUNTIME_EXCEPTION);
				baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.RUNTIME_EXCEPTION));
			}
			throw new BusinessException(baseResponse);
		}
		return true;
	}

	//未转换
	public boolean noConversion(AccountSubjectInfo accountSubjectInfo,EaccAccountamtlist eaccAccountamtlist) throws BusinessException{
		try{
			//检查可用余额
			if(accountSubjectInfo.getF_balance().compareTo(eaccAccountamtlist.getAmt())<0){

				BaseResponse baseResponse = new BaseResponse();
				baseResponse.setRecode(BusinessMsg.FAIL);
				baseResponse.setRemsg(String.format("账户可解冻余额不足，可用金额【%s】，转账金额【%s】", accountSubjectInfo.getF_balance(),eaccAccountamtlist.getAmt()));
				throw new BusinessException(baseResponse);
			}
            eaccAccountamtlist.setId(accountSubjectInfo.getId());//传入的id为账户id
			if(null == eaccAccountamtlist.getUpdate_by()) eaccAccountamtlist.setUpdate_by(SeqUtil.RANDOM_KEY);
			eaccAccountamtlist.setUpdate_time(new Date());
			custAccountSubjectInfoMapper.unfreezeAmt(eaccAccountamtlist);
		} catch (Exception e) {
			BaseResponse baseResponse=new BaseResponse();
			if(e instanceof BusinessException){
				baseResponse=((BusinessException) e).getBaseResponse();
			}else{
				logger.error(e);
				baseResponse.setRecode(BusinessMsg.RUNTIME_EXCEPTION);
				baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.RUNTIME_EXCEPTION));
			}
			throw new BusinessException(baseResponse);
		}
		return true;
	}
}
