package com.its.issuetrackingservice.api.mapper;

import com.its.issuetrackingservice.api.config.MapStructConfig;
import com.its.issuetrackingservice.api.dto.issue.request.IssueCreateRequest;
import com.its.issuetrackingservice.api.dto.issue.request.IssuePatchRequest;
import com.its.issuetrackingservice.api.dto.issue.response.IssueDetailResponse;
import com.its.issuetrackingservice.api.dto.issue.response.IssueSummaryResponse;
import com.its.issuetrackingservice.persistence.entity.Issue;
import org.mapstruct.*;

import java.util.List;

@Mapper(config = MapStructConfig.class, uses = {SprintMapper.class, UserMapper.class, ProjectMapper.class, StateMapper.class})
public abstract class IssueMapper {

    @Mapping(source = "project", target = "projectId")
    @Mapping(source = "sprint", target = "sprintId")
    @Mapping(source = "state", target = "stateId")
    @Mapping(source = "verifierUser", target = "verifierUserId")
    @Mapping(source = "creatorUser", target = "creatorUserId")
    public abstract IssueSummaryResponse toSummaryResponse(Issue issue);

    @Mapping(source = "project", target = "projectId")
    @Mapping(source = "sprint", target = "sprintId")
    @Mapping(source = "state", target = "stateId")
    @Mapping(source = "verifierUser", target = "verifierUserId")
    @Mapping(source = "creatorUser", target = "creatorUserId")
    public abstract IssueDetailResponse toDetailResponse(Issue issue);

    @Mapping(source = "projectId", target = "project")
    @Mapping(source = "sprintId", target = "sprint")
    @Mapping(source = "verifierUserId", target = "verifierUser")
    public abstract Issue toEntity(IssueCreateRequest issueCreateRequest);

    public abstract List<IssueSummaryResponse> toSummaryListResponse(List<Issue> issues);

    @Mapping(source = "sprintId", target = "sprint")
    @Mapping(source = "verifierUserId", target = "verifierUser")
    @Mapping(source = "stateId", target = "state")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public abstract Issue patchEntityViaPatchEntityRequest(IssuePatchRequest entityPatchRequest, @MappingTarget Issue issue);
}
