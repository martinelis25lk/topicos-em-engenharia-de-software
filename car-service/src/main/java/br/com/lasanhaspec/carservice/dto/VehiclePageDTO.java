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

