package com.lightereb.hrms.model.entity.training;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lightereb.hrms.model.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 培训记录表
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("hr_training")
public class Training extends BaseEntity {

	/**
	 * 培训名称
	 */
	private String trainingName;

	/**
	 * 培训类型
	 */
	private String trainingType;

	/**
	 * 培训内容
	 */
	private String content;

	/**
	 * 开始时间
	 */
	private LocalDateTime startTime;

	/**
	 * 结束时间
	 */
	private LocalDateTime endTime;

	/**
	 * 培训时长(小时)
	 */
	private Integer duration;

	/**
	 * 培训讲师
	 */
	private String trainer;

	/**
	 * 培训地点
	 */
	private String location;

	/**
	 * 培训预算
	 */
	private BigDecimal budget;

	/**
	 * 实际费用
	 */
	private BigDecimal actualCost;

	/**
	 * 状态 0-计划中 1-进行中 2-已完成 3-已取消
	 */
	private Integer status;

	/**
	 * 备注
	 */
	private String remark;
}