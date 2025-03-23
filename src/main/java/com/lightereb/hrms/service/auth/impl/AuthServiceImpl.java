package com.lightereb.hrms.service.auth.impl;

import com.lightereb.hrms.common.exception.BusinessException;
import com.lightereb.hrms.dto.response.LoginResponse;
import com.lightereb.hrms.dto.response.UserInfoResponse;
import com.lightereb.hrms.model.entity.system.SysUser;
import com.lightereb.hrms.security.util.JwtTokenUtil;
import com.lightereb.hrms.service.auth.AuthService;
import com.lightereb.hrms.service.system.SysUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final SysUserService userService;

    @Override
    public LoginResponse login(String username, String password) {
        try {
            // 使用Spring Security进行身份验证
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password)
            );

            // 设置认证信息到上下文
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // 生成JWT令牌
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String token = jwtTokenUtil.generateToken(userDetails);

            // 获取用户信息
            SysUser user = userService.getByUsername(username);
            UserInfoResponse userInfo = userService.getUserInfoByUsername(username);

            // 更新用户最后登录时间
            userService.updateLastLoginTime(user.getId());

            // 构造并返回登录响应
            return new LoginResponse(
                    token,
                    jwtTokenUtil.getExpirationTimeInMillis() / 1000, // 转换为秒
                    userInfo
            );

        } catch (Exception e) {
            log.error("登录出错：{}",e.getMessage(),e);
            throw new BusinessException(e.getMessage());
        }
    }

    @Override
    public String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            return authentication.getName();
        }
        return null;
    }

    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String rawPassword = "123456";
        String hashedPassword = encoder.encode(rawPassword);
        System.out.println("Hashed Password: " + hashedPassword);
    }
}
