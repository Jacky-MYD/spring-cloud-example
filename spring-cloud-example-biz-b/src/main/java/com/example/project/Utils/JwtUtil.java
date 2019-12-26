/**
 * @authour Jacky
 * @data Dec 23, 2019
 */
package com.example.project.Utils;

import java.util.Date;
import java.util.HashMap;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;

/**
 * @author Jacky
 *
 */
public class JwtUtil {
	/**
	 * 过期时间为一分钟
	 * TODO 正式上线更换为1小时
	 */
	private static final long EXPIRE_TIME = 1*60*60*1000;
	
	/**
	 * token私钥
	 */
	private static final String TOKEN_SECRET = "abc";
	
	/**
	 * 生成签名，1小时后过期
	 * @param username
	 * @param id
	 * @return
	 */
	public static String sign(String username, Long id) {
		// 过期时间
		Date date = new Date(System.currentTimeMillis() + EXPIRE_TIME);
		// 私钥及加密算法
		Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
		// 设置头信息
		HashMap<String, Object> header = new HashMap<>(2);
		header.put("typ", "JWT");
		header.put("alg", "HS256");
		// 附带username和userId生成签名
		return JWT.create().withHeader(header).withClaim("username", username)
				.withClaim("userId", id).withExpiresAt(date).sign(algorithm);
	}
	
	@SuppressWarnings("unused")
	public static boolean verity(String token) {
		try {
			Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
			JWTVerifier verifier = JWT.require(algorithm).build();
			DecodedJWT jwt = verifier.verify(token);
			return true;
		} catch (IllegalArgumentException e) {
			// TODO: handle exception
			return false;
		} catch (JWTVerificationException e) {
			// TODO: handle exception
			return false;
		}
	}
}
