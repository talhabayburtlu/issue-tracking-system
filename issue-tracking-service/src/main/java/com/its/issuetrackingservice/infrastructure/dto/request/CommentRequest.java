package com.its.issuetrackingservice.infrastructure.dto.request;

import com.its.issuetrackingservice.infrastructure.dto.AuditableEntityDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class CommentRequest extends AuditableEntityDto {
	@Serial
	private static final long serialVersionUID = 7665975947661825937L;

	private String content;
}
