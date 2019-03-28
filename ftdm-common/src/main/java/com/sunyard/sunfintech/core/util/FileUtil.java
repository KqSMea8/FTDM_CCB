package com.sunyard.sunfintech.core.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by wubin on 2017/5/5.
 */
public class FileUtil {

    public static void download(String fileName,String filePath, HttpServletResponse response){

//        String filePath = httpServletRequest.getSession().getServletContext().getRealPath("File");// 获取真实路径
        String downloadFilename = fileName;// 在下载框默认显示的文件名
        try {
            downloadFilename = URLEncoder.encode(downloadFilename, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        // 对默认下载的文件名编码。不编码的结果就是，在客户端下载时文件名乱码
        File file = new File(filePath);
        if (file.exists()) {
            // 写明要下载的文件的大小
            response.setContentLength((int) file.length());
            response.setHeader("Content-Disposition", "attachment;filename="
                    + downloadFilename);// 设置在下载框默认显示的文件名
            response.setContentType("application/octet-stream");// 指明response的返回对象是文件流
            // 读出文件到response
            // 这里是先需要把要把文件内容先读到缓冲区
            // 再把缓冲区的内容写到response的输出流供用户下载
            try {
                FileInputStream fileInputStream = new FileInputStream(file);
                BufferedInputStream bufferedInputStream = new BufferedInputStream(
                        fileInputStream);
                byte[] b = new byte[bufferedInputStream.available()];
                bufferedInputStream.read(b);
                OutputStream outputStream = response.getOutputStream();
                outputStream.write(b);
                // 关闭流
                bufferedInputStream.close();
                outputStream.flush();
                outputStream.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }else{
            System.out.println("文件不存在");
        }
    }



}
