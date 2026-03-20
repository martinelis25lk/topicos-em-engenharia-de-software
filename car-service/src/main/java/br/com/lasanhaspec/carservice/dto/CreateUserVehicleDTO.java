package br.com.lasanhaspec.carservice.dto;

public class CreateUserVehicleDTO {
    private Long userId;
    private Long vehicleCatalogModelId;
    private String nickName;
    private Integer currentHorsePower;
    private Integer currentWeight;
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
