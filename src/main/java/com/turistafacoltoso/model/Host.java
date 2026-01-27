package com.turistafacoltoso.model;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Host {

    private UUID id;
    private UUID utenteId;
    private String codiceHost;
}
