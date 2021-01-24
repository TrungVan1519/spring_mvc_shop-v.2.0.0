package com.dauXanh.dao.impl;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.dauXanh.dao.BillDetailDAO;
import com.dauXanh.entity.BillDetail;

@Repository
@Transactional
public class BillDetailDAOImpl extends BaseDAOImpl<BillDetail> implements BillDetailDAO<BillDetail> {

}
