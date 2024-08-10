package com.its.issuetrackingservice.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ActivityType {
    PUBLISH("Draft issue has been published", null),
    UPDATE("Issue has been updated", "[%s]:  %s -> %s"),
    SPENT_TIME("%s has spent %d minutes in %s", null),
    COMMENT(null, null);

    private final String description;
    private final String itemDescription;

}
