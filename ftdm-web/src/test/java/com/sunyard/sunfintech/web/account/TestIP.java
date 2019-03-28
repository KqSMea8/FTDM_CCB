package com.sunyard.sunfintech.web.account;

import java.net.InetAddress;

/**
 * @author heroy
 * @version 2018/3/21
 */
public class TestIP {

    public static void main(String[] args) {
        InetAddress addr=null;
        String ip="";
        String address="";
        try{
            // cp01-fengchao-public-7.epc.baidu.com|10.95.130.73
//  addr = InetAddress.getByName("cp01-fengchao-public-7.epc.baidu.com");
            addr = InetAddress.getLocalHost();
            ip = addr.getHostAddress().toString(); //获得机器IP　　
            address = addr.getHostName().toString(); //获得机器名称
            System.out.println(ip + "|" + address);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
