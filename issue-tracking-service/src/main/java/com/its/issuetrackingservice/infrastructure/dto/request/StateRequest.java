package com.its.issuetrackingservice.infrastructure.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.io.Serializable;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class StateRequest implements Serializable {
    @Serial
    private static final long serialVersionUID = 7665975947661825937L;

    @Schema(name = "id", description = "The id of the state", type = "Long", example = "1")
    private Long id;

    @Schema(name = "title", description = "The title of the state", type = "String", example = "In Progress")
    private String title;

    @Schema(name = "description", description = "The description of the state", type = "String", example = "Issues that are in progress are belong to this category")
    private String description;

    @Schema(name = "isInitial", description = "The indicator of is initial state of the project ", type = "boolean" , example = "false")
    private boolean isInitial;

    @Schema(name = "nextStateId", description = "The id of next state of current state", type = "Long" , example = "1")
    private Long nextStateId;

    @JsonProperty
    @Schema(name = "isDelete", description = "The indicator of is deleting state from project ", type = "boolean" , example = "false")
    private boolean isDelete = false;

}
