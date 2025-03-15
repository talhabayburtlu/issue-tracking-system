package com.its.issuetrackingservice.infrastructure.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
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

    @Schema(name = "description", description = "The description of the project", type = "String", example = "Issue Tracking System (ITS) is a platform designed for agile organizations to track their progress.")
    private String description;

    @Schema(name = "abbreviation", description = "The abbreviation of the project", type = "String", example = "ITS")
    private String abbreviation;

    @Schema(name = "sprints", description = "The list of sprints to be affected by this request", type = "List")
    private List<SprintRequest> sprints;

    @Schema(name = "states", description = "The list of states to be affected by this request", type = "List")
    private List<StateRequest> states;

    @Schema(name = "categories", description = "The list of categories to be affected by this request", type = "List")
    private List<CategoryRequest> categories;
}
