/* Copyright (c) 2020 RCL| All Rights Reserved*/
package com.rclgroup.dolphin.web.ws;

import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.rclgroup.dolphin.web.common.RrcApplicationContextWS;
import com.rclgroup.dolphin.web.dao.QuoteRequestDao;
import com.rclgroup.dolphin.web.model.ErrorMod;
import com.rclgroup.dolphin.web.model.PropertyMod;
import com.rclgroup.dolphin.web.model.RequestQuote;
import com.rclgroup.dolphin.web.model.ResponseMod;
import com.rclgroup.dolphin.web.utils.Constants;
import com.rclgroup.dolphin.web.utils.Utils;

/**
 * This class contain extends of {@code BlcBaseService}
 * 
 * @author Cognis Solution
 */
@Path("/quote")
public final class QuoteService extends BlcBaseService {

	 

	private final QuoteRequestDao dao = (QuoteRequestDao) RrcApplicationContextWS.getBean("quoteDao");
	
	@POST
	@Path("/searchData")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response searchData(@Context HttpServletRequest request, RequestQuote searchParam) {

		String jsonOutput = null;
		Object result = Utils.getAuthResult(request);
		
		System.out.println("searchParam ===> " + searchParam.toString());
		long startTime = System.currentTimeMillis();
		System.out.println("start of user List");

		try {
			if(result == null) {
		//		throw new Exception("Your are not autherize");
			}
			
			List<RequestQuote> searchList = dao.getSearchData(searchParam);
			ResponseMod responseMod = new ResponseMod(searchList, true);
			jsonOutput = serializeToJSONString(responseMod);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getStackTrace());
			ErrorMod error = new ErrorMod(Constants.SC_SERVER_ERROR, e.getMessage(), Constants.FAILED,
					Constants.SEARCH_USER);
			return Response.status(Constants.SC_SERVER_ERROR).entity(serializeToJSONString(error)).build();
		}
		System.out.println("  end  of user List ");
		long endTime = System.currentTimeMillis();
		System.out.println("Time Taken in user List : " + Utils.getTimeTaken(startTime, endTime));

		return Response.status(Constants.SC_OK).entity(jsonOutput).build();
	}


  @POST
	@Path("/create")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createUserData(@Context HttpServletRequest request, RequestQuote requestQuote) {

		String jsonOutput = null;
		Object result = Utils.getAuthResult(request);
		
		System.out.println("createUserData User ===> " + requestQuote.toString());
		long startTime = System.currentTimeMillis();
		System.out.println("start of create User");

		try {
			if(result == null) {
		//		throw new Exception("Your are not autherize");
			}
			
			dao.create(requestQuote);
			ResponseMod responseMod = new ResponseMod(Collections.singletonList(requestQuote), true);
			requestQuote.setStatus(true);		 
			jsonOutput = serializeToJSONString(responseMod);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getStackTrace());
			ErrorMod error = new ErrorMod(Constants.SC_SERVER_ERROR, e.getMessage(), Constants.FAILED,
					Constants.SEARCH_USER);
			return Response.status(Constants.SC_SERVER_ERROR).entity(serializeToJSONString(error)).build();
		}
		System.out.println("  end  of user List ");
		long endTime = System.currentTimeMillis();
		System.out.println("Time Taken in user List : " + Utils.getTimeTaken(startTime, endTime));

		return Response.status(Constants.SC_OK).entity(jsonOutput).build();
	}
  
  @POST
 	@Path("/updateTargetEtd")
 	@Consumes(MediaType.APPLICATION_JSON)
 	@Produces(MediaType.APPLICATION_JSON)
 	public Response updateTargetEtd(@Context HttpServletRequest request, RequestQuote requestQuote) {

 		String jsonOutput = null;
 		Object result = Utils.getAuthResult(request);
 		
 		System.out.println("updateTargetEtd User ===> " + requestQuote.toString());
 		long startTime = System.currentTimeMillis();
 		 
 		try {
 			if(result == null) {
 		//		throw new Exception("Your are not autherize");
 			}
 			
 			dao.updateTragetEtd(requestQuote);
 			ResponseMod responseMod = new ResponseMod(Collections.singletonList(requestQuote), true);
 			requestQuote.setStatus(true);		 
 			jsonOutput = serializeToJSONString(responseMod);
 		} catch (Exception e) {
 			e.printStackTrace();
 			System.out.println(e.getStackTrace());
 			ErrorMod error = new ErrorMod(Constants.SC_SERVER_ERROR, e.getMessage(), Constants.FAILED,
 					Constants.SEARCH_USER);
 			return Response.status(Constants.SC_SERVER_ERROR).entity(serializeToJSONString(error)).build();
 		}
 		 
 		long endTime = System.currentTimeMillis();
 		System.out.println("Time Taken in user List : " + Utils.getTimeTaken(startTime, endTime));

 		return Response.status(Constants.SC_OK).entity(jsonOutput).build();
 	}
 	

	

	@POST
	@Path("/approve")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response arrove(@Context HttpServletRequest request, RequestQuote quote) {

		String jsonOutput = null;
		Object result = Utils.getAuthResult(request);
		
		System.out.println("arrove ===> " + quote.toString());
		long startTime = System.currentTimeMillis();
		System.out.println("start of arrove");

		try {
			 
			
			dao.arrove(quote);
			ResponseMod responseMod = new ResponseMod(Collections.singletonList(quote), true);
			jsonOutput = serializeToJSONString(responseMod);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getStackTrace());
			ErrorMod error = new ErrorMod(Constants.SC_SERVER_ERROR, e.getMessage(), Constants.FAILED,
					Constants.SEARCH_USER);
			return Response.status(Constants.SC_SERVER_ERROR).entity(serializeToJSONString(error)).build();
		}
		System.out.println("  end  of user List ");
		long endTime = System.currentTimeMillis();
		System.out.println("Time Taken in user List : " + Utils.getTimeTaken(startTime, endTime));

		return Response.status(Constants.SC_OK).entity(jsonOutput).build();
	}
	
	@POST
	@Path("/reject")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response reject(@Context HttpServletRequest request, RequestQuote quote) {

		String jsonOutput = null;
		Object result = Utils.getAuthResult(request);
		
		System.out.println("reject ===> " + quote.toString());
		long startTime = System.currentTimeMillis();
		System.out.println("start of arrove");

		try {
			 
			
			dao.reject(quote);
			ResponseMod responseMod = new ResponseMod(Collections.singletonList(quote), true);
			jsonOutput = serializeToJSONString(responseMod);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getStackTrace());
			ErrorMod error = new ErrorMod(Constants.SC_SERVER_ERROR, e.getMessage(), Constants.FAILED,
					Constants.SEARCH_USER);
			return Response.status(Constants.SC_SERVER_ERROR).entity(serializeToJSONString(error)).build();
		}
		System.out.println("  end  of user List ");
		long endTime = System.currentTimeMillis();
		System.out.println("Time Taken in user List : " + Utils.getTimeTaken(startTime, endTime));

		return Response.status(Constants.SC_OK).entity(jsonOutput).build();
	}
	
	@POST
	@Path("/validateShipper")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response validateShipper(@Context HttpServletRequest request, RequestQuote quote) {
		String jsonOutput = null;		 		
		System.out.println("validateShipper ===> " + quote.toString());
		long startTime = System.currentTimeMillis();
		try {			 
			
			List<PropertyMod>	lists = dao.validateShipper(quote);			 
			ResponseMod responseMod = new ResponseMod(lists, true);
			jsonOutput = serializeToJSONString(responseMod);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getStackTrace());
			ErrorMod error = new ErrorMod(Constants.SC_SERVER_ERROR, e.getMessage(), Constants.FAILED,
					Constants.SEARCH_USER);
			return Response.status(Constants.SC_SERVER_ERROR).entity(serializeToJSONString(error)).build();
		}
		System.out.println("  end  of validateShipper ");
		long endTime = System.currentTimeMillis();
		System.out.println("Time Taken in user List : " + Utils.getTimeTaken(startTime, endTime));

		return Response.status(Constants.SC_OK).entity(jsonOutput).build();
	}
	
/*	
	@POST
	@Path("/getById")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getById(@Context HttpServletRequest request, UserMod userData) {

		String jsonOutput = null;
		Object result = Utils.getAuthResult(request);
		
		System.out.println("getById ===> " + userData.toString());
		long startTime = System.currentTimeMillis();
		System.out.println("start of getById");

		try {
			if(result == null) {
		//		throw new Exception("Your are not autherize");
			}
			
			userData =dao.getById(userData);
			userData.setStatus(true);
			ResponseMod responseMod = new ResponseMod(Collections.singletonList(userData), true);
			jsonOutput = serializeToJSONString(responseMod);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getStackTrace());
			ErrorMod error = new ErrorMod(Constants.SC_SERVER_ERROR, e.getMessage(), Constants.FAILED,
					Constants.SEARCH_USER);
			return Response.status(Constants.SC_SERVER_ERROR).entity(serializeToJSONString(error)).build();
		}
		System.out.println("  end  of user List ");
		long endTime = System.currentTimeMillis();
		System.out.println("Time Taken in user List : " + Utils.getTimeTaken(startTime, endTime));

		return Response.status(Constants.SC_OK).entity(jsonOutput).build();
	}
	
	
	@POST
	@Path("/update")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateUser(@Context HttpServletRequest request, UserMod userData) {

		String jsonOutput = null;
		Object result = Utils.getAuthResult(request);
		
		System.out.println("update ===> " + userData.toString());
		long startTime = System.currentTimeMillis();
		System.out.println("start of update");

		try {
			if(result == null) {
		//		throw new Exception("Your are not autherize");
			}
			
			dao.deleteUser(userData);
			dao.createUserData(userData);
			userData.setStatus(true);
			ResponseMod responseMod = new ResponseMod(Collections.singletonList(userData), true);
			jsonOutput = serializeToJSONString(responseMod);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getStackTrace());
			ErrorMod error = new ErrorMod(Constants.SC_SERVER_ERROR, e.getMessage(), Constants.FAILED,
					Constants.SEARCH_USER);
			return Response.status(Constants.SC_SERVER_ERROR).entity(serializeToJSONString(error)).build();
		}
		System.out.println("  end  of user List ");
		long endTime = System.currentTimeMillis();
		System.out.println("Time Taken in user List : " + Utils.getTimeTaken(startTime, endTime));

		return Response.status(Constants.SC_OK).entity(jsonOutput).build();
	}
	*/
}
