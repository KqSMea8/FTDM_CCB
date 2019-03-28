package com.sunyard.sunfintech.core.util;

import org.apache.commons.lang3.time.FastDateFormat;

import java.util.*;

/**
 * 用于生产序列号
 * 
 * @author heroy
 *
 */
public final class SeqUtil {

	public static final String RANDOM_KEY = String.valueOf((System.currentTimeMillis())%16416 + 100000);

	private static int COUNT = 10;

	private SeqUtil() {

	}
	static{
		System.out.println("【本机随机码】：" + RANDOM_KEY);
	}

	/**
	 * 返回4位随机数
	 */
	public static String getRadomNum() {
		int[] array = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 };
		Random rand = new Random();
		for (int i = 10; i > 1; i--) {
			int index = rand.nextInt(i);
			int tmp = array[index];
			array[index] = array[i - 1];
			array[i - 1] = tmp;
		}
		String result = "";
		for (int i = 0; i < 4; i++)
			result += String.valueOf(array[i]);
		return result;
	}

	/**
	 * 返回内部流水号 宝付的提交流水号 连连的提交流水号
	 */
	public  static String getSeqNum() {
		// 获取当前系统时间,截止到秒
        StringBuffer stringBuffer = new StringBuffer(FastDateFormat.getInstance("yyyyMMddHHmmssSSSS").format(Calendar.getInstance()))
				.append(RANDOM_KEY).append(SeqUtil.getRadomNum()).append(COUNT);
		//最后两位计数
        if(COUNT>=99)COUNT = 10;COUNT ++;
		return stringBuffer.toString();
	}

	/**
	 * 返回内部序列号 UUID 32位
	 */
	public static String getUUID() {
		return UUID.randomUUID().toString().replace("-", "");
	}
	
	public static void main(String[] args){
		for(int i=0; i<2000 ;i++){

			try {
				System.out.println(SeqUtil.getSeqNum());
//				System.out.println((System.currentTimeMillis())%16416 + 100000);
//				System.out.println(getUUID());
				Thread.sleep(0);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}


