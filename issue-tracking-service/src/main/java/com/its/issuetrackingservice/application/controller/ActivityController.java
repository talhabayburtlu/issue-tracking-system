package com.its.issuetrackingservice.application.controller;


import com.its.issuetrackingservice.domain.commands.Invoker;
import com.its.issuetrackingservice.domain.commands.activity.CommentOnIssueCommand;
import com.its.issuetrackingservice.domain.commands.activity.GetIssueActivitiesCommand;
import com.its.issuetrackingservice.domain.commands.activity.UpdateCommentOnIssueCommand;
import com.its.issuetrackingservice.infrastructure.dto.GenericRestResponse;
import com.its.issuetrackingservice.infrastructure.dto.request.CommentRequest;
import com.its.issuetrackingservice.infrastructure.dto.response.ActivityResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/activity")
@AllArgsConstructor
@Slf4j
public class ActivityController {

    private final Invoker invoker;

    @GetMapping("/project/{project_id}/issue/{issue_id}")
    public GenericRestResponse<List<ActivityResponse>> getIssueActivities(@PathVariable("project_id") Long projectId,
                                                                          @PathVariable("issue_id") Long issueId,
                                                                          Pageable pageable) {
        GetIssueActivitiesCommand command = GetIssueActivitiesCommand.builder()
                .projectId(projectId)
                .issueId(issueId)
                .pageable(pageable)
                .build();
        return GenericRestResponse.of(invoker.run(command));
    }

    @PostMapping("/project/{project_id}/issue/{issue_id}")
    public GenericRestResponse<ActivityResponse> commentOnIssue(@RequestBody CommentRequest commentRequest,
                                                                @PathVariable("project_id") Long projectId,
                                                                @PathVariable("issue_id") Long issueId) {
        CommentOnIssueCommand command = CommentOnIssueCommand.builder()
                .commentRequest(commentRequest)
                .projectId(projectId)
                .issueId(issueId)
                .build();
        return GenericRestResponse.of(invoker.run(command));
    }

    @PatchMapping("{activity_id}/project/{project_id}/issue/{issue_id}")
    public GenericRestResponse<ActivityResponse> updateCommentOnIssue(@RequestBody CommentRequest commentRequest,
                                                                     @PathVariable("activity_id") Long activityId,
                                                                     @PathVariable("project_id") Long projectId,
                                                                     @PathVariable("issue_id") Long issueId) {
        UpdateCommentOnIssueCommand command = UpdateCommentOnIssueCommand.builder()
                .commentRequest(commentRequest)
                .activityId(activityId)
                .projectId(projectId)
                .issueId(issueId)
                .build();
        return GenericRestResponse.of(invoker.run(command));
    }

}
