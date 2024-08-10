package com.its.issuetrackingservice.infrastructure.persistence.repository;

import com.its.issuetrackingservice.infrastructure.persistence.entity.IssueAttachment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IssueAttachmentRepository extends JpaRepository<IssueAttachment, Long> {

    @Query("SELECT COUNT(*) FROM IssueAttachment a WHERE a.issue.id = :issueId")
    Long getCountByIssueId(Long issueId);

}
