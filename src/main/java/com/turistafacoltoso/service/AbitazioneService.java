package com.turistafacoltoso.service;

import java.util.List;

import com.turistafacoltoso.model.Abitazione;
import com.turistafacoltoso.repository.AbitazioneRepository;

public class AbitazioneService {

    private final AbitazioneRepository abitazioneRepository;

    public AbitazioneService() {
        this.abitazioneRepository = new AbitazioneRepository();
    }

    public List<Abitazione> getAbitazioniByCodiceHost(String codiceHost) {

        if (codiceHost == null || codiceHost.isBlank()) {
            throw new IllegalArgumentException("Codice host non valido");
        }

        return abitazioneRepository.findByCodiceHost(codiceHost);
    }
}
