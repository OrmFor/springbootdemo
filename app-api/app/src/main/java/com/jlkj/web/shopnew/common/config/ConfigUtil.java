package com.jlkj.web.shopnew.common.config;

import com.jlkj.web.shopnew.utils.redis.RedisUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;



/**
 * 提供给业务代码用于随时获取redis中的配置
 * @author Administrator
 *
 */
public class ConfigUtil {
    
    	private static final Logger LOGGER = LogManager.getLogger(ConfigUtil.class);
	
	private static  Boolean flag = true ;
	
	private static RedisUtil configUtil ;
	
	public static String getConfigValue(String key){
		return configUtil.find(key) ;
	}
	
	
	public  static void setConfigUtil(RedisUtil redisUtil){
		if(flag){
			synchronized(ConfigUtil.class){
				if(flag){
				    	LOGGER.info("configUtil is set");
					configUtil = redisUtil ;
					flag = false ;
				}
			}
		}
	}

}
