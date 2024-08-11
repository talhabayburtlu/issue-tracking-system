package com.its.issuetrackingservice.domain.commands.attachment;

import com.its.issuetrackingservice.domain.commands.Command;
import com.its.issuetrackingservice.domain.constants.I18nExceptionKeys;
import com.its.issuetrackingservice.domain.exception.AccessException;
import com.its.issuetrackingservice.domain.service.ActivityService;
import com.its.issuetrackingservice.domain.service.AttachmentService;
import com.its.issuetrackingservice.domain.util.SpringContext;
import com.its.issuetrackingservice.infrastructure.dto.UserContext;
import com.its.issuetrackingservice.infrastructure.dto.response.ActivityAttachmentSummaryResponse;
import com.its.issuetrackingservice.infrastructure.persistence.entity.Activity;
import com.its.issuetrackingservice.infrastructure.persistence.entity.ActivityAttachment;
import com.its.issuetrackingservice.infrastructure.persistence.mapper.AttachmentMapper;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;
import java.util.Optional;


@Builder
@AllArgsConstructor
public class UploadActivityAttachmentCommand extends Command<ActivityAttachmentSummaryResponse> {
    // Inputs
    private Long activityId;
    private Long issueId;
    private MultipartFile file;

    // Generates
    private Activity activity;
    private ActivityAttachment attachment;


    // Services
    private ActivityService activityService;
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
    public ActivityAttachmentSummaryResponse execute() {
        userContext.applyAccessToProjectByIssueId(issueId);

        activity = activityService.getActivityById(activityId, issueId);
        if (!Objects.equals(activity.getCreatorUser().getId(), userContext.getUser().getId())) {
            throw new AccessException(I18nExceptionKeys.ONLY_COMMENT_CREATOR_CAN_UPLOAD_ATTACHMENTS);
        }

        this.attachment = attachmentService.uploadActivityAttachment(activityId, issueId, file);

        if (Boolean.TRUE.equals(getReturnResultAfterExecution())) {
            return getResult().orElse(null);
        }

        return null;
    }

    @Override
    public Optional<ActivityAttachmentSummaryResponse> getResult() {
        return Optional.ofNullable(attachmentMapper.toActivityAttachmentSummaryResponse(attachment));
    }


}
