package com.kuenhong.chat.controller;

import java.security.Principal;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;

import com.kuenhong.chat.bean.Message;

@Controller
public class ChattingController {
	
	private final Log LOG = LogFactory.getLog(ChattingController.class);

	@MessageMapping("/typing")
	@SendTo("/chatting/msg")
	public Message receiveMsg(Message msg, SimpMessageHeaderAccessor headerAccessor, Principal principal) {
		LOG.info(principal.getName());
		LOG.info(headerAccessor);
		return msg;
	}
	
	@SubscribeMapping("/specUser")
	public Message sendToSpecUser() {
		LOG.info("in sendToSpecUser");
		return new Message("", "hello,");
	}
}
