package com.turistafacoltoso.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StatisticheUtente {

    private String nome;
    private String cognome;
    private long giorniPrenotati;
}
