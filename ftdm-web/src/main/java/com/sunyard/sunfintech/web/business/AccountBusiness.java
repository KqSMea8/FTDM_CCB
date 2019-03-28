package com.sunyard.sunfintech.web.business;

import com.sunyard.sunfintech.core.exception.BusinessException;
import com.sunyard.sunfintech.user.model.bo.SwitchAccountRequestBo;
import com.sunyard.sunfintech.user.model.bo.TransferEaccountRequest;
import com.sunyard.sunfintech.user.provider.IUserAccountService;
import com.sunyard.sunfintech.user.provider.IUserBindCardService;
import com.sunyard.sunfintech.web.model.vo.account.SwitchAccountRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author heroy
 * @version 2017/4/6
 */
@Service("accountBusiness")
public class AccountBusiness {
    private final Logger logger = LogManager.getLogger(this.getClass());

	@Autowired
	private IUserAccountService userAccountService;

	@Autowired
	private IUserBindCardService userBindCardService;

//	public boolean switchAccount(SwitchAccountRequest switchAccountRequest)throws BusinessException{
//		SwitchAccountRequestBo switchAccountRequestBo=new SwitchAccountRequestBo();
//		BeanUtils.copyProperties(switchAccountRequest,switchAccountRequestBo);
//		userAccountService.switchAccount(switchAccountRequestBo);
//		return true;
//	}

	public boolean transferEaccount(TransferEaccountRequest transferEaccountRequest)throws BusinessException{
		userAccountService.transferEaccount(transferEaccountRequest);
		return true;
	}


}
