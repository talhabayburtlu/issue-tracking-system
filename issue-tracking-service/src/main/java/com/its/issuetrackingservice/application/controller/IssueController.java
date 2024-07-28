package com.its.issuetrackingservice.application.controller;

import com.its.issuetrackingservice.domain.commands.Invoker;
import com.its.issuetrackingservice.domain.commands.issue.*;
import com.its.issuetrackingservice.infrastructure.dto.GenericRestResponse;
import com.its.issuetrackingservice.infrastructure.dto.request.IssueRequest;
import com.its.issuetrackingservice.infrastructure.dto.response.IssueDetailResponse;
import com.its.issuetrackingservice.infrastructure.dto.response.IssueSummaryResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
        invoker.run(command);
        return GenericRestResponse.of(command.getResult().orElse(null));
    }

    @GetMapping("/project/{project_id}")
    public GenericRestResponse<List<IssueSummaryResponse>> getBacklogIssuesSummary(@PathVariable("project_id") Long projectId) {
        GetBacklogIssuesSummaryCommand command = GetBacklogIssuesSummaryCommand.builder()
                .projectId(projectId)
                .build();
        invoker.run(command);
        return GenericRestResponse.of(command.getResult().orElse(null));
    }

    @GetMapping("/project/{project_id}/{issue_id}")
    public GenericRestResponse<IssueDetailResponse> getIssueDetail(@PathVariable("project_id") Long projectId, @PathVariable("issue_id") Long issueId) {
        GetIssueDetailCommand command = GetIssueDetailCommand.builder()
                .projectId(projectId)
                .issueId(issueId)
                .build();
        invoker.run(command);
        return GenericRestResponse.of(command.getResult().orElse(null));
    }


    @PostMapping("/project/{project_id}")
    public GenericRestResponse<IssueDetailResponse> createIssue(@RequestBody IssueRequest issueRequest, @PathVariable("project_id") Long projectId) {
        CreateIssueCommand command = CreateIssueCommand.builder()
                .issueRequest(issueRequest)
                .projectId(projectId)
                .build();
        invoker.run(command);
        return GenericRestResponse.of(command.getResult().orElse(null));
    }

    @PatchMapping("{issue_id}/project/{project_id}")
    public GenericRestResponse<IssueDetailResponse> updateIssue(@RequestBody IssueRequest issueRequest,
                                                                @PathVariable("issue_id") Long issueId,
                                                                @PathVariable("project_id") Long projectId) {
        UpdateIssueCommand command = UpdateIssueCommand.builder()
                .issueRequest(issueRequest)
                .issueId(issueId)
                .projectId(projectId)
                .build();
        invoker.run(command);
        return GenericRestResponse.of(command.getResult().orElse(null));
    }

}
