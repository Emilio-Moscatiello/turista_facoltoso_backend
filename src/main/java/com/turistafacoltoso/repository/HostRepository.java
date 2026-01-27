package com.turistafacoltoso.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.turistafacoltoso.model.StatisticheHost;
import com.turistafacoltoso.util.DatabaseConnection;

public class HostRepository {

    private static final String HOST_PIU_PRENOTAZIONI_MESE = """
            SELECT
                h.codice_host,
                COUNT(p.id) AS totale_prenotazioni
            FROM prenotazione p
            JOIN abitazione a ON p.abitazione_id = a.id
            JOIN host h ON a.host_id = h.id
            WHERE p.data_inizio >= CURRENT_DATE - INTERVAL '1 month'
            GROUP BY h.codice_host
            ORDER BY totale_prenotazioni DESC
            """;

    private static final String SUPER_HOST = """
            SELECT
                h.codice_host,
                COUNT(p.id) AS totale_prenotazioni
            FROM prenotazione p
            JOIN abitazione a ON p.abitazione_id = a.id
            JOIN host h ON a.host_id = h.id
            GROUP BY h.codice_host
            HAVING COUNT(p.id) >= 100
            """;

    public List<StatisticheHost> findHostConPiuPrenotazioniUltimoMese() {

        List<StatisticheHost> result = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(HOST_PIU_PRENOTAZIONI_MESE);
                ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                result.add(
                        new StatisticheHost(
                                rs.getString("codice_host"),
                                rs.getLong("totale_prenotazioni")));
            }

        } catch (Exception e) {
            throw new RuntimeException("Errore nel recupero host più attivi", e);
        }

        return result;
    }

    public List<StatisticheHost> findSuperHost() {

        List<StatisticheHost> result = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(SUPER_HOST);
                ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                result.add(
                        new StatisticheHost(
                                rs.getString("codice_host"),
                                rs.getLong("totale_prenotazioni")));
            }

        } catch (Exception e) {
            throw new RuntimeException("Errore nel recupero super-host", e);
        }

        return result;
    }
}
