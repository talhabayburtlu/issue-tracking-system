package com.its.issuetrackingservice.domain.service;

import com.its.issuetrackingservice.domain.constants.I18nExceptionKeys;
import com.its.issuetrackingservice.domain.enums.AttachmentType;
import com.its.issuetrackingservice.domain.exception.DataNotFoundException;
import com.its.issuetrackingservice.domain.exception.WrongUsageException;
import com.its.issuetrackingservice.infrastructure.dto.UserContext;
import com.its.issuetrackingservice.infrastructure.persistence.entity.*;
import com.its.issuetrackingservice.infrastructure.persistence.repository.ActivityAttachmentRepository;
import com.its.issuetrackingservice.infrastructure.persistence.repository.AttachmentRepository;
import com.its.issuetrackingservice.infrastructure.persistence.repository.IssueAttachmentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@Service
@RequiredArgsConstructor
public class AttachmentService {

    private final IssueService issueService;
    private final ActivityService activityService;
    private final BucketService bucketService;
    private final NameGeneratorService nameGeneratorService;
    private final AttachmentRepository attachmentRepository;
    private final IssueAttachmentRepository issueAttachmentRepository;
    private final ActivityAttachmentRepository activityAttachmentRepository;
    private final UserContext userContext;
    private final static Long MAX_ATTACHMENT_SIZE_IN_BYTES = 10024000L;
    private final static Long ISSUE_MAX_ATTACHMENT_COUNT = 5L;
    private final static Long ACTIVITY_MAX_ATTACHMENT_COUNT = 2L;
    private final List<String> ALLOWED_ATTACHMENT_CONTENT_TYPES = Arrays.stream(AttachmentType.values()).map(AttachmentType::getTypes).flatMap(Collection::stream).toList();

    @Transactional
    public IssueAttachment uploadIssueAttachment(Long issueId, MultipartFile file) {
        checkAttachmentRequirements(file);
        checkIssueAttachmentRequirements(issueId);

        Issue issue = issueService.getIssueById(issueId);

        IssueAttachment attachment = IssueAttachment.builder()
                .issue(issue)
                .attachmentType(AttachmentType.findAttachmentTypeByContentType(file.getContentType()))
                .build();

        attachment = upsertAttachment(attachment);

        String attachmentObjectName = nameGeneratorService.getAttachmentObjectName(attachment.getId());
        bucketService.putObject(attachmentObjectName, file);

        return attachment;
    }

    @Transactional
    public ActivityAttachment uploadActivityAttachment(Long activityId, Long issueId, MultipartFile file) {
        checkAttachmentRequirements(file);
        checkActivityAttachmentRequirements(activityId);

        Activity activity = activityService.getActivityById(activityId, issueId);

        ActivityAttachment attachment = ActivityAttachment.builder()
                .activity(activity)
                .attachmentType(AttachmentType.findAttachmentTypeByContentType(file.getContentType()))
                .build();

        attachment = upsertAttachment(attachment);

        String attachmentObjectName = nameGeneratorService.getAttachmentObjectName(attachment.getId());
        bucketService.putObject(attachmentObjectName, file);

        return attachment;
    }

    public <T extends Attachment> T upsertAttachment(T attachment) {
        // Set auditable fields.
        attachment.setAuditableFields(userContext);
        return attachmentRepository.save(attachment);
    }

    public Set<IssueAttachment> getAttachmentsOfIssue(Long issueId) {
        issueService.getIssueById(issueId);
        return issueAttachmentRepository.findAllByIssueId(issueId);
    }

    public Set<ActivityAttachment> getAttachmentsOfActivity(Long activityId, Long issueId) {
        activityService.getActivityById(activityId, issueId);
        return activityAttachmentRepository.findAllByActivityId(activityId);
    }

    public List<Byte> getAttachmentContent(Long attachmentId) {
        Attachment attachment = getAttachmentById(attachmentId);
        String attachmentObjectName = nameGeneratorService.getAttachmentObjectName(attachment.getId());
        return bucketService.downloadObject(attachmentObjectName);
    }

    public Attachment getAttachmentById(Long attachmentId) {
        Optional<Attachment> attachment = attachmentRepository.findById(attachmentId);
        if (attachment.isEmpty()) {
            throw new DataNotFoundException(I18nExceptionKeys.ATTACHMENT_NOT_FOUND, String.format("attachment id=%d", attachmentId));
        }

        return attachment.get();
    }

    private void checkAttachmentRequirements(MultipartFile file) {
        if (file.getSize() > MAX_ATTACHMENT_SIZE_IN_BYTES) {
            throw new WrongUsageException(I18nExceptionKeys.MAX_ATTACHMENT_SIZE_EXCEEDED, String.format("max size =%d bytes, uploaded attachment size =%d", MAX_ATTACHMENT_SIZE_IN_BYTES, file.getSize()));
        }

        if (Objects.isNull(file.getContentType())) {
            throw new WrongUsageException(I18nExceptionKeys.ATTACHMENT_CONTENT_TYPE_IS_NOT_ALLOWED, String.format("allowed content types are= %s", ALLOWED_ATTACHMENT_CONTENT_TYPES.toString()));
        }

        if (!ALLOWED_ATTACHMENT_CONTENT_TYPES.contains(file.getContentType())) {
            throw new WrongUsageException(I18nExceptionKeys.ATTACHMENT_CONTENT_TYPE_IS_NOT_ALLOWED, String.format("allowed content types are= %s", ALLOWED_ATTACHMENT_CONTENT_TYPES));
        }
    }

    private void checkIssueAttachmentRequirements(Long issueId) {
        Long countOfAttachments = issueAttachmentRepository.getCountByIssueId(issueId);
        if (countOfAttachments.compareTo(ISSUE_MAX_ATTACHMENT_COUNT) > 0) {
            throw new WrongUsageException(I18nExceptionKeys.ATTACHMENT_COUNT_EXCEEDED_FOR_AN_ISSUE, String.format("max count= %d, current count= %d", ISSUE_MAX_ATTACHMENT_COUNT, countOfAttachments));
        }
    }

    private void checkActivityAttachmentRequirements(Long activityId) {
        Long countOfAttachments = activityAttachmentRepository.getCountByActivityId(activityId);
        if (countOfAttachments.compareTo(ACTIVITY_MAX_ATTACHMENT_COUNT) > 0) {
            throw new WrongUsageException(I18nExceptionKeys.ATTACHMENT_COUNT_EXCEEDED_FOR_AN_ISSUE, String.format("max count= %d, current count= %d", ACTIVITY_MAX_ATTACHMENT_COUNT, countOfAttachments));
        }
    }

}
