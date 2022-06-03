package com.rclgroup.dolphin.web.model;

import java.io.Serializable;

import com.rclgroup.dolphin.web.auth.BrowserData;

public class BaseInputMod implements Serializable {

	
	private static final long serialVersionUID = 1L;
	private String message;
	private boolean status;	
	protected BrowserData browserData;    
	private int seq;
	
	private String tokenId;
	private String passwordToken;
	 
	private String emailId;
	private String fsc;
	private boolean isMobileApp= false;
	private String userId;
	
	 
	public String getFsc() {
		return fsc;
	}
	public void setFsc(String fsc) {
		this.fsc = fsc;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getPasswordToken() {
		return passwordToken;
	}
	public void setPasswordToken(String passwordToken) {
		this.passwordToken = passwordToken;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public boolean isMobileApp() {
		return isMobileApp;
	}
	public void setMobileApp(boolean isMobileApp) {
		this.isMobileApp = isMobileApp;
	}
	public String getTokenId() {
		return tokenId;
	}

	public void setTokenId(String tokenId) {
		this.tokenId = tokenId;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public BrowserData getBrowserData() {
		return this.browserData;
	}

	public void setBrowserData(BrowserData browserData) {
		this.browserData = browserData;
	}

	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}

	
	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "BaseInputMod [browserData=" + browserData + ", seq=" + seq + "]";
	}
	
}
