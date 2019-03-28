package com.sunyard.sunfintech.core.util;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * 【IP 工具类】
 *
 * @author wyc 2017/11/9
 */
public class IpUtil {
	
	public static String getRemoteIP(HttpServletRequest request) {
		if (null == request) {
			return null;
		}
		String proxs[] = { "X-Forwarded-For", "Proxy-Client-IP",
				"WL-Proxy-Client-IP", "HTTP_CLIENT_IP", "HTTP_X_FORWARDED_FOR" };
		String ip = null;
		for (String prox : proxs) {
			ip = request.getHeader(prox);
			if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
				continue;
			} else {
				break;
			}
		}
		
		//对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
		if(ip!=null && ip.length()>15){ //"***.***.***.***".length() = 15
			if(ip.indexOf(",")>0){
				ip = ip.substring(0,ip.indexOf(","));
			}
		}
		
		if (StringUtils.isEmpty(ip)) {
			return request.getRemoteAddr();
		}
		return ip;
	}


//	　　获得客户端真实IP地址的方法二：
	public static String getIpAddr(HttpServletRequest request) {
	    String ip = request.getHeader("x-forwarded-for");  
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	        ip = request.getHeader("Proxy-Client-IP");  
	    }  
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	        ip = request.getHeader("WL-Proxy-Client-IP");  
	    }  
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	        ip = request.getRemoteAddr();  
	    }  
	    return ip;  
	}  
		
	//获得客户端真实IP地址的方法三：
	public static String getIp3(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if(StringUtils.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)){
            //多次反向代理后会有多个ip值，第一个ip才是真实ip
            int index = ip.indexOf(",");
            if(index != -1){
                return ip.substring(0,index);
            }else{
                return ip;
            }
        }
        ip = request.getHeader("X-Real-IP");
        if(StringUtils.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)){
            return ip;
        }
        return request.getRemoteAddr();
    }

	/**
	 * 【是否在ip白名单ip内】
	 * @author yangjh
	 * @param ip
	 * @param whiteListArg
	 * @return
	 */
	public static boolean isIpInWhitelist(String ip, String whiteListArg) {
		//允许所有
		if(whiteListArg.trim().equals("*")){
			return true;
		}
		boolean result = false;
		String[] whitelistArray = whiteListArg.split(";");
		for (String whitelist : whitelistArray) {
			whitelist = whitelist.trim();
			String[] whiteListConfig = whitelist.split("/");
			if (whiteListConfig.length == 1) {
				if (whiteListConfig[0].equals(ip)) {
					result = true;
					break;
				}
			} else if (whiteListConfig.length == 2) {
				try {
					int whiteListIp = getIpInt(whiteListConfig[0]);
					int requestIp = getIpInt(ip);
					int mask = 0xffffffff << (32 - Integer
							.valueOf(whiteListConfig[1]));
					if ((requestIp & mask) == (whiteListIp & mask)) {
						result = true;
						break;
					}
				} catch (UnknownHostException e) {
					throw new RuntimeException();
				}
			}
		}
		return result;
	}

	private static int getIpInt(String hostIp) throws UnknownHostException {
		byte[] address = InetAddress.getByName(hostIp).getAddress();
		int ipI = 0;
		ipI |= ((address[0] << 24) & 0xff000000);
		ipI |= ((address[1] << 16) & 0xff0000);
		ipI |= ((address[2] << 8) & 0xff00);
		ipI |= (address[3] & 0xff);
		return ipI;
	}
}
