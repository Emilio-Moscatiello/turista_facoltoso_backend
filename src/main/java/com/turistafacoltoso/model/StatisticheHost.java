package com.turistafacoltoso.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StatisticheHost {

    private String codiceHost;
    private long totalePrenotazioni;
}
