package com.turistafacoltoso.service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import com.turistafacoltoso.dto.AbitazioneCreateDTO;
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

    public List<AbitazioneDTO> getAbitazioniByHostId(UUID hostId) {
        if (hostId == null) {
            throw new IllegalArgumentException("Host ID non valido");
        }
        return abitazioneRepository.findByHostId(hostId);
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

    public Abitazione createAbitazioneForHost(UUID hostId, AbitazioneCreateDTO dto) {

        if (hostId == null) {
            throw new IllegalArgumentException("Host ID mancante");
        }

        if (dto == null) {
            throw new IllegalArgumentException("Dati abitazione mancanti");
        }

        if (dto.getNome() == null || dto.getNome().isBlank()) {
            throw new IllegalArgumentException("Nome abitazione obbligatorio");
        }

        if (dto.getIndirizzo() == null || dto.getIndirizzo().isBlank()) {
            throw new IllegalArgumentException("Indirizzo obbligatorio");
        }

        if (dto.getPostiLetto() <= 0) {
            throw new IllegalArgumentException("Posti letto non validi");
        }

        if (dto.getPrezzo() == null || dto.getPrezzo().signum() <= 0) {
            throw new IllegalArgumentException("Prezzo non valido");
        }

        Abitazione abitazione = new Abitazione(
                UUID.randomUUID(),
                hostId,
                dto.getNome(),
                dto.getIndirizzo(),
                1,
                dto.getPostiLetto(),
                null,
                dto.getPrezzo(),
                LocalDate.now(),
                LocalDate.now().plusYears(1));

        abitazioneRepository.saveForHost(hostId, abitazione);
        return abitazione;
    }

    public Abitazione updateAbitazioneForHost(
            UUID hostId,
            UUID abitazioneId,
            Abitazione abitazione) {

        if (hostId == null || abitazioneId == null) {
            throw new IllegalArgumentException("ID non validi");
        }

        abitazioneRepository.updateForHost(hostId, abitazioneId, abitazione);
        abitazione.setId(abitazioneId);
        return abitazione;
    }

    public void deleteAbitazioneForHost(UUID hostId, UUID abitazioneId) {

        if (hostId == null || abitazioneId == null) {
            throw new IllegalArgumentException("ID non validi");
        }

        abitazioneRepository.deleteForHost(hostId, abitazioneId);
    }
}
