package com.its.issuetrackingservice.infrastructure.persistence.repository;

import com.its.issuetrackingservice.infrastructure.persistence.entity.Attachment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AttachmentRepository extends JpaRepository<Attachment, Long> {

    @Query("SELECT COUNT(*) FROM Attachment a WHERE a.issue.id = :issueId")
    Long getCountByIssueId(Long issueId);

}
