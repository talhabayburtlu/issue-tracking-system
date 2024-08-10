package com.its.issuetrackingservice.infrastructure.dto.request;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.io.Serializable;

@Data
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class ActivityItemRequest implements Serializable {
    @Serial
    private static final long serialVersionUID = 7665975947661825937L;

    private String fieldName;
    private String oldValue;
    private String newValue;
    private String type;

}
