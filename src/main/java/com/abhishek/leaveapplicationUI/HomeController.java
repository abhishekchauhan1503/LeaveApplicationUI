package com.abhishek.leaveapplicationUI;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.abhishek.leaveapplication.model.Role;
import com.abhishek.leaveapplicationservice.services.RoleServices;
import com.abhishek.leaveapplicationservice.services.UserServices;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@Autowired
	private RoleServices userServices;
	
	public RoleServices getUserServices() {
		return userServices;
	}

	//@Resource(name="roleService")
	public void setUserServices(RoleServices userServices) {
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



	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate );
		model.addAttribute("roles", roles);
		model.addAttribute("selected", "1");

		try {
			//roleServices.createRole(role);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("Error: Cannot create new Role. Detailled error:\n", e);
		}
		logger.info(role.getRoleName());
		
		return "home";
	}
	
}
