package com.its.issuetrackingservice.infrastructure.persistence.mapper;

import com.its.issuetrackingservice.domain.service.CategoryService;
import com.its.issuetrackingservice.infrastructure.configuration.mapper.MapStructConfig;
import com.its.issuetrackingservice.infrastructure.persistence.entity.Category;
import lombok.Setter;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(config = MapStructConfig.class)
public abstract class CategoryMapper {

    @Setter(onMethod_ = @Autowired)
    protected CategoryService categoryService;

    public Category entityIdToEntity(Long entityId) {
        return entityId == null ? null : categoryService.getCategoryById(entityId);
    }

    public Long entityToEntityId(Category entity) {
        return entity == null ? null : entity.getId();
    }

}
