package com.rclgroup.dolphin.web.dao;

import java.util.Date;

import com.rclgroup.dolphin.web.common.RrcStandardDao;
import com.rclgroup.dolphin.web.model.RegistrationMod;

public class RegistrationJdbcDao extends RrcStandardDao implements RegistrationDao {
	
	@Override
	public int getCustomerDetails(RegistrationMod data)  {
		
		String query = "INSERT INTO rest_api_customer_info(user_name,company_name,password,email_id,"
				+ "city,state,country,phone,fax,address_1,address_2,address_3,address_4,created_date)"
				+ " VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)";
		
		Date currentDate = new Date();
		
		int result = getJdbcTemplate().update(query, new Object[] {  data.getFirstName()+data.getLastName(),
				data.getCompanyName(), data.getPassword(), data.getEmail(), false, data.getCity(),
				data.getState(), data.getCountry(), data.getPhoneNo(), data.getFaxNo(), data.getAddress1(),
				data.getAddress2(), data.getAddress3(), data.getAddress4(),currentDate } );
		return result;
		
	}

}
