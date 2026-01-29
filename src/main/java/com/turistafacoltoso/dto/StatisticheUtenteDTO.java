package com.turistafacoltoso.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StatisticheUtenteDTO {

    private String nome;
    private String cognome;
    private String email;
    private int giorniPrenotati;
}
