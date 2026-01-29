package com.turistafacoltoso.controller;

import java.util.UUID;

import com.turistafacoltoso.service.PrenotazioneService;

import io.javalin.Javalin;

public class PrenotazioneController {

    private final PrenotazioneService prenotazioneService;

    public PrenotazioneController(Javalin app) {
        this.prenotazioneService = new PrenotazioneService();
        registerRoutes(app);
    }

    private void registerRoutes(Javalin app) {

        app.get("/prenotazioni/ultima/{utenteId}", ctx -> {

            String utenteIdParam = ctx.pathParam("utenteId");
            UUID utenteId;

            try {
                utenteId = UUID.fromString(utenteIdParam);
            } catch (IllegalArgumentException e) {
                ctx.status(400).result("Formato UUID non valido");
                return;
            }

            try {
                ctx.json(
                        prenotazioneService.getUltimaPrenotazioneDettaglio(utenteId));
            } catch (RuntimeException e) {
                ctx.status(404).result("Nessuna prenotazione trovata");
            }
        });
    }
}
