package br.com.lasanhaspec.carservice.service;


import br.com.lasanhaspec.carservice.domain.enums.IssueCategory;
import br.com.lasanhaspec.carservice.domain.enums.IssueSeverity;
import br.com.lasanhaspec.carservice.domain.enums.IssueStatus;
import br.com.lasanhaspec.carservice.domain.enums.RepairComplexity;
import br.com.lasanhaspec.carservice.domain.models.ChronicIssue;
import br.com.lasanhaspec.carservice.domain.models.VehicleCatalogModel;
import br.com.lasanhaspec.carservice.dto.ChronicIssueDTO;
import br.com.lasanhaspec.carservice.dto.ChronicIssueDetailDTO;
import br.com.lasanhaspec.carservice.dto.VehicleChronicPageDTO;
import br.com.lasanhaspec.carservice.dto.VehicleChronicSummaryDTO;
import br.com.lasanhaspec.carservice.repository.ChronicIssueRepository;
import br.com.lasanhaspec.carservice.repository.IssueOccurrenceRepository;
import br.com.lasanhaspec.carservice.repository.IssueVoteRepository;
import br.com.lasanhaspec.carservice.repository.VehicleCatalogRepository;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public List<VehicleChronicSummaryDTO> getAllModelsWithIssues(){


    }


    //pagina comppleta de um modelo com seus cronicos
    public VehicleChronicPageDTO getModelChronicPage(Long vehicleId){

    }


    // detalhe de um cronico em especifico
    public ChronicIssueDetailDTO getIssueDetail(Long issueId) {
    }



    //registrar ocorrencia
    public void reportOccurrence(Long issueId, Long vehicleId){

    }








//issuevote - esse problema é util ou relevante? qqr usuario pode votar

    //issueOccurrence - "eu tive esse problema no meu carro" usuario registra ua ocorrencia real
    // é o botão "i experienced this issue"




}
