package com.its.issuetrackingservice.api.mapper;

import com.its.issuetrackingservice.api.config.MapStructConfig;
import com.its.issuetrackingservice.api.dto.response.AttachmentResponse;
import com.its.issuetrackingservice.api.dto.response.AttachmentSummaryResponse;
import com.its.issuetrackingservice.domain.service.AttachmentDomainService;
import com.its.issuetrackingservice.persistence.entity.Attachment;
import lombok.Setter;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper(config = MapStructConfig.class)
public abstract class AttachmentMapper {

    @Setter(onMethod_ = @Autowired)
    protected AttachmentDomainService attachmentDomainService;

    @Mapping(source = "issue.id", target = "issueId")
    public abstract AttachmentSummaryResponse toSummaryResponse(Attachment attachment);

    @Mapping(source = "issue.id", target = "issueId")
    @Mapping(target = "content", qualifiedByName = "setAttachmentContent")
    public abstract AttachmentResponse toResponse(Attachment attachment);

    @Mapping(source = "issue.id", target = "issueId")
    @Mapping(target = "content", qualifiedByName = "setAttachmentContent")
    public abstract List<AttachmentResponse> toListResponse(List<Attachment> attachmentList);

    @Named(value = "setAttachmentContent")
    public List<Byte> setAttachmentContent(Attachment attachment) {
        return attachmentDomainService.getAttachmentContent(attachment.getId());
    }

}
