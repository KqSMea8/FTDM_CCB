package com.sunyard.sunfintech.web.security;

import com.sunyard.sunfintech.core.exception.BusinessMsg;
import com.sunyard.sunfintech.core.support.security.AuthorizationAdapter;
import com.sunyard.sunfintech.core.util.SpringContextHolder;
import com.sunyard.sunfintech.user.model.bo.UserAuthItems;
import com.sunyard.sunfintech.user.provider.IUserAuthService;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.collections.map.HashedMap;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author heroy
 * @version 2018/1/17
 */
@Service("BOBCheckPassword")
public class BOBCheckPassword implements AuthorizationAdapter {

    private static Logger logger = LogManager.getLogger(AuthorizationAdapter.class);

    @Autowired
    private IUserAuthService userAuthService;

    public BOBCheckPassword(){
        super();
    }

    public BOBCheckPassword(IUserAuthService userAuthService){
        super();
        this.userAuthService = userAuthService;
    }
    @Override
    public Map<String, Object> checkPassword(Map<String, Object> params) {
        UserAuthItems userAuthItems = new UserAuthItems();
        userAuthItems.setMer_no(MapUtils.getString(params,"mer_no"));
        userAuthItems.setMall_no(MapUtils.getString(params,"mall_no"));
        userAuthItems.setPasswod(MapUtils.getString(params,"trans_pwd"));
        userAuthItems.setRandom_key(MapUtils.getString(params, "random_key"));
        userAuthItems.setPlatcust(MapUtils.getString(params, "platcust"));
        userAuthItems.setPassword_type(MapUtils.getString(params,"password_type"));
        userAuthItems.setTrans_flag(MapUtils.getString(params,"flag"));
        if(userAuthService == null){
            userAuthService = SpringContextHolder.getBean("userAuthService");
            if(null == userAuthService){
                logger.error("【密码验证】失败：SpringContextHolder无法获取验密服务！");
                return new HashedMap(){
                    {
                        put("recode", BusinessMsg.AUTH_ERROR);
                        put("remsg",BusinessMsg.getMsg(BusinessMsg.AUTH_ERROR) + ": 无法获取验密服务");
                    }
                };
            }
        }
        return userAuthService.checkPassowrd(userAuthItems);
    }
}
