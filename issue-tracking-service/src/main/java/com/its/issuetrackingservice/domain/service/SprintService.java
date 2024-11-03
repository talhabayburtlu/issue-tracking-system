package com.its.issuetrackingservice.domain.service;

import com.its.issuetrackingservice.domain.constants.I18nExceptionKeys;
import com.its.issuetrackingservice.domain.exception.DataNotFoundException;
import com.its.issuetrackingservice.domain.exception.WrongUsageException;
import com.its.issuetrackingservice.infrastructure.dto.UserContext;
import com.its.issuetrackingservice.infrastructure.dto.request.SprintRequest;
import com.its.issuetrackingservice.infrastructure.persistence.entity.Project;
import com.its.issuetrackingservice.infrastructure.persistence.entity.Sprint;
import com.its.issuetrackingservice.infrastructure.persistence.mapper.SprintMapper;
import com.its.issuetrackingservice.infrastructure.persistence.repository.IssueRepository;
import com.its.issuetrackingservice.infrastructure.persistence.repository.SprintRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SprintService {
    private final SprintRepository sprintRepository;
    private final IssueRepository issueRepository;
    private final UserContext userContext;

    private final SprintMapper sprintMapper;


    public Sprint getSprintById(Long sprintId) {
        Optional<Sprint> sprint = sprintRepository.findById(sprintId);
        if (sprint.isEmpty()) {
            throw new DataNotFoundException(I18nExceptionKeys.SPRINT_NOT_FOUND, String.format("sprint id=%d", sprintId));
        }

        return sprint.get();
    }

    public Map<Long, Sprint> getBulkSprints(List<Long> ids) {
        return sprintRepository.findAllById(ids).stream().collect(Collectors.toMap(Sprint::getId, sprint -> sprint));
    }

    public void checkSprintExists(Long sprintId) {
        Optional<Sprint> sprint = sprintRepository.findById(sprintId);
        if (sprint.isEmpty()) {
            throw new DataNotFoundException(I18nExceptionKeys.SPRINT_NOT_FOUND, String.format("sprint id=%d", sprintId));
        }
    }

    public void deleteSprintsByIdList(List<Long> sprintIdList) {
        if (issueRepository.existsBySprintIdIn(sprintIdList)) {
            throw new WrongUsageException(I18nExceptionKeys.SPRINTS_MUST_NOT_CONTAIN_ANY_ISSUE_TO_BE_DELETED, String.format("sprint id list=%s"  ,sprintIdList.toString()));
        }

        sprintRepository.deleteAllById(sprintIdList);
    }

    public void bulkUpsertSprint(List<Sprint> sprints) {
        sprints.forEach(sprint -> sprint.setAuditableFields(userContext));
        sprintRepository.saveAllAndFlush(sprints);
    }

    public void updateSprints(List<SprintRequest> sprintRequests, Project project) {
        if (CollectionUtils.isEmpty(sprintRequests)) {
            return;
        }

        List<Long> idsToRemove = new ArrayList<>();
        List<Long> idsToFetch = new ArrayList<>();
        sprintRequests.forEach(sprintRequest ->  {
            if (sprintRequest.isDelete()) {
                idsToRemove.add(sprintRequest.getId());
            } else if (Objects.nonNull(sprintRequest.getId())) {
                idsToFetch.add(sprintRequest.getId());
            }
        });


        List<Sprint> sprints = new ArrayList<>();
        Map<Long, Sprint> sprintsByIdMap = getBulkSprints(idsToFetch);
        sprintRequests.forEach(sprintRequest -> {
            Long sprintId = sprintRequest.getId();

            if (Objects.nonNull(sprintId) ) {
                if (sprintsByIdMap.containsKey(sprintId)) {
                    // Update existing entity
                    Sprint sprint = sprintsByIdMap.get(sprintId);
                    sprintMapper.patchEntity(sprintRequest, sprint);
                    sprints.add(sprint);
                }
                return;
            }

            // Create new entity
            sprints.add(sprintMapper.toEntity(sprintRequest, project));
        });

        bulkUpsertSprint(sprints);
        deleteSprintsByIdList(idsToRemove);
    }

}
