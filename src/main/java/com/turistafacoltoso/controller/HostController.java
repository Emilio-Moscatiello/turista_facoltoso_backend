package com.turistafacoltoso.controller;

import java.util.UUID;

import com.turistafacoltoso.model.Host;
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

        app.get("/host", ctx -> {
            ctx.json(hostService.getAllHost());
        });

        app.get("/host/{id}", ctx -> {
            ctx.json(
                    hostService.getHostById(UUID.fromString(ctx.pathParam("id"))));
        });

        app.post("/host", ctx -> {
            Host host = ctx.bodyAsClass(Host.class);
            ctx.status(201).json(hostService.createHost(host));
        });

        app.put("/host/{id}", ctx -> {
            UUID id = UUID.fromString(ctx.pathParam("id"));
            Host host = ctx.bodyAsClass(Host.class);
            ctx.json(hostService.updateHost(id, host));
        });

        app.delete("/host/{id}", ctx -> {
            hostService.deleteHost(UUID.fromString(ctx.pathParam("id")));
            ctx.status(204);
        });

        app.get("/host/{id}/prenotazioni", ctx -> {
            UUID hostId = UUID.fromString(ctx.pathParam("id"));
            ctx.json(
                    hostService.getPrenotazioniByHostId(hostId));
        });

    }
}
