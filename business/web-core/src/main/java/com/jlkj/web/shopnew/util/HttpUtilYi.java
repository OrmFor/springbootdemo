package com.jlkj.web.shopnew.util;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.*;
import java.net.*;
import java.nio.charset.Charset;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 工具类-Http请求
 */
public class HttpUtilYi {

	private static final Logger logger = LogManager.getLogger(HttpUtil.class);


	public static String getHttpResponse(String pageURL) {
		String pageContent = "";
		BufferedReader in = null;
		InputStreamReader isr = null;
		InputStream is = null;
		HttpURLConnection huc = null;
		try {
			URL url = new URL(pageURL);
			huc = (HttpURLConnection) url.openConnection();
			is = huc.getInputStream();
			isr = new InputStreamReader(is, Charset.forName("utf-8"));
			in = new BufferedReader(isr);
			String line = null;
			while (((line = in.readLine()) != null)) {
				if (line.length() == 0)
					continue;
				pageContent += line;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (is != null)
					is.close();
				if (isr != null)
					isr.close();
				if (in != null)
					in.close();
				if (huc != null)
					huc.disconnect();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return pageContent;
	}
	public static String executeHttpRequest(String requestUrl,String method, String requestBody) {

		String responseBody = "";
		CloseableHttpResponse resp = null;
		CloseableHttpClient httpclient = HttpClients.createDefault();
		try {
			if (HttpGet.METHOD_NAME.equals(method)) {
				HttpGet httpGet = new HttpGet(requestUrl);
				resp = httpclient.execute(httpGet);
			}
			if (HttpPost.METHOD_NAME.equals(method)) {
				HttpPost httpPost = new HttpPost(requestUrl);

				httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded");
				httpPost.setEntity(new StringEntity(requestBody, Charset.forName("utf-8")));

				resp = httpclient.execute(httpPost);
			}
			responseBody = EntityUtils.toString(resp.getEntity(), Charset.forName("utf-8"));
			resp.close();

		} catch (Exception e) {
			try {
				httpclient.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}

		return responseBody;
	}
	public static <T> T getJson(String url, Class<T> clazz) {
		HttpGet httpGet = new HttpGet(url);
		httpGet.addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
		httpGet.addHeader("Connection", "Keep-Alive");
		httpGet.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:29.0) Gecko/20100101 Firefox/29.0");
		httpGet.addHeader("Cookie", "");

		try {
			CloseableHttpClient httpclient = HttpClients.createDefault();
			CloseableHttpResponse response = httpclient.execute(httpGet);

			if (response == null) {
				return null;
			}
			String result = EntityUtils.toString(response.getEntity());
			return JSON.parseObject(result.toString().trim(), clazz);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String postHttpResponse(String pageURL, String parameter) {
		String xmlreturn = "time_out";
		HttpURLConnection connection = null;
		try {
			connection = (HttpURLConnection) (new URL(pageURL)).openConnection();
		} catch (Exception ex) {
			ex.printStackTrace();
			return "time_out";
		}
		if (connection != null) {
			try {
				connection.setRequestMethod("POST"); // POST为大写的方式才可以
				connection.setRequestProperty("content-type", "application/x-www-form-urlencoded");
				System.setProperty("sun.net.client.defaultConnectTimeout", "30000");// 设置超时时间
				System.setProperty("sun.net.client.defaultReadTimeout", "30000");
				connection.setDoOutput(true);
				OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
				writer.write(parameter);
				writer.flush();
				writer.close();
				BufferedReader read;
				read = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				String str;
				String tmp = "";
				while ((str = read.readLine()) != null) {
					tmp = tmp + str;
				}
				xmlreturn = tmp;
				read.close();// 关闭读取流
			} catch (Exception ex) {
				ex.printStackTrace();
				return "time_out";
			}
		} else {
			return "time_out";
		}
		connection.disconnect();// 断开连接
		return xmlreturn;
	}


	public static void httpPost(String url, List<BasicNameValuePair> formparams)
			throws Exception {
		// 创建默认的httpClient实例.
		CloseableHttpClient httpclient = HttpClients.createDefault();
		// 创建httppost
		HttpPost httppost = new HttpPost(url);
		RequestConfig requestConfig = RequestConfig.custom()
				.setConnectTimeout(30000)
				.setConnectionRequestTimeout(30000)
				.setSocketTimeout(30000)
				.build();// 设置超时
		httppost.setConfig(requestConfig);
		try {
			if (null != formparams) {
				UrlEncodedFormEntity uefEntity = new UrlEncodedFormEntity(
						formparams, "UTF-8");
				httppost.setEntity(uefEntity);
			}
			System.out.println("executing request " + httppost.getURI());
			CloseableHttpResponse response = httpclient.execute(httppost);
			String respData = null;
			try {
				HttpEntity entity = response.getEntity();
			} finally {
				response.close();
			}
		} finally {
			// 关闭连接,释放资源
			httpclient.close();
		}
	}

	/**
	 * http post请求传参的方法 返回实体
	 */
	public static CloseableHttpResponse httpPostWithPAaram(String url,
                                                           List<BasicNameValuePair> formparams,
                                                           String token, String tokenType) throws Exception {
		// 创建默认的httpClient实例.
		CloseableHttpClient httpclient = HttpClients.createDefault();
		CloseableHttpResponse response = null;
		// 创建httppost
		HttpPost httppost = new HttpPost(url);
		RequestConfig requestConfig = RequestConfig.custom()
				.setConnectTimeout(30000)
				.setConnectionRequestTimeout(10000)
				.setSocketTimeout(30000)
				.build();// 设置超时
		httppost.setConfig(requestConfig);
		if(StringUtils.isNotBlank(token) && StringUtils.isNotBlank(tokenType)){
			httppost.setHeader("Authorization",tokenType+" "+token);
		}
		//httppost.setHeader("Content-Type", "application/json; charset=UTF-8");

		try {
			if (null != formparams) {
				UrlEncodedFormEntity uefEntity = new UrlEncodedFormEntity(
						formparams, "UTF-8");
				httppost.setEntity(uefEntity);
			}
			response = httpclient.execute(httppost);
			return response;
		} catch (SocketException e) {
			logger.error("remote post socket exception", e);
		} catch (SocketTimeoutException e) {
			logger.error("remote post socket timeout exception", e);
		} catch (Exception e) {
			logger.error("remote post exception", e);
		}
		return response;
	}


	/**
	 * http post请求传参的方法 返回实体
	 */
	public static CloseableHttpResponse httpGet(String url, String token, String tokenType,
                                                Map<String, Object> param) throws Exception {
		// 创建默认的httpClient实例.
		CloseableHttpClient httpclient = HttpClients.createDefault();
		CloseableHttpResponse response = null;
		// 创建httpGet
		StringBuilder params=new StringBuilder();
		for(Map.Entry<String, Object> entry:param.entrySet()){
			params.append(entry.getKey());
			params.append("=");
			params.append(URLEncoder.encode(entry.getValue().toString(),
					"utf-8"));
			params.append("&");
		}
		if(params.length()>0){
			params.deleteCharAt(params.lastIndexOf("&"));
		}
		URL restServiceURL = new URL(url+(params.length()>0 ? "?"+params.toString() : ""));
		HttpGet httpGet = new HttpGet(restServiceURL.toURI());
		RequestConfig requestConfig = RequestConfig.custom()
				.setConnectTimeout(30000)
				.setConnectionRequestTimeout(10000)
				.setSocketTimeout(30000)
				.build();// 设置超时
		httpGet.setConfig(requestConfig);
		if(StringUtils.isNotBlank(token) && StringUtils.isNotBlank(tokenType)){
			httpGet.setHeader("Authorization",tokenType+" "+token);
		}
		httpGet.setHeader("Content-Type", "application/json; charset=UTF-8");


		try {
			response = httpclient.execute(httpGet);
			return response;
		} catch (SocketException e) {
			logger.error("remote post socket exception", e);
		} catch (SocketTimeoutException e) {
			logger.error("remote post socket timeout exception", e);
		} catch (Exception e) {
			logger.error("remote post exception", e);
		}
		return response;
	}

	/**
	 * get请求
	 *
	 * @param url
	 * @param param
	 * @return
	 */
	public static String get(String url,Map<String, Object> param) {
		String returnStr = "time_out";
		try {
			StringBuilder params=new StringBuilder();
			for(Map.Entry<String, Object> entry:param.entrySet()){
				params.append(entry.getKey());
				params.append("=");
				params.append(URLEncoder.encode(entry.getValue().toString(),
						"utf-8"));
				params.append("&");
			}
			if(params.length()>0){
				params.deleteCharAt(params.lastIndexOf("&"));
			}
			URL restServiceURL = new URL(url+(params.length()>0 ? "?"+params.toString() : ""));
			HttpURLConnection httpConnection = (HttpURLConnection) restServiceURL.openConnection();
			httpConnection.setRequestMethod("GET");
			httpConnection.setRequestProperty("Accept", "application/json");
			if (httpConnection.getResponseCode() != 200) {
				throw new RuntimeException("HTTP GET Request Failed with Error code : "
						+ httpConnection.getResponseCode());
			}

			InputStream in = httpConnection.getInputStream();
			byte[] buffer = new byte[1024];
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			for ( int len = 0; (len = in.read(buffer)) > 0;) {
				baos.write(buffer, 0, len);
			}
			String returnValue = new String(baos.toByteArray(), "utf-8" );
			returnStr = returnValue.toString();
			baos.flush();
			baos.close();
			in.close();
			httpConnection.disconnect();
		} catch (MalformedURLException e) {
			e.printStackTrace();
			return returnStr.toString();
		} catch (IOException e) {
			e.printStackTrace();
			return returnStr.toString();
		}
		return returnStr.toString();
	}
	/**
	 * post    请求
	 *
	 * @param url
	 * @param param
	 * @return
	 */
	public static String post(String url,Map<String, Object> param) {
		String returnStr = "time_out";
		try {
			StringBuilder params=new StringBuilder();
			for(Map.Entry<String, Object> entry:param.entrySet()){
				params.append(entry.getKey());
				params.append("=");
				params.append(entry.getValue().toString());
				params.append("&");
			}
			if(params.length()>0){
				params.deleteCharAt(params.lastIndexOf("&"));
			}
			URL restServiceURL = new URL(url+(params.length()>0 ? "?"+params.toString() : ""));
			HttpURLConnection httpConnection = (HttpURLConnection) restServiceURL.openConnection();
			httpConnection.setRequestMethod("POST");
			httpConnection.setRequestProperty("Accept", "application/json");
			// 设置是否从httpUrlConnection读入，默认情况下是true;
			httpConnection.setDoInput(true);
			// Post 请求不能使用缓存
			httpConnection.setUseCaches(false);
			if (httpConnection.getResponseCode() != 200) {
				throw new RuntimeException("HTTP POST Request Failed with Error code : "
						+ httpConnection.getResponseCode());
			}
			InputStream in = httpConnection.getInputStream();
			byte[] buffer = new byte[1024];
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			for ( int len = 0; (len = in.read(buffer)) > 0;) {
				baos.write(buffer, 0, len);
			}
			String returnValue = new String(baos.toByteArray(), "utf-8" );
			returnStr = returnValue.toString();
			baos.flush();
			baos.close();
			in.close();
			httpConnection.disconnect();
		} catch (MalformedURLException e) {
			e.printStackTrace();
			return returnStr.toString();
		} catch (IOException e) {
			e.printStackTrace();
			return returnStr.toString();
		}
		return returnStr.toString();
	}

	/**
	 * https请求处理
	 * @return
	 */
	public static CloseableHttpClient getHttpClient() {
		try {
			SSLContext sslContext = SSLContext.getInstance("SSL");
			sslContext.init(null, new TrustManager[]{new X509TrustManager() {
				@Override
				public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

				}

				@Override
				public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

				}

				@Override
				public X509Certificate[] getAcceptedIssuers() {
					return new X509Certificate[0];
				}
			}}, new SecureRandom());
			SSLConnectionSocketFactory socketFactory = new SSLConnectionSocketFactory(sslContext, NoopHostnameVerifier.INSTANCE);
			CloseableHttpClient closeableHttpClient = HttpClientBuilder.create().setSSLSocketFactory(socketFactory).build();
			return closeableHttpClient;
		} catch (Exception e) {
			return HttpClientBuilder.create().build();
		}
	}


/*	public static String executeHttpRequest(String requestUrl,String method, String requestBody) {
		String responseBody = "";
		CloseableHttpResponse resp = null;
		CloseableHttpClient httpclient =getHttpClient();
		try {
			if (HttpGet.METHOD_NAME.equals(method)) {
				HttpGet httpGet = new HttpGet(requestUrl);
				resp = httpclient.execute(httpGet);
			}
			if (HttpPost.METHOD_NAME.equals(method)) {
				HttpPost httpPost = new HttpPost(requestUrl);
				//httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded");
				httpPost.addHeader("Content-Type","multipart/form-data");
				httpPost.setEntity(new StringEntity(requestBody, Charset.forName("utf-8")));
				resp = httpclient.execute(httpPost);
			}
			responseBody = EntityUtils.toString(resp.getEntity(), Charset.forName("utf-8"));
			resp.close();

		} catch (Exception e) {
			try {
				httpclient.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}

		return responseBody;
	}*/
}
