package com.lightereb.hrms.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * JWT配置属性
 */
@Data
@Component
@ConfigurationProperties(prefix = "jwt")
@EnableConfigurationProperties
public class JwtProperties {
	/**
	 * 密钥
	 */
	private String secret;

	/**
	 * 过期时间（毫秒）
	 */
	private Long expiration;

	/**
	 * 请求头名称
	 */
	private String header;

	/**
	 * Token前缀
	 */
	private String tokenPrefix;
}