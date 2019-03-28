package com.sunyard.sunfintech.user.provider;

import com.sunyard.sunfintech.core.base.BaseResponse;
import com.sunyard.sunfintech.core.exception.BusinessException;
import com.sunyard.sunfintech.dao.entity.EaccUserauth;
import com.sunyard.sunfintech.user.model.bo.AuthPayFeeRequest;
import com.sunyard.sunfintech.user.model.bo.AuthPayFeeResponse;
import com.sunyard.sunfintech.user.model.bo.CancelPayFeeRequest;
import com.sunyard.sunfintech.user.model.bo.PayFeeRequest;

import java.util.List;

/**
 * 【功能描述】
 *
 * @author wyc  2018/3/11.
 */
public interface IUserTransferService {

    /**
     * 批量更新授权列表
     * @param eaccUserauths
     */
    void updateEaccUserAuth(List<EaccUserauth> eaccUserauths);
    /**
     * 缴费
     *
     * @param payFeeRequest
     * @return
     */
    BaseResponse payFee(PayFeeRequest payFeeRequest) throws BusinessException;

    /**
     * 授权缴费
     *
     * @param confirmAuthOperaRequest
     * @return
     */
    AuthPayFeeResponse authPayFee(AuthPayFeeRequest confirmAuthOperaRequest)  throws BusinessException;

    /**
     * 取消缴费
     *
     * @param cancelAuthOperaRequest
     * @return
     */
    BaseResponse cancelPayFee(CancelPayFeeRequest cancelAuthOperaRequest)  throws BusinessException;
}
