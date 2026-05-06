package br.com.lasanhaspec.market_service.client;


import br.com.lasanhaspec.market_service.dto.ExternalFipeResponseDTO;
import br.com.lasanhaspec.market_service.dto.FipePriceResponseDTO;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class FipeClient {


    private final WebClient webClient;

    public FipeClient(WebClient webClient) {
        this.webClient = webClient;
    }


    public FipePriceResponseDTO getPrice(String brandCode, String modelCode, String yearCode) {
        try {
            ExternalFipeResponseDTO external = webClient.get()
                    .uri("/carros/marcas/{brand}/modelos/{model}/anos/{year}",
                            brandCode, modelCode, yearCode)
                    .retrieve()
                    .bodyToMono(ExternalFipeResponseDTO.class)
                    .block();



            if(external == null) return null;

            // mapeia para o DTO limpo
            FipePriceResponseDTO dto = new FipePriceResponseDTO();

            dto.setValor(external.getValor());
            dto.setMarca(external.getMarca());
            dto.setModelo(external.getModelo());
            dto.setAnoModelo(external.getAnoModelo());
            dto.setCombustivel(external.getCombustivel());
            dto.setCodigoFipe(external.getCodigoFipe());
            dto.setMesReferencia(external.getMesReferencia());
            return dto;

        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar dados da FIPE", e);
        }
    }
}
