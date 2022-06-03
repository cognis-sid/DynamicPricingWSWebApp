/*Copyright (c) 2020 RCL| All Rights Reserved  */
package com.rclgroup.dolphin.web.dao;

import org.springframework.dao.DataAccessException;

import com.rclgroup.dolphin.web.model.UserMod;

 
/**
 * Interface contain methods related to Search user.
 * @author Cognis Solution
 *
 */

public interface LoginDao {
	                    
	public static final String PCR_REQUEST_QUOTE_LOGIN = "VASAPPS.PCR_REQUEST_QUOTE.PCR_REQUEST_QUOTE_LOGIN";
	
	public static final String PCR_REQUEST_QUOTE_USER_ASSIGNMENT= "VASAPPS.PCR_REQUEST_QUOTE.PCR_REQUEST_QUOTE_USER_ASSIGNMENT";
	
	public static final String PCR_REQUEST_QUOTE_LOGIN_TOKEN = "VASAPPS.PCR_REQUEST_QUOTE.PCR_REQUEST_QUOTE_LOGIN_TOKEN";
	
	 
	public UserMod loginUser(UserMod searchParam) throws DataAccessException;
	
	public boolean validateToeken(UserMod searchParam) throws DataAccessException;
	
	
	  
	}
	
   

