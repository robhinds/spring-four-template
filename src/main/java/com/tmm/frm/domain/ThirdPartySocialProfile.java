package com.tmm.frm.domain;


import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;



/**
 * Class responsible for persisting a third party profile 
 * 
 * @author robert.hinds
 * 
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(name = "CRM_THIRD_PARTY_PROFILE")
public class ThirdPartySocialProfile extends PersistableObject {

	private static final long serialVersionUID = 3458607287170514439L;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "linkedContactProfile")
	private ContactProfile linkedContactProfile;
	
	private String displayName;
	private String thirdPartyProfileUrl;
	private String thirdPartyImageUrl;
	private String description;
	private String location;
	private String website;
	private String firstName;
	private String lastName;
	
	@JsonIgnore
	public ContactProfile getLinkedContactProfile() {
		return linkedContactProfile;
	}
	public void setLinkedContactProfile(ContactProfile linkedContactProfile) {
		this.linkedContactProfile = linkedContactProfile;
	}
	@JsonProperty
	public String getDisplayName() {
		return displayName;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	@JsonProperty
	public String getThirdPartyProfileUrl() {
		return thirdPartyProfileUrl;
	}
	public void setThirdPartyProfileUrl(String thirdPartyProfileUrl) {
		this.thirdPartyProfileUrl = thirdPartyProfileUrl;
	}
	@JsonProperty
	public String getThirdPartyImageUrl() {
		return thirdPartyImageUrl;
	}
	public void setThirdPartyImageUrl(String thirdPartyImageUrl) {
		this.thirdPartyImageUrl = thirdPartyImageUrl;
	}
	@JsonProperty
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	@JsonProperty
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	@JsonProperty
	public String getWebsite() {
		return website;
	}
	public void setWebsite(String website) {
		this.website = website;
	}
	@JsonProperty
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	@JsonProperty
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	
}
