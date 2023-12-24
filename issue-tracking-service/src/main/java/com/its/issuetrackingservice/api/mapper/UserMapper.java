package com.its.issuetrackingservice.api.mapper;

import com.its.issuetrackingservice.api.config.MapStructConfig;
import com.its.issuetrackingservice.domain.service.UserDomainService;
import com.its.issuetrackingservice.persistence.entity.User;
import lombok.Setter;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(config = MapStructConfig.class)
public abstract class UserMapper {

    @Setter(onMethod_ = @Autowired)
    protected UserDomainService userDomainService;

    public User entityIdToEntity(Long entityId) {
        return entityId == null ? null : userDomainService.getUserById(entityId);
    }

    public Long entityToEntityId(User entity) {
        return entity == null ? null : entity.getId();
    }

}
