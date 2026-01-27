package com.turistafacoltoso.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.turistafacoltoso.model.StatisticheUtente;
import com.turistafacoltoso.util.DatabaseConnection;

public class UtenteRepository {

    private static final String TOP_5_UTENTI_GIORNI_MESE = """
            SELECT
                u.nome,
                u.cognome,
                SUM(p.data_fine - p.data_inizio) AS giorni_prenotati
            FROM prenotazione p
            JOIN utente u ON p.utente_id = u.id
            WHERE p.data_inizio >= CURRENT_DATE - INTERVAL '1 month'
            GROUP BY u.id
            ORDER BY giorni_prenotati DESC
            LIMIT 5
            """;

    public List<StatisticheUtente> findTop5UtentiPerGiorniPrenotatiUltimoMese() {

        List<StatisticheUtente> risultato = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(TOP_5_UTENTI_GIORNI_MESE);
                ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                risultato.add(
                        new StatisticheUtente(
                                rs.getString("nome"),
                                rs.getString("cognome"),
                                rs.getLong("giorni_prenotati")));
            }

        } catch (Exception e) {
            throw new RuntimeException(
                    "Errore nel recupero dei top 5 utenti per giorni prenotati",
                    e);
        }

        return risultato;
    }
}
