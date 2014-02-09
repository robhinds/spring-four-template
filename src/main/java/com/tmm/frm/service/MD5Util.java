package com.tmm.frm.service;

import java.io.UnsupportedEncodingException;

import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;

import com.tmm.frm.security.Account;

@Component
public class MD5Util {
	
	public String createGravHash(Account viewedUser) {
		String email = viewedUser.getEmail().trim().toLowerCase(); 
		try {
			return DigestUtils.md5DigestAsHex(email.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			return ""; 
		}
	}
}