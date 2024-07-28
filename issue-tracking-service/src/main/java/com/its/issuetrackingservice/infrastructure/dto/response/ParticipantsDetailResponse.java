package com.its.issuetrackingservice.infrastructure.dto.response;

import com.its.issuetrackingservice.infrastructure.dto.AuditableEntityDto;
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
	private Long id;

	private ParticipantDetailResponse creator;
	private Set<ParticipantDetailResponse> assignees;
	private Set<ParticipantDetailResponse> reviewers;
	private Set<ParticipantDetailResponse> verifiers;
	private Set<ParticipantDetailResponse> watchers;

}
