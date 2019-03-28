//package com.sunyard.sunfintech.web.account;
//
//import com.alibaba.fastjson.JSON;
//import com.alipay.api.internal.util.AlipaySignature;
//import com.sunyard.sunfintech.core.Constants;
//import com.sunyard.sunfintech.core.util.BeanUtil;
//import com.sunyard.sunfintech.core.util.CacheUtil;
//import com.sunyard.sunfintech.core.util.HttpClientUtils;
//import com.sunyard.sunfintech.core.util.MD5;
//import org.apache.commons.lang3.StringUtils;
//import org.apache.http.NameValuePair;
//import org.apache.http.client.entity.UrlEncodedFormEntity;
//import org.apache.http.message.BasicNameValuePair;
//import org.apache.http.util.EntityUtils;
//import org.junit.Test;
//import org.springframework.context.support.ClassPathXmlApplicationContext;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.data.redis.core.types.RedisClientInfo;
//
//import java.io.*;
//import java.math.BigDecimal;
//import java.net.URLDecoder;
//import java.net.URLEncoder;
//import java.security.PublicKey;
//import java.security.cert.CertificateFactory;
//import java.security.cert.X509Certificate;
//import java.text.SimpleDateFormat;
//import java.util.*;
//
///**
// * Created by terry on 2018/3/25.
// */
//public class signTest {
//
//    private static String publicKey="MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDHrMWm2Q3DIiyoBHjy3TqrlUwmCEjq9rWIEmK9A5KZzFljBZGZ/Q0wK9IyZr9Yz8/8WwaCrGYapSXW/iTiqiqJIHNQQJkHLtYqe6OcqGEBx68gXTvGIq+wZQG7vOfBb6WQYJJBg9d+hiRYs0F26IAbpktf7127OLDswrlUaYqSlQIDAQAB";
//    private static String privateKey="MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAMesxabZDcMiLKgEePLdOquVTCYISOr2tYgSYr0DkpnMWWMFkZn9DTAr0jJmv1jPz/xbBoKsZhqlJdb+JOKqKokgc1BAmQcu1ip7o5yoYQHHryBdO8Yir7BlAbu858FvpZBgkkGD136GJFizQXbogBumS1/vXbs4sOzCuVRpipKVAgMBAAECgYBSB9y7ivhobiXCcPPvcouj6hX6mfS68tfpKwR9enZD0Pv3/lWu2ZMNczNLmYKKzfU4S30FOiZIVAiTpp0osZf/O9qIoh+EaxHp0nhJyfGSsWFGr4yOwFXpP1cZbpB4GnuUD8DHJzbt2Q4Pi5/N25ErdXF94WkdnhBhvXM1sfz3kQJBAO5cs4o7Adj1NjPDUmC08H1K5axbcOGTREUTx8taRJZEnp7+Fw6agBgARUevxzXWlx/Z6chEvuXD3AgTNHA9G38CQQDWczfEP3TJGLO8Mu7xCgDBllFtQsdOh8ZAQRXt4ilqLFvMaQJB1f2OPdtEejS0iPiAPBuRj7vdpr6FvGucLCvrAkADqKyg3JMEr47rcH4dOenPIo0v/h9Xdxk/LjSM7I/M9rSwoBnegYSh2a82kH4wV2DufjvWdSexAzancRQG/QklAkAoajhlTMw2peghnLu5Q3hXA3w/CinDnAT50QpP9aJ2dvup71RUkf6iEvhC1IEgUPv8Nte/PAZcxHL9g0AIg66XAkEA6QODM0r02zzGh8OeLRdMRYQTGUWYgnbpGVXloOV92unNPkVtl0ApbKprIijy+Wbx6ochSCH8hqe9vvCponjXXg==";
//
//    public static void main(String[] args){
//        String jsonStr="{\n" +
//                "\t\"order_no\": \"201806140945010045\",\n" +
//                "\t\"trans_date\": \"20180614\",\n" +
//                "\t\"plat_no\": \"JS-BYM-BY-B-20180117\",\n" +
//                "\t\"pay_order_no\": \"201806140945010789816025\",\n" +
//                "\t\"pay_amt\": 1.0000,\n" +
//                "\t\"sign\": \"lX8SmmMFaftGmMyFOst9eDWYleTAyHEcD9h6N/fxuwjF5keqrzWajg6xQY/KOgs/qPs1U1avcwiuWkgIa5zwromHqQ8V1zeJ3MQkH65b2EhBm87A8oWty+/9B8sWplSg1/zAnlM/HizqbbnHRc4vPAlzdQn/scUmDWXi3NWMihM=\",\n" +
//                "\t\"trans_time\": \"094501\",\n" +
//                "\t\"type\": \"1\",\n" +
//                "\t\"pay_finish_date\": \"20180614\",\n" +
//                "\t\"order_status\": \"1\",\n" +
//                "\t\"host_req_serial_no\": \"201806140945010768100571763964\",\n" +
//                "\t\"order_amt\": 1.0000,\n" +
//                "\t\"pay_finish_time\": \"094535\",\n" +
//                "\t\"mall_no\": \"JS-BYM-B-20180117\",\n" +
//                "\t\"platcust\": \"201806131845440666100571063142\"\n" +
//                "}";
//        TreeMap<String,String> params = JSON.parseObject(jsonStr,TreeMap.class);
//        //获取加签或验签原文
//        String content=getSignContentFromTreeMap(params);
//        System.out.println("加签原文："+content);
//        //RSA加签
//        try{
////            String sign= AlipaySignature.rsaSign(content,privateKey,"UTF-8");
////            System.out.println("签名串："+sign);
//            //RSA验签
//            String sign=params.get("sign");
//            Boolean checkResult=AlipaySignature.rsaCheck(content,sign,publicKey,"UTF-8","RSA");
//            System.out.println("验签结果："+checkResult);
//        }catch (Exception e){
//            System.out.println("加签失败："+e.getMessage());
//        }
//    }
//
//
//    public static String getSignContentFromTreeMap(TreeMap<String, String> map) {
//        StringBuilder stringBuilder = new StringBuilder();
//        for (Map.Entry<String, String> sortedEntry : map.entrySet()) {
//            if (null != sortedEntry.getValue() && StringUtils.isNoneBlank(String.valueOf(sortedEntry.getValue())) && !"sign".equals(sortedEntry.getKey())) {
//                stringBuilder.append(String.valueOf(sortedEntry.getValue())).append("|");
//            }
//        }
//        if (stringBuilder.length() > 0) {
//            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
//        }
//        return stringBuilder.toString();
//    }
//
//    private static ArrayList<NameValuePair> covertParams2NVPS(Map<String, Object> params) {
//        ArrayList<NameValuePair> pairs = new ArrayList<NameValuePair>();
//        for (Map.Entry<String, Object> param : params.entrySet()) {
//            pairs.add(new BasicNameValuePair(param.getKey(), String.valueOf(param.getValue())));
//        }
//
//        return pairs;
//    }
//
//    @Test
//    public void getPublic(){
//        try{
//            X509Certificate x509Certificate = null;
//            CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
//            FileInputStream fileInputStream = new FileInputStream("C:\\Users\\terry\\Desktop\\平台公钥\\平台公钥\\车邦公钥.cer");
//            x509Certificate = (X509Certificate) certificateFactory.generateCertificate(fileInputStream);
////            System.out.println("读取Cer证书信息...");
////            System.out.println("x509Certificate_SerialNumber_序列号___:"+x509Certificate.getSerialNumber());
////            System.out.println("x509Certificate_getIssuerDN_发布方标识名___:"+x509Certificate.getIssuerDN());
////            System.out.println("x509Certificate_getSubjectDN_主体标识___:"+x509Certificate.getSubjectDN());
////            System.out.println("x509Certificate_getSigAlgOID_证书算法OID字符串___:"+x509Certificate.getSigAlgOID());
////            System.out.println("x509Certificate_getNotBefore_证书有效期___:"+x509Certificate.getNotAfter());
////            System.out.println("x509Certificate_getSigAlgName_签名算法___:"+x509Certificate.getSigAlgName());
////            System.out.println("x509Certificate_getVersion_版本号___:"+x509Certificate.getVersion());
////            System.out.println("x509Certificate_getPublicKey_公钥___:"+x509Certificate.getPublicKey());
////            PublicKey publicKey=AlipaySignature.getPublicKeyFromX509("RSA",fileInputStream);
////            System.out.println(JSON.toJSONString(publicKey));
//            fileInputStream.close();
//        }catch (Exception e){
//            System.out.println("error"+e.getMessage());
//        }
//    }
//
//    @Test
//    public void testReadX509CerFile() throws Exception{
//        try {
//            // 读取证书文件
//
//            File file = new File("C:\\Users\\terry\\Desktop\\平台公钥\\平台公钥\\车邦公钥.cer");
//            InputStream inStream = new FileInputStream(file);
//            // 创建X509工厂类
//            CertificateFactory cf = CertificateFactory.getInstance("X.509");
//            //CertificateFactory cf = CertificateFactory.getInstance("X509");
//            // 创建证书对象
//            X509Certificate oCert = (X509Certificate) cf
//                    .generateCertificate(inStream);
//            inStream.close();
//            SimpleDateFormat dateformat = new SimpleDateFormat("yyyy/MM/dd");
//            String info = null;
//            // 获得证书版本
//            info = String.valueOf(oCert.getVersion());
//            System.out.println("证书版本:" + info);
//            // 获得证书序列号
//            info = oCert.getSerialNumber().toString(16);
//            System.out.println("证书序列号:" + info);
//            // 获得证书有效期
//            Date beforedate = oCert.getNotBefore();
//            info = dateformat.format(beforedate);
//            System.out.println("证书生效日期:" + info);
//            Date afterdate = oCert.getNotAfter();
//            info = dateformat.format(afterdate);
//            System.out.println("证书失效日期:" + info);
//            // 获得证书主体信息
//            info = oCert.getSubjectDN().getName();
//            System.out.println("证书拥有者:" + info);
//            // 获得证书颁发者信息
//            info = oCert.getIssuerDN().getName();
//            System.out.println("证书颁发者:" + info);
//            // 获得证书签名算法名称
//            info = oCert.getSigAlgName();
//            System.out.println("证书签名算法:" + info);
//            System.out.println(oCert.getPublicKey().getAlgorithm());
//
//        } catch (Exception e) {
//            System.out.println("解析证书出错！");
//            e.printStackTrace();
//        }
//    }
//
//    @Test
//    public void getToken(){
//        String token_origin_str = Constants.TOKEN_STATIC_STR + "201804040000001";
//        String token_str = MD5.MD5Encode(token_origin_str, Constants.CHARACTERENCODING);
//        System.out.println("token_str:"+token_str);
//    }
//
//    @Test
//    public void calc(){
//        BigDecimal all_amt=BigDecimal.valueOf(7699);
//        BigDecimal real_amt=BigDecimal.ZERO;
//        for(int i=0;i<12;i++){
//            BigDecimal now_amt=all_amt.subtract(BigDecimal.valueOf(642));
//            real_amt= real_amt.add(now_amt.multiply(BigDecimal.valueOf(0.11 * 30 / 365)));
//            all_amt=all_amt.subtract(BigDecimal.valueOf(642));
//        }
//        System.out.println(real_amt);
//    }
//}
