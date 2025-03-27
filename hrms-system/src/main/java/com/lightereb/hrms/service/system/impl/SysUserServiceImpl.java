package com.lightereb.hrms.service.system.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lightereb.hrms.mapper.system.SysUserMapper;
import com.lightereb.hrms.model.entity.system.SysUser;
import com.lightereb.hrms.service.impl.BaseServiceImpl;
import com.lightereb.hrms.service.system.SysUserService;
import org.springframework.stereotype.Service;

@Service
public class SysUserServiceImpl extends BaseServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    @Override
    public SysUser getByUsername(String username) {
        return getOne(new LambdaQueryWrapper<SysUser>()
                .eq(SysUser::getUsername, username));
    }

    @Override
    public boolean existsByUsername(String username) {
        return count(new LambdaQueryWrapper<SysUser>()
                .eq(SysUser::getUsername, username)) > 0;
    }
}