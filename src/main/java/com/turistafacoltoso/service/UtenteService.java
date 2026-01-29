package com.turistafacoltoso.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.turistafacoltoso.dto.StatisticheUtenteDTO;
import com.turistafacoltoso.model.Utente;
import com.turistafacoltoso.repository.UtenteRepository;

public class UtenteService {

    private final UtenteRepository utenteRepository;

    public UtenteService() {
        this.utenteRepository = new UtenteRepository();
    }

    public List<Utente> getAllUtenti() {
        return utenteRepository.findAll();
    }

    public Utente getUtenteById(UUID id) {
        if (id == null) {
            throw new IllegalArgumentException("ID utente non valido");
        }

        return utenteRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("Utente non trovato"));
    }

    public Utente createUtente(Utente utente) {
        validateUtente(utente);

        if (utente.getId() == null) {
            utente.setId(UUID.randomUUID());
        }

        utenteRepository.save(utente);
        return utente;
    }

    public Utente updateUtente(UUID id, Utente utente) {
        if (id == null) {
            throw new IllegalArgumentException("ID utente non valido");
        }

        validateUtente(utente);

        Optional<Utente> existing = utenteRepository.findById(id);
        if (existing.isEmpty()) {
            throw new RuntimeException("Utente non trovato");
        }

        utente.setId(id);
        boolean updated = utenteRepository.update(utente);

        if (!updated) {
            throw new RuntimeException("Errore durante l'aggiornamento");
        }

        return utente;
    }

    public void deleteUtente(UUID id) {
        if (id == null) {
            throw new IllegalArgumentException("ID utente non valido");
        }

        boolean deleted = utenteRepository.delete(id);
        if (!deleted) {
            throw new RuntimeException("Utente non trovato");
        }
    }

    public List<StatisticheUtenteDTO> getTop5UtentiUltimoMese() {
        return utenteRepository
                .findTop5UtentiPerGiorniPrenotatiUltimoMese();
    }

    private void validateUtente(Utente utente) {
        if (utente == null) {
            throw new IllegalArgumentException("Utente nullo");
        }

        if (utente.getNome() == null || utente.getNome().isBlank()) {
            throw new IllegalArgumentException("Nome obbligatorio");
        }

        if (utente.getCognome() == null || utente.getCognome().isBlank()) {
            throw new IllegalArgumentException("Cognome obbligatorio");
        }

        if (utente.getEmail() == null || utente.getEmail().isBlank()) {
            throw new IllegalArgumentException("Email obbligatoria");
        }
    }
}
