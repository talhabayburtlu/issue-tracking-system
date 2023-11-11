package com.its.issuetrackingservice.domain.common.exception;


import com.its.issuetrackingservice.domain.common.constants.I18nExceptionKeys;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class DataNotFoundException extends I18nException {
    public DataNotFoundException(I18nExceptionKeys i18nExceptionKeys, Object... args) {
        super(i18nExceptionKeys, args);
        setStatusCode(HttpStatus.NOT_FOUND);
    }
}
