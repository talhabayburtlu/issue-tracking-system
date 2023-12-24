package com.its.issuetrackingservice.domain.dto;

import com.its.issuetrackingservice.domain.constants.I18nExceptionKeys;
import com.its.issuetrackingservice.domain.exception.AccessException;
import com.its.issuetrackingservice.persistence.entity.Project;
import com.its.issuetrackingservice.persistence.entity.User;
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

    public void applyAccessToProject(Project project) {
        if (!haveAccessToProject(project)) {
            throw new AccessException(I18nExceptionKeys.USER_DOES_NOT_ACCESS_TO_PROJECT, String.format("project id=%d", project.getId()));
        }
    }

    public void applyAccessToProject(Long projectId) {
        if (!haveAccessToProject(projectId)) {
            throw new AccessException(I18nExceptionKeys.USER_DOES_NOT_ACCESS_TO_PROJECT, String.format("project id=%d", projectId));
        }
    }

    private boolean haveAccessToProject(Project project) {
        return projects.stream().anyMatch(userProject -> Objects.equals(userProject.getId(), project.getId()));
    }

    private boolean haveAccessToProject(Long projectId) {
        return projects.stream().anyMatch(userProject -> Objects.equals(userProject.getId(), projectId));
    }
}
