package com.lightereb.hrms.security.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * JWT工具类，用于生成和验证JWT令牌
 */
@Component
public class JwtTokenUtil
{

	@Value("${jwt.secret}")
	private String secret;

	@Value("${jwt.expiration}")
	private long expiration;

	/**
	 * 获取签名密钥
	 *
	 * @return 签名密钥
	 */
	private Key getSigningKey() {
		byte[] keyBytes = secret.getBytes();
		return Keys.hmacShaKeyFor(keyBytes);
	}

	/**
	 * 为指定用户生成JWT令牌
	 *
	 * @param userDetails 用户详情
	 * @return JWT令牌
	 */
	public String generateToken(UserDetails userDetails) {
		Map<String, Object> claims = new HashMap<>();
		// 可以在这里添加额外的声明（如用户角色、权限等）
		return createToken(claims, userDetails.getUsername());
	}

	/**
	 * 创建JWT令牌
	 *
	 * @param claims  令牌声明
	 * @param subject 令牌主题（通常是用户名）
	 * @return JWT令牌
	 */
	private String createToken(Map<String, Object> claims, String subject) {
		Date now = new Date(System.currentTimeMillis());
		Date expiryDate = new Date(now.getTime() + expiration);

		return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(now).setExpiration(expiryDate).signWith(getSigningKey(), SignatureAlgorithm.HS256).compact();
	}

	/**
	 * 验证JWT令牌是否有效
	 *
	 * @param token       JWT令牌
	 * @param userDetails 用户详情
	 * @return 是否有效
	 */
	public Boolean validateToken(String token, UserDetails userDetails) {
		final String username = extractUsername(token);
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}

	/**
	 * 从JWT令牌中提取用户名
	 *
	 * @param token JWT令牌
	 * @return 用户名
	 */
	public String extractUsername(String token) {
		return extractClaim(token, Claims::getSubject);
	}

	/**
	 * 从JWT令牌中提取过期时间
	 *
	 * @param token JWT令牌
	 * @return 过期时间
	 */
	public Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}

	/**
	 * 提取JWT令牌声明
	 *
	 * @param token          JWT令牌
	 * @param claimsResolver 声明解析函数
	 * @return 解析后的结果
	 */
	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}

	/**
	 * 提取所有声明
	 *
	 * @param token JWT令牌
	 * @return 所有声明
	 */
	private Claims extractAllClaims(String token) {
		return Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token).getBody();
	}

	/**
	 * 检查令牌是否过期
	 *
	 * @param token JWT令牌
	 * @return 是否过期
	 */
	private Boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}

	/**
	 * 获取令牌过期时间（毫秒）
	 *
	 * @return 过期时间
	 */
	public long getExpirationTimeInMillis() {
		return expiration;
	}
}
