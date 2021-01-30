package com.dauXanh.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class HomeController {
	
	Logger log = Logger.getLogger(this.getClass());

	@GetMapping
	String showHome(HttpServletRequest req, HttpSession session) {
		
		req.setAttribute("user", session.getAttribute("user"));
		req.setAttribute("cart", session.getAttribute("cart"));

		return "home";
	}
}
