package com.jlkj.web.shopnew.util;

import cc.s2m.web.utils.webUtils.StaticProp;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.apache.commons.codec.binary.Base64;
import org.apache.http.Header;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.message.BasicNameValuePair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.util.*;

/**
 * 
 * @author Iren08
 * @date 2017年3月8日 下午12:51:48
 * @version 1.0
 */
@Component
public class LmAppUtil {

	private static final Logger logger = LogManager.getLogger(LmAppUtil.class);


	/**
	 * 网关直连公用
	 * 生成参数<br>
	 * 签名加密
	 */
	public static Map<String, String> createPostParam(String serviceName,
			Map<String, Object> reqDataMap) throws GeneralSecurityException {
		Map<String, String> result = new HashMap<String, String>();

		String privateStr = getLmPrivateKey();
		reqDataMap.put("timestamp", DateUtil.dateStr3(new Date()));
		String reqData = JSON.toJSONString(reqDataMap, SerializerFeature.DisableCircularReferenceDetect);
		//此处打印加密前业务数据
		logger.info("请求参数serviceName:" + serviceName);
		
		PrivateKey privateKey = LmSignatureUtils.getRsaPkcs8PrivateKey(Base64
				.decodeBase64(privateStr));
		byte[] sign = LmSignatureUtils.sign(LmSignatureAlgorithm.SHA1WithRSA,
				privateKey, reqData);

		// 拼装网关参数
		result.put("serviceName", serviceName);
		result.put("platformNo", getPlatformNo());
		result.put("reqData", reqData);
		result.put("keySerial", getKeySerial());
		result.put("sign", Base64.encodeBase64String(sign));
		logger.info("===请求报文===" + result);
/*		try {
			//添加网关请求记录
			RequestToBankLog requestToBankLog = new RequestToBankLog();
			if(LmServiceNameEnum.DOWNLOAD_CHECKFILE.getName().equalsIgnoreCase(serviceName)){
				requestToBankLog.setFlag(4);//文件下载
			}else {
				requestToBankLog.setFlag(0);//网关请求
				JSONObject jsonObject = (JSONObject) JSON.toJSON(reqDataMap);
				if(jsonObject!=null){
					requestToBankLog.setRequestNo(jsonObject.getString("requestNo"));
				}
			}
			requestToBankLog.setServiceName(serviceName);
			requestToBankLog.setReqData(JSON.toJSONString(result));//存储完整报文
			requestToBankLogImpl.insertSelective(requestToBankLog);
		} catch (Exception e) {
			logger.error("添加请求日志失败：" + e, e);
		}*/
		return result;
	}
	
	/**
	 * @Description 直连，同步返回结果
	 * @param reqCreatedData
	 * @return json,为空表示验签失败，业务处理失败依旧返回报文，自行判断
	 */
	public static JSONObject directPost(String serviceName,Map<String, Object> reqCreatedData){
		Map<String, String> reqData;
		String result = null;
		JSONObject json = null;
		try {
			reqData = createPostParam(serviceName, reqCreatedData);
			String url = getServiceUrl();
			CloseableHttpResponse response = null;
			List<BasicNameValuePair> formparams = new ArrayList<BasicNameValuePair>();
			BasicNameValuePair bn2 = new BasicNameValuePair("serviceName",reqData.get("serviceName"));
			BasicNameValuePair bn5 = new BasicNameValuePair("platformNo",reqData.get("platformNo"));
			BasicNameValuePair bn3 = new BasicNameValuePair("reqData",reqData.get("reqData"));
			BasicNameValuePair bn = new BasicNameValuePair("keySerial",reqData.get("keySerial"));
			BasicNameValuePair bn4 = new BasicNameValuePair("sign",reqData.get("sign"));
			formparams.add(bn);formparams.add(bn2);formparams.add(bn3);formparams.add(bn4);formparams.add(bn5);
			/*try {
				response = LmHttpUtils.httpPostWithPAaram(url,formparams);
				if (response != null)
					result = IOUtils.toString(response.getEntity().getContent(), "UTF-8");
				logger.info("===直连返回(未验签):==="+result);

				//添加请求日志
				RequestToBankLog cond = new RequestToBankLog();
				JSONObject jsonObject = (JSONObject) JSON.toJSON(reqCreatedData);
				if(jsonObject!=null){
					cond.setRequestNo(jsonObject.getString("requestNo"));
				}
				RequestToBankLog log = requestToBankLogImpl.getByCondition(cond);
				if(log!=null) {
					RequestToBankLog update = new RequestToBankLog();
					if(!LmServiceNameEnum.CONFIRM_CHECKFILE.getName().equalsIgnoreCase(serviceName)) {
                        update.setFlag(1);
                    }else{
					    update.setFlag(3);
                    }
					update.setId(log.getId());
					update.setRespData(result);
					requestToBankLogImpl.updateByPrimaryKeySelective(update);
				}

			} catch (Exception e) {
				logger.error("===直连请求异常:===="+e.getMessage(),e);
			}finally {
				IOUtils.closeQuietly(response);
			}*/
			//验签
			if(verifySign(response, result)){
				json = JSONObject.parseObject(result);
			}
		} catch (GeneralSecurityException e1) {
			logger.error("直连请求签名加密异常");
			logger.info("====直连加密前报文====="+reqCreatedData);
			e1.printStackTrace();
		}
		return json;
	}

	/**
	 *@description 对账下载
	 *@param
	 *@return
	 */

	public static String getLmPrivateKey(){
		/**
		* lm.privateKey=MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCqS7SctMIPfTeH1cFgoETs60/HWsVw2rIX8H7NxgEbE0MgvBjPOVCnws6UyYyBacymkPyA6nPDi0ZFgtyTwuhYVdVFIU4CoVNI/q3Upds2AS+CQcei03UaQ7BnISjB9nNilfNTfA1zx9FqJwzVBbdMr64XukEbWogVAGZtrLj7zyetZmbJfC71RlDGi20y3phQ7MXOnQf62X20vSEr3jKzEIXEZCl0rVnPpzLN9YGBZ9FHxQf+CyJT+4lbnC46bCHR90v5qZsKkYXne2WmmtgFI/jRV7JZddJbzz7cDJTFQn94nrUsMdSFHee/hGbfaXCM6wFchfMUmmr0BQFUDNMxAgMBAAECggEAS4mVLcWIJvK2BQyt4Dx3FlgyZQ1AcxSu1quYs5HWv7nFlumYX6LewetJUvor1uOlIWVCe7SDcQOAQikPyoM0KKq6FRX20nfYx5IgbSi8mbHg7vhao0EEuoe34ab1u0OkXuCAtFFcbjT+EQYNTRhh/CQZJvE5G/cGUbqBtLhevWKjexVHtpOakzOHj/XO8GKVmhm7RqoFSOJVU/6oW4+sqPqWWwxSYbzlW1OMvrtgjMA2RpdQpQ5KxL3zwx/w4yW2yB9DpX5rtO1EFxLkKDjNbteaB+JiEOprECAoiJFVCIYz9pd/3wAvVO177WOnFlFm6Lofg/TvPJgFck/SdzryWQKBgQD8QiJcmoLSTP+K2MexH0cLeLvBiWRCRuX6jepNHJHJ7ukBPgsnEf1xHo/7+llu70n0TkZNv+nbYDqcSP7hh9X4PeNfTFqGpJM0Jf1FVAHnRzVc5Zv8Q62Pc1UvmdfhKvZuZSMIzD7RP2IOyEaoBkcqRMiUTc8OTtz8G8aHVOodQwKBgQCs0liRslFTuHqrwscSuqb6nkBu6Vw+n5yplF/ZjZA3TdtUx7PKPF5Mw9KTtpm8g6Hg7ix3K6Dh5jQAs4s/G9EhVk9RAncsUnhUtjZBejwtdLUGjWnvpkEwqBd+TM9TnXBcsqdHOIycH3RMIJTxC2VnN2AxA+VzdOwJJXJfzB3sewKBgQDwdAHZD5c/6xFzQmwnE89eyfj+5H3jBz3U9U2vr1nwiIEmJAPxfB5/o0hQQjTZn9J4w9wT5Kl/6Vw0+Qer5xU4iZWgk2F/EoUWolenrAoccS4WnO9xKPnZ0XAT7atwAMniNRmOG1KNlsNbn+Bp7YIMcoBXjUufMeFHlxpIidKXjQKBgQCdoer5Ub2OcIkrm6i6s5dUpv28bIwf7rQVlWlr5Qyy9Qfmp5SdFT0qJb8vznEhmqsPoA7cz+WZxsCOFzYeZP/2uWP1TxtmSP7Kvbx1Msoq5/WqOVh0J5/0go2TPPoqmUMAuk+Lzc+rI6GPQnS3/B4M7FyHuLcT/YTBvu8449QzewKBgAvAlnZ0iLcdqnw8269SAyePUOiORG1hoCzNLH/jmfcMqX+OJmT0ZWsGKkZ3HBX8r04w/1oB837YD1NDZgluuUQ8A1Nn9sXjkMrqcVhLmTk7xdmS1yPFovU0g7/QHitQVQ/cjz4Nng3w9srhamaXYXUFYuQ75hwl7gB0EQdiBec/
		 *
		 * #公钥,提供给存管
		 * lm.publicKey=MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAqku0nLTCD303h9XBYKBE7OtPx1rFcNqyF/B+zcYBGxNDILwYzzlQp8LOlMmMgWnMppD8gOpzw4tGRYLck8LoWFXVRSFOAqFTSP6t1KXbNgEvgkHHotN1GkOwZyEowfZzYpXzU3wNc8fRaicM1QW3TK+uF7pBG1qIFQBmbay4+88nrWZmyXwu9UZQxottMt6YUOzFzp0H+tl9tL0hK94ysxCFxGQpdK1Zz6cyzfWBgWfRR8UH/gsiU/uJW5wuOmwh0fdL+ambCpGF53tlpprYBSP40VeyWXXSW88+3AyUxUJ/eJ61LDHUhR3nv4Rm32lwjOsBXIXzFJpq9AUBVAzTMQIDAQAB
		**/
		return "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCqS7SctMIPfTeH1cFgoETs60/HWsVw2rIX8H7NxgEbE0MgvBjPOVCnws6UyYyBacymkPyA6nPDi0ZFgtyTwuhYVdVFIU4CoVNI/q3Upds2AS+CQcei03UaQ7BnISjB9nNilfNTfA1zx9FqJwzVBbdMr64XukEbWogVAGZtrLj7zyetZmbJfC71RlDGi20y3phQ7MXOnQf62X20vSEr3jKzEIXEZCl0rVnPpzLN9YGBZ9FHxQf+CyJT+4lbnC46bCHR90v5qZsKkYXne2WmmtgFI/jRV7JZddJbzz7cDJTFQn94nrUsMdSFHee/hGbfaXCM6wFchfMUmmr0BQFUDNMxAgMBAAECggEAS4mVLcWIJvK2BQyt4Dx3FlgyZQ1AcxSu1quYs5HWv7nFlumYX6LewetJUvor1uOlIWVCe7SDcQOAQikPyoM0KKq6FRX20nfYx5IgbSi8mbHg7vhao0EEuoe34ab1u0OkXuCAtFFcbjT+EQYNTRhh/CQZJvE5G/cGUbqBtLhevWKjexVHtpOakzOHj/XO8GKVmhm7RqoFSOJVU/6oW4+sqPqWWwxSYbzlW1OMvrtgjMA2RpdQpQ5KxL3zwx/w4yW2yB9DpX5rtO1EFxLkKDjNbteaB+JiEOprECAoiJFVCIYz9pd/3wAvVO177WOnFlFm6Lofg/TvPJgFck/SdzryWQKBgQD8QiJcmoLSTP+K2MexH0cLeLvBiWRCRuX6jepNHJHJ7ukBPgsnEf1xHo/7+llu70n0TkZNv+nbYDqcSP7hh9X4PeNfTFqGpJM0Jf1FVAHnRzVc5Zv8Q62Pc1UvmdfhKvZuZSMIzD7RP2IOyEaoBkcqRMiUTc8OTtz8G8aHVOodQwKBgQCs0liRslFTuHqrwscSuqb6nkBu6Vw+n5yplF/ZjZA3TdtUx7PKPF5Mw9KTtpm8g6Hg7ix3K6Dh5jQAs4s/G9EhVk9RAncsUnhUtjZBejwtdLUGjWnvpkEwqBd+TM9TnXBcsqdHOIycH3RMIJTxC2VnN2AxA+VzdOwJJXJfzB3sewKBgQDwdAHZD5c/6xFzQmwnE89eyfj+5H3jBz3U9U2vr1nwiIEmJAPxfB5/o0hQQjTZn9J4w9wT5Kl/6Vw0+Qer5xU4iZWgk2F/EoUWolenrAoccS4WnO9xKPnZ0XAT7atwAMniNRmOG1KNlsNbn+Bp7YIMcoBXjUufMeFHlxpIidKXjQKBgQCdoer5Ub2OcIkrm6i6s5dUpv28bIwf7rQVlWlr5Qyy9Qfmp5SdFT0qJb8vznEhmqsPoA7cz+WZxsCOFzYeZP/2uWP1TxtmSP7Kvbx1Msoq5/WqOVh0J5/0go2TPPoqmUMAuk+Lzc+rI6GPQnS3/B4M7FyHuLcT/YTBvu8449QzewKBgAvAlnZ0iLcdqnw8269SAyePUOiORG1hoCzNLH/jmfcMqX+OJmT0ZWsGKkZ3HBX8r04w/1oB837YD1NDZgluuUQ8A1Nn9sXjkMrqcVhLmTk7xdmS1yPFovU0g7/QHitQVQ/cjz4Nng3w9srhamaXYXUFYuQ75hwl7gB0EQdiBec/";
	}
	
	public static String getPlatformNo(){
		return StaticProp.sysConfig.get("lm.platformNo");
	}
	
	public static String getKeySerial(){
		return StaticProp.sysConfig.get("lm.keySerial");
	}
	
	public static String getServiceUrl(){
		return StaticProp.sysConfig.get("lm.serviceUrl");
	}

	public static String getUploadUrl(){
		return StaticProp.sysConfig.get("lm.uploadUrl");
	}

	public static String getDownloadUrl(){
	    return StaticProp.sysConfig.get("lm.downloadUrl");
    }
	
	public static String getLmPublicKey(){
		return StaticProp.sysConfig.get("lm.lmpublicKey");
	}

	public static String getGatewayUrl(){
		return StaticProp.sysConfig.get("lm.gatewayUrl");
	}
	
	public static boolean verifySign(CloseableHttpResponse response, String responseData) {
		
		Map<String, Object> respMap = JSON.parseObject(responseData);
		//接口返回code!=0 || status!=SUCCESS时，不做验签处理
		if( !"0".equals(respMap.get("code")) || 
				!"SUCCESS".equals(respMap.get("status"))) {
			return true;
		}
		
		//校验返回报文
		String returnSign = "";
		Header[] headers = response.getHeaders("sign");
		if (headers != null && headers.length > 0) {
			Header header = headers[0];
			returnSign = header.getValue();
		}
		
		byte[] keyByte = Base64.decodeBase64(getLmPublicKey());
		byte[] signByte = Base64.decodeBase64(returnSign);
		try {
			PublicKey publicKey = LmSignatureUtils.getRsaX509PublicKey(keyByte);

			boolean b;
			try {
				b = LmSignatureUtils.verify(LmSignatureAlgorithm.SHA1WithRSA, publicKey, responseData, signByte);
				if (!b) {
					logger.error("验签失败，sign与respData不匹配");
					return false;
				}else{
					logger.info("sign success ...");
					return true;
				}
			} catch (UnsupportedEncodingException e) {
				logger.error("验签错误" + e.getMessage(), e);
				return false;
			}
			
		} catch (InvalidKeySpecException e) {
			logger.error("验签错误，生成商户公钥失败",e);
			return false;
		} catch (NoSuchAlgorithmException e) {
			logger.error("验签错误" + e.getMessage(), e);
			return false;
		} catch (GeneralSecurityException e) {
			logger.error("验签错误" + e.getMessage(), e);
			return false;
		}
	}



}
