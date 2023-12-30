package com.its.issuetrackingservice.domain.service;

import com.its.issuetrackingservice.api.model.UserContext;
import com.its.issuetrackingservice.domain.constants.I18nExceptionKeys;
import com.its.issuetrackingservice.domain.exception.DataNotFoundException;
import com.its.issuetrackingservice.domain.exception.WrongUsageException;
import com.its.issuetrackingservice.persistence.entity.Comment;
import com.its.issuetrackingservice.persistence.entity.Issue;
import com.its.issuetrackingservice.persistence.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentDomainService {
	private final UserContext userContext;
	private final IssueDomainService issueDomainService;

	private final CommentRepository commentRepository;

	public Comment getCommentById(Long commentId) {
		Optional<Comment> comment = commentRepository.findById(commentId);
		if (comment.isEmpty()) {
			throw new DataNotFoundException(I18nExceptionKeys.COMMENT_NOT_FOUND, String.format("comment id=%d", commentId));
		}

		return comment.get();
	}

	public Comment createComment(Long issueId, Comment comment) {
		Issue issue = issueDomainService.getIssueById(issueId);
		issueDomainService.validateAccessToProjectOfIssue(issue);

		comment.setAuditableFields(userContext);

		comment.setIssue(issue);

		return commentRepository.save(comment);
	}

	public Comment patchComment(Comment comment) {
		issueDomainService.validateAccessToProjectOfIssue(comment.getIssue());
		validateAuthUserIsCommentCreator(comment);

		comment.setAuditableFields(userContext);

		return commentRepository.save(comment);
	}

	public List<Comment> getIssueComments(Long issueId, Pageable pageable) {
		Issue issue = issueDomainService.getIssueById(issueId);
		issueDomainService.validateAccessToProjectOfIssue(issue);

		return commentRepository.getCommentsByIssueId(issueId, pageable);
	}

	private void validateAuthUserIsCommentCreator(Comment comment) {
		if (!Objects.equals(comment.getCreatorUser().getId(), userContext.getUser().getId())) {
			throw new WrongUsageException(I18nExceptionKeys.COMMENT_CREATOR_MUST_MATCH_AUTH_USER);
		}
 	}
}
