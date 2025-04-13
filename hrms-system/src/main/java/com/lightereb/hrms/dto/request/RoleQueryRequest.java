package com.lightereb.hrms.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 角色查询请求参数
 */
@Data
@Schema(description = "角色查询请求参数")
public class RoleQueryRequest {
    
    @Schema(description = "关键字（角色名称或编码）")
    private String keyword;
    
    @Schema(description = "状态（0-禁用 1-正常）")
    private Integer status;
    
    @Schema(description = "页码", defaultValue = "1")
    private Integer pageNum = 1;
    
    @Schema(description = "每页条数", defaultValue = "10")
    private Integer pageSize = 10;
} 