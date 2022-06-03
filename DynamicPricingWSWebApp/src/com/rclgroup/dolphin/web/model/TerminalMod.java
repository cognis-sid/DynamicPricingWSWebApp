package com.rclgroup.dolphin.web.model;

public class TerminalMod {
	private String terminal_code;
	private String terminal_name;
	
	public String getTerminal_code() {
		return terminal_code;
	}
	public void setTerminal_code(String terminal_code) {
		this.terminal_code = terminal_code;
	}
	public String getTerminal_name() {
		return terminal_name;
	}
	public void setTerminal_name(String terminal_name) {
		this.terminal_name = terminal_name;
	}
	@Override
	public String toString() {
		return "TerminalMod [terminal_code=" + terminal_code + ", terminal_name=" + terminal_name + "]";
	}
	

}
