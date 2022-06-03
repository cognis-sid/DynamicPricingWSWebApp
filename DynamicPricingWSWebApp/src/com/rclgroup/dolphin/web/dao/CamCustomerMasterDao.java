/*Copyright (c) 2020 RCL| All Rights Reserved  */
package com.rclgroup.dolphin.web.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.rclgroup.dolphin.web.model.PropertyMod;
import com.rclgroup.dolphin.web.model.UserMod;

 
/**
 * Interface contain methods related to Search user.
 * @author Cognis Solution
 *
 */

public interface CamCustomerMasterDao {
	                    
	 
	  
	 
	public List<PropertyMod> getShipperList(UserMod mod)throws DataAccessException;
	
	public List<PropertyMod> getPod()throws DataAccessException;
	  
	}
	
   

