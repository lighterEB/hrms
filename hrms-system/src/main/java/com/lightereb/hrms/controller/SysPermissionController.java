package com.lightereb.hrms.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lightereb.hrms.common.result.R;
import com.lightereb.hrms.common.security.util.SecurityUtils;
import com.lightereb.hrms.model.entity.system.SysPermission;
import com.lightereb.hrms.model.vo.system.MenuVO;
import com.lightereb.hrms.service.SysPermissionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 权限菜单管理控制器
 */
@RestController
@RequestMapping("/v1/permissions")
@RequiredArgsConstructor
@Tag(name = "权限菜单管理")
@Validated
public class SysPermissionController {

	private final SysPermissionService permissionService;

	/**
	 * 获取当前用户的菜单
	 */
	@GetMapping("/menu")
	@Operation(summary = "获取当前用户的菜单")
	public R<List<MenuVO>> getUserMenu() {
		Long userId = SecurityUtils.getCurrentUserId();
		List<MenuVO> menuTree = permissionService.getUserMenuTree(userId);
		return R.ok(menuTree);
	}
	
	/**
	 * 分页获取权限列表
	 */
	@GetMapping("/page")
	@Operation(summary = "分页获取权限列表")
	public R<Page<SysPermission>> page(
	        @RequestParam(defaultValue = "1") @Parameter(description = "页码") Integer pageNum,
	        @RequestParam(defaultValue = "10") @Parameter(description = "每页条数") Integer pageSize,
	        @RequestParam(required = false) @Parameter(description = "权限名称") String name,
	        @RequestParam(required = false) @Parameter(description = "权限类型：0-目录，1-菜单，2-按钮") Integer type) {
	    
	    Page<SysPermission> page = new Page<>(pageNum, pageSize);
	    LambdaQueryWrapper<SysPermission> queryWrapper = new LambdaQueryWrapper<>();
	    
	    // 根据名称模糊查询
	    if (name != null && !name.isEmpty()) {
	        queryWrapper.like(SysPermission::getName, name);
	    }
	    
	    // 根据类型查询
	    if (type != null) {
	        queryWrapper.eq(SysPermission::getType, type);
	    }
	    
	    // 排序：先按类型，再按排序字段
	    queryWrapper.orderByAsc(SysPermission::getType)
	                .orderByAsc(SysPermission::getSort);
	    
	    Page<SysPermission> result = permissionService.page(page, queryWrapper);
	    return R.ok(result);
	}
	
	/**
	 * 获取所有权限列表（树形结构）
	 */
	@GetMapping("/tree")
	@Operation(summary = "获取所有权限列表（树形结构）")
	public R<List<SysPermission>> tree() {
	    // 查询所有权限
	    LambdaQueryWrapper<SysPermission> queryWrapper = new LambdaQueryWrapper<>();
	    queryWrapper.orderByAsc(SysPermission::getType)
	                .orderByAsc(SysPermission::getSort);
	    
	    List<SysPermission> permissions = permissionService.list(queryWrapper);
	    
	    // 构建树形结构
	    List<SysPermission> permissionTree = permissionService.buildPermissionTree(permissions);
	    return R.ok(permissionTree);
	}
	
	/**
	 * 根据ID获取权限详情
	 */
	@GetMapping("/{id}")
	@Operation(summary = "获取权限详情")
	public R<SysPermission> getById(@PathVariable("id") Long id) {
	    SysPermission permission = permissionService.getById(id);
	    return R.ok(permission);
	}
	
	/**
	 * 新增权限
	 */
	@PostMapping
	@Operation(summary = "新增权限")
	public R<Void> add(@RequestBody @Valid SysPermission permission) {
	    permissionService.save(permission);
	    return R.ok();
	}
	
	/**
	 * 修改权限
	 */
	@PutMapping("/{id}")
	@Operation(summary = "修改权限")
	public R<Void> update(@PathVariable("id") Long id, @RequestBody @Valid SysPermission permission) {
	    permission.setId(id);
	    permissionService.updateById(permission);
	    return R.ok();
	}
	
	/**
	 * 删除权限
	 */
	@DeleteMapping("/{id}")
	@Operation(summary = "删除权限")
	public R<Void> delete(@PathVariable("id") Long id) {
	    // 检查是否有子权限
	    long count = permissionService.count(new LambdaQueryWrapper<SysPermission>()
	            .eq(SysPermission::getParentId, id));
	    
	    if (count > 0) {
	        return R.fail("该权限下存在子权限，无法删除");
	    }
	    
	    permissionService.removeById(id);
	    return R.ok();
	}
}