package com.lightereb.hrms.mapper.organization;

import com.lightereb.hrms.mapper.BaseMapper;
import com.lightereb.hrms.model.entity.organization.Position;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 职位Mapper接口
 */
@Mapper
public interface PositionMapper extends BaseMapper<Position> {

	/**
	 * 获取部门下的所有职位
	 */
	@Select("SELECT * FROM hr_position WHERE dept_id = #{deptId} AND is_deleted = 0")
	List<Position> selectByDeptId(Long deptId);
}