package com.sunyard.sunfintech.core.support.security;

import java.util.Map;

/**
 * 验证签名
 * @author heroy
 * @version 2017/6/16
 */
public interface SignAdapter {

    /**
     * 加签
     * @param destContent 需要加签的字符串
     * @return 加签后的字符串
     */
    public String signUp(String mall_no, String destContent, Map<String,String> params);

    /**
     * 验签
     * @param signData 签名
     * @param destContent 需验签内容
     * @return 是否正确
     */
    public boolean checkSign(String mall_no, String signData, String destContent, Map<String,String> params);

}
