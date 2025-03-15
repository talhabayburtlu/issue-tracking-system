package com.its.issuetrackingservice.infrastructure.dto.response;

import com.its.issuetrackingservice.infrastructure.dto.AuditableEntityDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class ActivityItemResponse extends AuditableEntityDto {
    @Serial
    private static final long serialVersionUID = 7665975947661825937L;

    @EqualsAndHashCode.Include
    @Schema(name = "id", description = "The id of the activity", type = "Long", example = "1")
    private Long id;

    @Schema(name = "fieldName", description = "The field name that is affected by this activity", type = "String", example = "title")
    private String fieldName;

    @Schema(name = "oldValue", description = "Old value of specified field before activity", type = "String", example = "Implement Notification service")
    private String oldValue;

    @Schema(name = "newValue", description = "New value of specified field after activity", type = "String", example = "Implement Logging service")
    private String newValue;

}
