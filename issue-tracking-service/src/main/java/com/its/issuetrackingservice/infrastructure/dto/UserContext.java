package com.its.issuetrackingservice.infrastructure.dto;

import com.its.issuetrackingservice.domain.constants.I18nExceptionKeys;
import com.its.issuetrackingservice.domain.exception.AccessException;
import com.its.issuetrackingservice.domain.util.SpringContext;
import com.its.issuetrackingservice.infrastructure.persistence.entity.Project;
import com.its.issuetrackingservice.infrastructure.persistence.entity.User;
import com.its.issuetrackingservice.infrastructure.persistence.repository.IssueRepository;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserContext {
    private User user;
    private Set<Project> projects;
    private IssueRepository issueRepository;

    public void init() {
        if (Objects.isNull(issueRepository)) {
            issueRepository = SpringContext.getBean(IssueRepository.class);
        }
    }

    public void applyAccessToProjectByIssueId(Long issueId) {
        Long projectId = issueRepository.getProjectIdByIssueId(issueId);
        applyAccessToProject(projectId);
    }

    public void applyAccessToProject(Long projectId) {
        if (!haveAccessToProject(projectId)) {
            throw new AccessException(I18nExceptionKeys.USER_DOES_NOT_ACCESS_TO_PROJECT, String.format("project id=%d", projectId));
        }
    }

    private boolean haveAccessToProject(Long projectId) {
        return projects.stream().anyMatch(userProject -> Objects.equals(userProject.getId(), projectId));
    }
}
