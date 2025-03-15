package com.its.issuetrackingservice.infrastructure.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.io.Serializable;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class CategoryRequest implements Serializable {
    @Serial
    private static final long serialVersionUID = 7665975947661825937L;

    @Schema(name = "id", description = "The id of the category", type = "Long", example = "1")
    private Long id;

    @Schema(name = "name", description = "The name of the related category", type = "String", example = "Backend")
    private String name;

    @Schema(name = "description", description = "The description of the related category", type = "String", example = "Backend related issue category")
    private String description;

    @JsonProperty
    @Schema(name = "isDelete", description = "The indicator of is deleting category from project", type = "boolean" , example = "false")
    private boolean isDelete = false;

}
