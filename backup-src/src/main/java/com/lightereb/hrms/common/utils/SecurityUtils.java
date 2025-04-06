package com.lightereb.hrms.common.utils;

import com.lightereb.hrms.common.exception.BusinessException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public class SecurityUtils {

	public static Long getCurrentUserId() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
			return Long.valueOf(((UserDetails) authentication.getPrincipal()).getUsername());
		}
		throw new BusinessException("用户未登录");
	}
}