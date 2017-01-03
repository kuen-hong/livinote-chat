package com.kuenhong.chat;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;

import com.kuenhong.chat.bean.Message;

/**
 * there are many kinds of events, 
 * @see http://docs.spring.io/spring-framework/docs/current/spring-framework-reference/html/websocket.html#websocket-stomp-appplication-context-events
 *
 */
@Component
public class StompConnectedEvent implements ApplicationListener<SessionConnectedEvent> {
	
	private Log LOG = LogFactory.getLog(StompConnectedEvent.class);
	
	@Autowired
	SimpMessagingTemplate msgTemplate;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void onApplicationEvent(SessionConnectedEvent event) {
		StompHeaderAccessor headers = StompHeaderAccessor.wrap(event.getMessage());
		LOG.info(headers);
		GenericMessage genericMsg = (GenericMessage)headers.getMessageHeaders().get("simpConnectMessage");
		Map<String, List<String>> nativeHeaders = (Map<String, List<String>>)genericMsg.getHeaders().get("nativeHeaders");
		// custom header
		String userId = nativeHeaders.get("userId").get(0);
		LOG.info(userId);
		
		msgTemplate.convertAndSend("/chatting/msg", new Message("", String.format("hello all, %s login.", userId)));
	}

}
