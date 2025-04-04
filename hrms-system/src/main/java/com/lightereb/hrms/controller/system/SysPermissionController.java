package com.lightereb.hrms.controller.system;

import com.lightereb.hrms.common.result.R;
import com.lightereb.hrms.common.utils.SecurityUtils;
import com.lightereb.hrms.model.vo.system.MenuVO;
import com.lightereb.hrms.service.system.SysPermissionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/permissions")
@RequiredArgsConstructor
@Tag(name = "权限菜单管理")
public class SysPermissionController {

	private final SysPermissionService permissionService;

	@GetMapping("/menu")
	@Operation(summary = "获取当前用户的菜单")
	public R<List<MenuVO>> getUserMenu() {
		Long userId = SecurityUtils.getCurrentUserId();
		List<MenuVO> menuTree = permissionService.getUserMenuTree(userId);
		return R.ok(menuTree);
	}
}