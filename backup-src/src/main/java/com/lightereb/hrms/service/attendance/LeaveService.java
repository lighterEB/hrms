package com.lightereb.hrms.service.attendance;

import com.lightereb.hrms.model.entity.attendance.Leave;
import com.lightereb.hrms.service.BaseService;

import java.util.List;

/**
 * 请假Service接口
 */
public interface LeaveService extends BaseService<Leave> {

	/**
	 * 申请请假
	 */
	void applyLeave(Leave leave);

	/**
	 * 审批请假
	 */
	void approveLeave(Long leaveId, Integer approvalStatus, String approvalRemark);

	/**
	 * 获取待审批的请假记录
	 */
	List<Leave> getPendingLeaves(Long empId);

	/**
	 * 取消请假申请
	 */
	void cancelLeave(Long leaveId);

	/**
	 * 检查请假时间是否冲突
	 */
	boolean checkLeaveConflict(Leave leave);
}