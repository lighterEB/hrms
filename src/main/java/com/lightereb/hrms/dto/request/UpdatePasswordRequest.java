package com.lightereb.hrms.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

/**
 * 修改密码请求DTO
 */
@Data
@Schema(description = "修改密码请求参数")
public class UpdatePasswordRequest {

	@NotBlank(message = "原密码不能为空")
	@Schema(description = "原密码", requiredMode = RequiredMode.REQUIRED)
	private String oldPassword;

	@Schema(description = "新密码", requiredMode = RequiredMode.REQUIRED)
	@NotBlank(message = "新密码不能为空")
	@Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{6,20}$",
			message = "密码必须包含大小写字母和数字，长度6-20位")
	private String newPassword;

	@Schema(description = "确认密码", requiredMode = RequiredMode.REQUIRED)
	@NotBlank(message = "确认密码不能为空")
	private String confirmPassword;
}