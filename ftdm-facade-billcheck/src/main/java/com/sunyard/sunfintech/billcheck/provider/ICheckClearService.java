package com.sunyard.sunfintech.billcheck.provider;

import com.sunyard.sunfintech.core.base.BaseResponse;

/**
 * Created by djh on 2017/6/28.
 */
public interface ICheckClearService {

	/**
     * 预清算，检查账户是否可以清算
	 * @param plat_no
     * @param clear_date
     * @return
     */
	BaseResponse checkClear(final String plat_no, final String clear_date) ;

}
