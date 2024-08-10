package com.its.issuetrackingservice.infrastructure.persistence.repository;

import com.its.issuetrackingservice.infrastructure.persistence.entity.ActivityAttachment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ActivityAttachmentRepository extends JpaRepository<ActivityAttachment, Long> {

    @Query("SELECT COUNT(*) FROM IssueAttachment a WHERE a.issue.id = :issueId")
    Long getCountByActivityId(Long issueId);

}
