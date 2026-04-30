package br.com.lasanhaspec.carservice.dto;


import br.com.lasanhaspec.carservice.domain.enums.RepairComplexity;
import java.util.List;

//página completa do crônico com Community Reports

public class ChronicIssueDetailDTO {

    private ChronicIssueCardDTO chronicIssueCardDTO;
    private List<String> symptoms;
    private List<String> preventiveMaintenance;
    private String affectedEngines;
    private String affectedYears;
    private String repairComplexity;
    private List<OccurrenceReportDTO> occurrenceReports; // ← descomenta

    public ChronicIssueCardDTO getChronicIssueCardDTO() {
        return chronicIssueCardDTO;
    }
    public void setChronicIssueCardDTO(ChronicIssueCardDTO cardDTO) {
        this.chronicIssueCardDTO = cardDTO; // ← seta o campo!
    }

    public List<String> getSymptoms() { return symptoms; }
    public void setSymptoms(List<String> symptoms) {
        this.symptoms = symptoms; // ← seta o campo!
    }

    public List<String> getPreventiveMaintenance() { return preventiveMaintenance; }
    public void setPreventiveMaintenance(List<String> preventiveMaintenance) {
        this.preventiveMaintenance = preventiveMaintenance;
    }

    public String getAffectedEngines() { return affectedEngines; }
    public void setAffectedEngines(String affectedEngines) {
        this.affectedEngines = affectedEngines;
    }

    public String getAffectedYears() { return affectedYears; }
    public void setAffectedYears(String affectedYears) {
        this.affectedYears = affectedYears;
    }

    public String getRepairComplexity() { return repairComplexity; }
    public void setRepairComplexity(RepairComplexity repairComplexity) {
        this.repairComplexity = repairComplexity != null ? repairComplexity.name() : null;
    }

    public List<OccurrenceReportDTO> getOccurrenceReports() { return occurrenceReports; }
    public void setOccurrenceReports(List<OccurrenceReportDTO> occurrenceReports) {
        this.occurrenceReports = occurrenceReports;
    }



}

    //private List<OccurrenceReportDTO> occurrenceReports;


//


