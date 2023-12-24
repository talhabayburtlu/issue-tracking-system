package com.its.issuetrackingservice.domain.exception;


import com.its.issuetrackingservice.domain.constants.I18nExceptionKeys;
import com.its.issuetrackingservice.domain.service.i18n.I18ExceptionService;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatusCode;

@Getter
@Setter
@NoArgsConstructor
public abstract class I18nException extends RuntimeException {
	private HttpStatusCode statusCode;
	private String message;

	public I18nException(I18nExceptionKeys i18nExceptionKey, Object... args) {
		this.message = I18ExceptionService.resolveI18nExceptionMessage(i18nExceptionKey, args);
	}

}
