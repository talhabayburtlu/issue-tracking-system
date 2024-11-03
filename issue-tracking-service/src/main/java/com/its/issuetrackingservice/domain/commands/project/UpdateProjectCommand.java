package com.its.issuetrackingservice.domain.commands.project;

import com.its.issuetrackingservice.domain.commands.Command;
import com.its.issuetrackingservice.domain.service.ProjectService;
import com.its.issuetrackingservice.domain.util.SpringContext;
import com.its.issuetrackingservice.infrastructure.dto.UserContext;
import com.its.issuetrackingservice.infrastructure.dto.request.ProjectRequest;
import com.its.issuetrackingservice.infrastructure.persistence.entity.Project;
import com.its.issuetrackingservice.infrastructure.persistence.mapper.ProjectMapper;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.util.Optional;


@Getter
@SuperBuilder
@AllArgsConstructor
public class UpdateProjectCommand extends Command<Void> {
    // Inputs
    private Long projectId;
    private ProjectRequest projectRequest;

    // Generates
    private Project project;

    // Services
    private ProjectService projectService;
    private ProjectMapper projectMapper;
    private UserContext userContext;

    @Override
    public void init() {
        this.projectService = SpringContext.getBean(ProjectService.class);
        this.projectMapper = SpringContext.getBean(ProjectMapper.class);
        this.userContext = SpringContext.getBean(UserContext.class);
    }

    @Override
    @Transactional
    public Void execute() {
        userContext.applyAccessToProject(projectId);

        project = projectService.updateProject(projectId, projectRequest);

        return null;
    }

    @Override
    public Optional<Void> getResult() {
        return Optional.empty();
    }

}
