package com.its.issuetrackingservice.api.controller;

import com.its.issuetrackingservice.api.model.GenericRestResponse;
import com.its.issuetrackingservice.api.service.IssueControllerService;
import com.its.issuetrackingservice.domain.dto.issue.request.IssueCreateRequest;
import com.its.issuetrackingservice.domain.dto.issue.request.IssuePatchRequest;
import com.its.issuetrackingservice.domain.dto.issue.response.IssueDetailResponse;
import com.its.issuetrackingservice.domain.dto.issue.response.IssueSummaryResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/issue")
@AllArgsConstructor
@Slf4j
public class IssueController {

    private final IssueControllerService issueControllerService;

    @PostMapping("/")
    public GenericRestResponse<IssueSummaryResponse> createIssue(@RequestBody IssueCreateRequest issueRequest) {
        return GenericRestResponse.of(issueControllerService.createIssue(issueRequest));
    }

    @PatchMapping("/{issue_id}")
    public GenericRestResponse<IssueSummaryResponse> patchIssue(@PathVariable("issue_id") Long issueId,
                                                                @RequestBody IssuePatchRequest issueRequest) {
        return GenericRestResponse.of(issueControllerService.patchIssue(issueId, issueRequest));
    }
    @GetMapping("/{issue_id}")
    public GenericRestResponse<IssueDetailResponse> getIssueDetail(@PathVariable("issue_id") Long issueId) {
        return GenericRestResponse.of(issueControllerService.getIssueDetail(issueId));
    }

    @GetMapping("/sprint/{sprint_id}")
    public GenericRestResponse<List<IssueSummaryResponse>> getSprintIssues(@PathVariable("sprint_id") Long sprintId) {
        return GenericRestResponse.of(issueControllerService.getSprintIssues(sprintId));
    }

}
