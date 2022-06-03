/*Copyright (c) 2020 RCL| All Rights Reserved  */
package com.rclgroup.dolphin.web.dao;

/* --------------------Change Log -------------------------------
 	##   DD/MM/YY       -User-                     -TaskRef-            -ShortDescription-
	1    24/09/20       chandrabhan harode           Initial Version
   -------------------------------------------------------------------
*/

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.rclgroup.dolphin.web.model.PropertyMod;
import com.rclgroup.dolphin.web.model.UserMod;

 
/**
 * Interface contain methods related to Search user.
 * @author Cognis Solution
 *
 */

public interface UserSearchDao {
	                    
	public static final String PCR_REQUEST_QUOTE_SEARCH_USER = "VASAPPS.PCR_REQUEST_QUOTE.PCR_REQUEST_QUOTE_SEARCH_USER";
	
	public static final String PCR_REQUEST_QUOTE_USER_ASSIGNMENT= "VASAPPS.PCR_REQUEST_QUOTE.PCR_REQUEST_QUOTE_USER_ASSIGNMENT";
	public static String PCR_REQUEST_DELETE_QUOTE_USER ="VASAPPS.PCR_REQUEST_QUOTE.PCR_REQUEST_DELETE_QUOTE_USER";
	
	public static final String PCR_REQUEST_QUOTE_GET_FSC = "VASAPPS.PCR_REQUEST_QUOTE.PCR_REQUEST_QUOTE_GET_FSC";
	public static final String PCR_REQUEST_QUOTE_GET_PORT = "VASAPPS.PCR_REQUEST_QUOTE.PCR_REQUEST_QUOTE_GET_PORT";
	public List<UserMod> findData(UserMod searchParam) throws DataAccessException;
	

	public String createUserData(UserMod searchParam) throws DataAccessException;
	
	public void deleteUser(UserMod searchParam) throws DataAccessException;
	
	public UserMod getById(UserMod userMod) throws DataAccessException;
	
	public List<PropertyMod> getFSC() throws DataAccessException;
	public List<PropertyMod> getPortC() throws DataAccessException;
	 
	}
	
   

