package com.turistafacoltoso.controller;

import com.turistafacoltoso.service.HostService;

import io.javalin.Javalin;

public class HostController {

    private final HostService hostService;

    public HostController(Javalin app) {
        this.hostService = new HostService();
        registerRoutes(app);
    }

    private void registerRoutes(Javalin app) {

        app.get("/host/top-ultimo-mese", ctx -> {
            ctx.json(hostService.getHostPiuPrenotazioniUltimoMese());
        });

        app.get("/host/super-host", ctx -> {
            ctx.json(hostService.getSuperHost());
        });
    }
}
