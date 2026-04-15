package br.com.lasanhaspec.carservice.service;


import br.com.lasanhaspec.carservice.domain.enums.IssueCategory;
import br.com.lasanhaspec.carservice.domain.enums.IssueSeverity;
import br.com.lasanhaspec.carservice.domain.enums.IssueStatus;
import br.com.lasanhaspec.carservice.domain.enums.RepairComplexity;
import br.com.lasanhaspec.carservice.domain.models.ChronicIssue;
import br.com.lasanhaspec.carservice.domain.models.VehicleCatalogModel;
import br.com.lasanhaspec.carservice.dto.*;
import br.com.lasanhaspec.carservice.repository.ChronicIssueRepository;
import br.com.lasanhaspec.carservice.repository.IssueOccurrenceRepository;
import br.com.lasanhaspec.carservice.repository.IssueVoteRepository;
import br.com.lasanhaspec.carservice.repository.VehicleCatalogRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

import static java.util.stream.Collectors.toList;

@Service
public class ChronicIssueService {


    private final ChronicIssueRepository chronicIssueRepository;
    private final VehicleCatalogRepository vehicleCatalogRepository;
    private final IssueOccurrenceRepository issueOccurrenceRepository;
    private final ReliabilityScoreCalculator reliabilityScoreCalculator;



    public ChronicIssueService(
            ChronicIssueRepository chronicIssueRepository,
            VehicleCatalogRepository vehicleCatalogRepository,
            IssueOccurrenceRepository issueOccurrenceRepository,
            ReliabilityScoreCalculator reliabilityScoreCalculator
    ){
        this.chronicIssueRepository = chronicIssueRepository;
        this.vehicleCatalogRepository = vehicleCatalogRepository;
        this.issueOccurrenceRepository = issueOccurrenceRepository;
        this.reliabilityScoreCalculator = reliabilityScoreCalculator;

    }




    //criar um novco cronico
    public  Long createIssue(ChronicIssueDTO chronicIssueDTO){

        VehicleCatalogModel model = vehicleCatalogRepository
                .findById(chronicIssueDTO.getVehicleCatalogModelId())
                .orElseThrow(() -> new RuntimeException("catalog vehicle not found"));


        //cria a entidade nova

        ChronicIssue chronic = new ChronicIssue();
        chronic.setVehicleCatalogModel(model);
        chronic.setTittle(chronicIssueDTO.getTittle());
        chronic.setDescription(chronicIssueDTO.getDescription());
        chronic.setSeverity(IssueSeverity.valueOf(chronicIssueDTO.getSeverity()));
        chronic.setStatus(IssueStatus.PENDING);

        chronic.setIssueCategory(IssueCategory.valueOf(chronicIssueDTO.getCategory()));


        chronic.setMillageMax(chronicIssueDTO.getMillageMax());
        chronic.setMillageMin(chronicIssueDTO.getMillageMin());
        chronic.setCostMax(chronicIssueDTO.getCostMax());
        chronic.setCostMin(chronicIssueDTO.getCostMin());
        chronic.setAffectedEngines(chronicIssueDTO.getAffectedEngines());
        chronic.setAffectedYears(chronicIssueDTO.getAffectedYears());

        chronic.setRepairComplexity(RepairComplexity.valueOf(chronicIssueDTO.getRepairComplexity()));

        chronic.setSymptoms(chronicIssueDTO.getSymptoms());
        chronic.setPreventiveMaintenance(chronicIssueDTO.getPreventiveMaintenance());

        chronic.setCreatedByUserId(chronicIssueDTO.getCreatedByUserId());
        // 3. salva e retorna o id
        return chronicIssueRepository.save(chronic).getId();


    }






    //lista todos os modelos q tem cronicos approvados
    public List<VehicleChronicSummaryDTO> getAllModelsWithIssues() {

        List<VehicleCatalogModel> models =
                vehicleCatalogRepository.findModelsWithIssuesByStatus(IssueStatus.APPROVED);

        return models.stream().map(model -> {

            List<ChronicIssue> issues =
                    chronicIssueRepository.findByVehicleCatalogModelIdAndStatus(
                            model.getId(),
                            IssueStatus.APPROVED
                    );

            Double reliabilityScore = reliabilityScoreCalculator.calculate(issues);
            Double criticalFailureRate = calculateCriticalFailureRate(issues);
            Double avgAnnualCost = calculateAvgAnualCost(issues);

            List<String> topIssueNames = issues.stream()
                    .sorted((a, b) -> Integer.compare(b.getUsefulVotes(), a.getUsefulVotes()))
                    .limit(3)
                    .map(ChronicIssue::getTittle)
                    .toList();

            VehicleChronicSummaryDTO dto = new VehicleChronicSummaryDTO();
            dto.setId(model.getId());
            dto.setName(model.getBrand() + " " + model.getModel());
            dto.setImageUrl(null);
            dto.setReliabilityScore(reliabilityScore);
            dto.setAvgAnnualCost(avgAnnualCost);
            dto.setCriticalFailureRate(criticalFailureRate);
            dto.setTopIssueNames(topIssueNames);

            return dto;

        }).toList();
    }





    // detalhe de um cronico em especifico
    public ChronicIssueDetailDTO getIssueDetail(Long issueId) {
    }





    //pagina comppleta de um modelo com seus cronicos
    public VehicleChronicPageDTO getModelChronicPage(Long vehicleId){

        // busca o modelo
        // busca os cronicos aprovados
        // calcula a metrica e monta o dto



        //1 - busca o modelo
        VehicleCatalogModel model = vehicleCatalogRepository
                .findById(vehicleId)
                .orElseThrow(() -> new RuntimeException("model from vehicle not found"));


        //2 - busca os cronicos aprovados desse modelo

        List<ChronicIssue> issues =
                chronicIssueRepository.findByVehicleCatalogModelIdAndStatus(vehicleId,IssueStatus.APPROVED);

        //3 - calcula as métricas


        Double reliabilityScore = reliabilityScoreCalculator.calculate(issues);
        Double criticalFailureRate = calculateCriticalFailureRate(issues);
        Double avgAnnualCost = calculateAvgAnualCost(issues);


        //4 - mapeia cada ChronicIssue para ChronicIssueCardDTO

        List<ChronicIssueCardDTO> issueDTOs = issues.stream()
                .map(issue -> {
                    ChronicIssueCardDTO dto = new ChronicIssueCardDTO();
                    dto.setTittle(issue.getTittle());
                    //popular o resto

                    return dto;
                }).toList();

        VehicleChronicPageDTO dto = new VehicleChronicPageDTO();

        dto.setName();
        dto.setYear();
        dto.setDocumentedIssues();
        //popular o resto



        return dto;



    }






    //registrar ocorrencia
    public void reportOccurrence(Long issueId, Long vehicleId){

    }



    private Double calculateCriticalFailureRate(List<ChronicIssue> issues){


        if(issues == null || issues.isEmpty()) {
            return 0.0;
        }

        long criticalCount = issues.stream()
                .filter(issue -> issue.getSeverity() == IssueSeverity.CRITICAL)
                .count();


        return (criticalCount * 100.0)/ issues.size();



    }


    private Double calculateAvgAnualCost(List<ChronicIssue> issues){

        if(issues == null || issues.isEmpty()){
            return 0.0;

        }

        return  issues.stream()
                .map(ChronicIssue::getCostMax)
                .filter(Objects::nonNull)
                .mapToDouble(Integer::doubleValue)
                .average()
                .orElse(0.0);

    }






    // 1 - contar total de issues
    // 2 - contar quantos tem severity critical
    // 3 - fazer a divisão e multiplicar por 100










//issuevote - esse problema é util ou relevante? qqr usuario pode votar

    //issueOccurrence - "eu tive esse problema no meu carro" usuario registra ua ocorrencia real
    // é o botão "i experienced this issue"




}
