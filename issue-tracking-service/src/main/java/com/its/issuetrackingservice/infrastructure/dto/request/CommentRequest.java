package com.its.issuetrackingservice.infrastructure.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.io.Serializable;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class CommentRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = 1320108222436775344L;

    @Schema(name = "description", description = "The description that contains the comment", type = "String", example = "<p>Related issue is still waiting to be resolved.<p>")
    private String description;

}
