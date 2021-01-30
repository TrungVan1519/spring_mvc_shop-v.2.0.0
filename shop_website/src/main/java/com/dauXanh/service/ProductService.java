package com.dauXanh.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dauXanh.dao.ProductDAO;
import com.dauXanh.dto.Page;
import com.dauXanh.entity.Product;

@Service
@Transactional
public class ProductService {
	
	Logger log = Logger.getLogger(this.getClass());

	@Autowired
	ProductDAO<Product> productDAO;

	public List<Product> findAll(Page page) throws Exception {
		
		return productDAO.findAll(null, null, page);
	}
	
	public Product findById(int id) throws Exception {
		
		return productDAO.findById(Product.class, id);
	}
	
	public List<Product> findByCategory(int categoryId) throws Exception {
		
		StringBuilder criteria = new StringBuilder("WHERE category.id = :categoryId");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("categoryId", categoryId);
		
		return productDAO.findAll(criteria, params, null);
	}

	public boolean save(Product product) throws Exception {
		
		product.setCreatedAt(new Date());
		product.setUpdatedAt(new Date());

		return productDAO.save(product);
	}

	public boolean update(Product product) throws Exception {
	
		product.setUpdatedAt(new Date());

		return productDAO.update(product);
	}

	public boolean deleteById(int id) throws Exception {
		
		return productDAO.deleteById(Product.class, id);
	}
}
