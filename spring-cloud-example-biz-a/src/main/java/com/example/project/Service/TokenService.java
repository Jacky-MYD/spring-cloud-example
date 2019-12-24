/**
 * @authour Jacky
 * @data Dec 21, 2019
 */
package com.example.project.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.example.project.Entity.Result;
import com.example.project.Utils.RedisUtil;

import nl.bitwalker.useragentutils.UserAgent;

/**
 * @author Jacky
 *
 */
@Service("tokenService")
public class TokenService {
	private RedisUtil redisUtil;
	
	public TokenService() {
		init();
	}
	
	private void init() {
		@SuppressWarnings("resource")
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
		redisUtil = (RedisUtil)applicationContext.getBean("RedisUtil");
	}

    //生成token(格式为token:设备-加密的用户名-时间-六位随机数)
	public String generateToken(String userAgent, String username) {
		StringBuilder token = new StringBuilder();
		
		// 加token
		token.append("token");
		// 加设备
		UserAgent userAgent1 = UserAgent.parseUserAgentString(userAgent);
		if (userAgent1.getOperatingSystem().isMobileDevice()) {
			token.append("MOBILE-");
		} else {
			token.append("PC-");
		}
		
		// 加加密的用户名
		token.append(DigestUtils.md5Hex(username + "-"));
		
		// 加时间
		token.append(new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()) + "-");
		
		// 加六位随机数111111 - 999999
		token.append(new Random().nextInt((999999 - 111111 + 1)) + 111111);
		System.out.println("token ==>" + token.toString());
		return token.toString();
	}
	
	// 保存token
	public void saveToken(String token, Result<Long> user) {
		if (token.startsWith("token:PC")) {
			redisUtil.setex(token, 2*60*60, JSONObject.toJSONString(user));
		} else {
			redisUtil.set(token, JSONObject.toJSONString(user));
		}
	}
}
