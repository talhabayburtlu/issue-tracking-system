package com.its.issuetrackingservice.infrastructure.persistence.repository;

import com.its.issuetrackingservice.infrastructure.persistence.entity.Attachment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttachmentRepository extends JpaRepository<Attachment, Long> {

}
