package br.com.lasanhaspec.carservice.dto;

import java.time.LocalDateTime;

public class OccurrenceReportDTO {

    private String username;
    private Double repairCost;
    private Double millageAtOccurrence;
    private String description;
    private Long userVehicleId;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Double getRepairCost() {
        return repairCost;
    }

    public void setRepairCost(Double repairCost) {
        this.repairCost = repairCost;
    }

    public Double getMillageAtOccurrence() {
        return millageAtOccurrence;
    }

    public void setMillageAtOccurrence(Double millageAtOccurrence) {
        this.millageAtOccurrence = millageAtOccurrence;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setUserVehicleId(Long userVehicleId) { this.userVehicleId = userVehicleId;
    }
}