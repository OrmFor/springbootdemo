package com.jlkj.web.shopnew.common.config.impl;

import com.jlkj.web.shopnew.common.config.AbstractGlobalPropertyPlaceholderConfigurer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.core.PriorityOrdered;
import redis.clients.jedis.Jedis;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;

public class RedisConfigurer extends AbstractGlobalPropertyPlaceholderConfigurer implements BeanFactoryPostProcessor,PriorityOrdered {

        private static final Logger LOGGER = LogManager.getLogger(RedisConfigurer.class);
    
    	private String prefix ;
	
	private int order ;

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}
	
	

	public void setOrder(int order) {
	    this.order = order;
	}



	private Jedis getRedisInstance(String host, int port, String password, int database) {

		Jedis jedis = new Jedis(host, port);
		// 鉴权信息
		jedis.auth(password);
		jedis.select(database);
		
		return jedis;
	}

	@Override
	public Properties obtainProperties() throws IOException {
		// 先从redis.properties文件读取redis的配置，然后通过手动创建Redis连接读取Redis中的配置
		Properties props = new Properties();
		ClassLoader loader = RedisConfigurer.class.getClassLoader();
		InputStream in = loader.getResourceAsStream("application.properties");
		props.load(in);
		// 创建redis连接
		String host = props.getProperty("spring.redis.host").trim();
		int port = Integer.parseInt(props.getProperty("spring.redis.port").trim());
		String password = props.getProperty("spring.redis.password");
		int database = Integer.parseInt(props.getProperty("spring.redis.database"));
		Jedis jedis = null ;
		try{
			jedis = getRedisInstance( host,  port, password,  database) ;
		
			Set<String> set = jedis.keys( prefix + ".*") ;
			
			Iterator<String> iterator = set.iterator() ;
			while(iterator.hasNext()){
				String key = iterator.next() ;
				String value = jedis.get(key) ;
				LOGGER.debug("redis key=" + key + ",value=" + value);
				props.put(key.substring(prefix.length()+1), value) ;
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(jedis != null){
				jedis.quit(); 
				jedis.close();
			}
			
		}
		LOGGER.info("jedis instance cereated! status=" + jedis.isConnected() );
		return props;
	}

	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
		Properties props = null;
		try {
			// 从redis中获取配置信息
			props = obtainProperties();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 对引用配置的Bean进行处理
		this.processProperties(beanFactory, props);

	}

	@Override
	public int getOrder() {
	    return this.order;
	}

}
