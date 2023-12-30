package com.its.issuetrackingservice.api.dto.comment.response;

import com.its.issuetrackingservice.api.dto.AuditableEntityDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.time.OffsetDateTime;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class CommentResponse extends AuditableEntityDto {
	@Serial
	private static final long serialVersionUID = -3254824055423661547L;

	@EqualsAndHashCode.Include
	private Long id;

	private String content;
	private Long creatorUserId;
	private OffsetDateTime createdDate;
	private OffsetDateTime changedDate;
}
