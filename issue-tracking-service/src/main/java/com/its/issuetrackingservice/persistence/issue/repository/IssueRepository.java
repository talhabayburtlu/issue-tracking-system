package com.its.issuetrackingservice.persistence.issue.repository;

import com.its.issuetrackingservice.persistence.issue.entity.Issue;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IssueRepository extends JpaRepository<Issue, Long> {
}
