package com.its.issuetrackingservice.infrastructure.persistence.repository;

import com.its.issuetrackingservice.infrastructure.persistence.entity.Issue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface IssueRepository extends JpaRepository<Issue, Long> {
    @Query("SELECT COALESCE(MAX(CAST(REPLACE(i.code, CONCAT(i.project.abbreviation , '-'), '') AS INTEGER)), 0) FROM Issue i WHERE i.project.id = :projectId")
    Long getLatestIssueNumberInProject(Long projectId);

    List<Issue> getAllByProjectIdAndSprintIdOrderByCreatedDateAsc(Long projectId, Long sprintId);

    List<Issue> getAllByProjectIdAndSprintIsNullOrderByCreatedDateAsc(Long projectId);

    Optional<Issue> getIssueByIdAndProjectId(Long issueId, Long projectId);

    // TODO: Cache this call
    @Query("SELECT i.project.id FROM Issue i WHERE i.id = :issueId")
    Long getProjectIdByIssueId(Long issueId);

}
