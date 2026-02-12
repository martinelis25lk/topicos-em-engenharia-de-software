package br.com.lasanhaspec.carservice.dto;


import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class TechnicalSpecsDTO {


    //aba ficha tecnica

    private Integer factoryHorsepower;
    private Integer factoryTorque;
    private String aspirationType;




    // Getter e Setter para factoryHorsepower
    public Integer getFactoryHorsepower() {
        return factoryHorsepower;
    }

    public void setFactoryHorsepower(Integer factoryHorsepower) {
        this.factoryHorsepower = factoryHorsepower;
    }

    // Getter e Setter para factoryTorque
    public Integer getFactoryTorque() {
        return factoryTorque;
    }

    public void setFactoryTorque(Integer factoryTorque) {
        this.factoryTorque = factoryTorque;
    }

    // Getter e Setter para aspirationType
    public String getAspirationType() {
        return aspirationType;
    }

    public void setAspirationType(String aspirationType) {
        this.aspirationType = aspirationType;
    }
}
