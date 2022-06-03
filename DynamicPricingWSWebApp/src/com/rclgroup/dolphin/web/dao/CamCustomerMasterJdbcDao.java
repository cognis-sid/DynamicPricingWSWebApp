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
import org.springframework.jdbc.core.RowMapper;

import com.rclgroup.dolphin.web.common.RrcStandardDao;
import com.rclgroup.dolphin.web.model.PropertyMod;
import com.rclgroup.dolphin.web.model.UserMod;
import com.rclgroup.dolphin.web.util.RutString;

/**
 * This class contain implementation of {@code UserSearchDao}
 * 
 * @author Cognis Solution
 *
 */
public final class CamCustomerMasterJdbcDao extends RrcStandardDao implements CamCustomerMasterDao {

	 

	protected void initDao() throws Exception {
		super.initDao();
		 
	}
	
	@Override
	public List<PropertyMod> getPod() throws DataAccessException {
		 List<PropertyMod> valueList = new ArrayList<PropertyMod>();
	        
	        StringBuffer sql = new StringBuffer();
	        sql.append(" select code,name from country_code order by  code ");
	       
	         
	            valueList = (List<PropertyMod>) getNamedParameterJdbcTemplate().query(
	                sql.toString(),
	                Collections.EMPTY_MAP,
	                new RowMapper() {
	                    public PropertyMod mapRow(ResultSet rs, int rowNum) throws SQLException {
	                    	PropertyMod mod = new PropertyMod();
	                    	mod.setText(RutString.nullToStr(rs.getString("code")));
	                    	mod.setValue(RutString.nullToStr(rs.getString("name")));
	                        return mod;
	                    }
	                } 
	            );
	         
	     return valueList;
	
	}
	@Override
	public List<PropertyMod> getShipperList(UserMod mod) throws DataAccessException {
		  List<PropertyMod> valueList = new ArrayList<PropertyMod>();
	        System.out.println("FSC----->"+mod.getFsc());
	        
	        StringBuffer sql = new StringBuffer();
	        sql.append(" select  pk_customer_code, customer_name from cam_customer where UPPER(fk_fsc) =:fk_fsc and  (UPPER(pk_customer_code) like '%"+mod.getCompanyName().toUpperCase()+"%' or UPPER(customer_name) like '%"+mod.getCompanyName().toUpperCase()+"%'  )  order by customer_name  ");
	       System.out.println(sql.toString().toUpperCase());
	        Map<String, String> paramMap =new HashMap<String, String>();
	        paramMap.put("fk_fsc", mod.getFsc());
	         
	            valueList = (List<PropertyMod>) getNamedParameterJdbcTemplate().query(
	                sql.toString(),
	                paramMap,
	                new RowMapper() {
	                    public PropertyMod mapRow(ResultSet rs, int rowNum) throws SQLException {
	                    	PropertyMod mod = new PropertyMod();
	                    	mod.setText(RutString.nullToStr(rs.getString("customer_name")));
	                    	mod.setValue(RutString.nullToStr(rs.getString("pk_customer_code")));
	                    	mod.setId(RutString.nullToStr(rs.getString("pk_customer_code")));
	                        return mod;
	                    }
	                } 
	            );
	         
	     return valueList;
	
	 
	}
 
}
