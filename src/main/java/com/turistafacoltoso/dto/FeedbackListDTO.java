package com.turistafacoltoso.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FeedbackListDTO {

    private String id;
    private String prenotazioneId;
    private String titolo;
    private String testo;
    private int punteggio;
    private LocalDateTime creatoIl;
    private String abitazioneNome;
    private String utenteNome;
    private String utenteCognome;
    private String dataInizio;
    private String dataFine;
}
