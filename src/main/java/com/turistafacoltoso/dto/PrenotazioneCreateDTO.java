package com.turistafacoltoso.dto;

import java.time.LocalDate;
import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PrenotazioneCreateDTO {

    private UUID utenteId;
    private UUID abitazioneId;
    private LocalDate dataInizio;
    private LocalDate dataFine;
}
