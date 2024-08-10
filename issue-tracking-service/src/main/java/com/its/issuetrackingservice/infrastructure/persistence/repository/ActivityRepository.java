package com.its.issuetrackingservice.infrastructure.persistence.repository;

import com.its.issuetrackingservice.infrastructure.persistence.entity.Activity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ActivityRepository extends JpaRepository<Activity, Long> {

    @Query("SELECT a FROM Activity a WHERE a.id = :id AND a.issue.id = :issueId AND a.issue.project.id = :projectId")
    Optional<Activity> getByIdAndIssueIdAndProjectId(Long id, Long issueId, Long projectId);

    List<Activity> getActivitiesByIssueId(Long issueId, Pageable pageable);
}
