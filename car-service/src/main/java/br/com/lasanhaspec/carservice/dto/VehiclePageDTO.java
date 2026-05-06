package br.com.lasanhaspec.carservice.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;




@Getter
@Setter
public class VehiclePageDTO {


    private VehicleSummaryDTO vehicle;
    private TechnicalSpecsDTO technicalSpecs;
    private List<CommunitySetupDTO> communitySetups;

    private String fipePrice;
    private String fipeReferenceMonth;
    private String fipeCode;


    public String getFipeReferenceMonth() {
        return fipeReferenceMonth;
    }

    public void setFipeReferenceMonth(String fipeReferenceMonth) {
        this.fipeReferenceMonth = fipeReferenceMonth;
    }

    public String getFipeCode() {
        return fipeCode;
    }

    public void setFipeCode(String fipeCode) {
        this.fipeCode = fipeCode;
    }

    public String getFipePrice() {
        return fipePrice;
    }

    public void setFipePrice(String fipePrice) {
        this.fipePrice = fipePrice;
    }



    public VehicleSummaryDTO getVehicle() {
        return vehicle;
    }

    public void setVehicle(VehicleSummaryDTO vehicle) {
        this.vehicle = vehicle;
    }

    public TechnicalSpecsDTO getTechnicalSpecs() {
        return technicalSpecs;
    }

    public void setTechnicalSpecs(TechnicalSpecsDTO technicalSpecs) {
        this.technicalSpecs = technicalSpecs;
    }

    public List<CommunitySetupDTO> getCommunitySetups() {
        return communitySetups;
    }

    public void setCommunitySetups(List<CommunitySetupDTO> communitySetups) {
        this.communitySetups = communitySetups;
    }




}

