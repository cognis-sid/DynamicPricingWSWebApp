package com.rclgroup.dolphin.web.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class PropertyMod  {
	private String id;
	private String text;
	private String value;

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	
}
