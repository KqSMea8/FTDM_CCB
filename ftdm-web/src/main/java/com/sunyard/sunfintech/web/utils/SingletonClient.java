package com.sunyard.sunfintech.web.utils;


import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;


public class SingletonClient {
	private static Client client=null;
	private SingletonClient(){
	}
	public synchronized static Client getClient(String softwareSerialNo,String key){
		if(client==null){
			try {
				client=new Client(softwareSerialNo,key);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return client;
	}
	public synchronized static Client getClient(){
		ResourceBundle bundle=PropertyResourceBundle.getBundle("emay-config");
		if(client==null){
			try {
				client=new Client(bundle.getString("softwareSerialNo"),bundle.getString("key"));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return client;
	}
	
	
}
