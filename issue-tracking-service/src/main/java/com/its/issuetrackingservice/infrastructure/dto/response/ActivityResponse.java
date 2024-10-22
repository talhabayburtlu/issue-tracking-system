package com.its.issuetrackingservice.infrastructure.dto.response;

import com.its.issuetrackingservice.infrastructure.dto.AuditableEntityDto;
import com.its.issuetrackingservice.infrastructure.dto.request.ActivityItemRequest;
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
    private Long id;
    private String description;
    private String activityType;
    private Long issueId;
    private Long creatorUserId;
    private List<ActivityItemRequest> activityItems;

}
