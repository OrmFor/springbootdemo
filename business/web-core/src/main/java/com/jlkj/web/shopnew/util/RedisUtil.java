package com.jlkj.web.shopnew.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.BoundListOperations;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;

/**
 * 
 * @Description redis工具类
 * @author wzq
 * @date 2018年6月26日 下午3:23:44
 */
public class RedisUtil {

	private StringRedisTemplate stringRedisTemplate;

	public StringRedisTemplate getStringRedisTemplate() {
		return stringRedisTemplate;
	}

	public void setStringRedisTemplate(StringRedisTemplate stringRedisTemplate) {
		this.stringRedisTemplate = stringRedisTemplate;
	}

	private String prefix = "";

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		Assert.notNull(prefix);
		this.prefix = prefix;
	}

	private String mergeKey(String key) {
		StringBuilder buffer = new StringBuilder();
		if (!StringUtils.isEmpty(prefix)) {
			buffer.append(prefix).append(".").append(key);
			return buffer.toString();
		} else {
			return key;
		}
	}

	public void addOrUpdate(String key, String value) {
		Assert.notNull(key);
		Assert.notNull(value);
		stringRedisTemplate.boundValueOps(mergeKey(key)).set(value);
	}

	/**
	 * 添加key，并设置超时间
	 * 
	 * @param key
	 * @param value
	 * @param timeout
	 */
	public void addWithTimeout(String key, String value, long timeout, TimeUnit unit) {
		key = mergeKey(key);
		stringRedisTemplate.boundValueOps(key).set(value);
		stringRedisTemplate.expire(key, timeout, unit);
	}

	/**
	 * value为对象
	 * 
	 * @param key
	 * @param o
	 */
	public void addOrUpdateObject(String key, Object o) {
		String value = JSON.toJSONString(o);
		addOrUpdate(key, value);
	}

	/**
	 * value为对象
	 * 
	 * @param key
	 * @param o
	 */
	public void addOrUpdateObjectWithTimeout(String key, Object o, long timeout, TimeUnit unit) {
		String value = JSON.toJSONString(o);
		addWithTimeout(key, value, timeout, unit);
	}

	/**
	 * 更新key的失效时间
	 * 
	 * @param key
	 * @param timeout
	 * @param unit
	 */
	public void updateTimeout(String key, long timeout, TimeUnit unit) {
		key = mergeKey(key);
		stringRedisTemplate.expire(key, timeout, unit);

	}

	/**
	 * 根据key获取value
	 * 
	 * @param key
	 * @return
	 */
	public String find(String key) {
		Assert.notNull(key);
		key = mergeKey(key);
		BoundValueOperations<String, String> operations = stringRedisTemplate.boundValueOps(key);
		return operations.get();
	}

	/**
	 * value为对象
	 * 
	 * @param key
	 * @return
	 */
	public Object findObject(String key, Class clazz) {
		Assert.notNull(key);
		String value = find(key);
		return JSON.parseObject(value, clazz);
	}

	public Set<String> keys(String condition) {
		Set<String> set = stringRedisTemplate.keys(prefix + condition);
		return set;

	}

	public void delete(final String key) {
		stringRedisTemplate.execute(new RedisCallback<Object>() {
			public Object doInRedis(RedisConnection connection) {
				String actulkey = mergeKey(key);
				connection.del(stringRedisTemplate.getStringSerializer().serialize(actulkey));
				return null;
			}
		});
	}

	public Boolean exist(final String key) {
		return stringRedisTemplate.execute(new RedisCallback<Boolean>() {
			public Boolean doInRedis(RedisConnection connection) {
				String actulkey = mergeKey(key);
				Boolean result = connection.exists(stringRedisTemplate.getStringSerializer().serialize(actulkey));
				return result;
			}
		});
	}

	public Boolean existOfHkey(String key, String hkey) {
		if (!exist(key)) {
			return false;
		}
		return stringRedisTemplate.boundHashOps(mergeKey(key)).hasKey(hkey);
	}

	public void putToMap(String key, String hkey, String hvalue) {
		String actulkey = mergeKey(key);
		stringRedisTemplate.boundHashOps(actulkey).put(hkey, hvalue);
	}

	public String getFromMap(String key, String hkey) {
		String actulkey = mergeKey(key);
		return (String) stringRedisTemplate.boundHashOps(actulkey).get(hkey);
	}

	public void putToList(String key, String value) {
		String actulkey = mergeKey(key);
		BoundListOperations<String, String> operations = stringRedisTemplate.boundListOps(actulkey);
		operations.rightPush(value);
	}

	/**
	 * 通过key获取List结构的列表数据，底层是以json方式存储
	 * 
	 * @param key
	 * @param requiredType
	 * @return
	 */
	public <T> List<T> getList(String key, Class<T> requiredType) {
		key = mergeKey(key);
		BoundListOperations<String, String> operations = stringRedisTemplate.boundListOps(key);
		long size = operations.size();
		if (size == 0) {
			return null;
		}
		List list = new ArrayList();
		for (int i = 0; i < size; i++) {
			String value = operations.index(i);
			list.add(JSON.parseObject(value, requiredType));
		}
		return list;
	}

	public Long incr(final String key) {
		return stringRedisTemplate.execute(new RedisCallback<Long>() {
			public Long doInRedis(RedisConnection connection) {
				String actulkey = mergeKey(key);
				Long result = connection.incr(key.getBytes());
				return result;
			}
		});
	}

	public Long incr(final String key, final long timeout) {
		return stringRedisTemplate.execute(new RedisCallback<Long>() {
			public Long doInRedis(RedisConnection connection) {
				String actulkey = mergeKey(key);
				Long result = connection.incr(key.getBytes());
				connection.expire(key.getBytes(), timeout);
				return result;
			}
		});
	}

	public Boolean setNX(final String key, final String value) {
		return stringRedisTemplate.execute(new RedisCallback<Boolean>() {
			public Boolean doInRedis(RedisConnection connection) {
				String actulkey = mergeKey(key);
				Boolean result = connection.setNX(key.getBytes(), value.getBytes());
				return result;
			}
		});
	}
}
