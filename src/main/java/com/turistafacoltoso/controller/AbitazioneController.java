package com.turistafacoltoso.controller;

import com.turistafacoltoso.service.AbitazioneService;

import io.javalin.Javalin;

public class AbitazioneController {

    private final AbitazioneService abitazioneService;

    public AbitazioneController(Javalin app) {
        this.abitazioneService = new AbitazioneService();
        registerRoutes(app);
    }

    private void registerRoutes(Javalin app) {

        app.get("/abitazioni", ctx -> {
            ctx.json(abitazioneService.getAllAbitazioni());
        });

        app.get("/abitazioni/host/{codiceHost}", ctx -> {
            String codiceHost = ctx.pathParam("codiceHost");
            ctx.json(abitazioneService.getAbitazioniByCodiceHost(codiceHost));
        });

        app.get("/abitazioni/piu-gettonata", ctx -> {
            ctx.json(
                    abitazioneService.getAbitazionePiuGettonataUltimoMese());
        });

        app.get("/abitazioni/media-posti-letto", ctx -> {
            ctx.json(
                    abitazioneService.getMediaPostiLetto());
        });
    }
}
