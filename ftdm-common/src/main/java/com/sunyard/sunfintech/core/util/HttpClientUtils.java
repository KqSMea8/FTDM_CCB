package com.sunyard.sunfintech.core.util;

import java.io.*;
import java.net.URISyntaxException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.SSLContext;
import javax.servlet.http.HttpServletRequest;

import com.sunyard.sunfintech.core.exception.BusinessException;
import com.sunyard.sunfintech.core.exception.BusinessMsg;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.sunyard.sunfintech.core.base.BaseHttpResponse;


/**
 * Httpclient工具类 主意：该工具类基于httpclient 4.5.2 版本
 * 
 * @author heroy
 * @version 20170208
 */
public class HttpClientUtils {
	private static RequestConfig requestConfig;
	private static PoolingHttpClientConnectionManager cm;
	private static final String EMPTY_STR = "";
	private static final String UTF_8 = "UTF-8";
	private static final int MAX_TOTAL = 50; // 整个连接池最大连接数
	private static final int MAX_PER_ROUTE = 20; // 每路由最大连接数，默认值是2
	private static final int MAX_TIMEOUT=600000;//最大超时时间10秒

	private static void init() {
		//设置超时时间
		requestConfig = RequestConfig.custom()
				.setConnectionRequestTimeout(MAX_TIMEOUT)   // 设置从连接池获取连接实例的超时
				.setConnectTimeout(MAX_TIMEOUT)             // 设置连接超时
				.setSocketTimeout(MAX_TIMEOUT)              // 设置读取超时
				.setCookieSpec(CookieSpecs.STANDARD_STRICT) // Cookie策略
				.build();
		if (cm == null) {
			LayeredConnectionSocketFactory sslsf = null;
			try {
				sslsf = new SSLConnectionSocketFactory(SSLContext.getDefault());
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			}

			//注册https协议
			Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
					.register("https", sslsf).register("http", new PlainConnectionSocketFactory()).build();

			cm = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
			cm.setMaxTotal(MAX_TOTAL);
			cm.setDefaultMaxPerRoute(MAX_PER_ROUTE);
		}
	}

	/**
	 * 通过连接池获取HttpClient
	 * 
	 * @return HttpClient
	 */
	private static CloseableHttpClient getHttpClient() {
		init();
		return HttpClients.custom().setConnectionManager(cm).build();
	}

	/**
	 * GET请求
	 * 
	 * @param url 请求地址
	 * @return 返回信息
	 */
	public static BaseHttpResponse httpGetRequest(String url) {
		HttpGet httpGet = new HttpGet(url);
		return getResult(httpGet);
	}

	/**
	 * GET请求
	 * @param url 请求地址
	 * @param params 请求参数MAP
	 * @return SimpleHttpResponse
	 * @throws URISyntaxException
	 */
	public static BaseHttpResponse httpGetRequest(String url, Map<String, Object> params) throws URISyntaxException {
		URIBuilder ub = new URIBuilder();
		ub.setPath(url);

		ArrayList<NameValuePair> pairs = covertParams2NVPS(params);
		ub.setParameters(pairs);

		HttpGet httpGet = new HttpGet(ub.build());
		return getResult(httpGet);
	}

	/**
	 * GET请求
	 * @param url 请求地址
	 * @param headers 头信息
	 * @param params 参数信息
	 * @return SimpleHttpResponse
	 * @throws URISyntaxException
	 */
	public static BaseHttpResponse httpGetRequest(String url, Map<String, Object> headers, Map<String, Object> params)
			throws URISyntaxException {
		URIBuilder ub = new URIBuilder();
		ub.setPath(url);

		ArrayList<NameValuePair> pairs = covertParams2NVPS(params);
		ub.setParameters(pairs);

		HttpGet httpGet = new HttpGet(ub.build());
		for (Map.Entry<String, Object> param : headers.entrySet()) {
			httpGet.addHeader(param.getKey(), String.valueOf(param.getValue()));
		}
		return getResult(httpGet);
	}

	/**
	 * POST请求
	 * @param url 请求URL
	 * @return SimpleHttpResponse
	 */
	public static BaseHttpResponse httpPostRequest(String url) {
		HttpPost httpPost = new HttpPost(url);
		return getResult(httpPost);
	}

	/**
	 * POST请求
	 * @param url 请求URL
	 * @param params 请求的参数
	 * @return SimpleHttpResponse
	 * @throws UnsupportedEncodingException
	 */
	public static BaseHttpResponse httpPostRequest(String url, Map<String, Object> params) throws UnsupportedEncodingException,BusinessException {
		HttpPost httpPost = new HttpPost(url);
		ArrayList<NameValuePair> pairs = covertParams2NVPS(params);
		httpPost.setEntity(new UrlEncodedFormEntity(pairs, UTF_8));
		return getResult(httpPost);
	}

	/**
	 * POST请求
	 * @param url 请求URL
	 * @param headers 头信息
	 * @param params 请求参数
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static BaseHttpResponse httpPostRequest(String url, Map<String, Object> headers, Map<String, Object> params)
			throws UnsupportedEncodingException {
		HttpPost httpPost = new HttpPost(url);

		for (Map.Entry<String, Object> param : headers.entrySet()) {
			httpPost.addHeader(param.getKey(), String.valueOf(param.getValue()));
		}

		ArrayList<NameValuePair> pairs = covertParams2NVPS(params);
		httpPost.setEntity(new UrlEncodedFormEntity(pairs, UTF_8));

		return getResult(httpPost);
	}

	/**
	 * POST请求
	 * @param url 请求URL
	 * @param headers 头信息
	 * @param reqStr 请求参数,Json字符串格式
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static BaseHttpResponse httpPostRequest(String url,Map<String, Object> headers, String reqStr)
			throws UnsupportedEncodingException {
		HttpPost httpPost = new HttpPost(url);

		for (Map.Entry<String, Object> param : headers.entrySet()) {
			httpPost.addHeader(param.getKey(), String.valueOf(param.getValue()));
		}

		StringEntity stringEntiry = new StringEntity(reqStr, UTF_8);
		httpPost.setEntity(stringEntiry);
		return getResult(httpPost);
	}

	/**
	 * POST请求
	 * @param url 请求URL
	 * @param reqStr 请求参数,Json字符串格式
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static BaseHttpResponse httpPostRequest(String url, String reqStr)
			throws UnsupportedEncodingException {
		return httpPostRequest(url, new HashMap<String, Object>(), reqStr);
	}

	/**
	 * 把MAP转换成NameValuePair
	 * @param params 请求参数
	 * @return NameValuePair
	 */
	private static ArrayList<NameValuePair> covertParams2NVPS(Map<String, Object> params) {
		ArrayList<NameValuePair> pairs = new ArrayList<NameValuePair>();
		for (Map.Entry<String, Object> param : params.entrySet()) {
			pairs.add(new BasicNameValuePair(param.getKey(), String.valueOf(param.getValue())));
		}

		return pairs;
	}

	/**
	 * 处理Http请求
	 * 
	 * @param request 请求
	 * @return 返回字符串，需要使用者自己转化成json或者对象
	 */
	private static BaseHttpResponse getResult(HttpRequestBase request) throws BusinessException {
		// CloseableHttpClient httpClient = HttpClients.createDefault();
		CloseableHttpClient httpClient = getHttpClient();
		CloseableHttpResponse response = null;
		BaseHttpResponse result = null;
		try {
			request.setConfig(requestConfig);
			response = httpClient.execute(request);
			int statusCode = response.getStatusLine().getStatusCode();
			HttpEntity entity = response.getEntity();
			
			if (entity != null) {
				// long len = entity.getContentLength();// -1 表示长度未知
				result = new BaseHttpResponse(statusCode, EntityUtils.toString(entity,UTF_8), EMPTY_STR);
				
				//httpClient.close();
				EntityUtils.consume(entity);
				return result;
			}
		} catch (ClientProtocolException e) {
			System.out.println("【HTTP请求异常】=========="+e);
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("【HTTP请求异常】=========="+e);
			if(e.getCause()!=null){
				String msg=e.getCause().getMessage();
				System.out.println("【HTTP请求异常】=========="+msg);
				if(msg.contains("Connection timed out")){
					msg="请求第三方连接超时";
				}else if(msg.contains("Read timed out")){
					msg="请求第三方超时";
				}else if(msg.contains("Connection refused")){
					msg="连接被拒绝";
				}else if(msg.contains("Connection reset")){
					msg="连接被重置";
				}
				throw new BusinessException(BusinessMsg.CALL_REMOTE_ERROR,msg);
			}
			e.printStackTrace();
		} finally {
			if(response!=null){
				try {
					response.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return null;
	}


	/**
	 * 构造函数
	 * @param httpServletRequest
	 * @return StringBuilder
	 */
	public static StringBuilder baseHttpResponseToString(HttpServletRequest httpServletRequest) {
		BufferedReader bufferedReader = null;
		StringBuilder stringBuilder = new StringBuilder();
		try {
			InputStream inputStream = httpServletRequest.getInputStream();

			if (inputStream != null) {
				bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
				char[] charBuffer = new char[128];
				int bytesRead = -1;
				while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
					stringBuilder.append(charBuffer, 0, bytesRead);
				}
			} else {
				stringBuilder.append("");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return stringBuilder;
	}

}
