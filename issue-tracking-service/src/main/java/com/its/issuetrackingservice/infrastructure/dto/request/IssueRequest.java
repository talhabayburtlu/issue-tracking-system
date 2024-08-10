package com.its.issuetrackingservice.infrastructure.dto.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.io.Serializable;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class IssueRequest implements Serializable {
    @Serial
    private static final long serialVersionUID = 7665975947661825937L;

    private String title;
    private String description;
    private Long estimation;
    private Long categoryId;
    private Long sprintId;
    private Long stateId;
    private ParticipantsRequest participantsRequest;
}
