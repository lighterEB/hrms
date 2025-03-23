package com.lightereb.hrms.controller.auth;

import com.lightereb.hrms.common.result.R;
import com.lightereb.hrms.dto.request.LoginRequest;
import com.lightereb.hrms.dto.request.RegisterRequest;
import com.lightereb.hrms.dto.request.UpdatePasswordRequest;
import com.lightereb.hrms.dto.response.LoginResponse;
import com.lightereb.hrms.dto.response.UserInfoResponse;
import com.lightereb.hrms.model.entity.system.SysUser;
import com.lightereb.hrms.service.auth.AuthService;
import com.lightereb.hrms.service.system.SysUserService;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * 认证控制器
 */
@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

	private final AuthService authService;

	@Resource
	private SysUserService userService;

	/**
	 * 用户登录
	 */
	@PostMapping("/login")
	public R<LoginResponse> login(@RequestBody @Valid LoginRequest request) {
		LoginResponse response = authService.login(request.getUsername(), request.getPassword());
		return R.ok(response, "登录成功");
	}

	/**
	 * 用户注册
	 */
	@PostMapping("/register")
	public R<Void> register(@RequestBody @Valid RegisterRequest request) {
		authService.register(request);
		return R.ok(null, "注册成功");
	}

	/**
	 * 修改密码
	 */
	@PostMapping("/password")
	public R<Void> updatePassword(@RequestBody @Valid UpdatePasswordRequest request) {
		authService.updatePassword(request);
		return R.ok(null, "密码修改成功");
	}

	/**
	 * 获取当前登录用户信息
	 */
	@GetMapping("/info")
	public R<SysUser> getUserInfo() {
		String username = authService.getCurrentUsername();
		SysUser userInfo = userService.getByUsername(username);
		return R.ok(userInfo);
	}

	/**
	 * 退出登录
	 */
	@PostMapping("/logout")
	public R<Void> logout() {
		return R.ok(null, "退出成功");
	}
}