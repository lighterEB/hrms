package com.lightereb.hrms.model.vo.attendance;

import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * 考勤统计值对象
 */
@Data
@Accessors(chain = true)
public class AttendanceStatistics {

	/**
	 * 员工ID
	 */
	private Long empId;

	/**
	 * 统计年份
	 */
	private Integer year;

	/**
	 * 统计月份
	 */
	private Integer month;

	/**
	 * 应出勤天数
	 */
	private Integer totalDays;

	/**
	 * 实际出勤天数
	 */
	private Integer actualDays;

	/**
	 * 正常出勤天数
	 */
	private Integer normalDays;

	/**
	 * 迟到次数
	 */
	private Integer lateTimes;

	/**
	 * 早退次数
	 */
	private Integer earlyLeaveTimes;

	/**
	 * 缺勤天数
	 */
	private Integer absentDays;

	/**
	 * 请假天数
	 */
	private Integer leaveDays;

	/**
	 * 外勤天数
	 */
	private Integer outDays;

	/**
	 * 总工作时长
	 */
	private BigDecimal totalWorkHours;

	/**
	 * 加班时长
	 */
	private BigDecimal overtimeHours;

	/**
	 * 出勤率
	 */
	private BigDecimal attendanceRate;
}