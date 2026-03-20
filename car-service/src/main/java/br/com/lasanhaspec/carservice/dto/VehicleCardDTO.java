package br.com.lasanhaspec.carservice.dto;

public class VehicleCardDTO {

    private Long id;
    private String name;
    private String imageUrl;

    private Integer factoryHorsePower;
    private Integer currentHorsePower;
//
    private Integer factoryTorque;
    private Integer currentTorque;
    private String engine;
    private Integer currentWeight;
    private Integer modificationsCount; // ou quantidade de upgrades

    private Integer horsepowerDiff;
    private Integer torqueDiff;
    private Integer weightDiff;

    private String weightTrend;
    private String horsepowerTrend;
    private String torqueTrend;

    public Integer getFactoryWeight() {
        return factoryWeight;
    }

    public void setFactoryWeight(Integer factoryWeight) {
        this.factoryWeight = factoryWeight;
    }

    private Integer factoryWeight;
    //
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

    public Integer getFactoryTorque() { return factoryTorque; }
    public void setFactoryTorque(Integer factoryTorque) { this.factoryTorque = factoryTorque; }

    public Integer getCurrentTorque() { return currentTorque; }
    public void setCurrentTorque(Integer currentTorque) { this.currentTorque = currentTorque; }

    public String getEngine() { return engine; }
    public void setEngine(String engine) { this.engine = engine; }

    public Integer getModificationsCount() { return modificationsCount; }
    public void setModificationsCount(Integer modificationsCount) { this.modificationsCount = modificationsCount; }


    public Integer getCurrentWeight() {
        return currentWeight  ;
    }

    public void setCurrentWeight(Integer currentWeight) {
        this.currentWeight = currentWeight;
    }


    public Integer getHorsepowerDiff() {
        return horsepowerDiff;
    }

    public void setHorsepowerDiff(Integer horsepowerDiff) {
        this.horsepowerDiff = horsepowerDiff;
    }

    public Integer getTorqueDiff() {
        return torqueDiff;
    }

    public void setTorqueDiff(Integer torqueDiff) {
        this.torqueDiff = torqueDiff;
    }

    public Integer getWeightDiff() {
        return weightDiff;
    }

    public void setWeightDiff(Integer weightDiff) {
        this.weightDiff = weightDiff;
    }


    public String getWeightTrend() {
        return weightTrend;
    }

    public void setWeightTrend(String weightTrend) {
        this.weightTrend = weightTrend;
    }

    public String getHorsepowerTrend() {
        return horsepowerTrend;
    }

    public void setHorsepowerTrend(String horsepowerTrend) {
        this.horsepowerTrend = horsepowerTrend;
    }

    public String getTorqueTrend() {
        return torqueTrend;
    }

    public void setTorqueTrend(String torqueTrend) {
        this.torqueTrend = torqueTrend;
    }








}