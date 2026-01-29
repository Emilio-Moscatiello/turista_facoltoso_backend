package com.turistafacoltoso.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.turistafacoltoso.dto.AbitazioneDTO;
import com.turistafacoltoso.dto.AbitazioneGettonataDTO;
import com.turistafacoltoso.util.DatabaseConnection;

public class AbitazioneRepository {

    private static final String FIND_BY_CODICE_HOST = """
                SELECT
                    a.id,
                    a.nome,
                    a.indirizzo,
                    a.numero_posti_letto,
                    a.prezzo
                FROM abitazione a
                JOIN host h ON a.host_id = h.id
                WHERE h.codice_host = ?
            """;

    public List<AbitazioneDTO> findByCodiceHost(String codiceHost) {

        List<AbitazioneDTO> result = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(FIND_BY_CODICE_HOST)) {

            ps.setString(1, codiceHost);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    result.add(mapRow(rs));
                }
            }

        } catch (Exception e) {
            throw new RuntimeException(
                    "Errore nel recupero delle abitazioni per codice host", e);
        }

        return result;
    }

    private AbitazioneDTO mapRow(ResultSet rs) throws Exception {
        return new AbitazioneDTO(
                rs.getObject("id", UUID.class),
                rs.getString("nome"),
                rs.getString("indirizzo"),
                rs.getInt("numero_posti_letto"), // 👈 QUI
                rs.getBigDecimal("prezzo"));
    }

    private static final String ABITAZIONE_PIU_GETTONATA = """
                SELECT
                    a.nome,
                    a.indirizzo,
                    COUNT(p.id) AS numero_prenotazioni
                FROM prenotazione p
                JOIN abitazione a ON p.abitazione_id = a.id
                WHERE p.data_inizio >= CURRENT_DATE - INTERVAL '1 month'
                GROUP BY a.id, a.nome, a.indirizzo
                ORDER BY numero_prenotazioni DESC
                LIMIT 1
            """;

    public AbitazioneGettonataDTO findAbitazionePiuGettonataUltimoMese() {

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(ABITAZIONE_PIU_GETTONATA);
                ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                return new AbitazioneGettonataDTO(
                        rs.getString("nome"),
                        rs.getString("indirizzo"),
                        rs.getInt("numero_prenotazioni"));
            }

            return null;

        } catch (Exception e) {
            throw new RuntimeException(
                    "Errore nel recupero dell'abitazione più gettonata",
                    e);
        }
    }

}
