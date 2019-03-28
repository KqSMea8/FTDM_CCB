package com.sunyard.sunfintech.user.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.sunyard.sunfintech.core.base.BaseServiceSimple;
import com.sunyard.sunfintech.pub.provider.ISysParameterService;
import com.sunyard.sunfintech.thirdparty.provider.IAdapterService;
import com.sunyard.sunfintech.user.model.bo.UserAuthItems;
import com.sunyard.sunfintech.user.provider.IUserAuthService;
import com.sunyard.sunfintech.core.util.URLConfigUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

/**
 * @author heroy
 * @version 2018/1/9
 */
@Service(interfaceClass = IUserAuthService.class)
@org.springframework.stereotype.Service("userAuthService")
public class UserAuthService extends BaseServiceSimple implements IUserAuthService {

    @Autowired
    private IAdapterService adapterService;

    @Autowired
    private ISysParameterService sysParameterService;

    /**
     * 获取因子
     * @param mall_no 集团号
     * @param mer_no 平台号
     * @return
     */
    @Override
    public Map<String, Object> getRandomKey(String mall_no, String mer_no, String password_type) {
        Map<String, Object> result = adapterService.getRandomKey(new HashMap<String, Object>(){
            {
                put("mall_no",mall_no);
                put("mer_no",mer_no);
                put("password_type",password_type);
            }
        });
        return result;
    }

    @Override
    public Map<String, Object> getRandomKey(UserAuthItems userAuthItems) {
        Map<String, Object> result = adapterService.getRandomKey(new HashMap<String, Object>(){
            {
                put("mall_no",userAuthItems.getMall_no());
                put("mer_no",userAuthItems.getMer_no());
                put("password_type",userAuthItems.getPassword_type());
            }
        });
        return result;
    }

    /**
     * 密码验证
     * @param userAuthItems
     * @return
     */
    @Override
    public Map<String, Object> checkPassowrd(UserAuthItems userAuthItems) {
        Map<String, Object> result = adapterService.checkPassowrd(new HashMap<String, Object>(){
            {
                put("mall_no",userAuthItems.getMall_no());
                put("mer_no",userAuthItems.getMer_no());
                put("plat_cust",userAuthItems.getPlatcust());
                put("id_kind",userAuthItems.getId_kind());
                put("id_no",userAuthItems.getId_no());
                put("password",userAuthItems.getPasswod());
                put("random_key",userAuthItems.getRandom_key());
                put("type",userAuthItems.getPassword_type());
                put("flag",userAuthItems.getTrans_flag());
                put("host",sysParameterService.querySysParameter(userAuthItems.getMall_no(), URLConfigUtil.EPAY_SERVER_KEY));
                put("url",sysParameterService.querySysParameter(userAuthItems.getMall_no(),URLConfigUtil.ICIS_PASSWORDCHECK));
            }
        });
        return result;
    }

    /**
     * 修改或者重置密码
     * @param userAuthItems
     * @return responseMap
     */
    @Override
    public Map<String, Object> setOrResetPassword(UserAuthItems userAuthItems) {
        Map<String, Object> result = adapterService.setOrResetPassword(new HashMap<String, Object>(){
            {
                put("mall_no",userAuthItems.getMall_no());
                put("mer_no",userAuthItems.getMer_no());
                put("plat_cust",userAuthItems.getPlatcust());
                put("id_kind",userAuthItems.getId_kind());
                put("id_no",userAuthItems.getId_no());
                put("tran_flag",userAuthItems.getTrans_flag());
                put("ori_random_key",userAuthItems.getOri_random_key());
                put("ori_password",userAuthItems.getOri_password());
                put("random_key",userAuthItems.getRandom_key());
                put("password",userAuthItems.getPasswod());
                put("host",sysParameterService.querySysParameter(userAuthItems.getMall_no(),URLConfigUtil.EPAY_SERVER_KEY));
                put("url",sysParameterService.querySysParameter(userAuthItems.getMall_no(),URLConfigUtil.ICIS_PASSWORDMODIFY));
            }
        });
        return result;
    }

    /**
     * 验签
     * @param origText 原文
     * @param sign 签名
     * @return 是否成功
     */
    @Override
    public boolean checkSign(String origText, String sign) {
        return false;
    }

    /**
     * 加签
     * @param origText 原文
     * @return 签名
     */
    @Override
    public String signUp(String origText) {
        return "";
    }
}
