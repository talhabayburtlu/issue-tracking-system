package com.its.issuetrackingservice.domain.common.exception;


import com.its.issuetrackingservice.domain.common.constants.I18nExceptionKeys;
import com.its.issuetrackingservice.domain.common.service.i18n.I18ExceptionService;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class I18nException extends RuntimeException {
	private String message;
	private Class<? extends RuntimeException> exceptionTypeClazz;

	public I18nException(I18nExceptionKeys i18nExceptionKey, Class<? extends RuntimeException> exceptionTypeClazz) {
		this.exceptionTypeClazz = exceptionTypeClazz;
		this.message = I18ExceptionService.resolveI18nExceptionMessage(i18nExceptionKey);
	}

}
