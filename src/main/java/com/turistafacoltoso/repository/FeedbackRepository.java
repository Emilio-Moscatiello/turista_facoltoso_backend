package com.turistafacoltoso.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;

import com.turistafacoltoso.model.Feedback;
import com.turistafacoltoso.util.DatabaseConnection;

public class FeedbackRepository {

    private static final String INSERT = """
                INSERT INTO feedback (id, prenotazione_id, titolo, testo, punteggio)
                VALUES (?, ?, ?, ?, ?)
            """;

    public void save(Feedback feedback) {

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(INSERT)) {

            ps.setObject(1, feedback.getId());
            ps.setObject(2, feedback.getPrenotazioneId());
            ps.setString(3, feedback.getTitolo());
            ps.setString(4, feedback.getTesto());
            ps.setInt(5, feedback.getPunteggio());

            ps.executeUpdate();

        } catch (Exception e) {
            throw new RuntimeException("Errore salvataggio feedback", e);
        }
    }
}
