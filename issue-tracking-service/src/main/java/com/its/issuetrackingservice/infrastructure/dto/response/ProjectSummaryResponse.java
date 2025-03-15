package com.its.issuetrackingservice.infrastructure.dto.response;

import com.its.issuetrackingservice.infrastructure.dto.AuditableEntityDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class ProjectSummaryResponse extends AuditableEntityDto {
	@Serial
	private static final long serialVersionUID = -3254824055423661547L;

	@EqualsAndHashCode.Include
	@Schema(name = "id", description = "The id of the project", type = "Long", example = "1")
	private Long id;

	@Schema(name = "name", description = "The name of the project", type = "String", example = "Issue Tracking System")
	private String name;

	@Schema(name = "description", description = "The description of the project", type = "String", example = "Issue Tracking System (ITS) is a platform designed for agile organizations to track their progress.")
	private String description;

	@Schema(name = "abbreviation", description = "The abbreviation of the project", type = "String", example = "ITS")
	private String abbreviation;

	@Schema(name = "isActive", description = "Is project actively maintaining", type = "Boolean", example = "true")
	private Boolean isActive;
}
