package com.kuenhong.chat.event;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.kuenhong.chat.bean.ActiveUser;

public class ActiveUserRepository {

	private Map<String, String> activeSessions = new ConcurrentHashMap<>();
	
	public void addUser(String sessionId, String userId) {
		activeSessions.put(sessionId, userId);
	}
	
	public String getUser(String sessionId) {
		return activeSessions.get(sessionId);
	}
	
	public void removeUser(String sessionId) {
		activeSessions.remove(sessionId);
	}
	
	public List<ActiveUser> getActiveUsers() {
		List<ActiveUser> activeUsers = new ArrayList<>();
		activeSessions.forEach((k, v) -> {
			activeUsers.add(new ActiveUser(k, v));
		});
		return activeUsers;
	}

	public Map<String, String> getActiveSessions() {
		return activeSessions;
	}

	public void setActiveSessions(Map<String, String> activeSessions) {
		this.activeSessions = activeSessions;
	}
}
