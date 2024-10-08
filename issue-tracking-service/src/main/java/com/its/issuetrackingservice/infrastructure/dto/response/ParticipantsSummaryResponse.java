package com.its.issuetrackingservice.infrastructure.dto.response;

import com.its.issuetrackingservice.infrastructure.dto.AuditableEntityDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.util.Set;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class ParticipantsSummaryResponse extends AuditableEntityDto {
	@Serial
	private static final long serialVersionUID = -3254824055423661547L;

	private Set<ParticipantDetailResponse> assignees;

}
