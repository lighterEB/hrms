package com.lightereb.hrms.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

/**
 * 分配用户角色请求参数
 */
@Data
@Schema(description = "分配用户角色请求参数")
public class AssignUserRolesRequest {
    
    @NotNull(message = "角色ID列表不能为空")
    @Schema(description = "角色ID列表")
    private List<Long> roleIds;
} 