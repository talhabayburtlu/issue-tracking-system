package com.its.issuetrackingservice.application.controller;


import com.its.issuetrackingservice.domain.commands.Invoker;
import com.its.issuetrackingservice.domain.commands.activity.CommentOnIssueCommand;
import com.its.issuetrackingservice.domain.commands.activity.GetIssueActivitiesCommand;
import com.its.issuetrackingservice.domain.commands.activity.UpdateCommentOnIssueCommand;
import com.its.issuetrackingservice.infrastructure.dto.GenericRestResponse;
import com.its.issuetrackingservice.infrastructure.dto.request.CommentRequest;
import com.its.issuetrackingservice.infrastructure.dto.response.ActivityResponse;
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
@RequestMapping("/activity")
@AllArgsConstructor
@Slf4j
public class ActivityController {

    private final Invoker invoker;

    @GetMapping("/issue/{issue_id}")
    @Operation(summary = "Get list of issue activities")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of issue activities returned", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = List.class)) }),
            @ApiResponse(responseCode = "403", description = "No access to project of the issue", content = @Content),
            @ApiResponse(responseCode = "404", description = "Issue does not exist", content = @Content)
    })
    public GenericRestResponse<List<ActivityResponse>> getIssueActivities(@Parameter(name = "issue_id" , description = "The id of the issue of the activities", required = true) @PathVariable("issue_id") Long issueId,
                                                                          Pageable pageable) {
        GetIssueActivitiesCommand command = GetIssueActivitiesCommand.builder()
                .issueId(issueId)
                .pageable(pageable)
                .build();
        Page<ActivityResponse> activities = invoker.run(command);
        return GenericRestResponse.of(activities.toList(), activities.getPageable());
    }

    @PostMapping("/issue/{issue_id}")
    @Operation(summary = "Comment on an issue")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Comment created successfully on issue", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ActivityResponse.class)) }),
            @ApiResponse(responseCode = "403", description = "No access to project of the issue", content = @Content),
            @ApiResponse(responseCode = "404", description = "Issue does not exist", content = @Content)
    })
    public GenericRestResponse<ActivityResponse> commentOnIssue(@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Comment request that includes created comment", required = true) @RequestBody CommentRequest commentRequest,
                                                                @Parameter(name = "issue_id" , description = "The id of the issue of the activities", required = true) @PathVariable("issue_id") Long issueId) {
        CommentOnIssueCommand command = CommentOnIssueCommand.builder()
                .commentRequest(commentRequest)
                .issueId(issueId)
                .build();
        return GenericRestResponse.of(invoker.run(command));
    }

    @PatchMapping("{activity_id}/issue/{issue_id}")
    @Operation(summary = "Update the existing comment on an issue")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Comment updated successfully on issue", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ActivityResponse.class)) }),
            @ApiResponse(responseCode = "403", description = "No access to project of the issue", content = @Content),
            @ApiResponse(responseCode = "404", description = "Issue does not exist", content = @Content)
    })
    public GenericRestResponse<ActivityResponse> updateCommentOnIssue(@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Comment request that includes updated comment", required = true) @RequestBody CommentRequest commentRequest,
                                                                      @Parameter(name = "activity_id" , description = "The id of the comment activity which to be updated", required = true) @PathVariable("activity_id") Long activityId,
                                                                     @Parameter(name = "issue_id" , description = "The id of the issue of the activities", required = true) @PathVariable("issue_id") Long issueId) {
        UpdateCommentOnIssueCommand command = UpdateCommentOnIssueCommand.builder()
                .commentRequest(commentRequest)
                .activityId(activityId)
                .issueId(issueId)
                .build();
        return GenericRestResponse.of(invoker.run(command));
    }

}
