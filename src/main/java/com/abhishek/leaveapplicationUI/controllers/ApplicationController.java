package com.abhishek.leaveapplicationUI.controllers;

import java.util.Date;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.abhishek.leaveapplication.generatedclasses.CreateNewMessageInput;
import com.abhishek.leaveapplication.model.Message;
import com.abhishek.leaveapplication.model.User;
import com.abhishek.leaveapplicationservice.generatedclasses.CreateApplicationInput;
import com.abhishek.leaveapplicationservice.services.ApplicationServices;
import com.abhishek.leaveapplicationservice.services.MessageService;

@Controller
public class ApplicationController {

	@Autowired
	private ApplicationServices applicationServices;

	@Autowired
	private MessageService messageService;

	//@Autowired//@Resource(name = "system")
	//@Qualifier("system")
	@Resource(name = "system")
	private User systemUser;

	public User getSystemUser() {
		return systemUser;
	}

	public void setSystemUser(User systemUser) {
		this.systemUser = systemUser;
	}

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
	public ModelAndView saveApplication(
			@ModelAttribute("application") CreateApplicationInput application,
			BindingResult result) {
		ModelAndView mav = new ModelAndView();
		CreateNewMessageInput input = new CreateNewMessageInput();

		try {
			application.setStatus("P");
			Date date = new Date();
			application.setSubmissionDate(date);
			
			long id = applicationServices.createNewApplication(application);
			mav.setViewName("home");
			input.setContent("You have a new message");
			input.setRead(false);
			input.setTo(application.getTo());
			input.setFrom(1);
			long saveMessage = messageService.saveNewMessage(input);
			
		} catch (Exception ex) {
			logger.error("Cannot Create Application. Detailled Error: \n", ex);
			String attributeValues = "To: " + application.getTo() + "\nFrom: "
					+ application.getFrom() + "\nContent: "
					+ application.getContent() + "\nDate: "
					+ application.getSubmissionDate() + "\nStatus: "
					+ application.getStatus();
			
			String messageAttributeValues = "To: " + input.getTo() + "\nFrom: "
					+ input.getFrom() + "\nContent: "
					+ input.getContent() + "\nRead: "
					+ input.isRead();
			
			logger.error("Attribute Values:\n" + attributeValues);
			logger.error("Message Values:\n" + messageAttributeValues);
			mav.setViewName("error");
		}
		return mav;
	}
}