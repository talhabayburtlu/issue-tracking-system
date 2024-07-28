package com.its.issuetrackingservice.domain.service.auth;

import com.its.issuetrackingservice.domain.service.ProjectService;
import com.its.issuetrackingservice.domain.service.UserService;
import com.its.issuetrackingservice.infrastructure.dto.UserContext;
import com.its.issuetrackingservice.infrastructure.persistence.entity.Project;
import com.its.issuetrackingservice.infrastructure.persistence.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class GlobalAuthenticationService {

    private final UserService userService;
    private final ProjectService projectService;

    public UserContext generateUserContext(String keycloakId) {
        User user = userService.getUserByKeycloakId(keycloakId, true);
        Set<Project> projects = projectService.getProjectsOfUser(user.getId());

        return UserContext.builder()
                .user(user)
                .projects(projects)
                .build();
    }
}
