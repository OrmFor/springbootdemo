package com.jlkj.web.shopnew.utils;

/**
 * @author huxin
 * @Date 2017年8月7日 上午11:02:37
 */
public class RandomUtil {

	public static int generateNumber(int bound) {
		int number = (int) (Math.random() * bound);
		return number;
	}

}
