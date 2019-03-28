package com.sunyard.sunfintech.web.controller;

import com.sunyard.sunfintech.account.model.bo.RamdomKeyRequest;
import com.sunyard.sunfintech.account.model.bo.RamdomKeyResponse;
import com.sunyard.sunfintech.core.annotation.Sign;
import com.sunyard.sunfintech.core.base.BaseController;
import com.sunyard.sunfintech.web.business.AuthBusiness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 权限系统对外web接口
 *
 * @author heroy
 * @version 20180122
 */
@RestController
@RequestMapping("/auth")
public class AuthController extends BaseController {

	@Autowired
	private AuthBusiness authBusiness;

	/**
	 * 获取密码因子
	 *
	 * @param httpServletRequest request请求参数
	 * @param request   请求参数
	 * @return RamdomKeyResponse 密码因子
	 * @author yanglei
	 */
	@RequestMapping("/get_randomkey")
	@Sign
	//@Log(method = "getRandom_key", transCode = "AT000001")
	public RamdomKeyResponse getRandomKey(HttpServletRequest httpServletRequest, RamdomKeyRequest request) {
		logger.info("请求参数:" + request);
		validate(request);
		RamdomKeyResponse ramdomKeyResponse = authBusiness.getRandomKey(request);
		return ramdomKeyResponse;
	}
}
