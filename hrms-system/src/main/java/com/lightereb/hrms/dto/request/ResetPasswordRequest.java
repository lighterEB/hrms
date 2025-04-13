package com.lightereb.hrms.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 重置密码请求参数
 */
@Data
@Schema(description = "重置密码请求参数")
public class ResetPasswordRequest {
    
    @NotBlank(message = "新密码不能为空")
    @Schema(description = "新密码")
    private String newPassword;
} 