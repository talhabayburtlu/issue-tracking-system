package com.its.issuetrackingservice.domain.common.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;

import java.time.OffsetDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GenericRestResponse<T> {
    private Metadata meta;
    private Pageable page;
    private T data;

    public static <T> GenericRestResponse<T> of(T data) {
        return of(data, null);
    }

    public static <T> GenericRestResponse<T> of(T data, Pageable pageable) {
        return of(HttpStatus.OK.value(), HttpStatus.OK.name(), data, pageable);
    }

    public static <T> GenericRestResponse<T> of(Integer responseCode, String responseMessage) {
        return of(responseCode, responseMessage, null, null);
    }

    public static <T> GenericRestResponse<T> of(Integer responseCode, String responseMessage, T data, Pageable pageable) {
        return GenericRestResponse.<T>builder()
                .meta(Metadata.builder()
                        .code(responseCode)
                        .message(responseMessage)
                        .fetchTime(OffsetDateTime.now())
                        .build())
                .data(data)
                .page(pageable)
                .build();
    }
}
