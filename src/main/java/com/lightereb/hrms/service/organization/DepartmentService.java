package com.lightereb.hrms.service.organization;

import com.lightereb.hrms.model.entity.organization.Department;
import com.lightereb.hrms.service.BaseService;

import java.util.List;

/**
 * 部门Service接口
 */
public interface DepartmentService extends BaseService<Department> {

	/**
	 * 获取部门树形结构
	 */
	List<Department> getDepartmentTree();

	/**
	 * 获取子部门ID列表
	 */
	List<Long> getChildDeptIds(Long deptId);

	/**
	 * 检查部门是否存在下级部门
	 */
	boolean hasChildren(Long deptId);

	/**
	 * 检查部门是否存在员工
	 */
	boolean hasEmployees(Long deptId);

	/**
	 * 更新部门状态
	 */
	void updateStatus(Long deptId, Integer status);
}