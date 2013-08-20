package com.abhishek.leaveapplicationUI.controllers;

import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import com.abhishek.leaveapplication.model.Message;
import com.abhishek.leaveapplication.model.User;
import com.abhishek.leaveapplicationservice.entity.ApplicationEntity;
import com.abhishek.leaveapplicationservice.generatedclasses.CreateApplicationInput;
import com.abhishek.leaveapplicationservice.generatedclasses.CreateNewMessageInput;
import com.abhishek.leaveapplicationservice.generatedclasses.GetApplicationForUserInput;
import com.abhishek.leaveapplicationservice.generatedclasses.UpdateApplicationInput;
import com.abhishek.leaveapplicationservice.services.ApplicationServices;
import com.abhishek.leaveapplicationservice.services.MessageService;

@Controller
public class ApplicationController {

	@Autowired
	private ApplicationServices applicationServices;

	@Autowired
	private MessageService messageService;

	// @Autowired//@Resource(name = "system")
	// @Qualifier("system")
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
			input.setContent("You have a new Application Request");
			input.setRead(false);
			input.setTo(application.getTo());
			input.setFrom(1);
			long saveMessageManager = messageService.saveNewMessage(input);

			input.setContent("You Application Request has been sent to manager for approval");
			input.setTo(application.getFrom());
			long saveMessageUser = messageService.saveNewMessage(input);

		} catch (Exception ex) {
			logger.error("Cannot Create Application. Detailled Error: \n", ex);
			String attributeValues = "To: " + application.getTo() + "\nFrom: "
					+ application.getFrom() + "\nContent: "
					+ application.getContent() + "\nDate: "
					+ application.getSubmissionDate() + "\nStatus: "
					+ application.getStatus();

			String messageAttributeValues = "To: " + input.getTo() + "\nFrom: "
					+ input.getFrom() + "\nContent: " + input.getContent()
					+ "\nRead: " + input.isRead();

			logger.error("Attribute Values:\n" + attributeValues);
			logger.error("Message Values:\n" + messageAttributeValues);
			mav.setViewName("error");
		}
		return mav;
	}

	@RequestMapping(value = "/getAllApplicationsForUserView")
	public ModelAndView getAllApplicationsForUserView() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("getApplicationsView");
		mav.addObject("getInput", new GetApplicationForUserInput());
		return mav;
	}

	@RequestMapping(value = "/getAllApplicationsForUser")
	public ModelAndView getAllApplicationsForUser(
			@ModelAttribute("getInput") GetApplicationForUserInput input,
			BindingResult result,
			@RequestParam(value = "type") String type) {
		ModelAndView mav = new ModelAndView();
		try {
			ArrayList<ApplicationEntity> applications;
			mav.setViewName("viewAllApplications");
			if (type != null && type.equalsIgnoreCase("all")) {
				logger.info("GETTING ALL APPLICATIONS");
				applications = applicationServices
						.getAllApplicationsForUser(input);
			} else {
				logger.info("GETTING PENDING APPLICATIONS");
				applications = applicationServices
						.getAllPendingApplicationsForUser(input);
			}
			mav.addObject("applicationList", applications);
			mav.addObject("updateInput", new UpdateApplicationInput());
		} catch (Exception ex) {
			mav.setViewName("error");
			logger.error(
					"Failed to retrieve applications for the user. Detailled error: \n",
					ex);
		}
		return mav;
	}

	@RequestMapping(value = "/updateApplication")
	public ModelAndView updateApplication(
			@ModelAttribute("updateInput") UpdateApplicationInput updateInput,
			BindingResult result,
			@RequestParam(value = "action", required = false) String action) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("viewAllApplications");
		String managerActionMessage = "";
		String userActionMessage = "";

		try {
			if (action.equalsIgnoreCase("approve")) {
				updateInput.setStatus("A");
				managerActionMessage = "You have approved an Application Request. Application ID: "
						+ updateInput.getId();
				userActionMessage = "You Application Request has been approved by your manager Application ID: "
						+ updateInput.getId();
			} else if (action.equalsIgnoreCase("reject")) {
				updateInput.setStatus("R");
				managerActionMessage = "You have rejected an Application Request. Application ID: "
						+ updateInput.getId();
				userActionMessage = "You Application Request has been rejected by your manager Application ID: "
						+ updateInput.getId();
			}
			CreateNewMessageInput input = new CreateNewMessageInput();

			applicationServices.updateApplication(updateInput);
			input.setContent(managerActionMessage);
			input.setRead(false);
			input.setTo(updateInput.getTo());
			input.setFrom(1);
			long saveMessageManager = messageService.saveNewMessage(input);

			input.setContent(userActionMessage);
			input.setTo(updateInput.getFrom());
			long saveMessageUser = messageService.saveNewMessage(input);

		} catch (Exception ex) {
			String attributeValues = "ID: " + updateInput.getId() + "\nTo: "
					+ updateInput.getTo() + "\nFrom: " + updateInput.getFrom()
					+ "\nContent: " + updateInput.getContent() + "\nDate: "
					+ updateInput.getSubmissionDate() + "\nStatus: "
					+ updateInput.getStatus();

			logger.error(
					"Failed to update application for the user. Detailled error: \n",
					ex);
			logger.error("Attribute Values:\n" + attributeValues);

			mav.setViewName("error");
		}
		return mav;
	}

}