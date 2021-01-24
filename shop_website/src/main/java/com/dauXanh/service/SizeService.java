package com.dauXanh.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dauXanh.dao.SizeDAO;
import com.dauXanh.dto.Page;
import com.dauXanh.entity.Size;

@Service
@Transactional
public class SizeService {

	@Autowired
	SizeDAO<Size> sizeDAO;
	
	public List<Size> findAll(Page page) throws Exception {
		return sizeDAO.findAll(null, null, page);
	}
	
	public Size findById(int id) throws Exception {
		return sizeDAO.findById(Size.class, id);
	}
	
	public boolean save(Size size) throws Exception {
		size.setCreatedAt(new Date());
		size.setUpdatedAt(new Date());

		return sizeDAO.save(size);
	}

	public boolean update(Size size) throws Exception {
		size.setCreatedAt(size.getCreatedAt());
		size.setUpdatedAt(new Date());

		return sizeDAO.update(size);
	}

	public boolean deleteById(int id) {
		return sizeDAO.deleteById(Size.class, id);
	}
}
