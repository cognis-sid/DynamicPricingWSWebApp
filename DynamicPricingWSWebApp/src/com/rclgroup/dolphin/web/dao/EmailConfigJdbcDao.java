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
import org.springframework.jdbc.object.StoredProcedure;

import com.rclgroup.dolphin.web.common.RrcStandardDao;
import com.rclgroup.dolphin.web.model.EmailConfigMod;

import oracle.jdbc.internal.OracleTypes;

/**
 * This class contain implementation of {@code UserSearchDao}
 * 
 * @author Cognis Solution
 *
 */
public final class EmailConfigJdbcDao extends RrcStandardDao implements EmailConfigDao {

	private EmailConfigProcedure emailConfigProcedure;
 

	protected void initDao() throws Exception {
		super.initDao();
		emailConfigProcedure = new EmailConfigProcedure(getJdbcTemplate(), new EmailConfigMapper());

	}

	@Override
	public EmailConfigMod getEmailConfig() throws DataAccessException {
		// TODO Auto-generated method stub
		return emailConfigProcedure.getEmailConfig();
	}

	protected class EmailConfigProcedure extends StoredProcedure {

		protected EmailConfigProcedure(JdbcTemplate jdbcTemplate, RowMapper rowMapper) {
			super(jdbcTemplate, PCR_REQUEST_QUOTE_EMAIL_CONFIG);

			declareParameter(new SqlOutParameter("P_O_V_SEARCH_LIST", OracleTypes.CURSOR, rowMapper));
		}

		protected EmailConfigMod getEmailConfig() { 

			Map<String,Object> outMap = execute(new HashMap<>());
			System.out.println("   StoredProcedure executed Successfully...!   ");
			List<EmailConfigMod> list = (List<EmailConfigMod>) outMap.get("P_O_V_SEARCH_LIST");
			if (list != null && list.size() > 0) {
				return list.get(0);
			} else {
				return null;
			}

		}

	}

	class EmailConfigMapper implements RowMapper {

		public EmailConfigMod mapRow(ResultSet rs, int rowNum) throws SQLException {

			EmailConfigMod model = new EmailConfigMod();
			model.setAdminEmail(rs.getString("MAIL_SENDER_EMAIL"));
			model.setHost(rs.getString("MAIL_SERVER_IP"));
			model.setPassword(rs.getString("MAIL_SERVER_PASSWORD"));
			model.setUsername(rs.getString("MAIL_SERVER_USER_ID"));
			return model;
		}
	}

}
