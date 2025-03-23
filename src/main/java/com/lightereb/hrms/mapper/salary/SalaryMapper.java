package com.lightereb.hrms.mapper.salary;

import com.lightereb.hrms.mapper.BaseMapper;
import com.lightereb.hrms.model.entity.salary.Salary;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 薪资Mapper接口
 */
@Mapper
public interface SalaryMapper extends BaseMapper<Salary> {

	/**
	 * 获取员工某年某月的薪资记录
	 */
	@Select("SELECT * FROM hr_salary WHERE emp_id = #{empId} " +
			"AND year = #{year} AND month = #{month} AND is_deleted = 0")
	Salary selectMonthSalary(@Param("empId") Long empId,
	                         @Param("year") Integer year,
	                         @Param("month") Integer month);

	/**
	 * 获取某月未发放的薪资记录
	 */
	@Select("SELECT * FROM hr_salary WHERE year = #{year} " +
			"AND month = #{month} AND payment_status = 0 AND is_deleted = 0")
	List<Salary> selectUnpaidSalaries(@Param("year") Integer year,
	                                  @Param("month") Integer month);
}