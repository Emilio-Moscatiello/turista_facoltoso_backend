package com.turistafacoltoso.model;

import java.time.LocalDate;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Prenotazione {

    private UUID id;
    private UUID utenteId;
    private UUID abitazioneId;

    private LocalDate dataInizio;
    private LocalDate dataFine;
}
