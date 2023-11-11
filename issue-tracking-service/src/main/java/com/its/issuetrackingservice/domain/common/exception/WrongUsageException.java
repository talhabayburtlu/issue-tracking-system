package com.its.issuetrackingservice.domain.common.exception;


import com.its.issuetrackingservice.domain.common.constants.I18nExceptionKeys;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
@Setter
public class WrongUsageException extends I18nException {
    public WrongUsageException(I18nExceptionKeys i18nExceptionKeys, Object... args) {
        super(i18nExceptionKeys, args);
        setStatusCode(HttpStatus.BAD_REQUEST);
    }
}
