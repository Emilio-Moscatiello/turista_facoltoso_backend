package com.turistafacoltoso.service;

import java.util.Optional;
import java.util.UUID;

import com.turistafacoltoso.model.Prenotazione;
import com.turistafacoltoso.repository.PrenotazioneRepository;

public class PrenotazioneService {

    private final PrenotazioneRepository prenotazioneRepository;

    public PrenotazioneService() {
        this.prenotazioneRepository = new PrenotazioneRepository();
    }

    public Optional<Prenotazione> getUltimaPrenotazioneUtente(UUID utenteId) {

        if (utenteId == null) {
            throw new IllegalArgumentException("ID utente non valido");
        }

        return prenotazioneRepository.findUltimaByUtenteId(utenteId);
    }
}
