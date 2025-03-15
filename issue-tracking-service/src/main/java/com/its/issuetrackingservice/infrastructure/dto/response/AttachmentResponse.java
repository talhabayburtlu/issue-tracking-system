package com.its.issuetrackingservice.infrastructure.dto.response;

import com.its.issuetrackingservice.infrastructure.dto.AuditableEntityDto;
import io.swagger.v3.oas.annotations.media.Schema;
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
	@Schema(name = "id", description = "The id of the attachment", type = "Long", example = "1")
	private Long id;

	@Schema(name = "attachmentType", description = "Type of the attachment", type = "Long", example = "IMAGE")
	private String attachmentType;

	@Schema(name = "issueId", description = "The id of the issue that this attachment is related to", type = "Long", example = "1")
	private Long issueId;

	@Schema(name = "activityId", description = "The id of the activity that this attachment is related to", type = "Long", example = "1")
	private Long activityId;

	@Schema(name = "content", description = "Contents of the attachment as list of bytes", type = "List")
	private List<Byte> content;
}
