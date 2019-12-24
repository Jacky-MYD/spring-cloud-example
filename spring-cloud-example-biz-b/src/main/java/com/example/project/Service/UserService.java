/**
 * @authour Jacky
 * @data Dec 23, 2019
 */
package com.example.project.Service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.project.Entity.User;
import com.example.project.Repository.UserRepository;
import com.example.project.Utils.Util;

/**
 * @author Jacky
 *
 */
@Service
public class UserService {
	@Autowired
	UserRepository userRepository;
	
	/**
	 * 用户登录
	 * @param username
	 * @return
	 */
	public User checkUser(String username) {
		List<User> list = userRepository.getByUsername(username);
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}
	
	/**
	 * 用户注册
	 * @param user
	 * @return
	 */
	public User insert(User user) {
		String username = user.getUsername();
		Long time = System.currentTimeMillis();
		user.setCreated(new Date(time));
		String password = Util.stringMD5(user.getPassword());
		user.setPassword(password);
		
		userRepository.save(user);
		return this.geUser(username);
	}
	
	/**
	 * 
	 * @param username
	 * @return
	 */
	public User geUser(String username) {
		List<User> list = userRepository.getByUsername(username);
		if (list != null && list.size() > 0) {
			User u = list.get(0);
			User user = new User();
			user.setId(u.getId());
			user.setUsername(u.getUsername());
			user.setCreated(u.getCreated());
			return user;
		}
		return null;
	}
}
