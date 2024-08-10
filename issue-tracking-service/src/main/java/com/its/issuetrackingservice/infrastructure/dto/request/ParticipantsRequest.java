package com.its.issuetrackingservice.infrastructure.dto.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.io.Serializable;
import java.util.Set;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class ParticipantsRequest implements Serializable {
    @Serial
    private static final long serialVersionUID = 7665975947661825937L;

    private Set<ParticipantRequest> assignees;
    private Set<ParticipantRequest> reviewers;
    private Set<ParticipantRequest> verifiers;
    private Set<ParticipantRequest> watchers;
}
