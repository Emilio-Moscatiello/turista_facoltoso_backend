package com.turistafacoltoso.controller;

import com.turistafacoltoso.service.UtenteService;

import io.javalin.Javalin;

public class UtenteController {

    private final UtenteService utenteService;

    public UtenteController(Javalin app) {
        this.utenteService = new UtenteService();
        registerRoutes(app);
    }

    private void registerRoutes(Javalin app) {

        app.get("/utenti/top-5-giorni-ultimo-mese", ctx -> {
            ctx.json(utenteService.getTop5UtentiPerGiorniPrenotatiUltimoMese());
        });
    }
}
