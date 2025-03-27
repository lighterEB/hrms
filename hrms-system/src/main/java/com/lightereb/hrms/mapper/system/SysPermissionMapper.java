package com.lightereb.hrms.mapper.system;

import com.lightereb.hrms.mapper.BaseMapper;
import com.lightereb.hrms.model.entity.system.SysPermission;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 权限Mapper接口
 */
@Mapper
public interface SysPermissionMapper extends BaseMapper<SysPermission> {

	/**
	 * 获取用户权限列表
	 */
	@Select("SELECT DISTINCT p.* FROM sys_permission p " +
			"LEFT JOIN sys_role_permission rp ON p.id = rp.permission_id " +
			"LEFT JOIN sys_user_role ur ON rp.role_id = ur.role_id " +
			"WHERE ur.user_id = #{userId} AND p.is_deleted = 0")
	List<SysPermission> selectPermissionsByUserId(Long userId);

	@Select("SELECT DISTINCT p.perms FROM sys_permission p " +
			"LEFT JOIN sys_role_permission rp ON p.id = rp.permission_id " +
			"LEFT JOIN sys_user_role ur ON rp.role_id = ur.role_id " +
			"WHERE ur.user_id = #{userId} AND p.is_deleted = 0")
	List<String> selectPermissionCodesByUserId(@Param("userId") Long userId);
}