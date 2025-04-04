package com.lightereb.hrms.security.util;

import com.lightereb.hrms.config.properties.JwtProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * JWT工具类
 */
@Component
@RequiredArgsConstructor
public class JwtTokenUtil {

	private final JwtProperties jwtProperties;

	/**
	 * 获取签名密钥
	 */
	private Key getSigningKey() {
		byte[] keyBytes = jwtProperties.getSecret().getBytes();
		return Keys.hmacShaKeyFor(keyBytes);
	}

	/**
	 * 生成JWT令牌
	 */
	public String generateToken(UserDetails userDetails) {
		Map<String, Object> claims = new HashMap<>();
		return createToken(claims, userDetails.getUsername());
	}

	/**
	 * 创建令牌
	 */
	private String createToken(Map<String, Object> claims, String subject) {
		Date now = new Date();
		Date expiryDate = new Date(now.getTime() + jwtProperties.getExpiration());

		return Jwts.builder()
				.setClaims(claims)
				.setSubject(subject)
				.setIssuedAt(now)
				.setExpiration(expiryDate)
				.signWith(getSigningKey(), SignatureAlgorithm.HS256)
				.compact();
	}

	/**
	 * 从令牌中获取用户名
	 */
	public String getUsernameFromToken(String token) {
		return getClaimFromToken(token, Claims::getSubject);
	}

	/**
	 * 判断令牌是否过期
	 */
	public boolean isTokenExpired(String token) {
		Date expiration = getClaimFromToken(token, Claims::getExpiration);
		return expiration.before(new Date());
	}

	/**
	 * 验证令牌
	 */
	public boolean validateToken(String token, UserDetails userDetails) {
		String username = getUsernameFromToken(token);
		return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
	}

	/**
	 * 获取过期时间（毫秒）
	 */
	public long getExpirationTimeInMillis() {
		return jwtProperties.getExpiration();
	}

	private <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
		Claims claims = getAllClaimsFromToken(token);
		return claimsResolver.apply(claims);
	}

	private Claims getAllClaimsFromToken(String token) {
		return Jwts.parserBuilder()
				.setSigningKey(getSigningKey())
				.build()
				.parseClaimsJws(token)
				.getBody();
	}
}