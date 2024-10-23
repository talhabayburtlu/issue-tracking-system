package com.its.issuetrackingservice.infrastructure.persistence.repository;

import com.its.issuetrackingservice.infrastructure.persistence.entity.ActivityAttachment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Set;

public interface ActivityAttachmentRepository extends JpaRepository<ActivityAttachment, Long> {

    @Query("SELECT COUNT(*) FROM ActivityAttachment a WHERE a.activity.id = :activityId")
    Long getCountByActivityId(Long activityId);

    Set<ActivityAttachment> findAllByActivityId(Long activityId);

}
