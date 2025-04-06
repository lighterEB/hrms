package com.lightereb.hrms.controller;

import java.util.List;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lightereb.hrms.common.result.R;
import com.lightereb.hrms.dto.request.AssignUserRolesRequest;
import com.lightereb.hrms.dto.request.ResetPasswordRequest;
import com.lightereb.hrms.dto.request.UserQueryRequest;
import com.lightereb.hrms.model.entity.system.SysUser;
import com.lightereb.hrms.service.system.SysUserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

/**
 * 用户管理控制器
 */
@RestController
@RequestMapping("/v1/users")
@RequiredArgsConstructor
@Tag(name = "用户管理", description = "用户相关接口")
@Validated
public class SysUserController {

    private final SysUserService userService;

    /**
     * 分页查询用户列表
     */
    @GetMapping("/page")
    @Operation(summary = "分页查询用户列表")
    public R<Page<SysUser>> page(UserQueryRequest request) {
        Page<SysUser> page = new Page<>(request.getPageNum(), request.getPageSize());
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        
        // 根据关键字查询（用户名、真实姓名、手机号、邮箱）
        if (request.getKeyword() != null && !request.getKeyword().isEmpty()) {
            queryWrapper.like(SysUser::getUsername, request.getKeyword())
                    .or().like(SysUser::getRealName, request.getKeyword())
                    .or().like(SysUser::getPhone, request.getKeyword())
                    .or().like(SysUser::getEmail, request.getKeyword());
        }
        
        // 根据状态查询
        if (request.getStatus() != null) {
            queryWrapper.eq(SysUser::getStatus, request.getStatus());
        }
        
        // 按创建时间降序排序
        queryWrapper.orderByDesc(SysUser::getCreateTime);
        
        Page<SysUser> result = userService.page(page, queryWrapper);
        return R.ok(result);
    }

    /**
     * 获取用户详情
     */
    @GetMapping("/{id}")
    @Operation(summary = "获取用户详情")
    public R<SysUser> getById(@PathVariable("id") Long id) {
        SysUser user = userService.getById(id);
        // 出于安全考虑，不返回密码
        if (user != null) {
            user.setPassword(null);
        }
        return R.ok(user);
    }

    /**
     * 新增用户
     */
    @PostMapping
    @Operation(summary = "新增用户")
    public R<Void> add(@RequestBody @Valid SysUser user) {
        // 检查用户名唯一性
        SysUser existUser = userService.getByUsername(user.getUsername());
        if (existUser != null) {
            return R.fail("用户名已存在");
        }
        
        userService.save(user);
        // 分配默认角色
        userService.assignDefaultRole(user.getId());
        return R.ok();
    }

    /**
     * 修改用户
     */
    @PutMapping("/{id}")
    @Operation(summary = "修改用户")
    public R<Void> update(@PathVariable("id") Long id, @RequestBody @Valid SysUser user) {
        // 设置用户ID
        user.setId(id);
        
        // 检查用户名唯一性（排除自身）
        SysUser existUser = userService.getByUsername(user.getUsername());
        if (existUser != null && !existUser.getId().equals(id)) {
            return R.fail("用户名已存在");
        }
        
        // 不修改密码（密码修改通过专门的接口处理）
        user.setPassword(null);
        
        userService.updateById(user);
        return R.ok();
    }

    /**
     * 删除用户
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "删除用户")
    public R<Void> delete(@PathVariable("id") Long id) {
        userService.removeById(id);
        return R.ok();
    }

    /**
     * 重置用户密码
     */
    @PostMapping("/{id}/password/reset")
    @Operation(summary = "重置用户密码")
    public R<Void> resetPassword(
            @PathVariable("id") @Parameter(description = "用户ID") Long id,
            @RequestBody @Valid ResetPasswordRequest request) {
        userService.resetPassword(id, request.getNewPassword());
        return R.ok();
    }

    /**
     * 修改用户状态
     */
    @PutMapping("/{id}/status/{status}")
    @Operation(summary = "修改用户状态")
    public R<Void> updateStatus(
            @PathVariable("id") @Parameter(description = "用户ID") Long id,
            @PathVariable("status") @Parameter(description = "状态（0-禁用 1-正常）") Integer status) {
        userService.updateStatus(id, status);
        return R.ok();
    }

    /**
     * 获取用户的角色ID列表
     */
    @GetMapping("/{id}/roles")
    @Operation(summary = "获取用户的角色ID列表")
    public R<List<Long>> getUserRoles(@PathVariable("id") Long id) {
        List<Long> roleIds = userService.getUserRoleIds(id);
        return R.ok(roleIds);
    }

    /**
     * 分配用户角色
     */
    @PostMapping("/{id}/roles")
    @Operation(summary = "分配用户角色")
    public R<Void> assignRoles(
            @PathVariable("id") @Parameter(description = "用户ID") Long id,
            @RequestBody @Valid AssignUserRolesRequest request) {
        userService.assignUserRoles(id, request.getRoleIds());
        return R.ok();
    }
} 