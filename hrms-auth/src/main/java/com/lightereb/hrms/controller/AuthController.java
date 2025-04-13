package com.lightereb.hrms.controller;

import com.lightereb.hrms.common.result.R;
import com.lightereb.hrms.dto.request.LoginRequest;
import com.lightereb.hrms.dto.request.RegisterRequest;
import com.lightereb.hrms.dto.request.UpdatePasswordRequest;
import com.lightereb.hrms.dto.response.LoginResponse;
import com.lightereb.hrms.model.entity.system.SysUser;
import com.lightereb.hrms.service.auth.AuthService;
import com.lightereb.hrms.service.system.SysUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 认证控制器
 */
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "认证管理", description = "认证相关接口")
public class AuthController {

	private final AuthService authService;
	private final SysUserService userService;

	/**
	 * 用户登录
	 */
	@Operation(summary = "用户登录", description = "用户登录接口")
	@PostMapping("/login")
	public R<LoginResponse> login(@RequestBody @Valid LoginRequest request) {
		LoginResponse response = authService.login(request.getUsername(), request.getPassword());
		return R.ok(response, "登录成功");
	}

	/**
	 * 用户注册
	 */
	@Operation(summary = "用户注册", description = "用户注册接口")
	@PostMapping("/register")
	public R<Void> register(@RequestBody @Valid RegisterRequest request) {
		authService.register(request);
		return R.ok(null, "注册成功");
	}

	/**
	 * 修改密码
	 */
	@Operation(summary = "修改密码", description = "修改当前登录用户的密码", security = {@SecurityRequirement(name = "Authorization")})
	@PostMapping("/password")
	public R<Void> updatePassword(@RequestBody @Valid UpdatePasswordRequest request) {
		authService.updatePassword(request);
		return R.ok(null, "密码修改成功");
	}

	/**
	 * 获取当前登录用户信息
	 */
	@Operation(summary = "获取用户信息", description = "获取当前登录用户的详细信息", security = {@SecurityRequirement(name = "Authorization")})
	@GetMapping("/info")
	public R<SysUser> getUserInfo() {
		String username = authService.getCurrentUsername();
		SysUser userInfo = userService.getByUsername(username);
		return R.ok(userInfo);
	}

	/**
	 * 退出登录
	 */
	@Operation(summary = "退出登录", description = "用户退出登录", security = {@SecurityRequirement(name = "Authorization")})
	@PostMapping("/logout")
	public R<Void> logout() {
		return R.ok(null, "退出成功");
	}
}