package com.jlkj.web.shopnew.utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author huxin
 * @Date 2017年5月10日 下午2:32:07
 */
public class OrderNoGenerator {

	private static SimpleDateFormat datetimeFormat = new SimpleDateFormat("YYYYMMddHHmmssSSS");

	public static String makeOrderNo(String prefix) {
		String datetime = "";
		synchronized (OrderNoGenerator.class) {
			datetime = datetimeFormat.format(new Date());
		}

		String orderno = prefix + datetime + RandomUtil.generateNumber(100);
		return orderno;
	}

	public static List<String> getDetailNo(String order, int size) {
		List<String> list = new ArrayList<String>(size);
		for (int i = 1; i <= size; i++) {
			list.add(order + "_" + i);
		}
		return list;

	}

}
