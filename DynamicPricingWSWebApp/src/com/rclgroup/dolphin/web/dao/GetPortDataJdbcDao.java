package com.rclgroup.dolphin.web.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;

import com.rclgroup.dolphin.web.common.RrcStandardDao;
import com.rclgroup.dolphin.web.model.DynamicPricingMod;
import com.rclgroup.dolphin.web.model.PortMod;
import com.rclgroup.dolphin.web.model.TerminalMod;
import com.rclgroup.dolphin.web.util.RutString;

public class GetPortDataJdbcDao extends RrcStandardDao implements GetPortDataDao{
	
	protected void initDao() throws Exception {
		super.initDao();
		 
	}

	@Override
	public DynamicPricingMod portData(DynamicPricingMod searchParam) throws DataAccessException {
		DynamicPricingMod dmod= new DynamicPricingMod();
		List<PortMod> valueList= new ArrayList<>();
		
		valueList = (List<PortMod>) getJdbcTemplate().query(
				get_port_data,new Object[]{ searchParam.getUserId()},new PortDataRowMapper());
		dmod.setPortdata(valueList);
		return dmod;
	}

	public class PortDataRowMapper implements RowMapper {

		@Override
        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
        	PortMod mod = new PortMod();
        	mod.setPortCode(RutString.nullToStr(rs.getString("pk_port_code")));
        	mod.setPortName(RutString.nullToStr(rs.getString("port_name")));
        	return mod;
        }
    } 
	
	@Override
	public DynamicPricingMod terminalData(PortMod searchParam) throws DataAccessException {
			DynamicPricingMod dmod= new DynamicPricingMod();
			List<TerminalMod> valueList= new ArrayList<>();
			
			valueList = (List<TerminalMod>) getJdbcTemplate().query(
					get_terminal_data,new Object[]{ searchParam.getPortCode()},new TerminalDataRowMapper());
			dmod.setTerminaldata(valueList);
			return dmod;
		}

		public class TerminalDataRowMapper implements RowMapper {

			
			@Override
	        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
	        TerminalMod mod = new TerminalMod();
	        	mod.setTerminal_code(RutString.nullToStr(rs.getString("pk_terminal")));
	        	mod.setTerminal_name(RutString.nullToStr(rs.getString("terminal_name")));
	        	return mod;
	        	
	        }
	    }
	
	
	

}
