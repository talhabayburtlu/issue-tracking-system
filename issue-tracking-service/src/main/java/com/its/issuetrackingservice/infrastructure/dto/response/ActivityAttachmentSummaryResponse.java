package com.its.issuetrackingservice.infrastructure.dto.response;

import com.its.issuetrackingservice.infrastructure.dto.AuditableEntityDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class ActivityAttachmentSummaryResponse extends AuditableEntityDto {
	@Serial
	private static final long serialVersionUID = -3254824055423661547L;

	@EqualsAndHashCode.Include
	@Schema(name = "id", description = "The id of the activity attachment", type = "Long", example = "1")
	private Long id;

	@Schema(name = "attachmentType", description = "Type of the attachment", type = "Long", example = "IMAGE")
	private String attachmentType;

	@Schema(name = "activityId", description = "The id of the activity attachment", type = "Long", example = "1")
	private Long activityId;
}
