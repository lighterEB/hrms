package com.lightereb.hrms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lightereb.hrms.mapper.system.SysPermissionMapper;
import com.lightereb.hrms.mapper.system.SysRolePermissionMapper;
import com.lightereb.hrms.model.entity.system.SysPermission;
import com.lightereb.hrms.model.entity.system.SysRolePermission;
import com.lightereb.hrms.model.vo.system.MenuVO;
import com.lightereb.hrms.service.SysPermissionService;
import com.lightereb.hrms.service.SysRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 权限Service实现类
 */
@Service
@RequiredArgsConstructor
public class SysPermissionServiceImpl extends ServiceImpl<SysPermissionMapper, SysPermission> implements SysPermissionService
{

	private final SysRoleService roleService;
	private final SysRolePermissionMapper rolePermissionMapper;

	/**
	 * 获取用户菜单树
	 * @param userId 用户ID
	 * @return 菜单树
	 */
	@Override
	public List<MenuVO> getUserMenuTree(Long userId) {
		// 获取用户权限列表（包括菜单和按钮）
		Set<SysPermission> permissionList = getUserPermissionList(userId);
		
		// 筛选出菜单类型的权限（类型0为目录、1为菜单）
		List<SysPermission> menuList = permissionList.stream()
				.filter(p -> p.getType() == 0 || p.getType() == 1)
				.sorted(Comparator.comparing(SysPermission::getSort))
				.collect(Collectors.toList());
		
		// 构建菜单树
		return buildMenuTree(menuList, 0L);
	}

	/**
	 * 获取用户权限集合
	 * @param userId 用户ID
	 * @return 权限标识集合
	 */
	@Override
	public Set<String> getUserPermissions(Long userId) {
		// 获取用户权限列表
		Set<SysPermission> permissionList = getUserPermissionList(userId);
		
		// 提取权限标识并过滤空值
		return permissionList.stream()
				.map(SysPermission::getPerms)
				.filter(perms -> perms != null && !perms.isEmpty())
				.collect(Collectors.toSet());
	}
	
	/**
	 * 获取用户的权限列表
	 * @param userId 用户ID
	 * @return 权限列表
	 */
	private Set<SysPermission> getUserPermissionList(Long userId) {
		// 获取用户所有角色ID
		List<Long> roleIds = roleService.getRolesByUserId(userId).stream()
				.map(role -> role.getId())
				.collect(Collectors.toList());
		
		// 如果用户没有角色，则返回空集合
		if (roleIds.isEmpty()) {
			return Collections.emptySet();
		}
		
		// 获取角色对应的权限ID
		List<SysRolePermission> rolePermissions = rolePermissionMapper.selectList(
				new LambdaQueryWrapper<SysRolePermission>()
						.in(SysRolePermission::getRoleId, roleIds)
		);
		
		// 如果没有权限，则返回空集合
		if (rolePermissions.isEmpty()) {
			return Collections.emptySet();
		}
		
		// 获取权限ID列表
		List<Long> permissionIds = rolePermissions.stream()
				.map(SysRolePermission::getPermissionId)
				.collect(Collectors.toList());
		
		// 查询权限信息
		List<SysPermission> permissions = baseMapper.selectList(
				new LambdaQueryWrapper<SysPermission>()
						.in(SysPermission::getId, permissionIds)
						.eq(SysPermission::getStatus, 1) // 只查询正常状态的权限
		);
		
		return new HashSet<>(permissions);
	}
	
	/**
	 * 构建菜单树
	 * @param menuList 菜单列表
	 * @param parentId 父菜单ID
	 * @return 菜单树
	 */
	private List<MenuVO> buildMenuTree(List<SysPermission> menuList, Long parentId) {
		return menuList.stream()
				.filter(menu -> Objects.equals(menu.getParentId(), parentId))
				.map(menu -> {
					MenuVO menuVO = new MenuVO();
					menuVO.setId(menu.getId());
					menuVO.setName(menu.getName());
					menuVO.setPath(menu.getPath());
					menuVO.setComponent(menu.getComponent());
					menuVO.setIcon(menu.getIcon());
					menuVO.setChildren(buildMenuTree(menuList, menu.getId()));
					return menuVO;
				})
				.collect(Collectors.toList());
	}

	/**
	 * 构建权限树
	 * @param permissions 权限列表
	 * @return 权限树
	 */
	@Override
	public List<SysPermission> buildPermissionTree(List<SysPermission> permissions) {
		if (permissions == null || permissions.isEmpty()) {
			return new ArrayList<>();
		}
		
		// 将权限列表转换为Map，便于查找
		Map<Long, SysPermission> permissionMap = permissions.stream()
				.collect(Collectors.toMap(SysPermission::getId, permission -> permission));
		
		List<SysPermission> rootPermissions = new ArrayList<>();
		
		// 遍历权限列表
		for (SysPermission permission : permissions) {
			// 如果是根权限（parentId为0），则直接添加到结果列表
			if (permission.getParentId() == 0L) {
				rootPermissions.add(permission);
			} else {
				// 否则添加到父级权限的子权限列表中
				SysPermission parent = permissionMap.get(permission.getParentId());
				if (parent != null) {
					if (parent.getChildren() == null) {
						parent.setChildren(new ArrayList<>());
					}
					parent.getChildren().add(permission);
				}
			}
		}
		
		// 按类型和排序字段排序
		rootPermissions.sort(Comparator.comparing(SysPermission::getType)
				.thenComparing(SysPermission::getSort));
		
		return rootPermissions;
	}
}
