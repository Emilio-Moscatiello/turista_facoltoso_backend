package com.turistafacoltoso.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.turistafacoltoso.dto.StatisticheHostDTO;
import com.turistafacoltoso.model.Host;
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

    private static final String FIND_ALL = """
                SELECT * FROM host
            """;

    private static final String FIND_BY_ID = """
                SELECT * FROM host WHERE id = ?
            """;

    private static final String INSERT = """
                INSERT INTO host (id, utente_id, codice_host)
                VALUES (?, ?, ?)
            """;

    private static final String UPDATE = """
                UPDATE host
                SET utente_id = ?, codice_host = ?
                WHERE id = ?
            """;

    private static final String DELETE = """
                DELETE FROM host WHERE id = ?
            """;

    public List<Host> findAll() {
        List<Host> list = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(FIND_ALL);
                ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(mapRow(rs));
            }

        } catch (Exception e) {
            throw new RuntimeException("Errore recupero host", e);
        }

        return list;
    }

    public Host findById(UUID id) {
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(FIND_BY_ID)) {

            ps.setObject(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                }
            }

        } catch (Exception e) {
            throw new RuntimeException("Host non trovato", e);
        }

        throw new RuntimeException("Host non trovato");
    }

    public void save(Host host) {
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(INSERT)) {

            ps.setObject(1, host.getId());
            ps.setObject(2, host.getUtenteId());
            ps.setString(3, host.getCodiceHost());
            ps.executeUpdate();

        } catch (Exception e) {
            throw new RuntimeException("Errore creazione host", e);
        }
    }

    public void update(UUID id, Host host) {
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(UPDATE)) {

            ps.setObject(1, host.getUtenteId());
            ps.setString(2, host.getCodiceHost());
            ps.setObject(3, id);

            if (ps.executeUpdate() == 0) {
                throw new RuntimeException("Host non trovato");
            }

        } catch (Exception e) {
            throw new RuntimeException("Errore aggiornamento host", e);
        }
    }

    public void delete(UUID id) {
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(DELETE)) {

            if (ps.executeUpdate() == 0) {
                throw new RuntimeException("Host non trovato");
            }

        } catch (Exception e) {
            throw new RuntimeException("Errore eliminazione host", e);
        }
    }

    private Host mapRow(ResultSet rs) throws Exception {
        return new Host(
                rs.getObject("id", UUID.class),
                rs.getObject("utente_id", UUID.class),
                rs.getString("codice_host"));
    }

}
