package com.lightereb.hrms.common.utils;

import jakarta.annotation.Resource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import java.util.concurrent.TimeUnit;

@Component
public class RedisUtil {

	@Resource
	private RedisTemplate<String, Object> redisTemplate;

	/**
	 * 设置缓存
	 */
	public boolean set(String key, Object value) {
		try {
			redisTemplate.opsForValue().set(key, value);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 设置缓存并设置过期时间
	 */
	public boolean set(String key, Object value, long time) {
		try {
			if (time > 0) {
				redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
			} else {
				set(key, value);
			}
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 获取缓存
	 */
	public Object get(String key) {
		return key == null ? null : redisTemplate.opsForValue().get(key);
	}

	/**
	 * 删除缓存
	 */
	public boolean delete(String key) {
		try {
			redisTemplate.delete(key);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
}