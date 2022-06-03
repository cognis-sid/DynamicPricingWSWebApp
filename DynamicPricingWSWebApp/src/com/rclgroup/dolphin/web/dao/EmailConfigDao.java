/*Copyright (c) 2020 RCL| All Rights Reserved  */
package com.rclgroup.dolphin.web.dao;

import org.springframework.dao.DataAccessException;

import com.rclgroup.dolphin.web.model.EmailConfigMod;

 
/**
 * Interface contain methods related to Search user.
 * @author Cognis Solution
 *
 */

public interface EmailConfigDao {
	                    
	public static final String PCR_REQUEST_QUOTE_EMAIL_CONFIG = "VASAPPS.PCR_REQUEST_QUOTE.PCR_REQUEST_QUOTE_EMAIL_CONFIG";
	
	  
	 
	public EmailConfigMod getEmailConfig() throws DataAccessException;
	

	 
	
	  
	}
	
   

