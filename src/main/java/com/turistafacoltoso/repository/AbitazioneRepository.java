package com.turistafacoltoso.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.turistafacoltoso.model.Abitazione;
import com.turistafacoltoso.util.DatabaseConnection;

public class AbitazioneRepository {

    private static final String FIND_BY_CODICE_HOST = """
            SELECT a.*
            FROM abitazione a
            JOIN host h ON a.host_id = h.id
            WHERE h.codice_host = ?
            """;

    public List<Abitazione> findByCodiceHost(String codiceHost) {

        List<Abitazione> abitazioni = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(FIND_BY_CODICE_HOST)) {

            ps.setString(1, codiceHost);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Abitazione abitazione = mapRow(rs);
                    abitazioni.add(abitazione);
                }
            }

        } catch (Exception e) {
            throw new RuntimeException("Errore nel recupero delle abitazioni per codice host", e);
        }

        return abitazioni;
    }

    private Abitazione mapRow(ResultSet rs) throws Exception {
        return new Abitazione(
                UUID.fromString(rs.getString("id")),
                UUID.fromString(rs.getString("host_id")),
                rs.getString("nome"),
                rs.getString("indirizzo"),
                rs.getInt("numero_locali"),
                rs.getInt("numero_posti_letto"),
                rs.getObject("piano", Integer.class),
                rs.getBigDecimal("prezzo"),
                rs.getDate("disponibilita_inizio").toLocalDate(),
                rs.getDate("disponibilita_fine").toLocalDate());
    }
}
