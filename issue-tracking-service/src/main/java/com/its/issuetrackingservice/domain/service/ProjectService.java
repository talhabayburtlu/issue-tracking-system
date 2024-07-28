package com.its.issuetrackingservice.domain.service;

import com.its.issuetrackingservice.domain.constants.I18nExceptionKeys;
import com.its.issuetrackingservice.domain.exception.DataNotFoundException;
import com.its.issuetrackingservice.infrastructure.dto.UserContext;
import com.its.issuetrackingservice.infrastructure.persistence.entity.Project;
import com.its.issuetrackingservice.infrastructure.persistence.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ProjectService {
    private final ProjectRepository projectRepository;
    private final UserContext userContext;

    public Project getProjectById(Long projectId) {
        Optional<Project> project = projectRepository.findById(projectId);
        if (project.isEmpty()) {
            throw new DataNotFoundException(I18nExceptionKeys.PROJECT_NOT_FOUND, String.format("project id=%d", projectId));
        }

        return project.get();
    }

    public void checkProjectExists(Long projectId) {
        Optional<Project> project = projectRepository.findById(projectId);
        if (project.isEmpty()) {
            throw new DataNotFoundException(I18nExceptionKeys.PROJECT_NOT_FOUND, String.format("project id=%d", projectId));
        }
    }

    public Project getProjectByName(String name, boolean isRequired) {
        Project project = projectRepository.getProjectByName(name);
        if (Objects.isNull(project) && isRequired) {
            throw new DataNotFoundException(I18nExceptionKeys.PROJECT_NOT_FOUND, String.format("project name=%d", name));
        }

        return project;
    }

    public Project getProjectByKeycloakId(String keycloakId, boolean isRequired) {
        Project project = projectRepository.getProjectByKeycloakId(keycloakId);
        if (Objects.isNull(project) && isRequired) {
            throw new DataNotFoundException(I18nExceptionKeys.PROJECT_NOT_FOUND, String.format("project keycloak id=%d", keycloakId));
        }

        return project;
    }

    public Set<Project> getProjectsOfUser(Long userId) {
        return projectRepository.getProjectsOfUser(userId);
    }

    public Project upsertProject(Project project, boolean isSystemUser) {
        project.setAuditableFields(!isSystemUser ? userContext : null);
        return projectRepository.save(project);
    }

    public void changeActiveStateOfUser(String keycloakId, boolean isActive, boolean isSystemUser) {
        Project project = getProjectByKeycloakId(keycloakId, false);
        if (Objects.nonNull(project)) {
            project.setIsActive(isActive);
            upsertProject(project, isSystemUser);
        }
    }

    public Set<Project> getProjectsByNames(List<String> names) {
        return projectRepository.getProjectsByNameIn(names);
    }

}
