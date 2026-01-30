package com.turistafacoltoso.service;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import com.turistafacoltoso.dto.PrenotazioneCreateDTO;
import com.turistafacoltoso.dto.PrenotazioneDettaglioDTO;
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

    public PrenotazioneDettaglioDTO getUltimaPrenotazioneDettaglio(UUID utenteId) {

        if (utenteId == null) {
            throw new IllegalArgumentException("ID utente non valido");
        }

        return prenotazioneRepository
                .findUltimaPrenotazioneDettaglioByUtenteId(utenteId)
                .orElseThrow(() -> new RuntimeException("Prenotazione non trovata"));
    }

    public Prenotazione createPrenotazione(PrenotazioneCreateDTO dto) {

        if (dto.getUtenteId() == null ||
                dto.getAbitazioneId() == null ||
                dto.getDataInizio() == null ||
                dto.getDataFine() == null) {
            throw new IllegalArgumentException("Dati prenotazione incompleti");
        }

        if (dto.getDataInizio().isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("La data di inizio non può essere nel passato");
        }

        if (!dto.getDataFine().isAfter(dto.getDataInizio())) {
            throw new IllegalArgumentException("La data di fine deve essere successiva alla data di inizio");
        }

        if (prenotazioneRepository.existsOverlappingPrenotazione(
                dto.getAbitazioneId(),
                dto.getDataInizio(),
                dto.getDataFine())) {
            throw new IllegalArgumentException("L'abitazione non è disponibile nel periodo selezionato");
        }

        Prenotazione prenotazione = new Prenotazione(
                UUID.randomUUID(),
                dto.getUtenteId(),
                dto.getAbitazioneId(),
                dto.getDataInizio(),
                dto.getDataFine());

        prenotazioneRepository.save(prenotazione);

        return prenotazione;
    }

    public Prenotazione creaPrenotazionePerUtente(
            UUID utenteId,
            UUID abitazioneId,
            LocalDate dataInizio,
            LocalDate dataFine) {

        if (dataInizio.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("La data di inizio non può essere nel passato");
        }

        if (dataFine.isBefore(dataInizio)) {
            throw new IllegalArgumentException("La data di fine deve essere successiva alla data di inizio");
        }

        if (prenotazioneRepository.existsOverlappingPrenotazione(
                abitazioneId, dataInizio, dataFine)) {
            throw new IllegalArgumentException(
                    "L'abitazione non è disponibile nel periodo selezionato");
        }

        Prenotazione prenotazione = new Prenotazione(
                UUID.randomUUID(),
                utenteId,
                abitazioneId,
                dataInizio,
                dataFine);

        prenotazioneRepository.save(prenotazione);
        return prenotazione;
    }

}
