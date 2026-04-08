package br.com.lasanhaspec.carservice.dto;

import java.util.List;

public class VehicleChronicSummaryDTO {

    private Long id;
    private String name;
    private String imageUrl;
    private Double reliabilityScore;
    private Double avgAnnualCost;
    private Double criticalFailureRate;

    private List<String> topIssueNames;

    // Getter e Setter para id
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    // Getter e Setter para name
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Getter e Setter para imageUrl
    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    // Getter e Setter para reliabilityScore
    public Double getReliabilityScore() {
        return reliabilityScore;
    }

    public void setReliabilityScore(Double reliabilityScore) {
        this.reliabilityScore = reliabilityScore;
    }

    // Getter e Setter para avgAnnualCost
    public Double getAvgAnnualCost() {
        return avgAnnualCost;
    }

    public void setAvgAnnualCost(Double avgAnnualCost) {
        this.avgAnnualCost = avgAnnualCost;
    }

    // Getter e Setter para criticalFailureRate
    public Double getCriticalFailureRate() {
        return criticalFailureRate;
    }

    public void setCriticalFailureRate(Double criticalFailureRate) {
        this.criticalFailureRate = criticalFailureRate;
    }

    // Getter e Setter para topIssueNames
    public List<String> getTopIssueNames() {
        return topIssueNames;
    }

    public void setTopIssueNames(List<String> topIssueNames) {
        this.topIssueNames = topIssueNames;
    }
}