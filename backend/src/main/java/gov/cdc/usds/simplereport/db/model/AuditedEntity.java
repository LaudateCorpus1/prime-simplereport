package gov.cdc.usds.simplereport.db.model;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Immutable;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * A base entity class for things that have UUID primary key and auto-populated
 * creation/modification timestamps. 
 */
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@DynamicUpdate
public abstract class AuditedEntity {

	@Column(updatable = false, nullable = false)
	@Id
	@GeneratedValue(generator = "UUID4")
	private UUID internalId;

	@Column(updatable = false)
	@CreatedDate
	private Date createdAt;

	@Column
	@LastModifiedDate
	private Date updatedAt;

	@CreatedBy
	@Immutable // not sure this is needed. Not sure it works if it is. :-(
	@ManyToOne(optional = false)
	@JoinColumn(name="created_by", updatable = false)
	private ApiUser createdBy;

	@LastModifiedBy
	@ManyToOne(optional = false)
	@JoinColumn(name="updated_by")
	private ApiUser updatedBy;

	public UUID getInternalId() {
		return internalId;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}
}
