package com.turistafacoltoso.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class AbitazioneCreateDTO {

    private String nome;
    private String indirizzo;
    private int postiLetto;
    private BigDecimal prezzo;
}
