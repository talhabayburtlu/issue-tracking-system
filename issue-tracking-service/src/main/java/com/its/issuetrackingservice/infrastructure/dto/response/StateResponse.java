package com.its.issuetrackingservice.infrastructure.dto.response;

import com.its.issuetrackingservice.infrastructure.dto.AuditableEntityDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class StateResponse extends AuditableEntityDto {
	@Serial
	private static final long serialVersionUID = -3254824055423661547L;

	@EqualsAndHashCode.Include
	@Schema(name = "id", description = "The id of the state", type = "Long", example = "1")
	private Long id;

	@Schema(name = "title", description = "The title of the state", type = "String", example = "In Progress")
	private String title;

	@Schema(name = "description", description = "The description of the state", type = "String", example = "Issues that are in progress are belong to this category")
	private String description;

	@Schema(name = "isInitial", description = "The indicator of is initial state of the project ", type = "boolean" , example = "false")
	private Boolean isInitial;

	@Schema(name = "nextStateId", description = "The id of next state of current state", type = "Long" , example = "1")
	private Long nextStateId;
}
