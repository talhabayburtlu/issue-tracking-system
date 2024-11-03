package com.its.issuetrackingservice.infrastructure.persistence.mapper;

import com.its.issuetrackingservice.domain.service.ProjectService;
import com.its.issuetrackingservice.infrastructure.configuration.mapper.MapStructConfig;
import com.its.issuetrackingservice.infrastructure.dto.request.ProjectRequest;
import com.its.issuetrackingservice.infrastructure.dto.response.ProjectDetailResponse;
import com.its.issuetrackingservice.infrastructure.dto.response.ProjectSummaryResponse;
import com.its.issuetrackingservice.infrastructure.persistence.entity.Project;
import lombok.Setter;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

@Mapper(config = MapStructConfig.class)
public abstract class ProjectMapper {

    @Setter(onMethod_ = {@Autowired, @Lazy})
    protected ProjectService projectService;

    public abstract ProjectSummaryResponse toSummaryResponse(Project project);

    public abstract ProjectDetailResponse toDetailResponse(Project project);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public abstract void patchEntity(ProjectRequest projectRequest, @MappingTarget Project project);

    public Project entityIdToEntity(Long entityId) {
        return entityId == null ? null : projectService.getProjectById(entityId);
    }

    public Long entityToEntityId(Project entity) {
        return entity == null ? null : entity.getId();
    }

}
