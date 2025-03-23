package com.lightereb.hrms.service.system.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lightereb.hrms.mapper.system.SysPermissionMapper;
import com.lightereb.hrms.mapper.system.SysRolePermissionMapper;
import com.lightereb.hrms.mapper.system.SysUserRoleMapper;
import com.lightereb.hrms.model.entity.system.SysPermission;
import com.lightereb.hrms.model.vo.system.MenuVO;
import com.lightereb.hrms.service.system.SysPermissionService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SysPermissionServiceImpl extends ServiceImpl<SysPermissionMapper, SysPermission> implements SysPermissionService
{

	private final SysUserRoleMapper userRoleMapper;
	private final SysRolePermissionMapper rolePermissionMapper;

	@Override
	@Cacheable(value = "menu", key = "'user:' + #userId")
	public List<MenuVO> getUserMenuTree(Long userId) {
		// 1. 获取用户的角色ID列表
		List<Long> roleIds = userRoleMapper.selectRoleIdsByUserId(userId);

		// 2. 获取角色对应的权限ID列表
		List<Long> permissionIds = rolePermissionMapper.selectPermissionIdsByRoleIds(roleIds);

		// 3. 查询权限菜单信息
		List<SysPermission> permissions = baseMapper.selectBatchIds(permissionIds);

		// 4. 转换为树形结构
		return buildMenuTree(permissions);
	}

	@Override
	@Cacheable(value = "permission", key = "'user:' + #userId")
	public Set<String> getUserPermissions(Long userId) {
		List<Long> roleIds = userRoleMapper.selectRoleIdsByUserId(userId);
		List<Long> permissionIds = rolePermissionMapper.selectPermissionIdsByRoleIds(roleIds);
		List<SysPermission> permissions = baseMapper.selectBatchIds(permissionIds);

		return permissions.stream()
				.map(SysPermission::getPerms)
				.filter(StringUtils::isNotBlank)
				.collect(Collectors.toSet());
	}

	private List<MenuVO> buildMenuTree(List<SysPermission> permissions) {
		List<MenuVO> menuTree = new ArrayList<>();
		Map<Long, MenuVO> menuMap = new HashMap<>();

		// 1. 转换为MenuVO对象
		for (SysPermission permission : permissions) {
			MenuVO menu = new MenuVO();
			BeanUtils.copyProperties(permission, menu);
			menuMap.put(menu.getId(), menu);
		}

		// 2. 构建树形结构
		for (MenuVO menu : menuMap.values()) {
			if (menu.getParentId() == 0) {
				menuTree.add(menu);
			} else {
				MenuVO parentMenu = menuMap.get(menu.getParentId());
				if (parentMenu != null) {
					if (parentMenu.getChildren() == null) {
						parentMenu.setChildren(new ArrayList<>());
					}
					parentMenu.getChildren().add(menu);
				}
			}
		}

		// 3. 排序
		menuTree.sort(Comparator.comparing(MenuVO::getSort));
		return menuTree;
	}
}
