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
    private int voto;
    private String commento;
    private LocalDateTime creatoIl;
}
