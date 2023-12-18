package com.its.issuetrackingservice.api.common.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Metadata {
    private Integer code;
    private String message;
    private OffsetDateTime fetchTime;
}
