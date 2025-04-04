package com.lightereb.hrms.common.utils;

import com.lightereb.hrms.common.exception.BusinessException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

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
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
			return Long.valueOf(((UserDetails) authentication.getPrincipal()).getUsername());
		}
		throw new BusinessException("用户未登录");
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