package com.its.issuetrackingservice.infrastructure.persistence.repository;

import com.its.issuetrackingservice.infrastructure.persistence.entity.Activity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ActivityRepository extends JpaRepository<Activity, Long> {

    @Query("SELECT a FROM Activity a WHERE a.id = :id AND a.issue.id = :issueId")
    Optional<Activity> getByIdAndIssueId(Long id, Long issueId);

    List<Activity> getActivitiesByIssueId(Long issueId, Pageable pageable);
}
