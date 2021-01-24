package com.dauXanh.dao.impl;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.dauXanh.dao.ProductDAO;
import com.dauXanh.entity.Product;

@Repository
@Transactional
public class ProductDAOImpl extends BaseDAOImpl<Product> implements ProductDAO<Product> {

}
