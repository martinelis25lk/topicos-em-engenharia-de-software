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
    private Integer millageAtOccurrence;

    private Integer repairCost;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private String description; // relato do usuario



    @PrePersist
    public void prePersist(){
        this.createdAt = LocalDateTime.now();
    }
}
