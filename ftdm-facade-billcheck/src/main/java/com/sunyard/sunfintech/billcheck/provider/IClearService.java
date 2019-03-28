package com.sunyard.sunfintech.billcheck.provider;

import com.sunyard.sunfintech.core.base.BaseResponse;

/**
 * Created by djh on 2017/6/28.
 */
public interface IClearService {

	BaseResponse doClear(final String plat_no, final String clear_date) ;


	BaseResponse clearAll(final String plat_no) ;


	/**
	 * 手动生成清算文件
	 * @param plat_no
	 * @param clear_date 格式yyyy-MM-dd
	 * @param pay_code
	 * @return
	 */
	BaseResponse createClearFileInside(final String plat_no, final String clear_date, final String pay_code);

	/**
	 * 生成清算结束ready文件
	 * @param mall_no
	 * @param clear_date
	 */
	void getReadyFile(String mall_no, String clear_date);
}
