package com.turistafacoltoso.controller;

import java.util.UUID;

import com.turistafacoltoso.model.Utente;
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
            ctx.json(
                    utenteService.getTop5UtentiUltimoMese());
        });

        app.get("/utenti", ctx -> {
            ctx.json(utenteService.getAllUtenti());
        });

        app.get("/utenti/{id}", ctx -> {
            try {
                UUID id = UUID.fromString(ctx.pathParam("id"));
                ctx.json(utenteService.getUtenteById(id));
            } catch (IllegalArgumentException e) {
                ctx.status(400).result("ID utente non valido");
            } catch (RuntimeException e) {
                ctx.status(404).result(e.getMessage());
            }
        });

        app.post("/utenti", ctx -> {
            try {
                Utente utente = ctx.bodyAsClass(Utente.class);
                ctx.status(201).json(
                        utenteService.createUtente(utente));
            } catch (IllegalArgumentException e) {
                ctx.status(400).result(e.getMessage());
            }
        });

        app.put("/utenti/{id}", ctx -> {
            try {
                UUID id = UUID.fromString(ctx.pathParam("id"));
                Utente utente = ctx.bodyAsClass(Utente.class);
                ctx.json(
                        utenteService.updateUtente(id, utente));
            } catch (IllegalArgumentException e) {
                ctx.status(400).result(e.getMessage());
            } catch (RuntimeException e) {
                ctx.status(404).result(e.getMessage());
            }
        });

        app.delete("/utenti/{id}", ctx -> {
            try {
                UUID id = UUID.fromString(ctx.pathParam("id"));
                utenteService.deleteUtente(id);
                ctx.status(204);
            } catch (IllegalArgumentException e) {
                ctx.status(400).result("ID utente non valido");
            } catch (RuntimeException e) {
                ctx.status(404).result(e.getMessage());
            }
        });
    }
}
