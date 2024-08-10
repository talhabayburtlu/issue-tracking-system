package com.its.issuetrackingservice.infrastructure.persistence.mapper;

import com.its.issuetrackingservice.domain.service.ActivityService;
import com.its.issuetrackingservice.infrastructure.configuration.mapper.MapStructConfig;
import com.its.issuetrackingservice.infrastructure.dto.request.ActivityItemRequest;
import com.its.issuetrackingservice.infrastructure.dto.response.ActivityItemResponse;
import com.its.issuetrackingservice.infrastructure.persistence.entity.ActivityItem;
import lombok.Setter;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(config = MapStructConfig.class)
public abstract class ActivityItemMapper {

    @Setter(onMethod_ = @Autowired)
    protected ActivityService activityService;

    public abstract ActivityItemResponse toResponse(ActivityItem activityItem);

    public abstract ActivityItem toEntity(ActivityItemRequest activityItemRequest);


}
