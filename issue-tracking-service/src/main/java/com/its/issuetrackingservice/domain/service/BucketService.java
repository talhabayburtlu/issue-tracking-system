package com.its.issuetrackingservice.domain.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.its.issuetrackingservice.domain.constants.I18nExceptionKeys;
import com.its.issuetrackingservice.domain.exception.InternalServerException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
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

        try {
            multipartFile.transferTo(tempFile);
        } catch (IOException ioException) {
            throw new InternalServerException(I18nExceptionKeys.ATTACHMENT_UPLOAD_OPERATION_FAILED);
        }

        var putObjectRequest = new PutObjectRequest(bucketName, objectName, tempFile).withCannedAcl(CannedAccessControlList.PublicRead);
        amazonS3Client.putObject(putObjectRequest);

        tempFile.delete();
    }

    public List<Byte> downloadObject(String objectName) {
        S3Object s3object = amazonS3Client.getObject(bucketName, objectName);
        S3ObjectInputStream inputStream = s3object.getObjectContent();
        try {
            FileUtils.copyInputStreamToFile(inputStream, new File("." + File.separator + objectName));
            return Arrays.asList(ArrayUtils.toObject(inputStream.readAllBytes()));
        } catch (IOException e) {
            throw new InternalServerException(I18nExceptionKeys.ATTACHMENT_DOWNLOAD_OPERATION_FAILED);
        }
    }

}
