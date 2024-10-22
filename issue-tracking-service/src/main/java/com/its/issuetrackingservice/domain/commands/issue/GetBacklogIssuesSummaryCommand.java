package com.its.issuetrackingservice.domain.commands.issue;

import com.its.issuetrackingservice.domain.commands.Command;
import com.its.issuetrackingservice.domain.service.IssueService;
import com.its.issuetrackingservice.domain.util.SpringContext;
import com.its.issuetrackingservice.infrastructure.dto.UserContext;
import com.its.issuetrackingservice.infrastructure.dto.response.IssueSummaryResponse;
import com.its.issuetrackingservice.infrastructure.persistence.entity.Issue;
import com.its.issuetrackingservice.infrastructure.persistence.mapper.IssueMapper;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;


@Builder
@AllArgsConstructor
public class GetBacklogIssuesSummaryCommand extends Command<Page<IssueSummaryResponse>> {
    // Inputs
    private Long projectId;
    private Pageable pageable;

    // Generates
    private Page<Issue> issues;

    // Services
    private IssueService issueService;
    private IssueMapper issueMapper;
    private UserContext userContext;

    @Override
    public void init() {
        this.issueService = SpringContext.getBean(IssueService.class);
        this.issueMapper = SpringContext.getBean(IssueMapper.class);
        this.userContext = SpringContext.getBean(UserContext.class);
    }

    @Override
    public Page<IssueSummaryResponse> execute() {
        userContext.applyAccessToProject(projectId);

        this.issues = issueService.getBacklogIssues(projectId, pageable);
        if (Boolean.TRUE.equals(getReturnResultAfterExecution())) {
            return getResult().orElse(null);
        }

        return null;
    }

    @Override
    public Optional<Page<IssueSummaryResponse>> getResult() {
        return Optional.ofNullable(issueMapper.toSummaryPageResponse(issues));
    }

}
