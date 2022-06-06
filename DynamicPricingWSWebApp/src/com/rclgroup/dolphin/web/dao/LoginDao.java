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
	                    
	public static final String PCR_LOGIN_SQL = "select user_name,email_id,user_role,token,user_id,password from rcltbls.user_login_dyna where user_id=? and password=?";
	
	
	public static final String PCR_LOGIN_TOEKN_SQL = "select user_name,email_id,user_role,token,user_id,password from rcltbls.user_login_dyna where user_id=? and token=?";
	
	public UserMod loginUser(UserMod searchParam) throws DataAccessException;
	
	public boolean validateToeken(UserMod searchParam) throws DataAccessException;
	
	
	  
	}
	
   

