package com.its.issuetrackingservice.api.dto.request;

import com.its.issuetrackingservice.api.dto.AuditableEntityDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class IssueCreateRequest extends AuditableEntityDto {
	@Serial
	private static final long serialVersionUID = 7665975947661825937L;

	private String title;
	private String description;
	private String category;
	private Integer points;
	private Long projectId;
	private Long sprintId;
	private Long verifierUserId;

}
