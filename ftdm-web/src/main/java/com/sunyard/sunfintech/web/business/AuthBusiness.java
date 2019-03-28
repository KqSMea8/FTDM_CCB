package com.sunyard.sunfintech.web.business;

import com.sunyard.sunfintech.account.model.bo.RamdomKeyRequest;
import com.sunyard.sunfintech.account.model.bo.RamdomKeyResponse;
import com.sunyard.sunfintech.core.exception.BusinessMsg;
import com.sunyard.sunfintech.core.util.StringUtils;
import com.sunyard.sunfintech.core.util.URLConfigUtil;
import com.sunyard.sunfintech.thirdparty.provider.IAdapterService;
import org.apache.commons.collections.MapUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author heroy
 * @version 2017/4/6
 */
@Service("authBusiness")
public class AuthBusiness {
    private final Logger logger = LogManager.getLogger(this.getClass());

    @Autowired
    private IAdapterService adapterService;

	@Autowired
	private SysBusiness sysParameterService;

	public RamdomKeyResponse getRandomKey(RamdomKeyRequest baseRequest){
		String host = sysParameterService.querySysParameter(baseRequest.getMall_no(), URLConfigUtil.EPAY_SERVER_KEY);
		String url = sysParameterService.querySysParameter(baseRequest.getMall_no(), URLConfigUtil.ICIS_RANDOMGET);
		RamdomKeyResponse ramdomKeyResponse = new RamdomKeyResponse();
		if(StringUtils.isBlank(host) || StringUtils.isBlank(url)) {
			ramdomKeyResponse.setRecode(BusinessMsg.FAIL);
			ramdomKeyResponse.setRemsg("【无法获取到系统参数】：第三方地址");
			return ramdomKeyResponse;
		}
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("host", host);
		param.put("url", url);
		param.put("mall_no", baseRequest.getMall_no());
		param.put("mer_no", baseRequest.getMer_no());
		param.put("password_type", baseRequest.getPassword_type());
		Map<String, Object> result = adapterService.getRandomKey(param);
		ramdomKeyResponse.setRecode(MapUtils.getString(result,"recode"));
		ramdomKeyResponse.setRemsg(MapUtils.getString(result,"remsg"));
		ramdomKeyResponse.setRandom_key(MapUtils.getString(result,"random_key"));
		ramdomKeyResponse.setRandom_value(MapUtils.getString(result,"random_value", ""));

		return ramdomKeyResponse;
	}
}
