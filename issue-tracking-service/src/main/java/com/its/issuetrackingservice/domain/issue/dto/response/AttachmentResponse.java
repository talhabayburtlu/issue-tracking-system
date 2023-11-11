package com.its.issuetrackingservice.domain.issue.dto.response;

import com.its.issuetrackingservice.api.common.dto.AuditableEntityDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class AttachmentResponse extends AuditableEntityDto {
	@Serial
	private static final long serialVersionUID = -3254824055423661547L;

	@EqualsAndHashCode.Include
	private Long id;

	private String description;
	private String attachmentType;
	private Long issueId;
	private Long commentId;

}