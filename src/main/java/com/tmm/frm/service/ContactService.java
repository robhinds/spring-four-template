package com.tmm.frm.service;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Service;

import com.tmm.frm.domain.ContactProfile;
import com.tmm.frm.domain.ThirdPartySocialProfile;


@Service("contactService")
public class ContactService {

	private EntityManager entityManager;

	@PersistenceContext
	public void setEntityManager(EntityManager em) {
		this.entityManager = em;
	}
	
	public void persistContactProfile( ContactProfile cp ){
		entityManager.persist(cp);
	}
	
	public void persistSocialProfile( ThirdPartySocialProfile p ){
		entityManager.persist(p);
	}

}