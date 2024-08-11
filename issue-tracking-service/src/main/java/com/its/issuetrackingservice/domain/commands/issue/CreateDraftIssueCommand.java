package com.its.issuetrackingservice.domain.commands.issue;

import com.its.issuetrackingservice.domain.commands.Command;
import com.its.issuetrackingservice.domain.service.IssueService;
import com.its.issuetrackingservice.domain.util.SpringContext;
import com.its.issuetrackingservice.infrastructure.dto.UserContext;
import com.its.issuetrackingservice.infrastructure.dto.request.IssueRequest;
import com.its.issuetrackingservice.infrastructure.dto.response.IssueDetailResponse;
import com.its.issuetrackingservice.infrastructure.persistence.entity.Issue;
import com.its.issuetrackingservice.infrastructure.persistence.mapper.IssueMapper;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.Builder;

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
    private IssueService issueService;
    private IssueMapper issueMapper;
    private UserContext userContext;

    @PostConstruct
    public void init() {
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

        return null;
    }

    @Override
    public Optional<IssueDetailResponse> getResult() {
        return Optional.ofNullable(issueMapper.toDetailResponse(issue));
    }


}
