package com.dauXanh.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dauXanh.dao.CategoryDAO;
import com.dauXanh.dto.Page;
import com.dauXanh.entity.Category;

@Service
@Transactional
public class CategoryService {

	@Autowired
	CategoryDAO<Category> categoryDAO;

	public List<Category> findAll(Page page) throws Exception {
		return categoryDAO.findAll(null, null, page);
	}

	public Category findById(int id) throws Exception {
		return categoryDAO.findById(Category.class, id);
	}

	public boolean save(Category category) throws Exception {
		category.setCreatedAt(new Date());
		category.setUpdatedAt(new Date());

		return categoryDAO.save(category);
	}

	public boolean update(Category category) throws Exception {
		category.setCreatedAt(category.getCreatedAt());
		category.setUpdatedAt(new Date());

		return categoryDAO.update(category);
	}

	public boolean deleteById(int id) {
		return categoryDAO.deleteById(Category.class, id);
	}
}
