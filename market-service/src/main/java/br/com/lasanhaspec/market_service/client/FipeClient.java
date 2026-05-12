package br.com.lasanhaspec.market_service.client;

import br.com.lasanhaspec.market_service.dto.ExternalFipeResponseDTO;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class FipeClient {

    private final WebClient webClient;

    public FipeClient(WebClient webClient) {
        this.webClient = webClient;
    }

    public ExternalFipeResponseDTO getPrice(String brandCode, String modelCode, String yearCode) {
        try {
            return webClient.get()
                    .uri("/carros/marcas/{brand}/modelos/{model}/anos/{year}",
                            brandCode, modelCode, yearCode)
                    .retrieve()
                    .bodyToMono(ExternalFipeResponseDTO.class)
                    .block();

        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar dados da FIPE", e);
        }
    }
}