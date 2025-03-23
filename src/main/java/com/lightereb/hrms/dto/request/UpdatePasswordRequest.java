package com.lightereb.hrms.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

/**
 * 修改密码请求DTO
 */
@Data
public class UpdatePasswordRequest {

	@NotBlank(message = "原密码不能为空")
	private String oldPassword;

	@NotBlank(message = "新密码不能为空")
	@Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{6,20}$",
			message = "密码必须包含大小写字母和数字，长度6-20位")
	private String newPassword;

	@NotBlank(message = "确认密码不能为空")
	private String confirmPassword;
}