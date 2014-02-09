package com.tmm.frm.controller.api;

import java.util.HashMap;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.tmm.frm.core.exception.ApiNotAuthorisedException;

/**
 * Controller catches all API exceptions and returns consistent responses
 * 
 * @author robert.hinds
 * 
 */
@ControllerAdvice
public class ApiExceptionController {
	
	/**
	 * Overkill - this should never get called as the security config should just restrict
	 * appropriate urls to authorised only, but just playing with some ideas. Could use this
	 * pattern if we wanted particular other responses such as 304s etc
	 * @param e
	 * @return
	 */
	@ExceptionHandler(ApiNotAuthorisedException.class)
	@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
	public @ResponseBody HashMap<String, String> handleException(ApiNotAuthorisedException e) {
		HashMap<String,String> response = new HashMap<String,String>();
		response.put("code", "401");
		response.put("status", "Not authorised to access the requested resource");
		return response;
	}

}
