package com.jlkj.web.shopnew.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.support.atomic.RedisAtomicLong;
import org.springframework.util.Assert;

import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * 
 * @Description redis工具类
 * @author wzq
 * @date 2018年6月26日 下午3:23:44
 */
public class RedisStringManager {

	private static final Logger LOGGER = LogManager.getLogger(FreemarkerUtil.class);

	private StringRedisTemplate redisTemplate;

	public void setRedisTemplate(StringRedisTemplate redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	public StringRedisTemplate getRedisTemplate() {
		return redisTemplate;
	}

	public void add(String key, String value) {
		redisTemplate.boundValueOps(key).set(value);
	}

	public String find(String key) {
		return redisTemplate.boundValueOps(key).get();
	}

	public void delete(String key) {
		redisTemplate.delete(key);
	}

	/**
	 * @Description 自增
	 * @param key
	 * @return 自增前的值
	 */
	public Long incr(String key) {
		RedisAtomicLong entityIdCounter = new RedisAtomicLong(key, redisTemplate.getConnectionFactory());
		return entityIdCounter.getAndIncrement();
	}

	/**
	 * @Description 自减
	 * @param key
	 * @return 自减前的值
	 */
	public Long decr(String key) {
		RedisAtomicLong entityIdCounter = new RedisAtomicLong(key, redisTemplate.getConnectionFactory());
		return entityIdCounter.getAndDecrement();
	}

	public void keys() {
		Set<String> set = redisTemplate.keys("*");
		Iterator<String> iterator = set.iterator();
		while (iterator.hasNext()) {
			final String key = iterator.next();
			LOGGER.info(key);
		}
	}

	/**
	 * @Description 正则删除key开头的
	 * @param key
	 */
	public void deleteByKeyLeft(String key) {
		Set<String> set = redisTemplate.keys(key + "*");
		Iterator<String> iterator = set.iterator();
		while (iterator.hasNext()) {
			final String keyStr = iterator.next();
			redisTemplate.execute(new RedisCallback<Object>() {
				public Object doInRedis(RedisConnection connection) {
					connection.del(redisTemplate.getStringSerializer().serialize(keyStr));
					return null;
				}
			});
		}
	}

	public void delete() {
		Set<String> set = redisTemplate.keys("*");
		Iterator<String> iterator = set.iterator();
		while (iterator.hasNext()) {
			final String key = iterator.next();
			redisTemplate.execute(new RedisCallback<Object>() {
				public Object doInRedis(RedisConnection connection) {
					connection.del(redisTemplate.getStringSerializer().serialize(key));
					return null;
				}
			});
			LOGGER.info(key);
		}
	}

	/**
	 * 添加key值
	 * 
	 * @param key
	 * @param value
	 * @return void
	 */
	public void addOrUpdate(String key, String value) {
		Assert.notNull(key);
		Assert.notNull(value);
		redisTemplate.boundValueOps(key).set(value);
	}

	/**
	 * 更新key的失效时间
	 * 
	 * @param key
	 * @param timeout
	 * @param unit
	 */
	public void updateTimeout(String key, long timeout, TimeUnit unit) {
		Assert.notNull(key);
		redisTemplate.expire(key, timeout, unit);

	}

	/**
	 * 添加key，并设置超时间
	 * 
	 * @param key
	 * @param value
	 * @param timeout
	 */
	public void addWithTimeout(String key, String value, long timeout, TimeUnit unit) {
		Assert.notNull(key);
		Assert.notNull(value);
		redisTemplate.boundValueOps(key).set(value);
		redisTemplate.expire(key, timeout, unit);
	}

}
