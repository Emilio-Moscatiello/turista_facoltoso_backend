package com.turistafacoltoso.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StatisticheHostDTO {

    private String codiceHost;
    private String nome;
    private String cognome;
    private int numeroPrenotazioni;
}
