/* Copyright (c) 2020 RCL| All Rights Reserved*/
package com.rclgroup.dolphin.web.ws;

import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.rclgroup.dolphin.web.common.RrcApplicationContextWS;
import com.rclgroup.dolphin.web.dao.CamCustomerMasterDao;
import com.rclgroup.dolphin.web.dao.GetPortDataDao;
import com.rclgroup.dolphin.web.dao.LoginDao;
import com.rclgroup.dolphin.web.dao.QuoteRequestDao;
import com.rclgroup.dolphin.web.email.EZLMail;
import com.rclgroup.dolphin.web.email.EZLMail2;
import com.rclgroup.dolphin.web.model.DynamicPricingMod;
import com.rclgroup.dolphin.web.model.ErrorMod;
import com.rclgroup.dolphin.web.model.PortMod;
import com.rclgroup.dolphin.web.model.RequestQuote;
import com.rclgroup.dolphin.web.model.ResponseMod;
import com.rclgroup.dolphin.web.model.UserMod;
import com.rclgroup.dolphin.web.utils.Constants;
import com.rclgroup.dolphin.web.utils.EncryptDecryptUtil;
import com.rclgroup.dolphin.web.utils.Utils;

/**
 * This class contain extends of {@code BlcBaseService}
 * 
 * @author Cognis Solution
 */
@Path("/rclDynamicPricing")
public final class DynamicPricingService extends BlcBaseService {

	 

	private final GetPortDataDao getPortDataDao = (GetPortDataDao) RrcApplicationContextWS.getBean("getPortDataDao");
	
	@POST
	@Path("/getPortData")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response portData(@Context HttpServletRequest request, DynamicPricingMod pricingMod) throws Exception {

		String jsonOutput = null;
		DynamicPricingMod searchPortList  = null;
		System.out.println("login Email id ===> " + pricingMod.getUserId());
		long startTime = System.currentTimeMillis();
		try {
			if(pricingMod.getUserId()!= null) {
				pricingMod.setUserId(pricingMod.getUserId().toUpperCase());
				 
			}
			
			
			
			searchPortList = getPortDataDao.portData(pricingMod);
			
			if(searchPortList.getPortdata().size()!=0) {	
				
				System.out.println("port available");
				
				
				
			}			 
			jsonOutput = serializeToJSONString(searchPortList.getPortdata());
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getStackTrace());
			ErrorMod error = new ErrorMod(Constants.SC_SERVER_ERROR, e.getMessage(), Constants.FAILED,
					Constants.LOGIN_USER);
			return Response.status(Constants.SC_SERVER_ERROR).entity(serializeToJSONString(error)).build();
		}
		System.out.println("  end  of user List ");
		long endTime = System.currentTimeMillis();
		System.out.println("Time Taken in user List : " + Utils.getTimeTaken(startTime, endTime));

		return Response.status(Constants.SC_OK).entity(jsonOutput).build();
	}
	
	
	@POST
	@Path("/getTerminalData")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response terminalData(@Context HttpServletRequest request, PortMod portmod) throws Exception {

		String jsonOutput = null;
		DynamicPricingMod searchPortList  = null;
		System.out.println("Port code is ===> " + portmod.getPortCode());
		long startTime = System.currentTimeMillis();
		try {
			if(portmod.getPortCode()!= null) {
			searchPortList = getPortDataDao.terminalData(portmod);
			}
			if(searchPortList.getTerminaldata().size()!=0) {	
				
				System.out.println("terminal available");
			}
			
			jsonOutput = serializeToJSONString(searchPortList.getTerminaldata());
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getStackTrace());
			ErrorMod error = new ErrorMod(Constants.SC_SERVER_ERROR, e.getMessage(), Constants.FAILED,
					Constants.LOGIN_USER);
			return Response.status(Constants.SC_SERVER_ERROR).entity(serializeToJSONString(error)).build();
		}
		System.out.println("  end  of user List ");
		long endTime = System.currentTimeMillis();
		System.out.println("Time Taken in user List : " + Utils.getTimeTaken(startTime, endTime));

		return Response.status(Constants.SC_OK).entity(jsonOutput).build();
	}

	


}
