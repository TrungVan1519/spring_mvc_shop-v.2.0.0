package com.dauXanh.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.dauXanh.dto.Page;

public interface BaseDAO<E> {
	
	List<E> findAll(StringBuilder criteria, Map<String, Object> params, Page page);
	E findById(Class<E> entityClassName, Serializable id);
	boolean save(E obj);
	boolean update(E obj);
	boolean deleteById(Class<E> entityClassName, Serializable id);
}
