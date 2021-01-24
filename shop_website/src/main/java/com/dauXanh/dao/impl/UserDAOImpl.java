package com.dauXanh.dao.impl;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.dauXanh.dao.UserDAO;
import com.dauXanh.entity.User;

@Repository
@Transactional
public class UserDAOImpl extends BaseDAOImpl<User> implements UserDAO<User> {

}
