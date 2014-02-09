/**
 * 
 */
package com.tmm.frm.helper;

import javax.servlet.http.HttpServletRequest;

/**
 * Util class to assist with common user/account processing
 * 
 * @author robert.hinds
 *
 */
public class UserHelper {
	
	/**
	 * Method to determine if request is authenticated
	 * @param request
	 * @return
	 */
	public static boolean isAnonymousUser(HttpServletRequest request) {
		if (request.getRemoteUser() == null){
			return true;
		}
		else{
			return false;
		}
	}

}
