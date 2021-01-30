package com.dauXanh.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dauXanh.dao.BillDAO;
import com.dauXanh.dto.Page;
import com.dauXanh.entity.Bill;

@Service
@Transactional
public class BillService {
	
	Logger log = Logger.getLogger(this.getClass());

	@Autowired
	BillDAO<Bill> billDAO;

	public List<Bill> findAll(Page page) throws Exception {
		
		return billDAO.findAll(null, null, page);
	}

	public Bill findById(int id) throws Exception {
		
		return billDAO.findById(Bill.class, id);
	}
	
	public Bill findByName(String name) throws Exception {
		
		StringBuilder criteria = new StringBuilder("WHERE name = :name");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("name", name);
		
		return billDAO.findAll(criteria, params, null).get(0);
	}

	public boolean save(Bill bill) throws Exception {
		
		bill.setCreatedAt(new Date());
		bill.setUpdatedAt(new Date());

		return billDAO.save(bill);
	}

	public boolean update(Bill bill) throws Exception {
		
		bill.setCreatedAt(bill.getCreatedAt());
		bill.setUpdatedAt(new Date());

		return billDAO.update(bill);
	}

	public boolean deleteById(int id) throws Exception {
		
		return billDAO.deleteById(Bill.class, id);
	}
}
