package br.com.lasanhaspec.carservice.dto;

import jakarta.validation.constraints.*;

public class CreateUserVehicleDTO {


    private Long userId;


    @NotNull
    @Positive
    private Long vehicleCatalogModelId;

    @NotBlank
    @Size(min = 3, max = 30)
    private String nickName;


    @Positive
    @NotNull
    private Integer currentHorsePower;


    @Positive
    @NotNull
    private Integer currentWeight;


    @Positive
    @NotNull
    private Integer currentTorque;



    public Integer getCurrentHorsePower(){
        return currentHorsePower;
    }

    public void setCurrentHorsePower(Integer currentHorsePowers){
        this.currentHorsePower = currentHorsePowers;
    }


    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getVehicleCatalogModelId() {
        return vehicleCatalogModelId;
    }

    public void setVehicleCatalogModelId(Long vehicleCatalogModelId) {
        this.vehicleCatalogModelId = vehicleCatalogModelId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public Integer getCurrentWeight() {
        return currentWeight;
    }

    public Integer getCurrentTorque() {
        return currentTorque;
    }
}
