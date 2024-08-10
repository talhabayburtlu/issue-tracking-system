package com.its.issuetrackingservice.domain.service;

import com.its.issuetrackingservice.domain.constants.I18nExceptionKeys;
import com.its.issuetrackingservice.domain.exception.DataNotFoundException;
import com.its.issuetrackingservice.infrastructure.persistence.entity.Category;
import com.its.issuetrackingservice.infrastructure.persistence.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public Category getCategoryById(Long categoryId) {
        Optional<Category> project = categoryRepository.findById(categoryId);
        if (project.isEmpty()) {
            throw new DataNotFoundException(I18nExceptionKeys.CATEGORY_NOT_FOUND, String.format("category id=%d", categoryId));
        }

        return project.get();
    }

}
