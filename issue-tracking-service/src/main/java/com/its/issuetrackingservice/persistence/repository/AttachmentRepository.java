package com.its.issuetrackingservice.persistence.repository;

import com.its.issuetrackingservice.persistence.entity.Attachment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttachmentRepository extends JpaRepository<Attachment, Long> {
}
