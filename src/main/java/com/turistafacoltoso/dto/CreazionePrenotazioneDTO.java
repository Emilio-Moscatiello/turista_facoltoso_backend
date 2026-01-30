package com.turistafacoltoso.dto;

import java.time.LocalDate;
import java.util.UUID;

import lombok.Data;

@Data
public class CreazionePrenotazioneDTO {

    private UUID abitazioneId;
    private LocalDate dataInizio;
    private LocalDate dataFine;
}
