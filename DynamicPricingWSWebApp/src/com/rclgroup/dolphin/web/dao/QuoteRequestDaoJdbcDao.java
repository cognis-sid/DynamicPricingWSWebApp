/*Copyright (c) 2020 RCL| All Rights Reserved  */
package com.rclgroup.dolphin.web.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
import com.rclgroup.dolphin.web.model.PropertyMod;
import com.rclgroup.dolphin.web.model.RequestQuote;
import com.rclgroup.dolphin.web.util.RutString;
import com.rclgroup.dolphin.web.utils.DateUtils;

import oracle.jdbc.internal.OracleTypes;

/**
 * This class contain implementation of {@code UserSearchDao}
 * 
 * @author Cognis Solution
 *
 */
public final class QuoteRequestDaoJdbcDao extends RrcStandardDao implements QuoteRequestDao {
	private ActionRequestProcedure actionRequestProcedure;
	private SearchProcedure searchProcedure;	
	private CreateRequestProcedure createRequestProcedure;
	private DeleteRequestProcedure deleteRequestProcedure;
	private UpdateRateRequestProcedure updateRequestProcedure;
	 private int i=0;
	
	protected void initDao() throws Exception {
		super.initDao();
		createRequestProcedure = new CreateRequestProcedure(getJdbcTemplate());	
		searchProcedure = new SearchProcedure(getJdbcTemplate(), new RequestMapper());
		actionRequestProcedure = new ActionRequestProcedure(getJdbcTemplate());
		deleteRequestProcedure = new DeleteRequestProcedure(getJdbcTemplate());
		updateRequestProcedure =new UpdateRateRequestProcedure(getJdbcTemplate());
	 }
	
	@Override
	public List<PropertyMod> validateShipper(RequestQuote quote) throws DataAccessException {
		
		List<PropertyMod> valueList = new ArrayList<PropertyMod>();
        System.out.println("FSC----->"+quote.getFsc());
        
        StringBuffer sql = new StringBuffer();
        sql.append(" select CUSTOMER_CODE,CUSTOMER_NAME from V_QUOTE_CAM_CUSTOMER where FSC =:fsc and CUSTOMER_CODE =:code");
       System.out.println(sql.toString().toUpperCase());
        Map<String, String> paramMap =new HashMap<String, String>();
        paramMap.put("fsc", quote.getFsc());
        paramMap.put("code", quote.getShipper());
         
            valueList = (List<PropertyMod>) getNamedParameterJdbcTemplate().query(
                sql.toString(),
                paramMap,
                new RowMapper() {
                    public PropertyMod mapRow(ResultSet rs, int rowNum) throws SQLException {
                    	PropertyMod mod = new PropertyMod();
                    	mod.setText(RutString.nullToStr(rs.getString("CUSTOMER_CODE")));
                    	mod.setValue(RutString.nullToStr(rs.getString("CUSTOMER_NAME")));
                    	 
                        return mod;
                    }
                } 
            );
         
     return valueList;
		
	}
	@Override
	public void arrove(RequestQuote requestQuote) throws DataAccessException {
		actionRequestProcedure.actions(requestQuote, "APPROVE");
	}
	@Override
	public void reject(RequestQuote requestQuote) throws DataAccessException {
		actionRequestProcedure.actions(requestQuote, "REJECT");
		
	}
	@Override
	public List<RequestQuote> getSearchData(RequestQuote searchParam) throws DataAccessException {
		 this.i=0;
		return searchProcedure.getSearchData(searchParam);
	}
	 
	 @Override
	public String create(RequestQuote quote) throws DataAccessException {
		 
		return createRequestProcedure.createUserData(quote);
	}
	 
	 @Override
	public void delete(RequestQuote requestQuote) throws DataAccessException {
		// TODO Auto-generated method stub
		 deleteRequestProcedure.delete(requestQuote);
		
	}
	 protected class ActionRequestProcedure extends StoredProcedure {

			protected ActionRequestProcedure(JdbcTemplate jdbcTemplate) {
				super(jdbcTemplate, PCR_REQUEST_QUOTE_REQUEST_ACTION); 
				declareParameter(new SqlParameter("P_I_V_REQUEST_PK", OracleTypes.VARCHAR)); 
				declareParameter(new SqlParameter("P_I_ACTION_TYPE", OracleTypes.VARCHAR)); 
				declareParameter(new SqlParameter("P_I_V_USER_ID", OracleTypes.VARCHAR)); 
				declareParameter(new SqlParameter("P_I_V_REASON", OracleTypes.VARCHAR)); 
				
			}

			protected String actions(RequestQuote searchParam,String action) {			
				Map<String,String> inPara = new HashMap<>();		
				inPara.put("P_I_V_REQUEST_PK", searchParam.getId());	
				inPara.put("P_I_ACTION_TYPE", action);	
				inPara.put("P_I_V_USER_ID", searchParam.getBrowserData().getUserId());		
				inPara.put("P_I_V_REASON", searchParam.getReasonOfReject());		
				Map outMap = execute(inPara);
				System.out.println("   StoredProcedure executed Successfully...!   ");
				return (String) outMap.get("P_O_V_MAPPING_LIST");

			}

	 }
	
	 protected class DeleteRequestProcedure extends StoredProcedure {

			protected DeleteRequestProcedure(JdbcTemplate jdbcTemplate) {
				super(jdbcTemplate, PCR_REQUEST_QUOTE_DELETE); 
				declareParameter(new SqlParameter("P_I_V_REQUEST_ID", OracleTypes.VARCHAR)); 
				declareParameter(new SqlParameter("P_I_V_USER_ID", OracleTypes.VARCHAR)); 
				 
				
			}

			protected void delete(RequestQuote searchParam) {			
				Map<String,String> inPara = new HashMap<>();		
				inPara.put("P_I_V_REQUEST_ID", searchParam.getId());					 
				inPara.put("P_I_V_USER_ID", searchParam.getBrowserData().getUserId());			
				Map outMap = execute(inPara);
				System.out.println("   StoredProcedure executed Successfully...!   ");				 
			}

	 }
	
	protected class CreateRequestProcedure extends StoredProcedure {

		protected CreateRequestProcedure(JdbcTemplate jdbcTemplate) {
			super(jdbcTemplate, PCR_REQUEST_QUOTE_REQUEST);
			declareParameter(new SqlParameter("P_I_V_POL", OracleTypes.VARCHAR));                              
			declareParameter(new SqlParameter("P_I_V_POD", OracleTypes.VARCHAR));                             
			declareParameter(new SqlParameter("P_I_V_SHIPPER", OracleTypes.VARCHAR));                             
			declareParameter(new SqlParameter("P_I_V_VESSEL", OracleTypes.VARCHAR)); 
			declareParameter(new SqlParameter("P_I_V_VOYAGE", OracleTypes.VARCHAR)); 
			declareParameter(new SqlParameter("P_I_V_VOLUME", OracleTypes.VARCHAR)); 
			declareParameter(new SqlParameter("P_I_V_FREE_TIME", OracleTypes.VARCHAR)); 
			declareParameter(new SqlParameter("P_I_V_TAGET_RATE", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("P_I_V_TARGET_ETD", OracleTypes.VARCHAR)); 
			declareParameter(new SqlParameter("P_I_V_REQUEST_ID", OracleTypes.VARCHAR)); 
			declareParameter(new SqlParameter("P_I_V_USER_ID", OracleTypes.VARCHAR)); 
			declareParameter(new SqlParameter("P_I_V_REMARKS", OracleTypes.VARCHAR)); 
			declareParameter(new SqlParameter("P_I_V_CMODITY", OracleTypes.VARCHAR)); 
			declareParameter(new SqlParameter("P_I_V_WEIGHT", OracleTypes.VARCHAR)); 
			declareParameter(new SqlParameter("P_I_V_CONTRACT_PARTY", OracleTypes.VARCHAR)); 
			
			
		}

		/**
		 * 
		 * Used to call StoredProcedure.
		 * 
		 * @author Cognis Solution
		 *
		 */
		protected String createUserData(RequestQuote searchParam) {			
			Map<String,String> inPara = new HashMap<>();		
			inPara.put("P_I_V_POL", searchParam.getPol());
			inPara.put("P_I_V_POD", searchParam.getPod());
			inPara.put("P_I_V_SHIPPER", searchParam.getShipper());
			inPara.put("P_I_V_VESSEL", searchParam.getVessel());
			inPara.put("P_I_V_VOYAGE", searchParam.getVoyage());
			inPara.put("P_I_V_VOLUME", searchParam.getWeeklyVolume());
			inPara.put("P_I_V_FREE_TIME", searchParam.getFreeTime());
			inPara.put("P_I_V_TAGET_RATE", searchParam.getTargetRate());
			inPara.put("P_I_V_TARGET_ETD",DateUtils.getDateFromDefaultDateStringYYYYMMDD(searchParam.getTargetEtd()));
			inPara.put("P_I_V_REQUEST_ID", searchParam.getRequestId());
			inPara.put("P_I_V_USER_ID", searchParam.getBrowserData().getUserId());	
			inPara.put("P_I_V_REMARKS", searchParam.getRemarks());
			inPara.put("P_I_V_CMODITY", searchParam.getCmodity());
			inPara.put("P_I_V_WEIGHT", searchParam.getWeight());
			inPara.put("P_I_V_CONTRACT_PARTY", searchParam.getContractParty());		
			Map outMap = execute(inPara);
			System.out.println("   StoredProcedure executed Successfully...!   ");
			return (String) outMap.get("P_O_V_MAPPING_LIST");

		}

	}
	

	

	protected class SearchProcedure extends StoredProcedure {

		protected SearchProcedure(JdbcTemplate jdbcTemplate, RowMapper rowMapper) {
			super(jdbcTemplate, PCR_REQUEST_QUOTE_REQUEST_SEARCH);
			declareParameter(new SqlParameter("P_I_V_POL", OracleTypes.VARCHAR));                              
			declareParameter(new SqlParameter("P_I_V_POD", OracleTypes.VARCHAR));                             
			declareParameter(new SqlParameter("P_I_V_SHIPPER", OracleTypes.VARCHAR));                             
			declareParameter(new SqlParameter("P_I_V_VESSEL", OracleTypes.VARCHAR));                             
			declareParameter(new SqlParameter("P_I_V_VOYAGE", OracleTypes.VARCHAR));                             
			declareParameter(new SqlParameter("P_I_V_VOLUME", OracleTypes.VARCHAR));                             
			declareParameter(new SqlParameter("P_I_V_FREE_TIME", OracleTypes.VARCHAR));  
			declareParameter(new SqlParameter("P_I_V_FROM_DATE", OracleTypes.VARCHAR));  
			declareParameter(new SqlParameter("P_I_V_TO_DATE", OracleTypes.VARCHAR));  
			declareParameter(new SqlParameter("P_I_V_REQUEST_ID", OracleTypes.VARCHAR));  
			declareParameter(new SqlParameter("P_I_V_USER_ID", OracleTypes.VARCHAR));		
			declareParameter(new SqlParameter("P_I_V_LOGIN_ID", OracleTypes.VARCHAR));		
			declareParameter(new SqlParameter("P_I_V_ROLE_NAME", OracleTypes.VARCHAR));	
			declareParameter(new SqlParameter("P_I_V_STATUS_ID", OracleTypes.VARCHAR));	
			declareParameter(new SqlParameter("P_I_V_APPROVE_ID", OracleTypes.VARCHAR));	
			declareParameter(new SqlParameter("P_I_V_APPROVE_BY_ID", OracleTypes.VARCHAR));	
			declareParameter(new SqlParameter("P_I_V_TARGETED_DATE", OracleTypes.VARCHAR));	
			 
			declareParameter(new SqlOutParameter("P_O_V_SEARCH_LIST", OracleTypes.CURSOR, rowMapper));
		}

		 
		protected List<RequestQuote> getSearchData(RequestQuote searchParam) {			
			Map<String,String> inPara = new HashMap<>();		
			inPara.put("P_I_V_POL", searchParam.getPol());
			inPara.put("P_I_V_POD", searchParam.getPod());
			inPara.put("P_I_V_SHIPPER", searchParam.getShipper());
			inPara.put("P_I_V_VESSEL", searchParam.getVessel());
			inPara.put("P_I_V_VOYAGE", searchParam.getVoyage());	
			inPara.put("P_I_V_VOLUME", searchParam.getWeeklyVolume());	
			inPara.put("P_I_V_FREE_TIME", searchParam.getFreeTime());	
			inPara.put("P_I_V_REQUEST_ID", searchParam.getRequestId());	
			inPara.put("P_I_V_USER_ID", searchParam.getCreatedBy());
			inPara.put("P_I_V_LOGIN_ID", searchParam.getBrowserData().getUserId());
			inPara.put("P_I_V_ROLE_NAME", searchParam.getRoleId());
			inPara.put("P_I_V_FROM_DATE", DateUtils.getDateFromDefaultDateStringYYYYMMDD(searchParam.getFromDate()));
			inPara.put("P_I_V_TO_DATE", DateUtils.getDateFromDefaultDateStringYYYYMMDD(searchParam.getToDate()));	
			inPara.put("P_I_V_TARGETED_DATE", DateUtils.getDateFromDefaultDateStringYYYYMMDD(searchParam.getTargetEtd()));	
			inPara.put("P_I_V_STATUS_ID", searchParam.getStatusId());
			inPara.put("P_I_V_APPROVE_ID", searchParam.getApprovedId());
			inPara.put("P_I_V_APPROVE_BY_ID", searchParam.getArppovedBy());
			Map outMap = execute(inPara);
			System.out.println("   StoredProcedure executed Successfully...!   ");
			return (List<RequestQuote>) outMap.get("P_O_V_SEARCH_LIST");

		}

	}




	class RequestMapper implements RowMapper {

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
		
		public RequestQuote mapRow(ResultSet rs, int rowNum) throws SQLException {
			i++;
			RequestQuote model = new RequestQuote();
			model.setSeq((i));	
			model.setRequestId(rs.getString("REQUEST_ID"));
			model.setFreeTime(rs.getString("FREE_TIME"));
			model.setVessel(rs.getString("VESSEL"));
			model.setVoyage(rs.getString("VOYOGE"));
			model.setPod(rs.getString("POD"));
			model.setPol(rs.getString("POL"));
			model.setWeeklyVolume(rs.getString("VOLUME"));
			model.setRemarks(rs.getString("REMARKS"));
			model.setCmodity(rs.getString("CMODITY"));
			model.setCreatedDate(DateUtils.formateDateWithTime(rs.getDate("RECORD_ADD_DATE")));
			model.setCreatedBy(rs.getString("RECORD_ADD_USER"));
			model.setStatusId(rs.getString("RECORD_STATUS"));
			model.setTargetRate(rs.getString("TARGET_RATE"));
			model.setTargetEtd(DateUtils.formateDate(rs.getDate("TARGET_ETD")));
			model.setApprovedId(rs.getString("APPROVE_ID"));
			model.setArppovedBy(rs.getString("APPOVE_BY"));
			model.setId(rs.getString("QUOTE_REQUEST_PK"));
		    model.setApprovedDate(DateUtils.formateDateWithTime(rs.getDate("RECORD_CHANGE_DATE")));
		    model.setWeight(rs.getString("WEIGHT"));
		    model.setShipper(rs.getString("SHIPPER"));
		    model.setReasonOfReject(rs.getString("REASON_OF_REJECT"));
		    model.setContractParty(rs.getString("CONTRACT_PARTY"));
		    if(model.getPod()!= null) {
		    	model.setPod(model.getPod().toUpperCase());
		    }
		    if(model.getPol()!= null) {
		    	model.setPol(model.getPol().toUpperCase());
		    }
			return model;
		}
	}




	protected class UpdateRateRequestProcedure extends StoredProcedure {

		protected UpdateRateRequestProcedure(JdbcTemplate jdbcTemplate) {
			super(jdbcTemplate, PCR_REQUEST_QUOTE_RATE_UPDATE); 
			declareParameter(new SqlParameter("P_I_V_REQUEST_PK", OracleTypes.VARCHAR)); 
			declareParameter(new SqlParameter("P_I_RATE", OracleTypes.VARCHAR)); 
			 
			
		}

		protected void update(RequestQuote quote) {			
			Map<String,String> inPara = new HashMap<>();		
			inPara.put("P_I_V_REQUEST_PK", quote.getId());					 
			inPara.put("P_I_RATE", quote.getTargetRate());			
			Map outMap = execute(inPara);
			System.out.println(quote.getId()+"   StoredProcedure update rate Successfully...!   "+quote.getTargetRate());				 
		}

 }
	
	@Override
	public void updateTragetEtd(RequestQuote quote) throws DataAccessException {

		 
        System.out.println("update getTargetRate----->"+quote.getTargetRate());
        updateRequestProcedure.update(quote);
       /* StringBuffer sql = new StringBuffer();
        sql.append(" update QUOTE_REQUEST set TARGET_RATE=:TARGET_RATE,RECORD_STATUS='S' where QUOTE_REQUEST_PK=:QUOTE_REQUEST_PK ");
       System.out.println(sql.toString().toUpperCase());
        Map<String, Object> paramMap =new HashMap<String, Object>();
        paramMap.put("TARGET_RATE", quote.getTargetRate());
        paramMap.put("QUOTE_REQUEST_PK", Integer.parseInt(quote.getId()));
         
              getNamedParameterJdbcTemplate().update(
                sql.toString(),
                paramMap
                
            );*/
         
		
	}


}
