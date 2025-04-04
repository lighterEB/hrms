package com.lightereb.hrms.mapper.system;

import com.lightereb.hrms.common.mapper.BaseMapper;
import com.lightereb.hrms.model.entity.system.SysRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 角色Mapper接口
 */
@Mapper
public interface SysRoleMapper extends BaseMapper<SysRole> {
    
    /**
     * 查询用户角色列表
     */
    @Select("SELECT DISTINCT r.* FROM sys_role r " +
            "LEFT JOIN sys_user_role ur ON r.id = ur.role_id " +
            "WHERE ur.user_id = #{userId} AND r.is_deleted = 0")
    List<SysRole> selectRolesByUserId(Long userId);

    /**
     * 查询用户角色编码列表
     */
    @Select("SELECT DISTINCT r.role_code FROM sys_role r " +
            "LEFT JOIN sys_user_role ur ON r.id = ur.role_id " +
            "WHERE ur.user_id = #{userId} AND r.is_deleted = 0")
    List<String> selectRoleCodesByUserId(@Param("userId") Long userId);
} 