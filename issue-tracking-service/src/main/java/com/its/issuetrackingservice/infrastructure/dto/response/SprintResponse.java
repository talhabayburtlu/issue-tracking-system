package com.its.issuetrackingservice.infrastructure.dto.response;

import com.its.issuetrackingservice.infrastructure.dto.AuditableEntityDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class SprintResponse extends AuditableEntityDto {
	@Serial
	private static final long serialVersionUID = -3254824055423661547L;

	@EqualsAndHashCode.Include
	@Schema(name = "id", description = "The id of the sprint", type = "Long", example = "1")
	private Long id;

	@Schema(name = "name", description = "The name of the sprint", type = "String", example = "ITS-5")
	private String name;

	@Schema(name = "period", description = "The period of the sprint in days", type = "Short", example = "14")
	private Short period;

	@Schema(name = "version", description = "The version of the sprint", type = "Short", example = "5")
	private Short version;
}
