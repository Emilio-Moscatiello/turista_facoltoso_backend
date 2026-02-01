package com.turistafacoltoso.controller;

import java.util.UUID;

import com.turistafacoltoso.dto.AbitazioneCreateDTO;
import com.turistafacoltoso.model.Abitazione;
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

        app.post("/abitazioni/host/{hostId}", ctx -> {
            UUID hostId = UUID.fromString(ctx.pathParam("hostId"));
            AbitazioneCreateDTO dto = ctx.bodyAsClass(AbitazioneCreateDTO.class);

            ctx.status(201).json(
                    abitazioneService.createAbitazioneForHost(hostId, dto));
        });

        app.put("/abitazioni/host/{hostId}/{abitazioneId}", ctx -> {
            UUID hostId = UUID.fromString(ctx.pathParam("hostId"));
            UUID abitazioneId = UUID.fromString(ctx.pathParam("abitazioneId"));
            Abitazione abitazione = ctx.bodyAsClass(Abitazione.class);

            ctx.json(
                    abitazioneService.updateAbitazioneForHost(
                            hostId,
                            abitazioneId,
                            abitazione));
        });

        app.delete("/abitazioni/host/{hostId}/{abitazioneId}", ctx -> {
            UUID hostId = UUID.fromString(ctx.pathParam("hostId"));
            UUID abitazioneId = UUID.fromString(ctx.pathParam("abitazioneId"));

            abitazioneService.deleteAbitazioneForHost(hostId, abitazioneId);
            ctx.status(204);
        });

        app.get("/host/{hostId}/abitazioni", ctx -> {
            UUID hostId = UUID.fromString(ctx.pathParam("hostId"));
            ctx.json(abitazioneService.getAbitazioniByHostId(hostId));
        });

    }
}
