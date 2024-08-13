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
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;


@Builder
@AllArgsConstructor
public class CreateDraftIssueCommand extends Command<IssueDetailResponse> {
    // Inputs
    private Long projectId;
    private IssueRequest issueRequest;

    // Generates
    private Issue issue;

    // Services
    private Invoker invoker;
    private IssueService issueService;
    private IssueMapper issueMapper;
    private UserContext userContext;

    @Override
    public void init() {
        this.invoker = SpringContext.getBean(Invoker.class);
        this.issueService = SpringContext.getBean(IssueService.class);
        this.issueMapper = SpringContext.getBean(IssueMapper.class);
        this.userContext = SpringContext.getBean(UserContext.class);
    }


    @Override
    public IssueDetailResponse execute() {
        userContext.applyAccessToProject(projectId);

        this.issue = issueService.createDraftIssue(issueRequest, projectId);

        if (Boolean.TRUE.equals(getReturnResultAfterExecution())) {
            return getResult().orElse(null);
        }

        // Create activity
        CreateActivityCommand createActivityCommand = buildCreateActivityCommand();
        invoker.run(createActivityCommand);

        return null;
    }

    @Override
    public Optional<IssueDetailResponse> getResult() {
        return Optional.ofNullable(issueMapper.toDetailResponse(issue));
    }

    private CreateActivityCommand buildCreateActivityCommand() {
        ActivityRequest activityRequest = ActivityRequest.builder()
                .activityType(ActivityType.PUBLISH.toString())
                .description(ActivityType.PUBLISH.getDescription())
                .activityItems(buildActivityItemRequests())
                .creatorUserId(userContext.getUser().getId())
                .issueId(issue.getId())
                .build();

        // Update issue by issue request
        return CreateActivityCommand.builder()
                .activityRequest(activityRequest)
                .sendNotification(Boolean.FALSE)
                .returnResultAfterExecution(Boolean.FALSE)
                .build();
    }

    private List<ActivityItemRequest> buildActivityItemRequests() {
        return Arrays.stream(IssueActivityField.values())
                .map(field -> field.getGenerateActivityItemFunction().apply(null, issue))
                .flatMap(Collection::parallelStream)
                .toList();
    }


}
