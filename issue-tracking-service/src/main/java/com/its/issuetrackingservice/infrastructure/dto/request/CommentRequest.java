package com.its.issuetrackingservice.infrastructure.dto.request;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.io.Serializable;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@Builder
public class CommentRequest implements Serializable {
    @Serial
    private static final long serialVersionUID = 7665975947661825937L;

    private String description;

}
