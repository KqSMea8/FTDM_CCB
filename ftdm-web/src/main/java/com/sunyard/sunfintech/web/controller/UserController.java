package com.sunyard.sunfintech.web.controller;

import com.sunyard.sunfintech.core.annotation.CheckPassword;
import com.sunyard.sunfintech.core.annotation.Log;
import com.sunyard.sunfintech.core.annotation.Sign;
import com.sunyard.sunfintech.core.base.BaseResponse;
import com.sunyard.sunfintech.core.exception.BusinessException;
import com.sunyard.sunfintech.user.model.bo.AuthPayFeeRequest;
import com.sunyard.sunfintech.user.model.bo.AuthPayFeeResponse;
import com.sunyard.sunfintech.user.model.bo.CancelPayFeeRequest;
import com.sunyard.sunfintech.user.model.bo.PayFeeRequest;
import com.sunyard.sunfintech.web.business.UserBusiness;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 【功能描述】
 *
 * @author wyc  2018/2/7.
 */

@RestController
@RequestMapping("/user")
public class UserController {

    @Resource(name = "userBusiness")
    private UserBusiness userBusiness;
    /**
     * 缴费
     *
     * @param payFeeRequest
     * @return
     */

    @RequestMapping("/pay_fee")
    @Sign(checkPassword= CheckPassword.YES)
    @Log(method = "payFee")
    public BaseResponse payFee(HttpServletRequest httpServletRequest, PayFeeRequest payFeeRequest) throws BusinessException {
        return userBusiness.payFee(payFeeRequest);
    }

    /**
     * 授权缴费
     *
     * @param authPayFeeRequest
     * @return
     */

    @RequestMapping("/auth_pay_fee")
    @Sign
    @Log(method = "authPayFee",batchDataName = "data")
    public AuthPayFeeResponse authPayFee(HttpServletRequest httpServletRequest, AuthPayFeeRequest authPayFeeRequest)  throws BusinessException{
        return userBusiness.authPayFee(authPayFeeRequest);
    }

    /**
     * 取消缴费
     *
     * @param cancelPayFeeRequest
     * @return
     */

    @RequestMapping("/cancel_pay_fee")
    @Sign
    @Log(method = "cancelPayFee")
    public	BaseResponse cancelPayFee(HttpServletRequest httpServletRequest,CancelPayFeeRequest cancelPayFeeRequest)  throws BusinessException{
        return userBusiness.cancelPayFee(cancelPayFeeRequest);
    }
}
