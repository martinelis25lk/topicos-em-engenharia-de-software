package br.com.lasanhaspec.carservice.domain.models;



//representa um problema cronico em um modelo de carro
//ex: bomba de oleo, falha prematura no gol  G5 1.6
// guarda descrição, gravidade, faixa de km

// contadores agregados - usefulVotes, notUsefulVotes, occurrences

// representa o estado coletivo da comunidade sobre aquele problema


import br.com.lasanhaspec.carservice.domain.enums.IssueSeverity;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity(name="Chronic_Issues")
public class ChronicIssue {



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;


    @ManyToOne(optional = false)
    @JoinColumn(name = "issue_id")
    private VehicleCatalogModel vehicleCatalogModel;

    @Column(nullable = false)
    private String tittle;


    @Column(length = 2000, nullable = false)
    private String description;


    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private IssueSeverity severity;

    private Integer millageMin;
    private Integer millageMx;

    private  Integer costMin;
    private Integer costMax;


    @Column(nullable = false)
    private Integer usefulVotes = 0;

    @Column(nullable = false)
    private Integer notUsefulVotes = 0;

    @Column(nullable = false)
    private Integer occurrences = 0;

    @Column(nullable = false)
    private boolean approved = false;

    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist(){
        this.createdAt = LocalDateTime.now();
    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public VehicleCatalogModel getVehicleCatalogModel() {
        return vehicleCatalogModel;
    }

    public void setVehicleCatalogModel(VehicleCatalogModel vehicleCatalogModel) {
        this.vehicleCatalogModel = vehicleCatalogModel;
    }

    public String getTittle() {
        return tittle;
    }

    public void setTittle(String tittle) {
        this.tittle = tittle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public IssueSeverity getSeverity() {
        return severity;
    }

    public void setSeverity(IssueSeverity severity) {
        this.severity = severity;
    }

    public Integer getMillageMin() {
        return millageMin;
    }

    public void setMillageMin(Integer millageMin) {
        this.millageMin = millageMin;
    }

    public Integer getMillageMx() {
        return millageMx;
    }

    public void setMillageMx(Integer millageMx) {
        this.millageMx = millageMx;
    }

    public Integer getCostMin() {
        return costMin;
    }

    public void setCostMin(Integer costMin) {
        this.costMin = costMin;
    }

    public Integer getCostMax() {
        return costMax;
    }

    public void setCostMax(Integer costMax) {
        this.costMax = costMax;
    }

    public Integer getUsefulVotes() {
        return usefulVotes;
    }

    public void setUsefulVotes(Integer usefulVotes) {
        this.usefulVotes = usefulVotes;
    }

    public Integer getNotUsefulVotes() {
        return notUsefulVotes;
    }

    public void setNotUsefulVotes(Integer notUsefulVotes) {
        this.notUsefulVotes = notUsefulVotes;
    }

    public Integer getOccurrences() {
        return occurrences;
    }

    public void setOccurrences(Integer occurrences) {
        this.occurrences = occurrences;
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }


}
