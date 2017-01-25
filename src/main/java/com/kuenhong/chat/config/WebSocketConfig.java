package com.kuenhong.chat.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

import com.kuenhong.chat.event.ActiveUserRepository;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig extends AbstractWebSocketMessageBrokerConfigurer {
	
	@Override
	public void configureMessageBroker(MessageBrokerRegistry registry) {
		/**
		 * for destinations to be path-like strings where "/topic/.." implies publish-subscribe (one-to-many)
		 * and "/queue/" implies point-to-point (one-to-one) message exchanges.
		 */
		registry.enableSimpleBroker("/chatting", "/topic", "/queue");
		registry.setApplicationDestinationPrefixes("/app");
		registry.setUserDestinationPrefix("/user");
	}

	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		registry.addEndpoint("/chat").withSockJS();
	}
	
//	@Bean
//    public PresenceChannelInterceptor presenceChannelInterceptor() {
//        return new PresenceChannelInterceptor();
//    }
//	
//	@Override
//    public void configureClientInboundChannel(ChannelRegistration registration) {
//        registration.setInterceptors(presenceChannelInterceptor());
//    }
// 
//    @Override
//    public void configureClientOutboundChannel(ChannelRegistration registration) {
//        registration.taskExecutor().corePoolSize(8);
//        registration.setInterceptors(presenceChannelInterceptor());
//    }
    
    @Bean(name = "activeUserRepository")
	public ActiveUserRepository activeUserRepository() {
		return new ActiveUserRepository();
	}

}
