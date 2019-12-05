package com.jlkj.web.shopnew.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * @Description 校验工具类
 * @author wzq
 * @date 2017年6月21日 下午2:39:41
 */
public class RegexUtil {

	public static boolean checkInput(String regex, String input) {
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(input);
		if (!matcher.matches())
			return false;
		return true;
	}

}
