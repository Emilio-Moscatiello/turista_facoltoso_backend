package com.turistafacoltoso.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.turistafacoltoso.dto.FeedbackListDTO;
import com.turistafacoltoso.model.Feedback;
import com.turistafacoltoso.util.DatabaseConnection;

public class FeedbackRepository {

    private static final String INSERT = """
                INSERT INTO feedback (id, prenotazione_id, titolo, testo, punteggio)
                VALUES (?, ?, ?, ?, ?)
            """;

    private static final String FIND_ALL_WITH_DETAILS = """
                SELECT
                    f.id AS feedback_id,
                    f.prenotazione_id,
                    f.titolo,
                    f.testo,
                    f.punteggio,
                    p.data_inizio,
                    p.data_fine,
                    a.nome AS abitazione_nome,
                    u.nome AS utente_nome,
                    u.cognome AS utente_cognome
                FROM feedback f
                JOIN prenotazione p ON f.prenotazione_id = p.id
                JOIN abitazione a ON p.abitazione_id = a.id
                JOIN utente u ON p.utente_id = u.id
                ORDER BY p.data_fine DESC
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

    public List<FeedbackListDTO> findAllWithDetails() {
        List<FeedbackListDTO> list = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(FIND_ALL_WITH_DETAILS);
                ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                LocalDate di = rs.getObject("data_inizio", LocalDate.class);
                LocalDate df = rs.getObject("data_fine", LocalDate.class);
                list.add(new FeedbackListDTO(
                        rs.getObject("feedback_id", java.util.UUID.class).toString(),
                        rs.getObject("prenotazione_id", java.util.UUID.class).toString(),
                        rs.getString("titolo"),
                        rs.getString("testo"),
                        rs.getInt("punteggio"),
                        null,
                        rs.getString("abitazione_nome"),
                        rs.getString("utente_nome"),
                        rs.getString("utente_cognome"),
                        di != null ? di.toString() : null,
                        df != null ? df.toString() : null));
            }
        } catch (Exception e) {
            throw new RuntimeException("Errore recupero feedback", e);
        }
        return list;
    }
}
