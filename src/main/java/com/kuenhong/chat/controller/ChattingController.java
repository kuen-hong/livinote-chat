package com.kuenhong.chat.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import com.kuenhong.chat.bean.Message;

@Controller
public class ChattingController {

	@MessageMapping("/typing")
	@SendTo("/chatting/msg")
	public Message receiveMsg(Message msg) {
		return msg;
	}
}
