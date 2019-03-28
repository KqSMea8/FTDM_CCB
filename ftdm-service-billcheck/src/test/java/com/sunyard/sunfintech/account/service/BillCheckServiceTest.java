package com.sunyard.sunfintech.account.service;

import com.sunyard.sunfintech.billcheck.provider.IBillCheckService;
import com.sunyard.sunfintech.billcheck.utils.SFTPUtils;
import com.sunyard.sunfintech.core.base.BaseHttpResponse;
import com.sunyard.sunfintech.core.util.HttpClientUtils;
import com.sunyard.sunfintech.dao.entity.BillCheckNotifyRetry;
import com.sunyard.sunfintech.dao.entity.BillCheckNotifyRetryExample;
import com.sunyard.sunfintech.dao.mapper.BillCheckNotifyRetryMapper;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration("classpath:spring/spring-context.xml")
public class BillCheckServiceTest {

    @Autowired
    IBillCheckService iBillCheckService;

    @Autowired
    BillCheckNotifyRetryMapper billCheckNotifyRetryMapper;

    @Ignore
    @Test
    public void testBillCheckNotifyReturnSuccess() {
        String mall_no = "BOB-FENGJR-B-20170509";
        String clear_date = "20180205";
        boolean result = iBillCheckService.billCheckNotify(mall_no, clear_date);
        Assert.assertEquals(true, result);
    }

    @Ignore
    @Test
    public void testBillCheckNotifyReturnFailure() {
        //验证结果失败
        String mall_no = "BOB-FENGJR-X-20170509";
        String clear_date = "20180205";
        boolean result = iBillCheckService.billCheckNotify(mall_no, clear_date);
        Assert.assertEquals(false, result);
        //验证重试表中有一条重试记录
        BillCheckNotifyRetryExample billCheckNotifyRetryExample = new BillCheckNotifyRetryExample();
        billCheckNotifyRetryExample.createCriteria().andMall_noEqualTo(mall_no).andClear_dateEqualTo(clear_date);
        List<BillCheckNotifyRetry> billCheckNotifyRetryList = billCheckNotifyRetryMapper.selectByExample(billCheckNotifyRetryExample);
        Assert.assertEquals(1, billCheckNotifyRetryList.size());
    }

//    @Ignore
    @Test
    public void testNotify() throws Exception {
        Map<String,Object> params = new HashMap<>();
        params.put("mall_no","BOB-FENGJR-B-20170509");
        params.put("clear_date", "20180201");
//        params.put("type", SortingType.END.getCode());
//SortingRequest sortingRequest = new SortingRequest();
//sortingRequest.setMall_no("BOB-FENGJR-B-20170509");
//sortingRequest.setClear_date("20180201");
//        String url = "http://72.160.0.220:8885/ftdm-web/notify/notify_clear_status";
        String url = "http://72.160.0.220:8885/ftdm-web/billchecking/request_sortingfile";
        BaseHttpResponse baseHttpResponse = HttpClientUtils.httpPostRequest(url, params);
        Assert.assertNotNull(baseHttpResponse);
    }

    @Test
    public void testDev(){
//        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring/spring-context.xml");
//        context.start();
//        IBillCheckService billCheckService=context.getBean(IBillCheckService.class);
//        billCheckService.makeAccountBalance("123");
        try {
            String fileDirName = "C:\\Users\\terry\\Desktop\\";
            String fileName = "4e0cd94bly1fpbhmed2z0g208o07z1l1.gif";
            File file = new File(fileDirName, fileName);
            if (!file.exists()) {
                System.out.println(file.getName() + " 不存在");
            }

            SFTPUtils ftp = new SFTPUtils("101.37.82.175", "22", "root", "sunyard@175");
            if (ftp.connect()) {
                ftp.uploadFile("/home/test/", fileName, fileDirName + "/", fileName);
                //关闭连接
                ftp.disconnect();
            }
        } catch (Exception e) {
        }
    }

}