package com.dauXanh.dao.impl;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.dauXanh.dao.SizeDAO;
import com.dauXanh.entity.Size;

@Repository
@Transactional
public class SizeDAOImpl extends BaseDAOImpl<Size> implements SizeDAO<Size> {

}
