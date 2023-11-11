package com.its.issuetrackingservice.domain.issue.dto.response;

import com.its.issuetrackingservice.api.common.dto.AuditableEntityDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.util.Set;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class CommentResponse extends AuditableEntityDto {
	@Serial
	private static final long serialVersionUID = -3254824055423661547L;

	@EqualsAndHashCode.Include
	private Long id;

	private String content;
	private String description;
	private Long creatorUserId;
	private Long issueId;
	private Set<AttachmentResponse> attachments;

}
