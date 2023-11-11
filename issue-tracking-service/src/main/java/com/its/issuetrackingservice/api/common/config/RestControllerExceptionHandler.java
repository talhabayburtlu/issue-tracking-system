package com.its.issuetrackingservice.api.common.config;

import com.its.issuetrackingservice.domain.common.exception.DataNotFoundException;
import com.its.issuetrackingservice.domain.common.exception.I18nException;
import com.its.issuetrackingservice.domain.common.model.GenericRestResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@RequiredArgsConstructor
public class RestControllerExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {Exception.class, RuntimeException.class})
    protected ResponseEntity<GenericRestResponse<Void>> handleRuntimeException(RuntimeException ex) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(GenericRestResponse.of(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage()));
    }

    @ExceptionHandler(value = {I18nException.class})
    protected ResponseEntity<GenericRestResponse<Void>> handleI18nException(I18nException ex) {
        return ResponseEntity
                .status(ex.getStatusCode())
                .body(GenericRestResponse.of(ex.getStatusCode().value(), ex.getMessage()));
    }
}
