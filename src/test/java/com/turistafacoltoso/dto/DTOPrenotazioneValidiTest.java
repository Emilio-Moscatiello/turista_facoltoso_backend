package com.turistafacoltoso.dto;

import java.util.UUID;

import static org.junit.Assert.assertNotNull;
import org.junit.Test;

public class DTOPrenotazioneValidiTest {

    @Test
    public void prenotazioneCreateDTOIsValid() {
        PrenotazioneCreateDTO dto = new PrenotazioneCreateDTO();
        dto.setUtenteId(UUID.randomUUID());
        dto.setAbitazioneId(UUID.randomUUID());

        assertNotNull(dto);
    }
}
