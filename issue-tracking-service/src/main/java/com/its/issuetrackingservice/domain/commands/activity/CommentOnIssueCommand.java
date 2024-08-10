package com.its.issuetrackingservice.domain.commands.activity;

import com.its.issuetrackingservice.domain.commands.Command;
import com.its.issuetrackingservice.domain.service.ActivityService;
import com.its.issuetrackingservice.domain.util.SpringContext;
import com.its.issuetrackingservice.infrastructure.dto.UserContext;
import com.its.issuetrackingservice.infrastructure.dto.request.CommentRequest;
import com.its.issuetrackingservice.infrastructure.dto.response.ActivityResponse;
import com.its.issuetrackingservice.infrastructure.persistence.entity.Activity;
import com.its.issuetrackingservice.infrastructure.persistence.mapper.ActivityMapper;
import jakarta.annotation.PostConstruct;
import lombok.experimental.SuperBuilder;

import java.util.Optional;


@SuperBuilder
public class CommentOnIssueCommand extends Command<ActivityResponse> {
    // Inputs
    private Long issueId;
    private Long projectId;
    private CommentRequest commentRequest;

    // Generates
    private Activity activity;

    // Services
    private ActivityService activityService;
    private ActivityMapper activityMapper;
    private UserContext userContext;

    @PostConstruct
    public void init() {
        this.activityService = SpringContext.getBean(ActivityService.class);
        this.activityMapper = SpringContext.getBean(ActivityMapper.class);
        this.userContext = SpringContext.getBean(UserContext.class);
    }

    @Override
    public ActivityResponse execute() {
        userContext.applyAccessToProject(projectId);

        this.activity = activityMapper.commentRequestToEntity(commentRequest, issueId);
        this.activity = activityService.createComment(activity);

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
