package com.sunyard.sunfintech.account.service;

import com.alibaba.fastjson.JSON;
import com.sunyard.sunfintech.account.provider.IBackDoorService;
import com.sunyard.sunfintech.core.support.security.BASE64Decoder;
import com.sunyard.sunfintech.core.support.security.BASE64Encoder;
import com.sunyard.sunfintech.core.util.SecurityUtil;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Created by terry on 2018/4/10.
 */
public class BackDoorServiceTest {
    @Test
    public void dbQuery() throws Exception {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring/spring-context.xml");
        context.start();
        IBackDoorService backDoorService=context.getBean(IBackDoorService.class);
        String sql="delete from plat_paycode where id=171";
        List<Map<String,Object>> queryResult=backDoorService.dbQuery(sql);
        System.out.println("\n\n\n\n\n\n\n"+JSON.toJSONString(queryResult)+"\n\n\n\n\n\n\n");
    }

    @Test
    public void executeShell() throws Exception {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring/spring-context.xml");
        context.start();
        IBackDoorService backDoorService=context.getBean(IBackDoorService.class);
        String output=backDoorService.executeShell("java");
        System.out.println("\n\n\n\n\n\n\n"+output+"\n\n\n\n\n\n\n");
    }

    @Test
    public void downFiles() throws Exception {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring/spring-context.xml");
        context.start();
        IBackDoorService backDoorService=context.getBean(IBackDoorService.class);
        String filePath="C:\\Users\\terry\\Desktop";
        String fileName="img_0222.jpeg";
        String fileBase64=backDoorService.downFiles(filePath,fileName);
        byte[] bytes= SecurityUtil.decryptBASE64(fileBase64);
        byte2File(bytes,"D:\\11111111",fileName);
//        System.out.println("\n\n\n\n\n\n\n"+fileBase64+"\n\n\n\n\n\n\n");
//        System.out.println("\n\n\n\n\n\n\n"+new String(bytes)+"\n\n\n\n\n\n\n");

    }

    public static void byte2File(byte[] buf, String filePath, String fileName)
    {
        BufferedOutputStream bos = null;
        FileOutputStream fos = null;
        File file = null;
        try
        {
            File dir = new File(filePath);
            if (!dir.exists() && dir.isDirectory())
            {
                dir.mkdirs();
            }
            file = new File(filePath + File.separator + fileName);
            fos = new FileOutputStream(file);
            bos = new BufferedOutputStream(fos);
            bos.write(buf);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            if (bos != null)
            {
                try
                {
                    bos.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
            if (fos != null)
            {
                try
                {
                    fos.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
    }

}