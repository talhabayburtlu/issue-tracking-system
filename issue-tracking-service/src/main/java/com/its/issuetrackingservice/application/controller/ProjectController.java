package com.its.issuetrackingservice.application.controller;

import com.its.issuetrackingservice.domain.commands.Invoker;
import com.its.issuetrackingservice.domain.commands.project.GetProjectDetailCommand;
import com.its.issuetrackingservice.domain.commands.project.GetProjectSummaryCommand;
import com.its.issuetrackingservice.domain.commands.project.UpdateProjectCommand;
import com.its.issuetrackingservice.infrastructure.dto.GenericRestResponse;
import com.its.issuetrackingservice.infrastructure.dto.request.ProjectRequest;
import com.its.issuetrackingservice.infrastructure.dto.response.ProjectDetailResponse;
import com.its.issuetrackingservice.infrastructure.dto.response.ProjectSummaryResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/project")
@AllArgsConstructor
@Slf4j
public class ProjectController {
    private final Invoker invoker;

    @GetMapping("/{project_id}")
    public GenericRestResponse<ProjectDetailResponse> getProjectDetail(@PathVariable("project_id") Long projectId) {
        GetProjectDetailCommand command = GetProjectDetailCommand.builder()
                .projectId(projectId)
                .build();
        return GenericRestResponse.of(invoker.run(command));
    }

    @GetMapping("/{project_id}/summary")
    public GenericRestResponse<ProjectSummaryResponse> getProjectSummary(@PathVariable("project_id") Long projectId) {
        GetProjectSummaryCommand command = GetProjectSummaryCommand.builder()
                .projectId(projectId)
                .build();
        return GenericRestResponse.of(invoker.run(command));
    }

    @PostMapping("/{project_id}")
    public GenericRestResponse<Void> updateProject(@RequestBody ProjectRequest projectRequest, @PathVariable("project_id") Long projectId) {
        UpdateProjectCommand command = UpdateProjectCommand.builder()
                .projectId(projectId)
                .projectRequest(projectRequest)
                .build();
        return GenericRestResponse.of(invoker.run(command));
    }

}
