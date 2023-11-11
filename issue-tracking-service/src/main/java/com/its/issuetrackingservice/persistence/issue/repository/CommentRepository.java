package com.its.issuetrackingservice.persistence.issue.repository;

import com.its.issuetrackingservice.persistence.issue.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
