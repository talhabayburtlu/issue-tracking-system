package com.its.issuetrackingservice.infrastructure.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.io.Serializable;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class ParticipantRequest implements Serializable {
    @Serial
    private static final long serialVersionUID = 7665975947661825937L;

    @Schema(name = "id", description = "The id of participation", type = "Long" , example = "1")
    private Long id;

    @Schema(name = "userId", description = "The id of user which participates", type = "Long" , example = "1")
    private Long userId;

    @Schema(name = "isWatching", description = "The indicator of is participant watching the issue or not", type = "boolean" , example = "true")
    private boolean isWatching;

    @JsonProperty
    @Schema(name = "isDelete", description = "The indicator of is deleting participant of the issue ", type = "boolean" , example = "false")
    private boolean isDelete = false;

}
