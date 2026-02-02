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

        if (dto.getPunteggio() < 1 || dto.getPunteggio() > 5) {
            throw new IllegalArgumentException("Il punteggio deve essere tra 1 e 5");
        }

        Feedback feedback = new Feedback(
                UUID.randomUUID(),
                prenotazioneId,
                dto.getTitolo(),
                dto.getTesto(),
                dto.getPunteggio(),
                LocalDateTime.now());

        feedbackRepository.save(feedback);
        return feedback;
    }
}
