package com.its.issuetrackingservice.domain.service.auth;

import com.its.issuetrackingservice.api.model.UserContext;
import com.its.issuetrackingservice.domain.service.ProjectDomainService;
import com.its.issuetrackingservice.domain.service.UserDomainService;
import com.its.issuetrackingservice.persistence.entity.Project;
import com.its.issuetrackingservice.persistence.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class GlobalAuthenticationService {

    private final UserDomainService userDomainService;
    private final ProjectDomainService projectDomainService;

    public UserContext generateUserContext(String keycloakId) {
        User user = userDomainService.getUserByKeycloakId(keycloakId);
        Set<Project> projects = projectDomainService.getProjectsOfUser(user.getId());

        return UserContext.builder()
                .user(user)
                .projects(projects)
                .build();
    }
}
