package com.lightereb.hrms.model.entity.attendance;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lightereb.hrms.model.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 考勤表
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("hr_attendance")
public class Attendance extends BaseEntity {

	/**
	 * 员工ID
	 */
	private Long empId;

	/**
	 * 考勤日期
	 */
	private LocalDate attendanceDate;

	/**
	 * 签到时间
	 */
	private LocalDateTime checkInTime;

	/**
	 * 签退时间
	 */
	private LocalDateTime checkOutTime;

	/**
	 * 考勤状态 0-缺勤 1-正常 2-迟到 3-早退 4-请假 5-外勤
	 */
	private Integer attendanceStatus;

	/**
	 * 工作时长(小时)
	 */
	private BigDecimal workHours;

	/**
	 * 加班时长(小时)
	 */
	private BigDecimal overtimeHours;

	/**
	 * 迟到时长(分钟)
	 */
	private Integer lateMinutes;

	/**
	 * 早退时长(分钟)
	 */
	private Integer earlyLeaveMinutes;

	/**
	 * 请假类型
	 */
	private String leaveType;

	/**
	 * 备注
	 */
	private String remark;
}