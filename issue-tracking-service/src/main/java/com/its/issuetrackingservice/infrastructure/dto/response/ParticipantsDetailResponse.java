package com.its.issuetrackingservice.infrastructure.dto.response;

import com.its.issuetrackingservice.infrastructure.dto.AuditableEntityDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.util.Set;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class ParticipantsDetailResponse extends AuditableEntityDto {
	@Serial
	private static final long serialVersionUID = -3254824055423661547L;

	@EqualsAndHashCode.Include
	@Schema(name = "id", description = "The id of the participant", type = "Long", example = "1")
	private Long id;

	@Schema(name = "creator", description = "The creator participant of the issue", type = "ParticipantDetailResponse")
	private ParticipantDetailResponse creator;

	@Schema(name = "assignees", description = "Assignee participants of the issue", type = "Set")
	private Set<ParticipantDetailResponse> assignees;

	@Schema(name = "reviewers", description = "Reviewer participants of the issue", type = "Set")
	private Set<ParticipantDetailResponse> reviewers;

	@Schema(name = "verifiers", description = "Verifier participants of the issue", type = "Set")
	private Set<ParticipantDetailResponse> verifiers;

	@Schema(name = "watchers", description = "Watcher participants of the issue", type = "Set")
	private Set<ParticipantDetailResponse> watchers;

}
