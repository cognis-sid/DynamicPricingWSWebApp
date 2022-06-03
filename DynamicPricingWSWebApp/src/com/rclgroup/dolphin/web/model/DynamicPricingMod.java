package com.rclgroup.dolphin.web.model;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;
@XmlRootElement
public class DynamicPricingMod {
	private String userId;
	private String fsc;
	private List<PortMod> portdata;
	private List<TerminalMod> terminaldata;
	
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getFsc() {
		return fsc;
	}
	public void setFsc(String fsc) {
		this.fsc = fsc;
	}
	public List<PortMod> getPortdata() {
		return portdata;
	}
	public void setPortdata(List<PortMod> portdata) {
		this.portdata = portdata;
	}
	public List<TerminalMod> getTerminaldata() {
		return terminaldata;
	}
	public void setTerminaldata(List<TerminalMod> terminaldata) {
		this.terminaldata = terminaldata;
	}
	@Override
	public String toString() {
		return "DynamicPricingMod [userId=" + userId + ", fsc=" + fsc + ", portdata=" + portdata + ", terminaldata="
				+ terminaldata + "]";
	}
	

}
