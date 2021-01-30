package com.dauXanh.service;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dauXanh.dao.BillDetailDAO;
import com.dauXanh.dto.Page;
import com.dauXanh.entity.BillDetail;

@Service
@Transactional
public class BillDetailService {
	
	Logger log = Logger.getLogger(this.getClass());

	@Autowired
	BillDetailDAO<BillDetail> billDetailDAO;

	public List<BillDetail> findAll(Page page) throws Exception {
		
		return billDetailDAO.findAll(null, null, page);
	}

	public BillDetail findById(int id) throws Exception {
		
		return billDetailDAO.findById(BillDetail.class, id);
	}

	public boolean save(BillDetail billDetail) throws Exception {
		
		billDetail.setCreatedAt(new Date());
		billDetail.setUpdatedAt(new Date());

		return billDetailDAO.save(billDetail);
	}

	public boolean update(BillDetail billDetail) throws Exception {
		
		billDetail.setCreatedAt(billDetail.getCreatedAt());
		billDetail.setUpdatedAt(new Date());

		return billDetailDAO.update(billDetail);
	}

	public boolean deleteById(int id) throws Exception {
		
		return billDetailDAO.deleteById(BillDetail.class, id);
	}
}
