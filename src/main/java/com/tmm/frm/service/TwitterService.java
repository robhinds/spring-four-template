package com.tmm.frm.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.twitter.api.CursoredList;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.social.twitter.api.TwitterProfile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tmm.frm.domain.ContactProfile;
import com.tmm.frm.domain.Profile;


@Service
public class TwitterService {

	@Autowired Twitter twitter;
	@Autowired ProfileService profileService;
	@Autowired ContactService contactService;
	
	public List<TwitterProfile> getTwitterContacts(){
		CursoredList<TwitterProfile> followers = twitter.friendOperations().getFollowers();
		List<TwitterProfile> accumulatedFollowers = new ArrayList<TwitterProfile>();
		accumulatedFollowers.addAll(followers);
		while ( followers.hasNext() ){
			followers = twitter.friendOperations().getFollowersInCursor( followers.getNextCursor() );
			accumulatedFollowers.addAll(followers);
		}
		return accumulatedFollowers;
	}
	
	@Transactional
	public void importTwitterContacts(){
		Profile p = profileService.getLoggedInProfile();
		if (p.isTwitterConnected()){
			List<TwitterProfile> contacts = getTwitterContacts();
			for (TwitterProfile c : contacts){
				com.tmm.frm.domain.TwitterProfile tProfile = new com.tmm.frm.domain.TwitterProfile();
				tProfile.setDescription(c.getDescription());
				tProfile.setDisplayName(c.getScreenName());
				tProfile.setLocation(c.getLocation());
				tProfile.setThirdPartyImageUrl(c.getProfileImageUrl());
				tProfile.setThirdPartyProfileUrl(c.getProfileUrl());
				tProfile.setWebsite(c.getUrl());
				
				//Link contact profile with twitter profile
				ContactProfile cProfile = new ContactProfile();
				tProfile.setLinkedContactProfile(cProfile);
				cProfile.addSocialProfile(tProfile);
				
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