package com.its.issuetrackingservice.api.controller;

import com.its.issuetrackingservice.api.dto.comment.request.CommentRequest;
import com.its.issuetrackingservice.api.dto.comment.response.CommentResponse;
import com.its.issuetrackingservice.api.model.GenericRestResponse;
import com.its.issuetrackingservice.api.service.CommentControllerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comment")
@AllArgsConstructor
@Slf4j
public class CommentController {

    private final CommentControllerService commentControllerService;

    @PostMapping("/issue/{issue_id}")
    public GenericRestResponse<CommentResponse> creatComment(@RequestBody CommentRequest commentRequest,
                                                             @PathVariable("issue_id") Long issueId) {
        return GenericRestResponse.of(commentControllerService.createComment(issueId, commentRequest));
    }

    @PatchMapping("/{comment_id}")
    public GenericRestResponse<CommentResponse> patchComment(@RequestBody CommentRequest commentRequest,
                                                             @PathVariable("comment_id") Long commentId) {
        return GenericRestResponse.of(commentControllerService.patchComment(commentId, commentRequest));
    }

    @GetMapping("/issue/{issue_id}")
    public GenericRestResponse<List<CommentResponse>> getIssueComments(@PathVariable("issue_id") Long issueId, Pageable pageable) {
        return GenericRestResponse.of(commentControllerService.getIssueComments(issueId, pageable), pageable);
    }

}
