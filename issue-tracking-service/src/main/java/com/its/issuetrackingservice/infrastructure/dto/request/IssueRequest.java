package com.its.issuetrackingservice.infrastructure.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.io.Serializable;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class IssueRequest implements Serializable {
    @Serial
    private static final long serialVersionUID = 7665975947661825937L;

    @Schema(name = "title", description = "Title of the issue", type = "String", example = "Implement Notification service")
    private String title;

    @Schema(name = "description", description = "Description of the issue as clob", type = "String", example = "<p>Notification service must be implemented in this issue.<p>")
    private String description;

    @Schema(name = "estimation", description = "Estimation of the issue that is going to take in milliseconds", type = "Long", example = "86400000")
    private Long estimation;

    @Schema(name = "categoryId", description = "The id of the category that this issue is related to", type = "Long", example = "1")
    private Long categoryId;

    @Schema(name = "sprintId", description = "The id of the sprint that this issue is related to", type = "Long", example = "1")
    private Long sprintId;

    @Schema(name = "stateId", description = "The id of the sprint that this issue is related to", type = "Long", example = "1")
    private Long stateId;

    @Schema(name = "participants", description = "The list of participants", type = "ParticipantsRequest")
    private ParticipantsRequest participants;
}
