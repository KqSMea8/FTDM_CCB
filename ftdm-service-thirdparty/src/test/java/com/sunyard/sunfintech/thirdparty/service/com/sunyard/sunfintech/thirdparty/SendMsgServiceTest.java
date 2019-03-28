package com.sunyard.sunfintech.thirdparty.service.com.sunyard.sunfintech.thirdparty;

import com.alibaba.fastjson.JSONObject;
import com.sunyard.sunfintech.core.base.BaseHttpResponse;
import com.sunyard.sunfintech.core.util.DateUtils;
import com.sunyard.sunfintech.core.util.SeqUtil;
import org.apache.http.client.methods.HttpPost;

import java.util.Date;
import java.util.Map;

/**
 * Created by terry on 2017/5/12.
 */
public class SendMsgServiceTest {
//    @Test
    public static void main(String[] args) {
        String request = "{\n" +
                "\"version\" : \"V1.0\",\n" +
                "\"partner_trans_date\" : \"20170909\",\n" +
                "\"partner_trans_time\" : \"175235\",\n" +
                "\"mall_no\" : \"BOB-U51-C-20170821\",\n" +
                "\"mall_name\" : \"U51\",\n" +
                "\"mer_no\" : \"BOB-U51-51-C-20170821\",\n" +
                "\"mer_name\" : \"51XY\",\n" +
                "\"order_no\" : \"20170909175235000050612844974648\",\n" +
                "\"sign\" : \"RCIgHqTmbCUEhT/E6xSa3NFAbrARdyQUPDfaAM61GQbPjsr+y4TU3IynMgH7aK/JPBEqhvX91cB7o/dC33Bw9fM4HZewcN4MlfXJxu5pTc4NWVrBY7/8XNkcpbPc0zyNDFWKgdMuqyrqUMcaf0oCS/GdDzCSO7ulNsmxPRg59Js=\",\n" +
                "\"data\" : \"[\n" +
                "{\\\"detail_no\\\":\\\"20170909175235000050612859993713\\\",\\\"platcust\\\":\\\"201709081709140933056198\\\",\\\"card_no_old\\\":\\\"62596260288986844\\\"}\n" +
                "]\"\n" +
                "}";
        request = request.replaceAll("\n","");
        Map<String,Object> params = JSONObject.parseObject(request);
        params.put("order_no", SeqUtil.getSeqNum());
        params.put("partner_trans_date", DateUtils.formatDateToStr(new Date(),DateUtils.DEF_FORMAT_NOTIME));
        params.put("partner_trans_time", DateUtils.formatDateToStr(new Date(), "HHmmss"));
        String ip = "101.37.82.175";
        String port ="8885";
        String main ="account";
        String method ="unbind_cards";
        String url = String.format("http://%s:%s/ftdm-web/%s/%s",ip,port,main,method);
        System.out.println(url);

        BaseHttpResponse baseHttpResponse = null;
        HttpPost httpPost = new HttpPost(url);
        try {

        } catch (Exception e) {
            System.out.println(e);
        }

        System.out.println(baseHttpResponse.getEntityString());
        System.out.println(baseHttpResponse.getStatusCode());
        System.out.println(baseHttpResponse.getErrorMessage());
    }

}