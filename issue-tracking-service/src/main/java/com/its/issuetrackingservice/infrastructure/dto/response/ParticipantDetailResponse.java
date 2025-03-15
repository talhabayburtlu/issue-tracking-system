package com.its.issuetrackingservice.infrastructure.dto.response;

import com.its.issuetrackingservice.infrastructure.dto.AuditableEntityDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class ParticipantDetailResponse extends AuditableEntityDto {
	@Serial
	private static final long serialVersionUID = -3254824055423661547L;

	@EqualsAndHashCode.Include
	@Schema(name = "id", description = "The id of the issue", type = "Long", example = "1")
	private Long id;

	@Schema(name = "userId", description = "The id of the user that this participant is related to", type = "Long", example = "1")
	private Long userId;

	@Schema(name = "issueId", description = "The id of the issue that this participant is related to", type = "Long", example = "1")
	private Long issueId;

	@Schema(name = "type", description = "The type of the participant", type = "String", example = "ASSIGNEE")
	private String type;

	@Schema(name = "isWatching", description = "Is issue actively watching by the participant", type = "Boolean", example = "true")
	private Boolean isWatching;

}
