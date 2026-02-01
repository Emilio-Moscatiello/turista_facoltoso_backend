package com.turistafacoltoso.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;

import com.turistafacoltoso.model.Feedback;
import com.turistafacoltoso.util.DatabaseConnection;

public class FeedbackRepository {

    private static final String INSERT = """
                INSERT INTO feedback (id, prenotazione_id, voto, commento)
                VALUES (?, ?, ?, ?)
            """;

    public void save(Feedback feedback) {

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(INSERT)) {

            ps.setObject(1, feedback.getId());
            ps.setObject(2, feedback.getPrenotazioneId());
            ps.setInt(3, feedback.getVoto());
            ps.setString(4, feedback.getCommento());

            ps.executeUpdate();

        } catch (Exception e) {
            throw new RuntimeException("Errore salvataggio feedback", e);
        }
    }
}
