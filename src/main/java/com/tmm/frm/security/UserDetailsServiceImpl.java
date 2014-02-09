package com.tmm.frm.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tmm.frm.service.AccountService;

/**
 * Custom implementation to support Spring Security logon based on custom
 * authentication of persisted databases
 * 
 * @author robert.hinds
 * 
 */
@Service("userService")
public class UserDetailsServiceImpl implements UserDetailsService, InitializingBean {

	@Autowired
	private AccountService accountService;

	public void afterPropertiesSet() throws Exception {
	}

	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException, DataAccessException {
		username = username.toLowerCase();
		try {
			Account account = accountService.loadUserAccountByEmail(username);
			if (account == null) {
				throw new UsernameNotFoundException("Could not find email: " + username + "in the DB.");
			}

			List<GrantedAuthority> auths = new ArrayList<GrantedAuthority>();
			for (Role r : account.getRoles()) {
				auths.add(new SimpleGrantedAuthority(r.getRole()));
			}
			ApplicationUser user = null;
			try {
				user = new ApplicationUser(new Long(account.getId()), username, account.getPassword(), true, true, true, true, auths);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			return user;
		} catch (Exception e) {
			e.printStackTrace();
			throw new UsernameNotFoundException(username + "not found", e);
		}
	}

}
