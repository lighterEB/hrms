package com.lightereb.hrms.common.interceptor;

import com.lightereb.hrms.common.annotation.RequirePermission;
import com.lightereb.hrms.common.exception.BusinessException;
import com.lightereb.hrms.common.service.PermissionService;
import com.lightereb.hrms.common.utils.SecurityUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@RequiredArgsConstructor
public class PermissionInterceptor implements HandlerInterceptor
{
	private final PermissionService permissionService;

	@Override
	public boolean preHandle(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler) {
		if (!(handler instanceof HandlerMethod)) {
			return true;
		}

		HandlerMethod handlerMethod = (HandlerMethod) handler;
		RequirePermission requirePermission = handlerMethod.getMethodAnnotation(RequirePermission.class);
		if (requirePermission == null) {
			return true;
		}

		String permission = requirePermission.value();
		Long userId = SecurityUtils.getCurrentUserId();
		
		if (!permissionService.hasPermission(userId, permission)) {
			throw new BusinessException("没有操作权限");
		}

		return true;
	}
}