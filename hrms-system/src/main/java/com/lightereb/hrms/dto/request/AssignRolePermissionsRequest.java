package com.lightereb.hrms.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

/**
 * 分配角色权限请求参数
 */
@Data
@Schema(description = "分配角色权限请求参数")
public class AssignRolePermissionsRequest {
    
    @NotNull(message = "权限ID列表不能为空")
    @Schema(description = "权限ID列表")
    private List<Long> permissionIds;
} 