package com.jlkj.web.shopnew.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 
 * @Description 生成用户编号工具类
 * @author wzq
 * @date 2018年6月26日 下午3:25:07
 */
public class UserCodeGenerator {

	private static final Logger LOGGER = LogManager.getLogger(UserCodeGenerator.class);

	private SequenceGenerator sequence;

	public SequenceGenerator getSequence() {
		return sequence;
	}

	public void setSequence(SequenceGenerator sequence) {
		this.sequence = sequence;
	}

	public String getUserCode(String prefix) {
		String userCode = null;
		try {
			long val = 0;
			String today = DateUtil.dateStr12(DateUtil.getNow());
			prefix = prefix + today;
			if (sequence.isExist(prefix)) {
				val = sequence.nextVal(prefix);
			} else {
				boolean flag = sequence.createNewSequence(prefix);
				if (flag) {
					val = sequence.nextVal(prefix);
				}
			}
			String sequence = addZeroForNum(val + "", 4);
			userCode = prefix + sequence;

		} catch (Exception e) {
			LOGGER.error("生成用户编号失败：" + e.getMessage(), e);
		}
		return userCode;
	}

	private static String addZeroForNum(String str, int strLength) {
		int strLen = str.length();
		if (strLen < strLength) {
			while (strLen < strLength) {
				StringBuffer sb = new StringBuffer();
				sb.append("0").append(str);// 左补0
				str = sb.toString();
				strLen = str.length();
			}
		}
		return str;
	}

}
