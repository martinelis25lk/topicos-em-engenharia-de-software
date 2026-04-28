package br.com.lasanhaspec.carservice.dto;


public class CreateVehicleCatalogModelDTO {


    private  String brand;
    private String aspirationType;
    private Integer year;
    private  String engineCode;
    private Integer factoryHorsePower;
    private Integer factoryTorque ;
    private String model ;
    private Integer factoryWeight ;



    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public Integer getFactoryWeight() {
        return factoryWeight;
    }

    public void setFactoryWeight(Integer factoryWeight) {
        this.factoryWeight = factoryWeight;
    }


    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Integer getFactoryTorque() {
        return factoryTorque;
    }

    public void setFactoryTorque(Integer factoryTorque) {
        this.factoryTorque = factoryTorque;
    }

    public Integer getFactoryHorsePower() {
        return factoryHorsePower;
    }

    public void setFactoryHorsePower(Integer factoryHorsePower) {
        this.factoryHorsePower = factoryHorsePower;
    }

    public String getEngineCode() {
        return engineCode;
    }

    public void setEngineCode(String engineCode) {
        this.engineCode = engineCode;
    }


    public String getAspirationType() {
        return aspirationType;
    }

    public void setAspirationType(String aspirationType) {
        this.aspirationType = aspirationType;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }






}

