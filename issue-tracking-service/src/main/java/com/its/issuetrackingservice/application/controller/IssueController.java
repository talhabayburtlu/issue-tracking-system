package com.its.issuetrackingservice.application.controller;

import com.its.issuetrackingservice.domain.commands.Invoker;
import com.its.issuetrackingservice.domain.commands.issue.*;
import com.its.issuetrackingservice.infrastructure.dto.GenericRestResponse;
import com.its.issuetrackingservice.infrastructure.dto.request.IssueRequest;
import com.its.issuetrackingservice.infrastructure.dto.response.IssueDetailResponse;
import com.its.issuetrackingservice.infrastructure.dto.response.IssueSummaryResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
    @Operation(summary = "Get summary of sprint issues")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Summary of spring issues returned", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = List.class)) }),
            @ApiResponse(responseCode = "403", description = "No access to project", content = @Content),
            @ApiResponse(responseCode = "404", description = "Project or sprint does not exist", content = @Content)
    })
    public GenericRestResponse<List<IssueSummaryResponse>> getSprintIssuesSummary(@Parameter(name = "project_id" , description = "The id of the project", required = true) @PathVariable("project_id") Long projectId,
                                                                                  @Parameter(name = "sprint_id" , description = "The id of sprint to get issues", required = true) @PathVariable("sprint_id") Long sprintId) {
        GetSprintIssuesSummaryCommand command = GetSprintIssuesSummaryCommand.builder()
                .projectId(projectId)
                .sprintId(sprintId)
                .build();
        return GenericRestResponse.of(invoker.run(command));
    }

    @GetMapping("/project/{project_id}")
    @Operation(summary = "Get backlog issues of project")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Backlog issues of project returned", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = List.class)) }),
            @ApiResponse(responseCode = "403", description = "No access to project", content = @Content),
            @ApiResponse(responseCode = "404", description = "Project does not exist", content = @Content)
    })
    public GenericRestResponse<List<IssueSummaryResponse>> getBacklogIssuesSummary(@Parameter(name = "project_id" , description = "The id of the project", required = true) @PathVariable("project_id") Long projectId,
                                                                                   Pageable pageable) {
        GetBacklogIssuesSummaryCommand command = GetBacklogIssuesSummaryCommand.builder()
                .projectId(projectId)
                .pageable(pageable)
                .build();
        Page<IssueSummaryResponse> issueSummaryResponses = invoker.run(command);
        return GenericRestResponse.of(issueSummaryResponses.toList(), issueSummaryResponses.getPageable());
    }

    @PostMapping("/project/{project_id}")
    @Operation(summary = "Create draft issue in a project")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Created issue returned in detail ", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = IssueDetailResponse.class)) }),
            @ApiResponse(responseCode = "403", description = "No access to project", content = @Content),
            @ApiResponse(responseCode = "404", description = "Project does not exist", content = @Content)
    })
    public GenericRestResponse<IssueDetailResponse> createDraftIssue(@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Issue request that creating draft issue", required = true) @RequestBody IssueRequest issueRequest,
                                                                     @Parameter(name = "project_id" , description = "The id of the project", required = true) @PathVariable("project_id") Long projectId) {
        CreateDraftIssueCommand command = CreateDraftIssueCommand.builder()
                .issueRequest(issueRequest)
                .projectId(projectId)
                .build();
        return GenericRestResponse.of(invoker.run(command));
    }

    @GetMapping("/{issue_id}")
    @Operation(summary = "Get issue in detail")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Issue returned in detail ", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = IssueDetailResponse.class)) }),
            @ApiResponse(responseCode = "403", description = "No access to project", content = @Content),
            @ApiResponse(responseCode = "404", description = "Issue does not exist", content = @Content)
    })
    public GenericRestResponse<IssueDetailResponse> getIssueDetail(@Parameter(name = "issue_id" , description = "The id of the issue", required = true) @PathVariable("issue_id") Long issueId) {
        GetIssueDetailCommand command = GetIssueDetailCommand.builder()
                .issueId(issueId)
                .build();
        return GenericRestResponse.of(invoker.run(command));
    }


    @PostMapping("/{issue_id}")
    @Operation(summary = "Publish draft issue")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Publishes draft issue", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = IssueDetailResponse.class)) }),
            @ApiResponse(responseCode = "403", description = "No access to project", content = @Content),
            @ApiResponse(responseCode = "404", description = "Issue does not exist", content = @Content)
    })
    public GenericRestResponse<IssueDetailResponse> publishDraftIssue(@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Issue request that publishes draft issue", required = true) @RequestBody IssueRequest issueRequest,
                                                                      @Parameter(name = "issue_id" , description = "The id of the issue", required = true) @PathVariable("issue_id") Long issueId) {
        PublishDraftIssueCommand command = PublishDraftIssueCommand.builder()
                .issueRequest(issueRequest)
                .issueId(issueId)
                .build();
        return GenericRestResponse.of(invoker.run(command));
    }


    @PatchMapping("/{issue_id}")
    @Operation(summary = "Update published or draft issue")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Updates issue with given content", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = IssueDetailResponse.class)) }),
            @ApiResponse(responseCode = "403", description = "No access to project", content = @Content),
            @ApiResponse(responseCode = "404", description = "Issue does not exist", content = @Content)
    })
    public GenericRestResponse<IssueDetailResponse> updateIssue(@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Issue request that updates published or draft issue", required = true) @RequestBody IssueRequest issueRequest,
                                                                @Parameter(name = "issue_id" , description = "The id of the issue", required = true) @PathVariable("issue_id") Long issueId) {
        UpdateIssueCommand command = UpdateIssueCommand.builder()
                .issueRequest(issueRequest)
                .issueId(issueId)
                .sendNotification(Boolean.TRUE)
                .createActivity(Boolean.TRUE)
                .build();
        return GenericRestResponse.of(invoker.run(command));
    }

}
