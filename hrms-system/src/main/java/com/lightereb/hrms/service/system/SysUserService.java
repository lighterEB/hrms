package com.lightereb.hrms.service.system;

import com.lightereb.hrms.model.entity.system.SysUser;
import com.lightereb.hrms.service.BaseService;

public interface SysUserService extends BaseService<SysUser> {
    /**
     * 根据用户名查询用户
     */
    SysUser getByUsername(String username);

    /**
     * 检查用户名是否已存在
     */
    boolean existsByUsername(String username);
}