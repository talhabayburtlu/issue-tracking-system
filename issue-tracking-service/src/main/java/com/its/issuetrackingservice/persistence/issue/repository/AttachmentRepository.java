package com.its.issuetrackingservice.persistence.issue.repository;

import com.its.issuetrackingservice.persistence.issue.entity.Attachment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttachmentRepository extends JpaRepository<Attachment, Long> {
}
