package com.turistafacoltoso.service;

import java.util.List;

import com.turistafacoltoso.dto.AbitazioneDTO;
import com.turistafacoltoso.dto.AbitazioneGettonataDTO;
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

}
