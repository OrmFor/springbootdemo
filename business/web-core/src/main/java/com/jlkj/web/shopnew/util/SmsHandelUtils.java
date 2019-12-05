//package com.jlkj.web.shopnew.util;
//
//import java.nio.charset.Charset;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.concurrent.TimeUnit;
//
//import com.jlkj.web.shopnew.enums.EnumNoticeNid;
//import com.jlkj.web.shopnew.enums.EnumNoticeSend;
//import com.jlkj.web.shopnew.enums.EnumNoticeStatus;
//import com.jlkj.web.shopnew.enums.EnumNoticeType;
//import com.jlkj.web.shopnew.exception.BussinessExceptionNew;
//import com.springbootproject.jlkj.exception.BussinessExceptionNew;
//import org.apache.http.NameValuePair;
//import org.apache.http.client.config.RequestConfig;
//import org.apache.http.client.entity.UrlEncodedFormEntity;
//import org.apache.http.client.methods.CloseableHttpResponse;
//import org.apache.http.client.methods.HttpPost;
//import org.apache.http.entity.ContentType;
//import org.apache.http.impl.client.CloseableHttpClient;
//import org.apache.http.impl.client.HttpClients;
//import org.apache.http.message.BasicNameValuePair;
//import org.apache.http.util.EntityUtils;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//import org.aspectj.bridge.IMessage;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import com.google.common.base.Strings;
//import com.google.common.collect.Maps;
//import com.yinmimoney.web.p2pnew.core.StatusCode;
//import com.yinmimoney.web.p2pnew.exception.BussinessExceptionNew;
//
//import cc.s2m.util.HttpUtil;
//import cc.s2m.web.utils.webUtils.StaticProp;
//
///**
// * 短信相关统一公用类
// */
//public class SmsHandelUtils {
//
//	private static final Logger LOGGER = LogManager.getLogger(SmsHandelUtils.class);
//
//	@Autowired
//	private ISConfig sConfigService;
//	@Autowired
//	private INoticeType noticeTypeService;
//	@Autowired
//	private INotice noticeService;
//	@Autowired
//	private IMessage messageService;
//	@Autowired
//	private RedisUtil redisUtil;
//
//	// 短信接口 助通
//	private static final String SMS_URL = StaticProp.sysConfig.get("ZhuTong.url");
//	private static final String SMS_USERNAME = StaticProp.sysConfig.get("ZhuTong.username");
//	private static final String SMS_PWD = StaticProp.sysConfig.get("ZhuTong.pwd");
//	private static final String SMS_PID = StaticProp.sysConfig.get("ZhuTong.productid");
//	// 短信接口 中聚元
//	private static final String ZJYSMS_URL = StaticProp.sysConfig.get("ZhongJuYuan.url");
//	private static final String ZJYSMS_USERNAME = StaticProp.sysConfig.get("ZhongJuYuan.username");
//	private static final String ZJYSMS_PWD = StaticProp.sysConfig.get("ZhongJuYuan.pwd");
//	private static final String ZJYSMS_YX_USERNAME = StaticProp.sysConfig.get("ZhongJuYuan.yx.username");
//	private static final String ZJYSMS_YX_PWD = StaticProp.sysConfig.get("ZhongJuYuan.yx.pwd");
//	// 以下梦网接口账户
//	private static final String MWSMS_URL = StaticProp.sysConfig.get("MengWang.url");
//	private static final String MWSMS_USERID = StaticProp.sysConfig.get("MengWang.userId");
//	private static final String MWSMS_PASSWORD = StaticProp.sysConfig.get("MengWang.password");
//	private static final String MWSMS_MSGID = StaticProp.sysConfig.get("MengWang.MsgId");
//	private static final String MWSMS_ENVIRONMENT = StaticProp.sysConfig.get("MengWang.environment");
//	// 以下梦网营销账户
//	private static final String MWSMS_YX_URL = StaticProp.sysConfig.get("MengWang.yx.url");
//	private static final String MWSMS_YX_USERID = StaticProp.sysConfig.get("MengWang.yx.userId");
//	private static final String MWSMS_YX_PASSWORD = StaticProp.sysConfig.get("MengWang.yx.password");
//	private static final String MWSMS_YX_MSGID = StaticProp.sysConfig.get("MengWang.yx.MsgId");
//	private static final String MWSMS_YX_ENVIRONMENT = StaticProp.sysConfig.get("MengWang.yx.environment");
//
//	private SmsHandelUtils() {
//	}
//
//	/**
//	 *
//	 * @Title checkMobileCodeIsEqual
//	 * @Description 校验手机短信验证码
//	 * @author wzq
//	 * @date 2018年6月28日 下午5:38:29
//	 * @param mobilePhone 手机号
//	 * @param code 手机短信验证码
//	 * @return
//	 * @return boolean
//	 */
//	public boolean checkMobileCodeIsEqual(String mobilePhone, String code) {
//		String code_error = (String) redisUtil.find(EnumRedisKeys.MOBILE_CODE + mobilePhone + "_error");
//		if (!Strings.isNullOrEmpty(code_error)) {
//			long errorNumber = Long.parseLong(code_error);
//			if (errorNumber >= 5) {
//				// 错误次数上限
//				redisUtil.delete(EnumRedisKeys.MOBILE_CODE + mobilePhone);
//				redisUtil.delete(EnumRedisKeys.MOBILE_CODE + mobilePhone + "_error");
//				throw new BussinessExceptionNew(StatusCode.ERROR_MOBILE_CODE_EXPIRED);
//			}
//		}
//		String code_ = (String) redisUtil.find(EnumRedisKeys.MOBILE_CODE + mobilePhone);
//		if (Strings.isNullOrEmpty(code_)) {
//			throw new BussinessExceptionNew(StatusCode.ERROR_MOBILE_CODE_EXPIRED);
//		}
//		if (!code_.equalsIgnoreCase(code)) {
//			// 增加错误次数
//			redisUtil.addWithTimeout(EnumRedisKeys.MOBILE_CODE + mobilePhone + "_error", Strings.isNullOrEmpty(code_error) ? "1" : code_error, 30, TimeUnit.MINUTES);
//			throw new BussinessExceptionNew(StatusCode.ERROR_MOBILE_CODE);
//		}
//		return true;
//	}
//
//	/**
//	 *
//	 * @Title send
//	 * @Description 发送通知公共方法
//	 * @author wzq
//	 * @date 2018年6月27日 下午2:02:06
//	 * @param enumNoticeNid 必填，通知NID类型枚举类
//	 * @param enumNoticeType 必填，通知类型枚举类
//	 * @param sentUser 选填，发送人，为空系统发送
//	 * @param receiveUser 必填，接收人
//	 * @param receiveAddr 必填，接收地址，例如邮箱，手机号
//	 * @param sendData 选填，填充数据，可以为空
//	 * @param code 选填，手机验证码
//	 * @param ip 选填，操作ip
//	 * @param validate 选填，是否校验短信发送次数，目前20分钟内限制发送5次短信验证码，仅当通知类型为短信时会校验
//	 * @return boolean true：通知成功，false：通知失败，以该结果为准
//	 */
//	public boolean send(EnumNoticeNid enumNoticeNid, EnumNoticeType enumNoticeType, String sentUser, String receiveUser, String receiveAddr, HashMap<String, Object> sendData,
//						String code, String ip, Boolean validate) {
//		if (enumNoticeNid == null) {
//			throw new BussinessExceptionNew(StatusCode.ERROR_NOTIC_NID_EMPTY);
//		}
//		if (enumNoticeType == null) {
//			throw new BussinessExceptionNew(StatusCode.ERROR_NOTIC_TYPE_EMPTY);
//		}
//		if (Strings.isNullOrEmpty(sentUser)) {
//			sentUser = "1";
//		}
//		if (Strings.isNullOrEmpty(receiveUser)) {
//			throw new BussinessExceptionNew(StatusCode.ERROR_NOTIC_RECEIVE_USER_EMPTY);
//		}
//		if (receiveAddr == null) {
//			throw new BussinessExceptionNew(StatusCode.ERROR_NOTIC_RECEIVE_ADDRESS_EMPTY);
//		}
//		if (validate == null)
//			validate = false;
//		// 查找通知类型配置
//		NoticeType condition = new NoticeType();
//		condition.setNid(enumNoticeNid.getNid());
//		condition.setNoticeType(enumNoticeType.getType());
//		NoticeType smsType = noticeTypeService.getByCondition(condition);
//		if (smsType == null) {
//			throw new BussinessExceptionNew(StatusCode.ERROR_NOTIC_TYPE_NOT_CONFIG);
//		}
//		if (smsType.getSend() == EnumNoticeSend.TYPE_SEND.getType()) {// 需要发送
//			Notice notice = new Notice();
//			notice.setNid(enumNoticeNid.getNid());
//			notice.setType(enumNoticeType.getType());
//			notice.setSentUser(sentUser);
//			notice.setReceiveUser(receiveUser);
//			notice.setReceiveAddr(receiveAddr);
//			notice.setStatus(EnumNoticeStatus.STATUS_UNSEND.getStatus());
//			notice.setTitle(smsType.getTitleTemplet());
//			notice.setContent(StringUtil.fillTemplet(smsType.getTemplet(), sendData));
//			notice.setCode(code);
//			noticeService.insertSelective(notice);
//			// 发送通知
//			switch (enumNoticeType) {
//			case TYPE_SMS:
//				String key = enumNoticeNid.getNid() + "_" + receiveAddr;
//				String value = "";
//				if (validate) {
//					// 校验发送验证码次数
//					value = validateSendMobileCodeLimit(key);
//				}
//				Integer smsChannel = null;
//				String smsChannelStr = sConfigService.getValueByNid("con_nomarl_sms_channel");
//				if (Strings.isNullOrEmpty(smsChannelStr)) {
//					smsChannel = EnumMessageChannel.TYPE_SMS_MW.getType();
//				} else {
//					smsChannel = Integer.parseInt(smsChannelStr);
//				}
//				if (Strings.isNullOrEmpty(code)) {
//					sendSms(notice.getReceiveAddr(), notice.getContent(), smsChannel);
//				} else {
//					sendCodeSms(notice.getReceiveAddr(), notice.getContent(), smsChannel, code);
//				}
//				// 保存至缓存
//				redisUtil.addWithTimeout(EnumRedisKeys.MOBILE_CODE + receiveAddr, code, 30, TimeUnit.MINUTES);
//				if (validate) {
//					// 保存/更新发送验证码限制次数
//					updateSendMobileCodeLimit(key, value);
//				}
//				break;
//			case TYPE_EMAIL:
//				MailHandelUtils.sendHtmlMail(notice.getReceiveAddr(), notice.getTitle(), notice.getContent());
//				break;
//			case TYPE_MESSAGE:
//				sendMessage(notice, ip);
//				break;
//			}
//			// 更新通知状态
//			Notice update = new Notice();
//			update.setId(notice.getId());
//			update.setStatus(EnumNoticeStatus.STATUS_SEND.getStatus());
//			update.setResult("ok");
//			noticeService.updateByPrimaryKeySelective(update);
//		}
//		return true;
//	}
//
//	// 保存站内信信息
//	private void sendMessage(Notice notice, String ip) {
//		Message message = new Message();
//		message.setSentUser(notice.getSentUser());
//		message.setReceiveUser(notice.getReceiveUser());
//		message.setTitle(notice.getTitle());
//		message.setContent(notice.getContent());
//		message.setAddIp(ip);
//		message.setNid(notice.getNid());
//		messageService.insertSelective(message);
//	}
//
//	private String validateSendMobileCodeLimit(String key) {
//		String value = redisUtil.find(key);// 目前已经发送次数
//		if (!Strings.isNullOrEmpty(value)) {
//			if (Integer.parseInt(value) > 4) {
//				throw new BussinessExceptionNew(StatusCode.ERROR_MOBILE_CODE_SEND_LIMIT);
//			}
//		}
//		return value;
//	}
//
//	private void updateSendMobileCodeLimit(String key, String value) {
//		// 保存第一次发送验证码，并且置过期时间
//		if (Strings.isNullOrEmpty(value))
//			redisUtil.addWithTimeout(key, "1", 20, TimeUnit.MINUTES);
//		else
//			// 每发送一次增加一次记录
//			redisUtil.incr(key);
//	}
//
//	public static void sendSms(final String phone, final String content, final Integer smsChannel) {
//		StaticProp.execute.submit(new Runnable() {
//			public void run() {
//				sendSmsByChannelType(phone, content, smsChannel, 0);
//			}
//		});
//	}
//
//	public static Boolean sendSmsByChannelType(final String phone, final String content, final Integer smsChannel, final int type) {
//		try {
//			Map<String, Object> paramMap = Maps.newHashMap();
//			if (smsChannel == EnumMessageChannel.TYPE_SMS_ZJY.getType()) {
//				paramMap.put("name", ZJYSMS_YX_USERNAME);
//				paramMap.put("pwd", ZJYSMS_YX_PWD);
//				paramMap.put("dst", phone);
//				paramMap.put("msg", content + "退订回T");
//				paramMap.put("time", "");
//				paramMap.put("sender", "");
//				paramMap.put("txt", "");
//				LOGGER.info(paramMap);
//				String s = postMethodUrlForZjy(ZJYSMS_URL, paramMap);
//				LOGGER.info(new String(s.getBytes("iso8859_1"), "gb2312"));
//				if (s.contains("num=1")) {
//					return true;
//				}
//			} else if (smsChannel == EnumMessageChannel.TYPE_SMS_ZT.getType()) {
//				paramMap.put("username", SMS_USERNAME);
//				paramMap.put("password", SMS_PWD);
//				if (type == 0) {
//					paramMap.put("productid", "727727");
//				} else {
//					paramMap.put("productid", "411623");
//				}
//				paramMap.put("mobile", phone);
//				paramMap.put("content", content);
//				paramMap.put("dstime", "");
//				paramMap.put("xh", 0);
//				LOGGER.info(paramMap);
//				String s = HttpUtil.postMethodUrl(SMS_URL, null, paramMap, null);
//				LOGGER.info(s);
//				if (s.contains("1,")) {// 发送成功
//					return true;
//				}
//			} else if (smsChannel == EnumMessageChannel.TYPE_SMS_MW.getType()) {
//				paramMap.put("userId", MWSMS_YX_USERID);
//				paramMap.put("password", MWSMS_YX_PASSWORD);
//				paramMap.put("pszMobis", phone);
//				if ("test".equals(MWSMS_YX_ENVIRONMENT)) {
//					paramMap.put("pszMsg", "同事您好，感谢您对此次测试的配合。");
//				} else {
//					paramMap.put("pszMsg", content);
//				}
//				paramMap.put("iMobiCount", 1);
//				paramMap.put("pszSubPort", "*");
//				paramMap.put("MsgId", MWSMS_YX_MSGID);
//				LOGGER.info(paramMap);
//				String s = HttpUtil.postMethodUrl(MWSMS_YX_URL, null, paramMap, null);
//				LOGGER.info(s);
//			}
//		} catch (Exception e) {
//			LOGGER.error("发送短信异常：", e);
//			return false;
//		}
//		return false;
//	}
//
//	public static void sendCodeSms(final String phone, final String content, final Integer smsChannel, final String code) {
//		StaticProp.execute.submit(new Runnable() {
//			public void run() {
//				try {
//					Map<String, Object> paramMap = Maps.newHashMap();
//					if (smsChannel == EnumMessageChannel.TYPE_SMS_ZJY.getType()) {
//						paramMap.put("name", ZJYSMS_USERNAME);
//						paramMap.put("pwd", ZJYSMS_PWD);
//						paramMap.put("dst", phone);
//						paramMap.put("msg", "验证码为" + code);
//						paramMap.put("time", "");
//						paramMap.put("sender", "");
//						paramMap.put("txt", "");
//						LOGGER.info(paramMap);
//						String s = postMethodUrlForZjy(ZJYSMS_URL, paramMap);
//						LOGGER.info(new String(s.getBytes("iso8859_1"), "gb2312"));
//					} else if (smsChannel == EnumMessageChannel.TYPE_SMS_ZT.getType()) {
//						paramMap.put("username", SMS_USERNAME);
//						paramMap.put("password", SMS_PWD);
//						paramMap.put("productid", "712712");
//						paramMap.put("mobile", phone);
//						paramMap.put("content", content);
//						paramMap.put("dstime", "");
//						paramMap.put("xh", 0);
//						LOGGER.info(paramMap);
//						String s = HttpUtil.postMethodUrl(SMS_URL, null, paramMap, null);
//						LOGGER.info(s);
//					} else if (smsChannel == EnumMessageChannel.TYPE_SMS_MW.getType()) {
//						paramMap.put("userId", MWSMS_USERID);
//						paramMap.put("password", MWSMS_PASSWORD);
//						paramMap.put("pszMobis", phone);
//						if ("test".equals(MWSMS_ENVIRONMENT)) {
//							paramMap.put("pszMsg", "同事您好，感谢您对此次测试的配合。");
//						} else {
//							paramMap.put("pszMsg", content);
//						}
//						paramMap.put("iMobiCount", 1);
//						paramMap.put("pszSubPort", "*");
//						paramMap.put("MsgId", MWSMS_MSGID);
//						LOGGER.info(paramMap);
//						String s = HttpUtil.postMethodUrl(MWSMS_URL, null, paramMap, null);
//						LOGGER.info(s);
//					}
//				} catch (Exception e) {
//					LOGGER.error("发送短信异常：", e);
//				}
//			}
//		});
//	}
//
//	public static String postMethodUrlForZjy(String url, Map<String, Object> paramMap) {
//		try {
//			CloseableHttpResponse execute;
//			CloseableHttpClient client;
//
//			HttpPost post = new HttpPost(url);
//			if (paramMap != null && paramMap.size() > 0) {
//				List<NameValuePair> nvps = new ArrayList<NameValuePair>();
//				for (String key : paramMap.keySet()) {
//					Object value = paramMap.get(key);
//					if (value != null) {
//						nvps.add(new BasicNameValuePair(key, String.valueOf(value)));
//					}
//				}
//				post.setEntity(new UrlEncodedFormEntity(nvps, "gbk"));
//			}
//
//			client = HttpClients.custom().setUserAgent("Mozilla/5.0 (Windows NT 10.0; WOW64; rv:41.0) Gecko/20100101 Firefox/41.0")
//					.setDefaultRequestConfig(RequestConfig.custom().setSocketTimeout(60000).setConnectTimeout(60000).setConnectionRequestTimeout(60000).build()).build();
//
//			execute = client.execute(post);
//			String content = EntityUtils.toString(execute.getEntity());
//			LOGGER.info(url);
//			Charset charset = ContentType.getOrDefault(execute.getEntity()).getCharset();
//			LOGGER.info(charset);
//			LOGGER.info(execute.getStatusLine().getStatusCode());
//			LOGGER.info(content);
//			execute.close();
//			client.close();
//			return content;
//		} catch (Exception e) {
//			LOGGER.error(e);
//			return null;
//		}
//	}
//}
