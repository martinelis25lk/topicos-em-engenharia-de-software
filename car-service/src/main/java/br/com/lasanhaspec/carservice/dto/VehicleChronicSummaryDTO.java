package br.com.lasanhaspec.carservice.dto;

import java.util.List;

public class VehicleChronicSummaryDTO {

    private Long id;
    private String name;
    private String imageUrl;
    private String reliabilityScore;  // tem q ser injetado do service
    private Double avgAnnualCost;
    private Double criticalFailureRate;


    private List<String> topIssueNames;


}
