package br.com.lasanhaspec.market_service.service;


import br.com.lasanhaspec.market_service.client.FipeClient;
import br.com.lasanhaspec.market_service.dto.FipePriceResponseDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class FipeService {

    private final FipeClient fipeClient;


    public FipeService(FipeClient fipeClient ){
        this.fipeClient = fipeClient;
    }



    public FipePriceResponseDTO getPrice(String brandCode, String modelCode, String yearCode){
        return fipeClient.getPrice(brandCode, modelCode, yearCode);
    }

}
