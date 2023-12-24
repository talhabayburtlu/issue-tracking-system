package com.its.issuetrackingservice.api.mapper;

import com.its.issuetrackingservice.api.config.MapStructConfig;
import com.its.issuetrackingservice.domain.service.SprintDomainService;
import com.its.issuetrackingservice.persistence.entity.Sprint;
import lombok.Setter;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(config = MapStructConfig.class)
public abstract class SprintMapper {

    @Setter(onMethod_ = @Autowired)
    protected SprintDomainService sprintDomainService;

    public Sprint entityIdToEntity(Long entityId) {
        return entityId == null ? null : sprintDomainService.getSprintById(entityId);
    }

    public Long entityToEntityId(Sprint entity) {
        return entity == null ? null : entity.getId();
    }

}
