package com.its.issuetrackingservice.infrastructure.persistence.mapper;

import com.its.issuetrackingservice.domain.service.ActivityService;
import com.its.issuetrackingservice.infrastructure.configuration.mapper.MapStructConfig;
import com.its.issuetrackingservice.infrastructure.dto.request.ActivityRequest;
import com.its.issuetrackingservice.infrastructure.dto.request.CommentRequest;
import com.its.issuetrackingservice.infrastructure.dto.response.ActivityResponse;
import com.its.issuetrackingservice.infrastructure.persistence.entity.Activity;
import lombok.Setter;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

@Mapper(config = MapStructConfig.class, uses = {ActivityItemMapper.class})
public abstract class ActivityMapper {

    @Setter(onMethod_ = @Autowired)
    protected ActivityService activityService;

    @Mapping(source = "issue.id", target = "issueId")
    @Mapping(source = "creatorUser.id", target = "creatorUserId")
    public abstract ActivityResponse toResponse(Activity activity);

    @Mapping(source = "issueId", target = "issue.id")
    @Mapping(source = "creatorUserId", target = "creatorUser.id")
    public abstract Activity activityRequestToEntity(ActivityRequest activityRequest);

    @Mapping(source = "issueId", target = "issue.id", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_DEFAULT)
    public abstract Activity commentRequestToEntity(CommentRequest commentRequest, Long issueId);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public abstract Activity patchComment(CommentRequest commentRequest, @MappingTarget Activity activity);

    public Page<ActivityResponse> toPageResponse(Page<Activity> activities) {
        return activities.map(this::toResponse);
    }

}
