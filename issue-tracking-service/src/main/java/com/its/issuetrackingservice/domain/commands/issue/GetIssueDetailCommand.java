package com.its.issuetrackingservice.domain.commands.issue;

import com.its.issuetrackingservice.domain.commands.Command;
import com.its.issuetrackingservice.domain.service.IssueService;
import com.its.issuetrackingservice.domain.util.SpringContext;
import com.its.issuetrackingservice.infrastructure.dto.UserContext;
import com.its.issuetrackingservice.infrastructure.dto.response.IssueDetailResponse;
import com.its.issuetrackingservice.infrastructure.persistence.entity.Issue;
import com.its.issuetrackingservice.infrastructure.persistence.mapper.IssueMapper;
import jakarta.annotation.PostConstruct;
import lombok.Builder;

import java.util.Optional;


@Builder
public class GetIssueDetailCommand implements Command {
    // Inputs
    private Long projectId;
    private Long issueId;

    // Outputs
    private IssueDetailResponse issueDetailResponse;

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
    public void execute() {
        userContext.applyAccessToProject(projectId);

        Issue issue = issueService.getIssueById(issueId);
        this.issueDetailResponse = issueMapper.toDetailResponse(issue);
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public Optional<IssueDetailResponse> getResult() {
        return Optional.ofNullable(issueDetailResponse);
    }

}
