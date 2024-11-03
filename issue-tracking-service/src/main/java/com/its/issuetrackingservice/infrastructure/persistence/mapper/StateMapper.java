package com.its.issuetrackingservice.infrastructure.persistence.mapper;

import com.its.issuetrackingservice.domain.service.StateService;
import com.its.issuetrackingservice.infrastructure.configuration.mapper.MapStructConfig;
import com.its.issuetrackingservice.infrastructure.dto.request.StateRequest;
import com.its.issuetrackingservice.infrastructure.persistence.entity.Project;
import com.its.issuetrackingservice.infrastructure.persistence.entity.State;
import lombok.Setter;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

@Mapper(config = MapStructConfig.class)
public abstract class StateMapper {

    @Setter(onMethod_ = {@Autowired, @Lazy})
    protected StateService stateService;

    @Mapping(target = "id" , ignore = true)
    @Mapping(source = "stateRequest.title", target = "title")
    @Mapping(source = "stateRequest.description", target = "description")
    @Mapping(source = "stateRequest.initial", target = "isInitial")
    @Mapping(source = "stateRequest.nextStateId", target = "nextState")
    @Mapping(source = "project", target = "project")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public abstract State toEntity(StateRequest stateRequest, Project project);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public abstract void patchEntity(StateRequest stateRequest, @MappingTarget State state);

    public State entityIdToEntity(Long entityId) {
        return entityId == null ? null : stateService.getStateById(entityId);
    }

    public Long entityToEntityId(State entity) {
        return entity == null ? null : entity.getId();
    }

}
