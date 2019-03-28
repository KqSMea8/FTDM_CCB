package com.sunyard.sunfintech.core.util;

/**
 * Created by Lid on 2017/7/5.
 */
import com.sunyard.sunfintech.core.exception.BusinessException;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * FTP工具类
 * @author dq
 */
public class FtpUtils {
    private FTPClient ftpClient = null;
    private String server;
    private int port;
    private String userName;
    private String userPassword;
    private final Logger logger = LogManager.getLogger(this.getClass());

    public FtpUtils(String server, int port, String userName, String userPassword) {
        this.server = server;
        this.port = port;
        this.userName = userName;
        this.userPassword = userPassword;
    }

    public FTPClient getFtpClient() {
        return ftpClient;
    }
    /**
     * 连接服务器
     * @return 连接成功与否 true:成功， false:失败
     */
    public boolean open() {
        if (ftpClient != null && ftpClient.isConnected()) {
            return true;
        }
        try {
            ftpClient = new FTPClient();
            // 连接
            ftpClient.connect(this.server, this.port);
            ftpClient.login(this.userName, this.userPassword);
            setFtpClient(ftpClient);
            // 检测连接是否成功
            int reply = ftpClient.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                this.close();
                logger.info("=============================================FTP连接失败===============================================");
                System.err.println("FTP server refused connection.");
            }
            System.out.println("open FTP success:" + this.server + ";port:" + this.port + ";name:" + this.userName
                    + ";pwd:" + this.userPassword);
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE); // 设置上传模式.binally  or ascii
            return true;
        } catch (Exception ex) {
            this.close();
            ex.printStackTrace();
            return false;
        }
    }

    /**
     * 切换到父目录
     * @return 切换结果 true：成功， false：失败
     */
    public boolean changeToParentDir() {
        try {
            return ftpClient.changeToParentDirectory();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 改变当前目录到指定目录
     * @param dir 目的目录
     * @return 切换结果 true：成功，false：失败
     */
    public boolean cd(String dir) {
        try {
            return ftpClient.changeWorkingDirectory(dir);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 获取目录下所有的文件名称
     *
     * @param filePath 指定的目录
     * @return 文件列表,或者null
     */
    public FTPFile[] getFileList(String filePath) {
        try {
            return ftpClient.listFiles(filePath);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 层层切换工作目录
     * @param ftpPath 目的目录
     * @return 切换结果
     */
    public boolean changeDir(String ftpPath) {
        if (!ftpClient.isConnected()) {
            return false;
        }
        try {
            // 将路径中的斜杠统一
            char[] chars = ftpPath.toCharArray();
            StringBuffer sbStr = new StringBuffer(256);
            for (int i = 0; i < chars.length; i++) {
                if ('\\' == chars[i]) {
                    sbStr.append('/');
                } else {
                    sbStr.append(chars[i]);
                }
            }
            ftpPath = sbStr.toString();
            if (ftpPath.indexOf('/') == -1) {
                // 只有一层目录
                ftpClient.changeWorkingDirectory(new String(ftpPath.getBytes(), "iso-8859-1"));
            } else {
                // 多层目录循环创建
                String[] paths = ftpPath.split("/");
                for (int i = 0; i < paths.length; i++) {
                    ftpClient.changeWorkingDirectory(new String(paths[i].getBytes(), "iso-8859-1"));
                }
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 循环创建目录，并且创建完目录后，设置工作目录为当前创建的目录下
     * @param ftpPath 需要创建的目录
     * @return
     */
    public boolean mkDir(String ftpPath) {
        if (!ftpClient.isConnected()) {
            return false;
        }
        try {
            // 将路径中的斜杠统一
            char[] chars = ftpPath.toCharArray();
            StringBuffer sbStr = new StringBuffer(256);
            for (int i = 0; i < chars.length; i++) {
                if ('\\' == chars[i]) {
                    sbStr.append('/');
                } else {
                    sbStr.append(chars[i]);
                }
            }
            ftpPath = sbStr.toString();
            System.out.println("ftpPath:" + ftpPath);
            if (ftpPath.indexOf('/') == -1) {
                // 只有一层目录
                ftpClient.makeDirectory(new String(ftpPath.getBytes(), "iso-8859-1"));
                ftpClient.changeWorkingDirectory(new String(ftpPath.getBytes(), "iso-8859-1"));
            } else {
                // 多层目录循环创建
                String[] paths = ftpPath.split("/");
                for (int i = 0; i < paths.length; i++) {
                    ftpClient.makeDirectory(new String(paths[i].getBytes(), "iso-8859-1"));
                    ftpClient.changeWorkingDirectory(new String(paths[i].getBytes(), "iso-8859-1"));
                }
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 上传文件到FTP服务器
     * @param localDirectoryAndFileName 本地文件目录和文件名
     * @param ftpFileName 上传到服务器的文件名
     * @param ftpDirectory FTP目录如:/path1/pathb2/,如果目录不存在会自动创建目录
     * @return
     */
    public boolean upload(String localDirectoryAndFileName, String ftpFileName, String ftpDirectory) {
        if (!ftpClient.isConnected()) {
            return false;
        }
        boolean flag = false;
        if (ftpClient != null) {
            File srcFile = new File(localDirectoryAndFileName);
            FileInputStream fis = null;
            try {
                fis = new FileInputStream(srcFile);
                // 创建目录
                this.mkDir(ftpDirectory);
                ftpClient.setBufferSize(100000);
                ftpClient.setControlEncoding("UTF-8");
                // 设置文件类型（二进制）
                ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
                // 上传
                //ftpClient.enterLocalPassiveMode();
                ftpClient.enterLocalActiveMode();
                flag = ftpClient.storeFile(new String(ftpFileName.getBytes(), "iso-8859-1"), fis);
                logger.info("ftp服务器响应码："+ftpClient.getReplyCode()+",ftp响应信息："+ftpClient.getReplyString()+",ftp storeFile结果"+flag);
            } catch (Exception e) {
                this.close();
                throw new BusinessException(e);
            } finally {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
//        if(!flag){
//            throw new BusinessException("ftp上传失败");
//        }
        logger.info("上传文件成功，本地文件名： " + localDirectoryAndFileName + "，上传到目录：" + ftpDirectory + "/" + ftpFileName);
        return flag;
    }

    /**
     * 从FTP服务器上下载文件
     * @param ftpDirectoryAndFileName ftp服务器文件路径，以/dir形式开始
     * @param localDirectoryAndFileName 保存到本地的目录
     * @return
     */
    public boolean get(String ftpDirectoryAndFileName, String localDirectoryAndFileName) {
        if (!ftpClient.isConnected()) {
            return false;
        }
        ftpClient.enterLocalPassiveMode(); // Use passive mode as default
        try {
            // 将路径中的斜杠统一
            char[] chars = ftpDirectoryAndFileName.toCharArray();
            StringBuffer sbStr = new StringBuffer(256);
            for (int i = 0; i < chars.length; i++) {
                if ('\\' == chars[i]) {
                    sbStr.append('/');
                } else {
                    sbStr.append(chars[i]);
                }
            }
            ftpDirectoryAndFileName = sbStr.toString();
            String filePath = ftpDirectoryAndFileName.substring(0, ftpDirectoryAndFileName.lastIndexOf("/"));
            String fileName = ftpDirectoryAndFileName.substring(ftpDirectoryAndFileName.lastIndexOf("/") + 1);
            this.changeDir(filePath);
            ftpClient.retrieveFile(new String(fileName.getBytes(), "iso-8859-1"),
                    new FileOutputStream(localDirectoryAndFileName)); // download
            // file
            System.out.println(ftpClient.getReplyString()); // check result
            System.out.println("从ftp服务器上下载文件：" + ftpDirectoryAndFileName + "， 保存到：" + localDirectoryAndFileName);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 返回FTP目录下的文件列表
     * @param pathName
     * @return
     */
    public String[] getFileNameList(String pathName) {
        try {
            return ftpClient.listNames(pathName);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 删除FTP上的文件
     * @param ftpDirAndFileName 路径开头不能加/，比如应该是test/filename1
     * @return
     */
    public boolean deleteFile(String ftpDirAndFileName) {
        if (!ftpClient.isConnected()) {
            return false;
        }
        try {
            return ftpClient.deleteFile(ftpDirAndFileName);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 删除FTP目录
     * @param ftpDirectory
     * @return
     */
    public boolean deleteDirectory(String ftpDirectory) {
        if (!ftpClient.isConnected()) {
            return false;
        }
        try {
            return ftpClient.removeDirectory(ftpDirectory);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 关闭链接
     */
    public void close() {
        try {
            if (ftpClient != null && ftpClient.isConnected()) {
                ftpClient.disconnect();
            }
            System.out.println("成功关闭连接，服务器ip:" + this.server + ", 端口:" + this.port);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public FTPClient getClient() {
        return ftpClient;
    }

    public void setFtpClient(FTPClient ftpClient) {
        this.ftpClient = ftpClient;
    }

    public static void main(String[] args) {
        FtpUtils f = new FtpUtils("39.108.170.98", 21, "ftptest", "123456");

        try {
            if(f.open()) {
                String fileName = "test12.txt";

                //上传
                //System.out.println(f.changeToParentDir());
//                f.get
                f.upload("D:/test/test.txt", fileName, "/home/ftptest/rootdir/");
                System.out.println(f.getFileNameList("/test1"));
                f.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
