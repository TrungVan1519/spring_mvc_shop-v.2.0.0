package com.dauXanh.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dauXanh.entity.User;
import com.dauXanh.service.UserService;

@Controller
@RequestMapping("/auth")
public class AuthController {
	
	Logger log = Logger.getLogger(this.getClass());

	@Autowired
	UserService userService;

	/**
	 * For auth/auth.jsp
	 * 
	 * @param command
	 * @param req
	 * @param session
	 * @return
	 */
	@GetMapping
	String showForm(@RequestParam(required = false) String command, HttpServletRequest req, HttpSession session) {

		req.setAttribute("user", session.getAttribute("user"));
		req.setAttribute("cart", session.getAttribute("cart"));

		if (command == null || (!command.contentEquals("signup") && !command.contentEquals("logout"))) {
			req.setAttribute("command", "login");

			return "auth";
		}

		if (command.contentEquals("signup")) {
			req.setAttribute("command", "signup");

			return "auth";
		}

		if (command.contentEquals("logout")) {
			session.removeAttribute("user");

			return "redirect:/";
		}

		return null;
	}

	@PostMapping("/ajax-login")
	@ResponseBody
	String handleLogin(@RequestParam String email, @RequestParam String password, HttpSession session) {

		User user = userService.validate(email, password);
		if (user != null) {
			session.setAttribute("user", user);
			session.setMaxInactiveInterval(60 * 3); // 3 mins
			
			return "/";
		}

		return null;
	}

	@PostMapping("/ajax-signup")
	@ResponseBody
	List<String> handleSignup(@Valid @ModelAttribute User user, BindingResult bindingResult, HttpServletRequest req) {

		if (bindingResult.hasErrors()) {
			List<String> errors = new ArrayList<String>();
			bindingResult.getFieldErrors().forEach(field -> {
				errors.add(field.getDefaultMessage());
			});

			return errors;
		}

		boolean isCreateSuccess = userService.save(user);
		if (!isCreateSuccess) {
			List<String> errors = new ArrayList<String>();
			errors.add("Email is already existing");

			return errors;
		}

		return null;
	}
}
