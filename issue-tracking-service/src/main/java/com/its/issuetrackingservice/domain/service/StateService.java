package com.its.issuetrackingservice.domain.service;

import com.its.issuetrackingservice.domain.constants.I18nExceptionKeys;
import com.its.issuetrackingservice.domain.exception.DataNotFoundException;
import com.its.issuetrackingservice.domain.exception.WrongUsageException;
import com.its.issuetrackingservice.infrastructure.dto.UserContext;
import com.its.issuetrackingservice.infrastructure.dto.request.StateRequest;
import com.its.issuetrackingservice.infrastructure.persistence.entity.Project;
import com.its.issuetrackingservice.infrastructure.persistence.entity.State;
import com.its.issuetrackingservice.infrastructure.persistence.mapper.StateMapper;
import com.its.issuetrackingservice.infrastructure.persistence.repository.IssueRepository;
import com.its.issuetrackingservice.infrastructure.persistence.repository.StateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StateService {
    private final StateRepository stateRepository;
    private final IssueRepository issueRepository;
    private final UserContext userContext;
    private final StateMapper stateMapper;

    public State getStateById(Long stateId) {
        Optional<State> state = stateRepository.findById(stateId);
        if (state.isEmpty()) {
            throw new DataNotFoundException(I18nExceptionKeys.STATE_NOT_FOUND, String.format("state id=%d", stateId));
        }

        return state.get();
    }

    public Map<Long, State> getBulkStates(List<Long> ids) {
        return stateRepository.findAllById(ids).stream().collect(Collectors.toMap(State::getId, state -> state));
    }

    public State getProjectInitialState(Long projectId) {
        Optional<State> initialState = stateRepository.getProjectInitialState(projectId);

        if (initialState.isEmpty()) {
            throw new DataNotFoundException(I18nExceptionKeys.PROJECT_MUST_HAVE_INITIAL_STATE, String.format("project id=%d", projectId));
        }

        return initialState.get();
    }

    public void checkStateTransitionAllowed(State currentState, State nextState) {
        // TODO: Change logic to include state rule transitions
        if (!Objects.equals(currentState.getNextState().getId(), nextState.getId())){
            throw new WrongUsageException(I18nExceptionKeys.STATE_TRANSITION_NOT_ALLOWED,
                    String.format("current state id =%d to next state id =%d" , currentState.getId(), nextState.getId()));
        }
    }

    public void deleteStatesByIdList(List<Long> stateIdList) {
        if (issueRepository.existsByStateIdIn(stateIdList)) {
            throw new WrongUsageException(I18nExceptionKeys.STATES_MUST_NOT_CONTAIN_ANY_ISSUE_TO_BE_DELETED, String.format("state id list=%s" , stateIdList.toString()));
        }

        if (stateRepository.existsByNextStateIdIn(stateIdList)) {
            throw new WrongUsageException(I18nExceptionKeys.STATES_MUST_NOT_BE_NEXT_STATE_TO_BE_DELETED, String.format("state id list=%s" , stateIdList.toString()));
        }

        stateRepository.deleteAllById(stateIdList);
    }

    public void bulkUpsertState(List<State> states) {
        states.forEach(state -> state.setAuditableFields(userContext));
        stateRepository.saveAllAndFlush(states);
    }

    public void updateStates(List<StateRequest> stateRequests, Project project) {
        if (CollectionUtils.isEmpty(stateRequests)) {
            return;
        }

        List<Long> idsToRemove = new ArrayList<>();
        List<Long> idsToFetch = new ArrayList<>();
        stateRequests.forEach(sprintRequest ->  {
            if (sprintRequest.isDelete()) {
                idsToRemove.add(sprintRequest.getId());
            } else if (Objects.nonNull(sprintRequest.getId())) {
                idsToFetch.add(sprintRequest.getId());
            }
        });

        List<State> states = new ArrayList<>();
        Map<Long, State> statesByIdMap = getBulkStates(idsToFetch);
        stateRequests.forEach(stateRequest -> {
            Long stateId = stateRequest.getId();

            // Update existing entity
            if (Objects.nonNull(stateId)) {
                if (statesByIdMap.containsKey(stateId)) {
                    State state = statesByIdMap.get(stateId);
                    stateMapper.patchEntity(stateRequest, state);
                    states.add(state);

                }
                return;
           }
            // Create new entity
            states.add(stateMapper.toEntity(stateRequest, project));

        });

        bulkUpsertState(states);
        deleteStatesByIdList(idsToRemove);
    }

}
