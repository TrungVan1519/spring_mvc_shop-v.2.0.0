package com.dauXanh.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dauXanh.dao.ColorDAO;
import com.dauXanh.dto.Page;
import com.dauXanh.entity.Color;

@Service
@Transactional
public class ColorService {

	@Autowired
	ColorDAO<Color> colorDAO;
	
	public List<Color> findAll(Page page) throws Exception {
		return colorDAO.findAll(null, null, page);
	}
	
	public Color findById(int id) throws Exception {
		return colorDAO.findById(Color.class, id);
	}
	
	public boolean save(Color color) throws Exception {
		color.setCreatedAt(new Date());
		color.setUpdatedAt(new Date());

		return colorDAO.save(color);
	}

	public boolean update(Color color) throws Exception {
		color.setCreatedAt(color.getCreatedAt());
		color.setUpdatedAt(new Date());

		return colorDAO.update(color);
	}

	public boolean deleteById(int id) {
		return colorDAO.deleteById(Color.class, id);
	}
}
