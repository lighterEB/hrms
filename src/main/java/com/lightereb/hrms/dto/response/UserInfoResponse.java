package com.lightereb.hrms.dto.response;

import lombok.Data;

@Data
public class UserInfoResponse {
    private Long id;
    private String username;
    private String realName;
    private String avatar;
    private String email;
    private String phone;
}
