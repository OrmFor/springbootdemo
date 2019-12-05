package com.jlkj.web.shopnew.common.config;

import com.jlkj.web.shopnew.utils.redis.RedisUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;


public class ConfigListener implements ApplicationListener<ContextRefreshedEvent>{
        
    private static final Logger LOGGER = LogManager.getLogger(ConfigListener.class);
	
	private RedisUtil redisUtil ;
	

	public void setRedisUtil(RedisUtil redisUtil) {
		this.redisUtil = redisUtil;
	}

	public void onApplicationEvent(ContextRefreshedEvent event) {
	        StringBuilder message = new StringBuilder() ;
	        message.append("event ").append(event).append(" was happened ");
	        LOGGER.info(message.toString());
	        LOGGER.info("ConfigUtil set redisUtil , it is " + redisUtil);
	        ConfigUtil.setConfigUtil(redisUtil);
		
	}

	
}
