package br.com.lasanhaspec.carservice.service;

import br.com.lasanhaspec.carservice.domain.enums.IssueSeverity;
import br.com.lasanhaspec.carservice.domain.models.ChronicIssue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ReliabilityScoreCalculatorTest {

    private ReliabilityScoreCalculator calculator;

    @BeforeEach
    void setup() {
         calculator = new ReliabilityScoreCalculator();


    }


    @Test
    void shouldReturnTenWhenIssuesIsNull(){
        Double result = calculator.calculate(null);
        assertEquals(10.0, result);
    }

    @Test
    void shouldReturnTenWhenIssuesIsEmpty() {
        Double result = calculator.calculate(List.of());
        assertEquals(10.0, result);
    }

    @Test
    void shouldCalculateScoreForOneCriticalIssue() {
        ChronicIssue issue = new ChronicIssue();
        issue.setSeverity(IssueSeverity.CRITICAL);

        Double result = calculator.calculate(List.of(issue));

        assertEquals(4.0, result);
    }


    @Test
    void shouldCalculateAverageScoreForMultipleIssues() {
        ChronicIssue low = new ChronicIssue();
        low.setSeverity(IssueSeverity.LOW);

        ChronicIssue high = new ChronicIssue();
        high.setSeverity(IssueSeverity.HIGH);

        ChronicIssue critical = new ChronicIssue();
        critical.setSeverity(IssueSeverity.CRITICAL);

        Double result = calculator.calculate(List.of(low, high, critical));

        assertEquals(6.67, result, 0.01);
    }

}
