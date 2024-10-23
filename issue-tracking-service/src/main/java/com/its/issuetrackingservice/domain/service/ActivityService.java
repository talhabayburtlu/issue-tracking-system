package com.its.issuetrackingservice.domain.service;

import com.its.issuetrackingservice.domain.constants.I18nExceptionKeys;
import com.its.issuetrackingservice.domain.enums.ActivityType;
import com.its.issuetrackingservice.domain.exception.DataNotFoundException;
import com.its.issuetrackingservice.infrastructure.dto.UserContext;
import com.its.issuetrackingservice.infrastructure.persistence.entity.Activity;
import com.its.issuetrackingservice.infrastructure.persistence.repository.ActivityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ActivityService {

    private final ActivityRepository activityRepository;
    private final UserContext userContext;

    public Activity getActivityById(Long activityId, Long issueId) {
        Optional<Activity> optionalActivityItem = activityRepository.getByIdAndIssueId(activityId, issueId);
        if (optionalActivityItem.isEmpty()) {
            throw new DataNotFoundException(I18nExceptionKeys.ACTIVITY_NOT_FOUND, String.format("activity id=%d", activityId));
        }

        return optionalActivityItem.get();
    }

    public Page<Activity> getActivitiesByIssueId(Long issueId, Pageable pageable) {
        return activityRepository.getActivitiesByIssueId(issueId, pageable);
    }

    public Activity createComment(Activity activity) {
        activity.setCreatorUser(userContext.getUser());
        activity.setActivityType(ActivityType.COMMENT);

        return upsertActivity(activity);
    }

    public Activity upsertActivity(Activity activity) {
        activity.setAuditableFields(userContext);
        activity.getActivityItems().forEach(activityItem -> {
            activityItem.setActivity(activity);
            activityItem.setAuditableFields(userContext);
        });

        return activityRepository.save(activity);
    }

}
