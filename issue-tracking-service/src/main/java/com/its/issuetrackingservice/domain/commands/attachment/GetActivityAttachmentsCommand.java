package com.its.issuetrackingservice.domain.commands.attachment;

import com.its.issuetrackingservice.domain.commands.Command;
import com.its.issuetrackingservice.domain.service.AttachmentService;
import com.its.issuetrackingservice.domain.util.SpringContext;
import com.its.issuetrackingservice.infrastructure.dto.UserContext;
import com.its.issuetrackingservice.infrastructure.dto.response.AttachmentResponse;
import com.its.issuetrackingservice.infrastructure.persistence.entity.ActivityAttachment;
import com.its.issuetrackingservice.infrastructure.persistence.mapper.AttachmentMapper;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.util.List;
import java.util.Optional;
import java.util.Set;


@Builder
@AllArgsConstructor
public class GetActivityAttachmentsCommand extends Command<List<AttachmentResponse>> {

    // Inputs
    private Long activityId;
    private Long issueId;

    // Generates
    private Set<ActivityAttachment> attachments;

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
    public List<AttachmentResponse> execute() {
        userContext.applyAccessToProjectByIssueId(issueId);

        this.attachments = attachmentService.getAttachmentsOfActivity(activityId, issueId);

        if (Boolean.TRUE.equals(getReturnResultAfterExecution())) {
            return getResult().orElse(null);
        }

        return null;
    }

    @Override
    public Optional<List<AttachmentResponse>> getResult() {
        return Optional.ofNullable(attachmentMapper.toActivityAttachmentListResponse(attachments));
    }


}
