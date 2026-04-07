package br.com.lasanhaspec.carservice.dto;


import java.util.List;

public class ChronicIssueDetailDTO {
    private ChronicIssueCardDTO chronicIssueCardDTO;

    private List<String> symptoms, preventiveMaintenance;

    private String affectedEngines, affectedYears, repairComplexity;

    private List<OccurrenceReportDTO> occurrenceReports;


}
