package com.its.issuetrackingservice.domain.constants;

public class NotificationConstants {
    public static String ISSUE_ACTIVITY_NOTIFICATION_BODY_TEMPLATE = "<p>" +
            "<h2>New activity took place for <i>%s</i> issue.</h2>" +
            "<br>%s<br>" +
            "<i>You received this notification because of you being a watcher for this particular issue. You can stop getting notification by un-watching the issue.</i>" +
            "<br><br>" +
            "<small>Issue Tracking System © 2024 <small>" +
            "</p>";

    public static String ISSUE_ACTIVITY_NOTIFICATION_SUBJECT_TEMPLATE = "New Activity on %s Issue";
}
