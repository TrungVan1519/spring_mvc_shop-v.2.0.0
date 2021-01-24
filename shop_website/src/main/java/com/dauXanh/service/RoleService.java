package com.dauXanh.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dauXanh.dao.RoleDAO;
import com.dauXanh.dto.Page;
import com.dauXanh.entity.Role;

@Service
@Transactional
public class RoleService {

	@Autowired
	RoleDAO<Role> roleDAO;

	public List<Role> findAll(Page page) throws Exception {
		return roleDAO.findAll(null, null, page);
	}

	public Role findById(int id) throws Exception {
		return roleDAO.findById(Role.class, id);
	}
}
