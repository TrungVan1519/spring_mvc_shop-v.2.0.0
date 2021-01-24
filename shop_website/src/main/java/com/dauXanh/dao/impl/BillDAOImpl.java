package com.dauXanh.dao.impl;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.dauXanh.dao.BillDAO;
import com.dauXanh.entity.Bill;

@Repository
@Transactional
public class BillDAOImpl extends BaseDAOImpl<Bill> implements BillDAO<Bill> {

}
