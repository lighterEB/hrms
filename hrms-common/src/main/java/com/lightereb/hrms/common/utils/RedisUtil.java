package com.lightereb.hrms.common.utils;

import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RedisUtil {

	private final RedisTemplate<String, Object> redisTemplate;

	/**
	 * 设置缓存
	 */
	public boolean set(String key, Object value) {
		try {
			redisTemplate.opsForValue().set(key, value);
			return true;
		} catch (org.springframework.data.redis.RedisConnectionFailureException e) {
			// 连接失败异常
			return false;
		} catch (org.springframework.data.redis.RedisSystemException e) {
			// Redis系统异常
			return false;
		} catch (Exception e) {
			// 其他异常
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