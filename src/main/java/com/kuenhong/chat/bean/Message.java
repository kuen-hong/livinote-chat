package com.kuenhong.chat.bean;

public class Message {

	private String userId;
	private String msg;
	
	public String getUserId() {
		return userId;
	}
	
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public String getMsg() {
		return msg;
	}
	
	public void setMsg(String msg) {
		this.msg = msg;
	}

	@Override
	public String toString() {
		return String.format("Message [userId=%s, msg=%s]", userId, msg);
	}
	
}
