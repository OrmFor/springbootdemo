package com.jlkj.web.shopnew.util;

import java.util.concurrent.TimeUnit;

/**
 * 
 * @Description sequence工具类
 * @author wzq
 * @date 2018年6月26日 下午3:24:22
 */
public class SequenceGenerator {

	private RedisUtil redisUtil;

	public RedisUtil getRedisUtil() {
		return redisUtil;
	}

	public void setRedisUtil(RedisUtil redisUtil) {
		this.redisUtil = redisUtil;
	}

	/**
	 * 创建Sequence
	 * 
	 * @param name
	 * @return
	 */
	public Boolean createNewSequence(String name) {
		Long initVal = 0l;
		Boolean result = redisUtil.setNX(name, initVal.toString());
		if (result) {
			redisUtil.updateTimeout(name, 1, TimeUnit.DAYS);
		}
		return result;
	}

	/**
	 * 判断Sequence是否存在
	 * 
	 * @param name
	 * @return
	 */
	public Boolean isExist(String name) {
		return redisUtil.exist(name);
	}

	/**
	 * 从Sequence中获取下一个递增值
	 * 
	 * @param name
	 * @return
	 */
	public Long nextVal(String name) {
		return redisUtil.incr(name);
	}

}
