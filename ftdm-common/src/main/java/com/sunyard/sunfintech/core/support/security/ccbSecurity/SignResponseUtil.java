package com.sunyard.sunfintech.core.support.security.ccbSecurity;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sunyard.sunfintech.core.exception.BusinessException;
import com.sunyard.sunfintech.core.util.SunSignatureUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Field;
import java.util.*;


/**
 * 给商户发送响应消息之前,对响应消息进行加密
 * @author Juffy
 * @version 20170123
 */
public class SignResponseUtil {
	/**
	 * 对map进行加密
	 * @param map
	 * @param KEY
	 * @return
	 */
	public static String getSignByMap(Map<String, Object> map, String KEY) {
		//去掉签名串
		map.remove("cert_sign");
		map.remove("sign");
		//排序
		TreeMap<String, Object> SignMap = new TreeMap<String, Object>();
		SignMap.putAll(map);

		//拼接属性值
		String str = "";
		for (Object v : SignMap.values()) {
			//忽略null值
			if(null==v || "".equals(v)){
				continue;
			}
			str +=v.toString().trim()+"|";
		}

		str = str.substring(0, str.length()-1);

		//进行加密
		//进行加密
		/*
		 * modified by yanglei 20170317
		 * 修改加密方式由原先的MD5 修改位3DES
		 * MD5Helper md = MD5Helper.getInstance();
		 * String cert_sign = md.signData(str);
		 */
		String cert_sign = SecurityKeyUtil.getSignString(str, KEY);
		return cert_sign;

	}

	/**
	 * 对象属性排序之后加密产生加密字符串
	 * @param object
	 * @param KEY 机密密钥
	 * @return
	 */
	public static String getSignByObject(Object object, String KEY) {

		List<Field> fieldList = new ArrayList<>() ;
		Class<? extends Object> tempClass = (Class<? extends Object>)object.getClass();
		while (tempClass != null) {//当父类为null的时候说明到达了最上层的父类(Object类).
			fieldList.addAll(Arrays.asList(tempClass .getDeclaredFields()));
			tempClass = tempClass.getSuperclass(); //得到父类,然后赋给自己
		}

		//用TreeMap排序
		TreeMap<String, Object> map = new TreeMap<String, Object>();
		for (Field f : fieldList) {
			//遍历属性
			//忽略编译产生的属性
			if (f.isSynthetic()) {
				continue;
			}
			//忽略serialVersionUID
			if (f.getName().equals("serialVersionUID")) {
				continue;
			}
			//忽略签名串
			if (f.getName().equals("cert_sign")) {
				continue;
			}
			f.setAccessible(true); //设置些属性是可以访问的
			try {
				//把属性添加到map里
				map.put(f.getName(),f.get(object));
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
				throw new BusinessException("签名失败");
			} catch (IllegalAccessException e) {
				e.printStackTrace();
				throw new BusinessException("签名失败");
			}
		}
		//拼接属性值
		String str = "";
		for (Object v : map.values()) {
			//忽略null值
			if(null==v || "".equals(v)){
				continue;
			}
			str +=v.toString().trim()+"|";
		}
		str = str.substring(0, str.length()-1);

		//进行加密
		/*
		 * modified by yanglei 20170317
		 * 修改加密方式由原先的MD5 修改位3DES
		 * MD5Helper md = MD5Helper.getInstance();
		 * String cert_sign = md.signData(str);
		 */
		String cert_sign = SecurityKeyUtil.getSignString(str, KEY);
		return cert_sign;

	}
	public static void main(String[] args){


		Map<String, Object> map = new HashMap<>();
		map.put("summary","0");
		map.put("partner_serial_no","201709131645120510407135");
		map.put("partner_trans_date","20170913");
		map.put("partner_trans_time","164585");
		map.put("pickup_url","https://www.cgtz.com");
		map.put("prod_code","C00002");
		map.put("prod_name","网关充值");
		map.put("partner_id","20170619789345");
		map.put("card_no","6236681940006713577");
		map.put("receive_url","http://114.55.158.208:8885/ftdm-web/payment/notity_payment");
		map.put("client_property","0");
		map.put("channelId","019");
		map.put("occur_balance","1");
		map.put("bank_id","");

		String jsonstring = JSON.toJSONString(map);

		String key ="GhWSW7kmTCoOAcTmTPvBFUkZpMRJ31jC";
		String signData= SignResponseUtil.getSignByMap(map,key);

		System.out.print("ding    "+signData);
		System.out.print("ding    "+jsonstring);
	}
	

}
