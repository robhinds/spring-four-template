package com.tmm.frm.domain;


import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;


/**
 * Class responsible for persisting a Twitter profile 
 * @author robert.hinds
 */
@Entity
@Table(name = "CRM_THIRD_PARTY_PROFILE")
@DiscriminatorValue("twitter")
public class TwitterProfile extends ThirdPartySocialProfile {
	private static final long serialVersionUID = 1L; 
}