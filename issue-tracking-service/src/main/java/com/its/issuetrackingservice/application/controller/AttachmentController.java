package com.its.issuetrackingservice.application.controller;


import com.its.issuetrackingservice.domain.commands.Invoker;
import com.its.issuetrackingservice.domain.commands.attachment.GetActivityAttachmentsCommand;
import com.its.issuetrackingservice.domain.commands.attachment.GetIssueAttachmentsCommand;
import com.its.issuetrackingservice.domain.commands.attachment.UploadActivityAttachmentCommand;
import com.its.issuetrackingservice.domain.commands.attachment.UploadIssueAttachmentCommand;
import com.its.issuetrackingservice.infrastructure.dto.GenericRestResponse;
import com.its.issuetrackingservice.infrastructure.dto.response.ActivityAttachmentSummaryResponse;
import com.its.issuetrackingservice.infrastructure.dto.response.AttachmentResponse;
import com.its.issuetrackingservice.infrastructure.dto.response.IssueAttachmentSummaryResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;

@RestController
@RequestMapping("/attachment")
@AllArgsConstructor
@Slf4j
public class AttachmentController {

    private final Invoker invoker;

    @GetMapping(path = "/project/{project_id}/issue/{issue_id}")
    public GenericRestResponse<List<AttachmentResponse>> getIssueAttachments(@PathVariable("project_id") Long projectId,
                                                                             @PathVariable("issue_id") Long issueId) {
        GetIssueAttachmentsCommand command = GetIssueAttachmentsCommand.builder()
                .issueId(issueId)
                .projectId(projectId)
                .build();
        return GenericRestResponse.of(invoker.run(command));
    }

    @GetMapping(path = "/project/{project_id}/issue/{issue_id}/activity_item/{activity_item_id}")
    public GenericRestResponse<List<AttachmentResponse>> getActivityItemAttachments(@PathVariable("project_id") Long projectId,
                                                                             @PathVariable("issue_id") Long issueId,
                                                                             @PathVariable("activity_item_id") Long activityItemId) {
        GetActivityAttachmentsCommand command = GetActivityAttachmentsCommand.builder()
                .activityItemId(activityItemId)
                .issueId(issueId)
                .projectId(projectId)
                .build();
        return GenericRestResponse.of(invoker.run(command));
    }

    @PostMapping(path = "/project/{project_id}/issue/{issue_id}", consumes = {MULTIPART_FORM_DATA_VALUE})
    public GenericRestResponse<IssueAttachmentSummaryResponse> uploadIssueAttachment(@RequestParam("file") MultipartFile file,
                                                                                     @PathVariable("project_id") Long projectId,
                                                                                     @PathVariable("issue_id") Long issueId) {
        UploadIssueAttachmentCommand command = UploadIssueAttachmentCommand.builder()
                .issueId(issueId)
                .projectId(projectId)
                .file(file)
                .build();
        return GenericRestResponse.of(invoker.run(command));
    }

    @PostMapping(path = "/project/{project_id}/issue/{issue_id}/activity_item/{activity_item_id}", consumes = {MULTIPART_FORM_DATA_VALUE})
    public GenericRestResponse<ActivityAttachmentSummaryResponse> uploadActivityItemAttachment(@RequestParam("file") MultipartFile file,
                                                                                               @PathVariable("project_id") Long projectId,
                                                                                               @PathVariable("issue_id") Long issueId,
                                                                                               @PathVariable("activity_item_id") Long activityItemId) {
        UploadActivityAttachmentCommand command = UploadActivityAttachmentCommand.builder()
                .activityId(activityItemId)
                .issueId(issueId)
                .projectId(projectId)
                .file(file)
                .build();
        return GenericRestResponse.of(invoker.run(command));
    }



}
