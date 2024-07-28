package com.its.issuetrackingservice.infrastructure.dto.response;

import com.its.issuetrackingservice.infrastructure.dto.AuditableEntityDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class ParticipantDetailResponse extends AuditableEntityDto {
	@Serial
	private static final long serialVersionUID = -3254824055423661547L;

	@EqualsAndHashCode.Include
	private Long id;

	// TODO: Change userId to include userSummary
	private Long userId;
	private Long issueId;
	private String type;
	private Boolean isWatching;

}
