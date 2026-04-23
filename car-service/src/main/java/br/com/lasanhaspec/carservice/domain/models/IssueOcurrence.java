package br.com.lasanhaspec.carservice.domain.models;

// é o registro q um usuário teve esse problema no proprio carro
// representa um fato real

//votar q algo é util n significa q vc passa de fato por aquele problema


//obs permitir apenas uma ocorrencia por problema
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "issue_occurrences",
        uniqueConstraints = @UniqueConstraint(columnNames = {"issue_id", "user_vehicle_id"})
)
public class IssueOcurrence {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_vehicle_id")
    private UserVehicle userVehicle;

    @Column(nullable = false)
    private Double millageAtOccurrence;

    private Double repairCost;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @ManyToOne(optional = false)
    @JoinColumn(name = "issue_id")
    private ChronicIssue chronicIssue;

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserVehicle getUserVehicle() {
        return userVehicle;
    }

    public void setUserVehicle(UserVehicle userVehicle) {
        this.userVehicle = userVehicle;
    }

    public Double getMillageAtOccurrence() {
        return millageAtOccurrence;
    }

    public void setMillageAtOccurrence(Double millageAtOccurrence) {
        this.millageAtOccurrence = millageAtOccurrence;
    }

    public Double getRepairCost() {
        return repairCost;
    }

    public void setRepairCost(Double repairCost) {
        this.repairCost = repairCost;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    private String description; // relato do usuario



    @PrePersist
    public void prePersist(){
        this.createdAt = LocalDateTime.now();
    }

    public void setChronicIssue(ChronicIssue chronicIssue) {
        this.chronicIssue = chronicIssue;

    }
}
