/**
 * @authour Jacky
 * @data Dec 23, 2019
 */
package com.example.project.controller;

import java.awt.image.BufferedImage;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.project.dto.AjaxResult;
import com.example.project.dto.Request;
import com.example.project.entity.User;
import com.example.project.query.Search;
import com.example.project.service.UserService;
import com.example.project.utils.Assert;
import com.example.project.utils.JwtUtil;
import com.example.project.utils.Util;
import com.google.code.kaptcha.Producer;

/**
 * @author Jacky
 *
 */
@RestController
public class UserController extends baseController {
	@Autowired
	Producer producer;
	
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
	
	@PostMapping("/user")
	@ResponseBody
	public AjaxResult getUser(@RequestBody Request data, HttpServletRequest request) {
		Search search = toSearch(data);
		System.out.println("========================>" + search);
		return AjaxResult.success("成功", userService.search(search));
	}

	/**
	 * 创建验证码
	 * 
	 * @param request, response
	 * @throws Exception
	 */
	@GetMapping("/code/{randomStr}")
	public void createCode(@PathVariable String randomStr, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Assert.isBlank(randomStr, "机器码不能为空");
		response.setHeader("Cache-Control", "no-store, no-cache");
		response.setContentType("image/jpeg");
		// 生成文字验证码
		String text = producer.createText();
		// 生成图片验证码
		BufferedImage image = producer.createImage(text);
		ServletOutputStream out = response.getOutputStream();
		ImageIO.write(image, "JPEG", out);
		IOUtils.closeQuietly(out);
	}
}
