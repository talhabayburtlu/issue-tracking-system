package com.its.issuetrackingservice.infrastructure.dto.response;

import com.its.issuetrackingservice.infrastructure.dto.AuditableEntityDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.util.Set;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class IssueDetailResponse extends AuditableEntityDto {
    @Serial
    private static final long serialVersionUID = -7612219584240385454L;

    @EqualsAndHashCode.Include
    private Long id;

    private String title;
    private String description;
    private String code;
    private Long spentTime;
    private Long estimation;
    private Long stateId;
    private Long sprintId;
    private Long categoryId;
    private Set<Long> subsystemIds;
    private ParticipantDetailResponse participants;
}
