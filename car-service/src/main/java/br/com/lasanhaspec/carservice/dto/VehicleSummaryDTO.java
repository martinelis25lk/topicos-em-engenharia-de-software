package br.com.lasanhaspec.carservice.dto;


//cabecalho da pagina


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VehicleSummaryDTO {

    private Long id;
    private String brand;
    private String model;
    private Integer year;



    private String engineCode;
    private String aspirationType;




    // Getter e Setter para id
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    // Getter e Setter para brand
    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    // Getter e Setter para model
    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    // Getter e Setter para year
    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    // Getter e Setter para engineCode
    public String getEngineCode() {
        return engineCode;
    }

    public void setEngineCode(String engineCode) {
        this.engineCode = engineCode;
    }

    // Getter e Setter para aspirationType
    public String getAspirationType() {
        return aspirationType;
    }

    public void setAspirationType(String aspirationType) {
        this.aspirationType = aspirationType;
    }
}
