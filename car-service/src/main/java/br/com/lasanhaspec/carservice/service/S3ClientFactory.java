package br.com.lasanhaspec.carservice.service;

import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;


public class S3ClientFactory {

    private static S3Client client;

    public static S3Client getClient() {

        if (client == null) {

            client = S3Client.builder()
                    .region(Region.US_EAST_2)
                    .build();
        }

        return client;
    }
}