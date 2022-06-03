package com.rclgroup.dolphin.web.model;

import java.util.List;

public class ResponseMod<T> {
	private List<T> data;
	private boolean status;

	public ResponseMod(List<T> data, boolean status) {
		this.data = data;
		this.status = status;
	}

	public List<T> getData() {
		return data;
	}

	public boolean isStatus() {
		return status;
	}

}
