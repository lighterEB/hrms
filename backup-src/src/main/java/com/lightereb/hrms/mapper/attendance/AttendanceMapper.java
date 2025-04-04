package com.lightereb.hrms.mapper.attendance;

import com.lightereb.hrms.mapper.BaseMapper;
import com.lightereb.hrms.model.entity.attendance.Attendance;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;
import java.util.List;

/**
 * 考勤Mapper接口
 */
@Mapper
public interface AttendanceMapper extends BaseMapper<Attendance> {

	/**
	 * 获取员工某天的考勤记录
	 */
	@Select("SELECT * FROM hr_attendance WHERE emp_id = #{empId} AND attendance_date = #{date} AND is_deleted = 0")
	Attendance selectByEmpIdAndDate(@Param("empId") Long empId, @Param("date") LocalDate date);

	/**
	 * 获取员工某月的考勤记录
	 */
	@Select("SELECT * FROM hr_attendance WHERE emp_id = #{empId} " +
			"AND attendance_date >= #{startDate} AND attendance_date <= #{endDate} " +
			"AND is_deleted = 0")
	List<Attendance> selectMonthlyAttendance(@Param("empId") Long empId,
	                                         @Param("startDate") LocalDate startDate,
	                                         @Param("endDate") LocalDate endDate);
}