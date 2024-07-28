package com.its.issuetrackingservice.domain.service;

import com.its.issuetrackingservice.domain.constants.I18nExceptionKeys;
import com.its.issuetrackingservice.domain.exception.DataNotFoundException;
import com.its.issuetrackingservice.infrastructure.persistence.entity.Sprint;
import com.its.issuetrackingservice.infrastructure.persistence.repository.SprintRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SprintService {
    private final SprintRepository sprintRepository;

    public Sprint getSprintById(Long sprintId) {
        Optional<Sprint> sprint = sprintRepository.findById(sprintId);
        if (sprint.isEmpty()) {
            throw new DataNotFoundException(I18nExceptionKeys.SPRINT_NOT_FOUND, String.format("sprint id=%d", sprintId));
        }

        return sprint.get();
    }

    public void checkSprintExists(Long sprintId) {
        Optional<Sprint> sprint = sprintRepository.findById(sprintId);
        if (sprint.isEmpty()) {
            throw new DataNotFoundException(I18nExceptionKeys.SPRINT_NOT_FOUND, String.format("sprint id=%d", sprintId));
        }
    }

}
