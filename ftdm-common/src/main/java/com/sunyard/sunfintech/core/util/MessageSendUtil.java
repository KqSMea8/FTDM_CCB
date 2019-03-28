package com.sunyard.sunfintech.core.util;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by terry on 2017/7/12.
 */
public class MessageSendUtil {
    private String serviceURL = "";
    private String sn = "";
    private String password = "";
    private String pwd = "";

    public MessageSendUtil(String sn, String password, String url) throws UnsupportedEncodingException {
        this.sn = sn;
        this.password = password;
        this.pwd = this.getMD5(sn + password);
        this.serviceURL=url;
    }

    public String getMD5(String sourceStr) throws UnsupportedEncodingException {
        String resultStr = "";

        try {
            byte[] temp = sourceStr.getBytes();
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(temp);
            byte[] b = md5.digest();

            for(int i = 0; i < b.length; ++i) {
                char[] digit = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
                char[] ob = new char[]{digit[b[i] >>> 4 & 15], digit[b[i] & 15]};
                resultStr = resultStr + new String(ob);
            }

            return resultStr;
        } catch (NoSuchAlgorithmException var9) {
            var9.printStackTrace();
            return null;
        }
    }

    public String mdgetSninfo() throws IOException {
        String result = "";
        String soapAction = "http://entinfo.cn/mdgetSninfo";
        String xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>";
        xml = xml + "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">";
        xml = xml + "<soap:Body>";
        xml = xml + "<mdgetSninfo xmlns=\"http://entinfo.cn/\">";
        xml = xml + "<sn>" + this.sn + "</sn>";
        xml = xml + "<pwd>" + this.pwd + "</pwd>";
        xml = xml + "<mobile></mobile>";
        xml = xml + "<content></content>";
        xml = xml + "<ext></ext>";
        xml = xml + "<stime></stime>";
        xml = xml + "<rrid></rrid>";
        xml = xml + "<msgfmt></msgfmt>";
        xml = xml + "</mdgetSninfo>";
        xml = xml + "</soap:Body>";
        xml = xml + "</soap:Envelope>";
        ByteArrayOutputStream bout = null;
        OutputStream out = null;
        InputStreamReader isr = null;
        BufferedReader in = null;

        String var10;
        try {
            URL url = new URL(this.serviceURL);
            URLConnection connection = url.openConnection();
            HttpURLConnection httpconn = (HttpURLConnection)connection;
            bout = new ByteArrayOutputStream();
            bout.write(xml.getBytes());
            byte[] b = bout.toByteArray();
            httpconn.setRequestProperty("Content-Length", String.valueOf(b.length));
            httpconn.setRequestProperty("Content-Type", "text/xml; charset=gb2312");
            httpconn.setRequestProperty("SOAPAction", soapAction);
            httpconn.setRequestMethod("POST");
            httpconn.setDoInput(true);
            httpconn.setDoOutput(true);
            out = httpconn.getOutputStream();
            out.write(b);
            isr = new InputStreamReader(httpconn.getInputStream());
            in = new BufferedReader(isr);

            label129:
            while(true) {
                String inputLine;
                if(null != (inputLine = in.readLine())) {
                    Pattern pattern = Pattern.compile("<mdgetSninfoResult>(.*)</mdgetSninfoResult>");
                    Matcher matcher = pattern.matcher(inputLine);

                    while(true) {
                        if(!matcher.find()) {
                            continue label129;
                        }

                        result = matcher.group(1);
                    }
                }

                String var13 = result;
                return var13;
            }
        } catch (Exception var18) {
            var18.printStackTrace();
            var10 = "";
        } finally {
            if(bout != null) {
                bout.close();
            }

            if(out != null) {
                out.close();
            }

            if(isr != null) {
                isr.close();
            }

            if(in != null) {
                in.close();
            }

        }

        return var10;
    }

    public String mdgxsend(String mobile, String content, String ext, String stime, String rrid, String msgfmt) throws IOException {
        String result = "";
        String soapAction = "http://entinfo.cn/mdgxsend";
        String xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>";
        xml = xml + "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">";
        xml = xml + "<soap:Body>";
        xml = xml + "<mdgxsend xmlns=\"http://entinfo.cn/\">";
        xml = xml + "<sn>" + this.sn + "</sn>";
        xml = xml + "<pwd>" + this.pwd + "</pwd>";
        xml = xml + "<mobile>" + mobile + "</mobile>";
        xml = xml + "<content>" + content + "</content>";
        xml = xml + "<ext>" + ext + "</ext>";
        xml = xml + "<stime>" + stime + "</stime>";
        xml = xml + "<rrid>" + rrid + "</rrid>";
        xml = xml + "<msgfmt>" + msgfmt + "</msgfmt>";
        xml = xml + "</mdgxsend>";
        xml = xml + "</soap:Body>";
        xml = xml + "</soap:Envelope>";
        ByteArrayOutputStream bout = null;
        OutputStream out = null;
        InputStreamReader isr = null;
        BufferedReader in = null;

        String var16;
        try {
            URL url = new URL(this.serviceURL);
            URLConnection connection = url.openConnection();
            HttpURLConnection httpconn = (HttpURLConnection)connection;
            bout = new ByteArrayOutputStream();
            bout.write(xml.getBytes());
            byte[] b = bout.toByteArray();
            httpconn.setRequestProperty("Content-Length", String.valueOf(b.length));
            httpconn.setRequestProperty("Content-Type", "text/xml; charset=gb2312");
            httpconn.setRequestProperty("SOAPAction", soapAction);
            httpconn.setRequestMethod("POST");
            httpconn.setDoInput(true);
            httpconn.setDoOutput(true);
            out = httpconn.getOutputStream();
            out.write(b);
            out.close();
            isr = new InputStreamReader(httpconn.getInputStream());
            in = new BufferedReader(isr);

            label129:
            while(true) {
                String inputLine;
                if(null != (inputLine = in.readLine())) {
                    Pattern pattern = Pattern.compile("<mdgxsendResult>(.*)</mdgxsendResult>");
                    Matcher matcher = pattern.matcher(inputLine);

                    while(true) {
                        if(!matcher.find()) {
                            continue label129;
                        }

                        result = matcher.group(1);
                    }
                }

                String var19 = result;
                return var19;
            }
        } catch (Exception var24) {
            var24.printStackTrace();
            var16 = "";
        } finally {
            if(bout != null) {
                bout.close();
            }

            if(out != null) {
                out.close();
            }

            if(isr != null) {
                isr.close();
            }

            if(in != null) {
                in.close();
            }

        }

        return var16;
    }

    public String mdsmssend(String mobile, String content, String ext, String stime, String rrid, String msgfmt) throws IOException {
        String result = "";
        String soapAction = "http://entinfo.cn/mdsmssend";
        String xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>";
        xml = xml + "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">";
        xml = xml + "<soap:Body>";
        xml = xml + "<mdsmssend  xmlns=\"http://entinfo.cn/\">";
        xml = xml + "<sn>" + this.sn + "</sn>";
        xml = xml + "<pwd>" + this.pwd + "</pwd>";
        xml = xml + "<mobile>" + mobile + "</mobile>";
        xml = xml + "<content>" + content + "</content>";
        xml = xml + "<ext>" + ext + "</ext>";
        xml = xml + "<stime>" + stime + "</stime>";
        xml = xml + "<rrid>" + rrid + "</rrid>";
        xml = xml + "<msgfmt>" + msgfmt + "</msgfmt>";
        xml = xml + "</mdsmssend>";
        xml = xml + "</soap:Body>";
        xml = xml + "</soap:Envelope>";
        ByteArrayOutputStream bout = null;
        OutputStream out = null;
        InputStreamReader isr = null;
        BufferedReader in = null;

        String var16;
        try {
            URL url = new URL(this.serviceURL);
            URLConnection connection = url.openConnection();
            HttpURLConnection httpconn = (HttpURLConnection)connection;
            bout = new ByteArrayOutputStream();
            bout.write(xml.getBytes());
            byte[] b = bout.toByteArray();
            httpconn.setRequestProperty("Content-Length", String.valueOf(b.length));
            httpconn.setRequestProperty("Content-Type", "text/xml; charset=gb2312");
            httpconn.setRequestProperty("SOAPAction", soapAction);
            httpconn.setRequestMethod("POST");
            httpconn.setDoInput(true);
            httpconn.setDoOutput(true);
            out = httpconn.getOutputStream();
            out.write(b);
            out.close();
            isr = new InputStreamReader(httpconn.getInputStream());
            in = new BufferedReader(isr);

            label129:
            while(true) {
                String inputLine;
                if(null != (inputLine = in.readLine())) {
                    Pattern pattern = Pattern.compile("<mdsmssendResult>(.*)</mdsmssendResult>");
                    Matcher matcher = pattern.matcher(inputLine);

                    while(true) {
                        if(!matcher.find()) {
                            continue label129;
                        }

                        result = matcher.group(1);
                    }
                }

                String var19 = result;
                return var19;
            }
        } catch (Exception var24) {
            var24.printStackTrace();
            var16 = "";
        } finally {
            if(bout != null) {
                bout.close();
            }

            if(out != null) {
                out.close();
            }

            if(isr != null) {
                isr.close();
            }

            if(in != null) {
                in.close();
            }

        }

        return var16;
    }
}
