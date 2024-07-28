package com.its.issuetrackingservice.domain.commands.issue;

import com.its.issuetrackingservice.domain.commands.Command;
import com.its.issuetrackingservice.domain.commands.Invoker;
import com.its.issuetrackingservice.domain.service.IssueService;
import com.its.issuetrackingservice.domain.util.SpringContext;
import com.its.issuetrackingservice.infrastructure.dto.UserContext;
import com.its.issuetrackingservice.infrastructure.dto.request.IssueRequest;
import com.its.issuetrackingservice.infrastructure.dto.response.IssueDetailResponse;
import com.its.issuetrackingservice.infrastructure.persistence.entity.Issue;
import com.its.issuetrackingservice.infrastructure.persistence.mapper.IssueMapper;
import jakarta.annotation.PostConstruct;
import lombok.Builder;

import java.util.Optional;


@Builder
public class PublishDraftIssueCommand extends Command<IssueDetailResponse> {
    // Inputs
    private Long projectId;
    private Long issueId;
    private IssueRequest issueRequest;
    private Boolean sendNotification;

    // Generates
    private Issue issue;

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
    }

    @Override
    public IssueDetailResponse execute() {
        issueService.validateAccessToProject(projectId);

        // Update issue by issue request
        UpdateIssueCommand command = UpdateIssueCommand.builder()
                .issueRequest(issueRequest)
                .issueId(issueId)
                .projectId(projectId)
                .returnResultAfterExecution(Boolean.FALSE)
                .build();
        invoker.run(command);

        // Publish issue after updating
        Issue issue = command.getIssue();
        issueService.publishDraftIssue(issue);

        if (Boolean.TRUE.equals(sendNotification)) {
            // TODO: Build send notification command and execute
        }

        if (Boolean.TRUE.equals(getReturnResultAfterExecution())) {
            return getResult().orElse(null);
        }

        return null;
    }

    @Override
    public Optional<IssueDetailResponse> getResult() {
        return Optional.ofNullable(issueMapper.toDetailResponse(issue));
    }

}
