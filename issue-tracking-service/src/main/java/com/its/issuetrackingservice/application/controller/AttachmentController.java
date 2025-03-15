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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
    @Operation(summary = "Get all attachments of the issue")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of issue attachments returned", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = List.class)) }),
            @ApiResponse(responseCode = "403", description = "No access to project of the issue", content = @Content),
            @ApiResponse(responseCode = "404", description = "Issue does not exist", content = @Content)
    })
    public GenericRestResponse<List<AttachmentResponse>> getIssueAttachments(@Parameter(name = "issue_id" , description = "The id of the issue of the attachments", required = true) @PathVariable("issue_id") Long issueId) {
        GetIssueAttachmentsCommand command = GetIssueAttachmentsCommand.builder()
                .issueId(issueId)
                .build();
        return GenericRestResponse.of(invoker.run(command));
    }

    @GetMapping(path = "/issue/{issue_id}/activity/{activity_id}")
    @Operation(summary = "Get all attachments of the activity")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of activity attachments returned", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = List.class)) }),
            @ApiResponse(responseCode = "403", description = "No access to project of the issue", content = @Content),
            @ApiResponse(responseCode = "404", description = "Issue or activity does not exist", content = @Content)
    })
    public GenericRestResponse<List<AttachmentResponse>> getActivityAttachments(@Parameter(name = "issue_id" , description = "The id of the issue", required = true) @PathVariable("issue_id") Long issueId,
                                                                                @Parameter(name = "activity_id" , description = "The id of the activity of attachments", required = true) @PathVariable("activity_id") Long activityId) {
        GetActivityAttachmentsCommand command = GetActivityAttachmentsCommand.builder()
                .activityId(activityId)
                .issueId(issueId)
                .build();
        return GenericRestResponse.of(invoker.run(command));
    }

    @PostMapping(path = "/issue/{issue_id}", consumes = {MULTIPART_FORM_DATA_VALUE})
    @Operation(summary = "Upload attachment to the issue")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Attachment is uploaded to issue", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = IssueAttachmentSummaryResponse.class)) }),
            @ApiResponse(responseCode = "403", description = "No access to project of the issue", content = @Content),
            @ApiResponse(responseCode = "404", description = "Issue does not exist", content = @Content)
    })
    public GenericRestResponse<IssueAttachmentSummaryResponse> uploadIssueAttachment(@Parameter(name = "file" , description = "The attachment that is selected to upload", required = true) @RequestParam("file") MultipartFile file,
                                                                                     @Parameter(name = "issue_id" , description = "The id of the issue of the attachment", required = true) @PathVariable("issue_id") Long issueId) {
        UploadIssueAttachmentCommand command = UploadIssueAttachmentCommand.builder()
                .issueId(issueId)
                .file(file)
                .build();
        return GenericRestResponse.of(invoker.run(command));
    }

    @PostMapping(path = "/issue/{issue_id}/activity/{activity_id}", consumes = {MULTIPART_FORM_DATA_VALUE})
    @Operation(summary = "Upload attachment to the activity of an issue")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Attachment is uploaded to activity", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ActivityAttachmentSummaryResponse.class)) }),
            @ApiResponse(responseCode = "403", description = "No access to project of the issue", content = @Content),
            @ApiResponse(responseCode = "404", description = "Issue or activity does not exist", content = @Content)
    })
    public GenericRestResponse<ActivityAttachmentSummaryResponse> uploadActivityAttachment(@Parameter(name = "file" , description = "The attachment that is selected to upload", required = true) @RequestParam("file") MultipartFile file,
                                                                                           @Parameter(name = "issue_id" , description = "The id of the issue of the attachment", required = true) @PathVariable("issue_id") Long issueId,
                                                                                           @Parameter(name = "activity_id" , description = "The id of the activity of attachments", required = true) @PathVariable("activity_id") Long activityId) {
        UploadActivityAttachmentCommand command = UploadActivityAttachmentCommand.builder()
                .activityId(activityId)
                .issueId(issueId)
                .file(file)
                .build();
        return GenericRestResponse.of(invoker.run(command));
    }



}
