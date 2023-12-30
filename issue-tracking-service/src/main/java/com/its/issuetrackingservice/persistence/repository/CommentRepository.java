package com.its.issuetrackingservice.persistence.repository;

import com.its.issuetrackingservice.persistence.entity.Comment;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Transactional
    List<Comment> getCommentsByIssueId(Long issueId, Pageable pageable);
}
