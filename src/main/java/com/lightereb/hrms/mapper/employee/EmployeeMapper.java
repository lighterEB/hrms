package com.lightereb.hrms.mapper.employee;

import com.lightereb.hrms.mapper.BaseMapper;
import com.lightereb.hrms.model.entity.employee.Employee;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * 员工Mapper接口
 */
@Mapper
public interface EmployeeMapper extends BaseMapper<Employee> {

	/**
	 * 根据员工编号查询员工
	 */
	@Select("SELECT * FROM hr_employee WHERE emp_no = #{empNo} AND is_deleted = 0")
	Employee selectByEmpNo(String empNo);

	/**
	 * 根据用户ID查询员工
	 */
	@Select("SELECT * FROM hr_employee WHERE user_id = #{userId} AND is_deleted = 0")
	Employee selectByUserId(Long userId);
}