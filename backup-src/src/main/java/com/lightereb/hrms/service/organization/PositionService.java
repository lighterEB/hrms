package com.lightereb.hrms.service.organization;

import com.lightereb.hrms.model.entity.organization.Position;
import com.lightereb.hrms.service.BaseService;

import java.util.List;

/**
 * 职位Service接口
 */
public interface PositionService extends BaseService<Position> {

	/**
	 * 获取部门下的所有职位
	 */
	List<Position> getPositionsByDeptId(Long deptId);

	/**
	 * 检查职位是否存在员工
	 */
	boolean hasEmployees(Long positionId);

	/**
	 * 更新职位状态
	 */
	void updateStatus(Long positionId, Integer status);
}