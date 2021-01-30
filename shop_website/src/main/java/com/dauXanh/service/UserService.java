package com.dauXanh.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dauXanh.dao.RoleDAO;
import com.dauXanh.dao.UserDAO;
import com.dauXanh.dto.Page;
import com.dauXanh.entity.Role;
import com.dauXanh.entity.User;

@Service
@Transactional
public class UserService {
	
	Logger log = Logger.getLogger(this.getClass());

	@Autowired
	RoleDAO<Role> roleDAO;
	
	@Autowired
	UserDAO<User> userDAO;

	public List<User> findAll(Page page) {
		
		return userDAO.findAll(null, null, page);
	}

	public User validate(String email, String password) {
	
		StringBuilder criteria = new StringBuilder("WHERE email = :email AND password = :password");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("email", email);
		params.put("password", password);
		
		List<User> users = userDAO.findAll(criteria, params, null);
		if (users.size() > 0) {
			return users.get(0);
		}
		
		return null;
	}
	
	public boolean save(User user) {
		
		StringBuilder isExist = new StringBuilder("WHERE email = :email");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("email", user.getEmail());

		// if user is not created in DB
		List<User> users = userDAO.findAll(isExist, params, null);
		if (users.size() == 0) {
			
			// user will be set by "user" role by DB automatically but I will set it explicitly
			StringBuilder criteria = new StringBuilder("WHERE name = 'user'");
			Role role = roleDAO.findAll(criteria, null, null).get(0);
			user.setRole(role);
			
			user.setCreatedAt(new Date());
			user.setUpdatedAt(new Date());
			
			return userDAO.save(user);
		}
		
		return false;
	}
}
