package com.its.issuetrackingservice.domain.service;

import com.its.issuetrackingservice.api.model.UserContext;
import com.its.issuetrackingservice.domain.constants.I18nExceptionKeys;
import com.its.issuetrackingservice.domain.exception.DataNotFoundException;
import com.its.issuetrackingservice.domain.exception.WrongUsageException;
import com.its.issuetrackingservice.persistence.entity.Attachment;
import com.its.issuetrackingservice.persistence.entity.Issue;
import com.its.issuetrackingservice.persistence.repository.AttachmentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeType;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AttachmentDomainService {

    private final IssueDomainService issueDomainService;
    private final BucketService bucketService;
    private final NameGeneratorService nameGeneratorService;
    private final AttachmentRepository attachmentRepository;
    private final UserContext userContext;
    private final Long MAX_ATTACHMENT_SIZE_IN_BYTES = 10024000L;
    private final Long MAX_ATTACHMENT_COUNT = 5L;
    private final List<MimeType> ALLOWED_ATTACHMENT_CONTENT_TYPES = List.of(MimeTypeUtils.IMAGE_JPEG, MimeTypeUtils.IMAGE_PNG, MimeTypeUtils.IMAGE_GIF);

    @Transactional
    public Attachment uploadAttachment(Long issueId, MultipartFile file) {
        checkAttachmentRequirements(issueId, file);

        Issue issue = issueDomainService.getIssueById(issueId);

        Attachment attachment = Attachment.builder()
                .issue(issue)
                .attachmentType(file.getContentType())
                .build();

        attachment.setAuditableFields(userContext);

        attachment = attachmentRepository.save(attachment);

        String attachmentObjectName = nameGeneratorService.getIssueAttachmentObjectName(issueId, attachment.getId());
        bucketService.putObject(attachmentObjectName, file);

        return attachment;
    }

    public List<Attachment> getAttachmentsOfIssue(Long issueId) {
        Issue issue = issueDomainService.getIssueById(issueId);

        return issue.getAttachments();
    }

    public List<Byte> getAttachmentContent(Long attachmentId) {
        Attachment attachment = getAttachmentById(attachmentId);

        String attachmentObjectName = nameGeneratorService.getIssueAttachmentObjectName(attachment.getIssue().getId(), attachment.getId());

        return bucketService.downloadObject(attachmentObjectName);
    }

    public Attachment getAttachmentById(Long attachmentId) {
        Optional<Attachment> attachment = attachmentRepository.findById(attachmentId);
        if (attachment.isEmpty()) {
            throw new DataNotFoundException(I18nExceptionKeys.ATTACHMENT_NOT_FOUND, String.format("attachment id=%d", attachmentId));
        }

        return attachment.get();
    }

    private void checkAttachmentRequirements(Long issueId, MultipartFile file) {
        if (file.getSize() > MAX_ATTACHMENT_SIZE_IN_BYTES) {
            throw new WrongUsageException(I18nExceptionKeys.MAX_ATTACHMENT_SIZE_EXCEEDED, String.format("max size =%d bytes, uploaded attachment size =%d", MAX_ATTACHMENT_SIZE_IN_BYTES, file.getSize()));
        }

        if (Objects.isNull(file.getContentType())) {
            throw new WrongUsageException(I18nExceptionKeys.ATTACHMENT_CONTENT_TYPE_IS_NOT_ALLOWED, String.format("allowed content types are= %s", ALLOWED_ATTACHMENT_CONTENT_TYPES.toString()));
        }


        MimeType attachmentContentType = MimeTypeUtils.parseMimeType(file.getContentType());
        if (!ALLOWED_ATTACHMENT_CONTENT_TYPES.contains(attachmentContentType)) {
            throw new WrongUsageException(I18nExceptionKeys.ATTACHMENT_CONTENT_TYPE_IS_NOT_ALLOWED, String.format("allowed content types are= %s", ALLOWED_ATTACHMENT_CONTENT_TYPES));
        }

        Long countOfAttachments = attachmentRepository.getCountByIssueId(issueId);
        if (countOfAttachments.compareTo(MAX_ATTACHMENT_COUNT) > 0) {
            throw new WrongUsageException(I18nExceptionKeys.ATTACHMENT_COUNT_EXCEEDED_FOR_AN_ISSUE, String.format("max count= %d, current count= %d", MAX_ATTACHMENT_COUNT, countOfAttachments));
        }
    }

}
