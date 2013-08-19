package com.abhishek.leaveapplicationUI.controllers;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import com.abhishek.leaveapplication.model.Message;
import com.abhishek.leaveapplicationservice.generatedclasses.GetMessagesInput;
import com.abhishek.leaveapplicationservice.services.MessageService;

@Controller
public class MessageController {

	@Autowired
	private MessageService messageService;

	private static final Logger logger = LoggerFactory
			.getLogger(MessageController.class);
	private static long pageLoadCounter = 0;

	@RequestMapping(value = "/getMessagesForUserView")
	public ModelAndView getMessagesForuserView() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("getMessagesView");
		mav.addObject("getInput", new GetMessagesInput());
		return mav;
	}

	@RequestMapping(value = "/getMessagesForUser")
	public ModelAndView getMessagesForUser(
			@ModelAttribute("getInput") GetMessagesInput input,
			BindingResult result) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("messagesView");
		try {
			ArrayList<Message> messages = messageService
					.getAllMessagesForUser(input);
			if(pageLoadCounter != 0 ){
				for(int i=0;i<messages.size();i++){
					messages.get(i).setRead(true);
				}
			}
			mav.addObject("messages", messages);
			//mav.addObject("loadCount", pageLoadCounter);
			++pageLoadCounter;
		} catch (Exception ex) {
			mav.setViewName("error");
			logger.error(
					"Cannot retrieve messages for this user. Detailled error: \n",
					ex);
		}
		return mav;
	}
}
