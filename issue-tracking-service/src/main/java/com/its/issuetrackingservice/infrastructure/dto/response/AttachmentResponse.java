package com.its.issuetrackingservice.infrastructure.dto.response;

import com.its.issuetrackingservice.infrastructure.dto.AuditableEntityDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.util.List;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class AttachmentResponse extends AuditableEntityDto {
	@Serial
	private static final long serialVersionUID = -3254824055423661547L;

	@EqualsAndHashCode.Include
	private Long id;

	private String attachmentType;
	private Long issueId;
	private Long activityId;
	private List<Byte> content;
}
