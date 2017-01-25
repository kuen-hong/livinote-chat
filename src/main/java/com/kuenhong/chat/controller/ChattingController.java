package com.kuenhong.chat.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import com.kuenhong.chat.bean.Message;

@Controller
public class ChattingController {
	
	private final Log LOG = LogFactory.getLog(ChattingController.class);

	@MessageMapping("/typing")
	@SendTo("/chatting/msg")
	public Message receiveMsg(@Payload Message msg) {
		return msg;
	}
}
