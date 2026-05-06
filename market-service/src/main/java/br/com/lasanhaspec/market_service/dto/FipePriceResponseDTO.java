package br.com.lasanhaspec.market_service.dto;


import lombok.Data;

@Data
public class FipePriceResponseDTO {
    private String Valor;
    private String Marca;
    private String Modelo;
    private String AnoModelo;
    private String Combustivel;
    private String CodigoFipe;
    private String MesReferencia;
}
