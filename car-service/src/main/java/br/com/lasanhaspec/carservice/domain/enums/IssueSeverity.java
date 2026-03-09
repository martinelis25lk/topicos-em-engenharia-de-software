package br.com.lasanhaspec.carservice.domain.enums;


import software.amazon.awssdk.services.s3.endpoints.internal.Value;

public enum IssueSeverity {
    LOW(1),
    MEDIUM(2),
    HIGH(3),
    CRITICAL(4);


    private final int weight;


    IssueSeverity(int weight){
        this.weight = weight;
    }



    public int getWeight(){
        return weight;
    }
}
