package com.turistafacoltoso.dto;

import lombok.Data;

@Data
public class FeedbackCreateDTO {
    private int punteggio;
    private String titolo;
    private String testo;
}
