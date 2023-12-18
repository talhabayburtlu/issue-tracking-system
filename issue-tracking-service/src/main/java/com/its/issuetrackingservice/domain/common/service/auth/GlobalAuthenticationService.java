package com.its.issuetrackingservice.domain.common.service.auth;

import com.its.issuetrackingservice.domain.common.dto.UserContext;
import com.its.issuetrackingservice.domain.user.service.ProjectDomainService;
import com.its.issuetrackingservice.domain.user.service.UserDomainService;
import com.its.issuetrackingservice.persistence.user.entity.Project;
import com.its.issuetrackingservice.persistence.user.entity.User;
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
