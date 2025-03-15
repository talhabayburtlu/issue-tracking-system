package com.its.issuetrackingservice.infrastructure.dto.response;

import com.its.issuetrackingservice.infrastructure.dto.AuditableEntityDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.util.List;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class ActivityResponse extends AuditableEntityDto {
    @Serial
    private static final long serialVersionUID = 7665975947661825937L;

    @EqualsAndHashCode.Include
    @Schema(name = "id", description = "The id of the activity", type = "Long", example = "1")
    private Long id;

    @Schema(name = "description", description = "The description of the activity", type = "String", example = "Issue has been updated title:  old_title -> new_title")
    private String description;

    @Schema(name = "activityType", description = "Type of the activity", type = "String", example = "UPDATE")
    private String activityType;

    @Schema(name = "issueId", description = "The id of the issue that this activity is related to", type = "Long", example = "1")
    private Long issueId;

    @Schema(name = "creatorUserId", description = "The id of the user which creates this activity", type = "Long", example = "1")
    private Long creatorUserId;

    @Schema(name = "activityItems", description = "The list of activity items belong to this activity", type = "List")
    private List<ActivityItemResponse> activityItems;

}
