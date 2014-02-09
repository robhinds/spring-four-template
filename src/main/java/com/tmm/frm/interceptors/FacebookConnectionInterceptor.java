package com.tmm.frm.interceptors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionFactory;
import org.springframework.social.connect.web.ConnectInterceptor;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.request.WebRequest;

import com.tmm.frm.service.FacebookService;


public class FacebookConnectionInterceptor implements ConnectInterceptor<Facebook> {

	@Autowired FacebookService facebookService;
	
	@Override
	public void preConnect(ConnectionFactory<Facebook> connectionFactory, MultiValueMap<String, String> parameters, WebRequest request) { }

	@Override
	public void postConnect(Connection<Facebook> connection, WebRequest request) {
		facebookService.importFacebookContacts();
	}
}