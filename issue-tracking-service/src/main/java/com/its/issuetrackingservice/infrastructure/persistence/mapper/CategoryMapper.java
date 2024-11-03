package com.its.issuetrackingservice.infrastructure.persistence.mapper;

import com.its.issuetrackingservice.domain.service.CategoryService;
import com.its.issuetrackingservice.infrastructure.configuration.mapper.MapStructConfig;
import com.its.issuetrackingservice.infrastructure.dto.request.CategoryRequest;
import com.its.issuetrackingservice.infrastructure.persistence.entity.Category;
import com.its.issuetrackingservice.infrastructure.persistence.entity.Project;
import lombok.Setter;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

@Mapper(config = MapStructConfig.class)
public abstract class CategoryMapper {

    @Setter(onMethod_ = {@Autowired, @Lazy})
    protected CategoryService categoryService;

    @Mapping(target = "id" , ignore = true)
    @Mapping(source = "categoryRequest.name", target = "name")
    @Mapping(source = "categoryRequest.description", target = "description")
    @Mapping(source = "project", target = "project")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public abstract Category toEntity(CategoryRequest categoryRequest, Project project);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public abstract void patchEntity(CategoryRequest categoryRequest, @MappingTarget Category category);

    public Category entityIdToEntity(Long entityId) {
        return entityId == null ? null : categoryService.getCategoryById(entityId);
    }

    public Long entityToEntityId(Category entity) {
        return entity == null ? null : entity.getId();
    }

}
