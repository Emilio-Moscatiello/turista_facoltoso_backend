package com.turistafacoltoso.service;

import java.util.List;
import java.util.UUID;

import com.turistafacoltoso.dto.AbitazioneDTO;
import com.turistafacoltoso.dto.AbitazioneGettonataDTO;
import com.turistafacoltoso.model.Abitazione;
import com.turistafacoltoso.repository.AbitazioneRepository;

public class AbitazioneService {

    private final AbitazioneRepository abitazioneRepository;

    public AbitazioneService() {
        this.abitazioneRepository = new AbitazioneRepository();
    }

    public List<AbitazioneDTO> getAbitazioniByCodiceHost(String codiceHost) {

        if (codiceHost == null || codiceHost.isBlank()) {
            throw new IllegalArgumentException("Codice host non valido");
        }

        return abitazioneRepository.findByCodiceHost(codiceHost);
    }

    public AbitazioneGettonataDTO getAbitazionePiuGettonataUltimoMese() {
        return abitazioneRepository.findAbitazionePiuGettonataUltimoMese();
    }

    public double getMediaPostiLetto() {
        return abitazioneRepository.findMediaPostiLetto();
    }

    public List<Abitazione> getAllAbitazioni() {
        return abitazioneRepository.findAll();
    }

    public Abitazione createAbitazioneForHost(UUID hostId, Abitazione abitazione) {

        validateHostId(hostId);
        validateAbitazione(abitazione);

        if (abitazione.getId() == null) {
            abitazione.setId(UUID.randomUUID());
        }

        abitazioneRepository.saveForHost(hostId, abitazione);
        return abitazione;
    }

    public Abitazione updateAbitazioneForHost(
            UUID hostId,
            UUID abitazioneId,
            Abitazione abitazione) {

        validateHostId(hostId);
        validateAbitazioneId(abitazioneId);
        validateAbitazione(abitazione);

        abitazioneRepository.updateForHost(hostId, abitazioneId, abitazione);
        abitazione.setId(abitazioneId);

        return abitazione;
    }

    public void deleteAbitazioneForHost(UUID hostId, UUID abitazioneId) {

        validateHostId(hostId);
        validateAbitazioneId(abitazioneId);

        abitazioneRepository.deleteForHost(hostId, abitazioneId);
    }

    private void validateHostId(UUID hostId) {
        if (hostId == null) {
            throw new IllegalArgumentException("Host ID non valido");
        }
    }

    private void validateAbitazioneId(UUID abitazioneId) {
        if (abitazioneId == null) {
            throw new IllegalArgumentException("Abitazione ID non valido");
        }
    }

    private void validateAbitazione(Abitazione a) {

        if (a == null) {
            throw new IllegalArgumentException("Abitazione nulla");
        }

        if (a.getNome() == null || a.getNome().isBlank()) {
            throw new IllegalArgumentException("Nome abitazione obbligatorio");
        }

        if (a.getIndirizzo() == null || a.getIndirizzo().isBlank()) {
            throw new IllegalArgumentException("Indirizzo obbligatorio");
        }

        if (a.getNumeroPostiLetto() <= 0) {
            throw new IllegalArgumentException("Numero posti letto non valido");
        }

        if (a.getPrezzo() == null || a.getPrezzo().signum() <= 0) {
            throw new IllegalArgumentException("Prezzo non valido");
        }

        if (a.getDisponibilitaInizio() == null ||
                a.getDisponibilitaFine() == null ||
                a.getDisponibilitaFine().isBefore(a.getDisponibilitaInizio())) {

            throw new IllegalArgumentException("Periodo di disponibilità non valido");
        }
    }
}
