package com.its.issuetrackingservice.infrastructure.dto.response;

import com.its.issuetrackingservice.infrastructure.dto.AuditableEntityDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class ActivityItemResponse extends AuditableEntityDto {
    @Serial
    private static final long serialVersionUID = 7665975947661825937L;

    @EqualsAndHashCode.Include
    private Long id;
    private String fieldName;
    private String oldValue;
    private String newValue;

}
