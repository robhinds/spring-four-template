/**
 * 
 */
package com.tmm.frm.security;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.validator.constraints.Email;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.tmm.frm.domain.Profile;

/**
 * Class that represents an individual user account. Contains user details such
 * as username, password, name, email and the set of Roles that the person has.
 * 
 * @author robert.hinds
 * 
 */
@Entity
@Table(name = "CRM_ACCOUNT")
public class Account implements Serializable {

	public static final long serialVersionUID = 42L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Email
	@Column(name = "email", unique = true, nullable = false)
	private String email;
	
	private String password;
	private String confirmationId;
	private String resetPasswordId;
	private Timestamp resetPasswordExpiryTime;
	private boolean resetComplete;
	private boolean confirmed = false;

	@OneToOne(mappedBy = "linkedAccount", cascade = { CascadeType.ALL }, optional = false)
	private Profile userProfile;

	@ManyToMany(fetch = FetchType.EAGER)
	private Set<Role> roles;

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public void addRole(Role role) {
		if (getRoles() != null) {
			getRoles().add(role);
		} else {
			Set<Role> r = new HashSet<Role>();
			r.add(role);
			setRoles(r);
		}
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	protected void setPassword(String password) {
		this.password = password;
	}

	public void setAndEncodePassword(String password) {
		String encodedPassword = new BCryptPasswordEncoder().encode(password);
		setPassword(encodedPassword);
	}

	public String getPassword() {
		return password;
	}

	public String getConfirmationId() {
		return confirmationId;
	}

	public void setConfirmationId(String confirmationId) {
		this.confirmationId = confirmationId;
	}

	public boolean isConfirmed() {
		return confirmed;
	}

	public void setConfirmed(boolean confirmed) {
		this.confirmed = confirmed;
	}

	public void setResetPasswordId(String resetPasswordId) {
		this.resetPasswordId = resetPasswordId;
	}

	public String getResetPasswordId() {
		return resetPasswordId;
	}

	public void setResetComplete(boolean resetComplete) {
		this.resetComplete = resetComplete;
	}

	public boolean isResetComplete() {
		return resetComplete;
	}

	public void setResetPasswordExpiryTime(Timestamp resetPasswordExpiryTime) {
		this.resetPasswordExpiryTime = resetPasswordExpiryTime;
	}

	public Timestamp getResetPasswordExpiryTime() {
		return resetPasswordExpiryTime;
	}

	public Profile getUserProfile() {
		return userProfile;
	}

	public void setUserProfile(Profile userProfile) {
		this.userProfile = userProfile;
	}
}