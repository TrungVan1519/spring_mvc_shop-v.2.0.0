package com.dauXanh.dao.impl;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.dauXanh.dao.CategoryDAO;
import com.dauXanh.entity.Category;

@Repository
@Transactional
public class CategoryDAOImpl extends BaseDAOImpl<Category> implements CategoryDAO<Category> {

}
