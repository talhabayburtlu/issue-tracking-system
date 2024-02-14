package com.its.issuetrackingservice.api.dto.response;

import com.its.issuetrackingservice.api.dto.AuditableEntityDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class IssueDetailResponse extends AuditableEntityDto {
	@Serial
	private static final long serialVersionUID = -7612219584240385454L;

	@EqualsAndHashCode.Include
	private Long id;

	private String title;
	private String description;
	private String category;
	private Integer points;
	private Long creatorUserId;
	private Long verifierUserId;
	private Long projectId;
	private Long stateId;
	private Long sprintId;

}
