package com.lightereb.hrms.dto.response;

import lombok.Builder;
import lombok.Data;

/**
 * 登录响应对象
 */
@Data
@Builder
public class LoginResponse {
    /**
     * 访问令牌
     */
    private String token;

    /**
     * 令牌类型
     */
    private String tokenType;

    /**
     * 过期时间（秒）
     */
    private Long expiresIn;

    /**
     * 用户信息
     */
    private UserInfoResponse userInfo;

    public static LoginResponse of(String token, Long expiresIn, UserInfoResponse userInfo) {
        return LoginResponse.builder()
                .token(token)
                .tokenType("Bearer")
                .expiresIn(expiresIn)
                .userInfo(userInfo)
                .build();
    }
}