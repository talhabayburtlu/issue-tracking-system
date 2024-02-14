package com.its.issuetrackingservice.domain.constants;

public enum I18nExceptionKeys {
	USER_NOT_FOUND,
	ISSUE_NOT_FOUND,
	SPRINT_NOT_FOUND,
	PROJECT_NOT_FOUND,
	STATE_NOT_FOUND,
	COMMENT_NOT_FOUND,
	ATTACHMENT_NOT_FOUND,
	PROJECT_MUST_HAVE_INITIAL_STATE,
	STATE_TRANSITION_NOT_ALLOWED,
	USER_DOES_NOT_ACCESS_TO_PROJECT,
	ISSUE_CREATOR_MUST_MATCH_AUTH_USER,
	COMMENT_CREATOR_MUST_MATCH_AUTH_USER,
	MAX_ATTACHMENT_SIZE_EXCEEDED,
	ATTACHMENT_CONTENT_TYPE_IS_NOT_ALLOWED,
	ATTACHMENT_COUNT_EXCEEDED_FOR_AN_ISSUE,
	ATTACHMENT_UPLOAD_OPERATION_FAILED,
	ATTACHMENT_DOWNLOAD_OPERATION_FAILED
	;

	public String getPropertyKey() {
		return this.name().toLowerCase().replaceAll("_", ".");
	}
}
