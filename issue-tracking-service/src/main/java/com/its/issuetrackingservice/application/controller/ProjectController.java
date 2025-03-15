package com.its.issuetrackingservice.application.controller;

import com.its.issuetrackingservice.domain.commands.Invoker;
import com.its.issuetrackingservice.domain.commands.project.GetProjectDetailCommand;
import com.its.issuetrackingservice.domain.commands.project.GetProjectSummaryCommand;
import com.its.issuetrackingservice.domain.commands.project.UpdateProjectCommand;
import com.its.issuetrackingservice.infrastructure.dto.GenericRestResponse;
import com.its.issuetrackingservice.infrastructure.dto.request.ProjectRequest;
import com.its.issuetrackingservice.infrastructure.dto.response.ProjectDetailResponse;
import com.its.issuetrackingservice.infrastructure.dto.response.ProjectSummaryResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
    @Operation(summary = "Get project in detail")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Returns project in detail", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ProjectDetailResponse.class)) }),
            @ApiResponse(responseCode = "403", description = "No access to project", content = @Content),
            @ApiResponse(responseCode = "404", description = "Project does not exist", content = @Content)
    })
    public GenericRestResponse<ProjectDetailResponse> getProjectDetail(@Parameter(name = "project_id" , description = "The id of the project", required = true) @PathVariable("project_id") Long projectId) {
        GetProjectDetailCommand command = GetProjectDetailCommand.builder()
                .projectId(projectId)
                .build();
        return GenericRestResponse.of(invoker.run(command));
    }

    @GetMapping("/{project_id}/summary")
    @Operation(summary = "Get summary of project")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Returns summary of project", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ProjectSummaryResponse.class)) }),
            @ApiResponse(responseCode = "403", description = "No access to project", content = @Content),
            @ApiResponse(responseCode = "404", description = "Project does not exist", content = @Content)
    })
    public GenericRestResponse<ProjectSummaryResponse> getProjectSummary(@Parameter(name = "project_id" , description = "The id of the project", required = true)  @PathVariable("project_id") Long projectId) {
        GetProjectSummaryCommand command = GetProjectSummaryCommand.builder()
                .projectId(projectId)
                .build();
        return GenericRestResponse.of(invoker.run(command));
    }

    @PostMapping("/{project_id}")
    @Operation(summary = "Update project")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Updates project by given content", content = { @Content(mediaType = "application/json", schema = @Schema()) }),
            @ApiResponse(responseCode = "403", description = "No access to project", content = @Content),
            @ApiResponse(responseCode = "404", description = "Project does not exist", content = @Content)
    })
    public GenericRestResponse<Void> updateProject(@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Project request that updates project", required = true)  @RequestBody ProjectRequest projectRequest,
                                                   @Parameter(name = "project_id" , description = "The id of the project", required = true) @PathVariable("project_id") Long projectId) {
        UpdateProjectCommand command = UpdateProjectCommand.builder()
                .projectId(projectId)
                .projectRequest(projectRequest)
                .build();
        return GenericRestResponse.of(invoker.run(command));
    }

}
