/**
 * @authour Jacky
 * @data Dec 23, 2019
 */
package com.example.project.Controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.project.Dto.AjaxResult;
import com.example.project.Entity.User;
import com.example.project.Service.UserService;
import com.example.project.Utils.JwtUtil;
import com.example.project.Utils.Util;

/**
 * @author Jacky
 *
 */
@RestController
public class UserController {
	@Autowired
	UserService userService;
	
	@PostMapping("/login")
	@ResponseBody
	public AjaxResult login(@RequestBody Map<String, String> map) {
		String username = map.get("username");
		String password = map.get("password");
		User user = userService.checkUser(username);
		if (user != null) {
			String pwd = Util.stringMD5(password);
			String upwd = user.getPassword();
			if (upwd.equals(pwd)) {
				// 返回token
				String token = JwtUtil.sign(username, user.getId());
				if (token != null) {
					return AjaxResult.success("成功", token);
				}
			} else {
				return AjaxResult.fail("密码不正确");
			}
		}
		return AjaxResult.fail("用户不存在");
	}
	
	@PostMapping("/register")
	@ResponseBody
	public AjaxResult register(@RequestBody User map) {
		String username = map.getUsername();
		String password = map.getPassword();
		if (username != null && password != null) {
			User user = userService.checkUser(username);
			if (user == null) {
				User u = userService.insert(map);
				return AjaxResult.success("注册成功", u);
			} else {
				return AjaxResult.fail("用户名已存在");
			}
		} else {
			return AjaxResult.fail("注册失败：用户名和密码不能为空");
		}
		
		
	}
	
	@PostMapping("/getUser")
	@ResponseBody
	public AjaxResult getUserInfo(HttpServletRequest request, @RequestBody Map<String, String> map) {
		String username = map.get("username");
		String token = request.getHeader("token");
		boolean verity = JwtUtil.verity(token);
		if (verity) {
			User user = userService.geUser(username);
			if (user != null) {
				return AjaxResult.success("成功", user);
			}
		}
		return AjaxResult.fail();
	}
}
