package br.com.lasanhaspec.carservice.infrastructure.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;

import java.io.IOException;
import java.util.UUID;

@Service
@Slf4j
public class S3StorageService implements StorageService {

    private final S3Client s3Client;
    private final String bucketName;

    public S3StorageService() {

        AwsBasicCredentials credentials =
                AwsBasicCredentials.create(
                        "AKIA2EVMOFZAOVH66SHX",
                        "yiqjWFYJ7hlSmgkuAFhOWP9w45K0wfltt3kuL3zW"
                );

        this.bucketName = "lasanhaspec-vehicle-images-dev";

        this.s3Client = S3Client.builder()
                .region(Region.of("us-east-2"))
                .credentialsProvider(
                        StaticCredentialsProvider.create(credentials)
                )
                .build();
    }

    @Override
    public UploadResult uploadFile(MultipartFile file) {

        String fileName =
                UUID.randomUUID() + "-" +
                        file.getOriginalFilename().replace(" ", "_");

        try {
            s3Client.putObject(
                    PutObjectRequest.builder()
                            .bucket(bucketName)
                            .key(fileName)
                            .contentType(file.getContentType())
                            .build(),
                    RequestBody.fromBytes(file.getBytes())
            );
        } catch (IOException e) {
            throw new RuntimeException("Erro ao enviar arquivo", e);
        }

        String url =
                "https://" +
                        bucketName +
                        ".s3.amazonaws.com/" +
                        fileName;

        return new UploadResult(url, fileName);
    }

    @Override
    public void deleteFile(String fileKey) {
        s3Client.deleteObject(
                DeleteObjectRequest.builder()
                        .bucket(bucketName)
                        .key(fileKey)
                        .build()
        );
    }

    public static class UploadResult {

        private final String url;
        private final String key;

        public UploadResult(String url, String key) {
            this.url = url;
            this.key = key;
        }

        public String getUrl() {
            return url;
        }

        public String getKey() {
            return key;
        }
    }
}