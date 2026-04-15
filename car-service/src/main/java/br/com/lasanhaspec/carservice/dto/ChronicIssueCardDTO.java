package br.com.lasanhaspec.carservice.dto;

public class ChronicIssueCardDTO {

    private Integer id, occurrences, usefulVotes, notUsefulVotes;
    private String tittle;
    private Integer millageMin, millageMax, costMin, costMax;
    private String description, severity;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOccurrences() {
        return occurrences;
    }

    public void setOccurrences(Integer occurrences) {
        this.occurrences = occurrences;
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

    public String getTittle() {
        return tittle;
    }

    public void setTittle(String tittle) {
        this.tittle = tittle;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSeverity() {
        return severity;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }
}