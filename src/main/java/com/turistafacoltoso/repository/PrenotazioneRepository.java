package com.turistafacoltoso.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Optional;
import java.util.UUID;

import com.turistafacoltoso.model.Prenotazione;
import com.turistafacoltoso.util.DatabaseConnection;

public class PrenotazioneRepository {

    private static final String FIND_ULTIMA_BY_UTENTE = """
            SELECT *
            FROM prenotazione
            WHERE utente_id = ?
            ORDER BY data_fine DESC
            LIMIT 1
            """;

    public Optional<Prenotazione> findUltimaByUtenteId(UUID utenteId) {

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(FIND_ULTIMA_BY_UTENTE)) {

            ps.setObject(1, utenteId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapRow(rs));
                }
            }

        } catch (Exception e) {
            throw new RuntimeException("Errore nel recupero dell'ultima prenotazione", e);
        }

        return Optional.empty();
    }

    private Prenotazione mapRow(ResultSet rs) throws Exception {
        return new Prenotazione(
                UUID.fromString(rs.getString("id")),
                UUID.fromString(rs.getString("utente_id")),
                UUID.fromString(rs.getString("abitazione_id")),
                rs.getDate("data_inizio").toLocalDate(),
                rs.getDate("data_fine").toLocalDate());
    }
}
