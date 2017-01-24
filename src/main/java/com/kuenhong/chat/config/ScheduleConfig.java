package com.kuenhong.chat.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.kuenhong.chat.event.ActiveUserRepository;

//@Configuration
@EnableScheduling
public class ScheduleConfig {
	
	@Autowired 
	ActiveUserRepository activeUserRepository;
	
	@Scheduled(fixedDelay = 10000)
	public void showActiveUsers() {
		activeUserRepository.getActiveSessions().forEach((k, v) -> {
			System.out.println("sessionId="+k+", userId=["+v+"]");
		});
	}
	
}
