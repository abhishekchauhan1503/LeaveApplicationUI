package com.abhishek.leaveapplicationUI.controllers;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import com.abhishek.leaveapplication.model.Message;
import com.abhishek.leaveapplicationservice.generatedclasses.CreateNewMessageInput;
import com.abhishek.leaveapplicationservice.generatedclasses.GetMessagesInput;
import com.abhishek.leaveapplicationservice.services.MessageService;

@Controller
public class MessageController {

	@Autowired
	private MessageService messageService;

	private ArrayList<Message> messagesList;

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

			mav.addObject("messages", messages);
			messagesList = messages;
		} catch (Exception ex) {
			mav.setViewName("error");
			logger.error(
					"Cannot retrieve messages for this user. Detailled error: \n",
					ex);
		}
		return mav;
	}

	@RequestMapping(value = "/messageDetails")
	public ModelAndView viewMessageDetails(@RequestParam("id") long id) {
		ModelAndView mav = new ModelAndView();
		if (messagesList == null) {
			mav.setViewName("error");
		}
		for (int i = 0; i < messagesList.size(); i++) {
			if (messagesList.get(i).getId() == id) {
				mav.addObject("message", messagesList.get(i));
				messagesList.get(i);
				if (!messagesList.get(i).isRead()) {
					CreateNewMessageInput messageToUpdate = new CreateNewMessageInput();
					messageToUpdate.setId(messagesList.get(i).getId());
					messageToUpdate.setRead(true);
					try {
						messageService.updateMessage(messageToUpdate);
					} catch (Exception ex) {
						logger.error(
								"ERROR: Cannot update message. Detailled error: \n: ",
								ex);
					}
				}
				break;
			}
			mav.setViewName("messageDetails");
		}
		return mav;
	}
}
