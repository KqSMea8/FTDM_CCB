//package com.sunyard.sunfintech.billcheck.service;
//
//import com.sunyard.sunfintech.billcheck.model.ClearModel;
//import com.sunyard.sunfintech.dao.entity.PlatCardinfo;
//import org.apache.commons.io.FileUtils;
//import org.junit.Test;
//
//import java.io.File;
//import java.io.IOException;
//import java.math.BigDecimal;
//import java.util.Map;
//
///**
// * Created by djh on 2017/12/15.
// */
//public class CreateClearFileTest {
//
//    @Test
//    public void createClearFile() {
//        ClearModel clearModel = new ClearModel("Plat_Test","2017-12-12",null);
//        PlatCardinfo platCardinfo = new PlatCardinfo();//custPlatCardinfoMapper.getByUnionKey(clearModel.getPlat_no(), card_type);
//        platCardinfo.setCard_name("test");
//        platCardinfo.setCard_no("12222222222222222");
//        clearModel.addAdvance(new BigDecimal("123"));
//        Map<String, BigDecimal> payCodes = clearModel.getPayCodeCharges();
//        payCodes.put("01", BigDecimal.ZERO);
//        payCodes.put("02", BigDecimal.ZERO);
//        payCodes.put("03", BigDecimal.ZERO);
//        clearModel.addAmtByPayCode(new BigDecimal("12"),"01");
//        clearModel.addAmtByPayCode(new BigDecimal("0"),"02");
//        clearModel.addAmtByPayCode(new BigDecimal("300"),"03");
//
//        BigDecimal advanceAmt = clearModel.getAdvance();//总垫付金额
//        Map<String, BigDecimal> paycodeCharges = clearModel.getPayCodeCharges();
//
//
//        for (Map.Entry<String, BigDecimal> entry : paycodeCharges.entrySet()) {
//            StringBuffer sb = new StringBuffer();
//            String pay_code = entry.getKey();
//            BigDecimal paycodeAmt = entry.getValue();//渠道充值
//            BigDecimal paycodeAdvance;//渠道垫付
//            if (paycodeAmt.compareTo(advanceAmt) >= 0) {
//                //Assert.assertEquals(advanceAmt,BigDecimal.TEN);
//                paycodeAdvance = advanceAmt;
//                advanceAmt = BigDecimal.ZERO;
//                paycodeAmt = paycodeAmt.subtract(paycodeAdvance);
//            } else {
//                paycodeAdvance = paycodeAmt;
//                paycodeAmt = BigDecimal.ZERO;
//                advanceAmt = advanceAmt.subtract(paycodeAdvance);
//            }
//
//            //添加标题头
//            sb.append(clearModel.getPlat_no()).append("|").append(pay_code).append("|").append(entry.getValue()).append("\n");
//            sb.append(platCardinfo.getCard_name()).append("|").append("1").append("|")
//                    .append(platCardinfo.getCard_no()).append("|").append(paycodeAmt).append("\n");
//            sb.append("|3||").append(paycodeAdvance);
//            String fileName = clearModel.getPlat_no() + "_" + pay_code + ".txt";
//            try {
//                FileUtils.write(new File("D:/test/"+fileName),sb.toString(),"utf-8");
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            //写文件
//            //uploadFilesIn(clearModel.getPlat_no(), clearModel.getClear_date(), sb, fileName, pay_code);
//        }
//
////        baseResponse.setRecode(BusinessMsg.SUCCESS);
////        baseResponse.setRemsg("生成清算文件成功");
//    }
//    public static void main(String[] args) {
//        System.out.println("Hello world");
//    }
//}
