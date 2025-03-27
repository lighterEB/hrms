package com.lightereb.hrms.mapper.system;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lightereb.hrms.model.entity.system.SysRolePermission;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SysRolePermissionMapper extends BaseMapper<SysRolePermission>
{
	@Select("<script>" +
			"SELECT permission_id FROM sys_role_permission WHERE role_id IN " +
			"<foreach collection='roleIds' item='roleId' open='(' separator=',' close=')'>" +
			"#{roleId}" +
			"</foreach>" +
			"</script>")
	List<Long> selectPermissionIdsByRoleIds(@Param("roleIds") List<Long> roleIds);
}
