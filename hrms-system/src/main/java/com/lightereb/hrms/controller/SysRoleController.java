package com.lightereb.hrms.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lightereb.hrms.common.result.R;
import com.lightereb.hrms.dto.request.AssignRolePermissionsRequest;
import com.lightereb.hrms.dto.request.RoleQueryRequest;
import com.lightereb.hrms.model.entity.system.SysRole;
import com.lightereb.hrms.service.SysRoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 角色管理控制器
 */
@RestController
@RequestMapping("/v1/roles")
@RequiredArgsConstructor
@Tag(name = "角色管理", description = "角色相关接口")
@Validated
public class SysRoleController {

    private final SysRoleService roleService;

    /**
     * 分页查询角色列表
     */
    @GetMapping("/page")
    @Operation(summary = "分页查询角色列表")
    public R<Page<SysRole>> page(RoleQueryRequest request) {
        Page<SysRole> page = new Page<>(request.getPageNum(), request.getPageSize());
        LambdaQueryWrapper<SysRole> queryWrapper = new LambdaQueryWrapper<>();
        
        // 根据角色名称或角色编码模糊查询
        if (request.getKeyword() != null && !request.getKeyword().isEmpty()) {
            queryWrapper.like(SysRole::getRoleName, request.getKeyword())
                    .or()
                    .like(SysRole::getRoleCode, request.getKeyword());
        }
        
        // 根据状态查询
        if (request.getStatus() != null) {
            queryWrapper.eq(SysRole::getStatus, request.getStatus());
        }
        
        // 按创建时间降序排序
        queryWrapper.orderByDesc(SysRole::getCreateTime);
        
        Page<SysRole> result = roleService.page(page, queryWrapper);
        return R.ok(result);
    }

    /**
     * 获取所有角色列表
     */
    @GetMapping("/list")
    @Operation(summary = "获取所有角色列表")
    public R<List<SysRole>> list() {
        LambdaQueryWrapper<SysRole> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysRole::getStatus, 1); // 只查询正常状态的角色
        queryWrapper.orderByAsc(SysRole::getRoleName);
        List<SysRole> roles = roleService.list(queryWrapper);
        return R.ok(roles);
    }

    /**
     * 根据ID获取角色详情
     */
    @GetMapping("/{id}")
    @Operation(summary = "获取角色详情")
    public R<SysRole> getById(@PathVariable("id") Long id) {
        SysRole role = roleService.getById(id);
        return R.ok(role);
    }

    /**
     * 新增角色
     */
    @PostMapping
    @Operation(summary = "新增角色")
    public R<Void> add(@RequestBody @Valid SysRole role) {
        // 检查角色编码唯一性
        LambdaQueryWrapper<SysRole> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysRole::getRoleCode, role.getRoleCode());
        if (roleService.count(queryWrapper) > 0) {
            return R.fail("角色编码已存在");
        }
        
        roleService.save(role);
        return R.ok();
    }

    /**
     * 修改角色
     */
    @PutMapping("/{id}")
    @Operation(summary = "修改角色")
    public R<Void> update(@PathVariable("id") Long id, @RequestBody @Valid SysRole role) {
        role.setId(id);
        
        // 检查角色编码唯一性
        LambdaQueryWrapper<SysRole> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysRole::getRoleCode, role.getRoleCode())
                .ne(SysRole::getId, id);
        if (roleService.count(queryWrapper) > 0) {
            return R.fail("角色编码已存在");
        }
        
        roleService.updateById(role);
        return R.ok();
    }

    /**
     * 删除角色
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "删除角色")
    public R<Void> delete(@PathVariable("id") Long id) {
        roleService.removeById(id);
        return R.ok();
    }

    /**
     * 获取角色的权限ID列表
     */
    @GetMapping("/{id}/permissions")
    @Operation(summary = "获取角色的权限ID列表")
    public R<List<Long>> getRolePermissions(@PathVariable("id") Long id) {
        List<Long> permissionIds = roleService.getRolePermissionIds(id);
        return R.ok(permissionIds);
    }

    /**
     * 分配角色权限
     */
    @PostMapping("/{id}/permissions")
    @Operation(summary = "分配角色权限")
    public R<Void> assignPermissions(
            @PathVariable("id") @Parameter(description = "角色ID") Long id,
            @RequestBody @Valid AssignRolePermissionsRequest request) {
        roleService.assignRolePermissions(id, request.getPermissionIds());
        return R.ok();
    }
} 