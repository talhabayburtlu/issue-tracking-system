package com.its.issuetrackingservice.api.common.dto;

import java.io.Serializable;
import java.time.OffsetDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public abstract class AuditableEntityDto implements Serializable {

	@ToString.Exclude
	private OffsetDateTime createdDate;

	@ToString.Exclude
	private String createdBy;

	@ToString.Exclude
	private OffsetDateTime changedDate;

	@ToString.Exclude
	private String changedBy;

	@ToString.Exclude
	private Long rowStamp;

}