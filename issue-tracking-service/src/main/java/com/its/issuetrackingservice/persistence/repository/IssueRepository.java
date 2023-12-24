package com.its.issuetrackingservice.persistence.repository;

import com.its.issuetrackingservice.persistence.entity.Issue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IssueRepository extends JpaRepository<Issue, Long> {
    @Query("SELECT COALESCE(MAX(i.number), 0) FROM Issue i WHERE i.project.id = :projectId")
    Long getLatestIssueNumberInProject(Long projectId);

    List<Issue> getAllBySprintId(Long sprintId);
}
