package com.lightereb.hrms.service.attendance.impl;

import com.lightereb.hrms.common.exception.BusinessException;
import com.lightereb.hrms.mapper.attendance.AttendanceMapper;
import com.lightereb.hrms.model.entity.attendance.Attendance;
import com.lightereb.hrms.model.vo.attendance.AttendanceStatistics;
import com.lightereb.hrms.service.impl.BaseServiceImpl;
import com.lightereb.hrms.service.attendance.AttendanceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

/**
 * 考勤Service实现类
 */
@Slf4j
@Service
public class AttendanceServiceImpl extends BaseServiceImpl<AttendanceMapper, Attendance> implements AttendanceService {

	// 配置工作时间
	private static final LocalTime WORK_START_TIME = LocalTime.of(9, 0);
	private static final LocalTime WORK_END_TIME = LocalTime.of(18, 0);

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void checkIn(Long empId) {
		LocalDateTime now = LocalDateTime.now();
		Attendance attendance = getByEmpIdAndDate(empId, now.toLocalDate());

		if (attendance == null) {
			attendance = new Attendance();
			attendance.setEmpId(empId);
			attendance.setAttendanceDate(now.toLocalDate());
			attendance.setCheckInTime(now);

			// 判断是否迟到
			if (now.toLocalTime().isAfter(WORK_START_TIME)) {
				attendance.setAttendanceStatus(2); // 迟到
				attendance.setLateMinutes(calculateMinutes(WORK_START_TIME, now.toLocalTime()));
			} else {
				attendance.setAttendanceStatus(1); // 正常
			}

			save(attendance);
		} else {
			throw new BusinessException("今日已签到");
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void checkOut(Long empId) {
		LocalDateTime now = LocalDateTime.now();
		Attendance attendance = getByEmpIdAndDate(empId, now.toLocalDate());

		if (attendance == null) {
			throw new BusinessException("未找到签到记录");
		}

		if (attendance.getCheckOutTime() != null) {
			throw new BusinessException("今日已签退");
		}

		attendance.setCheckOutTime(now);

		// 判断是否早退
		if (now.toLocalTime().isBefore(WORK_END_TIME)) {
			attendance.setAttendanceStatus(3); // 早退
			attendance.setEarlyLeaveMinutes(calculateMinutes(now.toLocalTime(), WORK_END_TIME));
		}

		// 计算工作时长
		attendance.setWorkHours(calculateWorkHours(attendance.getCheckInTime(), now));

		updateById(attendance);
	}

	@Override
	public Attendance getByEmpIdAndDate(Long empId, LocalDate date) {
		return baseMapper.selectByEmpIdAndDate(empId, date);
	}

	@Override
	public List<Attendance> getMonthlyAttendance(Long empId, LocalDate startDate, LocalDate endDate) {
		return baseMapper.selectMonthlyAttendance(empId, startDate, endDate);
	}

	@Override
	public AttendanceStatistics getMonthlyStatistics(Long empId, Integer year, Integer month) {
		// 获取月初和月末日期
		LocalDate startDate = LocalDate.of(year, month, 1);
		LocalDate endDate = startDate.plusMonths(1).minusDays(1);

		// 获取月度考勤记录
		List<Attendance> monthlyAttendance = getMonthlyAttendance(empId, startDate, endDate);

		// 创建统计对象
		AttendanceStatistics statistics = new AttendanceStatistics()
				.setEmpId(empId)
				.setYear(year)
				.setMonth(month);

		// 计算工作日数（这里简单处理，实际应该排除节假日）
		statistics.setTotalDays((int) startDate.until(endDate.plusDays(1), ChronoUnit.DAYS));

		// 统计各项数据
		statistics.setActualDays(monthlyAttendance.size());

		// 统计正常出勤、迟到、早退等
		int normalDays = 0;
		int lateTimes = 0;
		int earlyLeaveTimes = 0;
		int absentDays = 0;
		int outDays = 0;
		BigDecimal totalWorkHours = BigDecimal.ZERO;
		BigDecimal overtimeHours = BigDecimal.ZERO;

		for (Attendance attendance : monthlyAttendance) {
			switch (attendance.getAttendanceStatus()) {
				case 1: // 正常
					normalDays++;
					break;
				case 2: // 迟到
					lateTimes++;
					break;
				case 3: // 早退
					earlyLeaveTimes++;
					break;
				case 0: // 缺勤
					absentDays++;
					break;
				case 5: // 外勤
					outDays++;
					break;
			}

			// 累计工作时长
			if (attendance.getWorkHours() != null) {
				totalWorkHours = totalWorkHours.add(attendance.getWorkHours());
			}
			// 累计加班时长
			if (attendance.getOvertimeHours() != null) {
				overtimeHours = overtimeHours.add(attendance.getOvertimeHours());
			}
		}

		statistics.setNormalDays(normalDays)
				.setLateTimes(lateTimes)
				.setEarlyLeaveTimes(earlyLeaveTimes)
				.setAbsentDays(absentDays)
				.setOutDays(outDays)
				.setTotalWorkHours(totalWorkHours)
				.setOvertimeHours(overtimeHours);

		// 计算出勤率
		if (statistics.getTotalDays() > 0) {
			BigDecimal attendanceRate = BigDecimal.valueOf(statistics.getActualDays())
					.divide(BigDecimal.valueOf(statistics.getTotalDays()), 4, RoundingMode.HALF_UP)
					.multiply(BigDecimal.valueOf(100));
			statistics.setAttendanceRate(attendanceRate);
		}

		return statistics;
	}

	/**
	 * 计算时间差（分钟）
	 */
	private int calculateMinutes(LocalTime start, LocalTime end) {
		return (int) java.time.Duration.between(start, end).toMinutes();
	}

	/**
	 * 计算工作时长（小时）
	 */
	private BigDecimal calculateWorkHours(LocalDateTime start, LocalDateTime end) {
		long minutes = java.time.Duration.between(start, end).toMinutes();
		return BigDecimal.valueOf(minutes / 60.0);
	}
}