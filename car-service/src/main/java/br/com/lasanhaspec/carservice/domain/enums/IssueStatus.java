package br.com.lasanhaspec.carservice.domain.enums;

public enum IssueStatus {


    PENDING,    // aguardando votos da comunidade
    IN_REVIEW,  // com votos suficientes, aguardando admin
    APPROVED,   // aprovado pelo admin
    REJECTED    // rejeitado

}
