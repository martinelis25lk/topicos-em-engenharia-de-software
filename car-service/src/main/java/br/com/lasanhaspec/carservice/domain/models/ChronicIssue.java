package br.com.lasanhaspec.carservice.domain.models;



//representa um problema cronico em um modelo de carro
//ex: bomba de oleo, falha prematura no gol  G5 1.6
// guarda descrição, gravidade, faixa de km

// contadores agregados - usefulVotes, notUsefulVotes, occurrences

// representa o estado coletivo da comunidade sobre aquele problema


import br.com.lasanhaspec.carservice.domain.enums.IssueCategory;
import br.com.lasanhaspec.carservice.domain.enums.IssueSeverity;
import br.com.lasanhaspec.carservice.domain.enums.IssueStatus;
import br.com.lasanhaspec.carservice.domain.enums.RepairComplexity;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

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
    private Integer millageMax;

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

    //NOVOS ATRIBUTOS
    @Enumerated(EnumType.STRING)
    private RepairComplexity repairComplexity;

    @Enumerated(EnumType.STRING)
    private IssueCategory issueCategory;

    private String affectedEngines;

    private String affectedYears;

    @ElementCollection
    private List<String> symptoms;


    @ElementCollection
    private List<String> preventiveMaintenace;

    private Long createdByUserId; // quem criou



    @Enumerated(EnumType.STRING)

    private IssueStatus status;
    // PENDING, APPROVED, REJECTED


    private List<String> preventiveMaintenance;


    public IssueStatus getStatus() {
        return status;
    }

    public void setStatus(IssueStatus status) {
        this.status = status;
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

    public Integer getMillageMax() {
        return millageMax;
    }

    public void setMillageMax(Integer millageMax) {
        this.millageMax = millageMax;
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


    public RepairComplexity getRepairComplexity() { return repairComplexity;
    }

    public void setRepairComplexity(RepairComplexity repairComplexity) { this.repairComplexity = repairComplexity;
    }

    public IssueCategory getIssueCategory() { return issueCategory;
    }

    public void setIssueCategory(IssueCategory issueCategory) { this.issueCategory = issueCategory;
    }

    public String getAffectedEngines() { return affectedEngines;
    }

    public void setAffectedEngines(String affectedEngines) { this.affectedEngines = affectedEngines;
    }

    public String getAffectedYears() { return affectedYears;
    }

    public void setAffectedYears(String affectedYears) { this.affectedYears = affectedYears;
    }

    public List<String> getSymptoms() { return symptoms;
    }

    public void setSymptoms(List<String> symptoms) { this.symptoms = symptoms;
    }


    public void setPreventiveMaintenance(List<String> preventiveMaintenance) {
    }

    public void setCreatedByUserId(Long createdByUserId) {
    }
}
