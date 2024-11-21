package com.its.issuetrackingservice.domain.commands.activity;

import com.its.issuetrackingservice.domain.commands.Command;
import com.its.issuetrackingservice.domain.constants.NotificationConstants;
import com.its.issuetrackingservice.domain.service.ActivityService;
import com.its.issuetrackingservice.domain.service.IssueService;
import com.its.issuetrackingservice.domain.service.NotificationService;
import com.its.issuetrackingservice.domain.service.ParticipantService;
import com.its.issuetrackingservice.domain.util.SpringContext;
import com.its.issuetrackingservice.infrastructure.dto.request.ActivityRequest;
import com.its.issuetrackingservice.infrastructure.dto.response.ActivityResponse;
import com.its.issuetrackingservice.infrastructure.persistence.entity.Activity;
import com.its.issuetrackingservice.infrastructure.persistence.entity.Issue;
import com.its.issuetrackingservice.infrastructure.persistence.entity.Participation;
import com.its.issuetrackingservice.infrastructure.persistence.mapper.ActivityMapper;
import lombok.AllArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;
import java.util.Optional;


@SuperBuilder
@AllArgsConstructor
public class CreateActivityCommand extends Command<ActivityResponse> {
    // Inputs
    private ActivityRequest activityRequest;
    private Boolean sendNotification;

    // Generates
    private Activity activity;

    // Services
    private ActivityService activityService;
    private ParticipantService participantService;
    private IssueService issueService;
    private NotificationService notificationService;
    private ActivityMapper activityMapper;

    @Override
    public void init() {
        this.activityService = SpringContext.getBean(ActivityService.class);
        this.participantService = SpringContext.getBean(ParticipantService.class);
        this.issueService = SpringContext.getBean(IssueService.class);
        this.notificationService = SpringContext.getBean(NotificationService.class);
        this.activityMapper = SpringContext.getBean(ActivityMapper.class);
    }

    @Override
    public ActivityResponse execute() {
        this.activity = activityMapper.activityRequestToEntity(activityRequest);
        this.activity = activityService.upsertActivity(activity);

        if (Boolean.TRUE.equals(sendNotification)) {
            List<Participation> watchers = participantService.getIssueWatcherParticipants(activityRequest.getIssueId());
            Issue issue = issueService.getIssueById(activityRequest.getIssueId());
            notificationService.sendNotification(watchers, getNotificationBody(issue), getNotificationSubject(issue));
        }

        if (Boolean.TRUE.equals(getReturnResultAfterExecution())) {
            return getResult().orElse(null);
        }

        return null;
    }

    private String getNotificationBody(Issue issue) {
        StringBuffer activities = new StringBuffer("<ul>");

        activity.getActivityItems().forEach(activityItem ->
                activities.append(String.format("<li><xmp>%s - %s -> %s</xmp></li>", activityItem.getFieldName(), activityItem.getOldValue() != null ? activityItem.getOldValue() : "null", activityItem.getNewValue()))
        );

        activities.append("</ul>");

        return String.format(NotificationConstants.ISSUE_ACTIVITY_NOTIFICATION_BODY_TEMPLATE, issue.getTitle(), activities);
    }

    private String getNotificationSubject(Issue issue) {
        return String.format(NotificationConstants.ISSUE_ACTIVITY_NOTIFICATION_SUBJECT_TEMPLATE, issue.getTitle());
    }

    @Override
    public Optional<ActivityResponse> getResult() {
        return Optional.ofNullable(activityMapper.toResponse(activity));
    }


}
