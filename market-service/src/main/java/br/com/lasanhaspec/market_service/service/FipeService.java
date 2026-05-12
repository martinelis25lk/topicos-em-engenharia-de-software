package br.com.lasanhaspec.market_service.service;

import br.com.lasanhaspec.market_service.client.FipeClient;
import br.com.lasanhaspec.market_service.dto.ExternalFipeResponseDTO;
import br.com.lasanhaspec.market_service.dto.FipePriceResponseDTO;
import org.springframework.stereotype.Service;

@Service
public class FipeService {

    private final FipeClient fipeClient;

    public FipeService(FipeClient fipeClient){
        this.fipeClient = fipeClient;
    }

    public FipePriceResponseDTO getPrice(String brandCode, String modelCode, String yearCode) {

        ExternalFipeResponseDTO external = fipeClient.getPrice(brandCode, modelCode, yearCode);

        return new FipePriceResponseDTO(
                external.getValor(),
                external.getMarca(),
                external.getModelo(),
                external.getAnoModelo(),
                external.getCombustivel(),
                external.getCodigoFipe(),
                external.getMesReferencia()
        );
    }
}