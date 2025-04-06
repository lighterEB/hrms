package com.lightereb.hrms.mapper.system;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lightereb.hrms.model.entity.system.SysUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * 用户Mapper接口
 */
@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {

	/**
	 * 根据用户名查询用户
	 */
	@Select("SELECT * FROM sys_user WHERE username = #{username} AND is_deleted = 0")
	SysUser selectByUsername(String username);
}