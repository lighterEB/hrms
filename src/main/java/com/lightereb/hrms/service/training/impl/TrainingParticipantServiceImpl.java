package com.lightereb.hrms.service.training.impl;

import com.lightereb.hrms.common.exception.BusinessException;
import com.lightereb.hrms.mapper.training.TrainingParticipantMapper;
import com.lightereb.hrms.model.entity.training.TrainingParticipant;
import com.lightereb.hrms.service.impl.BaseServiceImpl;
import com.lightereb.hrms.service.training.TrainingParticipantService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 培训参与Service实现类
 */
@Slf4j
@Service
public class TrainingParticipantServiceImpl extends BaseServiceImpl<TrainingParticipantMapper, TrainingParticipant> implements TrainingParticipantService {

	@Override
	public List<TrainingParticipant> getParticipantsByTrainingId(Long trainingId) {
		return baseMapper.selectByTrainingId(trainingId);
	}

	@Override
	public List<TrainingParticipant> getParticipantsByEmpId(Long empId) {
		return baseMapper.selectByEmpId(empId);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void batchAddParticipants(Long trainingId, List<Long> empIds) {
		List<TrainingParticipant> participants = empIds.stream()
				.map(empId -> {
					TrainingParticipant participant = new TrainingParticipant();
					participant.setTrainingId(trainingId);
					participant.setEmpId(empId);
					participant.setAttendanceStatus(1); // 默认出席
					return participant;
				})
				.collect(Collectors.toList());

		saveBatch(participants);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void recordScore(Long participantId, Double score) {
		TrainingParticipant participant = getById(participantId);
		if (participant == null) {
			throw new BusinessException("培训参与记录不存在");
		}

		participant.setScore(BigDecimal.valueOf(score));
		updateById(participant);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void submitFeedback(Long participantId, String feedback) {
		TrainingParticipant participant = getById(participantId);
		if (participant == null) {
			throw new BusinessException("培训参与记录不存在");
		}

		participant.setFeedback(feedback);
		updateById(participant);
	}
}