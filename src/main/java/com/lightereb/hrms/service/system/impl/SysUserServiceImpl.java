package com.lightereb.hrms.service.system.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lightereb.hrms.dto.response.UserInfoResponse;
import com.lightereb.hrms.model.entity.system.SysUser;
import com.lightereb.hrms.mapper.system.SysUserMapper;
import com.lightereb.hrms.service.system.SysUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class SysUserServiceImpl implements SysUserService {

    private final SysUserMapper userMapper;

    @Override
    public SysUser getByUsername(String username) {
        return userMapper.selectOne(
                new LambdaQueryWrapper<SysUser>()
                        .eq(SysUser::getUsername, username)
                        .eq(SysUser::getIsDeleted, 0)
        );
    }

    @Override
    public UserInfoResponse getUserInfoById(Long id) {
        SysUser user = userMapper.selectById(id);
        return convertToUserInfoResponse(user);
    }

    @Override
    public UserInfoResponse getUserInfoByUsername(String username) {
        SysUser user = getByUsername(username);
        return convertToUserInfoResponse(user);
    }

    @Override
    public void updateLastLoginTime(Long userId) {
        SysUser user = new SysUser();
        user.setId(userId);
        user.setLastLoginTime(LocalDateTime.now());
        user.setUpdateTime(LocalDateTime.now());
        userMapper.updateById(user);
    }

    /**
     * 将SysUser实体转换为UserInfoResponse DTO
     */
    private UserInfoResponse convertToUserInfoResponse(SysUser user) {
        if (user == null) {
            return null;
        }

        UserInfoResponse userInfoResponse = new UserInfoResponse();
        BeanUtils.copyProperties(user, userInfoResponse);
        return userInfoResponse;
    }
}
