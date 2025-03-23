package com.lightereb.hrms.service.attendance.impl;

import com.lightereb.hrms.common.exception.BusinessException;
import com.lightereb.hrms.mapper.attendance.LeaveMapper;
import com.lightereb.hrms.model.entity.attendance.Leave;
import com.lightereb.hrms.service.impl.BaseServiceImpl;
import com.lightereb.hrms.service.attendance.LeaveService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 请假Service实现类
 */
@Slf4j
@Service
public class LeaveServiceImpl extends BaseServiceImpl<LeaveMapper, Leave> implements LeaveService {

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void applyLeave(Leave leave) {
		// 检查请假时间是否冲突
		if (checkLeaveConflict(leave)) {
			throw new BusinessException("该时间段内已有请假记录");
		}

		// 设置初始状态
		leave.setApprovalStatus(0); // 待审批
		save(leave);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void approveLeave(Long leaveId, Integer approvalStatus, String approvalRemark) {
		Leave leave = getById(leaveId);
		if (leave == null) {
			throw new BusinessException("请假记录不存在");
		}

		if (!leave.getApprovalStatus().equals(0)) {
			throw new BusinessException("该请假记录已被审批");
		}

		leave.setApprovalStatus(approvalStatus);
		leave.setApprovalRemark(approvalRemark);
		leave.setApprovalTime(LocalDateTime.now());
		updateById(leave);
	}

	@Override
	public List<Leave> getPendingLeaves(Long empId) {
		return baseMapper.selectPendingLeaves(empId);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void cancelLeave(Long leaveId) {
		Leave leave = getById(leaveId);
		if (leave == null) {
			throw new BusinessException("请假记录不存在");
		}

		if (!leave.getApprovalStatus().equals(0)) {
			throw new BusinessException("只能取消待审批的请假记录");
		}

		removeById(leaveId);
	}

	@Override
	public boolean checkLeaveConflict(Leave leave) {
		return baseMapper.checkLeaveConflict(leave.getEmpId(), leave.getStartTime(), leave.getEndTime()) > 0;
	}
}