package com.its.issuetrackingservice.infrastructure.dto.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class ProjectRequest implements Serializable {
    @Serial
    private static final long serialVersionUID = 7665975947661825937L;

    private String description;
    private String abbreviation;

    private List<SprintRequest> sprints;
    private List<StateRequest> states;
    private List<CategoryRequest> categories;
}
