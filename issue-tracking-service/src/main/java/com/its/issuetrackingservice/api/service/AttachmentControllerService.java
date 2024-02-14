package com.its.issuetrackingservice.api.service;

import com.its.issuetrackingservice.api.dto.response.AttachmentResponse;
import com.its.issuetrackingservice.api.dto.response.AttachmentSummaryResponse;
import com.its.issuetrackingservice.api.mapper.AttachmentMapper;
import com.its.issuetrackingservice.domain.service.AttachmentDomainService;
import com.its.issuetrackingservice.persistence.entity.Attachment;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@Slf4j
@Service
@RequiredArgsConstructor
public class AttachmentControllerService {
    private final AttachmentDomainService attachmentDomainService;
    private final AttachmentMapper attachmentMapper;

    @Transactional
    public AttachmentSummaryResponse uploadAttachment(Long issueId, MultipartFile file) {
        Attachment attachment = attachmentDomainService.uploadAttachment(issueId, file);
        return attachmentMapper.toSummaryResponse(attachment);
    }

    @Transactional
    public List<AttachmentResponse> getTaskAttachments(Long issueId) {
        List<Attachment> attachment = attachmentDomainService.getAttachmentsOfIssue(issueId);
        return attachmentMapper.toListResponse(attachment);
    }

}
