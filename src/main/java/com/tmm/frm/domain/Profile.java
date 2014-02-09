/**
 * 
 */
package com.tmm.frm.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.tmm.frm.domain.social.SocialConnection;
import com.tmm.frm.security.Account;

/**
 * Class responsible for persisting user profile details
 * 
 * @author robert.hinds
 * 
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(name = "CRM_USERPROFILE")
public class Profile extends PersistableObject {

	private static final long serialVersionUID = 3458607287170514439L;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "account")
	private Account linkedAccount;
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<SocialConnection> connections = new HashSet<SocialConnection>();

	@OneToMany(mappedBy = "linkedProfile", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<ContactProfile> contacts = new HashSet<ContactProfile>();

	public Account getLinkedAccount() {
		return linkedAccount;
	}

	public void setLinkedAccount(Account linkedAccount) {
		this.linkedAccount = linkedAccount;
	}

	public Set<SocialConnection> getConnections() {
		return connections;
	}

	public void setConnections(Set<SocialConnection> connections) {
		this.connections = connections;
	}

	public void addConnection(SocialConnection connection) {
		this.connections.add(connection);
	}

	public void removeConnection(SocialConnection connection) {
		this.connections.remove(connection);
	}
	
	public boolean isTwitterConnected() {
		for (SocialConnection sc : getConnections()) {
			if ("twitter".equals(sc.getProviderId())) {
				return true;
			}
		}
		return false;
	}

	public boolean isFacebookConnected() {
		for (SocialConnection sc : getConnections()) {
			if ("facebook".equals(sc.getProviderId())) {
				return true;
			}
		}
		return false;
	}

	public boolean isLinkedInConnected() {
		for (SocialConnection sc : getConnections()) {
			if ("linkedin".equals(sc.getProviderId())) {
				return true;
			}
		}
		return false;
	}

	public Set<ContactProfile> getContacts() {
		return contacts;
	}

	public void setContacts(Set<ContactProfile> contacts) {
		this.contacts = contacts;
	}

	public void addContact(ContactProfile contact) {
		this.contacts.add(contact);
	}

	public void removeContact(ContactProfile contact) {
		this.contacts.remove(contact);
	}
	
}
