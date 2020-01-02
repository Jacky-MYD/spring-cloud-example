/**
 * @authour Jacky
 * @data Dec 23, 2019
 */
package com.example.project.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.Predicate;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.example.project.entity.User;
import com.example.project.query.Search;
import com.example.project.repository.UserRepository;
import com.example.project.utils.Util;

/**
 * @author Jacky
 *
 */
@Service
public class UserService extends BaseService<User> {
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
	
	public Page<User> search(Search search) {
		try {
			Pageable pageable = buildPageable(search);
			Page<User> page = userRepository.findAll((Specification<User>) (root, query, criteriaBuilder) -> {
				List<Predicate> list = new ArrayList<Predicate>();
				buildPredicate(root, query, criteriaBuilder, list, search);
				if (StringUtils.isNotBlank(search.getKeyword())) {
                	list.add(criteriaBuilder.like(root.get("username").as(String.class), "%" + search.getKeyword() + "%"));
                }
				if (0 < list.size()) {
                    Predicate[] p = new Predicate[list.size()];
                    return criteriaBuilder.and(list.toArray(p));
                }
				return query.getRestriction();
			}, pageable);
			return page;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}

	
}
