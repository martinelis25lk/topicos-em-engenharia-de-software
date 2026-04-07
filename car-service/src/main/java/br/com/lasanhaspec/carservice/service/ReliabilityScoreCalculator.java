package br.com.lasanhaspec.carservice.service;


import br.com.lasanhaspec.carservice.domain.models.ChronicIssue;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Component
public class ReliabilityScoreCalculator {


    public Double calculate(List<ChronicIssue> issues){
        if(issues == null || issues.isEmpty()){
            return 10.0;
        }



        double totalPenalty = issues.stream()
                .mapToDouble(issue -> issue.getSeverity().getWeight())
                .sum();

        return Math.max(0, 10 - (totalPenalty / issues.size()));


    }







}
