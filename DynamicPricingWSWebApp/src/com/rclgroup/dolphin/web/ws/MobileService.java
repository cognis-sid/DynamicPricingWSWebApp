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
import com.rclgroup.dolphin.web.dao.LoginDao;
import com.rclgroup.dolphin.web.dao.QuoteRequestDao;
import com.rclgroup.dolphin.web.email.EZLMail;
import com.rclgroup.dolphin.web.email.EZLMail2;
import com.rclgroup.dolphin.web.model.ErrorMod;
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
@Path("/rclMobile")
public final class MobileService extends BlcBaseService {

	 
	private final CamCustomerMasterDao  camMasterDao =(CamCustomerMasterDao) RrcApplicationContextWS.getBean("camMasterDao");

	private final LoginDao loginDao = (LoginDao) RrcApplicationContextWS.getBean("loginDao");
	private final EZLMail ezLMail = (EZLMail) RrcApplicationContextWS.getBean("mailMail");
	private final QuoteRequestDao dao = (QuoteRequestDao) RrcApplicationContextWS.getBean("quoteDao");
	
	@POST
	@Path("/login")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response login(@Context HttpServletRequest request, UserMod login) throws Exception {

		String jsonOutput = null;
		UserMod searchList  = null;
		System.out.println("login Email id ===> " + login.getUserId());
		System.out.println("login password id ===> " + login.getPass());		
		long startTime = System.currentTimeMillis();
		try {
			if(login.getUserId()!= null) {
				login.setUserId(login.getUserId().toUpperCase());
				 
			}
			
			if(login.getEmailId()!= null) {
				login.setEmailId(login.getEmailId().toUpperCase());
				 
			}
			
			login.setPasswordToken(Utils.genratePassword()); 
			searchList = loginDao.loginUser(login);
			if(login.getUserId().equalsIgnoreCase("admin")) {
				searchList.setStatus(true);
				searchList.setFsc("NDI");
			}
			if(searchList.isStatus()) {	
				
				login.setPods(camMasterDao.getPod());
				try {
					if(!login.getUserId().equalsIgnoreCase("admin")) {
						EZLMail2.sendPassword(login.getEmailId(),login.getPasswordToken(),login.getUserId());
					}
				}catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
				
				login.setTokenId(EncryptDecryptUtil.convertStringToHex(login.getUserId()+Constants.ENCRYPT_STR));
				login.setMobileApp(true);
			}			 
			jsonOutput = serializeToJSONString(login);
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
	@Path("/validateToken")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response validateToken(@Context HttpServletRequest request, UserMod login) throws Exception {

		String jsonOutput = null;
		 		
		System.out.println("login Email id ===> " + login.getEmailId());
		System.out.println("login password id ===> " + login.getPasswordToken());
		  
		long startTime = System.currentTimeMillis();
		 

		try {
			
			if(!Utils.isValidToken(login)) {
				throw new Exception("Your are not autherize");
			}

			 
			
			if(loginDao.validateToeken(login)) {				
				login.setStatus(true);
				login.setMobileApp(true);
			}			 
			jsonOutput = serializeToJSONString(login);
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
	@Path("/deleteRequest")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteRequest(@Context HttpServletRequest request,  RequestQuote requestQuote) throws Exception {

		String jsonOutput = null;
		 		
		System.out.println("deleteRequest Email id ===> " + requestQuote.getEmailId());
		System.out.println("deleteRequest id ===> " + requestQuote.getId());		  
		long startTime = System.currentTimeMillis();		 

		try {
			
			if(!Utils.isValidToken(requestQuote)) {
				throw new Exception("Your are not autherize");
			}

			 
			dao.delete(requestQuote);
			requestQuote.setStatus(true); 			 
			jsonOutput = serializeToJSONString(requestQuote);
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
	@Path("/searchData")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response searchData(@Context HttpServletRequest request, RequestQuote searchParam) {

		String jsonOutput = null;
		 
		searchParam.setCreatedBy(searchParam.getUserId());
		System.out.println("searchParam ===> " + searchParam.toString());
		long startTime = System.currentTimeMillis();
		System.out.println("start of user List");

		try {
		 
			if(!Utils.isValidToken(searchParam)) {
				throw new Exception("Your are not autherize");
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
		 
		
		System.out.println("createUserData User ===> " + requestQuote.toString());
		long startTime = System.currentTimeMillis();
		System.out.println("start of create User");

		try {
		 
			if(!Utils.isValidToken(requestQuote)) {
				throw new Exception("Your are not autherize");
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
	@Path("/delete")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteData(@Context HttpServletRequest request, RequestQuote requestQuote) {
		String jsonOutput = null;		
		System.out.println("deleteData User ===> " + requestQuote.toString());
		long startTime = System.currentTimeMillis();
		System.out.println("start of deleteData User");

		try {
		 
			if(!Utils.isValidToken(requestQuote)) {
				throw new Exception("Your are not autherize");
			}
			dao.delete(requestQuote);
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
	
   
  	@GET
	@Path("/filterShipper")	
  	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public Response search(@Context HttpServletRequest request, @QueryParam("fsc") String fsc,@QueryParam("tokenId") String tokenId,@QueryParam("userId") String userId ,@QueryParam("companyName") String companyName ) throws Exception {

		String jsonOutput = null;
		UserMod login = new UserMod();
		login.setFsc(fsc);
		login.setUserId(userId);
		login.setMobileApp(true);
		login.setCompanyName(companyName);
		login.setTokenId(tokenId);
		System.out.println("login Email id ===> " + login.getEmailId());
		System.out.println("login password id ===> " + login.getPasswordToken());
		  
		long startTime = System.currentTimeMillis();
		 

		try {
			
			if(!Utils.isValidToken(login)) {
				throw new Exception("Your are not autherize");
			}

			 
			
			login.setShipperList(camMasterDao.getShipperList(login));			 
			jsonOutput = serializeToJSONString(login);
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
 	@Path("/updateTargetEtd")
 	@Consumes(MediaType.APPLICATION_JSON)
 	@Produces(MediaType.APPLICATION_JSON)
 	public Response updateTargetEtd(@Context HttpServletRequest request, RequestQuote requestQuote) {

 		String jsonOutput = null;
 		Object result = Utils.getAuthResult(request);
 		
 		System.out.println("updateTargetEtd User ===> " + requestQuote.toString());
 		long startTime = System.currentTimeMillis();
 		 
 		try {
 			if(!Utils.isValidToken(requestQuote)) {
				throw new Exception("Your are not autherize");
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
 	


}
