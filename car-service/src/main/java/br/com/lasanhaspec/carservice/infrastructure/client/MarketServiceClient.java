package br.com.lasanhaspec.carservice.infrastructure.client;

import br.com.lasanhaspec.carservice.dto.FipeResponseDTO;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class MarketServiceClient {

    private final WebClient webClient;

    public MarketServiceClient(WebClient.Builder builder) {
        this.webClient = builder
                .baseUrl("http://localhost:8081")
                .build();
    }

    public FipeResponseDTO getFipePrice(String brandCode, String modelCode, String yearCode) {
        try {
            return webClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/market/fipe/price")
                            .queryParam("brandCode", brandCode)
                            .queryParam("modelCode", modelCode)
                            .queryParam("yearCode", yearCode)
                            .build())
                    .retrieve()
                    .bodyToMono(FipeResponseDTO.class)
                    .block();
        } catch (Exception e) {
            System.err.println("Erro ao chamar FIPE: " + e.getMessage());
            return null;
        }
    }




}