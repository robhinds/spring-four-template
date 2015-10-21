package com.tmm.frm.controller;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.tmm.frm.domain.Profile;
import com.tmm.frm.helper.UserHelper;
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
@RequestMapping("/index.html")
public class HomeController {
	
	@Autowired ProfileService profileService; 

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView homepage(HttpServletRequest request) throws Exception {
		boolean isAnon = UserHelper.isAnonymousUser(request);
		if (isAnon){
			return new ModelAndView("homepage");
		} else {
			Profile p = profileService.getLoggedInProfile();
			HashMap<String, Object> model = new HashMap<String,Object>();
			model.put("twitterConnected", p.isTwitterConnected());
			model.put("facebookConnected", p.isFacebookConnected());
			model.put("googleConnected", false);
			model.put("linkedinConnected", p.isLinkedInConnected());
			return new ModelAndView("dashboard", model);
		}
	}
}
