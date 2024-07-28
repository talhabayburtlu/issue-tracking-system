package com.its.issuetrackingservice.infrastructure.persistence.mapper;

import com.its.issuetrackingservice.domain.service.ProjectService;
import com.its.issuetrackingservice.infrastructure.configuration.mapper.MapStructConfig;
import com.its.issuetrackingservice.infrastructure.persistence.entity.Project;
import lombok.Setter;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(config = MapStructConfig.class)
public abstract class ProjectMapper {

    @Setter(onMethod_ = @Autowired)
    protected ProjectService projectService;

    public Project entityIdToEntity(Long entityId) {
        return entityId == null ? null : projectService.getProjectById(entityId);
    }

    public Long entityToEntityId(Project entity) {
        return entity == null ? null : entity.getId();
    }

}
