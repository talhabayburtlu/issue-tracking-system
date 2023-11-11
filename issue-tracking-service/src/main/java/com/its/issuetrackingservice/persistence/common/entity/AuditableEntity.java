package com.its.issuetrackingservice.persistence.common.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.time.OffsetDateTime;

@Data
@EqualsAndHashCode
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class AuditableEntity implements Serializable {

	@CreatedDate
	@Column(name = "created_date", columnDefinition = "TIMESTAMP", updatable = false)
	@ColumnDefault("CURRENT_TIMESTAMP")
	@ToString.Exclude
	private OffsetDateTime createdDate;

	@CreatedBy
	@Column(name = "created_by", length = 50, nullable = false, updatable = false)
	@ColumnDefault(value = "'Default'")
	@ToString.Exclude
	private String createdBy;

	@LastModifiedDate
	@Column(name = "change_date", columnDefinition = "TIMESTAMP")
	@ColumnDefault("CURRENT_TIMESTAMP")
	@ToString.Exclude
	private OffsetDateTime changedDate;

	@LastModifiedBy
	@Column(name = "change_by", length = 50, nullable = false)
	@ColumnDefault(value = "'Default'")
	@ToString.Exclude
	private String changedBy;

}
