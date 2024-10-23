package com.its.issuetrackingservice.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.util.MimeTypeUtils;

import java.util.Arrays;
import java.util.List;

@Getter
@RequiredArgsConstructor
public enum AttachmentType {
    IMAGE(Constants.IMAGE_CONTENT_TYPES),
    VIDEO(Constants.VIDEO_CONTENT_TYPES);

    private final List<String> types;

    public static AttachmentType findAttachmentTypeByContentType(String contentType) {
        return Arrays.stream(values()).
                filter(value -> value.types.contains(contentType)).
                findFirst()
                .orElse(null);
    }

    private static class Constants {
        private static final List<String> IMAGE_CONTENT_TYPES = List.of(MimeTypeUtils.IMAGE_JPEG.toString(), MimeTypeUtils.IMAGE_PNG.toString(), MimeTypeUtils.IMAGE_GIF.toString());
        private static final List<String> VIDEO_CONTENT_TYPES = List.of("video/mp4");
    }


}
