package com.lightereb.hrms.controller.auth;

import com.lightereb.hrms.common.exception.BusinessException;
import com.lightereb.hrms.common.result.R;
import com.lightereb.hrms.dto.request.LoginRequest;
import com.lightereb.hrms.dto.response.LoginResponse;
import com.lightereb.hrms.dto.response.UserInfoResponse;
import com.lightereb.hrms.service.auth.AuthService;
import com.lightereb.hrms.service.system.SysUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

/**
 * 认证控制器
 * 处理登录、注册等认证相关请求
 */
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController
{

	private final AuthService authService;
	private final SysUserService userService;

	@Value("${jwt.secret}")
	private String jwtSecret;

	@Autowired
	private PasswordEncoder passwordEncoder;

	/**
	 * 用户登录
	 *
	 * @param loginRequest 登录请求
	 * @return 登录响应（令牌、过期时间、用户信息）
	 */
	@PostMapping("/login")
	public R<LoginResponse> login(@RequestBody @Valid LoginRequest loginRequest)
	{
		log.info(passwordEncoder.encode(loginRequest.getPassword()));
		log.info("jwt secret: {}", jwtSecret);
		LoginResponse response = authService.login(
				loginRequest.getUsername(),
				loginRequest.getPassword()
		);
		return R.ok(response, "登录成功");
	}

	/**
	 * 获取当前登录用户信息
	 *
	 * @return 用户信息
	 */
	@GetMapping("/info")
	public R<UserInfoResponse> getUserInfo()
	{
		String username = authService.getCurrentUsername();
		UserInfoResponse userInfo = userService.getUserInfoByUsername(username);
		return R.ok(userInfo);
	}

	/**
	 * 退出登录
	 * 注意：由于JWT是无状态的，服务端无法使令牌失效
	 * 实际退出逻辑应由前端清除本地存储的令牌处理
	 */
	@PostMapping("/logout")
	public R<Void> logout()
	{
		return R.ok(null, "退出成功");
	}
}
