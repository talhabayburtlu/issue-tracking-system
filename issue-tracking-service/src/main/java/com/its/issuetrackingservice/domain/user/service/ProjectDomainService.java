package com.its.issuetrackingservice.domain.user.service;

import com.its.issuetrackingservice.persistence.user.entity.Project;
import com.its.issuetrackingservice.persistence.user.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class ProjectDomainService {
    private final ProjectRepository projectRepository;

    public Set<Project> getProjectsOfUser(Long userId) {
        return projectRepository.getProjectsOfUser(userId);
    }

}
