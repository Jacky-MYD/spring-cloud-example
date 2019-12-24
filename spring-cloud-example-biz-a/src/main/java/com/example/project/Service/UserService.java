/**
 * @authour Jacky
 * @data Dec 20, 2019
 */
package com.example.project.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.project.Entity.Result;
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
	 * 登录逻辑
	 * @param userName 用户名
	 * @param password MD5 hashed密码
	 * @return
	 */
	public Result<Long> login(String userName, String password) {
		Result<Long> result = new Result<>();
		List<User> list = userRepository.getByUserName(userName);
		
		User user = null;
		if(list != null && list.size() >0) {
			user = list.get(0);
		}
		if (user == null) {
			result.setErrCode(-1);
			result.setErrMsg("用户不存在");
		} else if (user.getPassword().equals(password)) {
			System.out.println(userName.toString());
			result.setErrCode(1);
			result.setData(user.getId());
		} else {
			result.setErrCode(-1);
			result.setErrMsg("密码错误");
		}
		return result;
	}
//	public Long login(String username, String password){
//		User user = userRepository.getByUserName(username).get();
//        //假设数据库有个用户用户名为fengqing,密码为123456
//        if (user != null && user.getPassword().equals(password)){
//            return user.getId();
//        } else {
//            return null;
//        }
//	}
	
	public Result<User> add(User user) {
		Result<User> result = new Result<>();
		User u = userRepository.save(user);
		if (u != null) {
			result.setErrCode(1);
			result.setData(u);
		} else {
			result.setErrCode(-1);
			result.setErrMsg("注册失败");
		}
		return result;
	}
	
	public void initUserData() {
		Iterable<User> users = userRepository.findAll();
		int sum = 0;
		while (users.iterator().hasNext()) {
			users.iterator().next();
			sum++;
		}
		if (sum == 0) {
			for (int i = 0; i < 5; i++) {
				User user = new User();
				user.setUserName("Admin" + i);
				String password = Util.stringMD5("Admin" + i);
				user.setPassword(password);
				add(user);
			}
		}
	}
}
