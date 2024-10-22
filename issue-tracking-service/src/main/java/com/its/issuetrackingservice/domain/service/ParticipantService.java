package com.its.issuetrackingservice.domain.service;

import com.its.issuetrackingservice.domain.constants.I18nExceptionKeys;
import com.its.issuetrackingservice.domain.enums.ParticipationType;
import com.its.issuetrackingservice.domain.exception.DataNotFoundException;
import com.its.issuetrackingservice.infrastructure.dto.UserContext;
import com.its.issuetrackingservice.infrastructure.dto.request.IssueRequest;
import com.its.issuetrackingservice.infrastructure.dto.request.ParticipantRequest;
import com.its.issuetrackingservice.infrastructure.persistence.entity.Issue;
import com.its.issuetrackingservice.infrastructure.persistence.entity.Participation;
import com.its.issuetrackingservice.infrastructure.persistence.entity.User;
import com.its.issuetrackingservice.infrastructure.persistence.repository.ParticipationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ParticipantService {
    private final UserContext userContext;
    private final UserService userService;
    private final ParticipationRepository participationRepository;

    public void removeAllParticipants(Set<Long> participantIds) {
        participationRepository.deleteAllById(participantIds);
    }

    public Set<Long> getParticipantIdsToRemove(IssueRequest issueRequest) {
        Set<Long> ids = new HashSet<>();
        if (Objects.isNull(issueRequest.getParticipantsRequest())) {
            return Set.of();
        }
        ids.addAll(issueRequest.getParticipantsRequest().getAssignees().stream().filter(ParticipantRequest::isDelete).map(ParticipantRequest::getId).collect(Collectors.toSet()));
        ids.addAll(issueRequest.getParticipantsRequest().getVerifiers().stream().filter(ParticipantRequest::isDelete).map(ParticipantRequest::getId).collect(Collectors.toSet()));
        ids.addAll(issueRequest.getParticipantsRequest().getReviewers().stream().filter(ParticipantRequest::isDelete).map(ParticipantRequest::getId).collect(Collectors.toSet()));
        ids.addAll(issueRequest.getParticipantsRequest().getWatchers().stream().filter(ParticipantRequest::isDelete).map(ParticipantRequest::getId).collect(Collectors.toSet()));
        return ids;
    }

    public Set<Participation> buildParticipants(IssueRequest issueRequest, Issue issue, Boolean isForUpdate) {
        Set<Participation> participationSet = new HashSet<>();
        User creatorUser = userContext.getUser();
        if (Boolean.FALSE.equals(isForUpdate)) {
            participationSet.add(buildParticipation(creatorUser, issue, ParticipationType.CREATOR));
        }

        if (Objects.isNull(issueRequest.getParticipantsRequest())) {
            return participationSet;
        }

        issueRequest.getParticipantsRequest().getAssignees().stream()
                .filter(Predicate.not(ParticipantRequest::isDelete))
                .forEach(assignee -> participationSet.add(buildParticipation(assignee, issue, ParticipationType.ASSIGNEE, isForUpdate)));
        issueRequest.getParticipantsRequest().getVerifiers().stream()
                .filter(Predicate.not(ParticipantRequest::isDelete))
                .forEach(verifier -> participationSet.add(buildParticipation(verifier, issue, ParticipationType.VERIFIER, isForUpdate)));
        issueRequest.getParticipantsRequest().getReviewers().stream()
                .filter(Predicate.not(ParticipantRequest::isDelete))
                .forEach(reviewer -> participationSet.add(buildParticipation(reviewer, issue, ParticipationType.REVIEWER, isForUpdate)));
        issueRequest.getParticipantsRequest().getWatchers().stream()
                .filter(Predicate.not(ParticipantRequest::isDelete))
                .forEach(reviewer -> participationSet.add(buildParticipation(reviewer, issue, ParticipationType.WATCHER, isForUpdate)));
        return participationSet;
    }

    public Participation buildParticipation(ParticipantRequest participantRequest, Issue issue, ParticipationType participationType, boolean includeIDs) {
        User user;
        if (Boolean.TRUE.equals(includeIDs)) {
            Long participationId = participantRequest.getId();
            Participation participation = getParticipationById(participationId);
            user = participation.getUser();

            return buildParticipation(participationId, user, issue, participationType);
        }

        user = userService.getUserById(participantRequest.getUserId());
        return buildParticipation(user, issue, participationType);
    }

    public Participation buildParticipation(User user, Issue issue, ParticipationType participationType) {
        return buildParticipation(null, user, issue, participationType);
    }

    public Participation buildParticipation(Long id, User user, Issue issue, ParticipationType participationType) {
        Participation participation = Participation.builder()
                .id(id)
                .issue(issue)
                .user(user)
                .participationType(participationType)
                .isWatching(Boolean.TRUE)
                .build();
        participation.setAuditableFields(userContext);

        return participation;
    }

    public Participation getParticipationById(Long participationId) {
        Optional<Participation> optionalParticipation = participationRepository.findById(participationId);
        if (optionalParticipation.isEmpty()) {
            throw new DataNotFoundException(I18nExceptionKeys.PARTICIPATION_NOT_FOUND, String.format("participation id=%d", participationId));
        }

        return optionalParticipation.get();
    }


}
