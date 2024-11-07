package com.its.issuetrackingservice.infrastructure.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.io.Serializable;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class CategoryRequest implements Serializable {
    @Serial
    private static final long serialVersionUID = 7665975947661825937L;

    private Long id;
    private String name;
    private String description;

    @JsonProperty
    private boolean isDelete = false;

}