package com.its.issuetrackingservice.application.controller;

import com.its.issuetrackingservice.domain.commands.Invoker;
import com.its.issuetrackingservice.domain.commands.issue.*;
import com.its.issuetrackingservice.infrastructure.dto.GenericRestResponse;
import com.its.issuetrackingservice.infrastructure.dto.request.IssueRequest;
import com.its.issuetrackingservice.infrastructure.dto.response.IssueDetailResponse;
import com.its.issuetrackingservice.infrastructure.dto.response.IssueSummaryResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/issue")
@AllArgsConstructor
@Slf4j
public class IssueController {
    private final Invoker invoker;

    @GetMapping("/project/{project_id}/sprint/{sprint_id}")
    public GenericRestResponse<List<IssueSummaryResponse>> getSprintIssuesSummary(@PathVariable("project_id") Long projectId,
                                                                                  @PathVariable("sprint_id") Long sprintId) {
        GetSprintIssuesSummaryCommand command = GetSprintIssuesSummaryCommand.builder()
                .projectId(projectId)
                .sprintId(sprintId)
                .build();
        return GenericRestResponse.of(invoker.run(command));
    }

    @GetMapping("/project/{project_id}")
    public GenericRestResponse<List<IssueSummaryResponse>> getBacklogIssuesSummary(@PathVariable("project_id") Long projectId,
                                                                                   Pageable pageable) {
        GetBacklogIssuesSummaryCommand command = GetBacklogIssuesSummaryCommand.builder()
                .projectId(projectId)
                .pageable(pageable)
                .build();
        Page<IssueSummaryResponse> issueSummaryResponses = invoker.run(command);
        return GenericRestResponse.of(issueSummaryResponses.toList(), issueSummaryResponses.getPageable());
    }

    @PostMapping("/project/{project_id}")
    public GenericRestResponse<IssueDetailResponse> createDraftIssue(@RequestBody IssueRequest issueRequest, @PathVariable("project_id") Long projectId) {
        CreateDraftIssueCommand command = CreateDraftIssueCommand.builder()
                .issueRequest(issueRequest)
                .projectId(projectId)
                .build();
        return GenericRestResponse.of(invoker.run(command));
    }

    @GetMapping("/{issue_id}")
    public GenericRestResponse<IssueDetailResponse> getIssueDetail(@PathVariable("issue_id") Long issueId) {
        GetIssueDetailCommand command = GetIssueDetailCommand.builder()
                .issueId(issueId)
                .build();
        return GenericRestResponse.of(invoker.run(command));
    }


    @PostMapping("/{issue_id}")
    public GenericRestResponse<IssueDetailResponse> publishDraftIssue(@RequestBody IssueRequest issueRequest, @PathVariable("issue_id") Long issueId) {
        PublishDraftIssueCommand command = PublishDraftIssueCommand.builder()
                .issueRequest(issueRequest)
                .issueId(issueId)
                .build();
        return GenericRestResponse.of(invoker.run(command));
    }


    @PatchMapping("/{issue_id}")
    public GenericRestResponse<IssueDetailResponse> updateIssue(@RequestBody IssueRequest issueRequest,
                                                                @PathVariable("issue_id") Long issueId) {
        UpdateIssueCommand command = UpdateIssueCommand.builder()
                .issueRequest(issueRequest)
                .issueId(issueId)
                .sendNotification(Boolean.TRUE)
                .createActivity(Boolean.TRUE)
                .build();
        return GenericRestResponse.of(invoker.run(command));
    }

}
