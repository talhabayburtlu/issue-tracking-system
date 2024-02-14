package com.its.issuetrackingservice.domain.exception;


import com.its.issuetrackingservice.domain.constants.I18nExceptionKeys;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class InternalServerException extends I18nException {
    public InternalServerException(I18nExceptionKeys i18nExceptionKeys, Object... args) {
        super(i18nExceptionKeys, args);
        setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
