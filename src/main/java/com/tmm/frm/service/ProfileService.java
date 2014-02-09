package com.tmm.frm.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tmm.frm.core.dao.ProfileDAOImpl;
import com.tmm.frm.core.exception.CustomException;
import com.tmm.frm.core.exception.CustomExceptionCode;
import com.tmm.frm.domain.Profile;
import com.tmm.frm.domain.social.SocialConnection;
import com.tmm.frm.security.Account;
import com.tmm.frm.security.ApplicationUser;

@Service("profileService")
@Transactional
public class ProfileService {

	private EntityManager entityManager;

	@Autowired
	private AccountService accountService;

	@Autowired
	private ProfileDAOImpl profileDao;

	@PersistenceContext
	public void setEntityManager(EntityManager em) {
		this.entityManager = em;
	}

	public EntityManager getEntityManager() {
		return entityManager;
	}

	public void setProfileDao(ProfileDAOImpl profileDao) {
		this.profileDao = profileDao;
	}

	public void setAccountService(AccountService accountService) {
		this.accountService = accountService;
	}

	@Transactional
	public void createProfile(Profile p) {
		profileDao.persist(p);
	}

	@Transactional
	public void mergeProfile(Profile p) {
		profileDao.merge(p);
	}

	@Transactional
	public Profile loadProfile(long id) {
		return getEntityManager().find(Profile.class, id);
	}

	@Transactional
	public List<Profile> loadAllProfiles() {
		return profileDao.loadAllProfiles();
	}

	

	@Transactional
	public Profile loadProfileByEmail(String email)
			throws CustomException {
		Account acc = accountService.loadUserAccountByEmail(email);
		if (acc != null) {
			Profile user = acc.getUserProfile();
			if (user != null) {
				return user;
			}
		}

		// if nothing returned now then lets throw an exception
		throw new CustomException(CustomExceptionCode.USER001_INVALIDUSER,
				"No User/Team found matching email: " + email);
	}

	@Transactional
	public Profile loadProfileFromAccount(long accountId) {
		Account acc = entityManager.find(Account.class, accountId);
		Profile p = acc.getUserProfile();
		return p;
	}

	@Transactional
	public void addConnectionToAccount(long accountId, SocialConnection sc) {
		Profile p = loadProfileFromAccount(accountId);
		p.addConnection(sc);
		sc.setUser(p);
		entityManager.persist(sc);
	}

	@Transactional
	public void updateSocialConnection(long accountId, String providerId,
			String providerUserId, String accessToken, String displayName,
			Long expireTime, String imageUrl, String profileUrl,
			String refreshToken, String secret) {
		Profile p = loadProfileFromAccount(accountId);
		for (SocialConnection c : p.getConnections()) {
			if (c.getProviderId().equalsIgnoreCase(providerId)
					&& c.getProviderUserId().equalsIgnoreCase(providerUserId)) {
				c.setAccessToken(accessToken);
				c.setDisplayName(displayName);
				c.setExpireTime(expireTime);
				c.setImageUrl(imageUrl);
				c.setProfileUrl(profileUrl);
				c.setRefreshToken(refreshToken);
				c.setSecret(secret);
				break;
			}
		}
	}

	
	/**
	 * If authenticated request then returns the logged in user
	 * @return
	 */
	public Profile getLoggedInProfile() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null) {
			throw new IllegalStateException("Unable to get logged in profile - no user signed in");
		}
		ApplicationUser user = (ApplicationUser) authentication.getPrincipal();
		return accountService.loadAccount(user.getAccountId()).getUserProfile();
	}

}