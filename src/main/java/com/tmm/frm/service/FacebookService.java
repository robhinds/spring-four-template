package com.tmm.frm.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.FacebookProfile;
import org.springframework.social.facebook.api.PagedList;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tmm.frm.domain.ContactProfile;
import com.tmm.frm.domain.Profile;


@Service
public class FacebookService {

	@Autowired Facebook facebook;
	@Autowired ProfileService profileService;
	@Autowired ContactService contactService;
	
	public List<FacebookProfile> getFacebookContacts(){
		PagedList<FacebookProfile> followers = facebook.friendOperations().getFriendProfiles();
		List<FacebookProfile> accumulatedFollowers = new ArrayList<FacebookProfile>();
		accumulatedFollowers.addAll(followers);
		
		return accumulatedFollowers;
	}
	
	@Transactional
	public void importFacebookContacts(){
		Profile p = profileService.getLoggedInProfile();
		if (p.isFacebookConnected()){
			
			facebook.friendOperations().getFriendLists();
			
			List<FacebookProfile> contacts = getFacebookContacts();
			for (FacebookProfile c : contacts){
				com.tmm.frm.domain.FacebookProfile tProfile = new com.tmm.frm.domain.FacebookProfile();
				tProfile.setDescription(c.getAbout() + " - " + c.getBio() );
				tProfile.setDisplayName(c.getUsername());
				tProfile.setThirdPartyProfileUrl(c.getLink());
				tProfile.setWebsite(c.getWebsite());
				tProfile.setFirstName(c.getFirstName());
				tProfile.setLastName(c.getLastName());
				//Link contact profile with twitter profile
				ContactProfile cProfile = new ContactProfile();
				tProfile.setLinkedContactProfile(cProfile);
				cProfile.addSocialProfile(tProfile);
				cProfile.setEmail(c.getEmail());
				
				//link contact profile with user account
				cProfile.setLinkedProfile(p);
				p.addContact(cProfile);
				
				contactService.persistContactProfile(cProfile);
			}
		} else {
			throw new IllegalStateException("Unable to import from Twitter - user not connected to twitter");
		}
	}

}