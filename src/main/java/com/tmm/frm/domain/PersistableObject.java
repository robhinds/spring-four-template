/**
 * 
 */
package com.tmm.frm.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;




import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.tmm.frm.security.Account;
import com.tmm.frm.service.ApplicationService;

/**
 * Persistable entity base class for all domain objects - this is so all objects
 * have a common set of fields - ID, and creation/modification details
 * 
 * @author robert.hinds
 */

@MappedSuperclass
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.NONE, getterVisibility = JsonAutoDetect.Visibility.NONE)
public abstract class PersistableObject implements Serializable {

	private static final long serialVersionUID = 1537058920585673529L;

	@Id
	@TableGenerator(name = "TABLE_GEN", table = "SEQUENCE", pkColumnName = "SEQ_NAME", valueColumnName = "SEQ_COUNT", pkColumnValue = "PO_SEQ")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "TABLE_GEN")
	protected Long id;

	@Column(name = "datcreated", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date creationDate;

	@Column(name = "datemodified", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date modifiedDate;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "createdby", nullable = false)
	private Account creationAccount;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "lastmodifiedby", nullable = false)
	private Account modificationAccount;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof PersistableObject)) {
			return false;
		}
		PersistableObject other = (PersistableObject) obj;
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		return true;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	@JsonProperty
	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public Account getCreationAccount() {
		return creationAccount;
	}

	public void setCreationAccount(Account creationAccount) {
		this.creationAccount = creationAccount;
	}

	public Account getModificationAccount() {
		return modificationAccount;
	}

	public void setModificationAccount(Account modificationAccount) {
		this.modificationAccount = modificationAccount;
	}

	@PrePersist
	public void onCreate() {
		Date now = new Date();
		Account user = null;

		user = ApplicationService.getInstance().getLoggedInUserAccount();

		if (creationAccount == null) {
			creationAccount = user;
		}
		if (creationDate == null) {
			creationDate = now;
		}
		modificationAccount = user;
		modifiedDate = now;
	}
}
