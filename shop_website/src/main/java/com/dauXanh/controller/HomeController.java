package com.dauXanh.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.dauXanh.service.CategoryService;
import com.dauXanh.service.ProductService;

@Controller
public class HomeController {

	Logger log = Logger.getLogger(this.getClass());
	
	@Autowired
	CategoryService categoryService;
	
	@Autowired
	ProductService productService;

	@GetMapping("/")
	String showHome(HttpServletRequest req, HttpSession session) {
		
		req.setAttribute("user", session.getAttribute("user"));
		return "home";
	}
}
