package com.lightereb.hrms.service.attendance;

import com.lightereb.hrms.model.entity.attendance.Attendance;
import com.lightereb.hrms.model.vo.attendance.AttendanceStatistics;
import com.lightereb.hrms.service.BaseService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 考勤Service接口
 */
public interface AttendanceService extends BaseService<Attendance> {

	/**
	 * 签到
	 */
	void checkIn(Long empId);

	/**
	 * 签退
	 */
	void checkOut(Long empId);

	/**
	 * 获取员工某天的考勤记录
	 */
	Attendance getByEmpIdAndDate(Long empId, LocalDate date);

	/**
	 * 获取员工某月的考勤记录
	 */
	List<Attendance> getMonthlyAttendance(Long empId, LocalDate startDate, LocalDate endDate);

	/**
	 * 统计员工某月的考勤情况
	 */
	AttendanceStatistics getMonthlyStatistics(Long empId, Integer year, Integer month);
}