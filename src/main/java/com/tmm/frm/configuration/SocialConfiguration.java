package com.tmm.frm.configuration;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.support.ConnectionFactoryRegistry;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.connect.FacebookConnectionFactory;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.social.twitter.connect.TwitterConnectionFactory;

import com.tmm.frm.connection.repository.HibernateUsersConnectionRepository;
import com.tmm.frm.controller.social.ConnectController;
import com.tmm.frm.interceptors.FacebookConnectionInterceptor;
import com.tmm.frm.interceptors.TwitterConnectionInterceptor;
import com.tmm.frm.security.ApplicationUser;


/**
 * Configuration class making use of the Spring @Configuration annotation,
 * allowing us to configure beans in code rather than in an xml file.
 * 
 * This controls the beans required to connect to the Spring-Social libraries
 * 
 * @author robert.hinds
 * 
 */
@Configuration
public class SocialConfiguration {

	private String twitterKey = "TODO PUT YOU APP KEY HERE";
	private String twitterSecret = "TODO PUT YOU APP SECRET HERE";
	private String facebookKey = "TODO PUT YOU APP KEY HERE";
	private String facebookSecret = "TODO PUT YOU APP SECRET HERE";
	
	@Autowired ConnectController connectController;
	
	
	@Bean public TwitterConnectionInterceptor twitterConnectionInterceptor(){
		TwitterConnectionInterceptor interceptor = new TwitterConnectionInterceptor();
		return interceptor;
	}
	
	@Bean public FacebookConnectionInterceptor facebookConnectionInterceptor(){
		FacebookConnectionInterceptor interceptor = new FacebookConnectionInterceptor();
		return interceptor;
	}

	
	/**
	 * When a new provider is added to the app, register its
	 * {@link ConnectionFactory} here..
	 * 
	 */
	@Bean public ConnectionFactoryLocator connectionFactoryLocator() {
		ConnectionFactoryRegistry registry = new ConnectionFactoryRegistry();
		registry.addConnectionFactory(new TwitterConnectionFactory(twitterKey, twitterSecret));
		registry.addConnectionFactory(new FacebookConnectionFactory(facebookKey, facebookSecret));
		return registry;
	}

	/**
	 * Singleton data access object providing access to connections across all
	 * users.
	 */
	@Bean
	@Scope(value = "singleton", proxyMode = ScopedProxyMode.INTERFACES)
	public UsersConnectionRepository usersConnectionRepository() {
		HibernateUsersConnectionRepository repository = new HibernateUsersConnectionRepository(connectionFactoryLocator());
		return repository;
	}

	/**
	 * Request-scoped data access object providing access to the current user's
	 * connections.
	 */
	@Bean
	@Scope(value = "request", proxyMode = ScopedProxyMode.INTERFACES)
	public ConnectionRepository connectionRepository() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null) {
			throw new IllegalStateException("Unable to get a ConnectionRepository: no user signed in");
		}
		ApplicationUser user = (ApplicationUser) authentication.getPrincipal();
		return usersConnectionRepository().createConnectionRepository(String.valueOf(user.getAccountId()));
	}

	@Bean
	@Scope(value = "request", proxyMode = ScopedProxyMode.INTERFACES)
	public Twitter twitter() {
		if (connectionRepository().findPrimaryConnection(Twitter.class) != null) {
			return connectionRepository().findPrimaryConnection(Twitter.class).getApi();
		}
		return null;
	}


	@Bean
	@Scope(value = "request", proxyMode = ScopedProxyMode.INTERFACES)
	public Facebook facebook() {
		if (connectionRepository().findPrimaryConnection(Facebook.class) != null) {
			return connectionRepository().findPrimaryConnection(Facebook.class).getApi();
		}
		return null;
	}
}