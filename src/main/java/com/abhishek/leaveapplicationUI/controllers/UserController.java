package com.abhishek.leaveapplicationUI.controllers;

import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.abhishek.leaveapplication.model.Role;
import com.abhishek.leaveapplication.model.User;
import com.abhishek.leaveapplicationUI.HomeController;
import com.abhishek.leaveapplicationservice.generatedclasses.CreateUserInput;
import com.abhishek.leaveapplicationservice.services.RoleServices;
import com.abhishek.leaveapplicationservice.services.UserServices;

@Controller
public class UserController {

	@Autowired
	private UserServices userServices;

	public UserServices getUserServices() {
		return userServices;
	}

	// @Resource(name="roleService")
	public void setUserServices(UserServices userServices) {
		this.userServices = userServices;
	}

	@Autowired
	private Role role;

	@Resource(name = "roles")
	Map<String, String> roles;

	public Map<String, String> getRoles() {
		return roles;
	}

	public void setRoles(Map<String, String> roles) {
		this.roles = roles;
	}

	private static final Logger logger = LoggerFactory
			.getLogger(UserController.class);

	@RequestMapping(value = "/registerUser")
	public ModelAndView registerUserView() {
		ModelAndView model = new ModelAndView();
		model.addObject("roles", roles);
		model.addObject("selected", "2");

		model.setViewName("registerUser");

		model.addObject("user", new CreateUserInput());
		logger.info("Redirecting to user registration page");

		return model;

	}
	
	@RequestMapping(value="/registerUserProcess")
	public ModelAndView registerUser(@ModelAttribute("user") CreateUserInput user, BindingResult result){
		ModelAndView modelAndView = new ModelAndView();

		try{
		long id = userServices.createUser(user);
		modelAndView.setViewName("home");
		
		}
		catch(Exception ex){
			logger.error("Cannot register user. Detailled Error: \n", ex);
			modelAndView.setViewName("error");

		}
		return modelAndView;
	}

}
