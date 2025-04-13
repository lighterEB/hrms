package com.lightereb.hrms.service.impl;

import com.lightereb.hrms.common.exception.BusinessException;
import com.lightereb.hrms.dto.request.RegisterRequest;
import com.lightereb.hrms.dto.request.UpdatePasswordRequest;
import com.lightereb.hrms.dto.response.LoginResponse;
import com.lightereb.hrms.dto.response.UserInfoResponse;
import com.lightereb.hrms.model.entity.system.SysUser;
import com.lightereb.hrms.security.util.JwtTokenUtil;
import com.lightereb.hrms.service.AuthService;
import com.lightereb.hrms.service.SysRoleService;
import com.lightereb.hrms.service.SysUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

	private final AuthenticationManager authenticationManager;
	private final JwtTokenUtil jwtTokenUtil;
	private final SysUserService userService;
	private final SysRoleService roleService;
	private final PasswordEncoder passwordEncoder;

	@Override
	public LoginResponse login(String username, String password) {
		try {
			// 认证
			Authentication authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(username, password)
			);
			SecurityContextHolder.getContext().setAuthentication(authentication);

			// 生成令牌
			UserDetails userDetails = (UserDetails) authentication.getPrincipal();
			String token = jwtTokenUtil.generateToken(userDetails);

			// 获取用户信息
			SysUser user = userService.getByUsername(username);
			if (user == null) {
				throw new BusinessException("用户不存在");
			}

			// 构建用户信息响应
			UserInfoResponse userInfo = UserInfoResponse.builder()
					.id(user.getId())
					.username(user.getUsername())
					.realName(user.getRealName())
					.avatar(user.getAvatar())
					.email(user.getEmail())
					.phone(user.getPhone())
					.roles(roleService.getRoleCodesByUserId(user.getId()))
					.permissions(roleService.getPermissionCodesByUserId(user.getId()))
					.build();

			// 更新登录时间
			userService.updateLastLoginTime(user.getId());

			// 返回登录响应
			return LoginResponse.of(
					token,
					jwtTokenUtil.getExpirationTimeInMillis() / 1000,
					userInfo
			);

		} catch (org.springframework.security.authentication.BadCredentialsException e) {
			log.error("凭证错误：{}", e.getMessage(), e);
			throw new BusinessException("用户名或密码错误");
		} catch (org.springframework.security.authentication.DisabledException e) {
			log.error("账户已禁用：{}", e.getMessage(), e);
			throw new BusinessException("账户已被禁用");
		} catch (org.springframework.security.authentication.LockedException e) {
			log.error("账户已锁定：{}", e.getMessage(), e);
			throw new BusinessException("账户已被锁定");
		} catch (BusinessException | AuthenticationException e) {
			log.error("登录失败：{}", e.getMessage(), e);
			throw new BusinessException("身份验证失败");
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void register(RegisterRequest request) {
		// 检查用户名是否已存在
		if (userService.getByUsername(request.getUsername()) != null) {
			throw new BusinessException("用户名已存在");
		}

		// 创建用户
		SysUser user = new SysUser()
				.setUsername(request.getUsername())
				.setPassword(passwordEncoder.encode(request.getPassword()))
				.setRealName(request.getRealName())
				.setPhone(request.getPhone())
				.setEmail(request.getEmail())
				.setStatus(1);  // 正常状态

		userService.save(user);

		// 分配默认角色
		userService.assignDefaultRole(user.getId());
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void updatePassword(UpdatePasswordRequest request) {
		// 获取当前用户
		String username = getCurrentUsername();
		SysUser user = userService.getByUsername(username);

		// 验证原密码
		if (!passwordEncoder.matches(request.getOldPassword(), user.getPassword())) {
			throw new BusinessException("原密码不正确");
		}

		// 验证新密码与确认密码
		if (!request.getNewPassword().equals(request.getConfirmPassword())) {
			throw new BusinessException("两次输入的密码不一致");
		}

		// 更新密码
		user.setPassword(passwordEncoder.encode(request.getNewPassword()));
		userService.updateById(user);
	}

	@Override
	public String getCurrentUsername() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null && authentication.isAuthenticated()) {
			return authentication.getName();
		}
		return null;
	}
}