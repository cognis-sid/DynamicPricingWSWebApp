/*Copyright (c) 2020 RCL| All Rights Reserved  */
package com.rclgroup.dolphin.web.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;

import com.rclgroup.dolphin.web.common.RrcStandardDao;
import com.rclgroup.dolphin.web.dao.GetPortDataJdbcDao.PortDataRowMapper;
import com.rclgroup.dolphin.web.model.PortMod;
import com.rclgroup.dolphin.web.model.PropertyMod;
import com.rclgroup.dolphin.web.model.UserMod;
import com.rclgroup.dolphin.web.util.RutString;

import oracle.jdbc.internal.OracleTypes;

/**
 * This class contain implementation of {@code UserSearchDao}
 * 
 * @author Cognis Solution
 *
 */
public final class LoginDaoJdbcDao extends RrcStandardDao implements LoginDao {

	 
	 private int i = 0;

	protected void initDao() throws Exception {
		super.initDao();
	 
	}

	 
	
	@Override
	public UserMod loginUser(UserMod searchParam) throws DataAccessException {
		// TODO Auto-generated method stub
		 

		 List<UserMod> valueList = new ArrayList<UserMod>();
	        
		 valueList = (List<UserMod>) getJdbcTemplate().query(
				 PCR_LOGIN_SQL,new Object[]{ searchParam.getUserId(),searchParam.getPass()},new UserMapper());
	       
	     if(valueList.size()==0) {
	    	 return null;
	     }
	             
	     return valueList.get(0);
	
	
		 
	}

	@Override
	public boolean validateToeken(UserMod searchParam) throws DataAccessException {
		// TODO Auto-generated method stub
	 
		 List<UserMod> valueList = new ArrayList<UserMod>();
	        
		 valueList = (List<UserMod>) getJdbcTemplate().query(
				 PCR_LOGIN_TOEKN_SQL,new Object[]{ searchParam.getUserId(),searchParam.getTokenId()},new UserMapper());
	       
	     if(valueList.size()==0) {
	    	 return false;
	     }
	             
	     return true;
	
	
	 
	}


	class UserMapper implements RowMapper {

		/**
		 * Map each row of data in the ResultSet.
		 * 
		 * @param rs
		 *            {@code ResultSet }
		 * @param rowNum
		 *            the number of the current row
		 * @return the result object for the current row
		 * @throws SQLException
		 */

		public UserMod mapRow(ResultSet rs, int rowNum) throws SQLException {
			i++;
			UserMod model = new UserMod();
			model.setSeq((i));
			model.setUserId(rs.getString("USER_ID"));
			model.setUserName(rs.getString("user_name"));
			// model.setFromDate(DateUtils.formateDate(rs.getDate("RECORD_ADD_DATE")));
			model.setEmailId(rs.getString("email_id"));
			model.setFsc(rs.getString("PROPERTY_NAME"));

			return model;
		}
	}

}
