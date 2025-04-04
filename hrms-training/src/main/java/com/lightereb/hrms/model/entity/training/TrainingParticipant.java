package com.lightereb.hrms.model.entity.training;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lightereb.hrms.model.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * 培训参与表
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("hr_training_participant")
public class TrainingParticipant extends BaseEntity {

	/**
	 * 培训ID
	 */
	private Long trainingId;

	/**
	 * 员工ID
	 */
	private Long empId;

	/**
	 * 出勤状态 0-缺席 1-出席
	 */
	private Integer attendanceStatus;

	/**
	 * 考核成绩
	 */
	private BigDecimal score;

	/**
	 * 获得证书
	 */
	private String certificate;

	/**
	 * 培训反馈
	 */
	private String feedback;
}