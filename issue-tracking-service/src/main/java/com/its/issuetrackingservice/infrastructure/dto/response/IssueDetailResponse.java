package com.its.issuetrackingservice.infrastructure.dto.response;

import com.its.issuetrackingservice.infrastructure.dto.AuditableEntityDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class IssueDetailResponse extends AuditableEntityDto {
    @Serial
    private static final long serialVersionUID = -7612219584240385454L;

    @EqualsAndHashCode.Include
    @Schema(name = "id", description = "The id of the issue", type = "Long", example = "1")
    private Long id;

    @Schema(name = "title", description = "Title of the issue", type = "String", example = "Implement Notification service")
    private String title;

    @Schema(name = "description", description = "Description of the issue as clob", type = "String", example = "<p>Notification service must be implemented in this issue.<p>")
    private String description;

    @Schema(name = "code", description = "The code of the issue as generated by services", type = "String", example = "ITS-1234")
    private String code;

    @Schema(name = "spentTime", description = "Total spent time of this issue in milli seconds.", type = "String", example = "86400000")
    private Long spentTime;

    @Schema(name = "estimation", description = "Estimation of the issue that is going to take in milliseconds", type = "Long", example = "86400000")
    private Long estimation;

    @Schema(name = "stateId", description = "The id of the sprint that this issue is related to", type = "Long", example = "1")
    private Long stateId;

    @Schema(name = "sprintId", description = "The id of the sprint that this issue is related to", type = "Long", example = "1")
    private Long sprintId;

    @Schema(name = "categoryId", description = "The id of the category that this issue is related to", type = "Long", example = "1")
    private Long categoryId;

    @Schema(name = "participants", description = "The list of participants", type = "ParticipantsDetailResponse")
    private ParticipantsDetailResponse participants;
}
