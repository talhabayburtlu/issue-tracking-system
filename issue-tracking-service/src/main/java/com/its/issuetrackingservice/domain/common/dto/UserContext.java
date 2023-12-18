package com.its.issuetrackingservice.domain.common.dto;

import com.its.issuetrackingservice.persistence.user.entity.Project;
import com.its.issuetrackingservice.persistence.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserContext {
    private User user;
    private Set<Project> projects;
}
