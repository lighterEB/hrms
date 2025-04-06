package com.lightereb.hrms.service.training;

import com.lightereb.hrms.model.entity.training.Training;
import com.lightereb.hrms.service.BaseService;

import java.util.List;

/**
 * 培训Service接口
 */
public interface TrainingService extends BaseService<Training> {

	/**
	 * 获取进行中的培训
	 */
	List<Training> getOngoingTrainings();

	/**
	 * 获取员工参与的培训
	 */
	List<Training> getTrainingsByEmpId(Long empId);

	/**
	 * 开始培训
	 */
	void startTraining(Long trainingId);

	/**
	 * 结束培训
	 */
	void endTraining(Long trainingId);

	/**
	 * 取消培训
	 */
	void cancelTraining(Long trainingId, String reason);
}