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
import com.rclgroup.dolphin.web.dao. UserSearchDao;
import com.rclgroup.dolphin.web.model. ErrorMod;
import com.rclgroup.dolphin.web.model. PropertyMod;
import com.rclgroup.dolphin.web.model. ResponseMod;
import com.rclgroup.dolphin.web.model. UserMod;
import com.rclgroup.dolphin.web.utils. Constants;
import com.rclgroup.dolphin.web.utils. Utils;

/**
 * This class contain extends of {@code BlcBaseService}
 * 
 * @author Cognis Solution
 */
@Path("/user")
public final class SearchUserService extends BlcBaseService {

	 

	private final UserSearchDao dao = (UserSearchDao) RrcApplicationContextWS.getBean("userSerchDao");
	
	@POST
	@Path("/searchData")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)

	public Response searchData(@Context HttpServletRequest request, UserMod searchParam) {

		String jsonOutput = null;
		Object result = Utils.getAuthResult(request);
		
		System.out.println("searchParam ===> " + searchParam.toString());
		long startTime = System.currentTimeMillis();
		System.out.println("start of user List");

		try {
			if(result == null) {
		//		throw new Exception("Your are not autherize");
			}
			
			List<UserMod> searchList = dao.findData(searchParam);
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
	@Path("/createUserData")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createUserData(@Context HttpServletRequest request, UserMod createUserData) {

		String jsonOutput = null;
		Object result = Utils.getAuthResult(request);
		
		System.out.println("Create User ===> " + createUserData.toString());
		long startTime = System.currentTimeMillis();
		System.out.println("start of create User");

		try {
			if(result == null) {
		//		throw new Exception("Your are not autherize");
			}
			
			String response = dao.createUserData(createUserData);
			if(response.equals("SUCESS")) {
				createUserData.setStatus(true);
				
			}else {
				createUserData.setMessage(response);
			}
			
			 
			jsonOutput = serializeToJSONString(createUserData);
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
	@Path("/deleteUserData")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteUserData(@Context HttpServletRequest request, UserMod createUserData) {

		String jsonOutput = null;
		Object result = Utils.getAuthResult(request);
		
		System.out.println("deleteUserData ===> " + createUserData.toString());
		long startTime = System.currentTimeMillis();
		System.out.println("start of deleteUserData");

		try {
			if(result == null) {
		//		throw new Exception("Your are not autherize");
			}
			
			dao.deleteUser(createUserData);
			createUserData.setStatus(true);
			 
			jsonOutput = serializeToJSONString(createUserData);
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
	@POST
	@Path("/role")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getRole(@Context HttpServletRequest request, UserMod searchParam) {

		String jsonOutput = null;
		 
		
		System.out.println("searchParam ===> " + searchParam.toString());
		long startTime = System.currentTimeMillis();
		System.out.println("start of user List");

		try {
			 if(searchParam.isMobileApp() && searchParam.getTokenId()== null) {
				 throw new Exception();
			 }
			
			List<UserMod> searchList = dao.findData(searchParam);
			UserMod	userData = null;
			if(searchList != null && searchList.size()>0) {
				userData = searchList.get(0);
				userData =dao.getById(userData);
			}else {
				new Exception("User has been deleted");
			}
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
	@Path("/fsc")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getFsc(@Context HttpServletRequest request, UserMod searchParam) {

		String jsonOutput = null;
		 
		
		System.out.println("getFsc ===> " + searchParam.toString());
		long startTime = System.currentTimeMillis();
		System.out.println("start of user List");

		try {
			  
			
			List<PropertyMod> searchList = dao.getFSC();
			 
			 
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

}
