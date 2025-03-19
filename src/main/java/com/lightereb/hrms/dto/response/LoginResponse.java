package com.lightereb.hrms.dto.response;

import lombok.Data;

@Data
public class LoginResponse {
    private String token;
    private String tokenType = "Bearer";
    private Long expiresIn;
    private UserInfoResponse userInfo;

    public LoginResponse(String token, Long expiresIn, UserInfoResponse userInfo) {
        this.token = token;
        this.expiresIn = expiresIn;
        this.userInfo = userInfo;
    }
}