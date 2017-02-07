package com.kuenhong.chat.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
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
	
	@ResponseBody
	@PostMapping("/existOrNot")
	public Map<String, Object> checkNicknameInRepository(@RequestBody ActiveUser joinUser, HttpSession session) {
		Map<String, Object> returnMap = new HashMap<>();
		returnMap.put("status", Boolean.FALSE);
		for (Map.Entry<String, String> entry : activeUserRepository.getActiveSessions().entrySet()) {
			if (entry.getValue().equals(joinUser.getUserId())) {
				returnMap.put("status", Boolean.TRUE);
				break;
			}
		}
		if (returnMap.get("status").equals(Boolean.FALSE)) {
			session.setAttribute("userId", joinUser.getUserId());
		}
		return returnMap;
	}
	
	@SubscribeMapping("/activeUsers")
	public List<ActiveUser> getConnectedUsersWhenSubscribe() {
		LOG.info("execute getConnectedUsersWhenSubscribe");
		return activeUserRepository.getActiveUsers();
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
