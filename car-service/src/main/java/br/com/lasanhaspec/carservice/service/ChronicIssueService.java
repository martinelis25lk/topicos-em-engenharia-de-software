package br.com.lasanhaspec.carservice.service;


import br.com.lasanhaspec.carservice.domain.enums.IssueCategory;
import br.com.lasanhaspec.carservice.domain.enums.IssueSeverity;
import br.com.lasanhaspec.carservice.domain.enums.IssueStatus;
import br.com.lasanhaspec.carservice.domain.enums.RepairComplexity;
import br.com.lasanhaspec.carservice.domain.models.*;
import br.com.lasanhaspec.carservice.dto.*;
import br.com.lasanhaspec.carservice.repository.ChronicIssueRepository;
import br.com.lasanhaspec.carservice.repository.IssueOccurrenceRepository;
import br.com.lasanhaspec.carservice.repository.UserVehicleRepository;
import br.com.lasanhaspec.carservice.repository.VehicleCatalogRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class ChronicIssueService {


    private final ChronicIssueRepository chronicIssueRepository;
    private final VehicleCatalogRepository vehicleCatalogRepository;
    private final IssueOccurrenceRepository issueOccurrenceRepository;
    private final ReliabilityScoreCalculator reliabilityScoreCalculator;
    private final UserVehicleRepository userVehicleRepository;



    public ChronicIssueService(
            ChronicIssueRepository chronicIssueRepository,
            VehicleCatalogRepository vehicleCatalogRepository,
            IssueOccurrenceRepository issueOccurrenceRepository,
            ReliabilityScoreCalculator reliabilityScoreCalculator,
            UserVehicleRepository userVehicleRepository

    ){
        this.chronicIssueRepository = chronicIssueRepository;
        this.vehicleCatalogRepository = vehicleCatalogRepository;
        this.issueOccurrenceRepository = issueOccurrenceRepository;
        this.reliabilityScoreCalculator = reliabilityScoreCalculator;
        this.userVehicleRepository = userVehicleRepository;

    }




    //criar um novco cronico
    public Long createIssue(ChronicIssueDTO chronicIssueDTO){

        VehicleCatalogModel model = vehicleCatalogRepository
                .findById(chronicIssueDTO.getVehicleCatalogModelId())
                .orElseThrow(() -> new RuntimeException("catalog vehicle not found"));


        //cria a entidade nova

        ChronicIssue chronic = new ChronicIssue();


        chronic.setVehicleCatalogModel(model);
        chronic.setTitle(chronicIssueDTO.getTitle());
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
                    .map(ChronicIssue::getTitle)
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

        ChronicIssue chronicIssue = chronicIssueRepository.findById(issueId)
                .orElseThrow(() -> new RuntimeException("chronic issue not found"));

        //  1. mapear para card
        ChronicIssueCardDTO cardDTO = new ChronicIssueCardDTO();

        cardDTO.setId(chronicIssue.getId());
        cardDTO.setNotUsefulVotes(chronicIssue.getNotUsefulVotes());
        cardDTO.setUsefulVotes(chronicIssue.getUsefulVotes());
        cardDTO.setOccurrences(chronicIssue.getOccurrences());
        cardDTO.setSeverity(chronicIssue.getSeverity().name());
        cardDTO.setTitle(chronicIssue.getTitle());
        cardDTO.setDescription(chronicIssue.getDescription());
        cardDTO.setMillageMax(chronicIssue.getMillageMax());
        cardDTO.setMillageMin(chronicIssue.getMillageMin());
        cardDTO.setCostMin(chronicIssue.getCostMin());
        cardDTO.setCostMax(chronicIssue.getCostMax());

        //  2. buscar occurrences
        List<IssueOcurrence> occurrences = issueOccurrenceRepository
                .findByChronicIssueId(issueId);

        //  3. mapear occurrences
        List<OccurrenceReportDTO> occurrenceDTOs = occurrences.stream()
                .map(occurrence -> {
                    OccurrenceReportDTO dto = new OccurrenceReportDTO();
                    dto.setMillageAtOccurrence(occurrence.getMillageAtOccurrence());
                    dto.setRepairCost(occurrence.getRepairCost());
                    dto.setDescription(occurrence.getDescription());
                    return dto;
                })
                .toList();

        //  4. montar DTO final
        ChronicIssueDetailDTO detailDTO = new ChronicIssueDetailDTO();

        detailDTO.setChronicIssueCardDTO(cardDTO);
        detailDTO.setSymptoms(chronicIssue.getSymptoms());
        detailDTO.setPreventiveMaintenance(chronicIssue.getPreventiveMaintenance());
        detailDTO.setAffectedEngines(chronicIssue.getAffectedEngines());
        detailDTO.setAffectedYears(chronicIssue.getAffectedYears());
        detailDTO.setRepairComplexity(chronicIssue.getRepairComplexity());

        // importante: você precisa ter esse campo no DTO
        detailDTO.setOccurrenceReports(occurrenceDTOs);

        return detailDTO;
    }




    //pagina completa de um modelo com seus cronicos
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
                    dto.setId(issue.getId());
                    dto.setTitle(issue.getTitle());
                    dto.setCostMax(issue.getCostMax());
                    dto.setCostMin(issue.getCostMin());
                    dto.setDescription(issue.getDescription());
                    dto.setSeverity(issue.getSeverity());
                    dto.setOccurrences(issue.getOccurrences());
                    dto.setMillageMax(issue.getMillageMax());
                    dto.setMillageMin(issue.getMillageMin());
                    dto.setUsefulVotes(issue.getUsefulVotes());
                    dto.setNotUsefulVotes(issue.getNotUsefulVotes());
                    return dto;
                }).toList();

        VehicleChronicPageDTO dto = new VehicleChronicPageDTO();

        dto.setName(model.getBrand() + " " + model.getModel()); // <- model, não issue
        dto.setYear(model.getYear());
        dto.setDocumentedIssues(issues.size()); // <- tamanho da lista de crônicos
        dto.setReliabilityScore(reliabilityScore);
        dto.setAvgAnnualCost(avgAnnualCost);
        dto.setCriticalFailureRate(criticalFailureRate);
        dto.setIssues(issueDTOs);




        return dto;



    }






    //registrar ocorrencia
    public void reportOccurrence(Long issueId, Long vehicleId){


        //busca o chronicissue
        ChronicIssue issue = chronicIssueRepository.findById(issueId).
                orElseThrow(()-> new RuntimeException("Issue not found"));


        //busca o uservehicle veiculo do usuario
        UserVehicle vehicle = userVehicleRepository.findById(vehicleId).
                orElseThrow(()-> new RuntimeException("vehicle not found"));


        //há uma diferença entre saber se dois ids tem o mesmo valor e se dois objetos long são a mesma instancia em memoria

        if(!vehicle.getVehicleCatalogModel().getId()
                .equals(issue.getVehicleCatalogModel().getId())){
            throw new RuntimeException("vehicle from uservehicle anf issue vehicle do not match");
        }


        //validando duplicidade
        if(issueOccurrenceRepository.existsByChronicIssueIdAndUserVehicleId(issueId,vehicleId)){
            throw new RuntimeException("occurence already registed to this vehicle");
        }




        IssueOcurrence occurrence = new IssueOcurrence();
        occurrence.setChronicIssue(issue);
        occurrence.setUserVehicle(vehicle);

        issueOccurrenceRepository.save(occurrence);

        // incrementa o contador de ocorrências no crônico
        issue.setOccurrences(issue.getOccurrences() + 1);
        chronicIssueRepository.save(issue);
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
