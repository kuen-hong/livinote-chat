package com.kuenhong.chat.bean;

public class ActiveUser {

	private String sessionId;
	private String userId;
	
	public ActiveUser() {
		
	}
	
	public ActiveUser(String sessionId, String userId) {
		super();
		this.sessionId = sessionId;
		this.userId = userId;
	}

	public String getSessionId() {
		return sessionId;
	}
	
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	
	public String getUserId() {
		return userId;
	}
	
	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		return String.format("ActiveUser [sessionId=%s, userId=%s]", sessionId, userId);
	}
}
