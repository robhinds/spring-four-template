/**
 * 
 */
package com.tmm.frm.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.validator.constraints.Email;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Class responsible for persisting a contact's profile (incorporates any kind of social media
 * 
 * @author robert.hinds
 * 
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(name = "CRM_CONTACT_PROFILE")
public class ContactProfile extends PersistableObject {

	private static final long serialVersionUID = 3458607287170514439L;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "linkedProfile")
	private Profile linkedProfile;
	
	@OneToMany(mappedBy = "linkedContactProfile", cascade = CascadeType.ALL)
	private Set<ThirdPartySocialProfile> socialProfiles = new HashSet<ThirdPartySocialProfile>();

	@Email
	@Column(name = "email", unique = true)
	private String email;
	
	private String displayName;

	@JsonIgnore
	public Profile getLinkedProfile() {
		return linkedProfile;
	}

	public void setLinkedProfile(Profile linkedProfile) {
		this.linkedProfile = linkedProfile;
	}

	@JsonProperty
	public Set<ThirdPartySocialProfile> getSocialProfiles() {
		return socialProfiles;
	}

	public void setSocialProfiles(Set<ThirdPartySocialProfile> socialProfiles) {
		this.socialProfiles = socialProfiles;
	}

	public void addSocialProfile(ThirdPartySocialProfile socialProfile) {
		this.socialProfiles.add(socialProfile);
	}

	public void removeSocialProfile(ThirdPartySocialProfile socialProfile) {
		this.socialProfiles.remove(socialProfile);
	}

	@JsonProperty
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@JsonProperty
	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	
}
