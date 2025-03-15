package com.its.issuetrackingservice.domain.service.auth;

import com.its.issuetrackingservice.domain.service.MembershipService;
import com.its.issuetrackingservice.domain.service.UserService;
import com.its.issuetrackingservice.infrastructure.dto.UserContext;
import com.its.issuetrackingservice.infrastructure.persistence.entity.Project;
import com.its.issuetrackingservice.infrastructure.persistence.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class GlobalAuthenticationService {

    private final UserService userService;
    private final MembershipService membershipService;

    public UserContext generateUserContext(String keycloakId) {
        Set<Project> projects = new HashSet<>();
        User user = userService.getUserByKeycloakId(keycloakId, false);
        if (user != null) {
            projects = membershipService.getProjectsOfMember(user.getId());
        }

        UserContext userContext = UserContext.builder()
                .user(user)
                .projects(projects)
                .build();
        userContext.init();
        return userContext;
    }
}
