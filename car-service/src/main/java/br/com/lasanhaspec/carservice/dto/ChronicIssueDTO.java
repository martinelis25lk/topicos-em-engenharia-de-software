package br.com.lasanhaspec.carservice.dto;

import java.util.List;

public class ChronicIssueDTO {

    private Long vehicleCatalogModelId;

    private String title, description;

    private String severity;

    private String category;

    private Integer millageMin, millageMax;

    private Integer costMin, costMax;

    private String affectedEngines;

    private String affectedYears;

    private String repairComplexity;

    private List<String> symptoms;

    private List<String> preventiveMaintenance;

    private Long createdByUserId;



    public Long getVehicleCatalogModelId() {
        return vehicleCatalogModelId;
    }

    public void setVehicleCatalogModelId(Long vehicleCatalogModelId) {
        this.vehicleCatalogModelId = vehicleCatalogModelId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSeverity() {
        return severity;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Integer getMillageMin() {
        return millageMin;
    }

    public void setMillageMin(Integer millageMin) {
        this.millageMin = millageMin;
    }

    public Integer getMillageMax() {
        return millageMax;
    }

    public void setMillageMax(Integer millageMax) {
        this.millageMax = millageMax;
    }

    public Integer getCostMin() {
        return costMin;
    }

    public void setCostMin(Integer costMin) {
        this.costMin = costMin;
    }

    public Integer getCostMax() {
        return costMax;
    }

    public void setCostMax(Integer costMax) {
        this.costMax = costMax;
    }

    public String getAffectedEngines() {
        return affectedEngines;
    }

    public void setAffectedEngines(String affectedEngines) {
        this.affectedEngines = affectedEngines;
    }

    public String getAffectedYears() {
        return affectedYears;
    }

    public void setAffectedYears(String affectedYears) {
        this.affectedYears = affectedYears;
    }

    public String getRepairComplexity() {
        return repairComplexity;
    }

    public void setRepairComplexity(String repairComplexity) {
        this.repairComplexity = repairComplexity;
    }

    public List<String> getSymptoms() {
        return symptoms;
    }

    public void setSymptoms(List<String> symptoms) {
        this.symptoms = symptoms;
    }

    public List<String> getPreventiveMaintenance() {
        return preventiveMaintenance;
    }

    public void setPreventiveMaintenance(List<String> preventiveMaintenance) {
        this.preventiveMaintenance = preventiveMaintenance;
    }

    public Long getCreatedByUserId() {
        return createdByUserId;
    }

    public void setCreatedByUserId(Long createdByUserId) {
        this.createdByUserId = createdByUserId;
    }




}
