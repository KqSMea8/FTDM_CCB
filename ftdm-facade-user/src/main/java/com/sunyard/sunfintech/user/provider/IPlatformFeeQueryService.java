package com.sunyard.sunfintech.user.provider;

import com.sunyard.sunfintech.core.exception.BusinessException;
import com.sunyard.sunfintech.dao.entity.EaccAccountamtlist;
import com.sunyard.sunfintech.user.model.bo.SubstitutePayFeeData;
import com.sunyard.sunfintech.user.model.bo.SubstitutePayFeeRequest;

import java.util.List;

/**
 * Created by terry on 2017/5/23.
 */
public interface IPlatformFeeQueryService {

    /**
     * 自有资金账户手续费扣款查询
     * @param substitutePayFeeRequest
     * @return
     * @throws BusinessException
     */
    public List<SubstitutePayFeeData> substitutePayFeeQuery(SubstitutePayFeeRequest substitutePayFeeRequest)throws BusinessException;

//    /**
//     * 查询特定账户流水单据
//     * @param platcust
//     * @param startDate
//     * @param endDate
//     * @return
//     * @throws BusinessException
//     */
//    public List<EaccAccountamtlist> accountFlowListQuery(String platcust, String startDate, String endDate)throws BusinessException;

}
