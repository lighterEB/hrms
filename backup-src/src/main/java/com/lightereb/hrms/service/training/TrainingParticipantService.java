package com.lightereb.hrms.service.training;

import com.lightereb.hrms.model.entity.training.TrainingParticipant;
import com.lightereb.hrms.service.BaseService;

import java.util.List;

/**
 * 培训参与Service接口
 */
public interface TrainingParticipantService extends BaseService<TrainingParticipant> {

	/**
	 * 获取培训参与人员
	 */
	List<TrainingParticipant> getParticipantsByTrainingId(Long trainingId);

	/**
	 * 获取员工培训记录
	 */
	List<TrainingParticipant> getParticipantsByEmpId(Long empId);

	/**
	 * 批量添加培训参与人员
	 */
	void batchAddParticipants(Long trainingId, List<Long> empIds);

	/**
	 * 记录培训成绩
	 */
	void recordScore(Long participantId, Double score);

	/**
	 * 提交培训反馈
	 */
	void submitFeedback(Long participantId, String feedback);
}