package com.sunyard.sunfintech.account.provider;


import com.sunyard.sunfintech.dao.entity.EaccAccountamtlist;

import java.util.List;

/**
 * 账户加款，账户扣款 
 * @author heroy
 * @version 2017/4/12
 */
public interface IAccountAssetService {
	
    /**
     * 账户加款
     * 
     * @param eaccAccountamtlist 
     * @return Boolean   成功true   失败false
     */
    public Boolean charge(EaccAccountamtlist eaccAccountamtlist);

    /**
     * 批量加款
     * @param eaccAccountamtlist
     * @return
     */
    public boolean batchCharge(List<EaccAccountamtlist> eaccAccountamtlist);

    /**
     * 账户扣款 
     * 
     * @param eaccAccountamtlist
     * @return Boolean   成功true   失败false
     */
    public Boolean withdraw(EaccAccountamtlist eaccAccountamtlist);


    /**
     * 扣款  针对扣负款
     * @param eaccAccountamtlist
     * @return Boolean   成功true   失败false
     */
    public Boolean negativeWithdraw(EaccAccountamtlist eaccAccountamtlist);
}
