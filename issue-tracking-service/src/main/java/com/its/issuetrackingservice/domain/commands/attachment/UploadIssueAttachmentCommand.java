package com.its.issuetrackingservice.domain.commands.attachment;

import com.its.issuetrackingservice.domain.commands.Command;
import com.its.issuetrackingservice.domain.service.AttachmentService;
import com.its.issuetrackingservice.domain.util.SpringContext;
import com.its.issuetrackingservice.infrastructure.dto.UserContext;
import com.its.issuetrackingservice.infrastructure.dto.response.IssueAttachmentSummaryResponse;
import com.its.issuetrackingservice.infrastructure.persistence.entity.IssueAttachment;
import com.its.issuetrackingservice.infrastructure.persistence.mapper.AttachmentMapper;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;


@Builder
@AllArgsConstructor
public class UploadIssueAttachmentCommand extends Command<IssueAttachmentSummaryResponse> {
    // Inputs
    private Long issueId;
    private MultipartFile file;

    // Generates
    private IssueAttachment attachment;

    // Services
    private AttachmentService attachmentService;
    private AttachmentMapper attachmentMapper;
    private UserContext userContext;

    @PostConstruct
    public void init() {
        this.attachmentService = SpringContext.getBean(AttachmentService.class);
        this.attachmentMapper = SpringContext.getBean(AttachmentMapper.class);
        this.userContext = SpringContext.getBean(UserContext.class);
    }

    @Override
    public IssueAttachmentSummaryResponse execute() {
        userContext.applyAccessToProjectByIssueId(issueId);

        this.attachment = attachmentService.uploadIssueAttachment(issueId, file);

        if (Boolean.TRUE.equals(getReturnResultAfterExecution())) {
            return getResult().orElse(null);
        }

        return null;
    }

    @Override
    public Optional<IssueAttachmentSummaryResponse> getResult() {
        return Optional.ofNullable(attachmentMapper.toIssueAttachmentSummaryResponse(attachment));
    }


}
