package com.its.issuetrackingservice.domain.commands.activity;

import com.its.issuetrackingservice.domain.commands.Command;
import com.its.issuetrackingservice.domain.service.ActivityService;
import com.its.issuetrackingservice.domain.util.SpringContext;
import com.its.issuetrackingservice.infrastructure.dto.request.ActivityRequest;
import com.its.issuetrackingservice.infrastructure.dto.response.ActivityResponse;
import com.its.issuetrackingservice.infrastructure.persistence.entity.Activity;
import com.its.issuetrackingservice.infrastructure.persistence.mapper.ActivityMapper;
import lombok.AllArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Optional;


@SuperBuilder
@AllArgsConstructor
public class CreateActivityCommand extends Command<ActivityResponse> {
    // Inputs
    private ActivityRequest activityRequest;
    private Boolean sendNotification;

    // Generates
    private Activity activity;

    // Services
    private ActivityService activityService;
    private ActivityMapper activityMapper;

    @Override
    public void init() {
        this.activityService = SpringContext.getBean(ActivityService.class);
        this.activityMapper = SpringContext.getBean(ActivityMapper.class);
    }

    @Override
    public ActivityResponse execute() {
        this.activity = activityMapper.activityRequestToEntity(activityRequest);
        this.activity = activityService.upsertActivity(activity);

        if (Boolean.TRUE.equals(sendNotification)) {
            // TODO: Build send notification command and execute
        }

        if (Boolean.TRUE.equals(getReturnResultAfterExecution())) {
            return getResult().orElse(null);
        }

        return null;
    }

    @Override
    public Optional<ActivityResponse> getResult() {
        return Optional.ofNullable(activityMapper.toResponse(activity));
    }


}
