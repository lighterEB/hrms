package com.lightereb.hrms.service.organization.impl;

import com.lightereb.hrms.common.exception.BusinessException;
import com.lightereb.hrms.mapper.organization.PositionMapper;
import com.lightereb.hrms.model.entity.organization.Position;
import com.lightereb.hrms.service.impl.BaseServiceImpl;
import com.lightereb.hrms.service.organization.PositionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 职位Service实现类
 */
@Slf4j
@Service
public class PositionServiceImpl extends BaseServiceImpl<PositionMapper, Position> implements PositionService {

	@Override
	public List<Position> getPositionsByDeptId(Long deptId) {
		return baseMapper.selectByDeptId(deptId);
	}

	@Override
	public boolean hasEmployees(Long positionId) {
		// TODO: 实现检查职位是否存在员工的逻辑
		return false;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void updateStatus(Long positionId, Integer status) {
		Position position = getById(positionId);
		if (position == null) {
			throw new BusinessException("职位不存在");
		}
		position.setStatus(status);
		updateById(position);
	}
}