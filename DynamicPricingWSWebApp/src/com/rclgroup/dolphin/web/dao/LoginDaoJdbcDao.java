/*Copyright (c) 2020 RCL| All Rights Reserved  */
package com.rclgroup.dolphin.web.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
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
import com.rclgroup.dolphin.web.model.UserMod;

import oracle.jdbc.internal.OracleTypes;

/**
 * This class contain implementation of {@code UserSearchDao}
 * 
 * @author Cognis Solution
 *
 */
public final class LoginDaoJdbcDao extends RrcStandardDao implements LoginDao {

	private LoginProcedure loginProcedure;
	private ValidateTokenProcedure procedure;
	private int i = 0;

	protected void initDao() throws Exception {
		super.initDao();
		loginProcedure = new LoginProcedure(getJdbcTemplate(), new UserMapper());
		procedure = new ValidateTokenProcedure(getJdbcTemplate());

	}

	 
	
	@Override
	public UserMod loginUser(UserMod searchParam) throws DataAccessException {
		// TODO Auto-generated method stub
		List<UserMod> listOfUser = loginProcedure.getUserData(searchParam);
		if (listOfUser != null && listOfUser.size() >= 1) {
			searchParam.setStatus(true);
			searchParam.setUserId(listOfUser.get(0).getUserId());
			searchParam.setEmailId(listOfUser.get(0).getEmailId());
			searchParam.setFsc(listOfUser.get(0).getFsc());
		} else {
			searchParam.setStatus(false);
		}
		return searchParam;
	}

	@Override
	public boolean validateToeken(UserMod searchParam) throws DataAccessException {
		// TODO Auto-generated method stub
		List<String> list = procedure.getTokenData(searchParam);
		if(list != null && list.size()>0) {
			if(list.get(0).equals(searchParam.getPasswordToken())){
				return true;
			}
		}
		return false;
	}
	protected class LoginProcedure extends StoredProcedure {

		protected LoginProcedure(JdbcTemplate jdbcTemplate, RowMapper rowMapper) {
			super(jdbcTemplate, PCR_REQUEST_QUOTE_LOGIN);
			declareParameter(new SqlParameter("P_I_V_USER_ID", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("P_I_V_PASSWORD", OracleTypes.VARCHAR));

			declareParameter(new SqlOutParameter("P_O_V_SEARCH_LIST", OracleTypes.CURSOR, rowMapper));
		}

		/**
		 * 
		 * Used to call StoredProcedure.
		 * 
		 * @author Cognis Solution
		 *
		 */
		protected List<UserMod> getUserData(UserMod searchParam) {

			Map<String, String> inPara = new HashMap<>();
			inPara.put("P_I_V_USER_ID", searchParam.getEmailId());
			inPara.put("P_I_V_PASSWORD", searchParam.getPasswordToken());

			Map outMap = execute(inPara);
			System.out.println("   StoredProcedure executed Successfully...!   ");
			return (List<UserMod>) outMap.get("P_O_V_SEARCH_LIST");

		}

	}
	
	protected class ValidateTokenProcedure extends StoredProcedure {

		protected ValidateTokenProcedure(JdbcTemplate jdbcTemplate ) {
			super(jdbcTemplate, PCR_REQUEST_QUOTE_LOGIN_TOKEN);
			declareParameter(new SqlParameter("P_I_V_USER_ID", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("P_I_TOKEN", OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("P_O_V_SEARCH_LIST", OracleTypes.CURSOR, new RowMapper() {
				
				@Override
				public String mapRow(ResultSet rs, int arg1) throws SQLException {
				  return rs.getString("TOKEN");
					 
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
		protected List<String> getTokenData(UserMod searchParam) {

			Map<String, String> inPara = new HashMap<>();
			inPara.put("P_I_V_USER_ID", searchParam.getUserId());
			inPara.put("P_I_TOKEN", searchParam.getPasswordToken());

			Map outMap = execute(inPara);
			System.out.println("   StoredProcedure executed Successfully...!   ");
			return (List<String>) outMap.get("P_O_V_SEARCH_LIST");

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
			// model.setFromDate(DateUtils.formateDate(rs.getDate("RECORD_ADD_DATE")));
			model.setEmailId(rs.getString("EMAIL_ID1"));
			model.setFsc(rs.getString("PROPERTY_NAME"));

			return model;
		}
	}

}
