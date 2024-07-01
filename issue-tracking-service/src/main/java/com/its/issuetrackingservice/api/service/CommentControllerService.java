package com.its.issuetrackingservice.api.service;

import com.its.issuetrackingservice.api.dto.request.CommentRequest;
import com.its.issuetrackingservice.api.dto.response.CommentResponse;
import com.its.issuetrackingservice.api.mapper.CommentMapper;
import com.its.issuetrackingservice.domain.service.CommentDomainService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;


@Slf4j
@Service
@RequiredArgsConstructor
public class CommentControllerService {
    private final CommentDomainService commentDomainService;
    private final CommentMapper commentMapper;

    @Transactional
    public CommentResponse createComment(Long issueId, CommentRequest commentRequest) {
        Comment comment = commentMapper.toEntity(commentRequest);
        comment = commentDomainService.createComment(issueId, comment);
        return commentMapper.toResponse(comment);
    }

    @Transactional
    public CommentResponse patchComment(Long commentId, CommentRequest commentRequest) {
        Comment existingComment = commentDomainService.getCommentById(commentId);
        Comment patchedComment = commentMapper.patchEntityViaPatchEntityRequest(commentRequest, existingComment);

        patchedComment = commentDomainService.patchComment(patchedComment);

        return commentMapper.toResponse(patchedComment);
    }

    public List<CommentResponse> getIssueComments(Long issueId, Pageable pageable) {
        List<Comment> comments = commentDomainService.getIssueComments(issueId, pageable);
        return commentMapper.toListResponse(comments);
    }
}
