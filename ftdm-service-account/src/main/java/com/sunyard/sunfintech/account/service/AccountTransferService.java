package com.sunyard.sunfintech.account.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.sunyard.sunfintech.account.enums.AMTTYPE;
import com.sunyard.sunfintech.account.model.bo.*;
import com.sunyard.sunfintech.account.provider.*;
import com.sunyard.sunfintech.core.Constants;
import com.sunyard.sunfintech.core.TransConsts;
import com.sunyard.sunfintech.core.base.BaseRequest;
import com.sunyard.sunfintech.core.base.BaseResponse;
import com.sunyard.sunfintech.core.base.BaseServiceSimple;
import com.sunyard.sunfintech.core.config.GlobalConfig;
import com.sunyard.sunfintech.core.dic.*;
import com.sunyard.sunfintech.core.exception.BusinessException;
import com.sunyard.sunfintech.core.exception.BusinessMsg;
import com.sunyard.sunfintech.core.util.CacheUtil;
import com.sunyard.sunfintech.core.util.DateUtils;
import com.sunyard.sunfintech.core.util.SeqUtil;
import com.sunyard.sunfintech.core.util.StringUtils;
import com.sunyard.sunfintech.custdao.mapper.CustAccountSubjectInfoMapper;
import com.sunyard.sunfintech.custdao.mapper.CustTransTransreqMapper;
import com.sunyard.sunfintech.dao.entity.*;
import com.sunyard.sunfintech.dao.mapper.*;
import com.sunyard.sunfintech.pub.provider.IEaccTransTransreqService;
import com.sunyard.sunfintech.pub.provider.ITransReqService;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.transaction.annotation.Transactional;

import javax.security.auth.Subject;
import java.math.BigDecimal;
import java.util.*;

/**
 * @author heroy
 * @version 2017/4/12
 */
@Service(interfaceClass = IAccountTransferService.class)
@CacheConfig(cacheNames="accountTransferService")
@org.springframework.stereotype.Service("accountTransferService")
public class AccountTransferService extends BaseServiceSimple implements IAccountTransferService {

	@Autowired
	private EaccAccountamtlistMapper eaccAccountamtlistMapper;

	@Autowired
	private IAccountAssetService accountAssetService;

	@Autowired
	private IAccountQueryService accountQueryService;

	@Autowired
	private IAccountTransferThirdParty accountTransferThirdParty;

	@Autowired
	private IEaccTransTransreqService eaccTransTransreqService;

	@Autowired
	private PlatPlatinfoMapper platPlatinfoMapper;

	@Autowired
	private AccountSubjectInfoMapper accountSubjectInfoMapper;

	@Autowired
	private ITransReqService transReqService;

	@Autowired
	private CustAccountSubjectInfoMapper custAccountSubjectInfoMapper;

	@Override
	@Transactional
	public List<EaccTransTransreqWithBLOBs> doTransfer(EaccAccountamtlist expenseAccount) throws BusinessException {
		List<EaccTransTransreqWithBLOBs> eaccTransTransreqList=new ArrayList<>();
		try {
			logger.info("转账参数："+expenseAccount.toString());
			if(StringUtils.isBlank(expenseAccount.getPlatcust()) || StringUtils.isBlank(expenseAccount.getSubject())
					|| StringUtils.isBlank(expenseAccount.getSub_subject()) || StringUtils.isBlank(expenseAccount.getOppo_platcust())
					|| StringUtils.isBlank(expenseAccount.getOppo_subject()) || StringUtils.isBlank(expenseAccount.getOppo_sub_subject())
					|| expenseAccount.getAmt()==null || StringUtils.isBlank(expenseAccount.getPlat_no())
					|| StringUtils.isBlank(expenseAccount.getTrans_serial()) || StringUtils.isBlank(expenseAccount.getTrans_code())
					|| StringUtils.isBlank(expenseAccount.getTrans_name()) || StringUtils.isBlank(expenseAccount.getTrans_date())
					|| StringUtils.isBlank(expenseAccount.getTrans_time())){
				logger.info("转账参数错误，请程序员检查代码！！！");
				throw new BusinessException(BusinessMsg.PARAMETER_ERROR,"转账失败");
			}

			if(!TransConsts.REFUND_CODE.equals(expenseAccount.getTrans_code()) && !TransConsts.PRODREPAY_CODE.equals(expenseAccount.getTrans_code())){
				//平台现金转有电子账户的个人现金投资，优先电子账户
				if(Tsubject.CASH.getCode().equals(expenseAccount.getSubject())){
					if(!Ssubject.INVEST.getCode().equals(expenseAccount.getSub_subject()) &&
							!Ssubject.FINANCING.getCode().equals(expenseAccount.getSub_subject()) &&
							!Ssubject.EACCOUNT.getCode().equals(expenseAccount.getSub_subject()) &&
							Ssubject.INVEST.getCode().equals(expenseAccount.getOppo_sub_subject())){
						AccountSubjectInfo checkEaccount=accountQueryService.queryAccount(null,
								expenseAccount.getOppo_platcust(),Tsubject.CASH.getCode(),Ssubject.EACCOUNT.getCode());
						if(checkEaccount!=null){
							expenseAccount.setOppo_sub_subject(Ssubject.EACCOUNT.getCode());
						}
					}
				}
			}
			//扣款流水将对手账户反向即为加款流水
			EaccAccountamtlist incomeAccount = new EaccAccountamtlist();
			BeanUtils.copyProperties(incomeAccount, expenseAccount);
			incomeAccount.setPlatcust(expenseAccount.getOppo_platcust());
			incomeAccount.setOppo_platcust(expenseAccount.getPlatcust());
			expenseAccount.setAmt_type(AMTTYPE.Expense.getCode());
			incomeAccount.setAmt_type(AMTTYPE.Income.getCode());
//			incomeAccount.setSub_subject(expenseAccount.getOppo_sub_subject());
//			incomeAccount.setSubject(expenseAccount.getOppo_subject());
//			incomeAccount.setOppo_subject(expenseAccount.getSubject());
//			incomeAccount.setOppo_sub_subject(expenseAccount.getSub_subject());

			//定义转出现金账户
			AccountSubjectInfo cashExpenseAccountSubjectInfo=null;
			//定义转出在途账户
			AccountSubjectInfo floatExpenseAccountSubjectInfo=null;
			//定义转出现金电子账户
			AccountSubjectInfo eaccountCashExpenseAccountSubject=null;
			//定义转出在途电子账户
			AccountSubjectInfo eaccountFloatExpenseAccountSubject=null;
			//定义转入现金账户
			AccountSubjectInfo cashIncomeAccountSubjectInfo=null;
			//定义转入在途账户
			AccountSubjectInfo floatIncomeAccountSubjectInfo=null;
			//定义转入现金电子账户
			AccountSubjectInfo eaccountCashIncomeAccountSubject=null;
			//定义转入在途电子账户
			AccountSubjectInfo eaccountFloatIncomeAccountSubject=null;
			//定义转出平台号
			String expensePlatNo=null;
			//定义转入平台号
			String incomePlatNo=null;

			//定义转出账户是个人账户还是平台账户
			boolean expenseIsPerson=false;

			//定义集团编号
			String mall_no=null;

			//查询各类账户
			List<AccountSubjectInfo> expenseAccountSubjectInfoList=null;
			if(expenseAccount.getSub_subject().equals(Ssubject.EACCOUNT.getCode())){
				expenseAccountSubjectInfoList=accountQueryService.queryPlatAccountList(
						null,expenseAccount.getPlatcust(),null,expenseAccount.getSub_subject());
			}else{
				expenseAccountSubjectInfoList=accountQueryService.queryPlatAccountList(
						expenseAccount.getPlat_no(),expenseAccount.getPlatcust(),null,expenseAccount.getSub_subject());
			}
			if(expenseAccountSubjectInfoList.size()<=0){
				logger.info("【转账】=========出账账户不存在，platcust："+expenseAccount.getPlatcust());
				throw new BusinessException(BusinessMsg.INVALID_ACCOUNT,"：扣款账户不存在");
			}

			//查询出账现金和在途账户
			for(AccountSubjectInfo account:expenseAccountSubjectInfoList){
				if(!StringUtils.isBlank(account.getSubject()) && Tsubject.CASH.getCode().equals(account.getSubject())){
					//设置现金账户
					cashExpenseAccountSubjectInfo=account;
				}else if(!StringUtils.isBlank(account.getSubject()) && Tsubject.FLOAT.getCode().equals(account.getSubject())){
					//设置在途账户
					floatExpenseAccountSubjectInfo=account;
				}
				if(Ssubject.EACCOUNT.getCode().equals(account.getSub_subject())){
					mall_no=account.getPlat_no();
				}
			}

			List<AccountSubjectInfo> incomeAccountSubjectInfoList=null;
			if(expenseAccount.getOppo_sub_subject().equals(Ssubject.EACCOUNT.getCode())){
				incomeAccountSubjectInfoList=accountQueryService.queryPlatAccountList(
						null,expenseAccount.getOppo_platcust(),null,expenseAccount.getOppo_sub_subject());
			}else{
				incomeAccountSubjectInfoList=accountQueryService.queryPlatAccountList(
						expenseAccount.getPlat_no(),expenseAccount.getOppo_platcust(),null,expenseAccount.getOppo_sub_subject());
			}
			if(incomeAccountSubjectInfoList.size()<=0){
				logger.info("【转账】=========入账账户不存在，platcust："+expenseAccount.getOppo_platcust());
				throw new BusinessException(BusinessMsg.INVALID_ACCOUNT,"加款账户不存在");
			}

			//如果转出子科目为01或02，则为个人账户
			if(expenseAccount.getSub_subject().equals(Ssubject.INVEST.getCode()) ||
					expenseAccount.getSub_subject().equals(Ssubject.FINANCING.getCode())){
				expenseIsPerson=true;
			}

			//查询入账现金和在途账户
			for(AccountSubjectInfo account:incomeAccountSubjectInfoList){
				if(!StringUtils.isBlank(account.getSubject()) && Tsubject.CASH.getCode().equals(account.getSubject())){
					//设置现金账户
					cashIncomeAccountSubjectInfo=account;
				}else if(!StringUtils.isBlank(account.getSubject()) && Tsubject.FLOAT.getCode().equals(account.getSubject())){
					//设置在途账户
					floatIncomeAccountSubjectInfo=account;
				}
				if(Ssubject.EACCOUNT.getCode().equals(account.getSub_subject())){
					mall_no=account.getPlat_no();
				}
			}

			//为了防止查询出一些莫名其妙的账户，却查不出现金和在途账户，判断一下现金和在途账户是否为空
			if(cashExpenseAccountSubjectInfo==null && floatExpenseAccountSubjectInfo==null){
				logger.info("【转账】=========数据库有一些乱七八糟的账户，找不到现金或在途账户，platcust："+
						expenseAccount.getPlatcust()+"，sub_subject："+expenseAccount.getSub_subject());
				throw new BusinessException(BusinessMsg.INVALID_ACCOUNT,"扣款账户不存在，platcust："+expenseAccount.getPlatcust());
			}

			//判断转账优先级
			BigDecimal transAmt=expenseAccount.getAmt();
			BigDecimal cashAmt=BigDecimal.ZERO;
			BigDecimal floatAmt=BigDecimal.ZERO;
			BigDecimal eaccCashAmt=BigDecimal.ZERO;
			BigDecimal eaccFloatAmt=BigDecimal.ZERO;

			if(Tsubject.CASH.getCode().equals(expenseAccount.getSubject())){
				//现金优先，先判断现金账户余额是否充足
				if(cashExpenseAccountSubjectInfo==null || cashExpenseAccountSubjectInfo.getN_balance()==null){
					logger.info("【转账】=========账户异常，现金优先，找不到现金账户或现金账户可用余额为null");
					throw new BusinessException(BusinessMsg.INVALID_ACCOUNT,"扣款人账户异常");
				}
				if(transAmt.compareTo(cashExpenseAccountSubjectInfo.getN_balance())>0){
					logger.info("【转账】========虚拟现金余额不足，出账platcust："+expenseAccount.getPlatcust());
					//如果转账金额大于现金可用余额，设置现金转账金额
					cashAmt=cashExpenseAccountSubjectInfo.getN_balance();
					//判断在途账户可用余额是否充足
					if(floatExpenseAccountSubjectInfo!=null && floatExpenseAccountSubjectInfo.getN_balance()!=null){
						BigDecimal saleAmt=BigDecimal.ZERO;
						if(cashAmt.compareTo(BigDecimal.ZERO)>0){
							saleAmt=transAmt.subtract(cashAmt);
						}else{
							saleAmt=transAmt;
						}
						if(saleAmt.compareTo(floatExpenseAccountSubjectInfo.getN_balance())>0){
							logger.info("【转账】========虚拟在途余额不足，出账platcust："+expenseAccount.getPlatcust());
							//如果在途账户余额不足，设置在途转账金额
							floatAmt=floatExpenseAccountSubjectInfo.getN_balance();
							//查询是否存在电子账户
							if(expenseIsPerson && expenseAccount.getSub_subject().equals(Ssubject.INVEST.getCode())){
								//如果子科目是投资，必定是用户账户，需查询电子账户
								eaccountCashExpenseAccountSubject=accountQueryService.queryFINANCINGPlatAccount(
										null,expenseAccount.getPlatcust(), AccountType.ElectronicAccount.getCode(),
										Tsubject.CASH.getCode(),Ssubject.EACCOUNT.getCode());
								eaccountCashIncomeAccountSubject=accountQueryService.queryFINANCINGPlatAccount(
										null,expenseAccount.getOppo_platcust(),AccountType.ElectronicAccount.getCode(),
										Tsubject.CASH.getCode(),Ssubject.EACCOUNT.getCode());
								if(eaccountCashExpenseAccountSubject!=null && eaccountCashExpenseAccountSubject.getN_balance()!=null){
									mall_no=eaccountCashExpenseAccountSubject.getPlat_no();
									saleAmt=saleAmt.subtract(floatAmt);
									//判断余额是否充足,不充足就抛异常
									if(saleAmt.compareTo(eaccountCashExpenseAccountSubject.getN_balance())>0){
									    eaccCashAmt=eaccountCashExpenseAccountSubject.getN_balance();
										logger.info("【转账】========电子现金余额不足，出账platcust："+expenseAccount.getPlatcust());
										//判断电子账户在途是否充足
										eaccountFloatExpenseAccountSubject=accountQueryService.queryFINANCINGPlatAccount(
												null,expenseAccount.getPlatcust(), AccountType.ElectronicAccount.getCode(),
												Tsubject.FLOAT.getCode(),Ssubject.EACCOUNT.getCode());
										eaccountFloatIncomeAccountSubject=accountQueryService.queryFINANCINGPlatAccount(
												null,expenseAccount.getOppo_platcust(),AccountType.ElectronicAccount.getCode(),
												Tsubject.FLOAT.getCode(),Ssubject.EACCOUNT.getCode());
										if(eaccountFloatExpenseAccountSubject!=null && eaccountFloatExpenseAccountSubject.getN_balance()!=null){
										    saleAmt=saleAmt.subtract(eaccCashAmt);
											if(saleAmt.compareTo(eaccountFloatExpenseAccountSubject.getN_balance())>0){
												logger.info("【转账】========电子在途余额不足，出账platcust："+expenseAccount.getPlatcust());
												throw new BusinessException(BusinessMsg.BALANCE_LACK,
														"出账账户余额不足，出账platcust："+expenseAccount.getPlatcust());
											}
											eaccFloatAmt=saleAmt;
										}else{
											logger.info("【转账】========余额不足，出账platcust："+expenseAccount.getPlatcust());
											throw new BusinessException(BusinessMsg.BALANCE_LACK,
													"出账账户余额不足，出账platcust："+expenseAccount.getPlatcust());
										}
									}else{
										eaccCashAmt=saleAmt;
									}
								}else{
									logger.info("【转账】========余额不足，出账platcust："+expenseAccount.getPlatcust());
									throw new BusinessException(BusinessMsg.BALANCE_LACK,
											"出账账户余额不足，出账platcust："+expenseAccount.getPlatcust());
								}
							}else{
								logger.info("【转账】========余额不足，出账platcust："+expenseAccount.getPlatcust());
								throw new BusinessException(BusinessMsg.BALANCE_LACK,
										"出账账户余额不足，出账platcust："+expenseAccount.getPlatcust());
							}
						}else{
							floatAmt=saleAmt;
						}
					}else{
						logger.info("【转账】========余额不足，出账platcust："+expenseAccount.getPlatcust());
						throw new BusinessException(BusinessMsg.BALANCE_LACK,
								"出账账户余额不足，出账platcust："+expenseAccount.getPlatcust());
					}
				}else{
					cashAmt=transAmt;
				}
			}else{
				//在途优先，先判断在途账户余额是否充足
				if(floatExpenseAccountSubjectInfo==null || floatExpenseAccountSubjectInfo.getN_balance()==null){
					logger.info("【转账】=========账户异常，现金优先，找不到现金账户或现金账户可用余额为null");
					throw new BusinessException(BusinessMsg.INVALID_ACCOUNT,"扣款人账户异常");
				}
				if(transAmt.compareTo(floatExpenseAccountSubjectInfo.getN_balance())>0){
					logger.info("【转账】========虚拟在途余额不足，出账platcust："+expenseAccount.getPlatcust());
					//如果转账金额大于在途可用余额，设置在途转账金额
					floatAmt=floatExpenseAccountSubjectInfo.getN_balance();
					//判断现金账户可用余额是否充足
					if(cashExpenseAccountSubjectInfo!=null && cashExpenseAccountSubjectInfo.getN_balance()!=null){
						BigDecimal saleAmt=BigDecimal.ZERO;
						if(floatAmt.compareTo(BigDecimal.ZERO)>0){
							saleAmt=transAmt.subtract(floatAmt);
						}else{
							saleAmt=transAmt;
						}
						if(saleAmt.compareTo(cashExpenseAccountSubjectInfo.getN_balance())>0){
							logger.info("【转账】========虚拟现金余额不足，出账platcust："+expenseAccount.getPlatcust());
							//如果现金账户余额不足，设置现金转账金额
							cashAmt=cashExpenseAccountSubjectInfo.getN_balance();
							//查询是否存在电子账户
							if(expenseIsPerson && expenseAccount.getSub_subject().equals(Ssubject.INVEST.getCode())){
								//如果子科目是投资，必定是用户账户，需查询电子账户
								eaccountFloatExpenseAccountSubject=accountQueryService.queryFINANCINGPlatAccount(
										null,expenseAccount.getPlatcust(), AccountType.ElectronicAccount.getCode(),
										Tsubject.FLOAT.getCode(),Ssubject.EACCOUNT.getCode());
								eaccountFloatIncomeAccountSubject=accountQueryService.queryFINANCINGPlatAccount(
										null,expenseAccount.getOppo_platcust(),AccountType.ElectronicAccount.getCode(),
										Tsubject.FLOAT.getCode(),Ssubject.EACCOUNT.getCode());
								if(eaccountFloatExpenseAccountSubject!=null && eaccountFloatExpenseAccountSubject.getN_balance()!=null){
									mall_no=eaccountFloatExpenseAccountSubject.getPlat_no();
									saleAmt=saleAmt.subtract(cashAmt);
									//判断余额是否充足,不充足就抛异常
									if(saleAmt.compareTo(eaccountFloatExpenseAccountSubject.getN_balance())>0){
									    eaccFloatAmt=eaccountFloatExpenseAccountSubject.getN_balance();
										logger.info("【转账】========电子在途余额不足，出账platcust："+expenseAccount.getPlatcust());
										//查询电子现金
										eaccountCashExpenseAccountSubject=accountQueryService.queryFINANCINGPlatAccount(
												null,expenseAccount.getPlatcust(), AccountType.ElectronicAccount.getCode(),
												Tsubject.CASH.getCode(),Ssubject.EACCOUNT.getCode());
										eaccountCashIncomeAccountSubject=accountQueryService.queryFINANCINGPlatAccount(
												null,expenseAccount.getOppo_platcust(),AccountType.ElectronicAccount.getCode(),
												Tsubject.CASH.getCode(),Ssubject.EACCOUNT.getCode());
										if(eaccountCashExpenseAccountSubject!=null && eaccountCashExpenseAccountSubject.getN_balance()!=null){
										    saleAmt=saleAmt.subtract(eaccFloatAmt);
											if(saleAmt.compareTo(eaccountCashExpenseAccountSubject.getN_balance())>0){
												logger.info("【转账】========电子现金余额不足，出账platcust："+expenseAccount.getPlatcust());
												throw new BusinessException(BusinessMsg.BALANCE_LACK,
														"出账账户余额不足，出账platcust："+expenseAccount.getPlatcust());
											}
											eaccCashAmt=saleAmt;
										}else{
											logger.info("【转账】========余额不足，出账platcust："+expenseAccount.getPlatcust());
											throw new BusinessException(BusinessMsg.BALANCE_LACK,
													"出账账户余额不足，出账platcust："+expenseAccount.getPlatcust());
										}
									}else{
										eaccFloatAmt=saleAmt;
									}
								}else{
									logger.info("【转账】========余额不足，出账platcust："+expenseAccount.getPlatcust());
									throw new BusinessException(BusinessMsg.BALANCE_LACK,
											"出账账户余额不足，出账platcust："+expenseAccount.getPlatcust());
								}
							}else{
								logger.info("【转账】========余额不足，出账platcust："+expenseAccount.getPlatcust());
								throw new BusinessException(BusinessMsg.BALANCE_LACK,
										"出账账户余额不足，出账platcust："+expenseAccount.getPlatcust());
							}
						}else{
							cashAmt=saleAmt;
						}
					}else{
						logger.info("【转账】========余额不足，出账platcust："+expenseAccount.getPlatcust());
						throw new BusinessException(BusinessMsg.BALANCE_LACK,
								"出账账户余额不足，出账platcust："+expenseAccount.getPlatcust());
					}
				}else{
					floatAmt=transAmt;
				}
			}

			//程序走到这说明账户可用余额充足，但是不知道要从哪些账户出金，需要判断几个出金账户
			if(cashAmt.compareTo(BigDecimal.ZERO)>0 && cashExpenseAccountSubjectInfo!=null){
				//现金账户转账,设置转出账户和转入账户
				if(cashIncomeAccountSubjectInfo!=null){
					//如果入账账户是用户账户
					expenseAccount=setSubjectAndAmt(expenseAccount,cashExpenseAccountSubjectInfo.getSubject(),
							cashExpenseAccountSubjectInfo.getSub_subject(),
							cashIncomeAccountSubjectInfo.getSubject(),
							cashIncomeAccountSubjectInfo.getSub_subject(),cashAmt);
				}else{
					logger.info("【转账】========找不到入账现金账户，入账platcust："+expenseAccount.getOppo_platcust());
					throw new BusinessException(BusinessMsg.INVALID_ACCOUNT, BusinessMsg.getMsg(BusinessMsg.INVALID_ACCOUNT)+"：入账账户异常");
				}
				incomeAccount=setSubjectAndAmt(incomeAccount,expenseAccount.getOppo_subject(),
						expenseAccount.getOppo_sub_subject(),expenseAccount.getSubject(),
						expenseAccount.getSub_subject(),expenseAccount.getAmt());
				expenseAccount.setPlat_no(cashExpenseAccountSubjectInfo.getPlat_no());
				expenseAccount.setOppo_plat_no(cashIncomeAccountSubjectInfo.getPlat_no());
				incomeAccount.setOppo_plat_no(cashExpenseAccountSubjectInfo.getPlat_no());
				incomeAccount.setPlat_no(cashIncomeAccountSubjectInfo.getPlat_no());
				//扣款
				accountAssetService.withdraw(expenseAccount);
				//加款
				accountAssetService.charge(incomeAccount);
				if(Ssubject.EACCOUNT.getCode().equals(expenseAccount.getSub_subject())
						|| Ssubject.EACCOUNT.getCode().equals(expenseAccount.getOppo_sub_subject())){
					//TODO 电子账户转账
					eaccTransTransreqList.add(eaccountTrans(expenseAccount,mall_no));
				}
			}
			if(floatAmt.compareTo(BigDecimal.ZERO)>0 && floatExpenseAccountSubjectInfo!=null){
				//在途账户转账,设置转出账户和转入账户
				//如果入账账户是平台账户，如果有在途账户
				if(floatIncomeAccountSubjectInfo!=null){
					//如果是平台标的账户或垫付账户
					expenseAccount=setSubjectAndAmt(expenseAccount,floatExpenseAccountSubjectInfo.getSubject(),
							floatExpenseAccountSubjectInfo.getSub_subject(),
							floatIncomeAccountSubjectInfo.getSubject(),
							floatIncomeAccountSubjectInfo.getSub_subject(),floatAmt);
					expenseAccount.setOppo_plat_no(floatIncomeAccountSubjectInfo.getPlat_no());
					incomeAccount.setPlat_no(floatIncomeAccountSubjectInfo.getPlat_no());
				}else{
					expenseAccount=setSubjectAndAmt(expenseAccount,floatExpenseAccountSubjectInfo.getSubject(),
							cashExpenseAccountSubjectInfo.getSub_subject(),
							cashIncomeAccountSubjectInfo.getSubject(),
							cashIncomeAccountSubjectInfo.getSub_subject(),floatAmt);
					expenseAccount.setOppo_plat_no(cashIncomeAccountSubjectInfo.getPlat_no());
					incomeAccount.setPlat_no(cashIncomeAccountSubjectInfo.getPlat_no());
				}
				incomeAccount=setSubjectAndAmt(incomeAccount,expenseAccount.getOppo_subject(),
						expenseAccount.getOppo_sub_subject(),expenseAccount.getSubject(),
						expenseAccount.getSub_subject(),expenseAccount.getAmt());
				expenseAccount.setPlat_no(floatExpenseAccountSubjectInfo.getPlat_no());
				incomeAccount.setOppo_plat_no(floatExpenseAccountSubjectInfo.getPlat_no());
				//扣款
				accountAssetService.withdraw(expenseAccount);
				//加款
				accountAssetService.charge(incomeAccount);
//				if(Ssubject.EACCOUNT.getCode().equals(expenseAccount.getSub_subject())
//						|| Ssubject.EACCOUNT.getCode().equals(expenseAccount.getOppo_sub_subject())){
//					//TODO 电子账户转账
//					eaccountTrans(expenseAccount,mall_no);
//				}
			}
			//电子账户现金
			if(eaccCashAmt.compareTo(BigDecimal.ZERO)>0 && eaccountCashExpenseAccountSubject!=null){
				if(eaccountCashIncomeAccountSubject!=null){
					expenseAccount=setSubjectAndAmt(expenseAccount,eaccountCashExpenseAccountSubject.getSubject(),
							eaccountCashExpenseAccountSubject.getSub_subject(),
							eaccountCashIncomeAccountSubject.getSubject(),
							eaccountCashIncomeAccountSubject.getSub_subject(),eaccCashAmt);
					incomeAccount=setSubjectAndAmt(incomeAccount,expenseAccount.getOppo_subject(),
							expenseAccount.getOppo_sub_subject(),expenseAccount.getSubject(),
							expenseAccount.getSub_subject(),expenseAccount.getAmt());
					expenseAccount.setOppo_plat_no(eaccountCashIncomeAccountSubject.getPlat_no());
					incomeAccount.setPlat_no(eaccountCashIncomeAccountSubject.getPlat_no());
				}else{
					if(cashIncomeAccountSubjectInfo!=null){
						expenseAccount=setSubjectAndAmt(expenseAccount,eaccountCashExpenseAccountSubject.getSubject(),
								eaccountCashExpenseAccountSubject.getSub_subject(),
								cashIncomeAccountSubjectInfo.getSubject(),
								cashIncomeAccountSubjectInfo.getSub_subject(),eaccCashAmt);
						expenseAccount.setOppo_plat_no(cashIncomeAccountSubjectInfo.getPlat_no());
						incomeAccount.setPlat_no(cashIncomeAccountSubjectInfo.getPlat_no());
					}else if(floatIncomeAccountSubjectInfo!=null) {
						expenseAccount = setSubjectAndAmt(expenseAccount, eaccountCashExpenseAccountSubject.getSubject(),
								eaccountCashExpenseAccountSubject.getSub_subject(),
								floatIncomeAccountSubjectInfo.getSubject(),
								floatIncomeAccountSubjectInfo.getSub_subject(), eaccCashAmt);
						expenseAccount.setOppo_plat_no(floatIncomeAccountSubjectInfo.getPlat_no());
						incomeAccount.setPlat_no(floatIncomeAccountSubjectInfo.getPlat_no());
					}
					incomeAccount=setSubjectAndAmt(incomeAccount,expenseAccount.getOppo_subject(),
							expenseAccount.getOppo_sub_subject(),expenseAccount.getSubject(),
							expenseAccount.getSub_subject(),expenseAccount.getAmt());
				}
				expenseAccount.setPlat_no(eaccountCashExpenseAccountSubject.getPlat_no());
				incomeAccount.setOppo_plat_no(eaccountCashExpenseAccountSubject.getPlat_no());
				//扣款
				accountAssetService.withdraw(expenseAccount);
				//加款
				accountAssetService.charge(incomeAccount);
				if(Tsubject.CASH.getCode().equals(expenseAccount.getOppo_subject())){
					//TODO 电子账户转账
					eaccTransTransreqList.add(eaccountTrans(expenseAccount,eaccountCashExpenseAccountSubject.getPlat_no()));
				}


			}
			//电子账户在途
			if(eaccFloatAmt.compareTo(BigDecimal.ZERO)>0 && eaccountFloatExpenseAccountSubject!=null){
				if(eaccountFloatIncomeAccountSubject!=null){
					expenseAccount=setSubjectAndAmt(expenseAccount,eaccountFloatExpenseAccountSubject.getSubject(),
							eaccountFloatExpenseAccountSubject.getSub_subject(),
							eaccountFloatIncomeAccountSubject.getSubject(),
							eaccountFloatIncomeAccountSubject.getSub_subject(),eaccFloatAmt);
					incomeAccount=setSubjectAndAmt(incomeAccount,expenseAccount.getOppo_subject(),
							expenseAccount.getOppo_sub_subject(),expenseAccount.getSubject(),
							expenseAccount.getSub_subject(),expenseAccount.getAmt());
					expenseAccount.setOppo_plat_no(eaccountFloatIncomeAccountSubject.getPlat_no());
					incomeAccount.setPlat_no(eaccountFloatIncomeAccountSubject.getPlat_no());
				}else{
					if(floatIncomeAccountSubjectInfo!=null){
						expenseAccount=setSubjectAndAmt(expenseAccount,eaccountFloatExpenseAccountSubject.getSubject(),
								eaccountFloatExpenseAccountSubject.getSub_subject(),
								floatIncomeAccountSubjectInfo.getSubject(),
								floatIncomeAccountSubjectInfo.getSub_subject(),eaccFloatAmt);
						expenseAccount.setOppo_plat_no(floatIncomeAccountSubjectInfo.getPlat_no());
						incomeAccount.setPlat_no(floatIncomeAccountSubjectInfo.getPlat_no());
					}else if(cashIncomeAccountSubjectInfo!=null){
						expenseAccount=setSubjectAndAmt(expenseAccount,eaccountFloatExpenseAccountSubject.getSubject(),
								eaccountFloatExpenseAccountSubject.getSub_subject(),
								cashIncomeAccountSubjectInfo.getSubject(),
								cashIncomeAccountSubjectInfo.getSub_subject(),eaccFloatAmt);
						expenseAccount.setOppo_plat_no(cashIncomeAccountSubjectInfo.getPlat_no());
						incomeAccount.setPlat_no(cashIncomeAccountSubjectInfo.getPlat_no());
					}
					incomeAccount=setSubjectAndAmt(incomeAccount,expenseAccount.getOppo_subject(),
							expenseAccount.getOppo_sub_subject(),expenseAccount.getSubject(),
							expenseAccount.getSub_subject(),expenseAccount.getAmt());
				}
				expenseAccount.setPlat_no(eaccountFloatExpenseAccountSubject.getPlat_no());
				incomeAccount.setOppo_plat_no(eaccountFloatExpenseAccountSubject.getPlat_no());
				//扣款
				accountAssetService.withdraw(expenseAccount);
				//加款
				accountAssetService.charge(incomeAccount);
				//TODO 电子账户转账
//				eaccountTrans(expenseAccount,eaccountFloatExpenseAccountSubject.getPlat_no());
			}
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
		return eaccTransTransreqList;
	}

	@Override
	@Transactional
	public Boolean transfer(EaccAccountamtlist eaccAccountamtlist) throws BusinessException {
		TransTransreq transTransreq=null;
		if(!"异步".equals(eaccAccountamtlist.getExt5())){
			BaseRequest baseRequest=new BaseRequest();
			baseRequest.setOrder_no(eaccAccountamtlist.getOrder_no());
			baseRequest.setPartner_trans_date(eaccAccountamtlist.getTrans_date());
			baseRequest.setPartner_trans_time(eaccAccountamtlist.getTrans_time());
			baseRequest.setMer_no(eaccAccountamtlist.getPlat_no());
			transTransreq=transReqService.getTransTransReq(baseRequest,
					eaccAccountamtlist.getTrans_code(),eaccAccountamtlist.getTrans_name(),false);
			transTransreq.setOrder_no(eaccAccountamtlist.getOrder_no());
			transTransreq.setTrans_serial(eaccAccountamtlist.getTrans_serial());
			BaseResponse oldBaseResponse=transReqService.insert(transTransreq);
			if(oldBaseResponse!=null){
				logger.info(String.format("【单笔转账】原单已存在，不进行消费|trans_serial:%s|status:%s",
						oldBaseResponse.getOrder_no(),oldBaseResponse.getTrans_serial()));
				throw new BusinessException(BusinessMsg.ORIGINAL_ORDER_NO_ERROR,BusinessMsg.getMsg(BusinessMsg.ORIGINAL_ORDER_NO_ERROR)+"转账订单重复，请检查订单");
			}
		}else{
			eaccAccountamtlist.setExt5(null);
		}
		List<EaccTransTransreqWithBLOBs> eaccTransTransreqList=new ArrayList<>();
		try{
			eaccTransTransreqList=doTransfer(eaccAccountamtlist);
			if(eaccTransTransreqList.size()>0){
				//单笔代付
				pay(eaccTransTransreqList.get(0));
			}
			if(transTransreq!=null){
				transTransreq.setStatus(FlowStatus.SUCCESS.getCode());
				transTransreq.setReturn_code(BusinessMsg.SUCCESS);
				transTransreq.setReturn_msg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));
				transReqService.insert(transTransreq);
			}
		}catch (Exception e){
			logger.error("【单笔转账】异常",e);
			if(transTransreq!=null){
				transTransreq.setStatus(FlowStatus.FAIL.getCode());
				if(e instanceof BusinessException){
					BaseResponse baseResponse=((BusinessException)e).getBaseResponse();
					transTransreq.setReturn_code(baseResponse.getRecode());
					transTransreq.setReturn_msg(baseResponse.getRemsg());
				}else{
					transTransreq.setReturn_code(BusinessMsg.UNKNOW_ERROE);
					transTransreq.setReturn_msg(BusinessMsg.getMsg(BusinessMsg.UNKNOW_ERROE));
				}
				transReqService.insert(transTransreq);
			}
			throw e;
		}

		return true;
	}

	@Override
	@Transactional
	public boolean batchTransfer(List<EaccAccountamtlist> eaccAccountamtlist) throws BusinessException {
		//添加流水
		TransTransreq transTransreq=null;
		EaccAccountamtlist teamEaccAccountamtlist=eaccAccountamtlist.get(0);
		if(!"异步".equals(teamEaccAccountamtlist.getExt5())){
			BaseRequest baseRequest=new BaseRequest();
			baseRequest.setOrder_no(teamEaccAccountamtlist.getOrder_no());
			baseRequest.setPartner_trans_date(teamEaccAccountamtlist.getTrans_date());
			baseRequest.setPartner_trans_time(teamEaccAccountamtlist.getTrans_time());
			baseRequest.setMer_no(teamEaccAccountamtlist.getPlat_no());
			transTransreq=transReqService.getTransTransReq(baseRequest,
					teamEaccAccountamtlist.getTrans_code(),teamEaccAccountamtlist.getTrans_name(),false);
			transTransreq.setOrder_no(teamEaccAccountamtlist.getOrder_no());
			transTransreq.setTrans_serial(teamEaccAccountamtlist.getTrans_serial());
			BaseResponse oldBaseResponse=transReqService.insert(transTransreq);
			if(oldBaseResponse!=null){
				logger.info(String.format("【批量转账】原单已存在，不进行消费|trans_serial:%s|status:%s",
						oldBaseResponse.getTrans_serial(),oldBaseResponse.getOrder_status()));
				throw new BusinessException(BusinessMsg.ORIGINAL_ORDER_NO_ERROR, BusinessMsg.getMsg(BusinessMsg.ORIGINAL_ORDER_NO_ERROR)+"转账订单重复，请检查订单");
			}
		}else{
			teamEaccAccountamtlist.setExt5(null);
		}
		try{
			List<EaccTransTransreqWithBLOBs> eaccTransTransreqList=new ArrayList<>();
			for(EaccAccountamtlist expense:eaccAccountamtlist){
				try{
					List<EaccTransTransreqWithBLOBs> thisEaccTrans=doTransfer(expense);
					if(!("OnlyVirtual".equals(expense.getRemark()))){
						eaccTransTransreqList.addAll(thisEaccTrans);
					}
				}catch (BusinessException e){
					logger.error(e);
					throw new BusinessException(e.getBaseResponse());
				}
			}
			//如有电子账户转账，开始电子账户转账
			if(eaccTransTransreqList.size()>1){
				//是否为代付
				List<EaccTransTransreqWithBLOBs> successTrans=new ArrayList<>();
				for(EaccTransTransreqWithBLOBs eaccTransTransreq:eaccTransTransreqList){
					//单笔代付
					logger.info("【批量转账】=======调用单笔代付");
					try {
						pay(eaccTransTransreq);
						successTrans.add(eaccTransTransreq);
					}catch (Exception e){
						BaseResponse baseResponse=new BaseResponse();
						if(e instanceof BusinessException){
							baseResponse=((BusinessException) e).getBaseResponse();
							//如果超时，冲正
							if("请求第三方超时".equals(baseResponse.getRemsg())){
								logger.info("【批量转账】=======单笔代付请求超时，冲正");
								BankReverse bankReverse=getBankReverse(eaccTransTransreq);
								accountTransferThirdParty.addReverseToQueue(bankReverse);
							}
						}else{
							baseResponse.setRecode(BusinessMsg.TRANS_FAIL);
							baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.TRANS_FAIL)+":电子账户转账失败");
						}
						//如果是处理中，在上层已经发起冲正,如果是其它错误，无需冲正，此时只需要冲正之前转成功的
						if(successTrans.size()>0){
							for(EaccTransTransreqWithBLOBs trans:successTrans){
								BankReverse bankReverse=getBankReverse(trans);
								accountTransferThirdParty.addReverseToQueue(bankReverse);
							}
						}
						throw new BusinessException(baseResponse);
					}
				}

			}else if(eaccTransTransreqList.size()==1){
				//单笔代付
				logger.info("【批量转账】=======调用单笔代付");
				try {
					pay(eaccTransTransreqList.get(0));
				}catch (BusinessException e){
					BaseResponse baseResponse=e.getBaseResponse();
					if("请求第三方超时".equals(baseResponse.getRemsg())){
						logger.info("【批量转账】=======单笔代付请求超时，冲正");
						BankReverse bankReverse=getBankReverse(eaccTransTransreqList.get(0));
						accountTransferThirdParty.addReverseToQueue(bankReverse);
					}
					throw e;
				}
			}
			if(transTransreq!=null){
				transTransreq.setStatus(FlowStatus.SUCCESS.getCode());
				transTransreq.setReturn_code(BusinessMsg.SUCCESS);
				transTransreq.setReturn_msg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));
			}
		}catch (Exception e){
			logger.error("【批量转账】异常",e);
			BaseResponse baseResponse=new BaseResponse();
			if(e instanceof BusinessException){
				baseResponse=((BusinessException) e).getBaseResponse();
			}else{
				baseResponse.setRecode(BusinessMsg.TRANS_FAIL);
				baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.TRANS_FAIL));
			}
			if(transTransreq!=null){
				transTransreq.setStatus(FlowStatus.FAIL.getCode());
				transTransreq.setReturn_code(baseResponse.getRecode());
				transTransreq.setReturn_msg(baseResponse.getRemsg());
			}
			throw new BusinessException(baseResponse);
		}finally {
			if(transTransreq!=null){
				transReqService.insert(transTransreq);
			}
		}

		return true;
	}

	@Override
	@Transactional
	public boolean fundTransfer(EaccAccountamtlist eaccAccountamtlist) throws BusinessException {
		EaccAccountamtlist incomeAccount = new EaccAccountamtlist();
		try {

			incomeAccount = getInParams(eaccAccountamtlist);

			incomeAccount.setPlat_no(eaccAccountamtlist.getOppo_plat_no());
			incomeAccount.setPlatcust(eaccAccountamtlist.getOppo_platcust());
			incomeAccount.setSubject(eaccAccountamtlist.getOppo_subject());
			incomeAccount.setSub_subject(eaccAccountamtlist.getOppo_sub_subject());

			incomeAccount.setOppo_plat_no(eaccAccountamtlist.getPlat_no());
			incomeAccount.setOppo_platcust(eaccAccountamtlist.getPlatcust());
			incomeAccount.setOppo_subject(eaccAccountamtlist.getSubject());
			incomeAccount.setOppo_sub_subject(eaccAccountamtlist.getSub_subject());

			incomeAccount.setAmt_type(AMTTYPE.Income.getCode());

			eaccAccountamtlist.setAmt_type(AMTTYPE.Expense.getCode());
			//扣款
			accountAssetService.negativeWithdraw(eaccAccountamtlist);

			//加款
			accountAssetService.charge(incomeAccount);
		}catch (Exception e){
			BaseResponse baseResponse=new BaseResponse();
			if(e instanceof BusinessException){
				baseResponse=((BusinessException) e).getBaseResponse();
			}else{
				logger.error(e);
				baseResponse.setRecode(BusinessMsg.RUNTIME_EXCEPTION);
				baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.RUNTIME_EXCEPTION)+"账户加扣款异常");
			}
			throw new BusinessException(baseResponse);
		}
		return true;
	}

	private EaccAccountamtlist getInParams(EaccAccountamtlist outEaccAccountamtlist){
		EaccAccountamtlist inEaccAccountamtlist=new EaccAccountamtlist();
		inEaccAccountamtlist.setOrder_no(outEaccAccountamtlist.getOrder_no());
		inEaccAccountamtlist.setTrans_serial(outEaccAccountamtlist.getTrans_serial());
		inEaccAccountamtlist.setPlat_no(outEaccAccountamtlist.getPlat_no());
		inEaccAccountamtlist.setPlatcust(outEaccAccountamtlist.getPlatcust());
		inEaccAccountamtlist.setSubject(outEaccAccountamtlist.getSubject());
		inEaccAccountamtlist.setSub_subject(outEaccAccountamtlist.getSub_subject());
		inEaccAccountamtlist.setOppo_plat_no(outEaccAccountamtlist.getOppo_plat_no());
		inEaccAccountamtlist.setOppo_platcust(outEaccAccountamtlist.getOppo_platcust());
		inEaccAccountamtlist.setOppo_subject(outEaccAccountamtlist.getOppo_subject());
		inEaccAccountamtlist.setOppo_sub_subject(outEaccAccountamtlist.getOppo_sub_subject());
		inEaccAccountamtlist.setAmt(outEaccAccountamtlist.getAmt());
		inEaccAccountamtlist.setTrans_code(outEaccAccountamtlist.getTrans_code());
		inEaccAccountamtlist.setTrans_name(outEaccAccountamtlist.getTrans_name());
		inEaccAccountamtlist.setTrans_date(outEaccAccountamtlist.getTrans_date());
		inEaccAccountamtlist.setTrans_time(outEaccAccountamtlist.getTrans_time());
		return inEaccAccountamtlist;
	}

	@Override
	@Transactional
	public boolean batchFundTransfer(List<EaccAccountamtlist> eaccAccountamtlist) throws BusinessException {
		for(EaccAccountamtlist expense:eaccAccountamtlist){
			try{
				fundTransfer(expense);
			}catch (BusinessException e){
				logger.error(e);
				throw new BusinessException(e.getBaseResponse());
			}
		}
		return true;
	}

	public static void main(String[] args) {
		System.out.println(String.format("账户余额不足，可用金额【%s】，转账金额【%s】", BigDecimal.ZERO,BigDecimal.TEN));
	}

	@Override
	public void addTransFlow(EaccAccountamtlist eaccAccountamtlist) throws BusinessException {
		eaccAccountamtlist.setCreate_time(DateUtils.today());
		//查看交易流水是否为空，为空则生成新的流水编号
		if(StringUtils.isEmpty(eaccAccountamtlist.getTrans_serial())){
			eaccAccountamtlist.setTrans_serial(SeqUtil.getSeqNum());
		}
		//查看交易日期是否为空，为空则填充当前日期
		if(StringUtils.isEmpty(eaccAccountamtlist.getTrans_date())){
			eaccAccountamtlist.setTrans_date(DateUtils.todayStr(DateUtils.DEF_FORMAT_STRING_DATE));
		}
		//查看交易时间是否为空，为空则填充当前时间
		if(StringUtils.isEmpty(eaccAccountamtlist.getTrans_time())){
			eaccAccountamtlist.setTrans_time(DateUtils.getTime());
		}
		if(null == eaccAccountamtlist.getAccount_date()){
			eaccAccountamtlist.setAccount_date(new Date());
		}
		eaccAccountamtlist.setEnabled(Constants.ENABLED);
		if(StringUtils.isEmpty(eaccAccountamtlist.getSwitch_state())){
			eaccAccountamtlist.setSwitch_state(SwitchState.NOSWITCH.getCode());
		}
		if(eaccAccountamtlist.getSwitch_amt()==null){
			eaccAccountamtlist.setSwitch_amt(BigDecimal.ZERO);
		}
		//添加流水
		logger.info("【插入交易流水】eaccAccountamtlist："+JSON.toJSONString(eaccAccountamtlist));
		eaccAccountamtlistMapper.insert(eaccAccountamtlist);

	}

	@Override
	public void updateTransFlow(EaccAccountamtlist eaccAccountamtlist) throws BusinessException {
		EaccAccountamtlist oldEaccAccountamtlist = queryTransFlow(eaccAccountamtlist);
		if(oldEaccAccountamtlist != null) {
			logger.debug("start update 交易流水");
			if(null == oldEaccAccountamtlist.getUpdate_by()) oldEaccAccountamtlist.setUpdate_by(oldEaccAccountamtlist.getPlat_no());
			oldEaccAccountamtlist.setUpdate_time(DateUtils.today());
			eaccAccountamtlistMapper.updateByPrimaryKey(oldEaccAccountamtlist);
		}
	}

	@Override
	public EaccAccountamtlist queryTransFlow(EaccAccountamtlist eaccAccountamtlist) throws BusinessException {
		EaccAccountamtlistExample example = new EaccAccountamtlistExample();
		EaccAccountamtlistExample.Criteria criteria = example.createCriteria();
        criteria.andTrans_serialEqualTo(eaccAccountamtlist.getTrans_serial());
        criteria.andPlat_noEqualTo(eaccAccountamtlist.getPlat_no());
        criteria.andPlatcustEqualTo(eaccAccountamtlist.getPlatcust());
        criteria.andSubjectEqualTo(eaccAccountamtlist.getSubject());
		criteria.andSub_subjectEqualTo(eaccAccountamtlist.getSub_subject());
		List<EaccAccountamtlist> eaccAccountamtlistList = eaccAccountamtlistMapper.selectByExample(example);

		if(eaccAccountamtlistList.size()==1){
			return eaccAccountamtlistList.get(0);
		}else if(eaccAccountamtlistList.size()<=0){
			return null;
		}else{
			BaseResponse baseResponse=new BaseResponse();
			baseResponse.setRecode(BusinessMsg.DATEBASE_EXCEPTION);
			baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.DATEBASE_EXCEPTION) + "数据库有重复的流水");
			throw new BusinessException(baseResponse);
		}
	}

	@Override
	public List<EaccAccountamtlist> queryTransFlowList(EaccAccountamtlist eaccAccountamtlist) throws BusinessException {
        EaccAccountamtlistExample example = new EaccAccountamtlistExample();
        EaccAccountamtlistExample.Criteria criteria = example.createCriteria();
        criteria.andTrans_serialEqualTo(eaccAccountamtlist.getTrans_serial());
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
//		if(!StringUtils.isEmpty(eaccAccountamtlist.getOppo_sub_subject())){
//			criteria.andOppo_sub_subjectEqualTo(eaccAccountamtlist.getOppo_sub_subject());
//		}
		List<EaccAccountamtlist> eaccAccountamtlistList=eaccAccountamtlistMapper.selectByExample(example);
		return eaccAccountamtlistList;
	}
	@Override
	@Transactional
	public boolean rollbackBatchWithdraw(String trans_serial,String detailNo)throws BusinessException{
		EaccAccountamtlistExample example = new EaccAccountamtlistExample();
		EaccAccountamtlistExample.Criteria criteria = example.createCriteria();
		criteria.andTrans_serialEqualTo(trans_serial);
		criteria.andAmt_typeEqualTo(AmtType.INCOME.getCode());
		example.setOrderByClause("id desc");
		List<EaccAccountamtlist> eaccAccountamtlistList = eaccAccountamtlistMapper.selectByExample(example);
		if(eaccAccountamtlistList.size() > 0){
			logger.info("【批量提现】异常，查询到电子账户转账，开始回滚转账-->detail_no:" + detailNo);
			queryEaccAccountamtlistByTransSerialAndPlatcust(trans_serial);
		}
		return true;
	}
	@Override
	@Transactional
	public boolean queryEaccAccountamtlistByTransSerialAndPlatcust(String trans_serial) throws BusinessException {
		logger.info("【回滚装账】-->开始回滚转账-->trans_serial:" + trans_serial);
		try {
			EaccAccountamtlistExample example = new EaccAccountamtlistExample();
			EaccAccountamtlistExample.Criteria criteria = example.createCriteria();
			criteria.andTrans_serialEqualTo(trans_serial);
			criteria.andAmt_typeEqualTo(AmtType.INCOME.getCode());
			criteria.andEnabledEqualTo(Constants.ENABLED);
			example.setOrderByClause("id desc");
			List<EaccAccountamtlist> eaccAccountamtlistList = eaccAccountamtlistMapper.selectByExample(example);
			for (EaccAccountamtlist e : eaccAccountamtlistList) {
				if(e.getTrans_code().equals(TransConsts.REFUND_CODE)) {
					logger.info("【回滚装账】查询到已回滚不做处理-->trans_serial:" + trans_serial);
					return true;
				}
			}
			for (EaccAccountamtlist e : eaccAccountamtlistList) {
				e.setId(null);
				e.setTrans_code(TransConsts.REFUND_CODE);
				e.setTrans_name(TransConsts.REFUND_NAME);
				e.setExt5("异步");
				e.setSubject(Tsubject.CASH.getCode());
				e.setOppo_subject(Tsubject.CASH.getCode());
				if(Ssubject.EACCOUNT.getCode().equals(e.getSub_subject())){
					e.setPlat_no(e.getOppo_plat_no());
				}
				//判断手续费
				if(!Ssubject.PLAT.getCode().equals(e.getSub_subject())){
					e.setRemark("OnlyVirtual");
				}
			}
			batchTransfer(eaccAccountamtlistList);
		}catch (Exception e){
			logger.error("提现反向转账报错：", e );
			throw new BusinessException(BusinessMsg.TRANS_FAIL,BusinessMsg.getMsg(BusinessMsg.TRANS_FAIL));
		}
		return true;
	}

	@Override
	@Transactional
	public boolean queryEaccAccountamtlistByTransSerial(String trans_serial,String df_trans_date) throws BusinessException {
		try {
			EaccAccountamtlistExample example = new EaccAccountamtlistExample();
			EaccAccountamtlistExample.Criteria criteria = example.createCriteria();
			criteria.andTrans_serialEqualTo(trans_serial);
			List<EaccAccountamtlist> eaccAccountamtlistList = eaccAccountamtlistMapper.selectByExample(example);
			for (EaccAccountamtlist e : eaccAccountamtlistList) {
				if(StringUtils.isNotBlank(df_trans_date)){
					EaccAccountamtlist eaccAccountamtlist = new EaccAccountamtlist();
					eaccAccountamtlist.setId(e.getId());
					eaccAccountamtlist.setAccount_date(DateUtils.parseDate(df_trans_date,DateUtils.DEF_FORMAT_NOTIME));
					eaccAccountamtlistMapper.updateByPrimaryKeySelective(eaccAccountamtlist);
				}
			}
		}catch (Exception e){
			logger.error("收到行内通知修改账期失败：", e );
		}
		return true;
	}

	/**
	 * 查询交易流水   中间户减款
	 * @param eaccAccountamtlist
	 * @return boolean
	 * @throws BusinessException
	 */
	@Override
	public boolean queryEaccAccountamtlistWithdrawByTransSerial(EaccAccountamtlist eaccAccountamtlist) throws BusinessException {
		EaccAccountamtlistExample example = new EaccAccountamtlistExample();
		EaccAccountamtlistExample.Criteria criteria = example.createCriteria();
		criteria.andTrans_serialEqualTo(eaccAccountamtlist.getTrans_serial());
		criteria.andTrans_codeEqualTo(eaccAccountamtlist.getTrans_code());
		List<EaccAccountamtlist> eaccAccountamtlistList = eaccAccountamtlistMapper.selectByExample(example);
		if(eaccAccountamtlistList.size() <= 0){
			try {
				if(StringUtils.isBlank(eaccAccountamtlist.getPlat_no())
						|| StringUtils.isBlank(eaccAccountamtlist.getPlatcust())
						|| StringUtils.isBlank(eaccAccountamtlist.getSubject())
						|| StringUtils.isBlank(eaccAccountamtlist.getSub_subject())
						|| StringUtils.isBlank(eaccAccountamtlist.getTrans_code())){
					return false;
				}
				accountAssetService.withdraw(eaccAccountamtlist);
				logger.info("【接受行内代付的异步通知】中间账户减款成功-->流水号："+eaccAccountamtlist.getTrans_serial());
			}catch (Exception e){
				logger.error("【接受行内代付的异步通知】,交易成功后给中间账户减款失败-->流水号："+eaccAccountamtlist.getTrans_serial(),e);
			}
		}
		return true;
	}

	@Override
	public boolean refundBack(String inAccount, String outAccount) throws BusinessException {
		return false;
	}

	@Override
	@Transactional
	public void transactionTest(String platcust, BigDecimal n_balance) throws BusinessException {
		AccountSubjectInfo accountSubjectInfo=accountQueryService.queryAccount("BOB-FENGJR-ZS-B-20170509",platcust,Tsubject.FLOAT.getCode(),Ssubject.INVEST.getCode());
		if(accountSubjectInfo!=null){
			logger.info("账户"+platcust+"，当前余额"+accountSubjectInfo.getN_balance());
			AccountSubjectInfoExample example=new AccountSubjectInfoExample();
			accountSubjectInfo.setN_balance(n_balance);
			if(null == accountSubjectInfo.getUpdate_by()) accountSubjectInfo.setUpdate_by(SeqUtil.RANDOM_KEY);
			accountSubjectInfo.setUpdate_time(new Date());
			accountSubjectInfoMapper.updateByPrimaryKeySelective(accountSubjectInfo);
		}
	}

	@Override
	public EaccAccountamtlist setSubjectAndAmt(EaccAccountamtlist expenseAccount, String subject,String sub_subject,
													 String oppo_subject,String oppo_sub_subject,BigDecimal amt){
		expenseAccount.setSubject(subject);
		expenseAccount.setSub_subject(sub_subject);
		expenseAccount.setOppo_subject(oppo_subject);
		expenseAccount.setOppo_sub_subject(oppo_sub_subject);
		expenseAccount.setAmt(amt);
		return expenseAccount;
	}

	@Override
	public EaccTransTransreqWithBLOBs eaccountTrans(EaccAccountamtlist eaccAccountamtlist,String mall_no)throws BusinessException{
		//查询电子账户号
		String outAccount=null;
		String inAccount=null;
		String outAccountName=null;
		String inAccountName=null;
		boolean outPerson=false;
		boolean inPerson=false;
		if(Ssubject.EACCOUNT.getCode().equals(eaccAccountamtlist.getSub_subject())){
			//如果转出账户是电子账户，查询电子账户号
			EaccUserinfo eaccUserinfo=accountQueryService.getEaccUserinfo(mall_no,eaccAccountamtlist.getPlatcust());
			outAccount=eaccUserinfo.getEaccount();
			outAccountName=eaccUserinfo.getName();
			outPerson=true;
		}else{
			//如果转出账户不是电子账户号，查询存管户
			PlatCardinfo platCardinfo=accountQueryService.getPlatcardinfo(eaccAccountamtlist.getPlat_no(),PlatAccType.PLATTRUST.getCode());
			outAccount=platCardinfo.getCard_no();
			outAccountName=platCardinfo.getCard_name();
			outPerson=false;
		}
		if(Ssubject.EACCOUNT.getCode().equals(eaccAccountamtlist.getOppo_sub_subject())){
			//如果转入账户是电子账户，查询电子账户号
			EaccUserinfo eaccUserinfo=accountQueryService.getEaccUserinfo(mall_no,eaccAccountamtlist.getOppo_platcust());
			inAccount=eaccUserinfo.getEaccount();
			inAccountName=eaccUserinfo.getName();
			inPerson=true;
		}else{
			//如果转入账户不是电子账户号，查询存管户
			PlatCardinfo platCardinfo=accountQueryService.getPlatcardinfo(eaccAccountamtlist.getOppo_plat_no(),PlatAccType.PLATTRUST.getCode());
			inAccount=platCardinfo.getCard_no();
			inAccountName=platCardinfo.getCard_name();
			inPerson=false;
		}
		EaccTransTransreqWithBLOBs eaccTransTransreq=new EaccTransTransreqWithBLOBs();
		eaccTransTransreq.setMall_no(mall_no);
		eaccTransTransreq.setParent_trans_serial(eaccAccountamtlist.getTrans_serial());
		eaccTransTransreq.setTrans_serial(SeqUtil.getSeqNum());
		eaccTransTransreq.setEaccount(outAccount);
		eaccTransTransreq.setName(outAccountName);
		if(outPerson){
			eaccTransTransreq.setProperty(Integer.valueOf(CusType.PERSONAL.getCode()));
		}else{
			eaccTransTransreq.setProperty(Integer.valueOf(CusType.COMPANY.getCode()));
		}
		eaccTransTransreq.setOppo_eaccount(inAccount);
		eaccTransTransreq.setOppo_name(inAccountName);
		if(inPerson){
			eaccTransTransreq.setOppo_property(Integer.valueOf(CusType.PERSONAL.getCode()));
		}else{
			eaccTransTransreq.setOppo_property(Integer.valueOf(CusType.COMPANY.getCode()));
		}
		eaccTransTransreq.setTrans_amt(eaccAccountamtlist.getAmt());
		return eaccTransTransreq;
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
	@Transactional
	public Boolean doCharge(EaccAccountamtlist income) {
		return accountAssetService.charge(income);
	}

	public boolean pay(EaccTransTransreqWithBLOBs eaccTransTransreq)throws BusinessException{
		Map<String,Object> params=new HashMap<>();
		params.put("partner_id","10000001");
		params.put("partner_serial_no",eaccTransTransreq.getTrans_serial());
		params.put("partner_trans_date",DateUtils.getyyyyMMddDate());
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
		EpayAgentPayRealResponse response=null;
		try{
			eaccTransTransreq.setReq_params(JSON.toJSONString(params, GlobalConfig.serializerFeature));
			response=accountTransferThirdParty.eaccTransfer(params,eaccTransTransreq.getMall_no());
			logger.info("【代付】=========E支付返回："+response.toString());
			eaccTransTransreq.setRes_params(JSON.toJSONString(response, GlobalConfig.serializerFeature));
			if(response.getData()==null || !(("3".trim()).equals(response.getData().get(0).getPay_status().trim()))){
				logger.info("【代付】=========电子账户转失败，调查询接口冲正trans_serial:"+eaccTransTransreq.getTrans_serial());
				if(response.getData()==null && "7".trim().equals(response.getData().get(0).getPay_status().trim())){
					eaccTransTransreq.setStatus(Integer.valueOf(FlowStatus.FAIL.getCode()));
				}
				throw new BusinessException(BusinessMsg.PAYMENT_FAILED,"电子账户转账失败");
			}
			//记录电子账户转账成功流水
			eaccTransTransreq.setStatus(Integer.valueOf(FlowStatus.SUCCESS.getCode()));
			return true;
		}catch (Exception e){
			logger.info("【代付】=========电子账户转失败：",e);
			if(null==eaccTransTransreq.getStatus()){
				logger.info("【代付】=========电子账户转失败，调接口冲正trans_serial:"+eaccTransTransreq.getTrans_serial());
				BankReverse bankReverse=new BankReverse();
				bankReverse.setPartner_trans_date((String) params.get("partner_trans_date"));
				bankReverse.setOriginal_serial_no(eaccTransTransreq.getTrans_serial());
				bankReverse.setMall_no(eaccTransTransreq.getMall_no());
				bankReverse.setOccur_balance(eaccTransTransreq.getTrans_amt());
				accountTransferThirdParty.addReverseToQueue(bankReverse);
				eaccTransTransreq.setStatus(999);//999为电子账户处理中
				logger.info("【代付】=========电子账户转账失败");
				//记录电子账户转账失败流水
				if(StringUtils.isBlank(eaccTransTransreq.getRes_params())){
					eaccTransTransreq.setRes_params(e.getMessage());
				}
			}
			if(response==null){
				throw new BusinessException(BusinessMsg.PAYMENT_FAILED,"电子账户转账失败");
			}else{
				throw e;
			}
		}finally {
			eaccTransTransreqService.addFlow(eaccTransTransreq);
		}
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

			logger.info("【批量代付】=========E支付返回："+response.toString());
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
						logger.info("【批量代付】=========订单状态查询失败："+queryResponse.getError_info());
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
				logger.info("【批量代付】=========电子账户转账失败："+response.getError_info());
				//记录电子账户转账失败流水
				for(EaccTransTransreqWithBLOBs eaccTransTransreq:eaccTransTransreqList){
					eaccTransTransreq.setRes_params(JSON.toJSONString(response,GlobalConfig.serializerFeature));
					eaccTransTransreq.setStatus(Integer.valueOf(FlowStatus.FAIL.getCode()));
					eaccTransTransreqService.addFlow(eaccTransTransreq);
				}
				throw new BusinessException(BusinessMsg.PAYMENT_FAILED,"电子账户转账失败："+response.getError_info());
			}
		}catch (Exception e){
			logger.info("【批量代付】=========电子账户转账失败："+e);
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

	public boolean batchCollection(List<EaccTransTransreqWithBLOBs> eaccTransTransreqList)throws BusinessException{
		Map<String,Object> params=new HashMap<>();
		String trans_serial=eaccTransTransreqList.get(0).getParent_trans_serial();
		params.put("partner_id","10000001");
		params.put("partner_serial_no",trans_serial);
		params.put("partner_trans_date",DateUtils.getyyyyMMddDate());
		params.put("partner_trans_time",DateUtils.getHHmmssTime());
		params.put("third_no",eaccTransTransreqList.get(0).getOppo_eaccount());
		params.put("sendercomp_id","10000001");
		params.put("targetcomp_id","91000");
		List<BatchPayDetail> detailList=new ArrayList<>();
		BigDecimal allAmt=BigDecimal.ZERO;
		for(EaccTransTransreqWithBLOBs eaccTransTransreq:eaccTransTransreqList){
			BatchPayDetail detail=new BatchPayDetail();
			detail.setDetail_no(eaccTransTransreq.getTrans_serial());
			detail.setOccur_balance(eaccTransTransreq.getTrans_amt());
			detail.setClient_name(eaccTransTransreq.getName());
			detail.setCard_no(eaccTransTransreq.getEaccount());
			detail.setBank_id("04031000");
			if(CusType.PERSONAL.getCode().equals(String.valueOf(eaccTransTransreq.getProperty()))){
				params.put("client_property","0");
			}else{
				params.put("client_property","1");
			}
			detail.setSummary(eaccTransTransreq.getEaccount().substring(eaccTransTransreq.getEaccount().length()-5)
					+"->"+eaccTransTransreq.getOppo_eaccount().substring(eaccTransTransreq.getOppo_eaccount().length()-5));
			allAmt=allAmt.add(eaccTransTransreq.getTrans_amt());
			params.put("details",JSON.toJSONString(detail,GlobalConfig.serializerFeature));
			eaccTransTransreq.setReq_params(JSON.toJSONString(params,GlobalConfig.serializerFeature));
			detailList.add(detail);
		}
		params.put("total_num",eaccTransTransreqList.size());
		params.put("total_balance",allAmt.toString());
		params.put("details",JSON.toJSONString(detailList,GlobalConfig.serializerFeature));
		BatchPayResponse response=accountTransferThirdParty.batchCollection(params,eaccTransTransreqList.get(0).getMall_no());
		List<BatchPayResponseData> batchPayResponseDataList=response.getData();
		logger.info("【批量代扣】=========E支付返回："+response.toString());
		if(response.getData()!=null){
			for(EaccTransTransreqWithBLOBs eaccTransTransreq:eaccTransTransreqList){
				String detail_trans_serial=eaccTransTransreq.getTrans_serial();
				for(BatchPayResponseData data:batchPayResponseDataList){
					if(detail_trans_serial.equals(data.getPartner_serial_no())){
						String status=FlowStatus.INPROCESS.getCode();
						if("3".equals(data.getPay_status())){
							//交易成功
							status=FlowStatus.SUCCESS.getCode();
						}else if("2".equals(data.getPay_status())){
							//处理中
							status=FlowStatus.INPROCESS.getCode();
						}else{
							//交易失败
							status=FlowStatus.FAIL.getCode();
						}
						//记录电子账户转账成功流水
						eaccTransTransreq.setStatus(Integer.valueOf(FlowStatus.SUCCESS.getCode()));
						eaccTransTransreqService.addFlow(eaccTransTransreq);
						break;
					}
				}
			}
		}else{
			logger.info("【批量代扣】=========电子账户转账失败："+response.getError_info());
			//记录电子账户转账失败流水
			for(EaccTransTransreqWithBLOBs eaccTransTransreq:eaccTransTransreqList){
				eaccTransTransreq.setRes_params(JSON.toJSONString(response,GlobalConfig.serializerFeature));
				eaccTransTransreq.setStatus(Integer.valueOf(FlowStatus.FAIL.getCode()));
				eaccTransTransreqService.addFlow(eaccTransTransreq);
			}
			throw new BusinessException(BusinessMsg.PAYMENT_FAILED,"电子账户转账失败："+response.getError_info());
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

	@Override
	@Transactional
	public List<EaccTransTransreqWithBLOBs> doTransferRollBack(EaccAccountamtlist inEaccAccountamtlist) throws BusinessException{
		List<EaccTransTransreqWithBLOBs> eaccTransTransreqList=new ArrayList<>();
		try{
			logger.info("反向转账参数："+inEaccAccountamtlist.toString());
			if(StringUtils.isBlank(inEaccAccountamtlist.getPlatcust()) || StringUtils.isBlank(inEaccAccountamtlist.getSubject())
					|| StringUtils.isBlank(inEaccAccountamtlist.getSub_subject()) || StringUtils.isBlank(inEaccAccountamtlist.getOppo_platcust())
					|| StringUtils.isBlank(inEaccAccountamtlist.getOppo_subject()) || StringUtils.isBlank(inEaccAccountamtlist.getOppo_sub_subject())
					|| inEaccAccountamtlist.getAmt()==null || StringUtils.isBlank(inEaccAccountamtlist.getPlat_no())
					|| StringUtils.isBlank(inEaccAccountamtlist.getTrans_serial()) || StringUtils.isBlank(inEaccAccountamtlist.getTrans_code())
					|| StringUtils.isBlank(inEaccAccountamtlist.getTrans_name())){
				logger.info("反向转账参数错误，请程序员检查代码！！！");
				throw new BusinessException(BusinessMsg.PARAMETER_ERROR,"反向转账失败");
			}

			EaccAccountamtlist outEaccAccountamtlist = new EaccAccountamtlist();
			BeanUtils.copyProperties(outEaccAccountamtlist, inEaccAccountamtlist);
			outEaccAccountamtlist.setPlat_no(inEaccAccountamtlist.getOppo_plat_no());
			outEaccAccountamtlist.setOppo_plat_no(inEaccAccountamtlist.getPlat_no());
			outEaccAccountamtlist.setSubject(inEaccAccountamtlist.getOppo_subject());
			outEaccAccountamtlist.setSub_subject(inEaccAccountamtlist.getOppo_sub_subject());
			outEaccAccountamtlist.setPlatcust(inEaccAccountamtlist.getOppo_platcust());
			outEaccAccountamtlist.setOppo_subject(inEaccAccountamtlist.getSubject());
			outEaccAccountamtlist.setOppo_sub_subject(inEaccAccountamtlist.getSub_subject());
			outEaccAccountamtlist.setOppo_platcust(inEaccAccountamtlist.getPlatcust());
			outEaccAccountamtlist.setAmt_type(AMTTYPE.Expense.getCode());//outEaccAccountamtlist作为出账
			inEaccAccountamtlist.setAmt_type(AMTTYPE.Income.getCode());//inEaccAccountamtlist作为入账
			//扣款
			accountAssetService.withdraw(outEaccAccountamtlist);
			//加款
			accountAssetService.charge(inEaccAccountamtlist);

			PlatPlatinfoExample platPlatinfoExample = new PlatPlatinfoExample();
			PlatPlatinfoExample.Criteria criteria = platPlatinfoExample.createCriteria();
			criteria.andEnabledEqualTo(Constants.ENABLED);
			criteria.andPlat_noEqualTo(outEaccAccountamtlist.getPlat_no());
			List<PlatPlatinfo> platPlatinfoList = platPlatinfoMapper.selectByExample(platPlatinfoExample);
			if(platPlatinfoList==null || platPlatinfoList.size()==0){
				logger.info("平台信息表对应平台没有配置集团信息，或者找不到该平台信息");
				return null;
			}

			if(Ssubject.EACCOUNT.getCode().equals(inEaccAccountamtlist.getSub_subject())
					|| Ssubject.EACCOUNT.getCode().equals(inEaccAccountamtlist.getOppo_sub_subject())){
				//将电子账户转账需要参数放入eaccTransTransreqList中
				eaccTransTransreqList.add(eaccountTrans(outEaccAccountamtlist,platPlatinfoList.get(0).getMall_no()));
			}
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
		return eaccTransTransreqList;
	}

	@Override
	@Transactional
	public Boolean transferRollBack(EaccAccountamtlist inEaccAccountamtlist) throws BusinessException{
		List<EaccTransTransreqWithBLOBs> eaccTransTransreqList=new ArrayList<>();
		eaccTransTransreqList=doTransferRollBack(inEaccAccountamtlist);
		if(eaccTransTransreqList.size()>0){
			//单笔代付
			pay(eaccTransTransreqList.get(0));
		}
		return true;
	}

	@Override
	@Transactional
	public boolean batchTransferRollBack(List<EaccAccountamtlist> eaccAccountamtlists) throws BusinessException{
		List<EaccTransTransreqWithBLOBs> eaccTransTransreqList=new ArrayList<>();
		for(EaccAccountamtlist inEaccAccountamtlist:eaccAccountamtlists){
			try{
				List<EaccTransTransreqWithBLOBs> thisEaccTrans=doTransferRollBack(inEaccAccountamtlist);
				eaccTransTransreqList.addAll(thisEaccTrans);
			}catch (BusinessException e){
				logger.error(e);
				throw new BusinessException(e.getBaseResponse());
			}
		}
		if(eaccTransTransreqList.size()==1){
			pay(eaccTransTransreqList.get(0));
		}else if(eaccTransTransreqList.size()>1){//如有电子账户转账，开始电子账户转账
			batchPay(eaccTransTransreqList);
		}
		return true;
	}

	private BankReverse getBankReverse(EaccTransTransreqWithBLOBs eaccTransTransreq){
		BankReverse bankReverse=new BankReverse();
		bankReverse.setMall_no(eaccTransTransreq.getMall_no());
		bankReverse.setOccur_balance(eaccTransTransreq.getTrans_amt());
		bankReverse.setOriginal_serial_no(eaccTransTransreq.getTrans_serial());
		bankReverse.setPartner_trans_date(DateUtils.getyyyyMMddDate());
		return bankReverse;
	}
}
