package com.its.issuetrackingservice.infrastructure.persistence.mapper;

import com.its.issuetrackingservice.domain.service.AttachmentService;
import com.its.issuetrackingservice.infrastructure.configuration.mapper.MapStructConfig;
import com.its.issuetrackingservice.infrastructure.dto.response.AttachmentResponse;
import com.its.issuetrackingservice.infrastructure.dto.response.AttachmentSummaryResponse;
import com.its.issuetrackingservice.infrastructure.persistence.entity.Attachment;
import lombok.Setter;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper(config = MapStructConfig.class)
public abstract class AttachmentMapper {

    @Setter(onMethod_ = @Autowired)
    protected AttachmentService attachmentService;

    @Mapping(source = "issue.id", target = "issueId")
    public abstract AttachmentSummaryResponse toSummaryResponse(Attachment attachment);

    @Mapping(source = "issue.id", target = "issueId")
    @Mapping(target = "content", source = "attachment",qualifiedByName = "setAttachmentContent")
    public abstract AttachmentResponse toResponse(Attachment attachment);

    @Mapping(source = "issue.id", target = "issueId")
    @Mapping(target = "qualified", qualifiedByName = "setAttachmentContent")
    public abstract List<AttachmentResponse> toListResponse(List<Attachment> attachmentList);

    @Named("setAttachmentContent")
    public List<Byte> setAttachmentContent(Attachment attachment) {
        return attachmentService.getAttachmentContent(attachment.getId());
    }

}
