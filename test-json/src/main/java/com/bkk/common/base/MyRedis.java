package com.bkk.common.base;

import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Component
public class MyRedis {
	protected final static Logger logger = Logger.getLogger(MyRedis.class);

	private static JedisPool jedisPool;

	@Autowired(required = true)
	public void setJedisPool(JedisPool jedisPool) {
		MyRedis.jedisPool = jedisPool;
	}

	/**
	 * 获取缓存
	 * 
	 * @param key
	 *            键
	 * @return 值
	 */
	public static String get(String key) {
		String value = null;
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			if (jedis.exists(key)) {
				value = jedis.get(key);
				value = value != null && !"nil".equalsIgnoreCase(value) ? value : null;
				logger.debug("get " + key + " = " + value);
			}
		} catch (Exception e) {
			logger.warn("get " + key + " = " + value);
			e.printStackTrace();
		} finally {
			jedisPool.returnResource(jedis);
		}
		return value;
	}

	/**
	 * 设置缓存
	 * 
	 * @param key
	 *            键
	 * @param value
	 *            值
	 * @param cacheSeconds
	 *            超时时间，0为不超时
	 * @return
	 */
	public static String set(String key, String value, int cacheSeconds) {
		String result = null;
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			result = jedis.set(key, value);
			if (cacheSeconds != 0) {
				jedis.expire(key, cacheSeconds);
			}
			logger.debug("set " + key + " = " + value);
		} catch (Exception e) {
			logger.warn("set " + key + " = " + value);
			e.printStackTrace();
		} finally {
			jedisPool.returnResource(jedis);
		}
		return result;
	}

	/**
	 * 删除缓存
	 * 
	 * @param key
	 *            键
	 * @return
	 */
	public static long del(String key) {
		long result = 0;
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			if (jedis.exists(key)) {
				result = jedis.del(key);
				logger.debug("del " + key);
			} else {
				logger.debug("del " + key + "not exists");
			}
		} catch (Exception e) {
			logger.warn("del " + key);
		} finally {
			jedisPool.returnResource(jedis);
		}
		return result;
	}

	/**
	 * 缓存是否存在
	 * 
	 * @param key
	 *            键
	 * @return
	 */
	public static boolean exists(String key) {
		boolean result = false;
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			result = jedis.exists(key);
			logger.debug("exists " + key);
		} catch (Exception e) {
			logger.warn("exists " + key);
		} finally {
			jedisPool.returnResource(jedis);
		}
		return result;
	}

	/**
	 * 获取所有key
	 */
	public static Set<String> getAllKeys() {
		Jedis jedis = null;
		Set<String> set = null;
		try {
			jedis = jedisPool.getResource();
			set = jedis.keys("*");
			logger.debug("set.size() =" + set.size());
		} catch (Exception e) {
			logger.warn("set.size() =" + set.size());
		} finally {
			jedisPool.returnResource(jedis);
		}
		return set;
	}

}
