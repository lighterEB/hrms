package com.lightereb.hrms.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * 用户信息响应对象
 */
@Data
@Builder
public class UserInfoResponse {
    /**
     * 用户ID
     */
    private Long id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 真实姓名
     */
    private String realName;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 角色列表
     */
    private List<String> roles;

    /**
     * 权限列表
     */
    private List<String> permissions;
}