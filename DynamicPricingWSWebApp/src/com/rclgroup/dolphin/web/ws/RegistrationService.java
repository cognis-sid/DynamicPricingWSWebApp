package com.rclgroup.dolphin.web.ws;

import java.io.IOException;
import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.rclgroup.dolphin.web.common.RrcApplicationContextWS;
import com.rclgroup.dolphin.web.dao.RegistrationDao;
import com.rclgroup.dolphin.web.model.ErrorMod;
import com.rclgroup.dolphin.web.model.RegistrationMod;
import com.rclgroup.dolphin.web.model.ResponseMod;
import com.rclgroup.dolphin.web.utils.Constants;
import com.rclgroup.dolphin.web.utils.Utils;


@Path("/registrationServices")
public class RegistrationService  extends BlcBaseService{
	
	public RegistrationDao  registraionDaoObj = (RegistrationDao) RrcApplicationContextWS.getBean("registrationdao");
	
	@POST
    @Path("/saveRegData")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response saveCustomerData(@Context HttpServletRequest request, RegistrationMod customerData)throws IOException {
		
		String jsonOutput = null;
		System.out.println(customerData.getFirstName() + ",,,,,,,,,,,,,,,,,,");
		long startTime = System.currentTimeMillis();

		System.out.println("Start of  saving customer Data");
		try {
			int i  = registraionDaoObj.getCustomerDetails(customerData);
			System.out.println(i + " number of record inserted successfully!!!");
			ResponseMod responseMod = new ResponseMod(Arrays.asList(customerData), true);
			jsonOutput = serializeToJSONString(responseMod);
		}catch(Exception e) {
			e.printStackTrace();
			ErrorMod error = new ErrorMod(Constants.SC_SERVER_ERROR, e.getMessage(), Constants.FAILED,
					Constants.REGISTRATION);
			return Response.status(Constants.SC_SERVER_ERROR).entity(serializeToJSONString(error)).build();
		}
		long endTime = System.currentTimeMillis();
		System.out.println("End  of saving customer Data");
		System.out.println(" Time Taken in seconds =  " + Utils.getTimeTaken(startTime, endTime));
		return Response.status(200).entity(jsonOutput).build();
	}
	
	
}
