package com.its.issuetrackingservice.infrastructure.persistence.repository;

import com.its.issuetrackingservice.infrastructure.persistence.entity.Issue;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface IssueRepository extends JpaRepository<Issue, Long> {
    @Query("SELECT COALESCE(MAX(CAST(REPLACE(i.code, CONCAT(i.project.abbreviation , '-'), '') AS INTEGER)), 0) FROM Issue i WHERE i.project.id = :projectId")
    Long getLatestIssueNumberInProject(Long projectId);

    List<Issue> getAllByProjectIdAndSprintIdOrderByCreatedDateAsc(Long projectId, Long sprintId);

    Page<Issue> getAllByProjectIdAndSprintIsNullOrderByCreatedDateAsc(Long projectId, Pageable pageable);

    Optional<Issue> getIssueByIdAndProjectId(Long issueId, Long projectId);

    @Query("SELECT i.project.id FROM Issue i WHERE i.id = :issueId")
    Long getProjectIdByIssueId(Long issueId);

    boolean existsBySprintIdIn(List<Long> sprintIds);

    boolean existsByStateIdIn(List<Long> stateIds);
    boolean existsByCategoryIdIn(List<Long> categoryIds);

}
