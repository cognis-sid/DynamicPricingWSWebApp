/*Copyright (c) 2020 RCL| All Rights Reserved  */
package com.rclgroup.dolphin.web.model;

/* --------------------Change Log -------------------------------
 	##   DD/MM/YY       -User-              -TaskRef-            -ShortDescription-
	1    01/04/20       Sushil            Initial Version
   -------------------------------------------------------------------
*/
/**
 * Used to send error detail message as response to Rest Api
 * 
 * @author Cognis Solution
 */
public class ErrorMod {
	/**
	 * Error Code like 404 or 500
	 */
	private int errorCode;
	/**
	 * Detail message root cause of exception.
	 */
	private String errorMessage;
	/**
	 * Status like failed or any other
	 */
	private String status;
	/**
	 * Page name where exception occur. 
	 */
	private String pageName;

	public ErrorMod(int errorCode, String errorMessage, String status, String pageName) {
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
		this.status = status;
		this.pageName = pageName;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getPageName() {
		return pageName;
	}

	public void setPageName(String pageName) {
		this.pageName = pageName;
	}

}
