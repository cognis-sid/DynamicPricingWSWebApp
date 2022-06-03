/*Copyright (c) 2020 RCL| All Rights Reserved  */
package com.rclgroup.dolphin.web.dao;

import org.springframework.dao.DataAccessException;

import com.rclgroup.dolphin.web.model.DynamicPricingMod;
import com.rclgroup.dolphin.web.model.PortMod;
import com.rclgroup.dolphin.web.model.UserMod;

 
/**
 * Interface contain methods related to Search user.
 * @author Cognis Solution
 *
 */

public interface GetPortDataDao {
	                    
	public static final String get_port_data="select pk_port_code , port_name from rcltbls.cam_port where fk_fsc in (select fsc_code from rcltbls.esm_user_login where user_id=?)"; 
	public static final String get_terminal_data="select pk_terminal,terminal_name from rcltbls.cam_terminal_depot where fk_port_code=?"; 
			
	public DynamicPricingMod portData(DynamicPricingMod searchParam) throws DataAccessException;
	
	public DynamicPricingMod terminalData(PortMod searchParam) throws DataAccessException;
	
	
	  
	}
	
   

