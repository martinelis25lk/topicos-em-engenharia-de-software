package br.com.lasanhaspec.market_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//resposta mais limpa do market-servisos


@NoArgsConstructor
@AllArgsConstructor
@Data
public class FipePriceResponseDTO {
    private String valor;
    private String marca;
    private String modelo;
    private String anoModelo;
    private String combustivel;
    private String codigoFipe;
    private String mesReferencia;
}