package com.tmm.frm.connection.repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.jasypt.util.text.BasicTextEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionData;
import org.springframework.social.connect.ConnectionFactory;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.ConnectionKey;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.tmm.frm.domain.Profile;
import com.tmm.frm.domain.social.SocialConnection;
import com.tmm.frm.service.ProfileService;


/**
 * Custom implementation of the Spring {@link ConnectionRepository} using
 * Hibernate persistence.
 * 
 * This class is required to persist connection details for third party
 * applications connecting through Spring-Social
 * 
 * @author robert.hinds
 * 
 */
public class HibernateConnectionRepository implements ConnectionRepository {

	private final long accountId;
	private final ConnectionFactoryLocator connectionFactoryLocator;
	private final BasicTextEncryptor textEncryptor;

	@Autowired
	private ProfileService profileService;

	public void setProfileService(ProfileService profileService) {
		this.profileService = profileService;
	}

	public HibernateConnectionRepository(long userId, ConnectionFactoryLocator connFactoryLocator, BasicTextEncryptor textEncryptor) {
		this.connectionFactoryLocator = connFactoryLocator;
		this.textEncryptor = textEncryptor;
		this.accountId = userId;
	}

	@Override
	@Transactional
	public void addConnection(Connection<?> connection) {
		try {
			ConnectionData data = connection.createData();
			String provId = data.getProviderId();
			String provUserId = data.getProviderUserId();
			String displayName = data.getDisplayName();
			String profUrl = data.getProfileUrl();
			String imageUrl = data.getImageUrl();
			String accessToken = encrypt(data.getAccessToken());
			String secret = encrypt(data.getSecret());
			String refreshToken = encrypt(data.getRefreshToken());
			Long expireTime = data.getExpireTime();
			SocialConnection sc = new SocialConnection(provId, provUserId, displayName, profUrl, imageUrl, accessToken, secret, refreshToken, expireTime);
			profileService.addConnectionToAccount(accountId, sc);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public MultiValueMap<String, Connection<?>> findAllConnections() {
		Profile profile = getUserProfile();
		Set<SocialConnection> sConns = profile.getConnections();
		MultiValueMap<String, Connection<?>> connections = new LinkedMultiValueMap<String, Connection<?>>();
		Set<String> registeredProviderIds = connectionFactoryLocator.registeredProviderIds();
		for (String registeredProviderId : registeredProviderIds) {
			connections.put(registeredProviderId, Collections.<Connection<?>> emptyList());
		}
		for (SocialConnection c : sConns) {
			Connection<?> connection = getConnectionFromSocialConnection(c);
			String providerId = connection.getKey().getProviderId();
			if (connections.get(providerId).size() == 0) {
				connections.put(providerId, new LinkedList<Connection<?>>());
			}
			connections.add(providerId, connection);
		}
		return connections;
	}

	private Profile getUserProfile() {
		Profile profile = profileService.loadProfileFromAccount(accountId);
		return profile;
	}

	@Override
	public List<Connection<?>> findConnections(String providerId) {
		List<Connection<?>> connections = new ArrayList<Connection<?>>();
		for (SocialConnection c : getUserProfile().getConnections()) {
			if (c.getProviderId().equalsIgnoreCase(providerId)) {
				connections.add(getConnectionFromSocialConnection(c));
			}
		}
		return connections;
	}

	@Override
	public <A> List<Connection<A>> findConnections(Class<A> apiType) {
		List<?> connections = findConnections(connectionFactoryLocator.getConnectionFactory(apiType).getProviderId());
		return (List<Connection<A>>) connections;
	}

	@Override
	public MultiValueMap<String, Connection<?>> findConnectionsToUsers(MultiValueMap<String, String> arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <A> Connection<A> findPrimaryConnection(Class<A> apiType) {
		String providerId = connectionFactoryLocator.getConnectionFactory(apiType).getProviderId();
		return (Connection<A>) findPrimaryConnection(providerId);
	}

	private Connection<?> findPrimaryConnection(String providerId) {
		Profile profile = getUserProfile();
		if (profile.getConnections().size() > 0) {
			for (SocialConnection sc : profile.getConnections()) {
				if (sc.getProviderId().equals(providerId)) {
					return getConnectionFromSocialConnection(sc);
				}
			}
			return null;
		} else {
			return null;
		}
	}

	public ConnectionData findPrimaryConnectionData(String providerId) {
		Profile profile = getUserProfile();
		if (profile.getConnections().size() > 0) {
			for (SocialConnection sc : profile.getConnections()) {
				if (sc.getProviderId().equals(providerId)) {
					return getConnectionDataFromSocialConnection(sc);
				}
			}
			return null;
		} else {
			return null;
		}
	}

	@Override
	public Connection<?> getConnection(ConnectionKey key) {
		for (SocialConnection c : getUserProfile().getConnections()) {
			if (c.getProviderId().equalsIgnoreCase(key.getProviderId()) && c.getProviderUserId().equalsIgnoreCase(key.getProviderUserId())) {
				return getConnectionFromSocialConnection(c);
			}
		}
		return null;
	}

	@Override
	public <A> Connection<A> getConnection(Class<A> apiType, String providerUserId) {
		String providerId = connectionFactoryLocator.getConnectionFactory(apiType).getProviderId();
		return (Connection<A>) getConnection(new ConnectionKey(providerId, providerUserId));
	}

	@Override
	public <A> Connection<A> getPrimaryConnection(Class<A> arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void removeConnection(ConnectionKey arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeConnections(String arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateConnection(Connection<?> connection) {
		ConnectionData data = connection.createData();
		profileService.updateSocialConnection(accountId, data.getProviderId(), data.getProviderUserId(), encrypt(data.getAccessToken()), data.getDisplayName(), data.getExpireTime(),
				data.getImageUrl(), data.getProfileUrl(), encrypt(data.getRefreshToken()), encrypt(data.getSecret()));
	}

	private Connection getConnectionFromSocialConnection(SocialConnection sConn) {
		ConnectionData connectionData = new ConnectionData(sConn.getProviderId(), sConn.getProviderUserId(), sConn.getDisplayName(), sConn.getProfileUrl(), sConn.getImageUrl(),
				decrypt(sConn.getAccessToken()), decrypt(sConn.getSecret()), decrypt(sConn.getRefreshToken()), expireTime(sConn.getExpireTime()));
		ConnectionFactory<?> connectionFactory = connectionFactoryLocator.getConnectionFactory(connectionData.getProviderId());
		return connectionFactory.createConnection(connectionData);
	}

	/**
	 * This is a badass method. Do not use this unless you are really sure this
	 * is what you want to do. It will provide the raw, decrypted accessToken to
	 * a thirdparty..
	 * 
	 * @param sConn
	 * @return
	 */
	public String getAccessTokenFromSocialConnection(SocialConnection sConn) {
		System.out.println("Yo");
		System.out.println(sConn.getAccessToken());
		System.out.println(decrypt(sConn.getAccessToken()));
		return decrypt(sConn.getAccessToken());
	}

	private ConnectionData getConnectionDataFromSocialConnection(SocialConnection sConn) {
		ConnectionData connectionData = new ConnectionData(sConn.getProviderId(), sConn.getProviderUserId(), sConn.getDisplayName(), sConn.getProfileUrl(), sConn.getImageUrl(),
				decrypt(sConn.getAccessToken()), decrypt(sConn.getSecret()), decrypt(sConn.getRefreshToken()), expireTime(sConn.getExpireTime()));
		return connectionData;
	}

	private String decrypt(String encryptedText) {
		return encryptedText != null ? textEncryptor.decrypt(encryptedText) : encryptedText;
	}

	private String encrypt(String text) {
		return text != null ? textEncryptor.encrypt(text) : text;
	}

	private Long expireTime(Long expireTime) {
		return expireTime == null || expireTime == 0 ? null : expireTime;
	}
}
