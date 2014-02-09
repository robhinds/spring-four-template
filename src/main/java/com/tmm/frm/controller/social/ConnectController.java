package com.tmm.frm.controller.social;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tmm.frm.interceptors.FacebookConnectionInterceptor;
import com.tmm.frm.interceptors.TwitterConnectionInterceptor;

/**
 * Controller implementation overrides the standard SpringSocial {@link ConnectController} 
 * to redirect users to the home page once they have authenticated against third party sites
 * 
 * @author robert.hinds
 *
 */
@Controller
@RequestMapping("/connect")
public class ConnectController extends org.springframework.social.connect.web.ConnectController {

	@Inject TwitterConnectionInterceptor twitterConnectionInterceptor;
	@Inject FacebookConnectionInterceptor facebookConnectionInterceptor;
	
	@Inject
	public ConnectController(ConnectionFactoryLocator connectionFactoryLocator, ConnectionRepository connectionRepository) {
		super(connectionFactoryLocator, connectionRepository);
	}

	@Override
	protected String connectedView(String providerId) {
		return "redirect:/";
	}
	
	@PostConstruct
	public void initIt(){
		this.addInterceptor(twitterConnectionInterceptor);
		this.addInterceptor(facebookConnectionInterceptor);
	}

}
