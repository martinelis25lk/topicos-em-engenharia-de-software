package br.com.lasanhaspec.carservice.dto;


import jakarta.validation.constraints.*;

public class CreateVehicleCatalogModelDTO {


    @NotBlank
    @Size(min = 2, max = 40)
    private String brand;

    @NotBlank
    @Size(min = 3, max = 40)
    private String aspirationType;

    @NotNull
    @Min(1886)
    @Max(2035)
    private Integer year;

    @NotBlank
    @Size(min = 1, max = 40)
    private String engineCode;

    @NotNull
    @Positive
    private Integer factoryHorsePower;

    @NotNull
    @Positive
    private Integer factoryTorque;

    @NotBlank
    @Size(min = 1, max = 60)
    private String model;

    @NotNull
    @Positive
    private Integer factoryWeight;


    // campos para correspondência com a FIPE
    @Size(max = 20)
    private String fipeBrandCode;   // ex: "26" (BMW)

    @Size(max = 20)
    private String fipeModelCode;   // ex: "5571" (325i)

    @Size(max = 20)
    private String fipeYearCode;    // ex: "1995-1"



    public String getFipeBrandCode() {
        return fipeBrandCode;
    }

    public void setFipeBrandCode(String fipeBrandCode) {
        this.fipeBrandCode = fipeBrandCode;
    }

    public String getFipeModelCode() {
        return fipeModelCode;
    }

    public void setFipeModelCode(String fipeModelCode) {
        this.fipeModelCode = fipeModelCode;
    }

    public String getFipeYearCode() {
        return fipeYearCode;
    }

    public void setFipeYearCode(String fipeYearCode) {
        this.fipeYearCode = fipeYearCode;
    }




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

