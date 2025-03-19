package com.lightereb.hrms.service.auth;

import com.lightereb.hrms.dto.response.LoginResponse;

public interface AuthService {
    /**
     * 用户登录
     * @param username 用户名
     * @param password 密码
     * @return 登录响应，包含令牌和用户信息
     */
    LoginResponse login(String username, String password);

    /**
     * 获取当前登录用户名
     * @return 当前用户名
     */
    String getCurrentUsername();
}