package com.turistafacoltoso.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.turistafacoltoso.dto.StatisticheUtenteDTO;
import com.turistafacoltoso.util.DatabaseConnection;

public class UtenteRepository {

    private static final String TOP_5_UTENTI_GIORNI_MESE = """
            SELECT
                u.nome,
                u.cognome,
                u.email,
                SUM(p.data_fine - p.data_inizio) AS giorni_prenotati
            FROM prenotazione p
            JOIN utente u ON p.utente_id = u.id
            WHERE p.data_inizio >= CURRENT_DATE - INTERVAL '1 month'
            GROUP BY u.nome, u.cognome, u.email
            ORDER BY giorni_prenotati DESC
            LIMIT 5
            """;

    public List<StatisticheUtenteDTO> findTop5UtentiPerGiorniPrenotatiUltimoMese() {

        List<StatisticheUtenteDTO> risultato = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(TOP_5_UTENTI_GIORNI_MESE);
                ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                risultato.add(
                        new StatisticheUtenteDTO(
                                rs.getString("nome"),
                                rs.getString("cognome"),
                                rs.getString("email"),
                                rs.getInt("giorni_prenotati")));
            }

        } catch (Exception e) {
            throw new RuntimeException(
                    "Errore nel recupero dei top 5 utenti per giorni prenotati",
                    e);
        }

        return risultato;
    }
}
