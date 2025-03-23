package com.lightereb.hrms.service.training.impl;

import com.lightereb.hrms.common.exception.BusinessException;
import com.lightereb.hrms.mapper.training.TrainingMapper;
import com.lightereb.hrms.model.entity.training.Training;
import com.lightereb.hrms.service.impl.BaseServiceImpl;
import com.lightereb.hrms.service.training.TrainingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 培训Service实现类
 */
@Slf4j
@Service
public class TrainingServiceImpl extends BaseServiceImpl<TrainingMapper, Training> implements TrainingService {

	@Override
	public List<Training> getOngoingTrainings() {
		return baseMapper.selectOngoingTrainings();
	}

	@Override
	public List<Training> getTrainingsByEmpId(Long empId) {
		return baseMapper.selectTrainingsByEmpId(empId);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void startTraining(Long trainingId) {
		Training training = getById(trainingId);
		if (training == null) {
			throw new BusinessException("培训记录不存在");
		}

		if (!training.getStatus().equals(0)) {
			throw new BusinessException("只能开始计划中的培训");
		}

		training.setStatus(1); // 进行中
		updateById(training);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void endTraining(Long trainingId) {
		Training training = getById(trainingId);
		if (training == null) {
			throw new BusinessException("培训记录不存在");
		}

		if (!training.getStatus().equals(1)) {
			throw new BusinessException("只能结束进行中的培训");
		}

		training.setStatus(2); // 已完成
		updateById(training);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void cancelTraining(Long trainingId, String reason) {
		Training training = getById(trainingId);
		if (training == null) {
			throw new BusinessException("培训记录不存在");
		}

		if (training.getStatus().equals(2)) {
			throw new BusinessException("已完成的培训不能取消");
		}

		training.setStatus(3); // 已取消
		training.setRemark(reason);
		updateById(training);
	}
}