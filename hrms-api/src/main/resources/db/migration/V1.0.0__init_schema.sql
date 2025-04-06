-- 创建系统用户表
CREATE TABLE
IF NOT EXISTS sys_user (
	id BIGINT NOT NULL PRIMARY KEY COMMENT '主键ID',
	username VARCHAR (50) NOT NULL COMMENT '用户名',
	PASSWORD VARCHAR (100) NOT NULL COMMENT '密码',
	real_name VARCHAR (50) COMMENT '真实姓名',
	phone VARCHAR (20) COMMENT '手机号码',
	email VARCHAR (100) COMMENT '邮箱',
	avatar VARCHAR (255) COMMENT '头像地址',
	gender TINYINT DEFAULT 2 COMMENT '性别 0-女 1-男 2-未知',
	STATUS TINYINT DEFAULT 1 COMMENT '状态 0-禁用 1-正常',
	last_login_time TIMESTAMP COMMENT '最后登录时间',
	create_user VARCHAR (50) COMMENT '创建人',
	create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
	update_user VARCHAR (50) COMMENT '更新人',
	update_time TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
	is_deleted TINYINT DEFAULT 0 COMMENT '删除标志 0-未删除 1-已删除',
	UNIQUE KEY uk_username (username)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COMMENT '系统用户表';

-- 创建角色表
CREATE TABLE
IF NOT EXISTS sys_role (
	id BIGINT NOT NULL PRIMARY KEY COMMENT '主键ID',
	role_name VARCHAR (50) NOT NULL COMMENT '角色名称',
	role_code VARCHAR (50) NOT NULL COMMENT '角色编码',
	description VARCHAR (200) COMMENT '角色描述',
	STATUS TINYINT DEFAULT 1 COMMENT '状态 0-禁用 1-正常',
	create_user VARCHAR (50) COMMENT '创建人',
	create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
	update_user VARCHAR (50) COMMENT '更新人',
	update_time TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
	is_deleted TINYINT DEFAULT 0 COMMENT '删除标志 0-未删除 1-已删除',
	UNIQUE KEY uk_role_code (role_code)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COMMENT '角色表';

-- 创建权限表
CREATE TABLE
IF NOT EXISTS sys_permission (
	id BIGINT NOT NULL PRIMARY KEY COMMENT '主键ID',
	parent_id BIGINT DEFAULT 0 COMMENT '父级权限ID',
	NAME VARCHAR (50) NOT NULL COMMENT '权限名称',
	path VARCHAR (100) COMMENT '前端路由路径',
	component VARCHAR (100) COMMENT '前端组件路径',
	perms VARCHAR (100) COMMENT '权限标识',
	icon VARCHAR (100) COMMENT '图标',
	type TINYINT NOT NULL COMMENT '类型 0-目录 1-菜单 2-按钮',
	sort INT DEFAULT 0 COMMENT '排序',
	STATUS TINYINT DEFAULT 1 COMMENT '状态 0-禁用 1-正常',
	create_user VARCHAR (50) COMMENT '创建人',
	create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
	update_user VARCHAR (50) COMMENT '更新人',
	update_time TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
	is_deleted TINYINT DEFAULT 0 COMMENT '删除标志 0-未删除 1-已删除'
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COMMENT '权限表';

-- 创建用户角色关联表
CREATE TABLE
IF NOT EXISTS sys_user_role (
	id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT '主键ID',
	user_id BIGINT NOT NULL COMMENT '用户ID',
	role_id BIGINT NOT NULL COMMENT '角色ID',
	create_user VARCHAR (50) COMMENT '创建人',
	create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
	update_user VARCHAR (50) COMMENT '更新人',
	update_time TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    is_deleted TINYINT DEFAULT 0 COMMENT '删除标志 0-未删除 1-已删除',
	UNIQUE KEY uk_user_role (user_id, role_id)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COMMENT '用户角色关联表';

-- 创建角色权限关联表
CREATE TABLE
IF NOT EXISTS sys_role_permission (
	id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT '主键ID',
	role_id BIGINT NOT NULL COMMENT '角色ID',
	permission_id BIGINT NOT NULL COMMENT '权限ID',
	create_user VARCHAR (50) COMMENT '创建人',
	create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
	update_user VARCHAR (50) COMMENT '更新人',
	update_time TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    is_deleted TINYINT DEFAULT 0 COMMENT '删除标志 0-未删除 1-已删除',
	UNIQUE KEY uk_role_perm (role_id, permission_id)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COMMENT '角色权限关联表';

-- 创建部门表
CREATE TABLE hr_department (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '部门ID',
    parent_id BIGINT DEFAULT 0 COMMENT '父部门ID',
    dept_name VARCHAR(50) NOT NULL COMMENT '部门名称',
    dept_code VARCHAR(50) UNIQUE COMMENT '部门编码',
    leader VARCHAR(50) COMMENT '负责人',
    phone VARCHAR(20) COMMENT '联系电话',
    status TINYINT DEFAULT 1 COMMENT '状态 0-禁用 1-正常',
    sort INT DEFAULT 0 COMMENT '排序',
	create_user VARCHAR (50) COMMENT '创建人',
	create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
	update_user VARCHAR (50) COMMENT '更新人',
	update_time TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    is_deleted TINYINT DEFAULT 0 COMMENT '删除标志 0-未删除 1-已删除'
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COMMENT '部门表';

-- 创建职位表
CREATE TABLE hr_position (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '职位ID',
    position_name VARCHAR(50) NOT NULL COMMENT '职位名称',
    position_code VARCHAR(50) UNIQUE COMMENT '职位编码',
    dept_id BIGINT COMMENT '所属部门ID',
    status TINYINT DEFAULT 1 COMMENT '状态 0-禁用 1-正常',
    sort INT DEFAULT 0 COMMENT '排序',
	create_user VARCHAR (50) COMMENT '创建人',
	create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
	update_user VARCHAR (50) COMMENT '更新人',
	update_time TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    is_deleted TINYINT DEFAULT 0 COMMENT '删除标志 0-未删除 1-已删除',
    INDEX idx_dept_id (dept_id)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COMMENT '职位表';

-- 创建员工表
CREATE TABLE hr_employee (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '员工ID',
    emp_no VARCHAR(50) NOT NULL UNIQUE COMMENT '员工编号',
    emp_name VARCHAR(50) NOT NULL COMMENT '员工姓名',
    gender TINYINT COMMENT '性别 0-女 1-男',
    birth_date DATE COMMENT '出生日期',
    id_card VARCHAR(20) COMMENT '身份证号',
    phone VARCHAR(20) COMMENT '手机号',
    email VARCHAR(100) COMMENT '邮箱',
    address VARCHAR(255) COMMENT '住址',
    education VARCHAR(50) COMMENT '学历',
    dept_id BIGINT COMMENT '部门ID',
    position_id BIGINT COMMENT '职位ID',
    entry_date DATE COMMENT '入职日期',
    leave_date DATE COMMENT '离职日期',
    employment_status TINYINT DEFAULT 1 COMMENT '在职状态 0-离职 1-在职 2-试用期',
    avatar VARCHAR(255) COMMENT '头像',
    emergency_contact VARCHAR(50) COMMENT '紧急联系人',
    emergency_phone VARCHAR(20) COMMENT '紧急联系人电话',
    remark VARCHAR(255) COMMENT '备注',
    user_id BIGINT COMMENT '关联系统用户ID',
	create_user VARCHAR (50) COMMENT '创建人',
	create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
	update_user VARCHAR (50) COMMENT '更新人',
	update_time TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    is_deleted TINYINT DEFAULT 0 COMMENT '删除标志 0-未删除 1-已删除',
    INDEX idx_dept_id (dept_id),
    INDEX idx_position_id (position_id),
    INDEX idx_user_id (user_id)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COMMENT '员工表';

-- 创建薪资表
CREATE TABLE hr_salary (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '薪资ID',
    emp_id BIGINT NOT NULL COMMENT '员工ID',
    year INT NOT NULL COMMENT '年份',
    month INT NOT NULL COMMENT '月份',
    basic_salary DECIMAL(12,2) NOT NULL COMMENT '基本工资',
    bonus DECIMAL(12,2) DEFAULT 0 COMMENT '奖金',
    allowance DECIMAL(12,2) DEFAULT 0 COMMENT '津贴',
    overtime_pay DECIMAL(12,2) DEFAULT 0 COMMENT '加班费',
    social_security DECIMAL(12,2) DEFAULT 0 COMMENT '社保扣款',
    housing_fund DECIMAL(12,2) DEFAULT 0 COMMENT '公积金扣款',
    tax DECIMAL(12,2) DEFAULT 0 COMMENT '个税扣款',
    other_deduction DECIMAL(12,2) DEFAULT 0 COMMENT '其他扣款',
    actual_salary DECIMAL(12,2) NOT NULL COMMENT '实发工资',
    payment_status TINYINT DEFAULT 0 COMMENT '发放状态 0-未发放 1-已发放',
    payment_time DATETIME COMMENT '发放时间',
    remark VARCHAR(255) COMMENT '备注',
	create_user VARCHAR (50) COMMENT '创建人',
	create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
	update_user VARCHAR (50) COMMENT '更新人',
	update_time TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    is_deleted TINYINT DEFAULT 0 COMMENT '删除标志 0-未删除 1-已删除',
    INDEX idx_emp_id (emp_id),
    UNIQUE INDEX idx_emp_year_month (emp_id, year, month)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COMMENT '薪资表';

-- 创建考勤表
CREATE TABLE hr_attendance (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '考勤ID',
    emp_id BIGINT NOT NULL COMMENT '员工ID',
    attendance_date DATE NOT NULL COMMENT '考勤日期',
    check_in_time DATETIME COMMENT '签到时间',
    check_out_time DATETIME COMMENT '签退时间',
    attendance_status TINYINT COMMENT '考勤状态 0-缺勤 1-正常 2-迟到 3-早退 4-请假 5-外勤',
    work_hours DECIMAL(5,2) COMMENT '工作时长(小时)',
    overtime_hours DECIMAL(5,2) DEFAULT 0 COMMENT '加班时长(小时)',
    late_minutes INT DEFAULT 0 COMMENT '迟到时长(分钟)',
    early_leave_minutes INT DEFAULT 0 COMMENT '早退时长(分钟)',
    leave_type VARCHAR(20) COMMENT '请假类型',
    remark VARCHAR(255) COMMENT '备注',
	create_user VARCHAR (50) COMMENT '创建人',
	create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
	update_user VARCHAR (50) COMMENT '更新人',
	update_time TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    is_deleted TINYINT DEFAULT 0 COMMENT '删除标志 0-未删除 1-已删除',
    INDEX idx_emp_id (emp_id),
    INDEX idx_attendance_date (attendance_date)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COMMENT '考勤表';

-- 创建请假记录表
CREATE TABLE hr_leave (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '请假ID',
    emp_id BIGINT NOT NULL COMMENT '员工ID',
    leave_type VARCHAR(20) NOT NULL COMMENT '请假类型(年假、病假、事假等)',
    start_time DATETIME NOT NULL COMMENT '开始时间',
    end_time DATETIME NOT NULL COMMENT '结束时间',
    duration DECIMAL(5,2) NOT NULL COMMENT '请假时长(天)',
    reason VARCHAR(255) NOT NULL COMMENT '请假原因',
    attachment_url VARCHAR(255) COMMENT '附件URL',
    approval_status TINYINT DEFAULT 0 COMMENT '审批状态 0-待审批 1-已批准 2-已拒绝',
    approver_id BIGINT COMMENT '审批人ID',
    approval_time DATETIME COMMENT '审批时间',
    approval_remark VARCHAR(255) COMMENT '审批备注',
	create_user VARCHAR (50) COMMENT '创建人',
	create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
	update_user VARCHAR (50) COMMENT '更新人',
	update_time TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    is_deleted TINYINT DEFAULT 0 COMMENT '删除标志 0-未删除 1-已删除',
    INDEX idx_emp_id (emp_id),
    INDEX idx_approver_id (approver_id)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COMMENT '请假记录表';

-- 创建绩效评估表
CREATE TABLE hr_performance (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '绩效ID',
    emp_id BIGINT NOT NULL COMMENT '员工ID',
    assessment_year INT NOT NULL COMMENT '考核年份',
    assessment_month INT NOT NULL COMMENT '考核月份',
    work_performance DECIMAL(3,1) COMMENT '工作业绩(0-10)',
    ability_score DECIMAL(3,1) COMMENT '能力评分(0-10)',
    attitude_score DECIMAL(3,1) COMMENT '态度评分(0-10)',
    total_score DECIMAL(3,1) COMMENT '总评分(0-10)',
    assessment_level VARCHAR(10) COMMENT '考核等级(A、B、C、D)',
    evaluator_id BIGINT COMMENT '评估人ID',
    self_evaluation TEXT COMMENT '自我评价',
    evaluation_content TEXT COMMENT '评估内容',
    improvement_plan TEXT COMMENT '改进计划',
    assessment_time DATE COMMENT '考核时间',
    remark VARCHAR(255) COMMENT '备注',
	create_user VARCHAR (50) COMMENT '创建人',
	create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
	update_user VARCHAR (50) COMMENT '更新人',
	update_time TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    is_deleted TINYINT DEFAULT 0 COMMENT '删除标志 0-未删除 1-已删除',
    INDEX idx_emp_id (emp_id),
    INDEX idx_evaluator_id (evaluator_id),
    INDEX idx_assessment_year_month (assessment_year, assessment_month)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COMMENT '绩效评估表';

-- 创建培训记录表
CREATE TABLE hr_training (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '培训ID',
    training_name VARCHAR(100) NOT NULL COMMENT '培训名称',
    training_type VARCHAR(50) COMMENT '培训类型',
    content TEXT COMMENT '培训内容',
    start_time DATETIME NOT NULL COMMENT '开始时间',
    end_time DATETIME NOT NULL COMMENT '结束时间',
    duration INT COMMENT '培训时长(小时)',
    trainer VARCHAR(50) COMMENT '培训讲师',
    location VARCHAR(100) COMMENT '培训地点',
    budget DECIMAL(12,2) COMMENT '培训预算',
    actual_cost DECIMAL(12,2) COMMENT '实际费用',
    status TINYINT DEFAULT 0 COMMENT '状态 0-计划中 1-进行中 2-已完成 3-已取消',
    remark VARCHAR(255) COMMENT '备注',
	create_user VARCHAR (50) COMMENT '创建人',
	create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
	update_user VARCHAR (50) COMMENT '更新人',
	update_time TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    is_deleted TINYINT DEFAULT 0 COMMENT '删除标志 0-未删除 1-已删除'
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COMMENT '培训记录表';

-- 创建培训参与表
CREATE TABLE hr_training_participant (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT 'ID',
    training_id BIGINT NOT NULL COMMENT '培训ID',
    emp_id BIGINT NOT NULL COMMENT '员工ID',
    attendance_status TINYINT DEFAULT 1 COMMENT '出勤状态 0-缺席 1-出席',
    score DECIMAL(5,2) COMMENT '考核成绩',
    certificate VARCHAR(100) COMMENT '获得证书',
    feedback TEXT COMMENT '培训反馈',
	create_user VARCHAR (50) COMMENT '创建人',
	create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
	update_user VARCHAR (50) COMMENT '更新人',
	update_time TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    is_deleted TINYINT DEFAULT 0 COMMENT '删除标志 0-未删除 1-已删除',
    INDEX idx_training_id (training_id),
    INDEX idx_emp_id (emp_id),
    UNIQUE INDEX idx_training_emp (training_id, emp_id)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COMMENT '培训参与表';

-- 创建合同管理表
CREATE TABLE hr_contract (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '合同ID',
    emp_id BIGINT NOT NULL COMMENT '员工ID',
    contract_no VARCHAR(50) UNIQUE COMMENT '合同编号',
    contract_type VARCHAR(50) COMMENT '合同类型',
    start_date DATE NOT NULL COMMENT '开始日期',
    end_date DATE COMMENT '结束日期',
    probation_months INT DEFAULT 0 COMMENT '试用期(月)',
    job_description TEXT COMMENT '工作描述',
    salary_amount DECIMAL(12,2) COMMENT '薪资金额',
    contract_status TINYINT DEFAULT 1 COMMENT '合同状态 0-终止 1-生效 2-到期',
    sign_date DATE COMMENT '签订日期',
    termination_date DATE COMMENT '终止日期',
    termination_reason VARCHAR(255) COMMENT '终止原因',
    attachment_url VARCHAR(255) COMMENT '附件URL',
    remark VARCHAR(255) COMMENT '备注',
	create_user VARCHAR (50) COMMENT '创建人',
	create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
	update_user VARCHAR (50) COMMENT '更新人',
	update_time TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    is_deleted TINYINT DEFAULT 0 COMMENT '删除标志 0-未删除 1-已删除',
    INDEX idx_emp_id (emp_id)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COMMENT '合同管理表';