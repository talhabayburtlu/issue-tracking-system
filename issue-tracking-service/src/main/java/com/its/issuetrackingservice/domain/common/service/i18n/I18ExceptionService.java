package com.its.issuetrackingservice.domain.common.service.i18n;

import com.its.issuetrackingservice.domain.common.constants.I18nExceptionKeys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.util.Locale;
import java.util.ResourceBundle;

@Service
@RequiredArgsConstructor
public class I18ExceptionService {

	public static String resolveI18nExceptionMessage(I18nExceptionKeys i18nExceptionKey, Object... args) {
		String propertyKey = i18nExceptionKey.getPropertyKey();

		Locale locale = LocaleContextHolder.getLocale();
		ResourceBundle resourceBundle = ResourceBundle.getBundle("language/messages", locale);

		StringBuilder message = new StringBuilder();
		message.append(String.format("[%s]", resourceBundle.getString(propertyKey)));
		message.append(String.format(", arguments: [%s]", StringUtils.join(args, ", ")));
		return message.toString();
	}
}
