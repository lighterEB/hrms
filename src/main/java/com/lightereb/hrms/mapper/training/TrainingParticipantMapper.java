package com.lightereb.hrms.mapper.training;

import com.lightereb.hrms.mapper.BaseMapper;
import com.lightereb.hrms.model.entity.training.TrainingParticipant;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 培训参与Mapper接口
 */
@Mapper
public interface TrainingParticipantMapper extends BaseMapper<TrainingParticipant> {

	/**
	 * 获取培训的参与人员列表
	 */
	@Select("SELECT * FROM hr_training_participant WHERE training_id = #{trainingId} AND is_deleted = 0")
	List<TrainingParticipant> selectByTrainingId(Long trainingId);

	/**
	 * 获取员工的培训参与记录
	 */
	@Select("SELECT * FROM hr_training_participant WHERE emp_id = #{empId} AND is_deleted = 0")
	List<TrainingParticipant> selectByEmpId(Long empId);
}