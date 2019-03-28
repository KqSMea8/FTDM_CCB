package com.sunyard.sunfintech.user.provider;

import com.sunyard.sunfintech.user.model.bo.UserAuthItems;

import java.util.Map;

/**
 * 权限管理服务
 * @author heroy
 * @version 2018/1/9
 */
public interface IUserAuthService {
    /** 获取随机因子 */
    public Map<String, Object> getRandomKey (String mall_no, String mer_no, String password_type);

    public Map<String, Object> getRandomKey(UserAuthItems userAuthItems);

    /** 验证密码 */
    public Map<String, Object> checkPassowrd(UserAuthItems userAuthItems);

    /** 修改或者重置密码 */
    public Map<String, Object> setOrResetPassword(UserAuthItems userAuthItems);

    /** 验签 */
    public boolean checkSign(String origText, String sign);

    /** 加签 */
    public String signUp(String origText);

}
