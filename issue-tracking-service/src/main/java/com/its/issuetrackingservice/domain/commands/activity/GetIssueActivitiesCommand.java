package com.its.issuetrackingservice.domain.commands.activity;

import com.its.issuetrackingservice.domain.commands.Command;
import com.its.issuetrackingservice.domain.service.ActivityService;
import com.its.issuetrackingservice.domain.service.IssueService;
import com.its.issuetrackingservice.domain.util.SpringContext;
import com.its.issuetrackingservice.infrastructure.dto.UserContext;
import com.its.issuetrackingservice.infrastructure.dto.response.ActivityResponse;
import com.its.issuetrackingservice.infrastructure.persistence.entity.Activity;
import com.its.issuetrackingservice.infrastructure.persistence.entity.Issue;
import com.its.issuetrackingservice.infrastructure.persistence.mapper.ActivityMapper;
import lombok.AllArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;


@SuperBuilder
@AllArgsConstructor
public class GetIssueActivitiesCommand extends Command<Page<ActivityResponse>> {
    // Inputs
    private Long issueId;
    private Pageable pageable;

    // Generates
    private Page<Activity> activities;

    // Services
    private ActivityService activityService;
    private IssueService issueService;
    private ActivityMapper activityMapper;
    private UserContext userContext;

    @Override
    public void init() {
        this.activityService = SpringContext.getBean(ActivityService.class);
        this.issueService = SpringContext.getBean(IssueService.class);
        this.activityMapper = SpringContext.getBean(ActivityMapper.class);
        this.userContext = SpringContext.getBean(UserContext.class);
    }

    @Override
    public Page<ActivityResponse> execute() {
        userContext.applyAccessToProjectByIssueId(issueId);

        Issue issue = issueService.getIssueById(issueId);
        this.activities = activityService.getActivitiesByIssueId(issue.getId(), pageable);

        if (Boolean.TRUE.equals(getReturnResultAfterExecution())) {
            return getResult().orElse(null);
        }

        return null;
    }

    @Override
    public Optional<Page<ActivityResponse>> getResult() {
        return Optional.ofNullable(activityMapper.toPageResponse(activities));
    }


}
