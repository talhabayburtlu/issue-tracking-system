package com.its.issuetrackingservice.infrastructure.dto;

import io.swagger.v3.oas.annotations.media.Schema;
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

    @Schema(name = "code", description = "The code of the related response", type = "Integer", example = "200")
    private Integer code;

    @Schema(name = "message", description = "The message of the related response", type = "String", example = "Success")
    private String message;

    @Schema(name = "fetchTime", description = "The message of the related response", type = "OffsetDateTime", example = "2025-03-03T10:00:00Z")
    private OffsetDateTime fetchTime;
}
