package br.com.lasanhaspec.carservice.dto;

public class VehicleCardDTO {

    private Long id;
    private String name;
    private String imageUrl;

    private Integer factoryHorsePower;
    private Integer currentHorsePower;

    private Double powerGainPercentage;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Integer getFactoryHorsePower() {
        return factoryHorsePower;
    }

    public void setFactoryHorsePower(Integer factoryHorsePower) {
        this.factoryHorsePower = factoryHorsePower;
    }

    public Integer getCurrentHorsePower() {
        return currentHorsePower;
    }

    public void setCurrentHorsePower(Integer currentHorsePower) {
        this.currentHorsePower = currentHorsePower;
    }

    public Double getPowerGainPercentage() {
        return powerGainPercentage;
    }

    public void setPowerGainPercentage(Double powerGainPercentage) {
        this.powerGainPercentage = powerGainPercentage;
    }
}