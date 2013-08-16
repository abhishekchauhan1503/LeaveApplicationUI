package com.abhishek.leaveapplicationUI.controllers;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.abhishek.leaveapplicationservice.generatedclasses.CreateApplicationInput;
import com.abhishek.leaveapplicationservice.services.ApplicationServices;

@Controller
public class ApplicationController {

	@Autowired
	private ApplicationServices applicationServices;

	private static final Logger logger = LoggerFactory
			.getLogger(ApplicationController.class);

	@RequestMapping(value = "/application")
	public ModelAndView prepareApplicationView() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("application");
		mav.addObject("application", new CreateApplicationInput());
		return mav;
	}

	@RequestMapping(value = "/saveapplication")
	public String saveApplication(
			@ModelAttribute("application") CreateApplicationInput application,
			BindingResult result) {
		ModelAndView mav = new ModelAndView();

		try {
			application.setStatus("P");
			Date date = new Date(); 
			application.setSubmissionDate(date);
			long id = applicationServices.createNewApplication(application);
			mav.setViewName("home");
		} catch (Exception ex) {
			logger.error("Cannot Create Application. Detailled Error: \n", ex);
			String attributeValues = "To: " + application.getTo() + "\nFrom: "
					+ application.getFrom() + "\nContent: "
					+ application.getContent() + "\nDate: "
					+ application.getSubmissionDate() + "\nStatus: "
					+ application.getStatus();
			logger.error("Attribute Values:\n"+attributeValues);
			mav.setViewName("error");
		}
		return "home";
	}
}
