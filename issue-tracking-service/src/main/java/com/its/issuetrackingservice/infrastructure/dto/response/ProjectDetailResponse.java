package com.its.issuetrackingservice.infrastructure.dto.response;

import com.its.issuetrackingservice.infrastructure.dto.AuditableEntityDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.util.List;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class ProjectDetailResponse extends AuditableEntityDto {
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

	@Schema(name = "isActive", description = "The abbreviation of the project", type = "String", example = "ITS")
	private Boolean isActive;

	@Schema(name = "sprints", description = "List of sprints of the project", type = "List")
	private List<SprintResponse> sprints;

	@Schema(name = "states", description = "List of states of the project", type = "List")
	private List<StateResponse> states;

	@Schema(name = "categories", description = "List of categories of the project", type = "List")
	private List<CategoryResponse> categories;
}
