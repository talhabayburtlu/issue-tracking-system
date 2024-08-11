package com.its.issuetrackingservice.infrastructure.persistence.mapper;

import com.its.issuetrackingservice.domain.service.AttachmentService;
import com.its.issuetrackingservice.infrastructure.configuration.mapper.MapStructConfig;
import com.its.issuetrackingservice.infrastructure.dto.response.ActivityAttachmentSummaryResponse;
import com.its.issuetrackingservice.infrastructure.dto.response.AttachmentResponse;
import com.its.issuetrackingservice.infrastructure.dto.response.IssueAttachmentSummaryResponse;
import com.its.issuetrackingservice.infrastructure.persistence.entity.ActivityAttachment;
import com.its.issuetrackingservice.infrastructure.persistence.entity.Attachment;
import com.its.issuetrackingservice.infrastructure.persistence.entity.IssueAttachment;
import lombok.Setter;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Set;

@Mapper(config = MapStructConfig.class)
public abstract class AttachmentMapper {

    @Setter(onMethod_ = @Autowired)
    protected AttachmentService attachmentService;

    @Mapping(source = "issue.id", target = "issueId")
    public abstract IssueAttachmentSummaryResponse toIssueAttachmentSummaryResponse(IssueAttachment attachment);

    @Mapping(source = "activity.id", target = "activityId")
    public abstract ActivityAttachmentSummaryResponse toActivityAttachmentSummaryResponse(ActivityAttachment attachment);

    @Mapping(target = "content", source = "attachment",qualifiedByName = "setAttachmentContent")
    public abstract AttachmentResponse toResponse(Attachment attachment);

    @Mapping(source = "issue.id", target = "issueId")
    @Mapping(target = "qualified", qualifiedByName = "setAttachmentContent")
    public abstract List<AttachmentResponse> toIssueAttachmentListResponse(Set<IssueAttachment> attachmentList);

    @Mapping(source = "activity.id", target = "activityId")
    @Mapping(target = "qualified", qualifiedByName = "setAttachmentContent")
    public abstract List<AttachmentResponse> toActivityAttachmentListResponse(Set<ActivityAttachment> attachmentList);

    @Named("setAttachmentContent")
    public List<Byte> setAttachmentContent(Attachment attachment) {
        return attachmentService.getAttachmentContent(attachment.getId());
    }

}
