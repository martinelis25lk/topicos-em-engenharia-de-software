package br.com.lasanhaspec.carservice.infrastructure.storage;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.util.UUID;

@Service
public class S3StorageService implements  StorageService{

    private final S3Client s3Client;
    private final String bucketName;



    public S3StorageService(
            @Value("${spring.aws.s3.bucket-name}") String bucketName,
            @Value("${spring.aws.region}") String region
    ){
        this.bucketName = bucketName;
        this.s3Client   = S3Client.builder()
                .region(Region.of(region))
                .build();
    }





    @Override
    public String uploadFile(MultipartFile file){

        String fileName = UUID.randomUUID() + "-shmalugou-" + file.getOriginalFilename();


        try{
            s3Client.putObject(
                    PutObjectRequest.builder()
                            .bucket(bucketName)
                            .key(fileName)
                            .contentType(file.getContentType())
                            .build(),
                    RequestBody.fromBytes(file.getBytes())
            );
        } catch (IOException e) {
            throw new RuntimeException("Error during upload service kkkk S3StorageService", e);
        }

        return "https://" + bucketName + ".s3.amazonaws.com/" + fileName;
    }


    @Override
    public void deleteFile(String fileKey){
        s3Client.deleteObject(
                DeleteObjectRequest.builder()
                        .bucket(bucketName)
                        .key(fileKey)
                        .build()


        );
    }


}
