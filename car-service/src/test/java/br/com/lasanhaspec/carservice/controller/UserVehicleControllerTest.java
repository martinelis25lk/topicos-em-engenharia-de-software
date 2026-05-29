package br.com.lasanhaspec.carservice.controller;

import org.springframework.http.MediaType;
import br.com.lasanhaspec.carservice.service.UserVehicleService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import br.com.lasanhaspec.carservice.service.JwtService;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;



@WebMvcTest(UserVehicleController.class)
@AutoConfigureMockMvc(addFilters = false)
class UserVehicleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserVehicleService userVehicleService;

    @MockBean
    private JwtService jwtService;

    @Test
    void shouldReturnBadRequestWhenVehicleCatalogModelIdIsNull() throws Exception {
        String payload = """
                {
                  "vehicleCatalogModelId": null,
                  "nickName": "Minha BMW",
                  "currentHorsePower": 190,
                  "currentWeight": 1300,
                  "currentTorque": 250
                }
                """;

        mockMvc.perform(post("/user-vehicles")
                        .contentType(
                                MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnBadRequestWhenNicknameIsBlank() throws Exception {

        String payload = """
            {
              "vehicleCatalogModelId": 1,
              "nickName": "",
              "currentHorsePower": 190,
              "currentWeight": 1300,
              "currentTorque": 250
            }
            """;

        mockMvc.perform(post("/user-vehicles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnBadRequestWhenHorsePowerIsNegative() throws Exception {

        String payload = """
            {
              "vehicleCatalogModelId": 1,
              "nickName": "Minha BMW",
              "currentHorsePower": -10,
              "currentWeight": 1300,
              "currentTorque": 250
            }
            """;

        mockMvc.perform(post("/user-vehicles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpect(status().isBadRequest());
    }





}