package com.turistafacoltoso.service;

import java.util.List;

import com.turistafacoltoso.dto.StatisticheUtenteDTO;
import com.turistafacoltoso.repository.UtenteRepository;

public class UtenteService {

    private final UtenteRepository utenteRepository;

    public UtenteService() {
        this.utenteRepository = new UtenteRepository();
    }

    public List<StatisticheUtenteDTO> getTop5UtentiPerGiorniPrenotatiUltimoMese() {
        return utenteRepository.findTop5UtentiPerGiorniPrenotatiUltimoMese();
    }
}
