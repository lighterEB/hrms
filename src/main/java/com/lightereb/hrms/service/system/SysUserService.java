package com.lightereb.hrms.service.system;

import com.lightereb.hrms.dto.response.UserInfoResponse;
import com.lightereb.hrms.entity.system.SysUser;

public interface SysUserService {
    /**
     * 根据用户名查询用户
     * @param username 用户名
     * @return 用户实体
     */
    SysUser getByUsername(String username);

    /**
     * 根据ID查询用户信息
     * @param id 用户ID
     * @return 用户信息DTO
     */
    UserInfoResponse getUserInfoById(Long id);

    /**
     * 根据用户名查询用户信息
     * @param username 用户名
     * @return 用户信息DTO
     */
    UserInfoResponse getUserInfoByUsername(String username);

    /**
     * 更新最后登录时间
     * @param userId 用户ID
     */
    void updateLastLoginTime(Long userId);
}
