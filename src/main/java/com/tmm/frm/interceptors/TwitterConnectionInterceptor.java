package com.tmm.frm.interceptors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionFactory;
import org.springframework.social.connect.web.ConnectInterceptor;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.request.WebRequest;

import com.tmm.frm.service.TwitterService;


public class TwitterConnectionInterceptor implements ConnectInterceptor<Twitter> {

	@Autowired TwitterService twitterService;
	
	@Override
	public void preConnect(ConnectionFactory<Twitter> connectionFactory, MultiValueMap<String, String> parameters, WebRequest request) { }

	@Override
	public void postConnect(Connection<Twitter> connection, WebRequest request) {
		twitterService.importTwitterContacts();
	}
}