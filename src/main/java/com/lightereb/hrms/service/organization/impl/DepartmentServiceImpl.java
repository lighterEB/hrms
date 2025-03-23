package com.lightereb.hrms.service.organization.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lightereb.hrms.common.exception.BusinessException;
import com.lightereb.hrms.mapper.organization.DepartmentMapper;
import com.lightereb.hrms.model.entity.organization.Department;
import com.lightereb.hrms.service.impl.BaseServiceImpl;
import com.lightereb.hrms.service.organization.DepartmentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 部门Service实现类
 */
@Slf4j
@Service
public class DepartmentServiceImpl extends BaseServiceImpl<DepartmentMapper, Department> implements DepartmentService {

	@Override
	public List<Department> getDepartmentTree() {
		List<Department> allDepts = list();
		return buildTree(allDepts, 0L);
	}

	@Override
	public List<Long> getChildDeptIds(Long deptId) {
		return baseMapper.selectChildrenIds(deptId);
	}

	@Override
	public boolean hasChildren(Long deptId) {
		LambdaQueryWrapper<Department> wrapper = new LambdaQueryWrapper<>();
		wrapper.eq(Department::getParentId, deptId);
		return count(wrapper) > 0;
	}

	@Override
	public boolean hasEmployees(Long deptId) {
		// TODO: 实现检查部门是否存在员工的逻辑
		return false;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void updateStatus(Long deptId, Integer status) {
		Department dept = getById(deptId);
		if (dept == null) {
			throw new BusinessException("部门不存在");
		}
		dept.setStatus(status);
		updateById(dept);
	}

	/**
	 * 构建部门树形结构
	 */
	private List<Department> buildTree(List<Department> depts, Long parentId) {
		List<Department> tree = new ArrayList<>();
		Map<Long, List<Department>> childrenMap = depts.stream()
				.collect(Collectors.groupingBy(Department::getParentId));

		depts.stream()
				.filter(dept -> dept.getParentId().equals(parentId))
				.forEach(dept -> {
					List<Department> children = buildTree(depts, dept.getId());
					if (!children.isEmpty()) {
						// TODO: 设置子部门
					}
					tree.add(dept);
				});

		return tree;
	}
}