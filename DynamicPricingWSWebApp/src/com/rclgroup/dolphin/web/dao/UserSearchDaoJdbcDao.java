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
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;

import com.rclgroup.dolphin.web.common.RrcStandardDao;
import com.rclgroup.dolphin.web.model.PropertyMod;
import com.rclgroup.dolphin.web.model.UserMod;
import com.rclgroup.dolphin.web.util.RutString;
import com.rclgroup.dolphin.web.utils.DateUtils;

import oracle.jdbc.internal.OracleTypes;

/**
 * This class contain implementation of {@code UserSearchDao}
 * 
 * @author Cognis Solution
 *
 */
public final class UserSearchDaoJdbcDao extends RrcStandardDao implements UserSearchDao {
	 
	private SearchProcedure searchProcedure;	
	private CreateUserProcedure createUserProcedure;
	private DeleteProcedure deleteProcedure;
	private FSCProcedure fscProcedure;
	private PortProcedure portProcedure;
	private int i=0;
	
	protected void initDao() throws Exception {
		super.initDao();
		searchProcedure = new SearchProcedure(getJdbcTemplate(),new UserMapper());		 
		createUserProcedure = new CreateUserProcedure(getJdbcTemplate());	
		deleteProcedure =new DeleteProcedure (getJdbcTemplate());
		fscProcedure = new FSCProcedure(getJdbcTemplate());
		portProcedure = new PortProcedure(getJdbcTemplate());
	}
	
	@Override
	public void deleteUser(UserMod searchParam) throws DataAccessException {
		deleteProcedure.deleteUser(searchParam);
	}
	
	 
	
	@Override
	public UserMod getById(UserMod userMod)
			throws DataAccessException {
		 
		   List<String> valueList = new ArrayList<String>();
	        
	        StringBuffer sql = new StringBuffer();
	        sql.append("select PROPERTY_NAME FROM ");
	        sql.append("QUOTE_USER_ASSIGNMENT_ROLE where USER_ID_FK= :USER_ID");
	        Map<String, String> paramMap = Collections.singletonMap("USER_ID", userMod.getUserId());
	        try {
	            valueList = (List<String>) getNamedParameterJdbcTemplate().queryForList(
	                sql.toString(),
	                paramMap,
	                String.class
	            );
	        } catch (EmptyResultDataAccessException e) {
	             
	        }
	        
	      String pro = null;
	      for(String val :valueList) {
	    	  if(pro == null) {
	    		  pro=val;
	    	  }else {
	    		  pro+=","+val;
	    	  }
	      }
	        
	    userMod.setPropertyName(pro);     
	    
		return userMod;
	}
	
	public List<UserMod> findData(UserMod searchParam){
		this.i=0;
		return searchProcedure.getSearchData(searchParam);
	}
	
	@Override
	public String createUserData(UserMod searchParam) throws DataAccessException {
		 
		return createUserProcedure.createUserData(searchParam);
	}
	


	@Override
	public List<PropertyMod> getFSC() throws DataAccessException {
		// TODO Auto-generated method stub
		return fscProcedure.getAllFSC();
	}

	@Override
	public List<PropertyMod> getPortC() throws DataAccessException {
		// TODO Auto-generated method stub
		return portProcedure.getPorts();
	}
	protected class CreateUserProcedure extends StoredProcedure {

		protected CreateUserProcedure(JdbcTemplate jdbcTemplate) {
			super(jdbcTemplate, PCR_REQUEST_QUOTE_USER_ASSIGNMENT);
			declareParameter(new SqlParameter("P_I_V_USER_ID", OracleTypes.VARCHAR));                              
			declareParameter(new SqlParameter("P_I_V_ROLE_NAME", OracleTypes.VARCHAR));                             
			declareParameter(new SqlParameter("P_I_V_PRO_NAME", OracleTypes.VARCHAR));                             
			declareParameter(new SqlParameter("P_I_V_LOGIN_ID", OracleTypes.VARCHAR)); 
			declareParameter(new SqlOutParameter("P_O_V_MAPPING_LIST", OracleTypes.VARCHAR));
		}

		/**
		 * 
		 * Used to call StoredProcedure.
		 * 
		 * @author Cognis Solution
		 *
		 */
		protected String createUserData(UserMod searchParam) {
			
			Map<String,String> inPara = new HashMap<>();		
			inPara.put("P_I_V_USER_ID", searchParam.getUserId());
			inPara.put("P_I_V_ROLE_NAME", searchParam.getRoleId());
			inPara.put("P_I_V_PRO_NAME", searchParam.getPropertyName());
			inPara.put("P_I_V_LOGIN_ID", searchParam.getBrowserData().getUserId());			
			Map outMap = execute(inPara);
			System.out.println("   StoredProcedure executed Successfully...!   ");
			return (String) outMap.get("P_O_V_MAPPING_LIST");

		}

	}
	

	
	protected class SearchProcedure extends StoredProcedure {

		protected SearchProcedure(JdbcTemplate jdbcTemplate, RowMapper rowMapper) {
			super(jdbcTemplate, PCR_REQUEST_QUOTE_SEARCH_USER);
			declareParameter(new SqlParameter("P_I_V_USER_ID", OracleTypes.VARCHAR));                              
			declareParameter(new SqlParameter("P_I_V_ROLE_NAME", OracleTypes.VARCHAR));                             
			declareParameter(new SqlParameter("P_I_V_EMAIL", OracleTypes.VARCHAR));                             
			declareParameter(new SqlParameter("P_I_V_PHONE", OracleTypes.VARCHAR));                             
			declareParameter(new SqlParameter("P_I_V_LOCATION", OracleTypes.VARCHAR));                             
			declareParameter(new SqlParameter("P_I_V_FROM_DATE", OracleTypes.VARCHAR));                             
			declareParameter(new SqlParameter("P_I_V_TO_DATE", OracleTypes.VARCHAR));  			
			declareParameter(new SqlOutParameter("P_O_V_SEARCH_LIST", OracleTypes.CURSOR, rowMapper));
		}

		/**
		 * 
		 * Used to call StoredProcedure.
		 * 
		 * @author Cognis Solution
		 *
		 */
		protected List<UserMod> getSearchData(UserMod searchParam) {
			
			Map<String,String> inPara = new HashMap<>();		
			inPara.put("P_I_V_USER_ID", searchParam.getUserId());
			inPara.put("P_I_V_ROLE_NAME", searchParam.getRoleSearch());
			inPara.put("P_I_V_EMAIL", searchParam.getEmailId());
			inPara.put("P_I_V_PHONE", searchParam.getPhoneNumber());
			inPara.put("P_I_V_LOCATION", searchParam.getLocation());			
			inPara.put("P_I_V_FROM_DATE", DateUtils.getDateFromDefaultDateStringYYYYMMDD(searchParam.getFromDate()));
			inPara.put("P_I_V_TO_DATE", DateUtils.getDateFromDefaultDateStringYYYYMMDD(searchParam.getToDate()));
			
			Map outMap = execute(inPara);
			System.out.println("   StoredProcedure executed Successfully...!   ");
			return (List<UserMod>) outMap.get("P_O_V_SEARCH_LIST");

		}

	}
	
	 
	
	protected class DeleteProcedure extends StoredProcedure {

		protected DeleteProcedure(JdbcTemplate jdbcTemplate) {
			super(jdbcTemplate, PCR_REQUEST_DELETE_QUOTE_USER);
			declareParameter(new SqlParameter("P_I_V_USER_ID", OracleTypes.VARCHAR));                              
			 
		}

		/**
		 * 
		 * Used to call StoredProcedure.
		 * 
		 * @author Cognis Solution
		 *
		 */
		protected void deleteUser(UserMod searchParam) {
			
			Map<String,String> inPara = new HashMap<>();		
			inPara.put("P_I_V_USER_ID", searchParam.getUserId());			 
			Map outMap = execute(inPara);
			System.out.println("   StoredProcedure executed Successfully...!   ");
			

		}

	}

	

	protected class FSCProcedure extends StoredProcedure {

		protected FSCProcedure(JdbcTemplate jdbcTemplate) {
			super(jdbcTemplate, PCR_REQUEST_QUOTE_GET_FSC);			  			
			declareParameter(new SqlOutParameter("P_O_V_SEARCH_LIST", OracleTypes.CURSOR, new RowMapper() {
				
				@Override
				public PropertyMod mapRow(ResultSet rs, int arg1) throws SQLException {
					PropertyMod mod = new PropertyMod();
					mod.setText(rs.getString("FSC_DESCRIPTION"));
					mod.setValue(rs.getString("PK_FSC_CODE"));
					return mod;
				}
			}));
		}

		/**
		 * 
		 * Used to call StoredProcedure.
		 * 
		 * @author Cognis Solution
		 *
		 */
		protected List<PropertyMod> getAllFSC() {
			
			Map<String,String> inPara = new HashMap<>();		
			 
			Map outMap = execute(inPara);
			System.out.println("   StoredProcedure executed Successfully...!   ");
			return (List<PropertyMod>) outMap.get("P_O_V_SEARCH_LIST");

		}

	}
	

	
	protected class PortProcedure extends StoredProcedure {

		protected PortProcedure(JdbcTemplate jdbcTemplate) {
			super(jdbcTemplate, PCR_REQUEST_QUOTE_GET_PORT);			  			
			declareParameter(new SqlOutParameter("P_O_V_SEARCH_LIST", OracleTypes.CURSOR, new RowMapper() {
				
				@Override
				public PropertyMod mapRow(ResultSet rs, int arg1) throws SQLException {
					PropertyMod mod = new PropertyMod();
					mod.setText(rs.getString("DESCRIPTION"));
					mod.setValue(rs.getString("PORT_CODE"));
					return mod;
				}
			}));
		}

		/**
		 * 
		 * Used to call StoredProcedure.
		 * 
		 * @author Cognis Solution
		 *
		 */
		protected List<PropertyMod> getPorts() {
			
			Map<String,String> inPara = new HashMap<>();		
			 
			Map outMap = execute(inPara);
			System.out.println("   StoredProcedure executed Successfully...!   ");
			return (List<PropertyMod>) outMap.get("P_O_V_SEARCH_LIST");

		}

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
			model.setUserName(rs.getString("USER_NAME"));
			model.setCompanyName(rs.getString("COMPANY_NAME"));
			model.setCountryName(rs.getString("COUNTRY"));
			model.setPhoneNumber(rs.getString("PHONE"));
			model.setRoleId(rs.getString("ROLE_NAME"));
			model.setFromDate(DateUtils.formateDateWithTime(rs.getDate("RECORD_ADD_DATE")));
			model.setEmailId(rs.getString("EMAIL_ID1"));
			model.setLoginUserId(rs.getString("RECORD_ADD_USER"));
			model.setFsc(rs.getString("FSC"));
			if("3".equals(rs.getString("ROLE_NAME"))){
				model.setFsc(rs.getString("FSC")+"...");
			}else if("4".equals(rs.getString("ROLE_NAME"))){
					if("AllAbove".equals(model.getFsc())) {
						model.setFsc("Create user and Approve Request");
					}else if("Approve".equals(model.getFsc())) {
						model.setFsc("Appove Request");
					}else if("Create".equals(model.getFsc())) {
						model.setFsc("Create user");
					}
				
			}
			return model;
		}
	}







}
