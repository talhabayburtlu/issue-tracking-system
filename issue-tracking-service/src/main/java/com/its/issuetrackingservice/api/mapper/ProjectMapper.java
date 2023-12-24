package com.its.issuetrackingservice.api.mapper;

import com.its.issuetrackingservice.api.config.MapStructConfig;
import com.its.issuetrackingservice.domain.service.ProjectDomainService;
import com.its.issuetrackingservice.persistence.entity.Project;
import lombok.Setter;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(config = MapStructConfig.class)
public abstract class ProjectMapper {

    @Setter(onMethod_ = @Autowired)
    protected ProjectDomainService projectDomainService;

    public Project entityIdToEntity(Long entityId) {
        return entityId == null ? null : projectDomainService.getProjectById(entityId);
    }

    public Long entityToEntityId(Project entity) {
        return entity == null ? null : entity.getId();
    }

}
