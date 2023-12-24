package com.its.issuetrackingservice.domain.service;

import com.its.issuetrackingservice.domain.constants.I18nExceptionKeys;
import com.its.issuetrackingservice.domain.exception.DataNotFoundException;
import com.its.issuetrackingservice.persistence.entity.Project;
import com.its.issuetrackingservice.persistence.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ProjectDomainService {
    private final ProjectRepository projectRepository;

    public Project getProjectById(Long projectId) {
        Optional<Project> issue = projectRepository.findById(projectId);
        if (issue.isEmpty()) {
            throw new DataNotFoundException(I18nExceptionKeys.PROJECT_NOT_FOUND, String.format("project id=%d", projectId));
        }

        return issue.get();
    }

    public Set<Project> getProjectsOfUser(Long userId) {
        return projectRepository.getProjectsOfUser(userId);
    }

}
