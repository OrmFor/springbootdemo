package com.jlkj.web.shopnew.utils.redis;

import java.util.concurrent.TimeUnit;

/**
* @author huxin
    * @Date 2017年6月3日 下午12:17:33   
*/
public class SequenceGenerator {
	
	private RedisUtil redisUtil ;
	
	public RedisUtil getRedisUtil() {
		return redisUtil;
	}

	public void setRedisUtil(RedisUtil redisUtil) {
		this.redisUtil = redisUtil;
	}

	/**
	 * 创建Sequence
	 * @param name
	 * @return
	 */
	public Boolean  createNewSequence(String name){
		
		Long initVal = 0l ;
		Boolean result = redisUtil.setNX(name, initVal.toString()); 
		if(result){
			redisUtil.updateTimeout(name,1,TimeUnit.DAYS);
		}
		return result; 
	}
	
	/**
	 * 判断Sequence是否存在
	 * @param name
	 * @return
	 */
	public Boolean isExist(String name){
		return redisUtil.exist(name) ;
	}
	
	
	/**
	 * 从Sequence中获取下一个递增值
	 * @param name
	 * @return
	 */
	public Long nextVal(String name){
		return redisUtil.incr(name) ;
	}

}
