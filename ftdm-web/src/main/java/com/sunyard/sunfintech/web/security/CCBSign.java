package com.sunyard.sunfintech.web.security;

import com.alipay.api.internal.util.AlipaySignature;
import com.sunyard.sunfintech.core.support.security.SignAdapter;
import com.sunyard.sunfintech.core.util.StringUtils;
import com.sunyard.sunfintech.web.business.SysBusiness;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.util.Map;

/**
 * 其它银行验签
 * @author heroy
 * @version 2017/6/16
 */
@Service("CCBSign")
public class CCBSign implements SignAdapter {

    private final Logger logger = LogManager.getLogger();

    @Autowired
    private SysBusiness sysBusiness;

    @Override
    public String signUp(String mall_no,String destContent, Map<String,String> params) {
        try {
            //初始化DSVS环境，需要先进行配置文件的配置，并放在指定目录下，注意必须是BJCAROOT这个文件夹
            if(StringUtils.isEmpty(mall_no)){
                mall_no = "FTDM";
            }
            logger.info("加签集团号:" + mall_no);
            String privateKey = sysBusiness.querySysParameter(mall_no,"private_key");
            logger.info("加签原文："+destContent+"，私钥："+privateKey);
            String signStr=AlipaySignature.rsaSign(destContent,privateKey,"UTF-8");
            logger.info("加签串："+signStr);

//            return URLEncoder.encode(signStr);
            return signStr;
        } catch (Exception e) {
            logger.error("加签失败：",e);
        }
        return "sign";

    }

    @Override
    public boolean checkSign(String mall_no, String signData, String destContent, Map<String,String> params) {
        if(StringUtils.isEmpty(mall_no)){
            mall_no = "FTDM";
        }
        logger.info("验签集团号:" + mall_no);
        String publicKey = sysBusiness.querySysParameter(mall_no,"public_key");
        try{
            logger.info("验签原文："+destContent+"，签名："+signData+"，公钥："+publicKey);
            Boolean checkResult=AlipaySignature.rsaCheck(destContent,signData,publicKey,"UTF-8","RSA");
            logger.info("验签结果："+checkResult);
            return true;
        }catch (Exception e){
            logger.error("验签失败：",e);
        }
        return false;
    }

    public static void main(String[] args){
        try {
            //初始化DSVS环境，需要先进行配置文件的配置，并放在指定目录下，注意必须是BJCAROOT这个文件夹
            String privateKey = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAMesxabZDcMiLKgEePLdOquVTCYISOr2tYgSYr0DkpnMWWMFkZn9DTAr0jJmv1jPz/xbBoKsZhqlJdb+JOKqKokgc1BAmQcu1ip7o5yoYQHHryBdO8Yir7BlAbu858FvpZBgkkGD136GJFizQXbogBumS1/vXbs4sOzCuVRpipKVAgMBAAECgYBSB9y7ivhobiXCcPPvcouj6hX6mfS68tfpKwR9enZD0Pv3/lWu2ZMNczNLmYKKzfU4S30FOiZIVAiTpp0osZf/O9qIoh+EaxHp0nhJyfGSsWFGr4yOwFXpP1cZbpB4GnuUD8DHJzbt2Q4Pi5/N25ErdXF94WkdnhBhvXM1sfz3kQJBAO5cs4o7Adj1NjPDUmC08H1K5axbcOGTREUTx8taRJZEnp7+Fw6agBgARUevxzXWlx/Z6chEvuXD3AgTNHA9G38CQQDWczfEP3TJGLO8Mu7xCgDBllFtQsdOh8ZAQRXt4ilqLFvMaQJB1f2OPdtEejS0iPiAPBuRj7vdpr6FvGucLCvrAkADqKyg3JMEr47rcH4dOenPIo0v/h9Xdxk/LjSM7I/M9rSwoBnegYSh2a82kH4wV2DufjvWdSexAzancRQG/QklAkAoajhlTMw2peghnLu5Q3hXA3w/CinDnAT50QpP9aJ2dvup71RUkf6iEvhC1IEgUPv8Nte/PAZcxHL9g0AIg66XAkEA6QODM0r02zzGh8OeLRdMRYQTGUWYgnbpGVXloOV92unNPkVtl0ApbKprIijy+Wbx6ochSCH8hqe9vvCponjXXg==";
            String publicKey="MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDHrMWm2Q3DIiyoBHjy3TqrlUwmCEjq9rWIEmK9A5KZzFljBZGZ/Q0wK9IyZr9Yz8/8WwaCrGYapSXW/iTiqiqJIHNQQJkHLtYqe6OcqGEBx68gXTvGIq+wZQG7vOfBb6WQYJJBg9d+hiRYs0F26IAbpktf7127OLDswrlUaYqSlQIDAQAB";
            String destContent="[{\"base_serial_order_no\":\"258303812113072128\",\"detail_no\":\"258303812113072128\",\"error_info\":\"数据库操作异常\",\"error_no\":\"30002\"}]|20180325131947|处理成功|258303812117266432|1|JS-QCM-C-20180117|10000|处理成功|0|1|20180325";
            // 对原文进行数字签名
            System.out.println("加签原文："+destContent);
            String rsaSignStr=AlipaySignature.rsaSign(destContent,privateKey,"UTF-8");
            System.out.println("加签串："+URLEncoder.encode(rsaSignStr));
            Boolean checkResult=AlipaySignature.rsaCheck(destContent,rsaSignStr,publicKey,"UTF-8","RSA");
            System.out.println("验签结果："+checkResult);
        } catch (Exception e) {
            System.out.println("加签："+e.getMessage());
        }
    }


}
