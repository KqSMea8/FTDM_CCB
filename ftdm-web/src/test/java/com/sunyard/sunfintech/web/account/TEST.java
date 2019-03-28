//package com.sunyard.sunfintech.web.account;
//
//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.JSONArray;
//import com.alibaba.fastjson.JSONObject;
//import ocx.SMUtil;
//import org.apache.commons.codec.DecoderException;
//import org.junit.Test;
//
//import java.io.*;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
///**
// * 晋商银行定制密码控件解密示例
// * shuc
// * 2018-3-20 10:00:30
// */
//public class TEST {
//    /** 解密私钥 */
//    private final static String PRIVATE_KEY
//            = "db401b5f846491cc0887d9e0bb6343b8d92e88e232415c3b93a606a53439fe07";
//    /** 密码控件端，1-h5，2-pc，3-iOS，4-Android */
//    private final static int SOURCE_H5 = 1;
//    private final static int SOURCE_PC = 2;
//    private final static int SOURCE_IOS =3;
//    private final static int SOURCE_ANDROID = 4;
//
//    public static void main(String[] args) throws DecoderException {
//        String pass = "nzLHHS/35qnI9wNjtb6RJnp5Lsqha9CxOmfYXZDMlDOqf4tGQfQI7aiqwqCFLaEEbQ1A9RUmRMuRSekg0qaJ0Dai7vnCZ+I6vqdigOnyDcv3etbXUh3i29P+x4vjNMHHTpNKPvzIfQWrgjiw3gs3T+mld9nyRNMZvXV407S0iWO5H9XY7EbiSPTsatVgkVN5sQIl/BEhUuYBUmPzlyNldMlp8WP1mdCu+0XPPSeCnsgWq5kgFEQWgOCE1UvJtoU7qBJ0IE1w3G4BNQTCMrDHRg==";
//
//        String randomKey = "q7s1f6r82plojxupfesbneunmyypn37a";
//        int source = 2;
//
//        String passdata = toDecrypt(pass, randomKey, source);
//        System.out.println("解密后密码：" + passdata);
//    }
//
//    /**
//     * 解密方法
//     * @param pass 密文密码
//     * @param randomKey  随机因子
//     * @param source 密码客户端
//     * @return 明文密码
//     * @throws DecoderException
//     */
//    public static String toDecrypt(String pass, String randomKey, int source) throws DecoderException {
//        try {
//            String passSm;
//            // SM4解密，H5使用decryptSM4Two方法，其他使用decryptSM4方法
//            if (source == SOURCE_H5) {
//                passSm = SMUtil.decryptSM4Two(randomKey, pass);
//            } else {
//                passSm = SMUtil.decryptSM4(randomKey, pass);
//            }
//            // SM2解密，iOS不需要拼接04，其他需要
//            if (source != SOURCE_IOS) {
//                passSm = "04" + passSm;
//            }
//            //解密SM2。PRIVATE_KEY:SM2私钥，passSm:SM2密文，0:表示密文为16进制格式。
//            return SMUtil.decryptSM2(PRIVATE_KEY, passSm, 0);
//        } catch (DecoderException e) {
//            e.printStackTrace();
//        }
//        return "";
//    }
//
//    @Test
//    public void testMap(){
//        Map<String,String> map=new HashMap<>();
//        map.put("1","1");
//        map.put("2","2");
//        map.put("3","3");
//        map.remove("2");
//        System.out.println(JSON.toJSONString(map));
//    }
//
//    @Test
//    public void jsonToSql(){
//        String[] fileNameArray=new String[]{"2018.3~7懒人投资出.txt","2018.3~7懒人投资入.txt","2018.3~7小小理财出.txt","2018.3~7小小理财入.txt","2018.6~7百亿猫出.txt","2018.6~7百亿猫入.txt"};
//        for(String fileName:fileNameArray){
//            String saveFileName="C:\\Users\\terry\\Desktop\\新建文件夹\\新建文件夹\\";
//            String json=txt2String(new File("C:\\Users\\terry\\Desktop\\新建文件夹\\新建文件夹\\"+fileName));
//            JSONArray jsonArray=JSON.parseArray(json);
//            StringBuilder saveString=new StringBuilder();
//            for(Object jo:jsonArray){
//                Map<String,Object> jsonMap= (Map<String, Object>) jo;
//                String table_name=fileName;
//                table_name=table_name.substring(0,table_name.lastIndexOf("."));
//                table_name=table_name.replace("~","").
//                        replace(".","").
//                        replace("/","").
//                        replace("\\","").
//                        replace("\"","").
//                        replace("'","").
//                        replace(",","").
//                        replace("(","").
//                        replace(")","");
//
////                if(fileName.contains("入金")){
////                    table_name="zr";
////                }
//                StringBuilder sqlKey=new StringBuilder("insert into "+table_name+"(");
//                StringBuilder sqlValue=new StringBuilder(" values(");
//                for(String key:jsonMap.keySet()){
//                    sqlKey.append(key).append(",");
//                    sqlValue.append("\"").append(jsonMap.get(key)).append("\"").append(",");
//                }
//                saveString.append(sqlKey.substring(0, sqlKey.length() - 1)).append(")").append(sqlValue.substring(0, sqlValue.length() - 1)).append(");\r\n");
//            }
//            appendMethodB(saveFileName+fileName.substring(0,fileName.lastIndexOf("."))+".sql",saveString.toString());
//        }
//
//    }
//
//    public static String txt2String(File file){
//        StringBuilder result = new StringBuilder();
//        try{
//            InputStreamReader reader = new InputStreamReader(
//                    new FileInputStream(file),"GBK"); // 建立一个输入流对象reader
//            BufferedReader br = new BufferedReader(reader); // 建立一个对象，它把文件内容转成计算机能读懂的语言
//            String line = "";
//            line = br.readLine();
//            while (line != null) {
//                result.append(line);
//                line = br.readLine(); // 一次读入一行数据
//            }
//        }catch(Exception e){
//            e.printStackTrace();
//        }
//        return result.toString();
//    }
//
//    public static void appendMethodB(String fileName, String content) {
//        try {
//            //打开一个写文件器，构造函数中的第二个参数true表示以追加形式写文件
//            FileWriter writer = new FileWriter(fileName, true);
//            writer.write(content);
//            writer.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//}
