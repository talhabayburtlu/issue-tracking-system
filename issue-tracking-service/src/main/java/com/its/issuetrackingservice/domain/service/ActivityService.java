package com.its.issuetrackingservice.domain.service;

import com.its.issuetrackingservice.domain.constants.I18nExceptionKeys;
import com.its.issuetrackingservice.domain.enums.ActivityType;
import com.its.issuetrackingservice.domain.exception.DataNotFoundException;
import com.its.issuetrackingservice.infrastructure.dto.UserContext;
import com.its.issuetrackingservice.infrastructure.persistence.entity.Activity;
import com.its.issuetrackingservice.infrastructure.persistence.repository.ActivityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ActivityService {

    private final ActivityRepository activityRepository;
    private final UserContext userContext;

    public Activity getActivityById(Long activityId, Long issueId, Long projectId) {
        Optional<Activity> optionalActivityItem = activityRepository.getByIdAndIssueIdAndProjectId(activityId, issueId, projectId);
        if (optionalActivityItem.isEmpty()) {
            throw new DataNotFoundException(I18nExceptionKeys.ACTIVITY_NOT_FOUND, String.format("activity id=%d", activityId));
        }

        return optionalActivityItem.get();
    }

    public List<Activity> getActivitiesByIssueId(Long issueId, Pageable pageable) {
        return activityRepository.getActivitiesByIssueId(issueId, pageable);
    }

    public Activity createComment(Activity activity) {
        activity.setCreatorUser(userContext.getUser());
        activity.setActivityType(ActivityType.COMMENT);

        return upsertActivity(activity);
    }

    public Activity upsertActivity(Activity activity) {
        activity.setAuditableFields(userContext);
        return activityRepository.save(activity);
    }

}