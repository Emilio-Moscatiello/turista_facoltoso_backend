package com.turistafacoltoso.model;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Utente {

    private UUID id;
    private String nome;
    private String cognome;
    private String email;
    private String indirizzo;
}
