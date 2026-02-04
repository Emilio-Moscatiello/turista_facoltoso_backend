package com.turistafacoltoso.controller;

import java.util.UUID;

import com.turistafacoltoso.dto.FeedbackCreateDTO;
import com.turistafacoltoso.service.FeedbackService;

import io.javalin.Javalin;

public class FeedbackController {

    private final FeedbackService feedbackService;

    public FeedbackController(Javalin app) {
        this.feedbackService = new FeedbackService();
        registerRoutes(app);
    }

    private void registerRoutes(Javalin app) {

        app.get("/feedback", ctx -> {
            ctx.json(feedbackService.getAllWithDetails());
        });

        app.post("/prenotazioni/{prenotazioneId}/feedback", ctx -> {
            UUID prenotazioneId = UUID.fromString(ctx.pathParam("prenotazioneId"));
            FeedbackCreateDTO dto = ctx.bodyAsClass(FeedbackCreateDTO.class);

            ctx.status(201).json(
                    feedbackService.createFeedback(prenotazioneId, dto));
        });
    }
}
