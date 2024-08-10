package com.its.issuetrackingservice.domain.commands.issue;

import com.its.issuetrackingservice.domain.commands.Command;
import com.its.issuetrackingservice.domain.commands.Invoker;
import com.its.issuetrackingservice.domain.commands.activity.CreateActivityCommand;
import com.its.issuetrackingservice.domain.enums.ActivityType;
import com.its.issuetrackingservice.domain.enums.IssueActivityField;
import com.its.issuetrackingservice.domain.service.IssueService;
import com.its.issuetrackingservice.domain.util.SpringContext;
import com.its.issuetrackingservice.infrastructure.dto.UserContext;
import com.its.issuetrackingservice.infrastructure.dto.request.ActivityItemRequest;
import com.its.issuetrackingservice.infrastructure.dto.request.ActivityRequest;
import com.its.issuetrackingservice.infrastructure.dto.request.IssueRequest;
import com.its.issuetrackingservice.infrastructure.dto.response.IssueDetailResponse;
import com.its.issuetrackingservice.infrastructure.persistence.entity.Issue;
import com.its.issuetrackingservice.infrastructure.persistence.mapper.IssueMapper;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;


@Getter
@SuperBuilder
public class UpdateIssueCommand extends Command<IssueDetailResponse> {
    // Inputs
    private Long projectId;
    private Long issueId;
    private IssueRequest issueRequest;
    private Boolean sendNotification = Boolean.TRUE;

    // Generates
    private Issue oldIssue;
    private Issue newIssue;

    // Services
    private IssueService issueService;
    private IssueMapper issueMapper;
    private UserContext userContext;
    private Invoker invoker;

    @PostConstruct
    public void init() {
        this.issueService = SpringContext.getBean(IssueService.class);
        this.issueMapper = SpringContext.getBean(IssueMapper.class);
        this.userContext = SpringContext.getBean(UserContext.class);
        this.invoker = SpringContext.getBean(Invoker.class);
    }

    @Override
    public IssueDetailResponse execute() {
        issueService.validateAccessToProject(projectId);

        oldIssue = issueService.getIssueById(issueId, projectId);
        newIssue = issueService.updateIssue(issueRequest, oldIssue);

        CreateActivityCommand createActivityCommand = buildCreateActivityCommand();
        invoker.run(createActivityCommand);

        if (Boolean.TRUE.equals(getReturnResultAfterExecution())) {
            return getResult().orElse(null);
        }

        return null;
    }

    @Override
    public Optional<IssueDetailResponse> getResult() {
        return Optional.ofNullable(issueMapper.toDetailResponse(newIssue));
    }

    private CreateActivityCommand buildCreateActivityCommand() {
        ActivityRequest activityRequest = ActivityRequest.builder()
                .activityType(ActivityType.UPDATE.toString())
                .description(ActivityType.UPDATE.getDescription())
                .activityItems(buildActivityItemRequests())
                .creatorUserId(userContext.getUser().getId())
                .issueId(issueId)
                .build();

        // Update issue by issue request
        return CreateActivityCommand.builder()
                .activityRequest(activityRequest)
                .sendNotification(sendNotification)
                .returnResultAfterExecution(Boolean.FALSE)
                .build();
    }

    private List<ActivityItemRequest> buildActivityItemRequests() {
        return Arrays.stream(IssueActivityField.values())
                .map(field -> field.getGenerateActivityItemFunction().apply(oldIssue, newIssue))
                .flatMap(Collection::parallelStream)
                .toList();
    }


}