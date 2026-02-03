package com.turistafacoltoso.service;

import static org.junit.Assert.assertNotNull;
import org.junit.Test;

public class PrenotazioneServiceTest {

    @Test
    public void serviceCorretto() {
        PrenotazioneService service = new PrenotazioneService();
        assertNotNull(service);
    }
}
