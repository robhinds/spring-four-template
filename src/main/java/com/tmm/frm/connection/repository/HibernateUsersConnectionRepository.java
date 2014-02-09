package com.tmm.frm.connection.repository;

import java.util.List;
import java.util.Set;

import org.jasypt.util.text.BasicTextEncryptor;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.UsersConnectionRepository;

/**
 * Custom implementation of the {@link UsersConnectionRepository} using
 * Hibernate
 * 
 * @author robert.hinds
 * 
 */
public class HibernateUsersConnectionRepository implements UsersConnectionRepository {

	public HibernateUsersConnectionRepository(ConnectionFactoryLocator connectionFactoryLocator) {
		this.connectionFactoryLocator = connectionFactoryLocator;
	}

	private final ConnectionFactoryLocator connectionFactoryLocator;

	@Override
	public ConnectionRepository createConnectionRepository(String id) {
		if (id == null) {
			throw new IllegalArgumentException("userId cannot be null");
		}
		Long userId = Long.parseLong(id);
		BasicTextEncryptor textEncryptor = new BasicTextEncryptor();
		textEncryptor.setPassword("6m0u1i1r7f1i1e7l9d2");
		return new HibernateConnectionRepository(userId, connectionFactoryLocator, textEncryptor);
	}

	@Override
	public Set<String> findUserIdsConnectedTo(String arg0, Set<String> arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> findUserIdsWithConnection(Connection<?> arg0) {
		// TODO Auto-generated method stub
		return null;
	}

}
