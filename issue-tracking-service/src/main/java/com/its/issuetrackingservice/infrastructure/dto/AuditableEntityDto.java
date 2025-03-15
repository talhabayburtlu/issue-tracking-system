package com.its.issuetrackingservice.infrastructure.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.time.OffsetDateTime;

@SuperBuilder
@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public abstract class AuditableEntityDto implements Serializable {

	@ToString.Exclude
	@Schema(name = "createdDate", description = "The timestamp when this entity is created", type = "OffsetDateTime", example = "2025-03-15T19:46:05.434Z")
	private OffsetDateTime createdDate;

	@Schema(name = "createdBy", description = "The username of the user which creates this entity", type = "String", example = "talha.bayburtlu")
	@ToString.Exclude
	private String createdBy;

	@ToString.Exclude
	@Schema(name = "changedDate", description = "The timestamp when this entity is updated", type = "OffsetDateTime", example = "2025-03-15T19:46:05.434Z")
	private OffsetDateTime changedDate;

	@ToString.Exclude
	@Schema(name = "changedBy", description = "The username of the user which updates this entity", type = "String", example = "talha.bayburtlu")
	private String changedBy;

	@ToString.Exclude
	@Schema(name = "rowStamp", description = "The stamp of the entity that verifies the version of the entity", type = "Long", example = "1")
	private Long rowStamp;

}
