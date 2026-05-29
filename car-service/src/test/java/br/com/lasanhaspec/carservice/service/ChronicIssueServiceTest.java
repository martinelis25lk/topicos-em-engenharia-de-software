package br.com.lasanhaspec.carservice.service;

import br.com.lasanhaspec.carservice.domain.models.User;
import br.com.lasanhaspec.carservice.domain.models.VehicleCatalogModel;
import br.com.lasanhaspec.carservice.dto.ChronicIssueDTO;
import br.com.lasanhaspec.carservice.exception.ResourceNotFoundException;
import br.com.lasanhaspec.carservice.repository.ChronicIssueRepository;
import br.com.lasanhaspec.carservice.repository.UserRepository;
import br.com.lasanhaspec.carservice.repository.VehicleCatalogRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class ChronicIssueServiceTest {


    @Mock
    private ChronicIssueRepository chronicIssueRepository;

    @Mock
    private VehicleCatalogRepository vehicleCatalogModelRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private ChronicIssueService chronicIssueService;



    @Test
    void shouldThrowWhenVehicleModelDoesNotExist() {
        String email = "user@test.com";

        User user = new User();
        user.setId(1L);
        user.setEmail(email);

        ChronicIssueDTO dto = new ChronicIssueDTO();
        dto.setVehicleCatalogModelId(999L);

        when(userRepository.findByEmail(email))
                .thenReturn(Optional.of(user));

        when(vehicleCatalogModelRepository.findById(999L))
                .thenReturn(Optional.empty());

        assertThrows(
                ResourceNotFoundException.class,
                () -> chronicIssueService.createIssue(dto, email)
        );

        verify(chronicIssueRepository, never()).save(any());
    }



    @Test
    void shouldThrowWhenUserDoesNotExist() {
        String email = "missing@test.com";

        ChronicIssueDTO dto = new ChronicIssueDTO();
        dto.setVehicleCatalogModelId(1L);

        when(userRepository.findByEmail(email))
                .thenReturn(Optional.empty());

        assertThrows(
                ResourceNotFoundException.class,
                () -> chronicIssueService.createIssue(dto, email)
        );

        verify(vehicleCatalogModelRepository, never()).findById(any());
        verify(chronicIssueRepository, never()).save(any());
    }



}
