package com.its.issuetrackingservice.infrastructure.persistence.mapper;

import com.its.issuetrackingservice.infrastructure.configuration.mapper.MapStructConfig;
import com.its.issuetrackingservice.infrastructure.dto.request.IssueRequest;
import com.its.issuetrackingservice.infrastructure.dto.response.IssueDetailResponse;
import com.its.issuetrackingservice.infrastructure.dto.response.IssueSummaryResponse;
import com.its.issuetrackingservice.infrastructure.persistence.entity.Issue;
import org.mapstruct.*;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(config = MapStructConfig.class, uses = {SprintMapper.class, UserMapper.class, ProjectMapper.class, StateMapper.class, CategoryMapper.class, ParticipatorsMapper.class})
public abstract class IssueMapper {

    @Mapping(source = "project", target = "projectId")
    @Mapping(source = "sprint", target = "sprintId")
    @Mapping(source = "state", target = "stateId")
    public abstract IssueSummaryResponse toSummaryResponse(Issue issue);

    public abstract List<IssueSummaryResponse> toSummaryListResponse(List<Issue> issues);

    public Page<IssueSummaryResponse> toSummaryPageResponse(Page<Issue> issues) {
       return issues.map(this::toSummaryResponse);
    }

    @Mapping(source = "sprint", target = "sprintId")
    @Mapping(source = "state", target = "stateId")
    @Mapping(source = "category", target = "categoryId")
    public abstract IssueDetailResponse toDetailResponse(Issue issue);

    @Mapping(source = "sprintId", target = "sprint")
    @Mapping(source = "categoryId", target = "category")
    @Mapping(target = "participants" , ignore = true)
    public abstract Issue toEntity(IssueRequest issueRequest);

    @Mapping(source = "sprintId", target = "sprint")
    @Mapping(source = "stateId", target = "state")
    @Mapping(source = "categoryId", target = "category")
    @Mapping(target = "participants" , ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public abstract void patchEntity(IssueRequest issueRequest, @MappingTarget Issue issue);

    public abstract Issue cloneEntity(Issue issue);
}
