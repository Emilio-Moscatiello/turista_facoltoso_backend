package com.turistafacoltoso.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Abitazione {

    private UUID id;
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
