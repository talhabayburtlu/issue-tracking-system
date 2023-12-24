package com.its.issuetrackingservice.persistence.repository;

import com.its.issuetrackingservice.persistence.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
