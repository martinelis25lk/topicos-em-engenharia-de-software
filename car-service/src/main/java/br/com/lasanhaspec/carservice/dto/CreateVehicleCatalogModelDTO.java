package br.com.lasanhaspec.carservice.dto;


import br.com.lasanhaspec.carservice.domain.enums.AspirationType;

public class CreateVehicleCatalogModelDTO {


    private  String brand;
    private  String aspirationtype;
    private  Integer engineCode;
    private Integer factoryHorsePower;
    private Integer factoryTorque ;
    private String model ;
    private String year ;
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

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
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

    public void setEngineCode(Integer engineCode) {
        this.engineCode = engineCode;
    }

    public AspirationType getAspirationtype() {
        return aspirationtype;
    }

    public void setAspirationtype(String aspirationtype) {
        this.aspirationtype = aspirationtype;
    }



}

