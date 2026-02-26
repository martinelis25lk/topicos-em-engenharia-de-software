package br.com.lasanhaspec.carservice.domain.models;


import br.com.lasanhaspec.carservice.domain.enums.EngineStage;
import br.com.lasanhaspec.carservice.domain.enums.ReliabilityLevel;
import br.com.lasanhaspec.carservice.domain.enums.UsageType;
import jakarta.persistence.*;

import java.time.LocalDate;




@Entity
@Table(name = "current_setups")
public class CurrentSetup {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer version;

    private Boolean active;

    @ManyToOne(optional = false)
    private UserVehicle userVehicle;

    @ManyToOne
    private CommunitySetup basedOn;

    @Enumerated(EnumType.STRING)
    private EngineStage engineStage;

    @Enumerated(EnumType.STRING)
    private UsageType usageType;


    private Integer targetHorsePower;
    private Integer targetTorque;


    @Enumerated(EnumType.STRING)
    private ReliabilityLevel reliability;


    private Boolean forgedInternals;

    private LocalDate createdAt;

    @PrePersist
    void prePersist() {
        this.createdAt = LocalDate.now();
    }





}
