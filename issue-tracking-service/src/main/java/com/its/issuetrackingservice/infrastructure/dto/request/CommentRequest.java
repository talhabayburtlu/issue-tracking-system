package com.its.issuetrackingservice.infrastructure.dto.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.io.Serializable;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class CommentRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = 1320108222436775344L;

    private String description;

}
