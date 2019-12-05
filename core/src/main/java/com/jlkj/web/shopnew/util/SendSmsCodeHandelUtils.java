/*
package com.jlkj.web.shopnew.util;

import java.util.HashMap;

import com.jlkj.web.shopnew.enums.EnumNoticeNid;
import com.jlkj.web.shopnew.enums.EnumNoticeType;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import com.yinmimoney.web.p2pnew.core.StatusCode;
import com.yinmimoney.web.p2pnew.enums.EnumAdminStatus;
import com.yinmimoney.web.p2pnew.exception.BussinessExceptionNew;

*/
/**
 * 短信验证码相关统一公用类
 *//*

public class SendSmsCodeHandelUtils {

	private static final Logger LOGGER = LogManager.getLogger(SendSmsCodeHandelUtils.class);

	@Autowired
	private SmsHandelUtils smsHandelUtils;
	@Autowired
	private IAdmin adminService;

	public boolean sendMobileCode(String mobilePhone, EnumSendSmsCodeType enumSendSmsCodeType, String ip) {
		if (Strings.isNullOrEmpty(mobilePhone)) {
			throw new BussinessExceptionNew(StatusCode.ERROR_MOBILE_EMPTY);
		}
		if (enumSendSmsCodeType == null) {
			throw new BussinessExceptionNew(StatusCode.ERROR_MOBILE_CODE_TYPE);
		}
		if (!RegexUtil.checkInput("^1[0-9]{10}$", Strings.nullToEmpty(mobilePhone))) {
			throw new BussinessExceptionNew(StatusCode.ERROR_MOBILE_FORMAT);
		}
		String code = RandomStringUtils.random(6, "0123456789");// 验证码
		switch (enumSendSmsCodeType) {
		case TYPE_REGISTER:
			Admin condition = new Admin();
			condition.setMobilePhone(mobilePhone);
			Admin admin = adminService.getAdminByMobilePhone(mobilePhone);
			if (admin != null) {
				throw new BussinessExceptionNew(StatusCode.ERROR_ACCOUNT_EXIST);
			}
			// 短信内容填充数据
			HashMap<String, Object> sendData = Maps.newHashMap();
			sendData.put("code", code);
			sendData.put("time", 30);
			smsHandelUtils.send(EnumNoticeNid.NID_REGISTER, EnumNoticeType.TYPE_SMS, null, mobilePhone, mobilePhone, sendData, code, ip, true);
			break;
		case TYPE_RESET_PASSWORD:
			condition = new Admin();
			condition.setMobilePhone(mobilePhone);
			admin = adminService.getAdminByMobilePhone(mobilePhone);
			if (admin == null) {
				throw new BussinessExceptionNew(StatusCode.ERROR_MOBILE_NOT_EXISTS);
			}
			if (admin.getStatus().intValue() != EnumAdminStatus.STATUS_NORMAL.getStatus()) {
				throw new BussinessExceptionNew(StatusCode.ERROR_ACCOUNT_LOCK_OR_LEAVE);
			}
			// 短信内容填充数据
			sendData = Maps.newHashMap();
			sendData.put("userName", admin.getUserName());
			sendData.put("code", code);
			sendData.put("time", 30);
			smsHandelUtils.send(EnumNoticeNid.NID_RESET_PASSWORD, EnumNoticeType.TYPE_SMS, null, admin.getUserCode(), admin.getMobilePhone(), sendData, code, ip, true);
			break;
		case TYPE_QUICK_LOGIN:
			condition = new Admin();
			condition.setMobilePhone(mobilePhone);
			admin = adminService.getAdminByMobilePhone(mobilePhone);
			if (admin == null) {
				throw new BussinessExceptionNew(StatusCode.ERROR_MOBILE_NOT_EXISTS);
			}
			if (admin.getStatus().intValue() != EnumAdminStatus.STATUS_NORMAL.getStatus()) {
				throw new BussinessExceptionNew(StatusCode.ERROR_ACCOUNT_LOCK_OR_LEAVE);
			}
			// 短信内容填充数据
			sendData = Maps.newHashMap();
			sendData.put("userName", admin.getUserName());
			sendData.put("code", code);
			sendData.put("time", 30);
			smsHandelUtils.send(EnumNoticeNid.NID_QUICK_LOGIN, EnumNoticeType.TYPE_SMS, null, admin.getUserCode(), admin.getMobilePhone(), sendData, code, ip, false);
			break;
		default:
			throw new BussinessExceptionNew(StatusCode.ERROR_PARAM);
		}
		return true;
	}
}
*/
