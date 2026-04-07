package br.com.lasanhaspec.carservice.dto;


import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class TechnicalSpecsDTO {


    //aba ficha tecnica

    private Integer factoryHorsepower;
    private Integer factoryTorque;
    private String aspirationType;


    //atributos novos
    private Integer cylinderCount;
    private Double topSpeed;
    private String fuelType;
    private String transmissionType;
    private Integer gearCount;




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



    public Integer getCylinderCount() {return cylinderCount;
    }

    public void setCylinderCount(Integer cylinderCount) {this.cylinderCount = cylinderCount;
    }

    public double getTopSpeed() {return topSpeed;
    }

    public void setTopSpeed(Double topSpeed) {this.topSpeed = topSpeed;
    }

    public String getFuelType() {return fuelType;
    }

    public void setFuelType(String fuelType) {this.fuelType = fuelType;
    }

    public String getTransmissionType() {return transmissionType;
    }

    public void setTransmissionType(String transmissionType) {this.transmissionType = transmissionType;
    }

    public Integer getGearCount() {return gearCount;
    }

    public void setGearCount(Integer gearCount) {this.gearCount = gearCount;
    }


}
