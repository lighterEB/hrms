package com.lightereb.hrms.service.system;

import com.lightereb.hrms.model.entity.system.SysUser;
import com.lightereb.hrms.service.BaseService;

/**
 * 系统用户Service接口
 */
public interface SysUserService extends BaseService<SysUser> {

    /**
     * 根据用户名查询用户
     */
    SysUser getByUsername(String username);

    /**
     * 更新用户最后登录时间
     */
    void updateLastLoginTime(Long userId);

    /**
     * 重置用户密码
     */
    void resetPassword(Long userId, String newPassword);

    /**
     * 修改用户状态
     */
    void updateStatus(Long userId, Integer status);
}