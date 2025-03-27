package com.lightereb.hrms.mapper.system;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lightereb.hrms.model.entity.system.SysUserRole;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SysUserRoleMapper extends BaseMapper<SysUserRole>
{
	/**
	 * 获取用户的角色ID列表
	 */
	@Select("SELECT role_id FROM sys_user_role WHERE user_id = #{userId}")
	List<Long> selectRoleIdsByUserId(@Param("userId") Long userId);
	/**
	 * 批量插入用户角色关联
	 *
	 * @return
	 */
	@Insert("INSERT INTO sys_user_role(user_id, role_id, create_time) VALUES " +
			"(#{userId}, #{roleId}, #{createTime})")
	int insert(SysUserRole userRole);
}
