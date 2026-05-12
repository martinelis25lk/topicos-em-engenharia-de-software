package br.com.lasanhaspec.carservice.dto;

import java.math.BigDecimal;

public class ReportOccurrenceRequestDTO {

    private Long vehicleId;
    private Double millageAtOccurrence;
    private Double repairCost;
    private String description;


    public Long getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(Long vehicleId) {
        this.vehicleId = vehicleId;
    }

    public Double getMillageAtOccurrence() {
        return millageAtOccurrence;
    }

    public void setMillageAtOccurrence(Double millageAtOccurrence) {
        this.millageAtOccurrence = millageAtOccurrence;
    }

    public Double getRepairCost() {
        return repairCost;
    }

    public void setRepairCost(Double repairCost) {
        this.repairCost = repairCost;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
