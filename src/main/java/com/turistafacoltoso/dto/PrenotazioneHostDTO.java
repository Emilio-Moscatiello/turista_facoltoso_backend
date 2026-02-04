package com.turistafacoltoso.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PrenotazioneHostDTO {

    private String prenotazioneId;
    private String utenteId;
    private String utenteNome;
    private String utenteCognome;
    private String abitazioneNome;
    private String dataInizio;
    private String dataFine;
}
