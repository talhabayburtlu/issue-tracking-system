package com.its.issuetrackingservice.api.mapper;

import com.its.issuetrackingservice.api.config.MapStructConfig;
import com.its.issuetrackingservice.domain.service.StateDomainService;
import com.its.issuetrackingservice.persistence.entity.State;
import lombok.Setter;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(config = MapStructConfig.class)
public abstract class StateMapper {

    @Setter(onMethod_ = @Autowired)
    protected StateDomainService stateDomainService;

    public State entityIdToEntity(Long entityId) {
        return entityId == null ? null : stateDomainService.getStateById(entityId);
    }

    public Long entityToEntityId(State entity) {
        return entity == null ? null : entity.getId();
    }

}
