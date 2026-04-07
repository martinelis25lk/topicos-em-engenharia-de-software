package br.com.lasanhaspec.carservice.dto;

import java.util.List;

public class VehicleChronicPageDTO {

    private String name;
    private Integer year, documentedIssues;
    private String imgUrl;
    private  double reliabilityScore, avgAnnualCost, criticalFailureRate;

    private List<ChronicIssueCardDTO>  issues;


}
