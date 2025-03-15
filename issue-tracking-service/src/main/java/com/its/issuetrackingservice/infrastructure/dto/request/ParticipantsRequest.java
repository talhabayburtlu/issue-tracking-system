package com.its.issuetrackingservice.infrastructure.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class ParticipantsRequest implements Serializable {
    @Serial
    private static final long serialVersionUID = 7665975947661825937L;

    @Schema(name = "assignees", description = "The list of assignees as participants", type = "ParticipantRequest")
    private Set<ParticipantRequest> assignees = new HashSet<>();

    @Schema(name = "reviewers", description = "The list of reviewers as participants", type = "ParticipantRequest")
    private Set<ParticipantRequest> reviewers = new HashSet<>();

    @Schema(name = "verifiers", description = "The list of verifiers as participants", type = "ParticipantRequest")
    private Set<ParticipantRequest> verifiers = new HashSet<>();

    @Schema(name = "watchers", description = "The list of watchers as participants", type = "ParticipantRequest")
    private Set<ParticipantRequest> watchers = new HashSet<>();
}
