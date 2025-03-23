package com.lightereb.hrms.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

/**
 * 注册请求DTO
 */
@Data
@Schema(description = "注册请求参数")
public class RegisterRequest {

	@Schema(description = "用户名", requiredMode = RequiredMode.REQUIRED)
	@NotBlank(message = "用户名不能为空")
	private String username;

	@Schema(description = "密码", requiredMode = RequiredMode.REQUIRED)
	@NotBlank(message = "密码不能为空")
	@Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{6,20}$",
			message = "密码必须包含大小写字母和数字，长度6-20位")
	private String password;

	@Schema(description = "真实姓名", requiredMode = RequiredMode.REQUIRED)
	@NotBlank(message = "真实姓名不能为空")
	private String realName;

	@Schema(description = "手机号", requiredMode = RequiredMode.REQUIRED)
	@NotBlank(message = "手机号不能为空")
	@Pattern(regexp = "^1[3-9]\\d{9}$", message = "手机号格式不正确")
	private String phone;

	@Schema(description = "邮箱", requiredMode = RequiredMode.NOT_REQUIRED)
	@Email(message = "邮箱格式不正确")
	private String email;
}