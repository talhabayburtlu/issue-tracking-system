package com.its.issuetrackingservice.api.service;

import com.its.issuetrackingservice.api.dto.request.IssueCreateRequest;
import com.its.issuetrackingservice.api.dto.request.IssuePatchRequest;
import com.its.issuetrackingservice.api.dto.response.IssueDetailResponse;
import com.its.issuetrackingservice.api.dto.response.IssueSummaryResponse;
import com.its.issuetrackingservice.api.mapper.IssueMapper;
import com.its.issuetrackingservice.domain.service.IssueDomainService;
import com.its.issuetrackingservice.persistence.entity.Issue;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;


@Slf4j
@Service
@RequiredArgsConstructor
public class IssueControllerService {
    private final IssueDomainService issueDomainService;
    private final IssueMapper issueMapper;

    public IssueDetailResponse getIssueDetail(Long issueId) {
        Issue issue = issueDomainService.getIssueDetail(issueId);
        return issueMapper.toDetailResponse(issue);
    }

    public List<IssueSummaryResponse> getSprintIssues(Long sprintId) {
        List<Issue> issues = issueDomainService.getSprintIssues(sprintId);
        return issueMapper.toSummaryListResponse(issues);
    }

    public IssueSummaryResponse createIssue(IssueCreateRequest issueRequest) {
        Issue issue = issueMapper.toEntity(issueRequest);
        issue = issueDomainService.createIssue(issue);
        return issueMapper.toSummaryResponse(issue);
    }

    public IssueSummaryResponse patchIssue(Long issueId, IssuePatchRequest issueRequest) {
        Issue existingIssue = issueDomainService.getIssueById(issueId);
        Issue patchedIssue = issueMapper.patchEntityViaPatchEntityRequest(issueRequest, existingIssue);

        patchedIssue = issueDomainService.patchIssue(existingIssue, patchedIssue);

        return issueMapper.toSummaryResponse(patchedIssue);
    }
}
