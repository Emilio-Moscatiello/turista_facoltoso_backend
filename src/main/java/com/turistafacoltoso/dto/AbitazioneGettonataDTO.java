package com.turistafacoltoso.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AbitazioneGettonataDTO {

    private String nome;
    private String indirizzo;
    private int numeroPrenotazioni;
}
