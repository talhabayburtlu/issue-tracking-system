package com.its.issuetrackingservice.domain.service;

import com.its.issuetrackingservice.domain.constants.I18nExceptionKeys;
import com.its.issuetrackingservice.domain.exception.DataNotFoundException;
import com.its.issuetrackingservice.domain.exception.WrongUsageException;
import com.its.issuetrackingservice.infrastructure.dto.UserContext;
import com.its.issuetrackingservice.infrastructure.dto.request.CategoryRequest;
import com.its.issuetrackingservice.infrastructure.persistence.entity.Category;
import com.its.issuetrackingservice.infrastructure.persistence.entity.Project;
import com.its.issuetrackingservice.infrastructure.persistence.mapper.CategoryMapper;
import com.its.issuetrackingservice.infrastructure.persistence.repository.CategoryRepository;
import com.its.issuetrackingservice.infrastructure.persistence.repository.IssueRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final IssueRepository issueRepository;
    private final UserContext userContext;
    private final CategoryMapper categoryMapper;

    public Category getCategoryById(Long categoryId) {
        Optional<Category> project = categoryRepository.findById(categoryId);
        if (project.isEmpty()) {
            throw new DataNotFoundException(I18nExceptionKeys.CATEGORY_NOT_FOUND, String.format("category id=%d", categoryId));
        }

        return project.get();
    }

    public Map<Long, Category> getBulkCategories(List<Long> ids) {
        return categoryRepository.findAllById(ids).stream().collect(Collectors.toMap(Category::getId, category -> category));
    }

    public void deleteCategoriesByIdList(List<Long> categoryIdList) {
        if (issueRepository.existsByCategoryIdIn(categoryIdList)) {
            throw new WrongUsageException(I18nExceptionKeys.CATEGORIES_MUST_NOT_CONTAIN_ANY_ISSUE_TO_BE_DELETED, String.format("category id list=%s" , categoryIdList.toString()));
        }

        categoryRepository.deleteAllById(categoryIdList);
    }

    public void bulkUpsertState(List<Category> categories) {
        categories.forEach(category -> category.setAuditableFields(userContext));
        categoryRepository.saveAllAndFlush(categories);
    }

    public void updateCategories(List<CategoryRequest> categoryRequests, Project project) {
        if (CollectionUtils.isEmpty(categoryRequests)) {
            return;
        }

        List<Long> idsToRemove = new ArrayList<>();
        List<Long> idsToFetch = new ArrayList<>();
        categoryRequests.forEach(categoryRequest ->  {
            if (categoryRequest.isDelete()) {
                idsToRemove.add(categoryRequest.getId());
            } else if (Objects.nonNull(categoryRequest.getId())) {
                idsToFetch.add(categoryRequest.getId());
            }
        });

        List<Category> categories = new ArrayList<>();
        Map<Long, Category> categoriesByIdMap = getBulkCategories(idsToFetch);
        categoryRequests.forEach(categoryRequest -> {
            Long categoryId = categoryRequest.getId();

            // Update existing entity
            if (Objects.nonNull(categoryId)) {
                if (categoriesByIdMap.containsKey(categoryId)) {
                    Category category = categoriesByIdMap.get(categoryId);
                    categoryMapper.patchEntity(categoryRequest, category);
                    categories.add(category);
                }
                return;
            }

            // Create new entity
            categories.add(categoryMapper.toEntity(categoryRequest, project));
        });

        bulkUpsertState(categories);
        deleteCategoriesByIdList(idsToRemove);
    }

}
