package com.lightereb.hrms.mapper.attendance;

import com.lightereb.hrms.mapper.BaseMapper;
import com.lightereb.hrms.model.entity.attendance.Leave;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 请假记录Mapper接口
 */
@Mapper
public interface LeaveMapper extends BaseMapper<Leave> {

	/**
	 * 获取员工待审批的请假记录
	 */
	@Select("SELECT * FROM hr_leave WHERE emp_id = #{empId} AND approval_status = 0 AND is_deleted = 0")
	List<Leave> selectPendingLeaves(Long empId);

	/**
	 * 检查指定时间段是否有重复的请假记录
	 */
	@Select("SELECT COUNT(*) FROM hr_leave WHERE emp_id = #{empId} " +
			"AND ((start_time BETWEEN #{startTime} AND #{endTime}) " +
			"OR (end_time BETWEEN #{startTime} AND #{endTime})) " +
			"AND approval_status = 1 AND is_deleted = 0")
	int checkLeaveConflict(@Param("empId") Long empId,
	                       @Param("startTime") LocalDateTime startTime,
	                       @Param("endTime") LocalDateTime endTime);
}