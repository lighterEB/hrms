package com.lightereb.hrms.common.security.util;

import com.lightereb.hrms.common.config.properties.JwtProperties;
import com.lightereb.hrms.common.exception.BusinessException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * 安全工具类
 */
public class SecurityUtils {

	/**
	 * 获取当前登录用户ID
	 *
	 * @return 用户ID
	 */
	public static Long getCurrentUserId() {
		// 获取当前用户名
		String token = getCurrentToken();
		if (token == null) {
			throw new BusinessException("用户未登录");
		}
		JwtTokenUtil jwtTokenUtil = SpringUtils.getBean(JwtTokenUtil.class);
		return jwtTokenUtil.getUserIdFromToken(token);
	}

	/**
	 * 获取当前请求中的token
	 */
	private static String getCurrentToken() {
		// 获取当前请求
		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		if (attributes == null) {
			return null;
		}

		HttpServletRequest request = attributes.getRequest();
		JwtProperties jwtProperties = SpringUtils.getBean(JwtProperties.class);

		// 从请求头获取token
		String authHeader = request.getHeader(jwtProperties.getHeader());
		if (authHeader != null && authHeader.startsWith(jwtProperties.getTokenPrefix() + " ")) {
			return authHeader.substring(jwtProperties.getTokenPrefix().length() + 1);
		}

		return null;
	}
}