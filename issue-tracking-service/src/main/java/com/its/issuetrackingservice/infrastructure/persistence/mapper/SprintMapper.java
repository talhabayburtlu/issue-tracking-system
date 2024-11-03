package com.its.issuetrackingservice.infrastructure.persistence.mapper;

import com.its.issuetrackingservice.domain.service.SprintService;
import com.its.issuetrackingservice.infrastructure.configuration.mapper.MapStructConfig;
import com.its.issuetrackingservice.infrastructure.dto.request.SprintRequest;
import com.its.issuetrackingservice.infrastructure.persistence.entity.Project;
import com.its.issuetrackingservice.infrastructure.persistence.entity.Sprint;
import lombok.Setter;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

@Mapper(config = MapStructConfig.class)
public abstract class SprintMapper {

    @Setter(onMethod_ = {@Autowired, @Lazy})
    protected SprintService sprintService;

    @Mapping(target = "id" , ignore = true)
    @Mapping(source = "sprintRequest.name", target = "name")
    @Mapping(source = "sprintRequest.period", target = "period")
    @Mapping(source = "sprintRequest.version", target = "version")
    @Mapping(source = "project", target = "project")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public abstract Sprint toEntity(SprintRequest sprintRequest, Project project);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public abstract void patchEntity(SprintRequest sprintRequest, @MappingTarget Sprint sprint);

    public Sprint entityIdToEntity(Long entityId) {
        return entityId == null ? null : sprintService.getSprintById(entityId);
    }

    public Long entityToEntityId(Sprint entity) {
        return entity == null ? null : entity.getId();
    }

}
