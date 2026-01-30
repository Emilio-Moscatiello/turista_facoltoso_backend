package com.turistafacoltoso.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import lombok.Data;

@Data
public class AbitazioneCreateDTO {

    private UUID hostId;

    private String nome;
    private String indirizzo;
    private int numeroLocali;
    private int numeroPostiLetto;
    private Integer piano;
    private BigDecimal prezzo;

    private LocalDate disponibilitaInizio;
    private LocalDate disponibilitaFine;
}
