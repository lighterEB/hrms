package com.lightereb.hrms.service;

import com.lightereb.hrms.dto.request.RegisterRequest;
import com.lightereb.hrms.dto.request.UpdatePasswordRequest;
import com.lightereb.hrms.dto.response.LoginResponse;

public interface AuthService {
    /**
     * 用户登录
     */
    LoginResponse login(String username, String password);

    /**
     * 用户注册
     */
    void register(RegisterRequest request);

    /**
     * 修改密码
     */
    void updatePassword(UpdatePasswordRequest request);

    /**
     * 获取当前登录用户名
     */
    String getCurrentUsername();
}