package com.lightereb.hrms.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Schema(description = "登录请求参数")
public class LoginRequest {

    @Schema(description = "用户名", requiredMode = RequiredMode.REQUIRED)
    @NotBlank(message = "用户名不能为空")
    private String username;

    @Schema(description = "密码", requiredMode = RequiredMode.REQUIRED)
    @NotBlank(message = "密码不能为空")
    private String password;
}
