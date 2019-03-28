package com.sunyard.sunfintech.core.support.security.ccbSecurity;

import com.sunyard.sunfintech.core.util.MD5;

/**
 * Created by dingjy on 2017/7/13.
 */
public class SecurityKeyUtil {

    public static final String ALGORITHM = "DESede";

    public static String getPrivateKey() {
        return DESede.DESedeKey();
    }

    /**
     * 签名串加密，不可逆，先用MD5加密后用3des加密
     * @param pass 需要机密的字符串
     * @param KEY 加密密钥
     * @return 加密后的字符串
     */
    public static String getSignString(String pass, String KEY){
        //先把字符串用MD5加密
        String src = MD5.MD5Encode(pass, "UTF-8");
        //然后再用3des加密
        src = DESede.DESedeEncode(src, KEY);
        return src;
    }

    public static void main(String[] args){
        String KEY = getPrivateKey();
        System.out.println("KEY: " + KEY);
        String src = "sunyard";

        System.out.println("getSignString: " + getSignString(src, KEY));
        System.out.println("getSignString: " + getSignString("Q", KEY));
    }

}