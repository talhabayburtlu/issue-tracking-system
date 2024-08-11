package com.its.issuetrackingservice.domain.commands.issue;

import com.its.issuetrackingservice.domain.commands.Command;
import com.its.issuetrackingservice.domain.service.IssueService;
import com.its.issuetrackingservice.domain.util.SpringContext;
import com.its.issuetrackingservice.infrastructure.dto.UserContext;
import com.its.issuetrackingservice.infrastructure.dto.response.IssueSummaryResponse;
import com.its.issuetrackingservice.infrastructure.persistence.entity.Issue;
import com.its.issuetrackingservice.infrastructure.persistence.mapper.IssueMapper;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.util.List;
import java.util.Optional;


@Builder
@AllArgsConstructor
public class GetSprintIssuesSummaryCommand extends Command<List<IssueSummaryResponse>> {
    // Inputs
    private Long projectId;
    private Long sprintId;

    // Generates
    private List<Issue> issues;

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
    public List<IssueSummaryResponse> execute() {
        userContext.applyAccessToProject(projectId);

        this.issues = issueService.getSprintIssues(projectId, sprintId);
        if (Boolean.TRUE.equals(getReturnResultAfterExecution())) {
            return getResult().orElse(null);
        }

        return null;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public Optional<List<IssueSummaryResponse>> getResult() {
        return Optional.ofNullable(issueMapper.toSummaryListResponse(issues));
    }

}
