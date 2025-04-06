package com.lightereb.hrms.model.entity.attendance;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lightereb.hrms.model.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 请假记录表
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("hr_leave")
public class Leave extends BaseEntity {

	/**
	 * 员工ID
	 */
	private Long empId;

	/**
	 * 请假类型(年假、病假、事假等)
	 */
	private String leaveType;

	/**
	 * 开始时间
	 */
	private LocalDateTime startTime;

	/**
	 * 结束时间
	 */
	private LocalDateTime endTime;

	/**
	 * 请假时长(天)
	 */
	private BigDecimal duration;

	/**
	 * 请假原因
	 */
	private String reason;

	/**
	 * 附件URL
	 */
	private String attachmentUrl;

	/**
	 * 审批状态 0-待审批 1-已批准 2-已拒绝
	 */
	private Integer approvalStatus;

	/**
	 * 审批人ID
	 */
	private Long approverId;

	/**
	 * 审批时间
	 */
	private LocalDateTime approvalTime;

	/**
	 * 审批备注
	 */
	private String approvalRemark;
}