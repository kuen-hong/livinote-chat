package com.kuenhong.chat;

import java.security.Principal;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptorAdapter;
import org.springframework.messaging.support.MessageHeaderAccessor;

public class PresenceChannelInterceptor extends ChannelInterceptorAdapter {

	private final Log LOGGER = LogFactory.getLog(PresenceChannelInterceptor.class);
	
	@Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
//		StompHeaderAccessor accessor = 
//				MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
//		
//		if (StompCommand.CONNECT.equals(accessor.getCommand())) {
//			Principal user = ... ; // access authentication header(s)
//			accessor.setUser(user);
//		}

		return message;
	}
	
	@Override
    public void postSend(Message<?> message, MessageChannel channel, boolean sent) {
 
        StompHeaderAccessor sha = StompHeaderAccessor.wrap(message);
 
        // ignore non-STOMP messages like heartbeat messages
        if (sha.getCommand() == null) {
            return;
        }
 
        String sessionId = sha.getSessionId();
 
        switch(sha.getCommand()) {
            case CONNECT:
            	LOGGER.info("STOMP Connect [sessionId: " + sessionId + "]");
                break;
            case CONNECTED:
            	LOGGER.info("STOMP Connected [sessionId: " + sessionId + "]");
                break;
            case SUBSCRIBE:
            	LOGGER.info("STOMP Subcribe [sessionId: " + sessionId + "]");
            	break;
            case DISCONNECT:
            	LOGGER.info("STOMP Disconnect [sessionId: " + sessionId + "]");
                break;
            default:
                break;
 
        }
    }
}
