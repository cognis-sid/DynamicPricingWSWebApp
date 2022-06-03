/*Copyright (c) 2020 RCL| All Rights Reserved  */
package com.rclgroup.dolphin.web.utils;
/* ------------------------- Change Log ---------------------------------------
##   DD/MM/YY       -User-              -TaskRef-            -ShortDescription-
1    14/03/20       chandu               Initial Version
-------------------------------------------------------------------------------
*/

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import com.rclgroup.dolphin.web.model.BaseInputMod;

/**
 * This class contain method to convert db code into respective String.
 * 
 * @author Cognis
 *
 */
public class Utils {

	public static final String SESSION_RCL_AUTHRESULT = "rclAuthresult";
	
	public static String getTimeTaken(long startTime, long endTime) {
		long diff = endTime - startTime;
		return diff / 1000+" Seconds ";
	}

	public static Object getAuthResult(HttpServletRequest request) {
		return request.getSession().getAttribute(SESSION_RCL_AUTHRESULT);
	}
 
	public static boolean isValidToken(BaseInputMod user) {
		if(user.isMobileApp()) {
				if(user.getTokenId() != null) {
						String token;
						try {
							token = EncryptDecryptUtil.convertHexToString(user.getTokenId());
						} catch (Exception e) {
							// TODO Auto-generated catch block
							return false;
						}
						if(token.equals(user.getUserId()+Constants.ENCRYPT_STR)) {
							return true;
						}
				}
		}else {
			
		}
		return false;
	}
	 
	public static String genratePassword() {
		String pass= UUID.randomUUID().toString().split("-")[0];
		pass =pass.substring(4).toUpperCase();
		return  pass;
}
	public static void main(String arg[]) {
		System.out.println(UUID.randomUUID().toString());
		System.out.println(genratePassword()); 
	}
}
