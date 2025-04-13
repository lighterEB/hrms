package com.lightereb.hrms.service.system;

import com.lightereb.hrms.common.service.BaseService;
import com.lightereb.hrms.model.entity.system.SysUser;

import java.util.List;

/**
 * 用户Service接口
 */
public interface SysUserService extends BaseService<SysUser> {

    /**
     * 根据用户名获取用户信息
     * @param username 用户名
     * @return 用户信息
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

    /**
     * 分配默认角色
     */
    void assignDefaultRole(Long userId);
    
    /**
     * 分配用户角色
     * @param userId 用户ID
     * @param roleIds 角色ID列表
     */
    void assignUserRoles(Long userId, List<Long> roleIds);
    
    /**
     * 获取用户角色ID列表
     * @param userId 用户ID
     * @return 角色ID列表
     */
    List<Long> getUserRoleIds(Long userId);
}