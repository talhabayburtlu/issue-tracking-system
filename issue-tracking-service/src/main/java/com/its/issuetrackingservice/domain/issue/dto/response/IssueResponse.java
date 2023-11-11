package com.its.issuetrackingservice.domain.issue.dto.response;

import com.its.issuetrackingservice.api.common.dto.AuditableEntityDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class IssueResponse extends AuditableEntityDto {
	@Serial
	private static final long serialVersionUID = -3254824055423661547L;

	@EqualsAndHashCode.Include
	private Long id;

	private String title;
	private String description;
	private String category;
	private Integer points;
	private Long creatorUserId;
	private Long verifierUserId;
	private Long stateId;
	private Long sprintId;

}