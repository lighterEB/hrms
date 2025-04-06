package com.lightereb.hrms.mapper.performance;

import com.lightereb.hrms.mapper.BaseMapper;
import com.lightereb.hrms.model.entity.performance.Performance;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 绩效Mapper接口
 */
@Mapper
public interface PerformanceMapper extends BaseMapper<Performance> {

	/**
	 * 获取员工某年某月的绩效记录
	 */
	@Select("SELECT * FROM hr_performance WHERE emp_id = #{empId} " +
			"AND assessment_year = #{year} AND assessment_month = #{month} " +
			"AND is_deleted = 0")
	Performance selectMonthPerformance(@Param("empId") Long empId,
	                                   @Param("year") Integer year,
	                                   @Param("month") Integer month);

	/**
	 * 获取部门某年某月的绩效记录
	 */
	@Select("SELECT p.* FROM hr_performance p " +
			"LEFT JOIN hr_employee e ON p.emp_id = e.id " +
			"WHERE e.dept_id = #{deptId} " +
			"AND p.assessment_year = #{year} " +
			"AND p.assessment_month = #{month} " +
			"AND p.is_deleted = 0")
	List<Performance> selectDeptMonthPerformance(@Param("deptId") Long deptId,
	                                             @Param("year") Integer year,
	                                             @Param("month") Integer month);
}