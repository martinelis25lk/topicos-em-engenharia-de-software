package br.com.lasanhaspec.carservice.dto;


import br.com.lasanhaspec.carservice.domain.enums.RepairComplexity;

import java.util.List;

//página completa do crônico com Community Reports

public class ChronicIssueDetailDTO {
    private ChronicIssueCardDTO chronicIssueCardDTO;

    private List<String> symptoms, preventiveMaintenance;

    private String affectedEngines, affectedYears, repairComplexity;

    

    public void setChronicIssueCardDTO(ChronicIssueCardDTO cardDTO) {
    }

    public void setSymptoms(List<String> symptoms) {
    }

    public void setPreventiveMaintenance(List<String> preventiveMaintenance) {
    }

    public void setAffectedEngines(String affectedEngines) {
    }

    public void setAffectedYears(String affectedYears) {
    }

    public void setRepairComplexity(RepairComplexity repairComplexity) {
    }

    public void setOccurrenceReports(List<OccurrenceReportDTO> occurrenceDTOs) {
    }

    //private List<OccurrenceReportDTO> occurrenceReports;


//

}
