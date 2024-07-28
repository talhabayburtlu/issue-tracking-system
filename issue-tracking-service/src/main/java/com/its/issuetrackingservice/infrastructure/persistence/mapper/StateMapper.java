package com.its.issuetrackingservice.infrastructure.persistence.mapper;

import com.its.issuetrackingservice.domain.service.StateService;
import com.its.issuetrackingservice.infrastructure.configuration.mapper.MapStructConfig;
import com.its.issuetrackingservice.infrastructure.persistence.entity.State;
import lombok.Setter;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(config = MapStructConfig.class)
public abstract class StateMapper {

    @Setter(onMethod_ = @Autowired)
    protected StateService stateService;

    public State entityIdToEntity(Long entityId) {
        return entityId == null ? null : stateService.getStateById(entityId);
    }

    public Long entityToEntityId(State entity) {
        return entity == null ? null : entity.getId();
    }

}
