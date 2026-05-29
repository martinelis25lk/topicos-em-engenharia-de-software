package br.com.lasanhaspec.carservice.service;


import br.com.lasanhaspec.carservice.exception.ResourceNotFoundException;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import br.com.lasanhaspec.carservice.domain.models.ChronicIssue;
import br.com.lasanhaspec.carservice.domain.models.UserVehicle;
import br.com.lasanhaspec.carservice.domain.models.VehicleCatalogModel;
import br.com.lasanhaspec.carservice.dto.OccurrenceReportDTO;
import br.com.lasanhaspec.carservice.dto.ReportOccurrenceRequestDTO;
import br.com.lasanhaspec.carservice.exception.BusinessException;
import br.com.lasanhaspec.carservice.repository.ChronicIssueRepository;
import br.com.lasanhaspec.carservice.repository.IssueOccurrenceRepository;
import br.com.lasanhaspec.carservice.repository.UserVehicleRepository;
import org.junit.jupiter.api.Test;
import java.util.Optional;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class IssueOccurrenceServiceTest {



    @Mock
    private ChronicIssueRepository chronicIssueRepository;

    @Mock
    private UserVehicleRepository userVehicleRepository;

    @Mock
    private IssueOccurrenceRepository issueOccurrenceRepository;

    @InjectMocks
    private ChronicIssueService chronicIssueService;

    @Test
    void shouldThrowWhenOccurrenceAlreadyExistsForSameIssueAndVehicle() {
        Long issueId = 1L;
        Long userVehicleId = 10L;

        VehicleCatalogModel model = new VehicleCatalogModel();
        model.setId(100L);

        ChronicIssue issue = new ChronicIssue();
        issue.setId(issueId);
        issue.setVehicleCatalogModel(model);

        UserVehicle userVehicle = new UserVehicle();
        userVehicle.setId(userVehicleId);
        userVehicle.setVehicleCatalogModel(model);

        ReportOccurrenceRequestDTO dto = new ReportOccurrenceRequestDTO();
        dto.setVehicleId(userVehicleId);
        dto.setMillageAtOccurrence(120000.0);
        dto.setRepairCost(1500.0);
        dto.setDescription("Problema ocorreu novamente");

        when(chronicIssueRepository.findById(issueId))
                .thenReturn(Optional.of(issue));

        when(userVehicleRepository.findById(userVehicleId))
                .thenReturn(Optional.of(userVehicle));

        when(issueOccurrenceRepository.existsByChronicIssueIdAndUserVehicleId(issueId, userVehicleId))
                .thenReturn(true);

        assertThrows(
                BusinessException.class,
                () -> chronicIssueService.reportOccurrence(issueId, dto)
        );

        verify(issueOccurrenceRepository, never()).save(any());
        verify(chronicIssueRepository, never()).save(any());
    }

    @Test
    void shouldThrowWhenIssueDoesNotExist() {

        Long issueId = 999L;

        ReportOccurrenceRequestDTO dto = new ReportOccurrenceRequestDTO();
        dto.setVehicleId(10L);

        when(chronicIssueRepository.findById(issueId))
                .thenReturn(Optional.empty());

        assertThrows(
                ResourceNotFoundException.class,
                () -> chronicIssueService.reportOccurrence(issueId, dto)
        );

        verify(issueOccurrenceRepository, never()).save(any());
    }

    @Test
    void shouldThrowWhenVehicleDoesNotExist() {

        Long issueId = 1L;
        Long vehicleId = 10L;

        ChronicIssue issue = new ChronicIssue();

        ReportOccurrenceRequestDTO dto = new ReportOccurrenceRequestDTO();
        dto.setVehicleId(vehicleId);

        when(chronicIssueRepository.findById(issueId))
                .thenReturn(Optional.of(issue));

        when(userVehicleRepository.findById(vehicleId))
                .thenReturn(Optional.empty());

        assertThrows(
                ResourceNotFoundException.class,
                () -> chronicIssueService.reportOccurrence(issueId, dto)
        );

        verify(issueOccurrenceRepository, never()).save(any());
    }

    @Test
    void shouldThrowWhenVehicleModelDoesNotMatchIssueModel() {

        VehicleCatalogModel bmwModel = new VehicleCatalogModel();
        bmwModel.setId(1L);

        VehicleCatalogModel golModel = new VehicleCatalogModel();
        golModel.setId(2L);

        ChronicIssue issue = new ChronicIssue();
        issue.setVehicleCatalogModel(bmwModel);

        UserVehicle vehicle = new UserVehicle();
        vehicle.setVehicleCatalogModel(golModel);

        ReportOccurrenceRequestDTO dto = new ReportOccurrenceRequestDTO();
        dto.setVehicleId(10L);

        when(chronicIssueRepository.findById(1L))
                .thenReturn(Optional.of(issue));

        when(userVehicleRepository.findById(10L))
                .thenReturn(Optional.of(vehicle));

        assertThrows(
                BusinessException.class,
                () -> chronicIssueService.reportOccurrence(1L, dto)
        );

        verify(issueOccurrenceRepository, never()).save(any());
    }



    @Test
    void shouldCreateOccurrenceSuccessfully() {

        Long issueId = 1L;
        Long vehicleId = 10L;

        VehicleCatalogModel model = new VehicleCatalogModel();
        model.setId(100L);

        ChronicIssue issue = new ChronicIssue();
        issue.setId(issueId);
        issue.setVehicleCatalogModel(model);
        issue.setOccurrences(0);

        UserVehicle vehicle = new UserVehicle();
        vehicle.setId(vehicleId);
        vehicle.setVehicleCatalogModel(model);

        ReportOccurrenceRequestDTO dto = new ReportOccurrenceRequestDTO();
        dto.setVehicleId(vehicleId);
        dto.setMillageAtOccurrence(120000.0);
        dto.setRepairCost(1500.0);
        dto.setDescription("Problema ocorreu novamente");

        when(chronicIssueRepository.findById(issueId))
                .thenReturn(Optional.of(issue));

        when(userVehicleRepository.findById(vehicleId))
                .thenReturn(Optional.of(vehicle));

        when(issueOccurrenceRepository.existsByChronicIssueIdAndUserVehicleId(issueId, vehicleId))
                .thenReturn(false);

        chronicIssueService.reportOccurrence(issueId, dto);

        assertEquals(1, issue.getOccurrences());

        verify(issueOccurrenceRepository).save(any());
        verify(chronicIssueRepository).save(issue);
    }
}
