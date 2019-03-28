package com.sunyard.mock;

import com.sunyard.sunfintech.core.util.HttpClientUtils;

/**
 * Created by djh on 2017/12/19.
 */
public class MyTestService {

    public static String doGet(String url){
        //assume network timeout
        HttpClientUtils.httpGetRequest("http://www.baidu.com");
        return "success";
    }
    public static void main(String[] args) {
        System.out.println("Hello world");
    }
}
