package com.kuenhong.chat.controller;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.kuenhong.chat.bean.ActiveUser;
import com.kuenhong.chat.bean.Message;
import com.kuenhong.chat.event.ActiveUserRepository;

@Controller
public class MonitorController {
	
	private final Log LOG = LogFactory.getLog(MonitorController.class);
	
	@Autowired
	SimpMessagingTemplate msgTemplate;
	@Autowired
	ActiveUserRepository activeUserRepository;

	@GetMapping("/monitor")
	public String indexPage(Model model, @RequestParam(value = "id", required = false) String id) {
		model.addAttribute("name", "Jackson");
		model.addAttribute("books", Arrays.asList("Spring 4", "Spring Boot 1.4.0", "Websocket"));
		return "monitor";
	}
	
	@SendTo("/topic/activeUsers")
	public List<ActiveUser> getConnectedUsers() {
		LOG.info("execute getConnectedUsers");
		return activeUserRepository.getActiveUsers();
	}
	
	@MessageMapping("/chat.private.{sessionId}")
	public void sendToSomeone(@Payload Message msg, @DestinationVariable("sessionId") String sessionId) {
		msgTemplate.convertAndSend("/queue/chat.message-user"+sessionId, msg);
	}
}
