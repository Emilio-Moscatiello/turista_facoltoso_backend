package com.turistafacoltoso.dto;

import java.math.BigDecimal;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AbitazioneDTO {

    private UUID id;
    private String nome;
    private String indirizzo;
    private int postiLetto;
    private BigDecimal prezzo;
}
