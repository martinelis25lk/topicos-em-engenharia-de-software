package br.com.lasanhaspec.carservice.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommunitySetupDTO {

    private Long id;



    private String usage;
    private String engineStage;


    private Integer targetHorsePower;
    private Integer targetTorque;


    private String reliability;
    private String targetAspirationType;




    // Getter e Setter para id
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    // Getter e Setter para usage
    public String getUsage() {
        return usage;
    }

    public void setUsage(String usage) {
        this.usage = usage;
    }

    // Getter e Setter para engineStage
    public String getEngineStage() {
        return engineStage;
    }

    public void setEngineStage(String engineStage) {
        this.engineStage = engineStage;
    }

    // Getter e Setter para targetHorsePower
    public Integer getTargetHorsePower() {
        return targetHorsePower;
    }

    public void setTargetHorsePower(Integer targetHorsePower) {
        this.targetHorsePower = targetHorsePower;
    }

    // Getter e Setter para targetTorque
    public Integer getTargetTorque() {
        return targetTorque;
    }

    public void setTargetTorque(Integer targetTorque) {
        this.targetTorque = targetTorque;
    }

    // Getter e Setter para reliability
    public String getReliability() {
        return reliability;
    }

    public void setReliability(String reliability) {
        this.reliability = reliability;
    }

    // Getter e Setter para targetAspirationType
    public String getTargetAspirationType() {
        return targetAspirationType;
    }

    public void setTargetAspirationType(String targetAspirationType) {
        this.targetAspirationType = targetAspirationType;
    }
}
