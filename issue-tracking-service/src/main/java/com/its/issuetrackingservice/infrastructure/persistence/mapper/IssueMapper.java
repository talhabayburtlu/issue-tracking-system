package com.its.issuetrackingservice.infrastructure.persistence.mapper;

import com.its.issuetrackingservice.infrastructure.configuration.mapper.MapStructConfig;
import com.its.issuetrackingservice.infrastructure.dto.request.IssueRequest;
import com.its.issuetrackingservice.infrastructure.dto.response.IssueDetailResponse;
import com.its.issuetrackingservice.infrastructure.dto.response.IssueSummaryResponse;
import com.its.issuetrackingservice.infrastructure.persistence.entity.Issue;
import org.mapstruct.*;

import java.util.List;

@Mapper(config = MapStructConfig.class, uses = {SprintMapper.class, UserMapper.class, ProjectMapper.class, StateMapper.class, ParticipatorsMapper.class})
public abstract class IssueMapper {

    @Mapping(source = "project", target = "projectId")
    @Mapping(source = "sprint", target = "sprintId")
    @Mapping(source = "state", target = "stateId")
    public abstract IssueSummaryResponse toSummaryResponse(Issue issue);

    public abstract List<IssueSummaryResponse> toSummaryListResponse(List<Issue> issues);

    @Mapping(source = "project", target = "projectId")
    @Mapping(source = "sprint", target = "sprintId")
    @Mapping(source = "state", target = "stateId")
    @Mapping(source = "category", target = "categoryId")
    @Mapping(source = "subsystems", target = "subsystemIds")
    public abstract IssueDetailResponse toDetailResponse(Issue issue);

    @Mapping(source = "projectId", target = "project")
    @Mapping(source = "sprintId", target = "sprint")
    @Mapping(source = "categoryId", target = "category")
    public abstract Issue toEntity(IssueRequest issueRequest);

    @Mapping(source = "sprintId", target = "sprint")
    @Mapping(source = "stateId", target = "state")
    @Mapping(source = "categoryId", target = "category")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public abstract Issue patchEntity(IssueRequest issueRequest, @MappingTarget Issue issue);
}
