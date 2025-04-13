package com.lightereb.hrms.common.utils;

import com.lightereb.hrms.common.exception.BusinessException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

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
		String username = getCurrentUsername();
		if (username == null) {
			throw new BusinessException("用户未登录");
		}
		
		// 这里需要通过Spring容器获取UserService并查询用户ID
		// 由于静态方法无法直接注入依赖，我们暂时无法在这里实现
		// 考虑临时方案：针对admin用户，返回ID 1
		if ("admin".equals(username)) {
			return 1L;
		}
		
		// 抛出异常，提示需要修改系统设计
		throw new BusinessException("获取用户ID失败，请修改系统设计以支持通过用户名查询用户ID");
	}

	/**
	 * 获取当前登录用户名
	 *
	 * @return 用户名
	 */
	public static String getCurrentUsername() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null && authentication.isAuthenticated()) {
			return authentication.getName();
		}
		return null;
	}
}