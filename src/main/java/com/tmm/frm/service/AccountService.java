package com.tmm.frm.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tmm.frm.core.exception.CustomException;
import com.tmm.frm.core.exception.CustomExceptionCode;
import com.tmm.frm.domain.Profile;
import com.tmm.frm.security.Account;
import com.tmm.frm.security.ApplicationUser;
import com.tmm.frm.security.Role;
import com.tmm.frm.security.dao.AccountDAO;
import com.tmm.frm.security.dao.RoleDAO;

@Service("accountService")
public class AccountService {
	@Autowired
	private AccountDAO accountDAO;
	@Autowired
	private RoleDAO roleDAO;
	@Autowired
	private Validator validator;


	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<Account> findAllAccounts() {
		return accountDAO.find();
	}

	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public Account loadAccount(long id) {
		return accountDAO.read(Account.class, new Long(id));
	}

	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public Account loadUserAccountByEmail(String email) {
		return accountDAO.loadUserAccountByEmail(email);
	}

	@Transactional
	public void removeAccount(long id) {
		accountDAO.delete(accountDAO.read(Account.class, new Long(id)));
	}

	@Transactional
	public Account storeAccount(Account account) {
		return accountDAO.merge(account);
	}

	public Account createNewBatchUser(String email, String password) {
		return createNewBatchUser(email, password, null, null);
	}

	@Transactional
	public Account createNewUser(String email, String password) throws CustomException {
		if (loadUserAccountByEmail(email) == null) {
			Account account = new Account();
			account.setEmail(email);
			Set<ConstraintViolation<Account>> result = validator.validate(account);
			if ((result.size() == 0)) {
				Role r = loadOrCreateRole(Role.ROLE_USER);
				account.addRole(r);
				Profile userProf = new Profile();
				userProf.setLinkedAccount(account);
				account.setUserProfile(userProf);

				UUID confirmationId = UUID.randomUUID();
				account.setConfirmationId(confirmationId.toString());

				accountDAO.persist(account);
				account.setAndEncodePassword(password);
				return account;
			}
			throw new CustomException(CustomExceptionCode.USER004_INVALIDUSERNAME, "Attempting to register user with invalid username");
		}
		throw new CustomException(CustomExceptionCode.USER003_DUPLICATEEMAIL, "Attempting to register user with existing email");
	}

	@Transactional
	public Role loadOrCreateRole(String authority) {
		Role r = roleDAO.loadRoleByAuthority(authority);
		if (r == null) {
			r = new Role();
			r.setRole(authority);
			roleDAO.persist(r);
		}
		return r;
	}

	@Transactional
	public Account createNewBatchUser(String email, String password, String firstName, String lastName) {
		if (loadUserAccountByEmail(email) == null) {
			Account account = new Account();
			account.setEmail(email);
			Role r = loadOrCreateRole(Role.ROLE_USER);
			account.addRole(r);
			account.setConfirmed(true);

			accountDAO.persist(account);
			account.setAndEncodePassword(password);
			return account;
		}

		return null;
	}


	@Transactional
	public void setCredentials() {
		Account account = loadUserAccountByEmail("admin@admin.com");
		if (account == null) {
			account = createNewBatchUser("admin@admin.com", "admin");
		}
		List<GrantedAuthority> auths = new ArrayList<GrantedAuthority>();
		auths.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
		ApplicationUser user = new ApplicationUser(new Long(account.getId()), account.getEmail(), account.getPassword(), true, true, true, true, auths);
		Authentication auth = new TestingAuthenticationToken(user, "ignored", auths);
		auth.setAuthenticated(true);
		SecurityContextHolder.getContext().setAuthentication(auth);
	}

	public void clearCredentials() {
		SecurityContextHolder.getContext().setAuthentication(null);
	}

	@Transactional
	public void confirmAccount(long accId) {
		Account acc = loadAccount(accId);
		acc.setConfirmed(true);
	}

}