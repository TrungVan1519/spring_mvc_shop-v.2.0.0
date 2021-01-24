package com.dauXanh.dao.impl;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.dauXanh.dao.ColorDAO;
import com.dauXanh.entity.Color;

@Repository
@Transactional
public class ColorDAOImpl extends BaseDAOImpl<Color> implements ColorDAO<Color> {

}
