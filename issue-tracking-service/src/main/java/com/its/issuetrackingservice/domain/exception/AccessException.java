package com.its.issuetrackingservice.domain.exception;


import com.its.issuetrackingservice.domain.constants.I18nExceptionKeys;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class AccessException extends I18nException {
    public AccessException(I18nExceptionKeys i18nExceptionKeys, Object... args) {
        super(i18nExceptionKeys, args);
        setStatusCode(HttpStatus.FORBIDDEN);
    }
}
