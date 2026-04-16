package br.com.lasanhaspec.carservice.dto;

import java.util.List;



//página do modelo com lista de crônicos
public class VehicleChronicPageDTO {

    private String name;
    private Integer year, documentedIssues;
    private String imgUrl;
    private double reliabilityScore, avgAnnualCost, criticalFailureRate;



    //name, year, documentedissues,reliabilityscore,avganualcost,critinalfailurerate


    private List<ChronicIssueCardDTO> issues;

    // Getter e Setter para name
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Getter e Setter para year
    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    // Getter e Setter para documentedIssues
    public Integer getDocumentedIssues() {
        return documentedIssues;
    }

    public void setDocumentedIssues(Integer documentedIssues) {
        this.documentedIssues = documentedIssues;
    }

    // Getter e Setter para imgUrl
    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    // Getter e Setter para reliabilityScore
    public double getReliabilityScore() {
        return reliabilityScore;
    }

    public void setReliabilityScore(double reliabilityScore) {
        this.reliabilityScore = reliabilityScore;
    }

    // Getter e Setter para avgAnnualCost
    public double getAvgAnnualCost() {
        return avgAnnualCost;
    }

    public void setAvgAnnualCost(double avgAnnualCost) {
        this.avgAnnualCost = avgAnnualCost;
    }

    // Getter e Setter para criticalFailureRate
    public double getCriticalFailureRate() {
        return criticalFailureRate;
    }

    public void setCriticalFailureRate(double criticalFailureRate) {
        this.criticalFailureRate = criticalFailureRate;
    }

    // Getter e Setter para issues
    public List<ChronicIssueCardDTO> getIssues() {
        return issues;
    }

    public void setIssues(List<ChronicIssueCardDTO> issues) {
        this.issues = issues;
    }
}