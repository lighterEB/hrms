-- 初始化超级管理员角色
INSERT INTO sys_role (id, role_name, role_code, description) VALUES
    (1, '超级管理员', 'ROLE_ADMIN', '系统超级管理员');

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
-- 人事管理
(14, 0, '人事管理', '/hr', null, null, 'team', 0, 2, 1),
-- 员工管理
(15, 14, '员工管理', '/hr/employee', '/hr/employee/index', 'hr:employee:list', 'user', 1, 1, 1),
(16, 15, '员工新增', null, null, 'hr:employee:add', null, 2, 1, 1),
(17, 15, '员工编辑', null, null, 'hr:employee:edit', null, 2, 2, 1),
(18, 15, '员工删除', null, null, 'hr:employee:delete', null, 2, 3, 1),
-- 部门管理
(19, 14, '部门管理', '/hr/department', '/hr/department/index', 'hr:department:list', 'apartment', 1, 2, 1),
(20, 19, '部门新增', null, null, 'hr:department:add', null, 2, 1, 1),
(21, 19, '部门编辑', null, null, 'hr:department:edit', null, 2, 2, 1),
(22, 19, '部门删除', null, null, 'hr:department:delete', null, 2, 3, 1),
-- 职位管理
(23, 14, '职位管理', '/hr/position', '/hr/position/index', 'hr:position:list', 'solution', 1, 3, 1),
(24, 23, '职位新增', null, null, 'hr:position:add', null, 2, 1, 1),
(25, 23, '职位编辑', null, null, 'hr:position:edit', null, 2, 2, 1),
(26, 23, '职位删除', null, null, 'hr:position:delete', null, 2, 3, 1);

-- 初始化角色权限关联（超级管理员拥有所有权限）
INSERT INTO sys_role_permission (role_id, permission_id)
SELECT 1, id FROM sys_permission;