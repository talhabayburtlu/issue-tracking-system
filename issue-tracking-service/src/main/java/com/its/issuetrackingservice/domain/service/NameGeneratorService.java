package com.its.issuetrackingservice.domain.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NameGeneratorService {
    public String getAttachmentObjectName(Long attachmentId) {
        return String.format("attachment_%d" , attachmentId);
    }

}
