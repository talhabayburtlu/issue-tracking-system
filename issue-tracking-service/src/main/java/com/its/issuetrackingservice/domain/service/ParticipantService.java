package com.its.issuetrackingservice.domain.service;

import com.its.issuetrackingservice.domain.enums.ParticipationType;
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
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ParticipantService {
    private final UserContext userContext;
    private final UserService userService;
    private final ParticipationRepository participationRepository;
    public void deleteParticipants(Set<Long> idsToBeDeleted) {
        participationRepository.deleteAllById(idsToBeDeleted);
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

    public Set<Participation> buildParticipants(IssueRequest issueRequest, Issue issue, Boolean includeIDs) {
        Set<Participation> participationSet = new HashSet<>();
        User creatorUser = userContext.getUser();
        participationSet.add(buildParticipation(creatorUser, issue, ParticipationType.CREATOR));

        if (Objects.isNull(issueRequest.getParticipantsRequest())) {
            return participationSet;
        }

        issueRequest.getParticipantsRequest().getAssignees().forEach(assignee -> participationSet.add(buildParticipation(assignee, issue, ParticipationType.ASSIGNEE, includeIDs)));
        issueRequest.getParticipantsRequest().getVerifiers().forEach(verifier -> participationSet.add(buildParticipation(verifier, issue, ParticipationType.VERIFIER, includeIDs)));
        issueRequest.getParticipantsRequest().getReviewers().forEach(reviewer -> participationSet.add(buildParticipation(reviewer, issue, ParticipationType.REVIEWER, includeIDs)));
        return participationSet;
    }

    public Participation buildParticipation(ParticipantRequest participantRequest, Issue issue, ParticipationType participationType, boolean includeIDs) {
        User user = userService.getUserById(participantRequest.getUserId());
        if (Boolean.TRUE.equals(includeIDs)) {
            return buildParticipation(participantRequest.getId(), user, issue, participationType);
        }

        return buildParticipation(user, issue, participationType);
    }

    public Participation buildParticipation(User user, Issue issue, ParticipationType participationType) {
        return buildParticipation(null, user, issue, participationType);
    }

    public Participation buildParticipation(Long id, User user, Issue issue, ParticipationType participationType) {
        return Participation.builder()
                .id(id)
                .issue(issue)
                .user(user)
                .participationType(participationType)
                .isWatching(Boolean.TRUE)
                .build();
    }


}
