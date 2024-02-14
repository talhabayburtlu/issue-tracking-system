package com.its.issuetrackingservice.api.controller;


import com.its.issuetrackingservice.api.dto.response.AttachmentResponse;
import com.its.issuetrackingservice.api.dto.response.AttachmentSummaryResponse;
import com.its.issuetrackingservice.api.model.GenericRestResponse;
import com.its.issuetrackingservice.api.service.AttachmentControllerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/attachment")
@AllArgsConstructor
@Slf4j
public class AttachmentController {

    private final AttachmentControllerService attachmentControllerService;

    @PostMapping("/issue/{issue_id}")
    public GenericRestResponse<AttachmentSummaryResponse> uploadAttachment(@RequestParam("file") MultipartFile file,
                                                                           @PathVariable("issue_id") Long issueId) {
        return GenericRestResponse.of(attachmentControllerService.uploadAttachment(issueId, file));
    }

    @GetMapping("/issue/{issue_id}")
    public GenericRestResponse<List<AttachmentResponse>> getIssueAttachments(@PathVariable("issue_id") Long issueId) {
        return GenericRestResponse.of(attachmentControllerService.getTaskAttachments(issueId));
    }

}
