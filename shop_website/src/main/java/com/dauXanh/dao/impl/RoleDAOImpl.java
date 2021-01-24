package com.dauXanh.dao.impl;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.dauXanh.dao.RoleDAO;
import com.dauXanh.entity.Role;

@Repository
@Transactional
public class RoleDAOImpl extends BaseDAOImpl<Role> implements RoleDAO<Role> {

}
