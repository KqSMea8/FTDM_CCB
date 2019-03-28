package com.sunyard.sunfintech.billcheck.model.bo;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * 每个账户科目对应一个对象
 * @author djh
 *
 */
public class ClearAccount {

	protected String platcust;

	protected String subject;

	protected String sub_subject;

	protected String pay_code;


	public Map<Long,BigDecimal> relationTrans = new HashMap<>();

	protected BigDecimal n_balance = BigDecimal.ZERO;//计算出的该清算金额

	protected BigDecimal f_balance = BigDecimal.ZERO;//

	protected BigDecimal cur_n_balance = BigDecimal.ZERO;//账户实时余额

	protected BigDecimal cur_f_balance = BigDecimal.ZERO;//

	protected BigDecimal n_balance_bak = BigDecimal.ZERO;//执行事务前备份余额

	protected BigDecimal f_balance_bak = BigDecimal.ZERO;

	protected BigDecimal last_n_balance = BigDecimal.ZERO;//多次清算中上次清算金额

	protected BigDecimal last_f_balance = BigDecimal.ZERO;



	public BigDecimal getLast_n_balance() {
		return last_n_balance;
	}

	public BigDecimal getLast_f_balance() {
		return last_f_balance;
	}

	public String getPay_code(){
		return pay_code;
	}

	public void setPay_code(String pay_code){
		this.pay_code = pay_code;
	}

	public ClearAccount(String platcust, String subject, String sub_subject){
		this.platcust = platcust;
		this.subject = subject;
		this.sub_subject = sub_subject;
	}

	/**
	 * 追踪并保存所有涉及流水
	 * @param frontAccount
	 * @param waitAmt
	 */
	public void copyTrans(ClearAccount frontAccount,BigDecimal waitAmt){
		Map<Long,BigDecimal> frontRelationTrans = frontAccount.relationTrans;//前置流水，当发生债权转让的时候
		for(Map.Entry<Long,BigDecimal> entry : frontRelationTrans.entrySet()){
			if(entry.getValue().compareTo(waitAmt) > 0){
				BigDecimal remainAmt = entry.getValue().subtract(waitAmt);
				frontRelationTrans.put(entry.getKey(),remainAmt);//前置流水够减
				if(relationTrans.get(entry.getKey()) == null){
					relationTrans.put(entry.getKey(),waitAmt);//转移部分金额，流水拆分
				}else {//流水已经存在，合并
					relationTrans.put(entry.getKey(),waitAmt.add(relationTrans.get(entry.getKey())));
				}
				break;
			}else if(entry.getValue().compareTo(waitAmt) == 0){
				frontRelationTrans.remove(entry.getKey());
				if(relationTrans.get(entry.getKey()) == null){
					relationTrans.put(entry.getKey(),waitAmt);
				}else {//流水已经存在，合并
					relationTrans.put(entry.getKey(),waitAmt.add(relationTrans.get(entry.getKey())));
				}//整条流水转移到对手账户
				break;
			}else {//一条流水不够
				waitAmt = waitAmt.subtract(entry.getValue());
				frontRelationTrans.remove(entry.getKey());
				if(relationTrans.get(entry.getKey()) == null){
					relationTrans.put(entry.getKey(),waitAmt);
				}else {//流水已经存在，合并
					relationTrans.put(entry.getKey(),entry.getValue().add(relationTrans.get(entry.getKey())));
				}//整条流水转移到对手账户，继续转移下条流水
			}
		}
	}
	public String getKey(){
		return this.getPlatcust() + "#" + this.getSubject() + "#" + this.getSub_subject();
	}
	/**
	 * 冻结可用
	 * @param amt 金额
	 */
	public void freeze(BigDecimal amt) {
		n_balance = n_balance.subtract(amt);
		f_balance = f_balance.add(amt);
	}
	public void backup(){
		n_balance_bak = n_balance;
		f_balance_bak = f_balance;
	}
	public void recover(){
		n_balance = n_balance_bak;
		f_balance = f_balance_bak;
	}
	/**
	 * 解冻
	 * @param amt 金额
	 */
	public void unfreeze(BigDecimal amt) {
		n_balance = n_balance.add(amt);
		f_balance = f_balance.subtract(amt);
	}
	/**
	 * 平台才需要传 oppo_account
	 * @param amt 金额
	 */
	public void addAmt(BigDecimal amt) {
		n_balance = n_balance.add(amt);
	}
	public void subtractAmt(BigDecimal amt) {
		n_balance = n_balance.subtract(amt);
	}

	public void setZeroAmt(){
		n_balance = BigDecimal.ZERO;
	}
	
	public BigDecimal getT_balance() {
		return n_balance.add(f_balance);
	}
	public BigDecimal getN_balance() {
		return n_balance;
	}
	
	public BigDecimal getF_balance() {
		return f_balance;
	}
	
	public String getPlatcust() {
		return platcust;
	}

	public String getSubject() {
		return subject;
	}

	public String getSub_subject() {
		return sub_subject;
	}

	private int error_fail_times = 0; //sql异常导致的更新失败

	private int concurrence_fail_times = 0; //并发导致的更新失败

	private int fail_type = 0;

	private boolean isNoNeedTry = false;//是否需要重试

	public static final int ERROR_FAIL = 1;

	public static final int CONCORRENCE_FAIL = 2;

	public static final int MAX_FAIL_TIME = 2;//最大应许失败次数


	public int insErrorFailTimes(){
		fail_type = ERROR_FAIL;
		error_fail_times += 1;
		//五次更新数据都失败了，不试了（这种情况应该不会出现，万一出现了可以避免无限重试）
		if(fail_type==ERROR_FAIL
				&&error_fail_times>MAX_FAIL_TIME){
			isNoNeedTry = true;
		}
		return  error_fail_times;
	}


	/**
	 * 删除并备份账户待清算金额
	 */
	public void removeF(){
		f_balance = BigDecimal.ZERO;
	}

	/**
	 * 删除并备份账户待清算金额
	 */
	public void removeN(){
		n_balance = BigDecimal.ZERO;
	}


	/**
	 * 保留该账户上次清算失败状况，用于判定下次是否还有重试的必要
	 * @return
	 */
	public int insConcurrenceFailTimes(){
		fail_type = CONCORRENCE_FAIL;
		concurrence_fail_times += 1;
		//当遇到并发条件并且再次清算钱还没有变化的时候，这账户是不可能清算成功了，放弃清算
		if(last_n_balance.compareTo(n_balance)==0
				&&last_f_balance.compareTo(f_balance)==0){
			isNoNeedTry = true;
		}else {
			last_f_balance = f_balance;
			last_n_balance = n_balance;
		}
		return  concurrence_fail_times;
	}

	/**
	 * 判断是否还有重试的必要
	 * @return
	 */
	public boolean isNoNeedToTry(){
		return isNoNeedTry;
	}
	public int getFail_type(){
		return  fail_type;
	}
	public int getError_fail_times(){
		return error_fail_times;
	}

	public int getConcurrence_fail_times(){
		return concurrence_fail_times;
	}

	public BigDecimal getCur_n_balance() {
		return cur_n_balance;
	}

	public void setCur_n_balance(BigDecimal cur_n_balance) {
		this.cur_n_balance = cur_n_balance;
	}

	public BigDecimal getCur_f_balance() {
		return cur_f_balance;
	}

	public void setCur_f_balance(BigDecimal cur_f_balance) {
		this.cur_f_balance = cur_f_balance;
	}
	@Override
	public String toString() {
		return "{platcust:"+platcust+",subject:"+subject+",sub_subject:"+sub_subject+",n_balance:"+n_balance+",f_balance:"+f_balance+"}";
	}
	
	
}
