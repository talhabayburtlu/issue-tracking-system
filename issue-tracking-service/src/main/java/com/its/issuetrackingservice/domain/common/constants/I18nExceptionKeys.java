package com.its.issuetrackingservice.domain.common.constants;

public enum I18nExceptionKeys {
	USER_NOT_FOUND,
	ISSUE_NOT_FOUND,
	ISSUE_CREATOR_MUST_MATCH_AUTH_USER

	;

	public String getPropertyKey() {
		return this.name().toLowerCase().replaceAll("_", ".");
	}
}
