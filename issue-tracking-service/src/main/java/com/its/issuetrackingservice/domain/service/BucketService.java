package com.its.issuetrackingservice.domain.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import com.its.issuetrackingservice.domain.constants.I18nExceptionKeys;
import com.its.issuetrackingservice.domain.exception.InternalServerException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.ArrayUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BucketService {

    @Value("${aws.s3.bucket.name}")
    private String bucketName;

    private final AmazonS3 amazonS3Client;

    public void putObject(String objectName, MultipartFile multipartFile) {

        File tempFile = new File("." + File.separator + objectName);
        tempFile.deleteOnExit();

        PutObjectRequest putObjectRequest;
        try {
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentLength(multipartFile.getSize());

            putObjectRequest = new PutObjectRequest(bucketName, objectName, multipartFile.getInputStream(), objectMetadata).withCannedAcl(CannedAccessControlList.PublicRead);
        } catch (IOException ioException) {
            throw new InternalServerException(I18nExceptionKeys.ATTACHMENT_UPLOAD_OPERATION_FAILED);
        }

        amazonS3Client.putObject(putObjectRequest);

        tempFile.delete();
    }

    public List<Byte> downloadObject(String objectName) {
        S3Object s3object = amazonS3Client.getObject(bucketName, objectName);
        S3ObjectInputStream inputStream = s3object.getObjectContent();

        try {
            Integer objectLength = Math.toIntExact(s3object.getObjectMetadata().getContentLength());

            byte[] content = new byte[objectLength];
            inputStream.read(content);

            return Arrays.asList(ArrayUtils.toObject(content));
        } catch (IOException e) {
            throw new InternalServerException(I18nExceptionKeys.ATTACHMENT_DOWNLOAD_OPERATION_FAILED);
        }
    }

}
