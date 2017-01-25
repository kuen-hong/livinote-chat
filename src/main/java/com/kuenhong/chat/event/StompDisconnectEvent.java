package com.kuenhong.chat.event;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
public class StompDisconnectEvent implements ApplicationListener<SessionDisconnectEvent> {
	
	private Log LOG = LogFactory.getLog(StompDisconnectEvent.class);
	
	@Autowired
	SimpMessagingTemplate msgTemplate;
	@Autowired
	ActiveUserRepository activeUserRepository;

	@Override
	public void onApplicationEvent(SessionDisconnectEvent event) {
		StompHeaderAccessor headers = StompHeaderAccessor.wrap(event.getMessage());
		//LOG.info("in StompDisconnectEvent, headers="+headers);
		
		activeUserRepository.removeUser(headers.getSessionId());
		msgTemplate.convertAndSend("/topic/activeUsers", activeUserRepository.getActiveUsers());
	}

}