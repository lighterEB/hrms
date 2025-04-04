package com.lightereb.hrms.mapper.organization;

import com.lightereb.hrms.mapper.BaseMapper;
import com.lightereb.hrms.model.entity.organization.Department;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 部门Mapper接口
 */
@Mapper
public interface DepartmentMapper extends BaseMapper<Department> {

	/**
	 * 获取子部门ID列表
	 */
	@Select("WITH RECURSIVE temp AS (" +
			"   SELECT id FROM hr_department WHERE id = #{deptId} " +
			"   UNION ALL " +
			"   SELECT d.id FROM hr_department d " +
			"   INNER JOIN temp t ON d.parent_id = t.id" +
			") " +
			"SELECT id FROM temp")
	List<Long> selectChildrenIds(Long deptId);
}