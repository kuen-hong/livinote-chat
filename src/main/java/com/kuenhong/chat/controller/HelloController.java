package com.kuenhong.chat.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kuenhong.chat.bean.ActiveUser;
import com.kuenhong.chat.bean.Message;
import com.kuenhong.chat.event.ActiveUserRepository;

@Controller
public class HelloController {
	
	private final Log LOG = LogFactory.getLog(HelloController.class);
	
	@Autowired
	SimpMessagingTemplate msgTemplate;
	@Autowired
	ActiveUserRepository activeUserRepository;

	@GetMapping("/hello")
	public String indexPage(Model model, @RequestParam(value = "id", required = false) String id) {
		model.addAttribute("name", "Jackson");
		model.addAttribute("books", Arrays.asList("Spring 4", "Spring Boot 1.4.0", "Websocket"));
		/*
		if (id != null) {
			// maybe because it doesn't connect to websocket
			System.out.println("testing, id="+id);
			SimpMessageHeaderAccessor headerAccessor = SimpMessageHeaderAccessor.create(SimpMessageType.MESSAGE);
			headerAccessor.setSessionId(id);
			headerAccessor.setLeaveMutable(true);
			msgTemplate.convertAndSendToUser(id , "/chatting/msg", new Message("", "hi, "+id), headerAccessor.getMessageHeaders());
		}
		*/
		msgTemplate.convertAndSend("/chatting/msg", new Message("", "system page, hello"));
		return "index";
	}
	
	@ResponseBody
	@PostMapping("/postHello")
	public Map<String, Object> postHello(@RequestBody Message msg) {
		Map<String, Object> map = new HashMap<>();
		map.put("status", Boolean.TRUE);
		System.out.println(msg);
		SimpMessageHeaderAccessor headerAccessor = SimpMessageHeaderAccessor.create(SimpMessageType.MESSAGE);
		headerAccessor.setSessionId(msg.getUserId());
		headerAccessor.setLeaveMutable(true);
		//msgTemplate.convertAndSendToUser(msg.getUserId() , "/chatting/specUser", new Message("", "hi, "+msg.getUserId()), headerAccessor.getMessageHeaders());
		msgTemplate.convertAndSendToUser(msg.getUserId(), "/queue/specUser", new Message("", "fuck you message."));
		return map;
	}
	
	@SendTo("/topic/activeUsers")
	public List<ActiveUser> getConnectedUsers() {
		LOG.info("execute getConnectedUsers");
		return activeUserRepository.getActiveUsers();
	}
	
}
