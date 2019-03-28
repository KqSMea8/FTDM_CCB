package com.sunyard.sunfintech.user.provider;

import com.sunyard.sunfintech.core.exception.BusinessException;
import com.sunyard.sunfintech.user.model.bo.*;

/**
 * 标的相关信息查询
 * @author Lid
 *
 */
public interface IProdRISearchService {
	/**
	 * 还款明细查询
	 * @author Lid
	 * @param repayDetailRequest
	 * @return datas 
	 */
	public RepayDetailResponse queryRepayDetail(RepayDetailRequest repayDetailRequest)throws BusinessException;

}