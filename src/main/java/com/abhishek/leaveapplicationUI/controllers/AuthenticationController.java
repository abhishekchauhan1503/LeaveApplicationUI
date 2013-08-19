package com.abhishek.leaveapplicationUI.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.abhishek.leaveapplicationservice.entity.UserEntity;
import com.abhishek.leaveapplicationservice.generatedclasses.AuthenticateUserInput;
import com.abhishek.leaveapplicationservice.services.AuthenticationService;

@Controller
public class AuthenticationController {

	@Autowired
	private AuthenticationService authenticationService;

	private static final Logger logger = LoggerFactory
			.getLogger(AuthenticationController.class);

	@RequestMapping(value = "/login")
	public ModelAndView getAuthenticationView() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("login");
		mav.addObject("authenticationInput", new AuthenticateUserInput());
		return mav;
	}

	@RequestMapping(value = "/authenticateUser")
	public ModelAndView authenticateUser(
			@ModelAttribute("authenticationInput") AuthenticateUserInput input,
			BindingResult result) {
		ModelAndView mav = new ModelAndView();

		try {
			UserEntity authenticatedUser = authenticationService
					.authenticateUserAndReturnProfileIfUserExists(input);
			if (authenticatedUser != null) {
				mav.addObject("authenticated", true);
				mav.addObject("user", authenticatedUser);
				mav.setViewName("userHomePage");

			} else {
				mav.addObject("user", authenticatedUser);
				mav.addObject("authenticated", false);
				mav.setViewName("login");

			}
		} catch (Exception ex) {
			logger.error(
					"ERROR: Cannot call authentication service. Detailled error: \n",
					ex);
			mav.setViewName("error");
		}
		return mav;
	}
}
