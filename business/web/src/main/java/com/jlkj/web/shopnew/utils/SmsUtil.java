//package com.jlkj.web.shopnew.utils;
//
//import java.util.Set;
//import java.util.concurrent.TimeUnit;
//
//
//import com.jlkj.web.shopnew.exception.Errors;
//import com.jlkj.web.shopnew.exception.SmsSendExceptionNew;
//import com.jlkj.web.shopnew.utils.redis.RedisUtil;
//
//public class SmsUtil {
//	/**
//	 * 获取短信发送的验证码
//	 * @return
//	 */
//	public static String getSmsCode(){
//		String code = (int)((Math.random()*9+1)*100000)+"";
//		return code;
//	}
//
//
//	/**
//	 * 构建短信发送信息
//	 * @param mobile
//	 * @param code
//	 * @param type
//	 * @param event
//	 * @return
//	 */
//	@Deprecated
//	public static SmsSendInfo buildSmsSendInfo(String mobile, String code, SmsType type, SmsEventEnum event) {
//		//构建短信发送信息
//		SmsSendInfo sms = new SmsSendInfo() ;
//		sms.setEvent(event);
//		sms.setType(type);
//		sms.setRecvMobile(mobile);
//		sms.setContent(code);
//		return sms ;
//	}
//
//
//	public static SmsSendInfo buildSmsSendInfoWithTemplate(String mobile, String template, String content, SmsType type, SmsEventEnum event) {
//		//构建短信发送信息
//		SmsSendInfo sms = new SmsSendInfo() ;
//		sms.setEvent(event);
//		sms.setType(type);
//		sms.setRecvMobile(mobile);
//		sms.setContent(content);
//		return sms ;
//	}
//
//
//
//
//	/**
//	 * 相关的规则如下：
//		 1. 把短信验证码放到redis上 存储 ( "sms" + smsToken 为key  , value为短信验证码的值 ) , 失效时间为30分钟
//		 2. 重复发送之间的间隔为1分钟
//		 3. 在1小时内的最多发送20次
//		 4. 输入错误5次，短信码失效
//	 * 判断当前手机是否允许发送验证码
//	 */
//	public static void checkSendPermission(String mobile  ,RedisUtil redisUtil) throws SmsSendExceptionNew {
//
//		//首先检查距上次发送的进间是否在一分钟之内
//		if( redisUtil.exist( mobile + ".lastsendflag" ) ){
//			throw new SmsSendExceptionNew(Errors.SmsError.smsSendIntervalTooShort) ;
//		}
//
//		Set set = redisUtil.keys(mobile + ".lastonehour." + "*") ;
//		//在1小时内的最多发送20次
//		if( set != null && set.size() > 20){
//			throw new SmsSendExceptionNew(Errors.SmsError.smsSendTimesTooMany) ;
//		}
//	}
//
//	public static void cleanSmsToken(String mobile, RedisUtil redisUtil){
//		redisUtil.delete(getSmsToken(mobile));
//	}
//
//	public static String getSmsToken(String mobile){
//		return SmsConstant.SMS_CODE_REDIS_KEY_PREFIX  + mobile + ".smsCode"  ;
//	}
//
//	/**
//	 * 添加短信码的发送统计记录
//	 * @param mobile
//	 * @param smsCode
//	 * @param redisUtil
//	 */
//	public static void addSmsCodeSendStatistics(String mobile , String smsCode ,RedisUtil redisUtil){
//		String smsToken = getSmsToken(mobile) ;
//		//设置短信验证码30分钟内有效
//		redisUtil.addWithTimeout(smsToken, smsCode, SmsConstant.SMS_INVALID, TimeUnit.MINUTES);
//		//设置短信发送间隔为1分钟
//		redisUtil.addWithTimeout(mobile + ".lastsendflag", smsCode, 1,  TimeUnit.MINUTES);
//		//设置短信发送1小时标志
//		long timestamp = System.currentTimeMillis() ;
//		redisUtil.addWithTimeout(mobile + ".lastonehour." + timestamp , "" , 1 , TimeUnit.HOURS);
//	}
//
//	public static String getSmsCodeCachedKey(String mobile) {
//		return "findpwd.smscode." + mobile;
//	}
//	public static String getErrorCachedKey(String mobile) {
//		return "findpwd.error." + mobile;
//	}
//}
