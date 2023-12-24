package com.its.issuetrackingservice.domain.service;

import com.its.issuetrackingservice.domain.constants.I18nExceptionKeys;
import com.its.issuetrackingservice.domain.exception.DataNotFoundException;
import com.its.issuetrackingservice.domain.exception.WrongUsageException;
import com.its.issuetrackingservice.persistence.entity.State;
import com.its.issuetrackingservice.persistence.repository.StateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StateDomainService {
    private final StateRepository stateRepository;

    public State getStateById(Long stateId) {
        Optional<State> state = stateRepository.findById(stateId);
        if (state.isEmpty()) {
            throw new DataNotFoundException(I18nExceptionKeys.STATE_NOT_FOUND, String.format("state id=%d", stateId));
        }

        return state.get();
    }
    public State getProjectInitialState(Long projectId) {
        Optional<State> initialState = stateRepository.getProjectInitialState(projectId);

        if (initialState.isEmpty()) {
            throw new DataNotFoundException(I18nExceptionKeys.PROJECT_MUST_HAVE_INITIAL_STATE, String.format("project id=%d", projectId));
        }

        return initialState.get();
    }

    public void checkStateTransitionAllowed(State currentState, State nextState) {
        if (!Objects.equals(currentState.getNextState().getId(), nextState.getId())){
            throw new WrongUsageException(I18nExceptionKeys.STATE_TRANSITION_NOT_ALLOWED,
                    String.format("current state id =%d to next state id =%d" , currentState.getId(), nextState.getId()));
        }
    }


}
