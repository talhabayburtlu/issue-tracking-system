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

    @GetMapping(path = "/issue/{issue_id}")
    public GenericRestResponse<List<AttachmentResponse>> getIssueAttachments(@PathVariable("issue_id") Long issueId) {
        GetIssueAttachmentsCommand command = GetIssueAttachmentsCommand.builder()
                .issueId(issueId)
                .build();
        return GenericRestResponse.of(invoker.run(command));
    }

    @GetMapping(path = "/issue/{issue_id}/activity/{activity_id}")
    public GenericRestResponse<List<AttachmentResponse>> getActivityItemAttachments(@PathVariable("issue_id") Long issueId, @PathVariable("activity_id") Long activityId) {
        GetActivityAttachmentsCommand command = GetActivityAttachmentsCommand.builder()
                .activityId(activityId)
                .issueId(issueId)
                .build();
        return GenericRestResponse.of(invoker.run(command));
    }

    @PostMapping(path = "/issue/{issue_id}", consumes = {MULTIPART_FORM_DATA_VALUE})
    public GenericRestResponse<IssueAttachmentSummaryResponse> uploadIssueAttachment(@RequestParam("file") MultipartFile file,
                                                                                     @PathVariable("issue_id") Long issueId) {
        UploadIssueAttachmentCommand command = UploadIssueAttachmentCommand.builder()
                .issueId(issueId)
                .file(file)
                .build();
        return GenericRestResponse.of(invoker.run(command));
    }

    @PostMapping(path = "/issue/{issue_id}/activity/{activity_id}", consumes = {MULTIPART_FORM_DATA_VALUE})
    public GenericRestResponse<ActivityAttachmentSummaryResponse> uploadActivityAttachment(@RequestParam("file") MultipartFile file,
                                                                                           @PathVariable("issue_id") Long issueId,
                                                                                           @PathVariable("activity_id") Long activityId) {
        UploadActivityAttachmentCommand command = UploadActivityAttachmentCommand.builder()
                .activityId(activityId)
                .issueId(issueId)
                .file(file)
                .build();
        return GenericRestResponse.of(invoker.run(command));
    }



}
