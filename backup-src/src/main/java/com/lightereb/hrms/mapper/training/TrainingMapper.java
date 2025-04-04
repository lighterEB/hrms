package com.lightereb.hrms.mapper.training;

import com.lightereb.hrms.mapper.BaseMapper;
import com.lightereb.hrms.model.entity.training.Training;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 培训Mapper接口
 */
@Mapper
public interface TrainingMapper extends BaseMapper<Training> {

	/**
	 * 获取进行中的培训列表
	 */
	@Select("SELECT * FROM hr_training WHERE status = 1 AND is_deleted = 0")
	List<Training> selectOngoingTrainings();

	/**
	 * 获取员工参与的培训列表
	 */
	@Select("SELECT t.* FROM hr_training t " +
			"LEFT JOIN hr_training_participant tp ON t.id = tp.training_id " +
			"WHERE tp.emp_id = #{empId} AND t.is_deleted = 0")
	List<Training> selectTrainingsByEmpId(Long empId);
}