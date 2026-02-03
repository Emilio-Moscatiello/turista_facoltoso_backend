package com.turistafacoltoso.service;

import java.time.LocalDate;
import java.util.UUID;

import org.junit.Test;

import com.turistafacoltoso.dto.PrenotazioneCreateDTO;

public class PrenotazioneDataPassataTest {

    @Test(expected = IllegalArgumentException.class)
    public void prenotazioneConDataPassataLanciaEccezione() {

        PrenotazioneService service = new PrenotazioneService();

        PrenotazioneCreateDTO dto = new PrenotazioneCreateDTO();
        dto.setUtenteId(UUID.randomUUID());
        dto.setAbitazioneId(UUID.randomUUID());
        dto.setDataInizio(LocalDate.now().minusDays(1));
        dto.setDataFine(LocalDate.now().plusDays(3));

        service.createPrenotazione(dto);
    }
}
