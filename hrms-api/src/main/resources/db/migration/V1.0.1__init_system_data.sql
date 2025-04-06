-- 初始化角色
INSERT INTO sys_role (id, role_name, role_code, description) VALUES
    (1, '超级管理员', 'ROLE_ADMIN', '系统超级管理员'),
    (2, '人事经理', 'ROLE_HR_MANAGER', '负责人事管理相关操作'),
    (3, '部门经理', 'ROLE_DEPT_MANAGER', '负责部门管理相关操作'),
    (4, '考勤管理员', 'ROLE_ATTENDANCE_ADMIN', '负责考勤管理相关操作'),
    (5, '薪资管理员', 'ROLE_SALARY_ADMIN', '负责薪资管理相关操作'),
    (6, '培训管理员', 'ROLE_TRAINING_ADMIN', '负责培训管理相关操作'),
    (7, '绩效管理员', 'ROLE_PERFORMANCE_ADMIN', '负责绩效管理相关操作'),
    (8, '普通员工', 'ROLE_EMPLOYEE', '普通员工角色');

-- 初始化系统管理员用户
INSERT INTO sys_user (id, username, password, real_name, status) VALUES
    (1, 'admin', '$2a$10$VQBKVbhwuL6tGYoGkYT5P.8KIbFb5ZpQhHDqhzQXj5e8qxZkZXE2e', '系统管理员', 1);

-- 初始化用户角色关联
INSERT INTO sys_user_role (user_id, role_id) VALUES (1, 1);

-- 初始化权限菜单数据
INSERT INTO sys_permission (id, parent_id, name, path, component, perms, icon, type, sort, status) VALUES
-- 系统管理
(1, 0, '系统管理', '/system', null, null, 'setting', 0, 1, 1),
-- 用户管理
(2, 1, '用户管理', '/system/user', '/system/user/index', 'system:user:list', 'user', 1, 1, 1),
(3, 2, '用户新增', null, null, 'system:user:add', null, 2, 1, 1),
(4, 2, '用户编辑', null, null, 'system:user:edit', null, 2, 2, 1),
(5, 2, '用户删除', null, null, 'system:user:delete', null, 2, 3, 1),
-- 角色管理
(6, 1, '角色管理', '/system/role', '/system/role/index', 'system:role:list', 'team', 1, 2, 1),
(7, 6, '角色新增', null, null, 'system:role:add', null, 2, 1, 1),
(8, 6, '角色编辑', null, null, 'system:role:edit', null, 2, 2, 1),
(9, 6, '角色删除', null, null, 'system:role:delete', null, 2, 3, 1),
-- 菜单管理
(10, 1, '菜单管理', '/system/menu', '/system/menu/index', 'system:menu:list', 'menu', 1, 3, 1),
(11, 10, '菜单新增', null, null, 'system:menu:add', null, 2, 1, 1),
(12, 10, '菜单编辑', null, null, 'system:menu:edit', null, 2, 2, 1),
(13, 10, '菜单删除', null, null, 'system:menu:delete', null, 2, 3, 1),
-- 系统监控
(14, 1, '系统监控', '/system/monitor', '/system/monitor/index', 'system:monitor:view', 'monitor', 1, 4, 1),
-- 人事管理
(15, 0, '人事管理', '/hr', null, null, 'team', 0, 2, 1),
-- 员工管理
(16, 15, '员工管理', '/hr/employee', '/hr/employee/index', 'hr:employee:list', 'user', 1, 1, 1),
(17, 16, '员工新增', null, null, 'hr:employee:add', null, 2, 1, 1),
(18, 16, '员工编辑', null, null, 'hr:employee:edit', null, 2, 2, 1),
(19, 16, '员工删除', null, null, 'hr:employee:delete', null, 2, 3, 1),
(20, 16, '员工查看', null, null, 'hr:employee:view', null, 2, 4, 1),
(21, 16, '员工导入', null, null, 'hr:employee:import', null, 2, 5, 1),
(22, 16, '员工导出', null, null, 'hr:employee:export', null, 2, 6, 1),
-- 部门管理
(23, 15, '部门管理', '/hr/department', '/hr/department/index', 'hr:department:list', 'apartment', 1, 2, 1),
(24, 23, '部门新增', null, null, 'hr:department:add', null, 2, 1, 1),
(25, 23, '部门编辑', null, null, 'hr:department:edit', null, 2, 2, 1),
(26, 23, '部门删除', null, null, 'hr:department:delete', null, 2, 3, 1),
(27, 23, '部门查看', null, null, 'hr:department:view', null, 2, 4, 1),
-- 职位管理
(28, 15, '职位管理', '/hr/position', '/hr/position/index', 'hr:position:list', 'solution', 1, 3, 1),
(29, 28, '职位新增', null, null, 'hr:position:add', null, 2, 1, 1),
(30, 28, '职位编辑', null, null, 'hr:position:edit', null, 2, 2, 1),
(31, 28, '职位删除', null, null, 'hr:position:delete', null, 2, 3, 1),
(32, 28, '职位查看', null, null, 'hr:position:view', null, 2, 4, 1),
-- 考勤管理
(33, 0, '考勤管理', '/attendance', null, null, 'calendar', 0, 3, 1),
(34, 33, '考勤记录', '/attendance/record', '/attendance/record/index', 'attendance:record:list', 'calendar-check', 1, 1, 1),
(35, 34, '考勤记录新增', null, null, 'attendance:record:add', null, 2, 1, 1),
(36, 34, '考勤记录编辑', null, null, 'attendance:record:edit', null, 2, 2, 1),
(37, 34, '考勤记录删除', null, null, 'attendance:record:delete', null, 2, 3, 1),
(38, 34, '考勤记录查看', null, null, 'attendance:record:view', null, 2, 4, 1),
(39, 34, '考勤记录导出', null, null, 'attendance:record:export', null, 2, 5, 1),
(40, 33, '请假管理', '/attendance/leave', '/attendance/leave/index', 'attendance:leave:list', 'calendar-alt', 1, 2, 1),
(41, 40, '请假申请', null, null, 'attendance:leave:apply', null, 2, 1, 1),
(42, 40, '请假审批', null, null, 'attendance:leave:approve', null, 2, 2, 1),
(43, 40, '请假查看', null, null, 'attendance:leave:view', null, 2, 3, 1),
-- 薪资管理
(44, 0, '薪资管理', '/salary', null, null, 'money-bill', 0, 4, 1),
(45, 44, '薪资记录', '/salary/record', '/salary/record/index', 'salary:record:list', 'dollar-sign', 1, 1, 1),
(46, 45, '薪资记录新增', null, null, 'salary:record:add', null, 2, 1, 1),
(47, 45, '薪资记录编辑', null, null, 'salary:record:edit', null, 2, 2, 1),
(48, 45, '薪资记录删除', null, null, 'salary:record:delete', null, 2, 3, 1),
(49, 45, '薪资记录查看', null, null, 'salary:record:view', null, 2, 4, 1),
(50, 45, '薪资单生成', null, null, 'salary:record:generate', null, 2, 5, 1),
(51, 45, '薪资记录导出', null, null, 'salary:record:export', null, 2, 6, 1),
(52, 44, '薪资设置', '/salary/config', '/salary/config/index', 'salary:config:list', 'cog', 1, 2, 1),
(53, 52, '薪资设置编辑', null, null, 'salary:config:edit', null, 2, 1, 1),
(54, 52, '薪资设置查看', null, null, 'salary:config:view', null, 2, 2, 1),
-- 绩效管理
(55, 0, '绩效管理', '/performance', null, null, 'chart-line', 0, 5, 1),
(56, 55, '绩效考核', '/performance/assessment', '/performance/assessment/index', 'performance:assessment:list', 'tasks', 1, 1, 1),
(57, 56, '绩效考核新增', null, null, 'performance:assessment:add', null, 2, 1, 1),
(58, 56, '绩效考核编辑', null, null, 'performance:assessment:edit', null, 2, 2, 1),
(59, 56, '绩效考核删除', null, null, 'performance:assessment:delete', null, 2, 3, 1),
(60, 56, '绩效考核查看', null, null, 'performance:assessment:view', null, 2, 4, 1),
(61, 56, '绩效考核审批', null, null, 'performance:assessment:approve', null, 2, 5, 1),
(62, 55, '绩效设置', '/performance/config', '/performance/config/index', 'performance:config:list', 'cog', 1, 2, 1),
(63, 62, '绩效设置编辑', null, null, 'performance:config:edit', null, 2, 1, 1),
(64, 62, '绩效设置查看', null, null, 'performance:config:view', null, 2, 2, 1),
-- 培训管理
(65, 0, '培训管理', '/training', null, null, 'graduation-cap', 0, 6, 1),
(66, 65, '培训计划', '/training/plan', '/training/plan/index', 'training:plan:list', 'book', 1, 1, 1),
(67, 66, '培训计划新增', null, null, 'training:plan:add', null, 2, 1, 1),
(68, 66, '培训计划编辑', null, null, 'training:plan:edit', null, 2, 2, 1),
(69, 66, '培训计划删除', null, null, 'training:plan:delete', null, 2, 3, 1),
(70, 66, '培训计划查看', null, null, 'training:plan:view', null, 2, 4, 1),
(71, 65, '培训记录', '/training/record', '/training/record/index', 'training:record:list', 'clipboard-list', 1, 2, 1),
(72, 71, '培训记录新增', null, null, 'training:record:add', null, 2, 1, 1),
(73, 71, '培训记录编辑', null, null, 'training:record:edit', null, 2, 2, 1),
(74, 71, '培训记录删除', null, null, 'training:record:delete', null, 2, 3, 1),
(75, 71, '培训记录查看', null, null, 'training:record:view', null, 2, 4, 1),
-- 组织管理
(76, 0, '组织管理', '/organization', null, null, 'sitemap', 0, 7, 1),
(77, 76, '组织架构', '/organization/structure', '/organization/structure/index', 'organization:structure:view', 'sitemap', 1, 1, 1),
(78, 76, '公司信息', '/organization/company', '/organization/company/index', 'organization:company:view', 'building', 1, 2, 1),
(79, 78, '公司信息编辑', null, null, 'organization:company:edit', null, 2, 1, 1);

-- 初始化角色权限关联（超级管理员拥有所有权限）
INSERT INTO sys_role_permission (role_id, permission_id)
SELECT 1, id FROM sys_permission;

-- 初始化人事经理权限
INSERT INTO sys_role_permission (role_id, permission_id)
SELECT 2, id FROM sys_permission WHERE id IN (15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 76, 77, 78, 79);

-- 初始化部门经理权限
INSERT INTO sys_role_permission (role_id, permission_id)
SELECT 3, id FROM sys_permission WHERE id IN (15, 16, 20, 23, 27, 28, 32, 76, 77, 78);

-- 初始化考勤管理员权限
INSERT INTO sys_role_permission (role_id, permission_id)
SELECT 4, id FROM sys_permission WHERE id IN (33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43);

-- 初始化薪资管理员权限
INSERT INTO sys_role_permission (role_id, permission_id)
SELECT 5, id FROM sys_permission WHERE id IN (44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54);

-- 初始化培训管理员权限
INSERT INTO sys_role_permission (role_id, permission_id)
SELECT 6, id FROM sys_permission WHERE id IN (65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75);

-- 初始化绩效管理员权限
INSERT INTO sys_role_permission (role_id, permission_id)
SELECT 7, id FROM sys_permission WHERE id IN (55, 56, 57, 58, 59, 60, 61, 62, 63, 64);

-- 初始化普通员工权限
INSERT INTO sys_role_permission (role_id, permission_id)
SELECT 8, id FROM sys_permission WHERE id IN (
    16, 20, -- 查看员工信息
    23, 27, -- 查看部门信息
    28, 32, -- 查看职位信息
    34, 38, -- 查看考勤记录
    40, 41, 43, -- 请假申请和查看
    45, 49, -- 查看薪资记录
    56, 60, -- 查看绩效考核
    66, 70, -- 查看培训计划
    71, 75, -- 查看培训记录
    77, 78 -- 查看组织架构和公司信息
);