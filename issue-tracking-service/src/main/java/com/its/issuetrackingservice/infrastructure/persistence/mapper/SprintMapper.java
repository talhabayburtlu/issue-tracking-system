package com.its.issuetrackingservice.infrastructure.persistence.mapper;

import com.its.issuetrackingservice.domain.service.SprintService;
import com.its.issuetrackingservice.infrastructure.configuration.mapper.MapStructConfig;
import com.its.issuetrackingservice.infrastructure.persistence.entity.Sprint;
import lombok.Setter;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(config = MapStructConfig.class)
public abstract class SprintMapper {

    @Setter(onMethod_ = @Autowired)
    protected SprintService sprintService;

    public Sprint entityIdToEntity(Long entityId) {
        return entityId == null ? null : sprintService.getSprintById(entityId);
    }

    public Long entityToEntityId(Sprint entity) {
        return entity == null ? null : entity.getId();
    }

}
