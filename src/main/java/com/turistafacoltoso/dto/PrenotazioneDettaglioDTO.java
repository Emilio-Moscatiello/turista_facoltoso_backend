package com.turistafacoltoso.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PrenotazioneDettaglioDTO {

    private String dataInizio;
    private String dataFine;

    private String utenteId;
    private String utenteNome;
    private String utenteCognome;

    private String abitazioneId;
    private String abitazioneNome;
}
