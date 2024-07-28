package com.its.issuetrackingservice.infrastructure.persistence.mapper;

import com.its.issuetrackingservice.domain.service.UserService;
import com.its.issuetrackingservice.infrastructure.configuration.mapper.MapStructConfig;
import com.its.issuetrackingservice.infrastructure.persistence.entity.User;
import lombok.Setter;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(config = MapStructConfig.class)
public abstract class UserMapper {

    @Setter(onMethod_ = @Autowired)
    protected UserService userService;

    public User entityIdToEntity(Long entityId) {
        return entityId == null ? null : userService.getUserById(entityId);
    }

    public Long entityToEntityId(User entity) {
        return entity == null ? null : entity.getId();
    }

}
