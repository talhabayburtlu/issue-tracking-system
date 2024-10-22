package com.its.issuetrackingservice.domain.enums;

import com.its.issuetrackingservice.infrastructure.dto.request.ActivityItemRequest;
import com.its.issuetrackingservice.infrastructure.persistence.entity.Issue;
import com.its.issuetrackingservice.infrastructure.persistence.entity.Participation;
import com.its.issuetrackingservice.infrastructure.persistence.entity.Sprint;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.*;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

@Getter
@RequiredArgsConstructor
public enum IssueActivityField {
    TITLE(IssueActivityField::generateForTitle),
    DESCRIPTION(IssueActivityField::generateForDescription),
    ESTIMATION(IssueActivityField::generateForEstimation),
    CATEGORY(IssueActivityField::generateForCategory),
    STATE(IssueActivityField::generateForState),
    SPRINT(IssueActivityField::generateForSprint),
    PARTICIPANTS(IssueActivityField::generateForParticipants);

    private final BiFunction<Issue, Issue, List<ActivityItemRequest>> generateActivityItemFunction;

    private static List<ActivityItemRequest> generateForTitle(Issue oldIssue, Issue newIssue) {
        ActivityItemType activityItemType = getActivityItemType(oldIssue);
        if (Objects.isNull(oldIssue) || !Objects.equals(oldIssue.getTitle(), newIssue.getTitle())) {
            return List.of(buildActivityItemRequest("title", Objects.nonNull(oldIssue) ? oldIssue.getTitle() : null, newIssue.getTitle(), activityItemType));
        }
        return List.of();
    }

    private static List<ActivityItemRequest> generateForDescription(Issue oldIssue, Issue newIssue) {
        ActivityItemType activityItemType = getActivityItemType(oldIssue);
        if (Objects.isNull(oldIssue) || !Objects.equals(oldIssue.getDescription(), newIssue.getDescription())) {
            return List.of(buildActivityItemRequest("description", Objects.nonNull(oldIssue) ? oldIssue.getDescription() : null, newIssue.getDescription(), activityItemType));
        }
        return List.of();
    }

    private static List<ActivityItemRequest> generateForEstimation(Issue oldIssue, Issue newIssue) {
        ActivityItemType activityItemType = getActivityItemType(oldIssue);
        if (Objects.isNull(oldIssue) || !Objects.equals(oldIssue.getEstimation(), newIssue.getEstimation())) {
            return List.of(buildActivityItemRequest("estimation", Objects.nonNull(oldIssue) ? oldIssue.getEstimation().toString() : null, newIssue.getEstimation().toString(), activityItemType));
        }
        return List.of();
    }

    private static List<ActivityItemRequest> generateForCategory(Issue oldIssue, Issue newIssue) {
        ActivityItemType activityItemType = getActivityItemType(oldIssue);
        if (Objects.isNull(oldIssue) || !Objects.equals(oldIssue.getCategory(), newIssue.getCategory())) {
            return List.of(buildActivityItemRequest("category", Objects.nonNull(oldIssue) ? oldIssue.getCategory().getName() : null, newIssue.getCategory().getName(), activityItemType));
        }
        return List.of();
    }

    private static List<ActivityItemRequest> generateForState(Issue oldIssue, Issue newIssue) {
        ActivityItemType activityItemType = getActivityItemType(oldIssue);
        if (Objects.isNull(oldIssue) || !Objects.equals(oldIssue.getState(), newIssue.getState())) {
            return List.of(buildActivityItemRequest("state", Objects.nonNull(oldIssue) ? oldIssue.getState().getTitle() : null, newIssue.getState().getTitle(), activityItemType));
        }
        return List.of();
    }

    private static List<ActivityItemRequest> generateForSprint(Issue oldIssue, Issue newIssue) {
        ActivityItemType activityItemType = getActivityItemType(oldIssue);
        Sprint oldSprint = Objects.nonNull(oldIssue) ? oldIssue.getSprint() : null;
        Sprint newSprint = newIssue.getSprint();

        if ((Objects.nonNull(oldSprint) || Objects.nonNull(newSprint)) && !(Objects.equals(Objects.nonNull(oldSprint) ? oldSprint.getName() : null, newSprint.getName()))) {
            return List.of(buildActivityItemRequest("sprint", Objects.nonNull(oldSprint) ? oldSprint.getName() : null, newSprint.getName(), activityItemType));
        }
        return List.of();
    }

    private static List<ActivityItemRequest> generateForParticipants(Issue oldIssue, Issue newIssue) {
        List<ActivityItemRequest> activityItemRequests = new ArrayList<>();

        Set<Participation> oldParticipantsSet = Objects.nonNull(oldIssue) ? oldIssue.getParticipants() : Set.of();
        Set<Participation> newParticipantsSet = newIssue.getParticipants();

        Set<Participation> removedParticipation = difference(oldParticipantsSet, newParticipantsSet);
        Set<Participation> addedParticipation = difference(newParticipantsSet, oldParticipantsSet);

        activityItemRequests.addAll(removedParticipation.stream()
                .map(removed -> buildActivityItemRequest(removed.getParticipationType().toString(), removed.getUser().getUsername(), null, ActivityItemType.DELETE))
                .toList()
        );
        activityItemRequests.addAll(addedParticipation.stream()
                .map(added -> buildActivityItemRequest(added.getParticipationType().toString(), null, added.getUser().getUsername(), ActivityItemType.CREATE))
                .toList()
        );

        return activityItemRequests;
    }

    private static ActivityItemRequest buildActivityItemRequest(String fieldName, String oldValue, String newValue, ActivityItemType activityItemType) {
        return ActivityItemRequest.builder()
                .fieldName(fieldName)
                .oldValue(oldValue)
                .newValue(newValue)
                .type(activityItemType.toString())
                .build();
    }

    private static <T extends Participation> Set<T> difference(final Set<T> setOne, final Set<T> setTwo) {
        Set<Long> ids = setTwo.stream().map(Participation::getId).collect(Collectors.toSet());
        Set<T> result = new HashSet<>(setOne);
        result.removeIf(t -> ids.contains(t.getId()));
        return result;
    }

    private static ActivityItemType getActivityItemType(Issue oldIssue) {
        return Objects.isNull(oldIssue) ? ActivityItemType.CREATE : ActivityItemType.UPDATE;
    }

}
