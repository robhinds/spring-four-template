package com.tmm.frm.controller.api;


import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tmm.frm.domain.ContactProfile;
import com.tmm.frm.domain.Profile;
import com.tmm.frm.service.ProfileService;

/**
 * Controller class that handles all requests for the site home page - it
 * determines whether or not the user is logged in and either displays the site
 * welcome/login page or directs the user to their user profile page
 * 
 * @author robert.hinds
 * 
 */
@Controller
@RequestMapping("/api/contacts")
public class ContactsController {
	
	@Autowired ProfileService profileService;

	@RequestMapping(value="/list", method = RequestMethod.GET)
	@ResponseBody
	public Set<ContactProfile> homepage(HttpServletRequest request) throws Exception {
		Profile p = profileService.getLoggedInProfile();
		return p.getContacts();
	}
}
