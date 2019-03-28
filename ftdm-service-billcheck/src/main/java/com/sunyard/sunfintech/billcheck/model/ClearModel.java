package com.sunyard.sunfintech.billcheck.model;

import com.sunyard.sunfintech.billcheck.handler.QueryListHandler;
import com.sunyard.sunfintech.billcheck.model.bo.ClearAccount;
import com.sunyard.sunfintech.core.util.DateUtils;
import com.sunyard.sunfintech.dao.entity.EaccAccountamtlist;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 每个账户科目对应一个对象
 * @author djh
 *
 */
public class ClearModel {

	public ClearModel(String plat_no, String clear_date, String pay_code) {
		this.plat_no = plat_no;
		this.clear_date = clear_date;
		this.pay_code = pay_code;
		advanceReference.set(BigDecimal.ZERO);
		//platReference.set(BigDecimal.ZERO);
	}

	private String plat_no;

	private String clear_date;

	private String pay_code;

	private ConcurrentHashMap<String,ClearAccount> accounts = new ConcurrentHashMap<>();

	private QueryListHandler chargeListHandler;

	private QueryListHandler transListHandler;

	private List<EaccAccountamtlist> transListNew = new ArrayList<>();

	private Map<String,BigDecimal> payCodeCharges = new HashMap<>();//各渠道充值金额

	//private volatile boolean tryAgain = false;

	private int tryTimes = 1;

	public final  static int MAX_TRY_TIME = 30;

	private AtomicReference<BigDecimal> advanceReference = new AtomicReference<>();//垫付金额

	//private AtomicReference<BigDecimal> platReference = new AtomicReference<>();//充值金额,分渠道payCodeCharges，不用它了

	private long maxTransId; //最大查询id

	//private long clearTimes = 0; //最大查询id

	public void addAdvance(BigDecimal amt) {
		while (true){
			BigDecimal oldVal = advanceReference.get();
			BigDecimal newVal = oldVal.add(amt);
			if(advanceReference.compareAndSet(oldVal,newVal)){
				break;
			}
		}
	}


	public void addAmtByPayCode(BigDecimal amt,String pay_code) {
		BigDecimal paycodeCharge = payCodeCharges.get(pay_code);
		paycodeCharge = paycodeCharge.add(amt);
		payCodeCharges.put(pay_code,paycodeCharge);
	}

	public Map<String, BigDecimal> getPayCodeCharges() {
		return payCodeCharges;
	}

//	public void setPayCodeCharges(Map<String, BigDecimal> payCodeCharges) {
//		this.payCodeCharges = payCodeCharges;
//	}

	public BigDecimal getAdvance() {
		return advanceReference.get();
	}

	public List<EaccAccountamtlist> getTransListNew() {
        return transListNew;
    }

	public long getMaxTransId() {
		if(transListNew.size()==0){
			maxTransId = transListHandler.getMergeListMaxId();
		}else {
			maxTransId = transListNew.get(transListNew.size()-1).getId();
		}
		return maxTransId;
	}

	public void setMaxTransId(long maxTransId) {
		this.maxTransId = maxTransId;
	}


	public long getTryTimes() {
		return tryTimes;
	}

	public void incTryTimes() {
		this.tryTimes++;
	}

	public QueryListHandler getChargeListHandler() {
		return chargeListHandler;
	}

	public void setChargeListHandler(QueryListHandler chargeListHandler) {
		this.chargeListHandler = chargeListHandler;
	}

	public QueryListHandler getTransListHandler() {
		return transListHandler;
	}

	public void setTransListHandler(QueryListHandler transListHandler) {
		this.transListHandler = transListHandler;
	}

	public String getPlat_no() {
		return plat_no;
	}

	public void setPlat_no(String plat_no) {
		this.plat_no = plat_no;
	}

	public String getClear_date() {
		return clear_date;
	}
	public Date getParsedClear_date() {
		return DateUtils.parseDate(clear_date,"yyyyMMdd");
	}

	public void setClear_date(String clear_date) {
		this.clear_date = clear_date;
	}

	public String getPay_code() {
		return pay_code;
	}

	public void setPay_code(String pay_code) {
		this.pay_code = pay_code;
	}

//	public boolean getTryAgain() {
//		return tryAgain;
//	}
//
//	public void setTryAgain(boolean tryAgain) {
//		this.tryAgain = tryAgain;
//	}

	public ConcurrentHashMap<String, ClearAccount> getAccounts() {
		return accounts;
	}

	public void setAccounts(ConcurrentHashMap<String, ClearAccount> accounts) {
		this.accounts = accounts;
	}

	@Override
	public String toString() {
		return super.toString();
	}


	public static void main(String[] args) {
		AtomicReference<BigDecimal> advanceReference = new AtomicReference<>();
		System.out.println(advanceReference.get());

	}
}
