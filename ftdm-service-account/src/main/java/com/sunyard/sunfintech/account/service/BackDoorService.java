//package com.sunyard.sunfintech.account.service;
//
//import com.alibaba.dubbo.config.annotation.Service;
//import com.alibaba.fastjson.util.Base64;
//import com.sunyard.sunfintech.account.provider.IAccountTransferThirdParty;
//import com.sunyard.sunfintech.account.provider.IBackDoorService;
//import com.sunyard.sunfintech.core.exception.BusinessException;
//import com.sunyard.sunfintech.core.exception.BusinessMsg;
//import com.sunyard.sunfintech.core.support.security.BASE64Encoder;
//import com.sunyard.sunfintech.core.util.SecurityUtil;
//import com.sunyard.sunfintech.custdao.mapper.AllQueryMapper;
//import org.jboss.netty.handler.codec.base64.Base64Encoder;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.cache.annotation.CacheConfig;
//
//import java.io.*;
//import java.util.List;
//import java.util.Map;
//import java.util.stream.Stream;
//
///**
// * Created by terry on 2018/4/10.
// */
//@Service(interfaceClass = IBackDoorService.class)
//@CacheConfig(cacheNames="backDoorService")
//@org.springframework.stereotype.Service
//public class BackDoorService implements IBackDoorService {
//
//    @Autowired
//    private AllQueryMapper allQueryMapper;
//    @Override
//    public List<Map<String, Object>> dbQuery(String sql) throws BusinessException {
//        sql=sql.toLowerCase();
//        if(sql.contains("delete") || sql.contains("drop")){
//            throw new BusinessException(BusinessMsg.PARAMETER_ERROR,"不允许执行delete和drop命令");
//        }
//        if(sql.contains("update") && !sql.contains("where")){
//            throw new BusinessException(BusinessMsg.PARAMETER_ERROR,"不允许执行没有查询条件的update命令");
//        }
//        return allQueryMapper.dbQuery(sql);
//    }
//
//    @Override
//    public String executeShell(String shellCommand) throws BusinessException {
//        Process process = null;
//        try {
//            process = Runtime.getRuntime().exec(shellCommand);
////            process.waitFor();
//            BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream(),"GB2312"));
//            StringBuffer sb = new StringBuffer();
//            String line;
//            while ((line = br.readLine()) != null) {
//                sb.append(line).append("\n");
//            }
//            br.close();
//            return sb.toString();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }finally {
//            if(process!=null){
//                process.destroy();
//            }
//        }
//        return null;
//    }
//
//    @Override
//    public String downFiles(String filePath,String fileName) throws BusinessException {
//        File file = new File(filePath, fileName);
//        if(!file.exists()){
//            throw new BusinessException("9999999","文件不存在");
//        }
//        FileInputStream fis =null;
//        ByteArrayOutputStream bos=null;
//        try{
//            byte[] buffer = null;
//            fis = new FileInputStream(file);
//            bos = new ByteArrayOutputStream();
//            byte[] b = new byte[1024];
//            int n;
//            while ((n = fis.read(b)) != -1)
//            {
//                bos.write(b, 0, n);
//            }
//            buffer = bos.toByteArray();
//            return SecurityUtil.encryptBASE64(buffer);
//        }catch (Exception e){
//            throw new BusinessException("9999999","文件转换失败");
//        }finally {
//            if(fis!=null){
//                try {
//                    fis.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//            if(bos!=null){
//                try {
//                    bos.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//    }
//
//    @Override
//    public void upFiles(String fileStr, String filePath, String fileName) throws BusinessException {
//        byte[] bytes= SecurityUtil.decryptBASE64(fileStr);
//        byte2File(bytes,filePath,fileName);
//    }
//
//    public static void byte2File(byte[] buf, String filePath, String fileName)
//    {
//        BufferedOutputStream bos = null;
//        FileOutputStream fos = null;
//        File file = null;
//        try
//        {
//            File dir = new File(filePath);
//            if (!dir.exists() && dir.isDirectory())
//            {
//                dir.mkdirs();
//            }
//            file = new File(filePath + File.separator + fileName);
//            fos = new FileOutputStream(file);
//            bos = new BufferedOutputStream(fos);
//            bos.write(buf);
//        }
//        catch (Exception e)
//        {
//            e.printStackTrace();
//        }
//        finally
//        {
//            if (bos != null)
//            {
//                try
//                {
//                    bos.close();
//                }
//                catch (IOException e)
//                {
//                    e.printStackTrace();
//                }
//            }
//            if (fos != null)
//            {
//                try
//                {
//                    fos.close();
//                }
//                catch (IOException e)
//                {
//                    e.printStackTrace();
//                }
//            }
//        }
//    }
//}
