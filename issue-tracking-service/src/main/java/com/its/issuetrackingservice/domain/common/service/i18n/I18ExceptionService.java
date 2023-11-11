package com.its.issuetrackingservice.domain.common.service.i18n;

import com.its.issuetrackingservice.domain.common.constants.I18nExceptionKeys;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class I18ExceptionService {
	public static String resolveI18nExceptionMessage(I18nExceptionKeys i18nExceptionKey) {
		String propertyKey = i18nExceptionKey.getPropertyKey();
		return null;
	}
}
