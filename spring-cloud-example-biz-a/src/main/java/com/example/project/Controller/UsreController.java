/**
 * @authour Jacky
 * @data Dec 20, 2019
 */
package com.example.project.Controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.example.project.Entity.Result;
import com.example.project.Entity.Token;
import com.example.project.Service.TokenService;
import com.example.project.Service.UserService;
import com.example.project.Utils.Util;

@RestController
@RequestMapping("/api/account")
public class UsreController {
	@Autowired
	UserService userService;
	
	@Autowired
	TokenService tokenService;
	
	@GetMapping("/")
	public void initData() {
		userService.initUserData();
	}
	
	@PostMapping("/login")
	public String login(@RequestBody JSONObject data, HttpServletRequest request) {
		System.out.println("===========++======================="+data);
		System.out.println(data.getString("username") + Util.stringMD5(data.getString("password")));
		String username = data.getString("username");
		String password = Util.stringMD5(data.getString("password"));
		
		Token token = new Token();
		
		Result<Long> user = userService.login(username, password);
		if (user != null) { // 登录成功生成token并保存token
			String userAgent = request.getHeader("user-agent");
			String token1 = tokenService.generateToken(userAgent, username);
			tokenService.saveToken(token1, user);
			
			token.setIsLogin("true");
			token.setToken(token1);
			token.setTokenCreatedTime(System.currentTimeMillis());
			token.setTokenExpiryTime(System.currentTimeMillis() + 2*60*60*1000);
		} else {
			token.setIsLogin("false");
		}
		
		return JSONObject.toJSONString(token);
	}
}
