package com.sunyard.sunfintech.core.util;

import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * 签名工具类
 * @author heroy
 * @version 2017/6/2
 */
public class SunSignatureUtil {

    /**
     * 获取签名字符串
     */
    public static String getSignContent(Map<String, Object> map){
        TreeMap<String, String> sortedParams = new TreeMap<String, String>();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            if(null != entry.getValue() && StringUtils.isNoneBlank(String.valueOf(entry.getValue())) && !"sign".equals(entry.getKey())){
                sortedParams.put(entry.getKey(), String.valueOf(entry.getValue()));
            }
        }
        return getSignContentFromTreeMap(sortedParams);
    }

    public static String getSignContentFromTreeMap(TreeMap<String, String> map) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Map.Entry<String, String> sortedEntry : map.entrySet()) {
            if (null != sortedEntry.getValue() && StringUtils.isNoneBlank(String.valueOf(sortedEntry.getValue())) && !"sign".equals(sortedEntry.getKey())) {
                stringBuilder.append(String.valueOf(sortedEntry.getValue())).append("|");
            }
        }
        if (stringBuilder.length() > 0) {
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        }
        return stringBuilder.toString();
    }

    public static void main(String[] args){
        Map<String, String> map = new HashMap();
        map.put("real_repay_date","20170602");
        map.put("partner_trans_time","120301");
        map.put("mer_no","BOB-FENGJR-ZS-C-20170509");
        map.put("order_no","201706026985");
        map.put("partner_trans_date","");
        map.put("sign", "AUOOMDR*(*HHm");
        map.put("prod_id","20170602753159");
        map.put("amt","12.3300");

        //System.out.println(SunSignatureUtil.getSignContent(map));

        TreeMap<String, String> treeMap = new TreeMap<>(map);

        String content = getSignContentFromTreeMap(treeMap);

        System.out.println(content);
    }
}
