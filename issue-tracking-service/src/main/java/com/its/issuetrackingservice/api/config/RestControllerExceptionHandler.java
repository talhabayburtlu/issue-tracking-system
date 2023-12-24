package com.its.issuetrackingservice.api.config;

import com.its.issuetrackingservice.api.model.GenericRestResponse;
import com.its.issuetrackingservice.domain.exception.I18nException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class RestControllerExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {Exception.class, RuntimeException.class})
    protected ResponseEntity<GenericRestResponse<Void>> handleRuntimeException(RuntimeException ex) {
        log.error(ex.getMessage(), ex);
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(GenericRestResponse.of(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage()));
    }

    @ExceptionHandler(value = {I18nException.class})
    protected ResponseEntity<GenericRestResponse<Void>> handleI18nException(I18nException ex) {
        log.error(ex.getMessage(), ex);
        return ResponseEntity
                .status(ex.getStatusCode())
                .body(GenericRestResponse.of(ex.getStatusCode().value(), ex.getMessage()));
    }
}
