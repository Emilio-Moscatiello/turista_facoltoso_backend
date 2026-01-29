package com.turistafacoltoso.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.turistafacoltoso.dto.StatisticheHostDTO;
import com.turistafacoltoso.util.DatabaseConnection;

public class HostRepository {

    private static final String HOST_PIU_PRENOTAZIONI_MESE = """
            SELECT
                h.codice_host,
                u.nome,
                u.cognome,
                COUNT(p.id) AS numero_prenotazioni
            FROM host h
            JOIN utente u ON h.utente_id = u.id
            JOIN abitazione a ON a.host_id = h.id
            JOIN prenotazione p ON p.abitazione_id = a.id
            WHERE p.data_inizio >= CURRENT_DATE - INTERVAL '1 month'
            GROUP BY h.codice_host, u.nome, u.cognome
            ORDER BY numero_prenotazioni DESC
            """;

    private static final String SUPER_HOST = """
            SELECT
                h.codice_host,
                u.nome,
                u.cognome,
                COUNT(p.id) AS numero_prenotazioni
            FROM host h
            JOIN utente u ON h.utente_id = u.id
            JOIN abitazione a ON a.host_id = h.id
            JOIN prenotazione p ON p.abitazione_id = a.id
            GROUP BY h.codice_host, u.nome, u.cognome
            HAVING COUNT(p.id) >= 100
            """;

    public List<StatisticheHostDTO> findHostConPiuPrenotazioniUltimoMese() {

        List<StatisticheHostDTO> result = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(HOST_PIU_PRENOTAZIONI_MESE);
                ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                result.add(
                        new StatisticheHostDTO(
                                rs.getString("codice_host"),
                                rs.getString("nome"),
                                rs.getString("cognome"),
                                rs.getInt("numero_prenotazioni")));
            }

        } catch (Exception e) {
            throw new RuntimeException("Errore nel recupero host più attivi", e);
        }

        return result;
    }

    public List<StatisticheHostDTO> findSuperHost() {

        List<StatisticheHostDTO> result = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(SUPER_HOST);
                ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                result.add(
                        new StatisticheHostDTO(
                                rs.getString("codice_host"),
                                rs.getString("nome"),
                                rs.getString("cognome"),
                                rs.getInt("numero_prenotazioni")));
            }

        } catch (Exception e) {
            throw new RuntimeException("Errore nel recupero super-host", e);
        }

        return result;
    }
}
