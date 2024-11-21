package com.its.issuetrackingservice.infrastructure.messaging.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Recipient {
    private String mail;
    private String phoneNumber;
}
