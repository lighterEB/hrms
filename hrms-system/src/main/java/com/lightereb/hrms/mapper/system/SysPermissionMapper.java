package com.lightereb.hrms.mapper.system;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.lightereb.hrms.common.mapper.BaseMapper;
import com.lightereb.hrms.model.entity.system.SysPermission;

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

	/**
	 * 根据用户ID查询权限编码列表
	 *
	 * @param userId 用户ID
	 * @return 权限编码列表
	 */
	@Select("SELECT DISTINCT p.perms FROM sys_permission p " +
			"INNER JOIN sys_role_permission rp ON p.id = rp.permission_id " +
			"INNER JOIN sys_role r ON rp.role_id = r.id " +
			"INNER JOIN sys_user_role ur ON r.id = ur.role_id " +
			"WHERE ur.user_id = #{userId} AND p.status = 1 AND p.perms IS NOT NULL AND p.perms != ''")
	List<String> selectPermissionCodesByUserId(@Param("userId") Long userId);
}