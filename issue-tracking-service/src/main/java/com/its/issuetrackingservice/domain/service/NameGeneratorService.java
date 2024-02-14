package com.its.issuetrackingservice.domain.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NameGeneratorService {

    public String getIssueAttachmentObjectName(Long issueId, Long attachmentId) {
        return String.format("issue_%d_attachment_%d" , issueId, attachmentId);
    }

}
