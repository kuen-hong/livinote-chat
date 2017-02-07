package com.kuenhong.chat.controller;

import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.kuenhong.chat.bean.Message;

@Controller
public class ChattingController {
	
	private final Log LOG = LogFactory.getLog(ChattingController.class);
	
	@GetMapping("/chatting")
	public String chatPage(Model model, HttpSession session) {
		String userId = (String)session.getAttribute("userId");
		model.addAttribute("userId", userId);
		model.addAttribute("isAdmin", userId.startsWith("admin"));
		return "chatting";
	}

	@MessageMapping("/typing")
	@SendTo("/chatting/msg")
	public Message receiveMsg(@Payload Message msg) {
		return msg;
	}
}
