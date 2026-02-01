package com.turistafacoltoso.service;

import java.time.LocalDateTime;
import java.util.UUID;

import com.turistafacoltoso.dto.FeedbackCreateDTO;
import com.turistafacoltoso.model.Feedback;
import com.turistafacoltoso.repository.FeedbackRepository;

public class FeedbackService {

    private final FeedbackRepository feedbackRepository;

    public FeedbackService() {
        this.feedbackRepository = new FeedbackRepository();
    }

    public Feedback createFeedback(UUID prenotazioneId, FeedbackCreateDTO dto) {

        if (prenotazioneId == null) {
            throw new IllegalArgumentException("Prenotazione ID mancante");
        }

        if (dto == null) {
            throw new IllegalArgumentException("Dati feedback mancanti");
        }

        if (dto.getVoto() < 1 || dto.getVoto() > 5) {
            throw new IllegalArgumentException("Il voto deve essere tra 1 e 5");
        }

        Feedback feedback = new Feedback(
                UUID.randomUUID(),
                prenotazioneId,
                dto.getVoto(),
                dto.getCommento(),
                LocalDateTime.now());

        feedbackRepository.save(feedback);
        return feedback;
    }
}
