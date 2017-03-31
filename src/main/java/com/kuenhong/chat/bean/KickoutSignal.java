package com.kuenhong.chat.bean;

public class KickoutSignal {

	private Integer timeout;
	private String url;
	
	public Integer getTimeout() {
		return timeout;
	}
	public void setTimeout(Integer timeout) {
		this.timeout = timeout;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	@Override
	public String toString() {
		return String.format("KickoutSignal [timeout=%s, url=%s]", timeout, url);
	}
	
}
