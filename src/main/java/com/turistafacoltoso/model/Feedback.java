package com.turistafacoltoso.model;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Feedback {

    private UUID id;
    private UUID prenotazioneId;
    private String titolo;
    private String testo;
    private int punteggio;
    private LocalDateTime creatoIl;
}
